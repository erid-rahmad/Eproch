package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvaluationMethodCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.repository.CEvaluationMethodCriteriaRepository;
import com.bhp.opusb.service.CEvaluationMethodCriteriaService;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodCriteriaMapper;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaCriteria;
import com.bhp.opusb.service.CEvaluationMethodCriteriaQueryService;

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
 * Integration tests for the {@link CEvaluationMethodCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvaluationMethodCriteriaResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvaluationMethodCriteriaRepository cEvaluationMethodCriteriaRepository;

    @Autowired
    private CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper;

    @Autowired
    private CEvaluationMethodCriteriaService cEvaluationMethodCriteriaService;

    @Autowired
    private CEvaluationMethodCriteriaQueryService cEvaluationMethodCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvaluationMethodCriteriaMockMvc;

    private CEvaluationMethodCriteria cEvaluationMethodCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethodCriteria createEntity(EntityManager em) {
        CEvaluationMethodCriteria cEvaluationMethodCriteria = new CEvaluationMethodCriteria()
            .weight(DEFAULT_WEIGHT)
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
        cEvaluationMethodCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvaluationMethodCriteria.setBiddingCriteria(cBiddingCriteria);
        return cEvaluationMethodCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethodCriteria createUpdatedEntity(EntityManager em) {
        CEvaluationMethodCriteria cEvaluationMethodCriteria = new CEvaluationMethodCriteria()
            .weight(UPDATED_WEIGHT)
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
        cEvaluationMethodCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvaluationMethodCriteria.setBiddingCriteria(cBiddingCriteria);
        return cEvaluationMethodCriteria;
    }

    @BeforeEach
    public void initTest() {
        cEvaluationMethodCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvaluationMethodCriteria() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodCriteriaRepository.findAll().size();

        // Create the CEvaluationMethodCriteria
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = cEvaluationMethodCriteriaMapper.toDto(cEvaluationMethodCriteria);
        restCEvaluationMethodCriteriaMockMvc.perform(post("/api/c-evaluation-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvaluationMethodCriteria in the database
        List<CEvaluationMethodCriteria> cEvaluationMethodCriteriaList = cEvaluationMethodCriteriaRepository.findAll();
        assertThat(cEvaluationMethodCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CEvaluationMethodCriteria testCEvaluationMethodCriteria = cEvaluationMethodCriteriaList.get(cEvaluationMethodCriteriaList.size() - 1);
        assertThat(testCEvaluationMethodCriteria.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCEvaluationMethodCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvaluationMethodCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvaluationMethodCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodCriteriaRepository.findAll().size();

        // Create the CEvaluationMethodCriteria with an existing ID
        cEvaluationMethodCriteria.setId(1L);
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = cEvaluationMethodCriteriaMapper.toDto(cEvaluationMethodCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvaluationMethodCriteriaMockMvc.perform(post("/api/c-evaluation-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethodCriteria in the database
        List<CEvaluationMethodCriteria> cEvaluationMethodCriteriaList = cEvaluationMethodCriteriaRepository.findAll();
        assertThat(cEvaluationMethodCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteria() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethodCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvaluationMethodCriteria() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get the cEvaluationMethodCriteria
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria/{id}", cEvaluationMethodCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvaluationMethodCriteria.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvaluationMethodCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        Long id = cEvaluationMethodCriteria.getId();

        defaultCEvaluationMethodCriteriaShouldBeFound("id.equals=" + id);
        defaultCEvaluationMethodCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCEvaluationMethodCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvaluationMethodCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvaluationMethodCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvaluationMethodCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight equals to DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight not equals to DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight not equals to UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight is not null
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.specified=true");

        // Get all the cEvaluationMethodCriteriaList where weight is null
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight is greater than or equal to UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight is less than or equal to SMALLER_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight is less than DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight is less than UPDATED_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where weight is greater than DEFAULT_WEIGHT
        defaultCEvaluationMethodCriteriaShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodCriteriaList where weight is greater than SMALLER_WEIGHT
        defaultCEvaluationMethodCriteriaShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where uid equals to DEFAULT_UID
        defaultCEvaluationMethodCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodCriteriaList where uid equals to UPDATED_UID
        defaultCEvaluationMethodCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where uid not equals to DEFAULT_UID
        defaultCEvaluationMethodCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodCriteriaList where uid not equals to UPDATED_UID
        defaultCEvaluationMethodCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvaluationMethodCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvaluationMethodCriteriaList where uid equals to UPDATED_UID
        defaultCEvaluationMethodCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where uid is not null
        defaultCEvaluationMethodCriteriaShouldBeFound("uid.specified=true");

        // Get all the cEvaluationMethodCriteriaList where uid is null
        defaultCEvaluationMethodCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCEvaluationMethodCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvaluationMethodCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvaluationMethodCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        // Get all the cEvaluationMethodCriteriaList where active is not null
        defaultCEvaluationMethodCriteriaShouldBeFound("active.specified=true");

        // Get all the cEvaluationMethodCriteriaList where active is null
        defaultCEvaluationMethodCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvaluationMethodCriteria.getAdOrganization();
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvaluationMethodCriteriaList where adOrganization equals to adOrganizationId
        defaultCEvaluationMethodCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvaluationMethodCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCEvaluationMethodCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByEvaluationMethodLineIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);
        CEvaluationMethodLine evaluationMethodLine = CEvaluationMethodLineResourceIT.createEntity(em);
        em.persist(evaluationMethodLine);
        em.flush();
        cEvaluationMethodCriteria.setEvaluationMethodLine(evaluationMethodLine);
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);
        Long evaluationMethodLineId = evaluationMethodLine.getId();

        // Get all the cEvaluationMethodCriteriaList where evaluationMethodLine equals to evaluationMethodLineId
        defaultCEvaluationMethodCriteriaShouldBeFound("evaluationMethodLineId.equals=" + evaluationMethodLineId);

        // Get all the cEvaluationMethodCriteriaList where evaluationMethodLine equals to evaluationMethodLineId + 1
        defaultCEvaluationMethodCriteriaShouldNotBeFound("evaluationMethodLineId.equals=" + (evaluationMethodLineId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cEvaluationMethodCriteria.getBiddingCriteria();
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cEvaluationMethodCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCEvaluationMethodCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cEvaluationMethodCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCEvaluationMethodCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvaluationMethodCriteriaShouldBeFound(String filter) throws Exception {
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethodCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvaluationMethodCriteriaShouldNotBeFound(String filter) throws Exception {
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvaluationMethodCriteria() throws Exception {
        // Get the cEvaluationMethodCriteria
        restCEvaluationMethodCriteriaMockMvc.perform(get("/api/c-evaluation-method-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvaluationMethodCriteria() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        int databaseSizeBeforeUpdate = cEvaluationMethodCriteriaRepository.findAll().size();

        // Update the cEvaluationMethodCriteria
        CEvaluationMethodCriteria updatedCEvaluationMethodCriteria = cEvaluationMethodCriteriaRepository.findById(cEvaluationMethodCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCEvaluationMethodCriteria are not directly saved in db
        em.detach(updatedCEvaluationMethodCriteria);
        updatedCEvaluationMethodCriteria
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = cEvaluationMethodCriteriaMapper.toDto(updatedCEvaluationMethodCriteria);

        restCEvaluationMethodCriteriaMockMvc.perform(put("/api/c-evaluation-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CEvaluationMethodCriteria in the database
        List<CEvaluationMethodCriteria> cEvaluationMethodCriteriaList = cEvaluationMethodCriteriaRepository.findAll();
        assertThat(cEvaluationMethodCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CEvaluationMethodCriteria testCEvaluationMethodCriteria = cEvaluationMethodCriteriaList.get(cEvaluationMethodCriteriaList.size() - 1);
        assertThat(testCEvaluationMethodCriteria.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCEvaluationMethodCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvaluationMethodCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvaluationMethodCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cEvaluationMethodCriteriaRepository.findAll().size();

        // Create the CEvaluationMethodCriteria
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = cEvaluationMethodCriteriaMapper.toDto(cEvaluationMethodCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvaluationMethodCriteriaMockMvc.perform(put("/api/c-evaluation-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethodCriteria in the database
        List<CEvaluationMethodCriteria> cEvaluationMethodCriteriaList = cEvaluationMethodCriteriaRepository.findAll();
        assertThat(cEvaluationMethodCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvaluationMethodCriteria() throws Exception {
        // Initialize the database
        cEvaluationMethodCriteriaRepository.saveAndFlush(cEvaluationMethodCriteria);

        int databaseSizeBeforeDelete = cEvaluationMethodCriteriaRepository.findAll().size();

        // Delete the cEvaluationMethodCriteria
        restCEvaluationMethodCriteriaMockMvc.perform(delete("/api/c-evaluation-method-criteria/{id}", cEvaluationMethodCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvaluationMethodCriteria> cEvaluationMethodCriteriaList = cEvaluationMethodCriteriaRepository.findAll();
        assertThat(cEvaluationMethodCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
