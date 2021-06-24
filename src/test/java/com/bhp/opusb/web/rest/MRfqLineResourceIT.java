package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MRfqLineRepository;
import com.bhp.opusb.service.MRfqLineService;
import com.bhp.opusb.service.dto.MRfqLineDTO;
import com.bhp.opusb.service.mapper.MRfqLineMapper;
import com.bhp.opusb.service.dto.MRfqLineCriteria;
import com.bhp.opusb.service.MRfqLineQueryService;

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
 * Integration tests for the {@link MRfqLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRfqLineResourceIT {

    private static final Integer DEFAULT_LINE_NO = 1;
    private static final Integer UPDATED_LINE_NO = 2;
    private static final Integer SMALLER_LINE_NO = 1 - 1;

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

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ORDER_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ORDER_AMOUNT = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private MRfqLineRepository mRfqLineRepository;

    @Autowired
    private MRfqLineMapper mRfqLineMapper;

    @Autowired
    private MRfqLineService mRfqLineService;

    @Autowired
    private MRfqLineQueryService mRfqLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRfqLineMockMvc;

    private MRfqLine mRfqLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqLine createEntity(EntityManager em) {
        MRfqLine mRfqLine = new MRfqLine()
            .lineNo(DEFAULT_LINE_NO)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .releaseQty(DEFAULT_RELEASE_QTY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .orderAmount(DEFAULT_ORDER_AMOUNT)
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .remark(DEFAULT_REMARK);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqLine.setQuotation(mRfq);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRfqLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRfqLine.setUom(cUnitOfMeasure);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqLine.setBusinessCategory(cBusinessCategory);
        return mRfqLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqLine createUpdatedEntity(EntityManager em) {
        MRfqLine mRfqLine = new MRfqLine()
            .lineNo(UPDATED_LINE_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .releaseQty(UPDATED_RELEASE_QTY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .orderAmount(UPDATED_ORDER_AMOUNT)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .remark(UPDATED_REMARK);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createUpdatedEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqLine.setQuotation(mRfq);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRfqLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRfqLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRfqLine.setUom(cUnitOfMeasure);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfqLine.setBusinessCategory(cBusinessCategory);
        return mRfqLine;
    }

    @BeforeEach
    public void initTest() {
        mRfqLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRfqLine() throws Exception {
        int databaseSizeBeforeCreate = mRfqLineRepository.findAll().size();

        // Create the MRfqLine
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(mRfqLine);
        restMRfqLineMockMvc.perform(post("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MRfqLine in the database
        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeCreate + 1);
        MRfqLine testMRfqLine = mRfqLineList.get(mRfqLineList.size() - 1);
        assertThat(testMRfqLine.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testMRfqLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRfqLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMRfqLine.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMRfqLine.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMRfqLine.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMRfqLine.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMRfqLine.getReleaseQty()).isEqualTo(DEFAULT_RELEASE_QTY);
        assertThat(testMRfqLine.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMRfqLine.getOrderAmount()).isEqualTo(DEFAULT_ORDER_AMOUNT);
        assertThat(testMRfqLine.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testMRfqLine.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMRfqLine.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createMRfqLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRfqLineRepository.findAll().size();

        // Create the MRfqLine with an existing ID
        mRfqLine.setId(1L);
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(mRfqLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRfqLineMockMvc.perform(post("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqLine in the database
        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqLineRepository.findAll().size();
        // set the field null
        mRfqLine.setDocumentAction(null);

        // Create the MRfqLine, which fails.
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(mRfqLine);

        restMRfqLineMockMvc.perform(post("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqLineRepository.findAll().size();
        // set the field null
        mRfqLine.setDocumentStatus(null);

        // Create the MRfqLine, which fails.
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(mRfqLine);

        restMRfqLineMockMvc.perform(post("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRfqLines() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseQty").value(hasItem(DEFAULT_RELEASE_QTY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].orderAmount").value(hasItem(DEFAULT_ORDER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
    
    @Test
    @Transactional
    public void getMRfqLine() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get the mRfqLine
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines/{id}", mRfqLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRfqLine.getId().intValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.releaseQty").value(DEFAULT_RELEASE_QTY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.orderAmount").value(DEFAULT_ORDER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }


    @Test
    @Transactional
    public void getMRfqLinesByIdFiltering() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        Long id = mRfqLine.getId();

        defaultMRfqLineShouldBeFound("id.equals=" + id);
        defaultMRfqLineShouldNotBeFound("id.notEquals=" + id);

        defaultMRfqLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRfqLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMRfqLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRfqLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo equals to DEFAULT_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.equals=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo equals to UPDATED_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.equals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo not equals to DEFAULT_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.notEquals=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo not equals to UPDATED_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.notEquals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo in DEFAULT_LINE_NO or UPDATED_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.in=" + DEFAULT_LINE_NO + "," + UPDATED_LINE_NO);

        // Get all the mRfqLineList where lineNo equals to UPDATED_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.in=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo is not null
        defaultMRfqLineShouldBeFound("lineNo.specified=true");

        // Get all the mRfqLineList where lineNo is null
        defaultMRfqLineShouldNotBeFound("lineNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo is greater than or equal to DEFAULT_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.greaterThanOrEqual=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo is greater than or equal to UPDATED_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.greaterThanOrEqual=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo is less than or equal to DEFAULT_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.lessThanOrEqual=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo is less than or equal to SMALLER_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.lessThanOrEqual=" + SMALLER_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo is less than DEFAULT_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.lessThan=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo is less than UPDATED_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.lessThan=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByLineNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where lineNo is greater than DEFAULT_LINE_NO
        defaultMRfqLineShouldNotBeFound("lineNo.greaterThan=" + DEFAULT_LINE_NO);

        // Get all the mRfqLineList where lineNo is greater than SMALLER_LINE_NO
        defaultMRfqLineShouldBeFound("lineNo.greaterThan=" + SMALLER_LINE_NO);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where uid equals to DEFAULT_UID
        defaultMRfqLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRfqLineList where uid equals to UPDATED_UID
        defaultMRfqLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where uid not equals to DEFAULT_UID
        defaultMRfqLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRfqLineList where uid not equals to UPDATED_UID
        defaultMRfqLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRfqLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRfqLineList where uid equals to UPDATED_UID
        defaultMRfqLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where uid is not null
        defaultMRfqLineShouldBeFound("uid.specified=true");

        // Get all the mRfqLineList where uid is null
        defaultMRfqLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where active equals to DEFAULT_ACTIVE
        defaultMRfqLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRfqLineList where active equals to UPDATED_ACTIVE
        defaultMRfqLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where active not equals to DEFAULT_ACTIVE
        defaultMRfqLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRfqLineList where active not equals to UPDATED_ACTIVE
        defaultMRfqLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRfqLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRfqLineList where active equals to UPDATED_ACTIVE
        defaultMRfqLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where active is not null
        defaultMRfqLineShouldBeFound("active.specified=true");

        // Get all the mRfqLineList where active is null
        defaultMRfqLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqLineShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqLineList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqLineShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqLineList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mRfqLineList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction is not null
        defaultMRfqLineShouldBeFound("documentAction.specified=true");

        // Get all the mRfqLineList where documentAction is null
        defaultMRfqLineShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMRfqLineShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqLineList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMRfqLineShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqLineList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMRfqLineShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqLineShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqLineList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqLineShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqLineList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mRfqLineList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus is not null
        defaultMRfqLineShouldBeFound("documentStatus.specified=true");

        // Get all the mRfqLineList where documentStatus is null
        defaultMRfqLineShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMRfqLineShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqLineList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMRfqLineShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqLineList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMRfqLineShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where approved equals to DEFAULT_APPROVED
        defaultMRfqLineShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mRfqLineList where approved equals to UPDATED_APPROVED
        defaultMRfqLineShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where approved not equals to DEFAULT_APPROVED
        defaultMRfqLineShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mRfqLineList where approved not equals to UPDATED_APPROVED
        defaultMRfqLineShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMRfqLineShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mRfqLineList where approved equals to UPDATED_APPROVED
        defaultMRfqLineShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where approved is not null
        defaultMRfqLineShouldBeFound("approved.specified=true");

        // Get all the mRfqLineList where approved is null
        defaultMRfqLineShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where processed equals to DEFAULT_PROCESSED
        defaultMRfqLineShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mRfqLineList where processed equals to UPDATED_PROCESSED
        defaultMRfqLineShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where processed not equals to DEFAULT_PROCESSED
        defaultMRfqLineShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mRfqLineList where processed not equals to UPDATED_PROCESSED
        defaultMRfqLineShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMRfqLineShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mRfqLineList where processed equals to UPDATED_PROCESSED
        defaultMRfqLineShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where processed is not null
        defaultMRfqLineShouldBeFound("processed.specified=true");

        // Get all the mRfqLineList where processed is null
        defaultMRfqLineShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty equals to DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.equals=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty equals to UPDATED_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.equals=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty not equals to DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.notEquals=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty not equals to UPDATED_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.notEquals=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty in DEFAULT_RELEASE_QTY or UPDATED_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.in=" + DEFAULT_RELEASE_QTY + "," + UPDATED_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty equals to UPDATED_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.in=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty is not null
        defaultMRfqLineShouldBeFound("releaseQty.specified=true");

        // Get all the mRfqLineList where releaseQty is null
        defaultMRfqLineShouldNotBeFound("releaseQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty is greater than or equal to DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.greaterThanOrEqual=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty is greater than or equal to UPDATED_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.greaterThanOrEqual=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty is less than or equal to DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.lessThanOrEqual=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty is less than or equal to SMALLER_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.lessThanOrEqual=" + SMALLER_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty is less than DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.lessThan=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty is less than UPDATED_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.lessThan=" + UPDATED_RELEASE_QTY);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByReleaseQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where releaseQty is greater than DEFAULT_RELEASE_QTY
        defaultMRfqLineShouldNotBeFound("releaseQty.greaterThan=" + DEFAULT_RELEASE_QTY);

        // Get all the mRfqLineList where releaseQty is greater than SMALLER_RELEASE_QTY
        defaultMRfqLineShouldBeFound("releaseQty.greaterThan=" + SMALLER_RELEASE_QTY);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice is not null
        defaultMRfqLineShouldBeFound("unitPrice.specified=true");

        // Get all the mRfqLineList where unitPrice is null
        defaultMRfqLineShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultMRfqLineShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mRfqLineList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultMRfqLineShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount equals to DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.equals=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount equals to UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.equals=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount not equals to DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.notEquals=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount not equals to UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.notEquals=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount in DEFAULT_ORDER_AMOUNT or UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.in=" + DEFAULT_ORDER_AMOUNT + "," + UPDATED_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount equals to UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.in=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount is not null
        defaultMRfqLineShouldBeFound("orderAmount.specified=true");

        // Get all the mRfqLineList where orderAmount is null
        defaultMRfqLineShouldNotBeFound("orderAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount is greater than or equal to DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.greaterThanOrEqual=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount is greater than or equal to UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.greaterThanOrEqual=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount is less than or equal to DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.lessThanOrEqual=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount is less than or equal to SMALLER_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.lessThanOrEqual=" + SMALLER_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount is less than DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.lessThan=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount is less than UPDATED_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.lessThan=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByOrderAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where orderAmount is greater than DEFAULT_ORDER_AMOUNT
        defaultMRfqLineShouldNotBeFound("orderAmount.greaterThan=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mRfqLineList where orderAmount is greater than SMALLER_ORDER_AMOUNT
        defaultMRfqLineShouldBeFound("orderAmount.greaterThan=" + SMALLER_ORDER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate is not null
        defaultMRfqLineShouldBeFound("documentDate.specified=true");

        // Get all the mRfqLineList where documentDate is null
        defaultMRfqLineShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultMRfqLineShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRfqLineList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultMRfqLineShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired is not null
        defaultMRfqLineShouldBeFound("dateRequired.specified=true");

        // Get all the mRfqLineList where dateRequired is null
        defaultMRfqLineShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRfqLineShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqLineList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRfqLineShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark equals to DEFAULT_REMARK
        defaultMRfqLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mRfqLineList where remark equals to UPDATED_REMARK
        defaultMRfqLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark not equals to DEFAULT_REMARK
        defaultMRfqLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mRfqLineList where remark not equals to UPDATED_REMARK
        defaultMRfqLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMRfqLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mRfqLineList where remark equals to UPDATED_REMARK
        defaultMRfqLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark is not null
        defaultMRfqLineShouldBeFound("remark.specified=true");

        // Get all the mRfqLineList where remark is null
        defaultMRfqLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark contains DEFAULT_REMARK
        defaultMRfqLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mRfqLineList where remark contains UPDATED_REMARK
        defaultMRfqLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRfqLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        // Get all the mRfqLineList where remark does not contain DEFAULT_REMARK
        defaultMRfqLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mRfqLineList where remark does not contain UPDATED_REMARK
        defaultMRfqLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByQuotationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfq quotation = mRfqLine.getQuotation();
        mRfqLineRepository.saveAndFlush(mRfqLine);
        Long quotationId = quotation.getId();

        // Get all the mRfqLineList where quotation equals to quotationId
        defaultMRfqLineShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the mRfqLineList where quotation equals to quotationId + 1
        defaultMRfqLineShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRfqLine.getAdOrganization();
        mRfqLineRepository.saveAndFlush(mRfqLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRfqLineList where adOrganization equals to adOrganizationId
        defaultMRfqLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRfqLineList where adOrganization equals to adOrganizationId + 1
        defaultMRfqLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mRfqLine.getProduct();
        mRfqLineRepository.saveAndFlush(mRfqLine);
        Long productId = product.getId();

        // Get all the mRfqLineList where product equals to productId
        defaultMRfqLineShouldBeFound("productId.equals=" + productId);

        // Get all the mRfqLineList where product equals to productId + 1
        defaultMRfqLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mRfqLine.getUom();
        mRfqLineRepository.saveAndFlush(mRfqLine);
        Long uomId = uom.getId();

        // Get all the mRfqLineList where uom equals to uomId
        defaultMRfqLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mRfqLineList where uom equals to uomId + 1
        defaultMRfqLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqLinesByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mRfqLine.getBusinessCategory();
        mRfqLineRepository.saveAndFlush(mRfqLine);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mRfqLineList where businessCategory equals to businessCategoryId
        defaultMRfqLineShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mRfqLineList where businessCategory equals to businessCategoryId + 1
        defaultMRfqLineShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRfqLineShouldBeFound(String filter) throws Exception {
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseQty").value(hasItem(DEFAULT_RELEASE_QTY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].orderAmount").value(hasItem(DEFAULT_ORDER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRfqLineShouldNotBeFound(String filter) throws Exception {
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRfqLine() throws Exception {
        // Get the mRfqLine
        restMRfqLineMockMvc.perform(get("/api/m-rfq-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRfqLine() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        int databaseSizeBeforeUpdate = mRfqLineRepository.findAll().size();

        // Update the mRfqLine
        MRfqLine updatedMRfqLine = mRfqLineRepository.findById(mRfqLine.getId()).get();
        // Disconnect from session so that the updates on updatedMRfqLine are not directly saved in db
        em.detach(updatedMRfqLine);
        updatedMRfqLine
            .lineNo(UPDATED_LINE_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .releaseQty(UPDATED_RELEASE_QTY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .orderAmount(UPDATED_ORDER_AMOUNT)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .remark(UPDATED_REMARK);
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(updatedMRfqLine);

        restMRfqLineMockMvc.perform(put("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isOk());

        // Validate the MRfqLine in the database
        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeUpdate);
        MRfqLine testMRfqLine = mRfqLineList.get(mRfqLineList.size() - 1);
        assertThat(testMRfqLine.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testMRfqLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRfqLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMRfqLine.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMRfqLine.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMRfqLine.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMRfqLine.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMRfqLine.getReleaseQty()).isEqualTo(UPDATED_RELEASE_QTY);
        assertThat(testMRfqLine.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMRfqLine.getOrderAmount()).isEqualTo(UPDATED_ORDER_AMOUNT);
        assertThat(testMRfqLine.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testMRfqLine.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMRfqLine.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingMRfqLine() throws Exception {
        int databaseSizeBeforeUpdate = mRfqLineRepository.findAll().size();

        // Create the MRfqLine
        MRfqLineDTO mRfqLineDTO = mRfqLineMapper.toDto(mRfqLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRfqLineMockMvc.perform(put("/api/m-rfq-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfqLine in the database
        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRfqLine() throws Exception {
        // Initialize the database
        mRfqLineRepository.saveAndFlush(mRfqLine);

        int databaseSizeBeforeDelete = mRfqLineRepository.findAll().size();

        // Delete the mRfqLine
        restMRfqLineMockMvc.perform(delete("/api/m-rfq-lines/{id}", mRfqLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRfqLine> mRfqLineList = mRfqLineRepository.findAll();
        assertThat(mRfqLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
