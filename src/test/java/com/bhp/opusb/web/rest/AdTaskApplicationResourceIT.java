package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdTaskApplicationRepository;
import com.bhp.opusb.service.AdTaskApplicationService;
import com.bhp.opusb.service.dto.AdTaskApplicationDTO;
import com.bhp.opusb.service.mapper.AdTaskApplicationMapper;
import com.bhp.opusb.service.dto.AdTaskApplicationCriteria;
import com.bhp.opusb.service.AdTaskApplicationQueryService;

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
 * Integration tests for the {@link AdTaskApplicationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTaskApplicationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_METADATA_URI = "AAAAAAAAAA";
    private static final String UPDATED_METADATA_URI = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OVERRIDE_EXISTING = false;
    private static final Boolean UPDATED_OVERRIDE_EXISTING = true;

    private static final Boolean DEFAULT_DEPLOYED = false;
    private static final Boolean UPDATED_DEPLOYED = true;

    @Autowired
    private AdTaskApplicationRepository adTaskApplicationRepository;

    @Autowired
    private AdTaskApplicationMapper adTaskApplicationMapper;

    @Autowired
    private AdTaskApplicationService adTaskApplicationService;

    @Autowired
    private AdTaskApplicationQueryService adTaskApplicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTaskApplicationMockMvc;

    private AdTaskApplication adTaskApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskApplication createEntity(EntityManager em) {
        AdTaskApplication adTaskApplication = new AdTaskApplication()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .uri(DEFAULT_URI)
            .metadataUri(DEFAULT_METADATA_URI)
            .version(DEFAULT_VERSION)
            .overrideExisting(DEFAULT_OVERRIDE_EXISTING)
            .deployed(DEFAULT_DEPLOYED);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskApplication.setAdOrganization(aDOrganization);
        return adTaskApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskApplication createUpdatedEntity(EntityManager em) {
        AdTaskApplication adTaskApplication = new AdTaskApplication()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .uri(UPDATED_URI)
            .metadataUri(UPDATED_METADATA_URI)
            .version(UPDATED_VERSION)
            .overrideExisting(UPDATED_OVERRIDE_EXISTING)
            .deployed(UPDATED_DEPLOYED);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskApplication.setAdOrganization(aDOrganization);
        return adTaskApplication;
    }

    @BeforeEach
    public void initTest() {
        adTaskApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTaskApplication() throws Exception {
        int databaseSizeBeforeCreate = adTaskApplicationRepository.findAll().size();

        // Create the AdTaskApplication
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);
        restAdTaskApplicationMockMvc.perform(post("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTaskApplication in the database
        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        AdTaskApplication testAdTaskApplication = adTaskApplicationList.get(adTaskApplicationList.size() - 1);
        assertThat(testAdTaskApplication.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTaskApplication.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTaskApplication.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTaskApplication.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdTaskApplication.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testAdTaskApplication.getMetadataUri()).isEqualTo(DEFAULT_METADATA_URI);
        assertThat(testAdTaskApplication.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAdTaskApplication.isOverrideExisting()).isEqualTo(DEFAULT_OVERRIDE_EXISTING);
        assertThat(testAdTaskApplication.isDeployed()).isEqualTo(DEFAULT_DEPLOYED);
    }

    @Test
    @Transactional
    public void createAdTaskApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTaskApplicationRepository.findAll().size();

        // Create the AdTaskApplication with an existing ID
        adTaskApplication.setId(1L);
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTaskApplicationMockMvc.perform(post("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskApplication in the database
        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskApplicationRepository.findAll().size();
        // set the field null
        adTaskApplication.setName(null);

        // Create the AdTaskApplication, which fails.
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);

        restAdTaskApplicationMockMvc.perform(post("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskApplicationRepository.findAll().size();
        // set the field null
        adTaskApplication.setValue(null);

        // Create the AdTaskApplication, which fails.
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);

        restAdTaskApplicationMockMvc.perform(post("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskApplicationRepository.findAll().size();
        // set the field null
        adTaskApplication.setUri(null);

        // Create the AdTaskApplication, which fails.
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);

        restAdTaskApplicationMockMvc.perform(post("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplications() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].metadataUri").value(hasItem(DEFAULT_METADATA_URI)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].overrideExisting").value(hasItem(DEFAULT_OVERRIDE_EXISTING.booleanValue())))
            .andExpect(jsonPath("$.[*].deployed").value(hasItem(DEFAULT_DEPLOYED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdTaskApplication() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get the adTaskApplication
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications/{id}", adTaskApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTaskApplication.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.metadataUri").value(DEFAULT_METADATA_URI))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.overrideExisting").value(DEFAULT_OVERRIDE_EXISTING.booleanValue()))
            .andExpect(jsonPath("$.deployed").value(DEFAULT_DEPLOYED.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdTaskApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        Long id = adTaskApplication.getId();

        defaultAdTaskApplicationShouldBeFound("id.equals=" + id);
        defaultAdTaskApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultAdTaskApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTaskApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTaskApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTaskApplicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uid equals to DEFAULT_UID
        defaultAdTaskApplicationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTaskApplicationList where uid equals to UPDATED_UID
        defaultAdTaskApplicationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uid not equals to DEFAULT_UID
        defaultAdTaskApplicationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTaskApplicationList where uid not equals to UPDATED_UID
        defaultAdTaskApplicationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTaskApplicationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTaskApplicationList where uid equals to UPDATED_UID
        defaultAdTaskApplicationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uid is not null
        defaultAdTaskApplicationShouldBeFound("uid.specified=true");

        // Get all the adTaskApplicationList where uid is null
        defaultAdTaskApplicationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where active equals to DEFAULT_ACTIVE
        defaultAdTaskApplicationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTaskApplicationList where active equals to UPDATED_ACTIVE
        defaultAdTaskApplicationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where active not equals to DEFAULT_ACTIVE
        defaultAdTaskApplicationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTaskApplicationList where active not equals to UPDATED_ACTIVE
        defaultAdTaskApplicationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTaskApplicationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTaskApplicationList where active equals to UPDATED_ACTIVE
        defaultAdTaskApplicationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where active is not null
        defaultAdTaskApplicationShouldBeFound("active.specified=true");

        // Get all the adTaskApplicationList where active is null
        defaultAdTaskApplicationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name equals to DEFAULT_NAME
        defaultAdTaskApplicationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTaskApplicationList where name equals to UPDATED_NAME
        defaultAdTaskApplicationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name not equals to DEFAULT_NAME
        defaultAdTaskApplicationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTaskApplicationList where name not equals to UPDATED_NAME
        defaultAdTaskApplicationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTaskApplicationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTaskApplicationList where name equals to UPDATED_NAME
        defaultAdTaskApplicationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name is not null
        defaultAdTaskApplicationShouldBeFound("name.specified=true");

        // Get all the adTaskApplicationList where name is null
        defaultAdTaskApplicationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name contains DEFAULT_NAME
        defaultAdTaskApplicationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTaskApplicationList where name contains UPDATED_NAME
        defaultAdTaskApplicationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where name does not contain DEFAULT_NAME
        defaultAdTaskApplicationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTaskApplicationList where name does not contain UPDATED_NAME
        defaultAdTaskApplicationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value equals to DEFAULT_VALUE
        defaultAdTaskApplicationShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the adTaskApplicationList where value equals to UPDATED_VALUE
        defaultAdTaskApplicationShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value not equals to DEFAULT_VALUE
        defaultAdTaskApplicationShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the adTaskApplicationList where value not equals to UPDATED_VALUE
        defaultAdTaskApplicationShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAdTaskApplicationShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the adTaskApplicationList where value equals to UPDATED_VALUE
        defaultAdTaskApplicationShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value is not null
        defaultAdTaskApplicationShouldBeFound("value.specified=true");

        // Get all the adTaskApplicationList where value is null
        defaultAdTaskApplicationShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value contains DEFAULT_VALUE
        defaultAdTaskApplicationShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the adTaskApplicationList where value contains UPDATED_VALUE
        defaultAdTaskApplicationShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where value does not contain DEFAULT_VALUE
        defaultAdTaskApplicationShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the adTaskApplicationList where value does not contain UPDATED_VALUE
        defaultAdTaskApplicationShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri equals to DEFAULT_URI
        defaultAdTaskApplicationShouldBeFound("uri.equals=" + DEFAULT_URI);

        // Get all the adTaskApplicationList where uri equals to UPDATED_URI
        defaultAdTaskApplicationShouldNotBeFound("uri.equals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri not equals to DEFAULT_URI
        defaultAdTaskApplicationShouldNotBeFound("uri.notEquals=" + DEFAULT_URI);

        // Get all the adTaskApplicationList where uri not equals to UPDATED_URI
        defaultAdTaskApplicationShouldBeFound("uri.notEquals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri in DEFAULT_URI or UPDATED_URI
        defaultAdTaskApplicationShouldBeFound("uri.in=" + DEFAULT_URI + "," + UPDATED_URI);

        // Get all the adTaskApplicationList where uri equals to UPDATED_URI
        defaultAdTaskApplicationShouldNotBeFound("uri.in=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri is not null
        defaultAdTaskApplicationShouldBeFound("uri.specified=true");

        // Get all the adTaskApplicationList where uri is null
        defaultAdTaskApplicationShouldNotBeFound("uri.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri contains DEFAULT_URI
        defaultAdTaskApplicationShouldBeFound("uri.contains=" + DEFAULT_URI);

        // Get all the adTaskApplicationList where uri contains UPDATED_URI
        defaultAdTaskApplicationShouldNotBeFound("uri.contains=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByUriNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where uri does not contain DEFAULT_URI
        defaultAdTaskApplicationShouldNotBeFound("uri.doesNotContain=" + DEFAULT_URI);

        // Get all the adTaskApplicationList where uri does not contain UPDATED_URI
        defaultAdTaskApplicationShouldBeFound("uri.doesNotContain=" + UPDATED_URI);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri equals to DEFAULT_METADATA_URI
        defaultAdTaskApplicationShouldBeFound("metadataUri.equals=" + DEFAULT_METADATA_URI);

        // Get all the adTaskApplicationList where metadataUri equals to UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.equals=" + UPDATED_METADATA_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri not equals to DEFAULT_METADATA_URI
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.notEquals=" + DEFAULT_METADATA_URI);

        // Get all the adTaskApplicationList where metadataUri not equals to UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldBeFound("metadataUri.notEquals=" + UPDATED_METADATA_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri in DEFAULT_METADATA_URI or UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldBeFound("metadataUri.in=" + DEFAULT_METADATA_URI + "," + UPDATED_METADATA_URI);

        // Get all the adTaskApplicationList where metadataUri equals to UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.in=" + UPDATED_METADATA_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri is not null
        defaultAdTaskApplicationShouldBeFound("metadataUri.specified=true");

        // Get all the adTaskApplicationList where metadataUri is null
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri contains DEFAULT_METADATA_URI
        defaultAdTaskApplicationShouldBeFound("metadataUri.contains=" + DEFAULT_METADATA_URI);

        // Get all the adTaskApplicationList where metadataUri contains UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.contains=" + UPDATED_METADATA_URI);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByMetadataUriNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where metadataUri does not contain DEFAULT_METADATA_URI
        defaultAdTaskApplicationShouldNotBeFound("metadataUri.doesNotContain=" + DEFAULT_METADATA_URI);

        // Get all the adTaskApplicationList where metadataUri does not contain UPDATED_METADATA_URI
        defaultAdTaskApplicationShouldBeFound("metadataUri.doesNotContain=" + UPDATED_METADATA_URI);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version equals to DEFAULT_VERSION
        defaultAdTaskApplicationShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the adTaskApplicationList where version equals to UPDATED_VERSION
        defaultAdTaskApplicationShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version not equals to DEFAULT_VERSION
        defaultAdTaskApplicationShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the adTaskApplicationList where version not equals to UPDATED_VERSION
        defaultAdTaskApplicationShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAdTaskApplicationShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the adTaskApplicationList where version equals to UPDATED_VERSION
        defaultAdTaskApplicationShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version is not null
        defaultAdTaskApplicationShouldBeFound("version.specified=true");

        // Get all the adTaskApplicationList where version is null
        defaultAdTaskApplicationShouldNotBeFound("version.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version contains DEFAULT_VERSION
        defaultAdTaskApplicationShouldBeFound("version.contains=" + DEFAULT_VERSION);

        // Get all the adTaskApplicationList where version contains UPDATED_VERSION
        defaultAdTaskApplicationShouldNotBeFound("version.contains=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByVersionNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where version does not contain DEFAULT_VERSION
        defaultAdTaskApplicationShouldNotBeFound("version.doesNotContain=" + DEFAULT_VERSION);

        // Get all the adTaskApplicationList where version does not contain UPDATED_VERSION
        defaultAdTaskApplicationShouldBeFound("version.doesNotContain=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAdTaskApplicationsByOverrideExistingIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where overrideExisting equals to DEFAULT_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldBeFound("overrideExisting.equals=" + DEFAULT_OVERRIDE_EXISTING);

        // Get all the adTaskApplicationList where overrideExisting equals to UPDATED_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldNotBeFound("overrideExisting.equals=" + UPDATED_OVERRIDE_EXISTING);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByOverrideExistingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where overrideExisting not equals to DEFAULT_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldNotBeFound("overrideExisting.notEquals=" + DEFAULT_OVERRIDE_EXISTING);

        // Get all the adTaskApplicationList where overrideExisting not equals to UPDATED_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldBeFound("overrideExisting.notEquals=" + UPDATED_OVERRIDE_EXISTING);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByOverrideExistingIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where overrideExisting in DEFAULT_OVERRIDE_EXISTING or UPDATED_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldBeFound("overrideExisting.in=" + DEFAULT_OVERRIDE_EXISTING + "," + UPDATED_OVERRIDE_EXISTING);

        // Get all the adTaskApplicationList where overrideExisting equals to UPDATED_OVERRIDE_EXISTING
        defaultAdTaskApplicationShouldNotBeFound("overrideExisting.in=" + UPDATED_OVERRIDE_EXISTING);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByOverrideExistingIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where overrideExisting is not null
        defaultAdTaskApplicationShouldBeFound("overrideExisting.specified=true");

        // Get all the adTaskApplicationList where overrideExisting is null
        defaultAdTaskApplicationShouldNotBeFound("overrideExisting.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByDeployedIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where deployed equals to DEFAULT_DEPLOYED
        defaultAdTaskApplicationShouldBeFound("deployed.equals=" + DEFAULT_DEPLOYED);

        // Get all the adTaskApplicationList where deployed equals to UPDATED_DEPLOYED
        defaultAdTaskApplicationShouldNotBeFound("deployed.equals=" + UPDATED_DEPLOYED);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByDeployedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where deployed not equals to DEFAULT_DEPLOYED
        defaultAdTaskApplicationShouldNotBeFound("deployed.notEquals=" + DEFAULT_DEPLOYED);

        // Get all the adTaskApplicationList where deployed not equals to UPDATED_DEPLOYED
        defaultAdTaskApplicationShouldBeFound("deployed.notEquals=" + UPDATED_DEPLOYED);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByDeployedIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where deployed in DEFAULT_DEPLOYED or UPDATED_DEPLOYED
        defaultAdTaskApplicationShouldBeFound("deployed.in=" + DEFAULT_DEPLOYED + "," + UPDATED_DEPLOYED);

        // Get all the adTaskApplicationList where deployed equals to UPDATED_DEPLOYED
        defaultAdTaskApplicationShouldNotBeFound("deployed.in=" + UPDATED_DEPLOYED);
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByDeployedIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        // Get all the adTaskApplicationList where deployed is not null
        defaultAdTaskApplicationShouldBeFound("deployed.specified=true");

        // Get all the adTaskApplicationList where deployed is null
        defaultAdTaskApplicationShouldNotBeFound("deployed.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskApplicationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTaskApplication.getAdOrganization();
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTaskApplicationList where adOrganization equals to adOrganizationId
        defaultAdTaskApplicationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTaskApplicationList where adOrganization equals to adOrganizationId + 1
        defaultAdTaskApplicationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTaskApplicationShouldBeFound(String filter) throws Exception {
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].metadataUri").value(hasItem(DEFAULT_METADATA_URI)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].overrideExisting").value(hasItem(DEFAULT_OVERRIDE_EXISTING.booleanValue())))
            .andExpect(jsonPath("$.[*].deployed").value(hasItem(DEFAULT_DEPLOYED.booleanValue())));

        // Check, that the count call also returns 1
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTaskApplicationShouldNotBeFound(String filter) throws Exception {
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTaskApplication() throws Exception {
        // Get the adTaskApplication
        restAdTaskApplicationMockMvc.perform(get("/api/ad-task-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTaskApplication() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        int databaseSizeBeforeUpdate = adTaskApplicationRepository.findAll().size();

        // Update the adTaskApplication
        AdTaskApplication updatedAdTaskApplication = adTaskApplicationRepository.findById(adTaskApplication.getId()).get();
        // Disconnect from session so that the updates on updatedAdTaskApplication are not directly saved in db
        em.detach(updatedAdTaskApplication);
        updatedAdTaskApplication
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .uri(UPDATED_URI)
            .metadataUri(UPDATED_METADATA_URI)
            .version(UPDATED_VERSION)
            .overrideExisting(UPDATED_OVERRIDE_EXISTING)
            .deployed(UPDATED_DEPLOYED);
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(updatedAdTaskApplication);

        restAdTaskApplicationMockMvc.perform(put("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isOk());

        // Validate the AdTaskApplication in the database
        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeUpdate);
        AdTaskApplication testAdTaskApplication = adTaskApplicationList.get(adTaskApplicationList.size() - 1);
        assertThat(testAdTaskApplication.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTaskApplication.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTaskApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTaskApplication.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdTaskApplication.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testAdTaskApplication.getMetadataUri()).isEqualTo(UPDATED_METADATA_URI);
        assertThat(testAdTaskApplication.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAdTaskApplication.isOverrideExisting()).isEqualTo(UPDATED_OVERRIDE_EXISTING);
        assertThat(testAdTaskApplication.isDeployed()).isEqualTo(UPDATED_DEPLOYED);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTaskApplication() throws Exception {
        int databaseSizeBeforeUpdate = adTaskApplicationRepository.findAll().size();

        // Create the AdTaskApplication
        AdTaskApplicationDTO adTaskApplicationDTO = adTaskApplicationMapper.toDto(adTaskApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTaskApplicationMockMvc.perform(put("/api/ad-task-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskApplication in the database
        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTaskApplication() throws Exception {
        // Initialize the database
        adTaskApplicationRepository.saveAndFlush(adTaskApplication);

        int databaseSizeBeforeDelete = adTaskApplicationRepository.findAll().size();

        // Delete the adTaskApplication
        restAdTaskApplicationMockMvc.perform(delete("/api/ad-task-applications/{id}", adTaskApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTaskApplication> adTaskApplicationList = adTaskApplicationRepository.findAll();
        assertThat(adTaskApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
