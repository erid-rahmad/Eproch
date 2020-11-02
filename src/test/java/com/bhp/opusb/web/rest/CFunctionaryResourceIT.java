package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CFunctionary;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CFunctionaryRepository;
import com.bhp.opusb.service.CFunctionaryService;
import com.bhp.opusb.service.dto.CFunctionaryDTO;
import com.bhp.opusb.service.mapper.CFunctionaryMapper;
import com.bhp.opusb.service.dto.CFunctionaryCriteria;
import com.bhp.opusb.service.CFunctionaryQueryService;

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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CFunctionaryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CFunctionaryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CFunctionaryRepository cFunctionaryRepository;

    @Autowired
    private CFunctionaryMapper cFunctionaryMapper;

    @Autowired
    private CFunctionaryService cFunctionaryService;

    @Autowired
    private CFunctionaryQueryService cFunctionaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCFunctionaryMockMvc;

    private CFunctionary cFunctionary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CFunctionary createEntity(EntityManager em) {
        CFunctionary cFunctionary = new CFunctionary()
            .name(DEFAULT_NAME)
            .position(DEFAULT_POSITION)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
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
        cFunctionary.setAdOrganization(aDOrganization);
        return cFunctionary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CFunctionary createUpdatedEntity(EntityManager em) {
        CFunctionary cFunctionary = new CFunctionary()
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
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
        cFunctionary.setAdOrganization(aDOrganization);
        return cFunctionary;
    }

    @BeforeEach
    public void initTest() {
        cFunctionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCFunctionary() throws Exception {
        int databaseSizeBeforeCreate = cFunctionaryRepository.findAll().size();

        // Create the CFunctionary
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);
        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CFunctionary in the database
        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeCreate + 1);
        CFunctionary testCFunctionary = cFunctionaryList.get(cFunctionaryList.size() - 1);
        assertThat(testCFunctionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCFunctionary.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCFunctionary.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCFunctionary.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCFunctionary.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCFunctionary.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCFunctionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cFunctionaryRepository.findAll().size();

        // Create the CFunctionary with an existing ID
        cFunctionary.setId(1L);
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CFunctionary in the database
        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFunctionaryRepository.findAll().size();
        // set the field null
        cFunctionary.setName(null);

        // Create the CFunctionary, which fails.
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFunctionaryRepository.findAll().size();
        // set the field null
        cFunctionary.setPosition(null);

        // Create the CFunctionary, which fails.
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFunctionaryRepository.findAll().size();
        // set the field null
        cFunctionary.setPhone(null);

        // Create the CFunctionary, which fails.
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFunctionaryRepository.findAll().size();
        // set the field null
        cFunctionary.setEmail(null);

        // Create the CFunctionary, which fails.
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        restCFunctionaryMockMvc.perform(post("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCFunctionaries() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cFunctionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCFunctionary() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get the cFunctionary
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries/{id}", cFunctionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cFunctionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCFunctionariesByIdFiltering() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        Long id = cFunctionary.getId();

        defaultCFunctionaryShouldBeFound("id.equals=" + id);
        defaultCFunctionaryShouldNotBeFound("id.notEquals=" + id);

        defaultCFunctionaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCFunctionaryShouldNotBeFound("id.greaterThan=" + id);

        defaultCFunctionaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCFunctionaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name equals to DEFAULT_NAME
        defaultCFunctionaryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cFunctionaryList where name equals to UPDATED_NAME
        defaultCFunctionaryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name not equals to DEFAULT_NAME
        defaultCFunctionaryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cFunctionaryList where name not equals to UPDATED_NAME
        defaultCFunctionaryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCFunctionaryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cFunctionaryList where name equals to UPDATED_NAME
        defaultCFunctionaryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name is not null
        defaultCFunctionaryShouldBeFound("name.specified=true");

        // Get all the cFunctionaryList where name is null
        defaultCFunctionaryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCFunctionariesByNameContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name contains DEFAULT_NAME
        defaultCFunctionaryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cFunctionaryList where name contains UPDATED_NAME
        defaultCFunctionaryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where name does not contain DEFAULT_NAME
        defaultCFunctionaryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cFunctionaryList where name does not contain UPDATED_NAME
        defaultCFunctionaryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position equals to DEFAULT_POSITION
        defaultCFunctionaryShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the cFunctionaryList where position equals to UPDATED_POSITION
        defaultCFunctionaryShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position not equals to DEFAULT_POSITION
        defaultCFunctionaryShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the cFunctionaryList where position not equals to UPDATED_POSITION
        defaultCFunctionaryShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultCFunctionaryShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the cFunctionaryList where position equals to UPDATED_POSITION
        defaultCFunctionaryShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position is not null
        defaultCFunctionaryShouldBeFound("position.specified=true");

        // Get all the cFunctionaryList where position is null
        defaultCFunctionaryShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllCFunctionariesByPositionContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position contains DEFAULT_POSITION
        defaultCFunctionaryShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the cFunctionaryList where position contains UPDATED_POSITION
        defaultCFunctionaryShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where position does not contain DEFAULT_POSITION
        defaultCFunctionaryShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the cFunctionaryList where position does not contain UPDATED_POSITION
        defaultCFunctionaryShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone equals to DEFAULT_PHONE
        defaultCFunctionaryShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the cFunctionaryList where phone equals to UPDATED_PHONE
        defaultCFunctionaryShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone not equals to DEFAULT_PHONE
        defaultCFunctionaryShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the cFunctionaryList where phone not equals to UPDATED_PHONE
        defaultCFunctionaryShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCFunctionaryShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the cFunctionaryList where phone equals to UPDATED_PHONE
        defaultCFunctionaryShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone is not null
        defaultCFunctionaryShouldBeFound("phone.specified=true");

        // Get all the cFunctionaryList where phone is null
        defaultCFunctionaryShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCFunctionariesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone contains DEFAULT_PHONE
        defaultCFunctionaryShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the cFunctionaryList where phone contains UPDATED_PHONE
        defaultCFunctionaryShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where phone does not contain DEFAULT_PHONE
        defaultCFunctionaryShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the cFunctionaryList where phone does not contain UPDATED_PHONE
        defaultCFunctionaryShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email equals to DEFAULT_EMAIL
        defaultCFunctionaryShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the cFunctionaryList where email equals to UPDATED_EMAIL
        defaultCFunctionaryShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email not equals to DEFAULT_EMAIL
        defaultCFunctionaryShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the cFunctionaryList where email not equals to UPDATED_EMAIL
        defaultCFunctionaryShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCFunctionaryShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the cFunctionaryList where email equals to UPDATED_EMAIL
        defaultCFunctionaryShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email is not null
        defaultCFunctionaryShouldBeFound("email.specified=true");

        // Get all the cFunctionaryList where email is null
        defaultCFunctionaryShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCFunctionariesByEmailContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email contains DEFAULT_EMAIL
        defaultCFunctionaryShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the cFunctionaryList where email contains UPDATED_EMAIL
        defaultCFunctionaryShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where email does not contain DEFAULT_EMAIL
        defaultCFunctionaryShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the cFunctionaryList where email does not contain UPDATED_EMAIL
        defaultCFunctionaryShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where uid equals to DEFAULT_UID
        defaultCFunctionaryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cFunctionaryList where uid equals to UPDATED_UID
        defaultCFunctionaryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where uid not equals to DEFAULT_UID
        defaultCFunctionaryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cFunctionaryList where uid not equals to UPDATED_UID
        defaultCFunctionaryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCFunctionaryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cFunctionaryList where uid equals to UPDATED_UID
        defaultCFunctionaryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where uid is not null
        defaultCFunctionaryShouldBeFound("uid.specified=true");

        // Get all the cFunctionaryList where uid is null
        defaultCFunctionaryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where active equals to DEFAULT_ACTIVE
        defaultCFunctionaryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cFunctionaryList where active equals to UPDATED_ACTIVE
        defaultCFunctionaryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where active not equals to DEFAULT_ACTIVE
        defaultCFunctionaryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cFunctionaryList where active not equals to UPDATED_ACTIVE
        defaultCFunctionaryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCFunctionaryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cFunctionaryList where active equals to UPDATED_ACTIVE
        defaultCFunctionaryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        // Get all the cFunctionaryList where active is not null
        defaultCFunctionaryShouldBeFound("active.specified=true");

        // Get all the cFunctionaryList where active is null
        defaultCFunctionaryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCFunctionariesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cFunctionary.getAdOrganization();
        cFunctionaryRepository.saveAndFlush(cFunctionary);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cFunctionaryList where adOrganization equals to adOrganizationId
        defaultCFunctionaryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cFunctionaryList where adOrganization equals to adOrganizationId + 1
        defaultCFunctionaryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCFunctionariesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        cFunctionary.setVendor(vendor);
        cFunctionaryRepository.saveAndFlush(cFunctionary);
        Long vendorId = vendor.getId();

        // Get all the cFunctionaryList where vendor equals to vendorId
        defaultCFunctionaryShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cFunctionaryList where vendor equals to vendorId + 1
        defaultCFunctionaryShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCFunctionaryShouldBeFound(String filter) throws Exception {
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cFunctionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCFunctionaryShouldNotBeFound(String filter) throws Exception {
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCFunctionary() throws Exception {
        // Get the cFunctionary
        restCFunctionaryMockMvc.perform(get("/api/c-functionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCFunctionary() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        int databaseSizeBeforeUpdate = cFunctionaryRepository.findAll().size();

        // Update the cFunctionary
        CFunctionary updatedCFunctionary = cFunctionaryRepository.findById(cFunctionary.getId()).get();
        // Disconnect from session so that the updates on updatedCFunctionary are not directly saved in db
        em.detach(updatedCFunctionary);
        updatedCFunctionary
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(updatedCFunctionary);

        restCFunctionaryMockMvc.perform(put("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CFunctionary in the database
        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeUpdate);
        CFunctionary testCFunctionary = cFunctionaryList.get(cFunctionaryList.size() - 1);
        assertThat(testCFunctionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCFunctionary.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCFunctionary.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCFunctionary.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCFunctionary.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCFunctionary.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCFunctionary() throws Exception {
        int databaseSizeBeforeUpdate = cFunctionaryRepository.findAll().size();

        // Create the CFunctionary
        CFunctionaryDTO cFunctionaryDTO = cFunctionaryMapper.toDto(cFunctionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCFunctionaryMockMvc.perform(put("/api/c-functionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cFunctionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CFunctionary in the database
        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCFunctionary() throws Exception {
        // Initialize the database
        cFunctionaryRepository.saveAndFlush(cFunctionary);

        int databaseSizeBeforeDelete = cFunctionaryRepository.findAll().size();

        // Delete the cFunctionary
        restCFunctionaryMockMvc.perform(delete("/api/c-functionaries/{id}", cFunctionary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CFunctionary> cFunctionaryList = cFunctionaryRepository.findAll();
        assertThat(cFunctionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
