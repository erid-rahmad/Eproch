package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.repository.CTaxRepository;
import com.bhp.opusb.service.CTaxService;
import com.bhp.opusb.service.dto.CTaxDTO;
import com.bhp.opusb.service.mapper.CTaxMapper;
import com.bhp.opusb.service.dto.CTaxCriteria;
import com.bhp.opusb.service.CTaxQueryService;

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

import com.bhp.opusb.domain.enumeration.CTaxTransactionType;
/**
 * Integration tests for the {@link CTaxResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CTaxResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RATE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final CTaxTransactionType DEFAULT_TRANSACTION_TYPE = CTaxTransactionType.SALES;
    private static final CTaxTransactionType UPDATED_TRANSACTION_TYPE = CTaxTransactionType.PURCHASE;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CTaxRepository cTaxRepository;

    @Autowired
    private CTaxMapper cTaxMapper;

    @Autowired
    private CTaxService cTaxService;

    @Autowired
    private CTaxQueryService cTaxQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCTaxMockMvc;

    private CTax cTax;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTax createEntity(EntityManager em) {
        CTax cTax = new CTax()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .rate(DEFAULT_RATE)
            .validFrom(DEFAULT_VALID_FROM)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
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
        cTax.setAdOrganization(aDOrganization);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        cTax.setTaxCategory(cTaxCategory);
        return cTax;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTax createUpdatedEntity(EntityManager em) {
        CTax cTax = new CTax()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rate(UPDATED_RATE)
            .validFrom(UPDATED_VALID_FROM)
            .transactionType(UPDATED_TRANSACTION_TYPE)
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
        cTax.setAdOrganization(aDOrganization);
        // Add required entity
        CTaxCategory cTaxCategory;
        if (TestUtil.findAll(em, CTaxCategory.class).isEmpty()) {
            cTaxCategory = CTaxCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cTaxCategory);
            em.flush();
        } else {
            cTaxCategory = TestUtil.findAll(em, CTaxCategory.class).get(0);
        }
        cTax.setTaxCategory(cTaxCategory);
        return cTax;
    }

    @BeforeEach
    public void initTest() {
        cTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createCTax() throws Exception {
        int databaseSizeBeforeCreate = cTaxRepository.findAll().size();

        // Create the CTax
        CTaxDTO cTaxDTO = cTaxMapper.toDto(cTax);
        restCTaxMockMvc.perform(post("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isCreated());

        // Validate the CTax in the database
        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeCreate + 1);
        CTax testCTax = cTaxList.get(cTaxList.size() - 1);
        assertThat(testCTax.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCTax.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCTax.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCTax.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCTax.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testCTax.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCTax.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cTaxRepository.findAll().size();

        // Create the CTax with an existing ID
        cTax.setId(1L);
        CTaxDTO cTaxDTO = cTaxMapper.toDto(cTax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCTaxMockMvc.perform(post("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTax in the database
        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxRepository.findAll().size();
        // set the field null
        cTax.setName(null);

        // Create the CTax, which fails.
        CTaxDTO cTaxDTO = cTaxMapper.toDto(cTax);

        restCTaxMockMvc.perform(post("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isBadRequest());

        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxRepository.findAll().size();
        // set the field null
        cTax.setTransactionType(null);

        // Create the CTax, which fails.
        CTaxDTO cTaxDTO = cTaxMapper.toDto(cTax);

        restCTaxMockMvc.perform(post("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isBadRequest());

        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCTaxes() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList
        restCTaxMockMvc.perform(get("/api/c-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCTax() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get the cTax
        restCTaxMockMvc.perform(get("/api/c-taxes/{id}", cTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cTax.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCTaxesByIdFiltering() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        Long id = cTax.getId();

        defaultCTaxShouldBeFound("id.equals=" + id);
        defaultCTaxShouldNotBeFound("id.notEquals=" + id);

        defaultCTaxShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCTaxShouldNotBeFound("id.greaterThan=" + id);

        defaultCTaxShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCTaxShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCTaxesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name equals to DEFAULT_NAME
        defaultCTaxShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cTaxList where name equals to UPDATED_NAME
        defaultCTaxShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name not equals to DEFAULT_NAME
        defaultCTaxShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cTaxList where name not equals to UPDATED_NAME
        defaultCTaxShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCTaxShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cTaxList where name equals to UPDATED_NAME
        defaultCTaxShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name is not null
        defaultCTaxShouldBeFound("name.specified=true");

        // Get all the cTaxList where name is null
        defaultCTaxShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxesByNameContainsSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name contains DEFAULT_NAME
        defaultCTaxShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cTaxList where name contains UPDATED_NAME
        defaultCTaxShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where name does not contain DEFAULT_NAME
        defaultCTaxShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cTaxList where name does not contain UPDATED_NAME
        defaultCTaxShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCTaxesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description equals to DEFAULT_DESCRIPTION
        defaultCTaxShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxList where description equals to UPDATED_DESCRIPTION
        defaultCTaxShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description not equals to DEFAULT_DESCRIPTION
        defaultCTaxShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxList where description not equals to UPDATED_DESCRIPTION
        defaultCTaxShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCTaxShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cTaxList where description equals to UPDATED_DESCRIPTION
        defaultCTaxShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description is not null
        defaultCTaxShouldBeFound("description.specified=true");

        // Get all the cTaxList where description is null
        defaultCTaxShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description contains DEFAULT_DESCRIPTION
        defaultCTaxShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxList where description contains UPDATED_DESCRIPTION
        defaultCTaxShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where description does not contain DEFAULT_DESCRIPTION
        defaultCTaxShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxList where description does not contain UPDATED_DESCRIPTION
        defaultCTaxShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCTaxesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate equals to DEFAULT_RATE
        defaultCTaxShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the cTaxList where rate equals to UPDATED_RATE
        defaultCTaxShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate not equals to DEFAULT_RATE
        defaultCTaxShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the cTaxList where rate not equals to UPDATED_RATE
        defaultCTaxShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultCTaxShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the cTaxList where rate equals to UPDATED_RATE
        defaultCTaxShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate is not null
        defaultCTaxShouldBeFound("rate.specified=true");

        // Get all the cTaxList where rate is null
        defaultCTaxShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate is greater than or equal to DEFAULT_RATE
        defaultCTaxShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the cTaxList where rate is greater than or equal to UPDATED_RATE
        defaultCTaxShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate is less than or equal to DEFAULT_RATE
        defaultCTaxShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the cTaxList where rate is less than or equal to SMALLER_RATE
        defaultCTaxShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate is less than DEFAULT_RATE
        defaultCTaxShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the cTaxList where rate is less than UPDATED_RATE
        defaultCTaxShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where rate is greater than DEFAULT_RATE
        defaultCTaxShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the cTaxList where rate is greater than SMALLER_RATE
        defaultCTaxShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom equals to DEFAULT_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom equals to UPDATED_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom not equals to UPDATED_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the cTaxList where validFrom equals to UPDATED_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom is not null
        defaultCTaxShouldBeFound("validFrom.specified=true");

        // Get all the cTaxList where validFrom is null
        defaultCTaxShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom is less than DEFAULT_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom is less than UPDATED_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCTaxesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where validFrom is greater than DEFAULT_VALID_FROM
        defaultCTaxShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the cTaxList where validFrom is greater than SMALLER_VALID_FROM
        defaultCTaxShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllCTaxesByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultCTaxShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the cTaxList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultCTaxShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultCTaxShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the cTaxList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultCTaxShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultCTaxShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the cTaxList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultCTaxShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where transactionType is not null
        defaultCTaxShouldBeFound("transactionType.specified=true");

        // Get all the cTaxList where transactionType is null
        defaultCTaxShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where uid equals to DEFAULT_UID
        defaultCTaxShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cTaxList where uid equals to UPDATED_UID
        defaultCTaxShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where uid not equals to DEFAULT_UID
        defaultCTaxShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cTaxList where uid not equals to UPDATED_UID
        defaultCTaxShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where uid in DEFAULT_UID or UPDATED_UID
        defaultCTaxShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cTaxList where uid equals to UPDATED_UID
        defaultCTaxShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where uid is not null
        defaultCTaxShouldBeFound("uid.specified=true");

        // Get all the cTaxList where uid is null
        defaultCTaxShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where active equals to DEFAULT_ACTIVE
        defaultCTaxShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cTaxList where active equals to UPDATED_ACTIVE
        defaultCTaxShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where active not equals to DEFAULT_ACTIVE
        defaultCTaxShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cTaxList where active not equals to UPDATED_ACTIVE
        defaultCTaxShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCTaxShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cTaxList where active equals to UPDATED_ACTIVE
        defaultCTaxShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        // Get all the cTaxList where active is not null
        defaultCTaxShouldBeFound("active.specified=true");

        // Get all the cTaxList where active is null
        defaultCTaxShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cTax.getAdOrganization();
        cTaxRepository.saveAndFlush(cTax);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cTaxList where adOrganization equals to adOrganizationId
        defaultCTaxShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cTaxList where adOrganization equals to adOrganizationId + 1
        defaultCTaxShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCTaxesByTaxCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CTaxCategory taxCategory = cTax.getTaxCategory();
        cTaxRepository.saveAndFlush(cTax);
        Long taxCategoryId = taxCategory.getId();

        // Get all the cTaxList where taxCategory equals to taxCategoryId
        defaultCTaxShouldBeFound("taxCategoryId.equals=" + taxCategoryId);

        // Get all the cTaxList where taxCategory equals to taxCategoryId + 1
        defaultCTaxShouldNotBeFound("taxCategoryId.equals=" + (taxCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCTaxShouldBeFound(String filter) throws Exception {
        restCTaxMockMvc.perform(get("/api/c-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCTaxMockMvc.perform(get("/api/c-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCTaxShouldNotBeFound(String filter) throws Exception {
        restCTaxMockMvc.perform(get("/api/c-taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCTaxMockMvc.perform(get("/api/c-taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCTax() throws Exception {
        // Get the cTax
        restCTaxMockMvc.perform(get("/api/c-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCTax() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        int databaseSizeBeforeUpdate = cTaxRepository.findAll().size();

        // Update the cTax
        CTax updatedCTax = cTaxRepository.findById(cTax.getId()).get();
        // Disconnect from session so that the updates on updatedCTax are not directly saved in db
        em.detach(updatedCTax);
        updatedCTax
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .rate(UPDATED_RATE)
            .validFrom(UPDATED_VALID_FROM)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CTaxDTO cTaxDTO = cTaxMapper.toDto(updatedCTax);

        restCTaxMockMvc.perform(put("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isOk());

        // Validate the CTax in the database
        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeUpdate);
        CTax testCTax = cTaxList.get(cTaxList.size() - 1);
        assertThat(testCTax.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCTax.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCTax.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCTax.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCTax.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testCTax.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCTax.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCTax() throws Exception {
        int databaseSizeBeforeUpdate = cTaxRepository.findAll().size();

        // Create the CTax
        CTaxDTO cTaxDTO = cTaxMapper.toDto(cTax);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCTaxMockMvc.perform(put("/api/c-taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTax in the database
        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCTax() throws Exception {
        // Initialize the database
        cTaxRepository.saveAndFlush(cTax);

        int databaseSizeBeforeDelete = cTaxRepository.findAll().size();

        // Delete the cTax
        restCTaxMockMvc.perform(delete("/api/c-taxes/{id}", cTax.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CTax> cTaxList = cTaxRepository.findAll();
        assertThat(cTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
