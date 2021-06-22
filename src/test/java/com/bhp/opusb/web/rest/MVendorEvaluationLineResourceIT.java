package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorEvaluationLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVendorEvaluation;
import com.bhp.opusb.domain.CVendorEvaluationLine;
import com.bhp.opusb.repository.MVendorEvaluationLineRepository;
import com.bhp.opusb.service.MVendorEvaluationLineService;
import com.bhp.opusb.service.dto.MVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationLineMapper;
import com.bhp.opusb.service.dto.MVendorEvaluationLineCriteria;
import com.bhp.opusb.service.MVendorEvaluationLineQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVendorEvaluationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorEvaluationLineResourceIT {

    private static final BigDecimal DEFAULT_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SCORE = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorEvaluationLineRepository mVendorEvaluationLineRepository;

    @Autowired
    private MVendorEvaluationLineMapper mVendorEvaluationLineMapper;

    @Autowired
    private MVendorEvaluationLineService mVendorEvaluationLineService;

    @Autowired
    private MVendorEvaluationLineQueryService mVendorEvaluationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorEvaluationLineMockMvc;

    private MVendorEvaluationLine mVendorEvaluationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorEvaluationLine createEntity(EntityManager em) {
        MVendorEvaluationLine mVendorEvaluationLine = new MVendorEvaluationLine()
            .score(DEFAULT_SCORE)
            .remark(DEFAULT_REMARK)
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
        mVendorEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MVendorEvaluation mVendorEvaluation;
        if (TestUtil.findAll(em, MVendorEvaluation.class).isEmpty()) {
            mVendorEvaluation = MVendorEvaluationResourceIT.createEntity(em);
            em.persist(mVendorEvaluation);
            em.flush();
        } else {
            mVendorEvaluation = TestUtil.findAll(em, MVendorEvaluation.class).get(0);
        }
        mVendorEvaluationLine.setVendorEvaluation(mVendorEvaluation);
        // Add required entity
        CVendorEvaluationLine cVendorEvaluationLine;
        if (TestUtil.findAll(em, CVendorEvaluationLine.class).isEmpty()) {
            cVendorEvaluationLine = CVendorEvaluationLineResourceIT.createEntity(em);
            em.persist(cVendorEvaluationLine);
            em.flush();
        } else {
            cVendorEvaluationLine = TestUtil.findAll(em, CVendorEvaluationLine.class).get(0);
        }
        mVendorEvaluationLine.setEvaluationLine(cVendorEvaluationLine);
        return mVendorEvaluationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorEvaluationLine createUpdatedEntity(EntityManager em) {
        MVendorEvaluationLine mVendorEvaluationLine = new MVendorEvaluationLine()
            .score(UPDATED_SCORE)
            .remark(UPDATED_REMARK)
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
        mVendorEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MVendorEvaluation mVendorEvaluation;
        if (TestUtil.findAll(em, MVendorEvaluation.class).isEmpty()) {
            mVendorEvaluation = MVendorEvaluationResourceIT.createUpdatedEntity(em);
            em.persist(mVendorEvaluation);
            em.flush();
        } else {
            mVendorEvaluation = TestUtil.findAll(em, MVendorEvaluation.class).get(0);
        }
        mVendorEvaluationLine.setVendorEvaluation(mVendorEvaluation);
        // Add required entity
        CVendorEvaluationLine cVendorEvaluationLine;
        if (TestUtil.findAll(em, CVendorEvaluationLine.class).isEmpty()) {
            cVendorEvaluationLine = CVendorEvaluationLineResourceIT.createUpdatedEntity(em);
            em.persist(cVendorEvaluationLine);
            em.flush();
        } else {
            cVendorEvaluationLine = TestUtil.findAll(em, CVendorEvaluationLine.class).get(0);
        }
        mVendorEvaluationLine.setEvaluationLine(cVendorEvaluationLine);
        return mVendorEvaluationLine;
    }

    @BeforeEach
    public void initTest() {
        mVendorEvaluationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorEvaluationLine() throws Exception {
        int databaseSizeBeforeCreate = mVendorEvaluationLineRepository.findAll().size();

        // Create the MVendorEvaluationLine
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = mVendorEvaluationLineMapper.toDto(mVendorEvaluationLine);
        restMVendorEvaluationLineMockMvc.perform(post("/api/m-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorEvaluationLine in the database
        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorEvaluationLine testMVendorEvaluationLine = mVendorEvaluationLineList.get(mVendorEvaluationLineList.size() - 1);
        assertThat(testMVendorEvaluationLine.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMVendorEvaluationLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMVendorEvaluationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorEvaluationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorEvaluationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorEvaluationLineRepository.findAll().size();

        // Create the MVendorEvaluationLine with an existing ID
        mVendorEvaluationLine.setId(1L);
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = mVendorEvaluationLineMapper.toDto(mVendorEvaluationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorEvaluationLineMockMvc.perform(post("/api/m-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorEvaluationLine in the database
        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVendorEvaluationLineRepository.findAll().size();
        // set the field null
        mVendorEvaluationLine.setScore(null);

        // Create the MVendorEvaluationLine, which fails.
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = mVendorEvaluationLineMapper.toDto(mVendorEvaluationLine);

        restMVendorEvaluationLineMockMvc.perform(post("/api/m-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLines() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorEvaluationLine() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get the mVendorEvaluationLine
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines/{id}", mVendorEvaluationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorEvaluationLine.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorEvaluationLinesByIdFiltering() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        Long id = mVendorEvaluationLine.getId();

        defaultMVendorEvaluationLineShouldBeFound("id.equals=" + id);
        defaultMVendorEvaluationLineShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorEvaluationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorEvaluationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorEvaluationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorEvaluationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score equals to DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score equals to UPDATED_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score not equals to DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score not equals to UPDATED_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the mVendorEvaluationLineList where score equals to UPDATED_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score is not null
        defaultMVendorEvaluationLineShouldBeFound("score.specified=true");

        // Get all the mVendorEvaluationLineList where score is null
        defaultMVendorEvaluationLineShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score is greater than or equal to DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score is greater than or equal to UPDATED_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score is less than or equal to DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score is less than or equal to SMALLER_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score is less than DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score is less than UPDATED_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where score is greater than DEFAULT_SCORE
        defaultMVendorEvaluationLineShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the mVendorEvaluationLineList where score is greater than SMALLER_SCORE
        defaultMVendorEvaluationLineShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark equals to DEFAULT_REMARK
        defaultMVendorEvaluationLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mVendorEvaluationLineList where remark equals to UPDATED_REMARK
        defaultMVendorEvaluationLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark not equals to DEFAULT_REMARK
        defaultMVendorEvaluationLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mVendorEvaluationLineList where remark not equals to UPDATED_REMARK
        defaultMVendorEvaluationLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMVendorEvaluationLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mVendorEvaluationLineList where remark equals to UPDATED_REMARK
        defaultMVendorEvaluationLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark is not null
        defaultMVendorEvaluationLineShouldBeFound("remark.specified=true");

        // Get all the mVendorEvaluationLineList where remark is null
        defaultMVendorEvaluationLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark contains DEFAULT_REMARK
        defaultMVendorEvaluationLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mVendorEvaluationLineList where remark contains UPDATED_REMARK
        defaultMVendorEvaluationLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where remark does not contain DEFAULT_REMARK
        defaultMVendorEvaluationLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mVendorEvaluationLineList where remark does not contain UPDATED_REMARK
        defaultMVendorEvaluationLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where uid equals to DEFAULT_UID
        defaultMVendorEvaluationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorEvaluationLineList where uid equals to UPDATED_UID
        defaultMVendorEvaluationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where uid not equals to DEFAULT_UID
        defaultMVendorEvaluationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorEvaluationLineList where uid not equals to UPDATED_UID
        defaultMVendorEvaluationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorEvaluationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorEvaluationLineList where uid equals to UPDATED_UID
        defaultMVendorEvaluationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where uid is not null
        defaultMVendorEvaluationLineShouldBeFound("uid.specified=true");

        // Get all the mVendorEvaluationLineList where uid is null
        defaultMVendorEvaluationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where active equals to DEFAULT_ACTIVE
        defaultMVendorEvaluationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultMVendorEvaluationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where active not equals to DEFAULT_ACTIVE
        defaultMVendorEvaluationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorEvaluationLineList where active not equals to UPDATED_ACTIVE
        defaultMVendorEvaluationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorEvaluationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultMVendorEvaluationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        // Get all the mVendorEvaluationLineList where active is not null
        defaultMVendorEvaluationLineShouldBeFound("active.specified=true");

        // Get all the mVendorEvaluationLineList where active is null
        defaultMVendorEvaluationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorEvaluationLine.getAdOrganization();
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorEvaluationLineList where adOrganization equals to adOrganizationId
        defaultMVendorEvaluationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorEvaluationLineList where adOrganization equals to adOrganizationId + 1
        defaultMVendorEvaluationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByVendorEvaluationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorEvaluation vendorEvaluation = mVendorEvaluationLine.getVendorEvaluation();
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);
        Long vendorEvaluationId = vendorEvaluation.getId();

        // Get all the mVendorEvaluationLineList where vendorEvaluation equals to vendorEvaluationId
        defaultMVendorEvaluationLineShouldBeFound("vendorEvaluationId.equals=" + vendorEvaluationId);

        // Get all the mVendorEvaluationLineList where vendorEvaluation equals to vendorEvaluationId + 1
        defaultMVendorEvaluationLineShouldNotBeFound("vendorEvaluationId.equals=" + (vendorEvaluationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorEvaluationLinesByEvaluationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendorEvaluationLine evaluationLine = mVendorEvaluationLine.getEvaluationLine();
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);
        Long evaluationLineId = evaluationLine.getId();

        // Get all the mVendorEvaluationLineList where evaluationLine equals to evaluationLineId
        defaultMVendorEvaluationLineShouldBeFound("evaluationLineId.equals=" + evaluationLineId);

        // Get all the mVendorEvaluationLineList where evaluationLine equals to evaluationLineId + 1
        defaultMVendorEvaluationLineShouldNotBeFound("evaluationLineId.equals=" + (evaluationLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorEvaluationLineShouldBeFound(String filter) throws Exception {
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorEvaluationLineShouldNotBeFound(String filter) throws Exception {
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorEvaluationLine() throws Exception {
        // Get the mVendorEvaluationLine
        restMVendorEvaluationLineMockMvc.perform(get("/api/m-vendor-evaluation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorEvaluationLine() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        int databaseSizeBeforeUpdate = mVendorEvaluationLineRepository.findAll().size();

        // Update the mVendorEvaluationLine
        MVendorEvaluationLine updatedMVendorEvaluationLine = mVendorEvaluationLineRepository.findById(mVendorEvaluationLine.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorEvaluationLine are not directly saved in db
        em.detach(updatedMVendorEvaluationLine);
        updatedMVendorEvaluationLine
            .score(UPDATED_SCORE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = mVendorEvaluationLineMapper.toDto(updatedMVendorEvaluationLine);

        restMVendorEvaluationLineMockMvc.perform(put("/api/m-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorEvaluationLine in the database
        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
        MVendorEvaluationLine testMVendorEvaluationLine = mVendorEvaluationLineList.get(mVendorEvaluationLineList.size() - 1);
        assertThat(testMVendorEvaluationLine.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMVendorEvaluationLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMVendorEvaluationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorEvaluationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorEvaluationLine() throws Exception {
        int databaseSizeBeforeUpdate = mVendorEvaluationLineRepository.findAll().size();

        // Create the MVendorEvaluationLine
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = mVendorEvaluationLineMapper.toDto(mVendorEvaluationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorEvaluationLineMockMvc.perform(put("/api/m-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorEvaluationLine in the database
        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorEvaluationLine() throws Exception {
        // Initialize the database
        mVendorEvaluationLineRepository.saveAndFlush(mVendorEvaluationLine);

        int databaseSizeBeforeDelete = mVendorEvaluationLineRepository.findAll().size();

        // Delete the mVendorEvaluationLine
        restMVendorEvaluationLineMockMvc.perform(delete("/api/m-vendor-evaluation-lines/{id}", mVendorEvaluationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorEvaluationLine> mVendorEvaluationLineList = mVendorEvaluationLineRepository.findAll();
        assertThat(mVendorEvaluationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
