package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CTaxRate;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.repository.CTaxRateRepository;
import com.bhp.opusb.service.CTaxRateService;
import com.bhp.opusb.service.dto.CTaxRateDTO;
import com.bhp.opusb.service.mapper.CTaxRateMapper;
import com.bhp.opusb.service.dto.CTaxRateCriteria;
import com.bhp.opusb.service.CTaxRateQueryService;

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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhp.opusb.domain.enumeration.CTaxOrderType;
/**
 * Integration tests for the {@link CTaxRateResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CTaxRateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RATE = "AAAAAAAAAA";
    private static final String UPDATED_RATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final CTaxOrderType DEFAULT_ORDER_TYPE = CTaxOrderType.SALES;
    private static final CTaxOrderType UPDATED_ORDER_TYPE = CTaxOrderType.PURCHASE;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CTaxRateRepository cTaxRateRepository;

    @Autowired
    private CTaxRateMapper cTaxRateMapper;

    @Autowired
    private CTaxRateService cTaxRateService;

    @Autowired
    private CTaxRateQueryService cTaxRateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCTaxRateMockMvc;

    private CTaxRate cTaxRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxRate createEntity(EntityManager em) {
        CTaxRate cTaxRate = new CTaxRate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .rate(DEFAULT_RATE)
            .validFrom(DEFAULT_VALID_FROM)
            .orderType(DEFAULT_ORDER_TYPE)
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
        cTaxRate.setAdOrganization(aDOrganization);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        cTaxRate.setTaxCategory(cTaxCategory);
        return cTaxRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxRate createUpdatedEntity(EntityManager em) {
        CTaxRate cTaxRate = new CTaxRate()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rate(UPDATED_RATE)
            .validFrom(UPDATED_VALID_FROM)
            .orderType(UPDATED_ORDER_TYPE)
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
        cTaxRate.setAdOrganization(aDOrganization);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        cTaxRate.setTaxCategory(cTaxCategory);
        return cTaxRate;
    }

    @BeforeEach
    public void initTest() {
        cTaxRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCTaxRate() throws Exception {
        int databaseSizeBeforeCreate = cTaxRateRepository.findAll().size();

        // Create the CTaxRate
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(cTaxRate);
        restCTaxRateMockMvc.perform(post("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CTaxRate in the database
        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeCreate + 1);
        CTaxRate testCTaxRate = cTaxRateList.get(cTaxRateList.size() - 1);
        assertThat(testCTaxRate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCTaxRate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCTaxRate.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCTaxRate.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCTaxRate.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testCTaxRate.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCTaxRate.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCTaxRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cTaxRateRepository.findAll().size();

        // Create the CTaxRate with an existing ID
        cTaxRate.setId(1L);
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(cTaxRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCTaxRateMockMvc.perform(post("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxRate in the database
        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxRateRepository.findAll().size();
        // set the field null
        cTaxRate.setName(null);

        // Create the CTaxRate, which fails.
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(cTaxRate);

        restCTaxRateMockMvc.perform(post("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isBadRequest());

        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxRateRepository.findAll().size();
        // set the field null
        cTaxRate.setOrderType(null);

        // Create the CTaxRate, which fails.
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(cTaxRate);

        restCTaxRateMockMvc.perform(post("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isBadRequest());

        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCTaxRates() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCTaxRate() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get the cTaxRate
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates/{id}", cTaxRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cTaxRate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCTaxRatesByIdFiltering() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        Long id = cTaxRate.getId();

        defaultCTaxRateShouldBeFound("id.equals=" + id);
        defaultCTaxRateShouldNotBeFound("id.notEquals=" + id);

        defaultCTaxRateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCTaxRateShouldNotBeFound("id.greaterThan=" + id);

        defaultCTaxRateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCTaxRateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name equals to DEFAULT_NAME
        defaultCTaxRateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cTaxRateList where name equals to UPDATED_NAME
        defaultCTaxRateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name not equals to DEFAULT_NAME
        defaultCTaxRateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cTaxRateList where name not equals to UPDATED_NAME
        defaultCTaxRateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCTaxRateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cTaxRateList where name equals to UPDATED_NAME
        defaultCTaxRateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name is not null
        defaultCTaxRateShouldBeFound("name.specified=true");

        // Get all the cTaxRateList where name is null
        defaultCTaxRateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxRatesByNameContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name contains DEFAULT_NAME
        defaultCTaxRateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cTaxRateList where name contains UPDATED_NAME
        defaultCTaxRateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where name does not contain DEFAULT_NAME
        defaultCTaxRateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cTaxRateList where name does not contain UPDATED_NAME
        defaultCTaxRateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description equals to DEFAULT_DESCRIPTION
        defaultCTaxRateShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxRateList where description equals to UPDATED_DESCRIPTION
        defaultCTaxRateShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description not equals to DEFAULT_DESCRIPTION
        defaultCTaxRateShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxRateList where description not equals to UPDATED_DESCRIPTION
        defaultCTaxRateShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCTaxRateShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cTaxRateList where description equals to UPDATED_DESCRIPTION
        defaultCTaxRateShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description is not null
        defaultCTaxRateShouldBeFound("description.specified=true");

        // Get all the cTaxRateList where description is null
        defaultCTaxRateShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description contains DEFAULT_DESCRIPTION
        defaultCTaxRateShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxRateList where description contains UPDATED_DESCRIPTION
        defaultCTaxRateShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where description does not contain DEFAULT_DESCRIPTION
        defaultCTaxRateShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxRateList where description does not contain UPDATED_DESCRIPTION
        defaultCTaxRateShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate equals to DEFAULT_RATE
        defaultCTaxRateShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the cTaxRateList where rate equals to UPDATED_RATE
        defaultCTaxRateShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate not equals to DEFAULT_RATE
        defaultCTaxRateShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the cTaxRateList where rate not equals to UPDATED_RATE
        defaultCTaxRateShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultCTaxRateShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the cTaxRateList where rate equals to UPDATED_RATE
        defaultCTaxRateShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate is not null
        defaultCTaxRateShouldBeFound("rate.specified=true");

        // Get all the cTaxRateList where rate is null
        defaultCTaxRateShouldNotBeFound("rate.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxRatesByRateContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate contains DEFAULT_RATE
        defaultCTaxRateShouldBeFound("rate.contains=" + DEFAULT_RATE);

        // Get all the cTaxRateList where rate contains UPDATED_RATE
        defaultCTaxRateShouldNotBeFound("rate.contains=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByRateNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where rate does not contain DEFAULT_RATE
        defaultCTaxRateShouldNotBeFound("rate.doesNotContain=" + DEFAULT_RATE);

        // Get all the cTaxRateList where rate does not contain UPDATED_RATE
        defaultCTaxRateShouldBeFound("rate.doesNotContain=" + UPDATED_RATE);
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom equals to DEFAULT_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom equals to UPDATED_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom not equals to UPDATED_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the cTaxRateList where validFrom equals to UPDATED_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom is not null
        defaultCTaxRateShouldBeFound("validFrom.specified=true");

        // Get all the cTaxRateList where validFrom is null
        defaultCTaxRateShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom is less than DEFAULT_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom is less than UPDATED_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where validFrom is greater than DEFAULT_VALID_FROM
        defaultCTaxRateShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the cTaxRateList where validFrom is greater than SMALLER_VALID_FROM
        defaultCTaxRateShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByOrderTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where orderType equals to DEFAULT_ORDER_TYPE
        defaultCTaxRateShouldBeFound("orderType.equals=" + DEFAULT_ORDER_TYPE);

        // Get all the cTaxRateList where orderType equals to UPDATED_ORDER_TYPE
        defaultCTaxRateShouldNotBeFound("orderType.equals=" + UPDATED_ORDER_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByOrderTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where orderType not equals to DEFAULT_ORDER_TYPE
        defaultCTaxRateShouldNotBeFound("orderType.notEquals=" + DEFAULT_ORDER_TYPE);

        // Get all the cTaxRateList where orderType not equals to UPDATED_ORDER_TYPE
        defaultCTaxRateShouldBeFound("orderType.notEquals=" + UPDATED_ORDER_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByOrderTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where orderType in DEFAULT_ORDER_TYPE or UPDATED_ORDER_TYPE
        defaultCTaxRateShouldBeFound("orderType.in=" + DEFAULT_ORDER_TYPE + "," + UPDATED_ORDER_TYPE);

        // Get all the cTaxRateList where orderType equals to UPDATED_ORDER_TYPE
        defaultCTaxRateShouldNotBeFound("orderType.in=" + UPDATED_ORDER_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByOrderTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where orderType is not null
        defaultCTaxRateShouldBeFound("orderType.specified=true");

        // Get all the cTaxRateList where orderType is null
        defaultCTaxRateShouldNotBeFound("orderType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where uid equals to DEFAULT_UID
        defaultCTaxRateShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cTaxRateList where uid equals to UPDATED_UID
        defaultCTaxRateShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where uid not equals to DEFAULT_UID
        defaultCTaxRateShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cTaxRateList where uid not equals to UPDATED_UID
        defaultCTaxRateShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where uid in DEFAULT_UID or UPDATED_UID
        defaultCTaxRateShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cTaxRateList where uid equals to UPDATED_UID
        defaultCTaxRateShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where uid is not null
        defaultCTaxRateShouldBeFound("uid.specified=true");

        // Get all the cTaxRateList where uid is null
        defaultCTaxRateShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where active equals to DEFAULT_ACTIVE
        defaultCTaxRateShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cTaxRateList where active equals to UPDATED_ACTIVE
        defaultCTaxRateShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where active not equals to DEFAULT_ACTIVE
        defaultCTaxRateShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cTaxRateList where active not equals to UPDATED_ACTIVE
        defaultCTaxRateShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCTaxRateShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cTaxRateList where active equals to UPDATED_ACTIVE
        defaultCTaxRateShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        // Get all the cTaxRateList where active is not null
        defaultCTaxRateShouldBeFound("active.specified=true");

        // Get all the cTaxRateList where active is null
        defaultCTaxRateShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxRatesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cTaxRate.getAdOrganization();
        cTaxRateRepository.saveAndFlush(cTaxRate);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cTaxRateList where adOrganization equals to adOrganizationId
        defaultCTaxRateShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cTaxRateList where adOrganization equals to adOrganizationId + 1
        defaultCTaxRateShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCTaxRatesByTaxCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CTaxCategory taxCategory = cTaxRate.getTaxCategory();
        cTaxRateRepository.saveAndFlush(cTaxRate);
        Long taxCategoryId = taxCategory.getId();

        // Get all the cTaxRateList where taxCategory equals to taxCategoryId
        defaultCTaxRateShouldBeFound("taxCategoryId.equals=" + taxCategoryId);

        // Get all the cTaxRateList where taxCategory equals to taxCategoryId + 1
        defaultCTaxRateShouldNotBeFound("taxCategoryId.equals=" + (taxCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCTaxRateShouldBeFound(String filter) throws Exception {
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCTaxRateShouldNotBeFound(String filter) throws Exception {
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCTaxRate() throws Exception {
        // Get the cTaxRate
        restCTaxRateMockMvc.perform(get("/api/c-tax-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCTaxRate() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        int databaseSizeBeforeUpdate = cTaxRateRepository.findAll().size();

        // Update the cTaxRate
        CTaxRate updatedCTaxRate = cTaxRateRepository.findById(cTaxRate.getId()).get();
        // Disconnect from session so that the updates on updatedCTaxRate are not directly saved in db
        em.detach(updatedCTaxRate);
        updatedCTaxRate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rate(UPDATED_RATE)
            .validFrom(UPDATED_VALID_FROM)
            .orderType(UPDATED_ORDER_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(updatedCTaxRate);

        restCTaxRateMockMvc.perform(put("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isOk());

        // Validate the CTaxRate in the database
        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeUpdate);
        CTaxRate testCTaxRate = cTaxRateList.get(cTaxRateList.size() - 1);
        assertThat(testCTaxRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCTaxRate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCTaxRate.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCTaxRate.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCTaxRate.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testCTaxRate.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCTaxRate.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCTaxRate() throws Exception {
        int databaseSizeBeforeUpdate = cTaxRateRepository.findAll().size();

        // Create the CTaxRate
        CTaxRateDTO cTaxRateDTO = cTaxRateMapper.toDto(cTaxRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCTaxRateMockMvc.perform(put("/api/c-tax-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxRate in the database
        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCTaxRate() throws Exception {
        // Initialize the database
        cTaxRateRepository.saveAndFlush(cTaxRate);

        int databaseSizeBeforeDelete = cTaxRateRepository.findAll().size();

        // Delete the cTaxRate
        restCTaxRateMockMvc.perform(delete("/api/c-tax-rates/{id}", cTaxRate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CTaxRate> cTaxRateList = cTaxRateRepository.findAll();
        assertThat(cTaxRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
