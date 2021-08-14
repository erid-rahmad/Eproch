package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRfqSubmission;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.MQuoteSupplier;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.repository.MRfqSubmissionRepository;
import com.bhp.opusb.service.MRfqSubmissionService;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionMapper;
import com.bhp.opusb.service.dto.MRfqSubmissionCriteria;
import com.bhp.opusb.service.MRfqSubmissionQueryService;

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
 * Integration tests for the {@link MRfqSubmissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRfqSubmissionResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final LocalDate DEFAULT_DATE_TRX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRX = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRX = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_SUBMITTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SUBMITTED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_SUBMITTED = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_SUBMISSION_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBMISSION_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUBMISSION_GRAND_TOTAL = new BigDecimal(1 - 1);

    @Autowired
    private MRfqSubmissionRepository mRfqSubmissionRepository;

    @Autowired
    private MRfqSubmissionMapper mRfqSubmissionMapper;

    @Autowired
    private MRfqSubmissionService mRfqSubmissionService;

    @Autowired
    private MRfqSubmissionQueryService mRfqSubmissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRfqSubmissionMockMvc;

    private MRfqSubmission mRfqSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqSubmission createEntity(EntityManager em) {
        MRfqSubmission mRfqSubmission = new MRfqSubmission()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateTrx(DEFAULT_DATE_TRX)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .dateSubmitted(DEFAULT_DATE_SUBMITTED)
            .submissionGrandTotal(DEFAULT_SUBMISSION_GRAND_TOTAL);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqSubmission.setQuotation(mRfq);
        // Add required entity
        MQuoteSupplier mQuoteSupplier;
        if (TestUtil.findAll(em, MQuoteSupplier.class).isEmpty()) {
            mQuoteSupplier = MQuoteSupplierResourceIT.createEntity(em);
            em.persist(mQuoteSupplier);
            em.flush();
        } else {
            mQuoteSupplier = TestUtil.findAll(em, MQuoteSupplier.class).get(0);
        }
        mRfqSubmission.setQuoteSupplier(mQuoteSupplier);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqSubmission.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mRfqSubmission.setBusinessCategory(cBusinessCategory);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRfqSubmission.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRfqSubmission.setWarehouse(cWarehouse);
        return mRfqSubmission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqSubmission createUpdatedEntity(EntityManager em) {
        MRfqSubmission mRfqSubmission = new MRfqSubmission()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .dateSubmitted(UPDATED_DATE_SUBMITTED)
            .submissionGrandTotal(UPDATED_SUBMISSION_GRAND_TOTAL);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createUpdatedEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqSubmission.setQuotation(mRfq);
        // Add required entity
        MQuoteSupplier mQuoteSupplier;
        if (TestUtil.findAll(em, MQuoteSupplier.class).isEmpty()) {
            mQuoteSupplier = MQuoteSupplierResourceIT.createUpdatedEntity(em);
            em.persist(mQuoteSupplier);
            em.flush();
        } else {
            mQuoteSupplier = TestUtil.findAll(em, MQuoteSupplier.class).get(0);
        }
        mRfqSubmission.setQuoteSupplier(mQuoteSupplier);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqSubmission.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mRfqSubmission.setBusinessCategory(cBusinessCategory);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRfqSubmission.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createUpdatedEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRfqSubmission.setWarehouse(cWarehouse);
        return mRfqSubmission;
    }

    @BeforeEach
    public void initTest() {
        mRfqSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRfqSubmission() throws Exception {
        int databaseSizeBeforeCreate = mRfqSubmissionRepository.findAll().size();

        // Create the MRfqSubmission
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(mRfqSubmission);
        restMRfqSubmissionMockMvc.perform(post("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the MRfqSubmission in the database
        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        MRfqSubmission testMRfqSubmission = mRfqSubmissionList.get(mRfqSubmissionList.size() - 1);
        assertThat(testMRfqSubmission.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRfqSubmission.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMRfqSubmission.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMRfqSubmission.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMRfqSubmission.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMRfqSubmission.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMRfqSubmission.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMRfqSubmission.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMRfqSubmission.getDateSubmitted()).isEqualTo(DEFAULT_DATE_SUBMITTED);
        assertThat(testMRfqSubmission.getSubmissionGrandTotal()).isEqualTo(DEFAULT_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void createMRfqSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRfqSubmissionRepository.findAll().size();

        // Create the MRfqSubmission with an existing ID
        mRfqSubmission.setId(1L);
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(mRfqSubmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRfqSubmissionMockMvc.perform(post("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqSubmission in the database
        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqSubmissionRepository.findAll().size();
        // set the field null
        mRfqSubmission.setDocumentAction(null);

        // Create the MRfqSubmission, which fails.
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(mRfqSubmission);

        restMRfqSubmissionMockMvc.perform(post("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqSubmissionRepository.findAll().size();
        // set the field null
        mRfqSubmission.setDocumentStatus(null);

        // Create the MRfqSubmission, which fails.
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(mRfqSubmission);

        restMRfqSubmissionMockMvc.perform(post("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissions() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].dateSubmitted").value(hasItem(DEFAULT_DATE_SUBMITTED.toString())))
            .andExpect(jsonPath("$.[*].submissionGrandTotal").value(hasItem(DEFAULT_SUBMISSION_GRAND_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getMRfqSubmission() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get the mRfqSubmission
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions/{id}", mRfqSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRfqSubmission.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.dateSubmitted").value(DEFAULT_DATE_SUBMITTED.toString()))
            .andExpect(jsonPath("$.submissionGrandTotal").value(DEFAULT_SUBMISSION_GRAND_TOTAL.intValue()));
    }


    @Test
    @Transactional
    public void getMRfqSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        Long id = mRfqSubmission.getId();

        defaultMRfqSubmissionShouldBeFound("id.equals=" + id);
        defaultMRfqSubmissionShouldNotBeFound("id.notEquals=" + id);

        defaultMRfqSubmissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRfqSubmissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMRfqSubmissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRfqSubmissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where uid equals to DEFAULT_UID
        defaultMRfqSubmissionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRfqSubmissionList where uid equals to UPDATED_UID
        defaultMRfqSubmissionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where uid not equals to DEFAULT_UID
        defaultMRfqSubmissionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRfqSubmissionList where uid not equals to UPDATED_UID
        defaultMRfqSubmissionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRfqSubmissionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRfqSubmissionList where uid equals to UPDATED_UID
        defaultMRfqSubmissionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where uid is not null
        defaultMRfqSubmissionShouldBeFound("uid.specified=true");

        // Get all the mRfqSubmissionList where uid is null
        defaultMRfqSubmissionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where active equals to DEFAULT_ACTIVE
        defaultMRfqSubmissionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRfqSubmissionList where active equals to UPDATED_ACTIVE
        defaultMRfqSubmissionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where active not equals to DEFAULT_ACTIVE
        defaultMRfqSubmissionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRfqSubmissionList where active not equals to UPDATED_ACTIVE
        defaultMRfqSubmissionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRfqSubmissionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRfqSubmissionList where active equals to UPDATED_ACTIVE
        defaultMRfqSubmissionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where active is not null
        defaultMRfqSubmissionShouldBeFound("active.specified=true");

        // Get all the mRfqSubmissionList where active is null
        defaultMRfqSubmissionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction is not null
        defaultMRfqSubmissionShouldBeFound("documentAction.specified=true");

        // Get all the mRfqSubmissionList where documentAction is null
        defaultMRfqSubmissionShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus is not null
        defaultMRfqSubmissionShouldBeFound("documentStatus.specified=true");

        // Get all the mRfqSubmissionList where documentStatus is null
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where approved equals to DEFAULT_APPROVED
        defaultMRfqSubmissionShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mRfqSubmissionList where approved equals to UPDATED_APPROVED
        defaultMRfqSubmissionShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where approved not equals to DEFAULT_APPROVED
        defaultMRfqSubmissionShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mRfqSubmissionList where approved not equals to UPDATED_APPROVED
        defaultMRfqSubmissionShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMRfqSubmissionShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mRfqSubmissionList where approved equals to UPDATED_APPROVED
        defaultMRfqSubmissionShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where approved is not null
        defaultMRfqSubmissionShouldBeFound("approved.specified=true");

        // Get all the mRfqSubmissionList where approved is null
        defaultMRfqSubmissionShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where processed equals to DEFAULT_PROCESSED
        defaultMRfqSubmissionShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mRfqSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMRfqSubmissionShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where processed not equals to DEFAULT_PROCESSED
        defaultMRfqSubmissionShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mRfqSubmissionList where processed not equals to UPDATED_PROCESSED
        defaultMRfqSubmissionShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMRfqSubmissionShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mRfqSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMRfqSubmissionShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where processed is not null
        defaultMRfqSubmissionShouldBeFound("processed.specified=true");

        // Get all the mRfqSubmissionList where processed is null
        defaultMRfqSubmissionShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx is not null
        defaultMRfqSubmissionShouldBeFound("dateTrx.specified=true");

        // Get all the mRfqSubmissionList where dateTrx is null
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx is less than UPDATED_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMRfqSubmissionShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMRfqSubmissionShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired is not null
        defaultMRfqSubmissionShouldBeFound("dateRequired.specified=true");

        // Get all the mRfqSubmissionList where dateRequired is null
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRfqSubmissionShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted equals to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.equals=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.equals=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted not equals to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.notEquals=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted not equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.notEquals=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted in DEFAULT_DATE_SUBMITTED or UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.in=" + DEFAULT_DATE_SUBMITTED + "," + UPDATED_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.in=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted is not null
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.specified=true");

        // Get all the mRfqSubmissionList where dateSubmitted is null
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted is greater than or equal to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.greaterThanOrEqual=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted is greater than or equal to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.greaterThanOrEqual=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted is less than or equal to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.lessThanOrEqual=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted is less than or equal to SMALLER_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.lessThanOrEqual=" + SMALLER_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted is less than DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.lessThan=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted is less than UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.lessThan=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsByDateSubmittedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where dateSubmitted is greater than DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionShouldNotBeFound("dateSubmitted.greaterThan=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionList where dateSubmitted is greater than SMALLER_DATE_SUBMITTED
        defaultMRfqSubmissionShouldBeFound("dateSubmitted.greaterThan=" + SMALLER_DATE_SUBMITTED);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal equals to DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.equals=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal equals to UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.equals=" + UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal not equals to DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.notEquals=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal not equals to UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.notEquals=" + UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal in DEFAULT_SUBMISSION_GRAND_TOTAL or UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.in=" + DEFAULT_SUBMISSION_GRAND_TOTAL + "," + UPDATED_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal equals to UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.in=" + UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal is not null
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.specified=true");

        // Get all the mRfqSubmissionList where submissionGrandTotal is null
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal is greater than or equal to DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.greaterThanOrEqual=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal is greater than or equal to UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.greaterThanOrEqual=" + UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal is less than or equal to DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.lessThanOrEqual=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal is less than or equal to SMALLER_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.lessThanOrEqual=" + SMALLER_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal is less than DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.lessThan=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal is less than UPDATED_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.lessThan=" + UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionsBySubmissionGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        // Get all the mRfqSubmissionList where submissionGrandTotal is greater than DEFAULT_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldNotBeFound("submissionGrandTotal.greaterThan=" + DEFAULT_SUBMISSION_GRAND_TOTAL);

        // Get all the mRfqSubmissionList where submissionGrandTotal is greater than SMALLER_SUBMISSION_GRAND_TOTAL
        defaultMRfqSubmissionShouldBeFound("submissionGrandTotal.greaterThan=" + SMALLER_SUBMISSION_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByQuotationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfq quotation = mRfqSubmission.getQuotation();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long quotationId = quotation.getId();

        // Get all the mRfqSubmissionList where quotation equals to quotationId
        defaultMRfqSubmissionShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the mRfqSubmissionList where quotation equals to quotationId + 1
        defaultMRfqSubmissionShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByQuoteSupplierIsEqualToSomething() throws Exception {
        // Get already existing entity
        MQuoteSupplier quoteSupplier = mRfqSubmission.getQuoteSupplier();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long quoteSupplierId = quoteSupplier.getId();

        // Get all the mRfqSubmissionList where quoteSupplier equals to quoteSupplierId
        defaultMRfqSubmissionShouldBeFound("quoteSupplierId.equals=" + quoteSupplierId);

        // Get all the mRfqSubmissionList where quoteSupplier equals to quoteSupplierId + 1
        defaultMRfqSubmissionShouldNotBeFound("quoteSupplierId.equals=" + (quoteSupplierId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRfqSubmission.getAdOrganization();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRfqSubmissionList where adOrganization equals to adOrganizationId
        defaultMRfqSubmissionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRfqSubmissionList where adOrganization equals to adOrganizationId + 1
        defaultMRfqSubmissionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByBusinessClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessClassification = mRfqSubmission.getBusinessClassification();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long businessClassificationId = businessClassification.getId();

        // Get all the mRfqSubmissionList where businessClassification equals to businessClassificationId
        defaultMRfqSubmissionShouldBeFound("businessClassificationId.equals=" + businessClassificationId);

        // Get all the mRfqSubmissionList where businessClassification equals to businessClassificationId + 1
        defaultMRfqSubmissionShouldNotBeFound("businessClassificationId.equals=" + (businessClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mRfqSubmission.getBusinessCategory();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mRfqSubmissionList where businessCategory equals to businessCategoryId
        defaultMRfqSubmissionShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mRfqSubmissionList where businessCategory equals to businessCategoryId + 1
        defaultMRfqSubmissionShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mRfqSubmission.getCurrency();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long currencyId = currency.getId();

        // Get all the mRfqSubmissionList where currency equals to currencyId
        defaultMRfqSubmissionShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mRfqSubmissionList where currency equals to currencyId + 1
        defaultMRfqSubmissionShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByWarehouseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CWarehouse warehouse = mRfqSubmission.getWarehouse();
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long warehouseId = warehouse.getId();

        // Get all the mRfqSubmissionList where warehouse equals to warehouseId
        defaultMRfqSubmissionShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mRfqSubmissionList where warehouse equals to warehouseId + 1
        defaultMRfqSubmissionShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionsByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        CCostCenter costCenter = CCostCenterResourceIT.createEntity(em);
        em.persist(costCenter);
        em.flush();
        mRfqSubmission.setCostCenter(costCenter);
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);
        Long costCenterId = costCenter.getId();

        // Get all the mRfqSubmissionList where costCenter equals to costCenterId
        defaultMRfqSubmissionShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mRfqSubmissionList where costCenter equals to costCenterId + 1
        defaultMRfqSubmissionShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRfqSubmissionShouldBeFound(String filter) throws Exception {
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].dateSubmitted").value(hasItem(DEFAULT_DATE_SUBMITTED.toString())))
            .andExpect(jsonPath("$.[*].submissionGrandTotal").value(hasItem(DEFAULT_SUBMISSION_GRAND_TOTAL.intValue())));

        // Check, that the count call also returns 1
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRfqSubmissionShouldNotBeFound(String filter) throws Exception {
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRfqSubmission() throws Exception {
        // Get the mRfqSubmission
        restMRfqSubmissionMockMvc.perform(get("/api/m-rfq-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRfqSubmission() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        int databaseSizeBeforeUpdate = mRfqSubmissionRepository.findAll().size();

        // Update the mRfqSubmission
        MRfqSubmission updatedMRfqSubmission = mRfqSubmissionRepository.findById(mRfqSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedMRfqSubmission are not directly saved in db
        em.detach(updatedMRfqSubmission);
        updatedMRfqSubmission
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .dateSubmitted(UPDATED_DATE_SUBMITTED)
            .submissionGrandTotal(UPDATED_SUBMISSION_GRAND_TOTAL);
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(updatedMRfqSubmission);

        restMRfqSubmissionMockMvc.perform(put("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isOk());

        // Validate the MRfqSubmission in the database
        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeUpdate);
        MRfqSubmission testMRfqSubmission = mRfqSubmissionList.get(mRfqSubmissionList.size() - 1);
        assertThat(testMRfqSubmission.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRfqSubmission.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMRfqSubmission.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMRfqSubmission.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMRfqSubmission.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMRfqSubmission.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMRfqSubmission.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMRfqSubmission.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMRfqSubmission.getDateSubmitted()).isEqualTo(UPDATED_DATE_SUBMITTED);
        assertThat(testMRfqSubmission.getSubmissionGrandTotal()).isEqualTo(UPDATED_SUBMISSION_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMRfqSubmission() throws Exception {
        int databaseSizeBeforeUpdate = mRfqSubmissionRepository.findAll().size();

        // Create the MRfqSubmission
        MRfqSubmissionDTO mRfqSubmissionDTO = mRfqSubmissionMapper.toDto(mRfqSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRfqSubmissionMockMvc.perform(put("/api/m-rfq-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqSubmission in the database
        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRfqSubmission() throws Exception {
        // Initialize the database
        mRfqSubmissionRepository.saveAndFlush(mRfqSubmission);

        int databaseSizeBeforeDelete = mRfqSubmissionRepository.findAll().size();

        // Delete the mRfqSubmission
        restMRfqSubmissionMockMvc.perform(delete("/api/m-rfq-submissions/{id}", mRfqSubmission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRfqSubmission> mRfqSubmissionList = mRfqSubmissionRepository.findAll();
        assertThat(mRfqSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
