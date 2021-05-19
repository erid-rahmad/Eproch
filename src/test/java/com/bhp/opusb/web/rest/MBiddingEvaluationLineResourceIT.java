package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvaluationLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingEvaluation;
import com.bhp.opusb.repository.MBiddingEvaluationLineRepository;
import com.bhp.opusb.service.MBiddingEvaluationLineService;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationLineMapper;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineCriteria;
import com.bhp.opusb.service.MBiddingEvaluationLineQueryService;

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
 * Integration tests for the {@link MBiddingEvaluationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvaluationLineResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingEvaluationLineRepository mBiddingEvaluationLineRepository;

    @Autowired
    private MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper;

    @Autowired
    private MBiddingEvaluationLineService mBiddingEvaluationLineService;

    @Autowired
    private MBiddingEvaluationLineQueryService mBiddingEvaluationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvaluationLineMockMvc;

    private MBiddingEvaluationLine mBiddingEvaluationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluationLine createEntity(EntityManager em) {
        MBiddingEvaluationLine mBiddingEvaluationLine = new MBiddingEvaluationLine()
            .status(DEFAULT_STATUS)
            .evaluationStatus(DEFAULT_EVALUATION_STATUS)
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
        mBiddingEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingEvaluation mBiddingEvaluation;
        if (TestUtil.findAll(em, MBiddingEvaluation.class).isEmpty()) {
            mBiddingEvaluation = MBiddingEvaluationResourceIT.createEntity(em);
            em.persist(mBiddingEvaluation);
            em.flush();
        } else {
            mBiddingEvaluation = TestUtil.findAll(em, MBiddingEvaluation.class).get(0);
        }
        mBiddingEvaluationLine.setBiddingEvaluation(mBiddingEvaluation);
        return mBiddingEvaluationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluationLine createUpdatedEntity(EntityManager em) {
        MBiddingEvaluationLine mBiddingEvaluationLine = new MBiddingEvaluationLine()
            .status(UPDATED_STATUS)
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
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
        mBiddingEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingEvaluation mBiddingEvaluation;
        if (TestUtil.findAll(em, MBiddingEvaluation.class).isEmpty()) {
            mBiddingEvaluation = MBiddingEvaluationResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvaluation);
            em.flush();
        } else {
            mBiddingEvaluation = TestUtil.findAll(em, MBiddingEvaluation.class).get(0);
        }
        mBiddingEvaluationLine.setBiddingEvaluation(mBiddingEvaluation);
        return mBiddingEvaluationLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvaluationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluationLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationLineRepository.findAll().size();

        // Create the MBiddingEvaluationLine
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO = mBiddingEvaluationLineMapper.toDto(mBiddingEvaluationLine);
        restMBiddingEvaluationLineMockMvc.perform(post("/api/m-bidding-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvaluationLine in the database
        List<MBiddingEvaluationLine> mBiddingEvaluationLineList = mBiddingEvaluationLineRepository.findAll();
        assertThat(mBiddingEvaluationLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvaluationLine testMBiddingEvaluationLine = mBiddingEvaluationLineList.get(mBiddingEvaluationLineList.size() - 1);
        assertThat(testMBiddingEvaluationLine.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMBiddingEvaluationLine.getEvaluationStatus()).isEqualTo(DEFAULT_EVALUATION_STATUS);
        assertThat(testMBiddingEvaluationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvaluationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationLineRepository.findAll().size();

        // Create the MBiddingEvaluationLine with an existing ID
        mBiddingEvaluationLine.setId(1L);
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO = mBiddingEvaluationLineMapper.toDto(mBiddingEvaluationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvaluationLineMockMvc.perform(post("/api/m-bidding-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluationLine in the database
        List<MBiddingEvaluationLine> mBiddingEvaluationLineList = mBiddingEvaluationLineRepository.findAll();
        assertThat(mBiddingEvaluationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationLines() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvaluationLine() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get the mBiddingEvaluationLine
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines/{id}", mBiddingEvaluationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvaluationLine.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.evaluationStatus").value(DEFAULT_EVALUATION_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingEvaluationLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        Long id = mBiddingEvaluationLine.getId();

        defaultMBiddingEvaluationLineShouldBeFound("id.equals=" + id);
        defaultMBiddingEvaluationLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvaluationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvaluationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvaluationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvaluationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status equals to DEFAULT_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationLineList where status equals to UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status not equals to DEFAULT_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationLineList where status not equals to UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mBiddingEvaluationLineList where status equals to UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status is not null
        defaultMBiddingEvaluationLineShouldBeFound("status.specified=true");

        // Get all the mBiddingEvaluationLineList where status is null
        defaultMBiddingEvaluationLineShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status contains DEFAULT_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationLineList where status contains UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where status does not contain DEFAULT_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationLineList where status does not contain UPDATED_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.equals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationLineList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.equals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus not equals to DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.notEquals=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationLineList where evaluationStatus not equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.notEquals=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus in DEFAULT_EVALUATION_STATUS or UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.in=" + DEFAULT_EVALUATION_STATUS + "," + UPDATED_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationLineList where evaluationStatus equals to UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.in=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus is not null
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.specified=true");

        // Get all the mBiddingEvaluationLineList where evaluationStatus is null
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus contains DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.contains=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationLineList where evaluationStatus contains UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.contains=" + UPDATED_EVALUATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByEvaluationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where evaluationStatus does not contain DEFAULT_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldNotBeFound("evaluationStatus.doesNotContain=" + DEFAULT_EVALUATION_STATUS);

        // Get all the mBiddingEvaluationLineList where evaluationStatus does not contain UPDATED_EVALUATION_STATUS
        defaultMBiddingEvaluationLineShouldBeFound("evaluationStatus.doesNotContain=" + UPDATED_EVALUATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where uid equals to DEFAULT_UID
        defaultMBiddingEvaluationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationLineList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where uid not equals to DEFAULT_UID
        defaultMBiddingEvaluationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationLineList where uid not equals to UPDATED_UID
        defaultMBiddingEvaluationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvaluationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvaluationLineList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where uid is not null
        defaultMBiddingEvaluationLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvaluationLineList where uid is null
        defaultMBiddingEvaluationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvaluationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        // Get all the mBiddingEvaluationLineList where active is not null
        defaultMBiddingEvaluationLineShouldBeFound("active.specified=true");

        // Get all the mBiddingEvaluationLineList where active is null
        defaultMBiddingEvaluationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvaluationLine.getAdOrganization();
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvaluationLineList where adOrganization equals to adOrganizationId
        defaultMBiddingEvaluationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvaluationLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvaluationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationLinesByBiddingEvaluationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvaluation biddingEvaluation = mBiddingEvaluationLine.getBiddingEvaluation();
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);
        Long biddingEvaluationId = biddingEvaluation.getId();

        // Get all the mBiddingEvaluationLineList where biddingEvaluation equals to biddingEvaluationId
        defaultMBiddingEvaluationLineShouldBeFound("biddingEvaluationId.equals=" + biddingEvaluationId);

        // Get all the mBiddingEvaluationLineList where biddingEvaluation equals to biddingEvaluationId + 1
        defaultMBiddingEvaluationLineShouldNotBeFound("biddingEvaluationId.equals=" + (biddingEvaluationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvaluationLineShouldBeFound(String filter) throws Exception {
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].evaluationStatus").value(hasItem(DEFAULT_EVALUATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvaluationLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvaluationLine() throws Exception {
        // Get the mBiddingEvaluationLine
        restMBiddingEvaluationLineMockMvc.perform(get("/api/m-bidding-evaluation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvaluationLine() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        int databaseSizeBeforeUpdate = mBiddingEvaluationLineRepository.findAll().size();

        // Update the mBiddingEvaluationLine
        MBiddingEvaluationLine updatedMBiddingEvaluationLine = mBiddingEvaluationLineRepository.findById(mBiddingEvaluationLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvaluationLine are not directly saved in db
        em.detach(updatedMBiddingEvaluationLine);
        updatedMBiddingEvaluationLine
            .status(UPDATED_STATUS)
            .evaluationStatus(UPDATED_EVALUATION_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO = mBiddingEvaluationLineMapper.toDto(updatedMBiddingEvaluationLine);

        restMBiddingEvaluationLineMockMvc.perform(put("/api/m-bidding-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvaluationLine in the database
        List<MBiddingEvaluationLine> mBiddingEvaluationLineList = mBiddingEvaluationLineRepository.findAll();
        assertThat(mBiddingEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvaluationLine testMBiddingEvaluationLine = mBiddingEvaluationLineList.get(mBiddingEvaluationLineList.size() - 1);
        assertThat(testMBiddingEvaluationLine.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMBiddingEvaluationLine.getEvaluationStatus()).isEqualTo(UPDATED_EVALUATION_STATUS);
        assertThat(testMBiddingEvaluationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvaluationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvaluationLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvaluationLineRepository.findAll().size();

        // Create the MBiddingEvaluationLine
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO = mBiddingEvaluationLineMapper.toDto(mBiddingEvaluationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvaluationLineMockMvc.perform(put("/api/m-bidding-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluationLine in the database
        List<MBiddingEvaluationLine> mBiddingEvaluationLineList = mBiddingEvaluationLineRepository.findAll();
        assertThat(mBiddingEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvaluationLine() throws Exception {
        // Initialize the database
        mBiddingEvaluationLineRepository.saveAndFlush(mBiddingEvaluationLine);

        int databaseSizeBeforeDelete = mBiddingEvaluationLineRepository.findAll().size();

        // Delete the mBiddingEvaluationLine
        restMBiddingEvaluationLineMockMvc.perform(delete("/api/m-bidding-evaluation-lines/{id}", mBiddingEvaluationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvaluationLine> mBiddingEvaluationLineList = mBiddingEvaluationLineRepository.findAll();
        assertThat(mBiddingEvaluationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
