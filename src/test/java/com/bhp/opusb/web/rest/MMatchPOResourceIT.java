package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.repository.MMatchPORepository;
import com.bhp.opusb.service.MMatchPOService;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;
import com.bhp.opusb.service.dto.MMatchPOCriteria;
import com.bhp.opusb.service.MMatchPOQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MMatchPOResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MMatchPOResourceIT {

    private static final String DEFAULT_DELIVERY_NO = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_NO = "BBBBBBBBBB";

    private static final String DEFAULT_C_DOC_TYPE = "AA";
    private static final String UPDATED_C_DOC_TYPE = "BB";

    private static final String DEFAULT_PO_NO = "AAAAAAAAAA";
    private static final String UPDATED_PO_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PO_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_RECEIPT_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIPT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIPT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIPT_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QTY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_C_CONVERSION_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_C_CONVERSION_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_C_CONVERSION_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OPEN_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPEN_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_OPEN_QTY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRICE_ACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_ACTUAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE_ACTUAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_ACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_ACTUAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_ACTUAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OPEN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPEN_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_OPEN_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OPEN_FOREIGN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPEN_FOREIGN_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_OPEN_FOREIGN_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_LINES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LINES = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_LINES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_TOTAL_LINES = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_TOTAL_LINES = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_TOTAL_LINES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATE_ACCOUNT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCOUNT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_ACCOUNT = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_C_DOC_TYPE_MR = "AA";
    private static final String UPDATED_C_DOC_TYPE_MR = "BB";

    private static final String DEFAULT_ORDER_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_SUFFIX = "BBBBBBBBBB";

    private static final Integer DEFAULT_LINE_NO_PO = 1;
    private static final Integer UPDATED_LINE_NO_PO = 2;
    private static final Integer SMALLER_LINE_NO_PO = 1 - 1;

    private static final Integer DEFAULT_LINE_NO_MR = 1;
    private static final Integer UPDATED_LINE_NO_MR = 2;
    private static final Integer SMALLER_LINE_NO_MR = 1 - 1;

    private static final Boolean DEFAULT_TAXABLE = false;
    private static final Boolean UPDATED_TAXABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_M_MATCH_TYPE = "A";
    private static final String UPDATED_M_MATCH_TYPE = "B";

    private static final String DEFAULT_ITEM_DESC_1 = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESC_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESC_2 = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESC_2 = "BBBBBBBBBB";

    @Autowired
    private MMatchPORepository mMatchPORepository;

    @Autowired
    private MMatchPOMapper mMatchPOMapper;

    @Autowired
    private MMatchPOService mMatchPOService;

    @Autowired
    private MMatchPOQueryService mMatchPOQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMatchPOMockMvc;

    private MMatchPO mMatchPO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMatchPO createEntity(EntityManager em) {
        MMatchPO mMatchPO = new MMatchPO()
            .deliveryNo(DEFAULT_DELIVERY_NO)
            .cDocType(DEFAULT_C_DOC_TYPE)
            .poNo(DEFAULT_PO_NO)
            .poDate(DEFAULT_PO_DATE)
            .receiptNo(DEFAULT_RECEIPT_NO)
            .receiptDate(DEFAULT_RECEIPT_DATE)
            .qty(DEFAULT_QTY)
            .cConversionRate(DEFAULT_C_CONVERSION_RATE)
            .openQty(DEFAULT_OPEN_QTY)
            .priceActual(DEFAULT_PRICE_ACTUAL)
            .foreignActual(DEFAULT_FOREIGN_ACTUAL)
            .openAmount(DEFAULT_OPEN_AMOUNT)
            .openForeignAmount(DEFAULT_OPEN_FOREIGN_AMOUNT)
            .totalLines(DEFAULT_TOTAL_LINES)
            .foreignTotalLines(DEFAULT_FOREIGN_TOTAL_LINES)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .foreignTaxAmount(DEFAULT_FOREIGN_TAX_AMOUNT)
            .dateAccount(DEFAULT_DATE_ACCOUNT)
            .cDocTypeMr(DEFAULT_C_DOC_TYPE_MR)
            .orderSuffix(DEFAULT_ORDER_SUFFIX)
            .lineNoPo(DEFAULT_LINE_NO_PO)
            .lineNoMr(DEFAULT_LINE_NO_MR)
            .taxable(DEFAULT_TAXABLE)
            .description(DEFAULT_DESCRIPTION)
            .mMatchType(DEFAULT_M_MATCH_TYPE)
            .itemDesc1(DEFAULT_ITEM_DESC_1)
            .itemDesc2(DEFAULT_ITEM_DESC_2);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mMatchPO.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mMatchPO.setCCostCenter(cCostCenter);
        return mMatchPO;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMatchPO createUpdatedEntity(EntityManager em) {
        MMatchPO mMatchPO = new MMatchPO()
            .deliveryNo(UPDATED_DELIVERY_NO)
            .cDocType(UPDATED_C_DOC_TYPE)
            .poNo(UPDATED_PO_NO)
            .poDate(UPDATED_PO_DATE)
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .qty(UPDATED_QTY)
            .cConversionRate(UPDATED_C_CONVERSION_RATE)
            .openQty(UPDATED_OPEN_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .openAmount(UPDATED_OPEN_AMOUNT)
            .openForeignAmount(UPDATED_OPEN_FOREIGN_AMOUNT)
            .totalLines(UPDATED_TOTAL_LINES)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .dateAccount(UPDATED_DATE_ACCOUNT)
            .cDocTypeMr(UPDATED_C_DOC_TYPE_MR)
            .orderSuffix(UPDATED_ORDER_SUFFIX)
            .lineNoPo(UPDATED_LINE_NO_PO)
            .lineNoMr(UPDATED_LINE_NO_MR)
            .taxable(UPDATED_TAXABLE)
            .description(UPDATED_DESCRIPTION)
            .mMatchType(UPDATED_M_MATCH_TYPE)
            .itemDesc1(UPDATED_ITEM_DESC_1)
            .itemDesc2(UPDATED_ITEM_DESC_2);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mMatchPO.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mMatchPO.setCCostCenter(cCostCenter);
        return mMatchPO;
    }

    @BeforeEach
    public void initTest() {
        mMatchPO = createEntity(em);
    }

    @Test
    @Transactional
    public void createMMatchPO() throws Exception {
        int databaseSizeBeforeCreate = mMatchPORepository.findAll().size();

        // Create the MMatchPO
        MMatchPODTO mMatchPODTO = mMatchPOMapper.toDto(mMatchPO);
        restMMatchPOMockMvc.perform(post("/api/m-match-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mMatchPODTO)))
            .andExpect(status().isCreated());

        // Validate the MMatchPO in the database
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeCreate + 1);
        MMatchPO testMMatchPO = mMatchPOList.get(mMatchPOList.size() - 1);
        assertThat(testMMatchPO.getDeliveryNo()).isEqualTo(DEFAULT_DELIVERY_NO);
        assertThat(testMMatchPO.getcDocType()).isEqualTo(DEFAULT_C_DOC_TYPE);
        assertThat(testMMatchPO.getPoNo()).isEqualTo(DEFAULT_PO_NO);
        assertThat(testMMatchPO.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testMMatchPO.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testMMatchPO.getReceiptDate()).isEqualTo(DEFAULT_RECEIPT_DATE);
        assertThat(testMMatchPO.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMMatchPO.getcConversionRate()).isEqualTo(DEFAULT_C_CONVERSION_RATE);
        assertThat(testMMatchPO.getOpenQty()).isEqualTo(DEFAULT_OPEN_QTY);
        assertThat(testMMatchPO.getPriceActual()).isEqualTo(DEFAULT_PRICE_ACTUAL);
        assertThat(testMMatchPO.getForeignActual()).isEqualTo(DEFAULT_FOREIGN_ACTUAL);
        assertThat(testMMatchPO.getOpenAmount()).isEqualTo(DEFAULT_OPEN_AMOUNT);
        assertThat(testMMatchPO.getOpenForeignAmount()).isEqualTo(DEFAULT_OPEN_FOREIGN_AMOUNT);
        assertThat(testMMatchPO.getTotalLines()).isEqualTo(DEFAULT_TOTAL_LINES);
        assertThat(testMMatchPO.getForeignTotalLines()).isEqualTo(DEFAULT_FOREIGN_TOTAL_LINES);
        assertThat(testMMatchPO.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testMMatchPO.getForeignTaxAmount()).isEqualTo(DEFAULT_FOREIGN_TAX_AMOUNT);
        assertThat(testMMatchPO.getDateAccount()).isEqualTo(DEFAULT_DATE_ACCOUNT);
        assertThat(testMMatchPO.getcDocTypeMr()).isEqualTo(DEFAULT_C_DOC_TYPE_MR);
        assertThat(testMMatchPO.getOrderSuffix()).isEqualTo(DEFAULT_ORDER_SUFFIX);
        assertThat(testMMatchPO.getLineNoPo()).isEqualTo(DEFAULT_LINE_NO_PO);
        assertThat(testMMatchPO.getLineNoMr()).isEqualTo(DEFAULT_LINE_NO_MR);
        assertThat(testMMatchPO.isTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testMMatchPO.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMMatchPO.getmMatchType()).isEqualTo(DEFAULT_M_MATCH_TYPE);
        assertThat(testMMatchPO.getItemDesc1()).isEqualTo(DEFAULT_ITEM_DESC_1);
        assertThat(testMMatchPO.getItemDesc2()).isEqualTo(DEFAULT_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void createMMatchPOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mMatchPORepository.findAll().size();

        // Create the MMatchPO with an existing ID
        mMatchPO.setId(1L);
        MMatchPODTO mMatchPODTO = mMatchPOMapper.toDto(mMatchPO);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMatchPOMockMvc.perform(post("/api/m-match-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mMatchPODTO)))
            .andExpect(status().isBadRequest());

        // Validate the MMatchPO in the database
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMMatchPOS() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList
        restMMatchPOMockMvc.perform(get("/api/m-match-pos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMatchPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].cDocType").value(hasItem(DEFAULT_C_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].cConversionRate").value(hasItem(DEFAULT_C_CONVERSION_RATE.intValue())))
            .andExpect(jsonPath("$.[*].openQty").value(hasItem(DEFAULT_OPEN_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].openAmount").value(hasItem(DEFAULT_OPEN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].openForeignAmount").value(hasItem(DEFAULT_OPEN_FOREIGN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].dateAccount").value(hasItem(DEFAULT_DATE_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].cDocTypeMr").value(hasItem(DEFAULT_C_DOC_TYPE_MR)))
            .andExpect(jsonPath("$.[*].orderSuffix").value(hasItem(DEFAULT_ORDER_SUFFIX)))
            .andExpect(jsonPath("$.[*].lineNoPo").value(hasItem(DEFAULT_LINE_NO_PO)))
            .andExpect(jsonPath("$.[*].lineNoMr").value(hasItem(DEFAULT_LINE_NO_MR)))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mMatchType").value(hasItem(DEFAULT_M_MATCH_TYPE)))
            .andExpect(jsonPath("$.[*].itemDesc1").value(hasItem(DEFAULT_ITEM_DESC_1)))
            .andExpect(jsonPath("$.[*].itemDesc2").value(hasItem(DEFAULT_ITEM_DESC_2)));
    }
    
    @Test
    @Transactional
    public void getMMatchPO() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get the mMatchPO
        restMMatchPOMockMvc.perform(get("/api/m-match-pos/{id}", mMatchPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMatchPO.getId().intValue()))
            .andExpect(jsonPath("$.deliveryNo").value(DEFAULT_DELIVERY_NO))
            .andExpect(jsonPath("$.cDocType").value(DEFAULT_C_DOC_TYPE))
            .andExpect(jsonPath("$.poNo").value(DEFAULT_PO_NO))
            .andExpect(jsonPath("$.poDate").value(DEFAULT_PO_DATE.toString()))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.receiptDate").value(DEFAULT_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.intValue()))
            .andExpect(jsonPath("$.cConversionRate").value(DEFAULT_C_CONVERSION_RATE.intValue()))
            .andExpect(jsonPath("$.openQty").value(DEFAULT_OPEN_QTY.intValue()))
            .andExpect(jsonPath("$.priceActual").value(DEFAULT_PRICE_ACTUAL.intValue()))
            .andExpect(jsonPath("$.foreignActual").value(DEFAULT_FOREIGN_ACTUAL.intValue()))
            .andExpect(jsonPath("$.openAmount").value(DEFAULT_OPEN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.openForeignAmount").value(DEFAULT_OPEN_FOREIGN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalLines").value(DEFAULT_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.foreignTotalLines").value(DEFAULT_FOREIGN_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.foreignTaxAmount").value(DEFAULT_FOREIGN_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.dateAccount").value(DEFAULT_DATE_ACCOUNT.toString()))
            .andExpect(jsonPath("$.cDocTypeMr").value(DEFAULT_C_DOC_TYPE_MR))
            .andExpect(jsonPath("$.orderSuffix").value(DEFAULT_ORDER_SUFFIX))
            .andExpect(jsonPath("$.lineNoPo").value(DEFAULT_LINE_NO_PO))
            .andExpect(jsonPath("$.lineNoMr").value(DEFAULT_LINE_NO_MR))
            .andExpect(jsonPath("$.taxable").value(DEFAULT_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mMatchType").value(DEFAULT_M_MATCH_TYPE))
            .andExpect(jsonPath("$.itemDesc1").value(DEFAULT_ITEM_DESC_1))
            .andExpect(jsonPath("$.itemDesc2").value(DEFAULT_ITEM_DESC_2));
    }


    @Test
    @Transactional
    public void getMMatchPOSByIdFiltering() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        Long id = mMatchPO.getId();

        defaultMMatchPOShouldBeFound("id.equals=" + id);
        defaultMMatchPOShouldNotBeFound("id.notEquals=" + id);

        defaultMMatchPOShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMMatchPOShouldNotBeFound("id.greaterThan=" + id);

        defaultMMatchPOShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMMatchPOShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo equals to DEFAULT_DELIVERY_NO
        defaultMMatchPOShouldBeFound("deliveryNo.equals=" + DEFAULT_DELIVERY_NO);

        // Get all the mMatchPOList where deliveryNo equals to UPDATED_DELIVERY_NO
        defaultMMatchPOShouldNotBeFound("deliveryNo.equals=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo not equals to DEFAULT_DELIVERY_NO
        defaultMMatchPOShouldNotBeFound("deliveryNo.notEquals=" + DEFAULT_DELIVERY_NO);

        // Get all the mMatchPOList where deliveryNo not equals to UPDATED_DELIVERY_NO
        defaultMMatchPOShouldBeFound("deliveryNo.notEquals=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo in DEFAULT_DELIVERY_NO or UPDATED_DELIVERY_NO
        defaultMMatchPOShouldBeFound("deliveryNo.in=" + DEFAULT_DELIVERY_NO + "," + UPDATED_DELIVERY_NO);

        // Get all the mMatchPOList where deliveryNo equals to UPDATED_DELIVERY_NO
        defaultMMatchPOShouldNotBeFound("deliveryNo.in=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo is not null
        defaultMMatchPOShouldBeFound("deliveryNo.specified=true");

        // Get all the mMatchPOList where deliveryNo is null
        defaultMMatchPOShouldNotBeFound("deliveryNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo contains DEFAULT_DELIVERY_NO
        defaultMMatchPOShouldBeFound("deliveryNo.contains=" + DEFAULT_DELIVERY_NO);

        // Get all the mMatchPOList where deliveryNo contains UPDATED_DELIVERY_NO
        defaultMMatchPOShouldNotBeFound("deliveryNo.contains=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDeliveryNoNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where deliveryNo does not contain DEFAULT_DELIVERY_NO
        defaultMMatchPOShouldNotBeFound("deliveryNo.doesNotContain=" + DEFAULT_DELIVERY_NO);

        // Get all the mMatchPOList where deliveryNo does not contain UPDATED_DELIVERY_NO
        defaultMMatchPOShouldBeFound("deliveryNo.doesNotContain=" + UPDATED_DELIVERY_NO);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType equals to DEFAULT_C_DOC_TYPE
        defaultMMatchPOShouldBeFound("cDocType.equals=" + DEFAULT_C_DOC_TYPE);

        // Get all the mMatchPOList where cDocType equals to UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldNotBeFound("cDocType.equals=" + UPDATED_C_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType not equals to DEFAULT_C_DOC_TYPE
        defaultMMatchPOShouldNotBeFound("cDocType.notEquals=" + DEFAULT_C_DOC_TYPE);

        // Get all the mMatchPOList where cDocType not equals to UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldBeFound("cDocType.notEquals=" + UPDATED_C_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType in DEFAULT_C_DOC_TYPE or UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldBeFound("cDocType.in=" + DEFAULT_C_DOC_TYPE + "," + UPDATED_C_DOC_TYPE);

        // Get all the mMatchPOList where cDocType equals to UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldNotBeFound("cDocType.in=" + UPDATED_C_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType is not null
        defaultMMatchPOShouldBeFound("cDocType.specified=true");

        // Get all the mMatchPOList where cDocType is null
        defaultMMatchPOShouldNotBeFound("cDocType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType contains DEFAULT_C_DOC_TYPE
        defaultMMatchPOShouldBeFound("cDocType.contains=" + DEFAULT_C_DOC_TYPE);

        // Get all the mMatchPOList where cDocType contains UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldNotBeFound("cDocType.contains=" + UPDATED_C_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocType does not contain DEFAULT_C_DOC_TYPE
        defaultMMatchPOShouldNotBeFound("cDocType.doesNotContain=" + DEFAULT_C_DOC_TYPE);

        // Get all the mMatchPOList where cDocType does not contain UPDATED_C_DOC_TYPE
        defaultMMatchPOShouldBeFound("cDocType.doesNotContain=" + UPDATED_C_DOC_TYPE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByPoNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo equals to DEFAULT_PO_NO
        defaultMMatchPOShouldBeFound("poNo.equals=" + DEFAULT_PO_NO);

        // Get all the mMatchPOList where poNo equals to UPDATED_PO_NO
        defaultMMatchPOShouldNotBeFound("poNo.equals=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo not equals to DEFAULT_PO_NO
        defaultMMatchPOShouldNotBeFound("poNo.notEquals=" + DEFAULT_PO_NO);

        // Get all the mMatchPOList where poNo not equals to UPDATED_PO_NO
        defaultMMatchPOShouldBeFound("poNo.notEquals=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoNoIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo in DEFAULT_PO_NO or UPDATED_PO_NO
        defaultMMatchPOShouldBeFound("poNo.in=" + DEFAULT_PO_NO + "," + UPDATED_PO_NO);

        // Get all the mMatchPOList where poNo equals to UPDATED_PO_NO
        defaultMMatchPOShouldNotBeFound("poNo.in=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo is not null
        defaultMMatchPOShouldBeFound("poNo.specified=true");

        // Get all the mMatchPOList where poNo is null
        defaultMMatchPOShouldNotBeFound("poNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByPoNoContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo contains DEFAULT_PO_NO
        defaultMMatchPOShouldBeFound("poNo.contains=" + DEFAULT_PO_NO);

        // Get all the mMatchPOList where poNo contains UPDATED_PO_NO
        defaultMMatchPOShouldNotBeFound("poNo.contains=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoNoNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poNo does not contain DEFAULT_PO_NO
        defaultMMatchPOShouldNotBeFound("poNo.doesNotContain=" + DEFAULT_PO_NO);

        // Get all the mMatchPOList where poNo does not contain UPDATED_PO_NO
        defaultMMatchPOShouldBeFound("poNo.doesNotContain=" + UPDATED_PO_NO);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate equals to DEFAULT_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.equals=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate equals to UPDATED_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.equals=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate not equals to DEFAULT_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.notEquals=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate not equals to UPDATED_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.notEquals=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate in DEFAULT_PO_DATE or UPDATED_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.in=" + DEFAULT_PO_DATE + "," + UPDATED_PO_DATE);

        // Get all the mMatchPOList where poDate equals to UPDATED_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.in=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate is not null
        defaultMMatchPOShouldBeFound("poDate.specified=true");

        // Get all the mMatchPOList where poDate is null
        defaultMMatchPOShouldNotBeFound("poDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate is greater than or equal to DEFAULT_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.greaterThanOrEqual=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate is greater than or equal to UPDATED_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.greaterThanOrEqual=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate is less than or equal to DEFAULT_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.lessThanOrEqual=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate is less than or equal to SMALLER_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.lessThanOrEqual=" + SMALLER_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate is less than DEFAULT_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.lessThan=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate is less than UPDATED_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.lessThan=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPoDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where poDate is greater than DEFAULT_PO_DATE
        defaultMMatchPOShouldNotBeFound("poDate.greaterThan=" + DEFAULT_PO_DATE);

        // Get all the mMatchPOList where poDate is greater than SMALLER_PO_DATE
        defaultMMatchPOShouldBeFound("poDate.greaterThan=" + SMALLER_PO_DATE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo equals to DEFAULT_RECEIPT_NO
        defaultMMatchPOShouldBeFound("receiptNo.equals=" + DEFAULT_RECEIPT_NO);

        // Get all the mMatchPOList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMMatchPOShouldNotBeFound("receiptNo.equals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo not equals to DEFAULT_RECEIPT_NO
        defaultMMatchPOShouldNotBeFound("receiptNo.notEquals=" + DEFAULT_RECEIPT_NO);

        // Get all the mMatchPOList where receiptNo not equals to UPDATED_RECEIPT_NO
        defaultMMatchPOShouldBeFound("receiptNo.notEquals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo in DEFAULT_RECEIPT_NO or UPDATED_RECEIPT_NO
        defaultMMatchPOShouldBeFound("receiptNo.in=" + DEFAULT_RECEIPT_NO + "," + UPDATED_RECEIPT_NO);

        // Get all the mMatchPOList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMMatchPOShouldNotBeFound("receiptNo.in=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo is not null
        defaultMMatchPOShouldBeFound("receiptNo.specified=true");

        // Get all the mMatchPOList where receiptNo is null
        defaultMMatchPOShouldNotBeFound("receiptNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo contains DEFAULT_RECEIPT_NO
        defaultMMatchPOShouldBeFound("receiptNo.contains=" + DEFAULT_RECEIPT_NO);

        // Get all the mMatchPOList where receiptNo contains UPDATED_RECEIPT_NO
        defaultMMatchPOShouldNotBeFound("receiptNo.contains=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptNoNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptNo does not contain DEFAULT_RECEIPT_NO
        defaultMMatchPOShouldNotBeFound("receiptNo.doesNotContain=" + DEFAULT_RECEIPT_NO);

        // Get all the mMatchPOList where receiptNo does not contain UPDATED_RECEIPT_NO
        defaultMMatchPOShouldBeFound("receiptNo.doesNotContain=" + UPDATED_RECEIPT_NO);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate equals to DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.equals=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate equals to UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.equals=" + UPDATED_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate not equals to DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.notEquals=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate not equals to UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.notEquals=" + UPDATED_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate in DEFAULT_RECEIPT_DATE or UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.in=" + DEFAULT_RECEIPT_DATE + "," + UPDATED_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate equals to UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.in=" + UPDATED_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate is not null
        defaultMMatchPOShouldBeFound("receiptDate.specified=true");

        // Get all the mMatchPOList where receiptDate is null
        defaultMMatchPOShouldNotBeFound("receiptDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate is greater than or equal to DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.greaterThanOrEqual=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate is greater than or equal to UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.greaterThanOrEqual=" + UPDATED_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate is less than or equal to DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.lessThanOrEqual=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate is less than or equal to SMALLER_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.lessThanOrEqual=" + SMALLER_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate is less than DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.lessThan=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate is less than UPDATED_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.lessThan=" + UPDATED_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByReceiptDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where receiptDate is greater than DEFAULT_RECEIPT_DATE
        defaultMMatchPOShouldNotBeFound("receiptDate.greaterThan=" + DEFAULT_RECEIPT_DATE);

        // Get all the mMatchPOList where receiptDate is greater than SMALLER_RECEIPT_DATE
        defaultMMatchPOShouldBeFound("receiptDate.greaterThan=" + SMALLER_RECEIPT_DATE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty equals to DEFAULT_QTY
        defaultMMatchPOShouldBeFound("qty.equals=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty equals to UPDATED_QTY
        defaultMMatchPOShouldNotBeFound("qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty not equals to DEFAULT_QTY
        defaultMMatchPOShouldNotBeFound("qty.notEquals=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty not equals to UPDATED_QTY
        defaultMMatchPOShouldBeFound("qty.notEquals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty in DEFAULT_QTY or UPDATED_QTY
        defaultMMatchPOShouldBeFound("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY);

        // Get all the mMatchPOList where qty equals to UPDATED_QTY
        defaultMMatchPOShouldNotBeFound("qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty is not null
        defaultMMatchPOShouldBeFound("qty.specified=true");

        // Get all the mMatchPOList where qty is null
        defaultMMatchPOShouldNotBeFound("qty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty is greater than or equal to DEFAULT_QTY
        defaultMMatchPOShouldBeFound("qty.greaterThanOrEqual=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty is greater than or equal to UPDATED_QTY
        defaultMMatchPOShouldNotBeFound("qty.greaterThanOrEqual=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty is less than or equal to DEFAULT_QTY
        defaultMMatchPOShouldBeFound("qty.lessThanOrEqual=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty is less than or equal to SMALLER_QTY
        defaultMMatchPOShouldNotBeFound("qty.lessThanOrEqual=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty is less than DEFAULT_QTY
        defaultMMatchPOShouldNotBeFound("qty.lessThan=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty is less than UPDATED_QTY
        defaultMMatchPOShouldBeFound("qty.lessThan=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty is greater than DEFAULT_QTY
        defaultMMatchPOShouldNotBeFound("qty.greaterThan=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty is greater than SMALLER_QTY
        defaultMMatchPOShouldBeFound("qty.greaterThan=" + SMALLER_QTY);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate equals to DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.equals=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate equals to UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.equals=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate not equals to DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.notEquals=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate not equals to UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.notEquals=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate in DEFAULT_C_CONVERSION_RATE or UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.in=" + DEFAULT_C_CONVERSION_RATE + "," + UPDATED_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate equals to UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.in=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate is not null
        defaultMMatchPOShouldBeFound("cConversionRate.specified=true");

        // Get all the mMatchPOList where cConversionRate is null
        defaultMMatchPOShouldNotBeFound("cConversionRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate is greater than or equal to DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.greaterThanOrEqual=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate is greater than or equal to UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.greaterThanOrEqual=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate is less than or equal to DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.lessThanOrEqual=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate is less than or equal to SMALLER_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.lessThanOrEqual=" + SMALLER_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate is less than DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.lessThan=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate is less than UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.lessThan=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate is greater than DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.greaterThan=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate is greater than SMALLER_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.greaterThan=" + SMALLER_C_CONVERSION_RATE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty equals to DEFAULT_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.equals=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty equals to UPDATED_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.equals=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty not equals to DEFAULT_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.notEquals=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty not equals to UPDATED_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.notEquals=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty in DEFAULT_OPEN_QTY or UPDATED_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.in=" + DEFAULT_OPEN_QTY + "," + UPDATED_OPEN_QTY);

        // Get all the mMatchPOList where openQty equals to UPDATED_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.in=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty is not null
        defaultMMatchPOShouldBeFound("openQty.specified=true");

        // Get all the mMatchPOList where openQty is null
        defaultMMatchPOShouldNotBeFound("openQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty is greater than or equal to DEFAULT_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.greaterThanOrEqual=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty is greater than or equal to UPDATED_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.greaterThanOrEqual=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty is less than or equal to DEFAULT_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.lessThanOrEqual=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty is less than or equal to SMALLER_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.lessThanOrEqual=" + SMALLER_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty is less than DEFAULT_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.lessThan=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty is less than UPDATED_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.lessThan=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty is greater than DEFAULT_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.greaterThan=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty is greater than SMALLER_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.greaterThan=" + SMALLER_OPEN_QTY);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual equals to DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.equals=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual equals to UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.equals=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual not equals to DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.notEquals=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual not equals to UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.notEquals=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual in DEFAULT_PRICE_ACTUAL or UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.in=" + DEFAULT_PRICE_ACTUAL + "," + UPDATED_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual equals to UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.in=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual is not null
        defaultMMatchPOShouldBeFound("priceActual.specified=true");

        // Get all the mMatchPOList where priceActual is null
        defaultMMatchPOShouldNotBeFound("priceActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual is greater than or equal to DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.greaterThanOrEqual=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual is greater than or equal to UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.greaterThanOrEqual=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual is less than or equal to DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.lessThanOrEqual=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual is less than or equal to SMALLER_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.lessThanOrEqual=" + SMALLER_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual is less than DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.lessThan=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual is less than UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.lessThan=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual is greater than DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.greaterThan=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual is greater than SMALLER_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.greaterThan=" + SMALLER_PRICE_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual equals to DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.equals=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual equals to UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.equals=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual not equals to DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.notEquals=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual not equals to UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.notEquals=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual in DEFAULT_FOREIGN_ACTUAL or UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.in=" + DEFAULT_FOREIGN_ACTUAL + "," + UPDATED_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual equals to UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.in=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual is not null
        defaultMMatchPOShouldBeFound("foreignActual.specified=true");

        // Get all the mMatchPOList where foreignActual is null
        defaultMMatchPOShouldNotBeFound("foreignActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual is greater than or equal to DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.greaterThanOrEqual=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual is greater than or equal to UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.greaterThanOrEqual=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual is less than or equal to DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.lessThanOrEqual=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual is less than or equal to SMALLER_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.lessThanOrEqual=" + SMALLER_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual is less than DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.lessThan=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual is less than UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.lessThan=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual is greater than DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.greaterThan=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual is greater than SMALLER_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.greaterThan=" + SMALLER_FOREIGN_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount equals to DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.equals=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount equals to UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.equals=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount not equals to DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.notEquals=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount not equals to UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.notEquals=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount in DEFAULT_OPEN_AMOUNT or UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.in=" + DEFAULT_OPEN_AMOUNT + "," + UPDATED_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount equals to UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.in=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount is not null
        defaultMMatchPOShouldBeFound("openAmount.specified=true");

        // Get all the mMatchPOList where openAmount is null
        defaultMMatchPOShouldNotBeFound("openAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount is greater than or equal to DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.greaterThanOrEqual=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount is greater than or equal to UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.greaterThanOrEqual=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount is less than or equal to DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.lessThanOrEqual=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount is less than or equal to SMALLER_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.lessThanOrEqual=" + SMALLER_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount is less than DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.lessThan=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount is less than UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.lessThan=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount is greater than DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.greaterThan=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount is greater than SMALLER_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.greaterThan=" + SMALLER_OPEN_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount equals to DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.equals=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount equals to UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.equals=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount not equals to DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.notEquals=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount not equals to UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.notEquals=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount in DEFAULT_OPEN_FOREIGN_AMOUNT or UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.in=" + DEFAULT_OPEN_FOREIGN_AMOUNT + "," + UPDATED_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount equals to UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.in=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount is not null
        defaultMMatchPOShouldBeFound("openForeignAmount.specified=true");

        // Get all the mMatchPOList where openForeignAmount is null
        defaultMMatchPOShouldNotBeFound("openForeignAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount is greater than or equal to DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.greaterThanOrEqual=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount is greater than or equal to UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.greaterThanOrEqual=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount is less than or equal to DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.lessThanOrEqual=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount is less than or equal to SMALLER_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.lessThanOrEqual=" + SMALLER_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount is less than DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.lessThan=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount is less than UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.lessThan=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount is greater than DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.greaterThan=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount is greater than SMALLER_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.greaterThan=" + SMALLER_OPEN_FOREIGN_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines equals to DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.equals=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.equals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines not equals to DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.notEquals=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines not equals to UPDATED_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.notEquals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines in DEFAULT_TOTAL_LINES or UPDATED_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.in=" + DEFAULT_TOTAL_LINES + "," + UPDATED_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.in=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines is not null
        defaultMMatchPOShouldBeFound("totalLines.specified=true");

        // Get all the mMatchPOList where totalLines is null
        defaultMMatchPOShouldNotBeFound("totalLines.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines is greater than or equal to DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.greaterThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines is greater than or equal to UPDATED_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.greaterThanOrEqual=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines is less than or equal to DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.lessThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines is less than or equal to SMALLER_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.lessThanOrEqual=" + SMALLER_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines is less than DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.lessThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines is less than UPDATED_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.lessThan=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines is greater than DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.greaterThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines is greater than SMALLER_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.greaterThan=" + SMALLER_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines equals to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.equals=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.equals=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines not equals to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.notEquals=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines not equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.notEquals=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines in DEFAULT_FOREIGN_TOTAL_LINES or UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.in=" + DEFAULT_FOREIGN_TOTAL_LINES + "," + UPDATED_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.in=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines is not null
        defaultMMatchPOShouldBeFound("foreignTotalLines.specified=true");

        // Get all the mMatchPOList where foreignTotalLines is null
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines is greater than or equal to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.greaterThanOrEqual=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines is greater than or equal to UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.greaterThanOrEqual=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines is less than or equal to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.lessThanOrEqual=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines is less than or equal to SMALLER_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.lessThanOrEqual=" + SMALLER_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines is less than DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.lessThan=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines is less than UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.lessThan=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines is greater than DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.greaterThan=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines is greater than SMALLER_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.greaterThan=" + SMALLER_FOREIGN_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount is not null
        defaultMMatchPOShouldBeFound("taxAmount.specified=true");

        // Get all the mMatchPOList where taxAmount is null
        defaultMMatchPOShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.equals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.equals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount not equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.notEquals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount not equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.notEquals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount in DEFAULT_FOREIGN_TAX_AMOUNT or UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.in=" + DEFAULT_FOREIGN_TAX_AMOUNT + "," + UPDATED_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.in=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount is not null
        defaultMMatchPOShouldBeFound("foreignTaxAmount.specified=true");

        // Get all the mMatchPOList where foreignTaxAmount is null
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount is greater than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.greaterThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount is greater than or equal to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.greaterThanOrEqual=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount is less than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.lessThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount is less than or equal to SMALLER_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.lessThanOrEqual=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount is less than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.lessThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount is less than UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.lessThan=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount is greater than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.greaterThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount is greater than SMALLER_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.greaterThan=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount equals to DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.equals=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount equals to UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.equals=" + UPDATED_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount not equals to DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.notEquals=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount not equals to UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.notEquals=" + UPDATED_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount in DEFAULT_DATE_ACCOUNT or UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.in=" + DEFAULT_DATE_ACCOUNT + "," + UPDATED_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount equals to UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.in=" + UPDATED_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount is not null
        defaultMMatchPOShouldBeFound("dateAccount.specified=true");

        // Get all the mMatchPOList where dateAccount is null
        defaultMMatchPOShouldNotBeFound("dateAccount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount is greater than or equal to DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.greaterThanOrEqual=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount is greater than or equal to UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.greaterThanOrEqual=" + UPDATED_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount is less than or equal to DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.lessThanOrEqual=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount is less than or equal to SMALLER_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.lessThanOrEqual=" + SMALLER_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount is less than DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.lessThan=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount is less than UPDATED_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.lessThan=" + UPDATED_DATE_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDateAccountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where dateAccount is greater than DEFAULT_DATE_ACCOUNT
        defaultMMatchPOShouldNotBeFound("dateAccount.greaterThan=" + DEFAULT_DATE_ACCOUNT);

        // Get all the mMatchPOList where dateAccount is greater than SMALLER_DATE_ACCOUNT
        defaultMMatchPOShouldBeFound("dateAccount.greaterThan=" + SMALLER_DATE_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr equals to DEFAULT_C_DOC_TYPE_MR
        defaultMMatchPOShouldBeFound("cDocTypeMr.equals=" + DEFAULT_C_DOC_TYPE_MR);

        // Get all the mMatchPOList where cDocTypeMr equals to UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.equals=" + UPDATED_C_DOC_TYPE_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr not equals to DEFAULT_C_DOC_TYPE_MR
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.notEquals=" + DEFAULT_C_DOC_TYPE_MR);

        // Get all the mMatchPOList where cDocTypeMr not equals to UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldBeFound("cDocTypeMr.notEquals=" + UPDATED_C_DOC_TYPE_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr in DEFAULT_C_DOC_TYPE_MR or UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldBeFound("cDocTypeMr.in=" + DEFAULT_C_DOC_TYPE_MR + "," + UPDATED_C_DOC_TYPE_MR);

        // Get all the mMatchPOList where cDocTypeMr equals to UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.in=" + UPDATED_C_DOC_TYPE_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr is not null
        defaultMMatchPOShouldBeFound("cDocTypeMr.specified=true");

        // Get all the mMatchPOList where cDocTypeMr is null
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr contains DEFAULT_C_DOC_TYPE_MR
        defaultMMatchPOShouldBeFound("cDocTypeMr.contains=" + DEFAULT_C_DOC_TYPE_MR);

        // Get all the mMatchPOList where cDocTypeMr contains UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.contains=" + UPDATED_C_DOC_TYPE_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycDocTypeMrNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cDocTypeMr does not contain DEFAULT_C_DOC_TYPE_MR
        defaultMMatchPOShouldNotBeFound("cDocTypeMr.doesNotContain=" + DEFAULT_C_DOC_TYPE_MR);

        // Get all the mMatchPOList where cDocTypeMr does not contain UPDATED_C_DOC_TYPE_MR
        defaultMMatchPOShouldBeFound("cDocTypeMr.doesNotContain=" + UPDATED_C_DOC_TYPE_MR);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix equals to DEFAULT_ORDER_SUFFIX
        defaultMMatchPOShouldBeFound("orderSuffix.equals=" + DEFAULT_ORDER_SUFFIX);

        // Get all the mMatchPOList where orderSuffix equals to UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldNotBeFound("orderSuffix.equals=" + UPDATED_ORDER_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix not equals to DEFAULT_ORDER_SUFFIX
        defaultMMatchPOShouldNotBeFound("orderSuffix.notEquals=" + DEFAULT_ORDER_SUFFIX);

        // Get all the mMatchPOList where orderSuffix not equals to UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldBeFound("orderSuffix.notEquals=" + UPDATED_ORDER_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix in DEFAULT_ORDER_SUFFIX or UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldBeFound("orderSuffix.in=" + DEFAULT_ORDER_SUFFIX + "," + UPDATED_ORDER_SUFFIX);

        // Get all the mMatchPOList where orderSuffix equals to UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldNotBeFound("orderSuffix.in=" + UPDATED_ORDER_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix is not null
        defaultMMatchPOShouldBeFound("orderSuffix.specified=true");

        // Get all the mMatchPOList where orderSuffix is null
        defaultMMatchPOShouldNotBeFound("orderSuffix.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix contains DEFAULT_ORDER_SUFFIX
        defaultMMatchPOShouldBeFound("orderSuffix.contains=" + DEFAULT_ORDER_SUFFIX);

        // Get all the mMatchPOList where orderSuffix contains UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldNotBeFound("orderSuffix.contains=" + UPDATED_ORDER_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOrderSuffixNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where orderSuffix does not contain DEFAULT_ORDER_SUFFIX
        defaultMMatchPOShouldNotBeFound("orderSuffix.doesNotContain=" + DEFAULT_ORDER_SUFFIX);

        // Get all the mMatchPOList where orderSuffix does not contain UPDATED_ORDER_SUFFIX
        defaultMMatchPOShouldBeFound("orderSuffix.doesNotContain=" + UPDATED_ORDER_SUFFIX);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo equals to DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.equals=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo equals to UPDATED_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.equals=" + UPDATED_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo not equals to DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.notEquals=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo not equals to UPDATED_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.notEquals=" + UPDATED_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo in DEFAULT_LINE_NO_PO or UPDATED_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.in=" + DEFAULT_LINE_NO_PO + "," + UPDATED_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo equals to UPDATED_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.in=" + UPDATED_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo is not null
        defaultMMatchPOShouldBeFound("lineNoPo.specified=true");

        // Get all the mMatchPOList where lineNoPo is null
        defaultMMatchPOShouldNotBeFound("lineNoPo.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo is greater than or equal to DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.greaterThanOrEqual=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo is greater than or equal to UPDATED_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.greaterThanOrEqual=" + UPDATED_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo is less than or equal to DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.lessThanOrEqual=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo is less than or equal to SMALLER_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.lessThanOrEqual=" + SMALLER_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo is less than DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.lessThan=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo is less than UPDATED_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.lessThan=" + UPDATED_LINE_NO_PO);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoPoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoPo is greater than DEFAULT_LINE_NO_PO
        defaultMMatchPOShouldNotBeFound("lineNoPo.greaterThan=" + DEFAULT_LINE_NO_PO);

        // Get all the mMatchPOList where lineNoPo is greater than SMALLER_LINE_NO_PO
        defaultMMatchPOShouldBeFound("lineNoPo.greaterThan=" + SMALLER_LINE_NO_PO);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr equals to DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.equals=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr equals to UPDATED_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.equals=" + UPDATED_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr not equals to DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.notEquals=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr not equals to UPDATED_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.notEquals=" + UPDATED_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr in DEFAULT_LINE_NO_MR or UPDATED_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.in=" + DEFAULT_LINE_NO_MR + "," + UPDATED_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr equals to UPDATED_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.in=" + UPDATED_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr is not null
        defaultMMatchPOShouldBeFound("lineNoMr.specified=true");

        // Get all the mMatchPOList where lineNoMr is null
        defaultMMatchPOShouldNotBeFound("lineNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr is greater than or equal to DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.greaterThanOrEqual=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr is greater than or equal to UPDATED_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.greaterThanOrEqual=" + UPDATED_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr is less than or equal to DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.lessThanOrEqual=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr is less than or equal to SMALLER_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.lessThanOrEqual=" + SMALLER_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsLessThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr is less than DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.lessThan=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr is less than UPDATED_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.lessThan=" + UPDATED_LINE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByLineNoMrIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where lineNoMr is greater than DEFAULT_LINE_NO_MR
        defaultMMatchPOShouldNotBeFound("lineNoMr.greaterThan=" + DEFAULT_LINE_NO_MR);

        // Get all the mMatchPOList where lineNoMr is greater than SMALLER_LINE_NO_MR
        defaultMMatchPOShouldBeFound("lineNoMr.greaterThan=" + SMALLER_LINE_NO_MR);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByTaxableIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxable equals to DEFAULT_TAXABLE
        defaultMMatchPOShouldBeFound("taxable.equals=" + DEFAULT_TAXABLE);

        // Get all the mMatchPOList where taxable equals to UPDATED_TAXABLE
        defaultMMatchPOShouldNotBeFound("taxable.equals=" + UPDATED_TAXABLE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxable not equals to DEFAULT_TAXABLE
        defaultMMatchPOShouldNotBeFound("taxable.notEquals=" + DEFAULT_TAXABLE);

        // Get all the mMatchPOList where taxable not equals to UPDATED_TAXABLE
        defaultMMatchPOShouldBeFound("taxable.notEquals=" + UPDATED_TAXABLE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxableIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxable in DEFAULT_TAXABLE or UPDATED_TAXABLE
        defaultMMatchPOShouldBeFound("taxable.in=" + DEFAULT_TAXABLE + "," + UPDATED_TAXABLE);

        // Get all the mMatchPOList where taxable equals to UPDATED_TAXABLE
        defaultMMatchPOShouldNotBeFound("taxable.in=" + UPDATED_TAXABLE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxableIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxable is not null
        defaultMMatchPOShouldBeFound("taxable.specified=true");

        // Get all the mMatchPOList where taxable is null
        defaultMMatchPOShouldNotBeFound("taxable.specified=false");
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description equals to DEFAULT_DESCRIPTION
        defaultMMatchPOShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mMatchPOList where description equals to UPDATED_DESCRIPTION
        defaultMMatchPOShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description not equals to DEFAULT_DESCRIPTION
        defaultMMatchPOShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mMatchPOList where description not equals to UPDATED_DESCRIPTION
        defaultMMatchPOShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMMatchPOShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mMatchPOList where description equals to UPDATED_DESCRIPTION
        defaultMMatchPOShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description is not null
        defaultMMatchPOShouldBeFound("description.specified=true");

        // Get all the mMatchPOList where description is null
        defaultMMatchPOShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description contains DEFAULT_DESCRIPTION
        defaultMMatchPOShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mMatchPOList where description contains UPDATED_DESCRIPTION
        defaultMMatchPOShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where description does not contain DEFAULT_DESCRIPTION
        defaultMMatchPOShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mMatchPOList where description does not contain UPDATED_DESCRIPTION
        defaultMMatchPOShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType equals to DEFAULT_M_MATCH_TYPE
        defaultMMatchPOShouldBeFound("mMatchType.equals=" + DEFAULT_M_MATCH_TYPE);

        // Get all the mMatchPOList where mMatchType equals to UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldNotBeFound("mMatchType.equals=" + UPDATED_M_MATCH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType not equals to DEFAULT_M_MATCH_TYPE
        defaultMMatchPOShouldNotBeFound("mMatchType.notEquals=" + DEFAULT_M_MATCH_TYPE);

        // Get all the mMatchPOList where mMatchType not equals to UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldBeFound("mMatchType.notEquals=" + UPDATED_M_MATCH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType in DEFAULT_M_MATCH_TYPE or UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldBeFound("mMatchType.in=" + DEFAULT_M_MATCH_TYPE + "," + UPDATED_M_MATCH_TYPE);

        // Get all the mMatchPOList where mMatchType equals to UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldNotBeFound("mMatchType.in=" + UPDATED_M_MATCH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType is not null
        defaultMMatchPOShouldBeFound("mMatchType.specified=true");

        // Get all the mMatchPOList where mMatchType is null
        defaultMMatchPOShouldNotBeFound("mMatchType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType contains DEFAULT_M_MATCH_TYPE
        defaultMMatchPOShouldBeFound("mMatchType.contains=" + DEFAULT_M_MATCH_TYPE);

        // Get all the mMatchPOList where mMatchType contains UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldNotBeFound("mMatchType.contains=" + UPDATED_M_MATCH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymMatchTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mMatchType does not contain DEFAULT_M_MATCH_TYPE
        defaultMMatchPOShouldNotBeFound("mMatchType.doesNotContain=" + DEFAULT_M_MATCH_TYPE);

        // Get all the mMatchPOList where mMatchType does not contain UPDATED_M_MATCH_TYPE
        defaultMMatchPOShouldBeFound("mMatchType.doesNotContain=" + UPDATED_M_MATCH_TYPE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1IsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 equals to DEFAULT_ITEM_DESC_1
        defaultMMatchPOShouldBeFound("itemDesc1.equals=" + DEFAULT_ITEM_DESC_1);

        // Get all the mMatchPOList where itemDesc1 equals to UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldNotBeFound("itemDesc1.equals=" + UPDATED_ITEM_DESC_1);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 not equals to DEFAULT_ITEM_DESC_1
        defaultMMatchPOShouldNotBeFound("itemDesc1.notEquals=" + DEFAULT_ITEM_DESC_1);

        // Get all the mMatchPOList where itemDesc1 not equals to UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldBeFound("itemDesc1.notEquals=" + UPDATED_ITEM_DESC_1);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1IsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 in DEFAULT_ITEM_DESC_1 or UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldBeFound("itemDesc1.in=" + DEFAULT_ITEM_DESC_1 + "," + UPDATED_ITEM_DESC_1);

        // Get all the mMatchPOList where itemDesc1 equals to UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldNotBeFound("itemDesc1.in=" + UPDATED_ITEM_DESC_1);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1IsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 is not null
        defaultMMatchPOShouldBeFound("itemDesc1.specified=true");

        // Get all the mMatchPOList where itemDesc1 is null
        defaultMMatchPOShouldNotBeFound("itemDesc1.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1ContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 contains DEFAULT_ITEM_DESC_1
        defaultMMatchPOShouldBeFound("itemDesc1.contains=" + DEFAULT_ITEM_DESC_1);

        // Get all the mMatchPOList where itemDesc1 contains UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldNotBeFound("itemDesc1.contains=" + UPDATED_ITEM_DESC_1);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc1NotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc1 does not contain DEFAULT_ITEM_DESC_1
        defaultMMatchPOShouldNotBeFound("itemDesc1.doesNotContain=" + DEFAULT_ITEM_DESC_1);

        // Get all the mMatchPOList where itemDesc1 does not contain UPDATED_ITEM_DESC_1
        defaultMMatchPOShouldBeFound("itemDesc1.doesNotContain=" + UPDATED_ITEM_DESC_1);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2IsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 equals to DEFAULT_ITEM_DESC_2
        defaultMMatchPOShouldBeFound("itemDesc2.equals=" + DEFAULT_ITEM_DESC_2);

        // Get all the mMatchPOList where itemDesc2 equals to UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldNotBeFound("itemDesc2.equals=" + UPDATED_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 not equals to DEFAULT_ITEM_DESC_2
        defaultMMatchPOShouldNotBeFound("itemDesc2.notEquals=" + DEFAULT_ITEM_DESC_2);

        // Get all the mMatchPOList where itemDesc2 not equals to UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldBeFound("itemDesc2.notEquals=" + UPDATED_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2IsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 in DEFAULT_ITEM_DESC_2 or UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldBeFound("itemDesc2.in=" + DEFAULT_ITEM_DESC_2 + "," + UPDATED_ITEM_DESC_2);

        // Get all the mMatchPOList where itemDesc2 equals to UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldNotBeFound("itemDesc2.in=" + UPDATED_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2IsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 is not null
        defaultMMatchPOShouldBeFound("itemDesc2.specified=true");

        // Get all the mMatchPOList where itemDesc2 is null
        defaultMMatchPOShouldNotBeFound("itemDesc2.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2ContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 contains DEFAULT_ITEM_DESC_2
        defaultMMatchPOShouldBeFound("itemDesc2.contains=" + DEFAULT_ITEM_DESC_2);

        // Get all the mMatchPOList where itemDesc2 contains UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldNotBeFound("itemDesc2.contains=" + UPDATED_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByItemDesc2NotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where itemDesc2 does not contain DEFAULT_ITEM_DESC_2
        defaultMMatchPOShouldNotBeFound("itemDesc2.doesNotContain=" + DEFAULT_ITEM_DESC_2);

        // Get all the mMatchPOList where itemDesc2 does not contain UPDATED_ITEM_DESC_2
        defaultMMatchPOShouldBeFound("itemDesc2.doesNotContain=" + UPDATED_ITEM_DESC_2);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mMatchPO.getAdOrganization();
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mMatchPOList where adOrganization equals to adOrganizationId
        defaultMMatchPOShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mMatchPOList where adOrganization equals to adOrganizationId + 1
        defaultMMatchPOShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter cCostCenter = mMatchPO.getCCostCenter();
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cCostCenterId = cCostCenter.getId();

        // Get all the mMatchPOList where cCostCenter equals to cCostCenterId
        defaultMMatchPOShouldBeFound("cCostCenterId.equals=" + cCostCenterId);

        // Get all the mMatchPOList where cCostCenter equals to cCostCenterId + 1
        defaultMMatchPOShouldNotBeFound("cCostCenterId.equals=" + (cCostCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CVendor cVendor = CVendorResourceIT.createEntity(em);
        em.persist(cVendor);
        em.flush();
        mMatchPO.setCVendor(cVendor);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cVendorId = cVendor.getId();

        // Get all the mMatchPOList where cVendor equals to cVendorId
        defaultMMatchPOShouldBeFound("cVendorId.equals=" + cVendorId);

        // Get all the mMatchPOList where cVendor equals to cVendorId + 1
        defaultMMatchPOShouldNotBeFound("cVendorId.equals=" + (cVendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CCurrency cCurrency = CCurrencyResourceIT.createEntity(em);
        em.persist(cCurrency);
        em.flush();
        mMatchPO.setCCurrency(cCurrency);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cCurrencyId = cCurrency.getId();

        // Get all the mMatchPOList where cCurrency equals to cCurrencyId
        defaultMMatchPOShouldBeFound("cCurrencyId.equals=" + cCurrencyId);

        // Get all the mMatchPOList where cCurrency equals to cCurrencyId + 1
        defaultMMatchPOShouldNotBeFound("cCurrencyId.equals=" + (cCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCTaxCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CTaxCategory cTaxCategory = CTaxCategoryResourceIT.createEntity(em);
        em.persist(cTaxCategory);
        em.flush();
        mMatchPO.setCTaxCategory(cTaxCategory);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cTaxCategoryId = cTaxCategory.getId();

        // Get all the mMatchPOList where cTaxCategory equals to cTaxCategoryId
        defaultMMatchPOShouldBeFound("cTaxCategoryId.equals=" + cTaxCategoryId);

        // Get all the mMatchPOList where cTaxCategory equals to cTaxCategoryId + 1
        defaultMMatchPOShouldNotBeFound("cTaxCategoryId.equals=" + (cTaxCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CTax cTax = CTaxResourceIT.createEntity(em);
        em.persist(cTax);
        em.flush();
        mMatchPO.setCTax(cTax);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cTaxId = cTax.getId();

        // Get all the mMatchPOList where cTax equals to cTaxId
        defaultMMatchPOShouldBeFound("cTaxId.equals=" + cTaxId);

        // Get all the mMatchPOList where cTax equals to cTaxId + 1
        defaultMMatchPOShouldNotBeFound("cTaxId.equals=" + (cTaxId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByCUomIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CUnitOfMeasure cUom = CUnitOfMeasureResourceIT.createEntity(em);
        em.persist(cUom);
        em.flush();
        mMatchPO.setCUom(cUom);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long cUomId = cUom.getId();

        // Get all the mMatchPOList where cUom equals to cUomId
        defaultMMatchPOShouldBeFound("cUomId.equals=" + cUomId);

        // Get all the mMatchPOList where cUom equals to cUomId + 1
        defaultMMatchPOShouldNotBeFound("cUomId.equals=" + (cUomId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByMProductIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CProduct mProduct = CProductResourceIT.createEntity(em);
        em.persist(mProduct);
        em.flush();
        mMatchPO.setMProduct(mProduct);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long mProductId = mProduct.getId();

        // Get all the mMatchPOList where mProduct equals to mProductId
        defaultMMatchPOShouldBeFound("mProductId.equals=" + mProductId);

        // Get all the mMatchPOList where mProduct equals to mProductId + 1
        defaultMMatchPOShouldNotBeFound("mProductId.equals=" + (mProductId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByMWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CWarehouse mWarehouse = CWarehouseResourceIT.createEntity(em);
        em.persist(mWarehouse);
        em.flush();
        mMatchPO.setMWarehouse(mWarehouse);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long mWarehouseId = mWarehouse.getId();

        // Get all the mMatchPOList where mWarehouse equals to mWarehouseId
        defaultMMatchPOShouldBeFound("mWarehouseId.equals=" + mWarehouseId);

        // Get all the mMatchPOList where mWarehouse equals to mWarehouseId + 1
        defaultMMatchPOShouldNotBeFound("mWarehouseId.equals=" + (mWarehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByMLocatorIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);
        CLocator mLocator = CLocatorResourceIT.createEntity(em);
        em.persist(mLocator);
        em.flush();
        mMatchPO.setMLocator(mLocator);
        mMatchPORepository.saveAndFlush(mMatchPO);
        Long mLocatorId = mLocator.getId();

        // Get all the mMatchPOList where mLocator equals to mLocatorId
        defaultMMatchPOShouldBeFound("mLocatorId.equals=" + mLocatorId);

        // Get all the mMatchPOList where mLocator equals to mLocatorId + 1
        defaultMMatchPOShouldNotBeFound("mLocatorId.equals=" + (mLocatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMMatchPOShouldBeFound(String filter) throws Exception {
        restMMatchPOMockMvc.perform(get("/api/m-match-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMatchPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].cDocType").value(hasItem(DEFAULT_C_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].cConversionRate").value(hasItem(DEFAULT_C_CONVERSION_RATE.intValue())))
            .andExpect(jsonPath("$.[*].openQty").value(hasItem(DEFAULT_OPEN_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].openAmount").value(hasItem(DEFAULT_OPEN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].openForeignAmount").value(hasItem(DEFAULT_OPEN_FOREIGN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].dateAccount").value(hasItem(DEFAULT_DATE_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].cDocTypeMr").value(hasItem(DEFAULT_C_DOC_TYPE_MR)))
            .andExpect(jsonPath("$.[*].orderSuffix").value(hasItem(DEFAULT_ORDER_SUFFIX)))
            .andExpect(jsonPath("$.[*].lineNoPo").value(hasItem(DEFAULT_LINE_NO_PO)))
            .andExpect(jsonPath("$.[*].lineNoMr").value(hasItem(DEFAULT_LINE_NO_MR)))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mMatchType").value(hasItem(DEFAULT_M_MATCH_TYPE)))
            .andExpect(jsonPath("$.[*].itemDesc1").value(hasItem(DEFAULT_ITEM_DESC_1)))
            .andExpect(jsonPath("$.[*].itemDesc2").value(hasItem(DEFAULT_ITEM_DESC_2)));

        // Check, that the count call also returns 1
        restMMatchPOMockMvc.perform(get("/api/m-match-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMMatchPOShouldNotBeFound(String filter) throws Exception {
        restMMatchPOMockMvc.perform(get("/api/m-match-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMMatchPOMockMvc.perform(get("/api/m-match-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMMatchPO() throws Exception {
        // Get the mMatchPO
        restMMatchPOMockMvc.perform(get("/api/m-match-pos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMMatchPO() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        int databaseSizeBeforeUpdate = mMatchPORepository.findAll().size();

        // Update the mMatchPO
        MMatchPO updatedMMatchPO = mMatchPORepository.findById(mMatchPO.getId()).get();
        // Disconnect from session so that the updates on updatedMMatchPO are not directly saved in db
        em.detach(updatedMMatchPO);
        updatedMMatchPO
            .deliveryNo(UPDATED_DELIVERY_NO)
            .cDocType(UPDATED_C_DOC_TYPE)
            .poNo(UPDATED_PO_NO)
            .poDate(UPDATED_PO_DATE)
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .qty(UPDATED_QTY)
            .cConversionRate(UPDATED_C_CONVERSION_RATE)
            .openQty(UPDATED_OPEN_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .openAmount(UPDATED_OPEN_AMOUNT)
            .openForeignAmount(UPDATED_OPEN_FOREIGN_AMOUNT)
            .totalLines(UPDATED_TOTAL_LINES)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .dateAccount(UPDATED_DATE_ACCOUNT)
            .cDocTypeMr(UPDATED_C_DOC_TYPE_MR)
            .orderSuffix(UPDATED_ORDER_SUFFIX)
            .lineNoPo(UPDATED_LINE_NO_PO)
            .lineNoMr(UPDATED_LINE_NO_MR)
            .taxable(UPDATED_TAXABLE)
            .description(UPDATED_DESCRIPTION)
            .mMatchType(UPDATED_M_MATCH_TYPE)
            .itemDesc1(UPDATED_ITEM_DESC_1)
            .itemDesc2(UPDATED_ITEM_DESC_2);
        MMatchPODTO mMatchPODTO = mMatchPOMapper.toDto(updatedMMatchPO);

        restMMatchPOMockMvc.perform(put("/api/m-match-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mMatchPODTO)))
            .andExpect(status().isOk());

        // Validate the MMatchPO in the database
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeUpdate);
        MMatchPO testMMatchPO = mMatchPOList.get(mMatchPOList.size() - 1);
        assertThat(testMMatchPO.getDeliveryNo()).isEqualTo(UPDATED_DELIVERY_NO);
        assertThat(testMMatchPO.getcDocType()).isEqualTo(UPDATED_C_DOC_TYPE);
        assertThat(testMMatchPO.getPoNo()).isEqualTo(UPDATED_PO_NO);
        assertThat(testMMatchPO.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testMMatchPO.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testMMatchPO.getReceiptDate()).isEqualTo(UPDATED_RECEIPT_DATE);
        assertThat(testMMatchPO.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMMatchPO.getcConversionRate()).isEqualTo(UPDATED_C_CONVERSION_RATE);
        assertThat(testMMatchPO.getOpenQty()).isEqualTo(UPDATED_OPEN_QTY);
        assertThat(testMMatchPO.getPriceActual()).isEqualTo(UPDATED_PRICE_ACTUAL);
        assertThat(testMMatchPO.getForeignActual()).isEqualTo(UPDATED_FOREIGN_ACTUAL);
        assertThat(testMMatchPO.getOpenAmount()).isEqualTo(UPDATED_OPEN_AMOUNT);
        assertThat(testMMatchPO.getOpenForeignAmount()).isEqualTo(UPDATED_OPEN_FOREIGN_AMOUNT);
        assertThat(testMMatchPO.getTotalLines()).isEqualTo(UPDATED_TOTAL_LINES);
        assertThat(testMMatchPO.getForeignTotalLines()).isEqualTo(UPDATED_FOREIGN_TOTAL_LINES);
        assertThat(testMMatchPO.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testMMatchPO.getForeignTaxAmount()).isEqualTo(UPDATED_FOREIGN_TAX_AMOUNT);
        assertThat(testMMatchPO.getDateAccount()).isEqualTo(UPDATED_DATE_ACCOUNT);
        assertThat(testMMatchPO.getcDocTypeMr()).isEqualTo(UPDATED_C_DOC_TYPE_MR);
        assertThat(testMMatchPO.getOrderSuffix()).isEqualTo(UPDATED_ORDER_SUFFIX);
        assertThat(testMMatchPO.getLineNoPo()).isEqualTo(UPDATED_LINE_NO_PO);
        assertThat(testMMatchPO.getLineNoMr()).isEqualTo(UPDATED_LINE_NO_MR);
        assertThat(testMMatchPO.isTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testMMatchPO.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMMatchPO.getmMatchType()).isEqualTo(UPDATED_M_MATCH_TYPE);
        assertThat(testMMatchPO.getItemDesc1()).isEqualTo(UPDATED_ITEM_DESC_1);
        assertThat(testMMatchPO.getItemDesc2()).isEqualTo(UPDATED_ITEM_DESC_2);
    }

    @Test
    @Transactional
    public void updateNonExistingMMatchPO() throws Exception {
        int databaseSizeBeforeUpdate = mMatchPORepository.findAll().size();

        // Create the MMatchPO
        MMatchPODTO mMatchPODTO = mMatchPOMapper.toDto(mMatchPO);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMatchPOMockMvc.perform(put("/api/m-match-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mMatchPODTO)))
            .andExpect(status().isBadRequest());

        // Validate the MMatchPO in the database
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMMatchPO() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        int databaseSizeBeforeDelete = mMatchPORepository.findAll().size();

        // Delete the mMatchPO
        restMMatchPOMockMvc.perform(delete("/api/m-match-pos/{id}", mMatchPO.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
