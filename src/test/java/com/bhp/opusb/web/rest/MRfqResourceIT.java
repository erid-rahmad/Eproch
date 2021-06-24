package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.repository.MRfqRepository;
import com.bhp.opusb.service.MRfqService;
import com.bhp.opusb.service.dto.MRfqDTO;
import com.bhp.opusb.service.mapper.MRfqMapper;
import com.bhp.opusb.service.dto.MRfqCriteria;
import com.bhp.opusb.service.MRfqQueryService;

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
 * Integration tests for the {@link MRfqResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRfqResourceIT {

    private static final LocalDate DEFAULT_DATE_TRX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRX = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRX = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

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

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATE_PROMISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROMISED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PROMISED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MRfqRepository mRfqRepository;

    @Autowired
    private MRfqMapper mRfqMapper;

    @Autowired
    private MRfqService mRfqService;

    @Autowired
    private MRfqQueryService mRfqQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRfqMockMvc;

    private MRfq mRfq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfq createEntity(EntityManager em) {
        MRfq mRfq = new MRfq()
            .dateTrx(DEFAULT_DATE_TRX)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .grandTotal(DEFAULT_GRAND_TOTAL)
            .datePromised(DEFAULT_DATE_PROMISED)
            .description(DEFAULT_DESCRIPTION)
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
        mRfq.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfq.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mRfq.setBusinessCategory(cBusinessCategory);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRfq.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRfq.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mRfq.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mRfq.setDocumentType(cDocumentType);
        return mRfq;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfq createUpdatedEntity(EntityManager em) {
        MRfq mRfq = new MRfq()
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .datePromised(UPDATED_DATE_PROMISED)
            .description(UPDATED_DESCRIPTION)
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
        mRfq.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mRfq.setBusinessClassification(cBusinessCategory);
        // Add required entity
        mRfq.setBusinessCategory(cBusinessCategory);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRfq.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createUpdatedEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRfq.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mRfq.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mRfq.setDocumentType(cDocumentType);
        return mRfq;
    }

    @BeforeEach
    public void initTest() {
        mRfq = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRfq() throws Exception {
        int databaseSizeBeforeCreate = mRfqRepository.findAll().size();

        // Create the MRfq
        MRfqDTO mRfqDTO = mRfqMapper.toDto(mRfq);
        restMRfqMockMvc.perform(post("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isCreated());

        // Validate the MRfq in the database
        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeCreate + 1);
        MRfq testMRfq = mRfqList.get(mRfqList.size() - 1);
        assertThat(testMRfq.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMRfq.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMRfq.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMRfq.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMRfq.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMRfq.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMRfq.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMRfq.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
        assertThat(testMRfq.getDatePromised()).isEqualTo(DEFAULT_DATE_PROMISED);
        assertThat(testMRfq.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMRfq.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRfq.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMRfqWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRfqRepository.findAll().size();

        // Create the MRfq with an existing ID
        mRfq.setId(1L);
        MRfqDTO mRfqDTO = mRfqMapper.toDto(mRfq);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRfqMockMvc.perform(post("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfq in the database
        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqRepository.findAll().size();
        // set the field null
        mRfq.setDocumentAction(null);

        // Create the MRfq, which fails.
        MRfqDTO mRfqDTO = mRfqMapper.toDto(mRfq);

        restMRfqMockMvc.perform(post("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isBadRequest());

        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRfqRepository.findAll().size();
        // set the field null
        mRfq.setDocumentStatus(null);

        // Create the MRfq, which fails.
        MRfqDTO mRfqDTO = mRfqMapper.toDto(mRfq);

        restMRfqMockMvc.perform(post("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isBadRequest());

        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRfqs() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList
        restMRfqMockMvc.perform(get("/api/m-rfqs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfq.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMRfq() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get the mRfq
        restMRfqMockMvc.perform(get("/api/m-rfqs/{id}", mRfq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRfq.getId().intValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.datePromised").value(DEFAULT_DATE_PROMISED.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMRfqsByIdFiltering() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        Long id = mRfq.getId();

        defaultMRfqShouldBeFound("id.equals=" + id);
        defaultMRfqShouldNotBeFound("id.notEquals=" + id);

        defaultMRfqShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRfqShouldNotBeFound("id.greaterThan=" + id);

        defaultMRfqShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRfqShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mRfqList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx is not null
        defaultMRfqShouldBeFound("dateTrx.specified=true");

        // Get all the mRfqList where dateTrx is null
        defaultMRfqShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx is less than UPDATED_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMRfqShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mRfqList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMRfqShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired is not null
        defaultMRfqShouldBeFound("dateRequired.specified=true");

        // Get all the mRfqList where dateRequired is null
        defaultMRfqShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRfqShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRfqShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMRfqShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRfqShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMRfqShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMRfqShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMRfqShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mRfqList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRfqShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo is not null
        defaultMRfqShouldBeFound("documentNo.specified=true");

        // Get all the mRfqList where documentNo is null
        defaultMRfqShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMRfqShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMRfqShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMRfqShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMRfqShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMRfqShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mRfqList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction is not null
        defaultMRfqShouldBeFound("documentAction.specified=true");

        // Get all the mRfqList where documentAction is null
        defaultMRfqShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMRfqShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMRfqShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRfqList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMRfqShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMRfqShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mRfqList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus is not null
        defaultMRfqShouldBeFound("documentStatus.specified=true");

        // Get all the mRfqList where documentStatus is null
        defaultMRfqShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMRfqShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMRfqShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRfqList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMRfqShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMRfqsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where approved equals to DEFAULT_APPROVED
        defaultMRfqShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mRfqList where approved equals to UPDATED_APPROVED
        defaultMRfqShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where approved not equals to DEFAULT_APPROVED
        defaultMRfqShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mRfqList where approved not equals to UPDATED_APPROVED
        defaultMRfqShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMRfqShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mRfqList where approved equals to UPDATED_APPROVED
        defaultMRfqShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where approved is not null
        defaultMRfqShouldBeFound("approved.specified=true");

        // Get all the mRfqList where approved is null
        defaultMRfqShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where processed equals to DEFAULT_PROCESSED
        defaultMRfqShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mRfqList where processed equals to UPDATED_PROCESSED
        defaultMRfqShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where processed not equals to DEFAULT_PROCESSED
        defaultMRfqShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mRfqList where processed not equals to UPDATED_PROCESSED
        defaultMRfqShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMRfqShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mRfqList where processed equals to UPDATED_PROCESSED
        defaultMRfqShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where processed is not null
        defaultMRfqShouldBeFound("processed.specified=true");

        // Get all the mRfqList where processed is null
        defaultMRfqShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal equals to DEFAULT_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.equals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.equals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal not equals to DEFAULT_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.notEquals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal not equals to UPDATED_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.notEquals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal in DEFAULT_GRAND_TOTAL or UPDATED_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.in=" + DEFAULT_GRAND_TOTAL + "," + UPDATED_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.in=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal is not null
        defaultMRfqShouldBeFound("grandTotal.specified=true");

        // Get all the mRfqList where grandTotal is null
        defaultMRfqShouldNotBeFound("grandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal is greater than or equal to DEFAULT_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.greaterThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal is greater than or equal to UPDATED_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.greaterThanOrEqual=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal is less than or equal to DEFAULT_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.lessThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal is less than or equal to SMALLER_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.lessThanOrEqual=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal is less than DEFAULT_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.lessThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal is less than UPDATED_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.lessThan=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMRfqsByGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where grandTotal is greater than DEFAULT_GRAND_TOTAL
        defaultMRfqShouldNotBeFound("grandTotal.greaterThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mRfqList where grandTotal is greater than SMALLER_GRAND_TOTAL
        defaultMRfqShouldBeFound("grandTotal.greaterThan=" + SMALLER_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised equals to DEFAULT_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.equals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.equals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised not equals to DEFAULT_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.notEquals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised not equals to UPDATED_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.notEquals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised in DEFAULT_DATE_PROMISED or UPDATED_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.in=" + DEFAULT_DATE_PROMISED + "," + UPDATED_DATE_PROMISED);

        // Get all the mRfqList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.in=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised is not null
        defaultMRfqShouldBeFound("datePromised.specified=true");

        // Get all the mRfqList where datePromised is null
        defaultMRfqShouldNotBeFound("datePromised.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised is greater than or equal to DEFAULT_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.greaterThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised is greater than or equal to UPDATED_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.greaterThanOrEqual=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised is less than or equal to DEFAULT_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.lessThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised is less than or equal to SMALLER_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.lessThanOrEqual=" + SMALLER_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised is less than DEFAULT_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.lessThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised is less than UPDATED_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.lessThan=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDatePromisedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where datePromised is greater than DEFAULT_DATE_PROMISED
        defaultMRfqShouldNotBeFound("datePromised.greaterThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRfqList where datePromised is greater than SMALLER_DATE_PROMISED
        defaultMRfqShouldBeFound("datePromised.greaterThan=" + SMALLER_DATE_PROMISED);
    }


    @Test
    @Transactional
    public void getAllMRfqsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description equals to DEFAULT_DESCRIPTION
        defaultMRfqShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mRfqList where description equals to UPDATED_DESCRIPTION
        defaultMRfqShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description not equals to DEFAULT_DESCRIPTION
        defaultMRfqShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mRfqList where description not equals to UPDATED_DESCRIPTION
        defaultMRfqShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMRfqShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mRfqList where description equals to UPDATED_DESCRIPTION
        defaultMRfqShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description is not null
        defaultMRfqShouldBeFound("description.specified=true");

        // Get all the mRfqList where description is null
        defaultMRfqShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description contains DEFAULT_DESCRIPTION
        defaultMRfqShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mRfqList where description contains UPDATED_DESCRIPTION
        defaultMRfqShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRfqsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where description does not contain DEFAULT_DESCRIPTION
        defaultMRfqShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mRfqList where description does not contain UPDATED_DESCRIPTION
        defaultMRfqShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMRfqsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where uid equals to DEFAULT_UID
        defaultMRfqShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRfqList where uid equals to UPDATED_UID
        defaultMRfqShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where uid not equals to DEFAULT_UID
        defaultMRfqShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRfqList where uid not equals to UPDATED_UID
        defaultMRfqShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRfqShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRfqList where uid equals to UPDATED_UID
        defaultMRfqShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRfqsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where uid is not null
        defaultMRfqShouldBeFound("uid.specified=true");

        // Get all the mRfqList where uid is null
        defaultMRfqShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where active equals to DEFAULT_ACTIVE
        defaultMRfqShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRfqList where active equals to UPDATED_ACTIVE
        defaultMRfqShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where active not equals to DEFAULT_ACTIVE
        defaultMRfqShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRfqList where active not equals to UPDATED_ACTIVE
        defaultMRfqShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRfqShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRfqList where active equals to UPDATED_ACTIVE
        defaultMRfqShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRfqsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        // Get all the mRfqList where active is not null
        defaultMRfqShouldBeFound("active.specified=true");

        // Get all the mRfqList where active is null
        defaultMRfqShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRfq.getAdOrganization();
        mRfqRepository.saveAndFlush(mRfq);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRfqList where adOrganization equals to adOrganizationId
        defaultMRfqShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRfqList where adOrganization equals to adOrganizationId + 1
        defaultMRfqShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByBusinessClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessClassification = mRfq.getBusinessClassification();
        mRfqRepository.saveAndFlush(mRfq);
        Long businessClassificationId = businessClassification.getId();

        // Get all the mRfqList where businessClassification equals to businessClassificationId
        defaultMRfqShouldBeFound("businessClassificationId.equals=" + businessClassificationId);

        // Get all the mRfqList where businessClassification equals to businessClassificationId + 1
        defaultMRfqShouldNotBeFound("businessClassificationId.equals=" + (businessClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mRfq.getBusinessCategory();
        mRfqRepository.saveAndFlush(mRfq);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mRfqList where businessCategory equals to businessCategoryId
        defaultMRfqShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mRfqList where businessCategory equals to businessCategoryId + 1
        defaultMRfqShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mRfq.getCurrency();
        mRfqRepository.saveAndFlush(mRfq);
        Long currencyId = currency.getId();

        // Get all the mRfqList where currency equals to currencyId
        defaultMRfqShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mRfqList where currency equals to currencyId + 1
        defaultMRfqShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByWarehouseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CWarehouse warehouse = mRfq.getWarehouse();
        mRfqRepository.saveAndFlush(mRfq);
        Long warehouseId = warehouse.getId();

        // Get all the mRfqList where warehouse equals to warehouseId
        defaultMRfqShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mRfqList where warehouse equals to warehouseId + 1
        defaultMRfqShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mRfq.getCostCenter();
        mRfqRepository.saveAndFlush(mRfq);
        Long costCenterId = costCenter.getId();

        // Get all the mRfqList where costCenter equals to costCenterId
        defaultMRfqShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mRfqList where costCenter equals to costCenterId + 1
        defaultMRfqShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMRfqsByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = mRfq.getDocumentType();
        mRfqRepository.saveAndFlush(mRfq);
        Long documentTypeId = documentType.getId();

        // Get all the mRfqList where documentType equals to documentTypeId
        defaultMRfqShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mRfqList where documentType equals to documentTypeId + 1
        defaultMRfqShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRfqShouldBeFound(String filter) throws Exception {
        restMRfqMockMvc.perform(get("/api/m-rfqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfq.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMRfqMockMvc.perform(get("/api/m-rfqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRfqShouldNotBeFound(String filter) throws Exception {
        restMRfqMockMvc.perform(get("/api/m-rfqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRfqMockMvc.perform(get("/api/m-rfqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRfq() throws Exception {
        // Get the mRfq
        restMRfqMockMvc.perform(get("/api/m-rfqs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRfq() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        int databaseSizeBeforeUpdate = mRfqRepository.findAll().size();

        // Update the mRfq
        MRfq updatedMRfq = mRfqRepository.findById(mRfq.getId()).get();
        // Disconnect from session so that the updates on updatedMRfq are not directly saved in db
        em.detach(updatedMRfq);
        updatedMRfq
            .dateTrx(UPDATED_DATE_TRX)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .datePromised(UPDATED_DATE_PROMISED)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MRfqDTO mRfqDTO = mRfqMapper.toDto(updatedMRfq);

        restMRfqMockMvc.perform(put("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isOk());

        // Validate the MRfq in the database
        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeUpdate);
        MRfq testMRfq = mRfqList.get(mRfqList.size() - 1);
        assertThat(testMRfq.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMRfq.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMRfq.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMRfq.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMRfq.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMRfq.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMRfq.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMRfq.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
        assertThat(testMRfq.getDatePromised()).isEqualTo(UPDATED_DATE_PROMISED);
        assertThat(testMRfq.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMRfq.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRfq.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMRfq() throws Exception {
        int databaseSizeBeforeUpdate = mRfqRepository.findAll().size();

        // Create the MRfq
        MRfqDTO mRfqDTO = mRfqMapper.toDto(mRfq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRfqMockMvc.perform(put("/api/m-rfqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRfqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRfq in the database
        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRfq() throws Exception {
        // Initialize the database
        mRfqRepository.saveAndFlush(mRfq);

        int databaseSizeBeforeDelete = mRfqRepository.findAll().size();

        // Delete the mRfq
        restMRfqMockMvc.perform(delete("/api/m-rfqs/{id}", mRfq.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRfq> mRfqList = mRfqRepository.findAll();
        assertThat(mRfqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
