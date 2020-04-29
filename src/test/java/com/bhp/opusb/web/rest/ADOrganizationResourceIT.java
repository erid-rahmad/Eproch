package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.repository.ADOrganizationRepository;
import com.bhp.opusb.service.ADOrganizationService;
import com.bhp.opusb.service.dto.ADOrganizationDTO;
import com.bhp.opusb.service.mapper.ADOrganizationMapper;
import com.bhp.opusb.service.dto.ADOrganizationCriteria;
import com.bhp.opusb.service.ADOrganizationQueryService;

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
 * Integration tests for the {@link ADOrganizationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADOrganizationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADOrganizationRepository aDOrganizationRepository;

    @Autowired
    private ADOrganizationMapper aDOrganizationMapper;

    @Autowired
    private ADOrganizationService aDOrganizationService;

    @Autowired
    private ADOrganizationQueryService aDOrganizationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADOrganizationMockMvc;

    private ADOrganization aDOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADOrganization createEntity(EntityManager em) {
        ADOrganization aDOrganization = new ADOrganization()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDOrganization.setAdClient(aDClient);
        return aDOrganization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADOrganization createUpdatedEntity(EntityManager em) {
        ADOrganization aDOrganization = new ADOrganization()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createUpdatedEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDOrganization.setAdClient(aDClient);
        return aDOrganization;
    }

    @BeforeEach
    public void initTest() {
        aDOrganization = createEntity(em);
    }

    @Test
    @Transactional
    public void createADOrganization() throws Exception {
        int databaseSizeBeforeCreate = aDOrganizationRepository.findAll().size();

        // Create the ADOrganization
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(aDOrganization);
        restADOrganizationMockMvc.perform(post("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isCreated());

        // Validate the ADOrganization in the database
        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        ADOrganization testADOrganization = aDOrganizationList.get(aDOrganizationList.size() - 1);
        assertThat(testADOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADOrganization.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testADOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADOrganization.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDOrganizationRepository.findAll().size();

        // Create the ADOrganization with an existing ID
        aDOrganization.setId(1L);
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(aDOrganization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADOrganizationMockMvc.perform(post("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADOrganization in the database
        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDOrganizationRepository.findAll().size();
        // set the field null
        aDOrganization.setName(null);

        // Create the ADOrganization, which fails.
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(aDOrganization);

        restADOrganizationMockMvc.perform(post("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDOrganizationRepository.findAll().size();
        // set the field null
        aDOrganization.setCode(null);

        // Create the ADOrganization, which fails.
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(aDOrganization);

        restADOrganizationMockMvc.perform(post("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADOrganizations() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList
        restADOrganizationMockMvc.perform(get("/api/ad-organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADOrganization() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get the aDOrganization
        restADOrganizationMockMvc.perform(get("/api/ad-organizations/{id}", aDOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDOrganization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADOrganizationsByIdFiltering() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        Long id = aDOrganization.getId();

        defaultADOrganizationShouldBeFound("id.equals=" + id);
        defaultADOrganizationShouldNotBeFound("id.notEquals=" + id);

        defaultADOrganizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADOrganizationShouldNotBeFound("id.greaterThan=" + id);

        defaultADOrganizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADOrganizationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADOrganizationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name equals to DEFAULT_NAME
        defaultADOrganizationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDOrganizationList where name equals to UPDATED_NAME
        defaultADOrganizationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name not equals to DEFAULT_NAME
        defaultADOrganizationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDOrganizationList where name not equals to UPDATED_NAME
        defaultADOrganizationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADOrganizationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDOrganizationList where name equals to UPDATED_NAME
        defaultADOrganizationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name is not null
        defaultADOrganizationShouldBeFound("name.specified=true");

        // Get all the aDOrganizationList where name is null
        defaultADOrganizationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name contains DEFAULT_NAME
        defaultADOrganizationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDOrganizationList where name contains UPDATED_NAME
        defaultADOrganizationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where name does not contain DEFAULT_NAME
        defaultADOrganizationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDOrganizationList where name does not contain UPDATED_NAME
        defaultADOrganizationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADOrganizationsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code equals to DEFAULT_CODE
        defaultADOrganizationShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the aDOrganizationList where code equals to UPDATED_CODE
        defaultADOrganizationShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code not equals to DEFAULT_CODE
        defaultADOrganizationShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the aDOrganizationList where code not equals to UPDATED_CODE
        defaultADOrganizationShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code in DEFAULT_CODE or UPDATED_CODE
        defaultADOrganizationShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the aDOrganizationList where code equals to UPDATED_CODE
        defaultADOrganizationShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code is not null
        defaultADOrganizationShouldBeFound("code.specified=true");

        // Get all the aDOrganizationList where code is null
        defaultADOrganizationShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationsByCodeContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code contains DEFAULT_CODE
        defaultADOrganizationShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the aDOrganizationList where code contains UPDATED_CODE
        defaultADOrganizationShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where code does not contain DEFAULT_CODE
        defaultADOrganizationShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the aDOrganizationList where code does not contain UPDATED_CODE
        defaultADOrganizationShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description equals to DEFAULT_DESCRIPTION
        defaultADOrganizationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDOrganizationList where description equals to UPDATED_DESCRIPTION
        defaultADOrganizationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description not equals to DEFAULT_DESCRIPTION
        defaultADOrganizationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDOrganizationList where description not equals to UPDATED_DESCRIPTION
        defaultADOrganizationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADOrganizationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDOrganizationList where description equals to UPDATED_DESCRIPTION
        defaultADOrganizationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description is not null
        defaultADOrganizationShouldBeFound("description.specified=true");

        // Get all the aDOrganizationList where description is null
        defaultADOrganizationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description contains DEFAULT_DESCRIPTION
        defaultADOrganizationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDOrganizationList where description contains UPDATED_DESCRIPTION
        defaultADOrganizationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where description does not contain DEFAULT_DESCRIPTION
        defaultADOrganizationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDOrganizationList where description does not contain UPDATED_DESCRIPTION
        defaultADOrganizationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADOrganizationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where active equals to DEFAULT_ACTIVE
        defaultADOrganizationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDOrganizationList where active equals to UPDATED_ACTIVE
        defaultADOrganizationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where active not equals to DEFAULT_ACTIVE
        defaultADOrganizationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDOrganizationList where active not equals to UPDATED_ACTIVE
        defaultADOrganizationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADOrganizationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDOrganizationList where active equals to UPDATED_ACTIVE
        defaultADOrganizationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        // Get all the aDOrganizationList where active is not null
        defaultADOrganizationShouldBeFound("active.specified=true");

        // Get all the aDOrganizationList where active is null
        defaultADOrganizationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADOrganizationsByAdClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADClient adClient = aDOrganization.getAdClient();
        aDOrganizationRepository.saveAndFlush(aDOrganization);
        Long adClientId = adClient.getId();

        // Get all the aDOrganizationList where adClient equals to adClientId
        defaultADOrganizationShouldBeFound("adClientId.equals=" + adClientId);

        // Get all the aDOrganizationList where adClient equals to adClientId + 1
        defaultADOrganizationShouldNotBeFound("adClientId.equals=" + (adClientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADOrganizationShouldBeFound(String filter) throws Exception {
        restADOrganizationMockMvc.perform(get("/api/ad-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADOrganizationMockMvc.perform(get("/api/ad-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADOrganizationShouldNotBeFound(String filter) throws Exception {
        restADOrganizationMockMvc.perform(get("/api/ad-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADOrganizationMockMvc.perform(get("/api/ad-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADOrganization() throws Exception {
        // Get the aDOrganization
        restADOrganizationMockMvc.perform(get("/api/ad-organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADOrganization() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        int databaseSizeBeforeUpdate = aDOrganizationRepository.findAll().size();

        // Update the aDOrganization
        ADOrganization updatedADOrganization = aDOrganizationRepository.findById(aDOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedADOrganization are not directly saved in db
        em.detach(updatedADOrganization);
        updatedADOrganization
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(updatedADOrganization);

        restADOrganizationMockMvc.perform(put("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isOk());

        // Validate the ADOrganization in the database
        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeUpdate);
        ADOrganization testADOrganization = aDOrganizationList.get(aDOrganizationList.size() - 1);
        assertThat(testADOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADOrganization.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testADOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADOrganization.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADOrganization() throws Exception {
        int databaseSizeBeforeUpdate = aDOrganizationRepository.findAll().size();

        // Create the ADOrganization
        ADOrganizationDTO aDOrganizationDTO = aDOrganizationMapper.toDto(aDOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADOrganizationMockMvc.perform(put("/api/ad-organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADOrganization in the database
        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADOrganization() throws Exception {
        // Initialize the database
        aDOrganizationRepository.saveAndFlush(aDOrganization);

        int databaseSizeBeforeDelete = aDOrganizationRepository.findAll().size();

        // Delete the aDOrganization
        restADOrganizationMockMvc.perform(delete("/api/ad-organizations/{id}", aDOrganization.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADOrganization> aDOrganizationList = aDOrganizationRepository.findAll();
        assertThat(aDOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
