package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBudgetPlan;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.repository.CBudgetPlanRepository;
import com.bhp.opusb.service.CBudgetPlanService;
import com.bhp.opusb.service.dto.CBudgetPlanDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanMapper;
import com.bhp.opusb.service.dto.CBudgetPlanCriteria;
import com.bhp.opusb.service.CBudgetPlanQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CBudgetPlanResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBudgetPlanResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUDGET_DEDUCTION = "AAAAAAAAAA";
    private static final String UPDATED_BUDGET_DEDUCTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BUDGET_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUDGET_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_BUDGET_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT_AVAILABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_AVAILABLE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_AVAILABLE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_PREVENT_OVER_BUDGET = false;
    private static final Boolean UPDATED_PREVENT_OVER_BUDGET = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBudgetPlanRepository cBudgetPlanRepository;

    @Autowired
    private CBudgetPlanMapper cBudgetPlanMapper;

    @Autowired
    private CBudgetPlanService cBudgetPlanService;

    @Autowired
    private CBudgetPlanQueryService cBudgetPlanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBudgetPlanMockMvc;

    private CBudgetPlan cBudgetPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBudgetPlan createEntity(EntityManager em) {
        CBudgetPlan cBudgetPlan = new CBudgetPlan()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .budgetDeduction(DEFAULT_BUDGET_DEDUCTION)
            .budgetAmount(DEFAULT_BUDGET_AMOUNT)
            .amountAvailable(DEFAULT_AMOUNT_AVAILABLE)
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .preventOverBudget(DEFAULT_PREVENT_OVER_BUDGET)
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
        cBudgetPlan.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        cBudgetPlan.setCCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cBudgetPlan.setCCurrency(cCurrency);
        return cBudgetPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBudgetPlan createUpdatedEntity(EntityManager em) {
        CBudgetPlan cBudgetPlan = new CBudgetPlan()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .budgetDeduction(UPDATED_BUDGET_DEDUCTION)
            .budgetAmount(UPDATED_BUDGET_AMOUNT)
            .amountAvailable(UPDATED_AMOUNT_AVAILABLE)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .preventOverBudget(UPDATED_PREVENT_OVER_BUDGET)
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
        cBudgetPlan.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        cBudgetPlan.setCCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cBudgetPlan.setCCurrency(cCurrency);
        return cBudgetPlan;
    }

    @BeforeEach
    public void initTest() {
        cBudgetPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBudgetPlan() throws Exception {
        int databaseSizeBeforeCreate = cBudgetPlanRepository.findAll().size();

        // Create the CBudgetPlan
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);
        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the CBudgetPlan in the database
        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeCreate + 1);
        CBudgetPlan testCBudgetPlan = cBudgetPlanList.get(cBudgetPlanList.size() - 1);
        assertThat(testCBudgetPlan.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCBudgetPlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBudgetPlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBudgetPlan.getBudgetDeduction()).isEqualTo(DEFAULT_BUDGET_DEDUCTION);
        assertThat(testCBudgetPlan.getBudgetAmount()).isEqualTo(DEFAULT_BUDGET_AMOUNT);
        assertThat(testCBudgetPlan.getAmountAvailable()).isEqualTo(DEFAULT_AMOUNT_AVAILABLE);
        assertThat(testCBudgetPlan.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testCBudgetPlan.isPreventOverBudget()).isEqualTo(DEFAULT_PREVENT_OVER_BUDGET);
        assertThat(testCBudgetPlan.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBudgetPlan.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBudgetPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBudgetPlanRepository.findAll().size();

        // Create the CBudgetPlan with an existing ID
        cBudgetPlan.setId(1L);
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBudgetPlan in the database
        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBudgetPlanRepository.findAll().size();
        // set the field null
        cBudgetPlan.setCode(null);

        // Create the CBudgetPlan, which fails.
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBudgetPlanRepository.findAll().size();
        // set the field null
        cBudgetPlan.setName(null);

        // Create the CBudgetPlan, which fails.
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBudgetAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBudgetPlanRepository.findAll().size();
        // set the field null
        cBudgetPlan.setBudgetAmount(null);

        // Create the CBudgetPlan, which fails.
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBudgetPlanRepository.findAll().size();
        // set the field null
        cBudgetPlan.setAmountAvailable(null);

        // Create the CBudgetPlan, which fails.
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBudgetPlanRepository.findAll().size();
        // set the field null
        cBudgetPlan.setDocumentDate(null);

        // Create the CBudgetPlan, which fails.
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        restCBudgetPlanMockMvc.perform(post("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlans() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBudgetPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].budgetDeduction").value(hasItem(DEFAULT_BUDGET_DEDUCTION)))
            .andExpect(jsonPath("$.[*].budgetAmount").value(hasItem(DEFAULT_BUDGET_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amountAvailable").value(hasItem(DEFAULT_AMOUNT_AVAILABLE.intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].preventOverBudget").value(hasItem(DEFAULT_PREVENT_OVER_BUDGET.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBudgetPlan() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get the cBudgetPlan
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans/{id}", cBudgetPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBudgetPlan.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.budgetDeduction").value(DEFAULT_BUDGET_DEDUCTION))
            .andExpect(jsonPath("$.budgetAmount").value(DEFAULT_BUDGET_AMOUNT.intValue()))
            .andExpect(jsonPath("$.amountAvailable").value(DEFAULT_AMOUNT_AVAILABLE.intValue()))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.preventOverBudget").value(DEFAULT_PREVENT_OVER_BUDGET.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBudgetPlansByIdFiltering() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        Long id = cBudgetPlan.getId();

        defaultCBudgetPlanShouldBeFound("id.equals=" + id);
        defaultCBudgetPlanShouldNotBeFound("id.notEquals=" + id);

        defaultCBudgetPlanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBudgetPlanShouldNotBeFound("id.greaterThan=" + id);

        defaultCBudgetPlanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBudgetPlanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code equals to DEFAULT_CODE
        defaultCBudgetPlanShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cBudgetPlanList where code equals to UPDATED_CODE
        defaultCBudgetPlanShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code not equals to DEFAULT_CODE
        defaultCBudgetPlanShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cBudgetPlanList where code not equals to UPDATED_CODE
        defaultCBudgetPlanShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCBudgetPlanShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cBudgetPlanList where code equals to UPDATED_CODE
        defaultCBudgetPlanShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code is not null
        defaultCBudgetPlanShouldBeFound("code.specified=true");

        // Get all the cBudgetPlanList where code is null
        defaultCBudgetPlanShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBudgetPlansByCodeContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code contains DEFAULT_CODE
        defaultCBudgetPlanShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cBudgetPlanList where code contains UPDATED_CODE
        defaultCBudgetPlanShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where code does not contain DEFAULT_CODE
        defaultCBudgetPlanShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cBudgetPlanList where code does not contain UPDATED_CODE
        defaultCBudgetPlanShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name equals to DEFAULT_NAME
        defaultCBudgetPlanShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBudgetPlanList where name equals to UPDATED_NAME
        defaultCBudgetPlanShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name not equals to DEFAULT_NAME
        defaultCBudgetPlanShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBudgetPlanList where name not equals to UPDATED_NAME
        defaultCBudgetPlanShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBudgetPlanShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBudgetPlanList where name equals to UPDATED_NAME
        defaultCBudgetPlanShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name is not null
        defaultCBudgetPlanShouldBeFound("name.specified=true");

        // Get all the cBudgetPlanList where name is null
        defaultCBudgetPlanShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBudgetPlansByNameContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name contains DEFAULT_NAME
        defaultCBudgetPlanShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBudgetPlanList where name contains UPDATED_NAME
        defaultCBudgetPlanShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where name does not contain DEFAULT_NAME
        defaultCBudgetPlanShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBudgetPlanList where name does not contain UPDATED_NAME
        defaultCBudgetPlanShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description equals to DEFAULT_DESCRIPTION
        defaultCBudgetPlanShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBudgetPlanList where description equals to UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description not equals to DEFAULT_DESCRIPTION
        defaultCBudgetPlanShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBudgetPlanList where description not equals to UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBudgetPlanList where description equals to UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description is not null
        defaultCBudgetPlanShouldBeFound("description.specified=true");

        // Get all the cBudgetPlanList where description is null
        defaultCBudgetPlanShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description contains DEFAULT_DESCRIPTION
        defaultCBudgetPlanShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBudgetPlanList where description contains UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where description does not contain DEFAULT_DESCRIPTION
        defaultCBudgetPlanShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBudgetPlanList where description does not contain UPDATED_DESCRIPTION
        defaultCBudgetPlanShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction equals to DEFAULT_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldBeFound("budgetDeduction.equals=" + DEFAULT_BUDGET_DEDUCTION);

        // Get all the cBudgetPlanList where budgetDeduction equals to UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.equals=" + UPDATED_BUDGET_DEDUCTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction not equals to DEFAULT_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.notEquals=" + DEFAULT_BUDGET_DEDUCTION);

        // Get all the cBudgetPlanList where budgetDeduction not equals to UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldBeFound("budgetDeduction.notEquals=" + UPDATED_BUDGET_DEDUCTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction in DEFAULT_BUDGET_DEDUCTION or UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldBeFound("budgetDeduction.in=" + DEFAULT_BUDGET_DEDUCTION + "," + UPDATED_BUDGET_DEDUCTION);

        // Get all the cBudgetPlanList where budgetDeduction equals to UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.in=" + UPDATED_BUDGET_DEDUCTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction is not null
        defaultCBudgetPlanShouldBeFound("budgetDeduction.specified=true");

        // Get all the cBudgetPlanList where budgetDeduction is null
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction contains DEFAULT_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldBeFound("budgetDeduction.contains=" + DEFAULT_BUDGET_DEDUCTION);

        // Get all the cBudgetPlanList where budgetDeduction contains UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.contains=" + UPDATED_BUDGET_DEDUCTION);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetDeductionNotContainsSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetDeduction does not contain DEFAULT_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldNotBeFound("budgetDeduction.doesNotContain=" + DEFAULT_BUDGET_DEDUCTION);

        // Get all the cBudgetPlanList where budgetDeduction does not contain UPDATED_BUDGET_DEDUCTION
        defaultCBudgetPlanShouldBeFound("budgetDeduction.doesNotContain=" + UPDATED_BUDGET_DEDUCTION);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount equals to DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.equals=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount equals to UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.equals=" + UPDATED_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount not equals to DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.notEquals=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount not equals to UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.notEquals=" + UPDATED_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount in DEFAULT_BUDGET_AMOUNT or UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.in=" + DEFAULT_BUDGET_AMOUNT + "," + UPDATED_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount equals to UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.in=" + UPDATED_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount is not null
        defaultCBudgetPlanShouldBeFound("budgetAmount.specified=true");

        // Get all the cBudgetPlanList where budgetAmount is null
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount is greater than or equal to DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.greaterThanOrEqual=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount is greater than or equal to UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.greaterThanOrEqual=" + UPDATED_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount is less than or equal to DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.lessThanOrEqual=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount is less than or equal to SMALLER_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.lessThanOrEqual=" + SMALLER_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount is less than DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.lessThan=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount is less than UPDATED_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.lessThan=" + UPDATED_BUDGET_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByBudgetAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where budgetAmount is greater than DEFAULT_BUDGET_AMOUNT
        defaultCBudgetPlanShouldNotBeFound("budgetAmount.greaterThan=" + DEFAULT_BUDGET_AMOUNT);

        // Get all the cBudgetPlanList where budgetAmount is greater than SMALLER_BUDGET_AMOUNT
        defaultCBudgetPlanShouldBeFound("budgetAmount.greaterThan=" + SMALLER_BUDGET_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable equals to DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.equals=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable equals to UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.equals=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable not equals to DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.notEquals=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable not equals to UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.notEquals=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable in DEFAULT_AMOUNT_AVAILABLE or UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.in=" + DEFAULT_AMOUNT_AVAILABLE + "," + UPDATED_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable equals to UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.in=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable is not null
        defaultCBudgetPlanShouldBeFound("amountAvailable.specified=true");

        // Get all the cBudgetPlanList where amountAvailable is null
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable is greater than or equal to DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.greaterThanOrEqual=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable is greater than or equal to UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.greaterThanOrEqual=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable is less than or equal to DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.lessThanOrEqual=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable is less than or equal to SMALLER_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.lessThanOrEqual=" + SMALLER_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsLessThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable is less than DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.lessThan=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable is less than UPDATED_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.lessThan=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAmountAvailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where amountAvailable is greater than DEFAULT_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldNotBeFound("amountAvailable.greaterThan=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the cBudgetPlanList where amountAvailable is greater than SMALLER_AMOUNT_AVAILABLE
        defaultCBudgetPlanShouldBeFound("amountAvailable.greaterThan=" + SMALLER_AMOUNT_AVAILABLE);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate is not null
        defaultCBudgetPlanShouldBeFound("documentDate.specified=true");

        // Get all the cBudgetPlanList where documentDate is null
        defaultCBudgetPlanShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultCBudgetPlanShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the cBudgetPlanList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultCBudgetPlanShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByPreventOverBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where preventOverBudget equals to DEFAULT_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldBeFound("preventOverBudget.equals=" + DEFAULT_PREVENT_OVER_BUDGET);

        // Get all the cBudgetPlanList where preventOverBudget equals to UPDATED_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldNotBeFound("preventOverBudget.equals=" + UPDATED_PREVENT_OVER_BUDGET);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByPreventOverBudgetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where preventOverBudget not equals to DEFAULT_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldNotBeFound("preventOverBudget.notEquals=" + DEFAULT_PREVENT_OVER_BUDGET);

        // Get all the cBudgetPlanList where preventOverBudget not equals to UPDATED_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldBeFound("preventOverBudget.notEquals=" + UPDATED_PREVENT_OVER_BUDGET);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByPreventOverBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where preventOverBudget in DEFAULT_PREVENT_OVER_BUDGET or UPDATED_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldBeFound("preventOverBudget.in=" + DEFAULT_PREVENT_OVER_BUDGET + "," + UPDATED_PREVENT_OVER_BUDGET);

        // Get all the cBudgetPlanList where preventOverBudget equals to UPDATED_PREVENT_OVER_BUDGET
        defaultCBudgetPlanShouldNotBeFound("preventOverBudget.in=" + UPDATED_PREVENT_OVER_BUDGET);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByPreventOverBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where preventOverBudget is not null
        defaultCBudgetPlanShouldBeFound("preventOverBudget.specified=true");

        // Get all the cBudgetPlanList where preventOverBudget is null
        defaultCBudgetPlanShouldNotBeFound("preventOverBudget.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where uid equals to DEFAULT_UID
        defaultCBudgetPlanShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBudgetPlanList where uid equals to UPDATED_UID
        defaultCBudgetPlanShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where uid not equals to DEFAULT_UID
        defaultCBudgetPlanShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBudgetPlanList where uid not equals to UPDATED_UID
        defaultCBudgetPlanShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBudgetPlanShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBudgetPlanList where uid equals to UPDATED_UID
        defaultCBudgetPlanShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where uid is not null
        defaultCBudgetPlanShouldBeFound("uid.specified=true");

        // Get all the cBudgetPlanList where uid is null
        defaultCBudgetPlanShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where active equals to DEFAULT_ACTIVE
        defaultCBudgetPlanShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBudgetPlanList where active equals to UPDATED_ACTIVE
        defaultCBudgetPlanShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where active not equals to DEFAULT_ACTIVE
        defaultCBudgetPlanShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBudgetPlanList where active not equals to UPDATED_ACTIVE
        defaultCBudgetPlanShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBudgetPlanShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBudgetPlanList where active equals to UPDATED_ACTIVE
        defaultCBudgetPlanShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        // Get all the cBudgetPlanList where active is not null
        defaultCBudgetPlanShouldBeFound("active.specified=true");

        // Get all the cBudgetPlanList where active is null
        defaultCBudgetPlanShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlansByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBudgetPlan.getAdOrganization();
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBudgetPlanList where adOrganization equals to adOrganizationId
        defaultCBudgetPlanShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBudgetPlanList where adOrganization equals to adOrganizationId + 1
        defaultCBudgetPlanShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByCCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter cCostCenter = cBudgetPlan.getCCostCenter();
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);
        Long cCostCenterId = cCostCenter.getId();

        // Get all the cBudgetPlanList where cCostCenter equals to cCostCenterId
        defaultCBudgetPlanShouldBeFound("cCostCenterId.equals=" + cCostCenterId);

        // Get all the cBudgetPlanList where cCostCenter equals to cCostCenterId + 1
        defaultCBudgetPlanShouldNotBeFound("cCostCenterId.equals=" + (cCostCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlansByCCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency cCurrency = cBudgetPlan.getCCurrency();
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);
        Long cCurrencyId = cCurrency.getId();

        // Get all the cBudgetPlanList where cCurrency equals to cCurrencyId
        defaultCBudgetPlanShouldBeFound("cCurrencyId.equals=" + cCurrencyId);

        // Get all the cBudgetPlanList where cCurrency equals to cCurrencyId + 1
        defaultCBudgetPlanShouldNotBeFound("cCurrencyId.equals=" + (cCurrencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBudgetPlanShouldBeFound(String filter) throws Exception {
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBudgetPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].budgetDeduction").value(hasItem(DEFAULT_BUDGET_DEDUCTION)))
            .andExpect(jsonPath("$.[*].budgetAmount").value(hasItem(DEFAULT_BUDGET_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amountAvailable").value(hasItem(DEFAULT_AMOUNT_AVAILABLE.intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].preventOverBudget").value(hasItem(DEFAULT_PREVENT_OVER_BUDGET.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBudgetPlanShouldNotBeFound(String filter) throws Exception {
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBudgetPlan() throws Exception {
        // Get the cBudgetPlan
        restCBudgetPlanMockMvc.perform(get("/api/c-budget-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBudgetPlan() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        int databaseSizeBeforeUpdate = cBudgetPlanRepository.findAll().size();

        // Update the cBudgetPlan
        CBudgetPlan updatedCBudgetPlan = cBudgetPlanRepository.findById(cBudgetPlan.getId()).get();
        // Disconnect from session so that the updates on updatedCBudgetPlan are not directly saved in db
        em.detach(updatedCBudgetPlan);
        updatedCBudgetPlan
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .budgetDeduction(UPDATED_BUDGET_DEDUCTION)
            .budgetAmount(UPDATED_BUDGET_AMOUNT)
            .amountAvailable(UPDATED_AMOUNT_AVAILABLE)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .preventOverBudget(UPDATED_PREVENT_OVER_BUDGET)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(updatedCBudgetPlan);

        restCBudgetPlanMockMvc.perform(put("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isOk());

        // Validate the CBudgetPlan in the database
        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeUpdate);
        CBudgetPlan testCBudgetPlan = cBudgetPlanList.get(cBudgetPlanList.size() - 1);
        assertThat(testCBudgetPlan.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCBudgetPlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBudgetPlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBudgetPlan.getBudgetDeduction()).isEqualTo(UPDATED_BUDGET_DEDUCTION);
        assertThat(testCBudgetPlan.getBudgetAmount()).isEqualTo(UPDATED_BUDGET_AMOUNT);
        assertThat(testCBudgetPlan.getAmountAvailable()).isEqualTo(UPDATED_AMOUNT_AVAILABLE);
        assertThat(testCBudgetPlan.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testCBudgetPlan.isPreventOverBudget()).isEqualTo(UPDATED_PREVENT_OVER_BUDGET);
        assertThat(testCBudgetPlan.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBudgetPlan.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBudgetPlan() throws Exception {
        int databaseSizeBeforeUpdate = cBudgetPlanRepository.findAll().size();

        // Create the CBudgetPlan
        CBudgetPlanDTO cBudgetPlanDTO = cBudgetPlanMapper.toDto(cBudgetPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBudgetPlanMockMvc.perform(put("/api/c-budget-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBudgetPlan in the database
        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBudgetPlan() throws Exception {
        // Initialize the database
        cBudgetPlanRepository.saveAndFlush(cBudgetPlan);

        int databaseSizeBeforeDelete = cBudgetPlanRepository.findAll().size();

        // Delete the cBudgetPlan
        restCBudgetPlanMockMvc.perform(delete("/api/c-budget-plans/{id}", cBudgetPlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBudgetPlan> cBudgetPlanList = cBudgetPlanRepository.findAll();
        assertThat(cBudgetPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
