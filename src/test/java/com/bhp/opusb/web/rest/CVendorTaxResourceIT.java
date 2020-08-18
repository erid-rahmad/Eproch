package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorTax;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorTaxRepository;
import com.bhp.opusb.service.CVendorTaxService;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.bhp.opusb.service.mapper.CVendorTaxMapper;
import com.bhp.opusb.service.dto.CVendorTaxCriteria;
import com.bhp.opusb.service.CVendorTaxQueryService;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CVendorTaxResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorTaxResourceIT {

    private static final Boolean DEFAULT_E_INVOICE = false;
    private static final Boolean UPDATED_E_INVOICE = true;

    private static final Boolean DEFAULT_TAXABLE_EMPLOYERS = false;
    private static final Boolean UPDATED_TAXABLE_EMPLOYERS = true;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RATE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorTaxRepository cVendorTaxRepository;

    @Autowired
    private CVendorTaxMapper cVendorTaxMapper;

    @Autowired
    private CVendorTaxService cVendorTaxService;

    @Autowired
    private CVendorTaxQueryService cVendorTaxQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorTaxMockMvc;

    private CVendorTax cVendorTax;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorTax createEntity(EntityManager em) {
        CVendorTax cVendorTax = new CVendorTax()
            .eInvoice(DEFAULT_E_INVOICE)
            .taxableEmployers(DEFAULT_TAXABLE_EMPLOYERS)
            .rate(DEFAULT_RATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cVendorTax.setVendor(cVendor);
        // Add required entity
        CTax cTax;
        if (TestUtil.findAll(em, CTax.class).isEmpty()) {
            cTax = CTaxResourceIT.createEntity(em);
            em.persist(cTax);
            em.flush();
        } else {
            cTax = TestUtil.findAll(em, CTax.class).get(0);
        }
        cVendorTax.setTax(cTax);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorTax.setAdOrganization(aDOrganization);
        return cVendorTax;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorTax createUpdatedEntity(EntityManager em) {
        CVendorTax cVendorTax = new CVendorTax()
            .eInvoice(UPDATED_E_INVOICE)
            .taxableEmployers(UPDATED_TAXABLE_EMPLOYERS)
            .rate(UPDATED_RATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cVendorTax.setVendor(cVendor);
        // Add required entity
        CTax cTax;
        if (TestUtil.findAll(em, CTax.class).isEmpty()) {
            cTax = CTaxResourceIT.createUpdatedEntity(em);
            em.persist(cTax);
            em.flush();
        } else {
            cTax = TestUtil.findAll(em, CTax.class).get(0);
        }
        cVendorTax.setTax(cTax);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorTax.setAdOrganization(aDOrganization);
        return cVendorTax;
    }

    @BeforeEach
    public void initTest() {
        cVendorTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorTax() throws Exception {
        int databaseSizeBeforeCreate = cVendorTaxRepository.findAll().size();

        // Create the CVendorTax
        CVendorTaxDTO cVendorTaxDTO = cVendorTaxMapper.toDto(cVendorTax);
        restCVendorTaxMockMvc.perform(post("/api/c-vendor-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorTaxDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorTax in the database
        List<CVendorTax> cVendorTaxList = cVendorTaxRepository.findAll();
        assertThat(cVendorTaxList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorTax testCVendorTax = cVendorTaxList.get(cVendorTaxList.size() - 1);
        assertThat(testCVendorTax.isEInvoice()).isEqualTo(DEFAULT_E_INVOICE);
        assertThat(testCVendorTax.isTaxableEmployers()).isEqualTo(DEFAULT_TAXABLE_EMPLOYERS);
        assertThat(testCVendorTax.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCVendorTax.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorTax.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorTaxRepository.findAll().size();

        // Create the CVendorTax with an existing ID
        cVendorTax.setId(1L);
        CVendorTaxDTO cVendorTaxDTO = cVendorTaxMapper.toDto(cVendorTax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorTaxMockMvc.perform(post("/api/c-vendor-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorTax in the database
        List<CVendorTax> cVendorTaxList = cVendorTaxRepository.findAll();
        assertThat(cVendorTaxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCVendorTaxes() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].eInvoice").value(hasItem(DEFAULT_E_INVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].taxableEmployers").value(hasItem(DEFAULT_TAXABLE_EMPLOYERS.booleanValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorTax() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get the cVendorTax
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes/{id}", cVendorTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorTax.getId().intValue()))
            .andExpect(jsonPath("$.eInvoice").value(DEFAULT_E_INVOICE.booleanValue()))
            .andExpect(jsonPath("$.taxableEmployers").value(DEFAULT_TAXABLE_EMPLOYERS.booleanValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorTaxesByIdFiltering() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        Long id = cVendorTax.getId();

        defaultCVendorTaxShouldBeFound("id.equals=" + id);
        defaultCVendorTaxShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorTaxShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorTaxShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorTaxShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorTaxShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorTaxesByeInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where eInvoice equals to DEFAULT_E_INVOICE
        defaultCVendorTaxShouldBeFound("eInvoice.equals=" + DEFAULT_E_INVOICE);

        // Get all the cVendorTaxList where eInvoice equals to UPDATED_E_INVOICE
        defaultCVendorTaxShouldNotBeFound("eInvoice.equals=" + UPDATED_E_INVOICE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByeInvoiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where eInvoice not equals to DEFAULT_E_INVOICE
        defaultCVendorTaxShouldNotBeFound("eInvoice.notEquals=" + DEFAULT_E_INVOICE);

        // Get all the cVendorTaxList where eInvoice not equals to UPDATED_E_INVOICE
        defaultCVendorTaxShouldBeFound("eInvoice.notEquals=" + UPDATED_E_INVOICE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByeInvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where eInvoice in DEFAULT_E_INVOICE or UPDATED_E_INVOICE
        defaultCVendorTaxShouldBeFound("eInvoice.in=" + DEFAULT_E_INVOICE + "," + UPDATED_E_INVOICE);

        // Get all the cVendorTaxList where eInvoice equals to UPDATED_E_INVOICE
        defaultCVendorTaxShouldNotBeFound("eInvoice.in=" + UPDATED_E_INVOICE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByeInvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where eInvoice is not null
        defaultCVendorTaxShouldBeFound("eInvoice.specified=true");

        // Get all the cVendorTaxList where eInvoice is null
        defaultCVendorTaxShouldNotBeFound("eInvoice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByTaxableEmployersIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where taxableEmployers equals to DEFAULT_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldBeFound("taxableEmployers.equals=" + DEFAULT_TAXABLE_EMPLOYERS);

        // Get all the cVendorTaxList where taxableEmployers equals to UPDATED_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldNotBeFound("taxableEmployers.equals=" + UPDATED_TAXABLE_EMPLOYERS);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByTaxableEmployersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where taxableEmployers not equals to DEFAULT_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldNotBeFound("taxableEmployers.notEquals=" + DEFAULT_TAXABLE_EMPLOYERS);

        // Get all the cVendorTaxList where taxableEmployers not equals to UPDATED_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldBeFound("taxableEmployers.notEquals=" + UPDATED_TAXABLE_EMPLOYERS);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByTaxableEmployersIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where taxableEmployers in DEFAULT_TAXABLE_EMPLOYERS or UPDATED_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldBeFound("taxableEmployers.in=" + DEFAULT_TAXABLE_EMPLOYERS + "," + UPDATED_TAXABLE_EMPLOYERS);

        // Get all the cVendorTaxList where taxableEmployers equals to UPDATED_TAXABLE_EMPLOYERS
        defaultCVendorTaxShouldNotBeFound("taxableEmployers.in=" + UPDATED_TAXABLE_EMPLOYERS);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByTaxableEmployersIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where taxableEmployers is not null
        defaultCVendorTaxShouldBeFound("taxableEmployers.specified=true");

        // Get all the cVendorTaxList where taxableEmployers is null
        defaultCVendorTaxShouldNotBeFound("taxableEmployers.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate equals to DEFAULT_RATE
        defaultCVendorTaxShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate equals to UPDATED_RATE
        defaultCVendorTaxShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate not equals to DEFAULT_RATE
        defaultCVendorTaxShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate not equals to UPDATED_RATE
        defaultCVendorTaxShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultCVendorTaxShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the cVendorTaxList where rate equals to UPDATED_RATE
        defaultCVendorTaxShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate is not null
        defaultCVendorTaxShouldBeFound("rate.specified=true");

        // Get all the cVendorTaxList where rate is null
        defaultCVendorTaxShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate is greater than or equal to DEFAULT_RATE
        defaultCVendorTaxShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate is greater than or equal to UPDATED_RATE
        defaultCVendorTaxShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate is less than or equal to DEFAULT_RATE
        defaultCVendorTaxShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate is less than or equal to SMALLER_RATE
        defaultCVendorTaxShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate is less than DEFAULT_RATE
        defaultCVendorTaxShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate is less than UPDATED_RATE
        defaultCVendorTaxShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where rate is greater than DEFAULT_RATE
        defaultCVendorTaxShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the cVendorTaxList where rate is greater than SMALLER_RATE
        defaultCVendorTaxShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllCVendorTaxesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where uid equals to DEFAULT_UID
        defaultCVendorTaxShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorTaxList where uid equals to UPDATED_UID
        defaultCVendorTaxShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where uid not equals to DEFAULT_UID
        defaultCVendorTaxShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorTaxList where uid not equals to UPDATED_UID
        defaultCVendorTaxShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorTaxShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorTaxList where uid equals to UPDATED_UID
        defaultCVendorTaxShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where uid is not null
        defaultCVendorTaxShouldBeFound("uid.specified=true");

        // Get all the cVendorTaxList where uid is null
        defaultCVendorTaxShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where active equals to DEFAULT_ACTIVE
        defaultCVendorTaxShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorTaxList where active equals to UPDATED_ACTIVE
        defaultCVendorTaxShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where active not equals to DEFAULT_ACTIVE
        defaultCVendorTaxShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorTaxList where active not equals to UPDATED_ACTIVE
        defaultCVendorTaxShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorTaxShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorTaxList where active equals to UPDATED_ACTIVE
        defaultCVendorTaxShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        // Get all the cVendorTaxList where active is not null
        defaultCVendorTaxShouldBeFound("active.specified=true");

        // Get all the cVendorTaxList where active is null
        defaultCVendorTaxShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorTaxesByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = cVendorTax.getVendor();
        cVendorTaxRepository.saveAndFlush(cVendorTax);
        Long vendorId = vendor.getId();

        // Get all the cVendorTaxList where vendor equals to vendorId
        defaultCVendorTaxShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cVendorTaxList where vendor equals to vendorId + 1
        defaultCVendorTaxShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorTaxesByTaxIsEqualToSomething() throws Exception {
        // Get already existing entity
        CTax tax = cVendorTax.getTax();
        cVendorTaxRepository.saveAndFlush(cVendorTax);
        Long taxId = tax.getId();

        // Get all the cVendorTaxList where tax equals to taxId
        defaultCVendorTaxShouldBeFound("taxId.equals=" + taxId);

        // Get all the cVendorTaxList where tax equals to taxId + 1
        defaultCVendorTaxShouldNotBeFound("taxId.equals=" + (taxId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorTaxesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorTax.getAdOrganization();
        cVendorTaxRepository.saveAndFlush(cVendorTax);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorTaxList where adOrganization equals to adOrganizationId
        defaultCVendorTaxShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorTaxList where adOrganization equals to adOrganizationId + 1
        defaultCVendorTaxShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorTaxShouldBeFound(String filter) throws Exception {
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].eInvoice").value(hasItem(DEFAULT_E_INVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].taxableEmployers").value(hasItem(DEFAULT_TAXABLE_EMPLOYERS.booleanValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorTaxShouldNotBeFound(String filter) throws Exception {
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorTax() throws Exception {
        // Get the cVendorTax
        restCVendorTaxMockMvc.perform(get("/api/c-vendor-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorTax() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        int databaseSizeBeforeUpdate = cVendorTaxRepository.findAll().size();

        // Update the cVendorTax
        CVendorTax updatedCVendorTax = cVendorTaxRepository.findById(cVendorTax.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorTax are not directly saved in db
        em.detach(updatedCVendorTax);
        updatedCVendorTax
            .eInvoice(UPDATED_E_INVOICE)
            .taxableEmployers(UPDATED_TAXABLE_EMPLOYERS)
            .rate(UPDATED_RATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorTaxDTO cVendorTaxDTO = cVendorTaxMapper.toDto(updatedCVendorTax);

        restCVendorTaxMockMvc.perform(put("/api/c-vendor-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorTaxDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorTax in the database
        List<CVendorTax> cVendorTaxList = cVendorTaxRepository.findAll();
        assertThat(cVendorTaxList).hasSize(databaseSizeBeforeUpdate);
        CVendorTax testCVendorTax = cVendorTaxList.get(cVendorTaxList.size() - 1);
        assertThat(testCVendorTax.isEInvoice()).isEqualTo(UPDATED_E_INVOICE);
        assertThat(testCVendorTax.isTaxableEmployers()).isEqualTo(UPDATED_TAXABLE_EMPLOYERS);
        assertThat(testCVendorTax.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCVendorTax.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorTax.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorTax() throws Exception {
        int databaseSizeBeforeUpdate = cVendorTaxRepository.findAll().size();

        // Create the CVendorTax
        CVendorTaxDTO cVendorTaxDTO = cVendorTaxMapper.toDto(cVendorTax);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorTaxMockMvc.perform(put("/api/c-vendor-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorTax in the database
        List<CVendorTax> cVendorTaxList = cVendorTaxRepository.findAll();
        assertThat(cVendorTaxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorTax() throws Exception {
        // Initialize the database
        cVendorTaxRepository.saveAndFlush(cVendorTax);

        int databaseSizeBeforeDelete = cVendorTaxRepository.findAll().size();

        // Delete the cVendorTax
        restCVendorTaxMockMvc.perform(delete("/api/c-vendor-taxes/{id}", cVendorTax.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorTax> cVendorTaxList = cVendorTaxRepository.findAll();
        assertThat(cVendorTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
