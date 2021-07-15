package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalVendorSuggestion;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MPrequalVendorSuggestionRepository;
import com.bhp.opusb.service.MPrequalVendorSuggestionService;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MPrequalVendorSuggestionMapper;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionCriteria;
import com.bhp.opusb.service.MPrequalVendorSuggestionQueryService;

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
 * Integration tests for the {@link MPrequalVendorSuggestionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalVendorSuggestionResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalVendorSuggestionRepository mPrequalVendorSuggestionRepository;

    @Autowired
    private MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper;

    @Autowired
    private MPrequalVendorSuggestionService mPrequalVendorSuggestionService;

    @Autowired
    private MPrequalVendorSuggestionQueryService mPrequalVendorSuggestionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalVendorSuggestionMockMvc;

    private MPrequalVendorSuggestion mPrequalVendorSuggestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalVendorSuggestion createEntity(EntityManager em) {
        MPrequalVendorSuggestion mPrequalVendorSuggestion = new MPrequalVendorSuggestion()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalVendorSuggestion.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalVendorSuggestion.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mPrequalVendorSuggestion.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalVendorSuggestion.setVendor(cVendor);
        return mPrequalVendorSuggestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalVendorSuggestion createUpdatedEntity(EntityManager em) {
        MPrequalVendorSuggestion mPrequalVendorSuggestion = new MPrequalVendorSuggestion()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalVendorSuggestion.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalVendorSuggestion.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mPrequalVendorSuggestion.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalVendorSuggestion.setVendor(cVendor);
        return mPrequalVendorSuggestion;
    }

    @BeforeEach
    public void initTest() {
        mPrequalVendorSuggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalVendorSuggestion() throws Exception {
        int databaseSizeBeforeCreate = mPrequalVendorSuggestionRepository.findAll().size();

        // Create the MPrequalVendorSuggestion
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO = mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestion);
        restMPrequalVendorSuggestionMockMvc.perform(post("/api/m-prequal-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalVendorSuggestionDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalVendorSuggestion in the database
        List<MPrequalVendorSuggestion> mPrequalVendorSuggestionList = mPrequalVendorSuggestionRepository.findAll();
        assertThat(mPrequalVendorSuggestionList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalVendorSuggestion testMPrequalVendorSuggestion = mPrequalVendorSuggestionList.get(mPrequalVendorSuggestionList.size() - 1);
        assertThat(testMPrequalVendorSuggestion.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalVendorSuggestion.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalVendorSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalVendorSuggestionRepository.findAll().size();

        // Create the MPrequalVendorSuggestion with an existing ID
        mPrequalVendorSuggestion.setId(1L);
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO = mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalVendorSuggestionMockMvc.perform(post("/api/m-prequal-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalVendorSuggestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalVendorSuggestion in the database
        List<MPrequalVendorSuggestion> mPrequalVendorSuggestionList = mPrequalVendorSuggestionRepository.findAll();
        assertThat(mPrequalVendorSuggestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestions() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalVendorSuggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalVendorSuggestion() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get the mPrequalVendorSuggestion
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions/{id}", mPrequalVendorSuggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalVendorSuggestion.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalVendorSuggestionsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        Long id = mPrequalVendorSuggestion.getId();

        defaultMPrequalVendorSuggestionShouldBeFound("id.equals=" + id);
        defaultMPrequalVendorSuggestionShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalVendorSuggestionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalVendorSuggestionShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalVendorSuggestionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalVendorSuggestionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where uid equals to DEFAULT_UID
        defaultMPrequalVendorSuggestionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalVendorSuggestionList where uid equals to UPDATED_UID
        defaultMPrequalVendorSuggestionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where uid not equals to DEFAULT_UID
        defaultMPrequalVendorSuggestionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalVendorSuggestionList where uid not equals to UPDATED_UID
        defaultMPrequalVendorSuggestionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalVendorSuggestionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalVendorSuggestionList where uid equals to UPDATED_UID
        defaultMPrequalVendorSuggestionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where uid is not null
        defaultMPrequalVendorSuggestionShouldBeFound("uid.specified=true");

        // Get all the mPrequalVendorSuggestionList where uid is null
        defaultMPrequalVendorSuggestionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where active equals to DEFAULT_ACTIVE
        defaultMPrequalVendorSuggestionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalVendorSuggestionList where active equals to UPDATED_ACTIVE
        defaultMPrequalVendorSuggestionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalVendorSuggestionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalVendorSuggestionList where active not equals to UPDATED_ACTIVE
        defaultMPrequalVendorSuggestionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalVendorSuggestionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalVendorSuggestionList where active equals to UPDATED_ACTIVE
        defaultMPrequalVendorSuggestionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        // Get all the mPrequalVendorSuggestionList where active is not null
        defaultMPrequalVendorSuggestionShouldBeFound("active.specified=true");

        // Get all the mPrequalVendorSuggestionList where active is null
        defaultMPrequalVendorSuggestionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalVendorSuggestion.getPrequalification();
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalVendorSuggestionList where prequalification equals to prequalificationId
        defaultMPrequalVendorSuggestionShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalVendorSuggestionList where prequalification equals to prequalificationId + 1
        defaultMPrequalVendorSuggestionShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalVendorSuggestion.getAdOrganization();
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalVendorSuggestionList where adOrganization equals to adOrganizationId
        defaultMPrequalVendorSuggestionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalVendorSuggestionList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalVendorSuggestionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByBusinessSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessSubCategory = mPrequalVendorSuggestion.getBusinessSubCategory();
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);
        Long businessSubCategoryId = businessSubCategory.getId();

        // Get all the mPrequalVendorSuggestionList where businessSubCategory equals to businessSubCategoryId
        defaultMPrequalVendorSuggestionShouldBeFound("businessSubCategoryId.equals=" + businessSubCategoryId);

        // Get all the mPrequalVendorSuggestionList where businessSubCategory equals to businessSubCategoryId + 1
        defaultMPrequalVendorSuggestionShouldNotBeFound("businessSubCategoryId.equals=" + (businessSubCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalVendorSuggestionsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPrequalVendorSuggestion.getVendor();
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);
        Long vendorId = vendor.getId();

        // Get all the mPrequalVendorSuggestionList where vendor equals to vendorId
        defaultMPrequalVendorSuggestionShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPrequalVendorSuggestionList where vendor equals to vendorId + 1
        defaultMPrequalVendorSuggestionShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalVendorSuggestionShouldBeFound(String filter) throws Exception {
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalVendorSuggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalVendorSuggestionShouldNotBeFound(String filter) throws Exception {
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalVendorSuggestion() throws Exception {
        // Get the mPrequalVendorSuggestion
        restMPrequalVendorSuggestionMockMvc.perform(get("/api/m-prequal-vendor-suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalVendorSuggestion() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        int databaseSizeBeforeUpdate = mPrequalVendorSuggestionRepository.findAll().size();

        // Update the mPrequalVendorSuggestion
        MPrequalVendorSuggestion updatedMPrequalVendorSuggestion = mPrequalVendorSuggestionRepository.findById(mPrequalVendorSuggestion.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalVendorSuggestion are not directly saved in db
        em.detach(updatedMPrequalVendorSuggestion);
        updatedMPrequalVendorSuggestion
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO = mPrequalVendorSuggestionMapper.toDto(updatedMPrequalVendorSuggestion);

        restMPrequalVendorSuggestionMockMvc.perform(put("/api/m-prequal-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalVendorSuggestionDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalVendorSuggestion in the database
        List<MPrequalVendorSuggestion> mPrequalVendorSuggestionList = mPrequalVendorSuggestionRepository.findAll();
        assertThat(mPrequalVendorSuggestionList).hasSize(databaseSizeBeforeUpdate);
        MPrequalVendorSuggestion testMPrequalVendorSuggestion = mPrequalVendorSuggestionList.get(mPrequalVendorSuggestionList.size() - 1);
        assertThat(testMPrequalVendorSuggestion.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalVendorSuggestion.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalVendorSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalVendorSuggestionRepository.findAll().size();

        // Create the MPrequalVendorSuggestion
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO = mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalVendorSuggestionMockMvc.perform(put("/api/m-prequal-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalVendorSuggestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalVendorSuggestion in the database
        List<MPrequalVendorSuggestion> mPrequalVendorSuggestionList = mPrequalVendorSuggestionRepository.findAll();
        assertThat(mPrequalVendorSuggestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalVendorSuggestion() throws Exception {
        // Initialize the database
        mPrequalVendorSuggestionRepository.saveAndFlush(mPrequalVendorSuggestion);

        int databaseSizeBeforeDelete = mPrequalVendorSuggestionRepository.findAll().size();

        // Delete the mPrequalVendorSuggestion
        restMPrequalVendorSuggestionMockMvc.perform(delete("/api/m-prequal-vendor-suggestions/{id}", mPrequalVendorSuggestion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalVendorSuggestion> mPrequalVendorSuggestionList = mPrequalVendorSuggestionRepository.findAll();
        assertThat(mPrequalVendorSuggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
