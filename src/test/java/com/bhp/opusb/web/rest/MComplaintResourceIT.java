package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MComplaint;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MComplaintRepository;
import com.bhp.opusb.service.MComplaintService;
import com.bhp.opusb.service.dto.MComplaintDTO;
import com.bhp.opusb.service.mapper.MComplaintMapper;
import com.bhp.opusb.service.dto.MComplaintCriteria;
import com.bhp.opusb.service.MComplaintQueryService;

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
 * Integration tests for the {@link MComplaintResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MComplaintResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REPORT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_WARNING = "AAAAAAAAAA";
    private static final String UPDATED_WARNING = "BBBBBBBBBB";

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
    private MComplaintRepository mComplaintRepository;

    @Autowired
    private MComplaintMapper mComplaintMapper;

    @Autowired
    private MComplaintService mComplaintService;

    @Autowired
    private MComplaintQueryService mComplaintQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMComplaintMockMvc;

    private MComplaint mComplaint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MComplaint createEntity(EntityManager em) {
        MComplaint mComplaint = new MComplaint()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .reportDate(DEFAULT_REPORT_DATE)
            .warning(DEFAULT_WARNING)
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
        mComplaint.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mComplaint.setVendor(cVendor);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mComplaint.setCostCenter(cCostCenter);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mComplaint.setContract(mContract);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mComplaint.setBusinessCategory(cBusinessCategory);
        return mComplaint;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MComplaint createUpdatedEntity(EntityManager em) {
        MComplaint mComplaint = new MComplaint()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .warning(UPDATED_WARNING)
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
        mComplaint.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mComplaint.setVendor(cVendor);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mComplaint.setCostCenter(cCostCenter);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mComplaint.setContract(mContract);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mComplaint.setBusinessCategory(cBusinessCategory);
        return mComplaint;
    }

    @BeforeEach
    public void initTest() {
        mComplaint = createEntity(em);
    }

    @Test
    @Transactional
    public void createMComplaint() throws Exception {
        int databaseSizeBeforeCreate = mComplaintRepository.findAll().size();

        // Create the MComplaint
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);
        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isCreated());

        // Validate the MComplaint in the database
        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeCreate + 1);
        MComplaint testMComplaint = mComplaintList.get(mComplaintList.size() - 1);
        assertThat(testMComplaint.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMComplaint.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMComplaint.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testMComplaint.getWarning()).isEqualTo(DEFAULT_WARNING);
        assertThat(testMComplaint.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMComplaint.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMComplaint.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMComplaint.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMComplaint.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMComplaint.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMComplaint.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
    }

    @Test
    @Transactional
    public void createMComplaintWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mComplaintRepository.findAll().size();

        // Create the MComplaint with an existing ID
        mComplaint.setId(1L);
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MComplaint in the database
        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReportDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mComplaintRepository.findAll().size();
        // set the field null
        mComplaint.setReportDate(null);

        // Create the MComplaint, which fails.
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mComplaintRepository.findAll().size();
        // set the field null
        mComplaint.setDateTrx(null);

        // Create the MComplaint, which fails.
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mComplaintRepository.findAll().size();
        // set the field null
        mComplaint.setDocumentAction(null);

        // Create the MComplaint, which fails.
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mComplaintRepository.findAll().size();
        // set the field null
        mComplaint.setDocumentStatus(null);

        // Create the MComplaint, which fails.
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        restMComplaintMockMvc.perform(post("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMComplaints() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList
        restMComplaintMockMvc.perform(get("/api/m-complaints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mComplaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
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
    public void getMComplaint() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get the mComplaint
        restMComplaintMockMvc.perform(get("/api/m-complaints/{id}", mComplaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mComplaint.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()))
            .andExpect(jsonPath("$.warning").value(DEFAULT_WARNING.toString()))
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
    public void getMComplaintsByIdFiltering() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        Long id = mComplaint.getId();

        defaultMComplaintShouldBeFound("id.equals=" + id);
        defaultMComplaintShouldNotBeFound("id.notEquals=" + id);

        defaultMComplaintShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMComplaintShouldNotBeFound("id.greaterThan=" + id);

        defaultMComplaintShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMComplaintShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where uid equals to DEFAULT_UID
        defaultMComplaintShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mComplaintList where uid equals to UPDATED_UID
        defaultMComplaintShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where uid not equals to DEFAULT_UID
        defaultMComplaintShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mComplaintList where uid not equals to UPDATED_UID
        defaultMComplaintShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where uid in DEFAULT_UID or UPDATED_UID
        defaultMComplaintShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mComplaintList where uid equals to UPDATED_UID
        defaultMComplaintShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where uid is not null
        defaultMComplaintShouldBeFound("uid.specified=true");

        // Get all the mComplaintList where uid is null
        defaultMComplaintShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where active equals to DEFAULT_ACTIVE
        defaultMComplaintShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mComplaintList where active equals to UPDATED_ACTIVE
        defaultMComplaintShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where active not equals to DEFAULT_ACTIVE
        defaultMComplaintShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mComplaintList where active not equals to UPDATED_ACTIVE
        defaultMComplaintShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMComplaintShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mComplaintList where active equals to UPDATED_ACTIVE
        defaultMComplaintShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where active is not null
        defaultMComplaintShouldBeFound("active.specified=true");

        // Get all the mComplaintList where active is null
        defaultMComplaintShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate equals to DEFAULT_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.equals=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate equals to UPDATED_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.equals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate not equals to DEFAULT_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.notEquals=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate not equals to UPDATED_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.notEquals=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate in DEFAULT_REPORT_DATE or UPDATED_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.in=" + DEFAULT_REPORT_DATE + "," + UPDATED_REPORT_DATE);

        // Get all the mComplaintList where reportDate equals to UPDATED_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.in=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate is not null
        defaultMComplaintShouldBeFound("reportDate.specified=true");

        // Get all the mComplaintList where reportDate is null
        defaultMComplaintShouldNotBeFound("reportDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate is greater than or equal to DEFAULT_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.greaterThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate is greater than or equal to UPDATED_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.greaterThanOrEqual=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate is less than or equal to DEFAULT_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.lessThanOrEqual=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate is less than or equal to SMALLER_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.lessThanOrEqual=" + SMALLER_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate is less than DEFAULT_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.lessThan=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate is less than UPDATED_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.lessThan=" + UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByReportDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where reportDate is greater than DEFAULT_REPORT_DATE
        defaultMComplaintShouldNotBeFound("reportDate.greaterThan=" + DEFAULT_REPORT_DATE);

        // Get all the mComplaintList where reportDate is greater than SMALLER_REPORT_DATE
        defaultMComplaintShouldBeFound("reportDate.greaterThan=" + SMALLER_REPORT_DATE);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type equals to DEFAULT_TYPE
        defaultMComplaintShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the mComplaintList where type equals to UPDATED_TYPE
        defaultMComplaintShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type not equals to DEFAULT_TYPE
        defaultMComplaintShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the mComplaintList where type not equals to UPDATED_TYPE
        defaultMComplaintShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMComplaintShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the mComplaintList where type equals to UPDATED_TYPE
        defaultMComplaintShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type is not null
        defaultMComplaintShouldBeFound("type.specified=true");

        // Get all the mComplaintList where type is null
        defaultMComplaintShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllMComplaintsByTypeContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type contains DEFAULT_TYPE
        defaultMComplaintShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the mComplaintList where type contains UPDATED_TYPE
        defaultMComplaintShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where type does not contain DEFAULT_TYPE
        defaultMComplaintShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the mComplaintList where type does not contain UPDATED_TYPE
        defaultMComplaintShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx equals to UPDATED_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mComplaintList where dateTrx equals to UPDATED_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx is not null
        defaultMComplaintShouldBeFound("dateTrx.specified=true");

        // Get all the mComplaintList where dateTrx is null
        defaultMComplaintShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx is less than UPDATED_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMComplaintShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mComplaintList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMComplaintShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMComplaintShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mComplaintList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMComplaintShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMComplaintShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mComplaintList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMComplaintShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMComplaintShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mComplaintList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMComplaintShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo is not null
        defaultMComplaintShouldBeFound("documentNo.specified=true");

        // Get all the mComplaintList where documentNo is null
        defaultMComplaintShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMComplaintShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mComplaintList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMComplaintShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMComplaintShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mComplaintList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMComplaintShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMComplaintShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mComplaintList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMComplaintShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mComplaintList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mComplaintList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction is not null
        defaultMComplaintShouldBeFound("documentAction.specified=true");

        // Get all the mComplaintList where documentAction is null
        defaultMComplaintShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMComplaintShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mComplaintList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMComplaintShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mComplaintList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMComplaintShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMComplaintShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mComplaintList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMComplaintShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mComplaintList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mComplaintList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus is not null
        defaultMComplaintShouldBeFound("documentStatus.specified=true");

        // Get all the mComplaintList where documentStatus is null
        defaultMComplaintShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMComplaintShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mComplaintList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMComplaintShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mComplaintList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMComplaintShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMComplaintsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where approved equals to DEFAULT_APPROVED
        defaultMComplaintShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mComplaintList where approved equals to UPDATED_APPROVED
        defaultMComplaintShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where approved not equals to DEFAULT_APPROVED
        defaultMComplaintShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mComplaintList where approved not equals to UPDATED_APPROVED
        defaultMComplaintShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMComplaintShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mComplaintList where approved equals to UPDATED_APPROVED
        defaultMComplaintShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where approved is not null
        defaultMComplaintShouldBeFound("approved.specified=true");

        // Get all the mComplaintList where approved is null
        defaultMComplaintShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where processed equals to DEFAULT_PROCESSED
        defaultMComplaintShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mComplaintList where processed equals to UPDATED_PROCESSED
        defaultMComplaintShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where processed not equals to DEFAULT_PROCESSED
        defaultMComplaintShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mComplaintList where processed not equals to UPDATED_PROCESSED
        defaultMComplaintShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMComplaintShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mComplaintList where processed equals to UPDATED_PROCESSED
        defaultMComplaintShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMComplaintsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        // Get all the mComplaintList where processed is not null
        defaultMComplaintShouldBeFound("processed.specified=true");

        // Get all the mComplaintList where processed is null
        defaultMComplaintShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMComplaintsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mComplaint.getAdOrganization();
        mComplaintRepository.saveAndFlush(mComplaint);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mComplaintList where adOrganization equals to adOrganizationId
        defaultMComplaintShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mComplaintList where adOrganization equals to adOrganizationId + 1
        defaultMComplaintShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMComplaintsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mComplaint.getVendor();
        mComplaintRepository.saveAndFlush(mComplaint);
        Long vendorId = vendor.getId();

        // Get all the mComplaintList where vendor equals to vendorId
        defaultMComplaintShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mComplaintList where vendor equals to vendorId + 1
        defaultMComplaintShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMComplaintsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mComplaint.getCostCenter();
        mComplaintRepository.saveAndFlush(mComplaint);
        Long costCenterId = costCenter.getId();

        // Get all the mComplaintList where costCenter equals to costCenterId
        defaultMComplaintShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mComplaintList where costCenter equals to costCenterId + 1
        defaultMComplaintShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMComplaintsByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mComplaint.getContract();
        mComplaintRepository.saveAndFlush(mComplaint);
        Long contractId = contract.getId();

        // Get all the mComplaintList where contract equals to contractId
        defaultMComplaintShouldBeFound("contractId.equals=" + contractId);

        // Get all the mComplaintList where contract equals to contractId + 1
        defaultMComplaintShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMComplaintsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mComplaint.getBusinessCategory();
        mComplaintRepository.saveAndFlush(mComplaint);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mComplaintList where businessCategory equals to businessCategoryId
        defaultMComplaintShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mComplaintList where businessCategory equals to businessCategoryId + 1
        defaultMComplaintShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMComplaintsBySubBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);
        CBusinessCategory subBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(subBusinessCategory);
        em.flush();
        mComplaint.setSubBusinessCategory(subBusinessCategory);
        mComplaintRepository.saveAndFlush(mComplaint);
        Long subBusinessCategoryId = subBusinessCategory.getId();

        // Get all the mComplaintList where subBusinessCategory equals to subBusinessCategoryId
        defaultMComplaintShouldBeFound("subBusinessCategoryId.equals=" + subBusinessCategoryId);

        // Get all the mComplaintList where subBusinessCategory equals to subBusinessCategoryId + 1
        defaultMComplaintShouldNotBeFound("subBusinessCategoryId.equals=" + (subBusinessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMComplaintShouldBeFound(String filter) throws Exception {
        restMComplaintMockMvc.perform(get("/api/m-complaints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mComplaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restMComplaintMockMvc.perform(get("/api/m-complaints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMComplaintShouldNotBeFound(String filter) throws Exception {
        restMComplaintMockMvc.perform(get("/api/m-complaints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMComplaintMockMvc.perform(get("/api/m-complaints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMComplaint() throws Exception {
        // Get the mComplaint
        restMComplaintMockMvc.perform(get("/api/m-complaints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMComplaint() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        int databaseSizeBeforeUpdate = mComplaintRepository.findAll().size();

        // Update the mComplaint
        MComplaint updatedMComplaint = mComplaintRepository.findById(mComplaint.getId()).get();
        // Disconnect from session so that the updates on updatedMComplaint are not directly saved in db
        em.detach(updatedMComplaint);
        updatedMComplaint
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .reportDate(UPDATED_REPORT_DATE)
            .warning(UPDATED_WARNING)
            .type(UPDATED_TYPE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED);
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(updatedMComplaint);

        restMComplaintMockMvc.perform(put("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isOk());

        // Validate the MComplaint in the database
        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeUpdate);
        MComplaint testMComplaint = mComplaintList.get(mComplaintList.size() - 1);
        assertThat(testMComplaint.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMComplaint.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMComplaint.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testMComplaint.getWarning()).isEqualTo(UPDATED_WARNING);
        assertThat(testMComplaint.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMComplaint.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMComplaint.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMComplaint.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMComplaint.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMComplaint.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMComplaint.isProcessed()).isEqualTo(UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingMComplaint() throws Exception {
        int databaseSizeBeforeUpdate = mComplaintRepository.findAll().size();

        // Create the MComplaint
        MComplaintDTO mComplaintDTO = mComplaintMapper.toDto(mComplaint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMComplaintMockMvc.perform(put("/api/m-complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mComplaintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MComplaint in the database
        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMComplaint() throws Exception {
        // Initialize the database
        mComplaintRepository.saveAndFlush(mComplaint);

        int databaseSizeBeforeDelete = mComplaintRepository.findAll().size();

        // Delete the mComplaint
        restMComplaintMockMvc.perform(delete("/api/m-complaints/{id}", mComplaint.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MComplaint> mComplaintList = mComplaintRepository.findAll();
        assertThat(mComplaintList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
