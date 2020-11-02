package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MMatchPO;
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

    private static final String DEFAULT_C_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_C_DOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_C_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_C_VENDOR = "BBBBBBBBBB";

    private static final String DEFAULT_C_ELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_C_ELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_C_COST_CENTER = "AAAAAAAAAA";
    private static final String UPDATED_C_COST_CENTER = "BBBBBBBBBB";

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

    private static final String DEFAULT_DELIVERY_NO = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_NO = "BBBBBBBBBB";

    private static final String DEFAULT_M_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_M_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_M_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_M_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_M_PRODUCT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_M_PRODUCT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_C_UOM = "AAAAAAAAAA";
    private static final String UPDATED_C_UOM = "BBBBBBBBBB";

    private static final String DEFAULT_QTY = "AAAAAAAAAA";
    private static final String UPDATED_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_C_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_C_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_C_CONVERSION_RATE = "AAAAAAAAAA";
    private static final String UPDATED_C_CONVERSION_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_OPEN_QTY = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE_ACTUAL = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_ACTUAL = "BBBBBBBBBB";

    private static final String DEFAULT_FOREIGN_ACTUAL = "AAAAAAAAAA";
    private static final String UPDATED_FOREIGN_ACTUAL = "BBBBBBBBBB";

    private static final String DEFAULT_OPEN_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_OPEN_FOREIGN_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_FOREIGN_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_LINES = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_LINES = "BBBBBBBBBB";

    private static final String DEFAULT_FOREIGN_TOTAL_LINES = "AAAAAAAAAA";
    private static final String UPDATED_FOREIGN_TOTAL_LINES = "BBBBBBBBBB";

    private static final String DEFAULT_C_TAX = "AAAAAAAAAA";
    private static final String UPDATED_C_TAX = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TAX_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_FOREIGN_TAX_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_FOREIGN_TAX_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_M_LOCATOR = "AAAAAAAAAA";
    private static final String UPDATED_M_LOCATOR = "BBBBBBBBBB";

    private static final String DEFAULT_AD_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_AD_ORGANIZATION = "BBBBBBBBBB";

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
            .cDocType(DEFAULT_C_DOC_TYPE)
            .cVendor(DEFAULT_C_VENDOR)
            .cElement(DEFAULT_C_ELEMENT)
            .cCostCenter(DEFAULT_C_COST_CENTER)
            .poNo(DEFAULT_PO_NO)
            .poDate(DEFAULT_PO_DATE)
            .receiptNo(DEFAULT_RECEIPT_NO)
            .receiptDate(DEFAULT_RECEIPT_DATE)
            .deliveryNo(DEFAULT_DELIVERY_NO)
            .mProductCode(DEFAULT_M_PRODUCT_CODE)
            .mProductName(DEFAULT_M_PRODUCT_NAME)
            .mProductDesc(DEFAULT_M_PRODUCT_DESC)
            .cUOM(DEFAULT_C_UOM)
            .qty(DEFAULT_QTY)
            .cCurrency(DEFAULT_C_CURRENCY)
            .cConversionRate(DEFAULT_C_CONVERSION_RATE)
            .openQty(DEFAULT_OPEN_QTY)
            .priceActual(DEFAULT_PRICE_ACTUAL)
            .foreignActual(DEFAULT_FOREIGN_ACTUAL)
            .openAmount(DEFAULT_OPEN_AMOUNT)
            .openForeignAmount(DEFAULT_OPEN_FOREIGN_AMOUNT)
            .totalLines(DEFAULT_TOTAL_LINES)
            .foreignTotalLines(DEFAULT_FOREIGN_TOTAL_LINES)
            .cTax(DEFAULT_C_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .foreignTaxAmount(DEFAULT_FOREIGN_TAX_AMOUNT)
            .mLocator(DEFAULT_M_LOCATOR)
            .adOrganization(DEFAULT_AD_ORGANIZATION);
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
            .cDocType(UPDATED_C_DOC_TYPE)
            .cVendor(UPDATED_C_VENDOR)
            .cElement(UPDATED_C_ELEMENT)
            .cCostCenter(UPDATED_C_COST_CENTER)
            .poNo(UPDATED_PO_NO)
            .poDate(UPDATED_PO_DATE)
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .mProductCode(UPDATED_M_PRODUCT_CODE)
            .mProductName(UPDATED_M_PRODUCT_NAME)
            .mProductDesc(UPDATED_M_PRODUCT_DESC)
            .cUOM(UPDATED_C_UOM)
            .qty(UPDATED_QTY)
            .cCurrency(UPDATED_C_CURRENCY)
            .cConversionRate(UPDATED_C_CONVERSION_RATE)
            .openQty(UPDATED_OPEN_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .openAmount(UPDATED_OPEN_AMOUNT)
            .openForeignAmount(UPDATED_OPEN_FOREIGN_AMOUNT)
            .totalLines(UPDATED_TOTAL_LINES)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .cTax(UPDATED_C_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .mLocator(UPDATED_M_LOCATOR)
            .adOrganization(UPDATED_AD_ORGANIZATION);
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
        assertThat(testMMatchPO.getcDocType()).isEqualTo(DEFAULT_C_DOC_TYPE);
        assertThat(testMMatchPO.getcVendor()).isEqualTo(DEFAULT_C_VENDOR);
        assertThat(testMMatchPO.getcElement()).isEqualTo(DEFAULT_C_ELEMENT);
        assertThat(testMMatchPO.getcCostCenter()).isEqualTo(DEFAULT_C_COST_CENTER);
        assertThat(testMMatchPO.getPoNo()).isEqualTo(DEFAULT_PO_NO);
        assertThat(testMMatchPO.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testMMatchPO.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testMMatchPO.getReceiptDate()).isEqualTo(DEFAULT_RECEIPT_DATE);
        assertThat(testMMatchPO.getDeliveryNo()).isEqualTo(DEFAULT_DELIVERY_NO);
        assertThat(testMMatchPO.getmProductCode()).isEqualTo(DEFAULT_M_PRODUCT_CODE);
        assertThat(testMMatchPO.getmProductName()).isEqualTo(DEFAULT_M_PRODUCT_NAME);
        assertThat(testMMatchPO.getmProductDesc()).isEqualTo(DEFAULT_M_PRODUCT_DESC);
        assertThat(testMMatchPO.getcUOM()).isEqualTo(DEFAULT_C_UOM);
        assertThat(testMMatchPO.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMMatchPO.getcCurrency()).isEqualTo(DEFAULT_C_CURRENCY);
        assertThat(testMMatchPO.getcConversionRate()).isEqualTo(DEFAULT_C_CONVERSION_RATE);
        assertThat(testMMatchPO.getOpenQty()).isEqualTo(DEFAULT_OPEN_QTY);
        assertThat(testMMatchPO.getPriceActual()).isEqualTo(DEFAULT_PRICE_ACTUAL);
        assertThat(testMMatchPO.getForeignActual()).isEqualTo(DEFAULT_FOREIGN_ACTUAL);
        assertThat(testMMatchPO.getOpenAmount()).isEqualTo(DEFAULT_OPEN_AMOUNT);
        assertThat(testMMatchPO.getOpenForeignAmount()).isEqualTo(DEFAULT_OPEN_FOREIGN_AMOUNT);
        assertThat(testMMatchPO.getTotalLines()).isEqualTo(DEFAULT_TOTAL_LINES);
        assertThat(testMMatchPO.getForeignTotalLines()).isEqualTo(DEFAULT_FOREIGN_TOTAL_LINES);
        assertThat(testMMatchPO.getcTax()).isEqualTo(DEFAULT_C_TAX);
        assertThat(testMMatchPO.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testMMatchPO.getForeignTaxAmount()).isEqualTo(DEFAULT_FOREIGN_TAX_AMOUNT);
        assertThat(testMMatchPO.getmLocator()).isEqualTo(DEFAULT_M_LOCATOR);
        assertThat(testMMatchPO.getAdOrganization()).isEqualTo(DEFAULT_AD_ORGANIZATION);
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
            .andExpect(jsonPath("$.[*].cDocType").value(hasItem(DEFAULT_C_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].cVendor").value(hasItem(DEFAULT_C_VENDOR)))
            .andExpect(jsonPath("$.[*].cElement").value(hasItem(DEFAULT_C_ELEMENT)))
            .andExpect(jsonPath("$.[*].cCostCenter").value(hasItem(DEFAULT_C_COST_CENTER)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].mProductCode").value(hasItem(DEFAULT_M_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].mProductName").value(hasItem(DEFAULT_M_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].mProductDesc").value(hasItem(DEFAULT_M_PRODUCT_DESC)))
            .andExpect(jsonPath("$.[*].cUOM").value(hasItem(DEFAULT_C_UOM)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].cCurrency").value(hasItem(DEFAULT_C_CURRENCY)))
            .andExpect(jsonPath("$.[*].cConversionRate").value(hasItem(DEFAULT_C_CONVERSION_RATE)))
            .andExpect(jsonPath("$.[*].openQty").value(hasItem(DEFAULT_OPEN_QTY)))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL)))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL)))
            .andExpect(jsonPath("$.[*].openAmount").value(hasItem(DEFAULT_OPEN_AMOUNT)))
            .andExpect(jsonPath("$.[*].openForeignAmount").value(hasItem(DEFAULT_OPEN_FOREIGN_AMOUNT)))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES)))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES)))
            .andExpect(jsonPath("$.[*].cTax").value(hasItem(DEFAULT_C_TAX)))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT)))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT)))
            .andExpect(jsonPath("$.[*].mLocator").value(hasItem(DEFAULT_M_LOCATOR)))
            .andExpect(jsonPath("$.[*].adOrganization").value(hasItem(DEFAULT_AD_ORGANIZATION)));
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
            .andExpect(jsonPath("$.cDocType").value(DEFAULT_C_DOC_TYPE))
            .andExpect(jsonPath("$.cVendor").value(DEFAULT_C_VENDOR))
            .andExpect(jsonPath("$.cElement").value(DEFAULT_C_ELEMENT))
            .andExpect(jsonPath("$.cCostCenter").value(DEFAULT_C_COST_CENTER))
            .andExpect(jsonPath("$.poNo").value(DEFAULT_PO_NO))
            .andExpect(jsonPath("$.poDate").value(DEFAULT_PO_DATE.toString()))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.receiptDate").value(DEFAULT_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.deliveryNo").value(DEFAULT_DELIVERY_NO))
            .andExpect(jsonPath("$.mProductCode").value(DEFAULT_M_PRODUCT_CODE))
            .andExpect(jsonPath("$.mProductName").value(DEFAULT_M_PRODUCT_NAME))
            .andExpect(jsonPath("$.mProductDesc").value(DEFAULT_M_PRODUCT_DESC))
            .andExpect(jsonPath("$.cUOM").value(DEFAULT_C_UOM))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.cCurrency").value(DEFAULT_C_CURRENCY))
            .andExpect(jsonPath("$.cConversionRate").value(DEFAULT_C_CONVERSION_RATE))
            .andExpect(jsonPath("$.openQty").value(DEFAULT_OPEN_QTY))
            .andExpect(jsonPath("$.priceActual").value(DEFAULT_PRICE_ACTUAL))
            .andExpect(jsonPath("$.foreignActual").value(DEFAULT_FOREIGN_ACTUAL))
            .andExpect(jsonPath("$.openAmount").value(DEFAULT_OPEN_AMOUNT))
            .andExpect(jsonPath("$.openForeignAmount").value(DEFAULT_OPEN_FOREIGN_AMOUNT))
            .andExpect(jsonPath("$.totalLines").value(DEFAULT_TOTAL_LINES))
            .andExpect(jsonPath("$.foreignTotalLines").value(DEFAULT_FOREIGN_TOTAL_LINES))
            .andExpect(jsonPath("$.cTax").value(DEFAULT_C_TAX))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT))
            .andExpect(jsonPath("$.foreignTaxAmount").value(DEFAULT_FOREIGN_TAX_AMOUNT))
            .andExpect(jsonPath("$.mLocator").value(DEFAULT_M_LOCATOR))
            .andExpect(jsonPath("$.adOrganization").value(DEFAULT_AD_ORGANIZATION));
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
    public void getAllMMatchPOSBycVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor equals to DEFAULT_C_VENDOR
        defaultMMatchPOShouldBeFound("cVendor.equals=" + DEFAULT_C_VENDOR);

        // Get all the mMatchPOList where cVendor equals to UPDATED_C_VENDOR
        defaultMMatchPOShouldNotBeFound("cVendor.equals=" + UPDATED_C_VENDOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycVendorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor not equals to DEFAULT_C_VENDOR
        defaultMMatchPOShouldNotBeFound("cVendor.notEquals=" + DEFAULT_C_VENDOR);

        // Get all the mMatchPOList where cVendor not equals to UPDATED_C_VENDOR
        defaultMMatchPOShouldBeFound("cVendor.notEquals=" + UPDATED_C_VENDOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycVendorIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor in DEFAULT_C_VENDOR or UPDATED_C_VENDOR
        defaultMMatchPOShouldBeFound("cVendor.in=" + DEFAULT_C_VENDOR + "," + UPDATED_C_VENDOR);

        // Get all the mMatchPOList where cVendor equals to UPDATED_C_VENDOR
        defaultMMatchPOShouldNotBeFound("cVendor.in=" + UPDATED_C_VENDOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycVendorIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor is not null
        defaultMMatchPOShouldBeFound("cVendor.specified=true");

        // Get all the mMatchPOList where cVendor is null
        defaultMMatchPOShouldNotBeFound("cVendor.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycVendorContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor contains DEFAULT_C_VENDOR
        defaultMMatchPOShouldBeFound("cVendor.contains=" + DEFAULT_C_VENDOR);

        // Get all the mMatchPOList where cVendor contains UPDATED_C_VENDOR
        defaultMMatchPOShouldNotBeFound("cVendor.contains=" + UPDATED_C_VENDOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycVendorNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cVendor does not contain DEFAULT_C_VENDOR
        defaultMMatchPOShouldNotBeFound("cVendor.doesNotContain=" + DEFAULT_C_VENDOR);

        // Get all the mMatchPOList where cVendor does not contain UPDATED_C_VENDOR
        defaultMMatchPOShouldBeFound("cVendor.doesNotContain=" + UPDATED_C_VENDOR);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycElementIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement equals to DEFAULT_C_ELEMENT
        defaultMMatchPOShouldBeFound("cElement.equals=" + DEFAULT_C_ELEMENT);

        // Get all the mMatchPOList where cElement equals to UPDATED_C_ELEMENT
        defaultMMatchPOShouldNotBeFound("cElement.equals=" + UPDATED_C_ELEMENT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycElementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement not equals to DEFAULT_C_ELEMENT
        defaultMMatchPOShouldNotBeFound("cElement.notEquals=" + DEFAULT_C_ELEMENT);

        // Get all the mMatchPOList where cElement not equals to UPDATED_C_ELEMENT
        defaultMMatchPOShouldBeFound("cElement.notEquals=" + UPDATED_C_ELEMENT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycElementIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement in DEFAULT_C_ELEMENT or UPDATED_C_ELEMENT
        defaultMMatchPOShouldBeFound("cElement.in=" + DEFAULT_C_ELEMENT + "," + UPDATED_C_ELEMENT);

        // Get all the mMatchPOList where cElement equals to UPDATED_C_ELEMENT
        defaultMMatchPOShouldNotBeFound("cElement.in=" + UPDATED_C_ELEMENT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycElementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement is not null
        defaultMMatchPOShouldBeFound("cElement.specified=true");

        // Get all the mMatchPOList where cElement is null
        defaultMMatchPOShouldNotBeFound("cElement.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycElementContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement contains DEFAULT_C_ELEMENT
        defaultMMatchPOShouldBeFound("cElement.contains=" + DEFAULT_C_ELEMENT);

        // Get all the mMatchPOList where cElement contains UPDATED_C_ELEMENT
        defaultMMatchPOShouldNotBeFound("cElement.contains=" + UPDATED_C_ELEMENT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycElementNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cElement does not contain DEFAULT_C_ELEMENT
        defaultMMatchPOShouldNotBeFound("cElement.doesNotContain=" + DEFAULT_C_ELEMENT);

        // Get all the mMatchPOList where cElement does not contain UPDATED_C_ELEMENT
        defaultMMatchPOShouldBeFound("cElement.doesNotContain=" + UPDATED_C_ELEMENT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter equals to DEFAULT_C_COST_CENTER
        defaultMMatchPOShouldBeFound("cCostCenter.equals=" + DEFAULT_C_COST_CENTER);

        // Get all the mMatchPOList where cCostCenter equals to UPDATED_C_COST_CENTER
        defaultMMatchPOShouldNotBeFound("cCostCenter.equals=" + UPDATED_C_COST_CENTER);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter not equals to DEFAULT_C_COST_CENTER
        defaultMMatchPOShouldNotBeFound("cCostCenter.notEquals=" + DEFAULT_C_COST_CENTER);

        // Get all the mMatchPOList where cCostCenter not equals to UPDATED_C_COST_CENTER
        defaultMMatchPOShouldBeFound("cCostCenter.notEquals=" + UPDATED_C_COST_CENTER);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter in DEFAULT_C_COST_CENTER or UPDATED_C_COST_CENTER
        defaultMMatchPOShouldBeFound("cCostCenter.in=" + DEFAULT_C_COST_CENTER + "," + UPDATED_C_COST_CENTER);

        // Get all the mMatchPOList where cCostCenter equals to UPDATED_C_COST_CENTER
        defaultMMatchPOShouldNotBeFound("cCostCenter.in=" + UPDATED_C_COST_CENTER);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter is not null
        defaultMMatchPOShouldBeFound("cCostCenter.specified=true");

        // Get all the mMatchPOList where cCostCenter is null
        defaultMMatchPOShouldNotBeFound("cCostCenter.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter contains DEFAULT_C_COST_CENTER
        defaultMMatchPOShouldBeFound("cCostCenter.contains=" + DEFAULT_C_COST_CENTER);

        // Get all the mMatchPOList where cCostCenter contains UPDATED_C_COST_CENTER
        defaultMMatchPOShouldNotBeFound("cCostCenter.contains=" + UPDATED_C_COST_CENTER);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCostCenterNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCostCenter does not contain DEFAULT_C_COST_CENTER
        defaultMMatchPOShouldNotBeFound("cCostCenter.doesNotContain=" + DEFAULT_C_COST_CENTER);

        // Get all the mMatchPOList where cCostCenter does not contain UPDATED_C_COST_CENTER
        defaultMMatchPOShouldBeFound("cCostCenter.doesNotContain=" + UPDATED_C_COST_CENTER);
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
    public void getAllMMatchPOSBymProductCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode equals to DEFAULT_M_PRODUCT_CODE
        defaultMMatchPOShouldBeFound("mProductCode.equals=" + DEFAULT_M_PRODUCT_CODE);

        // Get all the mMatchPOList where mProductCode equals to UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldNotBeFound("mProductCode.equals=" + UPDATED_M_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode not equals to DEFAULT_M_PRODUCT_CODE
        defaultMMatchPOShouldNotBeFound("mProductCode.notEquals=" + DEFAULT_M_PRODUCT_CODE);

        // Get all the mMatchPOList where mProductCode not equals to UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldBeFound("mProductCode.notEquals=" + UPDATED_M_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode in DEFAULT_M_PRODUCT_CODE or UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldBeFound("mProductCode.in=" + DEFAULT_M_PRODUCT_CODE + "," + UPDATED_M_PRODUCT_CODE);

        // Get all the mMatchPOList where mProductCode equals to UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldNotBeFound("mProductCode.in=" + UPDATED_M_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode is not null
        defaultMMatchPOShouldBeFound("mProductCode.specified=true");

        // Get all the mMatchPOList where mProductCode is null
        defaultMMatchPOShouldNotBeFound("mProductCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBymProductCodeContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode contains DEFAULT_M_PRODUCT_CODE
        defaultMMatchPOShouldBeFound("mProductCode.contains=" + DEFAULT_M_PRODUCT_CODE);

        // Get all the mMatchPOList where mProductCode contains UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldNotBeFound("mProductCode.contains=" + UPDATED_M_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductCode does not contain DEFAULT_M_PRODUCT_CODE
        defaultMMatchPOShouldNotBeFound("mProductCode.doesNotContain=" + DEFAULT_M_PRODUCT_CODE);

        // Get all the mMatchPOList where mProductCode does not contain UPDATED_M_PRODUCT_CODE
        defaultMMatchPOShouldBeFound("mProductCode.doesNotContain=" + UPDATED_M_PRODUCT_CODE);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName equals to DEFAULT_M_PRODUCT_NAME
        defaultMMatchPOShouldBeFound("mProductName.equals=" + DEFAULT_M_PRODUCT_NAME);

        // Get all the mMatchPOList where mProductName equals to UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldNotBeFound("mProductName.equals=" + UPDATED_M_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName not equals to DEFAULT_M_PRODUCT_NAME
        defaultMMatchPOShouldNotBeFound("mProductName.notEquals=" + DEFAULT_M_PRODUCT_NAME);

        // Get all the mMatchPOList where mProductName not equals to UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldBeFound("mProductName.notEquals=" + UPDATED_M_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName in DEFAULT_M_PRODUCT_NAME or UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldBeFound("mProductName.in=" + DEFAULT_M_PRODUCT_NAME + "," + UPDATED_M_PRODUCT_NAME);

        // Get all the mMatchPOList where mProductName equals to UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldNotBeFound("mProductName.in=" + UPDATED_M_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName is not null
        defaultMMatchPOShouldBeFound("mProductName.specified=true");

        // Get all the mMatchPOList where mProductName is null
        defaultMMatchPOShouldNotBeFound("mProductName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName contains DEFAULT_M_PRODUCT_NAME
        defaultMMatchPOShouldBeFound("mProductName.contains=" + DEFAULT_M_PRODUCT_NAME);

        // Get all the mMatchPOList where mProductName contains UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldNotBeFound("mProductName.contains=" + UPDATED_M_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductName does not contain DEFAULT_M_PRODUCT_NAME
        defaultMMatchPOShouldNotBeFound("mProductName.doesNotContain=" + DEFAULT_M_PRODUCT_NAME);

        // Get all the mMatchPOList where mProductName does not contain UPDATED_M_PRODUCT_NAME
        defaultMMatchPOShouldBeFound("mProductName.doesNotContain=" + UPDATED_M_PRODUCT_NAME);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc equals to DEFAULT_M_PRODUCT_DESC
        defaultMMatchPOShouldBeFound("mProductDesc.equals=" + DEFAULT_M_PRODUCT_DESC);

        // Get all the mMatchPOList where mProductDesc equals to UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldNotBeFound("mProductDesc.equals=" + UPDATED_M_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc not equals to DEFAULT_M_PRODUCT_DESC
        defaultMMatchPOShouldNotBeFound("mProductDesc.notEquals=" + DEFAULT_M_PRODUCT_DESC);

        // Get all the mMatchPOList where mProductDesc not equals to UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldBeFound("mProductDesc.notEquals=" + UPDATED_M_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc in DEFAULT_M_PRODUCT_DESC or UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldBeFound("mProductDesc.in=" + DEFAULT_M_PRODUCT_DESC + "," + UPDATED_M_PRODUCT_DESC);

        // Get all the mMatchPOList where mProductDesc equals to UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldNotBeFound("mProductDesc.in=" + UPDATED_M_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc is not null
        defaultMMatchPOShouldBeFound("mProductDesc.specified=true");

        // Get all the mMatchPOList where mProductDesc is null
        defaultMMatchPOShouldNotBeFound("mProductDesc.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc contains DEFAULT_M_PRODUCT_DESC
        defaultMMatchPOShouldBeFound("mProductDesc.contains=" + DEFAULT_M_PRODUCT_DESC);

        // Get all the mMatchPOList where mProductDesc contains UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldNotBeFound("mProductDesc.contains=" + UPDATED_M_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymProductDescNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mProductDesc does not contain DEFAULT_M_PRODUCT_DESC
        defaultMMatchPOShouldNotBeFound("mProductDesc.doesNotContain=" + DEFAULT_M_PRODUCT_DESC);

        // Get all the mMatchPOList where mProductDesc does not contain UPDATED_M_PRODUCT_DESC
        defaultMMatchPOShouldBeFound("mProductDesc.doesNotContain=" + UPDATED_M_PRODUCT_DESC);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycUOMIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM equals to DEFAULT_C_UOM
        defaultMMatchPOShouldBeFound("cUOM.equals=" + DEFAULT_C_UOM);

        // Get all the mMatchPOList where cUOM equals to UPDATED_C_UOM
        defaultMMatchPOShouldNotBeFound("cUOM.equals=" + UPDATED_C_UOM);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycUOMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM not equals to DEFAULT_C_UOM
        defaultMMatchPOShouldNotBeFound("cUOM.notEquals=" + DEFAULT_C_UOM);

        // Get all the mMatchPOList where cUOM not equals to UPDATED_C_UOM
        defaultMMatchPOShouldBeFound("cUOM.notEquals=" + UPDATED_C_UOM);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycUOMIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM in DEFAULT_C_UOM or UPDATED_C_UOM
        defaultMMatchPOShouldBeFound("cUOM.in=" + DEFAULT_C_UOM + "," + UPDATED_C_UOM);

        // Get all the mMatchPOList where cUOM equals to UPDATED_C_UOM
        defaultMMatchPOShouldNotBeFound("cUOM.in=" + UPDATED_C_UOM);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycUOMIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM is not null
        defaultMMatchPOShouldBeFound("cUOM.specified=true");

        // Get all the mMatchPOList where cUOM is null
        defaultMMatchPOShouldNotBeFound("cUOM.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycUOMContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM contains DEFAULT_C_UOM
        defaultMMatchPOShouldBeFound("cUOM.contains=" + DEFAULT_C_UOM);

        // Get all the mMatchPOList where cUOM contains UPDATED_C_UOM
        defaultMMatchPOShouldNotBeFound("cUOM.contains=" + UPDATED_C_UOM);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycUOMNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cUOM does not contain DEFAULT_C_UOM
        defaultMMatchPOShouldNotBeFound("cUOM.doesNotContain=" + DEFAULT_C_UOM);

        // Get all the mMatchPOList where cUOM does not contain UPDATED_C_UOM
        defaultMMatchPOShouldBeFound("cUOM.doesNotContain=" + UPDATED_C_UOM);
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
    public void getAllMMatchPOSByQtyContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty contains DEFAULT_QTY
        defaultMMatchPOShouldBeFound("qty.contains=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty contains UPDATED_QTY
        defaultMMatchPOShouldNotBeFound("qty.contains=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByQtyNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where qty does not contain DEFAULT_QTY
        defaultMMatchPOShouldNotBeFound("qty.doesNotContain=" + DEFAULT_QTY);

        // Get all the mMatchPOList where qty does not contain UPDATED_QTY
        defaultMMatchPOShouldBeFound("qty.doesNotContain=" + UPDATED_QTY);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency equals to DEFAULT_C_CURRENCY
        defaultMMatchPOShouldBeFound("cCurrency.equals=" + DEFAULT_C_CURRENCY);

        // Get all the mMatchPOList where cCurrency equals to UPDATED_C_CURRENCY
        defaultMMatchPOShouldNotBeFound("cCurrency.equals=" + UPDATED_C_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency not equals to DEFAULT_C_CURRENCY
        defaultMMatchPOShouldNotBeFound("cCurrency.notEquals=" + DEFAULT_C_CURRENCY);

        // Get all the mMatchPOList where cCurrency not equals to UPDATED_C_CURRENCY
        defaultMMatchPOShouldBeFound("cCurrency.notEquals=" + UPDATED_C_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency in DEFAULT_C_CURRENCY or UPDATED_C_CURRENCY
        defaultMMatchPOShouldBeFound("cCurrency.in=" + DEFAULT_C_CURRENCY + "," + UPDATED_C_CURRENCY);

        // Get all the mMatchPOList where cCurrency equals to UPDATED_C_CURRENCY
        defaultMMatchPOShouldNotBeFound("cCurrency.in=" + UPDATED_C_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency is not null
        defaultMMatchPOShouldBeFound("cCurrency.specified=true");

        // Get all the mMatchPOList where cCurrency is null
        defaultMMatchPOShouldNotBeFound("cCurrency.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency contains DEFAULT_C_CURRENCY
        defaultMMatchPOShouldBeFound("cCurrency.contains=" + DEFAULT_C_CURRENCY);

        // Get all the mMatchPOList where cCurrency contains UPDATED_C_CURRENCY
        defaultMMatchPOShouldNotBeFound("cCurrency.contains=" + UPDATED_C_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycCurrencyNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cCurrency does not contain DEFAULT_C_CURRENCY
        defaultMMatchPOShouldNotBeFound("cCurrency.doesNotContain=" + DEFAULT_C_CURRENCY);

        // Get all the mMatchPOList where cCurrency does not contain UPDATED_C_CURRENCY
        defaultMMatchPOShouldBeFound("cCurrency.doesNotContain=" + UPDATED_C_CURRENCY);
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
    public void getAllMMatchPOSBycConversionRateContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate contains DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.contains=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate contains UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.contains=" + UPDATED_C_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycConversionRateNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cConversionRate does not contain DEFAULT_C_CONVERSION_RATE
        defaultMMatchPOShouldNotBeFound("cConversionRate.doesNotContain=" + DEFAULT_C_CONVERSION_RATE);

        // Get all the mMatchPOList where cConversionRate does not contain UPDATED_C_CONVERSION_RATE
        defaultMMatchPOShouldBeFound("cConversionRate.doesNotContain=" + UPDATED_C_CONVERSION_RATE);
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
    public void getAllMMatchPOSByOpenQtyContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty contains DEFAULT_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.contains=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty contains UPDATED_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.contains=" + UPDATED_OPEN_QTY);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenQtyNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openQty does not contain DEFAULT_OPEN_QTY
        defaultMMatchPOShouldNotBeFound("openQty.doesNotContain=" + DEFAULT_OPEN_QTY);

        // Get all the mMatchPOList where openQty does not contain UPDATED_OPEN_QTY
        defaultMMatchPOShouldBeFound("openQty.doesNotContain=" + UPDATED_OPEN_QTY);
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
    public void getAllMMatchPOSByPriceActualContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual contains DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.contains=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual contains UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.contains=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByPriceActualNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where priceActual does not contain DEFAULT_PRICE_ACTUAL
        defaultMMatchPOShouldNotBeFound("priceActual.doesNotContain=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mMatchPOList where priceActual does not contain UPDATED_PRICE_ACTUAL
        defaultMMatchPOShouldBeFound("priceActual.doesNotContain=" + UPDATED_PRICE_ACTUAL);
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
    public void getAllMMatchPOSByForeignActualContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual contains DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.contains=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual contains UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.contains=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignActualNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignActual does not contain DEFAULT_FOREIGN_ACTUAL
        defaultMMatchPOShouldNotBeFound("foreignActual.doesNotContain=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mMatchPOList where foreignActual does not contain UPDATED_FOREIGN_ACTUAL
        defaultMMatchPOShouldBeFound("foreignActual.doesNotContain=" + UPDATED_FOREIGN_ACTUAL);
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
    public void getAllMMatchPOSByOpenAmountContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount contains DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.contains=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount contains UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.contains=" + UPDATED_OPEN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenAmountNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openAmount does not contain DEFAULT_OPEN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openAmount.doesNotContain=" + DEFAULT_OPEN_AMOUNT);

        // Get all the mMatchPOList where openAmount does not contain UPDATED_OPEN_AMOUNT
        defaultMMatchPOShouldBeFound("openAmount.doesNotContain=" + UPDATED_OPEN_AMOUNT);
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
    public void getAllMMatchPOSByOpenForeignAmountContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount contains DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.contains=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount contains UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.contains=" + UPDATED_OPEN_FOREIGN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByOpenForeignAmountNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where openForeignAmount does not contain DEFAULT_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldNotBeFound("openForeignAmount.doesNotContain=" + DEFAULT_OPEN_FOREIGN_AMOUNT);

        // Get all the mMatchPOList where openForeignAmount does not contain UPDATED_OPEN_FOREIGN_AMOUNT
        defaultMMatchPOShouldBeFound("openForeignAmount.doesNotContain=" + UPDATED_OPEN_FOREIGN_AMOUNT);
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
    public void getAllMMatchPOSByTotalLinesContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines contains DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.contains=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines contains UPDATED_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.contains=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTotalLinesNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where totalLines does not contain DEFAULT_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("totalLines.doesNotContain=" + DEFAULT_TOTAL_LINES);

        // Get all the mMatchPOList where totalLines does not contain UPDATED_TOTAL_LINES
        defaultMMatchPOShouldBeFound("totalLines.doesNotContain=" + UPDATED_TOTAL_LINES);
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
    public void getAllMMatchPOSByForeignTotalLinesContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines contains DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.contains=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines contains UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.contains=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTotalLinesNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTotalLines does not contain DEFAULT_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldNotBeFound("foreignTotalLines.doesNotContain=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mMatchPOList where foreignTotalLines does not contain UPDATED_FOREIGN_TOTAL_LINES
        defaultMMatchPOShouldBeFound("foreignTotalLines.doesNotContain=" + UPDATED_FOREIGN_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBycTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax equals to DEFAULT_C_TAX
        defaultMMatchPOShouldBeFound("cTax.equals=" + DEFAULT_C_TAX);

        // Get all the mMatchPOList where cTax equals to UPDATED_C_TAX
        defaultMMatchPOShouldNotBeFound("cTax.equals=" + UPDATED_C_TAX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax not equals to DEFAULT_C_TAX
        defaultMMatchPOShouldNotBeFound("cTax.notEquals=" + DEFAULT_C_TAX);

        // Get all the mMatchPOList where cTax not equals to UPDATED_C_TAX
        defaultMMatchPOShouldBeFound("cTax.notEquals=" + UPDATED_C_TAX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycTaxIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax in DEFAULT_C_TAX or UPDATED_C_TAX
        defaultMMatchPOShouldBeFound("cTax.in=" + DEFAULT_C_TAX + "," + UPDATED_C_TAX);

        // Get all the mMatchPOList where cTax equals to UPDATED_C_TAX
        defaultMMatchPOShouldNotBeFound("cTax.in=" + UPDATED_C_TAX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax is not null
        defaultMMatchPOShouldBeFound("cTax.specified=true");

        // Get all the mMatchPOList where cTax is null
        defaultMMatchPOShouldNotBeFound("cTax.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBycTaxContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax contains DEFAULT_C_TAX
        defaultMMatchPOShouldBeFound("cTax.contains=" + DEFAULT_C_TAX);

        // Get all the mMatchPOList where cTax contains UPDATED_C_TAX
        defaultMMatchPOShouldNotBeFound("cTax.contains=" + UPDATED_C_TAX);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBycTaxNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where cTax does not contain DEFAULT_C_TAX
        defaultMMatchPOShouldNotBeFound("cTax.doesNotContain=" + DEFAULT_C_TAX);

        // Get all the mMatchPOList where cTax does not contain UPDATED_C_TAX
        defaultMMatchPOShouldBeFound("cTax.doesNotContain=" + UPDATED_C_TAX);
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
    public void getAllMMatchPOSByTaxAmountContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount contains DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.contains=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount contains UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.contains=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByTaxAmountNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where taxAmount does not contain DEFAULT_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("taxAmount.doesNotContain=" + DEFAULT_TAX_AMOUNT);

        // Get all the mMatchPOList where taxAmount does not contain UPDATED_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("taxAmount.doesNotContain=" + UPDATED_TAX_AMOUNT);
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
    public void getAllMMatchPOSByForeignTaxAmountContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount contains DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.contains=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount contains UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.contains=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByForeignTaxAmountNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where foreignTaxAmount does not contain DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldNotBeFound("foreignTaxAmount.doesNotContain=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mMatchPOList where foreignTaxAmount does not contain UPDATED_FOREIGN_TAX_AMOUNT
        defaultMMatchPOShouldBeFound("foreignTaxAmount.doesNotContain=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator equals to DEFAULT_M_LOCATOR
        defaultMMatchPOShouldBeFound("mLocator.equals=" + DEFAULT_M_LOCATOR);

        // Get all the mMatchPOList where mLocator equals to UPDATED_M_LOCATOR
        defaultMMatchPOShouldNotBeFound("mLocator.equals=" + UPDATED_M_LOCATOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator not equals to DEFAULT_M_LOCATOR
        defaultMMatchPOShouldNotBeFound("mLocator.notEquals=" + DEFAULT_M_LOCATOR);

        // Get all the mMatchPOList where mLocator not equals to UPDATED_M_LOCATOR
        defaultMMatchPOShouldBeFound("mLocator.notEquals=" + UPDATED_M_LOCATOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator in DEFAULT_M_LOCATOR or UPDATED_M_LOCATOR
        defaultMMatchPOShouldBeFound("mLocator.in=" + DEFAULT_M_LOCATOR + "," + UPDATED_M_LOCATOR);

        // Get all the mMatchPOList where mLocator equals to UPDATED_M_LOCATOR
        defaultMMatchPOShouldNotBeFound("mLocator.in=" + UPDATED_M_LOCATOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator is not null
        defaultMMatchPOShouldBeFound("mLocator.specified=true");

        // Get all the mMatchPOList where mLocator is null
        defaultMMatchPOShouldNotBeFound("mLocator.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator contains DEFAULT_M_LOCATOR
        defaultMMatchPOShouldBeFound("mLocator.contains=" + DEFAULT_M_LOCATOR);

        // Get all the mMatchPOList where mLocator contains UPDATED_M_LOCATOR
        defaultMMatchPOShouldNotBeFound("mLocator.contains=" + UPDATED_M_LOCATOR);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSBymLocatorNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where mLocator does not contain DEFAULT_M_LOCATOR
        defaultMMatchPOShouldNotBeFound("mLocator.doesNotContain=" + DEFAULT_M_LOCATOR);

        // Get all the mMatchPOList where mLocator does not contain UPDATED_M_LOCATOR
        defaultMMatchPOShouldBeFound("mLocator.doesNotContain=" + UPDATED_M_LOCATOR);
    }


    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization equals to DEFAULT_AD_ORGANIZATION
        defaultMMatchPOShouldBeFound("adOrganization.equals=" + DEFAULT_AD_ORGANIZATION);

        // Get all the mMatchPOList where adOrganization equals to UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldNotBeFound("adOrganization.equals=" + UPDATED_AD_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization not equals to DEFAULT_AD_ORGANIZATION
        defaultMMatchPOShouldNotBeFound("adOrganization.notEquals=" + DEFAULT_AD_ORGANIZATION);

        // Get all the mMatchPOList where adOrganization not equals to UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldBeFound("adOrganization.notEquals=" + UPDATED_AD_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization in DEFAULT_AD_ORGANIZATION or UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldBeFound("adOrganization.in=" + DEFAULT_AD_ORGANIZATION + "," + UPDATED_AD_ORGANIZATION);

        // Get all the mMatchPOList where adOrganization equals to UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldNotBeFound("adOrganization.in=" + UPDATED_AD_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization is not null
        defaultMMatchPOShouldBeFound("adOrganization.specified=true");

        // Get all the mMatchPOList where adOrganization is null
        defaultMMatchPOShouldNotBeFound("adOrganization.specified=false");
    }
                @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization contains DEFAULT_AD_ORGANIZATION
        defaultMMatchPOShouldBeFound("adOrganization.contains=" + DEFAULT_AD_ORGANIZATION);

        // Get all the mMatchPOList where adOrganization contains UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldNotBeFound("adOrganization.contains=" + UPDATED_AD_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllMMatchPOSByAdOrganizationNotContainsSomething() throws Exception {
        // Initialize the database
        mMatchPORepository.saveAndFlush(mMatchPO);

        // Get all the mMatchPOList where adOrganization does not contain DEFAULT_AD_ORGANIZATION
        defaultMMatchPOShouldNotBeFound("adOrganization.doesNotContain=" + DEFAULT_AD_ORGANIZATION);

        // Get all the mMatchPOList where adOrganization does not contain UPDATED_AD_ORGANIZATION
        defaultMMatchPOShouldBeFound("adOrganization.doesNotContain=" + UPDATED_AD_ORGANIZATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMMatchPOShouldBeFound(String filter) throws Exception {
        restMMatchPOMockMvc.perform(get("/api/m-match-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMatchPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].cDocType").value(hasItem(DEFAULT_C_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].cVendor").value(hasItem(DEFAULT_C_VENDOR)))
            .andExpect(jsonPath("$.[*].cElement").value(hasItem(DEFAULT_C_ELEMENT)))
            .andExpect(jsonPath("$.[*].cCostCenter").value(hasItem(DEFAULT_C_COST_CENTER)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].mProductCode").value(hasItem(DEFAULT_M_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].mProductName").value(hasItem(DEFAULT_M_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].mProductDesc").value(hasItem(DEFAULT_M_PRODUCT_DESC)))
            .andExpect(jsonPath("$.[*].cUOM").value(hasItem(DEFAULT_C_UOM)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].cCurrency").value(hasItem(DEFAULT_C_CURRENCY)))
            .andExpect(jsonPath("$.[*].cConversionRate").value(hasItem(DEFAULT_C_CONVERSION_RATE)))
            .andExpect(jsonPath("$.[*].openQty").value(hasItem(DEFAULT_OPEN_QTY)))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL)))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL)))
            .andExpect(jsonPath("$.[*].openAmount").value(hasItem(DEFAULT_OPEN_AMOUNT)))
            .andExpect(jsonPath("$.[*].openForeignAmount").value(hasItem(DEFAULT_OPEN_FOREIGN_AMOUNT)))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES)))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES)))
            .andExpect(jsonPath("$.[*].cTax").value(hasItem(DEFAULT_C_TAX)))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT)))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT)))
            .andExpect(jsonPath("$.[*].mLocator").value(hasItem(DEFAULT_M_LOCATOR)))
            .andExpect(jsonPath("$.[*].adOrganization").value(hasItem(DEFAULT_AD_ORGANIZATION)));

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
            .cDocType(UPDATED_C_DOC_TYPE)
            .cVendor(UPDATED_C_VENDOR)
            .cElement(UPDATED_C_ELEMENT)
            .cCostCenter(UPDATED_C_COST_CENTER)
            .poNo(UPDATED_PO_NO)
            .poDate(UPDATED_PO_DATE)
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .mProductCode(UPDATED_M_PRODUCT_CODE)
            .mProductName(UPDATED_M_PRODUCT_NAME)
            .mProductDesc(UPDATED_M_PRODUCT_DESC)
            .cUOM(UPDATED_C_UOM)
            .qty(UPDATED_QTY)
            .cCurrency(UPDATED_C_CURRENCY)
            .cConversionRate(UPDATED_C_CONVERSION_RATE)
            .openQty(UPDATED_OPEN_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .openAmount(UPDATED_OPEN_AMOUNT)
            .openForeignAmount(UPDATED_OPEN_FOREIGN_AMOUNT)
            .totalLines(UPDATED_TOTAL_LINES)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .cTax(UPDATED_C_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .mLocator(UPDATED_M_LOCATOR)
            .adOrganization(UPDATED_AD_ORGANIZATION);
        MMatchPODTO mMatchPODTO = mMatchPOMapper.toDto(updatedMMatchPO);

        restMMatchPOMockMvc.perform(put("/api/m-match-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mMatchPODTO)))
            .andExpect(status().isOk());

        // Validate the MMatchPO in the database
        List<MMatchPO> mMatchPOList = mMatchPORepository.findAll();
        assertThat(mMatchPOList).hasSize(databaseSizeBeforeUpdate);
        MMatchPO testMMatchPO = mMatchPOList.get(mMatchPOList.size() - 1);
        assertThat(testMMatchPO.getcDocType()).isEqualTo(UPDATED_C_DOC_TYPE);
        assertThat(testMMatchPO.getcVendor()).isEqualTo(UPDATED_C_VENDOR);
        assertThat(testMMatchPO.getcElement()).isEqualTo(UPDATED_C_ELEMENT);
        assertThat(testMMatchPO.getcCostCenter()).isEqualTo(UPDATED_C_COST_CENTER);
        assertThat(testMMatchPO.getPoNo()).isEqualTo(UPDATED_PO_NO);
        assertThat(testMMatchPO.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testMMatchPO.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testMMatchPO.getReceiptDate()).isEqualTo(UPDATED_RECEIPT_DATE);
        assertThat(testMMatchPO.getDeliveryNo()).isEqualTo(UPDATED_DELIVERY_NO);
        assertThat(testMMatchPO.getmProductCode()).isEqualTo(UPDATED_M_PRODUCT_CODE);
        assertThat(testMMatchPO.getmProductName()).isEqualTo(UPDATED_M_PRODUCT_NAME);
        assertThat(testMMatchPO.getmProductDesc()).isEqualTo(UPDATED_M_PRODUCT_DESC);
        assertThat(testMMatchPO.getcUOM()).isEqualTo(UPDATED_C_UOM);
        assertThat(testMMatchPO.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMMatchPO.getcCurrency()).isEqualTo(UPDATED_C_CURRENCY);
        assertThat(testMMatchPO.getcConversionRate()).isEqualTo(UPDATED_C_CONVERSION_RATE);
        assertThat(testMMatchPO.getOpenQty()).isEqualTo(UPDATED_OPEN_QTY);
        assertThat(testMMatchPO.getPriceActual()).isEqualTo(UPDATED_PRICE_ACTUAL);
        assertThat(testMMatchPO.getForeignActual()).isEqualTo(UPDATED_FOREIGN_ACTUAL);
        assertThat(testMMatchPO.getOpenAmount()).isEqualTo(UPDATED_OPEN_AMOUNT);
        assertThat(testMMatchPO.getOpenForeignAmount()).isEqualTo(UPDATED_OPEN_FOREIGN_AMOUNT);
        assertThat(testMMatchPO.getTotalLines()).isEqualTo(UPDATED_TOTAL_LINES);
        assertThat(testMMatchPO.getForeignTotalLines()).isEqualTo(UPDATED_FOREIGN_TOTAL_LINES);
        assertThat(testMMatchPO.getcTax()).isEqualTo(UPDATED_C_TAX);
        assertThat(testMMatchPO.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testMMatchPO.getForeignTaxAmount()).isEqualTo(UPDATED_FOREIGN_TAX_AMOUNT);
        assertThat(testMMatchPO.getmLocator()).isEqualTo(UPDATED_M_LOCATOR);
        assertThat(testMMatchPO.getAdOrganization()).isEqualTo(UPDATED_AD_ORGANIZATION);
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
