package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionParticipant;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MAuctionParticipantRepository;
import com.bhp.opusb.service.MAuctionParticipantService;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;
import com.bhp.opusb.service.mapper.MAuctionParticipantMapper;
import com.bhp.opusb.service.dto.MAuctionParticipantCriteria;
import com.bhp.opusb.service.MAuctionParticipantQueryService;

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
 * Integration tests for the {@link MAuctionParticipantResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionParticipantResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionParticipantRepository mAuctionParticipantRepository;

    @Autowired
    private MAuctionParticipantMapper mAuctionParticipantMapper;

    @Autowired
    private MAuctionParticipantService mAuctionParticipantService;

    @Autowired
    private MAuctionParticipantQueryService mAuctionParticipantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionParticipantMockMvc;

    private MAuctionParticipant mAuctionParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionParticipant createEntity(EntityManager em) {
        MAuctionParticipant mAuctionParticipant = new MAuctionParticipant()
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
        mAuctionParticipant.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionParticipant.setAuction(mAuction);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuctionParticipant.setUser(adUser);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mAuctionParticipant.setVendor(cVendor);
        return mAuctionParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionParticipant createUpdatedEntity(EntityManager em) {
        MAuctionParticipant mAuctionParticipant = new MAuctionParticipant()
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
        mAuctionParticipant.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createUpdatedEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionParticipant.setAuction(mAuction);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuctionParticipant.setUser(adUser);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mAuctionParticipant.setVendor(cVendor);
        return mAuctionParticipant;
    }

    @BeforeEach
    public void initTest() {
        mAuctionParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionParticipant() throws Exception {
        int databaseSizeBeforeCreate = mAuctionParticipantRepository.findAll().size();

        // Create the MAuctionParticipant
        MAuctionParticipantDTO mAuctionParticipantDTO = mAuctionParticipantMapper.toDto(mAuctionParticipant);
        restMAuctionParticipantMockMvc.perform(post("/api/m-auction-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionParticipant in the database
        List<MAuctionParticipant> mAuctionParticipantList = mAuctionParticipantRepository.findAll();
        assertThat(mAuctionParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionParticipant testMAuctionParticipant = mAuctionParticipantList.get(mAuctionParticipantList.size() - 1);
        assertThat(testMAuctionParticipant.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionParticipant.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionParticipantRepository.findAll().size();

        // Create the MAuctionParticipant with an existing ID
        mAuctionParticipant.setId(1L);
        MAuctionParticipantDTO mAuctionParticipantDTO = mAuctionParticipantMapper.toDto(mAuctionParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionParticipantMockMvc.perform(post("/api/m-auction-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionParticipant in the database
        List<MAuctionParticipant> mAuctionParticipantList = mAuctionParticipantRepository.findAll();
        assertThat(mAuctionParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMAuctionParticipants() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionParticipant() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get the mAuctionParticipant
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants/{id}", mAuctionParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionParticipant.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        Long id = mAuctionParticipant.getId();

        defaultMAuctionParticipantShouldBeFound("id.equals=" + id);
        defaultMAuctionParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionParticipantsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where uid equals to DEFAULT_UID
        defaultMAuctionParticipantShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionParticipantList where uid equals to UPDATED_UID
        defaultMAuctionParticipantShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where uid not equals to DEFAULT_UID
        defaultMAuctionParticipantShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionParticipantList where uid not equals to UPDATED_UID
        defaultMAuctionParticipantShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionParticipantShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionParticipantList where uid equals to UPDATED_UID
        defaultMAuctionParticipantShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where uid is not null
        defaultMAuctionParticipantShouldBeFound("uid.specified=true");

        // Get all the mAuctionParticipantList where uid is null
        defaultMAuctionParticipantShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where active equals to DEFAULT_ACTIVE
        defaultMAuctionParticipantShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionParticipantList where active equals to UPDATED_ACTIVE
        defaultMAuctionParticipantShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionParticipantShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionParticipantList where active not equals to UPDATED_ACTIVE
        defaultMAuctionParticipantShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionParticipantShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionParticipantList where active equals to UPDATED_ACTIVE
        defaultMAuctionParticipantShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        // Get all the mAuctionParticipantList where active is not null
        defaultMAuctionParticipantShouldBeFound("active.specified=true");

        // Get all the mAuctionParticipantList where active is null
        defaultMAuctionParticipantShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionParticipantsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionParticipant.getAdOrganization();
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionParticipantList where adOrganization equals to adOrganizationId
        defaultMAuctionParticipantShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionParticipantList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionParticipantShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionParticipantsByAuctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuction auction = mAuctionParticipant.getAuction();
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);
        Long auctionId = auction.getId();

        // Get all the mAuctionParticipantList where auction equals to auctionId
        defaultMAuctionParticipantShouldBeFound("auctionId.equals=" + auctionId);

        // Get all the mAuctionParticipantList where auction equals to auctionId + 1
        defaultMAuctionParticipantShouldNotBeFound("auctionId.equals=" + (auctionId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionParticipantsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser user = mAuctionParticipant.getUser();
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);
        Long userId = user.getId();

        // Get all the mAuctionParticipantList where user equals to userId
        defaultMAuctionParticipantShouldBeFound("userId.equals=" + userId);

        // Get all the mAuctionParticipantList where user equals to userId + 1
        defaultMAuctionParticipantShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionParticipantsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mAuctionParticipant.getVendor();
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);
        Long vendorId = vendor.getId();

        // Get all the mAuctionParticipantList where vendor equals to vendorId
        defaultMAuctionParticipantShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mAuctionParticipantList where vendor equals to vendorId + 1
        defaultMAuctionParticipantShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionParticipantShouldBeFound(String filter) throws Exception {
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionParticipantShouldNotBeFound(String filter) throws Exception {
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionParticipant() throws Exception {
        // Get the mAuctionParticipant
        restMAuctionParticipantMockMvc.perform(get("/api/m-auction-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionParticipant() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        int databaseSizeBeforeUpdate = mAuctionParticipantRepository.findAll().size();

        // Update the mAuctionParticipant
        MAuctionParticipant updatedMAuctionParticipant = mAuctionParticipantRepository.findById(mAuctionParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionParticipant are not directly saved in db
        em.detach(updatedMAuctionParticipant);
        updatedMAuctionParticipant
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionParticipantDTO mAuctionParticipantDTO = mAuctionParticipantMapper.toDto(updatedMAuctionParticipant);

        restMAuctionParticipantMockMvc.perform(put("/api/m-auction-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionParticipant in the database
        List<MAuctionParticipant> mAuctionParticipantList = mAuctionParticipantRepository.findAll();
        assertThat(mAuctionParticipantList).hasSize(databaseSizeBeforeUpdate);
        MAuctionParticipant testMAuctionParticipant = mAuctionParticipantList.get(mAuctionParticipantList.size() - 1);
        assertThat(testMAuctionParticipant.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionParticipant.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionParticipant() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionParticipantRepository.findAll().size();

        // Create the MAuctionParticipant
        MAuctionParticipantDTO mAuctionParticipantDTO = mAuctionParticipantMapper.toDto(mAuctionParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionParticipantMockMvc.perform(put("/api/m-auction-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionParticipant in the database
        List<MAuctionParticipant> mAuctionParticipantList = mAuctionParticipantRepository.findAll();
        assertThat(mAuctionParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionParticipant() throws Exception {
        // Initialize the database
        mAuctionParticipantRepository.saveAndFlush(mAuctionParticipant);

        int databaseSizeBeforeDelete = mAuctionParticipantRepository.findAll().size();

        // Delete the mAuctionParticipant
        restMAuctionParticipantMockMvc.perform(delete("/api/m-auction-participants/{id}", mAuctionParticipant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionParticipant> mAuctionParticipantList = mAuctionParticipantRepository.findAll();
        assertThat(mAuctionParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
