package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVerificationTax;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.repository.MVerificationTaxRepository;
import com.bhp.opusb.service.MVerificationTaxService;
import com.bhp.opusb.service.dto.MVerificationTaxDTO;
import com.bhp.opusb.service.mapper.MVerificationTaxMapper;
import com.bhp.opusb.service.dto.MVerificationTaxCriteria;
import com.bhp.opusb.service.MVerificationTaxQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVerificationTaxResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVerificationTaxResourceIT {

    private static final Long DEFAULT_TAX_PERIOD = 1L;
    private static final Long UPDATED_TAX_PERIOD = 2L;
    private static final Long SMALLER_TAX_PERIOD = 1L - 1L;

    private static final String DEFAULT_TRAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRAX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;
    private static final Long SMALLER_YEAR = 1L - 1L;

    private static final String DEFAULT_RETURN_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_DOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REP_SER_NO = "AAAAAAAAAA";
    private static final String UPDATED_REP_SER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_EXP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_EXP_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SSP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SSP = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_SSP = LocalDate.ofEpochDay(-1L);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVerificationTaxRepository mVerificationTaxRepository;

    @Autowired
    private MVerificationTaxMapper mVerificationTaxMapper;

    @Autowired
    private MVerificationTaxService mVerificationTaxService;

    @Autowired
    private MVerificationTaxQueryService mVerificationTaxQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVerificationTaxMockMvc;

    private MVerificationTax mVerificationTax;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerificationTax createEntity(EntityManager em) {
        MVerificationTax mVerificationTax = new MVerificationTax()
            .taxPeriod(DEFAULT_TAX_PERIOD)
            .traxCode(DEFAULT_TRAX_CODE)
            .statusCode(DEFAULT_STATUS_CODE)
            .docCode(DEFAULT_DOC_CODE)
            .year(DEFAULT_YEAR)
            .returnDocType(DEFAULT_RETURN_DOC_TYPE)
            .repSerNo(DEFAULT_REP_SER_NO)
            .taxExpCode(DEFAULT_TAX_EXP_CODE)
            .dateSSP(DEFAULT_DATE_SSP)
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
        mVerificationTax.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerificationTax.setCurrency(cCurrency);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        mVerificationTax.setTaxCategory(cTaxCategory);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVerificationTax.setCostCenter(cCostCenter);
        return mVerificationTax;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerificationTax createUpdatedEntity(EntityManager em) {
        MVerificationTax mVerificationTax = new MVerificationTax()
            .taxPeriod(UPDATED_TAX_PERIOD)
            .traxCode(UPDATED_TRAX_CODE)
            .statusCode(UPDATED_STATUS_CODE)
            .docCode(UPDATED_DOC_CODE)
            .year(UPDATED_YEAR)
            .returnDocType(UPDATED_RETURN_DOC_TYPE)
            .repSerNo(UPDATED_REP_SER_NO)
            .taxExpCode(UPDATED_TAX_EXP_CODE)
            .dateSSP(UPDATED_DATE_SSP)
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
        mVerificationTax.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerificationTax.setCurrency(cCurrency);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        mVerificationTax.setTaxCategory(cTaxCategory);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVerificationTax.setCostCenter(cCostCenter);
        return mVerificationTax;
    }

    @BeforeEach
    public void initTest() {
        mVerificationTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVerificationTax() throws Exception {
        int databaseSizeBeforeCreate = mVerificationTaxRepository.findAll().size();

        // Create the MVerificationTax
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);
        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isCreated());

        // Validate the MVerificationTax in the database
        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeCreate + 1);
        MVerificationTax testMVerificationTax = mVerificationTaxList.get(mVerificationTaxList.size() - 1);
        assertThat(testMVerificationTax.getTaxPeriod()).isEqualTo(DEFAULT_TAX_PERIOD);
        assertThat(testMVerificationTax.getTraxCode()).isEqualTo(DEFAULT_TRAX_CODE);
        assertThat(testMVerificationTax.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testMVerificationTax.getDocCode()).isEqualTo(DEFAULT_DOC_CODE);
        assertThat(testMVerificationTax.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMVerificationTax.getReturnDocType()).isEqualTo(DEFAULT_RETURN_DOC_TYPE);
        assertThat(testMVerificationTax.getRepSerNo()).isEqualTo(DEFAULT_REP_SER_NO);
        assertThat(testMVerificationTax.getTaxExpCode()).isEqualTo(DEFAULT_TAX_EXP_CODE);
        assertThat(testMVerificationTax.getDateSSP()).isEqualTo(DEFAULT_DATE_SSP);
        assertThat(testMVerificationTax.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVerificationTax.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVerificationTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVerificationTaxRepository.findAll().size();

        // Create the MVerificationTax with an existing ID
        mVerificationTax.setId(1L);
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerificationTax in the database
        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaxPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationTaxRepository.findAll().size();
        // set the field null
        mVerificationTax.setTaxPeriod(null);

        // Create the MVerificationTax, which fails.
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTraxCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationTaxRepository.findAll().size();
        // set the field null
        mVerificationTax.setTraxCode(null);

        // Create the MVerificationTax, which fails.
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationTaxRepository.findAll().size();
        // set the field null
        mVerificationTax.setStatusCode(null);

        // Create the MVerificationTax, which fails.
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationTaxRepository.findAll().size();
        // set the field null
        mVerificationTax.setDocCode(null);

        // Create the MVerificationTax, which fails.
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationTaxRepository.findAll().size();
        // set the field null
        mVerificationTax.setYear(null);

        // Create the MVerificationTax, which fails.
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        restMVerificationTaxMockMvc.perform(post("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxes() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerificationTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxPeriod").value(hasItem(DEFAULT_TAX_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].traxCode").value(hasItem(DEFAULT_TRAX_CODE)))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].docCode").value(hasItem(DEFAULT_DOC_CODE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].returnDocType").value(hasItem(DEFAULT_RETURN_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].repSerNo").value(hasItem(DEFAULT_REP_SER_NO)))
            .andExpect(jsonPath("$.[*].taxExpCode").value(hasItem(DEFAULT_TAX_EXP_CODE)))
            .andExpect(jsonPath("$.[*].dateSSP").value(hasItem(DEFAULT_DATE_SSP.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVerificationTax() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get the mVerificationTax
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes/{id}", mVerificationTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVerificationTax.getId().intValue()))
            .andExpect(jsonPath("$.taxPeriod").value(DEFAULT_TAX_PERIOD.intValue()))
            .andExpect(jsonPath("$.traxCode").value(DEFAULT_TRAX_CODE))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE))
            .andExpect(jsonPath("$.docCode").value(DEFAULT_DOC_CODE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.returnDocType").value(DEFAULT_RETURN_DOC_TYPE))
            .andExpect(jsonPath("$.repSerNo").value(DEFAULT_REP_SER_NO))
            .andExpect(jsonPath("$.taxExpCode").value(DEFAULT_TAX_EXP_CODE))
            .andExpect(jsonPath("$.dateSSP").value(DEFAULT_DATE_SSP.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVerificationTaxesByIdFiltering() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        Long id = mVerificationTax.getId();

        defaultMVerificationTaxShouldBeFound("id.equals=" + id);
        defaultMVerificationTaxShouldNotBeFound("id.notEquals=" + id);

        defaultMVerificationTaxShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVerificationTaxShouldNotBeFound("id.greaterThan=" + id);

        defaultMVerificationTaxShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVerificationTaxShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod equals to DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.equals=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod equals to UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.equals=" + UPDATED_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod not equals to DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.notEquals=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod not equals to UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.notEquals=" + UPDATED_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod in DEFAULT_TAX_PERIOD or UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.in=" + DEFAULT_TAX_PERIOD + "," + UPDATED_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod equals to UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.in=" + UPDATED_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod is not null
        defaultMVerificationTaxShouldBeFound("taxPeriod.specified=true");

        // Get all the mVerificationTaxList where taxPeriod is null
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod is greater than or equal to DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.greaterThanOrEqual=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod is greater than or equal to UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.greaterThanOrEqual=" + UPDATED_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod is less than or equal to DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.lessThanOrEqual=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod is less than or equal to SMALLER_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.lessThanOrEqual=" + SMALLER_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod is less than DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.lessThan=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod is less than UPDATED_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.lessThan=" + UPDATED_TAX_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxPeriod is greater than DEFAULT_TAX_PERIOD
        defaultMVerificationTaxShouldNotBeFound("taxPeriod.greaterThan=" + DEFAULT_TAX_PERIOD);

        // Get all the mVerificationTaxList where taxPeriod is greater than SMALLER_TAX_PERIOD
        defaultMVerificationTaxShouldBeFound("taxPeriod.greaterThan=" + SMALLER_TAX_PERIOD);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode equals to DEFAULT_TRAX_CODE
        defaultMVerificationTaxShouldBeFound("traxCode.equals=" + DEFAULT_TRAX_CODE);

        // Get all the mVerificationTaxList where traxCode equals to UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldNotBeFound("traxCode.equals=" + UPDATED_TRAX_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode not equals to DEFAULT_TRAX_CODE
        defaultMVerificationTaxShouldNotBeFound("traxCode.notEquals=" + DEFAULT_TRAX_CODE);

        // Get all the mVerificationTaxList where traxCode not equals to UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldBeFound("traxCode.notEquals=" + UPDATED_TRAX_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode in DEFAULT_TRAX_CODE or UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldBeFound("traxCode.in=" + DEFAULT_TRAX_CODE + "," + UPDATED_TRAX_CODE);

        // Get all the mVerificationTaxList where traxCode equals to UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldNotBeFound("traxCode.in=" + UPDATED_TRAX_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode is not null
        defaultMVerificationTaxShouldBeFound("traxCode.specified=true");

        // Get all the mVerificationTaxList where traxCode is null
        defaultMVerificationTaxShouldNotBeFound("traxCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode contains DEFAULT_TRAX_CODE
        defaultMVerificationTaxShouldBeFound("traxCode.contains=" + DEFAULT_TRAX_CODE);

        // Get all the mVerificationTaxList where traxCode contains UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldNotBeFound("traxCode.contains=" + UPDATED_TRAX_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTraxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where traxCode does not contain DEFAULT_TRAX_CODE
        defaultMVerificationTaxShouldNotBeFound("traxCode.doesNotContain=" + DEFAULT_TRAX_CODE);

        // Get all the mVerificationTaxList where traxCode does not contain UPDATED_TRAX_CODE
        defaultMVerificationTaxShouldBeFound("traxCode.doesNotContain=" + UPDATED_TRAX_CODE);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode equals to DEFAULT_STATUS_CODE
        defaultMVerificationTaxShouldBeFound("statusCode.equals=" + DEFAULT_STATUS_CODE);

        // Get all the mVerificationTaxList where statusCode equals to UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldNotBeFound("statusCode.equals=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode not equals to DEFAULT_STATUS_CODE
        defaultMVerificationTaxShouldNotBeFound("statusCode.notEquals=" + DEFAULT_STATUS_CODE);

        // Get all the mVerificationTaxList where statusCode not equals to UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldBeFound("statusCode.notEquals=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode in DEFAULT_STATUS_CODE or UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldBeFound("statusCode.in=" + DEFAULT_STATUS_CODE + "," + UPDATED_STATUS_CODE);

        // Get all the mVerificationTaxList where statusCode equals to UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldNotBeFound("statusCode.in=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode is not null
        defaultMVerificationTaxShouldBeFound("statusCode.specified=true");

        // Get all the mVerificationTaxList where statusCode is null
        defaultMVerificationTaxShouldNotBeFound("statusCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode contains DEFAULT_STATUS_CODE
        defaultMVerificationTaxShouldBeFound("statusCode.contains=" + DEFAULT_STATUS_CODE);

        // Get all the mVerificationTaxList where statusCode contains UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldNotBeFound("statusCode.contains=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByStatusCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where statusCode does not contain DEFAULT_STATUS_CODE
        defaultMVerificationTaxShouldNotBeFound("statusCode.doesNotContain=" + DEFAULT_STATUS_CODE);

        // Get all the mVerificationTaxList where statusCode does not contain UPDATED_STATUS_CODE
        defaultMVerificationTaxShouldBeFound("statusCode.doesNotContain=" + UPDATED_STATUS_CODE);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode equals to DEFAULT_DOC_CODE
        defaultMVerificationTaxShouldBeFound("docCode.equals=" + DEFAULT_DOC_CODE);

        // Get all the mVerificationTaxList where docCode equals to UPDATED_DOC_CODE
        defaultMVerificationTaxShouldNotBeFound("docCode.equals=" + UPDATED_DOC_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode not equals to DEFAULT_DOC_CODE
        defaultMVerificationTaxShouldNotBeFound("docCode.notEquals=" + DEFAULT_DOC_CODE);

        // Get all the mVerificationTaxList where docCode not equals to UPDATED_DOC_CODE
        defaultMVerificationTaxShouldBeFound("docCode.notEquals=" + UPDATED_DOC_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode in DEFAULT_DOC_CODE or UPDATED_DOC_CODE
        defaultMVerificationTaxShouldBeFound("docCode.in=" + DEFAULT_DOC_CODE + "," + UPDATED_DOC_CODE);

        // Get all the mVerificationTaxList where docCode equals to UPDATED_DOC_CODE
        defaultMVerificationTaxShouldNotBeFound("docCode.in=" + UPDATED_DOC_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode is not null
        defaultMVerificationTaxShouldBeFound("docCode.specified=true");

        // Get all the mVerificationTaxList where docCode is null
        defaultMVerificationTaxShouldNotBeFound("docCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode contains DEFAULT_DOC_CODE
        defaultMVerificationTaxShouldBeFound("docCode.contains=" + DEFAULT_DOC_CODE);

        // Get all the mVerificationTaxList where docCode contains UPDATED_DOC_CODE
        defaultMVerificationTaxShouldNotBeFound("docCode.contains=" + UPDATED_DOC_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDocCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where docCode does not contain DEFAULT_DOC_CODE
        defaultMVerificationTaxShouldNotBeFound("docCode.doesNotContain=" + DEFAULT_DOC_CODE);

        // Get all the mVerificationTaxList where docCode does not contain UPDATED_DOC_CODE
        defaultMVerificationTaxShouldBeFound("docCode.doesNotContain=" + UPDATED_DOC_CODE);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year equals to DEFAULT_YEAR
        defaultMVerificationTaxShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year equals to UPDATED_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year not equals to DEFAULT_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year not equals to UPDATED_YEAR
        defaultMVerificationTaxShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultMVerificationTaxShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the mVerificationTaxList where year equals to UPDATED_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year is not null
        defaultMVerificationTaxShouldBeFound("year.specified=true");

        // Get all the mVerificationTaxList where year is null
        defaultMVerificationTaxShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year is greater than or equal to DEFAULT_YEAR
        defaultMVerificationTaxShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year is greater than or equal to UPDATED_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year is less than or equal to DEFAULT_YEAR
        defaultMVerificationTaxShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year is less than or equal to SMALLER_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year is less than DEFAULT_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year is less than UPDATED_YEAR
        defaultMVerificationTaxShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where year is greater than DEFAULT_YEAR
        defaultMVerificationTaxShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the mVerificationTaxList where year is greater than SMALLER_YEAR
        defaultMVerificationTaxShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType equals to DEFAULT_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldBeFound("returnDocType.equals=" + DEFAULT_RETURN_DOC_TYPE);

        // Get all the mVerificationTaxList where returnDocType equals to UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldNotBeFound("returnDocType.equals=" + UPDATED_RETURN_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType not equals to DEFAULT_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldNotBeFound("returnDocType.notEquals=" + DEFAULT_RETURN_DOC_TYPE);

        // Get all the mVerificationTaxList where returnDocType not equals to UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldBeFound("returnDocType.notEquals=" + UPDATED_RETURN_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType in DEFAULT_RETURN_DOC_TYPE or UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldBeFound("returnDocType.in=" + DEFAULT_RETURN_DOC_TYPE + "," + UPDATED_RETURN_DOC_TYPE);

        // Get all the mVerificationTaxList where returnDocType equals to UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldNotBeFound("returnDocType.in=" + UPDATED_RETURN_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType is not null
        defaultMVerificationTaxShouldBeFound("returnDocType.specified=true");

        // Get all the mVerificationTaxList where returnDocType is null
        defaultMVerificationTaxShouldNotBeFound("returnDocType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType contains DEFAULT_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldBeFound("returnDocType.contains=" + DEFAULT_RETURN_DOC_TYPE);

        // Get all the mVerificationTaxList where returnDocType contains UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldNotBeFound("returnDocType.contains=" + UPDATED_RETURN_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByReturnDocTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where returnDocType does not contain DEFAULT_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldNotBeFound("returnDocType.doesNotContain=" + DEFAULT_RETURN_DOC_TYPE);

        // Get all the mVerificationTaxList where returnDocType does not contain UPDATED_RETURN_DOC_TYPE
        defaultMVerificationTaxShouldBeFound("returnDocType.doesNotContain=" + UPDATED_RETURN_DOC_TYPE);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo equals to DEFAULT_REP_SER_NO
        defaultMVerificationTaxShouldBeFound("repSerNo.equals=" + DEFAULT_REP_SER_NO);

        // Get all the mVerificationTaxList where repSerNo equals to UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldNotBeFound("repSerNo.equals=" + UPDATED_REP_SER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo not equals to DEFAULT_REP_SER_NO
        defaultMVerificationTaxShouldNotBeFound("repSerNo.notEquals=" + DEFAULT_REP_SER_NO);

        // Get all the mVerificationTaxList where repSerNo not equals to UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldBeFound("repSerNo.notEquals=" + UPDATED_REP_SER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo in DEFAULT_REP_SER_NO or UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldBeFound("repSerNo.in=" + DEFAULT_REP_SER_NO + "," + UPDATED_REP_SER_NO);

        // Get all the mVerificationTaxList where repSerNo equals to UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldNotBeFound("repSerNo.in=" + UPDATED_REP_SER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo is not null
        defaultMVerificationTaxShouldBeFound("repSerNo.specified=true");

        // Get all the mVerificationTaxList where repSerNo is null
        defaultMVerificationTaxShouldNotBeFound("repSerNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo contains DEFAULT_REP_SER_NO
        defaultMVerificationTaxShouldBeFound("repSerNo.contains=" + DEFAULT_REP_SER_NO);

        // Get all the mVerificationTaxList where repSerNo contains UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldNotBeFound("repSerNo.contains=" + UPDATED_REP_SER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByRepSerNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where repSerNo does not contain DEFAULT_REP_SER_NO
        defaultMVerificationTaxShouldNotBeFound("repSerNo.doesNotContain=" + DEFAULT_REP_SER_NO);

        // Get all the mVerificationTaxList where repSerNo does not contain UPDATED_REP_SER_NO
        defaultMVerificationTaxShouldBeFound("repSerNo.doesNotContain=" + UPDATED_REP_SER_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode equals to DEFAULT_TAX_EXP_CODE
        defaultMVerificationTaxShouldBeFound("taxExpCode.equals=" + DEFAULT_TAX_EXP_CODE);

        // Get all the mVerificationTaxList where taxExpCode equals to UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.equals=" + UPDATED_TAX_EXP_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode not equals to DEFAULT_TAX_EXP_CODE
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.notEquals=" + DEFAULT_TAX_EXP_CODE);

        // Get all the mVerificationTaxList where taxExpCode not equals to UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldBeFound("taxExpCode.notEquals=" + UPDATED_TAX_EXP_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode in DEFAULT_TAX_EXP_CODE or UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldBeFound("taxExpCode.in=" + DEFAULT_TAX_EXP_CODE + "," + UPDATED_TAX_EXP_CODE);

        // Get all the mVerificationTaxList where taxExpCode equals to UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.in=" + UPDATED_TAX_EXP_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode is not null
        defaultMVerificationTaxShouldBeFound("taxExpCode.specified=true");

        // Get all the mVerificationTaxList where taxExpCode is null
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode contains DEFAULT_TAX_EXP_CODE
        defaultMVerificationTaxShouldBeFound("taxExpCode.contains=" + DEFAULT_TAX_EXP_CODE);

        // Get all the mVerificationTaxList where taxExpCode contains UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.contains=" + UPDATED_TAX_EXP_CODE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxExpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where taxExpCode does not contain DEFAULT_TAX_EXP_CODE
        defaultMVerificationTaxShouldNotBeFound("taxExpCode.doesNotContain=" + DEFAULT_TAX_EXP_CODE);

        // Get all the mVerificationTaxList where taxExpCode does not contain UPDATED_TAX_EXP_CODE
        defaultMVerificationTaxShouldBeFound("taxExpCode.doesNotContain=" + UPDATED_TAX_EXP_CODE);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP equals to DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.equals=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP equals to UPDATED_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.equals=" + UPDATED_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP not equals to DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.notEquals=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP not equals to UPDATED_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.notEquals=" + UPDATED_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP in DEFAULT_DATE_SSP or UPDATED_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.in=" + DEFAULT_DATE_SSP + "," + UPDATED_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP equals to UPDATED_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.in=" + UPDATED_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP is not null
        defaultMVerificationTaxShouldBeFound("dateSSP.specified=true");

        // Get all the mVerificationTaxList where dateSSP is null
        defaultMVerificationTaxShouldNotBeFound("dateSSP.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP is greater than or equal to DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.greaterThanOrEqual=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP is greater than or equal to UPDATED_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.greaterThanOrEqual=" + UPDATED_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP is less than or equal to DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.lessThanOrEqual=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP is less than or equal to SMALLER_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.lessThanOrEqual=" + SMALLER_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP is less than DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.lessThan=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP is less than UPDATED_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.lessThan=" + UPDATED_DATE_SSP);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByDateSSPIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where dateSSP is greater than DEFAULT_DATE_SSP
        defaultMVerificationTaxShouldNotBeFound("dateSSP.greaterThan=" + DEFAULT_DATE_SSP);

        // Get all the mVerificationTaxList where dateSSP is greater than SMALLER_DATE_SSP
        defaultMVerificationTaxShouldBeFound("dateSSP.greaterThan=" + SMALLER_DATE_SSP);
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where uid equals to DEFAULT_UID
        defaultMVerificationTaxShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVerificationTaxList where uid equals to UPDATED_UID
        defaultMVerificationTaxShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where uid not equals to DEFAULT_UID
        defaultMVerificationTaxShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVerificationTaxList where uid not equals to UPDATED_UID
        defaultMVerificationTaxShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVerificationTaxShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVerificationTaxList where uid equals to UPDATED_UID
        defaultMVerificationTaxShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where uid is not null
        defaultMVerificationTaxShouldBeFound("uid.specified=true");

        // Get all the mVerificationTaxList where uid is null
        defaultMVerificationTaxShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where active equals to DEFAULT_ACTIVE
        defaultMVerificationTaxShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationTaxList where active equals to UPDATED_ACTIVE
        defaultMVerificationTaxShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where active not equals to DEFAULT_ACTIVE
        defaultMVerificationTaxShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationTaxList where active not equals to UPDATED_ACTIVE
        defaultMVerificationTaxShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVerificationTaxShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVerificationTaxList where active equals to UPDATED_ACTIVE
        defaultMVerificationTaxShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        // Get all the mVerificationTaxList where active is not null
        defaultMVerificationTaxShouldBeFound("active.specified=true");

        // Get all the mVerificationTaxList where active is null
        defaultMVerificationTaxShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationTaxesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVerificationTax.getAdOrganization();
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVerificationTaxList where adOrganization equals to adOrganizationId
        defaultMVerificationTaxShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVerificationTaxList where adOrganization equals to adOrganizationId + 1
        defaultMVerificationTaxShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mVerificationTax.getCurrency();
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);
        Long currencyId = currency.getId();

        // Get all the mVerificationTaxList where currency equals to currencyId
        defaultMVerificationTaxShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mVerificationTaxList where currency equals to currencyId + 1
        defaultMVerificationTaxShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByTaxCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CTaxCategory taxCategory = mVerificationTax.getTaxCategory();
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);
        Long taxCategoryId = taxCategory.getId();

        // Get all the mVerificationTaxList where taxCategory equals to taxCategoryId
        defaultMVerificationTaxShouldBeFound("taxCategoryId.equals=" + taxCategoryId);

        // Get all the mVerificationTaxList where taxCategory equals to taxCategoryId + 1
        defaultMVerificationTaxShouldNotBeFound("taxCategoryId.equals=" + (taxCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationTaxesByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mVerificationTax.getCostCenter();
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);
        Long costCenterId = costCenter.getId();

        // Get all the mVerificationTaxList where costCenter equals to costCenterId
        defaultMVerificationTaxShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mVerificationTaxList where costCenter equals to costCenterId + 1
        defaultMVerificationTaxShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVerificationTaxShouldBeFound(String filter) throws Exception {
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerificationTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxPeriod").value(hasItem(DEFAULT_TAX_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].traxCode").value(hasItem(DEFAULT_TRAX_CODE)))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].docCode").value(hasItem(DEFAULT_DOC_CODE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].returnDocType").value(hasItem(DEFAULT_RETURN_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].repSerNo").value(hasItem(DEFAULT_REP_SER_NO)))
            .andExpect(jsonPath("$.[*].taxExpCode").value(hasItem(DEFAULT_TAX_EXP_CODE)))
            .andExpect(jsonPath("$.[*].dateSSP").value(hasItem(DEFAULT_DATE_SSP.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVerificationTaxShouldNotBeFound(String filter) throws Exception {
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVerificationTax() throws Exception {
        // Get the mVerificationTax
        restMVerificationTaxMockMvc.perform(get("/api/m-verification-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVerificationTax() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        int databaseSizeBeforeUpdate = mVerificationTaxRepository.findAll().size();

        // Update the mVerificationTax
        MVerificationTax updatedMVerificationTax = mVerificationTaxRepository.findById(mVerificationTax.getId()).get();
        // Disconnect from session so that the updates on updatedMVerificationTax are not directly saved in db
        em.detach(updatedMVerificationTax);
        updatedMVerificationTax
            .taxPeriod(UPDATED_TAX_PERIOD)
            .traxCode(UPDATED_TRAX_CODE)
            .statusCode(UPDATED_STATUS_CODE)
            .docCode(UPDATED_DOC_CODE)
            .year(UPDATED_YEAR)
            .returnDocType(UPDATED_RETURN_DOC_TYPE)
            .repSerNo(UPDATED_REP_SER_NO)
            .taxExpCode(UPDATED_TAX_EXP_CODE)
            .dateSSP(UPDATED_DATE_SSP)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(updatedMVerificationTax);

        restMVerificationTaxMockMvc.perform(put("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isOk());

        // Validate the MVerificationTax in the database
        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeUpdate);
        MVerificationTax testMVerificationTax = mVerificationTaxList.get(mVerificationTaxList.size() - 1);
        assertThat(testMVerificationTax.getTaxPeriod()).isEqualTo(UPDATED_TAX_PERIOD);
        assertThat(testMVerificationTax.getTraxCode()).isEqualTo(UPDATED_TRAX_CODE);
        assertThat(testMVerificationTax.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testMVerificationTax.getDocCode()).isEqualTo(UPDATED_DOC_CODE);
        assertThat(testMVerificationTax.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMVerificationTax.getReturnDocType()).isEqualTo(UPDATED_RETURN_DOC_TYPE);
        assertThat(testMVerificationTax.getRepSerNo()).isEqualTo(UPDATED_REP_SER_NO);
        assertThat(testMVerificationTax.getTaxExpCode()).isEqualTo(UPDATED_TAX_EXP_CODE);
        assertThat(testMVerificationTax.getDateSSP()).isEqualTo(UPDATED_DATE_SSP);
        assertThat(testMVerificationTax.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVerificationTax.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVerificationTax() throws Exception {
        int databaseSizeBeforeUpdate = mVerificationTaxRepository.findAll().size();

        // Create the MVerificationTax
        MVerificationTaxDTO mVerificationTaxDTO = mVerificationTaxMapper.toDto(mVerificationTax);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVerificationTaxMockMvc.perform(put("/api/m-verification-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerificationTax in the database
        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVerificationTax() throws Exception {
        // Initialize the database
        mVerificationTaxRepository.saveAndFlush(mVerificationTax);

        int databaseSizeBeforeDelete = mVerificationTaxRepository.findAll().size();

        // Delete the mVerificationTax
        restMVerificationTaxMockMvc.perform(delete("/api/m-verification-taxes/{id}", mVerificationTax.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVerificationTax> mVerificationTaxList = mVerificationTaxRepository.findAll();
        assertThat(mVerificationTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
