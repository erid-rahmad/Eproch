package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
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

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_PROCESSED = false;
    private static final Boolean UPDATED_IS_PROCESSED = true;

    private static final Boolean DEFAULT_TAX = false;
    private static final Boolean UPDATED_TAX = true;

    private static final LocalDate DEFAULT_DOCUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

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
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .isApproved(DEFAULT_IS_APPROVED)
            .isProcessed(DEFAULT_IS_PROCESSED)
            .tax(DEFAULT_TAX)
            .documentDate(DEFAULT_DOCUMENT_DATE)
            .dateRequired(DEFAULT_DATE_REQUIRED)
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
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .isApproved(UPDATED_IS_APPROVED)
            .isProcessed(UPDATED_IS_PROCESSED)
            .tax(UPDATED_TAX)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .dateRequired(UPDATED_DATE_REQUIRED)
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
        assertThat(testMPurchaseOrder.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMPurchaseOrder.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMPurchaseOrder.isIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testMPurchaseOrder.isIsProcessed()).isEqualTo(DEFAULT_IS_PROCESSED);
        assertThat(testMPurchaseOrder.isTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testMPurchaseOrder.getDocumentDate()).isEqualTo(DEFAULT_DOCUMENT_DATE);
        assertThat(testMPurchaseOrder.getDateRequired()).isEqualTo(DEFAULT_DATE_REQUIRED);
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
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isProcessed").value(hasItem(DEFAULT_IS_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
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
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isProcessed").value(DEFAULT_IS_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.booleanValue()))
            .andExpect(jsonPath("$.documentDate").value(DEFAULT_DOCUMENT_DATE.toString()))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
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
    public void getAllMPurchaseOrdersByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isApproved equals to DEFAULT_IS_APPROVED
        defaultMPurchaseOrderShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the mPurchaseOrderList where isApproved equals to UPDATED_IS_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the mPurchaseOrderList where isApproved not equals to UPDATED_IS_APPROVED
        defaultMPurchaseOrderShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultMPurchaseOrderShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the mPurchaseOrderList where isApproved equals to UPDATED_IS_APPROVED
        defaultMPurchaseOrderShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isApproved is not null
        defaultMPurchaseOrderShouldBeFound("isApproved.specified=true");

        // Get all the mPurchaseOrderList where isApproved is null
        defaultMPurchaseOrderShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isProcessed equals to DEFAULT_IS_PROCESSED
        defaultMPurchaseOrderShouldBeFound("isProcessed.equals=" + DEFAULT_IS_PROCESSED);

        // Get all the mPurchaseOrderList where isProcessed equals to UPDATED_IS_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("isProcessed.equals=" + UPDATED_IS_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isProcessed not equals to DEFAULT_IS_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("isProcessed.notEquals=" + DEFAULT_IS_PROCESSED);

        // Get all the mPurchaseOrderList where isProcessed not equals to UPDATED_IS_PROCESSED
        defaultMPurchaseOrderShouldBeFound("isProcessed.notEquals=" + UPDATED_IS_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isProcessed in DEFAULT_IS_PROCESSED or UPDATED_IS_PROCESSED
        defaultMPurchaseOrderShouldBeFound("isProcessed.in=" + DEFAULT_IS_PROCESSED + "," + UPDATED_IS_PROCESSED);

        // Get all the mPurchaseOrderList where isProcessed equals to UPDATED_IS_PROCESSED
        defaultMPurchaseOrderShouldNotBeFound("isProcessed.in=" + UPDATED_IS_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByIsProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where isProcessed is not null
        defaultMPurchaseOrderShouldBeFound("isProcessed.specified=true");

        // Get all the mPurchaseOrderList where isProcessed is null
        defaultMPurchaseOrderShouldNotBeFound("isProcessed.specified=false");
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
    public void getAllMPurchaseOrdersByDocumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate equals to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.equals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.equals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate not equals to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.notEquals=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate not equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.notEquals=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate in DEFAULT_DOCUMENT_DATE or UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.in=" + DEFAULT_DOCUMENT_DATE + "," + UPDATED_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate equals to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.in=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate is not null
        defaultMPurchaseOrderShouldBeFound("documentDate.specified=true");

        // Get all the mPurchaseOrderList where documentDate is null
        defaultMPurchaseOrderShouldNotBeFound("documentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate is greater than or equal to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate is greater than or equal to UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate is less than or equal to DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate is less than or equal to SMALLER_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate is less than DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.lessThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate is less than UPDATED_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.lessThan=" + UPDATED_DOCUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDocumentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where documentDate is greater than DEFAULT_DOCUMENT_DATE
        defaultMPurchaseOrderShouldNotBeFound("documentDate.greaterThan=" + DEFAULT_DOCUMENT_DATE);

        // Get all the mPurchaseOrderList where documentDate is greater than SMALLER_DOCUMENT_DATE
        defaultMPurchaseOrderShouldBeFound("documentDate.greaterThan=" + SMALLER_DOCUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired is not null
        defaultMPurchaseOrderShouldBeFound("dateRequired.specified=true");

        // Get all the mPurchaseOrderList where dateRequired is null
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMPurchaseOrdersByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPurchaseOrderRepository.saveAndFlush(mPurchaseOrder);

        // Get all the mPurchaseOrderList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMPurchaseOrderShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mPurchaseOrderList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMPurchaseOrderShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPurchaseOrderShouldBeFound(String filter) throws Exception {
        restMPurchaseOrderMockMvc.perform(get("/api/m-purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isProcessed").value(hasItem(DEFAULT_IS_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].documentDate").value(hasItem(DEFAULT_DOCUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
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
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .isApproved(UPDATED_IS_APPROVED)
            .isProcessed(UPDATED_IS_PROCESSED)
            .tax(UPDATED_TAX)
            .documentDate(UPDATED_DOCUMENT_DATE)
            .dateRequired(UPDATED_DATE_REQUIRED)
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
        assertThat(testMPurchaseOrder.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMPurchaseOrder.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMPurchaseOrder.isIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testMPurchaseOrder.isIsProcessed()).isEqualTo(UPDATED_IS_PROCESSED);
        assertThat(testMPurchaseOrder.isTax()).isEqualTo(UPDATED_TAX);
        assertThat(testMPurchaseOrder.getDocumentDate()).isEqualTo(UPDATED_DOCUMENT_DATE);
        assertThat(testMPurchaseOrder.getDateRequired()).isEqualTo(UPDATED_DATE_REQUIRED);
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
