package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CompanyFunctionary;
import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.repository.CompanyFunctionaryRepository;
import com.bhp.opusb.service.CompanyFunctionaryService;
import com.bhp.opusb.service.dto.CompanyFunctionaryDTO;
import com.bhp.opusb.service.mapper.CompanyFunctionaryMapper;
import com.bhp.opusb.service.dto.CompanyFunctionaryCriteria;
import com.bhp.opusb.service.CompanyFunctionaryQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyFunctionaryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CompanyFunctionaryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private CompanyFunctionaryRepository companyFunctionaryRepository;

    @Autowired
    private CompanyFunctionaryMapper companyFunctionaryMapper;

    @Autowired
    private CompanyFunctionaryService companyFunctionaryService;

    @Autowired
    private CompanyFunctionaryQueryService companyFunctionaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyFunctionaryMockMvc;

    private CompanyFunctionary companyFunctionary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyFunctionary createEntity(EntityManager em) {
        CompanyFunctionary companyFunctionary = new CompanyFunctionary()
            .name(DEFAULT_NAME)
            .position(DEFAULT_POSITION)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return companyFunctionary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyFunctionary createUpdatedEntity(EntityManager em) {
        CompanyFunctionary companyFunctionary = new CompanyFunctionary()
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        return companyFunctionary;
    }

    @BeforeEach
    public void initTest() {
        companyFunctionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyFunctionary() throws Exception {
        int databaseSizeBeforeCreate = companyFunctionaryRepository.findAll().size();

        // Create the CompanyFunctionary
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);
        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyFunctionary in the database
        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyFunctionary testCompanyFunctionary = companyFunctionaryList.get(companyFunctionaryList.size() - 1);
        assertThat(testCompanyFunctionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyFunctionary.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCompanyFunctionary.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCompanyFunctionary.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCompanyFunctionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyFunctionaryRepository.findAll().size();

        // Create the CompanyFunctionary with an existing ID
        companyFunctionary.setId(1L);
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyFunctionary in the database
        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyFunctionaryRepository.findAll().size();
        // set the field null
        companyFunctionary.setName(null);

        // Create the CompanyFunctionary, which fails.
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyFunctionaryRepository.findAll().size();
        // set the field null
        companyFunctionary.setPosition(null);

        // Create the CompanyFunctionary, which fails.
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyFunctionaryRepository.findAll().size();
        // set the field null
        companyFunctionary.setPhone(null);

        // Create the CompanyFunctionary, which fails.
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyFunctionaryRepository.findAll().size();
        // set the field null
        companyFunctionary.setEmail(null);

        // Create the CompanyFunctionary, which fails.
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        restCompanyFunctionaryMockMvc.perform(post("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionaries() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyFunctionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getCompanyFunctionary() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get the companyFunctionary
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries/{id}", companyFunctionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyFunctionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }


    @Test
    @Transactional
    public void getCompanyFunctionariesByIdFiltering() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        Long id = companyFunctionary.getId();

        defaultCompanyFunctionaryShouldBeFound("id.equals=" + id);
        defaultCompanyFunctionaryShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyFunctionaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyFunctionaryShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyFunctionaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyFunctionaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name equals to DEFAULT_NAME
        defaultCompanyFunctionaryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyFunctionaryList where name equals to UPDATED_NAME
        defaultCompanyFunctionaryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name not equals to DEFAULT_NAME
        defaultCompanyFunctionaryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyFunctionaryList where name not equals to UPDATED_NAME
        defaultCompanyFunctionaryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyFunctionaryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyFunctionaryList where name equals to UPDATED_NAME
        defaultCompanyFunctionaryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name is not null
        defaultCompanyFunctionaryShouldBeFound("name.specified=true");

        // Get all the companyFunctionaryList where name is null
        defaultCompanyFunctionaryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name contains DEFAULT_NAME
        defaultCompanyFunctionaryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyFunctionaryList where name contains UPDATED_NAME
        defaultCompanyFunctionaryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where name does not contain DEFAULT_NAME
        defaultCompanyFunctionaryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyFunctionaryList where name does not contain UPDATED_NAME
        defaultCompanyFunctionaryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position equals to DEFAULT_POSITION
        defaultCompanyFunctionaryShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the companyFunctionaryList where position equals to UPDATED_POSITION
        defaultCompanyFunctionaryShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position not equals to DEFAULT_POSITION
        defaultCompanyFunctionaryShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the companyFunctionaryList where position not equals to UPDATED_POSITION
        defaultCompanyFunctionaryShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultCompanyFunctionaryShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the companyFunctionaryList where position equals to UPDATED_POSITION
        defaultCompanyFunctionaryShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position is not null
        defaultCompanyFunctionaryShouldBeFound("position.specified=true");

        // Get all the companyFunctionaryList where position is null
        defaultCompanyFunctionaryShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position contains DEFAULT_POSITION
        defaultCompanyFunctionaryShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the companyFunctionaryList where position contains UPDATED_POSITION
        defaultCompanyFunctionaryShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where position does not contain DEFAULT_POSITION
        defaultCompanyFunctionaryShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the companyFunctionaryList where position does not contain UPDATED_POSITION
        defaultCompanyFunctionaryShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone equals to DEFAULT_PHONE
        defaultCompanyFunctionaryShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the companyFunctionaryList where phone equals to UPDATED_PHONE
        defaultCompanyFunctionaryShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone not equals to DEFAULT_PHONE
        defaultCompanyFunctionaryShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the companyFunctionaryList where phone not equals to UPDATED_PHONE
        defaultCompanyFunctionaryShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCompanyFunctionaryShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the companyFunctionaryList where phone equals to UPDATED_PHONE
        defaultCompanyFunctionaryShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone is not null
        defaultCompanyFunctionaryShouldBeFound("phone.specified=true");

        // Get all the companyFunctionaryList where phone is null
        defaultCompanyFunctionaryShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone contains DEFAULT_PHONE
        defaultCompanyFunctionaryShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the companyFunctionaryList where phone contains UPDATED_PHONE
        defaultCompanyFunctionaryShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where phone does not contain DEFAULT_PHONE
        defaultCompanyFunctionaryShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the companyFunctionaryList where phone does not contain UPDATED_PHONE
        defaultCompanyFunctionaryShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email equals to DEFAULT_EMAIL
        defaultCompanyFunctionaryShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyFunctionaryList where email equals to UPDATED_EMAIL
        defaultCompanyFunctionaryShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email not equals to DEFAULT_EMAIL
        defaultCompanyFunctionaryShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyFunctionaryList where email not equals to UPDATED_EMAIL
        defaultCompanyFunctionaryShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyFunctionaryShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyFunctionaryList where email equals to UPDATED_EMAIL
        defaultCompanyFunctionaryShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email is not null
        defaultCompanyFunctionaryShouldBeFound("email.specified=true");

        // Get all the companyFunctionaryList where email is null
        defaultCompanyFunctionaryShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email contains DEFAULT_EMAIL
        defaultCompanyFunctionaryShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyFunctionaryList where email contains UPDATED_EMAIL
        defaultCompanyFunctionaryShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompanyFunctionariesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        // Get all the companyFunctionaryList where email does not contain DEFAULT_EMAIL
        defaultCompanyFunctionaryShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyFunctionaryList where email does not contain UPDATED_EMAIL
        defaultCompanyFunctionaryShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCompanyFunctionariesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);
        Vendor vendor = VendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        companyFunctionary.setVendor(vendor);
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);
        Long vendorId = vendor.getId();

        // Get all the companyFunctionaryList where vendor equals to vendorId
        defaultCompanyFunctionaryShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the companyFunctionaryList where vendor equals to vendorId + 1
        defaultCompanyFunctionaryShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyFunctionaryShouldBeFound(String filter) throws Exception {
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyFunctionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyFunctionaryShouldNotBeFound(String filter) throws Exception {
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompanyFunctionary() throws Exception {
        // Get the companyFunctionary
        restCompanyFunctionaryMockMvc.perform(get("/api/company-functionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyFunctionary() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        int databaseSizeBeforeUpdate = companyFunctionaryRepository.findAll().size();

        // Update the companyFunctionary
        CompanyFunctionary updatedCompanyFunctionary = companyFunctionaryRepository.findById(companyFunctionary.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyFunctionary are not directly saved in db
        em.detach(updatedCompanyFunctionary);
        updatedCompanyFunctionary
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(updatedCompanyFunctionary);

        restCompanyFunctionaryMockMvc.perform(put("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyFunctionary in the database
        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeUpdate);
        CompanyFunctionary testCompanyFunctionary = companyFunctionaryList.get(companyFunctionaryList.size() - 1);
        assertThat(testCompanyFunctionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyFunctionary.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCompanyFunctionary.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompanyFunctionary.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyFunctionary() throws Exception {
        int databaseSizeBeforeUpdate = companyFunctionaryRepository.findAll().size();

        // Create the CompanyFunctionary
        CompanyFunctionaryDTO companyFunctionaryDTO = companyFunctionaryMapper.toDto(companyFunctionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyFunctionaryMockMvc.perform(put("/api/company-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyFunctionary in the database
        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyFunctionary() throws Exception {
        // Initialize the database
        companyFunctionaryRepository.saveAndFlush(companyFunctionary);

        int databaseSizeBeforeDelete = companyFunctionaryRepository.findAll().size();

        // Delete the companyFunctionary
        restCompanyFunctionaryMockMvc.perform(delete("/api/company-functionaries/{id}", companyFunctionary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyFunctionary> companyFunctionaryList = companyFunctionaryRepository.findAll();
        assertThat(companyFunctionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
