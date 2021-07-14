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
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .notes(DEFAULT_NOTES)
            .evaluation(DEFAULT_EVALUATION)
            .averageScore(DEFAULT_AVERAGE_SCORE)
            .passFail(DEFAULT_PASS_FAIL)
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
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
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
        assertThat(testMProposalTechnical.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMProposalTechnical.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMProposalTechnical.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMProposalTechnical.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testMProposalTechnical.getAverageScore()).isEqualTo(DEFAULT_AVERAGE_SCORE);
        assertThat(testMProposalTechnical.getPassFail()).isEqualTo(DEFAULT_PASS_FAIL);
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
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
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
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.averageScore").value(DEFAULT_AVERAGE_SCORE))
            .andExpect(jsonPath("$.passFail").value(DEFAULT_PASS_FAIL))
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
    public void getAllMProposalTechnicalsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalTechnicalList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalTechnicalList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mProposalTechnicalList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction is not null
        defaultMProposalTechnicalShouldBeFound("documentAction.specified=true");

        // Get all the mProposalTechnicalList where documentAction is null
        defaultMProposalTechnicalShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalTechnicalList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProposalTechnicalList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMProposalTechnicalShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalTechnicalList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalTechnicalList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mProposalTechnicalList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus is not null
        defaultMProposalTechnicalShouldBeFound("documentStatus.specified=true");

        // Get all the mProposalTechnicalList where documentStatus is null
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalTechnicalList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProposalTechnicalList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMProposalTechnicalShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes equals to DEFAULT_NOTES
        defaultMProposalTechnicalShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the mProposalTechnicalList where notes equals to UPDATED_NOTES
        defaultMProposalTechnicalShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes not equals to DEFAULT_NOTES
        defaultMProposalTechnicalShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the mProposalTechnicalList where notes not equals to UPDATED_NOTES
        defaultMProposalTechnicalShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultMProposalTechnicalShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the mProposalTechnicalList where notes equals to UPDATED_NOTES
        defaultMProposalTechnicalShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes is not null
        defaultMProposalTechnicalShouldBeFound("notes.specified=true");

        // Get all the mProposalTechnicalList where notes is null
        defaultMProposalTechnicalShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes contains DEFAULT_NOTES
        defaultMProposalTechnicalShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the mProposalTechnicalList where notes contains UPDATED_NOTES
        defaultMProposalTechnicalShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where notes does not contain DEFAULT_NOTES
        defaultMProposalTechnicalShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the mProposalTechnicalList where notes does not contain UPDATED_NOTES
        defaultMProposalTechnicalShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation equals to DEFAULT_EVALUATION
        defaultMProposalTechnicalShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mProposalTechnicalList where evaluation equals to UPDATED_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation not equals to DEFAULT_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mProposalTechnicalList where evaluation not equals to UPDATED_EVALUATION
        defaultMProposalTechnicalShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMProposalTechnicalShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mProposalTechnicalList where evaluation equals to UPDATED_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation is not null
        defaultMProposalTechnicalShouldBeFound("evaluation.specified=true");

        // Get all the mProposalTechnicalList where evaluation is null
        defaultMProposalTechnicalShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation contains DEFAULT_EVALUATION
        defaultMProposalTechnicalShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mProposalTechnicalList where evaluation contains UPDATED_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where evaluation does not contain DEFAULT_EVALUATION
        defaultMProposalTechnicalShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mProposalTechnicalList where evaluation does not contain UPDATED_EVALUATION
        defaultMProposalTechnicalShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore equals to DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.equals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.equals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore not equals to DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.notEquals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore not equals to UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.notEquals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore in DEFAULT_AVERAGE_SCORE or UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.in=" + DEFAULT_AVERAGE_SCORE + "," + UPDATED_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.in=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore is not null
        defaultMProposalTechnicalShouldBeFound("averageScore.specified=true");

        // Get all the mProposalTechnicalList where averageScore is null
        defaultMProposalTechnicalShouldNotBeFound("averageScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore is greater than or equal to DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.greaterThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore is greater than or equal to UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.greaterThanOrEqual=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore is less than or equal to DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.lessThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore is less than or equal to SMALLER_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.lessThanOrEqual=" + SMALLER_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore is less than DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.lessThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore is less than UPDATED_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.lessThan=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByAverageScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where averageScore is greater than DEFAULT_AVERAGE_SCORE
        defaultMProposalTechnicalShouldNotBeFound("averageScore.greaterThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mProposalTechnicalList where averageScore is greater than SMALLER_AVERAGE_SCORE
        defaultMProposalTechnicalShouldBeFound("averageScore.greaterThan=" + SMALLER_AVERAGE_SCORE);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail equals to DEFAULT_PASS_FAIL
        defaultMProposalTechnicalShouldBeFound("passFail.equals=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalTechnicalList where passFail equals to UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldNotBeFound("passFail.equals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail not equals to DEFAULT_PASS_FAIL
        defaultMProposalTechnicalShouldNotBeFound("passFail.notEquals=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalTechnicalList where passFail not equals to UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldBeFound("passFail.notEquals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail in DEFAULT_PASS_FAIL or UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldBeFound("passFail.in=" + DEFAULT_PASS_FAIL + "," + UPDATED_PASS_FAIL);

        // Get all the mProposalTechnicalList where passFail equals to UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldNotBeFound("passFail.in=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail is not null
        defaultMProposalTechnicalShouldBeFound("passFail.specified=true");

        // Get all the mProposalTechnicalList where passFail is null
        defaultMProposalTechnicalShouldNotBeFound("passFail.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail contains DEFAULT_PASS_FAIL
        defaultMProposalTechnicalShouldBeFound("passFail.contains=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalTechnicalList where passFail contains UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldNotBeFound("passFail.contains=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalsByPassFailNotContainsSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalRepository.saveAndFlush(mProposalTechnical);

        // Get all the mProposalTechnicalList where passFail does not contain DEFAULT_PASS_FAIL
        defaultMProposalTechnicalShouldNotBeFound("passFail.doesNotContain=" + DEFAULT_PASS_FAIL);

        // Get all the mProposalTechnicalList where passFail does not contain UPDATED_PASS_FAIL
        defaultMProposalTechnicalShouldBeFound("passFail.doesNotContain=" + UPDATED_PASS_FAIL);
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
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
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
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
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
        assertThat(testMProposalTechnical.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMProposalTechnical.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMProposalTechnical.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMProposalTechnical.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testMProposalTechnical.getAverageScore()).isEqualTo(UPDATED_AVERAGE_SCORE);
        assertThat(testMProposalTechnical.getPassFail()).isEqualTo(UPDATED_PASS_FAIL);
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
