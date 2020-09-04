package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CConvertionRate;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CConvertionType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CConvertionRateRepository;
import com.bhp.opusb.service.CConvertionRateService;
import com.bhp.opusb.service.dto.CConvertionRateDTO;
import com.bhp.opusb.service.mapper.CConvertionRateMapper;
import com.bhp.opusb.service.dto.CConvertionRateCriteria;
import com.bhp.opusb.service.CConvertionRateQueryService;

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
 * Integration tests for the {@link CConvertionRateResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CConvertionRateResourceIT {

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RATE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CConvertionRateRepository cConvertionRateRepository;

    @Autowired
    private CConvertionRateMapper cConvertionRateMapper;

    @Autowired
    private CConvertionRateService cConvertionRateService;

    @Autowired
    private CConvertionRateQueryService cConvertionRateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCConvertionRateMockMvc;

    private CConvertionRate cConvertionRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CConvertionRate createEntity(EntityManager em) {
        CConvertionRate cConvertionRate = new CConvertionRate()
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .rate(DEFAULT_RATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cConvertionRate.setSourceCurrency(cCurrency);
        // Add required entity
        cConvertionRate.setTargetCurrency(cCurrency);
        // Add required entity
        CConvertionType cConvertionType;
        if (TestUtil.findAll(em, CConvertionType.class).isEmpty()) {
            cConvertionType = CConvertionTypeResourceIT.createEntity(em);
            em.persist(cConvertionType);
            em.flush();
        } else {
            cConvertionType = TestUtil.findAll(em, CConvertionType.class).get(0);
        }
        cConvertionRate.setConvertionType(cConvertionType);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cConvertionRate.setAdOrganization(aDOrganization);
        return cConvertionRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CConvertionRate createUpdatedEntity(EntityManager em) {
        CConvertionRate cConvertionRate = new CConvertionRate()
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .rate(UPDATED_RATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cConvertionRate.setSourceCurrency(cCurrency);
        // Add required entity
        cConvertionRate.setTargetCurrency(cCurrency);
        // Add required entity
        CConvertionType cConvertionType;
        if (TestUtil.findAll(em, CConvertionType.class).isEmpty()) {
            cConvertionType = CConvertionTypeResourceIT.createUpdatedEntity(em);
            em.persist(cConvertionType);
            em.flush();
        } else {
            cConvertionType = TestUtil.findAll(em, CConvertionType.class).get(0);
        }
        cConvertionRate.setConvertionType(cConvertionType);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cConvertionRate.setAdOrganization(aDOrganization);
        return cConvertionRate;
    }

    @BeforeEach
    public void initTest() {
        cConvertionRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCConvertionRate() throws Exception {
        int databaseSizeBeforeCreate = cConvertionRateRepository.findAll().size();

        // Create the CConvertionRate
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);
        restCConvertionRateMockMvc.perform(post("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CConvertionRate in the database
        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeCreate + 1);
        CConvertionRate testCConvertionRate = cConvertionRateList.get(cConvertionRateList.size() - 1);
        assertThat(testCConvertionRate.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCConvertionRate.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCConvertionRate.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCConvertionRate.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCConvertionRate.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCConvertionRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cConvertionRateRepository.findAll().size();

        // Create the CConvertionRate with an existing ID
        cConvertionRate.setId(1L);
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCConvertionRateMockMvc.perform(post("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CConvertionRate in the database
        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = cConvertionRateRepository.findAll().size();
        // set the field null
        cConvertionRate.setValidFrom(null);

        // Create the CConvertionRate, which fails.
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);

        restCConvertionRateMockMvc.perform(post("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isBadRequest());

        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = cConvertionRateRepository.findAll().size();
        // set the field null
        cConvertionRate.setValidTo(null);

        // Create the CConvertionRate, which fails.
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);

        restCConvertionRateMockMvc.perform(post("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isBadRequest());

        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cConvertionRateRepository.findAll().size();
        // set the field null
        cConvertionRate.setRate(null);

        // Create the CConvertionRate, which fails.
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);

        restCConvertionRateMockMvc.perform(post("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isBadRequest());

        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCConvertionRates() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cConvertionRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCConvertionRate() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get the cConvertionRate
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates/{id}", cConvertionRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cConvertionRate.getId().intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCConvertionRatesByIdFiltering() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        Long id = cConvertionRate.getId();

        defaultCConvertionRateShouldBeFound("id.equals=" + id);
        defaultCConvertionRateShouldNotBeFound("id.notEquals=" + id);

        defaultCConvertionRateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCConvertionRateShouldNotBeFound("id.greaterThan=" + id);

        defaultCConvertionRateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCConvertionRateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom equals to DEFAULT_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom equals to UPDATED_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom not equals to UPDATED_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the cConvertionRateList where validFrom equals to UPDATED_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom is not null
        defaultCConvertionRateShouldBeFound("validFrom.specified=true");

        // Get all the cConvertionRateList where validFrom is null
        defaultCConvertionRateShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom is less than DEFAULT_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom is less than UPDATED_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validFrom is greater than DEFAULT_VALID_FROM
        defaultCConvertionRateShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the cConvertionRateList where validFrom is greater than SMALLER_VALID_FROM
        defaultCConvertionRateShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo equals to DEFAULT_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo equals to UPDATED_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo not equals to DEFAULT_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo not equals to UPDATED_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the cConvertionRateList where validTo equals to UPDATED_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo is not null
        defaultCConvertionRateShouldBeFound("validTo.specified=true");

        // Get all the cConvertionRateList where validTo is null
        defaultCConvertionRateShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo is less than or equal to SMALLER_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo is less than DEFAULT_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo is less than UPDATED_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where validTo is greater than DEFAULT_VALID_TO
        defaultCConvertionRateShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the cConvertionRateList where validTo is greater than SMALLER_VALID_TO
        defaultCConvertionRateShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate equals to DEFAULT_RATE
        defaultCConvertionRateShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate equals to UPDATED_RATE
        defaultCConvertionRateShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate not equals to DEFAULT_RATE
        defaultCConvertionRateShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate not equals to UPDATED_RATE
        defaultCConvertionRateShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultCConvertionRateShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the cConvertionRateList where rate equals to UPDATED_RATE
        defaultCConvertionRateShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate is not null
        defaultCConvertionRateShouldBeFound("rate.specified=true");

        // Get all the cConvertionRateList where rate is null
        defaultCConvertionRateShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate is greater than or equal to DEFAULT_RATE
        defaultCConvertionRateShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate is greater than or equal to UPDATED_RATE
        defaultCConvertionRateShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate is less than or equal to DEFAULT_RATE
        defaultCConvertionRateShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate is less than or equal to SMALLER_RATE
        defaultCConvertionRateShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate is less than DEFAULT_RATE
        defaultCConvertionRateShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate is less than UPDATED_RATE
        defaultCConvertionRateShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where rate is greater than DEFAULT_RATE
        defaultCConvertionRateShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the cConvertionRateList where rate is greater than SMALLER_RATE
        defaultCConvertionRateShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where uid equals to DEFAULT_UID
        defaultCConvertionRateShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cConvertionRateList where uid equals to UPDATED_UID
        defaultCConvertionRateShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where uid not equals to DEFAULT_UID
        defaultCConvertionRateShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cConvertionRateList where uid not equals to UPDATED_UID
        defaultCConvertionRateShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where uid in DEFAULT_UID or UPDATED_UID
        defaultCConvertionRateShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cConvertionRateList where uid equals to UPDATED_UID
        defaultCConvertionRateShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where uid is not null
        defaultCConvertionRateShouldBeFound("uid.specified=true");

        // Get all the cConvertionRateList where uid is null
        defaultCConvertionRateShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where active equals to DEFAULT_ACTIVE
        defaultCConvertionRateShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cConvertionRateList where active equals to UPDATED_ACTIVE
        defaultCConvertionRateShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where active not equals to DEFAULT_ACTIVE
        defaultCConvertionRateShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cConvertionRateList where active not equals to UPDATED_ACTIVE
        defaultCConvertionRateShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCConvertionRateShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cConvertionRateList where active equals to UPDATED_ACTIVE
        defaultCConvertionRateShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        // Get all the cConvertionRateList where active is not null
        defaultCConvertionRateShouldBeFound("active.specified=true");

        // Get all the cConvertionRateList where active is null
        defaultCConvertionRateShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionRatesBySourceCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency sourceCurrency = cConvertionRate.getSourceCurrency();
        cConvertionRateRepository.saveAndFlush(cConvertionRate);
        Long sourceCurrencyId = sourceCurrency.getId();

        // Get all the cConvertionRateList where sourceCurrency equals to sourceCurrencyId
        defaultCConvertionRateShouldBeFound("sourceCurrencyId.equals=" + sourceCurrencyId);

        // Get all the cConvertionRateList where sourceCurrency equals to sourceCurrencyId + 1
        defaultCConvertionRateShouldNotBeFound("sourceCurrencyId.equals=" + (sourceCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByTargetCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency targetCurrency = cConvertionRate.getTargetCurrency();
        cConvertionRateRepository.saveAndFlush(cConvertionRate);
        Long targetCurrencyId = targetCurrency.getId();

        // Get all the cConvertionRateList where targetCurrency equals to targetCurrencyId
        defaultCConvertionRateShouldBeFound("targetCurrencyId.equals=" + targetCurrencyId);

        // Get all the cConvertionRateList where targetCurrency equals to targetCurrencyId + 1
        defaultCConvertionRateShouldNotBeFound("targetCurrencyId.equals=" + (targetCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByConvertionTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CConvertionType convertionType = cConvertionRate.getConvertionType();
        cConvertionRateRepository.saveAndFlush(cConvertionRate);
        Long convertionTypeId = convertionType.getId();

        // Get all the cConvertionRateList where convertionType equals to convertionTypeId
        defaultCConvertionRateShouldBeFound("convertionTypeId.equals=" + convertionTypeId);

        // Get all the cConvertionRateList where convertionType equals to convertionTypeId + 1
        defaultCConvertionRateShouldNotBeFound("convertionTypeId.equals=" + (convertionTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCConvertionRatesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cConvertionRate.getAdOrganization();
        cConvertionRateRepository.saveAndFlush(cConvertionRate);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cConvertionRateList where adOrganization equals to adOrganizationId
        defaultCConvertionRateShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cConvertionRateList where adOrganization equals to adOrganizationId + 1
        defaultCConvertionRateShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCConvertionRateShouldBeFound(String filter) throws Exception {
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cConvertionRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCConvertionRateShouldNotBeFound(String filter) throws Exception {
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCConvertionRate() throws Exception {
        // Get the cConvertionRate
        restCConvertionRateMockMvc.perform(get("/api/c-convertion-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCConvertionRate() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        int databaseSizeBeforeUpdate = cConvertionRateRepository.findAll().size();

        // Update the cConvertionRate
        CConvertionRate updatedCConvertionRate = cConvertionRateRepository.findById(cConvertionRate.getId()).get();
        // Disconnect from session so that the updates on updatedCConvertionRate are not directly saved in db
        em.detach(updatedCConvertionRate);
        updatedCConvertionRate
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .rate(UPDATED_RATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(updatedCConvertionRate);

        restCConvertionRateMockMvc.perform(put("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isOk());

        // Validate the CConvertionRate in the database
        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeUpdate);
        CConvertionRate testCConvertionRate = cConvertionRateList.get(cConvertionRateList.size() - 1);
        assertThat(testCConvertionRate.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCConvertionRate.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCConvertionRate.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCConvertionRate.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCConvertionRate.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCConvertionRate() throws Exception {
        int databaseSizeBeforeUpdate = cConvertionRateRepository.findAll().size();

        // Create the CConvertionRate
        CConvertionRateDTO cConvertionRateDTO = cConvertionRateMapper.toDto(cConvertionRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCConvertionRateMockMvc.perform(put("/api/c-convertion-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CConvertionRate in the database
        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCConvertionRate() throws Exception {
        // Initialize the database
        cConvertionRateRepository.saveAndFlush(cConvertionRate);

        int databaseSizeBeforeDelete = cConvertionRateRepository.findAll().size();

        // Delete the cConvertionRate
        restCConvertionRateMockMvc.perform(delete("/api/c-convertion-rates/{id}", cConvertionRate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CConvertionRate> cConvertionRateList = cConvertionRateRepository.findAll();
        assertThat(cConvertionRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
