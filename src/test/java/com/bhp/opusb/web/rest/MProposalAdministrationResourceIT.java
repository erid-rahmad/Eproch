package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalAdministration;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.MProposalAdministrationRepository;
import com.bhp.opusb.service.MProposalAdministrationService;
import com.bhp.opusb.service.dto.MProposalAdministrationDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationMapper;
import com.bhp.opusb.service.dto.MProposalAdministrationCriteria;
import com.bhp.opusb.service.MProposalAdministrationQueryService;

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
 * Integration tests for the {@link MProposalAdministrationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalAdministrationResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DOCUMENT_EVALUATION = false;
    private static final Boolean UPDATED_DOCUMENT_EVALUATION = true;

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVERAGE_SCORE = 1;
    private static final Integer UPDATED_AVERAGE_SCORE = 2;
    private static final Integer SMALLER_AVERAGE_SCORE = 1 - 1;

    private static final String DEFAULT_PASS_FAIL = "AAAAAAAAAA";
    private static final String UPDATED_PASS_FAIL = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProposalAdministrationRepository mProposalAdministrationRepository;

    @Autowired
    private MProposalAdministrationMapper mProposalAdministrationMapper;

    @Autowired
    private MProposalAdministrationService mProposalAdministrationService;

    @Autowired
    private MProposalAdministrationQueryService mProposalAdministrationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalAdministrationMockMvc;

    private MProposalAdministration mProposalAdministration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalAdministration createEntity(EntityManager em) {
        MProposalAdministration mProposalAdministration = new MProposalAdministration()
            .answer(DEFAULT_ANSWER)
            .documentEvaluation(DEFAULT_DOCUMENT_EVALUATION)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .notes(DEFAULT_NOTES)
            .evaluation(DEFAULT_EVALUATION)
            .averageScore(DEFAULT_AVERAGE_SCORE)
            .passFail(DEFAULT_PASS_FAIL)
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
        mProposalAdministration.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalAdministration.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mProposalAdministration.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mProposalAdministration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalAdministration createUpdatedEntity(EntityManager em) {
        MProposalAdministration mProposalAdministration = new MProposalAdministration()
            .answer(UPDATED_ANSWER)
            .documentEvaluation(UPDATED_DOCUMENT_EVALUATION)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
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
        mProposalAdministration.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalAdministration.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mProposalAdministration.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mProposalAdministration;
    }

    @BeforeEach
    public void initTest() {
        mProposalAdministration = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalAdministration() throws Exception {
        int databaseSizeBeforeCreate = mProposalAdministrationRepository.findAll().size();

        // Create the MProposalAdministration
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(mProposalAdministration);
        restMProposalAdministrationMockMvc.perform(post("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalAdministration in the database
        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalAdministration testMProposalAdministration = mProposalAdministrationList.get(mProposalAdministrationList.size() - 1);
        assertThat(testMProposalAdministration.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testMProposalAdministration.isDocumentEvaluation()).isEqualTo(DEFAULT_DOCUMENT_EVALUATION);
        assertThat(testMProposalAdministration.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMProposalAdministration.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMProposalAdministration.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMProposalAdministration.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testMProposalAdministration.getAverageScore()).isEqualTo(DEFAULT_AVERAGE_SCORE);
        assertThat(testMProposalAdministration.getPassFail()).isEqualTo(DEFAULT_PASS_FAIL);
        assertThat(testMProposalAdministration.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalAdministration.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalAdministrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalAdministrationRepository.findAll().size();

        // Create the MProposalAdministration with an existing ID
        mProposalAdministration.setId(1L);
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(mProposalAdministration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalAdministrationMockMvc.perform(post("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalAdministration in the database
        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalAdministrationRepository.findAll().size();
        // set the field null
        mProposalAdministration.setAnswer(null);

        // Create the MProposalAdministration, which fails.
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(mProposalAdministration);

        restMProposalAdministrationMockMvc.perform(post("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalAdministrationRepository.findAll().size();
        // set the field null
        mProposalAdministration.setEvaluation(null);

        // Create the MProposalAdministration, which fails.
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(mProposalAdministration);

        restMProposalAdministrationMockMvc.perform(post("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrations() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalAdministration.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].documentEvaluation").value(hasItem(DEFAULT_DOCUMENT_EVALUATION.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getMProposalAdministration() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get the mProposalAdministration
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations/{id}", mProposalAdministration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalAdministration.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.documentEvaluation").value(DEFAULT_DOCUMENT_EVALUATION.booleanValue()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.averageScore").value(DEFAULT_AVERAGE_SCORE))
            .andExpect(jsonPath("$.passFail").value(DEFAULT_PASS_FAIL))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalAdministrationsByIdFiltering() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        Long id = mProposalAdministration.getId();

        defaultMProposalAdministrationShouldBeFound("id.equals=" + id);
        defaultMProposalAdministrationShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalAdministrationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalAdministrationShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalAdministrationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalAdministrationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer equals to DEFAULT_ANSWER
        defaultMProposalAdministrationShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the mProposalAdministrationList where answer equals to UPDATED_ANSWER
        defaultMProposalAdministrationShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer not equals to DEFAULT_ANSWER
        defaultMProposalAdministrationShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the mProposalAdministrationList where answer not equals to UPDATED_ANSWER
        defaultMProposalAdministrationShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultMProposalAdministrationShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the mProposalAdministrationList where answer equals to UPDATED_ANSWER
        defaultMProposalAdministrationShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer is not null
        defaultMProposalAdministrationShouldBeFound("answer.specified=true");

        // Get all the mProposalAdministrationList where answer is null
        defaultMProposalAdministrationShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer contains DEFAULT_ANSWER
        defaultMProposalAdministrationShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the mProposalAdministrationList where answer contains UPDATED_ANSWER
        defaultMProposalAdministrationShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where answer does not contain DEFAULT_ANSWER
        defaultMProposalAdministrationShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the mProposalAdministrationList where answer does not contain UPDATED_ANSWER
        defaultMProposalAdministrationShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentEvaluation equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldBeFound("documentEvaluation.equals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mProposalAdministrationList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("documentEvaluation.equals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentEvaluation not equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("documentEvaluation.notEquals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mProposalAdministrationList where documentEvaluation not equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldBeFound("documentEvaluation.notEquals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentEvaluation in DEFAULT_DOCUMENT_EVALUATION or UPDATED_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldBeFound("documentEvaluation.in=" + DEFAULT_DOCUMENT_EVALUATION + "," + UPDATED_DOCUMENT_EVALUATION);

        // Get all the mProposalAdministrationList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("documentEvaluation.in=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentEvaluation is not null
        defaultMProposalAdministrationShouldBeFound("documentEvaluation.specified=true");

        // Get all the mProposalAdministrationList where documentEvaluation is null
        defaultMProposalAdministrationShouldNotBeFound("documentEvaluation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalAdministrationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalAdministrationList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mProposalAdministrationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction is not null
        defaultMProposalAdministrationShouldBeFound("documentAction.specified=true");

        // Get all the mProposalAdministrationList where documentAction is null
        defaultMProposalAdministrationShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalAdministrationList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalAdministrationList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMProposalAdministrationShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalAdministrationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalAdministrationList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mProposalAdministrationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus is not null
        defaultMProposalAdministrationShouldBeFound("documentStatus.specified=true");

        // Get all the mProposalAdministrationList where documentStatus is null
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalAdministrationList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalAdministrationList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMProposalAdministrationShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes equals to DEFAULT_NOTES
        defaultMProposalAdministrationShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the mProposalAdministrationList where notes equals to UPDATED_NOTES
        defaultMProposalAdministrationShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes not equals to DEFAULT_NOTES
        defaultMProposalAdministrationShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the mProposalAdministrationList where notes not equals to UPDATED_NOTES
        defaultMProposalAdministrationShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultMProposalAdministrationShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the mProposalAdministrationList where notes equals to UPDATED_NOTES
        defaultMProposalAdministrationShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes is not null
        defaultMProposalAdministrationShouldBeFound("notes.specified=true");

        // Get all the mProposalAdministrationList where notes is null
        defaultMProposalAdministrationShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes contains DEFAULT_NOTES
        defaultMProposalAdministrationShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the mProposalAdministrationList where notes contains UPDATED_NOTES
        defaultMProposalAdministrationShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where notes does not contain DEFAULT_NOTES
        defaultMProposalAdministrationShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the mProposalAdministrationList where notes does not contain UPDATED_NOTES
        defaultMProposalAdministrationShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation equals to DEFAULT_EVALUATION
        defaultMProposalAdministrationShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mProposalAdministrationList where evaluation equals to UPDATED_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation not equals to DEFAULT_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mProposalAdministrationList where evaluation not equals to UPDATED_EVALUATION
        defaultMProposalAdministrationShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMProposalAdministrationShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mProposalAdministrationList where evaluation equals to UPDATED_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation is not null
        defaultMProposalAdministrationShouldBeFound("evaluation.specified=true");

        // Get all the mProposalAdministrationList where evaluation is null
        defaultMProposalAdministrationShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation contains DEFAULT_EVALUATION
        defaultMProposalAdministrationShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mProposalAdministrationList where evaluation contains UPDATED_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where evaluation does not contain DEFAULT_EVALUATION
        defaultMProposalAdministrationShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mProposalAdministrationList where evaluation does not contain UPDATED_EVALUATION
        defaultMProposalAdministrationShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore equals to DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.equals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.equals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore not equals to DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.notEquals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore not equals to UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.notEquals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore in DEFAULT_AVERAGE_SCORE or UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.in=" + DEFAULT_AVERAGE_SCORE + "," + UPDATED_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.in=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore is not null
        defaultMProposalAdministrationShouldBeFound("averageScore.specified=true");

        // Get all the mProposalAdministrationList where averageScore is null
        defaultMProposalAdministrationShouldNotBeFound("averageScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore is greater than or equal to DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.greaterThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore is greater than or equal to UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.greaterThanOrEqual=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore is less than or equal to DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.lessThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore is less than or equal to SMALLER_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.lessThanOrEqual=" + SMALLER_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore is less than DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.lessThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore is less than UPDATED_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.lessThan=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAverageScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where averageScore is greater than DEFAULT_AVERAGE_SCORE
        defaultMProposalAdministrationShouldNotBeFound("averageScore.greaterThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalAdministrationList where averageScore is greater than SMALLER_AVERAGE_SCORE
        defaultMProposalAdministrationShouldBeFound("averageScore.greaterThan=" + SMALLER_AVERAGE_SCORE);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail equals to DEFAULT_PASS_FAIL
        defaultMProposalAdministrationShouldBeFound("passFail.equals=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalAdministrationList where passFail equals to UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldNotBeFound("passFail.equals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail not equals to DEFAULT_PASS_FAIL
        defaultMProposalAdministrationShouldNotBeFound("passFail.notEquals=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalAdministrationList where passFail not equals to UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldBeFound("passFail.notEquals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail in DEFAULT_PASS_FAIL or UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldBeFound("passFail.in=" + DEFAULT_PASS_FAIL + "," + UPDATED_PASS_FAIL);

        // Get all the mProposalAdministrationList where passFail equals to UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldNotBeFound("passFail.in=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail is not null
        defaultMProposalAdministrationShouldBeFound("passFail.specified=true");

        // Get all the mProposalAdministrationList where passFail is null
        defaultMProposalAdministrationShouldNotBeFound("passFail.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail contains DEFAULT_PASS_FAIL
        defaultMProposalAdministrationShouldBeFound("passFail.contains=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalAdministrationList where passFail contains UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldNotBeFound("passFail.contains=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByPassFailNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where passFail does not contain DEFAULT_PASS_FAIL
        defaultMProposalAdministrationShouldNotBeFound("passFail.doesNotContain=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalAdministrationList where passFail does not contain UPDATED_PASS_FAIL
        defaultMProposalAdministrationShouldBeFound("passFail.doesNotContain=" + UPDATED_PASS_FAIL);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where uid equals to DEFAULT_UID
        defaultMProposalAdministrationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalAdministrationList where uid equals to UPDATED_UID
        defaultMProposalAdministrationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where uid not equals to DEFAULT_UID
        defaultMProposalAdministrationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalAdministrationList where uid not equals to UPDATED_UID
        defaultMProposalAdministrationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalAdministrationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalAdministrationList where uid equals to UPDATED_UID
        defaultMProposalAdministrationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where uid is not null
        defaultMProposalAdministrationShouldBeFound("uid.specified=true");

        // Get all the mProposalAdministrationList where uid is null
        defaultMProposalAdministrationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where active equals to DEFAULT_ACTIVE
        defaultMProposalAdministrationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalAdministrationList where active equals to UPDATED_ACTIVE
        defaultMProposalAdministrationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where active not equals to DEFAULT_ACTIVE
        defaultMProposalAdministrationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalAdministrationList where active not equals to UPDATED_ACTIVE
        defaultMProposalAdministrationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalAdministrationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalAdministrationList where active equals to UPDATED_ACTIVE
        defaultMProposalAdministrationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        // Get all the mProposalAdministrationList where active is not null
        defaultMProposalAdministrationShouldBeFound("active.specified=true");

        // Get all the mProposalAdministrationList where active is null
        defaultMProposalAdministrationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalAdministration.getAdOrganization();
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalAdministrationList where adOrganization equals to adOrganizationId
        defaultMProposalAdministrationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalAdministrationList where adOrganization equals to adOrganizationId + 1
        defaultMProposalAdministrationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mProposalAdministration.getBiddingSubmission();
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mProposalAdministrationList where biddingSubmission equals to biddingSubmissionId
        defaultMProposalAdministrationShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mProposalAdministrationList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMProposalAdministrationShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationsByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteriaLine biddingSubCriteriaLine = mProposalAdministration.getBiddingSubCriteriaLine();
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mProposalAdministrationList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMProposalAdministrationShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mProposalAdministrationList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMProposalAdministrationShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalAdministrationShouldBeFound(String filter) throws Exception {
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalAdministration.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].documentEvaluation").value(hasItem(DEFAULT_DOCUMENT_EVALUATION.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalAdministrationShouldNotBeFound(String filter) throws Exception {
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalAdministration() throws Exception {
        // Get the mProposalAdministration
        restMProposalAdministrationMockMvc.perform(get("/api/m-proposal-administrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalAdministration() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        int databaseSizeBeforeUpdate = mProposalAdministrationRepository.findAll().size();

        // Update the mProposalAdministration
        MProposalAdministration updatedMProposalAdministration = mProposalAdministrationRepository.findById(mProposalAdministration.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalAdministration are not directly saved in db
        em.detach(updatedMProposalAdministration);
        updatedMProposalAdministration
            .answer(UPDATED_ANSWER)
            .documentEvaluation(UPDATED_DOCUMENT_EVALUATION)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(updatedMProposalAdministration);

        restMProposalAdministrationMockMvc.perform(put("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalAdministration in the database
        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeUpdate);
        MProposalAdministration testMProposalAdministration = mProposalAdministrationList.get(mProposalAdministrationList.size() - 1);
        assertThat(testMProposalAdministration.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testMProposalAdministration.isDocumentEvaluation()).isEqualTo(UPDATED_DOCUMENT_EVALUATION);
        assertThat(testMProposalAdministration.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMProposalAdministration.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMProposalAdministration.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMProposalAdministration.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testMProposalAdministration.getAverageScore()).isEqualTo(UPDATED_AVERAGE_SCORE);
        assertThat(testMProposalAdministration.getPassFail()).isEqualTo(UPDATED_PASS_FAIL);
        assertThat(testMProposalAdministration.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalAdministration.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalAdministration() throws Exception {
        int databaseSizeBeforeUpdate = mProposalAdministrationRepository.findAll().size();

        // Create the MProposalAdministration
        MProposalAdministrationDTO mProposalAdministrationDTO = mProposalAdministrationMapper.toDto(mProposalAdministration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalAdministrationMockMvc.perform(put("/api/m-proposal-administrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalAdministration in the database
        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalAdministration() throws Exception {
        // Initialize the database
        mProposalAdministrationRepository.saveAndFlush(mProposalAdministration);

        int databaseSizeBeforeDelete = mProposalAdministrationRepository.findAll().size();

        // Delete the mProposalAdministration
        restMProposalAdministrationMockMvc.perform(delete("/api/m-proposal-administrations/{id}", mProposalAdministration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalAdministration> mProposalAdministrationList = mProposalAdministrationRepository.findAll();
        assertThat(mProposalAdministrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
