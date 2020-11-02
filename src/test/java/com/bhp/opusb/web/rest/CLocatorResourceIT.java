package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.repository.CLocatorRepository;
import com.bhp.opusb.service.CLocatorService;
import com.bhp.opusb.service.dto.CLocatorDTO;
import com.bhp.opusb.service.mapper.CLocatorMapper;
import com.bhp.opusb.service.dto.CLocatorCriteria;
import com.bhp.opusb.service.CLocatorQueryService;

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
 * Integration tests for the {@link CLocatorResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CLocatorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATOR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AISLE = "AAAAAAAAAA";
    private static final String UPDATED_AISLE = "BBBBBBBBBB";

    private static final String DEFAULT_BIN = "AAAAAAAAAA";
    private static final String UPDATED_BIN = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CLocatorRepository cLocatorRepository;

    @Autowired
    private CLocatorMapper cLocatorMapper;

    @Autowired
    private CLocatorService cLocatorService;

    @Autowired
    private CLocatorQueryService cLocatorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCLocatorMockMvc;

    private CLocator cLocator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLocator createEntity(EntityManager em) {
        CLocator cLocator = new CLocator()
            .code(DEFAULT_CODE)
            .locatorType(DEFAULT_LOCATOR_TYPE)
            .aisle(DEFAULT_AISLE)
            .bin(DEFAULT_BIN)
            .level(DEFAULT_LEVEL)
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
        cLocator.setAdOrganization(aDOrganization);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        cLocator.setWarehouse(cWarehouse);
        return cLocator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLocator createUpdatedEntity(EntityManager em) {
        CLocator cLocator = new CLocator()
            .code(UPDATED_CODE)
            .locatorType(UPDATED_LOCATOR_TYPE)
            .aisle(UPDATED_AISLE)
            .bin(UPDATED_BIN)
            .level(UPDATED_LEVEL)
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
        cLocator.setAdOrganization(aDOrganization);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createUpdatedEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        cLocator.setWarehouse(cWarehouse);
        return cLocator;
    }

    @BeforeEach
    public void initTest() {
        cLocator = createEntity(em);
    }

    @Test
    @Transactional
    public void createCLocator() throws Exception {
        int databaseSizeBeforeCreate = cLocatorRepository.findAll().size();

        // Create the CLocator
        CLocatorDTO cLocatorDTO = cLocatorMapper.toDto(cLocator);
        restCLocatorMockMvc.perform(post("/api/c-locators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocatorDTO)))
            .andExpect(status().isCreated());

        // Validate the CLocator in the database
        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeCreate + 1);
        CLocator testCLocator = cLocatorList.get(cLocatorList.size() - 1);
        assertThat(testCLocator.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCLocator.getLocatorType()).isEqualTo(DEFAULT_LOCATOR_TYPE);
        assertThat(testCLocator.getAisle()).isEqualTo(DEFAULT_AISLE);
        assertThat(testCLocator.getBin()).isEqualTo(DEFAULT_BIN);
        assertThat(testCLocator.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCLocator.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCLocator.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCLocatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cLocatorRepository.findAll().size();

        // Create the CLocator with an existing ID
        cLocator.setId(1L);
        CLocatorDTO cLocatorDTO = cLocatorMapper.toDto(cLocator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCLocatorMockMvc.perform(post("/api/c-locators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLocator in the database
        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cLocatorRepository.findAll().size();
        // set the field null
        cLocator.setCode(null);

        // Create the CLocator, which fails.
        CLocatorDTO cLocatorDTO = cLocatorMapper.toDto(cLocator);

        restCLocatorMockMvc.perform(post("/api/c-locators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocatorDTO)))
            .andExpect(status().isBadRequest());

        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCLocators() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList
        restCLocatorMockMvc.perform(get("/api/c-locators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLocator.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].locatorType").value(hasItem(DEFAULT_LOCATOR_TYPE)))
            .andExpect(jsonPath("$.[*].aisle").value(hasItem(DEFAULT_AISLE)))
            .andExpect(jsonPath("$.[*].bin").value(hasItem(DEFAULT_BIN)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCLocator() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get the cLocator
        restCLocatorMockMvc.perform(get("/api/c-locators/{id}", cLocator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cLocator.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.locatorType").value(DEFAULT_LOCATOR_TYPE))
            .andExpect(jsonPath("$.aisle").value(DEFAULT_AISLE))
            .andExpect(jsonPath("$.bin").value(DEFAULT_BIN))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCLocatorsByIdFiltering() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        Long id = cLocator.getId();

        defaultCLocatorShouldBeFound("id.equals=" + id);
        defaultCLocatorShouldNotBeFound("id.notEquals=" + id);

        defaultCLocatorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCLocatorShouldNotBeFound("id.greaterThan=" + id);

        defaultCLocatorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCLocatorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code equals to DEFAULT_CODE
        defaultCLocatorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cLocatorList where code equals to UPDATED_CODE
        defaultCLocatorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code not equals to DEFAULT_CODE
        defaultCLocatorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cLocatorList where code not equals to UPDATED_CODE
        defaultCLocatorShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCLocatorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cLocatorList where code equals to UPDATED_CODE
        defaultCLocatorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code is not null
        defaultCLocatorShouldBeFound("code.specified=true");

        // Get all the cLocatorList where code is null
        defaultCLocatorShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocatorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code contains DEFAULT_CODE
        defaultCLocatorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cLocatorList where code contains UPDATED_CODE
        defaultCLocatorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where code does not contain DEFAULT_CODE
        defaultCLocatorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cLocatorList where code does not contain UPDATED_CODE
        defaultCLocatorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType equals to DEFAULT_LOCATOR_TYPE
        defaultCLocatorShouldBeFound("locatorType.equals=" + DEFAULT_LOCATOR_TYPE);

        // Get all the cLocatorList where locatorType equals to UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldNotBeFound("locatorType.equals=" + UPDATED_LOCATOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType not equals to DEFAULT_LOCATOR_TYPE
        defaultCLocatorShouldNotBeFound("locatorType.notEquals=" + DEFAULT_LOCATOR_TYPE);

        // Get all the cLocatorList where locatorType not equals to UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldBeFound("locatorType.notEquals=" + UPDATED_LOCATOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType in DEFAULT_LOCATOR_TYPE or UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldBeFound("locatorType.in=" + DEFAULT_LOCATOR_TYPE + "," + UPDATED_LOCATOR_TYPE);

        // Get all the cLocatorList where locatorType equals to UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldNotBeFound("locatorType.in=" + UPDATED_LOCATOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType is not null
        defaultCLocatorShouldBeFound("locatorType.specified=true");

        // Get all the cLocatorList where locatorType is null
        defaultCLocatorShouldNotBeFound("locatorType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType contains DEFAULT_LOCATOR_TYPE
        defaultCLocatorShouldBeFound("locatorType.contains=" + DEFAULT_LOCATOR_TYPE);

        // Get all the cLocatorList where locatorType contains UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldNotBeFound("locatorType.contains=" + UPDATED_LOCATOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLocatorTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where locatorType does not contain DEFAULT_LOCATOR_TYPE
        defaultCLocatorShouldNotBeFound("locatorType.doesNotContain=" + DEFAULT_LOCATOR_TYPE);

        // Get all the cLocatorList where locatorType does not contain UPDATED_LOCATOR_TYPE
        defaultCLocatorShouldBeFound("locatorType.doesNotContain=" + UPDATED_LOCATOR_TYPE);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByAisleIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle equals to DEFAULT_AISLE
        defaultCLocatorShouldBeFound("aisle.equals=" + DEFAULT_AISLE);

        // Get all the cLocatorList where aisle equals to UPDATED_AISLE
        defaultCLocatorShouldNotBeFound("aisle.equals=" + UPDATED_AISLE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByAisleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle not equals to DEFAULT_AISLE
        defaultCLocatorShouldNotBeFound("aisle.notEquals=" + DEFAULT_AISLE);

        // Get all the cLocatorList where aisle not equals to UPDATED_AISLE
        defaultCLocatorShouldBeFound("aisle.notEquals=" + UPDATED_AISLE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByAisleIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle in DEFAULT_AISLE or UPDATED_AISLE
        defaultCLocatorShouldBeFound("aisle.in=" + DEFAULT_AISLE + "," + UPDATED_AISLE);

        // Get all the cLocatorList where aisle equals to UPDATED_AISLE
        defaultCLocatorShouldNotBeFound("aisle.in=" + UPDATED_AISLE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByAisleIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle is not null
        defaultCLocatorShouldBeFound("aisle.specified=true");

        // Get all the cLocatorList where aisle is null
        defaultCLocatorShouldNotBeFound("aisle.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocatorsByAisleContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle contains DEFAULT_AISLE
        defaultCLocatorShouldBeFound("aisle.contains=" + DEFAULT_AISLE);

        // Get all the cLocatorList where aisle contains UPDATED_AISLE
        defaultCLocatorShouldNotBeFound("aisle.contains=" + UPDATED_AISLE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByAisleNotContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where aisle does not contain DEFAULT_AISLE
        defaultCLocatorShouldNotBeFound("aisle.doesNotContain=" + DEFAULT_AISLE);

        // Get all the cLocatorList where aisle does not contain UPDATED_AISLE
        defaultCLocatorShouldBeFound("aisle.doesNotContain=" + UPDATED_AISLE);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByBinIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin equals to DEFAULT_BIN
        defaultCLocatorShouldBeFound("bin.equals=" + DEFAULT_BIN);

        // Get all the cLocatorList where bin equals to UPDATED_BIN
        defaultCLocatorShouldNotBeFound("bin.equals=" + UPDATED_BIN);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByBinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin not equals to DEFAULT_BIN
        defaultCLocatorShouldNotBeFound("bin.notEquals=" + DEFAULT_BIN);

        // Get all the cLocatorList where bin not equals to UPDATED_BIN
        defaultCLocatorShouldBeFound("bin.notEquals=" + UPDATED_BIN);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByBinIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin in DEFAULT_BIN or UPDATED_BIN
        defaultCLocatorShouldBeFound("bin.in=" + DEFAULT_BIN + "," + UPDATED_BIN);

        // Get all the cLocatorList where bin equals to UPDATED_BIN
        defaultCLocatorShouldNotBeFound("bin.in=" + UPDATED_BIN);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByBinIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin is not null
        defaultCLocatorShouldBeFound("bin.specified=true");

        // Get all the cLocatorList where bin is null
        defaultCLocatorShouldNotBeFound("bin.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocatorsByBinContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin contains DEFAULT_BIN
        defaultCLocatorShouldBeFound("bin.contains=" + DEFAULT_BIN);

        // Get all the cLocatorList where bin contains UPDATED_BIN
        defaultCLocatorShouldNotBeFound("bin.contains=" + UPDATED_BIN);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByBinNotContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where bin does not contain DEFAULT_BIN
        defaultCLocatorShouldNotBeFound("bin.doesNotContain=" + DEFAULT_BIN);

        // Get all the cLocatorList where bin does not contain UPDATED_BIN
        defaultCLocatorShouldBeFound("bin.doesNotContain=" + UPDATED_BIN);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level equals to DEFAULT_LEVEL
        defaultCLocatorShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the cLocatorList where level equals to UPDATED_LEVEL
        defaultCLocatorShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level not equals to DEFAULT_LEVEL
        defaultCLocatorShouldNotBeFound("level.notEquals=" + DEFAULT_LEVEL);

        // Get all the cLocatorList where level not equals to UPDATED_LEVEL
        defaultCLocatorShouldBeFound("level.notEquals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultCLocatorShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the cLocatorList where level equals to UPDATED_LEVEL
        defaultCLocatorShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level is not null
        defaultCLocatorShouldBeFound("level.specified=true");

        // Get all the cLocatorList where level is null
        defaultCLocatorShouldNotBeFound("level.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocatorsByLevelContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level contains DEFAULT_LEVEL
        defaultCLocatorShouldBeFound("level.contains=" + DEFAULT_LEVEL);

        // Get all the cLocatorList where level contains UPDATED_LEVEL
        defaultCLocatorShouldNotBeFound("level.contains=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByLevelNotContainsSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where level does not contain DEFAULT_LEVEL
        defaultCLocatorShouldNotBeFound("level.doesNotContain=" + DEFAULT_LEVEL);

        // Get all the cLocatorList where level does not contain UPDATED_LEVEL
        defaultCLocatorShouldBeFound("level.doesNotContain=" + UPDATED_LEVEL);
    }


    @Test
    @Transactional
    public void getAllCLocatorsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where uid equals to DEFAULT_UID
        defaultCLocatorShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cLocatorList where uid equals to UPDATED_UID
        defaultCLocatorShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where uid not equals to DEFAULT_UID
        defaultCLocatorShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cLocatorList where uid not equals to UPDATED_UID
        defaultCLocatorShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where uid in DEFAULT_UID or UPDATED_UID
        defaultCLocatorShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cLocatorList where uid equals to UPDATED_UID
        defaultCLocatorShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where uid is not null
        defaultCLocatorShouldBeFound("uid.specified=true");

        // Get all the cLocatorList where uid is null
        defaultCLocatorShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCLocatorsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where active equals to DEFAULT_ACTIVE
        defaultCLocatorShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cLocatorList where active equals to UPDATED_ACTIVE
        defaultCLocatorShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where active not equals to DEFAULT_ACTIVE
        defaultCLocatorShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cLocatorList where active not equals to UPDATED_ACTIVE
        defaultCLocatorShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCLocatorShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cLocatorList where active equals to UPDATED_ACTIVE
        defaultCLocatorShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocatorsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        // Get all the cLocatorList where active is not null
        defaultCLocatorShouldBeFound("active.specified=true");

        // Get all the cLocatorList where active is null
        defaultCLocatorShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCLocatorsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cLocator.getAdOrganization();
        cLocatorRepository.saveAndFlush(cLocator);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cLocatorList where adOrganization equals to adOrganizationId
        defaultCLocatorShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cLocatorList where adOrganization equals to adOrganizationId + 1
        defaultCLocatorShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCLocatorsByWarehouseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CWarehouse warehouse = cLocator.getWarehouse();
        cLocatorRepository.saveAndFlush(cLocator);
        Long warehouseId = warehouse.getId();

        // Get all the cLocatorList where warehouse equals to warehouseId
        defaultCLocatorShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the cLocatorList where warehouse equals to warehouseId + 1
        defaultCLocatorShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCLocatorShouldBeFound(String filter) throws Exception {
        restCLocatorMockMvc.perform(get("/api/c-locators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLocator.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].locatorType").value(hasItem(DEFAULT_LOCATOR_TYPE)))
            .andExpect(jsonPath("$.[*].aisle").value(hasItem(DEFAULT_AISLE)))
            .andExpect(jsonPath("$.[*].bin").value(hasItem(DEFAULT_BIN)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCLocatorMockMvc.perform(get("/api/c-locators/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCLocatorShouldNotBeFound(String filter) throws Exception {
        restCLocatorMockMvc.perform(get("/api/c-locators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCLocatorMockMvc.perform(get("/api/c-locators/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCLocator() throws Exception {
        // Get the cLocator
        restCLocatorMockMvc.perform(get("/api/c-locators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCLocator() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        int databaseSizeBeforeUpdate = cLocatorRepository.findAll().size();

        // Update the cLocator
        CLocator updatedCLocator = cLocatorRepository.findById(cLocator.getId()).get();
        // Disconnect from session so that the updates on updatedCLocator are not directly saved in db
        em.detach(updatedCLocator);
        updatedCLocator
            .code(UPDATED_CODE)
            .locatorType(UPDATED_LOCATOR_TYPE)
            .aisle(UPDATED_AISLE)
            .bin(UPDATED_BIN)
            .level(UPDATED_LEVEL)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CLocatorDTO cLocatorDTO = cLocatorMapper.toDto(updatedCLocator);

        restCLocatorMockMvc.perform(put("/api/c-locators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocatorDTO)))
            .andExpect(status().isOk());

        // Validate the CLocator in the database
        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeUpdate);
        CLocator testCLocator = cLocatorList.get(cLocatorList.size() - 1);
        assertThat(testCLocator.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCLocator.getLocatorType()).isEqualTo(UPDATED_LOCATOR_TYPE);
        assertThat(testCLocator.getAisle()).isEqualTo(UPDATED_AISLE);
        assertThat(testCLocator.getBin()).isEqualTo(UPDATED_BIN);
        assertThat(testCLocator.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCLocator.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCLocator.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCLocator() throws Exception {
        int databaseSizeBeforeUpdate = cLocatorRepository.findAll().size();

        // Create the CLocator
        CLocatorDTO cLocatorDTO = cLocatorMapper.toDto(cLocator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLocatorMockMvc.perform(put("/api/c-locators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLocator in the database
        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCLocator() throws Exception {
        // Initialize the database
        cLocatorRepository.saveAndFlush(cLocator);

        int databaseSizeBeforeDelete = cLocatorRepository.findAll().size();

        // Delete the cLocator
        restCLocatorMockMvc.perform(delete("/api/c-locators/{id}", cLocator.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CLocator> cLocatorList = cLocatorRepository.findAll();
        assertThat(cLocatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
