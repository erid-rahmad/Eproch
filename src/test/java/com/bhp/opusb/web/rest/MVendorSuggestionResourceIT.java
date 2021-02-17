package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.MVendorSuggestionService;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MVendorSuggestionMapper;
import com.bhp.opusb.service.dto.MVendorSuggestionCriteria;
import com.bhp.opusb.service.MVendorSuggestionQueryService;

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
 * Integration tests for the {@link MVendorSuggestionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorSuggestionResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorSuggestionRepository mVendorSuggestionRepository;

    @Autowired
    private MVendorSuggestionMapper mVendorSuggestionMapper;

    @Autowired
    private MVendorSuggestionService mVendorSuggestionService;

    @Autowired
    private MVendorSuggestionQueryService mVendorSuggestionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorSuggestionMockMvc;

    private MVendorSuggestion mVendorSuggestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorSuggestion createEntity(EntityManager em) {
        MVendorSuggestion mVendorSuggestion = new MVendorSuggestion()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mVendorSuggestion.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorSuggestion.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mVendorSuggestion.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorSuggestion.setVendor(cVendor);
        return mVendorSuggestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorSuggestion createUpdatedEntity(EntityManager em) {
        MVendorSuggestion mVendorSuggestion = new MVendorSuggestion()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mVendorSuggestion.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorSuggestion.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mVendorSuggestion.setBusinessSubCategory(cBusinessCategory);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorSuggestion.setVendor(cVendor);
        return mVendorSuggestion;
    }

    @BeforeEach
    public void initTest() {
        mVendorSuggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorSuggestion() throws Exception {
        int databaseSizeBeforeCreate = mVendorSuggestionRepository.findAll().size();

        // Create the MVendorSuggestion
        MVendorSuggestionDTO mVendorSuggestionDTO = mVendorSuggestionMapper.toDto(mVendorSuggestion);
        restMVendorSuggestionMockMvc.perform(post("/api/m-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorSuggestionDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorSuggestion in the database
        List<MVendorSuggestion> mVendorSuggestionList = mVendorSuggestionRepository.findAll();
        assertThat(mVendorSuggestionList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorSuggestion testMVendorSuggestion = mVendorSuggestionList.get(mVendorSuggestionList.size() - 1);
        assertThat(testMVendorSuggestion.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorSuggestion.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorSuggestionRepository.findAll().size();

        // Create the MVendorSuggestion with an existing ID
        mVendorSuggestion.setId(1L);
        MVendorSuggestionDTO mVendorSuggestionDTO = mVendorSuggestionMapper.toDto(mVendorSuggestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorSuggestionMockMvc.perform(post("/api/m-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorSuggestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorSuggestion in the database
        List<MVendorSuggestion> mVendorSuggestionList = mVendorSuggestionRepository.findAll();
        assertThat(mVendorSuggestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorSuggestions() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorSuggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorSuggestion() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get the mVendorSuggestion
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions/{id}", mVendorSuggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorSuggestion.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorSuggestionsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        Long id = mVendorSuggestion.getId();

        defaultMVendorSuggestionShouldBeFound("id.equals=" + id);
        defaultMVendorSuggestionShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorSuggestionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorSuggestionShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorSuggestionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorSuggestionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorSuggestionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where uid equals to DEFAULT_UID
        defaultMVendorSuggestionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorSuggestionList where uid equals to UPDATED_UID
        defaultMVendorSuggestionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where uid not equals to DEFAULT_UID
        defaultMVendorSuggestionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorSuggestionList where uid not equals to UPDATED_UID
        defaultMVendorSuggestionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorSuggestionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorSuggestionList where uid equals to UPDATED_UID
        defaultMVendorSuggestionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where uid is not null
        defaultMVendorSuggestionShouldBeFound("uid.specified=true");

        // Get all the mVendorSuggestionList where uid is null
        defaultMVendorSuggestionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where active equals to DEFAULT_ACTIVE
        defaultMVendorSuggestionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorSuggestionList where active equals to UPDATED_ACTIVE
        defaultMVendorSuggestionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where active not equals to DEFAULT_ACTIVE
        defaultMVendorSuggestionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorSuggestionList where active not equals to UPDATED_ACTIVE
        defaultMVendorSuggestionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorSuggestionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorSuggestionList where active equals to UPDATED_ACTIVE
        defaultMVendorSuggestionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        // Get all the mVendorSuggestionList where active is not null
        defaultMVendorSuggestionShouldBeFound("active.specified=true");

        // Get all the mVendorSuggestionList where active is null
        defaultMVendorSuggestionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorSuggestionsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mVendorSuggestion.getBidding();
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);
        Long biddingId = bidding.getId();

        // Get all the mVendorSuggestionList where bidding equals to biddingId
        defaultMVendorSuggestionShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mVendorSuggestionList where bidding equals to biddingId + 1
        defaultMVendorSuggestionShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorSuggestionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorSuggestion.getAdOrganization();
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorSuggestionList where adOrganization equals to adOrganizationId
        defaultMVendorSuggestionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorSuggestionList where adOrganization equals to adOrganizationId + 1
        defaultMVendorSuggestionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorSuggestionsByBusinessSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessSubCategory = mVendorSuggestion.getBusinessSubCategory();
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);
        Long businessSubCategoryId = businessSubCategory.getId();

        // Get all the mVendorSuggestionList where businessSubCategory equals to businessSubCategoryId
        defaultMVendorSuggestionShouldBeFound("businessSubCategoryId.equals=" + businessSubCategoryId);

        // Get all the mVendorSuggestionList where businessSubCategory equals to businessSubCategoryId + 1
        defaultMVendorSuggestionShouldNotBeFound("businessSubCategoryId.equals=" + (businessSubCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorSuggestionsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mVendorSuggestion.getVendor();
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);
        Long vendorId = vendor.getId();

        // Get all the mVendorSuggestionList where vendor equals to vendorId
        defaultMVendorSuggestionShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mVendorSuggestionList where vendor equals to vendorId + 1
        defaultMVendorSuggestionShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorSuggestionShouldBeFound(String filter) throws Exception {
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorSuggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorSuggestionShouldNotBeFound(String filter) throws Exception {
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorSuggestion() throws Exception {
        // Get the mVendorSuggestion
        restMVendorSuggestionMockMvc.perform(get("/api/m-vendor-suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorSuggestion() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        int databaseSizeBeforeUpdate = mVendorSuggestionRepository.findAll().size();

        // Update the mVendorSuggestion
        MVendorSuggestion updatedMVendorSuggestion = mVendorSuggestionRepository.findById(mVendorSuggestion.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorSuggestion are not directly saved in db
        em.detach(updatedMVendorSuggestion);
        updatedMVendorSuggestion
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorSuggestionDTO mVendorSuggestionDTO = mVendorSuggestionMapper.toDto(updatedMVendorSuggestion);

        restMVendorSuggestionMockMvc.perform(put("/api/m-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorSuggestionDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorSuggestion in the database
        List<MVendorSuggestion> mVendorSuggestionList = mVendorSuggestionRepository.findAll();
        assertThat(mVendorSuggestionList).hasSize(databaseSizeBeforeUpdate);
        MVendorSuggestion testMVendorSuggestion = mVendorSuggestionList.get(mVendorSuggestionList.size() - 1);
        assertThat(testMVendorSuggestion.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorSuggestion.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = mVendorSuggestionRepository.findAll().size();

        // Create the MVendorSuggestion
        MVendorSuggestionDTO mVendorSuggestionDTO = mVendorSuggestionMapper.toDto(mVendorSuggestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorSuggestionMockMvc.perform(put("/api/m-vendor-suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorSuggestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorSuggestion in the database
        List<MVendorSuggestion> mVendorSuggestionList = mVendorSuggestionRepository.findAll();
        assertThat(mVendorSuggestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorSuggestion() throws Exception {
        // Initialize the database
        mVendorSuggestionRepository.saveAndFlush(mVendorSuggestion);

        int databaseSizeBeforeDelete = mVendorSuggestionRepository.findAll().size();

        // Delete the mVendorSuggestion
        restMVendorSuggestionMockMvc.perform(delete("/api/m-vendor-suggestions/{id}", mVendorSuggestion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorSuggestion> mVendorSuggestionList = mVendorSuggestionRepository.findAll();
        assertThat(mVendorSuggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
