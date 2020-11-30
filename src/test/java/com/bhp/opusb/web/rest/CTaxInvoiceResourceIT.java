package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CTaxInvoice;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CTaxInvoiceRepository;
import com.bhp.opusb.service.CTaxInvoiceService;
import com.bhp.opusb.service.dto.CTaxInvoiceDTO;
import com.bhp.opusb.service.mapper.CTaxInvoiceMapper;
import com.bhp.opusb.service.dto.CTaxInvoiceCriteria;
import com.bhp.opusb.service.CTaxInvoiceQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CTaxInvoiceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CTaxInvoiceResourceIT {

    private static final String DEFAULT_START_NO = "AAAAAAAAAA";
    private static final String UPDATED_START_NO = "BBBBBBBBBB";

    private static final String DEFAULT_END_NO = "AAAAAAAAAA";
    private static final String UPDATED_END_NO = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CTaxInvoiceRepository cTaxInvoiceRepository;

    @Autowired
    private CTaxInvoiceMapper cTaxInvoiceMapper;

    @Autowired
    private CTaxInvoiceService cTaxInvoiceService;

    @Autowired
    private CTaxInvoiceQueryService cTaxInvoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCTaxInvoiceMockMvc;

    private CTaxInvoice cTaxInvoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxInvoice createEntity(EntityManager em) {
        CTaxInvoice cTaxInvoice = new CTaxInvoice()
            .startNo(DEFAULT_START_NO)
            .endNo(DEFAULT_END_NO)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cTaxInvoice.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cTaxInvoice.setVendor(cVendor);
        return cTaxInvoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxInvoice createUpdatedEntity(EntityManager em) {
        CTaxInvoice cTaxInvoice = new CTaxInvoice()
            .startNo(UPDATED_START_NO)
            .endNo(UPDATED_END_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cTaxInvoice.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cTaxInvoice.setVendor(cVendor);
        return cTaxInvoice;
    }

    @BeforeEach
    public void initTest() {
        cTaxInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCTaxInvoice() throws Exception {
        int databaseSizeBeforeCreate = cTaxInvoiceRepository.findAll().size();

        // Create the CTaxInvoice
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(cTaxInvoice);
        restCTaxInvoiceMockMvc.perform(post("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the CTaxInvoice in the database
        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        CTaxInvoice testCTaxInvoice = cTaxInvoiceList.get(cTaxInvoiceList.size() - 1);
        assertThat(testCTaxInvoice.getStartNo()).isEqualTo(DEFAULT_START_NO);
        assertThat(testCTaxInvoice.getEndNo()).isEqualTo(DEFAULT_END_NO);
        assertThat(testCTaxInvoice.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCTaxInvoice.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCTaxInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cTaxInvoiceRepository.findAll().size();

        // Create the CTaxInvoice with an existing ID
        cTaxInvoice.setId(1L);
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(cTaxInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCTaxInvoiceMockMvc.perform(post("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxInvoice in the database
        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxInvoiceRepository.findAll().size();
        // set the field null
        cTaxInvoice.setStartNo(null);

        // Create the CTaxInvoice, which fails.
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(cTaxInvoice);

        restCTaxInvoiceMockMvc.perform(post("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxInvoiceRepository.findAll().size();
        // set the field null
        cTaxInvoice.setEndNo(null);

        // Create the CTaxInvoice, which fails.
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(cTaxInvoice);

        restCTaxInvoiceMockMvc.perform(post("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoices() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].startNo").value(hasItem(DEFAULT_START_NO)))
            .andExpect(jsonPath("$.[*].endNo").value(hasItem(DEFAULT_END_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCTaxInvoice() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get the cTaxInvoice
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices/{id}", cTaxInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cTaxInvoice.getId().intValue()))
            .andExpect(jsonPath("$.startNo").value(DEFAULT_START_NO))
            .andExpect(jsonPath("$.endNo").value(DEFAULT_END_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCTaxInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        Long id = cTaxInvoice.getId();

        defaultCTaxInvoiceShouldBeFound("id.equals=" + id);
        defaultCTaxInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultCTaxInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCTaxInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultCTaxInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCTaxInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo equals to DEFAULT_START_NO
        defaultCTaxInvoiceShouldBeFound("startNo.equals=" + DEFAULT_START_NO);

        // Get all the cTaxInvoiceList where startNo equals to UPDATED_START_NO
        defaultCTaxInvoiceShouldNotBeFound("startNo.equals=" + UPDATED_START_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo not equals to DEFAULT_START_NO
        defaultCTaxInvoiceShouldNotBeFound("startNo.notEquals=" + DEFAULT_START_NO);

        // Get all the cTaxInvoiceList where startNo not equals to UPDATED_START_NO
        defaultCTaxInvoiceShouldBeFound("startNo.notEquals=" + UPDATED_START_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo in DEFAULT_START_NO or UPDATED_START_NO
        defaultCTaxInvoiceShouldBeFound("startNo.in=" + DEFAULT_START_NO + "," + UPDATED_START_NO);

        // Get all the cTaxInvoiceList where startNo equals to UPDATED_START_NO
        defaultCTaxInvoiceShouldNotBeFound("startNo.in=" + UPDATED_START_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo is not null
        defaultCTaxInvoiceShouldBeFound("startNo.specified=true");

        // Get all the cTaxInvoiceList where startNo is null
        defaultCTaxInvoiceShouldNotBeFound("startNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoContainsSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo contains DEFAULT_START_NO
        defaultCTaxInvoiceShouldBeFound("startNo.contains=" + DEFAULT_START_NO);

        // Get all the cTaxInvoiceList where startNo contains UPDATED_START_NO
        defaultCTaxInvoiceShouldNotBeFound("startNo.contains=" + UPDATED_START_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByStartNoNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where startNo does not contain DEFAULT_START_NO
        defaultCTaxInvoiceShouldNotBeFound("startNo.doesNotContain=" + DEFAULT_START_NO);

        // Get all the cTaxInvoiceList where startNo does not contain UPDATED_START_NO
        defaultCTaxInvoiceShouldBeFound("startNo.doesNotContain=" + UPDATED_START_NO);
    }


    @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo equals to DEFAULT_END_NO
        defaultCTaxInvoiceShouldBeFound("endNo.equals=" + DEFAULT_END_NO);

        // Get all the cTaxInvoiceList where endNo equals to UPDATED_END_NO
        defaultCTaxInvoiceShouldNotBeFound("endNo.equals=" + UPDATED_END_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo not equals to DEFAULT_END_NO
        defaultCTaxInvoiceShouldNotBeFound("endNo.notEquals=" + DEFAULT_END_NO);

        // Get all the cTaxInvoiceList where endNo not equals to UPDATED_END_NO
        defaultCTaxInvoiceShouldBeFound("endNo.notEquals=" + UPDATED_END_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo in DEFAULT_END_NO or UPDATED_END_NO
        defaultCTaxInvoiceShouldBeFound("endNo.in=" + DEFAULT_END_NO + "," + UPDATED_END_NO);

        // Get all the cTaxInvoiceList where endNo equals to UPDATED_END_NO
        defaultCTaxInvoiceShouldNotBeFound("endNo.in=" + UPDATED_END_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo is not null
        defaultCTaxInvoiceShouldBeFound("endNo.specified=true");

        // Get all the cTaxInvoiceList where endNo is null
        defaultCTaxInvoiceShouldNotBeFound("endNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoContainsSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo contains DEFAULT_END_NO
        defaultCTaxInvoiceShouldBeFound("endNo.contains=" + DEFAULT_END_NO);

        // Get all the cTaxInvoiceList where endNo contains UPDATED_END_NO
        defaultCTaxInvoiceShouldNotBeFound("endNo.contains=" + UPDATED_END_NO);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByEndNoNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where endNo does not contain DEFAULT_END_NO
        defaultCTaxInvoiceShouldNotBeFound("endNo.doesNotContain=" + DEFAULT_END_NO);

        // Get all the cTaxInvoiceList where endNo does not contain UPDATED_END_NO
        defaultCTaxInvoiceShouldBeFound("endNo.doesNotContain=" + UPDATED_END_NO);
    }


    @Test
    @Transactional
    public void getAllCTaxInvoicesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where uid equals to DEFAULT_UID
        defaultCTaxInvoiceShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cTaxInvoiceList where uid equals to UPDATED_UID
        defaultCTaxInvoiceShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where uid not equals to DEFAULT_UID
        defaultCTaxInvoiceShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cTaxInvoiceList where uid not equals to UPDATED_UID
        defaultCTaxInvoiceShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where uid in DEFAULT_UID or UPDATED_UID
        defaultCTaxInvoiceShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cTaxInvoiceList where uid equals to UPDATED_UID
        defaultCTaxInvoiceShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where uid is not null
        defaultCTaxInvoiceShouldBeFound("uid.specified=true");

        // Get all the cTaxInvoiceList where uid is null
        defaultCTaxInvoiceShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where active equals to DEFAULT_ACTIVE
        defaultCTaxInvoiceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cTaxInvoiceList where active equals to UPDATED_ACTIVE
        defaultCTaxInvoiceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where active not equals to DEFAULT_ACTIVE
        defaultCTaxInvoiceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cTaxInvoiceList where active not equals to UPDATED_ACTIVE
        defaultCTaxInvoiceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCTaxInvoiceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cTaxInvoiceList where active equals to UPDATED_ACTIVE
        defaultCTaxInvoiceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        // Get all the cTaxInvoiceList where active is not null
        defaultCTaxInvoiceShouldBeFound("active.specified=true");

        // Get all the cTaxInvoiceList where active is null
        defaultCTaxInvoiceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxInvoicesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cTaxInvoice.getAdOrganization();
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cTaxInvoiceList where adOrganization equals to adOrganizationId
        defaultCTaxInvoiceShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cTaxInvoiceList where adOrganization equals to adOrganizationId + 1
        defaultCTaxInvoiceShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCTaxInvoicesByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = cTaxInvoice.getVendor();
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);
        Long vendorId = vendor.getId();

        // Get all the cTaxInvoiceList where vendor equals to vendorId
        defaultCTaxInvoiceShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cTaxInvoiceList where vendor equals to vendorId + 1
        defaultCTaxInvoiceShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCTaxInvoiceShouldBeFound(String filter) throws Exception {
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].startNo").value(hasItem(DEFAULT_START_NO)))
            .andExpect(jsonPath("$.[*].endNo").value(hasItem(DEFAULT_END_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCTaxInvoiceShouldNotBeFound(String filter) throws Exception {
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCTaxInvoice() throws Exception {
        // Get the cTaxInvoice
        restCTaxInvoiceMockMvc.perform(get("/api/c-tax-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCTaxInvoice() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        int databaseSizeBeforeUpdate = cTaxInvoiceRepository.findAll().size();

        // Update the cTaxInvoice
        CTaxInvoice updatedCTaxInvoice = cTaxInvoiceRepository.findById(cTaxInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedCTaxInvoice are not directly saved in db
        em.detach(updatedCTaxInvoice);
        updatedCTaxInvoice
            .startNo(UPDATED_START_NO)
            .endNo(UPDATED_END_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(updatedCTaxInvoice);

        restCTaxInvoiceMockMvc.perform(put("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the CTaxInvoice in the database
        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeUpdate);
        CTaxInvoice testCTaxInvoice = cTaxInvoiceList.get(cTaxInvoiceList.size() - 1);
        assertThat(testCTaxInvoice.getStartNo()).isEqualTo(UPDATED_START_NO);
        assertThat(testCTaxInvoice.getEndNo()).isEqualTo(UPDATED_END_NO);
        assertThat(testCTaxInvoice.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCTaxInvoice.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCTaxInvoice() throws Exception {
        int databaseSizeBeforeUpdate = cTaxInvoiceRepository.findAll().size();

        // Create the CTaxInvoice
        CTaxInvoiceDTO cTaxInvoiceDTO = cTaxInvoiceMapper.toDto(cTaxInvoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCTaxInvoiceMockMvc.perform(put("/api/c-tax-invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxInvoice in the database
        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCTaxInvoice() throws Exception {
        // Initialize the database
        cTaxInvoiceRepository.saveAndFlush(cTaxInvoice);

        int databaseSizeBeforeDelete = cTaxInvoiceRepository.findAll().size();

        // Delete the cTaxInvoice
        restCTaxInvoiceMockMvc.perform(delete("/api/c-tax-invoices/{id}", cTaxInvoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CTaxInvoice> cTaxInvoiceList = cTaxInvoiceRepository.findAll();
        assertThat(cTaxInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
