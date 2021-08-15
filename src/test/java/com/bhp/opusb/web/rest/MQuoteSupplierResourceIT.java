package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MQuoteSupplier;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MQuoteSupplierRepository;
import com.bhp.opusb.service.MQuoteSupplierService;
import com.bhp.opusb.service.dto.MQuoteSupplierDTO;
import com.bhp.opusb.service.mapper.MQuoteSupplierMapper;
import com.bhp.opusb.service.dto.MQuoteSupplierCriteria;
import com.bhp.opusb.service.MQuoteSupplierQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MQuoteSupplierResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MQuoteSupplierResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    @Autowired
    private MQuoteSupplierRepository mQuoteSupplierRepository;

    @Autowired
    private MQuoteSupplierMapper mQuoteSupplierMapper;

    @Autowired
    private MQuoteSupplierService mQuoteSupplierService;

    @Autowired
    private MQuoteSupplierQueryService mQuoteSupplierQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMQuoteSupplierMockMvc;

    private MQuoteSupplier mQuoteSupplier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MQuoteSupplier createEntity(EntityManager em) {
        MQuoteSupplier mQuoteSupplier = new MQuoteSupplier()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .dateRequired(DEFAULT_DATE_REQUIRED);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mQuoteSupplier.setQuotation(mRfq);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mQuoteSupplier.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mQuoteSupplier.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mQuoteSupplier.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mQuoteSupplier.setVendor(cVendor);
        return mQuoteSupplier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MQuoteSupplier createUpdatedEntity(EntityManager em) {
        MQuoteSupplier mQuoteSupplier = new MQuoteSupplier()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .dateRequired(UPDATED_DATE_REQUIRED);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createUpdatedEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mQuoteSupplier.setQuotation(mRfq);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mQuoteSupplier.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mQuoteSupplier.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mQuoteSupplier.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mQuoteSupplier.setVendor(cVendor);
        return mQuoteSupplier;
    }

    @BeforeEach
    public void initTest() {
        mQuoteSupplier = createEntity(em);
    }

    @Test
    @Transactional
    public void createMQuoteSupplier() throws Exception {
        int databaseSizeBeforeCreate = mQuoteSupplierRepository.findAll().size();

        // Create the MQuoteSupplier
        MQuoteSupplierDTO mQuoteSupplierDTO = mQuoteSupplierMapper.toDto(mQuoteSupplier);
        restMQuoteSupplierMockMvc.perform(post("/api/m-quote-suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mQuoteSupplierDTO)))
            .andExpect(status().isCreated());

        // Validate the MQuoteSupplier in the database
        List<MQuoteSupplier> mQuoteSupplierList = mQuoteSupplierRepository.findAll();
        assertThat(mQuoteSupplierList).hasSize(databaseSizeBeforeCreate + 1);
        MQuoteSupplier testMQuoteSupplier = mQuoteSupplierList.get(mQuoteSupplierList.size() - 1);
        assertThat(testMQuoteSupplier.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMQuoteSupplier.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMQuoteSupplier.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void createMQuoteSupplierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mQuoteSupplierRepository.findAll().size();

        // Create the MQuoteSupplier with an existing ID
        mQuoteSupplier.setId(1L);
        MQuoteSupplierDTO mQuoteSupplierDTO = mQuoteSupplierMapper.toDto(mQuoteSupplier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMQuoteSupplierMockMvc.perform(post("/api/m-quote-suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mQuoteSupplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MQuoteSupplier in the database
        List<MQuoteSupplier> mQuoteSupplierList = mQuoteSupplierRepository.findAll();
        assertThat(mQuoteSupplierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliers() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mQuoteSupplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())));
    }
    
    @Test
    @Transactional
    public void getMQuoteSupplier() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get the mQuoteSupplier
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers/{id}", mQuoteSupplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mQuoteSupplier.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()));
    }


    @Test
    @Transactional
    public void getMQuoteSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        Long id = mQuoteSupplier.getId();

        defaultMQuoteSupplierShouldBeFound("id.equals=" + id);
        defaultMQuoteSupplierShouldNotBeFound("id.notEquals=" + id);

        defaultMQuoteSupplierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMQuoteSupplierShouldNotBeFound("id.greaterThan=" + id);

        defaultMQuoteSupplierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMQuoteSupplierShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where uid equals to DEFAULT_UID
        defaultMQuoteSupplierShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mQuoteSupplierList where uid equals to UPDATED_UID
        defaultMQuoteSupplierShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where uid not equals to DEFAULT_UID
        defaultMQuoteSupplierShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mQuoteSupplierList where uid not equals to UPDATED_UID
        defaultMQuoteSupplierShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where uid in DEFAULT_UID or UPDATED_UID
        defaultMQuoteSupplierShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mQuoteSupplierList where uid equals to UPDATED_UID
        defaultMQuoteSupplierShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where uid is not null
        defaultMQuoteSupplierShouldBeFound("uid.specified=true");

        // Get all the mQuoteSupplierList where uid is null
        defaultMQuoteSupplierShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where active equals to DEFAULT_ACTIVE
        defaultMQuoteSupplierShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mQuoteSupplierList where active equals to UPDATED_ACTIVE
        defaultMQuoteSupplierShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where active not equals to DEFAULT_ACTIVE
        defaultMQuoteSupplierShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mQuoteSupplierList where active not equals to UPDATED_ACTIVE
        defaultMQuoteSupplierShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMQuoteSupplierShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mQuoteSupplierList where active equals to UPDATED_ACTIVE
        defaultMQuoteSupplierShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where active is not null
        defaultMQuoteSupplierShouldBeFound("active.specified=true");

        // Get all the mQuoteSupplierList where active is null
        defaultMQuoteSupplierShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired is not null
        defaultMQuoteSupplierShouldBeFound("dateRequired.specified=true");

        // Get all the mQuoteSupplierList where dateRequired is null
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMQuoteSuppliersByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        // Get all the mQuoteSupplierList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMQuoteSupplierShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mQuoteSupplierList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMQuoteSupplierShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByQuotationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfq quotation = mQuoteSupplier.getQuotation();
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);
        Long quotationId = quotation.getId();

        // Get all the mQuoteSupplierList where quotation equals to quotationId
        defaultMQuoteSupplierShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the mQuoteSupplierList where quotation equals to quotationId + 1
        defaultMQuoteSupplierShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByBusinessClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessClassification = mQuoteSupplier.getBusinessClassification();
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);
        Long businessClassificationId = businessClassification.getId();

        // Get all the mQuoteSupplierList where businessClassification equals to businessClassificationId
        defaultMQuoteSupplierShouldBeFound("businessClassificationId.equals=" + businessClassificationId);

        // Get all the mQuoteSupplierList where businessClassification equals to businessClassificationId + 1
        defaultMQuoteSupplierShouldNotBeFound("businessClassificationId.equals=" + (businessClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mQuoteSupplier.getBusinessCategory();
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mQuoteSupplierList where businessCategory equals to businessCategoryId
        defaultMQuoteSupplierShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mQuoteSupplierList where businessCategory equals to businessCategoryId + 1
        defaultMQuoteSupplierShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByBusinessSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessSubCategory = mQuoteSupplier.getBusinessSubCategory();
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);
        Long businessSubCategoryId = businessSubCategory.getId();

        // Get all the mQuoteSupplierList where businessSubCategory equals to businessSubCategoryId
        defaultMQuoteSupplierShouldBeFound("businessSubCategoryId.equals=" + businessSubCategoryId);

        // Get all the mQuoteSupplierList where businessSubCategory equals to businessSubCategoryId + 1
        defaultMQuoteSupplierShouldNotBeFound("businessSubCategoryId.equals=" + (businessSubCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMQuoteSuppliersByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mQuoteSupplier.getVendor();
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);
        Long vendorId = vendor.getId();

        // Get all the mQuoteSupplierList where vendor equals to vendorId
        defaultMQuoteSupplierShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mQuoteSupplierList where vendor equals to vendorId + 1
        defaultMQuoteSupplierShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMQuoteSupplierShouldBeFound(String filter) throws Exception {
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mQuoteSupplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())));

        // Check, that the count call also returns 1
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMQuoteSupplierShouldNotBeFound(String filter) throws Exception {
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMQuoteSupplier() throws Exception {
        // Get the mQuoteSupplier
        restMQuoteSupplierMockMvc.perform(get("/api/m-quote-suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMQuoteSupplier() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        int databaseSizeBeforeUpdate = mQuoteSupplierRepository.findAll().size();

        // Update the mQuoteSupplier
        MQuoteSupplier updatedMQuoteSupplier = mQuoteSupplierRepository.findById(mQuoteSupplier.getId()).get();
        // Disconnect from session so that the updates on updatedMQuoteSupplier are not directly saved in db
        em.detach(updatedMQuoteSupplier);
        updatedMQuoteSupplier
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .dateRequired(UPDATED_DATE_REQUIRED);
        MQuoteSupplierDTO mQuoteSupplierDTO = mQuoteSupplierMapper.toDto(updatedMQuoteSupplier);

        restMQuoteSupplierMockMvc.perform(put("/api/m-quote-suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mQuoteSupplierDTO)))
            .andExpect(status().isOk());

        // Validate the MQuoteSupplier in the database
        List<MQuoteSupplier> mQuoteSupplierList = mQuoteSupplierRepository.findAll();
        assertThat(mQuoteSupplierList).hasSize(databaseSizeBeforeUpdate);
        MQuoteSupplier testMQuoteSupplier = mQuoteSupplierList.get(mQuoteSupplierList.size() - 1);
        assertThat(testMQuoteSupplier.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMQuoteSupplier.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMQuoteSupplier.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void updateNonExistingMQuoteSupplier() throws Exception {
        int databaseSizeBeforeUpdate = mQuoteSupplierRepository.findAll().size();

        // Create the MQuoteSupplier
        MQuoteSupplierDTO mQuoteSupplierDTO = mQuoteSupplierMapper.toDto(mQuoteSupplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMQuoteSupplierMockMvc.perform(put("/api/m-quote-suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mQuoteSupplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MQuoteSupplier in the database
        List<MQuoteSupplier> mQuoteSupplierList = mQuoteSupplierRepository.findAll();
        assertThat(mQuoteSupplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMQuoteSupplier() throws Exception {
        // Initialize the database
        mQuoteSupplierRepository.saveAndFlush(mQuoteSupplier);

        int databaseSizeBeforeDelete = mQuoteSupplierRepository.findAll().size();

        // Delete the mQuoteSupplier
        restMQuoteSupplierMockMvc.perform(delete("/api/m-quote-suppliers/{id}", mQuoteSupplier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MQuoteSupplier> mQuoteSupplierList = mQuoteSupplierRepository.findAll();
        assertThat(mQuoteSupplierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
