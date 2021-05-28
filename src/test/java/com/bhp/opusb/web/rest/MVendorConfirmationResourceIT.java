package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MVendorConfirmationRepository;
import com.bhp.opusb.service.MVendorConfirmationService;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationMapper;
import com.bhp.opusb.service.dto.MVendorConfirmationCriteria;
import com.bhp.opusb.service.MVendorConfirmationQueryService;

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
 * Integration tests for the {@link MVendorConfirmationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorConfirmationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorConfirmationRepository mVendorConfirmationRepository;

    @Autowired
    private MVendorConfirmationMapper mVendorConfirmationMapper;

    @Autowired
    private MVendorConfirmationService mVendorConfirmationService;

    @Autowired
    private MVendorConfirmationQueryService mVendorConfirmationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorConfirmationMockMvc;

    private MVendorConfirmation mVendorConfirmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmation createEntity(EntityManager em) {
        MVendorConfirmation mVendorConfirmation = new MVendorConfirmation()
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
        mVendorConfirmation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmation.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVendorConfirmation.setCostCenter(cCostCenter);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mVendorConfirmation.setPic(adUser);
        return mVendorConfirmation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmation createUpdatedEntity(EntityManager em) {
        MVendorConfirmation mVendorConfirmation = new MVendorConfirmation()
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
        mVendorConfirmation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmation.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVendorConfirmation.setCostCenter(cCostCenter);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mVendorConfirmation.setPic(adUser);
        return mVendorConfirmation;
    }

    @BeforeEach
    public void initTest() {
        mVendorConfirmation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorConfirmation() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationRepository.findAll().size();

        // Create the MVendorConfirmation
        MVendorConfirmationDTO mVendorConfirmationDTO = mVendorConfirmationMapper.toDto(mVendorConfirmation);
        restMVendorConfirmationMockMvc.perform(post("/api/m-vendor-confirmations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorConfirmation in the database
        List<MVendorConfirmation> mVendorConfirmationList = mVendorConfirmationRepository.findAll();
        assertThat(mVendorConfirmationList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorConfirmation testMVendorConfirmation = mVendorConfirmationList.get(mVendorConfirmationList.size() - 1);
        assertThat(testMVendorConfirmation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorConfirmation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationRepository.findAll().size();

        // Create the MVendorConfirmation with an existing ID
        mVendorConfirmation.setId(1L);
        MVendorConfirmationDTO mVendorConfirmationDTO = mVendorConfirmationMapper.toDto(mVendorConfirmation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorConfirmationMockMvc.perform(post("/api/m-vendor-confirmations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmation in the database
        List<MVendorConfirmation> mVendorConfirmationList = mVendorConfirmationRepository.findAll();
        assertThat(mVendorConfirmationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmations() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorConfirmation() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get the mVendorConfirmation
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations/{id}", mVendorConfirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorConfirmation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorConfirmationsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        Long id = mVendorConfirmation.getId();

        defaultMVendorConfirmationShouldBeFound("id.equals=" + id);
        defaultMVendorConfirmationShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorConfirmationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorConfirmationShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorConfirmationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorConfirmationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where uid equals to DEFAULT_UID
        defaultMVendorConfirmationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationList where uid equals to UPDATED_UID
        defaultMVendorConfirmationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where uid not equals to DEFAULT_UID
        defaultMVendorConfirmationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationList where uid not equals to UPDATED_UID
        defaultMVendorConfirmationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorConfirmationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorConfirmationList where uid equals to UPDATED_UID
        defaultMVendorConfirmationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where uid is not null
        defaultMVendorConfirmationShouldBeFound("uid.specified=true");

        // Get all the mVendorConfirmationList where uid is null
        defaultMVendorConfirmationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where active equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where active not equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationList where active not equals to UPDATED_ACTIVE
        defaultMVendorConfirmationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorConfirmationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorConfirmationList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        // Get all the mVendorConfirmationList where active is not null
        defaultMVendorConfirmationShouldBeFound("active.specified=true");

        // Get all the mVendorConfirmationList where active is null
        defaultMVendorConfirmationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mVendorConfirmation.getBidding();
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        Long biddingId = bidding.getId();

        // Get all the mVendorConfirmationList where bidding equals to biddingId
        defaultMVendorConfirmationShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mVendorConfirmationList where bidding equals to biddingId + 1
        defaultMVendorConfirmationShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorConfirmation.getAdOrganization();
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorConfirmationList where adOrganization equals to adOrganizationId
        defaultMVendorConfirmationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorConfirmationList where adOrganization equals to adOrganizationId + 1
        defaultMVendorConfirmationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        CCurrency currency = CCurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        mVendorConfirmation.setCurrency(currency);
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        Long currencyId = currency.getId();

        // Get all the mVendorConfirmationList where currency equals to currencyId
        defaultMVendorConfirmationShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mVendorConfirmationList where currency equals to currencyId + 1
        defaultMVendorConfirmationShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mVendorConfirmation.getCostCenter();
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        Long costCenterId = costCenter.getId();

        // Get all the mVendorConfirmationList where costCenter equals to costCenterId
        defaultMVendorConfirmationShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mVendorConfirmationList where costCenter equals to costCenterId + 1
        defaultMVendorConfirmationShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationsByPicIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser pic = mVendorConfirmation.getPic();
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);
        Long picId = pic.getId();

        // Get all the mVendorConfirmationList where pic equals to picId
        defaultMVendorConfirmationShouldBeFound("picId.equals=" + picId);

        // Get all the mVendorConfirmationList where pic equals to picId + 1
        defaultMVendorConfirmationShouldNotBeFound("picId.equals=" + (picId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorConfirmationShouldBeFound(String filter) throws Exception {
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorConfirmationShouldNotBeFound(String filter) throws Exception {
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorConfirmation() throws Exception {
        // Get the mVendorConfirmation
        restMVendorConfirmationMockMvc.perform(get("/api/m-vendor-confirmations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorConfirmation() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        int databaseSizeBeforeUpdate = mVendorConfirmationRepository.findAll().size();

        // Update the mVendorConfirmation
        MVendorConfirmation updatedMVendorConfirmation = mVendorConfirmationRepository.findById(mVendorConfirmation.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorConfirmation are not directly saved in db
        em.detach(updatedMVendorConfirmation);
        updatedMVendorConfirmation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorConfirmationDTO mVendorConfirmationDTO = mVendorConfirmationMapper.toDto(updatedMVendorConfirmation);

        restMVendorConfirmationMockMvc.perform(put("/api/m-vendor-confirmations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorConfirmation in the database
        List<MVendorConfirmation> mVendorConfirmationList = mVendorConfirmationRepository.findAll();
        assertThat(mVendorConfirmationList).hasSize(databaseSizeBeforeUpdate);
        MVendorConfirmation testMVendorConfirmation = mVendorConfirmationList.get(mVendorConfirmationList.size() - 1);
        assertThat(testMVendorConfirmation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorConfirmation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorConfirmation() throws Exception {
        int databaseSizeBeforeUpdate = mVendorConfirmationRepository.findAll().size();

        // Create the MVendorConfirmation
        MVendorConfirmationDTO mVendorConfirmationDTO = mVendorConfirmationMapper.toDto(mVendorConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorConfirmationMockMvc.perform(put("/api/m-vendor-confirmations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmation in the database
        List<MVendorConfirmation> mVendorConfirmationList = mVendorConfirmationRepository.findAll();
        assertThat(mVendorConfirmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorConfirmation() throws Exception {
        // Initialize the database
        mVendorConfirmationRepository.saveAndFlush(mVendorConfirmation);

        int databaseSizeBeforeDelete = mVendorConfirmationRepository.findAll().size();

        // Delete the mVendorConfirmation
        restMVendorConfirmationMockMvc.perform(delete("/api/m-vendor-confirmations/{id}", mVendorConfirmation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorConfirmation> mVendorConfirmationList = mVendorConfirmationRepository.findAll();
        assertThat(mVendorConfirmationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
