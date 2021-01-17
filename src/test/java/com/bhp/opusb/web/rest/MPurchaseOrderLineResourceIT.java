package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPurchaseOrderLine;
import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MPurchaseOrderLineRepository;
import com.bhp.opusb.service.MPurchaseOrderLineService;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderLineMapper;
import com.bhp.opusb.service.dto.MPurchaseOrderLineCriteria;
import com.bhp.opusb.service.MPurchaseOrderLineQueryService;

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
 * Integration tests for the {@link MPurchaseOrderLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPurchaseOrderLineResourceIT {

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_PROMISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROMISED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PROMISED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_ORDER_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ORDER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

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
    private MPurchaseOrderLineRepository mPurchaseOrderLineRepository;

    @Autowired
    private MPurchaseOrderLineMapper mPurchaseOrderLineMapper;

    @Autowired
    private MPurchaseOrderLineService mPurchaseOrderLineService;

    @Autowired
    private MPurchaseOrderLineQueryService mPurchaseOrderLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPurchaseOrderLineMockMvc;

    private MPurchaseOrderLine mPurchaseOrderLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPurchaseOrderLine createEntity(EntityManager em) {
        MPurchaseOrderLine mPurchaseOrderLine = new MPurchaseOrderLine()
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .datePromised(DEFAULT_DATE_PROMISED)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .orderAmount(DEFAULT_ORDER_AMOUNT)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .remark(DEFAULT_REMARK)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MPurchaseOrder mPurchaseOrder;
        if (TestUtil.findAll(em, MPurchaseOrder.class).isEmpty()) {
            mPurchaseOrder = MPurchaseOrderResourceIT.createEntity(em);
            em.persist(mPurchaseOrder);
            em.flush();
        } else {
            mPurchaseOrder = TestUtil.findAll(em, MPurchaseOrder.class).get(0);
        }
        mPurchaseOrderLine.setPurchaseOrder(mPurchaseOrder);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPurchaseOrderLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mPurchaseOrderLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mPurchaseOrderLine.setUom(cUnitOfMeasure);
        return mPurchaseOrderLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPurchaseOrderLine createUpdatedEntity(EntityManager em) {
        MPurchaseOrderLine mPurchaseOrderLine = new MPurchaseOrderLine()
            .documentDate(UPDATED_DOCUMENT_DATE)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .orderAmount(UPDATED_ORDER_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MPurchaseOrder mPurchaseOrder;
        if (TestUtil.findAll(em, MPurchaseOrder.class).isEmpty()) {
            mPurchaseOrder = MPurchaseOrderResourceIT.createUpdatedEntity(em);
            em.persist(mPurchaseOrder);
            em.flush();
        } else {
            mPurchaseOrder = TestUtil.findAll(em, MPurchaseOrder.class).get(0);
        }
        mPurchaseOrderLine.setPurchaseOrder(mPurchaseOrder);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPurchaseOrderLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mPurchaseOrderLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mPurchaseOrderLine.setUom(cUnitOfMeasure);
        return mPurchaseOrderLine;
    }

    @BeforeEach
    public void initTest() {
        mPurchaseOrderLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPurchaseOrderLine() throws Exception {
        int databaseSizeBeforeCreate = mPurchaseOrderLineRepository.findAll().size();

        // Create the MPurchaseOrderLine
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);
        restMPurchaseOrderLineMockMvc.perform(post("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MPurchaseOrderLine in the database
        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeCreate + 1);
        MPurchaseOrderLine testMPurchaseOrderLine = mPurchaseOrderLineList.get(mPurchaseOrderLineList.size() - 1);
        assertThat(testMPurchaseOrderLine.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testMPurchaseOrderLine.getDatePromised()).isEqualTo(DEFAULT_DATE_PROMISED);
        assertThat(testMPurchaseOrderLine.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
        assertThat(testMPurchaseOrderLine.getOrderAmount()).isEqualTo(DEFAULT_ORDER_AMOUNT);
        assertThat(testMPurchaseOrderLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMPurchaseOrderLine.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMPurchaseOrderLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMPurchaseOrderLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPurchaseOrderLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPurchaseOrderLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPurchaseOrderLineRepository.findAll().size();

        // Create the MPurchaseOrderLine with an existing ID
        mPurchaseOrderLine.setId(1L);
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPurchaseOrderLineMockMvc.perform(post("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPurchaseOrderLine in the database
        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPurchaseOrderLineRepository.findAll().size();
        // set the field null
        mPurchaseOrderLine.setOrderAmount(null);

        // Create the MPurchaseOrderLine, which fails.
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);

        restMPurchaseOrderLineMockMvc.perform(post("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isBadRequest());

        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPurchaseOrderLineRepository.findAll().size();
        // set the field null
        mPurchaseOrderLine.setQuantity(null);

        // Create the MPurchaseOrderLine, which fails.
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);

        restMPurchaseOrderLineMockMvc.perform(post("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isBadRequest());

        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPurchaseOrderLineRepository.findAll().size();
        // set the field null
        mPurchaseOrderLine.setUnitPrice(null);

        // Create the MPurchaseOrderLine, which fails.
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);

        restMPurchaseOrderLineMockMvc.perform(post("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isBadRequest());

        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLines() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPurchaseOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].orderAmount").value(hasItem(DEFAULT_ORDER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPurchaseOrderLine() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get the mPurchaseOrderLine
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines/{id}", mPurchaseOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPurchaseOrderLine.getId().intValue()))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.datePromised").value(DEFAULT_DATE_PROMISED.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.orderAmount").value(DEFAULT_ORDER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPurchaseOrderLinesByIdFiltering() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        Long id = mPurchaseOrderLine.getId();

        defaultMPurchaseOrderLineShouldBeFound("id.equals=" + id);
        defaultMPurchaseOrderLineShouldNotBeFound("id.notEquals=" + id);

        defaultMPurchaseOrderLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPurchaseOrderLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMPurchaseOrderLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPurchaseOrderLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate is not null
        defaultMPurchaseOrderLineShouldBeFound("documentDate.specified=true");

        // Get all the mPurchaseOrderLineList where documentDate is null
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderLineList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultMPurchaseOrderLineShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised equals to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.equals=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.equals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised not equals to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.notEquals=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised not equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.notEquals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised in DEFAULT_DATE_PROMISED or UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.in=" + DEFAULT_DATE_PROMISED + "," + UPDATED_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.in=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised is not null
        defaultMPurchaseOrderLineShouldBeFound("datePromised.specified=true");

        // Get all the mPurchaseOrderLineList where datePromised is null
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised is greater than or equal to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.greaterThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised is greater than or equal to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.greaterThanOrEqual=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised is less than or equal to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.lessThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised is less than or equal to SMALLER_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.lessThanOrEqual=" + SMALLER_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised is less than DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.lessThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised is less than UPDATED_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.lessThan=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDatePromisedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where datePromised is greater than DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderLineShouldNotBeFound("datePromised.greaterThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderLineList where datePromised is greater than SMALLER_DATE_PROMISED
        defaultMPurchaseOrderLineShouldBeFound("datePromised.greaterThan=" + SMALLER_DATE_PROMISED);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired is not null
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.specified=true");

        // Get all the mPurchaseOrderLineList where dateRequired is null
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderLineList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMPurchaseOrderLineShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount equals to DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.equals=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount equals to UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.equals=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount not equals to DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.notEquals=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount not equals to UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.notEquals=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount in DEFAULT_ORDER_AMOUNT or UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.in=" + DEFAULT_ORDER_AMOUNT + "," + UPDATED_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount equals to UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.in=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount is not null
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.specified=true");

        // Get all the mPurchaseOrderLineList where orderAmount is null
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount is greater than or equal to DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.greaterThanOrEqual=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount is greater than or equal to UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.greaterThanOrEqual=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount is less than or equal to DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.lessThanOrEqual=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount is less than or equal to SMALLER_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.lessThanOrEqual=" + SMALLER_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount is less than DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.lessThan=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount is less than UPDATED_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.lessThan=" + UPDATED_ORDER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByOrderAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where orderAmount is greater than DEFAULT_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldNotBeFound("orderAmount.greaterThan=" + DEFAULT_ORDER_AMOUNT);

        // Get all the mPurchaseOrderLineList where orderAmount is greater than SMALLER_ORDER_AMOUNT
        defaultMPurchaseOrderLineShouldBeFound("orderAmount.greaterThan=" + SMALLER_ORDER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity equals to DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity equals to UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity not equals to DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity not equals to UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity equals to UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity is not null
        defaultMPurchaseOrderLineShouldBeFound("quantity.specified=true");

        // Get all the mPurchaseOrderLineList where quantity is null
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity is less than DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity is less than UPDATED_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where quantity is greater than DEFAULT_QUANTITY
        defaultMPurchaseOrderLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mPurchaseOrderLineList where quantity is greater than SMALLER_QUANTITY
        defaultMPurchaseOrderLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice is not null
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.specified=true");

        // Get all the mPurchaseOrderLineList where unitPrice is null
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultMPurchaseOrderLineShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the mPurchaseOrderLineList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultMPurchaseOrderLineShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark equals to DEFAULT_REMARK
        defaultMPurchaseOrderLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mPurchaseOrderLineList where remark equals to UPDATED_REMARK
        defaultMPurchaseOrderLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark not equals to DEFAULT_REMARK
        defaultMPurchaseOrderLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mPurchaseOrderLineList where remark not equals to UPDATED_REMARK
        defaultMPurchaseOrderLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMPurchaseOrderLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mPurchaseOrderLineList where remark equals to UPDATED_REMARK
        defaultMPurchaseOrderLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark is not null
        defaultMPurchaseOrderLineShouldBeFound("remark.specified=true");

        // Get all the mPurchaseOrderLineList where remark is null
        defaultMPurchaseOrderLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark contains DEFAULT_REMARK
        defaultMPurchaseOrderLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mPurchaseOrderLineList where remark contains UPDATED_REMARK
        defaultMPurchaseOrderLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where remark does not contain DEFAULT_REMARK
        defaultMPurchaseOrderLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mPurchaseOrderLineList where remark does not contain UPDATED_REMARK
        defaultMPurchaseOrderLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where uid equals to DEFAULT_UID
        defaultMPurchaseOrderLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPurchaseOrderLineList where uid equals to UPDATED_UID
        defaultMPurchaseOrderLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where uid not equals to DEFAULT_UID
        defaultMPurchaseOrderLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPurchaseOrderLineList where uid not equals to UPDATED_UID
        defaultMPurchaseOrderLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPurchaseOrderLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPurchaseOrderLineList where uid equals to UPDATED_UID
        defaultMPurchaseOrderLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where uid is not null
        defaultMPurchaseOrderLineShouldBeFound("uid.specified=true");

        // Get all the mPurchaseOrderLineList where uid is null
        defaultMPurchaseOrderLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where active equals to DEFAULT_ACTIVE
        defaultMPurchaseOrderLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPurchaseOrderLineList where active equals to UPDATED_ACTIVE
        defaultMPurchaseOrderLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where active not equals to DEFAULT_ACTIVE
        defaultMPurchaseOrderLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPurchaseOrderLineList where active not equals to UPDATED_ACTIVE
        defaultMPurchaseOrderLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPurchaseOrderLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPurchaseOrderLineList where active equals to UPDATED_ACTIVE
        defaultMPurchaseOrderLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        // Get all the mPurchaseOrderLineList where active is not null
        defaultMPurchaseOrderLineShouldBeFound("active.specified=true");

        // Get all the mPurchaseOrderLineList where active is null
        defaultMPurchaseOrderLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPurchaseOrder purchaseOrder = mPurchaseOrderLine.getPurchaseOrder();
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the mPurchaseOrderLineList where purchaseOrder equals to purchaseOrderId
        defaultMPurchaseOrderLineShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the mPurchaseOrderLineList where purchaseOrder equals to purchaseOrderId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        MRequisition requisition = MRequisitionResourceIT.createEntity(em);
        em.persist(requisition);
        em.flush();
        mPurchaseOrderLine.setRequisition(requisition);
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long requisitionId = requisition.getId();

        // Get all the mPurchaseOrderLineList where requisition equals to requisitionId
        defaultMPurchaseOrderLineShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the mPurchaseOrderLineList where requisition equals to requisitionId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        CTax tax = CTaxResourceIT.createEntity(em);
        em.persist(tax);
        em.flush();
        mPurchaseOrderLine.setTax(tax);
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long taxId = tax.getId();

        // Get all the mPurchaseOrderLineList where tax equals to taxId
        defaultMPurchaseOrderLineShouldBeFound("taxId.equals=" + taxId);

        // Get all the mPurchaseOrderLineList where tax equals to taxId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("taxId.equals=" + (taxId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPurchaseOrderLine.getAdOrganization();
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPurchaseOrderLineList where adOrganization equals to adOrganizationId
        defaultMPurchaseOrderLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPurchaseOrderLineList where adOrganization equals to adOrganizationId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mPurchaseOrderLine.getProduct();
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long productId = product.getId();

        // Get all the mPurchaseOrderLineList where product equals to productId
        defaultMPurchaseOrderLineShouldBeFound("productId.equals=" + productId);

        // Get all the mPurchaseOrderLineList where product equals to productId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        CWarehouse warehouse = CWarehouseResourceIT.createEntity(em);
        em.persist(warehouse);
        em.flush();
        mPurchaseOrderLine.setWarehouse(warehouse);
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long warehouseId = warehouse.getId();

        // Get all the mPurchaseOrderLineList where warehouse equals to warehouseId
        defaultMPurchaseOrderLineShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mPurchaseOrderLineList where warehouse equals to warehouseId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        CCostCenter costCenter = CCostCenterResourceIT.createEntity(em);
        em.persist(costCenter);
        em.flush();
        mPurchaseOrderLine.setCostCenter(costCenter);
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long costCenterId = costCenter.getId();

        // Get all the mPurchaseOrderLineList where costCenter equals to costCenterId
        defaultMPurchaseOrderLineShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mPurchaseOrderLineList where costCenter equals to costCenterId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mPurchaseOrderLine.getUom();
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long uomId = uom.getId();

        // Get all the mPurchaseOrderLineList where uom equals to uomId
        defaultMPurchaseOrderLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mPurchaseOrderLineList where uom equals to uomId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrderLinesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        mPurchaseOrderLine.setVendor(vendor);
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);
        Long vendorId = vendor.getId();

        // Get all the mPurchaseOrderLineList where vendor equals to vendorId
        defaultMPurchaseOrderLineShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPurchaseOrderLineList where vendor equals to vendorId + 1
        defaultMPurchaseOrderLineShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPurchaseOrderLineShouldBeFound(String filter) throws Exception {
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPurchaseOrderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].orderAmount").value(hasItem(DEFAULT_ORDER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPurchaseOrderLineShouldNotBeFound(String filter) throws Exception {
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPurchaseOrderLine() throws Exception {
        // Get the mPurchaseOrderLine
        restMPurchaseOrderLineMockMvc.perform(get("/api/m-purchase-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPurchaseOrderLine() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        int databaseSizeBeforeUpdate = mPurchaseOrderLineRepository.findAll().size();

        // Update the mPurchaseOrderLine
        MPurchaseOrderLine updatedMPurchaseOrderLine = mPurchaseOrderLineRepository.findById(mPurchaseOrderLine.getId()).get();
        // Disconnect from session so that the updates on updatedMPurchaseOrderLine are not directly saved in db
        em.detach(updatedMPurchaseOrderLine);
        updatedMPurchaseOrderLine
            .documentDate(UPDATED_DOCUMENT_DATE)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .orderAmount(UPDATED_ORDER_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(updatedMPurchaseOrderLine);

        restMPurchaseOrderLineMockMvc.perform(put("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isOk());

        // Validate the MPurchaseOrderLine in the database
        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeUpdate);
        MPurchaseOrderLine testMPurchaseOrderLine = mPurchaseOrderLineList.get(mPurchaseOrderLineList.size() - 1);
        assertThat(testMPurchaseOrderLine.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testMPurchaseOrderLine.getDatePromised()).isEqualTo(UPDATED_DATE_PROMISED);
        assertThat(testMPurchaseOrderLine.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
        assertThat(testMPurchaseOrderLine.getOrderAmount()).isEqualTo(UPDATED_ORDER_AMOUNT);
        assertThat(testMPurchaseOrderLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMPurchaseOrderLine.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMPurchaseOrderLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMPurchaseOrderLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPurchaseOrderLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPurchaseOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = mPurchaseOrderLineRepository.findAll().size();

        // Create the MPurchaseOrderLine
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPurchaseOrderLineMockMvc.perform(put("/api/m-purchase-order-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPurchaseOrderLine in the database
        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPurchaseOrderLine() throws Exception {
        // Initialize the database
        mPurchaseOrderLineRepository.saveAndFlush(mPurchaseOrderLine);

        int databaseSizeBeforeDelete = mPurchaseOrderLineRepository.findAll().size();

        // Delete the mPurchaseOrderLine
        restMPurchaseOrderLineMockMvc.perform(delete("/api/m-purchase-order-lines/{id}", mPurchaseOrderLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPurchaseOrderLine> mPurchaseOrderLineList = mPurchaseOrderLineRepository.findAll();
        assertThat(mPurchaseOrderLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
