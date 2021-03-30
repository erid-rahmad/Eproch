package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorEvaluationLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CQuestionCategory;
import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.repository.CVendorEvaluationLineRepository;
import com.bhp.opusb.service.CVendorEvaluationLineService;
import com.bhp.opusb.service.dto.CVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationLineMapper;
import com.bhp.opusb.service.dto.CVendorEvaluationLineCriteria;
import com.bhp.opusb.service.CVendorEvaluationLineQueryService;

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
 * Integration tests for the {@link CVendorEvaluationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorEvaluationLineResourceIT {

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(100);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(99);
    private static final BigDecimal SMALLER_WEIGHT = new BigDecimal(100 - 1);

    private static final BigDecimal DEFAULT_USER_WEIGHT = new BigDecimal(100);
    private static final BigDecimal UPDATED_USER_WEIGHT = new BigDecimal(99);
    private static final BigDecimal SMALLER_USER_WEIGHT = new BigDecimal(100 - 1);

    private static final BigDecimal DEFAULT_PROCUREMENT_WEIGHT = new BigDecimal(100);
    private static final BigDecimal UPDATED_PROCUREMENT_WEIGHT = new BigDecimal(99);
    private static final BigDecimal SMALLER_PROCUREMENT_WEIGHT = new BigDecimal(100 - 1);

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorEvaluationLineRepository cVendorEvaluationLineRepository;

    @Autowired
    private CVendorEvaluationLineMapper cVendorEvaluationLineMapper;

    @Autowired
    private CVendorEvaluationLineService cVendorEvaluationLineService;

    @Autowired
    private CVendorEvaluationLineQueryService cVendorEvaluationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorEvaluationLineMockMvc;

    private CVendorEvaluationLine cVendorEvaluationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorEvaluationLine createEntity(EntityManager em) {
        CVendorEvaluationLine cVendorEvaluationLine = new CVendorEvaluationLine()
            .weight(DEFAULT_WEIGHT)
            .userWeight(DEFAULT_USER_WEIGHT)
            .procurementWeight(DEFAULT_PROCUREMENT_WEIGHT)
            .question(DEFAULT_QUESTION)
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
        cVendorEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CQuestionCategory cQuestionCategory;
        if (TestUtil.findAll(em, CQuestionCategory.class).isEmpty()) {
            cQuestionCategory = CQuestionCategoryResourceIT.createEntity(em);
            em.persist(cQuestionCategory);
            em.flush();
        } else {
            cQuestionCategory = TestUtil.findAll(em, CQuestionCategory.class).get(0);
        }
        cVendorEvaluationLine.setCQuestionCategory(cQuestionCategory);
        // Add required entity
        CVendorEvaluation cVendorEvaluation;
        if (TestUtil.findAll(em, CVendorEvaluation.class).isEmpty()) {
            cVendorEvaluation = CVendorEvaluationResourceIT.createEntity(em);
            em.persist(cVendorEvaluation);
            em.flush();
        } else {
            cVendorEvaluation = TestUtil.findAll(em, CVendorEvaluation.class).get(0);
        }
        cVendorEvaluationLine.setCVendorEvaluation(cVendorEvaluation);
        return cVendorEvaluationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorEvaluationLine createUpdatedEntity(EntityManager em) {
        CVendorEvaluationLine cVendorEvaluationLine = new CVendorEvaluationLine()
            .weight(UPDATED_WEIGHT)
            .userWeight(UPDATED_USER_WEIGHT)
            .procurementWeight(UPDATED_PROCUREMENT_WEIGHT)
            .question(UPDATED_QUESTION)
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
        cVendorEvaluationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CQuestionCategory cQuestionCategory;
        if (TestUtil.findAll(em, CQuestionCategory.class).isEmpty()) {
            cQuestionCategory = CQuestionCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cQuestionCategory);
            em.flush();
        } else {
            cQuestionCategory = TestUtil.findAll(em, CQuestionCategory.class).get(0);
        }
        cVendorEvaluationLine.setCQuestionCategory(cQuestionCategory);
        // Add required entity
        CVendorEvaluation cVendorEvaluation;
        if (TestUtil.findAll(em, CVendorEvaluation.class).isEmpty()) {
            cVendorEvaluation = CVendorEvaluationResourceIT.createUpdatedEntity(em);
            em.persist(cVendorEvaluation);
            em.flush();
        } else {
            cVendorEvaluation = TestUtil.findAll(em, CVendorEvaluation.class).get(0);
        }
        cVendorEvaluationLine.setCVendorEvaluation(cVendorEvaluation);
        return cVendorEvaluationLine;
    }

    @BeforeEach
    public void initTest() {
        cVendorEvaluationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorEvaluationLine() throws Exception {
        int databaseSizeBeforeCreate = cVendorEvaluationLineRepository.findAll().size();

        // Create the CVendorEvaluationLine
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO = cVendorEvaluationLineMapper.toDto(cVendorEvaluationLine);
        restCVendorEvaluationLineMockMvc.perform(post("/api/c-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorEvaluationLine in the database
        List<CVendorEvaluationLine> cVendorEvaluationLineList = cVendorEvaluationLineRepository.findAll();
        assertThat(cVendorEvaluationLineList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorEvaluationLine testCVendorEvaluationLine = cVendorEvaluationLineList.get(cVendorEvaluationLineList.size() - 1);
        assertThat(testCVendorEvaluationLine.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCVendorEvaluationLine.getUserWeight()).isEqualTo(DEFAULT_USER_WEIGHT);
        assertThat(testCVendorEvaluationLine.getProcurementWeight()).isEqualTo(DEFAULT_PROCUREMENT_WEIGHT);
        assertThat(testCVendorEvaluationLine.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testCVendorEvaluationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorEvaluationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorEvaluationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorEvaluationLineRepository.findAll().size();

        // Create the CVendorEvaluationLine with an existing ID
        cVendorEvaluationLine.setId(1L);
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO = cVendorEvaluationLineMapper.toDto(cVendorEvaluationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorEvaluationLineMockMvc.perform(post("/api/c-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorEvaluationLine in the database
        List<CVendorEvaluationLine> cVendorEvaluationLineList = cVendorEvaluationLineRepository.findAll();
        assertThat(cVendorEvaluationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLines() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].userWeight").value(hasItem(DEFAULT_USER_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].procurementWeight").value(hasItem(DEFAULT_PROCUREMENT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorEvaluationLine() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get the cVendorEvaluationLine
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines/{id}", cVendorEvaluationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorEvaluationLine.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.userWeight").value(DEFAULT_USER_WEIGHT.intValue()))
            .andExpect(jsonPath("$.procurementWeight").value(DEFAULT_PROCUREMENT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorEvaluationLinesByIdFiltering() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        Long id = cVendorEvaluationLine.getId();

        defaultCVendorEvaluationLineShouldBeFound("id.equals=" + id);
        defaultCVendorEvaluationLineShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorEvaluationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorEvaluationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorEvaluationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorEvaluationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight equals to DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight equals to UPDATED_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight not equals to DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight not equals to UPDATED_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight equals to UPDATED_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight is not null
        defaultCVendorEvaluationLineShouldBeFound("weight.specified=true");

        // Get all the cVendorEvaluationLineList where weight is null
        defaultCVendorEvaluationLineShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight is greater than or equal to (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldNotBeFound("weight.greaterThanOrEqual=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight is less than or equal to SMALLER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight is less than DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight is less than (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldBeFound("weight.lessThan=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where weight is greater than DEFAULT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cVendorEvaluationLineList where weight is greater than SMALLER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight equals to DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.equals=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight equals to UPDATED_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.equals=" + UPDATED_USER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight not equals to DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.notEquals=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight not equals to UPDATED_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.notEquals=" + UPDATED_USER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight in DEFAULT_USER_WEIGHT or UPDATED_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.in=" + DEFAULT_USER_WEIGHT + "," + UPDATED_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight equals to UPDATED_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.in=" + UPDATED_USER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight is not null
        defaultCVendorEvaluationLineShouldBeFound("userWeight.specified=true");

        // Get all the cVendorEvaluationLineList where userWeight is null
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight is greater than or equal to DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.greaterThanOrEqual=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight is greater than or equal to (DEFAULT_USER_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.greaterThanOrEqual=" + (DEFAULT_USER_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight is less than or equal to DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.lessThanOrEqual=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight is less than or equal to SMALLER_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.lessThanOrEqual=" + SMALLER_USER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight is less than DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.lessThan=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight is less than (DEFAULT_USER_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldBeFound("userWeight.lessThan=" + (DEFAULT_USER_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUserWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where userWeight is greater than DEFAULT_USER_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("userWeight.greaterThan=" + DEFAULT_USER_WEIGHT);

        // Get all the cVendorEvaluationLineList where userWeight is greater than SMALLER_USER_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("userWeight.greaterThan=" + SMALLER_USER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight equals to DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.equals=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight equals to UPDATED_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.equals=" + UPDATED_PROCUREMENT_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight not equals to DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.notEquals=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight not equals to UPDATED_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.notEquals=" + UPDATED_PROCUREMENT_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight in DEFAULT_PROCUREMENT_WEIGHT or UPDATED_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.in=" + DEFAULT_PROCUREMENT_WEIGHT + "," + UPDATED_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight equals to UPDATED_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.in=" + UPDATED_PROCUREMENT_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight is not null
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.specified=true");

        // Get all the cVendorEvaluationLineList where procurementWeight is null
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight is greater than or equal to DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.greaterThanOrEqual=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight is greater than or equal to (DEFAULT_PROCUREMENT_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.greaterThanOrEqual=" + (DEFAULT_PROCUREMENT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight is less than or equal to DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.lessThanOrEqual=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight is less than or equal to SMALLER_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.lessThanOrEqual=" + SMALLER_PROCUREMENT_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight is less than DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.lessThan=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight is less than (DEFAULT_PROCUREMENT_WEIGHT.add(BigDecimal.ONE))
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.lessThan=" + (DEFAULT_PROCUREMENT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByProcurementWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where procurementWeight is greater than DEFAULT_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldNotBeFound("procurementWeight.greaterThan=" + DEFAULT_PROCUREMENT_WEIGHT);

        // Get all the cVendorEvaluationLineList where procurementWeight is greater than SMALLER_PROCUREMENT_WEIGHT
        defaultCVendorEvaluationLineShouldBeFound("procurementWeight.greaterThan=" + SMALLER_PROCUREMENT_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question equals to DEFAULT_QUESTION
        defaultCVendorEvaluationLineShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the cVendorEvaluationLineList where question equals to UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question not equals to DEFAULT_QUESTION
        defaultCVendorEvaluationLineShouldNotBeFound("question.notEquals=" + DEFAULT_QUESTION);

        // Get all the cVendorEvaluationLineList where question not equals to UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldBeFound("question.notEquals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the cVendorEvaluationLineList where question equals to UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question is not null
        defaultCVendorEvaluationLineShouldBeFound("question.specified=true");

        // Get all the cVendorEvaluationLineList where question is null
        defaultCVendorEvaluationLineShouldNotBeFound("question.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionContainsSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question contains DEFAULT_QUESTION
        defaultCVendorEvaluationLineShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the cVendorEvaluationLineList where question contains UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where question does not contain DEFAULT_QUESTION
        defaultCVendorEvaluationLineShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the cVendorEvaluationLineList where question does not contain UPDATED_QUESTION
        defaultCVendorEvaluationLineShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where uid equals to DEFAULT_UID
        defaultCVendorEvaluationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorEvaluationLineList where uid equals to UPDATED_UID
        defaultCVendorEvaluationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where uid not equals to DEFAULT_UID
        defaultCVendorEvaluationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorEvaluationLineList where uid not equals to UPDATED_UID
        defaultCVendorEvaluationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorEvaluationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorEvaluationLineList where uid equals to UPDATED_UID
        defaultCVendorEvaluationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where uid is not null
        defaultCVendorEvaluationLineShouldBeFound("uid.specified=true");

        // Get all the cVendorEvaluationLineList where uid is null
        defaultCVendorEvaluationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where active equals to DEFAULT_ACTIVE
        defaultCVendorEvaluationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultCVendorEvaluationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where active not equals to DEFAULT_ACTIVE
        defaultCVendorEvaluationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorEvaluationLineList where active not equals to UPDATED_ACTIVE
        defaultCVendorEvaluationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorEvaluationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorEvaluationLineList where active equals to UPDATED_ACTIVE
        defaultCVendorEvaluationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        // Get all the cVendorEvaluationLineList where active is not null
        defaultCVendorEvaluationLineShouldBeFound("active.specified=true");

        // Get all the cVendorEvaluationLineList where active is null
        defaultCVendorEvaluationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorEvaluationLine.getAdOrganization();
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorEvaluationLineList where adOrganization equals to adOrganizationId
        defaultCVendorEvaluationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorEvaluationLineList where adOrganization equals to adOrganizationId + 1
        defaultCVendorEvaluationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByCQuestionCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CQuestionCategory cQuestionCategory = cVendorEvaluationLine.getCQuestionCategory();
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);
        Long cQuestionCategoryId = cQuestionCategory.getId();

        // Get all the cVendorEvaluationLineList where cQuestionCategory equals to cQuestionCategoryId
        defaultCVendorEvaluationLineShouldBeFound("cQuestionCategoryId.equals=" + cQuestionCategoryId);

        // Get all the cVendorEvaluationLineList where cQuestionCategory equals to cQuestionCategoryId + 1
        defaultCVendorEvaluationLineShouldNotBeFound("cQuestionCategoryId.equals=" + (cQuestionCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationLinesByCVendorEvaluationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendorEvaluation cVendorEvaluation = cVendorEvaluationLine.getCVendorEvaluation();
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);
        Long cVendorEvaluationId = cVendorEvaluation.getId();

        // Get all the cVendorEvaluationLineList where cVendorEvaluation equals to cVendorEvaluationId
        defaultCVendorEvaluationLineShouldBeFound("cVendorEvaluationId.equals=" + cVendorEvaluationId);

        // Get all the cVendorEvaluationLineList where cVendorEvaluation equals to cVendorEvaluationId + 1
        defaultCVendorEvaluationLineShouldNotBeFound("cVendorEvaluationId.equals=" + (cVendorEvaluationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorEvaluationLineShouldBeFound(String filter) throws Exception {
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorEvaluationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].userWeight").value(hasItem(DEFAULT_USER_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].procurementWeight").value(hasItem(DEFAULT_PROCUREMENT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorEvaluationLineShouldNotBeFound(String filter) throws Exception {
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorEvaluationLine() throws Exception {
        // Get the cVendorEvaluationLine
        restCVendorEvaluationLineMockMvc.perform(get("/api/c-vendor-evaluation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorEvaluationLine() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        int databaseSizeBeforeUpdate = cVendorEvaluationLineRepository.findAll().size();

        // Update the cVendorEvaluationLine
        CVendorEvaluationLine updatedCVendorEvaluationLine = cVendorEvaluationLineRepository.findById(cVendorEvaluationLine.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorEvaluationLine are not directly saved in db
        em.detach(updatedCVendorEvaluationLine);
        updatedCVendorEvaluationLine
            .weight(UPDATED_WEIGHT)
            .userWeight(UPDATED_USER_WEIGHT)
            .procurementWeight(UPDATED_PROCUREMENT_WEIGHT)
            .question(UPDATED_QUESTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO = cVendorEvaluationLineMapper.toDto(updatedCVendorEvaluationLine);

        restCVendorEvaluationLineMockMvc.perform(put("/api/c-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationLineDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorEvaluationLine in the database
        List<CVendorEvaluationLine> cVendorEvaluationLineList = cVendorEvaluationLineRepository.findAll();
        assertThat(cVendorEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
        CVendorEvaluationLine testCVendorEvaluationLine = cVendorEvaluationLineList.get(cVendorEvaluationLineList.size() - 1);
        assertThat(testCVendorEvaluationLine.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCVendorEvaluationLine.getUserWeight()).isEqualTo(UPDATED_USER_WEIGHT);
        assertThat(testCVendorEvaluationLine.getProcurementWeight()).isEqualTo(UPDATED_PROCUREMENT_WEIGHT);
        assertThat(testCVendorEvaluationLine.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testCVendorEvaluationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorEvaluationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorEvaluationLine() throws Exception {
        int databaseSizeBeforeUpdate = cVendorEvaluationLineRepository.findAll().size();

        // Create the CVendorEvaluationLine
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO = cVendorEvaluationLineMapper.toDto(cVendorEvaluationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorEvaluationLineMockMvc.perform(put("/api/c-vendor-evaluation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorEvaluationLine in the database
        List<CVendorEvaluationLine> cVendorEvaluationLineList = cVendorEvaluationLineRepository.findAll();
        assertThat(cVendorEvaluationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorEvaluationLine() throws Exception {
        // Initialize the database
        cVendorEvaluationLineRepository.saveAndFlush(cVendorEvaluationLine);

        int databaseSizeBeforeDelete = cVendorEvaluationLineRepository.findAll().size();

        // Delete the cVendorEvaluationLine
        restCVendorEvaluationLineMockMvc.perform(delete("/api/c-vendor-evaluation-lines/{id}", cVendorEvaluationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorEvaluationLine> cVendorEvaluationLineList = cVendorEvaluationLineRepository.findAll();
        assertThat(cVendorEvaluationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
