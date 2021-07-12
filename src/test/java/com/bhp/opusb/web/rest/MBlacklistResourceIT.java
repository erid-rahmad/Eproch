package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBlacklist;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MBlacklistRepository;
import com.bhp.opusb.service.MBlacklistService;
import com.bhp.opusb.service.dto.MBlacklistDTO;
import com.bhp.opusb.service.mapper.MBlacklistMapper;
import com.bhp.opusb.service.dto.MBlacklistCriteria;
import com.bhp.opusb.service.MBlacklistQueryService;

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
 * Integration tests for the {@link MBlacklistResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBlacklistResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REPORT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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
    private MBlacklistRepository mBlacklistRepository;

    @Autowired
    private MBlacklistMapper mBlacklistMapper;

    @Autowired
    private MBlacklistService mBlacklistService;

    @Autowired
    private MBlacklistQueryService mBlacklistQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBlacklistMockMvc;

    private MBlacklist mBlacklist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBlacklist createEntity(EntityManager em) {
        MBlacklist mBlacklist = new MBlacklist()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .reportDate(DEFAULT_REPORT_DATE)
            .notes(DEFAULT_NOTES)
            .type(DEFAULT_TYPE)
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
        mBlacklist.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBlacklist.setVendor(cVendor);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mBlacklist.setBusinessCategory(cBusinessCategory);
        return mBlacklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBlacklist createUpdatedEntity(EntityManager em) {
        MBlacklist mBlacklist = new MBlacklist()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .notes(UPDATED_NOTES)
            .type(UPDATED_TYPE)
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
        mBlacklist.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBlacklist.setVendor(cVendor);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mBlacklist.setBusinessCategory(cBusinessCategory);
        return mBlacklist;
    }

    @BeforeEach
    public void initTest() {
        mBlacklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBlacklist() throws Exception {
        int databaseSizeBeforeCreate = mBlacklistRepository.findAll().size();

        // Create the MBlacklist
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);
        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isCreated());

        // Validate the MBlacklist in the database
        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeCreate + 1);
        MBlacklist testMBlacklist = mBlacklistList.get(mBlacklistList.size() - 1);
        assertThat(testMBlacklist.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBlacklist.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBlacklist.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testMBlacklist.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMBlacklist.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMBlacklist.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMBlacklist.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMBlacklist.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMBlacklist.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMBlacklist.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMBlacklist.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
    }

    @Test
    @Transactional
    public void createMBlacklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBlacklistRepository.findAll().size();

        // Create the MBlacklist with an existing ID
        mBlacklist.setId(1L);
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBlacklist in the database
        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReportDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBlacklistRepository.findAll().size();
        // set the field null
        mBlacklist.setReportDate(null);

        // Create the MBlacklist, which fails.
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBlacklistRepository.findAll().size();
        // set the field null
        mBlacklist.setDateTrx(null);

        // Create the MBlacklist, which fails.
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBlacklistRepository.findAll().size();
        // set the field null
        mBlacklist.setDocumentAction(null);

        // Create the MBlacklist, which fails.
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBlacklistRepository.findAll().size();
        // set the field null
        mBlacklist.setDocumentStatus(null);

        // Create the MBlacklist, which fails.
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        restMBlacklistMockMvc.perform(post("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBlacklists() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList
        restMBlacklistMockMvc.perform(get("/api/m-blacklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBlacklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBlacklist() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get the mBlacklist
        restMBlacklistMockMvc.perform(get("/api/m-blacklists/{id}", mBlacklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBlacklist.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBlacklistsByIdFiltering() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        Long id = mBlacklist.getId();

        defaultMBlacklistShouldBeFound("id.equals=" + id);
        defaultMBlacklistShouldNotBeFound("id.notEquals=" + id);

        defaultMBlacklistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBlacklistShouldNotBeFound("id.greaterThan=" + id);

        defaultMBlacklistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBlacklistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where uid equals to DEFAULT_UID
        defaultMBlacklistShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBlacklistList where uid equals to UPDATED_UID
        defaultMBlacklistShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where uid not equals to DEFAULT_UID
        defaultMBlacklistShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBlacklistList where uid not equals to UPDATED_UID
        defaultMBlacklistShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBlacklistShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBlacklistList where uid equals to UPDATED_UID
        defaultMBlacklistShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where uid is not null
        defaultMBlacklistShouldBeFound("uid.specified=true");

        // Get all the mBlacklistList where uid is null
        defaultMBlacklistShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where active equals to DEFAULT_ACTIVE
        defaultMBlacklistShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBlacklistList where active equals to UPDATED_ACTIVE
        defaultMBlacklistShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where active not equals to DEFAULT_ACTIVE
        defaultMBlacklistShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBlacklistList where active not equals to UPDATED_ACTIVE
        defaultMBlacklistShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBlacklistShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBlacklistList where active equals to UPDATED_ACTIVE
        defaultMBlacklistShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where active is not null
        defaultMBlacklistShouldBeFound("active.specified=true");

        // Get all the mBlacklistList where active is null
        defaultMBlacklistShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate equals to DEFAULT_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.equals=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate equals to UPDATED_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.equals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate not equals to DEFAULT_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.notEquals=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate not equals to UPDATED_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.notEquals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate in DEFAULT_REPORT_DATE or UPDATED_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.in=" + DEFAULT_REPORT_DATE + "," + UPDATED_REPORT_DATE);

        // Get all the mBlacklistList where reportDate equals to UPDATED_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.in=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate is not null
        defaultMBlacklistShouldBeFound("reportDate.specified=true");

        // Get all the mBlacklistList where reportDate is null
        defaultMBlacklistShouldNotBeFound("reportDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate is greater than or equal to DEFAULT_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.greaterThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate is greater than or equal to UPDATED_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.greaterThanOrEqual=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate is less than or equal to DEFAULT_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.lessThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate is less than or equal to SMALLER_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.lessThanOrEqual=" + SMALLER_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate is less than DEFAULT_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.lessThan=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate is less than UPDATED_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.lessThan=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByReportDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where reportDate is greater than DEFAULT_REPORT_DATE
        defaultMBlacklistShouldNotBeFound("reportDate.greaterThan=" + DEFAULT_REPORT_DATE);

        // Get all the mBlacklistList where reportDate is greater than SMALLER_REPORT_DATE
        defaultMBlacklistShouldBeFound("reportDate.greaterThan=" + SMALLER_REPORT_DATE);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type equals to DEFAULT_TYPE
        defaultMBlacklistShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the mBlacklistList where type equals to UPDATED_TYPE
        defaultMBlacklistShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type not equals to DEFAULT_TYPE
        defaultMBlacklistShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the mBlacklistList where type not equals to UPDATED_TYPE
        defaultMBlacklistShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMBlacklistShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the mBlacklistList where type equals to UPDATED_TYPE
        defaultMBlacklistShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type is not null
        defaultMBlacklistShouldBeFound("type.specified=true");

        // Get all the mBlacklistList where type is null
        defaultMBlacklistShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBlacklistsByTypeContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type contains DEFAULT_TYPE
        defaultMBlacklistShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the mBlacklistList where type contains UPDATED_TYPE
        defaultMBlacklistShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where type does not contain DEFAULT_TYPE
        defaultMBlacklistShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the mBlacklistList where type does not contain UPDATED_TYPE
        defaultMBlacklistShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mBlacklistList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx is not null
        defaultMBlacklistShouldBeFound("dateTrx.specified=true");

        // Get all the mBlacklistList where dateTrx is null
        defaultMBlacklistShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx is less than UPDATED_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMBlacklistShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mBlacklistList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMBlacklistShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMBlacklistShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBlacklistList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMBlacklistShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBlacklistList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mBlacklistList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo is not null
        defaultMBlacklistShouldBeFound("documentNo.specified=true");

        // Get all the mBlacklistList where documentNo is null
        defaultMBlacklistShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMBlacklistShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBlacklistList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMBlacklistShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBlacklistList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMBlacklistShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMBlacklistShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBlacklistList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMBlacklistShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBlacklistList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mBlacklistList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction is not null
        defaultMBlacklistShouldBeFound("documentAction.specified=true");

        // Get all the mBlacklistList where documentAction is null
        defaultMBlacklistShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMBlacklistShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBlacklistList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMBlacklistShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBlacklistList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMBlacklistShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMBlacklistShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBlacklistList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMBlacklistShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBlacklistList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mBlacklistList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus is not null
        defaultMBlacklistShouldBeFound("documentStatus.specified=true");

        // Get all the mBlacklistList where documentStatus is null
        defaultMBlacklistShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMBlacklistShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBlacklistList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMBlacklistShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBlacklistList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMBlacklistShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where approved equals to DEFAULT_APPROVED
        defaultMBlacklistShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mBlacklistList where approved equals to UPDATED_APPROVED
        defaultMBlacklistShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where approved not equals to DEFAULT_APPROVED
        defaultMBlacklistShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mBlacklistList where approved not equals to UPDATED_APPROVED
        defaultMBlacklistShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMBlacklistShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mBlacklistList where approved equals to UPDATED_APPROVED
        defaultMBlacklistShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where approved is not null
        defaultMBlacklistShouldBeFound("approved.specified=true");

        // Get all the mBlacklistList where approved is null
        defaultMBlacklistShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where processed equals to DEFAULT_PROCESSED
        defaultMBlacklistShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mBlacklistList where processed equals to UPDATED_PROCESSED
        defaultMBlacklistShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where processed not equals to DEFAULT_PROCESSED
        defaultMBlacklistShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mBlacklistList where processed not equals to UPDATED_PROCESSED
        defaultMBlacklistShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMBlacklistShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mBlacklistList where processed equals to UPDATED_PROCESSED
        defaultMBlacklistShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        // Get all the mBlacklistList where processed is not null
        defaultMBlacklistShouldBeFound("processed.specified=true");

        // Get all the mBlacklistList where processed is null
        defaultMBlacklistShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        mBlacklist.setAttachment(attachment);
        mBlacklistRepository.saveAndFlush(mBlacklist);
        Long attachmentId = attachment.getId();

        // Get all the mBlacklistList where attachment equals to attachmentId
        defaultMBlacklistShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mBlacklistList where attachment equals to attachmentId + 1
        defaultMBlacklistShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBlacklist.getAdOrganization();
        mBlacklistRepository.saveAndFlush(mBlacklist);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBlacklistList where adOrganization equals to adOrganizationId
        defaultMBlacklistShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBlacklistList where adOrganization equals to adOrganizationId + 1
        defaultMBlacklistShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mBlacklist.getVendor();
        mBlacklistRepository.saveAndFlush(mBlacklist);
        Long vendorId = vendor.getId();

        // Get all the mBlacklistList where vendor equals to vendorId
        defaultMBlacklistShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBlacklistList where vendor equals to vendorId + 1
        defaultMBlacklistShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mBlacklist.getBusinessCategory();
        mBlacklistRepository.saveAndFlush(mBlacklist);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mBlacklistList where businessCategory equals to businessCategoryId
        defaultMBlacklistShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mBlacklistList where businessCategory equals to businessCategoryId + 1
        defaultMBlacklistShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistsBySubBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);
        CBusinessCategory subBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(subBusinessCategory);
        em.flush();
        mBlacklist.setSubBusinessCategory(subBusinessCategory);
        mBlacklistRepository.saveAndFlush(mBlacklist);
        Long subBusinessCategoryId = subBusinessCategory.getId();

        // Get all the mBlacklistList where subBusinessCategory equals to subBusinessCategoryId
        defaultMBlacklistShouldBeFound("subBusinessCategoryId.equals=" + subBusinessCategoryId);

        // Get all the mBlacklistList where subBusinessCategory equals to subBusinessCategoryId + 1
        defaultMBlacklistShouldNotBeFound("subBusinessCategoryId.equals=" + (subBusinessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBlacklistShouldBeFound(String filter) throws Exception {
        restMBlacklistMockMvc.perform(get("/api/m-blacklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBlacklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restMBlacklistMockMvc.perform(get("/api/m-blacklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBlacklistShouldNotBeFound(String filter) throws Exception {
        restMBlacklistMockMvc.perform(get("/api/m-blacklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBlacklistMockMvc.perform(get("/api/m-blacklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBlacklist() throws Exception {
        // Get the mBlacklist
        restMBlacklistMockMvc.perform(get("/api/m-blacklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBlacklist() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        int databaseSizeBeforeUpdate = mBlacklistRepository.findAll().size();

        // Update the mBlacklist
        MBlacklist updatedMBlacklist = mBlacklistRepository.findById(mBlacklist.getId()).get();
        // Disconnect from session so that the updates on updatedMBlacklist are not directly saved in db
        em.detach(updatedMBlacklist);
        updatedMBlacklist
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .notes(UPDATED_NOTES)
            .type(UPDATED_TYPE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED);
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(updatedMBlacklist);

        restMBlacklistMockMvc.perform(put("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isOk());

        // Validate the MBlacklist in the database
        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeUpdate);
        MBlacklist testMBlacklist = mBlacklistList.get(mBlacklistList.size() - 1);
        assertThat(testMBlacklist.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBlacklist.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBlacklist.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testMBlacklist.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMBlacklist.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMBlacklist.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMBlacklist.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMBlacklist.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMBlacklist.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMBlacklist.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMBlacklist.isProcessed()).isEqualTo(UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingMBlacklist() throws Exception {
        int databaseSizeBeforeUpdate = mBlacklistRepository.findAll().size();

        // Create the MBlacklist
        MBlacklistDTO mBlacklistDTO = mBlacklistMapper.toDto(mBlacklist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBlacklistMockMvc.perform(put("/api/m-blacklists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBlacklist in the database
        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBlacklist() throws Exception {
        // Initialize the database
        mBlacklistRepository.saveAndFlush(mBlacklist);

        int databaseSizeBeforeDelete = mBlacklistRepository.findAll().size();

        // Delete the mBlacklist
        restMBlacklistMockMvc.perform(delete("/api/m-blacklists/{id}", mBlacklist.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBlacklist> mBlacklistList = mBlacklistRepository.findAll();
        assertThat(mBlacklistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
