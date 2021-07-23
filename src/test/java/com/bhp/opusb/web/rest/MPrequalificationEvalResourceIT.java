package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationEval;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.MPrequalificationEvalRepository;
import com.bhp.opusb.service.MPrequalificationEvalService;
import com.bhp.opusb.service.dto.MPrequalificationEvalDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalMapper;
import com.bhp.opusb.service.dto.MPrequalificationEvalCriteria;
import com.bhp.opusb.service.MPrequalificationEvalQueryService;

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
 * Integration tests for the {@link MPrequalificationEvalResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationEvalResourceIT {

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
    private MPrequalificationEvalRepository mPrequalificationEvalRepository;

    @Autowired
    private MPrequalificationEvalMapper mPrequalificationEvalMapper;

    @Autowired
    private MPrequalificationEvalService mPrequalificationEvalService;

    @Autowired
    private MPrequalificationEvalQueryService mPrequalificationEvalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationEvalMockMvc;

    private MPrequalificationEval mPrequalificationEval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEval createEntity(EntityManager em) {
        MPrequalificationEval mPrequalificationEval = new MPrequalificationEval()
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
        mPrequalificationEval.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationEval.setPrequalificationSubmission(mPrequalificationSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mPrequalificationEval.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mPrequalificationEval;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEval createUpdatedEntity(EntityManager em) {
        MPrequalificationEval mPrequalificationEval = new MPrequalificationEval()
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
        mPrequalificationEval.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationEval.setPrequalificationSubmission(mPrequalificationSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mPrequalificationEval.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mPrequalificationEval;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationEval = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationEval() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEvalRepository.findAll().size();

        // Create the MPrequalificationEval
        MPrequalificationEvalDTO mPrequalificationEvalDTO = mPrequalificationEvalMapper.toDto(mPrequalificationEval);
        restMPrequalificationEvalMockMvc.perform(post("/api/m-prequalification-evals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationEval in the database
        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationEval testMPrequalificationEval = mPrequalificationEvalList.get(mPrequalificationEvalList.size() - 1);
        assertThat(testMPrequalificationEval.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testMPrequalificationEval.isDocumentEvaluation()).isEqualTo(DEFAULT_DOCUMENT_EVALUATION);
        assertThat(testMPrequalificationEval.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMPrequalificationEval.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMPrequalificationEval.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMPrequalificationEval.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testMPrequalificationEval.getAverageScore()).isEqualTo(DEFAULT_AVERAGE_SCORE);
        assertThat(testMPrequalificationEval.getPassFail()).isEqualTo(DEFAULT_PASS_FAIL);
        assertThat(testMPrequalificationEval.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationEval.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationEvalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEvalRepository.findAll().size();

        // Create the MPrequalificationEval with an existing ID
        mPrequalificationEval.setId(1L);
        MPrequalificationEvalDTO mPrequalificationEvalDTO = mPrequalificationEvalMapper.toDto(mPrequalificationEval);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationEvalMockMvc.perform(post("/api/m-prequalification-evals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEval in the database
        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationEvalRepository.findAll().size();
        // set the field null
        mPrequalificationEval.setAnswer(null);

        // Create the MPrequalificationEval, which fails.
        MPrequalificationEvalDTO mPrequalificationEvalDTO = mPrequalificationEvalMapper.toDto(mPrequalificationEval);

        restMPrequalificationEvalMockMvc.perform(post("/api/m-prequalification-evals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvals() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEval.getId().intValue())))
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
    public void getMPrequalificationEval() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get the mPrequalificationEval
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals/{id}", mPrequalificationEval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationEval.getId().intValue()))
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
    public void getMPrequalificationEvalsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        Long id = mPrequalificationEval.getId();

        defaultMPrequalificationEvalShouldBeFound("id.equals=" + id);
        defaultMPrequalificationEvalShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationEvalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationEvalShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationEvalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationEvalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer equals to DEFAULT_ANSWER
        defaultMPrequalificationEvalShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the mPrequalificationEvalList where answer equals to UPDATED_ANSWER
        defaultMPrequalificationEvalShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer not equals to DEFAULT_ANSWER
        defaultMPrequalificationEvalShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the mPrequalificationEvalList where answer not equals to UPDATED_ANSWER
        defaultMPrequalificationEvalShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultMPrequalificationEvalShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the mPrequalificationEvalList where answer equals to UPDATED_ANSWER
        defaultMPrequalificationEvalShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer is not null
        defaultMPrequalificationEvalShouldBeFound("answer.specified=true");

        // Get all the mPrequalificationEvalList where answer is null
        defaultMPrequalificationEvalShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer contains DEFAULT_ANSWER
        defaultMPrequalificationEvalShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the mPrequalificationEvalList where answer contains UPDATED_ANSWER
        defaultMPrequalificationEvalShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where answer does not contain DEFAULT_ANSWER
        defaultMPrequalificationEvalShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the mPrequalificationEvalList where answer does not contain UPDATED_ANSWER
        defaultMPrequalificationEvalShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentEvaluation equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("documentEvaluation.equals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mPrequalificationEvalList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("documentEvaluation.equals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentEvaluation not equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("documentEvaluation.notEquals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mPrequalificationEvalList where documentEvaluation not equals to UPDATED_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("documentEvaluation.notEquals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentEvaluation in DEFAULT_DOCUMENT_EVALUATION or UPDATED_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("documentEvaluation.in=" + DEFAULT_DOCUMENT_EVALUATION + "," + UPDATED_DOCUMENT_EVALUATION);

        // Get all the mPrequalificationEvalList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("documentEvaluation.in=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentEvaluation is not null
        defaultMPrequalificationEvalShouldBeFound("documentEvaluation.specified=true");

        // Get all the mPrequalificationEvalList where documentEvaluation is null
        defaultMPrequalificationEvalShouldNotBeFound("documentEvaluation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationEvalList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationEvalList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mPrequalificationEvalList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction is not null
        defaultMPrequalificationEvalShouldBeFound("documentAction.specified=true");

        // Get all the mPrequalificationEvalList where documentAction is null
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationEvalList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationEvalList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationEvalShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationEvalList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationEvalList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mPrequalificationEvalList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus is not null
        defaultMPrequalificationEvalShouldBeFound("documentStatus.specified=true");

        // Get all the mPrequalificationEvalList where documentStatus is null
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationEvalList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationEvalList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationEvalShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes equals to DEFAULT_NOTES
        defaultMPrequalificationEvalShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the mPrequalificationEvalList where notes equals to UPDATED_NOTES
        defaultMPrequalificationEvalShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes not equals to DEFAULT_NOTES
        defaultMPrequalificationEvalShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the mPrequalificationEvalList where notes not equals to UPDATED_NOTES
        defaultMPrequalificationEvalShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultMPrequalificationEvalShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the mPrequalificationEvalList where notes equals to UPDATED_NOTES
        defaultMPrequalificationEvalShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes is not null
        defaultMPrequalificationEvalShouldBeFound("notes.specified=true");

        // Get all the mPrequalificationEvalList where notes is null
        defaultMPrequalificationEvalShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes contains DEFAULT_NOTES
        defaultMPrequalificationEvalShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the mPrequalificationEvalList where notes contains UPDATED_NOTES
        defaultMPrequalificationEvalShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where notes does not contain DEFAULT_NOTES
        defaultMPrequalificationEvalShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the mPrequalificationEvalList where notes does not contain UPDATED_NOTES
        defaultMPrequalificationEvalShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation equals to DEFAULT_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mPrequalificationEvalList where evaluation equals to UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation not equals to DEFAULT_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mPrequalificationEvalList where evaluation not equals to UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mPrequalificationEvalList where evaluation equals to UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation is not null
        defaultMPrequalificationEvalShouldBeFound("evaluation.specified=true");

        // Get all the mPrequalificationEvalList where evaluation is null
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation contains DEFAULT_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mPrequalificationEvalList where evaluation contains UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where evaluation does not contain DEFAULT_EVALUATION
        defaultMPrequalificationEvalShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mPrequalificationEvalList where evaluation does not contain UPDATED_EVALUATION
        defaultMPrequalificationEvalShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore equals to DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.equals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.equals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore not equals to DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.notEquals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore not equals to UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.notEquals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore in DEFAULT_AVERAGE_SCORE or UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.in=" + DEFAULT_AVERAGE_SCORE + "," + UPDATED_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.in=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore is not null
        defaultMPrequalificationEvalShouldBeFound("averageScore.specified=true");

        // Get all the mPrequalificationEvalList where averageScore is null
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore is greater than or equal to DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.greaterThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore is greater than or equal to UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.greaterThanOrEqual=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore is less than or equal to DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.lessThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore is less than or equal to SMALLER_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.lessThanOrEqual=" + SMALLER_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore is less than DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.lessThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore is less than UPDATED_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.lessThan=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAverageScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where averageScore is greater than DEFAULT_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldNotBeFound("averageScore.greaterThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mPrequalificationEvalList where averageScore is greater than SMALLER_AVERAGE_SCORE
        defaultMPrequalificationEvalShouldBeFound("averageScore.greaterThan=" + SMALLER_AVERAGE_SCORE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail equals to DEFAULT_PASS_FAIL
        defaultMPrequalificationEvalShouldBeFound("passFail.equals=" + DEFAULT_PASS_FAIL);

        // Get all the mPrequalificationEvalList where passFail equals to UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldNotBeFound("passFail.equals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail not equals to DEFAULT_PASS_FAIL
        defaultMPrequalificationEvalShouldNotBeFound("passFail.notEquals=" + DEFAULT_PASS_FAIL);

        // Get all the mPrequalificationEvalList where passFail not equals to UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldBeFound("passFail.notEquals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail in DEFAULT_PASS_FAIL or UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldBeFound("passFail.in=" + DEFAULT_PASS_FAIL + "," + UPDATED_PASS_FAIL);

        // Get all the mPrequalificationEvalList where passFail equals to UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldNotBeFound("passFail.in=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail is not null
        defaultMPrequalificationEvalShouldBeFound("passFail.specified=true");

        // Get all the mPrequalificationEvalList where passFail is null
        defaultMPrequalificationEvalShouldNotBeFound("passFail.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail contains DEFAULT_PASS_FAIL
        defaultMPrequalificationEvalShouldBeFound("passFail.contains=" + DEFAULT_PASS_FAIL);

        // Get all the mPrequalificationEvalList where passFail contains UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldNotBeFound("passFail.contains=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPassFailNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where passFail does not contain DEFAULT_PASS_FAIL
        defaultMPrequalificationEvalShouldNotBeFound("passFail.doesNotContain=" + DEFAULT_PASS_FAIL);

        // Get all the mPrequalificationEvalList where passFail does not contain UPDATED_PASS_FAIL
        defaultMPrequalificationEvalShouldBeFound("passFail.doesNotContain=" + UPDATED_PASS_FAIL);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where uid equals to DEFAULT_UID
        defaultMPrequalificationEvalShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationEvalList where uid equals to UPDATED_UID
        defaultMPrequalificationEvalShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where uid not equals to DEFAULT_UID
        defaultMPrequalificationEvalShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationEvalList where uid not equals to UPDATED_UID
        defaultMPrequalificationEvalShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationEvalShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationEvalList where uid equals to UPDATED_UID
        defaultMPrequalificationEvalShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where uid is not null
        defaultMPrequalificationEvalShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationEvalList where uid is null
        defaultMPrequalificationEvalShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationEvalShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEvalList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationEvalShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEvalList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationEvalShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationEvalList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        // Get all the mPrequalificationEvalList where active is not null
        defaultMPrequalificationEvalShouldBeFound("active.specified=true");

        // Get all the mPrequalificationEvalList where active is null
        defaultMPrequalificationEvalShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationEval.getAdOrganization();
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationEvalList where adOrganization equals to adOrganizationId
        defaultMPrequalificationEvalShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationEvalList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationEvalShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByPrequalificationSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationSubmission prequalificationSubmission = mPrequalificationEval.getPrequalificationSubmission();
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);
        Long prequalificationSubmissionId = prequalificationSubmission.getId();

        // Get all the mPrequalificationEvalList where prequalificationSubmission equals to prequalificationSubmissionId
        defaultMPrequalificationEvalShouldBeFound("prequalificationSubmissionId.equals=" + prequalificationSubmissionId);

        // Get all the mPrequalificationEvalList where prequalificationSubmission equals to prequalificationSubmissionId + 1
        defaultMPrequalificationEvalShouldNotBeFound("prequalificationSubmissionId.equals=" + (prequalificationSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalsByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteriaLine biddingSubCriteriaLine = mPrequalificationEval.getBiddingSubCriteriaLine();
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mPrequalificationEvalList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMPrequalificationEvalShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mPrequalificationEvalList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMPrequalificationEvalShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationEvalShouldBeFound(String filter) throws Exception {
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEval.getId().intValue())))
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
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationEvalShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationEval() throws Exception {
        // Get the mPrequalificationEval
        restMPrequalificationEvalMockMvc.perform(get("/api/m-prequalification-evals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationEval() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        int databaseSizeBeforeUpdate = mPrequalificationEvalRepository.findAll().size();

        // Update the mPrequalificationEval
        MPrequalificationEval updatedMPrequalificationEval = mPrequalificationEvalRepository.findById(mPrequalificationEval.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationEval are not directly saved in db
        em.detach(updatedMPrequalificationEval);
        updatedMPrequalificationEval
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
        MPrequalificationEvalDTO mPrequalificationEvalDTO = mPrequalificationEvalMapper.toDto(updatedMPrequalificationEval);

        restMPrequalificationEvalMockMvc.perform(put("/api/m-prequalification-evals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationEval in the database
        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationEval testMPrequalificationEval = mPrequalificationEvalList.get(mPrequalificationEvalList.size() - 1);
        assertThat(testMPrequalificationEval.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testMPrequalificationEval.isDocumentEvaluation()).isEqualTo(UPDATED_DOCUMENT_EVALUATION);
        assertThat(testMPrequalificationEval.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMPrequalificationEval.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMPrequalificationEval.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMPrequalificationEval.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testMPrequalificationEval.getAverageScore()).isEqualTo(UPDATED_AVERAGE_SCORE);
        assertThat(testMPrequalificationEval.getPassFail()).isEqualTo(UPDATED_PASS_FAIL);
        assertThat(testMPrequalificationEval.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationEval.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationEval() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationEvalRepository.findAll().size();

        // Create the MPrequalificationEval
        MPrequalificationEvalDTO mPrequalificationEvalDTO = mPrequalificationEvalMapper.toDto(mPrequalificationEval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationEvalMockMvc.perform(put("/api/m-prequalification-evals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEval in the database
        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationEval() throws Exception {
        // Initialize the database
        mPrequalificationEvalRepository.saveAndFlush(mPrequalificationEval);

        int databaseSizeBeforeDelete = mPrequalificationEvalRepository.findAll().size();

        // Delete the mPrequalificationEval
        restMPrequalificationEvalMockMvc.perform(delete("/api/m-prequalification-evals/{id}", mPrequalificationEval.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationEval> mPrequalificationEvalList = mPrequalificationEvalRepository.findAll();
        assertThat(mPrequalificationEvalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
