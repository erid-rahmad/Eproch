package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorGroup;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorGroupRepository;
import com.bhp.opusb.service.CVendorGroupService;
import com.bhp.opusb.service.dto.CVendorGroupDTO;
import com.bhp.opusb.service.mapper.CVendorGroupMapper;
import com.bhp.opusb.service.dto.CVendorGroupCriteria;
import com.bhp.opusb.service.CVendorGroupQueryService;

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
 * Integration tests for the {@link CVendorGroupResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorGroupRepository cVendorGroupRepository;

    @Autowired
    private CVendorGroupMapper cVendorGroupMapper;

    @Autowired
    private CVendorGroupService cVendorGroupService;

    @Autowired
    private CVendorGroupQueryService cVendorGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorGroupMockMvc;

    private CVendorGroup cVendorGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorGroup createEntity(EntityManager em) {
        CVendorGroup cVendorGroup = new CVendorGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isDefault(DEFAULT_IS_DEFAULT)
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
        cVendorGroup.setAdOrganization(aDOrganization);
        return cVendorGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorGroup createUpdatedEntity(EntityManager em) {
        CVendorGroup cVendorGroup = new CVendorGroup()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isDefault(UPDATED_IS_DEFAULT)
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
        cVendorGroup.setAdOrganization(aDOrganization);
        return cVendorGroup;
    }

    @BeforeEach
    public void initTest() {
        cVendorGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorGroup() throws Exception {
        int databaseSizeBeforeCreate = cVendorGroupRepository.findAll().size();

        // Create the CVendorGroup
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(cVendorGroup);
        restCVendorGroupMockMvc.perform(post("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorGroup in the database
        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorGroup testCVendorGroup = cVendorGroupList.get(cVendorGroupList.size() - 1);
        assertThat(testCVendorGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCVendorGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCVendorGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCVendorGroup.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testCVendorGroup.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorGroupRepository.findAll().size();

        // Create the CVendorGroup with an existing ID
        cVendorGroup.setId(1L);
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(cVendorGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorGroupMockMvc.perform(post("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorGroup in the database
        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorGroupRepository.findAll().size();
        // set the field null
        cVendorGroup.setCode(null);

        // Create the CVendorGroup, which fails.
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(cVendorGroup);

        restCVendorGroupMockMvc.perform(post("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorGroupRepository.findAll().size();
        // set the field null
        cVendorGroup.setName(null);

        // Create the CVendorGroup, which fails.
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(cVendorGroup);

        restCVendorGroupMockMvc.perform(post("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCVendorGroups() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorGroup() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get the cVendorGroup
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups/{id}", cVendorGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorGroupsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        Long id = cVendorGroup.getId();

        defaultCVendorGroupShouldBeFound("id.equals=" + id);
        defaultCVendorGroupShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorGroupsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code equals to DEFAULT_CODE
        defaultCVendorGroupShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cVendorGroupList where code equals to UPDATED_CODE
        defaultCVendorGroupShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code not equals to DEFAULT_CODE
        defaultCVendorGroupShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cVendorGroupList where code not equals to UPDATED_CODE
        defaultCVendorGroupShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCVendorGroupShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cVendorGroupList where code equals to UPDATED_CODE
        defaultCVendorGroupShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code is not null
        defaultCVendorGroupShouldBeFound("code.specified=true");

        // Get all the cVendorGroupList where code is null
        defaultCVendorGroupShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorGroupsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code contains DEFAULT_CODE
        defaultCVendorGroupShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cVendorGroupList where code contains UPDATED_CODE
        defaultCVendorGroupShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where code does not contain DEFAULT_CODE
        defaultCVendorGroupShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cVendorGroupList where code does not contain UPDATED_CODE
        defaultCVendorGroupShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCVendorGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name equals to DEFAULT_NAME
        defaultCVendorGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cVendorGroupList where name equals to UPDATED_NAME
        defaultCVendorGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name not equals to DEFAULT_NAME
        defaultCVendorGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cVendorGroupList where name not equals to UPDATED_NAME
        defaultCVendorGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCVendorGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cVendorGroupList where name equals to UPDATED_NAME
        defaultCVendorGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name is not null
        defaultCVendorGroupShouldBeFound("name.specified=true");

        // Get all the cVendorGroupList where name is null
        defaultCVendorGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name contains DEFAULT_NAME
        defaultCVendorGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cVendorGroupList where name contains UPDATED_NAME
        defaultCVendorGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where name does not contain DEFAULT_NAME
        defaultCVendorGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cVendorGroupList where name does not contain UPDATED_NAME
        defaultCVendorGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description equals to DEFAULT_DESCRIPTION
        defaultCVendorGroupShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cVendorGroupList where description equals to UPDATED_DESCRIPTION
        defaultCVendorGroupShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description not equals to DEFAULT_DESCRIPTION
        defaultCVendorGroupShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cVendorGroupList where description not equals to UPDATED_DESCRIPTION
        defaultCVendorGroupShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCVendorGroupShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cVendorGroupList where description equals to UPDATED_DESCRIPTION
        defaultCVendorGroupShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description is not null
        defaultCVendorGroupShouldBeFound("description.specified=true");

        // Get all the cVendorGroupList where description is null
        defaultCVendorGroupShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description contains DEFAULT_DESCRIPTION
        defaultCVendorGroupShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cVendorGroupList where description contains UPDATED_DESCRIPTION
        defaultCVendorGroupShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where description does not contain DEFAULT_DESCRIPTION
        defaultCVendorGroupShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cVendorGroupList where description does not contain UPDATED_DESCRIPTION
        defaultCVendorGroupShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCVendorGroupsByIsDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where isDefault equals to DEFAULT_IS_DEFAULT
        defaultCVendorGroupShouldBeFound("isDefault.equals=" + DEFAULT_IS_DEFAULT);

        // Get all the cVendorGroupList where isDefault equals to UPDATED_IS_DEFAULT
        defaultCVendorGroupShouldNotBeFound("isDefault.equals=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByIsDefaultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where isDefault not equals to DEFAULT_IS_DEFAULT
        defaultCVendorGroupShouldNotBeFound("isDefault.notEquals=" + DEFAULT_IS_DEFAULT);

        // Get all the cVendorGroupList where isDefault not equals to UPDATED_IS_DEFAULT
        defaultCVendorGroupShouldBeFound("isDefault.notEquals=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByIsDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where isDefault in DEFAULT_IS_DEFAULT or UPDATED_IS_DEFAULT
        defaultCVendorGroupShouldBeFound("isDefault.in=" + DEFAULT_IS_DEFAULT + "," + UPDATED_IS_DEFAULT);

        // Get all the cVendorGroupList where isDefault equals to UPDATED_IS_DEFAULT
        defaultCVendorGroupShouldNotBeFound("isDefault.in=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByIsDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where isDefault is not null
        defaultCVendorGroupShouldBeFound("isDefault.specified=true");

        // Get all the cVendorGroupList where isDefault is null
        defaultCVendorGroupShouldNotBeFound("isDefault.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where uid equals to DEFAULT_UID
        defaultCVendorGroupShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorGroupList where uid equals to UPDATED_UID
        defaultCVendorGroupShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where uid not equals to DEFAULT_UID
        defaultCVendorGroupShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorGroupList where uid not equals to UPDATED_UID
        defaultCVendorGroupShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorGroupShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorGroupList where uid equals to UPDATED_UID
        defaultCVendorGroupShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where uid is not null
        defaultCVendorGroupShouldBeFound("uid.specified=true");

        // Get all the cVendorGroupList where uid is null
        defaultCVendorGroupShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where active equals to DEFAULT_ACTIVE
        defaultCVendorGroupShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorGroupList where active equals to UPDATED_ACTIVE
        defaultCVendorGroupShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where active not equals to DEFAULT_ACTIVE
        defaultCVendorGroupShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorGroupList where active not equals to UPDATED_ACTIVE
        defaultCVendorGroupShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorGroupShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorGroupList where active equals to UPDATED_ACTIVE
        defaultCVendorGroupShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        // Get all the cVendorGroupList where active is not null
        defaultCVendorGroupShouldBeFound("active.specified=true");

        // Get all the cVendorGroupList where active is null
        defaultCVendorGroupShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorGroupsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorGroup.getAdOrganization();
        cVendorGroupRepository.saveAndFlush(cVendorGroup);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorGroupList where adOrganization equals to adOrganizationId
        defaultCVendorGroupShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorGroupList where adOrganization equals to adOrganizationId + 1
        defaultCVendorGroupShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorGroupShouldBeFound(String filter) throws Exception {
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorGroupShouldNotBeFound(String filter) throws Exception {
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorGroup() throws Exception {
        // Get the cVendorGroup
        restCVendorGroupMockMvc.perform(get("/api/c-vendor-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorGroup() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        int databaseSizeBeforeUpdate = cVendorGroupRepository.findAll().size();

        // Update the cVendorGroup
        CVendorGroup updatedCVendorGroup = cVendorGroupRepository.findById(cVendorGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorGroup are not directly saved in db
        em.detach(updatedCVendorGroup);
        updatedCVendorGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isDefault(UPDATED_IS_DEFAULT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(updatedCVendorGroup);

        restCVendorGroupMockMvc.perform(put("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorGroup in the database
        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeUpdate);
        CVendorGroup testCVendorGroup = cVendorGroupList.get(cVendorGroupList.size() - 1);
        assertThat(testCVendorGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCVendorGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCVendorGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCVendorGroup.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testCVendorGroup.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorGroup() throws Exception {
        int databaseSizeBeforeUpdate = cVendorGroupRepository.findAll().size();

        // Create the CVendorGroup
        CVendorGroupDTO cVendorGroupDTO = cVendorGroupMapper.toDto(cVendorGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorGroupMockMvc.perform(put("/api/c-vendor-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorGroup in the database
        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorGroup() throws Exception {
        // Initialize the database
        cVendorGroupRepository.saveAndFlush(cVendorGroup);

        int databaseSizeBeforeDelete = cVendorGroupRepository.findAll().size();

        // Delete the cVendorGroup
        restCVendorGroupMockMvc.perform(delete("/api/c-vendor-groups/{id}", cVendorGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorGroup> cVendorGroupList = cVendorGroupRepository.findAll();
        assertThat(cVendorGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
