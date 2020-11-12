package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CVendorGroup;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.CVendorService;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.mapper.CVendorMapper;
import com.bhp.opusb.service.dto.CVendorCriteria;
import com.bhp.opusb.service.CVendorQueryService;

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
 * Integration tests for the {@link CVendorResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NO = "AAAAAAAAAA";
    private static final String UPDATED_ID_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TIN = "AAAAAAAAAA";
    private static final String UPDATED_TIN = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID_NO = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BRANCH = false;
    private static final Boolean UPDATED_BRANCH = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_CATEGORY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_TRX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRX = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRX = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorRepository cVendorRepository;

    @Autowired
    private CVendorMapper cVendorMapper;

    @Autowired
    private CVendorService cVendorService;

    @Autowired
    private CVendorQueryService cVendorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorMockMvc;

    private CVendor cVendor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendor createEntity(EntityManager em) {
        CVendor cVendor = new CVendor()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION)
            .idNo(DEFAULT_ID_NO)
            .tin(DEFAULT_TIN)
            .taxIdNo(DEFAULT_TAX_ID_NO)
            .taxIdName(DEFAULT_TAX_ID_NAME)
            .branch(DEFAULT_BRANCH)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .website(DEFAULT_WEBSITE)
            .paymentCategory(DEFAULT_PAYMENT_CATEGORY)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
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
        cVendor.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        cVendor.setDocumentType(cDocumentType);
        return cVendor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendor createUpdatedEntity(EntityManager em) {
        CVendor cVendor = new CVendor()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .idNo(UPDATED_ID_NO)
            .tin(UPDATED_TIN)
            .taxIdNo(UPDATED_TAX_ID_NO)
            .taxIdName(UPDATED_TAX_ID_NAME)
            .branch(UPDATED_BRANCH)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .website(UPDATED_WEBSITE)
            .paymentCategory(UPDATED_PAYMENT_CATEGORY)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
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
        cVendor.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        cVendor.setDocumentType(cDocumentType);
        return cVendor;
    }

    @BeforeEach
    public void initTest() {
        cVendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendor() throws Exception {
        int databaseSizeBeforeCreate = cVendorRepository.findAll().size();

        // Create the CVendor
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);
        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendor in the database
        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeCreate + 1);
        CVendor testCVendor = cVendorList.get(cVendorList.size() - 1);
        assertThat(testCVendor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCVendor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCVendor.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCVendor.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCVendor.getIdNo()).isEqualTo(DEFAULT_ID_NO);
        assertThat(testCVendor.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testCVendor.getTaxIdNo()).isEqualTo(DEFAULT_TAX_ID_NO);
        assertThat(testCVendor.getTaxIdName()).isEqualTo(DEFAULT_TAX_ID_NAME);
        assertThat(testCVendor.isBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testCVendor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCVendor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCVendor.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCVendor.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCVendor.getPaymentCategory()).isEqualTo(DEFAULT_PAYMENT_CATEGORY);
        assertThat(testCVendor.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testCVendor.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testCVendor.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testCVendor.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testCVendor.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testCVendor.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testCVendor.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendor.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorRepository.findAll().size();

        // Create the CVendor with an existing ID
        cVendor.setId(1L);
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendor in the database
        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setName(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setType(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setLocation(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setPaymentCategory(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setDateTrx(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setDocumentNo(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setDocumentAction(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorRepository.findAll().size();
        // set the field null
        cVendor.setDocumentStatus(null);

        // Create the CVendor, which fails.
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        restCVendorMockMvc.perform(post("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCVendors() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList
        restCVendorMockMvc.perform(get("/api/c-vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].idNo").value(hasItem(DEFAULT_ID_NO)))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN)))
            .andExpect(jsonPath("$.[*].taxIdNo").value(hasItem(DEFAULT_TAX_ID_NO)))
            .andExpect(jsonPath("$.[*].taxIdName").value(hasItem(DEFAULT_TAX_ID_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendor() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get the cVendor
        restCVendorMockMvc.perform(get("/api/c-vendors/{id}", cVendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendor.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.idNo").value(DEFAULT_ID_NO))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN))
            .andExpect(jsonPath("$.taxIdNo").value(DEFAULT_TAX_ID_NO))
            .andExpect(jsonPath("$.taxIdName").value(DEFAULT_TAX_ID_NAME))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.paymentCategory").value(DEFAULT_PAYMENT_CATEGORY))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        Long id = cVendor.getId();

        defaultCVendorShouldBeFound("id.equals=" + id);
        defaultCVendorShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code equals to DEFAULT_CODE
        defaultCVendorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cVendorList where code equals to UPDATED_CODE
        defaultCVendorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code not equals to DEFAULT_CODE
        defaultCVendorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cVendorList where code not equals to UPDATED_CODE
        defaultCVendorShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCVendorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cVendorList where code equals to UPDATED_CODE
        defaultCVendorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code is not null
        defaultCVendorShouldBeFound("code.specified=true");

        // Get all the cVendorList where code is null
        defaultCVendorShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code contains DEFAULT_CODE
        defaultCVendorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cVendorList where code contains UPDATED_CODE
        defaultCVendorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where code does not contain DEFAULT_CODE
        defaultCVendorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cVendorList where code does not contain UPDATED_CODE
        defaultCVendorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCVendorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name equals to DEFAULT_NAME
        defaultCVendorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cVendorList where name equals to UPDATED_NAME
        defaultCVendorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name not equals to DEFAULT_NAME
        defaultCVendorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cVendorList where name not equals to UPDATED_NAME
        defaultCVendorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCVendorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cVendorList where name equals to UPDATED_NAME
        defaultCVendorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name is not null
        defaultCVendorShouldBeFound("name.specified=true");

        // Get all the cVendorList where name is null
        defaultCVendorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByNameContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name contains DEFAULT_NAME
        defaultCVendorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cVendorList where name contains UPDATED_NAME
        defaultCVendorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where name does not contain DEFAULT_NAME
        defaultCVendorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cVendorList where name does not contain UPDATED_NAME
        defaultCVendorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCVendorsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type equals to DEFAULT_TYPE
        defaultCVendorShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cVendorList where type equals to UPDATED_TYPE
        defaultCVendorShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type not equals to DEFAULT_TYPE
        defaultCVendorShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cVendorList where type not equals to UPDATED_TYPE
        defaultCVendorShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCVendorShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cVendorList where type equals to UPDATED_TYPE
        defaultCVendorShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type is not null
        defaultCVendorShouldBeFound("type.specified=true");

        // Get all the cVendorList where type is null
        defaultCVendorShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByTypeContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type contains DEFAULT_TYPE
        defaultCVendorShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cVendorList where type contains UPDATED_TYPE
        defaultCVendorShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where type does not contain DEFAULT_TYPE
        defaultCVendorShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cVendorList where type does not contain UPDATED_TYPE
        defaultCVendorShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCVendorsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location equals to DEFAULT_LOCATION
        defaultCVendorShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the cVendorList where location equals to UPDATED_LOCATION
        defaultCVendorShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location not equals to DEFAULT_LOCATION
        defaultCVendorShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the cVendorList where location not equals to UPDATED_LOCATION
        defaultCVendorShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCVendorShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the cVendorList where location equals to UPDATED_LOCATION
        defaultCVendorShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location is not null
        defaultCVendorShouldBeFound("location.specified=true");

        // Get all the cVendorList where location is null
        defaultCVendorShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByLocationContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location contains DEFAULT_LOCATION
        defaultCVendorShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the cVendorList where location contains UPDATED_LOCATION
        defaultCVendorShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where location does not contain DEFAULT_LOCATION
        defaultCVendorShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the cVendorList where location does not contain UPDATED_LOCATION
        defaultCVendorShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllCVendorsByIdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo equals to DEFAULT_ID_NO
        defaultCVendorShouldBeFound("idNo.equals=" + DEFAULT_ID_NO);

        // Get all the cVendorList where idNo equals to UPDATED_ID_NO
        defaultCVendorShouldNotBeFound("idNo.equals=" + UPDATED_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByIdNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo not equals to DEFAULT_ID_NO
        defaultCVendorShouldNotBeFound("idNo.notEquals=" + DEFAULT_ID_NO);

        // Get all the cVendorList where idNo not equals to UPDATED_ID_NO
        defaultCVendorShouldBeFound("idNo.notEquals=" + UPDATED_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByIdNoIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo in DEFAULT_ID_NO or UPDATED_ID_NO
        defaultCVendorShouldBeFound("idNo.in=" + DEFAULT_ID_NO + "," + UPDATED_ID_NO);

        // Get all the cVendorList where idNo equals to UPDATED_ID_NO
        defaultCVendorShouldNotBeFound("idNo.in=" + UPDATED_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByIdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo is not null
        defaultCVendorShouldBeFound("idNo.specified=true");

        // Get all the cVendorList where idNo is null
        defaultCVendorShouldNotBeFound("idNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByIdNoContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo contains DEFAULT_ID_NO
        defaultCVendorShouldBeFound("idNo.contains=" + DEFAULT_ID_NO);

        // Get all the cVendorList where idNo contains UPDATED_ID_NO
        defaultCVendorShouldNotBeFound("idNo.contains=" + UPDATED_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByIdNoNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where idNo does not contain DEFAULT_ID_NO
        defaultCVendorShouldNotBeFound("idNo.doesNotContain=" + DEFAULT_ID_NO);

        // Get all the cVendorList where idNo does not contain UPDATED_ID_NO
        defaultCVendorShouldBeFound("idNo.doesNotContain=" + UPDATED_ID_NO);
    }


    @Test
    @Transactional
    public void getAllCVendorsByTinIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin equals to DEFAULT_TIN
        defaultCVendorShouldBeFound("tin.equals=" + DEFAULT_TIN);

        // Get all the cVendorList where tin equals to UPDATED_TIN
        defaultCVendorShouldNotBeFound("tin.equals=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin not equals to DEFAULT_TIN
        defaultCVendorShouldNotBeFound("tin.notEquals=" + DEFAULT_TIN);

        // Get all the cVendorList where tin not equals to UPDATED_TIN
        defaultCVendorShouldBeFound("tin.notEquals=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTinIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin in DEFAULT_TIN or UPDATED_TIN
        defaultCVendorShouldBeFound("tin.in=" + DEFAULT_TIN + "," + UPDATED_TIN);

        // Get all the cVendorList where tin equals to UPDATED_TIN
        defaultCVendorShouldNotBeFound("tin.in=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTinIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin is not null
        defaultCVendorShouldBeFound("tin.specified=true");

        // Get all the cVendorList where tin is null
        defaultCVendorShouldNotBeFound("tin.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByTinContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin contains DEFAULT_TIN
        defaultCVendorShouldBeFound("tin.contains=" + DEFAULT_TIN);

        // Get all the cVendorList where tin contains UPDATED_TIN
        defaultCVendorShouldNotBeFound("tin.contains=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTinNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where tin does not contain DEFAULT_TIN
        defaultCVendorShouldNotBeFound("tin.doesNotContain=" + DEFAULT_TIN);

        // Get all the cVendorList where tin does not contain UPDATED_TIN
        defaultCVendorShouldBeFound("tin.doesNotContain=" + UPDATED_TIN);
    }


    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo equals to DEFAULT_TAX_ID_NO
        defaultCVendorShouldBeFound("taxIdNo.equals=" + DEFAULT_TAX_ID_NO);

        // Get all the cVendorList where taxIdNo equals to UPDATED_TAX_ID_NO
        defaultCVendorShouldNotBeFound("taxIdNo.equals=" + UPDATED_TAX_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo not equals to DEFAULT_TAX_ID_NO
        defaultCVendorShouldNotBeFound("taxIdNo.notEquals=" + DEFAULT_TAX_ID_NO);

        // Get all the cVendorList where taxIdNo not equals to UPDATED_TAX_ID_NO
        defaultCVendorShouldBeFound("taxIdNo.notEquals=" + UPDATED_TAX_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo in DEFAULT_TAX_ID_NO or UPDATED_TAX_ID_NO
        defaultCVendorShouldBeFound("taxIdNo.in=" + DEFAULT_TAX_ID_NO + "," + UPDATED_TAX_ID_NO);

        // Get all the cVendorList where taxIdNo equals to UPDATED_TAX_ID_NO
        defaultCVendorShouldNotBeFound("taxIdNo.in=" + UPDATED_TAX_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo is not null
        defaultCVendorShouldBeFound("taxIdNo.specified=true");

        // Get all the cVendorList where taxIdNo is null
        defaultCVendorShouldNotBeFound("taxIdNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo contains DEFAULT_TAX_ID_NO
        defaultCVendorShouldBeFound("taxIdNo.contains=" + DEFAULT_TAX_ID_NO);

        // Get all the cVendorList where taxIdNo contains UPDATED_TAX_ID_NO
        defaultCVendorShouldNotBeFound("taxIdNo.contains=" + UPDATED_TAX_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNoNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdNo does not contain DEFAULT_TAX_ID_NO
        defaultCVendorShouldNotBeFound("taxIdNo.doesNotContain=" + DEFAULT_TAX_ID_NO);

        // Get all the cVendorList where taxIdNo does not contain UPDATED_TAX_ID_NO
        defaultCVendorShouldBeFound("taxIdNo.doesNotContain=" + UPDATED_TAX_ID_NO);
    }


    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName equals to DEFAULT_TAX_ID_NAME
        defaultCVendorShouldBeFound("taxIdName.equals=" + DEFAULT_TAX_ID_NAME);

        // Get all the cVendorList where taxIdName equals to UPDATED_TAX_ID_NAME
        defaultCVendorShouldNotBeFound("taxIdName.equals=" + UPDATED_TAX_ID_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName not equals to DEFAULT_TAX_ID_NAME
        defaultCVendorShouldNotBeFound("taxIdName.notEquals=" + DEFAULT_TAX_ID_NAME);

        // Get all the cVendorList where taxIdName not equals to UPDATED_TAX_ID_NAME
        defaultCVendorShouldBeFound("taxIdName.notEquals=" + UPDATED_TAX_ID_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName in DEFAULT_TAX_ID_NAME or UPDATED_TAX_ID_NAME
        defaultCVendorShouldBeFound("taxIdName.in=" + DEFAULT_TAX_ID_NAME + "," + UPDATED_TAX_ID_NAME);

        // Get all the cVendorList where taxIdName equals to UPDATED_TAX_ID_NAME
        defaultCVendorShouldNotBeFound("taxIdName.in=" + UPDATED_TAX_ID_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName is not null
        defaultCVendorShouldBeFound("taxIdName.specified=true");

        // Get all the cVendorList where taxIdName is null
        defaultCVendorShouldNotBeFound("taxIdName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName contains DEFAULT_TAX_ID_NAME
        defaultCVendorShouldBeFound("taxIdName.contains=" + DEFAULT_TAX_ID_NAME);

        // Get all the cVendorList where taxIdName contains UPDATED_TAX_ID_NAME
        defaultCVendorShouldNotBeFound("taxIdName.contains=" + UPDATED_TAX_ID_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdNameNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where taxIdName does not contain DEFAULT_TAX_ID_NAME
        defaultCVendorShouldNotBeFound("taxIdName.doesNotContain=" + DEFAULT_TAX_ID_NAME);

        // Get all the cVendorList where taxIdName does not contain UPDATED_TAX_ID_NAME
        defaultCVendorShouldBeFound("taxIdName.doesNotContain=" + UPDATED_TAX_ID_NAME);
    }


    @Test
    @Transactional
    public void getAllCVendorsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where branch equals to DEFAULT_BRANCH
        defaultCVendorShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the cVendorList where branch equals to UPDATED_BRANCH
        defaultCVendorShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorsByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where branch not equals to DEFAULT_BRANCH
        defaultCVendorShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the cVendorList where branch not equals to UPDATED_BRANCH
        defaultCVendorShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultCVendorShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the cVendorList where branch equals to UPDATED_BRANCH
        defaultCVendorShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where branch is not null
        defaultCVendorShouldBeFound("branch.specified=true");

        // Get all the cVendorList where branch is null
        defaultCVendorShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email equals to DEFAULT_EMAIL
        defaultCVendorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the cVendorList where email equals to UPDATED_EMAIL
        defaultCVendorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCVendorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email not equals to DEFAULT_EMAIL
        defaultCVendorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the cVendorList where email not equals to UPDATED_EMAIL
        defaultCVendorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCVendorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCVendorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the cVendorList where email equals to UPDATED_EMAIL
        defaultCVendorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCVendorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email is not null
        defaultCVendorShouldBeFound("email.specified=true");

        // Get all the cVendorList where email is null
        defaultCVendorShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email contains DEFAULT_EMAIL
        defaultCVendorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the cVendorList where email contains UPDATED_EMAIL
        defaultCVendorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCVendorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where email does not contain DEFAULT_EMAIL
        defaultCVendorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the cVendorList where email does not contain UPDATED_EMAIL
        defaultCVendorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCVendorsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone equals to DEFAULT_PHONE
        defaultCVendorShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the cVendorList where phone equals to UPDATED_PHONE
        defaultCVendorShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone not equals to DEFAULT_PHONE
        defaultCVendorShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the cVendorList where phone not equals to UPDATED_PHONE
        defaultCVendorShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCVendorShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the cVendorList where phone equals to UPDATED_PHONE
        defaultCVendorShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone is not null
        defaultCVendorShouldBeFound("phone.specified=true");

        // Get all the cVendorList where phone is null
        defaultCVendorShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone contains DEFAULT_PHONE
        defaultCVendorShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the cVendorList where phone contains UPDATED_PHONE
        defaultCVendorShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where phone does not contain DEFAULT_PHONE
        defaultCVendorShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the cVendorList where phone does not contain UPDATED_PHONE
        defaultCVendorShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCVendorsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax equals to DEFAULT_FAX
        defaultCVendorShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the cVendorList where fax equals to UPDATED_FAX
        defaultCVendorShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax not equals to DEFAULT_FAX
        defaultCVendorShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the cVendorList where fax not equals to UPDATED_FAX
        defaultCVendorShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultCVendorShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the cVendorList where fax equals to UPDATED_FAX
        defaultCVendorShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax is not null
        defaultCVendorShouldBeFound("fax.specified=true");

        // Get all the cVendorList where fax is null
        defaultCVendorShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByFaxContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax contains DEFAULT_FAX
        defaultCVendorShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the cVendorList where fax contains UPDATED_FAX
        defaultCVendorShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where fax does not contain DEFAULT_FAX
        defaultCVendorShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the cVendorList where fax does not contain UPDATED_FAX
        defaultCVendorShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllCVendorsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website equals to DEFAULT_WEBSITE
        defaultCVendorShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the cVendorList where website equals to UPDATED_WEBSITE
        defaultCVendorShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website not equals to DEFAULT_WEBSITE
        defaultCVendorShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the cVendorList where website not equals to UPDATED_WEBSITE
        defaultCVendorShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultCVendorShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the cVendorList where website equals to UPDATED_WEBSITE
        defaultCVendorShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website is not null
        defaultCVendorShouldBeFound("website.specified=true");

        // Get all the cVendorList where website is null
        defaultCVendorShouldNotBeFound("website.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website contains DEFAULT_WEBSITE
        defaultCVendorShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the cVendorList where website contains UPDATED_WEBSITE
        defaultCVendorShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where website does not contain DEFAULT_WEBSITE
        defaultCVendorShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the cVendorList where website does not contain UPDATED_WEBSITE
        defaultCVendorShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }


    @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory equals to DEFAULT_PAYMENT_CATEGORY
        defaultCVendorShouldBeFound("paymentCategory.equals=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the cVendorList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldNotBeFound("paymentCategory.equals=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory not equals to DEFAULT_PAYMENT_CATEGORY
        defaultCVendorShouldNotBeFound("paymentCategory.notEquals=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the cVendorList where paymentCategory not equals to UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldBeFound("paymentCategory.notEquals=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory in DEFAULT_PAYMENT_CATEGORY or UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldBeFound("paymentCategory.in=" + DEFAULT_PAYMENT_CATEGORY + "," + UPDATED_PAYMENT_CATEGORY);

        // Get all the cVendorList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldNotBeFound("paymentCategory.in=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory is not null
        defaultCVendorShouldBeFound("paymentCategory.specified=true");

        // Get all the cVendorList where paymentCategory is null
        defaultCVendorShouldNotBeFound("paymentCategory.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory contains DEFAULT_PAYMENT_CATEGORY
        defaultCVendorShouldBeFound("paymentCategory.contains=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the cVendorList where paymentCategory contains UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldNotBeFound("paymentCategory.contains=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCVendorsByPaymentCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where paymentCategory does not contain DEFAULT_PAYMENT_CATEGORY
        defaultCVendorShouldNotBeFound("paymentCategory.doesNotContain=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the cVendorList where paymentCategory does not contain UPDATED_PAYMENT_CATEGORY
        defaultCVendorShouldBeFound("paymentCategory.doesNotContain=" + UPDATED_PAYMENT_CATEGORY);
    }


    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx equals to DEFAULT_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx equals to UPDATED_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx not equals to UPDATED_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the cVendorList where dateTrx equals to UPDATED_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx is not null
        defaultCVendorShouldBeFound("dateTrx.specified=true");

        // Get all the cVendorList where dateTrx is null
        defaultCVendorShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx is less than DEFAULT_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx is less than UPDATED_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultCVendorShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the cVendorList where dateTrx is greater than SMALLER_DATE_TRX
        defaultCVendorShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllCVendorsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultCVendorShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cVendorList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCVendorShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultCVendorShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cVendorList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultCVendorShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultCVendorShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the cVendorList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCVendorShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo is not null
        defaultCVendorShouldBeFound("documentNo.specified=true");

        // Get all the cVendorList where documentNo is null
        defaultCVendorShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultCVendorShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the cVendorList where documentNo contains UPDATED_DOCUMENT_NO
        defaultCVendorShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultCVendorShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the cVendorList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultCVendorShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllCVendorsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultCVendorShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the cVendorList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultCVendorShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the cVendorList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the cVendorList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction is not null
        defaultCVendorShouldBeFound("documentAction.specified=true");

        // Get all the cVendorList where documentAction is null
        defaultCVendorShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultCVendorShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the cVendorList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultCVendorShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the cVendorList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultCVendorShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultCVendorShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the cVendorList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultCVendorShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the cVendorList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the cVendorList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus is not null
        defaultCVendorShouldBeFound("documentStatus.specified=true");

        // Get all the cVendorList where documentStatus is null
        defaultCVendorShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultCVendorShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the cVendorList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCVendorsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultCVendorShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the cVendorList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultCVendorShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllCVendorsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where approved equals to DEFAULT_APPROVED
        defaultCVendorShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the cVendorList where approved equals to UPDATED_APPROVED
        defaultCVendorShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where approved not equals to DEFAULT_APPROVED
        defaultCVendorShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the cVendorList where approved not equals to UPDATED_APPROVED
        defaultCVendorShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultCVendorShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the cVendorList where approved equals to UPDATED_APPROVED
        defaultCVendorShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where approved is not null
        defaultCVendorShouldBeFound("approved.specified=true");

        // Get all the cVendorList where approved is null
        defaultCVendorShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where processed equals to DEFAULT_PROCESSED
        defaultCVendorShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the cVendorList where processed equals to UPDATED_PROCESSED
        defaultCVendorShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where processed not equals to DEFAULT_PROCESSED
        defaultCVendorShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the cVendorList where processed not equals to UPDATED_PROCESSED
        defaultCVendorShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultCVendorShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the cVendorList where processed equals to UPDATED_PROCESSED
        defaultCVendorShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllCVendorsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where processed is not null
        defaultCVendorShouldBeFound("processed.specified=true");

        // Get all the cVendorList where processed is null
        defaultCVendorShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where uid equals to DEFAULT_UID
        defaultCVendorShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorList where uid equals to UPDATED_UID
        defaultCVendorShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where uid not equals to DEFAULT_UID
        defaultCVendorShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorList where uid not equals to UPDATED_UID
        defaultCVendorShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorList where uid equals to UPDATED_UID
        defaultCVendorShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where uid is not null
        defaultCVendorShouldBeFound("uid.specified=true");

        // Get all the cVendorList where uid is null
        defaultCVendorShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where active equals to DEFAULT_ACTIVE
        defaultCVendorShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorList where active equals to UPDATED_ACTIVE
        defaultCVendorShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where active not equals to DEFAULT_ACTIVE
        defaultCVendorShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorList where active not equals to UPDATED_ACTIVE
        defaultCVendorShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorList where active equals to UPDATED_ACTIVE
        defaultCVendorShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        // Get all the cVendorList where active is not null
        defaultCVendorShouldBeFound("active.specified=true");

        // Get all the cVendorList where active is null
        defaultCVendorShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorsByTaxIdFileIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);
        CAttachment taxIdFile = CAttachmentResourceIT.createEntity(em);
        em.persist(taxIdFile);
        em.flush();
        cVendor.setTaxIdFile(taxIdFile);
        cVendorRepository.saveAndFlush(cVendor);
        Long taxIdFileId = taxIdFile.getId();

        // Get all the cVendorList where taxIdFile equals to taxIdFileId
        defaultCVendorShouldBeFound("taxIdFileId.equals=" + taxIdFileId);

        // Get all the cVendorList where taxIdFile equals to taxIdFileId + 1
        defaultCVendorShouldNotBeFound("taxIdFileId.equals=" + (taxIdFileId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendor.getAdOrganization();
        cVendorRepository.saveAndFlush(cVendor);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorList where adOrganization equals to adOrganizationId
        defaultCVendorShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorList where adOrganization equals to adOrganizationId + 1
        defaultCVendorShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorsByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = cVendor.getDocumentType();
        cVendorRepository.saveAndFlush(cVendor);
        Long documentTypeId = documentType.getId();

        // Get all the cVendorList where documentType equals to documentTypeId
        defaultCVendorShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the cVendorList where documentType equals to documentTypeId + 1
        defaultCVendorShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorsByVendorGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);
        CVendorGroup vendorGroup = CVendorGroupResourceIT.createEntity(em);
        em.persist(vendorGroup);
        em.flush();
        cVendor.setVendorGroup(vendorGroup);
        cVendorRepository.saveAndFlush(cVendor);
        Long vendorGroupId = vendorGroup.getId();

        // Get all the cVendorList where vendorGroup equals to vendorGroupId
        defaultCVendorShouldBeFound("vendorGroupId.equals=" + vendorGroupId);

        // Get all the cVendorList where vendorGroup equals to vendorGroupId + 1
        defaultCVendorShouldNotBeFound("vendorGroupId.equals=" + (vendorGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorShouldBeFound(String filter) throws Exception {
        restCVendorMockMvc.perform(get("/api/c-vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].idNo").value(hasItem(DEFAULT_ID_NO)))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN)))
            .andExpect(jsonPath("$.[*].taxIdNo").value(hasItem(DEFAULT_TAX_ID_NO)))
            .andExpect(jsonPath("$.[*].taxIdName").value(hasItem(DEFAULT_TAX_ID_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorMockMvc.perform(get("/api/c-vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorShouldNotBeFound(String filter) throws Exception {
        restCVendorMockMvc.perform(get("/api/c-vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorMockMvc.perform(get("/api/c-vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendor() throws Exception {
        // Get the cVendor
        restCVendorMockMvc.perform(get("/api/c-vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendor() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        int databaseSizeBeforeUpdate = cVendorRepository.findAll().size();

        // Update the cVendor
        CVendor updatedCVendor = cVendorRepository.findById(cVendor.getId()).get();
        // Disconnect from session so that the updates on updatedCVendor are not directly saved in db
        em.detach(updatedCVendor);
        updatedCVendor
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .idNo(UPDATED_ID_NO)
            .tin(UPDATED_TIN)
            .taxIdNo(UPDATED_TAX_ID_NO)
            .taxIdName(UPDATED_TAX_ID_NAME)
            .branch(UPDATED_BRANCH)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .website(UPDATED_WEBSITE)
            .paymentCategory(UPDATED_PAYMENT_CATEGORY)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorDTO cVendorDTO = cVendorMapper.toDto(updatedCVendor);

        restCVendorMockMvc.perform(put("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isOk());

        // Validate the CVendor in the database
        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeUpdate);
        CVendor testCVendor = cVendorList.get(cVendorList.size() - 1);
        assertThat(testCVendor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCVendor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCVendor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCVendor.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCVendor.getIdNo()).isEqualTo(UPDATED_ID_NO);
        assertThat(testCVendor.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testCVendor.getTaxIdNo()).isEqualTo(UPDATED_TAX_ID_NO);
        assertThat(testCVendor.getTaxIdName()).isEqualTo(UPDATED_TAX_ID_NAME);
        assertThat(testCVendor.isBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCVendor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCVendor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCVendor.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCVendor.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCVendor.getPaymentCategory()).isEqualTo(UPDATED_PAYMENT_CATEGORY);
        assertThat(testCVendor.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testCVendor.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testCVendor.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testCVendor.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testCVendor.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testCVendor.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testCVendor.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendor.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendor() throws Exception {
        int databaseSizeBeforeUpdate = cVendorRepository.findAll().size();

        // Create the CVendor
        CVendorDTO cVendorDTO = cVendorMapper.toDto(cVendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorMockMvc.perform(put("/api/c-vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendor in the database
        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendor() throws Exception {
        // Initialize the database
        cVendorRepository.saveAndFlush(cVendor);

        int databaseSizeBeforeDelete = cVendorRepository.findAll().size();

        // Delete the cVendor
        restCVendorMockMvc.perform(delete("/api/c-vendors/{id}", cVendor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendor> cVendorList = cVendorRepository.findAll();
        assertThat(cVendorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
