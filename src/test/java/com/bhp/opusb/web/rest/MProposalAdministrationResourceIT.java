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
