package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.repository.MVerificationRepository;
import com.bhp.opusb.service.MVerificationService;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.mapper.MVerificationMapper;
import com.bhp.opusb.service.dto.MVerificationCriteria;
import com.bhp.opusb.service.MVerificationQueryService;

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
 * Integration tests for the {@link MVerificationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVerificationResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIPT_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INVOICE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TAX_INVOICE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_INVOICE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TAX_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TAX_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TAX_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_TOTAL_LINES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LINES = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_LINES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOREIGN_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOREIGN_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOREIGN_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATE_SUBMIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SUBMIT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_SUBMIT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_ACCT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_ACCT = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_WITHHOLDING_AMT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WITHHOLDING_AMT = new BigDecimal(2);
    private static final BigDecimal SMALLER_WITHHOLDING_AMT = new BigDecimal(1 - 1);

    private static final String DEFAULT_INVOICE_AP = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_AP = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PAY_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_PAY_AMT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAY_AMT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PAY_AMT = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATE_REJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REJECT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REJECT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_APPROVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_APPROVE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PAY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAY_STATUS = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_RECEIPT_REVERSED = false;
    private static final Boolean UPDATED_RECEIPT_REVERSED = true;

    private static final Boolean DEFAULT_AP_REVERSED = false;
    private static final Boolean UPDATED_AP_REVERSED = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVerificationRepository mVerificationRepository;

    @Autowired
    private MVerificationMapper mVerificationMapper;

    @Autowired
    private MVerificationService mVerificationService;

    @Autowired
    private MVerificationQueryService mVerificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVerificationMockMvc;

    private MVerification mVerification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerification createEntity(EntityManager em) {
        MVerification mVerification = new MVerification()
            .description(DEFAULT_DESCRIPTION)
            .receiptNo(DEFAULT_RECEIPT_NO)
            .invoiceNo(DEFAULT_INVOICE_NO)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .taxInvoice(DEFAULT_TAX_INVOICE)
            .taxDate(DEFAULT_TAX_DATE)
            .totalLines(DEFAULT_TOTAL_LINES)
            .grandTotal(DEFAULT_GRAND_TOTAL)
            .foreignGrandTotal(DEFAULT_FOREIGN_GRAND_TOTAL)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .foreignTaxAmount(DEFAULT_FOREIGN_TAX_AMOUNT)
            .dateSubmit(DEFAULT_DATE_SUBMIT)
            .dateAcct(DEFAULT_DATE_ACCT)
            .withholdingAmt(DEFAULT_WITHHOLDING_AMT)
            .invoiceAp(DEFAULT_INVOICE_AP)
            .docType(DEFAULT_DOC_TYPE)
            .payDate(DEFAULT_PAY_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .payAmt(DEFAULT_PAY_AMT)
            .dateReject(DEFAULT_DATE_REJECT)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .payStatus(DEFAULT_PAY_STATUS)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .receiptReversed(DEFAULT_RECEIPT_REVERSED)
            .apReversed(DEFAULT_AP_REVERSED)
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
        mVerification.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerification.setCurrency(cCurrency);
        // Add required entity
        mVerification.setMatchPoCurrency(cCurrency);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVerification.setVendor(cVendor);
        return mVerification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVerification createUpdatedEntity(EntityManager em) {
        MVerification mVerification = new MVerification()
            .description(UPDATED_DESCRIPTION)
            .receiptNo(UPDATED_RECEIPT_NO)
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .taxInvoice(UPDATED_TAX_INVOICE)
            .taxDate(UPDATED_TAX_DATE)
            .totalLines(UPDATED_TOTAL_LINES)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .foreignGrandTotal(UPDATED_FOREIGN_GRAND_TOTAL)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .dateSubmit(UPDATED_DATE_SUBMIT)
            .dateAcct(UPDATED_DATE_ACCT)
            .withholdingAmt(UPDATED_WITHHOLDING_AMT)
            .invoiceAp(UPDATED_INVOICE_AP)
            .docType(UPDATED_DOC_TYPE)
            .payDate(UPDATED_PAY_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .payAmt(UPDATED_PAY_AMT)
            .dateReject(UPDATED_DATE_REJECT)
            .dateApprove(UPDATED_DATE_APPROVE)
            .payStatus(UPDATED_PAY_STATUS)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .receiptReversed(UPDATED_RECEIPT_REVERSED)
            .apReversed(UPDATED_AP_REVERSED)
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
        mVerification.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mVerification.setCurrency(cCurrency);
        // Add required entity
        mVerification.setMatchPoCurrency(cCurrency);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVerification.setVendor(cVendor);
        return mVerification;
    }

    @BeforeEach
    public void initTest() {
        mVerification = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVerification() throws Exception {
        int databaseSizeBeforeCreate = mVerificationRepository.findAll().size();

        // Create the MVerification
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);
        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isCreated());

        // Validate the MVerification in the database
        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        MVerification testMVerification = mVerificationList.get(mVerificationList.size() - 1);
        assertThat(testMVerification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMVerification.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testMVerification.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testMVerification.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testMVerification.getTaxInvoice()).isEqualTo(DEFAULT_TAX_INVOICE);
        assertThat(testMVerification.getTaxDate()).isEqualTo(DEFAULT_TAX_DATE);
        assertThat(testMVerification.getTotalLines()).isEqualTo(DEFAULT_TOTAL_LINES);
        assertThat(testMVerification.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
        assertThat(testMVerification.getForeignGrandTotal()).isEqualTo(DEFAULT_FOREIGN_GRAND_TOTAL);
        assertThat(testMVerification.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testMVerification.getForeignTaxAmount()).isEqualTo(DEFAULT_FOREIGN_TAX_AMOUNT);
        assertThat(testMVerification.getDateSubmit()).isEqualTo(DEFAULT_DATE_SUBMIT);
        assertThat(testMVerification.getDateAcct()).isEqualTo(DEFAULT_DATE_ACCT);
        assertThat(testMVerification.getWithholdingAmt()).isEqualTo(DEFAULT_WITHHOLDING_AMT);
        assertThat(testMVerification.getInvoiceAp()).isEqualTo(DEFAULT_INVOICE_AP);
        assertThat(testMVerification.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testMVerification.getPayDate()).isEqualTo(DEFAULT_PAY_DATE);
        assertThat(testMVerification.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testMVerification.getPayAmt()).isEqualTo(DEFAULT_PAY_AMT);
        assertThat(testMVerification.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMVerification.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMVerification.getPayStatus()).isEqualTo(DEFAULT_PAY_STATUS);
        assertThat(testMVerification.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMVerification.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMVerification.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMVerification.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMVerification.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMVerification.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMVerification.isReceiptReversed()).isEqualTo(DEFAULT_RECEIPT_REVERSED);
        assertThat(testMVerification.isApReversed()).isEqualTo(DEFAULT_AP_REVERSED);
        assertThat(testMVerification.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVerification.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVerificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVerificationRepository.findAll().size();

        // Create the MVerification with an existing ID
        mVerification.setId(1L);
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerification in the database
        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInvoiceNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setInvoiceNo(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setInvoiceDate(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalLinesIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setTotalLines(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrandTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setGrandTotal(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setTaxAmount(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setDateTrx(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setDocumentAction(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVerificationRepository.findAll().size();
        // set the field null
        mVerification.setDocumentStatus(null);

        // Create the MVerification, which fails.
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        restMVerificationMockMvc.perform(post("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVerifications() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList
        restMVerificationMockMvc.perform(get("/api/m-verifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].taxInvoice").value(hasItem(DEFAULT_TAX_INVOICE)))
            .andExpect(jsonPath("$.[*].taxDate").value(hasItem(DEFAULT_TAX_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignGrandTotal").value(hasItem(DEFAULT_FOREIGN_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].dateSubmit").value(hasItem(DEFAULT_DATE_SUBMIT.toString())))
            .andExpect(jsonPath("$.[*].dateAcct").value(hasItem(DEFAULT_DATE_ACCT.toString())))
            .andExpect(jsonPath("$.[*].withholdingAmt").value(hasItem(DEFAULT_WITHHOLDING_AMT.intValue())))
            .andExpect(jsonPath("$.[*].invoiceAp").value(hasItem(DEFAULT_INVOICE_AP)))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].payAmt").value(hasItem(DEFAULT_PAY_AMT.intValue())))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(DEFAULT_DATE_REJECT.toString())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(DEFAULT_DATE_APPROVE.toString())))
            .andExpect(jsonPath("$.[*].payStatus").value(hasItem(DEFAULT_PAY_STATUS)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].receiptReversed").value(hasItem(DEFAULT_RECEIPT_REVERSED.booleanValue())))
            .andExpect(jsonPath("$.[*].apReversed").value(hasItem(DEFAULT_AP_REVERSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVerification() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get the mVerification
        restMVerificationMockMvc.perform(get("/api/m-verifications/{id}", mVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVerification.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.taxInvoice").value(DEFAULT_TAX_INVOICE))
            .andExpect(jsonPath("$.taxDate").value(DEFAULT_TAX_DATE.toString()))
            .andExpect(jsonPath("$.totalLines").value(DEFAULT_TOTAL_LINES.intValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.foreignGrandTotal").value(DEFAULT_FOREIGN_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.foreignTaxAmount").value(DEFAULT_FOREIGN_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.dateSubmit").value(DEFAULT_DATE_SUBMIT.toString()))
            .andExpect(jsonPath("$.dateAcct").value(DEFAULT_DATE_ACCT.toString()))
            .andExpect(jsonPath("$.withholdingAmt").value(DEFAULT_WITHHOLDING_AMT.intValue()))
            .andExpect(jsonPath("$.invoiceAp").value(DEFAULT_INVOICE_AP))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE))
            .andExpect(jsonPath("$.payDate").value(DEFAULT_PAY_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.payAmt").value(DEFAULT_PAY_AMT.intValue()))
            .andExpect(jsonPath("$.dateReject").value(DEFAULT_DATE_REJECT.toString()))
            .andExpect(jsonPath("$.dateApprove").value(DEFAULT_DATE_APPROVE.toString()))
            .andExpect(jsonPath("$.payStatus").value(DEFAULT_PAY_STATUS))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.receiptReversed").value(DEFAULT_RECEIPT_REVERSED.booleanValue()))
            .andExpect(jsonPath("$.apReversed").value(DEFAULT_AP_REVERSED.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVerificationsByIdFiltering() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        Long id = mVerification.getId();

        defaultMVerificationShouldBeFound("id.equals=" + id);
        defaultMVerificationShouldNotBeFound("id.notEquals=" + id);

        defaultMVerificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVerificationShouldNotBeFound("id.greaterThan=" + id);

        defaultMVerificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVerificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description equals to DEFAULT_DESCRIPTION
        defaultMVerificationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationList where description equals to UPDATED_DESCRIPTION
        defaultMVerificationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description not equals to DEFAULT_DESCRIPTION
        defaultMVerificationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationList where description not equals to UPDATED_DESCRIPTION
        defaultMVerificationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMVerificationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mVerificationList where description equals to UPDATED_DESCRIPTION
        defaultMVerificationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description is not null
        defaultMVerificationShouldBeFound("description.specified=true");

        // Get all the mVerificationList where description is null
        defaultMVerificationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description contains DEFAULT_DESCRIPTION
        defaultMVerificationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationList where description contains UPDATED_DESCRIPTION
        defaultMVerificationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where description does not contain DEFAULT_DESCRIPTION
        defaultMVerificationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mVerificationList where description does not contain UPDATED_DESCRIPTION
        defaultMVerificationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo equals to DEFAULT_RECEIPT_NO
        defaultMVerificationShouldBeFound("receiptNo.equals=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMVerificationShouldNotBeFound("receiptNo.equals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo not equals to DEFAULT_RECEIPT_NO
        defaultMVerificationShouldNotBeFound("receiptNo.notEquals=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationList where receiptNo not equals to UPDATED_RECEIPT_NO
        defaultMVerificationShouldBeFound("receiptNo.notEquals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo in DEFAULT_RECEIPT_NO or UPDATED_RECEIPT_NO
        defaultMVerificationShouldBeFound("receiptNo.in=" + DEFAULT_RECEIPT_NO + "," + UPDATED_RECEIPT_NO);

        // Get all the mVerificationList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultMVerificationShouldNotBeFound("receiptNo.in=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo is not null
        defaultMVerificationShouldBeFound("receiptNo.specified=true");

        // Get all the mVerificationList where receiptNo is null
        defaultMVerificationShouldNotBeFound("receiptNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo contains DEFAULT_RECEIPT_NO
        defaultMVerificationShouldBeFound("receiptNo.contains=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationList where receiptNo contains UPDATED_RECEIPT_NO
        defaultMVerificationShouldNotBeFound("receiptNo.contains=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptNo does not contain DEFAULT_RECEIPT_NO
        defaultMVerificationShouldNotBeFound("receiptNo.doesNotContain=" + DEFAULT_RECEIPT_NO);

        // Get all the mVerificationList where receiptNo does not contain UPDATED_RECEIPT_NO
        defaultMVerificationShouldBeFound("receiptNo.doesNotContain=" + UPDATED_RECEIPT_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo equals to DEFAULT_INVOICE_NO
        defaultMVerificationShouldBeFound("invoiceNo.equals=" + DEFAULT_INVOICE_NO);

        // Get all the mVerificationList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultMVerificationShouldNotBeFound("invoiceNo.equals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo not equals to DEFAULT_INVOICE_NO
        defaultMVerificationShouldNotBeFound("invoiceNo.notEquals=" + DEFAULT_INVOICE_NO);

        // Get all the mVerificationList where invoiceNo not equals to UPDATED_INVOICE_NO
        defaultMVerificationShouldBeFound("invoiceNo.notEquals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo in DEFAULT_INVOICE_NO or UPDATED_INVOICE_NO
        defaultMVerificationShouldBeFound("invoiceNo.in=" + DEFAULT_INVOICE_NO + "," + UPDATED_INVOICE_NO);

        // Get all the mVerificationList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultMVerificationShouldNotBeFound("invoiceNo.in=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo is not null
        defaultMVerificationShouldBeFound("invoiceNo.specified=true");

        // Get all the mVerificationList where invoiceNo is null
        defaultMVerificationShouldNotBeFound("invoiceNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo contains DEFAULT_INVOICE_NO
        defaultMVerificationShouldBeFound("invoiceNo.contains=" + DEFAULT_INVOICE_NO);

        // Get all the mVerificationList where invoiceNo contains UPDATED_INVOICE_NO
        defaultMVerificationShouldNotBeFound("invoiceNo.contains=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceNo does not contain DEFAULT_INVOICE_NO
        defaultMVerificationShouldNotBeFound("invoiceNo.doesNotContain=" + DEFAULT_INVOICE_NO);

        // Get all the mVerificationList where invoiceNo does not contain UPDATED_INVOICE_NO
        defaultMVerificationShouldBeFound("invoiceNo.doesNotContain=" + UPDATED_INVOICE_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate not equals to DEFAULT_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.notEquals=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate not equals to UPDATED_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.notEquals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate is not null
        defaultMVerificationShouldBeFound("invoiceDate.specified=true");

        // Get all the mVerificationList where invoiceDate is null
        defaultMVerificationShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate is greater than or equal to DEFAULT_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.greaterThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate is greater than or equal to UPDATED_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.greaterThanOrEqual=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate is less than or equal to DEFAULT_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.lessThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate is less than or equal to SMALLER_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.lessThanOrEqual=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate is less than DEFAULT_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate is less than UPDATED_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceDate is greater than DEFAULT_INVOICE_DATE
        defaultMVerificationShouldNotBeFound("invoiceDate.greaterThan=" + DEFAULT_INVOICE_DATE);

        // Get all the mVerificationList where invoiceDate is greater than SMALLER_INVOICE_DATE
        defaultMVerificationShouldBeFound("invoiceDate.greaterThan=" + SMALLER_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice equals to DEFAULT_TAX_INVOICE
        defaultMVerificationShouldBeFound("taxInvoice.equals=" + DEFAULT_TAX_INVOICE);

        // Get all the mVerificationList where taxInvoice equals to UPDATED_TAX_INVOICE
        defaultMVerificationShouldNotBeFound("taxInvoice.equals=" + UPDATED_TAX_INVOICE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice not equals to DEFAULT_TAX_INVOICE
        defaultMVerificationShouldNotBeFound("taxInvoice.notEquals=" + DEFAULT_TAX_INVOICE);

        // Get all the mVerificationList where taxInvoice not equals to UPDATED_TAX_INVOICE
        defaultMVerificationShouldBeFound("taxInvoice.notEquals=" + UPDATED_TAX_INVOICE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice in DEFAULT_TAX_INVOICE or UPDATED_TAX_INVOICE
        defaultMVerificationShouldBeFound("taxInvoice.in=" + DEFAULT_TAX_INVOICE + "," + UPDATED_TAX_INVOICE);

        // Get all the mVerificationList where taxInvoice equals to UPDATED_TAX_INVOICE
        defaultMVerificationShouldNotBeFound("taxInvoice.in=" + UPDATED_TAX_INVOICE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice is not null
        defaultMVerificationShouldBeFound("taxInvoice.specified=true");

        // Get all the mVerificationList where taxInvoice is null
        defaultMVerificationShouldNotBeFound("taxInvoice.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice contains DEFAULT_TAX_INVOICE
        defaultMVerificationShouldBeFound("taxInvoice.contains=" + DEFAULT_TAX_INVOICE);

        // Get all the mVerificationList where taxInvoice contains UPDATED_TAX_INVOICE
        defaultMVerificationShouldNotBeFound("taxInvoice.contains=" + UPDATED_TAX_INVOICE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxInvoiceNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxInvoice does not contain DEFAULT_TAX_INVOICE
        defaultMVerificationShouldNotBeFound("taxInvoice.doesNotContain=" + DEFAULT_TAX_INVOICE);

        // Get all the mVerificationList where taxInvoice does not contain UPDATED_TAX_INVOICE
        defaultMVerificationShouldBeFound("taxInvoice.doesNotContain=" + UPDATED_TAX_INVOICE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate equals to DEFAULT_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.equals=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate equals to UPDATED_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.equals=" + UPDATED_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate not equals to DEFAULT_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.notEquals=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate not equals to UPDATED_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.notEquals=" + UPDATED_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate in DEFAULT_TAX_DATE or UPDATED_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.in=" + DEFAULT_TAX_DATE + "," + UPDATED_TAX_DATE);

        // Get all the mVerificationList where taxDate equals to UPDATED_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.in=" + UPDATED_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate is not null
        defaultMVerificationShouldBeFound("taxDate.specified=true");

        // Get all the mVerificationList where taxDate is null
        defaultMVerificationShouldNotBeFound("taxDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate is greater than or equal to DEFAULT_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.greaterThanOrEqual=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate is greater than or equal to UPDATED_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.greaterThanOrEqual=" + UPDATED_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate is less than or equal to DEFAULT_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.lessThanOrEqual=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate is less than or equal to SMALLER_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.lessThanOrEqual=" + SMALLER_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate is less than DEFAULT_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.lessThan=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate is less than UPDATED_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.lessThan=" + UPDATED_TAX_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxDate is greater than DEFAULT_TAX_DATE
        defaultMVerificationShouldNotBeFound("taxDate.greaterThan=" + DEFAULT_TAX_DATE);

        // Get all the mVerificationList where taxDate is greater than SMALLER_TAX_DATE
        defaultMVerificationShouldBeFound("taxDate.greaterThan=" + SMALLER_TAX_DATE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines equals to DEFAULT_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.equals=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.equals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines not equals to DEFAULT_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.notEquals=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines not equals to UPDATED_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.notEquals=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines in DEFAULT_TOTAL_LINES or UPDATED_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.in=" + DEFAULT_TOTAL_LINES + "," + UPDATED_TOTAL_LINES);

        // Get all the mVerificationList where totalLines equals to UPDATED_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.in=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines is not null
        defaultMVerificationShouldBeFound("totalLines.specified=true");

        // Get all the mVerificationList where totalLines is null
        defaultMVerificationShouldNotBeFound("totalLines.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines is greater than or equal to DEFAULT_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.greaterThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines is greater than or equal to UPDATED_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.greaterThanOrEqual=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines is less than or equal to DEFAULT_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.lessThanOrEqual=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines is less than or equal to SMALLER_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.lessThanOrEqual=" + SMALLER_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines is less than DEFAULT_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.lessThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines is less than UPDATED_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.lessThan=" + UPDATED_TOTAL_LINES);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTotalLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where totalLines is greater than DEFAULT_TOTAL_LINES
        defaultMVerificationShouldNotBeFound("totalLines.greaterThan=" + DEFAULT_TOTAL_LINES);

        // Get all the mVerificationList where totalLines is greater than SMALLER_TOTAL_LINES
        defaultMVerificationShouldBeFound("totalLines.greaterThan=" + SMALLER_TOTAL_LINES);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal equals to DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.equals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.equals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal not equals to DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.notEquals=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal not equals to UPDATED_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.notEquals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal in DEFAULT_GRAND_TOTAL or UPDATED_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.in=" + DEFAULT_GRAND_TOTAL + "," + UPDATED_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.in=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal is not null
        defaultMVerificationShouldBeFound("grandTotal.specified=true");

        // Get all the mVerificationList where grandTotal is null
        defaultMVerificationShouldNotBeFound("grandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal is greater than or equal to DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.greaterThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal is greater than or equal to UPDATED_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.greaterThanOrEqual=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal is less than or equal to DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.lessThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal is less than or equal to SMALLER_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.lessThanOrEqual=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal is less than DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.lessThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal is less than UPDATED_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.lessThan=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where grandTotal is greater than DEFAULT_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("grandTotal.greaterThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the mVerificationList where grandTotal is greater than SMALLER_GRAND_TOTAL
        defaultMVerificationShouldBeFound("grandTotal.greaterThan=" + SMALLER_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal equals to DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.equals=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal equals to UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.equals=" + UPDATED_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal not equals to DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.notEquals=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal not equals to UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.notEquals=" + UPDATED_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal in DEFAULT_FOREIGN_GRAND_TOTAL or UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.in=" + DEFAULT_FOREIGN_GRAND_TOTAL + "," + UPDATED_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal equals to UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.in=" + UPDATED_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal is not null
        defaultMVerificationShouldBeFound("foreignGrandTotal.specified=true");

        // Get all the mVerificationList where foreignGrandTotal is null
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal is greater than or equal to DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.greaterThanOrEqual=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal is greater than or equal to UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.greaterThanOrEqual=" + UPDATED_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal is less than or equal to DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.lessThanOrEqual=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal is less than or equal to SMALLER_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.lessThanOrEqual=" + SMALLER_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal is less than DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.lessThan=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal is less than UPDATED_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.lessThan=" + UPDATED_FOREIGN_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignGrandTotal is greater than DEFAULT_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldNotBeFound("foreignGrandTotal.greaterThan=" + DEFAULT_FOREIGN_GRAND_TOTAL);

        // Get all the mVerificationList where foreignGrandTotal is greater than SMALLER_FOREIGN_GRAND_TOTAL
        defaultMVerificationShouldBeFound("foreignGrandTotal.greaterThan=" + SMALLER_FOREIGN_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount is not null
        defaultMVerificationShouldBeFound("taxAmount.specified=true");

        // Get all the mVerificationList where taxAmount is null
        defaultMVerificationShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the mVerificationList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultMVerificationShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.equals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.equals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount not equals to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.notEquals=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount not equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.notEquals=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount in DEFAULT_FOREIGN_TAX_AMOUNT or UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.in=" + DEFAULT_FOREIGN_TAX_AMOUNT + "," + UPDATED_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount equals to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.in=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount is not null
        defaultMVerificationShouldBeFound("foreignTaxAmount.specified=true");

        // Get all the mVerificationList where foreignTaxAmount is null
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount is greater than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.greaterThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount is greater than or equal to UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.greaterThanOrEqual=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount is less than or equal to DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.lessThanOrEqual=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount is less than or equal to SMALLER_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.lessThanOrEqual=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount is less than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.lessThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount is less than UPDATED_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.lessThan=" + UPDATED_FOREIGN_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByForeignTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where foreignTaxAmount is greater than DEFAULT_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldNotBeFound("foreignTaxAmount.greaterThan=" + DEFAULT_FOREIGN_TAX_AMOUNT);

        // Get all the mVerificationList where foreignTaxAmount is greater than SMALLER_FOREIGN_TAX_AMOUNT
        defaultMVerificationShouldBeFound("foreignTaxAmount.greaterThan=" + SMALLER_FOREIGN_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit equals to DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.equals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.equals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit not equals to DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.notEquals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit not equals to UPDATED_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.notEquals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit in DEFAULT_DATE_SUBMIT or UPDATED_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.in=" + DEFAULT_DATE_SUBMIT + "," + UPDATED_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.in=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit is not null
        defaultMVerificationShouldBeFound("dateSubmit.specified=true");

        // Get all the mVerificationList where dateSubmit is null
        defaultMVerificationShouldNotBeFound("dateSubmit.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit is greater than or equal to DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.greaterThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit is greater than or equal to UPDATED_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.greaterThanOrEqual=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit is less than or equal to DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.lessThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit is less than or equal to SMALLER_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.lessThanOrEqual=" + SMALLER_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit is less than DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.lessThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit is less than UPDATED_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.lessThan=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateSubmitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateSubmit is greater than DEFAULT_DATE_SUBMIT
        defaultMVerificationShouldNotBeFound("dateSubmit.greaterThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mVerificationList where dateSubmit is greater than SMALLER_DATE_SUBMIT
        defaultMVerificationShouldBeFound("dateSubmit.greaterThan=" + SMALLER_DATE_SUBMIT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct equals to DEFAULT_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.equals=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct equals to UPDATED_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.equals=" + UPDATED_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct not equals to DEFAULT_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.notEquals=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct not equals to UPDATED_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.notEquals=" + UPDATED_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct in DEFAULT_DATE_ACCT or UPDATED_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.in=" + DEFAULT_DATE_ACCT + "," + UPDATED_DATE_ACCT);

        // Get all the mVerificationList where dateAcct equals to UPDATED_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.in=" + UPDATED_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct is not null
        defaultMVerificationShouldBeFound("dateAcct.specified=true");

        // Get all the mVerificationList where dateAcct is null
        defaultMVerificationShouldNotBeFound("dateAcct.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct is greater than or equal to DEFAULT_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.greaterThanOrEqual=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct is greater than or equal to UPDATED_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.greaterThanOrEqual=" + UPDATED_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct is less than or equal to DEFAULT_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.lessThanOrEqual=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct is less than or equal to SMALLER_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.lessThanOrEqual=" + SMALLER_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct is less than DEFAULT_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.lessThan=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct is less than UPDATED_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.lessThan=" + UPDATED_DATE_ACCT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateAcctIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateAcct is greater than DEFAULT_DATE_ACCT
        defaultMVerificationShouldNotBeFound("dateAcct.greaterThan=" + DEFAULT_DATE_ACCT);

        // Get all the mVerificationList where dateAcct is greater than SMALLER_DATE_ACCT
        defaultMVerificationShouldBeFound("dateAcct.greaterThan=" + SMALLER_DATE_ACCT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt equals to DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.equals=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt equals to UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.equals=" + UPDATED_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt not equals to DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.notEquals=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt not equals to UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.notEquals=" + UPDATED_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt in DEFAULT_WITHHOLDING_AMT or UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.in=" + DEFAULT_WITHHOLDING_AMT + "," + UPDATED_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt equals to UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.in=" + UPDATED_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt is not null
        defaultMVerificationShouldBeFound("withholdingAmt.specified=true");

        // Get all the mVerificationList where withholdingAmt is null
        defaultMVerificationShouldNotBeFound("withholdingAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt is greater than or equal to DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.greaterThanOrEqual=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt is greater than or equal to UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.greaterThanOrEqual=" + UPDATED_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt is less than or equal to DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.lessThanOrEqual=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt is less than or equal to SMALLER_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.lessThanOrEqual=" + SMALLER_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt is less than DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.lessThan=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt is less than UPDATED_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.lessThan=" + UPDATED_WITHHOLDING_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByWithholdingAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where withholdingAmt is greater than DEFAULT_WITHHOLDING_AMT
        defaultMVerificationShouldNotBeFound("withholdingAmt.greaterThan=" + DEFAULT_WITHHOLDING_AMT);

        // Get all the mVerificationList where withholdingAmt is greater than SMALLER_WITHHOLDING_AMT
        defaultMVerificationShouldBeFound("withholdingAmt.greaterThan=" + SMALLER_WITHHOLDING_AMT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp equals to DEFAULT_INVOICE_AP
        defaultMVerificationShouldBeFound("invoiceAp.equals=" + DEFAULT_INVOICE_AP);

        // Get all the mVerificationList where invoiceAp equals to UPDATED_INVOICE_AP
        defaultMVerificationShouldNotBeFound("invoiceAp.equals=" + UPDATED_INVOICE_AP);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp not equals to DEFAULT_INVOICE_AP
        defaultMVerificationShouldNotBeFound("invoiceAp.notEquals=" + DEFAULT_INVOICE_AP);

        // Get all the mVerificationList where invoiceAp not equals to UPDATED_INVOICE_AP
        defaultMVerificationShouldBeFound("invoiceAp.notEquals=" + UPDATED_INVOICE_AP);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp in DEFAULT_INVOICE_AP or UPDATED_INVOICE_AP
        defaultMVerificationShouldBeFound("invoiceAp.in=" + DEFAULT_INVOICE_AP + "," + UPDATED_INVOICE_AP);

        // Get all the mVerificationList where invoiceAp equals to UPDATED_INVOICE_AP
        defaultMVerificationShouldNotBeFound("invoiceAp.in=" + UPDATED_INVOICE_AP);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp is not null
        defaultMVerificationShouldBeFound("invoiceAp.specified=true");

        // Get all the mVerificationList where invoiceAp is null
        defaultMVerificationShouldNotBeFound("invoiceAp.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp contains DEFAULT_INVOICE_AP
        defaultMVerificationShouldBeFound("invoiceAp.contains=" + DEFAULT_INVOICE_AP);

        // Get all the mVerificationList where invoiceAp contains UPDATED_INVOICE_AP
        defaultMVerificationShouldNotBeFound("invoiceAp.contains=" + UPDATED_INVOICE_AP);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByInvoiceApNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where invoiceAp does not contain DEFAULT_INVOICE_AP
        defaultMVerificationShouldNotBeFound("invoiceAp.doesNotContain=" + DEFAULT_INVOICE_AP);

        // Get all the mVerificationList where invoiceAp does not contain UPDATED_INVOICE_AP
        defaultMVerificationShouldBeFound("invoiceAp.doesNotContain=" + UPDATED_INVOICE_AP);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType equals to DEFAULT_DOC_TYPE
        defaultMVerificationShouldBeFound("docType.equals=" + DEFAULT_DOC_TYPE);

        // Get all the mVerificationList where docType equals to UPDATED_DOC_TYPE
        defaultMVerificationShouldNotBeFound("docType.equals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType not equals to DEFAULT_DOC_TYPE
        defaultMVerificationShouldNotBeFound("docType.notEquals=" + DEFAULT_DOC_TYPE);

        // Get all the mVerificationList where docType not equals to UPDATED_DOC_TYPE
        defaultMVerificationShouldBeFound("docType.notEquals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType in DEFAULT_DOC_TYPE or UPDATED_DOC_TYPE
        defaultMVerificationShouldBeFound("docType.in=" + DEFAULT_DOC_TYPE + "," + UPDATED_DOC_TYPE);

        // Get all the mVerificationList where docType equals to UPDATED_DOC_TYPE
        defaultMVerificationShouldNotBeFound("docType.in=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType is not null
        defaultMVerificationShouldBeFound("docType.specified=true");

        // Get all the mVerificationList where docType is null
        defaultMVerificationShouldNotBeFound("docType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByDocTypeContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType contains DEFAULT_DOC_TYPE
        defaultMVerificationShouldBeFound("docType.contains=" + DEFAULT_DOC_TYPE);

        // Get all the mVerificationList where docType contains UPDATED_DOC_TYPE
        defaultMVerificationShouldNotBeFound("docType.contains=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where docType does not contain DEFAULT_DOC_TYPE
        defaultMVerificationShouldNotBeFound("docType.doesNotContain=" + DEFAULT_DOC_TYPE);

        // Get all the mVerificationList where docType does not contain UPDATED_DOC_TYPE
        defaultMVerificationShouldBeFound("docType.doesNotContain=" + UPDATED_DOC_TYPE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate equals to DEFAULT_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.equals=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate equals to UPDATED_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.equals=" + UPDATED_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate not equals to DEFAULT_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.notEquals=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate not equals to UPDATED_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.notEquals=" + UPDATED_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate in DEFAULT_PAY_DATE or UPDATED_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.in=" + DEFAULT_PAY_DATE + "," + UPDATED_PAY_DATE);

        // Get all the mVerificationList where payDate equals to UPDATED_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.in=" + UPDATED_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate is not null
        defaultMVerificationShouldBeFound("payDate.specified=true");

        // Get all the mVerificationList where payDate is null
        defaultMVerificationShouldNotBeFound("payDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate is greater than or equal to DEFAULT_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.greaterThanOrEqual=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate is greater than or equal to UPDATED_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.greaterThanOrEqual=" + UPDATED_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate is less than or equal to DEFAULT_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.lessThanOrEqual=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate is less than or equal to SMALLER_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.lessThanOrEqual=" + SMALLER_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate is less than DEFAULT_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.lessThan=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate is less than UPDATED_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.lessThan=" + UPDATED_PAY_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payDate is greater than DEFAULT_PAY_DATE
        defaultMVerificationShouldNotBeFound("payDate.greaterThan=" + DEFAULT_PAY_DATE);

        // Get all the mVerificationList where payDate is greater than SMALLER_PAY_DATE
        defaultMVerificationShouldBeFound("payDate.greaterThan=" + SMALLER_PAY_DATE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate equals to DEFAULT_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate equals to UPDATED_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate not equals to DEFAULT_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate not equals to UPDATED_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the mVerificationList where dueDate equals to UPDATED_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate is not null
        defaultMVerificationShouldBeFound("dueDate.specified=true");

        // Get all the mVerificationList where dueDate is null
        defaultMVerificationShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate is less than DEFAULT_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate is less than UPDATED_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dueDate is greater than DEFAULT_DUE_DATE
        defaultMVerificationShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the mVerificationList where dueDate is greater than SMALLER_DUE_DATE
        defaultMVerificationShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt equals to DEFAULT_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.equals=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt equals to UPDATED_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.equals=" + UPDATED_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt not equals to DEFAULT_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.notEquals=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt not equals to UPDATED_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.notEquals=" + UPDATED_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt in DEFAULT_PAY_AMT or UPDATED_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.in=" + DEFAULT_PAY_AMT + "," + UPDATED_PAY_AMT);

        // Get all the mVerificationList where payAmt equals to UPDATED_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.in=" + UPDATED_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt is not null
        defaultMVerificationShouldBeFound("payAmt.specified=true");

        // Get all the mVerificationList where payAmt is null
        defaultMVerificationShouldNotBeFound("payAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt is greater than or equal to DEFAULT_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.greaterThanOrEqual=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt is greater than or equal to UPDATED_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.greaterThanOrEqual=" + UPDATED_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt is less than or equal to DEFAULT_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.lessThanOrEqual=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt is less than or equal to SMALLER_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.lessThanOrEqual=" + SMALLER_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt is less than DEFAULT_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.lessThan=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt is less than UPDATED_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.lessThan=" + UPDATED_PAY_AMT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payAmt is greater than DEFAULT_PAY_AMT
        defaultMVerificationShouldNotBeFound("payAmt.greaterThan=" + DEFAULT_PAY_AMT);

        // Get all the mVerificationList where payAmt is greater than SMALLER_PAY_AMT
        defaultMVerificationShouldBeFound("payAmt.greaterThan=" + SMALLER_PAY_AMT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mVerificationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject is not null
        defaultMVerificationShouldBeFound("dateReject.specified=true");

        // Get all the mVerificationList where dateReject is null
        defaultMVerificationShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject is less than UPDATED_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMVerificationShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mVerificationList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMVerificationShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove is not null
        defaultMVerificationShouldBeFound("dateApprove.specified=true");

        // Get all the mVerificationList where dateApprove is null
        defaultMVerificationShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMVerificationShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mVerificationList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMVerificationShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByPayStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus equals to DEFAULT_PAY_STATUS
        defaultMVerificationShouldBeFound("payStatus.equals=" + DEFAULT_PAY_STATUS);

        // Get all the mVerificationList where payStatus equals to UPDATED_PAY_STATUS
        defaultMVerificationShouldNotBeFound("payStatus.equals=" + UPDATED_PAY_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus not equals to DEFAULT_PAY_STATUS
        defaultMVerificationShouldNotBeFound("payStatus.notEquals=" + DEFAULT_PAY_STATUS);

        // Get all the mVerificationList where payStatus not equals to UPDATED_PAY_STATUS
        defaultMVerificationShouldBeFound("payStatus.notEquals=" + UPDATED_PAY_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus in DEFAULT_PAY_STATUS or UPDATED_PAY_STATUS
        defaultMVerificationShouldBeFound("payStatus.in=" + DEFAULT_PAY_STATUS + "," + UPDATED_PAY_STATUS);

        // Get all the mVerificationList where payStatus equals to UPDATED_PAY_STATUS
        defaultMVerificationShouldNotBeFound("payStatus.in=" + UPDATED_PAY_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus is not null
        defaultMVerificationShouldBeFound("payStatus.specified=true");

        // Get all the mVerificationList where payStatus is null
        defaultMVerificationShouldNotBeFound("payStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByPayStatusContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus contains DEFAULT_PAY_STATUS
        defaultMVerificationShouldBeFound("payStatus.contains=" + DEFAULT_PAY_STATUS);

        // Get all the mVerificationList where payStatus contains UPDATED_PAY_STATUS
        defaultMVerificationShouldNotBeFound("payStatus.contains=" + UPDATED_PAY_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByPayStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where payStatus does not contain DEFAULT_PAY_STATUS
        defaultMVerificationShouldNotBeFound("payStatus.doesNotContain=" + DEFAULT_PAY_STATUS);

        // Get all the mVerificationList where payStatus does not contain UPDATED_PAY_STATUS
        defaultMVerificationShouldBeFound("payStatus.doesNotContain=" + UPDATED_PAY_STATUS);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mVerificationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx is not null
        defaultMVerificationShouldBeFound("dateTrx.specified=true");

        // Get all the mVerificationList where dateTrx is null
        defaultMVerificationShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx is less than UPDATED_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMVerificationShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mVerificationList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMVerificationShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMVerificationShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVerificationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMVerificationShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMVerificationShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVerificationList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMVerificationShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMVerificationShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mVerificationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMVerificationShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo is not null
        defaultMVerificationShouldBeFound("documentNo.specified=true");

        // Get all the mVerificationList where documentNo is null
        defaultMVerificationShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMVerificationShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVerificationList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMVerificationShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMVerificationShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mVerificationList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMVerificationShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMVerificationShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVerificationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMVerificationShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVerificationList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mVerificationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction is not null
        defaultMVerificationShouldBeFound("documentAction.specified=true");

        // Get all the mVerificationList where documentAction is null
        defaultMVerificationShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMVerificationShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVerificationList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMVerificationShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mVerificationList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMVerificationShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMVerificationShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVerificationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMVerificationShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVerificationList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mVerificationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus is not null
        defaultMVerificationShouldBeFound("documentStatus.specified=true");

        // Get all the mVerificationList where documentStatus is null
        defaultMVerificationShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMVerificationShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVerificationList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMVerificationShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mVerificationList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMVerificationShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMVerificationsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where approved equals to DEFAULT_APPROVED
        defaultMVerificationShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mVerificationList where approved equals to UPDATED_APPROVED
        defaultMVerificationShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where approved not equals to DEFAULT_APPROVED
        defaultMVerificationShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mVerificationList where approved not equals to UPDATED_APPROVED
        defaultMVerificationShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMVerificationShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mVerificationList where approved equals to UPDATED_APPROVED
        defaultMVerificationShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where approved is not null
        defaultMVerificationShouldBeFound("approved.specified=true");

        // Get all the mVerificationList where approved is null
        defaultMVerificationShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where processed equals to DEFAULT_PROCESSED
        defaultMVerificationShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mVerificationList where processed equals to UPDATED_PROCESSED
        defaultMVerificationShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where processed not equals to DEFAULT_PROCESSED
        defaultMVerificationShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mVerificationList where processed not equals to UPDATED_PROCESSED
        defaultMVerificationShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMVerificationShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mVerificationList where processed equals to UPDATED_PROCESSED
        defaultMVerificationShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where processed is not null
        defaultMVerificationShouldBeFound("processed.specified=true");

        // Get all the mVerificationList where processed is null
        defaultMVerificationShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptReversedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptReversed equals to DEFAULT_RECEIPT_REVERSED
        defaultMVerificationShouldBeFound("receiptReversed.equals=" + DEFAULT_RECEIPT_REVERSED);

        // Get all the mVerificationList where receiptReversed equals to UPDATED_RECEIPT_REVERSED
        defaultMVerificationShouldNotBeFound("receiptReversed.equals=" + UPDATED_RECEIPT_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptReversedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptReversed not equals to DEFAULT_RECEIPT_REVERSED
        defaultMVerificationShouldNotBeFound("receiptReversed.notEquals=" + DEFAULT_RECEIPT_REVERSED);

        // Get all the mVerificationList where receiptReversed not equals to UPDATED_RECEIPT_REVERSED
        defaultMVerificationShouldBeFound("receiptReversed.notEquals=" + UPDATED_RECEIPT_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptReversedIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptReversed in DEFAULT_RECEIPT_REVERSED or UPDATED_RECEIPT_REVERSED
        defaultMVerificationShouldBeFound("receiptReversed.in=" + DEFAULT_RECEIPT_REVERSED + "," + UPDATED_RECEIPT_REVERSED);

        // Get all the mVerificationList where receiptReversed equals to UPDATED_RECEIPT_REVERSED
        defaultMVerificationShouldNotBeFound("receiptReversed.in=" + UPDATED_RECEIPT_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByReceiptReversedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where receiptReversed is not null
        defaultMVerificationShouldBeFound("receiptReversed.specified=true");

        // Get all the mVerificationList where receiptReversed is null
        defaultMVerificationShouldNotBeFound("receiptReversed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApReversedIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where apReversed equals to DEFAULT_AP_REVERSED
        defaultMVerificationShouldBeFound("apReversed.equals=" + DEFAULT_AP_REVERSED);

        // Get all the mVerificationList where apReversed equals to UPDATED_AP_REVERSED
        defaultMVerificationShouldNotBeFound("apReversed.equals=" + UPDATED_AP_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApReversedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where apReversed not equals to DEFAULT_AP_REVERSED
        defaultMVerificationShouldNotBeFound("apReversed.notEquals=" + DEFAULT_AP_REVERSED);

        // Get all the mVerificationList where apReversed not equals to UPDATED_AP_REVERSED
        defaultMVerificationShouldBeFound("apReversed.notEquals=" + UPDATED_AP_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApReversedIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where apReversed in DEFAULT_AP_REVERSED or UPDATED_AP_REVERSED
        defaultMVerificationShouldBeFound("apReversed.in=" + DEFAULT_AP_REVERSED + "," + UPDATED_AP_REVERSED);

        // Get all the mVerificationList where apReversed equals to UPDATED_AP_REVERSED
        defaultMVerificationShouldNotBeFound("apReversed.in=" + UPDATED_AP_REVERSED);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByApReversedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where apReversed is not null
        defaultMVerificationShouldBeFound("apReversed.specified=true");

        // Get all the mVerificationList where apReversed is null
        defaultMVerificationShouldNotBeFound("apReversed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where uid equals to DEFAULT_UID
        defaultMVerificationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVerificationList where uid equals to UPDATED_UID
        defaultMVerificationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where uid not equals to DEFAULT_UID
        defaultMVerificationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVerificationList where uid not equals to UPDATED_UID
        defaultMVerificationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVerificationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVerificationList where uid equals to UPDATED_UID
        defaultMVerificationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where uid is not null
        defaultMVerificationShouldBeFound("uid.specified=true");

        // Get all the mVerificationList where uid is null
        defaultMVerificationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where active equals to DEFAULT_ACTIVE
        defaultMVerificationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationList where active equals to UPDATED_ACTIVE
        defaultMVerificationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where active not equals to DEFAULT_ACTIVE
        defaultMVerificationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVerificationList where active not equals to UPDATED_ACTIVE
        defaultMVerificationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVerificationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVerificationList where active equals to UPDATED_ACTIVE
        defaultMVerificationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVerificationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        // Get all the mVerificationList where active is not null
        defaultMVerificationShouldBeFound("active.specified=true");

        // Get all the mVerificationList where active is null
        defaultMVerificationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVerificationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVerification.getAdOrganization();
        mVerificationRepository.saveAndFlush(mVerification);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVerificationList where adOrganization equals to adOrganizationId
        defaultMVerificationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVerificationList where adOrganization equals to adOrganizationId + 1
        defaultMVerificationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mVerification.getCurrency();
        mVerificationRepository.saveAndFlush(mVerification);
        Long currencyId = currency.getId();

        // Get all the mVerificationList where currency equals to currencyId
        defaultMVerificationShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mVerificationList where currency equals to currencyId + 1
        defaultMVerificationShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByMatchPoCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency matchPoCurrency = mVerification.getMatchPoCurrency();
        mVerificationRepository.saveAndFlush(mVerification);
        Long matchPoCurrencyId = matchPoCurrency.getId();

        // Get all the mVerificationList where matchPoCurrency equals to matchPoCurrencyId
        defaultMVerificationShouldBeFound("matchPoCurrencyId.equals=" + matchPoCurrencyId);

        // Get all the mVerificationList where matchPoCurrency equals to matchPoCurrencyId + 1
        defaultMVerificationShouldNotBeFound("matchPoCurrencyId.equals=" + (matchPoCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mVerification.getVendor();
        mVerificationRepository.saveAndFlush(mVerification);
        Long vendorId = vendor.getId();

        // Get all the mVerificationList where vendor equals to vendorId
        defaultMVerificationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mVerificationList where vendor equals to vendorId + 1
        defaultMVerificationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByVendorToIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);
        CVendor vendorTo = CVendorResourceIT.createEntity(em);
        em.persist(vendorTo);
        em.flush();
        mVerification.setVendorTo(vendorTo);
        mVerificationRepository.saveAndFlush(mVerification);
        Long vendorToId = vendorTo.getId();

        // Get all the mVerificationList where vendorTo equals to vendorToId
        defaultMVerificationShouldBeFound("vendorToId.equals=" + vendorToId);

        // Get all the mVerificationList where vendorTo equals to vendorToId + 1
        defaultMVerificationShouldNotBeFound("vendorToId.equals=" + (vendorToId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByVendorLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);
        CVendorLocation vendorLocation = CVendorLocationResourceIT.createEntity(em);
        em.persist(vendorLocation);
        em.flush();
        mVerification.setVendorLocation(vendorLocation);
        mVerificationRepository.saveAndFlush(mVerification);
        Long vendorLocationId = vendorLocation.getId();

        // Get all the mVerificationList where vendorLocation equals to vendorLocationId
        defaultMVerificationShouldBeFound("vendorLocationId.equals=" + vendorLocationId);

        // Get all the mVerificationList where vendorLocation equals to vendorLocationId + 1
        defaultMVerificationShouldNotBeFound("vendorLocationId.equals=" + (vendorLocationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByCTaxCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);
        CTaxCategory cTaxCategory = CTaxCategoryResourceIT.createEntity(em);
        em.persist(cTaxCategory);
        em.flush();
        mVerification.setCTaxCategory(cTaxCategory);
        mVerificationRepository.saveAndFlush(mVerification);
        Long cTaxCategoryId = cTaxCategory.getId();

        // Get all the mVerificationList where cTaxCategory equals to cTaxCategoryId
        defaultMVerificationShouldBeFound("cTaxCategoryId.equals=" + cTaxCategoryId);

        // Get all the mVerificationList where cTaxCategory equals to cTaxCategoryId + 1
        defaultMVerificationShouldNotBeFound("cTaxCategoryId.equals=" + (cTaxCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMVerificationsByCTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);
        CTax cTax = CTaxResourceIT.createEntity(em);
        em.persist(cTax);
        em.flush();
        mVerification.setCTax(cTax);
        mVerificationRepository.saveAndFlush(mVerification);
        Long cTaxId = cTax.getId();

        // Get all the mVerificationList where cTax equals to cTaxId
        defaultMVerificationShouldBeFound("cTaxId.equals=" + cTaxId);

        // Get all the mVerificationList where cTax equals to cTaxId + 1
        defaultMVerificationShouldNotBeFound("cTaxId.equals=" + (cTaxId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVerificationShouldBeFound(String filter) throws Exception {
        restMVerificationMockMvc.perform(get("/api/m-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].taxInvoice").value(hasItem(DEFAULT_TAX_INVOICE)))
            .andExpect(jsonPath("$.[*].taxDate").value(hasItem(DEFAULT_TAX_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalLines").value(hasItem(DEFAULT_TOTAL_LINES.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].foreignGrandTotal").value(hasItem(DEFAULT_FOREIGN_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].foreignTaxAmount").value(hasItem(DEFAULT_FOREIGN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].dateSubmit").value(hasItem(DEFAULT_DATE_SUBMIT.toString())))
            .andExpect(jsonPath("$.[*].dateAcct").value(hasItem(DEFAULT_DATE_ACCT.toString())))
            .andExpect(jsonPath("$.[*].withholdingAmt").value(hasItem(DEFAULT_WITHHOLDING_AMT.intValue())))
            .andExpect(jsonPath("$.[*].invoiceAp").value(hasItem(DEFAULT_INVOICE_AP)))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].payAmt").value(hasItem(DEFAULT_PAY_AMT.intValue())))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(DEFAULT_DATE_REJECT.toString())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(DEFAULT_DATE_APPROVE.toString())))
            .andExpect(jsonPath("$.[*].payStatus").value(hasItem(DEFAULT_PAY_STATUS)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].receiptReversed").value(hasItem(DEFAULT_RECEIPT_REVERSED.booleanValue())))
            .andExpect(jsonPath("$.[*].apReversed").value(hasItem(DEFAULT_AP_REVERSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVerificationMockMvc.perform(get("/api/m-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVerificationShouldNotBeFound(String filter) throws Exception {
        restMVerificationMockMvc.perform(get("/api/m-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVerificationMockMvc.perform(get("/api/m-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVerification() throws Exception {
        // Get the mVerification
        restMVerificationMockMvc.perform(get("/api/m-verifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVerification() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        int databaseSizeBeforeUpdate = mVerificationRepository.findAll().size();

        // Update the mVerification
        MVerification updatedMVerification = mVerificationRepository.findById(mVerification.getId()).get();
        // Disconnect from session so that the updates on updatedMVerification are not directly saved in db
        em.detach(updatedMVerification);
        updatedMVerification
            .description(UPDATED_DESCRIPTION)
            .receiptNo(UPDATED_RECEIPT_NO)
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .taxInvoice(UPDATED_TAX_INVOICE)
            .taxDate(UPDATED_TAX_DATE)
            .totalLines(UPDATED_TOTAL_LINES)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .foreignGrandTotal(UPDATED_FOREIGN_GRAND_TOTAL)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .foreignTaxAmount(UPDATED_FOREIGN_TAX_AMOUNT)
            .dateSubmit(UPDATED_DATE_SUBMIT)
            .dateAcct(UPDATED_DATE_ACCT)
            .withholdingAmt(UPDATED_WITHHOLDING_AMT)
            .invoiceAp(UPDATED_INVOICE_AP)
            .docType(UPDATED_DOC_TYPE)
            .payDate(UPDATED_PAY_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .payAmt(UPDATED_PAY_AMT)
            .dateReject(UPDATED_DATE_REJECT)
            .dateApprove(UPDATED_DATE_APPROVE)
            .payStatus(UPDATED_PAY_STATUS)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .receiptReversed(UPDATED_RECEIPT_REVERSED)
            .apReversed(UPDATED_AP_REVERSED)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(updatedMVerification);

        restMVerificationMockMvc.perform(put("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isOk());

        // Validate the MVerification in the database
        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeUpdate);
        MVerification testMVerification = mVerificationList.get(mVerificationList.size() - 1);
        assertThat(testMVerification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMVerification.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testMVerification.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testMVerification.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testMVerification.getTaxInvoice()).isEqualTo(UPDATED_TAX_INVOICE);
        assertThat(testMVerification.getTaxDate()).isEqualTo(UPDATED_TAX_DATE);
        assertThat(testMVerification.getTotalLines()).isEqualTo(UPDATED_TOTAL_LINES);
        assertThat(testMVerification.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
        assertThat(testMVerification.getForeignGrandTotal()).isEqualTo(UPDATED_FOREIGN_GRAND_TOTAL);
        assertThat(testMVerification.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testMVerification.getForeignTaxAmount()).isEqualTo(UPDATED_FOREIGN_TAX_AMOUNT);
        assertThat(testMVerification.getDateSubmit()).isEqualTo(UPDATED_DATE_SUBMIT);
        assertThat(testMVerification.getDateAcct()).isEqualTo(UPDATED_DATE_ACCT);
        assertThat(testMVerification.getWithholdingAmt()).isEqualTo(UPDATED_WITHHOLDING_AMT);
        assertThat(testMVerification.getInvoiceAp()).isEqualTo(UPDATED_INVOICE_AP);
        assertThat(testMVerification.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testMVerification.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testMVerification.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testMVerification.getPayAmt()).isEqualTo(UPDATED_PAY_AMT);
        assertThat(testMVerification.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMVerification.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMVerification.getPayStatus()).isEqualTo(UPDATED_PAY_STATUS);
        assertThat(testMVerification.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMVerification.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMVerification.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMVerification.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMVerification.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMVerification.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMVerification.isReceiptReversed()).isEqualTo(UPDATED_RECEIPT_REVERSED);
        assertThat(testMVerification.isApReversed()).isEqualTo(UPDATED_AP_REVERSED);
        assertThat(testMVerification.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVerification.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVerification() throws Exception {
        int databaseSizeBeforeUpdate = mVerificationRepository.findAll().size();

        // Create the MVerification
        MVerificationDTO mVerificationDTO = mVerificationMapper.toDto(mVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVerificationMockMvc.perform(put("/api/m-verifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVerification in the database
        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVerification() throws Exception {
        // Initialize the database
        mVerificationRepository.saveAndFlush(mVerification);

        int databaseSizeBeforeDelete = mVerificationRepository.findAll().size();

        // Delete the mVerification
        restMVerificationMockMvc.perform(delete("/api/m-verifications/{id}", mVerification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVerification> mVerificationList = mVerificationRepository.findAll();
        assertThat(mVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
