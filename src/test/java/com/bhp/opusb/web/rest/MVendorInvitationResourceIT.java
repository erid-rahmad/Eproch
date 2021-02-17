package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorInvitation;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MVendorInvitationRepository;
import com.bhp.opusb.service.MVendorInvitationService;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;
import com.bhp.opusb.service.mapper.MVendorInvitationMapper;
import com.bhp.opusb.service.dto.MVendorInvitationCriteria;
import com.bhp.opusb.service.MVendorInvitationQueryService;

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
 * Integration tests for the {@link MVendorInvitationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorInvitationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorInvitationRepository mVendorInvitationRepository;

    @Autowired
    private MVendorInvitationMapper mVendorInvitationMapper;

    @Autowired
    private MVendorInvitationService mVendorInvitationService;

    @Autowired
    private MVendorInvitationQueryService mVendorInvitationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorInvitationMockMvc;

    private MVendorInvitation mVendorInvitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorInvitation createEntity(EntityManager em) {
        MVendorInvitation mVendorInvitation = new MVendorInvitation()
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
        mVendorInvitation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mVendorInvitation.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mVendorInvitation.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mVendorInvitation.setBusinessSubCategory(cBusinessCategory);
        return mVendorInvitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorInvitation createUpdatedEntity(EntityManager em) {
        MVendorInvitation mVendorInvitation = new MVendorInvitation()
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
        mVendorInvitation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mVendorInvitation.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mVendorInvitation.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mVendorInvitation.setBusinessSubCategory(cBusinessCategory);
        return mVendorInvitation;
    }

    @BeforeEach
    public void initTest() {
        mVendorInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorInvitation() throws Exception {
        int databaseSizeBeforeCreate = mVendorInvitationRepository.findAll().size();

        // Create the MVendorInvitation
        MVendorInvitationDTO mVendorInvitationDTO = mVendorInvitationMapper.toDto(mVendorInvitation);
        restMVendorInvitationMockMvc.perform(post("/api/m-vendor-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorInvitationDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorInvitation in the database
        List<MVendorInvitation> mVendorInvitationList = mVendorInvitationRepository.findAll();
        assertThat(mVendorInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorInvitation testMVendorInvitation = mVendorInvitationList.get(mVendorInvitationList.size() - 1);
        assertThat(testMVendorInvitation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorInvitation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorInvitationRepository.findAll().size();

        // Create the MVendorInvitation with an existing ID
        mVendorInvitation.setId(1L);
        MVendorInvitationDTO mVendorInvitationDTO = mVendorInvitationMapper.toDto(mVendorInvitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorInvitationMockMvc.perform(post("/api/m-vendor-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorInvitation in the database
        List<MVendorInvitation> mVendorInvitationList = mVendorInvitationRepository.findAll();
        assertThat(mVendorInvitationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorInvitations() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorInvitation() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get the mVendorInvitation
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations/{id}", mVendorInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorInvitation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorInvitationsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        Long id = mVendorInvitation.getId();

        defaultMVendorInvitationShouldBeFound("id.equals=" + id);
        defaultMVendorInvitationShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorInvitationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorInvitationShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorInvitationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorInvitationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorInvitationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where uid equals to DEFAULT_UID
        defaultMVendorInvitationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorInvitationList where uid equals to UPDATED_UID
        defaultMVendorInvitationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where uid not equals to DEFAULT_UID
        defaultMVendorInvitationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorInvitationList where uid not equals to UPDATED_UID
        defaultMVendorInvitationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorInvitationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorInvitationList where uid equals to UPDATED_UID
        defaultMVendorInvitationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where uid is not null
        defaultMVendorInvitationShouldBeFound("uid.specified=true");

        // Get all the mVendorInvitationList where uid is null
        defaultMVendorInvitationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where active equals to DEFAULT_ACTIVE
        defaultMVendorInvitationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorInvitationList where active equals to UPDATED_ACTIVE
        defaultMVendorInvitationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where active not equals to DEFAULT_ACTIVE
        defaultMVendorInvitationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorInvitationList where active not equals to UPDATED_ACTIVE
        defaultMVendorInvitationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorInvitationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorInvitationList where active equals to UPDATED_ACTIVE
        defaultMVendorInvitationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        // Get all the mVendorInvitationList where active is not null
        defaultMVendorInvitationShouldBeFound("active.specified=true");

        // Get all the mVendorInvitationList where active is null
        defaultMVendorInvitationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorInvitationsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mVendorInvitation.getBidding();
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);
        Long biddingId = bidding.getId();

        // Get all the mVendorInvitationList where bidding equals to biddingId
        defaultMVendorInvitationShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mVendorInvitationList where bidding equals to biddingId + 1
        defaultMVendorInvitationShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorInvitationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorInvitation.getAdOrganization();
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorInvitationList where adOrganization equals to adOrganizationId
        defaultMVendorInvitationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorInvitationList where adOrganization equals to adOrganizationId + 1
        defaultMVendorInvitationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorInvitationsByBusinessClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessClassification = mVendorInvitation.getBusinessClassification();
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);
        Long businessClassificationId = businessClassification.getId();

        // Get all the mVendorInvitationList where businessClassification equals to businessClassificationId
        defaultMVendorInvitationShouldBeFound("businessClassificationId.equals=" + businessClassificationId);

        // Get all the mVendorInvitationList where businessClassification equals to businessClassificationId + 1
        defaultMVendorInvitationShouldNotBeFound("businessClassificationId.equals=" + (businessClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorInvitationsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mVendorInvitation.getBusinessCategory();
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mVendorInvitationList where businessCategory equals to businessCategoryId
        defaultMVendorInvitationShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mVendorInvitationList where businessCategory equals to businessCategoryId + 1
        defaultMVendorInvitationShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorInvitationsByBusinessSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessSubCategory = mVendorInvitation.getBusinessSubCategory();
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);
        Long businessSubCategoryId = businessSubCategory.getId();

        // Get all the mVendorInvitationList where businessSubCategory equals to businessSubCategoryId
        defaultMVendorInvitationShouldBeFound("businessSubCategoryId.equals=" + businessSubCategoryId);

        // Get all the mVendorInvitationList where businessSubCategory equals to businessSubCategoryId + 1
        defaultMVendorInvitationShouldNotBeFound("businessSubCategoryId.equals=" + (businessSubCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorInvitationShouldBeFound(String filter) throws Exception {
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorInvitationShouldNotBeFound(String filter) throws Exception {
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorInvitation() throws Exception {
        // Get the mVendorInvitation
        restMVendorInvitationMockMvc.perform(get("/api/m-vendor-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorInvitation() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        int databaseSizeBeforeUpdate = mVendorInvitationRepository.findAll().size();

        // Update the mVendorInvitation
        MVendorInvitation updatedMVendorInvitation = mVendorInvitationRepository.findById(mVendorInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorInvitation are not directly saved in db
        em.detach(updatedMVendorInvitation);
        updatedMVendorInvitation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorInvitationDTO mVendorInvitationDTO = mVendorInvitationMapper.toDto(updatedMVendorInvitation);

        restMVendorInvitationMockMvc.perform(put("/api/m-vendor-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorInvitationDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorInvitation in the database
        List<MVendorInvitation> mVendorInvitationList = mVendorInvitationRepository.findAll();
        assertThat(mVendorInvitationList).hasSize(databaseSizeBeforeUpdate);
        MVendorInvitation testMVendorInvitation = mVendorInvitationList.get(mVendorInvitationList.size() - 1);
        assertThat(testMVendorInvitation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorInvitation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorInvitation() throws Exception {
        int databaseSizeBeforeUpdate = mVendorInvitationRepository.findAll().size();

        // Create the MVendorInvitation
        MVendorInvitationDTO mVendorInvitationDTO = mVendorInvitationMapper.toDto(mVendorInvitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorInvitationMockMvc.perform(put("/api/m-vendor-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorInvitation in the database
        List<MVendorInvitation> mVendorInvitationList = mVendorInvitationRepository.findAll();
        assertThat(mVendorInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorInvitation() throws Exception {
        // Initialize the database
        mVendorInvitationRepository.saveAndFlush(mVendorInvitation);

        int databaseSizeBeforeDelete = mVendorInvitationRepository.findAll().size();

        // Delete the mVendorInvitation
        restMVendorInvitationMockMvc.perform(delete("/api/m-vendor-invitations/{id}", mVendorInvitation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorInvitation> mVendorInvitationList = mVendorInvitationRepository.findAll();
        assertThat(mVendorInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
