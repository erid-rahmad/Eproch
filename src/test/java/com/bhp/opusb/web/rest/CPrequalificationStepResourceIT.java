package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalificationStep;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPrequalificationStepRepository;
import com.bhp.opusb.service.CPrequalificationStepService;
import com.bhp.opusb.service.dto.CPrequalificationStepDTO;
import com.bhp.opusb.service.mapper.CPrequalificationStepMapper;
import com.bhp.opusb.service.dto.CPrequalificationStepCriteria;
import com.bhp.opusb.service.CPrequalificationStepQueryService;

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
 * Integration tests for the {@link CPrequalificationStepResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalificationStepResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalificationStepRepository cPrequalificationStepRepository;

    @Autowired
    private CPrequalificationStepMapper cPrequalificationStepMapper;

    @Autowired
    private CPrequalificationStepService cPrequalificationStepService;

    @Autowired
    private CPrequalificationStepQueryService cPrequalificationStepQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalificationStepMockMvc;

    private CPrequalificationStep cPrequalificationStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationStep createEntity(EntityManager em) {
        CPrequalificationStep cPrequalificationStep = new CPrequalificationStep()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
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
        cPrequalificationStep.setAdOrganization(aDOrganization);
        return cPrequalificationStep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationStep createUpdatedEntity(EntityManager em) {
        CPrequalificationStep cPrequalificationStep = new CPrequalificationStep()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
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
        cPrequalificationStep.setAdOrganization(aDOrganization);
        return cPrequalificationStep;
    }

    @BeforeEach
    public void initTest() {
        cPrequalificationStep = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalificationStep() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationStepRepository.findAll().size();

        // Create the CPrequalificationStep
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(cPrequalificationStep);
        restCPrequalificationStepMockMvc.perform(post("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalificationStep in the database
        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalificationStep testCPrequalificationStep = cPrequalificationStepList.get(cPrequalificationStepList.size() - 1);
        assertThat(testCPrequalificationStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCPrequalificationStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCPrequalificationStep.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCPrequalificationStep.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalificationStep.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalificationStepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationStepRepository.findAll().size();

        // Create the CPrequalificationStep with an existing ID
        cPrequalificationStep.setId(1L);
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(cPrequalificationStep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalificationStepMockMvc.perform(post("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationStep in the database
        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalificationStepRepository.findAll().size();
        // set the field null
        cPrequalificationStep.setName(null);

        // Create the CPrequalificationStep, which fails.
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(cPrequalificationStep);

        restCPrequalificationStepMockMvc.perform(post("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalificationStepRepository.findAll().size();
        // set the field null
        cPrequalificationStep.setDescription(null);

        // Create the CPrequalificationStep, which fails.
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(cPrequalificationStep);

        restCPrequalificationStepMockMvc.perform(post("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationSteps() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalificationStep() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get the cPrequalificationStep
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps/{id}", cPrequalificationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalificationStep.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalificationStepsByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        Long id = cPrequalificationStep.getId();

        defaultCPrequalificationStepShouldBeFound("id.equals=" + id);
        defaultCPrequalificationStepShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalificationStepShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalificationStepShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalificationStepShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalificationStepShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name equals to DEFAULT_NAME
        defaultCPrequalificationStepShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cPrequalificationStepList where name equals to UPDATED_NAME
        defaultCPrequalificationStepShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name not equals to DEFAULT_NAME
        defaultCPrequalificationStepShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cPrequalificationStepList where name not equals to UPDATED_NAME
        defaultCPrequalificationStepShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCPrequalificationStepShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cPrequalificationStepList where name equals to UPDATED_NAME
        defaultCPrequalificationStepShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name is not null
        defaultCPrequalificationStepShouldBeFound("name.specified=true");

        // Get all the cPrequalificationStepList where name is null
        defaultCPrequalificationStepShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name contains DEFAULT_NAME
        defaultCPrequalificationStepShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cPrequalificationStepList where name contains UPDATED_NAME
        defaultCPrequalificationStepShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where name does not contain DEFAULT_NAME
        defaultCPrequalificationStepShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cPrequalificationStepList where name does not contain UPDATED_NAME
        defaultCPrequalificationStepShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description equals to DEFAULT_DESCRIPTION
        defaultCPrequalificationStepShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationStepList where description equals to UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description not equals to DEFAULT_DESCRIPTION
        defaultCPrequalificationStepShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationStepList where description not equals to UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cPrequalificationStepList where description equals to UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description is not null
        defaultCPrequalificationStepShouldBeFound("description.specified=true");

        // Get all the cPrequalificationStepList where description is null
        defaultCPrequalificationStepShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description contains DEFAULT_DESCRIPTION
        defaultCPrequalificationStepShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationStepList where description contains UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where description does not contain DEFAULT_DESCRIPTION
        defaultCPrequalificationStepShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationStepList where description does not contain UPDATED_DESCRIPTION
        defaultCPrequalificationStepShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type equals to DEFAULT_TYPE
        defaultCPrequalificationStepShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cPrequalificationStepList where type equals to UPDATED_TYPE
        defaultCPrequalificationStepShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type not equals to DEFAULT_TYPE
        defaultCPrequalificationStepShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cPrequalificationStepList where type not equals to UPDATED_TYPE
        defaultCPrequalificationStepShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCPrequalificationStepShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cPrequalificationStepList where type equals to UPDATED_TYPE
        defaultCPrequalificationStepShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type is not null
        defaultCPrequalificationStepShouldBeFound("type.specified=true");

        // Get all the cPrequalificationStepList where type is null
        defaultCPrequalificationStepShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type contains DEFAULT_TYPE
        defaultCPrequalificationStepShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cPrequalificationStepList where type contains UPDATED_TYPE
        defaultCPrequalificationStepShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where type does not contain DEFAULT_TYPE
        defaultCPrequalificationStepShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cPrequalificationStepList where type does not contain UPDATED_TYPE
        defaultCPrequalificationStepShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationStepsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where uid equals to DEFAULT_UID
        defaultCPrequalificationStepShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalificationStepList where uid equals to UPDATED_UID
        defaultCPrequalificationStepShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where uid not equals to DEFAULT_UID
        defaultCPrequalificationStepShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalificationStepList where uid not equals to UPDATED_UID
        defaultCPrequalificationStepShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalificationStepShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalificationStepList where uid equals to UPDATED_UID
        defaultCPrequalificationStepShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where uid is not null
        defaultCPrequalificationStepShouldBeFound("uid.specified=true");

        // Get all the cPrequalificationStepList where uid is null
        defaultCPrequalificationStepShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where active equals to DEFAULT_ACTIVE
        defaultCPrequalificationStepShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationStepList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationStepShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalificationStepShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationStepList where active not equals to UPDATED_ACTIVE
        defaultCPrequalificationStepShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalificationStepShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalificationStepList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationStepShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        // Get all the cPrequalificationStepList where active is not null
        defaultCPrequalificationStepShouldBeFound("active.specified=true");

        // Get all the cPrequalificationStepList where active is null
        defaultCPrequalificationStepShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationStepsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalificationStep.getAdOrganization();
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalificationStepList where adOrganization equals to adOrganizationId
        defaultCPrequalificationStepShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalificationStepList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalificationStepShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalificationStepShouldBeFound(String filter) throws Exception {
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalificationStepShouldNotBeFound(String filter) throws Exception {
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalificationStep() throws Exception {
        // Get the cPrequalificationStep
        restCPrequalificationStepMockMvc.perform(get("/api/c-prequalification-steps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalificationStep() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        int databaseSizeBeforeUpdate = cPrequalificationStepRepository.findAll().size();

        // Update the cPrequalificationStep
        CPrequalificationStep updatedCPrequalificationStep = cPrequalificationStepRepository.findById(cPrequalificationStep.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalificationStep are not directly saved in db
        em.detach(updatedCPrequalificationStep);
        updatedCPrequalificationStep
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(updatedCPrequalificationStep);

        restCPrequalificationStepMockMvc.perform(put("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalificationStep in the database
        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeUpdate);
        CPrequalificationStep testCPrequalificationStep = cPrequalificationStepList.get(cPrequalificationStepList.size() - 1);
        assertThat(testCPrequalificationStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCPrequalificationStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCPrequalificationStep.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCPrequalificationStep.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalificationStep.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalificationStep() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalificationStepRepository.findAll().size();

        // Create the CPrequalificationStep
        CPrequalificationStepDTO cPrequalificationStepDTO = cPrequalificationStepMapper.toDto(cPrequalificationStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalificationStepMockMvc.perform(put("/api/c-prequalification-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationStep in the database
        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalificationStep() throws Exception {
        // Initialize the database
        cPrequalificationStepRepository.saveAndFlush(cPrequalificationStep);

        int databaseSizeBeforeDelete = cPrequalificationStepRepository.findAll().size();

        // Delete the cPrequalificationStep
        restCPrequalificationStepMockMvc.perform(delete("/api/c-prequalification-steps/{id}", cPrequalificationStep.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalificationStep> cPrequalificationStepList = cPrequalificationStepRepository.findAll();
        assertThat(cPrequalificationStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
