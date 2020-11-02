package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CCostCenterRepository;
import com.bhp.opusb.service.CCostCenterService;
import com.bhp.opusb.service.dto.CCostCenterDTO;
import com.bhp.opusb.service.mapper.CCostCenterMapper;
import com.bhp.opusb.service.dto.CCostCenterCriteria;
import com.bhp.opusb.service.CCostCenterQueryService;

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
 * Integration tests for the {@link CCostCenterResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CCostCenterResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CCostCenterRepository cCostCenterRepository;

    @Autowired
    private CCostCenterMapper cCostCenterMapper;

    @Autowired
    private CCostCenterService cCostCenterService;

    @Autowired
    private CCostCenterQueryService cCostCenterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCostCenterMockMvc;

    private CCostCenter cCostCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCostCenter createEntity(EntityManager em) {
        CCostCenter cCostCenter = new CCostCenter()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .division(DEFAULT_DIVISION)
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
        cCostCenter.setAdOrganization(aDOrganization);
        return cCostCenter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCostCenter createUpdatedEntity(EntityManager em) {
        CCostCenter cCostCenter = new CCostCenter()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .division(UPDATED_DIVISION)
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
        cCostCenter.setAdOrganization(aDOrganization);
        return cCostCenter;
    }

    @BeforeEach
    public void initTest() {
        cCostCenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCostCenter() throws Exception {
        int databaseSizeBeforeCreate = cCostCenterRepository.findAll().size();

        // Create the CCostCenter
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);
        restCCostCenterMockMvc.perform(post("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isCreated());

        // Validate the CCostCenter in the database
        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeCreate + 1);
        CCostCenter testCCostCenter = cCostCenterList.get(cCostCenterList.size() - 1);
        assertThat(testCCostCenter.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCCostCenter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCostCenter.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testCCostCenter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCCostCenter.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCCostCenter.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCCostCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCostCenterRepository.findAll().size();

        // Create the CCostCenter with an existing ID
        cCostCenter.setId(1L);
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCostCenterMockMvc.perform(post("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCostCenter in the database
        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCostCenterRepository.findAll().size();
        // set the field null
        cCostCenter.setCode(null);

        // Create the CCostCenter, which fails.
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);

        restCCostCenterMockMvc.perform(post("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isBadRequest());

        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCostCenterRepository.findAll().size();
        // set the field null
        cCostCenter.setName(null);

        // Create the CCostCenter, which fails.
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);

        restCCostCenterMockMvc.perform(post("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isBadRequest());

        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDivisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCostCenterRepository.findAll().size();
        // set the field null
        cCostCenter.setDivision(null);

        // Create the CCostCenter, which fails.
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);

        restCCostCenterMockMvc.perform(post("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isBadRequest());

        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCostCenters() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCostCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCCostCenter() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get the cCostCenter
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers/{id}", cCostCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCostCenter.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCCostCentersByIdFiltering() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        Long id = cCostCenter.getId();

        defaultCCostCenterShouldBeFound("id.equals=" + id);
        defaultCCostCenterShouldNotBeFound("id.notEquals=" + id);

        defaultCCostCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCCostCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultCCostCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCCostCenterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCCostCentersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code equals to DEFAULT_CODE
        defaultCCostCenterShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cCostCenterList where code equals to UPDATED_CODE
        defaultCCostCenterShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code not equals to DEFAULT_CODE
        defaultCCostCenterShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cCostCenterList where code not equals to UPDATED_CODE
        defaultCCostCenterShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCCostCenterShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cCostCenterList where code equals to UPDATED_CODE
        defaultCCostCenterShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code is not null
        defaultCCostCenterShouldBeFound("code.specified=true");

        // Get all the cCostCenterList where code is null
        defaultCCostCenterShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCostCentersByCodeContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code contains DEFAULT_CODE
        defaultCCostCenterShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cCostCenterList where code contains UPDATED_CODE
        defaultCCostCenterShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where code does not contain DEFAULT_CODE
        defaultCCostCenterShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cCostCenterList where code does not contain UPDATED_CODE
        defaultCCostCenterShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCCostCentersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name equals to DEFAULT_NAME
        defaultCCostCenterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cCostCenterList where name equals to UPDATED_NAME
        defaultCCostCenterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name not equals to DEFAULT_NAME
        defaultCCostCenterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cCostCenterList where name not equals to UPDATED_NAME
        defaultCCostCenterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCCostCenterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cCostCenterList where name equals to UPDATED_NAME
        defaultCCostCenterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name is not null
        defaultCCostCenterShouldBeFound("name.specified=true");

        // Get all the cCostCenterList where name is null
        defaultCCostCenterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCostCentersByNameContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name contains DEFAULT_NAME
        defaultCCostCenterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cCostCenterList where name contains UPDATED_NAME
        defaultCCostCenterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where name does not contain DEFAULT_NAME
        defaultCCostCenterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cCostCenterList where name does not contain UPDATED_NAME
        defaultCCostCenterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCCostCentersByDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division equals to DEFAULT_DIVISION
        defaultCCostCenterShouldBeFound("division.equals=" + DEFAULT_DIVISION);

        // Get all the cCostCenterList where division equals to UPDATED_DIVISION
        defaultCCostCenterShouldNotBeFound("division.equals=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDivisionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division not equals to DEFAULT_DIVISION
        defaultCCostCenterShouldNotBeFound("division.notEquals=" + DEFAULT_DIVISION);

        // Get all the cCostCenterList where division not equals to UPDATED_DIVISION
        defaultCCostCenterShouldBeFound("division.notEquals=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDivisionIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division in DEFAULT_DIVISION or UPDATED_DIVISION
        defaultCCostCenterShouldBeFound("division.in=" + DEFAULT_DIVISION + "," + UPDATED_DIVISION);

        // Get all the cCostCenterList where division equals to UPDATED_DIVISION
        defaultCCostCenterShouldNotBeFound("division.in=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDivisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division is not null
        defaultCCostCenterShouldBeFound("division.specified=true");

        // Get all the cCostCenterList where division is null
        defaultCCostCenterShouldNotBeFound("division.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCostCentersByDivisionContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division contains DEFAULT_DIVISION
        defaultCCostCenterShouldBeFound("division.contains=" + DEFAULT_DIVISION);

        // Get all the cCostCenterList where division contains UPDATED_DIVISION
        defaultCCostCenterShouldNotBeFound("division.contains=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDivisionNotContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where division does not contain DEFAULT_DIVISION
        defaultCCostCenterShouldNotBeFound("division.doesNotContain=" + DEFAULT_DIVISION);

        // Get all the cCostCenterList where division does not contain UPDATED_DIVISION
        defaultCCostCenterShouldBeFound("division.doesNotContain=" + UPDATED_DIVISION);
    }


    @Test
    @Transactional
    public void getAllCCostCentersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description equals to DEFAULT_DESCRIPTION
        defaultCCostCenterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cCostCenterList where description equals to UPDATED_DESCRIPTION
        defaultCCostCenterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description not equals to DEFAULT_DESCRIPTION
        defaultCCostCenterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cCostCenterList where description not equals to UPDATED_DESCRIPTION
        defaultCCostCenterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCCostCenterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cCostCenterList where description equals to UPDATED_DESCRIPTION
        defaultCCostCenterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description is not null
        defaultCCostCenterShouldBeFound("description.specified=true");

        // Get all the cCostCenterList where description is null
        defaultCCostCenterShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCostCentersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description contains DEFAULT_DESCRIPTION
        defaultCCostCenterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cCostCenterList where description contains UPDATED_DESCRIPTION
        defaultCCostCenterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where description does not contain DEFAULT_DESCRIPTION
        defaultCCostCenterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cCostCenterList where description does not contain UPDATED_DESCRIPTION
        defaultCCostCenterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCCostCentersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where uid equals to DEFAULT_UID
        defaultCCostCenterShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cCostCenterList where uid equals to UPDATED_UID
        defaultCCostCenterShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where uid not equals to DEFAULT_UID
        defaultCCostCenterShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cCostCenterList where uid not equals to UPDATED_UID
        defaultCCostCenterShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where uid in DEFAULT_UID or UPDATED_UID
        defaultCCostCenterShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cCostCenterList where uid equals to UPDATED_UID
        defaultCCostCenterShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where uid is not null
        defaultCCostCenterShouldBeFound("uid.specified=true");

        // Get all the cCostCenterList where uid is null
        defaultCCostCenterShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCostCentersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where active equals to DEFAULT_ACTIVE
        defaultCCostCenterShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cCostCenterList where active equals to UPDATED_ACTIVE
        defaultCCostCenterShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where active not equals to DEFAULT_ACTIVE
        defaultCCostCenterShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cCostCenterList where active not equals to UPDATED_ACTIVE
        defaultCCostCenterShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCCostCenterShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cCostCenterList where active equals to UPDATED_ACTIVE
        defaultCCostCenterShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCostCentersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        // Get all the cCostCenterList where active is not null
        defaultCCostCenterShouldBeFound("active.specified=true");

        // Get all the cCostCenterList where active is null
        defaultCCostCenterShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCostCentersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cCostCenter.getAdOrganization();
        cCostCenterRepository.saveAndFlush(cCostCenter);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cCostCenterList where adOrganization equals to adOrganizationId
        defaultCCostCenterShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cCostCenterList where adOrganization equals to adOrganizationId + 1
        defaultCCostCenterShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCCostCenterShouldBeFound(String filter) throws Exception {
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCostCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCCostCenterShouldNotBeFound(String filter) throws Exception {
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCCostCenter() throws Exception {
        // Get the cCostCenter
        restCCostCenterMockMvc.perform(get("/api/c-cost-centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCostCenter() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        int databaseSizeBeforeUpdate = cCostCenterRepository.findAll().size();

        // Update the cCostCenter
        CCostCenter updatedCCostCenter = cCostCenterRepository.findById(cCostCenter.getId()).get();
        // Disconnect from session so that the updates on updatedCCostCenter are not directly saved in db
        em.detach(updatedCCostCenter);
        updatedCCostCenter
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .division(UPDATED_DIVISION)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(updatedCCostCenter);

        restCCostCenterMockMvc.perform(put("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isOk());

        // Validate the CCostCenter in the database
        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeUpdate);
        CCostCenter testCCostCenter = cCostCenterList.get(cCostCenterList.size() - 1);
        assertThat(testCCostCenter.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCCostCenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCostCenter.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testCCostCenter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCCostCenter.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCCostCenter.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = cCostCenterRepository.findAll().size();

        // Create the CCostCenter
        CCostCenterDTO cCostCenterDTO = cCostCenterMapper.toDto(cCostCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCostCenterMockMvc.perform(put("/api/c-cost-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCostCenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCostCenter in the database
        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCostCenter() throws Exception {
        // Initialize the database
        cCostCenterRepository.saveAndFlush(cCostCenter);

        int databaseSizeBeforeDelete = cCostCenterRepository.findAll().size();

        // Delete the cCostCenter
        restCCostCenterMockMvc.perform(delete("/api/c-cost-centers/{id}", cCostCenter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCostCenter> cCostCenterList = cCostCenterRepository.findAll();
        assertThat(cCostCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
