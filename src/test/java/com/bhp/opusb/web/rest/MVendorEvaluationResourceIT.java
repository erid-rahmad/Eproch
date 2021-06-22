package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorEvaluation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MVendorEvaluationRepository;
import com.bhp.opusb.service.MVendorEvaluationService;
import com.bhp.opusb.service.dto.MVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationMapper;
import com.bhp.opusb.service.dto.MVendorEvaluationCriteria;
import com.bhp.opusb.service.MVendorEvaluationQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.bhp.opusb.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVendorEvaluationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorEvaluationResourceIT {

    private static final BigDecimal DEFAULT_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SCORE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_EVALUATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVALUATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EVALUATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TRX = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final ZonedDateTime DEFAULT_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_APPROVE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REJECT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REJECTED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_REASON = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorEvaluationRepository mVendorEvaluationRepository;

    @Autowired
    private MVendorEvaluationMapper mVendorEvaluationMapper;

    @Autowired
    private MVendorEvaluationService mVendorEvaluationService;

    @Autowired
    private MVendorEvaluationQueryService mVendorEvaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorEvaluationMockMvc;

    private MVendorEvaluation mVendorEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorEvaluation createEntity(EntityManager em) {
        MVendorEvaluation mVendorEvaluation = new MVendorEvaluation()
            .score(DEFAULT_SCORE)
            .evaluationDate(DEFAULT_EVALUATION_DATE)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON)
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
        mVendorEvaluation.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mVendorEvaluation.setContract(mContract);
        return mVendorEvaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorEvaluation createUpdatedEntity(EntityManager em) {
        MVendorEvaluation mVendorEvaluation = new MVendorEvaluation()
            .score(UPDATED_SCORE)
            .evaluationDate(UPDATED_EVALUATION_DATE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
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
        mVendorEvaluation.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mVendorEvaluation.setContract(mContract);
        return mVendorEvaluation;
    }

    @BeforeEach
    public void initTest() {
        mVendorEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorEvaluation() throws Exception {
        int databaseSizeBeforeCreate = mVendorEvaluationRepository.findAll().size();

        // Create the MVendorEvaluation
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);
        restMVendorEvaluationMockMvc.perform(post("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorEvaluation in the database
        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorEvaluation testMVendorEvaluation = mVendorEvaluationList.get(mVendorEvaluationList.size() - 1);
        assertThat(testMVendorEvaluation.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMVendorEvaluation.getEvaluationDate()).isEqualTo(DEFAULT_EVALUATION_DATE);
        assertThat(testMVendorEvaluation.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMVendorEvaluation.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMVendorEvaluation.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMVendorEvaluation.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMVendorEvaluation.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMVendorEvaluation.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMVendorEvaluation.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMVendorEvaluation.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMVendorEvaluation.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMVendorEvaluation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorEvaluation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorEvaluationRepository.findAll().size();

        // Create the MVendorEvaluation with an existing ID
        mVendorEvaluation.setId(1L);
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorEvaluationMockMvc.perform(post("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorEvaluation in the database
        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVendorEvaluationRepository.findAll().size();
        // set the field null
        mVendorEvaluation.setScore(null);

        // Create the MVendorEvaluation, which fails.
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);

        restMVendorEvaluationMockMvc.perform(post("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVendorEvaluationRepository.findAll().size();
        // set the field null
        mVendorEvaluation.setDocumentAction(null);

        // Create the MVendorEvaluation, which fails.
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);

        restMVendorEvaluationMockMvc.perform(post("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVendorEvaluationRepository.findAll().size();
        // set the field null
        mVendorEvaluation.setDocumentStatus(null);

        // Create the MVendorEvaluation, which fails.
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);

        restMVendorEvaluationMockMvc.perform(post("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluations() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].evaluationDate").value(hasItem(DEFAULT_EVALUATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorEvaluation() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get the mVendorEvaluation
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations/{id}", mVendorEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.evaluationDate").value(DEFAULT_EVALUATION_DATE.toString()))
            .andExpect(jsonPath("$.dateTrx").value(sameInstant(DEFAULT_DATE_TRX)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(sameInstant(DEFAULT_DATE_APPROVE)))
            .andExpect(jsonPath("$.dateReject").value(sameInstant(DEFAULT_DATE_REJECT)))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        Long id = mVendorEvaluation.getId();

        defaultMVendorEvaluationShouldBeFound("id.equals=" + id);
        defaultMVendorEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score equals to DEFAULT_SCORE
        defaultMVendorEvaluationShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score equals to UPDATED_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score not equals to DEFAULT_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score not equals to UPDATED_SCORE
        defaultMVendorEvaluationShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultMVendorEvaluationShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the mVendorEvaluationList where score equals to UPDATED_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score is not null
        defaultMVendorEvaluationShouldBeFound("score.specified=true");

        // Get all the mVendorEvaluationList where score is null
        defaultMVendorEvaluationShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score is greater than or equal to DEFAULT_SCORE
        defaultMVendorEvaluationShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score is greater than or equal to UPDATED_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score is less than or equal to DEFAULT_SCORE
        defaultMVendorEvaluationShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score is less than or equal to SMALLER_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score is less than DEFAULT_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score is less than UPDATED_SCORE
        defaultMVendorEvaluationShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where score is greater than DEFAULT_SCORE
        defaultMVendorEvaluationShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationList where score is greater than SMALLER_SCORE
        defaultMVendorEvaluationShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate equals to DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.equals=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate equals to UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.equals=" + UPDATED_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate not equals to DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.notEquals=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate not equals to UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.notEquals=" + UPDATED_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate in DEFAULT_EVALUATION_DATE or UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.in=" + DEFAULT_EVALUATION_DATE + "," + UPDATED_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate equals to UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.in=" + UPDATED_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate is not null
        defaultMVendorEvaluationShouldBeFound("evaluationDate.specified=true");

        // Get all the mVendorEvaluationList where evaluationDate is null
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate is greater than or equal to DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.greaterThanOrEqual=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate is greater than or equal to UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.greaterThanOrEqual=" + UPDATED_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate is less than or equal to DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.lessThanOrEqual=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate is less than or equal to SMALLER_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.lessThanOrEqual=" + SMALLER_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate is less than DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.lessThan=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate is less than UPDATED_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.lessThan=" + UPDATED_EVALUATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByEvaluationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where evaluationDate is greater than DEFAULT_EVALUATION_DATE
        defaultMVendorEvaluationShouldNotBeFound("evaluationDate.greaterThan=" + DEFAULT_EVALUATION_DATE);

        // Get all the mVendorEvaluationList where evaluationDate is greater than SMALLER_EVALUATION_DATE
        defaultMVendorEvaluationShouldBeFound("evaluationDate.greaterThan=" + SMALLER_EVALUATION_DATE);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx is not null
        defaultMVendorEvaluationShouldBeFound("dateTrx.specified=true");

        // Get all the mVendorEvaluationList where dateTrx is null
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx is less than UPDATED_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMVendorEvaluationShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mVendorEvaluationList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMVendorEvaluationShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMVendorEvaluationShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVendorEvaluationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMVendorEvaluationShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVendorEvaluationList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mVendorEvaluationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo is not null
        defaultMVendorEvaluationShouldBeFound("documentNo.specified=true");

        // Get all the mVendorEvaluationList where documentNo is null
        defaultMVendorEvaluationShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMVendorEvaluationShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVendorEvaluationList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMVendorEvaluationShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVendorEvaluationList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMVendorEvaluationShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVendorEvaluationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVendorEvaluationList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mVendorEvaluationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction is not null
        defaultMVendorEvaluationShouldBeFound("documentAction.specified=true");

        // Get all the mVendorEvaluationList where documentAction is null
        defaultMVendorEvaluationShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVendorEvaluationList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVendorEvaluationList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMVendorEvaluationShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVendorEvaluationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVendorEvaluationList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mVendorEvaluationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus is not null
        defaultMVendorEvaluationShouldBeFound("documentStatus.specified=true");

        // Get all the mVendorEvaluationList where documentStatus is null
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVendorEvaluationList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVendorEvaluationList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMVendorEvaluationShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where approved equals to DEFAULT_APPROVED
        defaultMVendorEvaluationShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mVendorEvaluationList where approved equals to UPDATED_APPROVED
        defaultMVendorEvaluationShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where approved not equals to DEFAULT_APPROVED
        defaultMVendorEvaluationShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mVendorEvaluationList where approved not equals to UPDATED_APPROVED
        defaultMVendorEvaluationShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMVendorEvaluationShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mVendorEvaluationList where approved equals to UPDATED_APPROVED
        defaultMVendorEvaluationShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where approved is not null
        defaultMVendorEvaluationShouldBeFound("approved.specified=true");

        // Get all the mVendorEvaluationList where approved is null
        defaultMVendorEvaluationShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where processed equals to DEFAULT_PROCESSED
        defaultMVendorEvaluationShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mVendorEvaluationList where processed equals to UPDATED_PROCESSED
        defaultMVendorEvaluationShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where processed not equals to DEFAULT_PROCESSED
        defaultMVendorEvaluationShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mVendorEvaluationList where processed not equals to UPDATED_PROCESSED
        defaultMVendorEvaluationShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMVendorEvaluationShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mVendorEvaluationList where processed equals to UPDATED_PROCESSED
        defaultMVendorEvaluationShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where processed is not null
        defaultMVendorEvaluationShouldBeFound("processed.specified=true");

        // Get all the mVendorEvaluationList where processed is null
        defaultMVendorEvaluationShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove is not null
        defaultMVendorEvaluationShouldBeFound("dateApprove.specified=true");

        // Get all the mVendorEvaluationList where dateApprove is null
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMVendorEvaluationShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mVendorEvaluationList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMVendorEvaluationShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject is not null
        defaultMVendorEvaluationShouldBeFound("dateReject.specified=true");

        // Get all the mVendorEvaluationList where dateReject is null
        defaultMVendorEvaluationShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject is less than UPDATED_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMVendorEvaluationShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mVendorEvaluationList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMVendorEvaluationShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMVendorEvaluationShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mVendorEvaluationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mVendorEvaluationList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mVendorEvaluationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason is not null
        defaultMVendorEvaluationShouldBeFound("rejectedReason.specified=true");

        // Get all the mVendorEvaluationList where rejectedReason is null
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMVendorEvaluationShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mVendorEvaluationList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMVendorEvaluationShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mVendorEvaluationList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMVendorEvaluationShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where uid equals to DEFAULT_UID
        defaultMVendorEvaluationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorEvaluationList where uid equals to UPDATED_UID
        defaultMVendorEvaluationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where uid not equals to DEFAULT_UID
        defaultMVendorEvaluationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorEvaluationList where uid not equals to UPDATED_UID
        defaultMVendorEvaluationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorEvaluationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorEvaluationList where uid equals to UPDATED_UID
        defaultMVendorEvaluationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where uid is not null
        defaultMVendorEvaluationShouldBeFound("uid.specified=true");

        // Get all the mVendorEvaluationList where uid is null
        defaultMVendorEvaluationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where active equals to DEFAULT_ACTIVE
        defaultMVendorEvaluationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorEvaluationList where active equals to UPDATED_ACTIVE
        defaultMVendorEvaluationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where active not equals to DEFAULT_ACTIVE
        defaultMVendorEvaluationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorEvaluationList where active not equals to UPDATED_ACTIVE
        defaultMVendorEvaluationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorEvaluationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorEvaluationList where active equals to UPDATED_ACTIVE
        defaultMVendorEvaluationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        // Get all the mVendorEvaluationList where active is not null
        defaultMVendorEvaluationShouldBeFound("active.specified=true");

        // Get all the mVendorEvaluationList where active is null
        defaultMVendorEvaluationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorEvaluation.getAdOrganization();
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorEvaluationList where adOrganization equals to adOrganizationId
        defaultMVendorEvaluationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorEvaluationList where adOrganization equals to adOrganizationId + 1
        defaultMVendorEvaluationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mVendorEvaluation.getContract();
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);
        Long contractId = contract.getId();

        // Get all the mVendorEvaluationList where contract equals to contractId
        defaultMVendorEvaluationShouldBeFound("contractId.equals=" + contractId);

        // Get all the mVendorEvaluationList where contract equals to contractId + 1
        defaultMVendorEvaluationShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationsByReviewerIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);
        AdUser reviewer = AdUserResourceIT.createEntity(em);
        em.persist(reviewer);
        em.flush();
        mVendorEvaluation.setReviewer(reviewer);
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);
        Long reviewerId = reviewer.getId();

        // Get all the mVendorEvaluationList where reviewer equals to reviewerId
        defaultMVendorEvaluationShouldBeFound("reviewerId.equals=" + reviewerId);

        // Get all the mVendorEvaluationList where reviewer equals to reviewerId + 1
        defaultMVendorEvaluationShouldNotBeFound("reviewerId.equals=" + (reviewerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorEvaluationShouldBeFound(String filter) throws Exception {
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].evaluationDate").value(hasItem(DEFAULT_EVALUATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorEvaluationShouldNotBeFound(String filter) throws Exception {
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorEvaluation() throws Exception {
        // Get the mVendorEvaluation
        restMVendorEvaluationMockMvc.perform(get("/api/m-vendor-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorEvaluation() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        int databaseSizeBeforeUpdate = mVendorEvaluationRepository.findAll().size();

        // Update the mVendorEvaluation
        MVendorEvaluation updatedMVendorEvaluation = mVendorEvaluationRepository.findById(mVendorEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorEvaluation are not directly saved in db
        em.detach(updatedMVendorEvaluation);
        updatedMVendorEvaluation
            .score(UPDATED_SCORE)
            .evaluationDate(UPDATED_EVALUATION_DATE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(updatedMVendorEvaluation);

        restMVendorEvaluationMockMvc.perform(put("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorEvaluation in the database
        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeUpdate);
        MVendorEvaluation testMVendorEvaluation = mVendorEvaluationList.get(mVendorEvaluationList.size() - 1);
        assertThat(testMVendorEvaluation.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMVendorEvaluation.getEvaluationDate()).isEqualTo(UPDATED_EVALUATION_DATE);
        assertThat(testMVendorEvaluation.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMVendorEvaluation.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMVendorEvaluation.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMVendorEvaluation.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMVendorEvaluation.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMVendorEvaluation.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMVendorEvaluation.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMVendorEvaluation.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMVendorEvaluation.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMVendorEvaluation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorEvaluation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = mVendorEvaluationRepository.findAll().size();

        // Create the MVendorEvaluation
        MVendorEvaluationDTO mVendorEvaluationDTO = mVendorEvaluationMapper.toDto(mVendorEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorEvaluationMockMvc.perform(put("/api/m-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorEvaluation in the database
        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorEvaluation() throws Exception {
        // Initialize the database
        mVendorEvaluationRepository.saveAndFlush(mVendorEvaluation);

        int databaseSizeBeforeDelete = mVendorEvaluationRepository.findAll().size();

        // Delete the mVendorEvaluation
        restMVendorEvaluationMockMvc.perform(delete("/api/m-vendor-evaluations/{id}", mVendorEvaluation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorEvaluation> mVendorEvaluationList = mVendorEvaluationRepository.findAll();
        assertThat(mVendorEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
