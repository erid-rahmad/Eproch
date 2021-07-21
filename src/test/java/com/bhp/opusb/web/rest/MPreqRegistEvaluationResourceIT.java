package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPreqRegistEvaluation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.repository.MPreqRegistEvaluationRepository;
import com.bhp.opusb.service.MPreqRegistEvaluationService;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationDTO;
import com.bhp.opusb.service.mapper.MPreqRegistEvaluationMapper;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationCriteria;
import com.bhp.opusb.service.MPreqRegistEvaluationQueryService;

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
 * Integration tests for the {@link MPreqRegistEvaluationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPreqRegistEvaluationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    @Autowired
    private MPreqRegistEvaluationRepository mPreqRegistEvaluationRepository;

    @Autowired
    private MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper;

    @Autowired
    private MPreqRegistEvaluationService mPreqRegistEvaluationService;

    @Autowired
    private MPreqRegistEvaluationQueryService mPreqRegistEvaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPreqRegistEvaluationMockMvc;

    private MPreqRegistEvaluation mPreqRegistEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreqRegistEvaluation createEntity(EntityManager em) {
        MPreqRegistEvaluation mPreqRegistEvaluation = new MPreqRegistEvaluation()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .evaluation(DEFAULT_EVALUATION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreqRegistEvaluation.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPreqRegistEvaluation.setVendor(cVendor);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPreqRegistEvaluation.setPrequalification(mPrequalificationInformation);
        return mPreqRegistEvaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreqRegistEvaluation createUpdatedEntity(EntityManager em) {
        MPreqRegistEvaluation mPreqRegistEvaluation = new MPreqRegistEvaluation()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .evaluation(UPDATED_EVALUATION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreqRegistEvaluation.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPreqRegistEvaluation.setVendor(cVendor);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPreqRegistEvaluation.setPrequalification(mPrequalificationInformation);
        return mPreqRegistEvaluation;
    }

    @BeforeEach
    public void initTest() {
        mPreqRegistEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPreqRegistEvaluation() throws Exception {
        int databaseSizeBeforeCreate = mPreqRegistEvaluationRepository.findAll().size();

        // Create the MPreqRegistEvaluation
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO = mPreqRegistEvaluationMapper.toDto(mPreqRegistEvaluation);
        restMPreqRegistEvaluationMockMvc.perform(post("/api/m-preq-regist-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the MPreqRegistEvaluation in the database
        List<MPreqRegistEvaluation> mPreqRegistEvaluationList = mPreqRegistEvaluationRepository.findAll();
        assertThat(mPreqRegistEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        MPreqRegistEvaluation testMPreqRegistEvaluation = mPreqRegistEvaluationList.get(mPreqRegistEvaluationList.size() - 1);
        assertThat(testMPreqRegistEvaluation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPreqRegistEvaluation.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMPreqRegistEvaluation.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
    }

    @Test
    @Transactional
    public void createMPreqRegistEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPreqRegistEvaluationRepository.findAll().size();

        // Create the MPreqRegistEvaluation with an existing ID
        mPreqRegistEvaluation.setId(1L);
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO = mPreqRegistEvaluationMapper.toDto(mPreqRegistEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPreqRegistEvaluationMockMvc.perform(post("/api/m-preq-regist-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreqRegistEvaluation in the database
        List<MPreqRegistEvaluation> mPreqRegistEvaluationList = mPreqRegistEvaluationRepository.findAll();
        assertThat(mPreqRegistEvaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPreqRegistEvaluations() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreqRegistEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)));
    }
    
    @Test
    @Transactional
    public void getMPreqRegistEvaluation() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get the mPreqRegistEvaluation
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations/{id}", mPreqRegistEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPreqRegistEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION));
    }


    @Test
    @Transactional
    public void getMPreqRegistEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        Long id = mPreqRegistEvaluation.getId();

        defaultMPreqRegistEvaluationShouldBeFound("id.equals=" + id);
        defaultMPreqRegistEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultMPreqRegistEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPreqRegistEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultMPreqRegistEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPreqRegistEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where uid equals to DEFAULT_UID
        defaultMPreqRegistEvaluationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPreqRegistEvaluationList where uid equals to UPDATED_UID
        defaultMPreqRegistEvaluationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where uid not equals to DEFAULT_UID
        defaultMPreqRegistEvaluationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPreqRegistEvaluationList where uid not equals to UPDATED_UID
        defaultMPreqRegistEvaluationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPreqRegistEvaluationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPreqRegistEvaluationList where uid equals to UPDATED_UID
        defaultMPreqRegistEvaluationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where uid is not null
        defaultMPreqRegistEvaluationShouldBeFound("uid.specified=true");

        // Get all the mPreqRegistEvaluationList where uid is null
        defaultMPreqRegistEvaluationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where active equals to DEFAULT_ACTIVE
        defaultMPreqRegistEvaluationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPreqRegistEvaluationList where active equals to UPDATED_ACTIVE
        defaultMPreqRegistEvaluationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where active not equals to DEFAULT_ACTIVE
        defaultMPreqRegistEvaluationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPreqRegistEvaluationList where active not equals to UPDATED_ACTIVE
        defaultMPreqRegistEvaluationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPreqRegistEvaluationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPreqRegistEvaluationList where active equals to UPDATED_ACTIVE
        defaultMPreqRegistEvaluationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where active is not null
        defaultMPreqRegistEvaluationShouldBeFound("active.specified=true");

        // Get all the mPreqRegistEvaluationList where active is null
        defaultMPreqRegistEvaluationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation equals to DEFAULT_EVALUATION
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mPreqRegistEvaluationList where evaluation equals to UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation not equals to DEFAULT_EVALUATION
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mPreqRegistEvaluationList where evaluation not equals to UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mPreqRegistEvaluationList where evaluation equals to UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation is not null
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.specified=true");

        // Get all the mPreqRegistEvaluationList where evaluation is null
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation contains DEFAULT_EVALUATION
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mPreqRegistEvaluationList where evaluation contains UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        // Get all the mPreqRegistEvaluationList where evaluation does not contain DEFAULT_EVALUATION
        defaultMPreqRegistEvaluationShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mPreqRegistEvaluationList where evaluation does not contain UPDATED_EVALUATION
        defaultMPreqRegistEvaluationShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPreqRegistEvaluation.getAdOrganization();
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPreqRegistEvaluationList where adOrganization equals to adOrganizationId
        defaultMPreqRegistEvaluationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPreqRegistEvaluationList where adOrganization equals to adOrganizationId + 1
        defaultMPreqRegistEvaluationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPreqRegistEvaluation.getVendor();
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);
        Long vendorId = vendor.getId();

        // Get all the mPreqRegistEvaluationList where vendor equals to vendorId
        defaultMPreqRegistEvaluationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPreqRegistEvaluationList where vendor equals to vendorId + 1
        defaultMPreqRegistEvaluationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreqRegistEvaluationsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPreqRegistEvaluation.getPrequalification();
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);
        Long prequalificationId = prequalification.getId();

        // Get all the mPreqRegistEvaluationList where prequalification equals to prequalificationId
        defaultMPreqRegistEvaluationShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPreqRegistEvaluationList where prequalification equals to prequalificationId + 1
        defaultMPreqRegistEvaluationShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPreqRegistEvaluationShouldBeFound(String filter) throws Exception {
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreqRegistEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)));

        // Check, that the count call also returns 1
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPreqRegistEvaluationShouldNotBeFound(String filter) throws Exception {
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPreqRegistEvaluation() throws Exception {
        // Get the mPreqRegistEvaluation
        restMPreqRegistEvaluationMockMvc.perform(get("/api/m-preq-regist-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPreqRegistEvaluation() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        int databaseSizeBeforeUpdate = mPreqRegistEvaluationRepository.findAll().size();

        // Update the mPreqRegistEvaluation
        MPreqRegistEvaluation updatedMPreqRegistEvaluation = mPreqRegistEvaluationRepository.findById(mPreqRegistEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedMPreqRegistEvaluation are not directly saved in db
        em.detach(updatedMPreqRegistEvaluation);
        updatedMPreqRegistEvaluation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .evaluation(UPDATED_EVALUATION);
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO = mPreqRegistEvaluationMapper.toDto(updatedMPreqRegistEvaluation);

        restMPreqRegistEvaluationMockMvc.perform(put("/api/m-preq-regist-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the MPreqRegistEvaluation in the database
        List<MPreqRegistEvaluation> mPreqRegistEvaluationList = mPreqRegistEvaluationRepository.findAll();
        assertThat(mPreqRegistEvaluationList).hasSize(databaseSizeBeforeUpdate);
        MPreqRegistEvaluation testMPreqRegistEvaluation = mPreqRegistEvaluationList.get(mPreqRegistEvaluationList.size() - 1);
        assertThat(testMPreqRegistEvaluation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPreqRegistEvaluation.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMPreqRegistEvaluation.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void updateNonExistingMPreqRegistEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = mPreqRegistEvaluationRepository.findAll().size();

        // Create the MPreqRegistEvaluation
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO = mPreqRegistEvaluationMapper.toDto(mPreqRegistEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPreqRegistEvaluationMockMvc.perform(put("/api/m-preq-regist-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreqRegistEvaluation in the database
        List<MPreqRegistEvaluation> mPreqRegistEvaluationList = mPreqRegistEvaluationRepository.findAll();
        assertThat(mPreqRegistEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPreqRegistEvaluation() throws Exception {
        // Initialize the database
        mPreqRegistEvaluationRepository.saveAndFlush(mPreqRegistEvaluation);

        int databaseSizeBeforeDelete = mPreqRegistEvaluationRepository.findAll().size();

        // Delete the mPreqRegistEvaluation
        restMPreqRegistEvaluationMockMvc.perform(delete("/api/m-preq-regist-evaluations/{id}", mPreqRegistEvaluation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPreqRegistEvaluation> mPreqRegistEvaluationList = mPreqRegistEvaluationRepository.findAll();
        assertThat(mPreqRegistEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
