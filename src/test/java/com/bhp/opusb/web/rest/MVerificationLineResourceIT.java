package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVerificationLine;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.MVerificationLineRepository;
import com.bhp.opusb.service.MVerificationLineService;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.mapper.MVerificationLineMapper;
import com.bhp.opusb.service.dto.MVerificationLineCriteria;
import com.bhp.opusb.service.MVerificationLineQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVerificationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVerificationLineResourceIT {

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIPT_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_NO = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_QTY = 1L;
    private static final Long UPDATED_QTY = 2L;
    private static final Long SMALLER_QTY = 1L - 1L;

    private static final BigDecimal DEFAULT_PRICE_ACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_ACTUAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE_ACTUAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_LINES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LINES = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_LINES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVerificationLineRepository mVerificationLineRepository;

    @Autowired
    private MVerificationLineMapper mVerificationLineMapper;

    @Autowired
    private MVerificationLineService mVerificationLineService;

    @Autowired
    private MVerificationLineQueryService mVerificationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVerificationLineMockMvc;

    private MVerificationLine mVerificationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerificationLine createEntity(EntityManager em) {
        MVerificationLine mVerificationLine = new MVerificationLine()
            .orderNo(DEFAULT_ORDER_NO)
            .receiptNo(DEFAULT_RECEIPT_NO)
            .deliveryNo(DEFAULT_DELIVERY_NO)
            .description(DEFAULT_DESCRIPTION)
            .qty(DEFAULT_QTY)
            .priceActual(DEFAULT_PRICE_ACTUAL)
            .totalLines(DEFAULT_TOTAL_LINES)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MVerification mVerification;
        if (TestUtil.findAll(em, MVerification.class).isEmpty()) {
            mVerification = MVerificationResourceIT.createEntity(em);
            em.persist(mVerification);
            em.flush();
        } else {
            mVerification = TestUtil.findAll(em, MVerification.class).get(0);
        }
        mVerificationLine.setVerification(mVerification);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVerificationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mVerificationLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mVerificationLine.setUom(cUnitOfMeasure);
        return mVerificationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerificationLine createUpdatedEntity(EntityManager em) {
        MVerificationLine mVerificationLine = new MVerificationLine()
            .orderNo(UPDATED_ORDER_NO)
            .receiptNo(UPDATED_RECEIPT_NO)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .totalLines(UPDATED_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MVerification mVerification;
        if (TestUtil.findAll(em, MVerification.class).isEmpty()) {
            mVerification = MVerificationResourceIT.createUpdatedEntity(em);
            em.persist(mVerification);
            em.flush();
        } else {
            mVerification = TestUtil.findAll(em, MVerification.class).get(0);
        }
        mVerificationLine.setVerification(mVerification);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVerificationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mVerificationLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mVerificationLine.setUom(cUnitOfMeasure);
        return mVerificationLine;
    }

    @BeforeEach
    public void initTest() {
        mVerificationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVerificationLine() throws Exception {
        int databaseSizeBeforeCreate = mVerificationLineRepository.findAll().size();

        // Create the MVerificationLine
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);
        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MVerificationLine in the database
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeCreate + 1);
        MVerificationLine testMVerificationLine = mVerificationLineList.get(mVerificationLineList.size() - 1);
        assertThat(testMVerificationLine.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testMVerificationLine.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testMVerificationLine.getDeliveryNo()).isEqualTo(DEFAULT_DELIVERY_NO);
        assertThat(testMVerificationLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMVerificationLine.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMVerificationLine.getPriceActual()).isEqualTo(DEFAULT_PRICE_ACTUAL);
        assertThat(testMVerificationLine.getTotalLines()).isEqualTo(DEFAULT_TOTAL_LINES);
        assertThat(testMVerificationLine.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testMVerificationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVerificationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVerificationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVerificationLineRepository.findAll().size();

        // Create the MVerificationLine with an existing ID
        mVerificationLine.setId(1L);
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerificationLine in the database
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setOrderNo(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiptNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setReceiptNo(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setDeliveryNo(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setQty(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceActualIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setPriceActual(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalLinesIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setTotalLines(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setTaxAmount(null);

        // Create the MVerificationLine, which fails.
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        restMVerificationLineMockMvc.perform(post("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVerificationLines() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerificationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVerificationLine() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get the mVerificationLine
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines/{id}", mVerificationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVerificationLine.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.deliveryNo").value(DEFAULT_DELIVERY_NO))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.intValue()))
            .andExpect(jsonPath("$.priceActual").value(DEFAULT_PRICE_ACTUAL.intValue()))
            .andExpect(jsonPath("$.totalLines").value(DEFAULT_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVerificationLinesByIdFiltering() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        Long id = mVerificationLine.getId();

        defaultMVerificationLineShouldBeFound("id.equals=" + id);
        defaultMVerificationLineShouldNotBeFound("id.notEquals=" + id);

        defaultMVerificationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVerificationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMVerificationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVerificationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo equals to DEFAULT_ORDER_NO
        defaultMVerificationLineShouldBeFound("orderNo.equals=" + DEFAULT_ORDER_NO);

        // Get all the mVerificationLineList where orderNo equals to UPDATED_ORDER_NO
        defaultMVerificationLineShouldNotBeFound("orderNo.equals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo not equals to DEFAULT_ORDER_NO
        defaultMVerificationLineShouldNotBeFound("orderNo.notEquals=" + DEFAULT_ORDER_NO);

        // Get all the mVerificationLineList where orderNo not equals to UPDATED_ORDER_NO
        defaultMVerificationLineShouldBeFound("orderNo.notEquals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo in DEFAULT_ORDER_NO or UPDATED_ORDER_NO
        defaultMVerificationLineShouldBeFound("orderNo.in=" + DEFAULT_ORDER_NO + "," + UPDATED_ORDER_NO);

        // Get all the mVerificationLineList where orderNo equals to UPDATED_ORDER_NO
        defaultMVerificationLineShouldNotBeFound("orderNo.in=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo is not null
        defaultMVerificationLineShouldBeFound("orderNo.specified=true");

        // Get all the mVerificationLineList where orderNo is null
        defaultMVerificationLineShouldNotBeFound("orderNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo contains DEFAULT_ORDER_NO
        defaultMVerificationLineShouldBeFound("orderNo.contains=" + DEFAULT_ORDER_NO);

        // Get all the mVerificationLineList where orderNo contains UPDATED_ORDER_NO
        defaultMVerificationLineShouldNotBeFound("orderNo.contains=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByOrderNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where orderNo does not contain DEFAULT_ORDER_NO
        defaultMVerificationLineShouldNotBeFound("orderNo.doesNotContain=" + DEFAULT_ORDER_NO);

        // Get all the mVerificationLineList where orderNo does not contain UPDATED_ORDER_NO
        defaultMVerificationLineShouldBeFound("orderNo.doesNotContain=" + UPDATED_ORDER_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo equals to DEFAULT_RECEIPT_NO
        defaultMVerificationLineShouldBeFound("receiptNo.equals=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationLineList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldNotBeFound("receiptNo.equals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo not equals to DEFAULT_RECEIPT_NO
        defaultMVerificationLineShouldNotBeFound("receiptNo.notEquals=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationLineList where receiptNo not equals to UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldBeFound("receiptNo.notEquals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo in DEFAULT_RECEIPT_NO or UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldBeFound("receiptNo.in=" + DEFAULT_RECEIPT_NO + "," + UPDATED_RECEIPT_NO);

        // Get all the mVerificationLineList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldNotBeFound("receiptNo.in=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo is not null
        defaultMVerificationLineShouldBeFound("receiptNo.specified=true");

        // Get all the mVerificationLineList where receiptNo is null
        defaultMVerificationLineShouldNotBeFound("receiptNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo contains DEFAULT_RECEIPT_NO
        defaultMVerificationLineShouldBeFound("receiptNo.contains=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationLineList where receiptNo contains UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldNotBeFound("receiptNo.contains=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiptNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiptNo does not contain DEFAULT_RECEIPT_NO
        defaultMVerificationLineShouldNotBeFound("receiptNo.doesNotContain=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationLineList where receiptNo does not contain UPDATED_RECEIPT_NO
        defaultMVerificationLineShouldBeFound("receiptNo.doesNotContain=" + UPDATED_RECEIPT_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo equals to DEFAULT_DELIVERY_NO
        defaultMVerificationLineShouldBeFound("deliveryNo.equals=" + DEFAULT_DELIVERY_NO);

        // Get all the mVerificationLineList where deliveryNo equals to UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldNotBeFound("deliveryNo.equals=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo not equals to DEFAULT_DELIVERY_NO
        defaultMVerificationLineShouldNotBeFound("deliveryNo.notEquals=" + DEFAULT_DELIVERY_NO);

        // Get all the mVerificationLineList where deliveryNo not equals to UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldBeFound("deliveryNo.notEquals=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo in DEFAULT_DELIVERY_NO or UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldBeFound("deliveryNo.in=" + DEFAULT_DELIVERY_NO + "," + UPDATED_DELIVERY_NO);

        // Get all the mVerificationLineList where deliveryNo equals to UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldNotBeFound("deliveryNo.in=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo is not null
        defaultMVerificationLineShouldBeFound("deliveryNo.specified=true");

        // Get all the mVerificationLineList where deliveryNo is null
        defaultMVerificationLineShouldNotBeFound("deliveryNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo contains DEFAULT_DELIVERY_NO
        defaultMVerificationLineShouldBeFound("deliveryNo.contains=" + DEFAULT_DELIVERY_NO);

        // Get all the mVerificationLineList where deliveryNo contains UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldNotBeFound("deliveryNo.contains=" + UPDATED_DELIVERY_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDeliveryNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where deliveryNo does not contain DEFAULT_DELIVERY_NO
        defaultMVerificationLineShouldNotBeFound("deliveryNo.doesNotContain=" + DEFAULT_DELIVERY_NO);

        // Get all the mVerificationLineList where deliveryNo does not contain UPDATED_DELIVERY_NO
        defaultMVerificationLineShouldBeFound("deliveryNo.doesNotContain=" + UPDATED_DELIVERY_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description equals to DEFAULT_DESCRIPTION
        defaultMVerificationLineShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationLineList where description equals to UPDATED_DESCRIPTION
        defaultMVerificationLineShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description not equals to DEFAULT_DESCRIPTION
        defaultMVerificationLineShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationLineList where description not equals to UPDATED_DESCRIPTION
        defaultMVerificationLineShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMVerificationLineShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mVerificationLineList where description equals to UPDATED_DESCRIPTION
        defaultMVerificationLineShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description is not null
        defaultMVerificationLineShouldBeFound("description.specified=true");

        // Get all the mVerificationLineList where description is null
        defaultMVerificationLineShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description contains DEFAULT_DESCRIPTION
        defaultMVerificationLineShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationLineList where description contains UPDATED_DESCRIPTION
        defaultMVerificationLineShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where description does not contain DEFAULT_DESCRIPTION
        defaultMVerificationLineShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationLineList where description does not contain UPDATED_DESCRIPTION
        defaultMVerificationLineShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty equals to DEFAULT_QTY
        defaultMVerificationLineShouldBeFound("qty.equals=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty equals to UPDATED_QTY
        defaultMVerificationLineShouldNotBeFound("qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty not equals to DEFAULT_QTY
        defaultMVerificationLineShouldNotBeFound("qty.notEquals=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty not equals to UPDATED_QTY
        defaultMVerificationLineShouldBeFound("qty.notEquals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty in DEFAULT_QTY or UPDATED_QTY
        defaultMVerificationLineShouldBeFound("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY);

        // Get all the mVerificationLineList where qty equals to UPDATED_QTY
        defaultMVerificationLineShouldNotBeFound("qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty is not null
        defaultMVerificationLineShouldBeFound("qty.specified=true");

        // Get all the mVerificationLineList where qty is null
        defaultMVerificationLineShouldNotBeFound("qty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty is greater than or equal to DEFAULT_QTY
        defaultMVerificationLineShouldBeFound("qty.greaterThanOrEqual=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty is greater than or equal to UPDATED_QTY
        defaultMVerificationLineShouldNotBeFound("qty.greaterThanOrEqual=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty is less than or equal to DEFAULT_QTY
        defaultMVerificationLineShouldBeFound("qty.lessThanOrEqual=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty is less than or equal to SMALLER_QTY
        defaultMVerificationLineShouldNotBeFound("qty.lessThanOrEqual=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty is less than DEFAULT_QTY
        defaultMVerificationLineShouldNotBeFound("qty.lessThan=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty is less than UPDATED_QTY
        defaultMVerificationLineShouldBeFound("qty.lessThan=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where qty is greater than DEFAULT_QTY
        defaultMVerificationLineShouldNotBeFound("qty.greaterThan=" + DEFAULT_QTY);

        // Get all the mVerificationLineList where qty is greater than SMALLER_QTY
        defaultMVerificationLineShouldBeFound("qty.greaterThan=" + SMALLER_QTY);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual equals to DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.equals=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual equals to UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.equals=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual not equals to DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.notEquals=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual not equals to UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.notEquals=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual in DEFAULT_PRICE_ACTUAL or UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.in=" + DEFAULT_PRICE_ACTUAL + "," + UPDATED_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual equals to UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.in=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual is not null
        defaultMVerificationLineShouldBeFound("priceActual.specified=true");

        // Get all the mVerificationLineList where priceActual is null
        defaultMVerificationLineShouldNotBeFound("priceActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual is greater than or equal to DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.greaterThanOrEqual=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual is greater than or equal to UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.greaterThanOrEqual=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual is less than or equal to DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.lessThanOrEqual=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual is less than or equal to SMALLER_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.lessThanOrEqual=" + SMALLER_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual is less than DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.lessThan=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual is less than UPDATED_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.lessThan=" + UPDATED_PRICE_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPriceActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where priceActual is greater than DEFAULT_PRICE_ACTUAL
        defaultMVerificationLineShouldNotBeFound("priceActual.greaterThan=" + DEFAULT_PRICE_ACTUAL);

        // Get all the mVerificationLineList where priceActual is greater than SMALLER_PRICE_ACTUAL
        defaultMVerificationLineShouldBeFound("priceActual.greaterThan=" + SMALLER_PRICE_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines equals to DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.equals=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.equals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines not equals to DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.notEquals=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines not equals to UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.notEquals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines in DEFAULT_TOTAL_LINES or UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.in=" + DEFAULT_TOTAL_LINES + "," + UPDATED_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.in=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines is not null
        defaultMVerificationLineShouldBeFound("totalLines.specified=true");

        // Get all the mVerificationLineList where totalLines is null
        defaultMVerificationLineShouldNotBeFound("totalLines.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines is greater than or equal to DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.greaterThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines is greater than or equal to UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.greaterThanOrEqual=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines is less than or equal to DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.lessThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines is less than or equal to SMALLER_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.lessThanOrEqual=" + SMALLER_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines is less than DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.lessThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines is less than UPDATED_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.lessThan=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTotalLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where totalLines is greater than DEFAULT_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("totalLines.greaterThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationLineList where totalLines is greater than SMALLER_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("totalLines.greaterThan=" + SMALLER_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount is not null
        defaultMVerificationLineShouldBeFound("taxAmount.specified=true");

        // Get all the mVerificationLineList where taxAmount is null
        defaultMVerificationLineShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationLineList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where uid equals to DEFAULT_UID
        defaultMVerificationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVerificationLineList where uid equals to UPDATED_UID
        defaultMVerificationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where uid not equals to DEFAULT_UID
        defaultMVerificationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVerificationLineList where uid not equals to UPDATED_UID
        defaultMVerificationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVerificationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVerificationLineList where uid equals to UPDATED_UID
        defaultMVerificationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where uid is not null
        defaultMVerificationLineShouldBeFound("uid.specified=true");

        // Get all the mVerificationLineList where uid is null
        defaultMVerificationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where active equals to DEFAULT_ACTIVE
        defaultMVerificationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationLineList where active equals to UPDATED_ACTIVE
        defaultMVerificationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where active not equals to DEFAULT_ACTIVE
        defaultMVerificationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationLineList where active not equals to UPDATED_ACTIVE
        defaultMVerificationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVerificationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVerificationLineList where active equals to UPDATED_ACTIVE
        defaultMVerificationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where active is not null
        defaultMVerificationLineShouldBeFound("active.specified=true");

        // Get all the mVerificationLineList where active is null
        defaultMVerificationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVerification verification = mVerificationLine.getVerification();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long verificationId = verification.getId();

        // Get all the mVerificationLineList where verification equals to verificationId
        defaultMVerificationLineShouldBeFound("verificationId.equals=" + verificationId);

        // Get all the mVerificationLineList where verification equals to verificationId + 1
        defaultMVerificationLineShouldNotBeFound("verificationId.equals=" + (verificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVerificationLine.getAdOrganization();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVerificationLineList where adOrganization equals to adOrganizationId
        defaultMVerificationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVerificationLineList where adOrganization equals to adOrganizationId + 1
        defaultMVerificationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mVerificationLine.getProduct();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long productId = product.getId();

        // Get all the mVerificationLineList where product equals to productId
        defaultMVerificationLineShouldBeFound("productId.equals=" + productId);

        // Get all the mVerificationLineList where product equals to productId + 1
        defaultMVerificationLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mVerificationLine.getUom();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long uomId = uom.getId();

        // Get all the mVerificationLineList where uom equals to uomId
        defaultMVerificationLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mVerificationLineList where uom equals to uomId + 1
        defaultMVerificationLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVerificationLineShouldBeFound(String filter) throws Exception {
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerificationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVerificationLineShouldNotBeFound(String filter) throws Exception {
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVerificationLine() throws Exception {
        // Get the mVerificationLine
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVerificationLine() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        int databaseSizeBeforeUpdate = mVerificationLineRepository.findAll().size();

        // Update the mVerificationLine
        MVerificationLine updatedMVerificationLine = mVerificationLineRepository.findById(mVerificationLine.getId()).get();
        // Disconnect from session so that the updates on updatedMVerificationLine are not directly saved in db
        em.detach(updatedMVerificationLine);
        updatedMVerificationLine
            .orderNo(UPDATED_ORDER_NO)
            .receiptNo(UPDATED_RECEIPT_NO)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .totalLines(UPDATED_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(updatedMVerificationLine);

        restMVerificationLineMockMvc.perform(put("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MVerificationLine in the database
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeUpdate);
        MVerificationLine testMVerificationLine = mVerificationLineList.get(mVerificationLineList.size() - 1);
        assertThat(testMVerificationLine.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMVerificationLine.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testMVerificationLine.getDeliveryNo()).isEqualTo(UPDATED_DELIVERY_NO);
        assertThat(testMVerificationLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMVerificationLine.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMVerificationLine.getPriceActual()).isEqualTo(UPDATED_PRICE_ACTUAL);
        assertThat(testMVerificationLine.getTotalLines()).isEqualTo(UPDATED_TOTAL_LINES);
        assertThat(testMVerificationLine.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testMVerificationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVerificationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVerificationLine() throws Exception {
        int databaseSizeBeforeUpdate = mVerificationLineRepository.findAll().size();

        // Create the MVerificationLine
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(mVerificationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVerificationLineMockMvc.perform(put("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerificationLine in the database
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVerificationLine() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        int databaseSizeBeforeDelete = mVerificationLineRepository.findAll().size();

        // Delete the mVerificationLine
        restMVerificationLineMockMvc.perform(delete("/api/m-verification-lines/{id}", mVerificationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
