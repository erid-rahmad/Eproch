package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationResult;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalAnnouncementResult;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.repository.MPrequalificationResultRepository;
import com.bhp.opusb.service.MPrequalificationResultService;
import com.bhp.opusb.service.dto.MPrequalificationResultDTO;
import com.bhp.opusb.service.mapper.MPrequalificationResultMapper;
import com.bhp.opusb.service.dto.MPrequalificationResultCriteria;
import com.bhp.opusb.service.MPrequalificationResultQueryService;

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
 * Integration tests for the {@link MPrequalificationResultResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationResultResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationResultRepository mPrequalificationResultRepository;

    @Autowired
    private MPrequalificationResultMapper mPrequalificationResultMapper;

    @Autowired
    private MPrequalificationResultService mPrequalificationResultService;

    @Autowired
    private MPrequalificationResultQueryService mPrequalificationResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationResultMockMvc;

    private MPrequalificationResult mPrequalificationResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationResult createEntity(EntityManager em) {
        MPrequalificationResult mPrequalificationResult = new MPrequalificationResult()
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
        mPrequalificationResult.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalAnnouncementResult mPrequalAnnouncementResult;
        if (TestUtil.findAll(em, MPrequalAnnouncementResult.class).isEmpty()) {
            mPrequalAnnouncementResult = MPrequalAnnouncementResultResourceIT.createEntity(em);
            em.persist(mPrequalAnnouncementResult);
            em.flush();
        } else {
            mPrequalAnnouncementResult = TestUtil.findAll(em, MPrequalAnnouncementResult.class).get(0);
        }
        mPrequalificationResult.setAnnouncementResult(mPrequalAnnouncementResult);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationResult.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalificationResult.setVendor(cVendor);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationResult.setSubmission(mPrequalificationSubmission);
        return mPrequalificationResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationResult createUpdatedEntity(EntityManager em) {
        MPrequalificationResult mPrequalificationResult = new MPrequalificationResult()
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
        mPrequalificationResult.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalAnnouncementResult mPrequalAnnouncementResult;
        if (TestUtil.findAll(em, MPrequalAnnouncementResult.class).isEmpty()) {
            mPrequalAnnouncementResult = MPrequalAnnouncementResultResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalAnnouncementResult);
            em.flush();
        } else {
            mPrequalAnnouncementResult = TestUtil.findAll(em, MPrequalAnnouncementResult.class).get(0);
        }
        mPrequalificationResult.setAnnouncementResult(mPrequalAnnouncementResult);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationResult.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalificationResult.setVendor(cVendor);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationResult.setSubmission(mPrequalificationSubmission);
        return mPrequalificationResult;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationResult() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationResultRepository.findAll().size();

        // Create the MPrequalificationResult
        MPrequalificationResultDTO mPrequalificationResultDTO = mPrequalificationResultMapper.toDto(mPrequalificationResult);
        restMPrequalificationResultMockMvc.perform(post("/api/m-prequalification-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationResultDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationResult in the database
        List<MPrequalificationResult> mPrequalificationResultList = mPrequalificationResultRepository.findAll();
        assertThat(mPrequalificationResultList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationResult testMPrequalificationResult = mPrequalificationResultList.get(mPrequalificationResultList.size() - 1);
        assertThat(testMPrequalificationResult.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationResult.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationResultRepository.findAll().size();

        // Create the MPrequalificationResult with an existing ID
        mPrequalificationResult.setId(1L);
        MPrequalificationResultDTO mPrequalificationResultDTO = mPrequalificationResultMapper.toDto(mPrequalificationResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationResultMockMvc.perform(post("/api/m-prequalification-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationResult in the database
        List<MPrequalificationResult> mPrequalificationResultList = mPrequalificationResultRepository.findAll();
        assertThat(mPrequalificationResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResults() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationResult() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get the mPrequalificationResult
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results/{id}", mPrequalificationResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationResult.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationResultsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        Long id = mPrequalificationResult.getId();

        defaultMPrequalificationResultShouldBeFound("id.equals=" + id);
        defaultMPrequalificationResultShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationResultShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResultsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where uid equals to DEFAULT_UID
        defaultMPrequalificationResultShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationResultList where uid equals to UPDATED_UID
        defaultMPrequalificationResultShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where uid not equals to DEFAULT_UID
        defaultMPrequalificationResultShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationResultList where uid not equals to UPDATED_UID
        defaultMPrequalificationResultShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationResultShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationResultList where uid equals to UPDATED_UID
        defaultMPrequalificationResultShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where uid is not null
        defaultMPrequalificationResultShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationResultList where uid is null
        defaultMPrequalificationResultShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationResultShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationResultList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationResultShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationResultShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationResultList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationResultShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationResultShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationResultList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationResultShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        // Get all the mPrequalificationResultList where active is not null
        defaultMPrequalificationResultShouldBeFound("active.specified=true");

        // Get all the mPrequalificationResultList where active is null
        defaultMPrequalificationResultShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationResultsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationResult.getAdOrganization();
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationResultList where adOrganization equals to adOrganizationId
        defaultMPrequalificationResultShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationResultList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationResultShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResultsByAnnouncementResultIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalAnnouncementResult announcementResult = mPrequalificationResult.getAnnouncementResult();
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);
        Long announcementResultId = announcementResult.getId();

        // Get all the mPrequalificationResultList where announcementResult equals to announcementResultId
        defaultMPrequalificationResultShouldBeFound("announcementResultId.equals=" + announcementResultId);

        // Get all the mPrequalificationResultList where announcementResult equals to announcementResultId + 1
        defaultMPrequalificationResultShouldNotBeFound("announcementResultId.equals=" + (announcementResultId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResultsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationResult.getPrequalification();
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationResultList where prequalification equals to prequalificationId
        defaultMPrequalificationResultShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationResultList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationResultShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResultsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPrequalificationResult.getVendor();
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);
        Long vendorId = vendor.getId();

        // Get all the mPrequalificationResultList where vendor equals to vendorId
        defaultMPrequalificationResultShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPrequalificationResultList where vendor equals to vendorId + 1
        defaultMPrequalificationResultShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationResultsBySubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationSubmission submission = mPrequalificationResult.getSubmission();
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);
        Long submissionId = submission.getId();

        // Get all the mPrequalificationResultList where submission equals to submissionId
        defaultMPrequalificationResultShouldBeFound("submissionId.equals=" + submissionId);

        // Get all the mPrequalificationResultList where submission equals to submissionId + 1
        defaultMPrequalificationResultShouldNotBeFound("submissionId.equals=" + (submissionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationResultShouldBeFound(String filter) throws Exception {
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationResultShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationResult() throws Exception {
        // Get the mPrequalificationResult
        restMPrequalificationResultMockMvc.perform(get("/api/m-prequalification-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationResult() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        int databaseSizeBeforeUpdate = mPrequalificationResultRepository.findAll().size();

        // Update the mPrequalificationResult
        MPrequalificationResult updatedMPrequalificationResult = mPrequalificationResultRepository.findById(mPrequalificationResult.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationResult are not directly saved in db
        em.detach(updatedMPrequalificationResult);
        updatedMPrequalificationResult
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationResultDTO mPrequalificationResultDTO = mPrequalificationResultMapper.toDto(updatedMPrequalificationResult);

        restMPrequalificationResultMockMvc.perform(put("/api/m-prequalification-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationResultDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationResult in the database
        List<MPrequalificationResult> mPrequalificationResultList = mPrequalificationResultRepository.findAll();
        assertThat(mPrequalificationResultList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationResult testMPrequalificationResult = mPrequalificationResultList.get(mPrequalificationResultList.size() - 1);
        assertThat(testMPrequalificationResult.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationResult.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationResult() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationResultRepository.findAll().size();

        // Create the MPrequalificationResult
        MPrequalificationResultDTO mPrequalificationResultDTO = mPrequalificationResultMapper.toDto(mPrequalificationResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationResultMockMvc.perform(put("/api/m-prequalification-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationResult in the database
        List<MPrequalificationResult> mPrequalificationResultList = mPrequalificationResultRepository.findAll();
        assertThat(mPrequalificationResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationResult() throws Exception {
        // Initialize the database
        mPrequalificationResultRepository.saveAndFlush(mPrequalificationResult);

        int databaseSizeBeforeDelete = mPrequalificationResultRepository.findAll().size();

        // Delete the mPrequalificationResult
        restMPrequalificationResultMockMvc.perform(delete("/api/m-prequalification-results/{id}", mPrequalificationResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationResult> mPrequalificationResultList = mPrequalificationResultRepository.findAll();
        assertThat(mPrequalificationResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
