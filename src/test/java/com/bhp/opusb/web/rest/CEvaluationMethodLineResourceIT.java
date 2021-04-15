package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvaluationMethod;
import com.bhp.opusb.repository.CEvaluationMethodLineRepository;
import com.bhp.opusb.service.CEvaluationMethodLineService;
import com.bhp.opusb.service.dto.CEvaluationMethodLineDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodLineMapper;
import com.bhp.opusb.service.dto.CEvaluationMethodLineCriteria;
import com.bhp.opusb.service.CEvaluationMethodLineQueryService;

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
 * Integration tests for the {@link CEvaluationMethodLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvaluationMethodLineResourceIT {

    private static final String DEFAULT_EVALUATION = "AAAAA";
    private static final String UPDATED_EVALUATION = "BBBBB";

    private static final String DEFAULT_EVALUATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(100);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(99);
    private static final BigDecimal SMALLER_WEIGHT = new BigDecimal(100 - 1);

    private static final BigDecimal DEFAULT_PASSING_GRADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PASSING_GRADE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PASSING_GRADE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvaluationMethodLineRepository cEvaluationMethodLineRepository;

    @Autowired
    private CEvaluationMethodLineMapper cEvaluationMethodLineMapper;

    @Autowired
    private CEvaluationMethodLineService cEvaluationMethodLineService;

    @Autowired
    private CEvaluationMethodLineQueryService cEvaluationMethodLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvaluationMethodLineMockMvc;

    private CEvaluationMethodLine cEvaluationMethodLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethodLine createEntity(EntityManager em) {
        CEvaluationMethodLine cEvaluationMethodLine = new CEvaluationMethodLine()
            .evaluation(DEFAULT_EVALUATION)
            .evaluationType(DEFAULT_EVALUATION_TYPE)
            .weight(DEFAULT_WEIGHT)
            .passingGrade(DEFAULT_PASSING_GRADE)
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
        cEvaluationMethodLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethod cEvaluationMethod;
        if (TestUtil.findAll(em, CEvaluationMethod.class).isEmpty()) {
            cEvaluationMethod = CEvaluationMethodResourceIT.createEntity(em);
            em.persist(cEvaluationMethod);
            em.flush();
        } else {
            cEvaluationMethod = TestUtil.findAll(em, CEvaluationMethod.class).get(0);
        }
        cEvaluationMethodLine.setEvaluationMethod(cEvaluationMethod);
        return cEvaluationMethodLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethodLine createUpdatedEntity(EntityManager em) {
        CEvaluationMethodLine cEvaluationMethodLine = new CEvaluationMethodLine()
            .evaluation(UPDATED_EVALUATION)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .weight(UPDATED_WEIGHT)
            .passingGrade(UPDATED_PASSING_GRADE)
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
        cEvaluationMethodLine.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethod cEvaluationMethod;
        if (TestUtil.findAll(em, CEvaluationMethod.class).isEmpty()) {
            cEvaluationMethod = CEvaluationMethodResourceIT.createUpdatedEntity(em);
            em.persist(cEvaluationMethod);
            em.flush();
        } else {
            cEvaluationMethod = TestUtil.findAll(em, CEvaluationMethod.class).get(0);
        }
        cEvaluationMethodLine.setEvaluationMethod(cEvaluationMethod);
        return cEvaluationMethodLine;
    }

    @BeforeEach
    public void initTest() {
        cEvaluationMethodLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvaluationMethodLine() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodLineRepository.findAll().size();

        // Create the CEvaluationMethodLine
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = cEvaluationMethodLineMapper.toDto(cEvaluationMethodLine);
        restCEvaluationMethodLineMockMvc.perform(post("/api/c-evaluation-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvaluationMethodLine in the database
        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeCreate + 1);
        CEvaluationMethodLine testCEvaluationMethodLine = cEvaluationMethodLineList.get(cEvaluationMethodLineList.size() - 1);
        assertThat(testCEvaluationMethodLine.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testCEvaluationMethodLine.getEvaluationType()).isEqualTo(DEFAULT_EVALUATION_TYPE);
        assertThat(testCEvaluationMethodLine.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCEvaluationMethodLine.getPassingGrade()).isEqualTo(DEFAULT_PASSING_GRADE);
        assertThat(testCEvaluationMethodLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvaluationMethodLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvaluationMethodLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodLineRepository.findAll().size();

        // Create the CEvaluationMethodLine with an existing ID
        cEvaluationMethodLine.setId(1L);
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = cEvaluationMethodLineMapper.toDto(cEvaluationMethodLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvaluationMethodLineMockMvc.perform(post("/api/c-evaluation-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethodLine in the database
        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEvaluationMethodLineRepository.findAll().size();
        // set the field null
        cEvaluationMethodLine.setEvaluation(null);

        // Create the CEvaluationMethodLine, which fails.
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = cEvaluationMethodLineMapper.toDto(cEvaluationMethodLine);

        restCEvaluationMethodLineMockMvc.perform(post("/api/c-evaluation-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodLineDTO)))
            .andExpect(status().isBadRequest());

        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLines() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethodLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].evaluationType").value(hasItem(DEFAULT_EVALUATION_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].passingGrade").value(hasItem(DEFAULT_PASSING_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvaluationMethodLine() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get the cEvaluationMethodLine
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines/{id}", cEvaluationMethodLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvaluationMethodLine.getId().intValue()))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.evaluationType").value(DEFAULT_EVALUATION_TYPE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.passingGrade").value(DEFAULT_PASSING_GRADE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvaluationMethodLinesByIdFiltering() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        Long id = cEvaluationMethodLine.getId();

        defaultCEvaluationMethodLineShouldBeFound("id.equals=" + id);
        defaultCEvaluationMethodLineShouldNotBeFound("id.notEquals=" + id);

        defaultCEvaluationMethodLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvaluationMethodLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvaluationMethodLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvaluationMethodLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation equals to DEFAULT_EVALUATION
        defaultCEvaluationMethodLineShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the cEvaluationMethodLineList where evaluation equals to UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation not equals to DEFAULT_EVALUATION
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the cEvaluationMethodLineList where evaluation not equals to UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the cEvaluationMethodLineList where evaluation equals to UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation is not null
        defaultCEvaluationMethodLineShouldBeFound("evaluation.specified=true");

        // Get all the cEvaluationMethodLineList where evaluation is null
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation contains DEFAULT_EVALUATION
        defaultCEvaluationMethodLineShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the cEvaluationMethodLineList where evaluation contains UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluation does not contain DEFAULT_EVALUATION
        defaultCEvaluationMethodLineShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the cEvaluationMethodLineList where evaluation does not contain UPDATED_EVALUATION
        defaultCEvaluationMethodLineShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType equals to DEFAULT_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.equals=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cEvaluationMethodLineList where evaluationType equals to UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.equals=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType not equals to DEFAULT_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.notEquals=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cEvaluationMethodLineList where evaluationType not equals to UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.notEquals=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType in DEFAULT_EVALUATION_TYPE or UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.in=" + DEFAULT_EVALUATION_TYPE + "," + UPDATED_EVALUATION_TYPE);

        // Get all the cEvaluationMethodLineList where evaluationType equals to UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.in=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType is not null
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.specified=true");

        // Get all the cEvaluationMethodLineList where evaluationType is null
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType contains DEFAULT_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.contains=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cEvaluationMethodLineList where evaluationType contains UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.contains=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where evaluationType does not contain DEFAULT_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationType.doesNotContain=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cEvaluationMethodLineList where evaluationType does not contain UPDATED_EVALUATION_TYPE
        defaultCEvaluationMethodLineShouldBeFound("evaluationType.doesNotContain=" + UPDATED_EVALUATION_TYPE);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight equals to DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight equals to UPDATED_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight not equals to DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight not equals to UPDATED_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight equals to UPDATED_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight is not null
        defaultCEvaluationMethodLineShouldBeFound("weight.specified=true");

        // Get all the cEvaluationMethodLineList where weight is null
        defaultCEvaluationMethodLineShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight is greater than or equal to (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCEvaluationMethodLineShouldNotBeFound("weight.greaterThanOrEqual=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight is less than or equal to SMALLER_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight is less than DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight is less than (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCEvaluationMethodLineShouldBeFound("weight.lessThan=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where weight is greater than DEFAULT_WEIGHT
        defaultCEvaluationMethodLineShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cEvaluationMethodLineList where weight is greater than SMALLER_WEIGHT
        defaultCEvaluationMethodLineShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade equals to DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.equals=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade equals to UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.equals=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade not equals to DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.notEquals=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade not equals to UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.notEquals=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade in DEFAULT_PASSING_GRADE or UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.in=" + DEFAULT_PASSING_GRADE + "," + UPDATED_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade equals to UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.in=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade is not null
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.specified=true");

        // Get all the cEvaluationMethodLineList where passingGrade is null
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade is greater than or equal to DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.greaterThanOrEqual=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade is greater than or equal to UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.greaterThanOrEqual=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade is less than or equal to DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.lessThanOrEqual=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade is less than or equal to SMALLER_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.lessThanOrEqual=" + SMALLER_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade is less than DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.lessThan=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade is less than UPDATED_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.lessThan=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByPassingGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where passingGrade is greater than DEFAULT_PASSING_GRADE
        defaultCEvaluationMethodLineShouldNotBeFound("passingGrade.greaterThan=" + DEFAULT_PASSING_GRADE);

        // Get all the cEvaluationMethodLineList where passingGrade is greater than SMALLER_PASSING_GRADE
        defaultCEvaluationMethodLineShouldBeFound("passingGrade.greaterThan=" + SMALLER_PASSING_GRADE);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where uid equals to DEFAULT_UID
        defaultCEvaluationMethodLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodLineList where uid equals to UPDATED_UID
        defaultCEvaluationMethodLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where uid not equals to DEFAULT_UID
        defaultCEvaluationMethodLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodLineList where uid not equals to UPDATED_UID
        defaultCEvaluationMethodLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvaluationMethodLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvaluationMethodLineList where uid equals to UPDATED_UID
        defaultCEvaluationMethodLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where uid is not null
        defaultCEvaluationMethodLineShouldBeFound("uid.specified=true");

        // Get all the cEvaluationMethodLineList where uid is null
        defaultCEvaluationMethodLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where active equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodLineList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where active not equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodLineList where active not equals to UPDATED_ACTIVE
        defaultCEvaluationMethodLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvaluationMethodLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvaluationMethodLineList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        // Get all the cEvaluationMethodLineList where active is not null
        defaultCEvaluationMethodLineShouldBeFound("active.specified=true");

        // Get all the cEvaluationMethodLineList where active is null
        defaultCEvaluationMethodLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvaluationMethodLine.getAdOrganization();
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvaluationMethodLineList where adOrganization equals to adOrganizationId
        defaultCEvaluationMethodLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvaluationMethodLineList where adOrganization equals to adOrganizationId + 1
        defaultCEvaluationMethodLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodLinesByEvaluationMethodIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEvaluationMethod evaluationMethod = cEvaluationMethodLine.getEvaluationMethod();
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);
        Long evaluationMethodId = evaluationMethod.getId();

        // Get all the cEvaluationMethodLineList where evaluationMethod equals to evaluationMethodId
        defaultCEvaluationMethodLineShouldBeFound("evaluationMethodId.equals=" + evaluationMethodId);

        // Get all the cEvaluationMethodLineList where evaluationMethod equals to evaluationMethodId + 1
        defaultCEvaluationMethodLineShouldNotBeFound("evaluationMethodId.equals=" + (evaluationMethodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvaluationMethodLineShouldBeFound(String filter) throws Exception {
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethodLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].evaluationType").value(hasItem(DEFAULT_EVALUATION_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].passingGrade").value(hasItem(DEFAULT_PASSING_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvaluationMethodLineShouldNotBeFound(String filter) throws Exception {
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvaluationMethodLine() throws Exception {
        // Get the cEvaluationMethodLine
        restCEvaluationMethodLineMockMvc.perform(get("/api/c-evaluation-method-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvaluationMethodLine() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        int databaseSizeBeforeUpdate = cEvaluationMethodLineRepository.findAll().size();

        // Update the cEvaluationMethodLine
        CEvaluationMethodLine updatedCEvaluationMethodLine = cEvaluationMethodLineRepository.findById(cEvaluationMethodLine.getId()).get();
        // Disconnect from session so that the updates on updatedCEvaluationMethodLine are not directly saved in db
        em.detach(updatedCEvaluationMethodLine);
        updatedCEvaluationMethodLine
            .evaluation(UPDATED_EVALUATION)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .weight(UPDATED_WEIGHT)
            .passingGrade(UPDATED_PASSING_GRADE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = cEvaluationMethodLineMapper.toDto(updatedCEvaluationMethodLine);

        restCEvaluationMethodLineMockMvc.perform(put("/api/c-evaluation-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodLineDTO)))
            .andExpect(status().isOk());

        // Validate the CEvaluationMethodLine in the database
        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeUpdate);
        CEvaluationMethodLine testCEvaluationMethodLine = cEvaluationMethodLineList.get(cEvaluationMethodLineList.size() - 1);
        assertThat(testCEvaluationMethodLine.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testCEvaluationMethodLine.getEvaluationType()).isEqualTo(UPDATED_EVALUATION_TYPE);
        assertThat(testCEvaluationMethodLine.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCEvaluationMethodLine.getPassingGrade()).isEqualTo(UPDATED_PASSING_GRADE);
        assertThat(testCEvaluationMethodLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvaluationMethodLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvaluationMethodLine() throws Exception {
        int databaseSizeBeforeUpdate = cEvaluationMethodLineRepository.findAll().size();

        // Create the CEvaluationMethodLine
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = cEvaluationMethodLineMapper.toDto(cEvaluationMethodLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvaluationMethodLineMockMvc.perform(put("/api/c-evaluation-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethodLine in the database
        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvaluationMethodLine() throws Exception {
        // Initialize the database
        cEvaluationMethodLineRepository.saveAndFlush(cEvaluationMethodLine);

        int databaseSizeBeforeDelete = cEvaluationMethodLineRepository.findAll().size();

        // Delete the cEvaluationMethodLine
        restCEvaluationMethodLineMockMvc.perform(delete("/api/c-evaluation-method-lines/{id}", cEvaluationMethodLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvaluationMethodLine> cEvaluationMethodLineList = cEvaluationMethodLineRepository.findAll();
        assertThat(cEvaluationMethodLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
