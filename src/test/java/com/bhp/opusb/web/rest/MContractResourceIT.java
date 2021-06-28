package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.repository.MContractRepository;
import com.bhp.opusb.service.MContractService;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.mapper.MContractMapper;
import com.bhp.opusb.service.dto.MContractCriteria;
import com.bhp.opusb.service.MContractQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.bhp.opusb.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MContractResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_BAN_CODE = false;
    private static final Boolean UPDATED_USE_BAN_CODE = true;

    private static final String DEFAULT_BAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FOR_PRICE_CONFIRMATION = false;
    private static final Boolean UPDATED_FOR_PRICE_CONFIRMATION = true;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EVALUATION_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_PERIOD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TRX = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

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

    private static final ZonedDateTime DEFAULT_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_APPROVE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REJECT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REJECTED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_REASON = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractRepository mContractRepository;

    @Autowired
    private MContractMapper mContractMapper;

    @Autowired
    private MContractService mContractService;

    @Autowired
    private MContractQueryService mContractQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractMockMvc;

    private MContract mContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContract createEntity(EntityManager em) {
        MContract mContract = new MContract()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .useBanCode(DEFAULT_USE_BAN_CODE)
            .banCode(DEFAULT_BAN_CODE)
            .purpose(DEFAULT_PURPOSE)
            .forPriceConfirmation(DEFAULT_FOR_PRICE_CONFIRMATION)
            .startDate(DEFAULT_START_DATE)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .evaluationPeriod(DEFAULT_EVALUATION_PERIOD)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON)
            .price(DEFAULT_PRICE)
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
        mContract.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mContract.setBidding(mBidding);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mContract.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mContract.setDocumentType(cDocumentType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContract.setPic(adUser);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mContract.setVendor(cVendor);
        return mContract;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContract createUpdatedEntity(EntityManager em) {
        MContract mContract = new MContract()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .useBanCode(UPDATED_USE_BAN_CODE)
            .banCode(UPDATED_BAN_CODE)
            .purpose(UPDATED_PURPOSE)
            .forPriceConfirmation(UPDATED_FOR_PRICE_CONFIRMATION)
            .startDate(UPDATED_START_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .evaluationPeriod(UPDATED_EVALUATION_PERIOD)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .price(UPDATED_PRICE)
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
        mContract.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mContract.setBidding(mBidding);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mContract.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mContract.setDocumentType(cDocumentType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContract.setPic(adUser);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mContract.setVendor(cVendor);
        return mContract;
    }

    @BeforeEach
    public void initTest() {
        mContract = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContract() throws Exception {
        int databaseSizeBeforeCreate = mContractRepository.findAll().size();

        // Create the MContract
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);
        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isCreated());

        // Validate the MContract in the database
        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeCreate + 1);
        MContract testMContract = mContractList.get(mContractList.size() - 1);
        assertThat(testMContract.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMContract.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMContract.isUseBanCode()).isEqualTo(DEFAULT_USE_BAN_CODE);
        assertThat(testMContract.getBanCode()).isEqualTo(DEFAULT_BAN_CODE);
        assertThat(testMContract.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testMContract.isForPriceConfirmation()).isEqualTo(DEFAULT_FOR_PRICE_CONFIRMATION);
        assertThat(testMContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMContract.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testMContract.getEvaluationPeriod()).isEqualTo(DEFAULT_EVALUATION_PERIOD);
        assertThat(testMContract.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMContract.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMContract.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMContract.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMContract.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMContract.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMContract.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMContract.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMContract.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMContract.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMContract.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContract.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractRepository.findAll().size();

        // Create the MContract with an existing ID
        mContract.setId(1L);
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContract in the database
        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractRepository.findAll().size();
        // set the field null
        mContract.setName(null);

        // Create the MContract, which fails.
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractRepository.findAll().size();
        // set the field null
        mContract.setStartDate(null);

        // Create the MContract, which fails.
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpirationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractRepository.findAll().size();
        // set the field null
        mContract.setExpirationDate(null);

        // Create the MContract, which fails.
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractRepository.findAll().size();
        // set the field null
        mContract.setDocumentAction(null);

        // Create the MContract, which fails.
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractRepository.findAll().size();
        // set the field null
        mContract.setDocumentStatus(null);

        // Create the MContract, which fails.
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        restMContractMockMvc.perform(post("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContracts() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList
        restMContractMockMvc.perform(get("/api/m-contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].useBanCode").value(hasItem(DEFAULT_USE_BAN_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].banCode").value(hasItem(DEFAULT_BAN_CODE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].forPriceConfirmation").value(hasItem(DEFAULT_FOR_PRICE_CONFIRMATION.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].evaluationPeriod").value(hasItem(DEFAULT_EVALUATION_PERIOD)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContract() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get the mContract
        restMContractMockMvc.perform(get("/api/m-contracts/{id}", mContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContract.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.useBanCode").value(DEFAULT_USE_BAN_CODE.booleanValue()))
            .andExpect(jsonPath("$.banCode").value(DEFAULT_BAN_CODE))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.forPriceConfirmation").value(DEFAULT_FOR_PRICE_CONFIRMATION.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.evaluationPeriod").value(DEFAULT_EVALUATION_PERIOD))
            .andExpect(jsonPath("$.dateTrx").value(sameInstant(DEFAULT_DATE_TRX)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(sameInstant(DEFAULT_DATE_APPROVE)))
            .andExpect(jsonPath("$.dateReject").value(sameInstant(DEFAULT_DATE_REJECT)))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractsByIdFiltering() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        Long id = mContract.getId();

        defaultMContractShouldBeFound("id.equals=" + id);
        defaultMContractShouldNotBeFound("id.notEquals=" + id);

        defaultMContractShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name equals to DEFAULT_NAME
        defaultMContractShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mContractList where name equals to UPDATED_NAME
        defaultMContractShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name not equals to DEFAULT_NAME
        defaultMContractShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mContractList where name not equals to UPDATED_NAME
        defaultMContractShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMContractShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mContractList where name equals to UPDATED_NAME
        defaultMContractShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name is not null
        defaultMContractShouldBeFound("name.specified=true");

        // Get all the mContractList where name is null
        defaultMContractShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByNameContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name contains DEFAULT_NAME
        defaultMContractShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mContractList where name contains UPDATED_NAME
        defaultMContractShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where name does not contain DEFAULT_NAME
        defaultMContractShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mContractList where name does not contain UPDATED_NAME
        defaultMContractShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMContractsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description equals to DEFAULT_DESCRIPTION
        defaultMContractShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mContractList where description equals to UPDATED_DESCRIPTION
        defaultMContractShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description not equals to DEFAULT_DESCRIPTION
        defaultMContractShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mContractList where description not equals to UPDATED_DESCRIPTION
        defaultMContractShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMContractShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mContractList where description equals to UPDATED_DESCRIPTION
        defaultMContractShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description is not null
        defaultMContractShouldBeFound("description.specified=true");

        // Get all the mContractList where description is null
        defaultMContractShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description contains DEFAULT_DESCRIPTION
        defaultMContractShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mContractList where description contains UPDATED_DESCRIPTION
        defaultMContractShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where description does not contain DEFAULT_DESCRIPTION
        defaultMContractShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mContractList where description does not contain UPDATED_DESCRIPTION
        defaultMContractShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMContractsByUseBanCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where useBanCode equals to DEFAULT_USE_BAN_CODE
        defaultMContractShouldBeFound("useBanCode.equals=" + DEFAULT_USE_BAN_CODE);

        // Get all the mContractList where useBanCode equals to UPDATED_USE_BAN_CODE
        defaultMContractShouldNotBeFound("useBanCode.equals=" + UPDATED_USE_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByUseBanCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where useBanCode not equals to DEFAULT_USE_BAN_CODE
        defaultMContractShouldNotBeFound("useBanCode.notEquals=" + DEFAULT_USE_BAN_CODE);

        // Get all the mContractList where useBanCode not equals to UPDATED_USE_BAN_CODE
        defaultMContractShouldBeFound("useBanCode.notEquals=" + UPDATED_USE_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByUseBanCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where useBanCode in DEFAULT_USE_BAN_CODE or UPDATED_USE_BAN_CODE
        defaultMContractShouldBeFound("useBanCode.in=" + DEFAULT_USE_BAN_CODE + "," + UPDATED_USE_BAN_CODE);

        // Get all the mContractList where useBanCode equals to UPDATED_USE_BAN_CODE
        defaultMContractShouldNotBeFound("useBanCode.in=" + UPDATED_USE_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByUseBanCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where useBanCode is not null
        defaultMContractShouldBeFound("useBanCode.specified=true");

        // Get all the mContractList where useBanCode is null
        defaultMContractShouldNotBeFound("useBanCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByBanCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode equals to DEFAULT_BAN_CODE
        defaultMContractShouldBeFound("banCode.equals=" + DEFAULT_BAN_CODE);

        // Get all the mContractList where banCode equals to UPDATED_BAN_CODE
        defaultMContractShouldNotBeFound("banCode.equals=" + UPDATED_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByBanCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode not equals to DEFAULT_BAN_CODE
        defaultMContractShouldNotBeFound("banCode.notEquals=" + DEFAULT_BAN_CODE);

        // Get all the mContractList where banCode not equals to UPDATED_BAN_CODE
        defaultMContractShouldBeFound("banCode.notEquals=" + UPDATED_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByBanCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode in DEFAULT_BAN_CODE or UPDATED_BAN_CODE
        defaultMContractShouldBeFound("banCode.in=" + DEFAULT_BAN_CODE + "," + UPDATED_BAN_CODE);

        // Get all the mContractList where banCode equals to UPDATED_BAN_CODE
        defaultMContractShouldNotBeFound("banCode.in=" + UPDATED_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByBanCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode is not null
        defaultMContractShouldBeFound("banCode.specified=true");

        // Get all the mContractList where banCode is null
        defaultMContractShouldNotBeFound("banCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByBanCodeContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode contains DEFAULT_BAN_CODE
        defaultMContractShouldBeFound("banCode.contains=" + DEFAULT_BAN_CODE);

        // Get all the mContractList where banCode contains UPDATED_BAN_CODE
        defaultMContractShouldNotBeFound("banCode.contains=" + UPDATED_BAN_CODE);
    }

    @Test
    @Transactional
    public void getAllMContractsByBanCodeNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where banCode does not contain DEFAULT_BAN_CODE
        defaultMContractShouldNotBeFound("banCode.doesNotContain=" + DEFAULT_BAN_CODE);

        // Get all the mContractList where banCode does not contain UPDATED_BAN_CODE
        defaultMContractShouldBeFound("banCode.doesNotContain=" + UPDATED_BAN_CODE);
    }


    @Test
    @Transactional
    public void getAllMContractsByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose equals to DEFAULT_PURPOSE
        defaultMContractShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the mContractList where purpose equals to UPDATED_PURPOSE
        defaultMContractShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPurposeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose not equals to DEFAULT_PURPOSE
        defaultMContractShouldNotBeFound("purpose.notEquals=" + DEFAULT_PURPOSE);

        // Get all the mContractList where purpose not equals to UPDATED_PURPOSE
        defaultMContractShouldBeFound("purpose.notEquals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultMContractShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the mContractList where purpose equals to UPDATED_PURPOSE
        defaultMContractShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose is not null
        defaultMContractShouldBeFound("purpose.specified=true");

        // Get all the mContractList where purpose is null
        defaultMContractShouldNotBeFound("purpose.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByPurposeContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose contains DEFAULT_PURPOSE
        defaultMContractShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the mContractList where purpose contains UPDATED_PURPOSE
        defaultMContractShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where purpose does not contain DEFAULT_PURPOSE
        defaultMContractShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the mContractList where purpose does not contain UPDATED_PURPOSE
        defaultMContractShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }


    @Test
    @Transactional
    public void getAllMContractsByForPriceConfirmationIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where forPriceConfirmation equals to DEFAULT_FOR_PRICE_CONFIRMATION
        defaultMContractShouldBeFound("forPriceConfirmation.equals=" + DEFAULT_FOR_PRICE_CONFIRMATION);

        // Get all the mContractList where forPriceConfirmation equals to UPDATED_FOR_PRICE_CONFIRMATION
        defaultMContractShouldNotBeFound("forPriceConfirmation.equals=" + UPDATED_FOR_PRICE_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMContractsByForPriceConfirmationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where forPriceConfirmation not equals to DEFAULT_FOR_PRICE_CONFIRMATION
        defaultMContractShouldNotBeFound("forPriceConfirmation.notEquals=" + DEFAULT_FOR_PRICE_CONFIRMATION);

        // Get all the mContractList where forPriceConfirmation not equals to UPDATED_FOR_PRICE_CONFIRMATION
        defaultMContractShouldBeFound("forPriceConfirmation.notEquals=" + UPDATED_FOR_PRICE_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMContractsByForPriceConfirmationIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where forPriceConfirmation in DEFAULT_FOR_PRICE_CONFIRMATION or UPDATED_FOR_PRICE_CONFIRMATION
        defaultMContractShouldBeFound("forPriceConfirmation.in=" + DEFAULT_FOR_PRICE_CONFIRMATION + "," + UPDATED_FOR_PRICE_CONFIRMATION);

        // Get all the mContractList where forPriceConfirmation equals to UPDATED_FOR_PRICE_CONFIRMATION
        defaultMContractShouldNotBeFound("forPriceConfirmation.in=" + UPDATED_FOR_PRICE_CONFIRMATION);
    }

    @Test
    @Transactional
    public void getAllMContractsByForPriceConfirmationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where forPriceConfirmation is not null
        defaultMContractShouldBeFound("forPriceConfirmation.specified=true");

        // Get all the mContractList where forPriceConfirmation is null
        defaultMContractShouldNotBeFound("forPriceConfirmation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate equals to DEFAULT_START_DATE
        defaultMContractShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate equals to UPDATED_START_DATE
        defaultMContractShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate not equals to DEFAULT_START_DATE
        defaultMContractShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate not equals to UPDATED_START_DATE
        defaultMContractShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMContractShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mContractList where startDate equals to UPDATED_START_DATE
        defaultMContractShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate is not null
        defaultMContractShouldBeFound("startDate.specified=true");

        // Get all the mContractList where startDate is null
        defaultMContractShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMContractShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMContractShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMContractShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate is less than or equal to SMALLER_START_DATE
        defaultMContractShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate is less than DEFAULT_START_DATE
        defaultMContractShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate is less than UPDATED_START_DATE
        defaultMContractShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where startDate is greater than DEFAULT_START_DATE
        defaultMContractShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mContractList where startDate is greater than SMALLER_START_DATE
        defaultMContractShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate is not null
        defaultMContractShouldBeFound("expirationDate.specified=true");

        // Get all the mContractList where expirationDate is null
        defaultMContractShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate is greater than or equal to DEFAULT_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.greaterThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate is greater than or equal to UPDATED_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.greaterThanOrEqual=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate is less than or equal to DEFAULT_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.lessThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate is less than or equal to SMALLER_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.lessThanOrEqual=" + SMALLER_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate is less than DEFAULT_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.lessThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate is less than UPDATED_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.lessThan=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractsByExpirationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where expirationDate is greater than DEFAULT_EXPIRATION_DATE
        defaultMContractShouldNotBeFound("expirationDate.greaterThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the mContractList where expirationDate is greater than SMALLER_EXPIRATION_DATE
        defaultMContractShouldBeFound("expirationDate.greaterThan=" + SMALLER_EXPIRATION_DATE);
    }


    @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod equals to DEFAULT_EVALUATION_PERIOD
        defaultMContractShouldBeFound("evaluationPeriod.equals=" + DEFAULT_EVALUATION_PERIOD);

        // Get all the mContractList where evaluationPeriod equals to UPDATED_EVALUATION_PERIOD
        defaultMContractShouldNotBeFound("evaluationPeriod.equals=" + UPDATED_EVALUATION_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod not equals to DEFAULT_EVALUATION_PERIOD
        defaultMContractShouldNotBeFound("evaluationPeriod.notEquals=" + DEFAULT_EVALUATION_PERIOD);

        // Get all the mContractList where evaluationPeriod not equals to UPDATED_EVALUATION_PERIOD
        defaultMContractShouldBeFound("evaluationPeriod.notEquals=" + UPDATED_EVALUATION_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod in DEFAULT_EVALUATION_PERIOD or UPDATED_EVALUATION_PERIOD
        defaultMContractShouldBeFound("evaluationPeriod.in=" + DEFAULT_EVALUATION_PERIOD + "," + UPDATED_EVALUATION_PERIOD);

        // Get all the mContractList where evaluationPeriod equals to UPDATED_EVALUATION_PERIOD
        defaultMContractShouldNotBeFound("evaluationPeriod.in=" + UPDATED_EVALUATION_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod is not null
        defaultMContractShouldBeFound("evaluationPeriod.specified=true");

        // Get all the mContractList where evaluationPeriod is null
        defaultMContractShouldNotBeFound("evaluationPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod contains DEFAULT_EVALUATION_PERIOD
        defaultMContractShouldBeFound("evaluationPeriod.contains=" + DEFAULT_EVALUATION_PERIOD);

        // Get all the mContractList where evaluationPeriod contains UPDATED_EVALUATION_PERIOD
        defaultMContractShouldNotBeFound("evaluationPeriod.contains=" + UPDATED_EVALUATION_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMContractsByEvaluationPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where evaluationPeriod does not contain DEFAULT_EVALUATION_PERIOD
        defaultMContractShouldNotBeFound("evaluationPeriod.doesNotContain=" + DEFAULT_EVALUATION_PERIOD);

        // Get all the mContractList where evaluationPeriod does not contain UPDATED_EVALUATION_PERIOD
        defaultMContractShouldBeFound("evaluationPeriod.doesNotContain=" + UPDATED_EVALUATION_PERIOD);
    }


    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx equals to UPDATED_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mContractList where dateTrx equals to UPDATED_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx is not null
        defaultMContractShouldBeFound("dateTrx.specified=true");

        // Get all the mContractList where dateTrx is null
        defaultMContractShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx is less than UPDATED_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMContractShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mContractList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMContractShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMContractsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMContractShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mContractList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMContractShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMContractShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mContractList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMContractShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMContractShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mContractList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMContractShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo is not null
        defaultMContractShouldBeFound("documentNo.specified=true");

        // Get all the mContractList where documentNo is null
        defaultMContractShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMContractShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mContractList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMContractShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMContractShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mContractList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMContractShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMContractsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMContractShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMContractShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mContractList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction is not null
        defaultMContractShouldBeFound("documentAction.specified=true");

        // Get all the mContractList where documentAction is null
        defaultMContractShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMContractShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMContractShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMContractShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMContractShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMContractsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMContractShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMContractShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mContractList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus is not null
        defaultMContractShouldBeFound("documentStatus.specified=true");

        // Get all the mContractList where documentStatus is null
        defaultMContractShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMContractShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMContractShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMContractShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMContractShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMContractsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where approved equals to DEFAULT_APPROVED
        defaultMContractShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mContractList where approved equals to UPDATED_APPROVED
        defaultMContractShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMContractsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where approved not equals to DEFAULT_APPROVED
        defaultMContractShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mContractList where approved not equals to UPDATED_APPROVED
        defaultMContractShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMContractsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMContractShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mContractList where approved equals to UPDATED_APPROVED
        defaultMContractShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMContractsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where approved is not null
        defaultMContractShouldBeFound("approved.specified=true");

        // Get all the mContractList where approved is null
        defaultMContractShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where processed equals to DEFAULT_PROCESSED
        defaultMContractShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mContractList where processed equals to UPDATED_PROCESSED
        defaultMContractShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMContractsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where processed not equals to DEFAULT_PROCESSED
        defaultMContractShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mContractList where processed not equals to UPDATED_PROCESSED
        defaultMContractShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMContractsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMContractShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mContractList where processed equals to UPDATED_PROCESSED
        defaultMContractShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMContractsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where processed is not null
        defaultMContractShouldBeFound("processed.specified=true");

        // Get all the mContractList where processed is null
        defaultMContractShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mContractList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove is not null
        defaultMContractShouldBeFound("dateApprove.specified=true");

        // Get all the mContractList where dateApprove is null
        defaultMContractShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMContractShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mContractList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMContractShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject equals to UPDATED_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mContractList where dateReject equals to UPDATED_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject is not null
        defaultMContractShouldBeFound("dateReject.specified=true");

        // Get all the mContractList where dateReject is null
        defaultMContractShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject is less than UPDATED_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMContractsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMContractShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mContractList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMContractShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMContractsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMContractShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mContractList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMContractShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMContractsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMContractShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mContractList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMContractShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMContractsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMContractShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mContractList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMContractShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMContractsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason is not null
        defaultMContractShouldBeFound("rejectedReason.specified=true");

        // Get all the mContractList where rejectedReason is null
        defaultMContractShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMContractShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mContractList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMContractShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMContractsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMContractShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mContractList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMContractShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMContractsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price equals to DEFAULT_PRICE
        defaultMContractShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mContractList where price equals to UPDATED_PRICE
        defaultMContractShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price not equals to DEFAULT_PRICE
        defaultMContractShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mContractList where price not equals to UPDATED_PRICE
        defaultMContractShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMContractShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mContractList where price equals to UPDATED_PRICE
        defaultMContractShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price is not null
        defaultMContractShouldBeFound("price.specified=true");

        // Get all the mContractList where price is null
        defaultMContractShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price is greater than or equal to DEFAULT_PRICE
        defaultMContractShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mContractList where price is greater than or equal to UPDATED_PRICE
        defaultMContractShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price is less than or equal to DEFAULT_PRICE
        defaultMContractShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mContractList where price is less than or equal to SMALLER_PRICE
        defaultMContractShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price is less than DEFAULT_PRICE
        defaultMContractShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mContractList where price is less than UPDATED_PRICE
        defaultMContractShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where price is greater than DEFAULT_PRICE
        defaultMContractShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mContractList where price is greater than SMALLER_PRICE
        defaultMContractShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMContractsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where uid equals to DEFAULT_UID
        defaultMContractShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractList where uid equals to UPDATED_UID
        defaultMContractShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where uid not equals to DEFAULT_UID
        defaultMContractShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractList where uid not equals to UPDATED_UID
        defaultMContractShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractList where uid equals to UPDATED_UID
        defaultMContractShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where uid is not null
        defaultMContractShouldBeFound("uid.specified=true");

        // Get all the mContractList where uid is null
        defaultMContractShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where active equals to DEFAULT_ACTIVE
        defaultMContractShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractList where active equals to UPDATED_ACTIVE
        defaultMContractShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where active not equals to DEFAULT_ACTIVE
        defaultMContractShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractList where active not equals to UPDATED_ACTIVE
        defaultMContractShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractList where active equals to UPDATED_ACTIVE
        defaultMContractShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        // Get all the mContractList where active is not null
        defaultMContractShouldBeFound("active.specified=true");

        // Get all the mContractList where active is null
        defaultMContractShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContract.getAdOrganization();
        mContractRepository.saveAndFlush(mContract);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractList where adOrganization equals to adOrganizationId
        defaultMContractShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractList where adOrganization equals to adOrganizationId + 1
        defaultMContractShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mContract.getBidding();
        mContractRepository.saveAndFlush(mContract);
        Long biddingId = bidding.getId();

        // Get all the mContractList where bidding equals to biddingId
        defaultMContractShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mContractList where bidding equals to biddingId + 1
        defaultMContractShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mContract.getCostCenter();
        mContractRepository.saveAndFlush(mContract);
        Long costCenterId = costCenter.getId();

        // Get all the mContractList where costCenter equals to costCenterId
        defaultMContractShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mContractList where costCenter equals to costCenterId + 1
        defaultMContractShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = mContract.getDocumentType();
        mContractRepository.saveAndFlush(mContract);
        Long documentTypeId = documentType.getId();

        // Get all the mContractList where documentType equals to documentTypeId
        defaultMContractShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mContractList where documentType equals to documentTypeId + 1
        defaultMContractShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByPicIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser pic = mContract.getPic();
        mContractRepository.saveAndFlush(mContract);
        Long picId = pic.getId();

        // Get all the mContractList where pic equals to picId
        defaultMContractShouldBeFound("picId.equals=" + picId);

        // Get all the mContractList where pic equals to picId + 1
        defaultMContractShouldNotBeFound("picId.equals=" + (picId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mContract.getVendor();
        mContractRepository.saveAndFlush(mContract);
        Long vendorId = vendor.getId();

        // Get all the mContractList where vendor equals to vendorId
        defaultMContractShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mContractList where vendor equals to vendorId + 1
        defaultMContractShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractsByVendorEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);
        CVendorEvaluation vendorEvaluation = CVendorEvaluationResourceIT.createEntity(em);
        em.persist(vendorEvaluation);
        em.flush();
        mContract.setVendorEvaluation(vendorEvaluation);
        mContractRepository.saveAndFlush(mContract);
        Long vendorEvaluationId = vendorEvaluation.getId();

        // Get all the mContractList where vendorEvaluation equals to vendorEvaluationId
        defaultMContractShouldBeFound("vendorEvaluationId.equals=" + vendorEvaluationId);

        // Get all the mContractList where vendorEvaluation equals to vendorEvaluationId + 1
        defaultMContractShouldNotBeFound("vendorEvaluationId.equals=" + (vendorEvaluationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractShouldBeFound(String filter) throws Exception {
        restMContractMockMvc.perform(get("/api/m-contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].useBanCode").value(hasItem(DEFAULT_USE_BAN_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].banCode").value(hasItem(DEFAULT_BAN_CODE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].forPriceConfirmation").value(hasItem(DEFAULT_FOR_PRICE_CONFIRMATION.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].evaluationPeriod").value(hasItem(DEFAULT_EVALUATION_PERIOD)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractMockMvc.perform(get("/api/m-contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractShouldNotBeFound(String filter) throws Exception {
        restMContractMockMvc.perform(get("/api/m-contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractMockMvc.perform(get("/api/m-contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContract() throws Exception {
        // Get the mContract
        restMContractMockMvc.perform(get("/api/m-contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContract() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        int databaseSizeBeforeUpdate = mContractRepository.findAll().size();

        // Update the mContract
        MContract updatedMContract = mContractRepository.findById(mContract.getId()).get();
        // Disconnect from session so that the updates on updatedMContract are not directly saved in db
        em.detach(updatedMContract);
        updatedMContract
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .useBanCode(UPDATED_USE_BAN_CODE)
            .banCode(UPDATED_BAN_CODE)
            .purpose(UPDATED_PURPOSE)
            .forPriceConfirmation(UPDATED_FOR_PRICE_CONFIRMATION)
            .startDate(UPDATED_START_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .evaluationPeriod(UPDATED_EVALUATION_PERIOD)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .price(UPDATED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractDTO mContractDTO = mContractMapper.toDto(updatedMContract);

        restMContractMockMvc.perform(put("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isOk());

        // Validate the MContract in the database
        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeUpdate);
        MContract testMContract = mContractList.get(mContractList.size() - 1);
        assertThat(testMContract.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMContract.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMContract.isUseBanCode()).isEqualTo(UPDATED_USE_BAN_CODE);
        assertThat(testMContract.getBanCode()).isEqualTo(UPDATED_BAN_CODE);
        assertThat(testMContract.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMContract.isForPriceConfirmation()).isEqualTo(UPDATED_FOR_PRICE_CONFIRMATION);
        assertThat(testMContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMContract.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testMContract.getEvaluationPeriod()).isEqualTo(UPDATED_EVALUATION_PERIOD);
        assertThat(testMContract.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMContract.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMContract.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMContract.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMContract.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMContract.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMContract.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMContract.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMContract.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMContract.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMContract.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContract.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContract() throws Exception {
        int databaseSizeBeforeUpdate = mContractRepository.findAll().size();

        // Create the MContract
        MContractDTO mContractDTO = mContractMapper.toDto(mContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractMockMvc.perform(put("/api/m-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContract in the database
        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContract() throws Exception {
        // Initialize the database
        mContractRepository.saveAndFlush(mContract);

        int databaseSizeBeforeDelete = mContractRepository.findAll().size();

        // Delete the mContract
        restMContractMockMvc.perform(delete("/api/m-contracts/{id}", mContract.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContract> mContractList = mContractRepository.findAll();
        assertThat(mContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
