package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvalMethodSubCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.CEvaluationMethodCriteria;
import com.bhp.opusb.repository.CEvalMethodSubCriteriaRepository;
import com.bhp.opusb.service.CEvalMethodSubCriteriaService;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvalMethodSubCriteriaMapper;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.CEvalMethodSubCriteriaQueryService;

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
 * Integration tests for the {@link CEvalMethodSubCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvalMethodSubCriteriaResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvalMethodSubCriteriaRepository cEvalMethodSubCriteriaRepository;

    @Autowired
    private CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper;

    @Autowired
    private CEvalMethodSubCriteriaService cEvalMethodSubCriteriaService;

    @Autowired
    private CEvalMethodSubCriteriaQueryService cEvalMethodSubCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvalMethodSubCriteriaMockMvc;

    private CEvalMethodSubCriteria cEvalMethodSubCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvalMethodSubCriteria createEntity(EntityManager em) {
        CEvalMethodSubCriteria cEvalMethodSubCriteria = new CEvalMethodSubCriteria()
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
        cEvalMethodSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        CEvaluationMethodCriteria cEvaluationMethodCriteria;
        if (TestUtil.findAll(em, CEvaluationMethodCriteria.class).isEmpty()) {
            cEvaluationMethodCriteria = CEvaluationMethodCriteriaResourceIT.createEntity(em);
            em.persist(cEvaluationMethodCriteria);
            em.flush();
        } else {
            cEvaluationMethodCriteria = TestUtil.findAll(em, CEvaluationMethodCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setEvaluationMethodCriteria(cEvaluationMethodCriteria);
        return cEvalMethodSubCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvalMethodSubCriteria createUpdatedEntity(EntityManager em) {
        CEvalMethodSubCriteria cEvalMethodSubCriteria = new CEvalMethodSubCriteria()
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
        cEvalMethodSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        CEvaluationMethodCriteria cEvaluationMethodCriteria;
        if (TestUtil.findAll(em, CEvaluationMethodCriteria.class).isEmpty()) {
            cEvaluationMethodCriteria = CEvaluationMethodCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cEvaluationMethodCriteria);
            em.flush();
        } else {
            cEvaluationMethodCriteria = TestUtil.findAll(em, CEvaluationMethodCriteria.class).get(0);
        }
        cEvalMethodSubCriteria.setEvaluationMethodCriteria(cEvaluationMethodCriteria);
        return cEvalMethodSubCriteria;
    }

    @BeforeEach
    public void initTest() {
        cEvalMethodSubCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvalMethodSubCriteria() throws Exception {
        int databaseSizeBeforeCreate = cEvalMethodSubCriteriaRepository.findAll().size();

        // Create the CEvalMethodSubCriteria
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO = cEvalMethodSubCriteriaMapper.toDto(cEvalMethodSubCriteria);
        restCEvalMethodSubCriteriaMockMvc.perform(post("/api/c-eval-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodSubCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvalMethodSubCriteria in the database
        List<CEvalMethodSubCriteria> cEvalMethodSubCriteriaList = cEvalMethodSubCriteriaRepository.findAll();
        assertThat(cEvalMethodSubCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CEvalMethodSubCriteria testCEvalMethodSubCriteria = cEvalMethodSubCriteriaList.get(cEvalMethodSubCriteriaList.size() - 1);
        assertThat(testCEvalMethodSubCriteria.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCEvalMethodSubCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvalMethodSubCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvalMethodSubCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvalMethodSubCriteriaRepository.findAll().size();

        // Create the CEvalMethodSubCriteria with an existing ID
        cEvalMethodSubCriteria.setId(1L);
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO = cEvalMethodSubCriteriaMapper.toDto(cEvalMethodSubCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvalMethodSubCriteriaMockMvc.perform(post("/api/c-eval-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvalMethodSubCriteria in the database
        List<CEvalMethodSubCriteria> cEvalMethodSubCriteriaList = cEvalMethodSubCriteriaRepository.findAll();
        assertThat(cEvalMethodSubCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteria() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvalMethodSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvalMethodSubCriteria() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get the cEvalMethodSubCriteria
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria/{id}", cEvalMethodSubCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvalMethodSubCriteria.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvalMethodSubCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        Long id = cEvalMethodSubCriteria.getId();

        defaultCEvalMethodSubCriteriaShouldBeFound("id.equals=" + id);
        defaultCEvalMethodSubCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCEvalMethodSubCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvalMethodSubCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvalMethodSubCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvalMethodSubCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight equals to DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight not equals to DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight not equals to UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight is not null
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.specified=true");

        // Get all the cEvalMethodSubCriteriaList where weight is null
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight is greater than or equal to UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight is less than or equal to SMALLER_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight is less than DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight is less than UPDATED_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where weight is greater than DEFAULT_WEIGHT
        defaultCEvalMethodSubCriteriaShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodSubCriteriaList where weight is greater than SMALLER_WEIGHT
        defaultCEvalMethodSubCriteriaShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where uid equals to DEFAULT_UID
        defaultCEvalMethodSubCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvalMethodSubCriteriaList where uid equals to UPDATED_UID
        defaultCEvalMethodSubCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where uid not equals to DEFAULT_UID
        defaultCEvalMethodSubCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvalMethodSubCriteriaList where uid not equals to UPDATED_UID
        defaultCEvalMethodSubCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvalMethodSubCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvalMethodSubCriteriaList where uid equals to UPDATED_UID
        defaultCEvalMethodSubCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where uid is not null
        defaultCEvalMethodSubCriteriaShouldBeFound("uid.specified=true");

        // Get all the cEvalMethodSubCriteriaList where uid is null
        defaultCEvalMethodSubCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCEvalMethodSubCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvalMethodSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvalMethodSubCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCEvalMethodSubCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvalMethodSubCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCEvalMethodSubCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvalMethodSubCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvalMethodSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvalMethodSubCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        // Get all the cEvalMethodSubCriteriaList where active is not null
        defaultCEvalMethodSubCriteriaShouldBeFound("active.specified=true");

        // Get all the cEvalMethodSubCriteriaList where active is null
        defaultCEvalMethodSubCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvalMethodSubCriteria.getAdOrganization();
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvalMethodSubCriteriaList where adOrganization equals to adOrganizationId
        defaultCEvalMethodSubCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvalMethodSubCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCEvalMethodSubCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cEvalMethodSubCriteria.getBiddingCriteria();
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cEvalMethodSubCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCEvalMethodSubCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cEvalMethodSubCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCEvalMethodSubCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = cEvalMethodSubCriteria.getBiddingSubCriteria();
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the cEvalMethodSubCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultCEvalMethodSubCriteriaShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the cEvalMethodSubCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultCEvalMethodSubCriteriaShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvalMethodSubCriteriaByEvaluationMethodCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEvaluationMethodCriteria evaluationMethodCriteria = cEvalMethodSubCriteria.getEvaluationMethodCriteria();
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);
        Long evaluationMethodCriteriaId = evaluationMethodCriteria.getId();

        // Get all the cEvalMethodSubCriteriaList where evaluationMethodCriteria equals to evaluationMethodCriteriaId
        defaultCEvalMethodSubCriteriaShouldBeFound("evaluationMethodCriteriaId.equals=" + evaluationMethodCriteriaId);

        // Get all the cEvalMethodSubCriteriaList where evaluationMethodCriteria equals to evaluationMethodCriteriaId + 1
        defaultCEvalMethodSubCriteriaShouldNotBeFound("evaluationMethodCriteriaId.equals=" + (evaluationMethodCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvalMethodSubCriteriaShouldBeFound(String filter) throws Exception {
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvalMethodSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvalMethodSubCriteriaShouldNotBeFound(String filter) throws Exception {
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvalMethodSubCriteria() throws Exception {
        // Get the cEvalMethodSubCriteria
        restCEvalMethodSubCriteriaMockMvc.perform(get("/api/c-eval-method-sub-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvalMethodSubCriteria() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        int databaseSizeBeforeUpdate = cEvalMethodSubCriteriaRepository.findAll().size();

        // Update the cEvalMethodSubCriteria
        CEvalMethodSubCriteria updatedCEvalMethodSubCriteria = cEvalMethodSubCriteriaRepository.findById(cEvalMethodSubCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCEvalMethodSubCriteria are not directly saved in db
        em.detach(updatedCEvalMethodSubCriteria);
        updatedCEvalMethodSubCriteria
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO = cEvalMethodSubCriteriaMapper.toDto(updatedCEvalMethodSubCriteria);

        restCEvalMethodSubCriteriaMockMvc.perform(put("/api/c-eval-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodSubCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CEvalMethodSubCriteria in the database
        List<CEvalMethodSubCriteria> cEvalMethodSubCriteriaList = cEvalMethodSubCriteriaRepository.findAll();
        assertThat(cEvalMethodSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CEvalMethodSubCriteria testCEvalMethodSubCriteria = cEvalMethodSubCriteriaList.get(cEvalMethodSubCriteriaList.size() - 1);
        assertThat(testCEvalMethodSubCriteria.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCEvalMethodSubCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvalMethodSubCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvalMethodSubCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cEvalMethodSubCriteriaRepository.findAll().size();

        // Create the CEvalMethodSubCriteria
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO = cEvalMethodSubCriteriaMapper.toDto(cEvalMethodSubCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvalMethodSubCriteriaMockMvc.perform(put("/api/c-eval-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvalMethodSubCriteria in the database
        List<CEvalMethodSubCriteria> cEvalMethodSubCriteriaList = cEvalMethodSubCriteriaRepository.findAll();
        assertThat(cEvalMethodSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvalMethodSubCriteria() throws Exception {
        // Initialize the database
        cEvalMethodSubCriteriaRepository.saveAndFlush(cEvalMethodSubCriteria);

        int databaseSizeBeforeDelete = cEvalMethodSubCriteriaRepository.findAll().size();

        // Delete the cEvalMethodSubCriteria
        restCEvalMethodSubCriteriaMockMvc.perform(delete("/api/c-eval-method-sub-criteria/{id}", cEvalMethodSubCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvalMethodSubCriteria> cEvalMethodSubCriteriaList = cEvalMethodSubCriteriaRepository.findAll();
        assertThat(cEvalMethodSubCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
