package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProductGroup;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CProductGroupRepository;
import com.bhp.opusb.service.CProductGroupService;
import com.bhp.opusb.service.dto.CProductGroupDTO;
import com.bhp.opusb.service.mapper.CProductGroupMapper;
import com.bhp.opusb.service.dto.CProductGroupCriteria;
import com.bhp.opusb.service.CProductGroupQueryService;

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
 * Integration tests for the {@link CProductGroupResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductGroupRepository cProductGroupRepository;

    @Autowired
    private CProductGroupMapper cProductGroupMapper;

    @Autowired
    private CProductGroupService cProductGroupService;

    @Autowired
    private CProductGroupQueryService cProductGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductGroupMockMvc;

    private CProductGroup cProductGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductGroup createEntity(EntityManager em) {
        CProductGroup cProductGroup = new CProductGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
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
        cProductGroup.setAdOrganization(aDOrganization);
        return cProductGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductGroup createUpdatedEntity(EntityManager em) {
        CProductGroup cProductGroup = new CProductGroup()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
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
        cProductGroup.setAdOrganization(aDOrganization);
        return cProductGroup;
    }

    @BeforeEach
    public void initTest() {
        cProductGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProductGroup() throws Exception {
        int databaseSizeBeforeCreate = cProductGroupRepository.findAll().size();

        // Create the CProductGroup
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(cProductGroup);
        restCProductGroupMockMvc.perform(post("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CProductGroup in the database
        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CProductGroup testCProductGroup = cProductGroupList.get(cProductGroupList.size() - 1);
        assertThat(testCProductGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCProductGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCProductGroup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCProductGroup.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProductGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductGroupRepository.findAll().size();

        // Create the CProductGroup with an existing ID
        cProductGroup.setId(1L);
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(cProductGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductGroupMockMvc.perform(post("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductGroup in the database
        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductGroupRepository.findAll().size();
        // set the field null
        cProductGroup.setCode(null);

        // Create the CProductGroup, which fails.
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(cProductGroup);

        restCProductGroupMockMvc.perform(post("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductGroupRepository.findAll().size();
        // set the field null
        cProductGroup.setName(null);

        // Create the CProductGroup, which fails.
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(cProductGroup);

        restCProductGroupMockMvc.perform(post("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCProductGroups() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList
        restCProductGroupMockMvc.perform(get("/api/c-product-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProductGroup() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get the cProductGroup
        restCProductGroupMockMvc.perform(get("/api/c-product-groups/{id}", cProductGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProductGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductGroupsByIdFiltering() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        Long id = cProductGroup.getId();

        defaultCProductGroupShouldBeFound("id.equals=" + id);
        defaultCProductGroupShouldNotBeFound("id.notEquals=" + id);

        defaultCProductGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductGroupsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code equals to DEFAULT_CODE
        defaultCProductGroupShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cProductGroupList where code equals to UPDATED_CODE
        defaultCProductGroupShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code not equals to DEFAULT_CODE
        defaultCProductGroupShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cProductGroupList where code not equals to UPDATED_CODE
        defaultCProductGroupShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCProductGroupShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cProductGroupList where code equals to UPDATED_CODE
        defaultCProductGroupShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code is not null
        defaultCProductGroupShouldBeFound("code.specified=true");

        // Get all the cProductGroupList where code is null
        defaultCProductGroupShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductGroupsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code contains DEFAULT_CODE
        defaultCProductGroupShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cProductGroupList where code contains UPDATED_CODE
        defaultCProductGroupShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where code does not contain DEFAULT_CODE
        defaultCProductGroupShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cProductGroupList where code does not contain UPDATED_CODE
        defaultCProductGroupShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCProductGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name equals to DEFAULT_NAME
        defaultCProductGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cProductGroupList where name equals to UPDATED_NAME
        defaultCProductGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name not equals to DEFAULT_NAME
        defaultCProductGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cProductGroupList where name not equals to UPDATED_NAME
        defaultCProductGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCProductGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cProductGroupList where name equals to UPDATED_NAME
        defaultCProductGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name is not null
        defaultCProductGroupShouldBeFound("name.specified=true");

        // Get all the cProductGroupList where name is null
        defaultCProductGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name contains DEFAULT_NAME
        defaultCProductGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cProductGroupList where name contains UPDATED_NAME
        defaultCProductGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where name does not contain DEFAULT_NAME
        defaultCProductGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cProductGroupList where name does not contain UPDATED_NAME
        defaultCProductGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCProductGroupsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type equals to DEFAULT_TYPE
        defaultCProductGroupShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cProductGroupList where type equals to UPDATED_TYPE
        defaultCProductGroupShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type not equals to DEFAULT_TYPE
        defaultCProductGroupShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cProductGroupList where type not equals to UPDATED_TYPE
        defaultCProductGroupShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCProductGroupShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cProductGroupList where type equals to UPDATED_TYPE
        defaultCProductGroupShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type is not null
        defaultCProductGroupShouldBeFound("type.specified=true");

        // Get all the cProductGroupList where type is null
        defaultCProductGroupShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductGroupsByTypeContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type contains DEFAULT_TYPE
        defaultCProductGroupShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cProductGroupList where type contains UPDATED_TYPE
        defaultCProductGroupShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where type does not contain DEFAULT_TYPE
        defaultCProductGroupShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cProductGroupList where type does not contain UPDATED_TYPE
        defaultCProductGroupShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCProductGroupsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where uid equals to DEFAULT_UID
        defaultCProductGroupShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductGroupList where uid equals to UPDATED_UID
        defaultCProductGroupShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where uid not equals to DEFAULT_UID
        defaultCProductGroupShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductGroupList where uid not equals to UPDATED_UID
        defaultCProductGroupShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductGroupShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductGroupList where uid equals to UPDATED_UID
        defaultCProductGroupShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where uid is not null
        defaultCProductGroupShouldBeFound("uid.specified=true");

        // Get all the cProductGroupList where uid is null
        defaultCProductGroupShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where active equals to DEFAULT_ACTIVE
        defaultCProductGroupShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductGroupList where active equals to UPDATED_ACTIVE
        defaultCProductGroupShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where active not equals to DEFAULT_ACTIVE
        defaultCProductGroupShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductGroupList where active not equals to UPDATED_ACTIVE
        defaultCProductGroupShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductGroupShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductGroupList where active equals to UPDATED_ACTIVE
        defaultCProductGroupShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        // Get all the cProductGroupList where active is not null
        defaultCProductGroupShouldBeFound("active.specified=true");

        // Get all the cProductGroupList where active is null
        defaultCProductGroupShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductGroupsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProductGroup.getAdOrganization();
        cProductGroupRepository.saveAndFlush(cProductGroup);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductGroupList where adOrganization equals to adOrganizationId
        defaultCProductGroupShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductGroupList where adOrganization equals to adOrganizationId + 1
        defaultCProductGroupShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductGroupShouldBeFound(String filter) throws Exception {
        restCProductGroupMockMvc.perform(get("/api/c-product-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductGroupMockMvc.perform(get("/api/c-product-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductGroupShouldNotBeFound(String filter) throws Exception {
        restCProductGroupMockMvc.perform(get("/api/c-product-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductGroupMockMvc.perform(get("/api/c-product-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProductGroup() throws Exception {
        // Get the cProductGroup
        restCProductGroupMockMvc.perform(get("/api/c-product-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProductGroup() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        int databaseSizeBeforeUpdate = cProductGroupRepository.findAll().size();

        // Update the cProductGroup
        CProductGroup updatedCProductGroup = cProductGroupRepository.findById(cProductGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCProductGroup are not directly saved in db
        em.detach(updatedCProductGroup);
        updatedCProductGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(updatedCProductGroup);

        restCProductGroupMockMvc.perform(put("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CProductGroup in the database
        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeUpdate);
        CProductGroup testCProductGroup = cProductGroupList.get(cProductGroupList.size() - 1);
        assertThat(testCProductGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCProductGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCProductGroup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCProductGroup.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProductGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProductGroup() throws Exception {
        int databaseSizeBeforeUpdate = cProductGroupRepository.findAll().size();

        // Create the CProductGroup
        CProductGroupDTO cProductGroupDTO = cProductGroupMapper.toDto(cProductGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductGroupMockMvc.perform(put("/api/c-product-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductGroup in the database
        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProductGroup() throws Exception {
        // Initialize the database
        cProductGroupRepository.saveAndFlush(cProductGroup);

        int databaseSizeBeforeDelete = cProductGroupRepository.findAll().size();

        // Delete the cProductGroup
        restCProductGroupMockMvc.perform(delete("/api/c-product-groups/{id}", cProductGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProductGroup> cProductGroupList = cProductGroupRepository.findAll();
        assertThat(cProductGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
