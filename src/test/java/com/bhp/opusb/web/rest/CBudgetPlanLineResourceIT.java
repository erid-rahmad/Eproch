package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBudgetPlanLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.repository.CBudgetPlanLineRepository;
import com.bhp.opusb.service.CBudgetPlanLineService;
import com.bhp.opusb.service.dto.CBudgetPlanLineDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanLineMapper;
import com.bhp.opusb.service.dto.CBudgetPlanLineCriteria;
import com.bhp.opusb.service.CBudgetPlanLineQueryService;

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
 * Integration tests for the {@link CBudgetPlanLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBudgetPlanLineResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_DEBIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DEBIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DEBIT = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBudgetPlanLineRepository cBudgetPlanLineRepository;

    @Autowired
    private CBudgetPlanLineMapper cBudgetPlanLineMapper;

    @Autowired
    private CBudgetPlanLineService cBudgetPlanLineService;

    @Autowired
    private CBudgetPlanLineQueryService cBudgetPlanLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBudgetPlanLineMockMvc;

    private CBudgetPlanLine cBudgetPlanLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBudgetPlanLine createEntity(EntityManager em) {
        CBudgetPlanLine cBudgetPlanLine = new CBudgetPlanLine()
            .totalDebit(DEFAULT_TOTAL_DEBIT)
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
        cBudgetPlanLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cBudgetPlanLine.setCCurrency(cCurrency);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        cBudgetPlanLine.setCDocumentType(cDocumentType);
        return cBudgetPlanLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBudgetPlanLine createUpdatedEntity(EntityManager em) {
        CBudgetPlanLine cBudgetPlanLine = new CBudgetPlanLine()
            .totalDebit(UPDATED_TOTAL_DEBIT)
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
        cBudgetPlanLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cBudgetPlanLine.setCCurrency(cCurrency);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        cBudgetPlanLine.setCDocumentType(cDocumentType);
        return cBudgetPlanLine;
    }

    @BeforeEach
    public void initTest() {
        cBudgetPlanLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBudgetPlanLine() throws Exception {
        int databaseSizeBeforeCreate = cBudgetPlanLineRepository.findAll().size();

        // Create the CBudgetPlanLine
        CBudgetPlanLineDTO cBudgetPlanLineDTO = cBudgetPlanLineMapper.toDto(cBudgetPlanLine);
        restCBudgetPlanLineMockMvc.perform(post("/api/c-budget-plan-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CBudgetPlanLine in the database
        List<CBudgetPlanLine> cBudgetPlanLineList = cBudgetPlanLineRepository.findAll();
        assertThat(cBudgetPlanLineList).hasSize(databaseSizeBeforeCreate + 1);
        CBudgetPlanLine testCBudgetPlanLine = cBudgetPlanLineList.get(cBudgetPlanLineList.size() - 1);
        assertThat(testCBudgetPlanLine.getTotalDebit()).isEqualTo(DEFAULT_TOTAL_DEBIT);
        assertThat(testCBudgetPlanLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBudgetPlanLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBudgetPlanLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBudgetPlanLineRepository.findAll().size();

        // Create the CBudgetPlanLine with an existing ID
        cBudgetPlanLine.setId(1L);
        CBudgetPlanLineDTO cBudgetPlanLineDTO = cBudgetPlanLineMapper.toDto(cBudgetPlanLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBudgetPlanLineMockMvc.perform(post("/api/c-budget-plan-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBudgetPlanLine in the database
        List<CBudgetPlanLine> cBudgetPlanLineList = cBudgetPlanLineRepository.findAll();
        assertThat(cBudgetPlanLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLines() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBudgetPlanLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDebit").value(hasItem(DEFAULT_TOTAL_DEBIT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBudgetPlanLine() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get the cBudgetPlanLine
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines/{id}", cBudgetPlanLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBudgetPlanLine.getId().intValue()))
            .andExpect(jsonPath("$.totalDebit").value(DEFAULT_TOTAL_DEBIT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBudgetPlanLinesByIdFiltering() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        Long id = cBudgetPlanLine.getId();

        defaultCBudgetPlanLineShouldBeFound("id.equals=" + id);
        defaultCBudgetPlanLineShouldNotBeFound("id.notEquals=" + id);

        defaultCBudgetPlanLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBudgetPlanLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCBudgetPlanLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBudgetPlanLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit equals to DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.equals=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit equals to UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.equals=" + UPDATED_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit not equals to DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.notEquals=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit not equals to UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.notEquals=" + UPDATED_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit in DEFAULT_TOTAL_DEBIT or UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.in=" + DEFAULT_TOTAL_DEBIT + "," + UPDATED_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit equals to UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.in=" + UPDATED_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit is not null
        defaultCBudgetPlanLineShouldBeFound("totalDebit.specified=true");

        // Get all the cBudgetPlanLineList where totalDebit is null
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit is greater than or equal to DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.greaterThanOrEqual=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit is greater than or equal to UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.greaterThanOrEqual=" + UPDATED_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit is less than or equal to DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.lessThanOrEqual=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit is less than or equal to SMALLER_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.lessThanOrEqual=" + SMALLER_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsLessThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit is less than DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.lessThan=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit is less than UPDATED_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.lessThan=" + UPDATED_TOTAL_DEBIT);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByTotalDebitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where totalDebit is greater than DEFAULT_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldNotBeFound("totalDebit.greaterThan=" + DEFAULT_TOTAL_DEBIT);

        // Get all the cBudgetPlanLineList where totalDebit is greater than SMALLER_TOTAL_DEBIT
        defaultCBudgetPlanLineShouldBeFound("totalDebit.greaterThan=" + SMALLER_TOTAL_DEBIT);
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where uid equals to DEFAULT_UID
        defaultCBudgetPlanLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBudgetPlanLineList where uid equals to UPDATED_UID
        defaultCBudgetPlanLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where uid not equals to DEFAULT_UID
        defaultCBudgetPlanLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBudgetPlanLineList where uid not equals to UPDATED_UID
        defaultCBudgetPlanLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBudgetPlanLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBudgetPlanLineList where uid equals to UPDATED_UID
        defaultCBudgetPlanLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where uid is not null
        defaultCBudgetPlanLineShouldBeFound("uid.specified=true");

        // Get all the cBudgetPlanLineList where uid is null
        defaultCBudgetPlanLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where active equals to DEFAULT_ACTIVE
        defaultCBudgetPlanLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBudgetPlanLineList where active equals to UPDATED_ACTIVE
        defaultCBudgetPlanLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where active not equals to DEFAULT_ACTIVE
        defaultCBudgetPlanLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBudgetPlanLineList where active not equals to UPDATED_ACTIVE
        defaultCBudgetPlanLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBudgetPlanLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBudgetPlanLineList where active equals to UPDATED_ACTIVE
        defaultCBudgetPlanLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        // Get all the cBudgetPlanLineList where active is not null
        defaultCBudgetPlanLineShouldBeFound("active.specified=true");

        // Get all the cBudgetPlanLineList where active is null
        defaultCBudgetPlanLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBudgetPlanLine.getAdOrganization();
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBudgetPlanLineList where adOrganization equals to adOrganizationId
        defaultCBudgetPlanLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBudgetPlanLineList where adOrganization equals to adOrganizationId + 1
        defaultCBudgetPlanLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByCCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency cCurrency = cBudgetPlanLine.getCCurrency();
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long cCurrencyId = cCurrency.getId();

        // Get all the cBudgetPlanLineList where cCurrency equals to cCurrencyId
        defaultCBudgetPlanLineShouldBeFound("cCurrencyId.equals=" + cCurrencyId);

        // Get all the cBudgetPlanLineList where cCurrency equals to cCurrencyId + 1
        defaultCBudgetPlanLineShouldNotBeFound("cCurrencyId.equals=" + (cCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByCDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType cDocumentType = cBudgetPlanLine.getCDocumentType();
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long cDocumentTypeId = cDocumentType.getId();

        // Get all the cBudgetPlanLineList where cDocumentType equals to cDocumentTypeId
        defaultCBudgetPlanLineShouldBeFound("cDocumentTypeId.equals=" + cDocumentTypeId);

        // Get all the cBudgetPlanLineList where cDocumentType equals to cDocumentTypeId + 1
        defaultCBudgetPlanLineShouldNotBeFound("cDocumentTypeId.equals=" + (cDocumentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByMBiddingIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        MBidding mBidding = MBiddingResourceIT.createEntity(em);
        em.persist(mBidding);
        em.flush();
        cBudgetPlanLine.setMBidding(mBidding);
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long mBiddingId = mBidding.getId();

        // Get all the cBudgetPlanLineList where mBidding equals to mBiddingId
        defaultCBudgetPlanLineShouldBeFound("mBiddingId.equals=" + mBiddingId);

        // Get all the cBudgetPlanLineList where mBidding equals to mBiddingId + 1
        defaultCBudgetPlanLineShouldNotBeFound("mBiddingId.equals=" + (mBiddingId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByMPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        MPurchaseOrder mPurchaseOrder = MPurchaseOrderResourceIT.createEntity(em);
        em.persist(mPurchaseOrder);
        em.flush();
        cBudgetPlanLine.setMPurchaseOrder(mPurchaseOrder);
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long mPurchaseOrderId = mPurchaseOrder.getId();

        // Get all the cBudgetPlanLineList where mPurchaseOrder equals to mPurchaseOrderId
        defaultCBudgetPlanLineShouldBeFound("mPurchaseOrderId.equals=" + mPurchaseOrderId);

        // Get all the cBudgetPlanLineList where mPurchaseOrder equals to mPurchaseOrderId + 1
        defaultCBudgetPlanLineShouldNotBeFound("mPurchaseOrderId.equals=" + (mPurchaseOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllCBudgetPlanLinesByMRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        MRequisition mRequisition = MRequisitionResourceIT.createEntity(em);
        em.persist(mRequisition);
        em.flush();
        cBudgetPlanLine.setMRequisition(mRequisition);
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);
        Long mRequisitionId = mRequisition.getId();

        // Get all the cBudgetPlanLineList where mRequisition equals to mRequisitionId
        defaultCBudgetPlanLineShouldBeFound("mRequisitionId.equals=" + mRequisitionId);

        // Get all the cBudgetPlanLineList where mRequisition equals to mRequisitionId + 1
        defaultCBudgetPlanLineShouldNotBeFound("mRequisitionId.equals=" + (mRequisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBudgetPlanLineShouldBeFound(String filter) throws Exception {
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBudgetPlanLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDebit").value(hasItem(DEFAULT_TOTAL_DEBIT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBudgetPlanLineShouldNotBeFound(String filter) throws Exception {
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBudgetPlanLine() throws Exception {
        // Get the cBudgetPlanLine
        restCBudgetPlanLineMockMvc.perform(get("/api/c-budget-plan-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBudgetPlanLine() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        int databaseSizeBeforeUpdate = cBudgetPlanLineRepository.findAll().size();

        // Update the cBudgetPlanLine
        CBudgetPlanLine updatedCBudgetPlanLine = cBudgetPlanLineRepository.findById(cBudgetPlanLine.getId()).get();
        // Disconnect from session so that the updates on updatedCBudgetPlanLine are not directly saved in db
        em.detach(updatedCBudgetPlanLine);
        updatedCBudgetPlanLine
            .totalDebit(UPDATED_TOTAL_DEBIT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBudgetPlanLineDTO cBudgetPlanLineDTO = cBudgetPlanLineMapper.toDto(updatedCBudgetPlanLine);

        restCBudgetPlanLineMockMvc.perform(put("/api/c-budget-plan-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanLineDTO)))
            .andExpect(status().isOk());

        // Validate the CBudgetPlanLine in the database
        List<CBudgetPlanLine> cBudgetPlanLineList = cBudgetPlanLineRepository.findAll();
        assertThat(cBudgetPlanLineList).hasSize(databaseSizeBeforeUpdate);
        CBudgetPlanLine testCBudgetPlanLine = cBudgetPlanLineList.get(cBudgetPlanLineList.size() - 1);
        assertThat(testCBudgetPlanLine.getTotalDebit()).isEqualTo(UPDATED_TOTAL_DEBIT);
        assertThat(testCBudgetPlanLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBudgetPlanLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBudgetPlanLine() throws Exception {
        int databaseSizeBeforeUpdate = cBudgetPlanLineRepository.findAll().size();

        // Create the CBudgetPlanLine
        CBudgetPlanLineDTO cBudgetPlanLineDTO = cBudgetPlanLineMapper.toDto(cBudgetPlanLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBudgetPlanLineMockMvc.perform(put("/api/c-budget-plan-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBudgetPlanLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBudgetPlanLine in the database
        List<CBudgetPlanLine> cBudgetPlanLineList = cBudgetPlanLineRepository.findAll();
        assertThat(cBudgetPlanLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBudgetPlanLine() throws Exception {
        // Initialize the database
        cBudgetPlanLineRepository.saveAndFlush(cBudgetPlanLine);

        int databaseSizeBeforeDelete = cBudgetPlanLineRepository.findAll().size();

        // Delete the cBudgetPlanLine
        restCBudgetPlanLineMockMvc.perform(delete("/api/c-budget-plan-lines/{id}", cBudgetPlanLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBudgetPlanLine> cBudgetPlanLineList = cBudgetPlanLineRepository.findAll();
        assertThat(cBudgetPlanLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
