package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.COrganization;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.COrganizationRepository;
import com.bhp.opusb.service.COrganizationService;
import com.bhp.opusb.service.dto.COrganizationDTO;
import com.bhp.opusb.service.mapper.COrganizationMapper;
import com.bhp.opusb.service.dto.COrganizationCriteria;
import com.bhp.opusb.service.COrganizationQueryService;

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
 * Integration tests for the {@link COrganizationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class COrganizationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private COrganizationRepository cOrganizationRepository;

    @Autowired
    private COrganizationMapper cOrganizationMapper;

    @Autowired
    private COrganizationService cOrganizationService;

    @Autowired
    private COrganizationQueryService cOrganizationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCOrganizationMockMvc;

    private COrganization cOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static COrganization createEntity(EntityManager em) {
        COrganization cOrganization = new COrganization()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
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
        cOrganization.setAdOrganization(aDOrganization);
        return cOrganization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static COrganization createUpdatedEntity(EntityManager em) {
        COrganization cOrganization = new COrganization()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
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
        cOrganization.setAdOrganization(aDOrganization);
        return cOrganization;
    }

    @BeforeEach
    public void initTest() {
        cOrganization = createEntity(em);
    }

    @Test
    @Transactional
    public void createCOrganization() throws Exception {
        int databaseSizeBeforeCreate = cOrganizationRepository.findAll().size();

        // Create the COrganization
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(cOrganization);
        restCOrganizationMockMvc.perform(post("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isCreated());

        // Validate the COrganization in the database
        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        COrganization testCOrganization = cOrganizationList.get(cOrganizationList.size() - 1);
        assertThat(testCOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCOrganization.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCOrganization.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCOrganization.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cOrganizationRepository.findAll().size();

        // Create the COrganization with an existing ID
        cOrganization.setId(1L);
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(cOrganization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCOrganizationMockMvc.perform(post("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the COrganization in the database
        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cOrganizationRepository.findAll().size();
        // set the field null
        cOrganization.setName(null);

        // Create the COrganization, which fails.
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(cOrganization);

        restCOrganizationMockMvc.perform(post("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cOrganizationRepository.findAll().size();
        // set the field null
        cOrganization.setCode(null);

        // Create the COrganization, which fails.
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(cOrganization);

        restCOrganizationMockMvc.perform(post("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCOrganizations() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList
        restCOrganizationMockMvc.perform(get("/api/c-organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCOrganization() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get the cOrganization
        restCOrganizationMockMvc.perform(get("/api/c-organizations/{id}", cOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cOrganization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCOrganizationsByIdFiltering() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        Long id = cOrganization.getId();

        defaultCOrganizationShouldBeFound("id.equals=" + id);
        defaultCOrganizationShouldNotBeFound("id.notEquals=" + id);

        defaultCOrganizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCOrganizationShouldNotBeFound("id.greaterThan=" + id);

        defaultCOrganizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCOrganizationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCOrganizationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name equals to DEFAULT_NAME
        defaultCOrganizationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cOrganizationList where name equals to UPDATED_NAME
        defaultCOrganizationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name not equals to DEFAULT_NAME
        defaultCOrganizationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cOrganizationList where name not equals to UPDATED_NAME
        defaultCOrganizationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCOrganizationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cOrganizationList where name equals to UPDATED_NAME
        defaultCOrganizationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name is not null
        defaultCOrganizationShouldBeFound("name.specified=true");

        // Get all the cOrganizationList where name is null
        defaultCOrganizationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationsByNameContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name contains DEFAULT_NAME
        defaultCOrganizationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cOrganizationList where name contains UPDATED_NAME
        defaultCOrganizationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where name does not contain DEFAULT_NAME
        defaultCOrganizationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cOrganizationList where name does not contain UPDATED_NAME
        defaultCOrganizationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCOrganizationsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code equals to DEFAULT_CODE
        defaultCOrganizationShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cOrganizationList where code equals to UPDATED_CODE
        defaultCOrganizationShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code not equals to DEFAULT_CODE
        defaultCOrganizationShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cOrganizationList where code not equals to UPDATED_CODE
        defaultCOrganizationShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCOrganizationShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cOrganizationList where code equals to UPDATED_CODE
        defaultCOrganizationShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code is not null
        defaultCOrganizationShouldBeFound("code.specified=true");

        // Get all the cOrganizationList where code is null
        defaultCOrganizationShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code contains DEFAULT_CODE
        defaultCOrganizationShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cOrganizationList where code contains UPDATED_CODE
        defaultCOrganizationShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where code does not contain DEFAULT_CODE
        defaultCOrganizationShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cOrganizationList where code does not contain UPDATED_CODE
        defaultCOrganizationShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description equals to DEFAULT_DESCRIPTION
        defaultCOrganizationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cOrganizationList where description equals to UPDATED_DESCRIPTION
        defaultCOrganizationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description not equals to DEFAULT_DESCRIPTION
        defaultCOrganizationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cOrganizationList where description not equals to UPDATED_DESCRIPTION
        defaultCOrganizationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCOrganizationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cOrganizationList where description equals to UPDATED_DESCRIPTION
        defaultCOrganizationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description is not null
        defaultCOrganizationShouldBeFound("description.specified=true");

        // Get all the cOrganizationList where description is null
        defaultCOrganizationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description contains DEFAULT_DESCRIPTION
        defaultCOrganizationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cOrganizationList where description contains UPDATED_DESCRIPTION
        defaultCOrganizationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where description does not contain DEFAULT_DESCRIPTION
        defaultCOrganizationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cOrganizationList where description does not contain UPDATED_DESCRIPTION
        defaultCOrganizationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCOrganizationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where uid equals to DEFAULT_UID
        defaultCOrganizationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cOrganizationList where uid equals to UPDATED_UID
        defaultCOrganizationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where uid not equals to DEFAULT_UID
        defaultCOrganizationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cOrganizationList where uid not equals to UPDATED_UID
        defaultCOrganizationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where uid in DEFAULT_UID or UPDATED_UID
        defaultCOrganizationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cOrganizationList where uid equals to UPDATED_UID
        defaultCOrganizationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where uid is not null
        defaultCOrganizationShouldBeFound("uid.specified=true");

        // Get all the cOrganizationList where uid is null
        defaultCOrganizationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where active equals to DEFAULT_ACTIVE
        defaultCOrganizationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cOrganizationList where active equals to UPDATED_ACTIVE
        defaultCOrganizationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where active not equals to DEFAULT_ACTIVE
        defaultCOrganizationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cOrganizationList where active not equals to UPDATED_ACTIVE
        defaultCOrganizationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCOrganizationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cOrganizationList where active equals to UPDATED_ACTIVE
        defaultCOrganizationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        // Get all the cOrganizationList where active is not null
        defaultCOrganizationShouldBeFound("active.specified=true");

        // Get all the cOrganizationList where active is null
        defaultCOrganizationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCOrganizationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cOrganization.getAdOrganization();
        cOrganizationRepository.saveAndFlush(cOrganization);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cOrganizationList where adOrganization equals to adOrganizationId
        defaultCOrganizationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cOrganizationList where adOrganization equals to adOrganizationId + 1
        defaultCOrganizationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCOrganizationShouldBeFound(String filter) throws Exception {
        restCOrganizationMockMvc.perform(get("/api/c-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCOrganizationMockMvc.perform(get("/api/c-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCOrganizationShouldNotBeFound(String filter) throws Exception {
        restCOrganizationMockMvc.perform(get("/api/c-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCOrganizationMockMvc.perform(get("/api/c-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCOrganization() throws Exception {
        // Get the cOrganization
        restCOrganizationMockMvc.perform(get("/api/c-organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCOrganization() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        int databaseSizeBeforeUpdate = cOrganizationRepository.findAll().size();

        // Update the cOrganization
        COrganization updatedCOrganization = cOrganizationRepository.findById(cOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedCOrganization are not directly saved in db
        em.detach(updatedCOrganization);
        updatedCOrganization
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(updatedCOrganization);

        restCOrganizationMockMvc.perform(put("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isOk());

        // Validate the COrganization in the database
        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeUpdate);
        COrganization testCOrganization = cOrganizationList.get(cOrganizationList.size() - 1);
        assertThat(testCOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCOrganization.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCOrganization.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCOrganization.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCOrganization() throws Exception {
        int databaseSizeBeforeUpdate = cOrganizationRepository.findAll().size();

        // Create the COrganization
        COrganizationDTO cOrganizationDTO = cOrganizationMapper.toDto(cOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCOrganizationMockMvc.perform(put("/api/c-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the COrganization in the database
        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCOrganization() throws Exception {
        // Initialize the database
        cOrganizationRepository.saveAndFlush(cOrganization);

        int databaseSizeBeforeDelete = cOrganizationRepository.findAll().size();

        // Delete the cOrganization
        restCOrganizationMockMvc.perform(delete("/api/c-organizations/{id}", cOrganization.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<COrganization> cOrganizationList = cOrganizationRepository.findAll();
        assertThat(cOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
