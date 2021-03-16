package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPaymentSchedule;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CPaymentTerm;
import com.bhp.opusb.repository.CPaymentScheduleRepository;
import com.bhp.opusb.service.CPaymentScheduleService;
import com.bhp.opusb.service.dto.CPaymentScheduleDTO;
import com.bhp.opusb.service.mapper.CPaymentScheduleMapper;
import com.bhp.opusb.service.dto.CPaymentScheduleCriteria;
import com.bhp.opusb.service.CPaymentScheduleQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CPaymentScheduleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPaymentScheduleResourceIT {

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT = new BigDecimal(1 - 1);

    private static final Long DEFAULT_DISCOUNT_DAYS = 10L;
    private static final Long UPDATED_DISCOUNT_DAYS = 9L;
    private static final Long SMALLER_DISCOUNT_DAYS = 10L - 1L;

    private static final Long DEFAULT_GRACE_DAYS = 10L;
    private static final Long UPDATED_GRACE_DAYS = 9L;
    private static final Long SMALLER_GRACE_DAYS = 10L - 1L;

    private static final Integer DEFAULT_NET_DAY = 1;
    private static final Integer UPDATED_NET_DAY = 0;
    private static final Integer SMALLER_NET_DAY = 1 - 1;

    private static final Long DEFAULT_NET_DAYS = 10L;
    private static final Long UPDATED_NET_DAYS = 9L;
    private static final Long SMALLER_NET_DAYS = 10L - 1L;

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PERCENTAGE = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    @Autowired
    private CPaymentScheduleRepository cPaymentScheduleRepository;

    @Autowired
    private CPaymentScheduleMapper cPaymentScheduleMapper;

    @Autowired
    private CPaymentScheduleService cPaymentScheduleService;

    @Autowired
    private CPaymentScheduleQueryService cPaymentScheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPaymentScheduleMockMvc;

    private CPaymentSchedule cPaymentSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPaymentSchedule createEntity(EntityManager em) {
        CPaymentSchedule cPaymentSchedule = new CPaymentSchedule()
            .discount(DEFAULT_DISCOUNT)
            .discountDays(DEFAULT_DISCOUNT_DAYS)
            .graceDays(DEFAULT_GRACE_DAYS)
            .netDay(DEFAULT_NET_DAY)
            .netDays(DEFAULT_NET_DAYS)
            .percentage(DEFAULT_PERCENTAGE)
            .valid(DEFAULT_VALID);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPaymentSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CPaymentTerm cPaymentTerm;
        if (TestUtil.findAll(em, CPaymentTerm.class).isEmpty()) {
            cPaymentTerm = CPaymentTermResourceIT.createEntity(em);
            em.persist(cPaymentTerm);
            em.flush();
        } else {
            cPaymentTerm = TestUtil.findAll(em, CPaymentTerm.class).get(0);
        }
        cPaymentSchedule.setCPaymentTerm(cPaymentTerm);
        return cPaymentSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPaymentSchedule createUpdatedEntity(EntityManager em) {
        CPaymentSchedule cPaymentSchedule = new CPaymentSchedule()
            .discount(UPDATED_DISCOUNT)
            .discountDays(UPDATED_DISCOUNT_DAYS)
            .graceDays(UPDATED_GRACE_DAYS)
            .netDay(UPDATED_NET_DAY)
            .netDays(UPDATED_NET_DAYS)
            .percentage(UPDATED_PERCENTAGE)
            .valid(UPDATED_VALID);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPaymentSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CPaymentTerm cPaymentTerm;
        if (TestUtil.findAll(em, CPaymentTerm.class).isEmpty()) {
            cPaymentTerm = CPaymentTermResourceIT.createUpdatedEntity(em);
            em.persist(cPaymentTerm);
            em.flush();
        } else {
            cPaymentTerm = TestUtil.findAll(em, CPaymentTerm.class).get(0);
        }
        cPaymentSchedule.setCPaymentTerm(cPaymentTerm);
        return cPaymentSchedule;
    }

    @BeforeEach
    public void initTest() {
        cPaymentSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPaymentSchedule() throws Exception {
        int databaseSizeBeforeCreate = cPaymentScheduleRepository.findAll().size();

        // Create the CPaymentSchedule
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);
        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the CPaymentSchedule in the database
        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        CPaymentSchedule testCPaymentSchedule = cPaymentScheduleList.get(cPaymentScheduleList.size() - 1);
        assertThat(testCPaymentSchedule.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testCPaymentSchedule.getDiscountDays()).isEqualTo(DEFAULT_DISCOUNT_DAYS);
        assertThat(testCPaymentSchedule.getGraceDays()).isEqualTo(DEFAULT_GRACE_DAYS);
        assertThat(testCPaymentSchedule.getNetDay()).isEqualTo(DEFAULT_NET_DAY);
        assertThat(testCPaymentSchedule.getNetDays()).isEqualTo(DEFAULT_NET_DAYS);
        assertThat(testCPaymentSchedule.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testCPaymentSchedule.isValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    public void createCPaymentScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPaymentScheduleRepository.findAll().size();

        // Create the CPaymentSchedule with an existing ID
        cPaymentSchedule.setId(1L);
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPaymentSchedule in the database
        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentScheduleRepository.findAll().size();
        // set the field null
        cPaymentSchedule.setDiscount(null);

        // Create the CPaymentSchedule, which fails.
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentScheduleRepository.findAll().size();
        // set the field null
        cPaymentSchedule.setDiscountDays(null);

        // Create the CPaymentSchedule, which fails.
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentScheduleRepository.findAll().size();
        // set the field null
        cPaymentSchedule.setNetDays(null);

        // Create the CPaymentSchedule, which fails.
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPaymentScheduleRepository.findAll().size();
        // set the field null
        cPaymentSchedule.setPercentage(null);

        // Create the CPaymentSchedule, which fails.
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        restCPaymentScheduleMockMvc.perform(post("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedules() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPaymentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountDays").value(hasItem(DEFAULT_DISCOUNT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].netDay").value(hasItem(DEFAULT_NET_DAY)))
            .andExpect(jsonPath("$.[*].netDays").value(hasItem(DEFAULT_NET_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPaymentSchedule() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get the cPaymentSchedule
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules/{id}", cPaymentSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPaymentSchedule.getId().intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.discountDays").value(DEFAULT_DISCOUNT_DAYS.intValue()))
            .andExpect(jsonPath("$.graceDays").value(DEFAULT_GRACE_DAYS.intValue()))
            .andExpect(jsonPath("$.netDay").value(DEFAULT_NET_DAY))
            .andExpect(jsonPath("$.netDays").value(DEFAULT_NET_DAYS.intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPaymentSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        Long id = cPaymentSchedule.getId();

        defaultCPaymentScheduleShouldBeFound("id.equals=" + id);
        defaultCPaymentScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultCPaymentScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPaymentScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultCPaymentScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPaymentScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount equals to DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount equals to UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount not equals to DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount not equals to UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the cPaymentScheduleList where discount equals to UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount is not null
        defaultCPaymentScheduleShouldBeFound("discount.specified=true");

        // Get all the cPaymentScheduleList where discount is null
        defaultCPaymentScheduleShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount is less than or equal to SMALLER_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount is less than DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount is less than UPDATED_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discount is greater than DEFAULT_DISCOUNT
        defaultCPaymentScheduleShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the cPaymentScheduleList where discount is greater than SMALLER_DISCOUNT
        defaultCPaymentScheduleShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays equals to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.equals=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.equals=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays not equals to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.notEquals=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays not equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.notEquals=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays in DEFAULT_DISCOUNT_DAYS or UPDATED_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.in=" + DEFAULT_DISCOUNT_DAYS + "," + UPDATED_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays equals to UPDATED_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.in=" + UPDATED_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays is not null
        defaultCPaymentScheduleShouldBeFound("discountDays.specified=true");

        // Get all the cPaymentScheduleList where discountDays is null
        defaultCPaymentScheduleShouldNotBeFound("discountDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays is greater than or equal to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.greaterThanOrEqual=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays is greater than or equal to (DEFAULT_DISCOUNT_DAYS + 1)
        defaultCPaymentScheduleShouldNotBeFound("discountDays.greaterThanOrEqual=" + (DEFAULT_DISCOUNT_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays is less than or equal to DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.lessThanOrEqual=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays is less than or equal to SMALLER_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.lessThanOrEqual=" + SMALLER_DISCOUNT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays is less than DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.lessThan=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays is less than (DEFAULT_DISCOUNT_DAYS + 1)
        defaultCPaymentScheduleShouldBeFound("discountDays.lessThan=" + (DEFAULT_DISCOUNT_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByDiscountDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where discountDays is greater than DEFAULT_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldNotBeFound("discountDays.greaterThan=" + DEFAULT_DISCOUNT_DAYS);

        // Get all the cPaymentScheduleList where discountDays is greater than SMALLER_DISCOUNT_DAYS
        defaultCPaymentScheduleShouldBeFound("discountDays.greaterThan=" + SMALLER_DISCOUNT_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays equals to DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.equals=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays equals to UPDATED_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.equals=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays not equals to DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.notEquals=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays not equals to UPDATED_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.notEquals=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays in DEFAULT_GRACE_DAYS or UPDATED_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.in=" + DEFAULT_GRACE_DAYS + "," + UPDATED_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays equals to UPDATED_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.in=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays is not null
        defaultCPaymentScheduleShouldBeFound("graceDays.specified=true");

        // Get all the cPaymentScheduleList where graceDays is null
        defaultCPaymentScheduleShouldNotBeFound("graceDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays is greater than or equal to DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.greaterThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays is greater than or equal to (DEFAULT_GRACE_DAYS + 1)
        defaultCPaymentScheduleShouldNotBeFound("graceDays.greaterThanOrEqual=" + (DEFAULT_GRACE_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays is less than or equal to DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.lessThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays is less than or equal to SMALLER_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.lessThanOrEqual=" + SMALLER_GRACE_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays is less than DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.lessThan=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays is less than (DEFAULT_GRACE_DAYS + 1)
        defaultCPaymentScheduleShouldBeFound("graceDays.lessThan=" + (DEFAULT_GRACE_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByGraceDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where graceDays is greater than DEFAULT_GRACE_DAYS
        defaultCPaymentScheduleShouldNotBeFound("graceDays.greaterThan=" + DEFAULT_GRACE_DAYS);

        // Get all the cPaymentScheduleList where graceDays is greater than SMALLER_GRACE_DAYS
        defaultCPaymentScheduleShouldBeFound("graceDays.greaterThan=" + SMALLER_GRACE_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay equals to DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.equals=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay equals to UPDATED_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.equals=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay not equals to DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.notEquals=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay not equals to UPDATED_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.notEquals=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay in DEFAULT_NET_DAY or UPDATED_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.in=" + DEFAULT_NET_DAY + "," + UPDATED_NET_DAY);

        // Get all the cPaymentScheduleList where netDay equals to UPDATED_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.in=" + UPDATED_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay is not null
        defaultCPaymentScheduleShouldBeFound("netDay.specified=true");

        // Get all the cPaymentScheduleList where netDay is null
        defaultCPaymentScheduleShouldNotBeFound("netDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay is greater than or equal to DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.greaterThanOrEqual=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay is greater than or equal to (DEFAULT_NET_DAY + 1)
        defaultCPaymentScheduleShouldNotBeFound("netDay.greaterThanOrEqual=" + (DEFAULT_NET_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay is less than or equal to DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.lessThanOrEqual=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay is less than or equal to SMALLER_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.lessThanOrEqual=" + SMALLER_NET_DAY);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay is less than DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.lessThan=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay is less than (DEFAULT_NET_DAY + 1)
        defaultCPaymentScheduleShouldBeFound("netDay.lessThan=" + (DEFAULT_NET_DAY + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDay is greater than DEFAULT_NET_DAY
        defaultCPaymentScheduleShouldNotBeFound("netDay.greaterThan=" + DEFAULT_NET_DAY);

        // Get all the cPaymentScheduleList where netDay is greater than SMALLER_NET_DAY
        defaultCPaymentScheduleShouldBeFound("netDay.greaterThan=" + SMALLER_NET_DAY);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays equals to DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.equals=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays equals to UPDATED_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.equals=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays not equals to DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.notEquals=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays not equals to UPDATED_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.notEquals=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays in DEFAULT_NET_DAYS or UPDATED_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.in=" + DEFAULT_NET_DAYS + "," + UPDATED_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays equals to UPDATED_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.in=" + UPDATED_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays is not null
        defaultCPaymentScheduleShouldBeFound("netDays.specified=true");

        // Get all the cPaymentScheduleList where netDays is null
        defaultCPaymentScheduleShouldNotBeFound("netDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays is greater than or equal to DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.greaterThanOrEqual=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays is greater than or equal to (DEFAULT_NET_DAYS + 1)
        defaultCPaymentScheduleShouldNotBeFound("netDays.greaterThanOrEqual=" + (DEFAULT_NET_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays is less than or equal to DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.lessThanOrEqual=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays is less than or equal to SMALLER_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.lessThanOrEqual=" + SMALLER_NET_DAYS);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays is less than DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.lessThan=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays is less than (DEFAULT_NET_DAYS + 1)
        defaultCPaymentScheduleShouldBeFound("netDays.lessThan=" + (DEFAULT_NET_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByNetDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where netDays is greater than DEFAULT_NET_DAYS
        defaultCPaymentScheduleShouldNotBeFound("netDays.greaterThan=" + DEFAULT_NET_DAYS);

        // Get all the cPaymentScheduleList where netDays is greater than SMALLER_NET_DAYS
        defaultCPaymentScheduleShouldBeFound("netDays.greaterThan=" + SMALLER_NET_DAYS);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage equals to DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage equals to UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage not equals to DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.notEquals=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage not equals to UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.notEquals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage equals to UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage is not null
        defaultCPaymentScheduleShouldBeFound("percentage.specified=true");

        // Get all the cPaymentScheduleList where percentage is null
        defaultCPaymentScheduleShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage is less than DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage is less than UPDATED_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where percentage is greater than DEFAULT_PERCENTAGE
        defaultCPaymentScheduleShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the cPaymentScheduleList where percentage is greater than SMALLER_PERCENTAGE
        defaultCPaymentScheduleShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByValidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where valid equals to DEFAULT_VALID
        defaultCPaymentScheduleShouldBeFound("valid.equals=" + DEFAULT_VALID);

        // Get all the cPaymentScheduleList where valid equals to UPDATED_VALID
        defaultCPaymentScheduleShouldNotBeFound("valid.equals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where valid not equals to DEFAULT_VALID
        defaultCPaymentScheduleShouldNotBeFound("valid.notEquals=" + DEFAULT_VALID);

        // Get all the cPaymentScheduleList where valid not equals to UPDATED_VALID
        defaultCPaymentScheduleShouldBeFound("valid.notEquals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByValidIsInShouldWork() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where valid in DEFAULT_VALID or UPDATED_VALID
        defaultCPaymentScheduleShouldBeFound("valid.in=" + DEFAULT_VALID + "," + UPDATED_VALID);

        // Get all the cPaymentScheduleList where valid equals to UPDATED_VALID
        defaultCPaymentScheduleShouldNotBeFound("valid.in=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        // Get all the cPaymentScheduleList where valid is not null
        defaultCPaymentScheduleShouldBeFound("valid.specified=true");

        // Get all the cPaymentScheduleList where valid is null
        defaultCPaymentScheduleShouldNotBeFound("valid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPaymentSchedulesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPaymentSchedule.getAdOrganization();
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPaymentScheduleList where adOrganization equals to adOrganizationId
        defaultCPaymentScheduleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPaymentScheduleList where adOrganization equals to adOrganizationId + 1
        defaultCPaymentScheduleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCPaymentSchedulesByCPaymentTermIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPaymentTerm cPaymentTerm = cPaymentSchedule.getCPaymentTerm();
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);
        Long cPaymentTermId = cPaymentTerm.getId();

        // Get all the cPaymentScheduleList where cPaymentTerm equals to cPaymentTermId
        defaultCPaymentScheduleShouldBeFound("cPaymentTermId.equals=" + cPaymentTermId);

        // Get all the cPaymentScheduleList where cPaymentTerm equals to cPaymentTermId + 1
        defaultCPaymentScheduleShouldNotBeFound("cPaymentTermId.equals=" + (cPaymentTermId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPaymentScheduleShouldBeFound(String filter) throws Exception {
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPaymentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountDays").value(hasItem(DEFAULT_DISCOUNT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].netDay").value(hasItem(DEFAULT_NET_DAY)))
            .andExpect(jsonPath("$.[*].netDays").value(hasItem(DEFAULT_NET_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));

        // Check, that the count call also returns 1
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPaymentScheduleShouldNotBeFound(String filter) throws Exception {
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPaymentSchedule() throws Exception {
        // Get the cPaymentSchedule
        restCPaymentScheduleMockMvc.perform(get("/api/c-payment-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPaymentSchedule() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        int databaseSizeBeforeUpdate = cPaymentScheduleRepository.findAll().size();

        // Update the cPaymentSchedule
        CPaymentSchedule updatedCPaymentSchedule = cPaymentScheduleRepository.findById(cPaymentSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedCPaymentSchedule are not directly saved in db
        em.detach(updatedCPaymentSchedule);
        updatedCPaymentSchedule
            .discount(UPDATED_DISCOUNT)
            .discountDays(UPDATED_DISCOUNT_DAYS)
            .graceDays(UPDATED_GRACE_DAYS)
            .netDay(UPDATED_NET_DAY)
            .netDays(UPDATED_NET_DAYS)
            .percentage(UPDATED_PERCENTAGE)
            .valid(UPDATED_VALID);
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(updatedCPaymentSchedule);

        restCPaymentScheduleMockMvc.perform(put("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the CPaymentSchedule in the database
        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeUpdate);
        CPaymentSchedule testCPaymentSchedule = cPaymentScheduleList.get(cPaymentScheduleList.size() - 1);
        assertThat(testCPaymentSchedule.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testCPaymentSchedule.getDiscountDays()).isEqualTo(UPDATED_DISCOUNT_DAYS);
        assertThat(testCPaymentSchedule.getGraceDays()).isEqualTo(UPDATED_GRACE_DAYS);
        assertThat(testCPaymentSchedule.getNetDay()).isEqualTo(UPDATED_NET_DAY);
        assertThat(testCPaymentSchedule.getNetDays()).isEqualTo(UPDATED_NET_DAYS);
        assertThat(testCPaymentSchedule.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testCPaymentSchedule.isValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    public void updateNonExistingCPaymentSchedule() throws Exception {
        int databaseSizeBeforeUpdate = cPaymentScheduleRepository.findAll().size();

        // Create the CPaymentSchedule
        CPaymentScheduleDTO cPaymentScheduleDTO = cPaymentScheduleMapper.toDto(cPaymentSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPaymentScheduleMockMvc.perform(put("/api/c-payment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPaymentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPaymentSchedule in the database
        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPaymentSchedule() throws Exception {
        // Initialize the database
        cPaymentScheduleRepository.saveAndFlush(cPaymentSchedule);

        int databaseSizeBeforeDelete = cPaymentScheduleRepository.findAll().size();

        // Delete the cPaymentSchedule
        restCPaymentScheduleMockMvc.perform(delete("/api/c-payment-schedules/{id}", cPaymentSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPaymentSchedule> cPaymentScheduleList = cPaymentScheduleRepository.findAll();
        assertThat(cPaymentScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
