package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MWarningLetter;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MWarningLetterRepository;
import com.bhp.opusb.service.MWarningLetterService;
import com.bhp.opusb.service.dto.MWarningLetterDTO;
import com.bhp.opusb.service.mapper.MWarningLetterMapper;
import com.bhp.opusb.service.dto.MWarningLetterCriteria;
import com.bhp.opusb.service.MWarningLetterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link MWarningLetterResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MWarningLetterResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REPORT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_WARNING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_WARNING_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_WARNING = "AAAAAAAAAA";
    private static final String UPDATED_WARNING = "BBBBBBBBBB";

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

    @Autowired
    private MWarningLetterRepository mWarningLetterRepository;

    @Autowired
    private MWarningLetterMapper mWarningLetterMapper;

    @Autowired
    private MWarningLetterService mWarningLetterService;

    @Autowired
    private MWarningLetterQueryService mWarningLetterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMWarningLetterMockMvc;

    private MWarningLetter mWarningLetter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MWarningLetter createEntity(EntityManager em) {
        MWarningLetter mWarningLetter = new MWarningLetter()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .reportDate(DEFAULT_REPORT_DATE)
            .warningType(DEFAULT_WARNING_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .location(DEFAULT_LOCATION)
            .warning(DEFAULT_WARNING)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mWarningLetter.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mWarningLetter.setVendor(cVendor);
        return mWarningLetter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MWarningLetter createUpdatedEntity(EntityManager em) {
        MWarningLetter mWarningLetter = new MWarningLetter()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .warningType(UPDATED_WARNING_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .warning(UPDATED_WARNING)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mWarningLetter.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mWarningLetter.setVendor(cVendor);
        return mWarningLetter;
    }

    @BeforeEach
    public void initTest() {
        mWarningLetter = createEntity(em);
    }

    @Test
    @Transactional
    public void createMWarningLetter() throws Exception {
        int databaseSizeBeforeCreate = mWarningLetterRepository.findAll().size();

        // Create the MWarningLetter
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);
        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isCreated());

        // Validate the MWarningLetter in the database
        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeCreate + 1);
        MWarningLetter testMWarningLetter = mWarningLetterList.get(mWarningLetterList.size() - 1);
        assertThat(testMWarningLetter.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMWarningLetter.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMWarningLetter.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testMWarningLetter.getWarningType()).isEqualTo(DEFAULT_WARNING_TYPE);
        assertThat(testMWarningLetter.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMWarningLetter.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMWarningLetter.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMWarningLetter.getWarning()).isEqualTo(DEFAULT_WARNING);
        assertThat(testMWarningLetter.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMWarningLetter.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMWarningLetter.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMWarningLetter.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMWarningLetter.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMWarningLetter.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
    }

    @Test
    @Transactional
    public void createMWarningLetterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mWarningLetterRepository.findAll().size();

        // Create the MWarningLetter with an existing ID
        mWarningLetter.setId(1L);
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MWarningLetter in the database
        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReportDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setReportDate(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setStartDate(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setEndDate(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setLocation(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setDateTrx(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setDocumentAction(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mWarningLetterRepository.findAll().size();
        // set the field null
        mWarningLetter.setDocumentStatus(null);

        // Create the MWarningLetter, which fails.
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        restMWarningLetterMockMvc.perform(post("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMWarningLetters() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mWarningLetter.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].warningType").value(hasItem(DEFAULT_WARNING_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMWarningLetter() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get the mWarningLetter
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters/{id}", mWarningLetter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mWarningLetter.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()))
            .andExpect(jsonPath("$.warningType").value(DEFAULT_WARNING_TYPE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.warning").value(DEFAULT_WARNING.toString()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()));
    }


    @Test
    @Transactional
    public void getMWarningLettersByIdFiltering() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        Long id = mWarningLetter.getId();

        defaultMWarningLetterShouldBeFound("id.equals=" + id);
        defaultMWarningLetterShouldNotBeFound("id.notEquals=" + id);

        defaultMWarningLetterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMWarningLetterShouldNotBeFound("id.greaterThan=" + id);

        defaultMWarningLetterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMWarningLetterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where uid equals to DEFAULT_UID
        defaultMWarningLetterShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mWarningLetterList where uid equals to UPDATED_UID
        defaultMWarningLetterShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where uid not equals to DEFAULT_UID
        defaultMWarningLetterShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mWarningLetterList where uid not equals to UPDATED_UID
        defaultMWarningLetterShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where uid in DEFAULT_UID or UPDATED_UID
        defaultMWarningLetterShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mWarningLetterList where uid equals to UPDATED_UID
        defaultMWarningLetterShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where uid is not null
        defaultMWarningLetterShouldBeFound("uid.specified=true");

        // Get all the mWarningLetterList where uid is null
        defaultMWarningLetterShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where active equals to DEFAULT_ACTIVE
        defaultMWarningLetterShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mWarningLetterList where active equals to UPDATED_ACTIVE
        defaultMWarningLetterShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where active not equals to DEFAULT_ACTIVE
        defaultMWarningLetterShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mWarningLetterList where active not equals to UPDATED_ACTIVE
        defaultMWarningLetterShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMWarningLetterShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mWarningLetterList where active equals to UPDATED_ACTIVE
        defaultMWarningLetterShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where active is not null
        defaultMWarningLetterShouldBeFound("active.specified=true");

        // Get all the mWarningLetterList where active is null
        defaultMWarningLetterShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate equals to DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.equals=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate equals to UPDATED_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.equals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate not equals to DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.notEquals=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate not equals to UPDATED_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.notEquals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate in DEFAULT_REPORT_DATE or UPDATED_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.in=" + DEFAULT_REPORT_DATE + "," + UPDATED_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate equals to UPDATED_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.in=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate is not null
        defaultMWarningLetterShouldBeFound("reportDate.specified=true");

        // Get all the mWarningLetterList where reportDate is null
        defaultMWarningLetterShouldNotBeFound("reportDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate is greater than or equal to DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.greaterThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate is greater than or equal to UPDATED_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.greaterThanOrEqual=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate is less than or equal to DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.lessThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate is less than or equal to SMALLER_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.lessThanOrEqual=" + SMALLER_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate is less than DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.lessThan=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate is less than UPDATED_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.lessThan=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByReportDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where reportDate is greater than DEFAULT_REPORT_DATE
        defaultMWarningLetterShouldNotBeFound("reportDate.greaterThan=" + DEFAULT_REPORT_DATE);

        // Get all the mWarningLetterList where reportDate is greater than SMALLER_REPORT_DATE
        defaultMWarningLetterShouldBeFound("reportDate.greaterThan=" + SMALLER_REPORT_DATE);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType equals to DEFAULT_WARNING_TYPE
        defaultMWarningLetterShouldBeFound("warningType.equals=" + DEFAULT_WARNING_TYPE);

        // Get all the mWarningLetterList where warningType equals to UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldNotBeFound("warningType.equals=" + UPDATED_WARNING_TYPE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType not equals to DEFAULT_WARNING_TYPE
        defaultMWarningLetterShouldNotBeFound("warningType.notEquals=" + DEFAULT_WARNING_TYPE);

        // Get all the mWarningLetterList where warningType not equals to UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldBeFound("warningType.notEquals=" + UPDATED_WARNING_TYPE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType in DEFAULT_WARNING_TYPE or UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldBeFound("warningType.in=" + DEFAULT_WARNING_TYPE + "," + UPDATED_WARNING_TYPE);

        // Get all the mWarningLetterList where warningType equals to UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldNotBeFound("warningType.in=" + UPDATED_WARNING_TYPE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType is not null
        defaultMWarningLetterShouldBeFound("warningType.specified=true");

        // Get all the mWarningLetterList where warningType is null
        defaultMWarningLetterShouldNotBeFound("warningType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType contains DEFAULT_WARNING_TYPE
        defaultMWarningLetterShouldBeFound("warningType.contains=" + DEFAULT_WARNING_TYPE);

        // Get all the mWarningLetterList where warningType contains UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldNotBeFound("warningType.contains=" + UPDATED_WARNING_TYPE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByWarningTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where warningType does not contain DEFAULT_WARNING_TYPE
        defaultMWarningLetterShouldNotBeFound("warningType.doesNotContain=" + DEFAULT_WARNING_TYPE);

        // Get all the mWarningLetterList where warningType does not contain UPDATED_WARNING_TYPE
        defaultMWarningLetterShouldBeFound("warningType.doesNotContain=" + UPDATED_WARNING_TYPE);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate equals to DEFAULT_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate equals to UPDATED_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate not equals to DEFAULT_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate not equals to UPDATED_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mWarningLetterList where startDate equals to UPDATED_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate is not null
        defaultMWarningLetterShouldBeFound("startDate.specified=true");

        // Get all the mWarningLetterList where startDate is null
        defaultMWarningLetterShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate is less than or equal to SMALLER_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate is less than DEFAULT_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate is less than UPDATED_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where startDate is greater than DEFAULT_START_DATE
        defaultMWarningLetterShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mWarningLetterList where startDate is greater than SMALLER_START_DATE
        defaultMWarningLetterShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate equals to DEFAULT_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate equals to UPDATED_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate not equals to DEFAULT_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate not equals to UPDATED_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mWarningLetterList where endDate equals to UPDATED_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate is not null
        defaultMWarningLetterShouldBeFound("endDate.specified=true");

        // Get all the mWarningLetterList where endDate is null
        defaultMWarningLetterShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate is greater than or equal to UPDATED_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate is less than or equal to DEFAULT_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate is less than or equal to SMALLER_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate is less than DEFAULT_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate is less than UPDATED_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where endDate is greater than DEFAULT_END_DATE
        defaultMWarningLetterShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the mWarningLetterList where endDate is greater than SMALLER_END_DATE
        defaultMWarningLetterShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location equals to DEFAULT_LOCATION
        defaultMWarningLetterShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the mWarningLetterList where location equals to UPDATED_LOCATION
        defaultMWarningLetterShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location not equals to DEFAULT_LOCATION
        defaultMWarningLetterShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the mWarningLetterList where location not equals to UPDATED_LOCATION
        defaultMWarningLetterShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultMWarningLetterShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the mWarningLetterList where location equals to UPDATED_LOCATION
        defaultMWarningLetterShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location is not null
        defaultMWarningLetterShouldBeFound("location.specified=true");

        // Get all the mWarningLetterList where location is null
        defaultMWarningLetterShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllMWarningLettersByLocationContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location contains DEFAULT_LOCATION
        defaultMWarningLetterShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the mWarningLetterList where location contains UPDATED_LOCATION
        defaultMWarningLetterShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where location does not contain DEFAULT_LOCATION
        defaultMWarningLetterShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the mWarningLetterList where location does not contain UPDATED_LOCATION
        defaultMWarningLetterShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx equals to UPDATED_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx equals to UPDATED_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx is not null
        defaultMWarningLetterShouldBeFound("dateTrx.specified=true");

        // Get all the mWarningLetterList where dateTrx is null
        defaultMWarningLetterShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx is less than UPDATED_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMWarningLetterShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mWarningLetterList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMWarningLetterShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMWarningLetterShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mWarningLetterList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMWarningLetterShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mWarningLetterList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mWarningLetterList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo is not null
        defaultMWarningLetterShouldBeFound("documentNo.specified=true");

        // Get all the mWarningLetterList where documentNo is null
        defaultMWarningLetterShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMWarningLetterShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mWarningLetterList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMWarningLetterShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mWarningLetterList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMWarningLetterShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMWarningLetterShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mWarningLetterList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMWarningLetterShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mWarningLetterList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mWarningLetterList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction is not null
        defaultMWarningLetterShouldBeFound("documentAction.specified=true");

        // Get all the mWarningLetterList where documentAction is null
        defaultMWarningLetterShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMWarningLetterShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mWarningLetterList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMWarningLetterShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mWarningLetterList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMWarningLetterShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMWarningLetterShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mWarningLetterList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMWarningLetterShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mWarningLetterList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mWarningLetterList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus is not null
        defaultMWarningLetterShouldBeFound("documentStatus.specified=true");

        // Get all the mWarningLetterList where documentStatus is null
        defaultMWarningLetterShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMWarningLetterShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mWarningLetterList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMWarningLetterShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mWarningLetterList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMWarningLetterShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where approved equals to DEFAULT_APPROVED
        defaultMWarningLetterShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mWarningLetterList where approved equals to UPDATED_APPROVED
        defaultMWarningLetterShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where approved not equals to DEFAULT_APPROVED
        defaultMWarningLetterShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mWarningLetterList where approved not equals to UPDATED_APPROVED
        defaultMWarningLetterShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMWarningLetterShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mWarningLetterList where approved equals to UPDATED_APPROVED
        defaultMWarningLetterShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where approved is not null
        defaultMWarningLetterShouldBeFound("approved.specified=true");

        // Get all the mWarningLetterList where approved is null
        defaultMWarningLetterShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where processed equals to DEFAULT_PROCESSED
        defaultMWarningLetterShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mWarningLetterList where processed equals to UPDATED_PROCESSED
        defaultMWarningLetterShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where processed not equals to DEFAULT_PROCESSED
        defaultMWarningLetterShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mWarningLetterList where processed not equals to UPDATED_PROCESSED
        defaultMWarningLetterShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMWarningLetterShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mWarningLetterList where processed equals to UPDATED_PROCESSED
        defaultMWarningLetterShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        // Get all the mWarningLetterList where processed is not null
        defaultMWarningLetterShouldBeFound("processed.specified=true");

        // Get all the mWarningLetterList where processed is null
        defaultMWarningLetterShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMWarningLettersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mWarningLetter.getAdOrganization();
        mWarningLetterRepository.saveAndFlush(mWarningLetter);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mWarningLetterList where adOrganization equals to adOrganizationId
        defaultMWarningLetterShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mWarningLetterList where adOrganization equals to adOrganizationId + 1
        defaultMWarningLetterShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMWarningLettersByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mWarningLetter.getVendor();
        mWarningLetterRepository.saveAndFlush(mWarningLetter);
        Long vendorId = vendor.getId();

        // Get all the mWarningLetterList where vendor equals to vendorId
        defaultMWarningLetterShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mWarningLetterList where vendor equals to vendorId + 1
        defaultMWarningLetterShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMWarningLetterShouldBeFound(String filter) throws Exception {
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mWarningLetter.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].warningType").value(hasItem(DEFAULT_WARNING_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMWarningLetterShouldNotBeFound(String filter) throws Exception {
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMWarningLetter() throws Exception {
        // Get the mWarningLetter
        restMWarningLetterMockMvc.perform(get("/api/m-warning-letters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMWarningLetter() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        int databaseSizeBeforeUpdate = mWarningLetterRepository.findAll().size();

        // Update the mWarningLetter
        MWarningLetter updatedMWarningLetter = mWarningLetterRepository.findById(mWarningLetter.getId()).get();
        // Disconnect from session so that the updates on updatedMWarningLetter are not directly saved in db
        em.detach(updatedMWarningLetter);
        updatedMWarningLetter
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .warningType(UPDATED_WARNING_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .warning(UPDATED_WARNING)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED);
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(updatedMWarningLetter);

        restMWarningLetterMockMvc.perform(put("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isOk());

        // Validate the MWarningLetter in the database
        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeUpdate);
        MWarningLetter testMWarningLetter = mWarningLetterList.get(mWarningLetterList.size() - 1);
        assertThat(testMWarningLetter.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMWarningLetter.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMWarningLetter.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testMWarningLetter.getWarningType()).isEqualTo(UPDATED_WARNING_TYPE);
        assertThat(testMWarningLetter.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMWarningLetter.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMWarningLetter.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMWarningLetter.getWarning()).isEqualTo(UPDATED_WARNING);
        assertThat(testMWarningLetter.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMWarningLetter.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMWarningLetter.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMWarningLetter.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMWarningLetter.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMWarningLetter.isProcessed()).isEqualTo(UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingMWarningLetter() throws Exception {
        int databaseSizeBeforeUpdate = mWarningLetterRepository.findAll().size();

        // Create the MWarningLetter
        MWarningLetterDTO mWarningLetterDTO = mWarningLetterMapper.toDto(mWarningLetter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMWarningLetterMockMvc.perform(put("/api/m-warning-letters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mWarningLetterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MWarningLetter in the database
        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMWarningLetter() throws Exception {
        // Initialize the database
        mWarningLetterRepository.saveAndFlush(mWarningLetter);

        int databaseSizeBeforeDelete = mWarningLetterRepository.findAll().size();

        // Delete the mWarningLetter
        restMWarningLetterMockMvc.perform(delete("/api/m-warning-letters/{id}", mWarningLetter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MWarningLetter> mWarningLetterList = mWarningLetterRepository.findAll();
        assertThat(mWarningLetterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
