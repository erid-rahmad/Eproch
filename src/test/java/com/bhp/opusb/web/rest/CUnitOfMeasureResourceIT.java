package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CUnitOfMeasureRepository;
import com.bhp.opusb.service.CUnitOfMeasureService;
import com.bhp.opusb.service.dto.CUnitOfMeasureDTO;
import com.bhp.opusb.service.mapper.CUnitOfMeasureMapper;
import com.bhp.opusb.service.dto.CUnitOfMeasureCriteria;
import com.bhp.opusb.service.CUnitOfMeasureQueryService;

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
 * Integration tests for the {@link CUnitOfMeasureResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CUnitOfMeasureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CUnitOfMeasureRepository cUnitOfMeasureRepository;

    @Autowired
    private CUnitOfMeasureMapper cUnitOfMeasureMapper;

    @Autowired
    private CUnitOfMeasureService cUnitOfMeasureService;

    @Autowired
    private CUnitOfMeasureQueryService cUnitOfMeasureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCUnitOfMeasureMockMvc;

    private CUnitOfMeasure cUnitOfMeasure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CUnitOfMeasure createEntity(EntityManager em) {
        CUnitOfMeasure cUnitOfMeasure = new CUnitOfMeasure()
            .code(DEFAULT_CODE)
            .symbol(DEFAULT_SYMBOL)
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
        cUnitOfMeasure.setAdOrganization(aDOrganization);
        return cUnitOfMeasure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CUnitOfMeasure createUpdatedEntity(EntityManager em) {
        CUnitOfMeasure cUnitOfMeasure = new CUnitOfMeasure()
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
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
        cUnitOfMeasure.setAdOrganization(aDOrganization);
        return cUnitOfMeasure;
    }

    @BeforeEach
    public void initTest() {
        cUnitOfMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createCUnitOfMeasure() throws Exception {
        int databaseSizeBeforeCreate = cUnitOfMeasureRepository.findAll().size();

        // Create the CUnitOfMeasure
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);
        restCUnitOfMeasureMockMvc.perform(post("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isCreated());

        // Validate the CUnitOfMeasure in the database
        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        CUnitOfMeasure testCUnitOfMeasure = cUnitOfMeasureList.get(cUnitOfMeasureList.size() - 1);
        assertThat(testCUnitOfMeasure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCUnitOfMeasure.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCUnitOfMeasure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCUnitOfMeasure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCUnitOfMeasure.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCUnitOfMeasure.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCUnitOfMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cUnitOfMeasureRepository.findAll().size();

        // Create the CUnitOfMeasure with an existing ID
        cUnitOfMeasure.setId(1L);
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCUnitOfMeasureMockMvc.perform(post("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CUnitOfMeasure in the database
        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUnitOfMeasureRepository.findAll().size();
        // set the field null
        cUnitOfMeasure.setCode(null);

        // Create the CUnitOfMeasure, which fails.
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);

        restCUnitOfMeasureMockMvc.perform(post("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUnitOfMeasureRepository.findAll().size();
        // set the field null
        cUnitOfMeasure.setSymbol(null);

        // Create the CUnitOfMeasure, which fails.
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);

        restCUnitOfMeasureMockMvc.perform(post("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUnitOfMeasureRepository.findAll().size();
        // set the field null
        cUnitOfMeasure.setName(null);

        // Create the CUnitOfMeasure, which fails.
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);

        restCUnitOfMeasureMockMvc.perform(post("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasures() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cUnitOfMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCUnitOfMeasure() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get the cUnitOfMeasure
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures/{id}", cUnitOfMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cUnitOfMeasure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCUnitOfMeasuresByIdFiltering() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        Long id = cUnitOfMeasure.getId();

        defaultCUnitOfMeasureShouldBeFound("id.equals=" + id);
        defaultCUnitOfMeasureShouldNotBeFound("id.notEquals=" + id);

        defaultCUnitOfMeasureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCUnitOfMeasureShouldNotBeFound("id.greaterThan=" + id);

        defaultCUnitOfMeasureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCUnitOfMeasureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code equals to DEFAULT_CODE
        defaultCUnitOfMeasureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cUnitOfMeasureList where code equals to UPDATED_CODE
        defaultCUnitOfMeasureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code not equals to DEFAULT_CODE
        defaultCUnitOfMeasureShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cUnitOfMeasureList where code not equals to UPDATED_CODE
        defaultCUnitOfMeasureShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCUnitOfMeasureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cUnitOfMeasureList where code equals to UPDATED_CODE
        defaultCUnitOfMeasureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code is not null
        defaultCUnitOfMeasureShouldBeFound("code.specified=true");

        // Get all the cUnitOfMeasureList where code is null
        defaultCUnitOfMeasureShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code contains DEFAULT_CODE
        defaultCUnitOfMeasureShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cUnitOfMeasureList where code contains UPDATED_CODE
        defaultCUnitOfMeasureShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where code does not contain DEFAULT_CODE
        defaultCUnitOfMeasureShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cUnitOfMeasureList where code does not contain UPDATED_CODE
        defaultCUnitOfMeasureShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol equals to DEFAULT_SYMBOL
        defaultCUnitOfMeasureShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the cUnitOfMeasureList where symbol equals to UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol not equals to DEFAULT_SYMBOL
        defaultCUnitOfMeasureShouldNotBeFound("symbol.notEquals=" + DEFAULT_SYMBOL);

        // Get all the cUnitOfMeasureList where symbol not equals to UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldBeFound("symbol.notEquals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the cUnitOfMeasureList where symbol equals to UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol is not null
        defaultCUnitOfMeasureShouldBeFound("symbol.specified=true");

        // Get all the cUnitOfMeasureList where symbol is null
        defaultCUnitOfMeasureShouldNotBeFound("symbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol contains DEFAULT_SYMBOL
        defaultCUnitOfMeasureShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the cUnitOfMeasureList where symbol contains UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where symbol does not contain DEFAULT_SYMBOL
        defaultCUnitOfMeasureShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the cUnitOfMeasureList where symbol does not contain UPDATED_SYMBOL
        defaultCUnitOfMeasureShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }


    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name equals to DEFAULT_NAME
        defaultCUnitOfMeasureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cUnitOfMeasureList where name equals to UPDATED_NAME
        defaultCUnitOfMeasureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name not equals to DEFAULT_NAME
        defaultCUnitOfMeasureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cUnitOfMeasureList where name not equals to UPDATED_NAME
        defaultCUnitOfMeasureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCUnitOfMeasureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cUnitOfMeasureList where name equals to UPDATED_NAME
        defaultCUnitOfMeasureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name is not null
        defaultCUnitOfMeasureShouldBeFound("name.specified=true");

        // Get all the cUnitOfMeasureList where name is null
        defaultCUnitOfMeasureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name contains DEFAULT_NAME
        defaultCUnitOfMeasureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cUnitOfMeasureList where name contains UPDATED_NAME
        defaultCUnitOfMeasureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where name does not contain DEFAULT_NAME
        defaultCUnitOfMeasureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cUnitOfMeasureList where name does not contain UPDATED_NAME
        defaultCUnitOfMeasureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description equals to DEFAULT_DESCRIPTION
        defaultCUnitOfMeasureShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cUnitOfMeasureList where description equals to UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description not equals to DEFAULT_DESCRIPTION
        defaultCUnitOfMeasureShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cUnitOfMeasureList where description not equals to UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cUnitOfMeasureList where description equals to UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description is not null
        defaultCUnitOfMeasureShouldBeFound("description.specified=true");

        // Get all the cUnitOfMeasureList where description is null
        defaultCUnitOfMeasureShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description contains DEFAULT_DESCRIPTION
        defaultCUnitOfMeasureShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cUnitOfMeasureList where description contains UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where description does not contain DEFAULT_DESCRIPTION
        defaultCUnitOfMeasureShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cUnitOfMeasureList where description does not contain UPDATED_DESCRIPTION
        defaultCUnitOfMeasureShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where uid equals to DEFAULT_UID
        defaultCUnitOfMeasureShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cUnitOfMeasureList where uid equals to UPDATED_UID
        defaultCUnitOfMeasureShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where uid not equals to DEFAULT_UID
        defaultCUnitOfMeasureShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cUnitOfMeasureList where uid not equals to UPDATED_UID
        defaultCUnitOfMeasureShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where uid in DEFAULT_UID or UPDATED_UID
        defaultCUnitOfMeasureShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cUnitOfMeasureList where uid equals to UPDATED_UID
        defaultCUnitOfMeasureShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where uid is not null
        defaultCUnitOfMeasureShouldBeFound("uid.specified=true");

        // Get all the cUnitOfMeasureList where uid is null
        defaultCUnitOfMeasureShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where active equals to DEFAULT_ACTIVE
        defaultCUnitOfMeasureShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cUnitOfMeasureList where active equals to UPDATED_ACTIVE
        defaultCUnitOfMeasureShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where active not equals to DEFAULT_ACTIVE
        defaultCUnitOfMeasureShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cUnitOfMeasureList where active not equals to UPDATED_ACTIVE
        defaultCUnitOfMeasureShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCUnitOfMeasureShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cUnitOfMeasureList where active equals to UPDATED_ACTIVE
        defaultCUnitOfMeasureShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        // Get all the cUnitOfMeasureList where active is not null
        defaultCUnitOfMeasureShouldBeFound("active.specified=true");

        // Get all the cUnitOfMeasureList where active is null
        defaultCUnitOfMeasureShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCUnitOfMeasuresByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cUnitOfMeasure.getAdOrganization();
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cUnitOfMeasureList where adOrganization equals to adOrganizationId
        defaultCUnitOfMeasureShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cUnitOfMeasureList where adOrganization equals to adOrganizationId + 1
        defaultCUnitOfMeasureShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCUnitOfMeasureShouldBeFound(String filter) throws Exception {
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cUnitOfMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCUnitOfMeasureShouldNotBeFound(String filter) throws Exception {
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCUnitOfMeasure() throws Exception {
        // Get the cUnitOfMeasure
        restCUnitOfMeasureMockMvc.perform(get("/api/c-unit-of-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCUnitOfMeasure() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        int databaseSizeBeforeUpdate = cUnitOfMeasureRepository.findAll().size();

        // Update the cUnitOfMeasure
        CUnitOfMeasure updatedCUnitOfMeasure = cUnitOfMeasureRepository.findById(cUnitOfMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedCUnitOfMeasure are not directly saved in db
        em.detach(updatedCUnitOfMeasure);
        updatedCUnitOfMeasure
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(updatedCUnitOfMeasure);

        restCUnitOfMeasureMockMvc.perform(put("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isOk());

        // Validate the CUnitOfMeasure in the database
        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
        CUnitOfMeasure testCUnitOfMeasure = cUnitOfMeasureList.get(cUnitOfMeasureList.size() - 1);
        assertThat(testCUnitOfMeasure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCUnitOfMeasure.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCUnitOfMeasure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCUnitOfMeasure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCUnitOfMeasure.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCUnitOfMeasure.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCUnitOfMeasure() throws Exception {
        int databaseSizeBeforeUpdate = cUnitOfMeasureRepository.findAll().size();

        // Create the CUnitOfMeasure
        CUnitOfMeasureDTO cUnitOfMeasureDTO = cUnitOfMeasureMapper.toDto(cUnitOfMeasure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCUnitOfMeasureMockMvc.perform(put("/api/c-unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cUnitOfMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CUnitOfMeasure in the database
        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCUnitOfMeasure() throws Exception {
        // Initialize the database
        cUnitOfMeasureRepository.saveAndFlush(cUnitOfMeasure);

        int databaseSizeBeforeDelete = cUnitOfMeasureRepository.findAll().size();

        // Delete the cUnitOfMeasure
        restCUnitOfMeasureMockMvc.perform(delete("/api/c-unit-of-measures/{id}", cUnitOfMeasure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CUnitOfMeasure> cUnitOfMeasureList = cUnitOfMeasureRepository.findAll();
        assertThat(cUnitOfMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
