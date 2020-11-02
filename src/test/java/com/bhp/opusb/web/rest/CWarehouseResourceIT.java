package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CWarehouseRepository;
import com.bhp.opusb.service.CWarehouseService;
import com.bhp.opusb.service.dto.CWarehouseDTO;
import com.bhp.opusb.service.mapper.CWarehouseMapper;
import com.bhp.opusb.service.dto.CWarehouseCriteria;
import com.bhp.opusb.service.CWarehouseQueryService;

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
 * Integration tests for the {@link CWarehouseResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CWarehouseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CWarehouseRepository cWarehouseRepository;

    @Autowired
    private CWarehouseMapper cWarehouseMapper;

    @Autowired
    private CWarehouseService cWarehouseService;

    @Autowired
    private CWarehouseQueryService cWarehouseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCWarehouseMockMvc;

    private CWarehouse cWarehouse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CWarehouse createEntity(EntityManager em) {
        CWarehouse cWarehouse = new CWarehouse()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
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
        cWarehouse.setAdOrganization(aDOrganization);
        return cWarehouse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CWarehouse createUpdatedEntity(EntityManager em) {
        CWarehouse cWarehouse = new CWarehouse()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
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
        cWarehouse.setAdOrganization(aDOrganization);
        return cWarehouse;
    }

    @BeforeEach
    public void initTest() {
        cWarehouse = createEntity(em);
    }

    @Test
    @Transactional
    public void createCWarehouse() throws Exception {
        int databaseSizeBeforeCreate = cWarehouseRepository.findAll().size();

        // Create the CWarehouse
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(cWarehouse);
        restCWarehouseMockMvc.perform(post("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isCreated());

        // Validate the CWarehouse in the database
        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeCreate + 1);
        CWarehouse testCWarehouse = cWarehouseList.get(cWarehouseList.size() - 1);
        assertThat(testCWarehouse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCWarehouse.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCWarehouse.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCWarehouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCWarehouse.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCWarehouse.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCWarehouseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cWarehouseRepository.findAll().size();

        // Create the CWarehouse with an existing ID
        cWarehouse.setId(1L);
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(cWarehouse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCWarehouseMockMvc.perform(post("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CWarehouse in the database
        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cWarehouseRepository.findAll().size();
        // set the field null
        cWarehouse.setName(null);

        // Create the CWarehouse, which fails.
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(cWarehouse);

        restCWarehouseMockMvc.perform(post("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isBadRequest());

        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cWarehouseRepository.findAll().size();
        // set the field null
        cWarehouse.setCode(null);

        // Create the CWarehouse, which fails.
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(cWarehouse);

        restCWarehouseMockMvc.perform(post("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isBadRequest());

        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCWarehouses() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList
        restCWarehouseMockMvc.perform(get("/api/c-warehouses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cWarehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCWarehouse() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get the cWarehouse
        restCWarehouseMockMvc.perform(get("/api/c-warehouses/{id}", cWarehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cWarehouse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCWarehousesByIdFiltering() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        Long id = cWarehouse.getId();

        defaultCWarehouseShouldBeFound("id.equals=" + id);
        defaultCWarehouseShouldNotBeFound("id.notEquals=" + id);

        defaultCWarehouseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCWarehouseShouldNotBeFound("id.greaterThan=" + id);

        defaultCWarehouseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCWarehouseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCWarehousesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name equals to DEFAULT_NAME
        defaultCWarehouseShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cWarehouseList where name equals to UPDATED_NAME
        defaultCWarehouseShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name not equals to DEFAULT_NAME
        defaultCWarehouseShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cWarehouseList where name not equals to UPDATED_NAME
        defaultCWarehouseShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCWarehouseShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cWarehouseList where name equals to UPDATED_NAME
        defaultCWarehouseShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name is not null
        defaultCWarehouseShouldBeFound("name.specified=true");

        // Get all the cWarehouseList where name is null
        defaultCWarehouseShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCWarehousesByNameContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name contains DEFAULT_NAME
        defaultCWarehouseShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cWarehouseList where name contains UPDATED_NAME
        defaultCWarehouseShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where name does not contain DEFAULT_NAME
        defaultCWarehouseShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cWarehouseList where name does not contain UPDATED_NAME
        defaultCWarehouseShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCWarehousesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code equals to DEFAULT_CODE
        defaultCWarehouseShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cWarehouseList where code equals to UPDATED_CODE
        defaultCWarehouseShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code not equals to DEFAULT_CODE
        defaultCWarehouseShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cWarehouseList where code not equals to UPDATED_CODE
        defaultCWarehouseShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCWarehouseShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cWarehouseList where code equals to UPDATED_CODE
        defaultCWarehouseShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code is not null
        defaultCWarehouseShouldBeFound("code.specified=true");

        // Get all the cWarehouseList where code is null
        defaultCWarehouseShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCWarehousesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code contains DEFAULT_CODE
        defaultCWarehouseShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cWarehouseList where code contains UPDATED_CODE
        defaultCWarehouseShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where code does not contain DEFAULT_CODE
        defaultCWarehouseShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cWarehouseList where code does not contain UPDATED_CODE
        defaultCWarehouseShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCWarehousesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description equals to DEFAULT_DESCRIPTION
        defaultCWarehouseShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cWarehouseList where description equals to UPDATED_DESCRIPTION
        defaultCWarehouseShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description not equals to DEFAULT_DESCRIPTION
        defaultCWarehouseShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cWarehouseList where description not equals to UPDATED_DESCRIPTION
        defaultCWarehouseShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCWarehouseShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cWarehouseList where description equals to UPDATED_DESCRIPTION
        defaultCWarehouseShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description is not null
        defaultCWarehouseShouldBeFound("description.specified=true");

        // Get all the cWarehouseList where description is null
        defaultCWarehouseShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCWarehousesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description contains DEFAULT_DESCRIPTION
        defaultCWarehouseShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cWarehouseList where description contains UPDATED_DESCRIPTION
        defaultCWarehouseShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where description does not contain DEFAULT_DESCRIPTION
        defaultCWarehouseShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cWarehouseList where description does not contain UPDATED_DESCRIPTION
        defaultCWarehouseShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCWarehousesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address equals to DEFAULT_ADDRESS
        defaultCWarehouseShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the cWarehouseList where address equals to UPDATED_ADDRESS
        defaultCWarehouseShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address not equals to DEFAULT_ADDRESS
        defaultCWarehouseShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the cWarehouseList where address not equals to UPDATED_ADDRESS
        defaultCWarehouseShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCWarehouseShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the cWarehouseList where address equals to UPDATED_ADDRESS
        defaultCWarehouseShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address is not null
        defaultCWarehouseShouldBeFound("address.specified=true");

        // Get all the cWarehouseList where address is null
        defaultCWarehouseShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCWarehousesByAddressContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address contains DEFAULT_ADDRESS
        defaultCWarehouseShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the cWarehouseList where address contains UPDATED_ADDRESS
        defaultCWarehouseShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where address does not contain DEFAULT_ADDRESS
        defaultCWarehouseShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the cWarehouseList where address does not contain UPDATED_ADDRESS
        defaultCWarehouseShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCWarehousesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where uid equals to DEFAULT_UID
        defaultCWarehouseShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cWarehouseList where uid equals to UPDATED_UID
        defaultCWarehouseShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where uid not equals to DEFAULT_UID
        defaultCWarehouseShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cWarehouseList where uid not equals to UPDATED_UID
        defaultCWarehouseShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where uid in DEFAULT_UID or UPDATED_UID
        defaultCWarehouseShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cWarehouseList where uid equals to UPDATED_UID
        defaultCWarehouseShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where uid is not null
        defaultCWarehouseShouldBeFound("uid.specified=true");

        // Get all the cWarehouseList where uid is null
        defaultCWarehouseShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCWarehousesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where active equals to DEFAULT_ACTIVE
        defaultCWarehouseShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cWarehouseList where active equals to UPDATED_ACTIVE
        defaultCWarehouseShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where active not equals to DEFAULT_ACTIVE
        defaultCWarehouseShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cWarehouseList where active not equals to UPDATED_ACTIVE
        defaultCWarehouseShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCWarehouseShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cWarehouseList where active equals to UPDATED_ACTIVE
        defaultCWarehouseShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCWarehousesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        // Get all the cWarehouseList where active is not null
        defaultCWarehouseShouldBeFound("active.specified=true");

        // Get all the cWarehouseList where active is null
        defaultCWarehouseShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCWarehousesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cWarehouse.getAdOrganization();
        cWarehouseRepository.saveAndFlush(cWarehouse);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cWarehouseList where adOrganization equals to adOrganizationId
        defaultCWarehouseShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cWarehouseList where adOrganization equals to adOrganizationId + 1
        defaultCWarehouseShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCWarehouseShouldBeFound(String filter) throws Exception {
        restCWarehouseMockMvc.perform(get("/api/c-warehouses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cWarehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCWarehouseMockMvc.perform(get("/api/c-warehouses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCWarehouseShouldNotBeFound(String filter) throws Exception {
        restCWarehouseMockMvc.perform(get("/api/c-warehouses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCWarehouseMockMvc.perform(get("/api/c-warehouses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCWarehouse() throws Exception {
        // Get the cWarehouse
        restCWarehouseMockMvc.perform(get("/api/c-warehouses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCWarehouse() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        int databaseSizeBeforeUpdate = cWarehouseRepository.findAll().size();

        // Update the cWarehouse
        CWarehouse updatedCWarehouse = cWarehouseRepository.findById(cWarehouse.getId()).get();
        // Disconnect from session so that the updates on updatedCWarehouse are not directly saved in db
        em.detach(updatedCWarehouse);
        updatedCWarehouse
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(updatedCWarehouse);

        restCWarehouseMockMvc.perform(put("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isOk());

        // Validate the CWarehouse in the database
        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeUpdate);
        CWarehouse testCWarehouse = cWarehouseList.get(cWarehouseList.size() - 1);
        assertThat(testCWarehouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCWarehouse.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCWarehouse.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCWarehouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCWarehouse.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCWarehouse.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = cWarehouseRepository.findAll().size();

        // Create the CWarehouse
        CWarehouseDTO cWarehouseDTO = cWarehouseMapper.toDto(cWarehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCWarehouseMockMvc.perform(put("/api/c-warehouses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cWarehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CWarehouse in the database
        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCWarehouse() throws Exception {
        // Initialize the database
        cWarehouseRepository.saveAndFlush(cWarehouse);

        int databaseSizeBeforeDelete = cWarehouseRepository.findAll().size();

        // Delete the cWarehouse
        restCWarehouseMockMvc.perform(delete("/api/c-warehouses/{id}", cWarehouse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CWarehouse> cWarehouseList = cWarehouseRepository.findAll();
        assertThat(cWarehouseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
