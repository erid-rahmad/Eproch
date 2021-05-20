package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvaluation;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MBiddingEvaluationRepository;
import com.bhp.opusb.service.MBiddingEvaluationService;
import com.bhp.opusb.service.dto.MBiddingEvaluationDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationMapper;
import com.bhp.opusb.service.dto.MBiddingEvaluationCriteria;
import com.bhp.opusb.service.MBiddingEvaluationQueryService;

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
 * Integration tests for the {@link MBiddingEvaluationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvaluationResourceIT {

    private static final String DEFAULT_EVALUATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingEvaluationRepository mBiddingEvaluationRepository;

    @Autowired
    private MBiddingEvaluationMapper mBiddingEvaluationMapper;

    @Autowired
    private MBiddingEvaluationService mBiddingEvaluationService;

    @Autowired
    private MBiddingEvaluationQueryService mBiddingEvaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvaluationMockMvc;

    private MBiddingEvaluation mBiddingEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluation createEntity(EntityManager em) {
        MBiddingEvaluation mBiddingEvaluation = new MBiddingEvaluation()
            .evaluationStatus(DEFAULT_EVALUATION_STATUS)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingEvaluation.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingEvaluation.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingEvaluation.setAdOrganization(aDOrganization);
        return mBiddingEvaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluation createUpdatedEntity(EntityManager em) {
        MBiddingEvaluation mBiddingEvaluation = new MBiddingEvaluation()
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingEvaluation.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingEvaluation.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingEvaluation.setAdOrganization(aDOrganization);
        return mBiddingEvaluation;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluation() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationRepository.findAll().size();

        // Create the MBiddingEvaluation
        MBiddingEvaluationDTO mBiddingEvaluationDTO = mBiddingEvaluationMapper.toDto(mBiddingEvaluation);
        restMBiddingEvaluationMockMvc.perform(post("/api/m-bidding-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvaluation in the database
        List<MBiddingEvaluation> mBiddingEvaluationList = mBiddingEvaluationRepository.findAll();
        assertThat(mBiddingEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvaluation testMBiddingEvaluation = mBiddingEvaluationList.get(mBiddingEvaluationList.size() - 1);
        assertThat(testMBiddingEvaluation.getEvaluationStatus()).isEqualTo(DEFAULT_EVALUATION_STATUS);
        assertThat(testMBiddingEvaluation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvaluation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationRepository.findAll().size();

        // Create the MBiddingEvaluation with an existing ID
        mBiddingEvaluation.setId(1L);
        MBiddingEvaluationDTO mBiddingEvaluationDTO = mBiddingEvaluationMapper.toDto(mBiddingEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvaluationMockMvc.perform(post("/api/m-bidding-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluation in the database
        List<MBiddingEvaluation> mBiddingEvaluationList = mBiddingEvaluationRepository.findAll();
        assertThat(mBiddingEvaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluations() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvaluation() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get the mBiddingEvaluation
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations/{id}", mBiddingEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.evaluationStatus").value(DEFAULT_EVALUATION_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        Long id = mBiddingEvaluation.getId();

        defaultMBiddingEvaluationShouldBeFound("id.equals=" + id);
        defaultMBiddingEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.equals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.equals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus not equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.notEquals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationList where evaluationStatus not equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.notEquals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus in DEFAULT_EVALUATION_STATUS or UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.in=" + DEFAULT_EVALUATION_STATUS + "," + UPDATED_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.in=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus is not null
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.specified=true");

        // Get all the mBiddingEvaluationList where evaluationStatus is null
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus contains DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.contains=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationList where evaluationStatus contains UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.contains=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByEvaluationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where evaluationStatus does not contain DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldNotBeFound("evaluationStatus.doesNotContain=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationList where evaluationStatus does not contain UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationShouldBeFound("evaluationStatus.doesNotContain=" + UPDATED_EVALUATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where uid equals to DEFAULT_UID
        defaultMBiddingEvaluationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where uid not equals to DEFAULT_UID
        defaultMBiddingEvaluationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationList where uid not equals to UPDATED_UID
        defaultMBiddingEvaluationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvaluationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvaluationList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where uid is not null
        defaultMBiddingEvaluationShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvaluationList where uid is null
        defaultMBiddingEvaluationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvaluationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvaluationList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        // Get all the mBiddingEvaluationList where active is not null
        defaultMBiddingEvaluationShouldBeFound("active.specified=true");

        // Get all the mBiddingEvaluationList where active is null
        defaultMBiddingEvaluationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mBiddingEvaluation.getBiddingSubmission();
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mBiddingEvaluationList where biddingSubmission equals to biddingSubmissionId
        defaultMBiddingEvaluationShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mBiddingEvaluationList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMBiddingEvaluationShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mBiddingEvaluation.getBiddingSchedule();
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mBiddingEvaluationList where biddingSchedule equals to biddingScheduleId
        defaultMBiddingEvaluationShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mBiddingEvaluationList where biddingSchedule equals to biddingScheduleId + 1
        defaultMBiddingEvaluationShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvaluation.getAdOrganization();
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvaluationList where adOrganization equals to adOrganizationId
        defaultMBiddingEvaluationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvaluationList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvaluationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvaluationShouldBeFound(String filter) throws Exception {
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvaluationShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvaluation() throws Exception {
        // Get the mBiddingEvaluation
        restMBiddingEvaluationMockMvc.perform(get("/api/m-bidding-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvaluation() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        int databaseSizeBeforeUpdate = mBiddingEvaluationRepository.findAll().size();

        // Update the mBiddingEvaluation
        MBiddingEvaluation updatedMBiddingEvaluation = mBiddingEvaluationRepository.findById(mBiddingEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvaluation are not directly saved in db
        em.detach(updatedMBiddingEvaluation);
        updatedMBiddingEvaluation
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingEvaluationDTO mBiddingEvaluationDTO = mBiddingEvaluationMapper.toDto(updatedMBiddingEvaluation);

        restMBiddingEvaluationMockMvc.perform(put("/api/m-bidding-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvaluation in the database
        List<MBiddingEvaluation> mBiddingEvaluationList = mBiddingEvaluationRepository.findAll();
        assertThat(mBiddingEvaluationList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvaluation testMBiddingEvaluation = mBiddingEvaluationList.get(mBiddingEvaluationList.size() - 1);
        assertThat(testMBiddingEvaluation.getEvaluationStatus()).isEqualTo(UPDATED_EVALUATION_STATUS);
        assertThat(testMBiddingEvaluation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvaluation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvaluationRepository.findAll().size();

        // Create the MBiddingEvaluation
        MBiddingEvaluationDTO mBiddingEvaluationDTO = mBiddingEvaluationMapper.toDto(mBiddingEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvaluationMockMvc.perform(put("/api/m-bidding-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluation in the database
        List<MBiddingEvaluation> mBiddingEvaluationList = mBiddingEvaluationRepository.findAll();
        assertThat(mBiddingEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvaluation() throws Exception {
        // Initialize the database
        mBiddingEvaluationRepository.saveAndFlush(mBiddingEvaluation);

        int databaseSizeBeforeDelete = mBiddingEvaluationRepository.findAll().size();

        // Delete the mBiddingEvaluation
        restMBiddingEvaluationMockMvc.perform(delete("/api/m-bidding-evaluations/{id}", mBiddingEvaluation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvaluation> mBiddingEvaluationList = mBiddingEvaluationRepository.findAll();
        assertThat(mBiddingEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
