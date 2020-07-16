package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTaskSchedulerGroup;
import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdTaskSchedulerGroupRepository;
import com.bhp.opusb.service.AdTaskSchedulerGroupService;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerGroupMapper;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupCriteria;
import com.bhp.opusb.service.AdTaskSchedulerGroupQueryService;

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
 * Integration tests for the {@link AdTaskSchedulerGroupResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTaskSchedulerGroupResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository;

    @Autowired
    private AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper;

    @Autowired
    private AdTaskSchedulerGroupService adTaskSchedulerGroupService;

    @Autowired
    private AdTaskSchedulerGroupQueryService adTaskSchedulerGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTaskSchedulerGroupMockMvc;

    private AdTaskSchedulerGroup adTaskSchedulerGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskSchedulerGroup createEntity(EntityManager em) {
        AdTaskSchedulerGroup adTaskSchedulerGroup = new AdTaskSchedulerGroup()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskSchedulerGroup.setAdOrganization(aDOrganization);
        return adTaskSchedulerGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskSchedulerGroup createUpdatedEntity(EntityManager em) {
        AdTaskSchedulerGroup adTaskSchedulerGroup = new AdTaskSchedulerGroup()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskSchedulerGroup.setAdOrganization(aDOrganization);
        return adTaskSchedulerGroup;
    }

    @BeforeEach
    public void initTest() {
        adTaskSchedulerGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTaskSchedulerGroup() throws Exception {
        int databaseSizeBeforeCreate = adTaskSchedulerGroupRepository.findAll().size();

        // Create the AdTaskSchedulerGroup
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);
        restAdTaskSchedulerGroupMockMvc.perform(post("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTaskSchedulerGroup in the database
        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeCreate + 1);
        AdTaskSchedulerGroup testAdTaskSchedulerGroup = adTaskSchedulerGroupList.get(adTaskSchedulerGroupList.size() - 1);
        assertThat(testAdTaskSchedulerGroup.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTaskSchedulerGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTaskSchedulerGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTaskSchedulerGroup.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdTaskSchedulerGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAdTaskSchedulerGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTaskSchedulerGroupRepository.findAll().size();

        // Create the AdTaskSchedulerGroup with an existing ID
        adTaskSchedulerGroup.setId(1L);
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTaskSchedulerGroupMockMvc.perform(post("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskSchedulerGroup in the database
        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskSchedulerGroupRepository.findAll().size();
        // set the field null
        adTaskSchedulerGroup.setName(null);

        // Create the AdTaskSchedulerGroup, which fails.
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);

        restAdTaskSchedulerGroupMockMvc.perform(post("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskSchedulerGroupRepository.findAll().size();
        // set the field null
        adTaskSchedulerGroup.setValue(null);

        // Create the AdTaskSchedulerGroup, which fails.
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);

        restAdTaskSchedulerGroupMockMvc.perform(post("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroups() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskSchedulerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAdTaskSchedulerGroup() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get the adTaskSchedulerGroup
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups/{id}", adTaskSchedulerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTaskSchedulerGroup.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getAdTaskSchedulerGroupsByIdFiltering() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        Long id = adTaskSchedulerGroup.getId();

        defaultAdTaskSchedulerGroupShouldBeFound("id.equals=" + id);
        defaultAdTaskSchedulerGroupShouldNotBeFound("id.notEquals=" + id);

        defaultAdTaskSchedulerGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTaskSchedulerGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTaskSchedulerGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTaskSchedulerGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where uid equals to DEFAULT_UID
        defaultAdTaskSchedulerGroupShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTaskSchedulerGroupList where uid equals to UPDATED_UID
        defaultAdTaskSchedulerGroupShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where uid not equals to DEFAULT_UID
        defaultAdTaskSchedulerGroupShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTaskSchedulerGroupList where uid not equals to UPDATED_UID
        defaultAdTaskSchedulerGroupShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTaskSchedulerGroupShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTaskSchedulerGroupList where uid equals to UPDATED_UID
        defaultAdTaskSchedulerGroupShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where uid is not null
        defaultAdTaskSchedulerGroupShouldBeFound("uid.specified=true");

        // Get all the adTaskSchedulerGroupList where uid is null
        defaultAdTaskSchedulerGroupShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where active equals to DEFAULT_ACTIVE
        defaultAdTaskSchedulerGroupShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTaskSchedulerGroupList where active equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerGroupShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where active not equals to DEFAULT_ACTIVE
        defaultAdTaskSchedulerGroupShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTaskSchedulerGroupList where active not equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerGroupShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTaskSchedulerGroupShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTaskSchedulerGroupList where active equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerGroupShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where active is not null
        defaultAdTaskSchedulerGroupShouldBeFound("active.specified=true");

        // Get all the adTaskSchedulerGroupList where active is null
        defaultAdTaskSchedulerGroupShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name equals to DEFAULT_NAME
        defaultAdTaskSchedulerGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerGroupList where name equals to UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name not equals to DEFAULT_NAME
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerGroupList where name not equals to UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTaskSchedulerGroupList where name equals to UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name is not null
        defaultAdTaskSchedulerGroupShouldBeFound("name.specified=true");

        // Get all the adTaskSchedulerGroupList where name is null
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name contains DEFAULT_NAME
        defaultAdTaskSchedulerGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerGroupList where name contains UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where name does not contain DEFAULT_NAME
        defaultAdTaskSchedulerGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerGroupList where name does not contain UPDATED_NAME
        defaultAdTaskSchedulerGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value equals to DEFAULT_VALUE
        defaultAdTaskSchedulerGroupShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the adTaskSchedulerGroupList where value equals to UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value not equals to DEFAULT_VALUE
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the adTaskSchedulerGroupList where value not equals to UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the adTaskSchedulerGroupList where value equals to UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value is not null
        defaultAdTaskSchedulerGroupShouldBeFound("value.specified=true");

        // Get all the adTaskSchedulerGroupList where value is null
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value contains DEFAULT_VALUE
        defaultAdTaskSchedulerGroupShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the adTaskSchedulerGroupList where value contains UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where value does not contain DEFAULT_VALUE
        defaultAdTaskSchedulerGroupShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the adTaskSchedulerGroupList where value does not contain UPDATED_VALUE
        defaultAdTaskSchedulerGroupShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description equals to DEFAULT_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adTaskSchedulerGroupList where description equals to UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description not equals to DEFAULT_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adTaskSchedulerGroupList where description not equals to UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adTaskSchedulerGroupList where description equals to UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description is not null
        defaultAdTaskSchedulerGroupShouldBeFound("description.specified=true");

        // Get all the adTaskSchedulerGroupList where description is null
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description contains DEFAULT_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adTaskSchedulerGroupList where description contains UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        // Get all the adTaskSchedulerGroupList where description does not contain DEFAULT_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adTaskSchedulerGroupList where description does not contain UPDATED_DESCRIPTION
        defaultAdTaskSchedulerGroupShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByAdTaskSchedulerIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);
        AdTaskScheduler adTaskScheduler = AdTaskSchedulerResourceIT.createEntity(em);
        em.persist(adTaskScheduler);
        em.flush();
        adTaskSchedulerGroup.addAdTaskScheduler(adTaskScheduler);
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);
        Long adTaskSchedulerId = adTaskScheduler.getId();

        // Get all the adTaskSchedulerGroupList where adTaskScheduler equals to adTaskSchedulerId
        defaultAdTaskSchedulerGroupShouldBeFound("adTaskSchedulerId.equals=" + adTaskSchedulerId);

        // Get all the adTaskSchedulerGroupList where adTaskScheduler equals to adTaskSchedulerId + 1
        defaultAdTaskSchedulerGroupShouldNotBeFound("adTaskSchedulerId.equals=" + (adTaskSchedulerId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulerGroupsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTaskSchedulerGroup.getAdOrganization();
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTaskSchedulerGroupList where adOrganization equals to adOrganizationId
        defaultAdTaskSchedulerGroupShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTaskSchedulerGroupList where adOrganization equals to adOrganizationId + 1
        defaultAdTaskSchedulerGroupShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTaskSchedulerGroupShouldBeFound(String filter) throws Exception {
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskSchedulerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTaskSchedulerGroupShouldNotBeFound(String filter) throws Exception {
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTaskSchedulerGroup() throws Exception {
        // Get the adTaskSchedulerGroup
        restAdTaskSchedulerGroupMockMvc.perform(get("/api/ad-task-scheduler-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTaskSchedulerGroup() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        int databaseSizeBeforeUpdate = adTaskSchedulerGroupRepository.findAll().size();

        // Update the adTaskSchedulerGroup
        AdTaskSchedulerGroup updatedAdTaskSchedulerGroup = adTaskSchedulerGroupRepository.findById(adTaskSchedulerGroup.getId()).get();
        // Disconnect from session so that the updates on updatedAdTaskSchedulerGroup are not directly saved in db
        em.detach(updatedAdTaskSchedulerGroup);
        updatedAdTaskSchedulerGroup
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(updatedAdTaskSchedulerGroup);

        restAdTaskSchedulerGroupMockMvc.perform(put("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isOk());

        // Validate the AdTaskSchedulerGroup in the database
        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeUpdate);
        AdTaskSchedulerGroup testAdTaskSchedulerGroup = adTaskSchedulerGroupList.get(adTaskSchedulerGroupList.size() - 1);
        assertThat(testAdTaskSchedulerGroup.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTaskSchedulerGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTaskSchedulerGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTaskSchedulerGroup.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdTaskSchedulerGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTaskSchedulerGroup() throws Exception {
        int databaseSizeBeforeUpdate = adTaskSchedulerGroupRepository.findAll().size();

        // Create the AdTaskSchedulerGroup
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO = adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTaskSchedulerGroupMockMvc.perform(put("/api/ad-task-scheduler-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskSchedulerGroup in the database
        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTaskSchedulerGroup() throws Exception {
        // Initialize the database
        adTaskSchedulerGroupRepository.saveAndFlush(adTaskSchedulerGroup);

        int databaseSizeBeforeDelete = adTaskSchedulerGroupRepository.findAll().size();

        // Delete the adTaskSchedulerGroup
        restAdTaskSchedulerGroupMockMvc.perform(delete("/api/ad-task-scheduler-groups/{id}", adTaskSchedulerGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTaskSchedulerGroup> adTaskSchedulerGroupList = adTaskSchedulerGroupRepository.findAll();
        assertThat(adTaskSchedulerGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
