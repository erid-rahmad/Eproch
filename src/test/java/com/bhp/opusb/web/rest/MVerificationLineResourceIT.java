package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVerificationLine;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CCurrency;
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
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final String DEFAULT_VERIFICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PO_NO = "AAAAAAAAAA";
    private static final String UPDATED_PO_NO = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVE_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_NO = "BBBBBBBBBB";

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

    private static final String DEFAULT_LINE_NO = "AAAAAAAAAA";
    private static final String UPDATED_LINE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONVERSION_RATE = "AAAAAAAAAA";
    private static final String UPDATED_CONVERSION_RATE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FOREIGN_ACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_ACTUAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_ACTUAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_TOTAL_LINES = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_TOTAL_LINES = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_TOTAL_LINES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVE_DATE = LocalDate.ofEpochDay(-1L);

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
            .verificationNo(DEFAULT_VERIFICATION_NO)
            .poNo(DEFAULT_PO_NO)
            .receiveNo(DEFAULT_RECEIVE_NO)
            .deliveryNo(DEFAULT_DELIVERY_NO)
            .description(DEFAULT_DESCRIPTION)
            .qty(DEFAULT_QTY)
            .priceActual(DEFAULT_PRICE_ACTUAL)
            .totalLines(DEFAULT_TOTAL_LINES)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .lineNo(DEFAULT_LINE_NO)
            .conversionRate(DEFAULT_CONVERSION_RATE)
            .foreignActual(DEFAULT_FOREIGN_ACTUAL)
            .foreignTotalLines(DEFAULT_FOREIGN_TOTAL_LINES)
            .foreignTaxAmount(DEFAULT_FOREIGN_TAX_AMOUNT)
            .receiveDate(DEFAULT_RECEIVE_DATE);
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
        // Add required entity
        CElementValue cElementValue;
        if (TestUtil.findAll(em, CElementValue.class).isEmpty()) {
            cElementValue = CElementValueResourceIT.createEntity(em);
            em.persist(cElementValue);
            em.flush();
        } else {
            cElementValue = TestUtil.findAll(em, CElementValue.class).get(0);
        }
        mVerificationLine.setCElement(cElementValue);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVerificationLine.setCCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerificationLine.setCCurrency(cCurrency);
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
            .verificationNo(UPDATED_VERIFICATION_NO)
            .poNo(UPDATED_PO_NO)
            .receiveNo(UPDATED_RECEIVE_NO)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .totalLines(UPDATED_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .lineNo(UPDATED_LINE_NO)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .receiveDate(UPDATED_RECEIVE_DATE);
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
        // Add required entity
        CElementValue cElementValue;
        if (TestUtil.findAll(em, CElementValue.class).isEmpty()) {
            cElementValue = CElementValueResourceIT.createUpdatedEntity(em);
            em.persist(cElementValue);
            em.flush();
        } else {
            cElementValue = TestUtil.findAll(em, CElementValue.class).get(0);
        }
        mVerificationLine.setCElement(cElementValue);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mVerificationLine.setCCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerificationLine.setCCurrency(cCurrency);
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
        assertThat(testMVerificationLine.getVerificationNo()).isEqualTo(DEFAULT_VERIFICATION_NO);
        assertThat(testMVerificationLine.getPoNo()).isEqualTo(DEFAULT_PO_NO);
        assertThat(testMVerificationLine.getReceiveNo()).isEqualTo(DEFAULT_RECEIVE_NO);
        assertThat(testMVerificationLine.getDeliveryNo()).isEqualTo(DEFAULT_DELIVERY_NO);
        assertThat(testMVerificationLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMVerificationLine.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMVerificationLine.getPriceActual()).isEqualTo(DEFAULT_PRICE_ACTUAL);
        assertThat(testMVerificationLine.getTotalLines()).isEqualTo(DEFAULT_TOTAL_LINES);
        assertThat(testMVerificationLine.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testMVerificationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVerificationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMVerificationLine.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testMVerificationLine.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
        assertThat(testMVerificationLine.getForeignActual()).isEqualTo(DEFAULT_FOREIGN_ACTUAL);
        assertThat(testMVerificationLine.getForeignTotalLines()).isEqualTo(DEFAULT_FOREIGN_TOTAL_LINES);
        assertThat(testMVerificationLine.getForeignTaxAmount()).isEqualTo(DEFAULT_FOREIGN_TAX_AMOUNT);
        assertThat(testMVerificationLine.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
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
    public void checkPoNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setPoNo(null);

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
    public void checkReceiveNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationLineRepository.findAll().size();
        // set the field null
        mVerificationLine.setReceiveNo(null);

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
            .andExpect(jsonPath("$.[*].verificationNo").value(hasItem(DEFAULT_VERIFICATION_NO)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].receiveNo").value(hasItem(DEFAULT_RECEIVE_NO)))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(DEFAULT_CONVERSION_RATE)))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())));
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
            .andExpect(jsonPath("$.verificationNo").value(DEFAULT_VERIFICATION_NO))
            .andExpect(jsonPath("$.poNo").value(DEFAULT_PO_NO))
            .andExpect(jsonPath("$.receiveNo").value(DEFAULT_RECEIVE_NO))
            .andExpect(jsonPath("$.deliveryNo").value(DEFAULT_DELIVERY_NO))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.intValue()))
            .andExpect(jsonPath("$.priceActual").value(DEFAULT_PRICE_ACTUAL.intValue()))
            .andExpect(jsonPath("$.totalLines").value(DEFAULT_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
            .andExpect(jsonPath("$.conversionRate").value(DEFAULT_CONVERSION_RATE))
            .andExpect(jsonPath("$.foreignActual").value(DEFAULT_FOREIGN_ACTUAL.intValue()))
            .andExpect(jsonPath("$.foreignTotalLines").value(DEFAULT_FOREIGN_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.foreignTaxAmount").value(DEFAULT_FOREIGN_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()));
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
    public void getAllMVerificationLinesByVerificationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo equals to DEFAULT_VERIFICATION_NO
        defaultMVerificationLineShouldBeFound("verificationNo.equals=" + DEFAULT_VERIFICATION_NO);

        // Get all the mVerificationLineList where verificationNo equals to UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldNotBeFound("verificationNo.equals=" + UPDATED_VERIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo not equals to DEFAULT_VERIFICATION_NO
        defaultMVerificationLineShouldNotBeFound("verificationNo.notEquals=" + DEFAULT_VERIFICATION_NO);

        // Get all the mVerificationLineList where verificationNo not equals to UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldBeFound("verificationNo.notEquals=" + UPDATED_VERIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo in DEFAULT_VERIFICATION_NO or UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldBeFound("verificationNo.in=" + DEFAULT_VERIFICATION_NO + "," + UPDATED_VERIFICATION_NO);

        // Get all the mVerificationLineList where verificationNo equals to UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldNotBeFound("verificationNo.in=" + UPDATED_VERIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo is not null
        defaultMVerificationLineShouldBeFound("verificationNo.specified=true");

        // Get all the mVerificationLineList where verificationNo is null
        defaultMVerificationLineShouldNotBeFound("verificationNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo contains DEFAULT_VERIFICATION_NO
        defaultMVerificationLineShouldBeFound("verificationNo.contains=" + DEFAULT_VERIFICATION_NO);

        // Get all the mVerificationLineList where verificationNo contains UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldNotBeFound("verificationNo.contains=" + UPDATED_VERIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByVerificationNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where verificationNo does not contain DEFAULT_VERIFICATION_NO
        defaultMVerificationLineShouldNotBeFound("verificationNo.doesNotContain=" + DEFAULT_VERIFICATION_NO);

        // Get all the mVerificationLineList where verificationNo does not contain UPDATED_VERIFICATION_NO
        defaultMVerificationLineShouldBeFound("verificationNo.doesNotContain=" + UPDATED_VERIFICATION_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo equals to DEFAULT_PO_NO
        defaultMVerificationLineShouldBeFound("poNo.equals=" + DEFAULT_PO_NO);

        // Get all the mVerificationLineList where poNo equals to UPDATED_PO_NO
        defaultMVerificationLineShouldNotBeFound("poNo.equals=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo not equals to DEFAULT_PO_NO
        defaultMVerificationLineShouldNotBeFound("poNo.notEquals=" + DEFAULT_PO_NO);

        // Get all the mVerificationLineList where poNo not equals to UPDATED_PO_NO
        defaultMVerificationLineShouldBeFound("poNo.notEquals=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo in DEFAULT_PO_NO or UPDATED_PO_NO
        defaultMVerificationLineShouldBeFound("poNo.in=" + DEFAULT_PO_NO + "," + UPDATED_PO_NO);

        // Get all the mVerificationLineList where poNo equals to UPDATED_PO_NO
        defaultMVerificationLineShouldNotBeFound("poNo.in=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo is not null
        defaultMVerificationLineShouldBeFound("poNo.specified=true");

        // Get all the mVerificationLineList where poNo is null
        defaultMVerificationLineShouldNotBeFound("poNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo contains DEFAULT_PO_NO
        defaultMVerificationLineShouldBeFound("poNo.contains=" + DEFAULT_PO_NO);

        // Get all the mVerificationLineList where poNo contains UPDATED_PO_NO
        defaultMVerificationLineShouldNotBeFound("poNo.contains=" + UPDATED_PO_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByPoNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where poNo does not contain DEFAULT_PO_NO
        defaultMVerificationLineShouldNotBeFound("poNo.doesNotContain=" + DEFAULT_PO_NO);

        // Get all the mVerificationLineList where poNo does not contain UPDATED_PO_NO
        defaultMVerificationLineShouldBeFound("poNo.doesNotContain=" + UPDATED_PO_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo equals to DEFAULT_RECEIVE_NO
        defaultMVerificationLineShouldBeFound("receiveNo.equals=" + DEFAULT_RECEIVE_NO);

        // Get all the mVerificationLineList where receiveNo equals to UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldNotBeFound("receiveNo.equals=" + UPDATED_RECEIVE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo not equals to DEFAULT_RECEIVE_NO
        defaultMVerificationLineShouldNotBeFound("receiveNo.notEquals=" + DEFAULT_RECEIVE_NO);

        // Get all the mVerificationLineList where receiveNo not equals to UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldBeFound("receiveNo.notEquals=" + UPDATED_RECEIVE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo in DEFAULT_RECEIVE_NO or UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldBeFound("receiveNo.in=" + DEFAULT_RECEIVE_NO + "," + UPDATED_RECEIVE_NO);

        // Get all the mVerificationLineList where receiveNo equals to UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldNotBeFound("receiveNo.in=" + UPDATED_RECEIVE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo is not null
        defaultMVerificationLineShouldBeFound("receiveNo.specified=true");

        // Get all the mVerificationLineList where receiveNo is null
        defaultMVerificationLineShouldNotBeFound("receiveNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo contains DEFAULT_RECEIVE_NO
        defaultMVerificationLineShouldBeFound("receiveNo.contains=" + DEFAULT_RECEIVE_NO);

        // Get all the mVerificationLineList where receiveNo contains UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldNotBeFound("receiveNo.contains=" + UPDATED_RECEIVE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveNo does not contain DEFAULT_RECEIVE_NO
        defaultMVerificationLineShouldNotBeFound("receiveNo.doesNotContain=" + DEFAULT_RECEIVE_NO);

        // Get all the mVerificationLineList where receiveNo does not contain UPDATED_RECEIVE_NO
        defaultMVerificationLineShouldBeFound("receiveNo.doesNotContain=" + UPDATED_RECEIVE_NO);
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
    public void getAllMVerificationLinesByLineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo equals to DEFAULT_LINE_NO
        defaultMVerificationLineShouldBeFound("lineNo.equals=" + DEFAULT_LINE_NO);

        // Get all the mVerificationLineList where lineNo equals to UPDATED_LINE_NO
        defaultMVerificationLineShouldNotBeFound("lineNo.equals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByLineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo not equals to DEFAULT_LINE_NO
        defaultMVerificationLineShouldNotBeFound("lineNo.notEquals=" + DEFAULT_LINE_NO);

        // Get all the mVerificationLineList where lineNo not equals to UPDATED_LINE_NO
        defaultMVerificationLineShouldBeFound("lineNo.notEquals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByLineNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo in DEFAULT_LINE_NO or UPDATED_LINE_NO
        defaultMVerificationLineShouldBeFound("lineNo.in=" + DEFAULT_LINE_NO + "," + UPDATED_LINE_NO);

        // Get all the mVerificationLineList where lineNo equals to UPDATED_LINE_NO
        defaultMVerificationLineShouldNotBeFound("lineNo.in=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByLineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo is not null
        defaultMVerificationLineShouldBeFound("lineNo.specified=true");

        // Get all the mVerificationLineList where lineNo is null
        defaultMVerificationLineShouldNotBeFound("lineNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByLineNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo contains DEFAULT_LINE_NO
        defaultMVerificationLineShouldBeFound("lineNo.contains=" + DEFAULT_LINE_NO);

        // Get all the mVerificationLineList where lineNo contains UPDATED_LINE_NO
        defaultMVerificationLineShouldNotBeFound("lineNo.contains=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByLineNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where lineNo does not contain DEFAULT_LINE_NO
        defaultMVerificationLineShouldNotBeFound("lineNo.doesNotContain=" + DEFAULT_LINE_NO);

        // Get all the mVerificationLineList where lineNo does not contain UPDATED_LINE_NO
        defaultMVerificationLineShouldBeFound("lineNo.doesNotContain=" + UPDATED_LINE_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate equals to DEFAULT_CONVERSION_RATE
        defaultMVerificationLineShouldBeFound("conversionRate.equals=" + DEFAULT_CONVERSION_RATE);

        // Get all the mVerificationLineList where conversionRate equals to UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldNotBeFound("conversionRate.equals=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate not equals to DEFAULT_CONVERSION_RATE
        defaultMVerificationLineShouldNotBeFound("conversionRate.notEquals=" + DEFAULT_CONVERSION_RATE);

        // Get all the mVerificationLineList where conversionRate not equals to UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldBeFound("conversionRate.notEquals=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate in DEFAULT_CONVERSION_RATE or UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldBeFound("conversionRate.in=" + DEFAULT_CONVERSION_RATE + "," + UPDATED_CONVERSION_RATE);

        // Get all the mVerificationLineList where conversionRate equals to UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldNotBeFound("conversionRate.in=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate is not null
        defaultMVerificationLineShouldBeFound("conversionRate.specified=true");

        // Get all the mVerificationLineList where conversionRate is null
        defaultMVerificationLineShouldNotBeFound("conversionRate.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate contains DEFAULT_CONVERSION_RATE
        defaultMVerificationLineShouldBeFound("conversionRate.contains=" + DEFAULT_CONVERSION_RATE);

        // Get all the mVerificationLineList where conversionRate contains UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldNotBeFound("conversionRate.contains=" + UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByConversionRateNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where conversionRate does not contain DEFAULT_CONVERSION_RATE
        defaultMVerificationLineShouldNotBeFound("conversionRate.doesNotContain=" + DEFAULT_CONVERSION_RATE);

        // Get all the mVerificationLineList where conversionRate does not contain UPDATED_CONVERSION_RATE
        defaultMVerificationLineShouldBeFound("conversionRate.doesNotContain=" + UPDATED_CONVERSION_RATE);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual equals to DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.equals=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual equals to UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.equals=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual not equals to DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.notEquals=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual not equals to UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.notEquals=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual in DEFAULT_FOREIGN_ACTUAL or UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.in=" + DEFAULT_FOREIGN_ACTUAL + "," + UPDATED_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual equals to UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.in=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual is not null
        defaultMVerificationLineShouldBeFound("foreignActual.specified=true");

        // Get all the mVerificationLineList where foreignActual is null
        defaultMVerificationLineShouldNotBeFound("foreignActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual is greater than or equal to DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.greaterThanOrEqual=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual is greater than or equal to UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.greaterThanOrEqual=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual is less than or equal to DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.lessThanOrEqual=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual is less than or equal to SMALLER_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.lessThanOrEqual=" + SMALLER_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual is less than DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.lessThan=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual is less than UPDATED_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.lessThan=" + UPDATED_FOREIGN_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignActual is greater than DEFAULT_FOREIGN_ACTUAL
        defaultMVerificationLineShouldNotBeFound("foreignActual.greaterThan=" + DEFAULT_FOREIGN_ACTUAL);

        // Get all the mVerificationLineList where foreignActual is greater than SMALLER_FOREIGN_ACTUAL
        defaultMVerificationLineShouldBeFound("foreignActual.greaterThan=" + SMALLER_FOREIGN_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines equals to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.equals=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.equals=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines not equals to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.notEquals=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines not equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.notEquals=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines in DEFAULT_FOREIGN_TOTAL_LINES or UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.in=" + DEFAULT_FOREIGN_TOTAL_LINES + "," + UPDATED_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines equals to UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.in=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines is not null
        defaultMVerificationLineShouldBeFound("foreignTotalLines.specified=true");

        // Get all the mVerificationLineList where foreignTotalLines is null
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines is greater than or equal to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.greaterThanOrEqual=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines is greater than or equal to UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.greaterThanOrEqual=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines is less than or equal to DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.lessThanOrEqual=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines is less than or equal to SMALLER_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.lessThanOrEqual=" + SMALLER_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines is less than DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.lessThan=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines is less than UPDATED_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.lessThan=" + UPDATED_FOREIGN_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTotalLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTotalLines is greater than DEFAULT_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldNotBeFound("foreignTotalLines.greaterThan=" + DEFAULT_FOREIGN_TOTAL_LINES);

        // Get all the mVerificationLineList where foreignTotalLines is greater than SMALLER_FOREIGN_TOTAL_LINES
        defaultMVerificationLineShouldBeFound("foreignTotalLines.greaterThan=" + SMALLER_FOREIGN_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.equals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.equals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount not equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.notEquals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount not equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.notEquals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount in DEFAULT_FOREIGN_TAX_AMOUNT or UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.in=" + DEFAULT_FOREIGN_TAX_AMOUNT + "," + UPDATED_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.in=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount is not null
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.specified=true");

        // Get all the mVerificationLineList where foreignTaxAmount is null
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount is greater than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.greaterThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount is greater than or equal to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.greaterThanOrEqual=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount is less than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.lessThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount is less than or equal to SMALLER_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.lessThanOrEqual=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount is less than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.lessThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount is less than UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.lessThan=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByForeignTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where foreignTaxAmount is greater than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldNotBeFound("foreignTaxAmount.greaterThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationLineList where foreignTaxAmount is greater than SMALLER_FOREIGN_TAX_AMOUNT
        defaultMVerificationLineShouldBeFound("foreignTaxAmount.greaterThan=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate equals to DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.equals=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.equals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate not equals to DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.notEquals=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate not equals to UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.notEquals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate in DEFAULT_RECEIVE_DATE or UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.in=" + DEFAULT_RECEIVE_DATE + "," + UPDATED_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.in=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate is not null
        defaultMVerificationLineShouldBeFound("receiveDate.specified=true");

        // Get all the mVerificationLineList where receiveDate is null
        defaultMVerificationLineShouldNotBeFound("receiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate is greater than or equal to DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.greaterThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate is greater than or equal to UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.greaterThanOrEqual=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate is less than or equal to DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.lessThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate is less than or equal to SMALLER_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.lessThanOrEqual=" + SMALLER_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate is less than DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.lessThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate is less than UPDATED_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.lessThan=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationLinesByReceiveDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationLineRepository.saveAndFlush(mVerificationLine);

        // Get all the mVerificationLineList where receiveDate is greater than DEFAULT_RECEIVE_DATE
        defaultMVerificationLineShouldNotBeFound("receiveDate.greaterThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the mVerificationLineList where receiveDate is greater than SMALLER_RECEIVE_DATE
        defaultMVerificationLineShouldBeFound("receiveDate.greaterThan=" + SMALLER_RECEIVE_DATE);
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


    @Test
    @Transactional
    public void getAllMVerificationLinesByCElementIsEqualToSomething() throws Exception {
        // Get already existing entity
        CElementValue cElement = mVerificationLine.getCElement();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long cElementId = cElement.getId();

        // Get all the mVerificationLineList where cElement equals to cElementId
        defaultMVerificationLineShouldBeFound("cElementId.equals=" + cElementId);

        // Get all the mVerificationLineList where cElement equals to cElementId + 1
        defaultMVerificationLineShouldNotBeFound("cElementId.equals=" + (cElementId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByCCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter cCostCenter = mVerificationLine.getCCostCenter();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long cCostCenterId = cCostCenter.getId();

        // Get all the mVerificationLineList where cCostCenter equals to cCostCenterId
        defaultMVerificationLineShouldBeFound("cCostCenterId.equals=" + cCostCenterId);

        // Get all the mVerificationLineList where cCostCenter equals to cCostCenterId + 1
        defaultMVerificationLineShouldNotBeFound("cCostCenterId.equals=" + (cCostCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationLinesByCCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency cCurrency = mVerificationLine.getCCurrency();
        mVerificationLineRepository.saveAndFlush(mVerificationLine);
        Long cCurrencyId = cCurrency.getId();

        // Get all the mVerificationLineList where cCurrency equals to cCurrencyId
        defaultMVerificationLineShouldBeFound("cCurrencyId.equals=" + cCurrencyId);

        // Get all the mVerificationLineList where cCurrency equals to cCurrencyId + 1
        defaultMVerificationLineShouldNotBeFound("cCurrencyId.equals=" + (cCurrencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVerificationLineShouldBeFound(String filter) throws Exception {
        restMVerificationLineMockMvc.perform(get("/api/m-verification-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerificationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].verificationNo").value(hasItem(DEFAULT_VERIFICATION_NO)))
            .andExpect(jsonPath("$.[*].poNo").value(hasItem(DEFAULT_PO_NO)))
            .andExpect(jsonPath("$.[*].receiveNo").value(hasItem(DEFAULT_RECEIVE_NO)))
            .andExpect(jsonPath("$.[*].deliveryNo").value(hasItem(DEFAULT_DELIVERY_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].priceActual").value(hasItem(DEFAULT_PRICE_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(DEFAULT_CONVERSION_RATE)))
            .andExpect(jsonPath("$.[*].foreignActual").value(hasItem(DEFAULT_FOREIGN_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignTotalLines").value(hasItem(DEFAULT_FOREIGN_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())));

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
            .verificationNo(UPDATED_VERIFICATION_NO)
            .poNo(UPDATED_PO_NO)
            .receiveNo(UPDATED_RECEIVE_NO)
            .deliveryNo(UPDATED_DELIVERY_NO)
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .priceActual(UPDATED_PRICE_ACTUAL)
            .totalLines(UPDATED_TOTAL_LINES)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .lineNo(UPDATED_LINE_NO)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .foreignActual(UPDATED_FOREIGN_ACTUAL)
            .foreignTotalLines(UPDATED_FOREIGN_TOTAL_LINES)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .receiveDate(UPDATED_RECEIVE_DATE);
        MVerificationLineDTO mVerificationLineDTO = mVerificationLineMapper.toDto(updatedMVerificationLine);

        restMVerificationLineMockMvc.perform(put("/api/m-verification-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MVerificationLine in the database
        List<MVerificationLine> mVerificationLineList = mVerificationLineRepository.findAll();
        assertThat(mVerificationLineList).hasSize(databaseSizeBeforeUpdate);
        MVerificationLine testMVerificationLine = mVerificationLineList.get(mVerificationLineList.size() - 1);
        assertThat(testMVerificationLine.getVerificationNo()).isEqualTo(UPDATED_VERIFICATION_NO);
        assertThat(testMVerificationLine.getPoNo()).isEqualTo(UPDATED_PO_NO);
        assertThat(testMVerificationLine.getReceiveNo()).isEqualTo(UPDATED_RECEIVE_NO);
        assertThat(testMVerificationLine.getDeliveryNo()).isEqualTo(UPDATED_DELIVERY_NO);
        assertThat(testMVerificationLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMVerificationLine.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMVerificationLine.getPriceActual()).isEqualTo(UPDATED_PRICE_ACTUAL);
        assertThat(testMVerificationLine.getTotalLines()).isEqualTo(UPDATED_TOTAL_LINES);
        assertThat(testMVerificationLine.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testMVerificationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVerificationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMVerificationLine.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testMVerificationLine.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testMVerificationLine.getForeignActual()).isEqualTo(UPDATED_FOREIGN_ACTUAL);
        assertThat(testMVerificationLine.getForeignTotalLines()).isEqualTo(UPDATED_FOREIGN_TOTAL_LINES);
        assertThat(testMVerificationLine.getForeignTaxAmount()).isEqualTo(UPDATED_FOREIGN_TAX_AMOUNT);
        assertThat(testMVerificationLine.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
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
