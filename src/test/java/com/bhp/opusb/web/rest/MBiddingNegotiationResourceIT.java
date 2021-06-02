package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.domain.MBiddingEvaluation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MBiddingNegotiationRepository;
import com.bhp.opusb.service.MBiddingNegotiationService;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationMapper;
import com.bhp.opusb.service.dto.MBiddingNegotiationCriteria;
import com.bhp.opusb.service.MBiddingNegotiationQueryService;

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
 * Integration tests for the {@link MBiddingNegotiationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingNegotiationResourceIT {

    private static final String DEFAULT_BIDDING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BIDDING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingNegotiationRepository mBiddingNegotiationRepository;

    @Autowired
    private MBiddingNegotiationMapper mBiddingNegotiationMapper;

    @Autowired
    private MBiddingNegotiationService mBiddingNegotiationService;

    @Autowired
    private MBiddingNegotiationQueryService mBiddingNegotiationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingNegotiationMockMvc;

    private MBiddingNegotiation mBiddingNegotiation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiation createEntity(EntityManager em) {
        MBiddingNegotiation mBiddingNegotiation = new MBiddingNegotiation()
            .biddingStatus(DEFAULT_BIDDING_STATUS)
            .evaluationStatus(DEFAULT_EVALUATION_STATUS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingEvaluation mBiddingEvaluation;
        if (TestUtil.findAll(em, MBiddingEvaluation.class).isEmpty()) {
            mBiddingEvaluation = MBiddingEvaluationResourceIT.createEntity(em);
            em.persist(mBiddingEvaluation);
            em.flush();
        } else {
            mBiddingEvaluation = TestUtil.findAll(em, MBiddingEvaluation.class).get(0);
        }
        mBiddingNegotiation.setBiddingEval(mBiddingEvaluation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingNegotiation.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingNegotiation.setBiddingSchedule(mBiddingSchedule);
        return mBiddingNegotiation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiation createUpdatedEntity(EntityManager em) {
        MBiddingNegotiation mBiddingNegotiation = new MBiddingNegotiation()
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingEvaluation mBiddingEvaluation;
        if (TestUtil.findAll(em, MBiddingEvaluation.class).isEmpty()) {
            mBiddingEvaluation = MBiddingEvaluationResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvaluation);
            em.flush();
        } else {
            mBiddingEvaluation = TestUtil.findAll(em, MBiddingEvaluation.class).get(0);
        }
        mBiddingNegotiation.setBiddingEval(mBiddingEvaluation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingNegotiation.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingNegotiation.setBiddingSchedule(mBiddingSchedule);
        return mBiddingNegotiation;
    }

    @BeforeEach
    public void initTest() {
        mBiddingNegotiation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiation() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationRepository.findAll().size();

        // Create the MBiddingNegotiation
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(mBiddingNegotiation);
        restMBiddingNegotiationMockMvc.perform(post("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingNegotiation in the database
        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingNegotiation testMBiddingNegotiation = mBiddingNegotiationList.get(mBiddingNegotiationList.size() - 1);
        assertThat(testMBiddingNegotiation.getBiddingStatus()).isEqualTo(DEFAULT_BIDDING_STATUS);
        assertThat(testMBiddingNegotiation.getEvaluationStatus()).isEqualTo(DEFAULT_EVALUATION_STATUS);
        assertThat(testMBiddingNegotiation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMBiddingNegotiation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMBiddingNegotiation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingNegotiation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationRepository.findAll().size();

        // Create the MBiddingNegotiation with an existing ID
        mBiddingNegotiation.setId(1L);
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(mBiddingNegotiation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingNegotiationMockMvc.perform(post("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiation in the database
        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingNegotiationRepository.findAll().size();
        // set the field null
        mBiddingNegotiation.setStartDate(null);

        // Create the MBiddingNegotiation, which fails.
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(mBiddingNegotiation);

        restMBiddingNegotiationMockMvc.perform(post("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingNegotiationRepository.findAll().size();
        // set the field null
        mBiddingNegotiation.setEndDate(null);

        // Create the MBiddingNegotiation, which fails.
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(mBiddingNegotiation);

        restMBiddingNegotiationMockMvc.perform(post("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiations() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].biddingStatus").value(hasItem(DEFAULT_BIDDING_STATUS)))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingNegotiation() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get the mBiddingNegotiation
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations/{id}", mBiddingNegotiation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingNegotiation.getId().intValue()))
            .andExpect(jsonPath("$.biddingStatus").value(DEFAULT_BIDDING_STATUS))
            .andExpect(jsonPath("$.evaluationStatus").value(DEFAULT_EVALUATION_STATUS))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingNegotiationsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        Long id = mBiddingNegotiation.getId();

        defaultMBiddingNegotiationShouldBeFound("id.equals=" + id);
        defaultMBiddingNegotiationShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingNegotiationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingNegotiationShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingNegotiationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingNegotiationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus equals to DEFAULT_BIDDING_STATUS
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.equals=" + DEFAULT_BIDDING_STATUS);

        // Get all the mBiddingNegotiationList where biddingStatus equals to UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.equals=" + UPDATED_BIDDING_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus not equals to DEFAULT_BIDDING_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.notEquals=" + DEFAULT_BIDDING_STATUS);

        // Get all the mBiddingNegotiationList where biddingStatus not equals to UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.notEquals=" + UPDATED_BIDDING_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus in DEFAULT_BIDDING_STATUS or UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.in=" + DEFAULT_BIDDING_STATUS + "," + UPDATED_BIDDING_STATUS);

        // Get all the mBiddingNegotiationList where biddingStatus equals to UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.in=" + UPDATED_BIDDING_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus is not null
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.specified=true");

        // Get all the mBiddingNegotiationList where biddingStatus is null
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus contains DEFAULT_BIDDING_STATUS
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.contains=" + DEFAULT_BIDDING_STATUS);

        // Get all the mBiddingNegotiationList where biddingStatus contains UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.contains=" + UPDATED_BIDDING_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where biddingStatus does not contain DEFAULT_BIDDING_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("biddingStatus.doesNotContain=" + DEFAULT_BIDDING_STATUS);

        // Get all the mBiddingNegotiationList where biddingStatus does not contain UPDATED_BIDDING_STATUS
        defaultMBiddingNegotiationShouldBeFound("biddingStatus.doesNotContain=" + UPDATED_BIDDING_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.equals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingNegotiationList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.equals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus not equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.notEquals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingNegotiationList where evaluationStatus not equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.notEquals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus in DEFAULT_EVALUATION_STATUS or UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.in=" + DEFAULT_EVALUATION_STATUS + "," + UPDATED_EVALUATION_STATUS);

        // Get all the mBiddingNegotiationList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.in=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus is not null
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.specified=true");

        // Get all the mBiddingNegotiationList where evaluationStatus is null
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus contains DEFAULT_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.contains=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingNegotiationList where evaluationStatus contains UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.contains=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEvaluationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where evaluationStatus does not contain DEFAULT_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldNotBeFound("evaluationStatus.doesNotContain=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingNegotiationList where evaluationStatus does not contain UPDATED_EVALUATION_STATUS
        defaultMBiddingNegotiationShouldBeFound("evaluationStatus.doesNotContain=" + UPDATED_EVALUATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate equals to DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate equals to UPDATED_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate not equals to DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate not equals to UPDATED_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mBiddingNegotiationList where startDate equals to UPDATED_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate is not null
        defaultMBiddingNegotiationShouldBeFound("startDate.specified=true");

        // Get all the mBiddingNegotiationList where startDate is null
        defaultMBiddingNegotiationShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate is less than or equal to SMALLER_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate is less than DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate is less than UPDATED_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where startDate is greater than DEFAULT_START_DATE
        defaultMBiddingNegotiationShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mBiddingNegotiationList where startDate is greater than SMALLER_START_DATE
        defaultMBiddingNegotiationShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate equals to DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate equals to UPDATED_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate not equals to DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate not equals to UPDATED_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mBiddingNegotiationList where endDate equals to UPDATED_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate is not null
        defaultMBiddingNegotiationShouldBeFound("endDate.specified=true");

        // Get all the mBiddingNegotiationList where endDate is null
        defaultMBiddingNegotiationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate is greater than or equal to UPDATED_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate is less than or equal to DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate is less than or equal to SMALLER_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate is less than DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate is less than UPDATED_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where endDate is greater than DEFAULT_END_DATE
        defaultMBiddingNegotiationShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the mBiddingNegotiationList where endDate is greater than SMALLER_END_DATE
        defaultMBiddingNegotiationShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where uid equals to DEFAULT_UID
        defaultMBiddingNegotiationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where uid not equals to DEFAULT_UID
        defaultMBiddingNegotiationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationList where uid not equals to UPDATED_UID
        defaultMBiddingNegotiationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingNegotiationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingNegotiationList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where uid is not null
        defaultMBiddingNegotiationShouldBeFound("uid.specified=true");

        // Get all the mBiddingNegotiationList where uid is null
        defaultMBiddingNegotiationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where active equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationList where active not equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingNegotiationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingNegotiationList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        // Get all the mBiddingNegotiationList where active is not null
        defaultMBiddingNegotiationShouldBeFound("active.specified=true");

        // Get all the mBiddingNegotiationList where active is null
        defaultMBiddingNegotiationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingEvalIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvaluation biddingEval = mBiddingNegotiation.getBiddingEval();
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);
        Long biddingEvalId = biddingEval.getId();

        // Get all the mBiddingNegotiationList where biddingEval equals to biddingEvalId
        defaultMBiddingNegotiationShouldBeFound("biddingEvalId.equals=" + biddingEvalId);

        // Get all the mBiddingNegotiationList where biddingEval equals to biddingEvalId + 1
        defaultMBiddingNegotiationShouldNotBeFound("biddingEvalId.equals=" + (biddingEvalId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingNegotiation.getAdOrganization();
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingNegotiationList where adOrganization equals to adOrganizationId
        defaultMBiddingNegotiationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingNegotiationList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingNegotiationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mBiddingNegotiation.getBiddingSchedule();
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mBiddingNegotiationList where biddingSchedule equals to biddingScheduleId
        defaultMBiddingNegotiationShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mBiddingNegotiationList where biddingSchedule equals to biddingScheduleId + 1
        defaultMBiddingNegotiationShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingNegotiationShouldBeFound(String filter) throws Exception {
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].biddingStatus").value(hasItem(DEFAULT_BIDDING_STATUS)))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingNegotiationShouldNotBeFound(String filter) throws Exception {
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingNegotiation() throws Exception {
        // Get the mBiddingNegotiation
        restMBiddingNegotiationMockMvc.perform(get("/api/m-bidding-negotiations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingNegotiation() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        int databaseSizeBeforeUpdate = mBiddingNegotiationRepository.findAll().size();

        // Update the mBiddingNegotiation
        MBiddingNegotiation updatedMBiddingNegotiation = mBiddingNegotiationRepository.findById(mBiddingNegotiation.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingNegotiation are not directly saved in db
        em.detach(updatedMBiddingNegotiation);
        updatedMBiddingNegotiation
            .biddingStatus(UPDATED_BIDDING_STATUS)
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(updatedMBiddingNegotiation);

        restMBiddingNegotiationMockMvc.perform(put("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingNegotiation in the database
        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeUpdate);
        MBiddingNegotiation testMBiddingNegotiation = mBiddingNegotiationList.get(mBiddingNegotiationList.size() - 1);
        assertThat(testMBiddingNegotiation.getBiddingStatus()).isEqualTo(UPDATED_BIDDING_STATUS);
        assertThat(testMBiddingNegotiation.getEvaluationStatus()).isEqualTo(UPDATED_EVALUATION_STATUS);
        assertThat(testMBiddingNegotiation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMBiddingNegotiation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMBiddingNegotiation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingNegotiation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingNegotiation() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingNegotiationRepository.findAll().size();

        // Create the MBiddingNegotiation
        MBiddingNegotiationDTO mBiddingNegotiationDTO = mBiddingNegotiationMapper.toDto(mBiddingNegotiation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingNegotiationMockMvc.perform(put("/api/m-bidding-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiation in the database
        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingNegotiation() throws Exception {
        // Initialize the database
        mBiddingNegotiationRepository.saveAndFlush(mBiddingNegotiation);

        int databaseSizeBeforeDelete = mBiddingNegotiationRepository.findAll().size();

        // Delete the mBiddingNegotiation
        restMBiddingNegotiationMockMvc.perform(delete("/api/m-bidding-negotiations/{id}", mBiddingNegotiation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingNegotiation> mBiddingNegotiationList = mBiddingNegotiationRepository.findAll();
        assertThat(mBiddingNegotiationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
