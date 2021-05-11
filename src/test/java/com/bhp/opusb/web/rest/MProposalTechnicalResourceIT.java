package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalTechnical;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.MProposalTechnicalRepository;
import com.bhp.opusb.service.MProposalTechnicalService;
import com.bhp.opusb.service.dto.MProposalTechnicalDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalMapper;
import com.bhp.opusb.service.dto.MProposalTechnicalCriteria;
import com.bhp.opusb.service.MProposalTechnicalQueryService;

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
 * Integration tests for the {@link MProposalTechnicalResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalTechnicalResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DOCUMENT_EVALUATION = false;
    private static final Boolean UPDATED_DOCUMENT_EVALUATION = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProposalTechnicalRepository mProposalTechnicalRepository;

    @Autowired
    private MProposalTechnicalMapper mProposalTechnicalMapper;

    @Autowired
    private MProposalTechnicalService mProposalTechnicalService;

    @Autowired
    private MProposalTechnicalQueryService mProposalTechnicalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalTechnicalMockMvc;

    private MProposalTechnical mProposalTechnical;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalTechnical createEntity(EntityManager em) {
        MProposalTechnical mProposalTechnical = new MProposalTechnical()
            .answer(DEFAULT_ANSWER)
            .documentEvaluation(DEFAULT_DOCUMENT_EVALUATION)
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
        mProposalTechnical.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalTechnical.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mProposalTechnical.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mProposalTechnical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalTechnical createUpdatedEntity(EntityManager em) {
        MProposalTechnical mProposalTechnical = new MProposalTechnical()
            .answer(UPDATED_ANSWER)
            .documentEvaluation(UPDATED_DOCUMENT_EVALUATION)
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
        mProposalTechnical.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalTechnical.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine;
        if (TestUtil.findAll(em, CBiddingSubCriteriaLine.class).isEmpty()) {
            cBiddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteriaLine);
            em.flush();
        } else {
            cBiddingSubCriteriaLine = TestUtil.findAll(em, CBiddingSubCriteriaLine.class).get(0);
        }
        mProposalTechnical.setBiddingSubCriteriaLine(cBiddingSubCriteriaLine);
        return mProposalTechnical;
    }

    @BeforeEach
    public void initTest() {
        mProposalTechnical = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalTechnical() throws Exception {
        int databaseSizeBeforeCreate = mProposalTechnicalRepository.findAll().size();

        // Create the MProposalTechnical
        MProposalTechnicalDTO mProposalTechnicalDTO = mProposalTechnicalMapper.toDto(mProposalTechnical);
        restMProposalTechnicalMockMvc.perform(post("/api/m-proposal-technicals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalTechnical in the database
        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalTechnical testMProposalTechnical = mProposalTechnicalList.get(mProposalTechnicalList.size() - 1);
        assertThat(testMProposalTechnical.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testMProposalTechnical.isDocumentEvaluation()).isEqualTo(DEFAULT_DOCUMENT_EVALUATION);
        assertThat(testMProposalTechnical.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalTechnical.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalTechnicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalTechnicalRepository.findAll().size();

        // Create the MProposalTechnical with an existing ID
        mProposalTechnical.setId(1L);
        MProposalTechnicalDTO mProposalTechnicalDTO = mProposalTechnicalMapper.toDto(mProposalTechnical);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalTechnicalMockMvc.perform(post("/api/m-proposal-technicals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalTechnical in the database
        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalTechnicalRepository.findAll().size();
        // set the field null
        mProposalTechnical.setAnswer(null);

        // Create the MProposalTechnical, which fails.
        MProposalTechnicalDTO mProposalTechnicalDTO = mProposalTechnicalMapper.toDto(mProposalTechnical);

        restMProposalTechnicalMockMvc.perform(post("/api/m-proposal-technicals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicals() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalTechnical.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].documentEvaluation").value(hasItem(DEFAULT_DOCUMENT_EVALUATION.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalTechnical() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get the mProposalTechnical
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals/{id}", mProposalTechnical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalTechnical.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.documentEvaluation").value(DEFAULT_DOCUMENT_EVALUATION.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalTechnicalsByIdFiltering() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        Long id = mProposalTechnical.getId();

        defaultMProposalTechnicalShouldBeFound("id.equals=" + id);
        defaultMProposalTechnicalShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalTechnicalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalTechnicalShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalTechnicalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalTechnicalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer equals to DEFAULT_ANSWER
        defaultMProposalTechnicalShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the mProposalTechnicalList where answer equals to UPDATED_ANSWER
        defaultMProposalTechnicalShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer not equals to DEFAULT_ANSWER
        defaultMProposalTechnicalShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the mProposalTechnicalList where answer not equals to UPDATED_ANSWER
        defaultMProposalTechnicalShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultMProposalTechnicalShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the mProposalTechnicalList where answer equals to UPDATED_ANSWER
        defaultMProposalTechnicalShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer is not null
        defaultMProposalTechnicalShouldBeFound("answer.specified=true");

        // Get all the mProposalTechnicalList where answer is null
        defaultMProposalTechnicalShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer contains DEFAULT_ANSWER
        defaultMProposalTechnicalShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the mProposalTechnicalList where answer contains UPDATED_ANSWER
        defaultMProposalTechnicalShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where answer does not contain DEFAULT_ANSWER
        defaultMProposalTechnicalShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the mProposalTechnicalList where answer does not contain UPDATED_ANSWER
        defaultMProposalTechnicalShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentEvaluation equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldBeFound("documentEvaluation.equals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mProposalTechnicalList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("documentEvaluation.equals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentEvaluation not equals to DEFAULT_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("documentEvaluation.notEquals=" + DEFAULT_DOCUMENT_EVALUATION);

        // Get all the mProposalTechnicalList where documentEvaluation not equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldBeFound("documentEvaluation.notEquals=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentEvaluation in DEFAULT_DOCUMENT_EVALUATION or UPDATED_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldBeFound("documentEvaluation.in=" + DEFAULT_DOCUMENT_EVALUATION + "," + UPDATED_DOCUMENT_EVALUATION);

        // Get all the mProposalTechnicalList where documentEvaluation equals to UPDATED_DOCUMENT_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("documentEvaluation.in=" + UPDATED_DOCUMENT_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentEvaluation is not null
        defaultMProposalTechnicalShouldBeFound("documentEvaluation.specified=true");

        // Get all the mProposalTechnicalList where documentEvaluation is null
        defaultMProposalTechnicalShouldNotBeFound("documentEvaluation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where uid equals to DEFAULT_UID
        defaultMProposalTechnicalShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalTechnicalList where uid equals to UPDATED_UID
        defaultMProposalTechnicalShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where uid not equals to DEFAULT_UID
        defaultMProposalTechnicalShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalTechnicalList where uid not equals to UPDATED_UID
        defaultMProposalTechnicalShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalTechnicalShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalTechnicalList where uid equals to UPDATED_UID
        defaultMProposalTechnicalShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where uid is not null
        defaultMProposalTechnicalShouldBeFound("uid.specified=true");

        // Get all the mProposalTechnicalList where uid is null
        defaultMProposalTechnicalShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where active equals to DEFAULT_ACTIVE
        defaultMProposalTechnicalShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalTechnicalList where active equals to UPDATED_ACTIVE
        defaultMProposalTechnicalShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where active not equals to DEFAULT_ACTIVE
        defaultMProposalTechnicalShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalTechnicalList where active not equals to UPDATED_ACTIVE
        defaultMProposalTechnicalShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalTechnicalShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalTechnicalList where active equals to UPDATED_ACTIVE
        defaultMProposalTechnicalShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where active is not null
        defaultMProposalTechnicalShouldBeFound("active.specified=true");

        // Get all the mProposalTechnicalList where active is null
        defaultMProposalTechnicalShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalTechnical.getAdOrganization();
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalTechnicalList where adOrganization equals to adOrganizationId
        defaultMProposalTechnicalShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalTechnicalList where adOrganization equals to adOrganizationId + 1
        defaultMProposalTechnicalShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mProposalTechnical.getBiddingSubmission();
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mProposalTechnicalList where biddingSubmission equals to biddingSubmissionId
        defaultMProposalTechnicalShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mProposalTechnicalList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMProposalTechnicalShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteriaLine biddingSubCriteriaLine = mProposalTechnical.getBiddingSubCriteriaLine();
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mProposalTechnicalList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMProposalTechnicalShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mProposalTechnicalList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMProposalTechnicalShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalTechnicalShouldBeFound(String filter) throws Exception {
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalTechnical.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].documentEvaluation").value(hasItem(DEFAULT_DOCUMENT_EVALUATION.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalTechnicalShouldNotBeFound(String filter) throws Exception {
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalTechnical() throws Exception {
        // Get the mProposalTechnical
        restMProposalTechnicalMockMvc.perform(get("/api/m-proposal-technicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalTechnical() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        int databaseSizeBeforeUpdate = mProposalTechnicalRepository.findAll().size();

        // Update the mProposalTechnical
        MProposalTechnical updatedMProposalTechnical = mProposalTechnicalRepository.findById(mProposalTechnical.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalTechnical are not directly saved in db
        em.detach(updatedMProposalTechnical);
        updatedMProposalTechnical
            .answer(UPDATED_ANSWER)
            .documentEvaluation(UPDATED_DOCUMENT_EVALUATION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalTechnicalDTO mProposalTechnicalDTO = mProposalTechnicalMapper.toDto(updatedMProposalTechnical);

        restMProposalTechnicalMockMvc.perform(put("/api/m-proposal-technicals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalTechnical in the database
        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeUpdate);
        MProposalTechnical testMProposalTechnical = mProposalTechnicalList.get(mProposalTechnicalList.size() - 1);
        assertThat(testMProposalTechnical.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testMProposalTechnical.isDocumentEvaluation()).isEqualTo(UPDATED_DOCUMENT_EVALUATION);
        assertThat(testMProposalTechnical.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalTechnical.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalTechnical() throws Exception {
        int databaseSizeBeforeUpdate = mProposalTechnicalRepository.findAll().size();

        // Create the MProposalTechnical
        MProposalTechnicalDTO mProposalTechnicalDTO = mProposalTechnicalMapper.toDto(mProposalTechnical);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalTechnicalMockMvc.perform(put("/api/m-proposal-technicals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalTechnical in the database
        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalTechnical() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        int databaseSizeBeforeDelete = mProposalTechnicalRepository.findAll().size();

        // Delete the mProposalTechnical
        restMProposalTechnicalMockMvc.perform(delete("/api/m-proposal-technicals/{id}", mProposalTechnical.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalTechnical> mProposalTechnicalList = mProposalTechnicalRepository.findAll();
        assertThat(mProposalTechnicalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
