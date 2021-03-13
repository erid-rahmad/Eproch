package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MRequisitionLineRepository;
import com.bhp.opusb.service.MRequisitionLineService;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.mapper.MRequisitionLineMapper;
import com.bhp.opusb.service.dto.MRequisitionLineCriteria;
import com.bhp.opusb.service.MRequisitionLineQueryService;

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
 * Integration tests for the {@link MRequisitionLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRequisitionLineResourceIT {

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_PROMISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROMISED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PROMISED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_REQUISITION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_REQUISITION_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_REQUISITION_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QUANTITY_ORDERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY_ORDERED = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY_ORDERED = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QUANTITY_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY_BALANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY_BALANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MRequisitionLineRepository mRequisitionLineRepository;

    @Autowired
    private MRequisitionLineMapper mRequisitionLineMapper;

    @Autowired
    private MRequisitionLineService mRequisitionLineService;

    @Autowired
    private MRequisitionLineQueryService mRequisitionLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRequisitionLineMockMvc;

    private MRequisitionLine mRequisitionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRequisitionLine createEntity(EntityManager em) {
        MRequisitionLine mRequisitionLine = new MRequisitionLine()
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .datePromised(DEFAULT_DATE_PROMISED)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .requisitionAmount(DEFAULT_REQUISITION_AMOUNT)
            .quantity(DEFAULT_QUANTITY)
            .quantityOrdered(DEFAULT_QUANTITY_ORDERED)
            .quantityBalance(DEFAULT_QUANTITY_BALANCE)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .remark(DEFAULT_REMARK)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MRequisition mRequisition;
        if (TestUtil.findAll(em, MRequisition.class).isEmpty()) {
            mRequisition = MRequisitionResourceIT.createEntity(em);
            em.persist(mRequisition);
            em.flush();
        } else {
            mRequisition = TestUtil.findAll(em, MRequisition.class).get(0);
        }
        mRequisitionLine.setRequisition(mRequisition);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRequisitionLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRequisitionLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRequisitionLine.setUom(cUnitOfMeasure);
        return mRequisitionLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRequisitionLine createUpdatedEntity(EntityManager em) {
        MRequisitionLine mRequisitionLine = new MRequisitionLine()
            .documentDate(UPDATED_DOCUMENT_DATE)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .requisitionAmount(UPDATED_REQUISITION_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .quantityOrdered(UPDATED_QUANTITY_ORDERED)
            .quantityBalance(UPDATED_QUANTITY_BALANCE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MRequisition mRequisition;
        if (TestUtil.findAll(em, MRequisition.class).isEmpty()) {
            mRequisition = MRequisitionResourceIT.createUpdatedEntity(em);
            em.persist(mRequisition);
            em.flush();
        } else {
            mRequisition = TestUtil.findAll(em, MRequisition.class).get(0);
        }
        mRequisitionLine.setRequisition(mRequisition);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mRequisitionLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mRequisitionLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mRequisitionLine.setUom(cUnitOfMeasure);
        return mRequisitionLine;
    }

    @BeforeEach
    public void initTest() {
        mRequisitionLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRequisitionLine() throws Exception {
        int databaseSizeBeforeCreate = mRequisitionLineRepository.findAll().size();

        // Create the MRequisitionLine
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);
        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MRequisitionLine in the database
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeCreate + 1);
        MRequisitionLine testMRequisitionLine = mRequisitionLineList.get(mRequisitionLineList.size() - 1);
        assertThat(testMRequisitionLine.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testMRequisitionLine.getDatePromised()).isEqualTo(DEFAULT_DATE_PROMISED);
        assertThat(testMRequisitionLine.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMRequisitionLine.getRequisitionAmount()).isEqualTo(DEFAULT_REQUISITION_AMOUNT);
        assertThat(testMRequisitionLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMRequisitionLine.getQuantityOrdered()).isEqualTo(DEFAULT_QUANTITY_ORDERED);
        assertThat(testMRequisitionLine.getQuantityBalance()).isEqualTo(DEFAULT_QUANTITY_BALANCE);
        assertThat(testMRequisitionLine.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMRequisitionLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMRequisitionLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRequisitionLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMRequisitionLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRequisitionLineRepository.findAll().size();

        // Create the MRequisitionLine with an existing ID
        mRequisitionLine.setId(1L);
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRequisitionLine in the database
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRequisitionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionLineRepository.findAll().size();
        // set the field null
        mRequisitionLine.setRequisitionAmount(null);

        // Create the MRequisitionLine, which fails.
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionLineRepository.findAll().size();
        // set the field null
        mRequisitionLine.setQuantity(null);

        // Create the MRequisitionLine, which fails.
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityOrderedIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionLineRepository.findAll().size();
        // set the field null
        mRequisitionLine.setQuantityOrdered(null);

        // Create the MRequisitionLine, which fails.
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionLineRepository.findAll().size();
        // set the field null
        mRequisitionLine.setQuantityBalance(null);

        // Create the MRequisitionLine, which fails.
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionLineRepository.findAll().size();
        // set the field null
        mRequisitionLine.setUnitPrice(null);

        // Create the MRequisitionLine, which fails.
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        restMRequisitionLineMockMvc.perform(post("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLines() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRequisitionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].requisitionAmount").value(hasItem(DEFAULT_REQUISITION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].quantityOrdered").value(hasItem(DEFAULT_QUANTITY_ORDERED.intValue())))
            .andExpect(jsonPath("$.[*].quantityBalance").value(hasItem(DEFAULT_QUANTITY_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMRequisitionLine() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get the mRequisitionLine
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines/{id}", mRequisitionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRequisitionLine.getId().intValue()))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.datePromised").value(DEFAULT_DATE_PROMISED.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.requisitionAmount").value(DEFAULT_REQUISITION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.quantityOrdered").value(DEFAULT_QUANTITY_ORDERED.intValue()))
            .andExpect(jsonPath("$.quantityBalance").value(DEFAULT_QUANTITY_BALANCE.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMRequisitionLinesByIdFiltering() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        Long id = mRequisitionLine.getId();

        defaultMRequisitionLineShouldBeFound("id.equals=" + id);
        defaultMRequisitionLineShouldNotBeFound("id.notEquals=" + id);

        defaultMRequisitionLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRequisitionLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMRequisitionLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRequisitionLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate is not null
        defaultMRequisitionLineShouldBeFound("documentDate.specified=true");

        // Get all the mRequisitionLineList where documentDate is null
        defaultMRequisitionLineShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultMRequisitionLineShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mRequisitionLineList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultMRequisitionLineShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised equals to DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.equals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.equals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised not equals to DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.notEquals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised not equals to UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.notEquals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised in DEFAULT_DATE_PROMISED or UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.in=" + DEFAULT_DATE_PROMISED + "," + UPDATED_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.in=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised is not null
        defaultMRequisitionLineShouldBeFound("datePromised.specified=true");

        // Get all the mRequisitionLineList where datePromised is null
        defaultMRequisitionLineShouldNotBeFound("datePromised.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised is greater than or equal to DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.greaterThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised is greater than or equal to UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.greaterThanOrEqual=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised is less than or equal to DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.lessThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised is less than or equal to SMALLER_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.lessThanOrEqual=" + SMALLER_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised is less than DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.lessThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised is less than UPDATED_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.lessThan=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDatePromisedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where datePromised is greater than DEFAULT_DATE_PROMISED
        defaultMRequisitionLineShouldNotBeFound("datePromised.greaterThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionLineList where datePromised is greater than SMALLER_DATE_PROMISED
        defaultMRequisitionLineShouldBeFound("datePromised.greaterThan=" + SMALLER_DATE_PROMISED);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired is not null
        defaultMRequisitionLineShouldBeFound("dateRequired.specified=true");

        // Get all the mRequisitionLineList where dateRequired is null
        defaultMRequisitionLineShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRequisitionLineShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRequisitionLineList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRequisitionLineShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount equals to DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.equals=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount equals to UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.equals=" + UPDATED_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount not equals to DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.notEquals=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount not equals to UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.notEquals=" + UPDATED_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount in DEFAULT_REQUISITION_AMOUNT or UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.in=" + DEFAULT_REQUISITION_AMOUNT + "," + UPDATED_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount equals to UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.in=" + UPDATED_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount is not null
        defaultMRequisitionLineShouldBeFound("requisitionAmount.specified=true");

        // Get all the mRequisitionLineList where requisitionAmount is null
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount is greater than or equal to DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.greaterThanOrEqual=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount is greater than or equal to UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.greaterThanOrEqual=" + UPDATED_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount is less than or equal to DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.lessThanOrEqual=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount is less than or equal to SMALLER_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.lessThanOrEqual=" + SMALLER_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount is less than DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.lessThan=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount is less than UPDATED_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.lessThan=" + UPDATED_REQUISITION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where requisitionAmount is greater than DEFAULT_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldNotBeFound("requisitionAmount.greaterThan=" + DEFAULT_REQUISITION_AMOUNT);

        // Get all the mRequisitionLineList where requisitionAmount is greater than SMALLER_REQUISITION_AMOUNT
        defaultMRequisitionLineShouldBeFound("requisitionAmount.greaterThan=" + SMALLER_REQUISITION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity equals to DEFAULT_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity equals to UPDATED_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity not equals to DEFAULT_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity not equals to UPDATED_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mRequisitionLineList where quantity equals to UPDATED_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity is not null
        defaultMRequisitionLineShouldBeFound("quantity.specified=true");

        // Get all the mRequisitionLineList where quantity is null
        defaultMRequisitionLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity is less than DEFAULT_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity is less than UPDATED_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantity is greater than DEFAULT_QUANTITY
        defaultMRequisitionLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mRequisitionLineList where quantity is greater than SMALLER_QUANTITY
        defaultMRequisitionLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered equals to DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.equals=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered equals to UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.equals=" + UPDATED_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered not equals to DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.notEquals=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered not equals to UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.notEquals=" + UPDATED_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered in DEFAULT_QUANTITY_ORDERED or UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.in=" + DEFAULT_QUANTITY_ORDERED + "," + UPDATED_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered equals to UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.in=" + UPDATED_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered is not null
        defaultMRequisitionLineShouldBeFound("quantityOrdered.specified=true");

        // Get all the mRequisitionLineList where quantityOrdered is null
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered is greater than or equal to DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.greaterThanOrEqual=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered is greater than or equal to UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.greaterThanOrEqual=" + UPDATED_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered is less than or equal to DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.lessThanOrEqual=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered is less than or equal to SMALLER_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.lessThanOrEqual=" + SMALLER_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered is less than DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.lessThan=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered is less than UPDATED_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.lessThan=" + UPDATED_QUANTITY_ORDERED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityOrderedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityOrdered is greater than DEFAULT_QUANTITY_ORDERED
        defaultMRequisitionLineShouldNotBeFound("quantityOrdered.greaterThan=" + DEFAULT_QUANTITY_ORDERED);

        // Get all the mRequisitionLineList where quantityOrdered is greater than SMALLER_QUANTITY_ORDERED
        defaultMRequisitionLineShouldBeFound("quantityOrdered.greaterThan=" + SMALLER_QUANTITY_ORDERED);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance equals to DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.equals=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance equals to UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.equals=" + UPDATED_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance not equals to DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.notEquals=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance not equals to UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.notEquals=" + UPDATED_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance in DEFAULT_QUANTITY_BALANCE or UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.in=" + DEFAULT_QUANTITY_BALANCE + "," + UPDATED_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance equals to UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.in=" + UPDATED_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance is not null
        defaultMRequisitionLineShouldBeFound("quantityBalance.specified=true");

        // Get all the mRequisitionLineList where quantityBalance is null
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance is greater than or equal to DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.greaterThanOrEqual=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance is greater than or equal to UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.greaterThanOrEqual=" + UPDATED_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance is less than or equal to DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.lessThanOrEqual=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance is less than or equal to SMALLER_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.lessThanOrEqual=" + SMALLER_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance is less than DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.lessThan=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance is less than UPDATED_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.lessThan=" + UPDATED_QUANTITY_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByQuantityBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where quantityBalance is greater than DEFAULT_QUANTITY_BALANCE
        defaultMRequisitionLineShouldNotBeFound("quantityBalance.greaterThan=" + DEFAULT_QUANTITY_BALANCE);

        // Get all the mRequisitionLineList where quantityBalance is greater than SMALLER_QUANTITY_BALANCE
        defaultMRequisitionLineShouldBeFound("quantityBalance.greaterThan=" + SMALLER_QUANTITY_BALANCE);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice is not null
        defaultMRequisitionLineShouldBeFound("unitPrice.specified=true");

        // Get all the mRequisitionLineList where unitPrice is null
        defaultMRequisitionLineShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultMRequisitionLineShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mRequisitionLineList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultMRequisitionLineShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark equals to DEFAULT_REMARK
        defaultMRequisitionLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mRequisitionLineList where remark equals to UPDATED_REMARK
        defaultMRequisitionLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark not equals to DEFAULT_REMARK
        defaultMRequisitionLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mRequisitionLineList where remark not equals to UPDATED_REMARK
        defaultMRequisitionLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMRequisitionLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mRequisitionLineList where remark equals to UPDATED_REMARK
        defaultMRequisitionLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark is not null
        defaultMRequisitionLineShouldBeFound("remark.specified=true");

        // Get all the mRequisitionLineList where remark is null
        defaultMRequisitionLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark contains DEFAULT_REMARK
        defaultMRequisitionLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mRequisitionLineList where remark contains UPDATED_REMARK
        defaultMRequisitionLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where remark does not contain DEFAULT_REMARK
        defaultMRequisitionLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mRequisitionLineList where remark does not contain UPDATED_REMARK
        defaultMRequisitionLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where uid equals to DEFAULT_UID
        defaultMRequisitionLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRequisitionLineList where uid equals to UPDATED_UID
        defaultMRequisitionLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where uid not equals to DEFAULT_UID
        defaultMRequisitionLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRequisitionLineList where uid not equals to UPDATED_UID
        defaultMRequisitionLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRequisitionLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRequisitionLineList where uid equals to UPDATED_UID
        defaultMRequisitionLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where uid is not null
        defaultMRequisitionLineShouldBeFound("uid.specified=true");

        // Get all the mRequisitionLineList where uid is null
        defaultMRequisitionLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where active equals to DEFAULT_ACTIVE
        defaultMRequisitionLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRequisitionLineList where active equals to UPDATED_ACTIVE
        defaultMRequisitionLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where active not equals to DEFAULT_ACTIVE
        defaultMRequisitionLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRequisitionLineList where active not equals to UPDATED_ACTIVE
        defaultMRequisitionLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRequisitionLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRequisitionLineList where active equals to UPDATED_ACTIVE
        defaultMRequisitionLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        // Get all the mRequisitionLineList where active is not null
        defaultMRequisitionLineShouldBeFound("active.specified=true");

        // Get all the mRequisitionLineList where active is null
        defaultMRequisitionLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionLinesByRequisitionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRequisition requisition = mRequisitionLine.getRequisition();
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long requisitionId = requisition.getId();

        // Get all the mRequisitionLineList where requisition equals to requisitionId
        defaultMRequisitionLineShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the mRequisitionLineList where requisition equals to requisitionId + 1
        defaultMRequisitionLineShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRequisitionLine.getAdOrganization();
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRequisitionLineList where adOrganization equals to adOrganizationId
        defaultMRequisitionLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRequisitionLineList where adOrganization equals to adOrganizationId + 1
        defaultMRequisitionLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mRequisitionLine.getProduct();
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long productId = product.getId();

        // Get all the mRequisitionLineList where product equals to productId
        defaultMRequisitionLineShouldBeFound("productId.equals=" + productId);

        // Get all the mRequisitionLineList where product equals to productId + 1
        defaultMRequisitionLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        CWarehouse warehouse = CWarehouseResourceIT.createEntity(em);
        em.persist(warehouse);
        em.flush();
        mRequisitionLine.setWarehouse(warehouse);
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long warehouseId = warehouse.getId();

        // Get all the mRequisitionLineList where warehouse equals to warehouseId
        defaultMRequisitionLineShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mRequisitionLineList where warehouse equals to warehouseId + 1
        defaultMRequisitionLineShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        CCostCenter costCenter = CCostCenterResourceIT.createEntity(em);
        em.persist(costCenter);
        em.flush();
        mRequisitionLine.setCostCenter(costCenter);
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long costCenterId = costCenter.getId();

        // Get all the mRequisitionLineList where costCenter equals to costCenterId
        defaultMRequisitionLineShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mRequisitionLineList where costCenter equals to costCenterId + 1
        defaultMRequisitionLineShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mRequisitionLine.getUom();
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long uomId = uom.getId();

        // Get all the mRequisitionLineList where uom equals to uomId
        defaultMRequisitionLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mRequisitionLineList where uom equals to uomId + 1
        defaultMRequisitionLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionLinesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        mRequisitionLine.setVendor(vendor);
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);
        Long vendorId = vendor.getId();

        // Get all the mRequisitionLineList where vendor equals to vendorId
        defaultMRequisitionLineShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mRequisitionLineList where vendor equals to vendorId + 1
        defaultMRequisitionLineShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRequisitionLineShouldBeFound(String filter) throws Exception {
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRequisitionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].requisitionAmount").value(hasItem(DEFAULT_REQUISITION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].quantityOrdered").value(hasItem(DEFAULT_QUANTITY_ORDERED.intValue())))
            .andExpect(jsonPath("$.[*].quantityBalance").value(hasItem(DEFAULT_QUANTITY_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRequisitionLineShouldNotBeFound(String filter) throws Exception {
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRequisitionLine() throws Exception {
        // Get the mRequisitionLine
        restMRequisitionLineMockMvc.perform(get("/api/m-requisition-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRequisitionLine() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        int databaseSizeBeforeUpdate = mRequisitionLineRepository.findAll().size();

        // Update the mRequisitionLine
        MRequisitionLine updatedMRequisitionLine = mRequisitionLineRepository.findById(mRequisitionLine.getId()).get();
        // Disconnect from session so that the updates on updatedMRequisitionLine are not directly saved in db
        em.detach(updatedMRequisitionLine);
        updatedMRequisitionLine
            .documentDate(UPDATED_DOCUMENT_DATE)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .requisitionAmount(UPDATED_REQUISITION_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .quantityOrdered(UPDATED_QUANTITY_ORDERED)
            .quantityBalance(UPDATED_QUANTITY_BALANCE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(updatedMRequisitionLine);

        restMRequisitionLineMockMvc.perform(put("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isOk());

        // Validate the MRequisitionLine in the database
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeUpdate);
        MRequisitionLine testMRequisitionLine = mRequisitionLineList.get(mRequisitionLineList.size() - 1);
        assertThat(testMRequisitionLine.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testMRequisitionLine.getDatePromised()).isEqualTo(UPDATED_DATE_PROMISED);
        assertThat(testMRequisitionLine.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMRequisitionLine.getRequisitionAmount()).isEqualTo(UPDATED_REQUISITION_AMOUNT);
        assertThat(testMRequisitionLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMRequisitionLine.getQuantityOrdered()).isEqualTo(UPDATED_QUANTITY_ORDERED);
        assertThat(testMRequisitionLine.getQuantityBalance()).isEqualTo(UPDATED_QUANTITY_BALANCE);
        assertThat(testMRequisitionLine.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMRequisitionLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMRequisitionLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRequisitionLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMRequisitionLine() throws Exception {
        int databaseSizeBeforeUpdate = mRequisitionLineRepository.findAll().size();

        // Create the MRequisitionLine
        MRequisitionLineDTO mRequisitionLineDTO = mRequisitionLineMapper.toDto(mRequisitionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRequisitionLineMockMvc.perform(put("/api/m-requisition-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRequisitionLine in the database
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRequisitionLine() throws Exception {
        // Initialize the database
        mRequisitionLineRepository.saveAndFlush(mRequisitionLine);

        int databaseSizeBeforeDelete = mRequisitionLineRepository.findAll().size();

        // Delete the mRequisitionLine
        restMRequisitionLineMockMvc.perform(delete("/api/m-requisition-lines/{id}", mRequisitionLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineRepository.findAll();
        assertThat(mRequisitionLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
