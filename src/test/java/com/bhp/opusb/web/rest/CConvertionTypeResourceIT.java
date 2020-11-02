package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CConvertionType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CConvertionTypeRepository;
import com.bhp.opusb.service.CConvertionTypeService;
import com.bhp.opusb.service.dto.CConvertionTypeDTO;
import com.bhp.opusb.service.mapper.CConvertionTypeMapper;
import com.bhp.opusb.service.dto.CConvertionTypeCriteria;
import com.bhp.opusb.service.CConvertionTypeQueryService;

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
 * Integration tests for the {@link CConvertionTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CConvertionTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CConvertionTypeRepository cConvertionTypeRepository;

    @Autowired
    private CConvertionTypeMapper cConvertionTypeMapper;

    @Autowired
    private CConvertionTypeService cConvertionTypeService;

    @Autowired
    private CConvertionTypeQueryService cConvertionTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCConvertionTypeMockMvc;

    private CConvertionType cConvertionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CConvertionType createEntity(EntityManager em) {
        CConvertionType cConvertionType = new CConvertionType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
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
        cConvertionType.setAdOrganization(aDOrganization);
        return cConvertionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CConvertionType createUpdatedEntity(EntityManager em) {
        CConvertionType cConvertionType = new CConvertionType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
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
        cConvertionType.setAdOrganization(aDOrganization);
        return cConvertionType;
    }

    @BeforeEach
    public void initTest() {
        cConvertionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCConvertionType() throws Exception {
        int databaseSizeBeforeCreate = cConvertionTypeRepository.findAll().size();

        // Create the CConvertionType
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(cConvertionType);
        restCConvertionTypeMockMvc.perform(post("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CConvertionType in the database
        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CConvertionType testCConvertionType = cConvertionTypeList.get(cConvertionTypeList.size() - 1);
        assertThat(testCConvertionType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCConvertionType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCConvertionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCConvertionType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCConvertionType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCConvertionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cConvertionTypeRepository.findAll().size();

        // Create the CConvertionType with an existing ID
        cConvertionType.setId(1L);
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(cConvertionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCConvertionTypeMockMvc.perform(post("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CConvertionType in the database
        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cConvertionTypeRepository.findAll().size();
        // set the field null
        cConvertionType.setCode(null);

        // Create the CConvertionType, which fails.
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(cConvertionType);

        restCConvertionTypeMockMvc.perform(post("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cConvertionTypeRepository.findAll().size();
        // set the field null
        cConvertionType.setName(null);

        // Create the CConvertionType, which fails.
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(cConvertionType);

        restCConvertionTypeMockMvc.perform(post("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypes() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cConvertionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCConvertionType() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get the cConvertionType
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types/{id}", cConvertionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cConvertionType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCConvertionTypesByIdFiltering() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        Long id = cConvertionType.getId();

        defaultCConvertionTypeShouldBeFound("id.equals=" + id);
        defaultCConvertionTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCConvertionTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCConvertionTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCConvertionTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCConvertionTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCConvertionTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code equals to DEFAULT_CODE
        defaultCConvertionTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cConvertionTypeList where code equals to UPDATED_CODE
        defaultCConvertionTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code not equals to DEFAULT_CODE
        defaultCConvertionTypeShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cConvertionTypeList where code not equals to UPDATED_CODE
        defaultCConvertionTypeShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCConvertionTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cConvertionTypeList where code equals to UPDATED_CODE
        defaultCConvertionTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code is not null
        defaultCConvertionTypeShouldBeFound("code.specified=true");

        // Get all the cConvertionTypeList where code is null
        defaultCConvertionTypeShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCConvertionTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code contains DEFAULT_CODE
        defaultCConvertionTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cConvertionTypeList where code contains UPDATED_CODE
        defaultCConvertionTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where code does not contain DEFAULT_CODE
        defaultCConvertionTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cConvertionTypeList where code does not contain UPDATED_CODE
        defaultCConvertionTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCConvertionTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name equals to DEFAULT_NAME
        defaultCConvertionTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cConvertionTypeList where name equals to UPDATED_NAME
        defaultCConvertionTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name not equals to DEFAULT_NAME
        defaultCConvertionTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cConvertionTypeList where name not equals to UPDATED_NAME
        defaultCConvertionTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCConvertionTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cConvertionTypeList where name equals to UPDATED_NAME
        defaultCConvertionTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name is not null
        defaultCConvertionTypeShouldBeFound("name.specified=true");

        // Get all the cConvertionTypeList where name is null
        defaultCConvertionTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCConvertionTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name contains DEFAULT_NAME
        defaultCConvertionTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cConvertionTypeList where name contains UPDATED_NAME
        defaultCConvertionTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where name does not contain DEFAULT_NAME
        defaultCConvertionTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cConvertionTypeList where name does not contain UPDATED_NAME
        defaultCConvertionTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCConvertionTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cConvertionTypeList where description equals to UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCConvertionTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cConvertionTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cConvertionTypeList where description equals to UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description is not null
        defaultCConvertionTypeShouldBeFound("description.specified=true");

        // Get all the cConvertionTypeList where description is null
        defaultCConvertionTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description contains DEFAULT_DESCRIPTION
        defaultCConvertionTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cConvertionTypeList where description contains UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCConvertionTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cConvertionTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCConvertionTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCConvertionTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where uid equals to DEFAULT_UID
        defaultCConvertionTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cConvertionTypeList where uid equals to UPDATED_UID
        defaultCConvertionTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where uid not equals to DEFAULT_UID
        defaultCConvertionTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cConvertionTypeList where uid not equals to UPDATED_UID
        defaultCConvertionTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCConvertionTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cConvertionTypeList where uid equals to UPDATED_UID
        defaultCConvertionTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where uid is not null
        defaultCConvertionTypeShouldBeFound("uid.specified=true");

        // Get all the cConvertionTypeList where uid is null
        defaultCConvertionTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where active equals to DEFAULT_ACTIVE
        defaultCConvertionTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cConvertionTypeList where active equals to UPDATED_ACTIVE
        defaultCConvertionTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where active not equals to DEFAULT_ACTIVE
        defaultCConvertionTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cConvertionTypeList where active not equals to UPDATED_ACTIVE
        defaultCConvertionTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCConvertionTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cConvertionTypeList where active equals to UPDATED_ACTIVE
        defaultCConvertionTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        // Get all the cConvertionTypeList where active is not null
        defaultCConvertionTypeShouldBeFound("active.specified=true");

        // Get all the cConvertionTypeList where active is null
        defaultCConvertionTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCConvertionTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cConvertionType.getAdOrganization();
        cConvertionTypeRepository.saveAndFlush(cConvertionType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cConvertionTypeList where adOrganization equals to adOrganizationId
        defaultCConvertionTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cConvertionTypeList where adOrganization equals to adOrganizationId + 1
        defaultCConvertionTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCConvertionTypeShouldBeFound(String filter) throws Exception {
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cConvertionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCConvertionTypeShouldNotBeFound(String filter) throws Exception {
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCConvertionType() throws Exception {
        // Get the cConvertionType
        restCConvertionTypeMockMvc.perform(get("/api/c-convertion-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCConvertionType() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        int databaseSizeBeforeUpdate = cConvertionTypeRepository.findAll().size();

        // Update the cConvertionType
        CConvertionType updatedCConvertionType = cConvertionTypeRepository.findById(cConvertionType.getId()).get();
        // Disconnect from session so that the updates on updatedCConvertionType are not directly saved in db
        em.detach(updatedCConvertionType);
        updatedCConvertionType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(updatedCConvertionType);

        restCConvertionTypeMockMvc.perform(put("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CConvertionType in the database
        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeUpdate);
        CConvertionType testCConvertionType = cConvertionTypeList.get(cConvertionTypeList.size() - 1);
        assertThat(testCConvertionType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCConvertionType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCConvertionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCConvertionType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCConvertionType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCConvertionType() throws Exception {
        int databaseSizeBeforeUpdate = cConvertionTypeRepository.findAll().size();

        // Create the CConvertionType
        CConvertionTypeDTO cConvertionTypeDTO = cConvertionTypeMapper.toDto(cConvertionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCConvertionTypeMockMvc.perform(put("/api/c-convertion-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cConvertionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CConvertionType in the database
        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCConvertionType() throws Exception {
        // Initialize the database
        cConvertionTypeRepository.saveAndFlush(cConvertionType);

        int databaseSizeBeforeDelete = cConvertionTypeRepository.findAll().size();

        // Delete the cConvertionType
        restCConvertionTypeMockMvc.perform(delete("/api/c-convertion-types/{id}", cConvertionType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CConvertionType> cConvertionTypeList = cConvertionTypeRepository.findAll();
        assertThat(cConvertionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
