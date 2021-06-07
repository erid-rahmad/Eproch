package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.repository.MBiddingEvalResultRepository;
import com.bhp.opusb.service.MBiddingEvalResultService;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultMapper;
import com.bhp.opusb.service.dto.MBiddingEvalResultCriteria;
import com.bhp.opusb.service.MBiddingEvalResultQueryService;

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
 * Integration tests for the {@link MBiddingEvalResultResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvalResultResourceIT {

    private static final String DEFAULT_EVALUATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;
    private static final Integer SMALLER_RANK = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingEvalResultRepository mBiddingEvalResultRepository;

    @Autowired
    private MBiddingEvalResultMapper mBiddingEvalResultMapper;

    @Autowired
    private MBiddingEvalResultService mBiddingEvalResultService;

    @Autowired
    private MBiddingEvalResultQueryService mBiddingEvalResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvalResultMockMvc;

    private MBiddingEvalResult mBiddingEvalResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalResult createEntity(EntityManager em) {
        MBiddingEvalResult mBiddingEvalResult = new MBiddingEvalResult()
            .evaluationStatus(DEFAULT_EVALUATION_STATUS)
            .status(DEFAULT_STATUS)
            .score(DEFAULT_SCORE)
            .rank(DEFAULT_RANK)
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
        mBiddingEvalResult.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingEvalResult.setBiddingSubmission(mBiddingSubmission);
        return mBiddingEvalResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalResult createUpdatedEntity(EntityManager em) {
        MBiddingEvalResult mBiddingEvalResult = new MBiddingEvalResult()
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .rank(UPDATED_RANK)
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
        mBiddingEvalResult.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingEvalResult.setBiddingSubmission(mBiddingSubmission);
        return mBiddingEvalResult;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvalResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvalResult() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalResultRepository.findAll().size();

        // Create the MBiddingEvalResult
        MBiddingEvalResultDTO mBiddingEvalResultDTO = mBiddingEvalResultMapper.toDto(mBiddingEvalResult);
        restMBiddingEvalResultMockMvc.perform(post("/api/m-bidding-eval-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvalResult in the database
        List<MBiddingEvalResult> mBiddingEvalResultList = mBiddingEvalResultRepository.findAll();
        assertThat(mBiddingEvalResultList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvalResult testMBiddingEvalResult = mBiddingEvalResultList.get(mBiddingEvalResultList.size() - 1);
        assertThat(testMBiddingEvalResult.getEvaluationStatus()).isEqualTo(DEFAULT_EVALUATION_STATUS);
        assertThat(testMBiddingEvalResult.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMBiddingEvalResult.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMBiddingEvalResult.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testMBiddingEvalResult.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvalResult.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingEvalResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalResultRepository.findAll().size();

        // Create the MBiddingEvalResult with an existing ID
        mBiddingEvalResult.setId(1L);
        MBiddingEvalResultDTO mBiddingEvalResultDTO = mBiddingEvalResultMapper.toDto(mBiddingEvalResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvalResultMockMvc.perform(post("/api/m-bidding-eval-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalResult in the database
        List<MBiddingEvalResult> mBiddingEvalResultList = mBiddingEvalResultRepository.findAll();
        assertThat(mBiddingEvalResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResults() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvalResult() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get the mBiddingEvalResult
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results/{id}", mBiddingEvalResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvalResult.getId().intValue()))
            .andExpect(jsonPath("$.evaluationStatus").value(DEFAULT_EVALUATION_STATUS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingEvalResultsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        Long id = mBiddingEvalResult.getId();

        defaultMBiddingEvalResultShouldBeFound("id.equals=" + id);
        defaultMBiddingEvalResultShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvalResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvalResultShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvalResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvalResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.equals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvalResultList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.equals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus not equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.notEquals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvalResultList where evaluationStatus not equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.notEquals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus in DEFAULT_EVALUATION_STATUS or UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.in=" + DEFAULT_EVALUATION_STATUS + "," + UPDATED_EVALUATION_STATUS);

        // Get all the mBiddingEvalResultList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.in=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus is not null
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.specified=true");

        // Get all the mBiddingEvalResultList where evaluationStatus is null
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus contains DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.contains=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvalResultList where evaluationStatus contains UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.contains=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByEvaluationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where evaluationStatus does not contain DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("evaluationStatus.doesNotContain=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvalResultList where evaluationStatus does not contain UPDATED_EVALUATION_STATUS
        defaultMBiddingEvalResultShouldBeFound("evaluationStatus.doesNotContain=" + UPDATED_EVALUATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status equals to DEFAULT_STATUS
        defaultMBiddingEvalResultShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultList where status equals to UPDATED_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status not equals to DEFAULT_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultList where status not equals to UPDATED_STATUS
        defaultMBiddingEvalResultShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMBiddingEvalResultShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mBiddingEvalResultList where status equals to UPDATED_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status is not null
        defaultMBiddingEvalResultShouldBeFound("status.specified=true");

        // Get all the mBiddingEvalResultList where status is null
        defaultMBiddingEvalResultShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status contains DEFAULT_STATUS
        defaultMBiddingEvalResultShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultList where status contains UPDATED_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where status does not contain DEFAULT_STATUS
        defaultMBiddingEvalResultShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultList where status does not contain UPDATED_STATUS
        defaultMBiddingEvalResultShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score equals to DEFAULT_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score equals to UPDATED_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score not equals to DEFAULT_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score not equals to UPDATED_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the mBiddingEvalResultList where score equals to UPDATED_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score is not null
        defaultMBiddingEvalResultShouldBeFound("score.specified=true");

        // Get all the mBiddingEvalResultList where score is null
        defaultMBiddingEvalResultShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score is greater than or equal to DEFAULT_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score is greater than or equal to UPDATED_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score is less than or equal to DEFAULT_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score is less than or equal to SMALLER_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score is less than DEFAULT_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score is less than UPDATED_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where score is greater than DEFAULT_SCORE
        defaultMBiddingEvalResultShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultList where score is greater than SMALLER_SCORE
        defaultMBiddingEvalResultShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank equals to DEFAULT_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.equals=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank equals to UPDATED_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.equals=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank not equals to DEFAULT_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.notEquals=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank not equals to UPDATED_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.notEquals=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank in DEFAULT_RANK or UPDATED_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.in=" + DEFAULT_RANK + "," + UPDATED_RANK);

        // Get all the mBiddingEvalResultList where rank equals to UPDATED_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.in=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank is not null
        defaultMBiddingEvalResultShouldBeFound("rank.specified=true");

        // Get all the mBiddingEvalResultList where rank is null
        defaultMBiddingEvalResultShouldNotBeFound("rank.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank is greater than or equal to DEFAULT_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.greaterThanOrEqual=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank is greater than or equal to UPDATED_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.greaterThanOrEqual=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank is less than or equal to DEFAULT_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.lessThanOrEqual=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank is less than or equal to SMALLER_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.lessThanOrEqual=" + SMALLER_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank is less than DEFAULT_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.lessThan=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank is less than UPDATED_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.lessThan=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByRankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where rank is greater than DEFAULT_RANK
        defaultMBiddingEvalResultShouldNotBeFound("rank.greaterThan=" + DEFAULT_RANK);

        // Get all the mBiddingEvalResultList where rank is greater than SMALLER_RANK
        defaultMBiddingEvalResultShouldBeFound("rank.greaterThan=" + SMALLER_RANK);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where uid equals to DEFAULT_UID
        defaultMBiddingEvalResultShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvalResultList where uid equals to UPDATED_UID
        defaultMBiddingEvalResultShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where uid not equals to DEFAULT_UID
        defaultMBiddingEvalResultShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvalResultList where uid not equals to UPDATED_UID
        defaultMBiddingEvalResultShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvalResultShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvalResultList where uid equals to UPDATED_UID
        defaultMBiddingEvalResultShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where uid is not null
        defaultMBiddingEvalResultShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvalResultList where uid is null
        defaultMBiddingEvalResultShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvalResultShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalResultList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvalResultShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalResultList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvalResultShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvalResultList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        // Get all the mBiddingEvalResultList where active is not null
        defaultMBiddingEvalResultShouldBeFound("active.specified=true");

        // Get all the mBiddingEvalResultList where active is null
        defaultMBiddingEvalResultShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvalResult.getAdOrganization();
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvalResultList where adOrganization equals to adOrganizationId
        defaultMBiddingEvalResultShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvalResultList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvalResultShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultsByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mBiddingEvalResult.getBiddingSubmission();
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mBiddingEvalResultList where biddingSubmission equals to biddingSubmissionId
        defaultMBiddingEvalResultShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mBiddingEvalResultList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMBiddingEvalResultShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvalResultShouldBeFound(String filter) throws Exception {
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvalResultShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvalResult() throws Exception {
        // Get the mBiddingEvalResult
        restMBiddingEvalResultMockMvc.perform(get("/api/m-bidding-eval-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvalResult() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        int databaseSizeBeforeUpdate = mBiddingEvalResultRepository.findAll().size();

        // Update the mBiddingEvalResult
        MBiddingEvalResult updatedMBiddingEvalResult = mBiddingEvalResultRepository.findById(mBiddingEvalResult.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvalResult are not directly saved in db
        em.detach(updatedMBiddingEvalResult);
        updatedMBiddingEvalResult
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .rank(UPDATED_RANK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingEvalResultDTO mBiddingEvalResultDTO = mBiddingEvalResultMapper.toDto(updatedMBiddingEvalResult);

        restMBiddingEvalResultMockMvc.perform(put("/api/m-bidding-eval-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvalResult in the database
        List<MBiddingEvalResult> mBiddingEvalResultList = mBiddingEvalResultRepository.findAll();
        assertThat(mBiddingEvalResultList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvalResult testMBiddingEvalResult = mBiddingEvalResultList.get(mBiddingEvalResultList.size() - 1);
        assertThat(testMBiddingEvalResult.getEvaluationStatus()).isEqualTo(UPDATED_EVALUATION_STATUS);
        assertThat(testMBiddingEvalResult.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMBiddingEvalResult.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMBiddingEvalResult.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testMBiddingEvalResult.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvalResult.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvalResult() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvalResultRepository.findAll().size();

        // Create the MBiddingEvalResult
        MBiddingEvalResultDTO mBiddingEvalResultDTO = mBiddingEvalResultMapper.toDto(mBiddingEvalResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvalResultMockMvc.perform(put("/api/m-bidding-eval-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalResult in the database
        List<MBiddingEvalResult> mBiddingEvalResultList = mBiddingEvalResultRepository.findAll();
        assertThat(mBiddingEvalResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvalResult() throws Exception {
        // Initialize the database
        mBiddingEvalResultRepository.saveAndFlush(mBiddingEvalResult);

        int databaseSizeBeforeDelete = mBiddingEvalResultRepository.findAll().size();

        // Delete the mBiddingEvalResult
        restMBiddingEvalResultMockMvc.perform(delete("/api/m-bidding-eval-results/{id}", mBiddingEvalResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvalResult> mBiddingEvalResultList = mBiddingEvalResultRepository.findAll();
        assertThat(mBiddingEvalResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
