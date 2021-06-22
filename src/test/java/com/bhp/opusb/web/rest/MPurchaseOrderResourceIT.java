package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CPaymentTerm;
import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.MPurchaseOrderService;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderMapper;
import com.bhp.opusb.service.dto.MPurchaseOrderCriteria;
import com.bhp.opusb.service.MPurchaseOrderQueryService;

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
 * Integration tests for the {@link MPurchaseOrderResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPurchaseOrderResourceIT {

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

    private static final Boolean DEFAULT_CONFIRMED = false;
    private static final Boolean UPDATED_CONFIRMED = true;

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_TAX = false;
    private static final Boolean UPDATED_TAX = true;

    private static final LocalDate DEFAULT_DATE_PROMISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROMISED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PROMISED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_DELIVERED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELIVERED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DELIVERED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_SHIPPED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SHIPPED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_SHIPPED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONFIRMATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPurchaseOrderRepository mPurchaseOrderRepository;

    @Autowired
    private MPurchaseOrderMapper mPurchaseOrderMapper;

    @Autowired
    private MPurchaseOrderService mPurchaseOrderService;

    @Autowired
    private MPurchaseOrderQueryService mPurchaseOrderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPurchaseOrderMockMvc;

    private MPurchaseOrder mPurchaseOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPurchaseOrder createEntity(EntityManager em) {
        MPurchaseOrder mPurchaseOrder = new MPurchaseOrder()
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .confirmed(DEFAULT_CONFIRMED)
            .grandTotal(DEFAULT_GRAND_TOTAL)
            .tax(DEFAULT_TAX)
            .datePromised(DEFAULT_DATE_PROMISED)
            .dateDelivered(DEFAULT_DATE_DELIVERED)
            .dateShipped(DEFAULT_DATE_SHIPPED)
            .confirmation(DEFAULT_CONFIRMATION)
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
        mPurchaseOrder.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mPurchaseOrder.setDocumentType(cDocumentType);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPurchaseOrder.setVendor(cVendor);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mPurchaseOrder.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mPurchaseOrder.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mPurchaseOrder.setCostCenter(cCostCenter);
        // Add required entity
        CPaymentTerm cPaymentTerm;
        if (TestUtil.findAll(em, CPaymentTerm.class).isEmpty()) {
            cPaymentTerm = CPaymentTermResourceIT.createEntity(em);
            em.persist(cPaymentTerm);
            em.flush();
        } else {
            cPaymentTerm = TestUtil.findAll(em, CPaymentTerm.class).get(0);
        }
        mPurchaseOrder.setPaymentTerm(cPaymentTerm);
        return mPurchaseOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPurchaseOrder createUpdatedEntity(EntityManager em) {
        MPurchaseOrder mPurchaseOrder = new MPurchaseOrder()
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .confirmed(UPDATED_CONFIRMED)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .tax(UPDATED_TAX)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateDelivered(UPDATED_DATE_DELIVERED)
            .dateShipped(UPDATED_DATE_SHIPPED)
            .confirmation(UPDATED_CONFIRMATION)
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
        mPurchaseOrder.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mPurchaseOrder.setDocumentType(cDocumentType);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPurchaseOrder.setVendor(cVendor);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mPurchaseOrder.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createUpdatedEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mPurchaseOrder.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mPurchaseOrder.setCostCenter(cCostCenter);
        // Add required entity
        CPaymentTerm cPaymentTerm;
        if (TestUtil.findAll(em, CPaymentTerm.class).isEmpty()) {
            cPaymentTerm = CPaymentTermResourceIT.createUpdatedEntity(em);
            em.persist(cPaymentTerm);
            em.flush();
        } else {
            cPaymentTerm = TestUtil.findAll(em, CPaymentTerm.class).get(0);
        }
        mPurchaseOrder.setPaymentTerm(cPaymentTerm);
        return mPurchaseOrder;
    }

    @BeforeEach
    public void initTest() {
        mPurchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = mPurchaseOrderRepository.findAll().size();

        // Create the MPurchaseOrder
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(mPurchaseOrder);
        restMPurchaseOrderMockMvc.perform(post("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the MPurchaseOrder in the database
        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MPurchaseOrder testMPurchaseOrder = mPurchaseOrderList.get(mPurchaseOrderList.size() - 1);
        assertThat(testMPurchaseOrder.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMPurchaseOrder.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMPurchaseOrder.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMPurchaseOrder.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMPurchaseOrder.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMPurchaseOrder.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMPurchaseOrder.isConfirmed()).isEqualTo(DEFAULT_CONFIRMED);
        assertThat(testMPurchaseOrder.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
        assertThat(testMPurchaseOrder.isTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testMPurchaseOrder.getDatePromised()).isEqualTo(DEFAULT_DATE_PROMISED);
        assertThat(testMPurchaseOrder.getDateDelivered()).isEqualTo(DEFAULT_DATE_DELIVERED);
        assertThat(testMPurchaseOrder.getDateShipped()).isEqualTo(DEFAULT_DATE_SHIPPED);
        assertThat(testMPurchaseOrder.getConfirmation()).isEqualTo(DEFAULT_CONFIRMATION);
        assertThat(testMPurchaseOrder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMPurchaseOrder.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPurchaseOrder.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPurchaseOrderRepository.findAll().size();

        // Create the MPurchaseOrder with an existing ID
        mPurchaseOrder.setId(1L);
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(mPurchaseOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPurchaseOrderMockMvc.perform(post("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPurchaseOrder in the database
        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPurchaseOrderRepository.findAll().size();
        // set the field null
        mPurchaseOrder.setDocumentAction(null);

        // Create the MPurchaseOrder, which fails.
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(mPurchaseOrder);

        restMPurchaseOrderMockMvc.perform(post("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPurchaseOrderRepository.findAll().size();
        // set the field null
        mPurchaseOrder.setDocumentStatus(null);

        // Create the MPurchaseOrder, which fails.
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(mPurchaseOrder);

        restMPurchaseOrderMockMvc.perform(post("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrders() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmed").value(hasItem(DEFAULT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateDelivered").value(hasItem(DEFAULT_DATE_DELIVERED.toString())))
            .andExpect(jsonPath("$.[*].dateShipped").value(hasItem(DEFAULT_DATE_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].confirmation").value(hasItem(DEFAULT_CONFIRMATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPurchaseOrder() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get the mPurchaseOrder
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders/{id}", mPurchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPurchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.confirmed").value(DEFAULT_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.booleanValue()))
            .andExpect(jsonPath("$.datePromised").value(DEFAULT_DATE_PROMISED.toString()))
            .andExpect(jsonPath("$.dateDelivered").value(DEFAULT_DATE_DELIVERED.toString()))
            .andExpect(jsonPath("$.dateShipped").value(DEFAULT_DATE_SHIPPED.toString()))
            .andExpect(jsonPath("$.confirmation").value(DEFAULT_CONFIRMATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPurchaseOrdersByIdFiltering() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        Long id = mPurchaseOrder.getId();

        defaultMPurchaseOrderShouldBeFound("id.equals=" + id);
        defaultMPurchaseOrderShouldNotBeFound("id.notEquals=" + id);

        defaultMPurchaseOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPurchaseOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultMPurchaseOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPurchaseOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx is not null
        defaultMPurchaseOrderShouldBeFound("dateTrx.specified=true");

        // Get all the mPurchaseOrderList where dateTrx is null
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx is less than UPDATED_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMPurchaseOrderShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mPurchaseOrderList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMPurchaseOrderShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMPurchaseOrderShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPurchaseOrderList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMPurchaseOrderShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPurchaseOrderList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mPurchaseOrderList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo is not null
        defaultMPurchaseOrderShouldBeFound("documentNo.specified=true");

        // Get all the mPurchaseOrderList where documentNo is null
        defaultMPurchaseOrderShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMPurchaseOrderShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPurchaseOrderList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMPurchaseOrderShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPurchaseOrderList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMPurchaseOrderShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPurchaseOrderList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPurchaseOrderList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mPurchaseOrderList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction is not null
        defaultMPurchaseOrderShouldBeFound("documentAction.specified=true");

        // Get all the mPurchaseOrderList where documentAction is null
        defaultMPurchaseOrderShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPurchaseOrderList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPurchaseOrderList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMPurchaseOrderShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPurchaseOrderList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPurchaseOrderList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mPurchaseOrderList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus is not null
        defaultMPurchaseOrderShouldBeFound("documentStatus.specified=true");

        // Get all the mPurchaseOrderList where documentStatus is null
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPurchaseOrderList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPurchaseOrderList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMPurchaseOrderShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where approved equals to DEFAULT_APPROVED
        defaultMPurchaseOrderShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mPurchaseOrderList where approved equals to UPDATED_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where approved not equals to DEFAULT_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mPurchaseOrderList where approved not equals to UPDATED_APPROVED
        defaultMPurchaseOrderShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMPurchaseOrderShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mPurchaseOrderList where approved equals to UPDATED_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where approved is not null
        defaultMPurchaseOrderShouldBeFound("approved.specified=true");

        // Get all the mPurchaseOrderList where approved is null
        defaultMPurchaseOrderShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where processed equals to DEFAULT_PROCESSED
        defaultMPurchaseOrderShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mPurchaseOrderList where processed equals to UPDATED_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where processed not equals to DEFAULT_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mPurchaseOrderList where processed not equals to UPDATED_PROCESSED
        defaultMPurchaseOrderShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMPurchaseOrderShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mPurchaseOrderList where processed equals to UPDATED_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where processed is not null
        defaultMPurchaseOrderShouldBeFound("processed.specified=true");

        // Get all the mPurchaseOrderList where processed is null
        defaultMPurchaseOrderShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmed equals to DEFAULT_CONFIRMED
        defaultMPurchaseOrderShouldBeFound("confirmed.equals=" + DEFAULT_CONFIRMED);

        // Get all the mPurchaseOrderList where confirmed equals to UPDATED_CONFIRMED
        defaultMPurchaseOrderShouldNotBeFound("confirmed.equals=" + UPDATED_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmed not equals to DEFAULT_CONFIRMED
        defaultMPurchaseOrderShouldNotBeFound("confirmed.notEquals=" + DEFAULT_CONFIRMED);

        // Get all the mPurchaseOrderList where confirmed not equals to UPDATED_CONFIRMED
        defaultMPurchaseOrderShouldBeFound("confirmed.notEquals=" + UPDATED_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmed in DEFAULT_CONFIRMED or UPDATED_CONFIRMED
        defaultMPurchaseOrderShouldBeFound("confirmed.in=" + DEFAULT_CONFIRMED + "," + UPDATED_CONFIRMED);

        // Get all the mPurchaseOrderList where confirmed equals to UPDATED_CONFIRMED
        defaultMPurchaseOrderShouldNotBeFound("confirmed.in=" + UPDATED_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmed is not null
        defaultMPurchaseOrderShouldBeFound("confirmed.specified=true");

        // Get all the mPurchaseOrderList where confirmed is null
        defaultMPurchaseOrderShouldNotBeFound("confirmed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal equals to DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.equals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.equals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal not equals to DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.notEquals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal not equals to UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.notEquals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal in DEFAULT_GRAND_TOTAL or UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.in=" + DEFAULT_GRAND_TOTAL + "," + UPDATED_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.in=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal is not null
        defaultMPurchaseOrderShouldBeFound("grandTotal.specified=true");

        // Get all the mPurchaseOrderList where grandTotal is null
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal is greater than or equal to DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.greaterThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal is greater than or equal to UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.greaterThanOrEqual=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal is less than or equal to DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.lessThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal is less than or equal to SMALLER_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.lessThanOrEqual=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal is less than DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.lessThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal is less than UPDATED_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.lessThan=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where grandTotal is greater than DEFAULT_GRAND_TOTAL
        defaultMPurchaseOrderShouldNotBeFound("grandTotal.greaterThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mPurchaseOrderList where grandTotal is greater than SMALLER_GRAND_TOTAL
        defaultMPurchaseOrderShouldBeFound("grandTotal.greaterThan=" + SMALLER_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where tax equals to DEFAULT_TAX
        defaultMPurchaseOrderShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the mPurchaseOrderList where tax equals to UPDATED_TAX
        defaultMPurchaseOrderShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where tax not equals to DEFAULT_TAX
        defaultMPurchaseOrderShouldNotBeFound("tax.notEquals=" + DEFAULT_TAX);

        // Get all the mPurchaseOrderList where tax not equals to UPDATED_TAX
        defaultMPurchaseOrderShouldBeFound("tax.notEquals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultMPurchaseOrderShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the mPurchaseOrderList where tax equals to UPDATED_TAX
        defaultMPurchaseOrderShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where tax is not null
        defaultMPurchaseOrderShouldBeFound("tax.specified=true");

        // Get all the mPurchaseOrderList where tax is null
        defaultMPurchaseOrderShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised equals to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.equals=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.equals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised not equals to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.notEquals=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised not equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.notEquals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised in DEFAULT_DATE_PROMISED or UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.in=" + DEFAULT_DATE_PROMISED + "," + UPDATED_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.in=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised is not null
        defaultMPurchaseOrderShouldBeFound("datePromised.specified=true");

        // Get all the mPurchaseOrderList where datePromised is null
        defaultMPurchaseOrderShouldNotBeFound("datePromised.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised is greater than or equal to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.greaterThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised is greater than or equal to UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.greaterThanOrEqual=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised is less than or equal to DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.lessThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised is less than or equal to SMALLER_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.lessThanOrEqual=" + SMALLER_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised is less than DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.lessThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised is less than UPDATED_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.lessThan=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDatePromisedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where datePromised is greater than DEFAULT_DATE_PROMISED
        defaultMPurchaseOrderShouldNotBeFound("datePromised.greaterThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mPurchaseOrderList where datePromised is greater than SMALLER_DATE_PROMISED
        defaultMPurchaseOrderShouldBeFound("datePromised.greaterThan=" + SMALLER_DATE_PROMISED);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered equals to DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.equals=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered equals to UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.equals=" + UPDATED_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered not equals to DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.notEquals=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered not equals to UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.notEquals=" + UPDATED_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered in DEFAULT_DATE_DELIVERED or UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.in=" + DEFAULT_DATE_DELIVERED + "," + UPDATED_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered equals to UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.in=" + UPDATED_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered is not null
        defaultMPurchaseOrderShouldBeFound("dateDelivered.specified=true");

        // Get all the mPurchaseOrderList where dateDelivered is null
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered is greater than or equal to DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.greaterThanOrEqual=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered is greater than or equal to UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.greaterThanOrEqual=" + UPDATED_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered is less than or equal to DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.lessThanOrEqual=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered is less than or equal to SMALLER_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.lessThanOrEqual=" + SMALLER_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered is less than DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.lessThan=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered is less than UPDATED_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.lessThan=" + UPDATED_DATE_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateDeliveredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateDelivered is greater than DEFAULT_DATE_DELIVERED
        defaultMPurchaseOrderShouldNotBeFound("dateDelivered.greaterThan=" + DEFAULT_DATE_DELIVERED);

        // Get all the mPurchaseOrderList where dateDelivered is greater than SMALLER_DATE_DELIVERED
        defaultMPurchaseOrderShouldBeFound("dateDelivered.greaterThan=" + SMALLER_DATE_DELIVERED);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped equals to DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.equals=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped equals to UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.equals=" + UPDATED_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped not equals to DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.notEquals=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped not equals to UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.notEquals=" + UPDATED_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped in DEFAULT_DATE_SHIPPED or UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.in=" + DEFAULT_DATE_SHIPPED + "," + UPDATED_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped equals to UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.in=" + UPDATED_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped is not null
        defaultMPurchaseOrderShouldBeFound("dateShipped.specified=true");

        // Get all the mPurchaseOrderList where dateShipped is null
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped is greater than or equal to DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.greaterThanOrEqual=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped is greater than or equal to UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.greaterThanOrEqual=" + UPDATED_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped is less than or equal to DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.lessThanOrEqual=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped is less than or equal to SMALLER_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.lessThanOrEqual=" + SMALLER_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped is less than DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.lessThan=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped is less than UPDATED_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.lessThan=" + UPDATED_DATE_SHIPPED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateShippedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateShipped is greater than DEFAULT_DATE_SHIPPED
        defaultMPurchaseOrderShouldNotBeFound("dateShipped.greaterThan=" + DEFAULT_DATE_SHIPPED);

        // Get all the mPurchaseOrderList where dateShipped is greater than SMALLER_DATE_SHIPPED
        defaultMPurchaseOrderShouldBeFound("dateShipped.greaterThan=" + SMALLER_DATE_SHIPPED);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation equals to DEFAULT_CONFIRMATION
        defaultMPurchaseOrderShouldBeFound("confirmation.equals=" + DEFAULT_CONFIRMATION);

        // Get all the mPurchaseOrderList where confirmation equals to UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldNotBeFound("confirmation.equals=" + UPDATED_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation not equals to DEFAULT_CONFIRMATION
        defaultMPurchaseOrderShouldNotBeFound("confirmation.notEquals=" + DEFAULT_CONFIRMATION);

        // Get all the mPurchaseOrderList where confirmation not equals to UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldBeFound("confirmation.notEquals=" + UPDATED_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation in DEFAULT_CONFIRMATION or UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldBeFound("confirmation.in=" + DEFAULT_CONFIRMATION + "," + UPDATED_CONFIRMATION);

        // Get all the mPurchaseOrderList where confirmation equals to UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldNotBeFound("confirmation.in=" + UPDATED_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation is not null
        defaultMPurchaseOrderShouldBeFound("confirmation.specified=true");

        // Get all the mPurchaseOrderList where confirmation is null
        defaultMPurchaseOrderShouldNotBeFound("confirmation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation contains DEFAULT_CONFIRMATION
        defaultMPurchaseOrderShouldBeFound("confirmation.contains=" + DEFAULT_CONFIRMATION);

        // Get all the mPurchaseOrderList where confirmation contains UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldNotBeFound("confirmation.contains=" + UPDATED_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByConfirmationNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where confirmation does not contain DEFAULT_CONFIRMATION
        defaultMPurchaseOrderShouldNotBeFound("confirmation.doesNotContain=" + DEFAULT_CONFIRMATION);

        // Get all the mPurchaseOrderList where confirmation does not contain UPDATED_CONFIRMATION
        defaultMPurchaseOrderShouldBeFound("confirmation.doesNotContain=" + UPDATED_CONFIRMATION);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description equals to DEFAULT_DESCRIPTION
        defaultMPurchaseOrderShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mPurchaseOrderList where description equals to UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description not equals to DEFAULT_DESCRIPTION
        defaultMPurchaseOrderShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mPurchaseOrderList where description not equals to UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mPurchaseOrderList where description equals to UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description is not null
        defaultMPurchaseOrderShouldBeFound("description.specified=true");

        // Get all the mPurchaseOrderList where description is null
        defaultMPurchaseOrderShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description contains DEFAULT_DESCRIPTION
        defaultMPurchaseOrderShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mPurchaseOrderList where description contains UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where description does not contain DEFAULT_DESCRIPTION
        defaultMPurchaseOrderShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mPurchaseOrderList where description does not contain UPDATED_DESCRIPTION
        defaultMPurchaseOrderShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where uid equals to DEFAULT_UID
        defaultMPurchaseOrderShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPurchaseOrderList where uid equals to UPDATED_UID
        defaultMPurchaseOrderShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where uid not equals to DEFAULT_UID
        defaultMPurchaseOrderShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPurchaseOrderList where uid not equals to UPDATED_UID
        defaultMPurchaseOrderShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPurchaseOrderShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPurchaseOrderList where uid equals to UPDATED_UID
        defaultMPurchaseOrderShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where uid is not null
        defaultMPurchaseOrderShouldBeFound("uid.specified=true");

        // Get all the mPurchaseOrderList where uid is null
        defaultMPurchaseOrderShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where active equals to DEFAULT_ACTIVE
        defaultMPurchaseOrderShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPurchaseOrderList where active equals to UPDATED_ACTIVE
        defaultMPurchaseOrderShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where active not equals to DEFAULT_ACTIVE
        defaultMPurchaseOrderShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPurchaseOrderList where active not equals to UPDATED_ACTIVE
        defaultMPurchaseOrderShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPurchaseOrderShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPurchaseOrderList where active equals to UPDATED_ACTIVE
        defaultMPurchaseOrderShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where active is not null
        defaultMPurchaseOrderShouldBeFound("active.specified=true");

        // Get all the mPurchaseOrderList where active is null
        defaultMPurchaseOrderShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPurchaseOrder.getAdOrganization();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPurchaseOrderList where adOrganization equals to adOrganizationId
        defaultMPurchaseOrderShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPurchaseOrderList where adOrganization equals to adOrganizationId + 1
        defaultMPurchaseOrderShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = mPurchaseOrder.getDocumentType();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long documentTypeId = documentType.getId();

        // Get all the mPurchaseOrderList where documentType equals to documentTypeId
        defaultMPurchaseOrderShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mPurchaseOrderList where documentType equals to documentTypeId + 1
        defaultMPurchaseOrderShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPurchaseOrder.getVendor();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long vendorId = vendor.getId();

        // Get all the mPurchaseOrderList where vendor equals to vendorId
        defaultMPurchaseOrderShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPurchaseOrderList where vendor equals to vendorId + 1
        defaultMPurchaseOrderShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mPurchaseOrder.getCurrency();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long currencyId = currency.getId();

        // Get all the mPurchaseOrderList where currency equals to currencyId
        defaultMPurchaseOrderShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mPurchaseOrderList where currency equals to currencyId + 1
        defaultMPurchaseOrderShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByWarehouseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CWarehouse warehouse = mPurchaseOrder.getWarehouse();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long warehouseId = warehouse.getId();

        // Get all the mPurchaseOrderList where warehouse equals to warehouseId
        defaultMPurchaseOrderShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mPurchaseOrderList where warehouse equals to warehouseId + 1
        defaultMPurchaseOrderShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mPurchaseOrder.getCostCenter();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long costCenterId = costCenter.getId();

        // Get all the mPurchaseOrderList where costCenter equals to costCenterId
        defaultMPurchaseOrderShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mPurchaseOrderList where costCenter equals to costCenterId + 1
        defaultMPurchaseOrderShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByPaymentTermIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPaymentTerm paymentTerm = mPurchaseOrder.getPaymentTerm();
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);
        Long paymentTermId = paymentTerm.getId();

        // Get all the mPurchaseOrderList where paymentTerm equals to paymentTermId
        defaultMPurchaseOrderShouldBeFound("paymentTermId.equals=" + paymentTermId);

        // Get all the mPurchaseOrderList where paymentTerm equals to paymentTermId + 1
        defaultMPurchaseOrderShouldNotBeFound("paymentTermId.equals=" + (paymentTermId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPurchaseOrderShouldBeFound(String filter) throws Exception {
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmed").value(hasItem(DEFAULT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].dateDelivered").value(hasItem(DEFAULT_DATE_DELIVERED.toString())))
            .andExpect(jsonPath("$.[*].dateShipped").value(hasItem(DEFAULT_DATE_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].confirmation").value(hasItem(DEFAULT_CONFIRMATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPurchaseOrderShouldNotBeFound(String filter) throws Exception {
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPurchaseOrder() throws Exception {
        // Get the mPurchaseOrder
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPurchaseOrder() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        int databaseSizeBeforeUpdate = mPurchaseOrderRepository.findAll().size();

        // Update the mPurchaseOrder
        MPurchaseOrder updatedMPurchaseOrder = mPurchaseOrderRepository.findById(mPurchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedMPurchaseOrder are not directly saved in db
        em.detach(updatedMPurchaseOrder);
        updatedMPurchaseOrder
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .confirmed(UPDATED_CONFIRMED)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .tax(UPDATED_TAX)
            .datePromised(UPDATED_DATE_PROMISED)
            .dateDelivered(UPDATED_DATE_DELIVERED)
            .dateShipped(UPDATED_DATE_SHIPPED)
            .confirmation(UPDATED_CONFIRMATION)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(updatedMPurchaseOrder);

        restMPurchaseOrderMockMvc.perform(put("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isOk());

        // Validate the MPurchaseOrder in the database
        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        MPurchaseOrder testMPurchaseOrder = mPurchaseOrderList.get(mPurchaseOrderList.size() - 1);
        assertThat(testMPurchaseOrder.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMPurchaseOrder.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMPurchaseOrder.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMPurchaseOrder.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMPurchaseOrder.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMPurchaseOrder.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMPurchaseOrder.isConfirmed()).isEqualTo(UPDATED_CONFIRMED);
        assertThat(testMPurchaseOrder.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
        assertThat(testMPurchaseOrder.isTax()).isEqualTo(UPDATED_TAX);
        assertThat(testMPurchaseOrder.getDatePromised()).isEqualTo(UPDATED_DATE_PROMISED);
        assertThat(testMPurchaseOrder.getDateDelivered()).isEqualTo(UPDATED_DATE_DELIVERED);
        assertThat(testMPurchaseOrder.getDateShipped()).isEqualTo(UPDATED_DATE_SHIPPED);
        assertThat(testMPurchaseOrder.getConfirmation()).isEqualTo(UPDATED_CONFIRMATION);
        assertThat(testMPurchaseOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMPurchaseOrder.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPurchaseOrder.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = mPurchaseOrderRepository.findAll().size();

        // Create the MPurchaseOrder
        MPurchaseOrderDTO mPurchaseOrderDTO = mPurchaseOrderMapper.toDto(mPurchaseOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPurchaseOrderMockMvc.perform(put("/api/m-purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPurchaseOrder in the database
        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPurchaseOrder() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        int databaseSizeBeforeDelete = mPurchaseOrderRepository.findAll().size();

        // Delete the mPurchaseOrder
        restMPurchaseOrderMockMvc.perform(delete("/api/m-purchase-orders/{id}", mPurchaseOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPurchaseOrder> mPurchaseOrderList = mPurchaseOrderRepository.findAll();
        assertThat(mPurchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
