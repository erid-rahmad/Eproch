package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvalResultLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.repository.MBiddingEvalResultLineRepository;
import com.bhp.opusb.service.MBiddingEvalResultLineService;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultLineMapper;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineCriteria;
import com.bhp.opusb.service.MBiddingEvalResultLineQueryService;

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
 * Integration tests for the {@link MBiddingEvalResultLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvalResultLineResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingEvalResultLineRepository mBiddingEvalResultLineRepository;

    @Autowired
    private MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper;

    @Autowired
    private MBiddingEvalResultLineService mBiddingEvalResultLineService;

    @Autowired
    private MBiddingEvalResultLineQueryService mBiddingEvalResultLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvalResultLineMockMvc;

    private MBiddingEvalResultLine mBiddingEvalResultLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalResultLine createEntity(EntityManager em) {
        MBiddingEvalResultLine mBiddingEvalResultLine = new MBiddingEvalResultLine()
            .status(DEFAULT_STATUS)
            .score(DEFAULT_SCORE)
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
        mBiddingEvalResultLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        mBiddingEvalResultLine.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingEvalResultLine.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingEvalResultLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalResultLine createUpdatedEntity(EntityManager em) {
        MBiddingEvalResultLine mBiddingEvalResultLine = new MBiddingEvalResultLine()
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
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
        mBiddingEvalResultLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createUpdatedEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        mBiddingEvalResultLine.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingEvalResultLine.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingEvalResultLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvalResultLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvalResultLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalResultLineRepository.findAll().size();

        // Create the MBiddingEvalResultLine
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO = mBiddingEvalResultLineMapper.toDto(mBiddingEvalResultLine);
        restMBiddingEvalResultLineMockMvc.perform(post("/api/m-bidding-eval-result-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvalResultLine in the database
        List<MBiddingEvalResultLine> mBiddingEvalResultLineList = mBiddingEvalResultLineRepository.findAll();
        assertThat(mBiddingEvalResultLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvalResultLine testMBiddingEvalResultLine = mBiddingEvalResultLineList.get(mBiddingEvalResultLineList.size() - 1);
        assertThat(testMBiddingEvalResultLine.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMBiddingEvalResultLine.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMBiddingEvalResultLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvalResultLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingEvalResultLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalResultLineRepository.findAll().size();

        // Create the MBiddingEvalResultLine with an existing ID
        mBiddingEvalResultLine.setId(1L);
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO = mBiddingEvalResultLineMapper.toDto(mBiddingEvalResultLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvalResultLineMockMvc.perform(post("/api/m-bidding-eval-result-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalResultLine in the database
        List<MBiddingEvalResultLine> mBiddingEvalResultLineList = mBiddingEvalResultLineRepository.findAll();
        assertThat(mBiddingEvalResultLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLines() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalResultLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvalResultLine() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get the mBiddingEvalResultLine
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines/{id}", mBiddingEvalResultLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvalResultLine.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingEvalResultLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        Long id = mBiddingEvalResultLine.getId();

        defaultMBiddingEvalResultLineShouldBeFound("id.equals=" + id);
        defaultMBiddingEvalResultLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvalResultLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvalResultLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvalResultLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvalResultLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status equals to DEFAULT_STATUS
        defaultMBiddingEvalResultLineShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultLineList where status equals to UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status not equals to DEFAULT_STATUS
        defaultMBiddingEvalResultLineShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultLineList where status not equals to UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mBiddingEvalResultLineList where status equals to UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status is not null
        defaultMBiddingEvalResultLineShouldBeFound("status.specified=true");

        // Get all the mBiddingEvalResultLineList where status is null
        defaultMBiddingEvalResultLineShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status contains DEFAULT_STATUS
        defaultMBiddingEvalResultLineShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultLineList where status contains UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where status does not contain DEFAULT_STATUS
        defaultMBiddingEvalResultLineShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mBiddingEvalResultLineList where status does not contain UPDATED_STATUS
        defaultMBiddingEvalResultLineShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score equals to DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score equals to UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score not equals to DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score not equals to UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the mBiddingEvalResultLineList where score equals to UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score is not null
        defaultMBiddingEvalResultLineShouldBeFound("score.specified=true");

        // Get all the mBiddingEvalResultLineList where score is null
        defaultMBiddingEvalResultLineShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score is greater than or equal to DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score is greater than or equal to UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score is less than or equal to DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score is less than or equal to SMALLER_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score is less than DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score is less than UPDATED_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where score is greater than DEFAULT_SCORE
        defaultMBiddingEvalResultLineShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the mBiddingEvalResultLineList where score is greater than SMALLER_SCORE
        defaultMBiddingEvalResultLineShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where uid equals to DEFAULT_UID
        defaultMBiddingEvalResultLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvalResultLineList where uid equals to UPDATED_UID
        defaultMBiddingEvalResultLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where uid not equals to DEFAULT_UID
        defaultMBiddingEvalResultLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvalResultLineList where uid not equals to UPDATED_UID
        defaultMBiddingEvalResultLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvalResultLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvalResultLineList where uid equals to UPDATED_UID
        defaultMBiddingEvalResultLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where uid is not null
        defaultMBiddingEvalResultLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvalResultLineList where uid is null
        defaultMBiddingEvalResultLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvalResultLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalResultLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvalResultLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalResultLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvalResultLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvalResultLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalResultLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        // Get all the mBiddingEvalResultLineList where active is not null
        defaultMBiddingEvalResultLineShouldBeFound("active.specified=true");

        // Get all the mBiddingEvalResultLineList where active is null
        defaultMBiddingEvalResultLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvalResultLine.getAdOrganization();
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvalResultLineList where adOrganization equals to adOrganizationId
        defaultMBiddingEvalResultLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvalResultLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvalResultLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByEvaluationMethodLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEvaluationMethodLine evaluationMethodLine = mBiddingEvalResultLine.getEvaluationMethodLine();
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);
        Long evaluationMethodLineId = evaluationMethodLine.getId();

        // Get all the mBiddingEvalResultLineList where evaluationMethodLine equals to evaluationMethodLineId
        defaultMBiddingEvalResultLineShouldBeFound("evaluationMethodLineId.equals=" + evaluationMethodLineId);

        // Get all the mBiddingEvalResultLineList where evaluationMethodLine equals to evaluationMethodLineId + 1
        defaultMBiddingEvalResultLineShouldNotBeFound("evaluationMethodLineId.equals=" + (evaluationMethodLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalResultLinesByBiddingEvalResultIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvalResult biddingEvalResult = mBiddingEvalResultLine.getBiddingEvalResult();
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);
        Long biddingEvalResultId = biddingEvalResult.getId();

        // Get all the mBiddingEvalResultLineList where biddingEvalResult equals to biddingEvalResultId
        defaultMBiddingEvalResultLineShouldBeFound("biddingEvalResultId.equals=" + biddingEvalResultId);

        // Get all the mBiddingEvalResultLineList where biddingEvalResult equals to biddingEvalResultId + 1
        defaultMBiddingEvalResultLineShouldNotBeFound("biddingEvalResultId.equals=" + (biddingEvalResultId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvalResultLineShouldBeFound(String filter) throws Exception {
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalResultLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvalResultLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvalResultLine() throws Exception {
        // Get the mBiddingEvalResultLine
        restMBiddingEvalResultLineMockMvc.perform(get("/api/m-bidding-eval-result-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvalResultLine() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        int databaseSizeBeforeUpdate = mBiddingEvalResultLineRepository.findAll().size();

        // Update the mBiddingEvalResultLine
        MBiddingEvalResultLine updatedMBiddingEvalResultLine = mBiddingEvalResultLineRepository.findById(mBiddingEvalResultLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvalResultLine are not directly saved in db
        em.detach(updatedMBiddingEvalResultLine);
        updatedMBiddingEvalResultLine
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO = mBiddingEvalResultLineMapper.toDto(updatedMBiddingEvalResultLine);

        restMBiddingEvalResultLineMockMvc.perform(put("/api/m-bidding-eval-result-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvalResultLine in the database
        List<MBiddingEvalResultLine> mBiddingEvalResultLineList = mBiddingEvalResultLineRepository.findAll();
        assertThat(mBiddingEvalResultLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvalResultLine testMBiddingEvalResultLine = mBiddingEvalResultLineList.get(mBiddingEvalResultLineList.size() - 1);
        assertThat(testMBiddingEvalResultLine.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMBiddingEvalResultLine.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMBiddingEvalResultLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvalResultLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvalResultLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvalResultLineRepository.findAll().size();

        // Create the MBiddingEvalResultLine
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO = mBiddingEvalResultLineMapper.toDto(mBiddingEvalResultLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvalResultLineMockMvc.perform(put("/api/m-bidding-eval-result-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalResultLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalResultLine in the database
        List<MBiddingEvalResultLine> mBiddingEvalResultLineList = mBiddingEvalResultLineRepository.findAll();
        assertThat(mBiddingEvalResultLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvalResultLine() throws Exception {
        // Initialize the database
        mBiddingEvalResultLineRepository.saveAndFlush(mBiddingEvalResultLine);

        int databaseSizeBeforeDelete = mBiddingEvalResultLineRepository.findAll().size();

        // Delete the mBiddingEvalResultLine
        restMBiddingEvalResultLineMockMvc.perform(delete("/api/m-bidding-eval-result-lines/{id}", mBiddingEvalResultLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvalResultLine> mBiddingEvalResultLineList = mBiddingEvalResultLineRepository.findAll();
        assertThat(mBiddingEvalResultLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
