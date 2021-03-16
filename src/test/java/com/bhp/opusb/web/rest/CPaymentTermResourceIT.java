package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPaymentTerm;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPaymentTermRepository;
import com.bhp.opusb.service.CPaymentTermService;
import com.bhp.opusb.service.dto.CPaymentTermDTO;
import com.bhp.opusb.service.mapper.CPaymentTermMapper;
import com.bhp.opusb.service.dto.CPaymentTermCriteria;
import com.bhp.opusb.service.CPaymentTermQueryService;

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

import com.bhp.opusb.domain.enumeration.CTransactionType;
/**
 * Integration tests for the {@link CPaymentTermResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPaymentTermResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AFTER_DELIVERY = false;
    private static final Boolean UPDATED_AFTER_DELIVERY = true;

    private static final Boolean DEFAULT_AS_DEFAULT = false;
    private static final Boolean UPDATED_AS_DEFAULT = true;

    private static final Boolean DEFAULT_CALCULATE_BUSINESS_DAY = false;
    private static final Boolean UPDATED_CALCULATE_BUSINESS_DAY = true;

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DISCOUNT_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_2 = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT_2 = new BigDecimal(1 - 1);

    private static final Long DEFAULT_DISCOUNT_DAYS = 10L;
    private static final Long UPDATED_DISCOUNT_DAYS = 9L;
    private static final Long SMALLER_DISCOUNT_DAYS = 10L - 1L;

    private static final Long DEFAULT_DISCOUNT_DAYS_2 = 10L;
    private static final Long UPDATED_DISCOUNT_DAYS_2 = 9L;
    private static final Long SMALLER_DISCOUNT_DAYS_2 = 10L - 1L;

    private static final String DEFAULT_DOCUMENT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NOTE = "BBBBBBBBBB";

    private static final Long DEFAULT_FIX_MONTH_CUT_OFF = 10L;
    private static final Long UPDATED_FIX_MONTH_CUT_OFF = 9L;
    private static final Long SMALLER_FIX_MONTH_CUT_OFF = 10L - 1L;

    private static final Long DEFAULT_FIX_MONTH_DAY = 10L;
    private static final Long UPDATED_FIX_MONTH_DAY = 9L;
    private static final Long SMALLER_FIX_MONTH_DAY = 10L - 1L;

    private static final Long DEFAULT_FIX_MONTH_OFFSET = 10L;
    private static final Long UPDATED_FIX_MONTH_OFFSET = 9L;
    private static final Long SMALLER_FIX_MONTH_OFFSET = 10L - 1L;

    private static final Boolean DEFAULT_FIXED_DUE_DATE = false;
    private static final Boolean UPDATED_FIXED_DUE_DATE = true;

    private static final Long DEFAULT_GRACE_DAYS = 10L;
    private static final Long UPDATED_GRACE_DAYS = 9L;
    private static final Long SMALLER_GRACE_DAYS = 10L - 1L;

    private static final Integer DEFAULT_NET_DAY = 1;
    private static final Integer UPDATED_NET_DAY = 0;
    private static final Integer SMALLER_NET_DAY = 1 - 1;

    private static final Long DEFAULT_NET_DAYS = 10L;
    private static final Long UPDATED_NET_DAYS = 9L;
    private static final Long SMALLER_NET_DAYS = 10L - 1L;

    private static final Boolean DEFAULT_ON_NEXT_BUSINESS_DAY = false;
    private static final Boolean UPDATED_ON_NEXT_BUSINESS_DAY = true;

    private static final CTransactionType DEFAULT_TRANSACTION_TYPE = CTransactionType.SALES;
    private static final CTransactionType UPDATED_TRANSACTION_TYPE = CTransactionType.PURCHASE;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPaymentTermRepository cPaymentTermRepository;

    @Autowired
    private CPaymentTermMapper cPaymentTermMapper;

    @Autowired
    private CPaymentTermService cPaymentTermService;

    @Autowired
    private CPaymentTermQueryService cPaymentTermQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPaymentTermMockMvc;

    private CPaymentTerm cPaymentTerm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPaymentTerm createEntity(EntityManager em) {
        CPaymentTerm cPaymentTerm = new CPaymentTerm()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .afterDelivery(DEFAULT_AFTER_DELIVERY)
            .asDefault(DEFAULT_AS_DEFAULT)
            .calculateBusinessDay(DEFAULT_CALCULATE_BUSINESS_DAY)
            .discount(DEFAULT_DISCOUNT)
            .discount2(DEFAULT_DISCOUNT_2)
            .discountDays(DEFAULT_DISCOUNT_DAYS)
            .discountDays2(DEFAULT_DISCOUNT_DAYS_2)
            .documentNote(DEFAULT_DOCUMENT_NOTE)
            .fixMonthCutOff(DEFAULT_FIX_MONTH_CUT_OFF)
            .fixMonthDay(DEFAULT_FIX_MONTH_DAY)
            .fixMonthOffset(DEFAULT_FIX_MONTH_OFFSET)
            .fixedDueDate(DEFAULT_FIXED_DUE_DATE)
            .graceDays(DEFAULT_GRACE_DAYS)
            .netDay(DEFAULT_NET_DAY)
            .netDays(DEFAULT_NET_DAYS)
            .onNextBusinessDay(DEFAULT_ON_NEXT_BUSINESS_DAY)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .valid(DEFAULT_VALID)
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
        cPaymentTerm.setAdOrganization(aDOrganization);
        return cPaymentTerm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPaymentTerm createUpdatedEntity(EntityManager em) {
        CPaymentTerm cPaymentTerm = new CPaymentTerm()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .afterDelivery(UPDATED_AFTER_DELIVERY)
            .asDefault(UPDATED_AS_DEFAULT)
            .calculateBusinessDay(UPDATED_CALCULATE_BUSINESS_DAY)
            .discount(UPDATED_DISCOUNT)
            .discount2(UPDATED_DISCOUNT_2)
            .discountDays(UPDATED_DISCOUNT_DAYS)
            .discountDays2(UPDATED_DISCOUNT_DAYS_2)
            .documentNote(UPDATED_DOCUMENT_NOTE)
            .fixMonthCutOff(UPDATED_FIX_MONTH_CUT_OFF)
            .fixMonthDay(UPDATED_FIX_MONTH_DAY)
            .fixMonthOffset(UPDATED_FIX_MONTH_OFFSET)
            .fixedDueDate(UPDATED_FIXED_DUE_DATE)
            .graceDays(UPDATED_GRACE_DAYS)
            .netDay(UPDATED_NET_DAY)
            .netDays(UPDATED_NET_DAYS)
            .onNextBusinessDay(UPDATED_ON_NEXT_BUSINESS_DAY)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .valid(UPDATED_VALID)
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
        cPaymentTerm.setAdOrganization(aDOrganization);
        return cPaymentTerm;
    }

    @BeforeEach
    public void initTest() {
        cPaymentTerm = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPaymentTerm() throws Exception {
        int databaseSizeBeforeCreate = cPaymentTermRepository.findAll().size();

        // Create the CPaymentTerm
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);
        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isCreated());

        // Validate the CPaymentTerm in the database
        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeCreate + 1);
        CPaymentTerm testCPaymentTerm = cPaymentTermList.get(cPaymentTermList.size() - 1);
        assertThat(testCPaymentTerm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCPaymentTerm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCPaymentTerm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCPaymentTerm.isAfterDelivery()).isEqualTo(DEFAULT_AFTER_DELIVERY);
        assertThat(testCPaymentTerm.isAsDefault()).isEqualTo(DEFAULT_AS_DEFAULT);
        assertThat(testCPaymentTerm.isCalculateBusinessDay()).isEqualTo(DEFAULT_CALCULATE_BUSINESS_DAY);
        assertThat(testCPaymentTerm.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testCPaymentTerm.getDiscount2()).isEqualTo(DEFAULT_DISCOUNT_2);
        assertThat(testCPaymentTerm.getDiscountDays()).isEqualTo(DEFAULT_DISCOUNT_DAYS);
        assertThat(testCPaymentTerm.getDiscountDays2()).isEqualTo(DEFAULT_DISCOUNT_DAYS_2);
        assertThat(testCPaymentTerm.getDocumentNote()).isEqualTo(DEFAULT_DOCUMENT_NOTE);
        assertThat(testCPaymentTerm.getFixMonthCutOff()).isEqualTo(DEFAULT_FIX_MONTH_CUT_OFF);
        assertThat(testCPaymentTerm.getFixMonthDay()).isEqualTo(DEFAULT_FIX_MONTH_DAY);
        assertThat(testCPaymentTerm.getFixMonthOffset()).isEqualTo(DEFAULT_FIX_MONTH_OFFSET);
        assertThat(testCPaymentTerm.isFixedDueDate()).isEqualTo(DEFAULT_FIXED_DUE_DATE);
        assertThat(testCPaymentTerm.getGraceDays()).isEqualTo(DEFAULT_GRACE_DAYS);
        assertThat(testCPaymentTerm.getNetDay()).isEqualTo(DEFAULT_NET_DAY);
        assertThat(testCPaymentTerm.getNetDays()).isEqualTo(DEFAULT_NET_DAYS);
        assertThat(testCPaymentTerm.isOnNextBusinessDay()).isEqualTo(DEFAULT_ON_NEXT_BUSINESS_DAY);
        assertThat(testCPaymentTerm.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testCPaymentTerm.isValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testCPaymentTerm.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPaymentTerm.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPaymentTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPaymentTermRepository.findAll().size();

        // Create the CPaymentTerm with an existing ID
        cPaymentTerm.setId(1L);
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPaymentTerm in the database
        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setCode(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setName(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setDiscount(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscount2IsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setDiscount2(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setDiscountDays(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountDays2IsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setDiscountDays2(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setNetDays(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentTermRepository.findAll().size();
        // set the field null
        cPaymentTerm.setTransactionType(null);

        // Create the CPaymentTerm, which fails.
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        restCPaymentTermMockMvc.perform(post("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPaymentTerms() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPaymentTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].afterDelivery").value(hasItem(DEFAULT_AFTER_DELIVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].asDefault").value(hasItem(DEFAULT_AS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].calculateBusinessDay").value(hasItem(DEFAULT_CALCULATE_BUSINESS_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount2").value(hasItem(DEFAULT_DISCOUNT_2.intValue())))
            .andExpect(jsonPath("$.[*].discountDays").value(hasItem(DEFAULT_DISCOUNT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].discountDays2").value(hasItem(DEFAULT_DISCOUNT_DAYS_2.intValue())))
            .andExpect(jsonPath("$.[*].documentNote").value(hasItem(DEFAULT_DOCUMENT_NOTE)))
            .andExpect(jsonPath("$.[*].fixMonthCutOff").value(hasItem(DEFAULT_FIX_MONTH_CUT_OFF.intValue())))
            .andExpect(jsonPath("$.[*].fixMonthDay").value(hasItem(DEFAULT_FIX_MONTH_DAY.intValue())))
            .andExpect(jsonPath("$.[*].fixMonthOffset").value(hasItem(DEFAULT_FIX_MONTH_OFFSET.intValue())))
            .andExpect(jsonPath("$.[*].fixedDueDate").value(hasItem(DEFAULT_FIXED_DUE_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].netDay").value(hasItem(DEFAULT_NET_DAY)))
            .andExpect(jsonPath("$.[*].netDays").value(hasItem(DEFAULT_NET_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].onNextBusinessDay").value(hasItem(DEFAULT_ON_NEXT_BUSINESS_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPaymentTerm() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get the cPaymentTerm
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms/{id}", cPaymentTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPaymentTerm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.afterDelivery").value(DEFAULT_AFTER_DELIVERY.booleanValue()))
            .andExpect(jsonPath("$.asDefault").value(DEFAULT_AS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.calculateBusinessDay").value(DEFAULT_CALCULATE_BUSINESS_DAY.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.discount2").value(DEFAULT_DISCOUNT_2.intValue()))
            .andExpect(jsonPath("$.discountDays").value(DEFAULT_DISCOUNT_DAYS.intValue()))
            .andExpect(jsonPath("$.discountDays2").value(DEFAULT_DISCOUNT_DAYS_2.intValue()))
            .andExpect(jsonPath("$.documentNote").value(DEFAULT_DOCUMENT_NOTE))
            .andExpect(jsonPath("$.fixMonthCutOff").value(DEFAULT_FIX_MONTH_CUT_OFF.intValue()))
            .andExpect(jsonPath("$.fixMonthDay").value(DEFAULT_FIX_MONTH_DAY.intValue()))
            .andExpect(jsonPath("$.fixMonthOffset").value(DEFAULT_FIX_MONTH_OFFSET.intValue()))
            .andExpect(jsonPath("$.fixedDueDate").value(DEFAULT_FIXED_DUE_DATE.booleanValue()))
            .andExpect(jsonPath("$.graceDays").value(DEFAULT_GRACE_DAYS.intValue()))
            .andExpect(jsonPath("$.netDay").value(DEFAULT_NET_DAY))
            .andExpect(jsonPath("$.netDays").value(DEFAULT_NET_DAYS.intValue()))
            .andExpect(jsonPath("$.onNextBusinessDay").value(DEFAULT_ON_NEXT_BUSINESS_DAY.booleanValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPaymentTermsByIdFiltering() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        Long id = cPaymentTerm.getId();

        defaultCPaymentTermShouldBeFound("id.equals=" + id);
        defaultCPaymentTermShouldNotBeFound("id.notEquals=" + id);

        defaultCPaymentTermShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPaymentTermShouldNotBeFound("id.greaterThan=" + id);

        defaultCPaymentTermShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPaymentTermShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code equals to DEFAULT_CODE
        defaultCPaymentTermShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cPaymentTermList where code equals to UPDATED_CODE
        defaultCPaymentTermShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code not equals to DEFAULT_CODE
        defaultCPaymentTermShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cPaymentTermList where code not equals to UPDATED_CODE
        defaultCPaymentTermShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCPaymentTermShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cPaymentTermList where code equals to UPDATED_CODE
        defaultCPaymentTermShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code is not null
        defaultCPaymentTermShouldBeFound("code.specified=true");

        // Get all the cPaymentTermList where code is null
        defaultCPaymentTermShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPaymentTermsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code contains DEFAULT_CODE
        defaultCPaymentTermShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cPaymentTermList where code contains UPDATED_CODE
        defaultCPaymentTermShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where code does not contain DEFAULT_CODE
        defaultCPaymentTermShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cPaymentTermList where code does not contain UPDATED_CODE
        defaultCPaymentTermShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name equals to DEFAULT_NAME
        defaultCPaymentTermShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cPaymentTermList where name equals to UPDATED_NAME
        defaultCPaymentTermShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name not equals to DEFAULT_NAME
        defaultCPaymentTermShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cPaymentTermList where name not equals to UPDATED_NAME
        defaultCPaymentTermShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCPaymentTermShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cPaymentTermList where name equals to UPDATED_NAME
        defaultCPaymentTermShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name is not null
        defaultCPaymentTermShouldBeFound("name.specified=true");

        // Get all the cPaymentTermList where name is null
        defaultCPaymentTermShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPaymentTermsByNameContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name contains DEFAULT_NAME
        defaultCPaymentTermShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cPaymentTermList where name contains UPDATED_NAME
        defaultCPaymentTermShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where name does not contain DEFAULT_NAME
        defaultCPaymentTermShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cPaymentTermList where name does not contain UPDATED_NAME
        defaultCPaymentTermShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description equals to DEFAULT_DESCRIPTION
        defaultCPaymentTermShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cPaymentTermList where description equals to UPDATED_DESCRIPTION
        defaultCPaymentTermShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description not equals to DEFAULT_DESCRIPTION
        defaultCPaymentTermShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cPaymentTermList where description not equals to UPDATED_DESCRIPTION
        defaultCPaymentTermShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCPaymentTermShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cPaymentTermList where description equals to UPDATED_DESCRIPTION
        defaultCPaymentTermShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description is not null
        defaultCPaymentTermShouldBeFound("description.specified=true");

        // Get all the cPaymentTermList where description is null
        defaultCPaymentTermShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description contains DEFAULT_DESCRIPTION
        defaultCPaymentTermShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cPaymentTermList where description contains UPDATED_DESCRIPTION
        defaultCPaymentTermShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where description does not contain DEFAULT_DESCRIPTION
        defaultCPaymentTermShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cPaymentTermList where description does not contain UPDATED_DESCRIPTION
        defaultCPaymentTermShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByAfterDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where afterDelivery equals to DEFAULT_AFTER_DELIVERY
        defaultCPaymentTermShouldBeFound("afterDelivery.equals=" + DEFAULT_AFTER_DELIVERY);

        // Get all the cPaymentTermList where afterDelivery equals to UPDATED_AFTER_DELIVERY
        defaultCPaymentTermShouldNotBeFound("afterDelivery.equals=" + UPDATED_AFTER_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAfterDeliveryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where afterDelivery not equals to DEFAULT_AFTER_DELIVERY
        defaultCPaymentTermShouldNotBeFound("afterDelivery.notEquals=" + DEFAULT_AFTER_DELIVERY);

        // Get all the cPaymentTermList where afterDelivery not equals to UPDATED_AFTER_DELIVERY
        defaultCPaymentTermShouldBeFound("afterDelivery.notEquals=" + UPDATED_AFTER_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAfterDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where afterDelivery in DEFAULT_AFTER_DELIVERY or UPDATED_AFTER_DELIVERY
        defaultCPaymentTermShouldBeFound("afterDelivery.in=" + DEFAULT_AFTER_DELIVERY + "," + UPDATED_AFTER_DELIVERY);

        // Get all the cPaymentTermList where afterDelivery equals to UPDATED_AFTER_DELIVERY
        defaultCPaymentTermShouldNotBeFound("afterDelivery.in=" + UPDATED_AFTER_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAfterDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where afterDelivery is not null
        defaultCPaymentTermShouldBeFound("afterDelivery.specified=true");

        // Get all the cPaymentTermList where afterDelivery is null
        defaultCPaymentTermShouldNotBeFound("afterDelivery.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAsDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where asDefault equals to DEFAULT_AS_DEFAULT
        defaultCPaymentTermShouldBeFound("asDefault.equals=" + DEFAULT_AS_DEFAULT);

        // Get all the cPaymentTermList where asDefault equals to UPDATED_AS_DEFAULT
        defaultCPaymentTermShouldNotBeFound("asDefault.equals=" + UPDATED_AS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAsDefaultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where asDefault not equals to DEFAULT_AS_DEFAULT
        defaultCPaymentTermShouldNotBeFound("asDefault.notEquals=" + DEFAULT_AS_DEFAULT);

        // Get all the cPaymentTermList where asDefault not equals to UPDATED_AS_DEFAULT
        defaultCPaymentTermShouldBeFound("asDefault.notEquals=" + UPDATED_AS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAsDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where asDefault in DEFAULT_AS_DEFAULT or UPDATED_AS_DEFAULT
        defaultCPaymentTermShouldBeFound("asDefault.in=" + DEFAULT_AS_DEFAULT + "," + UPDATED_AS_DEFAULT);

        // Get all the cPaymentTermList where asDefault equals to UPDATED_AS_DEFAULT
        defaultCPaymentTermShouldNotBeFound("asDefault.in=" + UPDATED_AS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAsDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where asDefault is not null
        defaultCPaymentTermShouldBeFound("asDefault.specified=true");

        // Get all the cPaymentTermList where asDefault is null
        defaultCPaymentTermShouldNotBeFound("asDefault.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCalculateBusinessDayIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where calculateBusinessDay equals to DEFAULT_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("calculateBusinessDay.equals=" + DEFAULT_CALCULATE_BUSINESS_DAY);

        // Get all the cPaymentTermList where calculateBusinessDay equals to UPDATED_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("calculateBusinessDay.equals=" + UPDATED_CALCULATE_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCalculateBusinessDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where calculateBusinessDay not equals to DEFAULT_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("calculateBusinessDay.notEquals=" + DEFAULT_CALCULATE_BUSINESS_DAY);

        // Get all the cPaymentTermList where calculateBusinessDay not equals to UPDATED_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("calculateBusinessDay.notEquals=" + UPDATED_CALCULATE_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCalculateBusinessDayIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where calculateBusinessDay in DEFAULT_CALCULATE_BUSINESS_DAY or UPDATED_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("calculateBusinessDay.in=" + DEFAULT_CALCULATE_BUSINESS_DAY + "," + UPDATED_CALCULATE_BUSINESS_DAY);

        // Get all the cPaymentTermList where calculateBusinessDay equals to UPDATED_CALCULATE_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("calculateBusinessDay.in=" + UPDATED_CALCULATE_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByCalculateBusinessDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where calculateBusinessDay is not null
        defaultCPaymentTermShouldBeFound("calculateBusinessDay.specified=true");

        // Get all the cPaymentTermList where calculateBusinessDay is null
        defaultCPaymentTermShouldNotBeFound("calculateBusinessDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount equals to DEFAULT_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount equals to UPDATED_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount not equals to DEFAULT_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount not equals to UPDATED_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the cPaymentTermList where discount equals to UPDATED_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount is not null
        defaultCPaymentTermShouldBeFound("discount.specified=true");

        // Get all the cPaymentTermList where discount is null
        defaultCPaymentTermShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount is less than or equal to SMALLER_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount is less than DEFAULT_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount is less than UPDATED_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount is greater than DEFAULT_DISCOUNT
        defaultCPaymentTermShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentTermList where discount is greater than SMALLER_DISCOUNT
        defaultCPaymentTermShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 equals to DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.equals=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 equals to UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.equals=" + UPDATED_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 not equals to DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.notEquals=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 not equals to UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.notEquals=" + UPDATED_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 in DEFAULT_DISCOUNT_2 or UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.in=" + DEFAULT_DISCOUNT_2 + "," + UPDATED_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 equals to UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.in=" + UPDATED_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 is not null
        defaultCPaymentTermShouldBeFound("discount2.specified=true");

        // Get all the cPaymentTermList where discount2 is null
        defaultCPaymentTermShouldNotBeFound("discount2.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 is greater than or equal to DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.greaterThanOrEqual=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 is greater than or equal to UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.greaterThanOrEqual=" + UPDATED_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 is less than or equal to DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.lessThanOrEqual=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 is less than or equal to SMALLER_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.lessThanOrEqual=" + SMALLER_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 is less than DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.lessThan=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 is less than UPDATED_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.lessThan=" + UPDATED_DISCOUNT_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscount2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discount2 is greater than DEFAULT_DISCOUNT_2
        defaultCPaymentTermShouldNotBeFound("discount2.greaterThan=" + DEFAULT_DISCOUNT_2);

        // Get all the cPaymentTermList where discount2 is greater than SMALLER_DISCOUNT_2
        defaultCPaymentTermShouldBeFound("discount2.greaterThan=" + SMALLER_DISCOUNT_2);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays equals to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.equals=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.equals=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays not equals to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.notEquals=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays not equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.notEquals=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays in DEFAULT_DISCOUNT_DAYS or UPDATED_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.in=" + DEFAULT_DISCOUNT_DAYS + "," + UPDATED_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.in=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays is not null
        defaultCPaymentTermShouldBeFound("discountDays.specified=true");

        // Get all the cPaymentTermList where discountDays is null
        defaultCPaymentTermShouldNotBeFound("discountDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays is greater than or equal to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.greaterThanOrEqual=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays is greater than or equal to (DEFAULT_DISCOUNT_DAYS + 1)
        defaultCPaymentTermShouldNotBeFound("discountDays.greaterThanOrEqual=" + (DEFAULT_DISCOUNT_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays is less than or equal to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.lessThanOrEqual=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays is less than or equal to SMALLER_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.lessThanOrEqual=" + SMALLER_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays is less than DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.lessThan=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays is less than (DEFAULT_DISCOUNT_DAYS + 1)
        defaultCPaymentTermShouldBeFound("discountDays.lessThan=" + (DEFAULT_DISCOUNT_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays is greater than DEFAULT_DISCOUNT_DAYS
        defaultCPaymentTermShouldNotBeFound("discountDays.greaterThan=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentTermList where discountDays is greater than SMALLER_DISCOUNT_DAYS
        defaultCPaymentTermShouldBeFound("discountDays.greaterThan=" + SMALLER_DISCOUNT_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 equals to DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.equals=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 equals to UPDATED_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.equals=" + UPDATED_DISCOUNT_DAYS_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 not equals to DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.notEquals=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 not equals to UPDATED_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.notEquals=" + UPDATED_DISCOUNT_DAYS_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 in DEFAULT_DISCOUNT_DAYS_2 or UPDATED_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.in=" + DEFAULT_DISCOUNT_DAYS_2 + "," + UPDATED_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 equals to UPDATED_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.in=" + UPDATED_DISCOUNT_DAYS_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 is not null
        defaultCPaymentTermShouldBeFound("discountDays2.specified=true");

        // Get all the cPaymentTermList where discountDays2 is null
        defaultCPaymentTermShouldNotBeFound("discountDays2.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 is greater than or equal to DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.greaterThanOrEqual=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 is greater than or equal to (DEFAULT_DISCOUNT_DAYS_2 + 1)
        defaultCPaymentTermShouldNotBeFound("discountDays2.greaterThanOrEqual=" + (DEFAULT_DISCOUNT_DAYS_2 + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 is less than or equal to DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.lessThanOrEqual=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 is less than or equal to SMALLER_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.lessThanOrEqual=" + SMALLER_DISCOUNT_DAYS_2);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 is less than DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.lessThan=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 is less than (DEFAULT_DISCOUNT_DAYS_2 + 1)
        defaultCPaymentTermShouldBeFound("discountDays2.lessThan=" + (DEFAULT_DISCOUNT_DAYS_2 + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDiscountDays2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where discountDays2 is greater than DEFAULT_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldNotBeFound("discountDays2.greaterThan=" + DEFAULT_DISCOUNT_DAYS_2);

        // Get all the cPaymentTermList where discountDays2 is greater than SMALLER_DISCOUNT_DAYS_2
        defaultCPaymentTermShouldBeFound("discountDays2.greaterThan=" + SMALLER_DISCOUNT_DAYS_2);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote equals to DEFAULT_DOCUMENT_NOTE
        defaultCPaymentTermShouldBeFound("documentNote.equals=" + DEFAULT_DOCUMENT_NOTE);

        // Get all the cPaymentTermList where documentNote equals to UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldNotBeFound("documentNote.equals=" + UPDATED_DOCUMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote not equals to DEFAULT_DOCUMENT_NOTE
        defaultCPaymentTermShouldNotBeFound("documentNote.notEquals=" + DEFAULT_DOCUMENT_NOTE);

        // Get all the cPaymentTermList where documentNote not equals to UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldBeFound("documentNote.notEquals=" + UPDATED_DOCUMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote in DEFAULT_DOCUMENT_NOTE or UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldBeFound("documentNote.in=" + DEFAULT_DOCUMENT_NOTE + "," + UPDATED_DOCUMENT_NOTE);

        // Get all the cPaymentTermList where documentNote equals to UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldNotBeFound("documentNote.in=" + UPDATED_DOCUMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote is not null
        defaultCPaymentTermShouldBeFound("documentNote.specified=true");

        // Get all the cPaymentTermList where documentNote is null
        defaultCPaymentTermShouldNotBeFound("documentNote.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote contains DEFAULT_DOCUMENT_NOTE
        defaultCPaymentTermShouldBeFound("documentNote.contains=" + DEFAULT_DOCUMENT_NOTE);

        // Get all the cPaymentTermList where documentNote contains UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldNotBeFound("documentNote.contains=" + UPDATED_DOCUMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByDocumentNoteNotContainsSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where documentNote does not contain DEFAULT_DOCUMENT_NOTE
        defaultCPaymentTermShouldNotBeFound("documentNote.doesNotContain=" + DEFAULT_DOCUMENT_NOTE);

        // Get all the cPaymentTermList where documentNote does not contain UPDATED_DOCUMENT_NOTE
        defaultCPaymentTermShouldBeFound("documentNote.doesNotContain=" + UPDATED_DOCUMENT_NOTE);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff equals to DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.equals=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff equals to UPDATED_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.equals=" + UPDATED_FIX_MONTH_CUT_OFF);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff not equals to DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.notEquals=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff not equals to UPDATED_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.notEquals=" + UPDATED_FIX_MONTH_CUT_OFF);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff in DEFAULT_FIX_MONTH_CUT_OFF or UPDATED_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.in=" + DEFAULT_FIX_MONTH_CUT_OFF + "," + UPDATED_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff equals to UPDATED_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.in=" + UPDATED_FIX_MONTH_CUT_OFF);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff is not null
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.specified=true");

        // Get all the cPaymentTermList where fixMonthCutOff is null
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff is greater than or equal to DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.greaterThanOrEqual=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff is greater than or equal to (DEFAULT_FIX_MONTH_CUT_OFF + 1)
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.greaterThanOrEqual=" + (DEFAULT_FIX_MONTH_CUT_OFF + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff is less than or equal to DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.lessThanOrEqual=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff is less than or equal to SMALLER_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.lessThanOrEqual=" + SMALLER_FIX_MONTH_CUT_OFF);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff is less than DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.lessThan=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff is less than (DEFAULT_FIX_MONTH_CUT_OFF + 1)
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.lessThan=" + (DEFAULT_FIX_MONTH_CUT_OFF + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthCutOffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthCutOff is greater than DEFAULT_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldNotBeFound("fixMonthCutOff.greaterThan=" + DEFAULT_FIX_MONTH_CUT_OFF);

        // Get all the cPaymentTermList where fixMonthCutOff is greater than SMALLER_FIX_MONTH_CUT_OFF
        defaultCPaymentTermShouldBeFound("fixMonthCutOff.greaterThan=" + SMALLER_FIX_MONTH_CUT_OFF);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay equals to DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.equals=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay equals to UPDATED_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.equals=" + UPDATED_FIX_MONTH_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay not equals to DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.notEquals=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay not equals to UPDATED_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.notEquals=" + UPDATED_FIX_MONTH_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay in DEFAULT_FIX_MONTH_DAY or UPDATED_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.in=" + DEFAULT_FIX_MONTH_DAY + "," + UPDATED_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay equals to UPDATED_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.in=" + UPDATED_FIX_MONTH_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay is not null
        defaultCPaymentTermShouldBeFound("fixMonthDay.specified=true");

        // Get all the cPaymentTermList where fixMonthDay is null
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay is greater than or equal to DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.greaterThanOrEqual=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay is greater than or equal to (DEFAULT_FIX_MONTH_DAY + 1)
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.greaterThanOrEqual=" + (DEFAULT_FIX_MONTH_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay is less than or equal to DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.lessThanOrEqual=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay is less than or equal to SMALLER_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.lessThanOrEqual=" + SMALLER_FIX_MONTH_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay is less than DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.lessThan=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay is less than (DEFAULT_FIX_MONTH_DAY + 1)
        defaultCPaymentTermShouldBeFound("fixMonthDay.lessThan=" + (DEFAULT_FIX_MONTH_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthDay is greater than DEFAULT_FIX_MONTH_DAY
        defaultCPaymentTermShouldNotBeFound("fixMonthDay.greaterThan=" + DEFAULT_FIX_MONTH_DAY);

        // Get all the cPaymentTermList where fixMonthDay is greater than SMALLER_FIX_MONTH_DAY
        defaultCPaymentTermShouldBeFound("fixMonthDay.greaterThan=" + SMALLER_FIX_MONTH_DAY);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset equals to DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.equals=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset equals to UPDATED_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.equals=" + UPDATED_FIX_MONTH_OFFSET);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset not equals to DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.notEquals=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset not equals to UPDATED_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.notEquals=" + UPDATED_FIX_MONTH_OFFSET);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset in DEFAULT_FIX_MONTH_OFFSET or UPDATED_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.in=" + DEFAULT_FIX_MONTH_OFFSET + "," + UPDATED_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset equals to UPDATED_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.in=" + UPDATED_FIX_MONTH_OFFSET);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset is not null
        defaultCPaymentTermShouldBeFound("fixMonthOffset.specified=true");

        // Get all the cPaymentTermList where fixMonthOffset is null
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset is greater than or equal to DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.greaterThanOrEqual=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset is greater than or equal to (DEFAULT_FIX_MONTH_OFFSET + 1)
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.greaterThanOrEqual=" + (DEFAULT_FIX_MONTH_OFFSET + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset is less than or equal to DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.lessThanOrEqual=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset is less than or equal to SMALLER_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.lessThanOrEqual=" + SMALLER_FIX_MONTH_OFFSET);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset is less than DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.lessThan=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset is less than (DEFAULT_FIX_MONTH_OFFSET + 1)
        defaultCPaymentTermShouldBeFound("fixMonthOffset.lessThan=" + (DEFAULT_FIX_MONTH_OFFSET + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixMonthOffsetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixMonthOffset is greater than DEFAULT_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldNotBeFound("fixMonthOffset.greaterThan=" + DEFAULT_FIX_MONTH_OFFSET);

        // Get all the cPaymentTermList where fixMonthOffset is greater than SMALLER_FIX_MONTH_OFFSET
        defaultCPaymentTermShouldBeFound("fixMonthOffset.greaterThan=" + SMALLER_FIX_MONTH_OFFSET);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByFixedDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixedDueDate equals to DEFAULT_FIXED_DUE_DATE
        defaultCPaymentTermShouldBeFound("fixedDueDate.equals=" + DEFAULT_FIXED_DUE_DATE);

        // Get all the cPaymentTermList where fixedDueDate equals to UPDATED_FIXED_DUE_DATE
        defaultCPaymentTermShouldNotBeFound("fixedDueDate.equals=" + UPDATED_FIXED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixedDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixedDueDate not equals to DEFAULT_FIXED_DUE_DATE
        defaultCPaymentTermShouldNotBeFound("fixedDueDate.notEquals=" + DEFAULT_FIXED_DUE_DATE);

        // Get all the cPaymentTermList where fixedDueDate not equals to UPDATED_FIXED_DUE_DATE
        defaultCPaymentTermShouldBeFound("fixedDueDate.notEquals=" + UPDATED_FIXED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixedDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixedDueDate in DEFAULT_FIXED_DUE_DATE or UPDATED_FIXED_DUE_DATE
        defaultCPaymentTermShouldBeFound("fixedDueDate.in=" + DEFAULT_FIXED_DUE_DATE + "," + UPDATED_FIXED_DUE_DATE);

        // Get all the cPaymentTermList where fixedDueDate equals to UPDATED_FIXED_DUE_DATE
        defaultCPaymentTermShouldNotBeFound("fixedDueDate.in=" + UPDATED_FIXED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByFixedDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where fixedDueDate is not null
        defaultCPaymentTermShouldBeFound("fixedDueDate.specified=true");

        // Get all the cPaymentTermList where fixedDueDate is null
        defaultCPaymentTermShouldNotBeFound("fixedDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays equals to DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.equals=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays equals to UPDATED_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.equals=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays not equals to DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.notEquals=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays not equals to UPDATED_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.notEquals=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays in DEFAULT_GRACE_DAYS or UPDATED_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.in=" + DEFAULT_GRACE_DAYS + "," + UPDATED_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays equals to UPDATED_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.in=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays is not null
        defaultCPaymentTermShouldBeFound("graceDays.specified=true");

        // Get all the cPaymentTermList where graceDays is null
        defaultCPaymentTermShouldNotBeFound("graceDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays is greater than or equal to DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.greaterThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays is greater than or equal to (DEFAULT_GRACE_DAYS + 1)
        defaultCPaymentTermShouldNotBeFound("graceDays.greaterThanOrEqual=" + (DEFAULT_GRACE_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays is less than or equal to DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.lessThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays is less than or equal to SMALLER_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.lessThanOrEqual=" + SMALLER_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays is less than DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.lessThan=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays is less than (DEFAULT_GRACE_DAYS + 1)
        defaultCPaymentTermShouldBeFound("graceDays.lessThan=" + (DEFAULT_GRACE_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByGraceDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where graceDays is greater than DEFAULT_GRACE_DAYS
        defaultCPaymentTermShouldNotBeFound("graceDays.greaterThan=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentTermList where graceDays is greater than SMALLER_GRACE_DAYS
        defaultCPaymentTermShouldBeFound("graceDays.greaterThan=" + SMALLER_GRACE_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay equals to DEFAULT_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.equals=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay equals to UPDATED_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.equals=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay not equals to DEFAULT_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.notEquals=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay not equals to UPDATED_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.notEquals=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay in DEFAULT_NET_DAY or UPDATED_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.in=" + DEFAULT_NET_DAY + "," + UPDATED_NET_DAY);

        // Get all the cPaymentTermList where netDay equals to UPDATED_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.in=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay is not null
        defaultCPaymentTermShouldBeFound("netDay.specified=true");

        // Get all the cPaymentTermList where netDay is null
        defaultCPaymentTermShouldNotBeFound("netDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay is greater than or equal to DEFAULT_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.greaterThanOrEqual=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay is greater than or equal to (DEFAULT_NET_DAY + 1)
        defaultCPaymentTermShouldNotBeFound("netDay.greaterThanOrEqual=" + (DEFAULT_NET_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay is less than or equal to DEFAULT_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.lessThanOrEqual=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay is less than or equal to SMALLER_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.lessThanOrEqual=" + SMALLER_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay is less than DEFAULT_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.lessThan=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay is less than (DEFAULT_NET_DAY + 1)
        defaultCPaymentTermShouldBeFound("netDay.lessThan=" + (DEFAULT_NET_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDay is greater than DEFAULT_NET_DAY
        defaultCPaymentTermShouldNotBeFound("netDay.greaterThan=" + DEFAULT_NET_DAY);

        // Get all the cPaymentTermList where netDay is greater than SMALLER_NET_DAY
        defaultCPaymentTermShouldBeFound("netDay.greaterThan=" + SMALLER_NET_DAY);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays equals to DEFAULT_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.equals=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays equals to UPDATED_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.equals=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays not equals to DEFAULT_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.notEquals=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays not equals to UPDATED_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.notEquals=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays in DEFAULT_NET_DAYS or UPDATED_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.in=" + DEFAULT_NET_DAYS + "," + UPDATED_NET_DAYS);

        // Get all the cPaymentTermList where netDays equals to UPDATED_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.in=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays is not null
        defaultCPaymentTermShouldBeFound("netDays.specified=true");

        // Get all the cPaymentTermList where netDays is null
        defaultCPaymentTermShouldNotBeFound("netDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays is greater than or equal to DEFAULT_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.greaterThanOrEqual=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays is greater than or equal to (DEFAULT_NET_DAYS + 1)
        defaultCPaymentTermShouldNotBeFound("netDays.greaterThanOrEqual=" + (DEFAULT_NET_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays is less than or equal to DEFAULT_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.lessThanOrEqual=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays is less than or equal to SMALLER_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.lessThanOrEqual=" + SMALLER_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays is less than DEFAULT_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.lessThan=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays is less than (DEFAULT_NET_DAYS + 1)
        defaultCPaymentTermShouldBeFound("netDays.lessThan=" + (DEFAULT_NET_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByNetDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where netDays is greater than DEFAULT_NET_DAYS
        defaultCPaymentTermShouldNotBeFound("netDays.greaterThan=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentTermList where netDays is greater than SMALLER_NET_DAYS
        defaultCPaymentTermShouldBeFound("netDays.greaterThan=" + SMALLER_NET_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentTermsByOnNextBusinessDayIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where onNextBusinessDay equals to DEFAULT_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("onNextBusinessDay.equals=" + DEFAULT_ON_NEXT_BUSINESS_DAY);

        // Get all the cPaymentTermList where onNextBusinessDay equals to UPDATED_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("onNextBusinessDay.equals=" + UPDATED_ON_NEXT_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByOnNextBusinessDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where onNextBusinessDay not equals to DEFAULT_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("onNextBusinessDay.notEquals=" + DEFAULT_ON_NEXT_BUSINESS_DAY);

        // Get all the cPaymentTermList where onNextBusinessDay not equals to UPDATED_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("onNextBusinessDay.notEquals=" + UPDATED_ON_NEXT_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByOnNextBusinessDayIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where onNextBusinessDay in DEFAULT_ON_NEXT_BUSINESS_DAY or UPDATED_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldBeFound("onNextBusinessDay.in=" + DEFAULT_ON_NEXT_BUSINESS_DAY + "," + UPDATED_ON_NEXT_BUSINESS_DAY);

        // Get all the cPaymentTermList where onNextBusinessDay equals to UPDATED_ON_NEXT_BUSINESS_DAY
        defaultCPaymentTermShouldNotBeFound("onNextBusinessDay.in=" + UPDATED_ON_NEXT_BUSINESS_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByOnNextBusinessDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where onNextBusinessDay is not null
        defaultCPaymentTermShouldBeFound("onNextBusinessDay.specified=true");

        // Get all the cPaymentTermList where onNextBusinessDay is null
        defaultCPaymentTermShouldNotBeFound("onNextBusinessDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultCPaymentTermShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the cPaymentTermList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultCPaymentTermShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultCPaymentTermShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the cPaymentTermList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultCPaymentTermShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultCPaymentTermShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the cPaymentTermList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultCPaymentTermShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where transactionType is not null
        defaultCPaymentTermShouldBeFound("transactionType.specified=true");

        // Get all the cPaymentTermList where transactionType is null
        defaultCPaymentTermShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByValidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where valid equals to DEFAULT_VALID
        defaultCPaymentTermShouldBeFound("valid.equals=" + DEFAULT_VALID);

        // Get all the cPaymentTermList where valid equals to UPDATED_VALID
        defaultCPaymentTermShouldNotBeFound("valid.equals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where valid not equals to DEFAULT_VALID
        defaultCPaymentTermShouldNotBeFound("valid.notEquals=" + DEFAULT_VALID);

        // Get all the cPaymentTermList where valid not equals to UPDATED_VALID
        defaultCPaymentTermShouldBeFound("valid.notEquals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByValidIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where valid in DEFAULT_VALID or UPDATED_VALID
        defaultCPaymentTermShouldBeFound("valid.in=" + DEFAULT_VALID + "," + UPDATED_VALID);

        // Get all the cPaymentTermList where valid equals to UPDATED_VALID
        defaultCPaymentTermShouldNotBeFound("valid.in=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where valid is not null
        defaultCPaymentTermShouldBeFound("valid.specified=true");

        // Get all the cPaymentTermList where valid is null
        defaultCPaymentTermShouldNotBeFound("valid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where uid equals to DEFAULT_UID
        defaultCPaymentTermShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPaymentTermList where uid equals to UPDATED_UID
        defaultCPaymentTermShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where uid not equals to DEFAULT_UID
        defaultCPaymentTermShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPaymentTermList where uid not equals to UPDATED_UID
        defaultCPaymentTermShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPaymentTermShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPaymentTermList where uid equals to UPDATED_UID
        defaultCPaymentTermShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where uid is not null
        defaultCPaymentTermShouldBeFound("uid.specified=true");

        // Get all the cPaymentTermList where uid is null
        defaultCPaymentTermShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where active equals to DEFAULT_ACTIVE
        defaultCPaymentTermShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPaymentTermList where active equals to UPDATED_ACTIVE
        defaultCPaymentTermShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where active not equals to DEFAULT_ACTIVE
        defaultCPaymentTermShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPaymentTermList where active not equals to UPDATED_ACTIVE
        defaultCPaymentTermShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPaymentTermShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPaymentTermList where active equals to UPDATED_ACTIVE
        defaultCPaymentTermShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        // Get all the cPaymentTermList where active is not null
        defaultCPaymentTermShouldBeFound("active.specified=true");

        // Get all the cPaymentTermList where active is null
        defaultCPaymentTermShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentTermsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPaymentTerm.getAdOrganization();
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPaymentTermList where adOrganization equals to adOrganizationId
        defaultCPaymentTermShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPaymentTermList where adOrganization equals to adOrganizationId + 1
        defaultCPaymentTermShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPaymentTermShouldBeFound(String filter) throws Exception {
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPaymentTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].afterDelivery").value(hasItem(DEFAULT_AFTER_DELIVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].asDefault").value(hasItem(DEFAULT_AS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].calculateBusinessDay").value(hasItem(DEFAULT_CALCULATE_BUSINESS_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount2").value(hasItem(DEFAULT_DISCOUNT_2.intValue())))
            .andExpect(jsonPath("$.[*].discountDays").value(hasItem(DEFAULT_DISCOUNT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].discountDays2").value(hasItem(DEFAULT_DISCOUNT_DAYS_2.intValue())))
            .andExpect(jsonPath("$.[*].documentNote").value(hasItem(DEFAULT_DOCUMENT_NOTE)))
            .andExpect(jsonPath("$.[*].fixMonthCutOff").value(hasItem(DEFAULT_FIX_MONTH_CUT_OFF.intValue())))
            .andExpect(jsonPath("$.[*].fixMonthDay").value(hasItem(DEFAULT_FIX_MONTH_DAY.intValue())))
            .andExpect(jsonPath("$.[*].fixMonthOffset").value(hasItem(DEFAULT_FIX_MONTH_OFFSET.intValue())))
            .andExpect(jsonPath("$.[*].fixedDueDate").value(hasItem(DEFAULT_FIXED_DUE_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].netDay").value(hasItem(DEFAULT_NET_DAY)))
            .andExpect(jsonPath("$.[*].netDays").value(hasItem(DEFAULT_NET_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].onNextBusinessDay").value(hasItem(DEFAULT_ON_NEXT_BUSINESS_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPaymentTermShouldNotBeFound(String filter) throws Exception {
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPaymentTerm() throws Exception {
        // Get the cPaymentTerm
        restCPaymentTermMockMvc.perform(get("/api/c-payment-terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPaymentTerm() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        int databaseSizeBeforeUpdate = cPaymentTermRepository.findAll().size();

        // Update the cPaymentTerm
        CPaymentTerm updatedCPaymentTerm = cPaymentTermRepository.findById(cPaymentTerm.getId()).get();
        // Disconnect from session so that the updates on updatedCPaymentTerm are not directly saved in db
        em.detach(updatedCPaymentTerm);
        updatedCPaymentTerm
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .afterDelivery(UPDATED_AFTER_DELIVERY)
            .asDefault(UPDATED_AS_DEFAULT)
            .calculateBusinessDay(UPDATED_CALCULATE_BUSINESS_DAY)
            .discount(UPDATED_DISCOUNT)
            .discount2(UPDATED_DISCOUNT_2)
            .discountDays(UPDATED_DISCOUNT_DAYS)
            .discountDays2(UPDATED_DISCOUNT_DAYS_2)
            .documentNote(UPDATED_DOCUMENT_NOTE)
            .fixMonthCutOff(UPDATED_FIX_MONTH_CUT_OFF)
            .fixMonthDay(UPDATED_FIX_MONTH_DAY)
            .fixMonthOffset(UPDATED_FIX_MONTH_OFFSET)
            .fixedDueDate(UPDATED_FIXED_DUE_DATE)
            .graceDays(UPDATED_GRACE_DAYS)
            .netDay(UPDATED_NET_DAY)
            .netDays(UPDATED_NET_DAYS)
            .onNextBusinessDay(UPDATED_ON_NEXT_BUSINESS_DAY)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .valid(UPDATED_VALID)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(updatedCPaymentTerm);

        restCPaymentTermMockMvc.perform(put("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isOk());

        // Validate the CPaymentTerm in the database
        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeUpdate);
        CPaymentTerm testCPaymentTerm = cPaymentTermList.get(cPaymentTermList.size() - 1);
        assertThat(testCPaymentTerm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCPaymentTerm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCPaymentTerm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCPaymentTerm.isAfterDelivery()).isEqualTo(UPDATED_AFTER_DELIVERY);
        assertThat(testCPaymentTerm.isAsDefault()).isEqualTo(UPDATED_AS_DEFAULT);
        assertThat(testCPaymentTerm.isCalculateBusinessDay()).isEqualTo(UPDATED_CALCULATE_BUSINESS_DAY);
        assertThat(testCPaymentTerm.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testCPaymentTerm.getDiscount2()).isEqualTo(UPDATED_DISCOUNT_2);
        assertThat(testCPaymentTerm.getDiscountDays()).isEqualTo(UPDATED_DISCOUNT_DAYS);
        assertThat(testCPaymentTerm.getDiscountDays2()).isEqualTo(UPDATED_DISCOUNT_DAYS_2);
        assertThat(testCPaymentTerm.getDocumentNote()).isEqualTo(UPDATED_DOCUMENT_NOTE);
        assertThat(testCPaymentTerm.getFixMonthCutOff()).isEqualTo(UPDATED_FIX_MONTH_CUT_OFF);
        assertThat(testCPaymentTerm.getFixMonthDay()).isEqualTo(UPDATED_FIX_MONTH_DAY);
        assertThat(testCPaymentTerm.getFixMonthOffset()).isEqualTo(UPDATED_FIX_MONTH_OFFSET);
        assertThat(testCPaymentTerm.isFixedDueDate()).isEqualTo(UPDATED_FIXED_DUE_DATE);
        assertThat(testCPaymentTerm.getGraceDays()).isEqualTo(UPDATED_GRACE_DAYS);
        assertThat(testCPaymentTerm.getNetDay()).isEqualTo(UPDATED_NET_DAY);
        assertThat(testCPaymentTerm.getNetDays()).isEqualTo(UPDATED_NET_DAYS);
        assertThat(testCPaymentTerm.isOnNextBusinessDay()).isEqualTo(UPDATED_ON_NEXT_BUSINESS_DAY);
        assertThat(testCPaymentTerm.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testCPaymentTerm.isValid()).isEqualTo(UPDATED_VALID);
        assertThat(testCPaymentTerm.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPaymentTerm.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPaymentTerm() throws Exception {
        int databaseSizeBeforeUpdate = cPaymentTermRepository.findAll().size();

        // Create the CPaymentTerm
        CPaymentTermDTO cPaymentTermDTO = cPaymentTermMapper.toDto(cPaymentTerm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPaymentTermMockMvc.perform(put("/api/c-payment-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentTermDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPaymentTerm in the database
        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPaymentTerm() throws Exception {
        // Initialize the database
        cPaymentTermRepository.saveAndFlush(cPaymentTerm);

        int databaseSizeBeforeDelete = cPaymentTermRepository.findAll().size();

        // Delete the cPaymentTerm
        restCPaymentTermMockMvc.perform(delete("/api/c-payment-terms/{id}", cPaymentTerm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPaymentTerm> cPaymentTermList = cPaymentTermRepository.findAll();
        assertThat(cPaymentTermList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
