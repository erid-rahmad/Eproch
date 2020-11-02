package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProductGroupAccount;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.CProductGroup;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CProductGroupAccountRepository;
import com.bhp.opusb.service.CProductGroupAccountService;
import com.bhp.opusb.service.dto.CProductGroupAccountDTO;
import com.bhp.opusb.service.mapper.CProductGroupAccountMapper;
import com.bhp.opusb.service.dto.CProductGroupAccountCriteria;
import com.bhp.opusb.service.CProductGroupAccountQueryService;

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

import com.bhp.opusb.domain.enumeration.Depreciation;
/**
 * Integration tests for the {@link CProductGroupAccountResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductGroupAccountResourceIT {

    private static final Depreciation DEFAULT_DEPRECIATION = Depreciation.STRAIGHT_LINE;
    private static final Depreciation UPDATED_DEPRECIATION = Depreciation.DOUBLE_DECLINING;

    private static final Integer DEFAULT_LIFE_YEAR = 1;
    private static final Integer UPDATED_LIFE_YEAR = 2;
    private static final Integer SMALLER_LIFE_YEAR = 1 - 1;

    private static final Integer DEFAULT_LIFE_MONTH = 1;
    private static final Integer UPDATED_LIFE_MONTH = 2;
    private static final Integer SMALLER_LIFE_MONTH = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductGroupAccountRepository cProductGroupAccountRepository;

    @Autowired
    private CProductGroupAccountMapper cProductGroupAccountMapper;

    @Autowired
    private CProductGroupAccountService cProductGroupAccountService;

    @Autowired
    private CProductGroupAccountQueryService cProductGroupAccountQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductGroupAccountMockMvc;

    private CProductGroupAccount cProductGroupAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductGroupAccount createEntity(EntityManager em) {
        CProductGroupAccount cProductGroupAccount = new CProductGroupAccount()
            .depreciation(DEFAULT_DEPRECIATION)
            .lifeYear(DEFAULT_LIFE_YEAR)
            .lifeMonth(DEFAULT_LIFE_MONTH)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CProductGroup cProductGroup;
        if (TestUtil.findAll(em, CProductGroup.class).isEmpty()) {
            cProductGroup = CProductGroupResourceIT.createEntity(em);
            em.persist(cProductGroup);
            em.flush();
        } else {
            cProductGroup = TestUtil.findAll(em, CProductGroup.class).get(0);
        }
        cProductGroupAccount.setProductGroup(cProductGroup);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cProductGroupAccount.setAdOrganization(aDOrganization);
        return cProductGroupAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductGroupAccount createUpdatedEntity(EntityManager em) {
        CProductGroupAccount cProductGroupAccount = new CProductGroupAccount()
            .depreciation(UPDATED_DEPRECIATION)
            .lifeYear(UPDATED_LIFE_YEAR)
            .lifeMonth(UPDATED_LIFE_MONTH)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CProductGroup cProductGroup;
        if (TestUtil.findAll(em, CProductGroup.class).isEmpty()) {
            cProductGroup = CProductGroupResourceIT.createUpdatedEntity(em);
            em.persist(cProductGroup);
            em.flush();
        } else {
            cProductGroup = TestUtil.findAll(em, CProductGroup.class).get(0);
        }
        cProductGroupAccount.setProductGroup(cProductGroup);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cProductGroupAccount.setAdOrganization(aDOrganization);
        return cProductGroupAccount;
    }

    @BeforeEach
    public void initTest() {
        cProductGroupAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProductGroupAccount() throws Exception {
        int databaseSizeBeforeCreate = cProductGroupAccountRepository.findAll().size();

        // Create the CProductGroupAccount
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);
        restCProductGroupAccountMockMvc.perform(post("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CProductGroupAccount in the database
        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CProductGroupAccount testCProductGroupAccount = cProductGroupAccountList.get(cProductGroupAccountList.size() - 1);
        assertThat(testCProductGroupAccount.getDepreciation()).isEqualTo(DEFAULT_DEPRECIATION);
        assertThat(testCProductGroupAccount.getLifeYear()).isEqualTo(DEFAULT_LIFE_YEAR);
        assertThat(testCProductGroupAccount.getLifeMonth()).isEqualTo(DEFAULT_LIFE_MONTH);
        assertThat(testCProductGroupAccount.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProductGroupAccount.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductGroupAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductGroupAccountRepository.findAll().size();

        // Create the CProductGroupAccount with an existing ID
        cProductGroupAccount.setId(1L);
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductGroupAccountMockMvc.perform(post("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductGroupAccount in the database
        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDepreciationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductGroupAccountRepository.findAll().size();
        // set the field null
        cProductGroupAccount.setDepreciation(null);

        // Create the CProductGroupAccount, which fails.
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);

        restCProductGroupAccountMockMvc.perform(post("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isBadRequest());

        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLifeYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductGroupAccountRepository.findAll().size();
        // set the field null
        cProductGroupAccount.setLifeYear(null);

        // Create the CProductGroupAccount, which fails.
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);

        restCProductGroupAccountMockMvc.perform(post("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isBadRequest());

        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLifeMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductGroupAccountRepository.findAll().size();
        // set the field null
        cProductGroupAccount.setLifeMonth(null);

        // Create the CProductGroupAccount, which fails.
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);

        restCProductGroupAccountMockMvc.perform(post("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isBadRequest());

        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccounts() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductGroupAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].depreciation").value(hasItem(DEFAULT_DEPRECIATION.toString())))
            .andExpect(jsonPath("$.[*].lifeYear").value(hasItem(DEFAULT_LIFE_YEAR)))
            .andExpect(jsonPath("$.[*].lifeMonth").value(hasItem(DEFAULT_LIFE_MONTH)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProductGroupAccount() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get the cProductGroupAccount
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts/{id}", cProductGroupAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProductGroupAccount.getId().intValue()))
            .andExpect(jsonPath("$.depreciation").value(DEFAULT_DEPRECIATION.toString()))
            .andExpect(jsonPath("$.lifeYear").value(DEFAULT_LIFE_YEAR))
            .andExpect(jsonPath("$.lifeMonth").value(DEFAULT_LIFE_MONTH))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductGroupAccountsByIdFiltering() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        Long id = cProductGroupAccount.getId();

        defaultCProductGroupAccountShouldBeFound("id.equals=" + id);
        defaultCProductGroupAccountShouldNotBeFound("id.notEquals=" + id);

        defaultCProductGroupAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductGroupAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductGroupAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductGroupAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByDepreciationIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where depreciation equals to DEFAULT_DEPRECIATION
        defaultCProductGroupAccountShouldBeFound("depreciation.equals=" + DEFAULT_DEPRECIATION);

        // Get all the cProductGroupAccountList where depreciation equals to UPDATED_DEPRECIATION
        defaultCProductGroupAccountShouldNotBeFound("depreciation.equals=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByDepreciationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where depreciation not equals to DEFAULT_DEPRECIATION
        defaultCProductGroupAccountShouldNotBeFound("depreciation.notEquals=" + DEFAULT_DEPRECIATION);

        // Get all the cProductGroupAccountList where depreciation not equals to UPDATED_DEPRECIATION
        defaultCProductGroupAccountShouldBeFound("depreciation.notEquals=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByDepreciationIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where depreciation in DEFAULT_DEPRECIATION or UPDATED_DEPRECIATION
        defaultCProductGroupAccountShouldBeFound("depreciation.in=" + DEFAULT_DEPRECIATION + "," + UPDATED_DEPRECIATION);

        // Get all the cProductGroupAccountList where depreciation equals to UPDATED_DEPRECIATION
        defaultCProductGroupAccountShouldNotBeFound("depreciation.in=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByDepreciationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where depreciation is not null
        defaultCProductGroupAccountShouldBeFound("depreciation.specified=true");

        // Get all the cProductGroupAccountList where depreciation is null
        defaultCProductGroupAccountShouldNotBeFound("depreciation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear equals to DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.equals=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear equals to UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.equals=" + UPDATED_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear not equals to DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.notEquals=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear not equals to UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.notEquals=" + UPDATED_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear in DEFAULT_LIFE_YEAR or UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.in=" + DEFAULT_LIFE_YEAR + "," + UPDATED_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear equals to UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.in=" + UPDATED_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear is not null
        defaultCProductGroupAccountShouldBeFound("lifeYear.specified=true");

        // Get all the cProductGroupAccountList where lifeYear is null
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear is greater than or equal to DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.greaterThanOrEqual=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear is greater than or equal to UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.greaterThanOrEqual=" + UPDATED_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear is less than or equal to DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.lessThanOrEqual=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear is less than or equal to SMALLER_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.lessThanOrEqual=" + SMALLER_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsLessThanSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear is less than DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.lessThan=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear is less than UPDATED_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.lessThan=" + UPDATED_LIFE_YEAR);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeYear is greater than DEFAULT_LIFE_YEAR
        defaultCProductGroupAccountShouldNotBeFound("lifeYear.greaterThan=" + DEFAULT_LIFE_YEAR);

        // Get all the cProductGroupAccountList where lifeYear is greater than SMALLER_LIFE_YEAR
        defaultCProductGroupAccountShouldBeFound("lifeYear.greaterThan=" + SMALLER_LIFE_YEAR);
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth equals to DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.equals=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth equals to UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.equals=" + UPDATED_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth not equals to DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.notEquals=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth not equals to UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.notEquals=" + UPDATED_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth in DEFAULT_LIFE_MONTH or UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.in=" + DEFAULT_LIFE_MONTH + "," + UPDATED_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth equals to UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.in=" + UPDATED_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth is not null
        defaultCProductGroupAccountShouldBeFound("lifeMonth.specified=true");

        // Get all the cProductGroupAccountList where lifeMonth is null
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth is greater than or equal to DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.greaterThanOrEqual=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth is greater than or equal to UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.greaterThanOrEqual=" + UPDATED_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth is less than or equal to DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.lessThanOrEqual=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth is less than or equal to SMALLER_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.lessThanOrEqual=" + SMALLER_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth is less than DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.lessThan=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth is less than UPDATED_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.lessThan=" + UPDATED_LIFE_MONTH);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByLifeMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where lifeMonth is greater than DEFAULT_LIFE_MONTH
        defaultCProductGroupAccountShouldNotBeFound("lifeMonth.greaterThan=" + DEFAULT_LIFE_MONTH);

        // Get all the cProductGroupAccountList where lifeMonth is greater than SMALLER_LIFE_MONTH
        defaultCProductGroupAccountShouldBeFound("lifeMonth.greaterThan=" + SMALLER_LIFE_MONTH);
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where uid equals to DEFAULT_UID
        defaultCProductGroupAccountShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductGroupAccountList where uid equals to UPDATED_UID
        defaultCProductGroupAccountShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where uid not equals to DEFAULT_UID
        defaultCProductGroupAccountShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductGroupAccountList where uid not equals to UPDATED_UID
        defaultCProductGroupAccountShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductGroupAccountShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductGroupAccountList where uid equals to UPDATED_UID
        defaultCProductGroupAccountShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where uid is not null
        defaultCProductGroupAccountShouldBeFound("uid.specified=true");

        // Get all the cProductGroupAccountList where uid is null
        defaultCProductGroupAccountShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where active equals to DEFAULT_ACTIVE
        defaultCProductGroupAccountShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductGroupAccountList where active equals to UPDATED_ACTIVE
        defaultCProductGroupAccountShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where active not equals to DEFAULT_ACTIVE
        defaultCProductGroupAccountShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductGroupAccountList where active not equals to UPDATED_ACTIVE
        defaultCProductGroupAccountShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductGroupAccountShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductGroupAccountList where active equals to UPDATED_ACTIVE
        defaultCProductGroupAccountShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        // Get all the cProductGroupAccountList where active is not null
        defaultCProductGroupAccountShouldBeFound("active.specified=true");

        // Get all the cProductGroupAccountList where active is null
        defaultCProductGroupAccountShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupAccountsByAssetAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        CElementValue assetAccount = CElementValueResourceIT.createEntity(em);
        em.persist(assetAccount);
        em.flush();
        cProductGroupAccount.setAssetAccount(assetAccount);
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        Long assetAccountId = assetAccount.getId();

        // Get all the cProductGroupAccountList where assetAccount equals to assetAccountId
        defaultCProductGroupAccountShouldBeFound("assetAccountId.equals=" + assetAccountId);

        // Get all the cProductGroupAccountList where assetAccount equals to assetAccountId + 1
        defaultCProductGroupAccountShouldNotBeFound("assetAccountId.equals=" + (assetAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByDepreciationAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        CElementValue depreciationAccount = CElementValueResourceIT.createEntity(em);
        em.persist(depreciationAccount);
        em.flush();
        cProductGroupAccount.setDepreciationAccount(depreciationAccount);
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        Long depreciationAccountId = depreciationAccount.getId();

        // Get all the cProductGroupAccountList where depreciationAccount equals to depreciationAccountId
        defaultCProductGroupAccountShouldBeFound("depreciationAccountId.equals=" + depreciationAccountId);

        // Get all the cProductGroupAccountList where depreciationAccount equals to depreciationAccountId + 1
        defaultCProductGroupAccountShouldNotBeFound("depreciationAccountId.equals=" + (depreciationAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByProductGroupIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductGroup productGroup = cProductGroupAccount.getProductGroup();
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        Long productGroupId = productGroup.getId();

        // Get all the cProductGroupAccountList where productGroup equals to productGroupId
        defaultCProductGroupAccountShouldBeFound("productGroupId.equals=" + productGroupId);

        // Get all the cProductGroupAccountList where productGroup equals to productGroupId + 1
        defaultCProductGroupAccountShouldNotBeFound("productGroupId.equals=" + (productGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductGroupAccountsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProductGroupAccount.getAdOrganization();
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductGroupAccountList where adOrganization equals to adOrganizationId
        defaultCProductGroupAccountShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductGroupAccountList where adOrganization equals to adOrganizationId + 1
        defaultCProductGroupAccountShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductGroupAccountShouldBeFound(String filter) throws Exception {
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductGroupAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].depreciation").value(hasItem(DEFAULT_DEPRECIATION.toString())))
            .andExpect(jsonPath("$.[*].lifeYear").value(hasItem(DEFAULT_LIFE_YEAR)))
            .andExpect(jsonPath("$.[*].lifeMonth").value(hasItem(DEFAULT_LIFE_MONTH)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductGroupAccountShouldNotBeFound(String filter) throws Exception {
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProductGroupAccount() throws Exception {
        // Get the cProductGroupAccount
        restCProductGroupAccountMockMvc.perform(get("/api/c-product-group-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProductGroupAccount() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        int databaseSizeBeforeUpdate = cProductGroupAccountRepository.findAll().size();

        // Update the cProductGroupAccount
        CProductGroupAccount updatedCProductGroupAccount = cProductGroupAccountRepository.findById(cProductGroupAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCProductGroupAccount are not directly saved in db
        em.detach(updatedCProductGroupAccount);
        updatedCProductGroupAccount
            .depreciation(UPDATED_DEPRECIATION)
            .lifeYear(UPDATED_LIFE_YEAR)
            .lifeMonth(UPDATED_LIFE_MONTH)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(updatedCProductGroupAccount);

        restCProductGroupAccountMockMvc.perform(put("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CProductGroupAccount in the database
        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeUpdate);
        CProductGroupAccount testCProductGroupAccount = cProductGroupAccountList.get(cProductGroupAccountList.size() - 1);
        assertThat(testCProductGroupAccount.getDepreciation()).isEqualTo(UPDATED_DEPRECIATION);
        assertThat(testCProductGroupAccount.getLifeYear()).isEqualTo(UPDATED_LIFE_YEAR);
        assertThat(testCProductGroupAccount.getLifeMonth()).isEqualTo(UPDATED_LIFE_MONTH);
        assertThat(testCProductGroupAccount.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProductGroupAccount.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProductGroupAccount() throws Exception {
        int databaseSizeBeforeUpdate = cProductGroupAccountRepository.findAll().size();

        // Create the CProductGroupAccount
        CProductGroupAccountDTO cProductGroupAccountDTO = cProductGroupAccountMapper.toDto(cProductGroupAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductGroupAccountMockMvc.perform(put("/api/c-product-group-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductGroupAccount in the database
        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProductGroupAccount() throws Exception {
        // Initialize the database
        cProductGroupAccountRepository.saveAndFlush(cProductGroupAccount);

        int databaseSizeBeforeDelete = cProductGroupAccountRepository.findAll().size();

        // Delete the cProductGroupAccount
        restCProductGroupAccountMockMvc.perform(delete("/api/c-product-group-accounts/{id}", cProductGroupAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProductGroupAccount> cProductGroupAccountList = cProductGroupAccountRepository.findAll();
        assertThat(cProductGroupAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
