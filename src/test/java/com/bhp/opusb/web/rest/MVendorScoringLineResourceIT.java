package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorScoringLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.MVendorScoring;
import com.bhp.opusb.repository.MVendorScoringLineRepository;
import com.bhp.opusb.service.MVendorScoringLineService;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;
import com.bhp.opusb.service.mapper.MVendorScoringLineMapper;
import com.bhp.opusb.service.dto.MVendorScoringLineCriteria;
import com.bhp.opusb.service.MVendorScoringLineQueryService;

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
 * Integration tests for the {@link MVendorScoringLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorScoringLineResourceIT {

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorScoringLineRepository mVendorScoringLineRepository;

    @Autowired
    private MVendorScoringLineMapper mVendorScoringLineMapper;

    @Autowired
    private MVendorScoringLineService mVendorScoringLineService;

    @Autowired
    private MVendorScoringLineQueryService mVendorScoringLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorScoringLineMockMvc;

    private MVendorScoringLine mVendorScoringLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoringLine createEntity(EntityManager em) {
        MVendorScoringLine mVendorScoringLine = new MVendorScoringLine()
            .evaluation(DEFAULT_EVALUATION)
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
        mVendorScoringLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        mVendorScoringLine.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        MVendorScoring mVendorScoring;
        if (TestUtil.findAll(em, MVendorScoring.class).isEmpty()) {
            mVendorScoring = MVendorScoringResourceIT.createEntity(em);
            em.persist(mVendorScoring);
            em.flush();
        } else {
            mVendorScoring = TestUtil.findAll(em, MVendorScoring.class).get(0);
        }
        mVendorScoringLine.setVendorScoring(mVendorScoring);
        return mVendorScoringLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoringLine createUpdatedEntity(EntityManager em) {
        MVendorScoringLine mVendorScoringLine = new MVendorScoringLine()
            .evaluation(UPDATED_EVALUATION)
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
        mVendorScoringLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createUpdatedEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        mVendorScoringLine.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        MVendorScoring mVendorScoring;
        if (TestUtil.findAll(em, MVendorScoring.class).isEmpty()) {
            mVendorScoring = MVendorScoringResourceIT.createUpdatedEntity(em);
            em.persist(mVendorScoring);
            em.flush();
        } else {
            mVendorScoring = TestUtil.findAll(em, MVendorScoring.class).get(0);
        }
        mVendorScoringLine.setVendorScoring(mVendorScoring);
        return mVendorScoringLine;
    }

    @BeforeEach
    public void initTest() {
        mVendorScoringLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorScoringLine() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringLineRepository.findAll().size();

        // Create the MVendorScoringLine
        MVendorScoringLineDTO mVendorScoringLineDTO = mVendorScoringLineMapper.toDto(mVendorScoringLine);
        restMVendorScoringLineMockMvc.perform(post("/api/m-vendor-scoring-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorScoringLine in the database
        List<MVendorScoringLine> mVendorScoringLineList = mVendorScoringLineRepository.findAll();
        assertThat(mVendorScoringLineList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorScoringLine testMVendorScoringLine = mVendorScoringLineList.get(mVendorScoringLineList.size() - 1);
        assertThat(testMVendorScoringLine.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testMVendorScoringLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorScoringLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorScoringLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringLineRepository.findAll().size();

        // Create the MVendorScoringLine with an existing ID
        mVendorScoringLine.setId(1L);
        MVendorScoringLineDTO mVendorScoringLineDTO = mVendorScoringLineMapper.toDto(mVendorScoringLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorScoringLineMockMvc.perform(post("/api/m-vendor-scoring-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoringLine in the database
        List<MVendorScoringLine> mVendorScoringLineList = mVendorScoringLineRepository.findAll();
        assertThat(mVendorScoringLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringLines() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoringLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorScoringLine() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get the mVendorScoringLine
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines/{id}", mVendorScoringLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorScoringLine.getId().intValue()))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorScoringLinesByIdFiltering() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        Long id = mVendorScoringLine.getId();

        defaultMVendorScoringLineShouldBeFound("id.equals=" + id);
        defaultMVendorScoringLineShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorScoringLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorScoringLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorScoringLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorScoringLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation equals to DEFAULT_EVALUATION
        defaultMVendorScoringLineShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mVendorScoringLineList where evaluation equals to UPDATED_EVALUATION
        defaultMVendorScoringLineShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation not equals to DEFAULT_EVALUATION
        defaultMVendorScoringLineShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mVendorScoringLineList where evaluation not equals to UPDATED_EVALUATION
        defaultMVendorScoringLineShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMVendorScoringLineShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mVendorScoringLineList where evaluation equals to UPDATED_EVALUATION
        defaultMVendorScoringLineShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation is not null
        defaultMVendorScoringLineShouldBeFound("evaluation.specified=true");

        // Get all the mVendorScoringLineList where evaluation is null
        defaultMVendorScoringLineShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation contains DEFAULT_EVALUATION
        defaultMVendorScoringLineShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mVendorScoringLineList where evaluation contains UPDATED_EVALUATION
        defaultMVendorScoringLineShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where evaluation does not contain DEFAULT_EVALUATION
        defaultMVendorScoringLineShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mVendorScoringLineList where evaluation does not contain UPDATED_EVALUATION
        defaultMVendorScoringLineShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where uid equals to DEFAULT_UID
        defaultMVendorScoringLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorScoringLineList where uid equals to UPDATED_UID
        defaultMVendorScoringLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where uid not equals to DEFAULT_UID
        defaultMVendorScoringLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorScoringLineList where uid not equals to UPDATED_UID
        defaultMVendorScoringLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorScoringLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorScoringLineList where uid equals to UPDATED_UID
        defaultMVendorScoringLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where uid is not null
        defaultMVendorScoringLineShouldBeFound("uid.specified=true");

        // Get all the mVendorScoringLineList where uid is null
        defaultMVendorScoringLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where active equals to DEFAULT_ACTIVE
        defaultMVendorScoringLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringLineList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where active not equals to DEFAULT_ACTIVE
        defaultMVendorScoringLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringLineList where active not equals to UPDATED_ACTIVE
        defaultMVendorScoringLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorScoringLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorScoringLineList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        // Get all the mVendorScoringLineList where active is not null
        defaultMVendorScoringLineShouldBeFound("active.specified=true");

        // Get all the mVendorScoringLineList where active is null
        defaultMVendorScoringLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorScoringLine.getAdOrganization();
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorScoringLineList where adOrganization equals to adOrganizationId
        defaultMVendorScoringLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorScoringLineList where adOrganization equals to adOrganizationId + 1
        defaultMVendorScoringLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringLinesByEvaluationMethodLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEvaluationMethodLine evaluationMethodLine = mVendorScoringLine.getEvaluationMethodLine();
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);
        Long evaluationMethodLineId = evaluationMethodLine.getId();

        // Get all the mVendorScoringLineList where evaluationMethodLine equals to evaluationMethodLineId
        defaultMVendorScoringLineShouldBeFound("evaluationMethodLineId.equals=" + evaluationMethodLineId);

        // Get all the mVendorScoringLineList where evaluationMethodLine equals to evaluationMethodLineId + 1
        defaultMVendorScoringLineShouldNotBeFound("evaluationMethodLineId.equals=" + (evaluationMethodLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringLinesByVendorScoringIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorScoring vendorScoring = mVendorScoringLine.getVendorScoring();
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);
        Long vendorScoringId = vendorScoring.getId();

        // Get all the mVendorScoringLineList where vendorScoring equals to vendorScoringId
        defaultMVendorScoringLineShouldBeFound("vendorScoringId.equals=" + vendorScoringId);

        // Get all the mVendorScoringLineList where vendorScoring equals to vendorScoringId + 1
        defaultMVendorScoringLineShouldNotBeFound("vendorScoringId.equals=" + (vendorScoringId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorScoringLineShouldBeFound(String filter) throws Exception {
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoringLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorScoringLineShouldNotBeFound(String filter) throws Exception {
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorScoringLine() throws Exception {
        // Get the mVendorScoringLine
        restMVendorScoringLineMockMvc.perform(get("/api/m-vendor-scoring-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorScoringLine() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        int databaseSizeBeforeUpdate = mVendorScoringLineRepository.findAll().size();

        // Update the mVendorScoringLine
        MVendorScoringLine updatedMVendorScoringLine = mVendorScoringLineRepository.findById(mVendorScoringLine.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorScoringLine are not directly saved in db
        em.detach(updatedMVendorScoringLine);
        updatedMVendorScoringLine
            .evaluation(UPDATED_EVALUATION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorScoringLineDTO mVendorScoringLineDTO = mVendorScoringLineMapper.toDto(updatedMVendorScoringLine);

        restMVendorScoringLineMockMvc.perform(put("/api/m-vendor-scoring-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringLineDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorScoringLine in the database
        List<MVendorScoringLine> mVendorScoringLineList = mVendorScoringLineRepository.findAll();
        assertThat(mVendorScoringLineList).hasSize(databaseSizeBeforeUpdate);
        MVendorScoringLine testMVendorScoringLine = mVendorScoringLineList.get(mVendorScoringLineList.size() - 1);
        assertThat(testMVendorScoringLine.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testMVendorScoringLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorScoringLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorScoringLine() throws Exception {
        int databaseSizeBeforeUpdate = mVendorScoringLineRepository.findAll().size();

        // Create the MVendorScoringLine
        MVendorScoringLineDTO mVendorScoringLineDTO = mVendorScoringLineMapper.toDto(mVendorScoringLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorScoringLineMockMvc.perform(put("/api/m-vendor-scoring-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoringLine in the database
        List<MVendorScoringLine> mVendorScoringLineList = mVendorScoringLineRepository.findAll();
        assertThat(mVendorScoringLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorScoringLine() throws Exception {
        // Initialize the database
        mVendorScoringLineRepository.saveAndFlush(mVendorScoringLine);

        int databaseSizeBeforeDelete = mVendorScoringLineRepository.findAll().size();

        // Delete the mVendorScoringLine
        restMVendorScoringLineMockMvc.perform(delete("/api/m-vendor-scoring-lines/{id}", mVendorScoringLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorScoringLine> mVendorScoringLineList = mVendorScoringLineRepository.findAll();
        assertThat(mVendorScoringLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
