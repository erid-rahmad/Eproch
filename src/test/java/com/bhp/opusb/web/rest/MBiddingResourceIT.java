package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.domain.CEventType;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.service.MBiddingService;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import com.bhp.opusb.service.dto.MBiddingCriteria;
import com.bhp.opusb.service.MBiddingQueryService;

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
 * Integration tests for the {@link MBiddingResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_SELECTION = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_SELECTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CEILING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CEILING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CEILING_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ESTIMATED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ESTIMATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ESTIMATED_PRICE = new BigDecimal(1 - 1);

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

    private static final LocalDate DEFAULT_DATE_APPROVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_APPROVE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REJECT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REJECT = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REJECTED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_REASON = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingRepository mBiddingRepository;

    @Autowired
    private MBiddingMapper mBiddingMapper;

    @Autowired
    private MBiddingService mBiddingService;

    @Autowired
    private MBiddingQueryService mBiddingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingMockMvc;

    private MBidding mBidding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidding createEntity(EntityManager em) {
        MBidding mBidding = new MBidding()
            .name(DEFAULT_NAME)
            .vendorSelection(DEFAULT_VENDOR_SELECTION)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
            .estimatedPrice(DEFAULT_ESTIMATED_PRICE)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON)
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
        mBidding.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mBidding.setCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mBidding.setCurrency(cCurrency);
        // Add required entity
        MRequisition mRequisition;
        if (TestUtil.findAll(em, MRequisition.class).isEmpty()) {
            mRequisition = MRequisitionResourceIT.createEntity(em);
            em.persist(mRequisition);
            em.flush();
        } else {
            mRequisition = TestUtil.findAll(em, MRequisition.class).get(0);
        }
        mBidding.setRequisition(mRequisition);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mBidding.setReferenceType(cDocumentType);
        // Add required entity
        CBiddingType cBiddingType;
        if (TestUtil.findAll(em, CBiddingType.class).isEmpty()) {
            cBiddingType = CBiddingTypeResourceIT.createEntity(em);
            em.persist(cBiddingType);
            em.flush();
        } else {
            cBiddingType = TestUtil.findAll(em, CBiddingType.class).get(0);
        }
        mBidding.setBiddingType(cBiddingType);
        // Add required entity
        CEventType cEventType;
        if (TestUtil.findAll(em, CEventType.class).isEmpty()) {
            cEventType = CEventTypeResourceIT.createEntity(em);
            em.persist(cEventType);
            em.flush();
        } else {
            cEventType = TestUtil.findAll(em, CEventType.class).get(0);
        }
        mBidding.setEventType(cEventType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mBidding.setAdUser(adUser);
        return mBidding;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidding createUpdatedEntity(EntityManager em) {
        MBidding mBidding = new MBidding()
            .name(UPDATED_NAME)
            .vendorSelection(UPDATED_VENDOR_SELECTION)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
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
        mBidding.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mBidding.setCostCenter(cCostCenter);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mBidding.setCurrency(cCurrency);
        // Add required entity
        MRequisition mRequisition;
        if (TestUtil.findAll(em, MRequisition.class).isEmpty()) {
            mRequisition = MRequisitionResourceIT.createUpdatedEntity(em);
            em.persist(mRequisition);
            em.flush();
        } else {
            mRequisition = TestUtil.findAll(em, MRequisition.class).get(0);
        }
        mBidding.setRequisition(mRequisition);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mBidding.setReferenceType(cDocumentType);
        // Add required entity
        CBiddingType cBiddingType;
        if (TestUtil.findAll(em, CBiddingType.class).isEmpty()) {
            cBiddingType = CBiddingTypeResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingType);
            em.flush();
        } else {
            cBiddingType = TestUtil.findAll(em, CBiddingType.class).get(0);
        }
        mBidding.setBiddingType(cBiddingType);
        // Add required entity
        CEventType cEventType;
        if (TestUtil.findAll(em, CEventType.class).isEmpty()) {
            cEventType = CEventTypeResourceIT.createUpdatedEntity(em);
            em.persist(cEventType);
            em.flush();
        } else {
            cEventType = TestUtil.findAll(em, CEventType.class).get(0);
        }
        mBidding.setEventType(cEventType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mBidding.setAdUser(adUser);
        return mBidding;
    }

    @BeforeEach
    public void initTest() {
        mBidding = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBidding() throws Exception {
        int databaseSizeBeforeCreate = mBiddingRepository.findAll().size();

        // Create the MBidding
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);
        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isCreated());

        // Validate the MBidding in the database
        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeCreate + 1);
        MBidding testMBidding = mBiddingList.get(mBiddingList.size() - 1);
        assertThat(testMBidding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMBidding.getVendorSelection()).isEqualTo(DEFAULT_VENDOR_SELECTION);
        assertThat(testMBidding.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMBidding.getEstimatedPrice()).isEqualTo(DEFAULT_ESTIMATED_PRICE);
        assertThat(testMBidding.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMBidding.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMBidding.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMBidding.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMBidding.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMBidding.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMBidding.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMBidding.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMBidding.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMBidding.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBidding.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingRepository.findAll().size();

        // Create the MBidding with an existing ID
        mBidding.setId(1L);
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidding in the database
        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingRepository.findAll().size();
        // set the field null
        mBidding.setName(null);

        // Create the MBidding, which fails.
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingRepository.findAll().size();
        // set the field null
        mBidding.setDocumentNo(null);

        // Create the MBidding, which fails.
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingRepository.findAll().size();
        // set the field null
        mBidding.setDocumentAction(null);

        // Create the MBidding, which fails.
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingRepository.findAll().size();
        // set the field null
        mBidding.setDocumentStatus(null);

        // Create the MBidding, which fails.
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        restMBiddingMockMvc.perform(post("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddings() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList
        restMBiddingMockMvc.perform(get("/api/m-biddings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorSelection").value(hasItem(DEFAULT_VENDOR_SELECTION)))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedPrice").value(hasItem(DEFAULT_ESTIMATED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(DEFAULT_DATE_APPROVE.toString())))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(DEFAULT_DATE_REJECT.toString())))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBidding() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get the mBidding
        restMBiddingMockMvc.perform(get("/api/m-biddings/{id}", mBidding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBidding.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendorSelection").value(DEFAULT_VENDOR_SELECTION))
            .andExpect(jsonPath("$.ceilingPrice").value(DEFAULT_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.estimatedPrice").value(DEFAULT_ESTIMATED_PRICE.intValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(DEFAULT_DATE_APPROVE.toString()))
            .andExpect(jsonPath("$.dateReject").value(DEFAULT_DATE_REJECT.toString()))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        Long id = mBidding.getId();

        defaultMBiddingShouldBeFound("id.equals=" + id);
        defaultMBiddingShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name equals to DEFAULT_NAME
        defaultMBiddingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mBiddingList where name equals to UPDATED_NAME
        defaultMBiddingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name not equals to DEFAULT_NAME
        defaultMBiddingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mBiddingList where name not equals to UPDATED_NAME
        defaultMBiddingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMBiddingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mBiddingList where name equals to UPDATED_NAME
        defaultMBiddingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name is not null
        defaultMBiddingShouldBeFound("name.specified=true");

        // Get all the mBiddingList where name is null
        defaultMBiddingShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByNameContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name contains DEFAULT_NAME
        defaultMBiddingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mBiddingList where name contains UPDATED_NAME
        defaultMBiddingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where name does not contain DEFAULT_NAME
        defaultMBiddingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mBiddingList where name does not contain UPDATED_NAME
        defaultMBiddingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection equals to DEFAULT_VENDOR_SELECTION
        defaultMBiddingShouldBeFound("vendorSelection.equals=" + DEFAULT_VENDOR_SELECTION);

        // Get all the mBiddingList where vendorSelection equals to UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldNotBeFound("vendorSelection.equals=" + UPDATED_VENDOR_SELECTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection not equals to DEFAULT_VENDOR_SELECTION
        defaultMBiddingShouldNotBeFound("vendorSelection.notEquals=" + DEFAULT_VENDOR_SELECTION);

        // Get all the mBiddingList where vendorSelection not equals to UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldBeFound("vendorSelection.notEquals=" + UPDATED_VENDOR_SELECTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection in DEFAULT_VENDOR_SELECTION or UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldBeFound("vendorSelection.in=" + DEFAULT_VENDOR_SELECTION + "," + UPDATED_VENDOR_SELECTION);

        // Get all the mBiddingList where vendorSelection equals to UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldNotBeFound("vendorSelection.in=" + UPDATED_VENDOR_SELECTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection is not null
        defaultMBiddingShouldBeFound("vendorSelection.specified=true");

        // Get all the mBiddingList where vendorSelection is null
        defaultMBiddingShouldNotBeFound("vendorSelection.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection contains DEFAULT_VENDOR_SELECTION
        defaultMBiddingShouldBeFound("vendorSelection.contains=" + DEFAULT_VENDOR_SELECTION);

        // Get all the mBiddingList where vendorSelection contains UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldNotBeFound("vendorSelection.contains=" + UPDATED_VENDOR_SELECTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByVendorSelectionNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where vendorSelection does not contain DEFAULT_VENDOR_SELECTION
        defaultMBiddingShouldNotBeFound("vendorSelection.doesNotContain=" + DEFAULT_VENDOR_SELECTION);

        // Get all the mBiddingList where vendorSelection does not contain UPDATED_VENDOR_SELECTION
        defaultMBiddingShouldBeFound("vendorSelection.doesNotContain=" + UPDATED_VENDOR_SELECTION);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice is not null
        defaultMBiddingShouldBeFound("ceilingPrice.specified=true");

        // Get all the mBiddingList where ceilingPrice is null
        defaultMBiddingShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMBiddingShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMBiddingShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice equals to DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.equals=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice equals to UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.equals=" + UPDATED_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice not equals to DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.notEquals=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice not equals to UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.notEquals=" + UPDATED_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice in DEFAULT_ESTIMATED_PRICE or UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.in=" + DEFAULT_ESTIMATED_PRICE + "," + UPDATED_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice equals to UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.in=" + UPDATED_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice is not null
        defaultMBiddingShouldBeFound("estimatedPrice.specified=true");

        // Get all the mBiddingList where estimatedPrice is null
        defaultMBiddingShouldNotBeFound("estimatedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice is greater than or equal to DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.greaterThanOrEqual=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice is greater than or equal to UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.greaterThanOrEqual=" + UPDATED_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice is less than or equal to DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.lessThanOrEqual=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice is less than or equal to SMALLER_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.lessThanOrEqual=" + SMALLER_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice is less than DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.lessThan=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice is less than UPDATED_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.lessThan=" + UPDATED_ESTIMATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByEstimatedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where estimatedPrice is greater than DEFAULT_ESTIMATED_PRICE
        defaultMBiddingShouldNotBeFound("estimatedPrice.greaterThan=" + DEFAULT_ESTIMATED_PRICE);

        // Get all the mBiddingList where estimatedPrice is greater than SMALLER_ESTIMATED_PRICE
        defaultMBiddingShouldBeFound("estimatedPrice.greaterThan=" + SMALLER_ESTIMATED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mBiddingList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx is not null
        defaultMBiddingShouldBeFound("dateTrx.specified=true");

        // Get all the mBiddingList where dateTrx is null
        defaultMBiddingShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx is less than UPDATED_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMBiddingShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMBiddingShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMBiddingShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBiddingShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMBiddingShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMBiddingShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMBiddingShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mBiddingList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBiddingShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo is not null
        defaultMBiddingShouldBeFound("documentNo.specified=true");

        // Get all the mBiddingList where documentNo is null
        defaultMBiddingShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMBiddingShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMBiddingShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMBiddingShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMBiddingShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMBiddingShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMBiddingShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mBiddingList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction is not null
        defaultMBiddingShouldBeFound("documentAction.specified=true");

        // Get all the mBiddingList where documentAction is null
        defaultMBiddingShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMBiddingShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMBiddingShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMBiddingShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMBiddingShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMBiddingShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mBiddingList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus is not null
        defaultMBiddingShouldBeFound("documentStatus.specified=true");

        // Get all the mBiddingList where documentStatus is null
        defaultMBiddingShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMBiddingShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMBiddingShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMBiddingShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where approved equals to DEFAULT_APPROVED
        defaultMBiddingShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mBiddingList where approved equals to UPDATED_APPROVED
        defaultMBiddingShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where approved not equals to DEFAULT_APPROVED
        defaultMBiddingShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mBiddingList where approved not equals to UPDATED_APPROVED
        defaultMBiddingShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMBiddingShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mBiddingList where approved equals to UPDATED_APPROVED
        defaultMBiddingShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where approved is not null
        defaultMBiddingShouldBeFound("approved.specified=true");

        // Get all the mBiddingList where approved is null
        defaultMBiddingShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where processed equals to DEFAULT_PROCESSED
        defaultMBiddingShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mBiddingList where processed equals to UPDATED_PROCESSED
        defaultMBiddingShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where processed not equals to DEFAULT_PROCESSED
        defaultMBiddingShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mBiddingList where processed not equals to UPDATED_PROCESSED
        defaultMBiddingShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMBiddingShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mBiddingList where processed equals to UPDATED_PROCESSED
        defaultMBiddingShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where processed is not null
        defaultMBiddingShouldBeFound("processed.specified=true");

        // Get all the mBiddingList where processed is null
        defaultMBiddingShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove is not null
        defaultMBiddingShouldBeFound("dateApprove.specified=true");

        // Get all the mBiddingList where dateApprove is null
        defaultMBiddingShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMBiddingShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMBiddingShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject equals to UPDATED_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mBiddingList where dateReject equals to UPDATED_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject is not null
        defaultMBiddingShouldBeFound("dateReject.specified=true");

        // Get all the mBiddingList where dateReject is null
        defaultMBiddingShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject is less than UPDATED_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMBiddingShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMBiddingShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMBiddingShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMBiddingShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMBiddingShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMBiddingShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMBiddingShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mBiddingList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMBiddingShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason is not null
        defaultMBiddingShouldBeFound("rejectedReason.specified=true");

        // Get all the mBiddingList where rejectedReason is null
        defaultMBiddingShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMBiddingShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMBiddingShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMBiddingShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMBiddingShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMBiddingsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where uid equals to DEFAULT_UID
        defaultMBiddingShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingList where uid equals to UPDATED_UID
        defaultMBiddingShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where uid not equals to DEFAULT_UID
        defaultMBiddingShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingList where uid not equals to UPDATED_UID
        defaultMBiddingShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingList where uid equals to UPDATED_UID
        defaultMBiddingShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where uid is not null
        defaultMBiddingShouldBeFound("uid.specified=true");

        // Get all the mBiddingList where uid is null
        defaultMBiddingShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where active equals to DEFAULT_ACTIVE
        defaultMBiddingShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingList where active equals to UPDATED_ACTIVE
        defaultMBiddingShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingList where active not equals to UPDATED_ACTIVE
        defaultMBiddingShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingList where active equals to UPDATED_ACTIVE
        defaultMBiddingShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        // Get all the mBiddingList where active is not null
        defaultMBiddingShouldBeFound("active.specified=true");

        // Get all the mBiddingList where active is null
        defaultMBiddingShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBidding.getAdOrganization();
        mBiddingRepository.saveAndFlush(mBidding);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingList where adOrganization equals to adOrganizationId
        defaultMBiddingShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mBidding.getCostCenter();
        mBiddingRepository.saveAndFlush(mBidding);
        Long costCenterId = costCenter.getId();

        // Get all the mBiddingList where costCenter equals to costCenterId
        defaultMBiddingShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mBiddingList where costCenter equals to costCenterId + 1
        defaultMBiddingShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mBidding.getCurrency();
        mBiddingRepository.saveAndFlush(mBidding);
        Long currencyId = currency.getId();

        // Get all the mBiddingList where currency equals to currencyId
        defaultMBiddingShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mBiddingList where currency equals to currencyId + 1
        defaultMBiddingShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByRequisitionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRequisition requisition = mBidding.getRequisition();
        mBiddingRepository.saveAndFlush(mBidding);
        Long requisitionId = requisition.getId();

        // Get all the mBiddingList where requisition equals to requisitionId
        defaultMBiddingShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the mBiddingList where requisition equals to requisitionId + 1
        defaultMBiddingShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByReferenceTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType referenceType = mBidding.getReferenceType();
        mBiddingRepository.saveAndFlush(mBidding);
        Long referenceTypeId = referenceType.getId();

        // Get all the mBiddingList where referenceType equals to referenceTypeId
        defaultMBiddingShouldBeFound("referenceTypeId.equals=" + referenceTypeId);

        // Get all the mBiddingList where referenceType equals to referenceTypeId + 1
        defaultMBiddingShouldNotBeFound("referenceTypeId.equals=" + (referenceTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByBiddingTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingType biddingType = mBidding.getBiddingType();
        mBiddingRepository.saveAndFlush(mBidding);
        Long biddingTypeId = biddingType.getId();

        // Get all the mBiddingList where biddingType equals to biddingTypeId
        defaultMBiddingShouldBeFound("biddingTypeId.equals=" + biddingTypeId);

        // Get all the mBiddingList where biddingType equals to biddingTypeId + 1
        defaultMBiddingShouldNotBeFound("biddingTypeId.equals=" + (biddingTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByEventTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEventType eventType = mBidding.getEventType();
        mBiddingRepository.saveAndFlush(mBidding);
        Long eventTypeId = eventType.getId();

        // Get all the mBiddingList where eventType equals to eventTypeId
        defaultMBiddingShouldBeFound("eventTypeId.equals=" + eventTypeId);

        // Get all the mBiddingList where eventType equals to eventTypeId + 1
        defaultMBiddingShouldNotBeFound("eventTypeId.equals=" + (eventTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingsByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = mBidding.getAdUser();
        mBiddingRepository.saveAndFlush(mBidding);
        Long adUserId = adUser.getId();

        // Get all the mBiddingList where adUser equals to adUserId
        defaultMBiddingShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the mBiddingList where adUser equals to adUserId + 1
        defaultMBiddingShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingShouldBeFound(String filter) throws Exception {
        restMBiddingMockMvc.perform(get("/api/m-biddings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorSelection").value(hasItem(DEFAULT_VENDOR_SELECTION)))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedPrice").value(hasItem(DEFAULT_ESTIMATED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(DEFAULT_DATE_APPROVE.toString())))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(DEFAULT_DATE_REJECT.toString())))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingMockMvc.perform(get("/api/m-biddings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingShouldNotBeFound(String filter) throws Exception {
        restMBiddingMockMvc.perform(get("/api/m-biddings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingMockMvc.perform(get("/api/m-biddings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBidding() throws Exception {
        // Get the mBidding
        restMBiddingMockMvc.perform(get("/api/m-biddings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBidding() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        int databaseSizeBeforeUpdate = mBiddingRepository.findAll().size();

        // Update the mBidding
        MBidding updatedMBidding = mBiddingRepository.findById(mBidding.getId()).get();
        // Disconnect from session so that the updates on updatedMBidding are not directly saved in db
        em.detach(updatedMBidding);
        updatedMBidding
            .name(UPDATED_NAME)
            .vendorSelection(UPDATED_VENDOR_SELECTION)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(updatedMBidding);

        restMBiddingMockMvc.perform(put("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isOk());

        // Validate the MBidding in the database
        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeUpdate);
        MBidding testMBidding = mBiddingList.get(mBiddingList.size() - 1);
        assertThat(testMBidding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMBidding.getVendorSelection()).isEqualTo(UPDATED_VENDOR_SELECTION);
        assertThat(testMBidding.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMBidding.getEstimatedPrice()).isEqualTo(UPDATED_ESTIMATED_PRICE);
        assertThat(testMBidding.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMBidding.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMBidding.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMBidding.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMBidding.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMBidding.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMBidding.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMBidding.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMBidding.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMBidding.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBidding.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBidding() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingRepository.findAll().size();

        // Create the MBidding
        MBiddingDTO mBiddingDTO = mBiddingMapper.toDto(mBidding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingMockMvc.perform(put("/api/m-biddings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidding in the database
        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBidding() throws Exception {
        // Initialize the database
        mBiddingRepository.saveAndFlush(mBidding);

        int databaseSizeBeforeDelete = mBiddingRepository.findAll().size();

        // Delete the mBidding
        restMBiddingMockMvc.perform(delete("/api/m-biddings/{id}", mBidding.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBidding> mBiddingList = mBiddingRepository.findAll();
        assertThat(mBiddingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
