package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRfqSubmissionLine;
import com.bhp.opusb.domain.MRfqSubmission;
import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MRfqSubmissionLineRepository;
import com.bhp.opusb.service.MRfqSubmissionLineService;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionLineMapper;
import com.bhp.opusb.service.dto.MRfqSubmissionLineCriteria;
import com.bhp.opusb.service.MRfqSubmissionLineQueryService;

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
 * Integration tests for the {@link MRfqSubmissionLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRfqSubmissionLineResourceIT {

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

    private static final Integer DEFAULT_RELEASE_QTY = 1;
    private static final Integer UPDATED_RELEASE_QTY = 2;
    private static final Integer SMALLER_RELEASE_QTY = 1 - 1;

    private static final BigDecimal DEFAULT_SUBMISSION_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBMISSION_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUBMISSION_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SUBMISSION_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SUBMISSION_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SUBMISSION_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_TRX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRX = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRX = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_SUBMITTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SUBMITTED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_SUBMITTED = LocalDate.ofEpochDay(-1L);

    @Autowired
    private MRfqSubmissionLineRepository mRfqSubmissionLineRepository;

    @Autowired
    private MRfqSubmissionLineMapper mRfqSubmissionLineMapper;

    @Autowired
    private MRfqSubmissionLineService mRfqSubmissionLineService;

    @Autowired
    private MRfqSubmissionLineQueryService mRfqSubmissionLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRfqSubmissionLineMockMvc;

    private MRfqSubmissionLine mRfqSubmissionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqSubmissionLine createEntity(EntityManager em) {
        MRfqSubmissionLine mRfqSubmissionLine = new MRfqSubmissionLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .releaseQty(DEFAULT_RELEASE_QTY)
            .submissionPrice(DEFAULT_SUBMISSION_PRICE)
            .totalSubmissionPrice(DEFAULT_TOTAL_SUBMISSION_PRICE)
            .remark(DEFAULT_REMARK)
            .dateTrx(DEFAULT_DATE_TRX)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .dateSubmitted(DEFAULT_DATE_SUBMITTED);
        // Add required entity
        MRfqSubmission mRfqSubmission;
        if (TestUtil.findAll(em, MRfqSubmission.class).isEmpty()) {
            mRfqSubmission = MRfqSubmissionResourceIT.createEntity(em);
            em.persist(mRfqSubmission);
            em.flush();
        } else {
            mRfqSubmission = TestUtil.findAll(em, MRfqSubmission.class).get(0);
        }
        mRfqSubmissionLine.setSubmission(mRfqSubmission);
        // Add required entity
        MRfqLine mRfqLine;
        if (TestUtil.findAll(em, MRfqLine.class).isEmpty()) {
            mRfqLine = MRfqLineResourceIT.createEntity(em);
            em.persist(mRfqLine);
            em.flush();
        } else {
            mRfqLine = TestUtil.findAll(em, MRfqLine.class).get(0);
        }
        mRfqSubmissionLine.setQuotationLine(mRfqLine);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqSubmissionLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRfqSubmissionLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRfqSubmissionLine.setUom(cUnitOfMeasure);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqSubmissionLine.setBusinessCategory(cBusinessCategory);
        return mRfqSubmissionLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqSubmissionLine createUpdatedEntity(EntityManager em) {
        MRfqSubmissionLine mRfqSubmissionLine = new MRfqSubmissionLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .releaseQty(UPDATED_RELEASE_QTY)
            .submissionPrice(UPDATED_SUBMISSION_PRICE)
            .totalSubmissionPrice(UPDATED_TOTAL_SUBMISSION_PRICE)
            .remark(UPDATED_REMARK)
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .dateSubmitted(UPDATED_DATE_SUBMITTED);
        // Add required entity
        MRfqSubmission mRfqSubmission;
        if (TestUtil.findAll(em, MRfqSubmission.class).isEmpty()) {
            mRfqSubmission = MRfqSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mRfqSubmission);
            em.flush();
        } else {
            mRfqSubmission = TestUtil.findAll(em, MRfqSubmission.class).get(0);
        }
        mRfqSubmissionLine.setSubmission(mRfqSubmission);
        // Add required entity
        MRfqLine mRfqLine;
        if (TestUtil.findAll(em, MRfqLine.class).isEmpty()) {
            mRfqLine = MRfqLineResourceIT.createUpdatedEntity(em);
            em.persist(mRfqLine);
            em.flush();
        } else {
            mRfqLine = TestUtil.findAll(em, MRfqLine.class).get(0);
        }
        mRfqSubmissionLine.setQuotationLine(mRfqLine);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqSubmissionLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRfqSubmissionLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRfqSubmissionLine.setUom(cUnitOfMeasure);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqSubmissionLine.setBusinessCategory(cBusinessCategory);
        return mRfqSubmissionLine;
    }

    @BeforeEach
    public void initTest() {
        mRfqSubmissionLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRfqSubmissionLine() throws Exception {
        int databaseSizeBeforeCreate = mRfqSubmissionLineRepository.findAll().size();

        // Create the MRfqSubmissionLine
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);
        restMRfqSubmissionLineMockMvc.perform(post("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MRfqSubmissionLine in the database
        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeCreate + 1);
        MRfqSubmissionLine testMRfqSubmissionLine = mRfqSubmissionLineList.get(mRfqSubmissionLineList.size() - 1);
        assertThat(testMRfqSubmissionLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRfqSubmissionLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMRfqSubmissionLine.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMRfqSubmissionLine.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMRfqSubmissionLine.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMRfqSubmissionLine.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMRfqSubmissionLine.getReleaseQty()).isEqualTo(DEFAULT_RELEASE_QTY);
        assertThat(testMRfqSubmissionLine.getSubmissionPrice()).isEqualTo(DEFAULT_SUBMISSION_PRICE);
        assertThat(testMRfqSubmissionLine.getTotalSubmissionPrice()).isEqualTo(DEFAULT_TOTAL_SUBMISSION_PRICE);
        assertThat(testMRfqSubmissionLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMRfqSubmissionLine.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMRfqSubmissionLine.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMRfqSubmissionLine.getDateSubmitted()).isEqualTo(DEFAULT_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void createMRfqSubmissionLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRfqSubmissionLineRepository.findAll().size();

        // Create the MRfqSubmissionLine with an existing ID
        mRfqSubmissionLine.setId(1L);
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRfqSubmissionLineMockMvc.perform(post("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqSubmissionLine in the database
        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqSubmissionLineRepository.findAll().size();
        // set the field null
        mRfqSubmissionLine.setDocumentAction(null);

        // Create the MRfqSubmissionLine, which fails.
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);

        restMRfqSubmissionLineMockMvc.perform(post("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqSubmissionLineRepository.findAll().size();
        // set the field null
        mRfqSubmissionLine.setDocumentStatus(null);

        // Create the MRfqSubmissionLine, which fails.
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);

        restMRfqSubmissionLineMockMvc.perform(post("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLines() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqSubmissionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseQty").value(hasItem(DEFAULT_RELEASE_QTY)))
            .andExpect(jsonPath("$.[*].submissionPrice").value(hasItem(DEFAULT_SUBMISSION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalSubmissionPrice").value(hasItem(DEFAULT_TOTAL_SUBMISSION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].dateSubmitted").value(hasItem(DEFAULT_DATE_SUBMITTED.toString())));
    }
    
    @Test
    @Transactional
    public void getMRfqSubmissionLine() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get the mRfqSubmissionLine
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines/{id}", mRfqSubmissionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRfqSubmissionLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.releaseQty").value(DEFAULT_RELEASE_QTY))
            .andExpect(jsonPath("$.submissionPrice").value(DEFAULT_SUBMISSION_PRICE.intValue()))
            .andExpect(jsonPath("$.totalSubmissionPrice").value(DEFAULT_TOTAL_SUBMISSION_PRICE.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.dateSubmitted").value(DEFAULT_DATE_SUBMITTED.toString()));
    }


    @Test
    @Transactional
    public void getMRfqSubmissionLinesByIdFiltering() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        Long id = mRfqSubmissionLine.getId();

        defaultMRfqSubmissionLineShouldBeFound("id.equals=" + id);
        defaultMRfqSubmissionLineShouldNotBeFound("id.notEquals=" + id);

        defaultMRfqSubmissionLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRfqSubmissionLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMRfqSubmissionLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRfqSubmissionLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where uid equals to DEFAULT_UID
        defaultMRfqSubmissionLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRfqSubmissionLineList where uid equals to UPDATED_UID
        defaultMRfqSubmissionLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where uid not equals to DEFAULT_UID
        defaultMRfqSubmissionLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRfqSubmissionLineList where uid not equals to UPDATED_UID
        defaultMRfqSubmissionLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRfqSubmissionLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRfqSubmissionLineList where uid equals to UPDATED_UID
        defaultMRfqSubmissionLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where uid is not null
        defaultMRfqSubmissionLineShouldBeFound("uid.specified=true");

        // Get all the mRfqSubmissionLineList where uid is null
        defaultMRfqSubmissionLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where active equals to DEFAULT_ACTIVE
        defaultMRfqSubmissionLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRfqSubmissionLineList where active equals to UPDATED_ACTIVE
        defaultMRfqSubmissionLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where active not equals to DEFAULT_ACTIVE
        defaultMRfqSubmissionLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRfqSubmissionLineList where active not equals to UPDATED_ACTIVE
        defaultMRfqSubmissionLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRfqSubmissionLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRfqSubmissionLineList where active equals to UPDATED_ACTIVE
        defaultMRfqSubmissionLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where active is not null
        defaultMRfqSubmissionLineShouldBeFound("active.specified=true");

        // Get all the mRfqSubmissionLineList where active is null
        defaultMRfqSubmissionLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionLineList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionLineList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionLineList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction is not null
        defaultMRfqSubmissionLineShouldBeFound("documentAction.specified=true");

        // Get all the mRfqSubmissionLineList where documentAction is null
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionLineList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqSubmissionLineList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMRfqSubmissionLineShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionLineList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionLineList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionLineList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus is not null
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.specified=true");

        // Get all the mRfqSubmissionLineList where documentStatus is null
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionLineList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqSubmissionLineList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMRfqSubmissionLineShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where approved equals to DEFAULT_APPROVED
        defaultMRfqSubmissionLineShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mRfqSubmissionLineList where approved equals to UPDATED_APPROVED
        defaultMRfqSubmissionLineShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where approved not equals to DEFAULT_APPROVED
        defaultMRfqSubmissionLineShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mRfqSubmissionLineList where approved not equals to UPDATED_APPROVED
        defaultMRfqSubmissionLineShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMRfqSubmissionLineShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mRfqSubmissionLineList where approved equals to UPDATED_APPROVED
        defaultMRfqSubmissionLineShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where approved is not null
        defaultMRfqSubmissionLineShouldBeFound("approved.specified=true");

        // Get all the mRfqSubmissionLineList where approved is null
        defaultMRfqSubmissionLineShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where processed equals to DEFAULT_PROCESSED
        defaultMRfqSubmissionLineShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mRfqSubmissionLineList where processed equals to UPDATED_PROCESSED
        defaultMRfqSubmissionLineShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where processed not equals to DEFAULT_PROCESSED
        defaultMRfqSubmissionLineShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mRfqSubmissionLineList where processed not equals to UPDATED_PROCESSED
        defaultMRfqSubmissionLineShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMRfqSubmissionLineShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mRfqSubmissionLineList where processed equals to UPDATED_PROCESSED
        defaultMRfqSubmissionLineShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where processed is not null
        defaultMRfqSubmissionLineShouldBeFound("processed.specified=true");

        // Get all the mRfqSubmissionLineList where processed is null
        defaultMRfqSubmissionLineShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty equals to DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.equals=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty equals to UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.equals=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty not equals to DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.notEquals=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty not equals to UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.notEquals=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty in DEFAULT_RELEASE_QTY or UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.in=" + DEFAULT_RELEASE_QTY + "," + UPDATED_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty equals to UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.in=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty is not null
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.specified=true");

        // Get all the mRfqSubmissionLineList where releaseQty is null
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty is greater than or equal to DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.greaterThanOrEqual=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty is greater than or equal to UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.greaterThanOrEqual=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty is less than or equal to DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.lessThanOrEqual=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty is less than or equal to SMALLER_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.lessThanOrEqual=" + SMALLER_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty is less than DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.lessThan=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty is less than UPDATED_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.lessThan=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByReleaseQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where releaseQty is greater than DEFAULT_RELEASE_QTY
        defaultMRfqSubmissionLineShouldNotBeFound("releaseQty.greaterThan=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqSubmissionLineList where releaseQty is greater than SMALLER_RELEASE_QTY
        defaultMRfqSubmissionLineShouldBeFound("releaseQty.greaterThan=" + SMALLER_RELEASE_QTY);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice equals to DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.equals=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice equals to UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.equals=" + UPDATED_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice not equals to DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.notEquals=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice not equals to UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.notEquals=" + UPDATED_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice in DEFAULT_SUBMISSION_PRICE or UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.in=" + DEFAULT_SUBMISSION_PRICE + "," + UPDATED_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice equals to UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.in=" + UPDATED_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice is not null
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.specified=true");

        // Get all the mRfqSubmissionLineList where submissionPrice is null
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice is greater than or equal to DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.greaterThanOrEqual=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice is greater than or equal to UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.greaterThanOrEqual=" + UPDATED_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice is less than or equal to DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.lessThanOrEqual=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice is less than or equal to SMALLER_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.lessThanOrEqual=" + SMALLER_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice is less than DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.lessThan=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice is less than UPDATED_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.lessThan=" + UPDATED_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where submissionPrice is greater than DEFAULT_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("submissionPrice.greaterThan=" + DEFAULT_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where submissionPrice is greater than SMALLER_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("submissionPrice.greaterThan=" + SMALLER_SUBMISSION_PRICE);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice equals to DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.equals=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice equals to UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.equals=" + UPDATED_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice not equals to DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.notEquals=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice not equals to UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.notEquals=" + UPDATED_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice in DEFAULT_TOTAL_SUBMISSION_PRICE or UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.in=" + DEFAULT_TOTAL_SUBMISSION_PRICE + "," + UPDATED_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice equals to UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.in=" + UPDATED_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is not null
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.specified=true");

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is null
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is greater than or equal to DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is greater than or equal to UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.greaterThanOrEqual=" + UPDATED_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is less than or equal to DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.lessThanOrEqual=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is less than or equal to SMALLER_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.lessThanOrEqual=" + SMALLER_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is less than DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.lessThan=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is less than UPDATED_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.lessThan=" + UPDATED_TOTAL_SUBMISSION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByTotalSubmissionPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is greater than DEFAULT_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldNotBeFound("totalSubmissionPrice.greaterThan=" + DEFAULT_TOTAL_SUBMISSION_PRICE);

        // Get all the mRfqSubmissionLineList where totalSubmissionPrice is greater than SMALLER_TOTAL_SUBMISSION_PRICE
        defaultMRfqSubmissionLineShouldBeFound("totalSubmissionPrice.greaterThan=" + SMALLER_TOTAL_SUBMISSION_PRICE);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark equals to DEFAULT_REMARK
        defaultMRfqSubmissionLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mRfqSubmissionLineList where remark equals to UPDATED_REMARK
        defaultMRfqSubmissionLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark not equals to DEFAULT_REMARK
        defaultMRfqSubmissionLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mRfqSubmissionLineList where remark not equals to UPDATED_REMARK
        defaultMRfqSubmissionLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMRfqSubmissionLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mRfqSubmissionLineList where remark equals to UPDATED_REMARK
        defaultMRfqSubmissionLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark is not null
        defaultMRfqSubmissionLineShouldBeFound("remark.specified=true");

        // Get all the mRfqSubmissionLineList where remark is null
        defaultMRfqSubmissionLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark contains DEFAULT_REMARK
        defaultMRfqSubmissionLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mRfqSubmissionLineList where remark contains UPDATED_REMARK
        defaultMRfqSubmissionLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where remark does not contain DEFAULT_REMARK
        defaultMRfqSubmissionLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mRfqSubmissionLineList where remark does not contain UPDATED_REMARK
        defaultMRfqSubmissionLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx is not null
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.specified=true");

        // Get all the mRfqSubmissionLineList where dateTrx is null
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx is less than UPDATED_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMRfqSubmissionLineShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqSubmissionLineList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMRfqSubmissionLineShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired is not null
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.specified=true");

        // Get all the mRfqSubmissionLineList where dateRequired is null
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqSubmissionLineList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRfqSubmissionLineShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted equals to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.equals=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.equals=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted not equals to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.notEquals=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted not equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.notEquals=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted in DEFAULT_DATE_SUBMITTED or UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.in=" + DEFAULT_DATE_SUBMITTED + "," + UPDATED_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted equals to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.in=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted is not null
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.specified=true");

        // Get all the mRfqSubmissionLineList where dateSubmitted is null
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted is greater than or equal to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.greaterThanOrEqual=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted is greater than or equal to UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.greaterThanOrEqual=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted is less than or equal to DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.lessThanOrEqual=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted is less than or equal to SMALLER_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.lessThanOrEqual=" + SMALLER_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted is less than DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.lessThan=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted is less than UPDATED_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.lessThan=" + UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByDateSubmittedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        // Get all the mRfqSubmissionLineList where dateSubmitted is greater than DEFAULT_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldNotBeFound("dateSubmitted.greaterThan=" + DEFAULT_DATE_SUBMITTED);

        // Get all the mRfqSubmissionLineList where dateSubmitted is greater than SMALLER_DATE_SUBMITTED
        defaultMRfqSubmissionLineShouldBeFound("dateSubmitted.greaterThan=" + SMALLER_DATE_SUBMITTED);
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesBySubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfqSubmission submission = mRfqSubmissionLine.getSubmission();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long submissionId = submission.getId();

        // Get all the mRfqSubmissionLineList where submission equals to submissionId
        defaultMRfqSubmissionLineShouldBeFound("submissionId.equals=" + submissionId);

        // Get all the mRfqSubmissionLineList where submission equals to submissionId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("submissionId.equals=" + (submissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByQuotationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfqLine quotationLine = mRfqSubmissionLine.getQuotationLine();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long quotationLineId = quotationLine.getId();

        // Get all the mRfqSubmissionLineList where quotationLine equals to quotationLineId
        defaultMRfqSubmissionLineShouldBeFound("quotationLineId.equals=" + quotationLineId);

        // Get all the mRfqSubmissionLineList where quotationLine equals to quotationLineId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("quotationLineId.equals=" + (quotationLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRfqSubmissionLine.getAdOrganization();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRfqSubmissionLineList where adOrganization equals to adOrganizationId
        defaultMRfqSubmissionLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRfqSubmissionLineList where adOrganization equals to adOrganizationId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mRfqSubmissionLine.getProduct();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long productId = product.getId();

        // Get all the mRfqSubmissionLineList where product equals to productId
        defaultMRfqSubmissionLineShouldBeFound("productId.equals=" + productId);

        // Get all the mRfqSubmissionLineList where product equals to productId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mRfqSubmissionLine.getUom();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long uomId = uom.getId();

        // Get all the mRfqSubmissionLineList where uom equals to uomId
        defaultMRfqSubmissionLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mRfqSubmissionLineList where uom equals to uomId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqSubmissionLinesByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mRfqSubmissionLine.getBusinessCategory();
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mRfqSubmissionLineList where businessCategory equals to businessCategoryId
        defaultMRfqSubmissionLineShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mRfqSubmissionLineList where businessCategory equals to businessCategoryId + 1
        defaultMRfqSubmissionLineShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRfqSubmissionLineShouldBeFound(String filter) throws Exception {
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqSubmissionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseQty").value(hasItem(DEFAULT_RELEASE_QTY)))
            .andExpect(jsonPath("$.[*].submissionPrice").value(hasItem(DEFAULT_SUBMISSION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalSubmissionPrice").value(hasItem(DEFAULT_TOTAL_SUBMISSION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].dateSubmitted").value(hasItem(DEFAULT_DATE_SUBMITTED.toString())));

        // Check, that the count call also returns 1
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRfqSubmissionLineShouldNotBeFound(String filter) throws Exception {
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRfqSubmissionLine() throws Exception {
        // Get the mRfqSubmissionLine
        restMRfqSubmissionLineMockMvc.perform(get("/api/m-rfq-submission-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRfqSubmissionLine() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        int databaseSizeBeforeUpdate = mRfqSubmissionLineRepository.findAll().size();

        // Update the mRfqSubmissionLine
        MRfqSubmissionLine updatedMRfqSubmissionLine = mRfqSubmissionLineRepository.findById(mRfqSubmissionLine.getId()).get();
        // Disconnect from session so that the updates on updatedMRfqSubmissionLine are not directly saved in db
        em.detach(updatedMRfqSubmissionLine);
        updatedMRfqSubmissionLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .releaseQty(UPDATED_RELEASE_QTY)
            .submissionPrice(UPDATED_SUBMISSION_PRICE)
            .totalSubmissionPrice(UPDATED_TOTAL_SUBMISSION_PRICE)
            .remark(UPDATED_REMARK)
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .dateSubmitted(UPDATED_DATE_SUBMITTED);
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(updatedMRfqSubmissionLine);

        restMRfqSubmissionLineMockMvc.perform(put("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isOk());

        // Validate the MRfqSubmissionLine in the database
        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeUpdate);
        MRfqSubmissionLine testMRfqSubmissionLine = mRfqSubmissionLineList.get(mRfqSubmissionLineList.size() - 1);
        assertThat(testMRfqSubmissionLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRfqSubmissionLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMRfqSubmissionLine.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMRfqSubmissionLine.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMRfqSubmissionLine.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMRfqSubmissionLine.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMRfqSubmissionLine.getReleaseQty()).isEqualTo(UPDATED_RELEASE_QTY);
        assertThat(testMRfqSubmissionLine.getSubmissionPrice()).isEqualTo(UPDATED_SUBMISSION_PRICE);
        assertThat(testMRfqSubmissionLine.getTotalSubmissionPrice()).isEqualTo(UPDATED_TOTAL_SUBMISSION_PRICE);
        assertThat(testMRfqSubmissionLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMRfqSubmissionLine.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMRfqSubmissionLine.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMRfqSubmissionLine.getDateSubmitted()).isEqualTo(UPDATED_DATE_SUBMITTED);
    }

    @Test
    @Transactional
    public void updateNonExistingMRfqSubmissionLine() throws Exception {
        int databaseSizeBeforeUpdate = mRfqSubmissionLineRepository.findAll().size();

        // Create the MRfqSubmissionLine
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRfqSubmissionLineMockMvc.perform(put("/api/m-rfq-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqSubmissionLine in the database
        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRfqSubmissionLine() throws Exception {
        // Initialize the database
        mRfqSubmissionLineRepository.saveAndFlush(mRfqSubmissionLine);

        int databaseSizeBeforeDelete = mRfqSubmissionLineRepository.findAll().size();

        // Delete the mRfqSubmissionLine
        restMRfqSubmissionLineMockMvc.perform(delete("/api/m-rfq-submission-lines/{id}", mRfqSubmissionLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRfqSubmissionLine> mRfqSubmissionLineList = mRfqSubmissionLineRepository.findAll();
        assertThat(mRfqSubmissionLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
