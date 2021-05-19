package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingResult;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAnnouncementResult;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.repository.MBiddingResultRepository;
import com.bhp.opusb.service.MBiddingResultService;
import com.bhp.opusb.service.dto.MBiddingResultDTO;
import com.bhp.opusb.service.mapper.MBiddingResultMapper;
import com.bhp.opusb.service.dto.MBiddingResultCriteria;
import com.bhp.opusb.service.MBiddingResultQueryService;

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
 * Integration tests for the {@link MBiddingResultResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingResultResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingResultRepository mBiddingResultRepository;

    @Autowired
    private MBiddingResultMapper mBiddingResultMapper;

    @Autowired
    private MBiddingResultService mBiddingResultService;

    @Autowired
    private MBiddingResultQueryService mBiddingResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingResultMockMvc;

    private MBiddingResult mBiddingResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingResult createEntity(EntityManager em) {
        MBiddingResult mBiddingResult = new MBiddingResult()
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
        mBiddingResult.setAdOrganization(aDOrganization);
        // Add required entity
        CAnnouncementResult cAnnouncementResult;
        if (TestUtil.findAll(em, CAnnouncementResult.class).isEmpty()) {
            cAnnouncementResult = CAnnouncementResultResourceIT.createEntity(em);
            em.persist(cAnnouncementResult);
            em.flush();
        } else {
            cAnnouncementResult = TestUtil.findAll(em, CAnnouncementResult.class).get(0);
        }
        mBiddingResult.setAnnouncementResult(cAnnouncementResult);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingResult.setBidding(mBidding);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingResult.setVendor(cVendor);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingResult.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingResult createUpdatedEntity(EntityManager em) {
        MBiddingResult mBiddingResult = new MBiddingResult()
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
        mBiddingResult.setAdOrganization(aDOrganization);
        // Add required entity
        CAnnouncementResult cAnnouncementResult;
        if (TestUtil.findAll(em, CAnnouncementResult.class).isEmpty()) {
            cAnnouncementResult = CAnnouncementResultResourceIT.createUpdatedEntity(em);
            em.persist(cAnnouncementResult);
            em.flush();
        } else {
            cAnnouncementResult = TestUtil.findAll(em, CAnnouncementResult.class).get(0);
        }
        mBiddingResult.setAnnouncementResult(cAnnouncementResult);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingResult.setBidding(mBidding);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingResult.setVendor(cVendor);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingResult.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingResult;
    }

    @BeforeEach
    public void initTest() {
        mBiddingResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingResult() throws Exception {
        int databaseSizeBeforeCreate = mBiddingResultRepository.findAll().size();

        // Create the MBiddingResult
        MBiddingResultDTO mBiddingResultDTO = mBiddingResultMapper.toDto(mBiddingResult);
        restMBiddingResultMockMvc.perform(post("/api/m-bidding-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingResultDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingResult in the database
        List<MBiddingResult> mBiddingResultList = mBiddingResultRepository.findAll();
        assertThat(mBiddingResultList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingResult testMBiddingResult = mBiddingResultList.get(mBiddingResultList.size() - 1);
        assertThat(testMBiddingResult.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingResult.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingResultRepository.findAll().size();

        // Create the MBiddingResult with an existing ID
        mBiddingResult.setId(1L);
        MBiddingResultDTO mBiddingResultDTO = mBiddingResultMapper.toDto(mBiddingResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingResultMockMvc.perform(post("/api/m-bidding-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingResult in the database
        List<MBiddingResult> mBiddingResultList = mBiddingResultRepository.findAll();
        assertThat(mBiddingResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingResults() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingResult() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get the mBiddingResult
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results/{id}", mBiddingResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingResult.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingResultsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        Long id = mBiddingResult.getId();

        defaultMBiddingResultShouldBeFound("id.equals=" + id);
        defaultMBiddingResultShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingResultShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingResultsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where uid equals to DEFAULT_UID
        defaultMBiddingResultShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingResultList where uid equals to UPDATED_UID
        defaultMBiddingResultShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where uid not equals to DEFAULT_UID
        defaultMBiddingResultShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingResultList where uid not equals to UPDATED_UID
        defaultMBiddingResultShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingResultShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingResultList where uid equals to UPDATED_UID
        defaultMBiddingResultShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where uid is not null
        defaultMBiddingResultShouldBeFound("uid.specified=true");

        // Get all the mBiddingResultList where uid is null
        defaultMBiddingResultShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where active equals to DEFAULT_ACTIVE
        defaultMBiddingResultShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingResultList where active equals to UPDATED_ACTIVE
        defaultMBiddingResultShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingResultShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingResultList where active not equals to UPDATED_ACTIVE
        defaultMBiddingResultShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingResultShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingResultList where active equals to UPDATED_ACTIVE
        defaultMBiddingResultShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        // Get all the mBiddingResultList where active is not null
        defaultMBiddingResultShouldBeFound("active.specified=true");

        // Get all the mBiddingResultList where active is null
        defaultMBiddingResultShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingResultsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingResult.getAdOrganization();
        mBiddingResultRepository.saveAndFlush(mBiddingResult);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingResultList where adOrganization equals to adOrganizationId
        defaultMBiddingResultShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingResultList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingResultShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingResultsByAnnouncementResultIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAnnouncementResult announcementResult = mBiddingResult.getAnnouncementResult();
        mBiddingResultRepository.saveAndFlush(mBiddingResult);
        Long announcementResultId = announcementResult.getId();

        // Get all the mBiddingResultList where announcementResult equals to announcementResultId
        defaultMBiddingResultShouldBeFound("announcementResultId.equals=" + announcementResultId);

        // Get all the mBiddingResultList where announcementResult equals to announcementResultId + 1
        defaultMBiddingResultShouldNotBeFound("announcementResultId.equals=" + (announcementResultId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingResultsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingResult.getBidding();
        mBiddingResultRepository.saveAndFlush(mBiddingResult);
        Long biddingId = bidding.getId();

        // Get all the mBiddingResultList where bidding equals to biddingId
        defaultMBiddingResultShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingResultList where bidding equals to biddingId + 1
        defaultMBiddingResultShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingResultsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mBiddingResult.getVendor();
        mBiddingResultRepository.saveAndFlush(mBiddingResult);
        Long vendorId = vendor.getId();

        // Get all the mBiddingResultList where vendor equals to vendorId
        defaultMBiddingResultShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBiddingResultList where vendor equals to vendorId + 1
        defaultMBiddingResultShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingResultsByBiddingEvalResultIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvalResult biddingEvalResult = mBiddingResult.getBiddingEvalResult();
        mBiddingResultRepository.saveAndFlush(mBiddingResult);
        Long biddingEvalResultId = biddingEvalResult.getId();

        // Get all the mBiddingResultList where biddingEvalResult equals to biddingEvalResultId
        defaultMBiddingResultShouldBeFound("biddingEvalResultId.equals=" + biddingEvalResultId);

        // Get all the mBiddingResultList where biddingEvalResult equals to biddingEvalResultId + 1
        defaultMBiddingResultShouldNotBeFound("biddingEvalResultId.equals=" + (biddingEvalResultId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingResultShouldBeFound(String filter) throws Exception {
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingResultShouldNotBeFound(String filter) throws Exception {
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingResult() throws Exception {
        // Get the mBiddingResult
        restMBiddingResultMockMvc.perform(get("/api/m-bidding-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingResult() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        int databaseSizeBeforeUpdate = mBiddingResultRepository.findAll().size();

        // Update the mBiddingResult
        MBiddingResult updatedMBiddingResult = mBiddingResultRepository.findById(mBiddingResult.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingResult are not directly saved in db
        em.detach(updatedMBiddingResult);
        updatedMBiddingResult
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingResultDTO mBiddingResultDTO = mBiddingResultMapper.toDto(updatedMBiddingResult);

        restMBiddingResultMockMvc.perform(put("/api/m-bidding-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingResultDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingResult in the database
        List<MBiddingResult> mBiddingResultList = mBiddingResultRepository.findAll();
        assertThat(mBiddingResultList).hasSize(databaseSizeBeforeUpdate);
        MBiddingResult testMBiddingResult = mBiddingResultList.get(mBiddingResultList.size() - 1);
        assertThat(testMBiddingResult.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingResult.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingResult() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingResultRepository.findAll().size();

        // Create the MBiddingResult
        MBiddingResultDTO mBiddingResultDTO = mBiddingResultMapper.toDto(mBiddingResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingResultMockMvc.perform(put("/api/m-bidding-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingResult in the database
        List<MBiddingResult> mBiddingResultList = mBiddingResultRepository.findAll();
        assertThat(mBiddingResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingResult() throws Exception {
        // Initialize the database
        mBiddingResultRepository.saveAndFlush(mBiddingResult);

        int databaseSizeBeforeDelete = mBiddingResultRepository.findAll().size();

        // Delete the mBiddingResult
        restMBiddingResultMockMvc.perform(delete("/api/m-bidding-results/{id}", mBiddingResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingResult> mBiddingResultList = mBiddingResultRepository.findAll();
        assertThat(mBiddingResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
