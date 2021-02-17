package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.PaDashboard;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.PaDashboardRepository;
import com.bhp.opusb.service.PaDashboardService;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.mapper.PaDashboardMapper;
import com.bhp.opusb.service.dto.PaDashboardCriteria;
import com.bhp.opusb.service.PaDashboardQueryService;

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
 * Integration tests for the {@link PaDashboardResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PaDashboardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_COLUMNS = 1;
    private static final Integer UPDATED_MIN_COLUMNS = 2;
    private static final Integer SMALLER_MIN_COLUMNS = 1 - 1;

    private static final Integer DEFAULT_MAX_COLUMNS = 1;
    private static final Integer UPDATED_MAX_COLUMNS = 2;
    private static final Integer SMALLER_MAX_COLUMNS = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PaDashboardRepository paDashboardRepository;

    @Autowired
    private PaDashboardMapper paDashboardMapper;

    @Autowired
    private PaDashboardService paDashboardService;

    @Autowired
    private PaDashboardQueryService paDashboardQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaDashboardMockMvc;

    private PaDashboard paDashboard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboard createEntity(EntityManager em) {
        PaDashboard paDashboard = new PaDashboard()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .minColumns(DEFAULT_MIN_COLUMNS)
            .maxColumns(DEFAULT_MAX_COLUMNS)
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
        paDashboard.setAdOrganization(aDOrganization);
        return paDashboard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboard createUpdatedEntity(EntityManager em) {
        PaDashboard paDashboard = new PaDashboard()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .minColumns(UPDATED_MIN_COLUMNS)
            .maxColumns(UPDATED_MAX_COLUMNS)
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
        paDashboard.setAdOrganization(aDOrganization);
        return paDashboard;
    }

    @BeforeEach
    public void initTest() {
        paDashboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaDashboard() throws Exception {
        int databaseSizeBeforeCreate = paDashboardRepository.findAll().size();

        // Create the PaDashboard
        PaDashboardDTO paDashboardDTO = paDashboardMapper.toDto(paDashboard);
        restPaDashboardMockMvc.perform(post("/api/pa-dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the PaDashboard in the database
        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeCreate + 1);
        PaDashboard testPaDashboard = paDashboardList.get(paDashboardList.size() - 1);
        assertThat(testPaDashboard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaDashboard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaDashboard.getMinColumns()).isEqualTo(DEFAULT_MIN_COLUMNS);
        assertThat(testPaDashboard.getMaxColumns()).isEqualTo(DEFAULT_MAX_COLUMNS);
        assertThat(testPaDashboard.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testPaDashboard.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPaDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paDashboardRepository.findAll().size();

        // Create the PaDashboard with an existing ID
        paDashboard.setId(1L);
        PaDashboardDTO paDashboardDTO = paDashboardMapper.toDto(paDashboard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaDashboardMockMvc.perform(post("/api/pa-dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboard in the database
        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardRepository.findAll().size();
        // set the field null
        paDashboard.setName(null);

        // Create the PaDashboard, which fails.
        PaDashboardDTO paDashboardDTO = paDashboardMapper.toDto(paDashboard);

        restPaDashboardMockMvc.perform(post("/api/pa-dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaDashboards() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minColumns").value(hasItem(DEFAULT_MIN_COLUMNS)))
            .andExpect(jsonPath("$.[*].maxColumns").value(hasItem(DEFAULT_MAX_COLUMNS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPaDashboard() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get the paDashboard
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards/{id}", paDashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paDashboard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.minColumns").value(DEFAULT_MIN_COLUMNS))
            .andExpect(jsonPath("$.maxColumns").value(DEFAULT_MAX_COLUMNS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getPaDashboardsByIdFiltering() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        Long id = paDashboard.getId();

        defaultPaDashboardShouldBeFound("id.equals=" + id);
        defaultPaDashboardShouldNotBeFound("id.notEquals=" + id);

        defaultPaDashboardShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaDashboardShouldNotBeFound("id.greaterThan=" + id);

        defaultPaDashboardShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaDashboardShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaDashboardsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name equals to DEFAULT_NAME
        defaultPaDashboardShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paDashboardList where name equals to UPDATED_NAME
        defaultPaDashboardShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name not equals to DEFAULT_NAME
        defaultPaDashboardShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the paDashboardList where name not equals to UPDATED_NAME
        defaultPaDashboardShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPaDashboardShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paDashboardList where name equals to UPDATED_NAME
        defaultPaDashboardShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name is not null
        defaultPaDashboardShouldBeFound("name.specified=true");

        // Get all the paDashboardList where name is null
        defaultPaDashboardShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaDashboardsByNameContainsSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name contains DEFAULT_NAME
        defaultPaDashboardShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the paDashboardList where name contains UPDATED_NAME
        defaultPaDashboardShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where name does not contain DEFAULT_NAME
        defaultPaDashboardShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the paDashboardList where name does not contain UPDATED_NAME
        defaultPaDashboardShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description equals to DEFAULT_DESCRIPTION
        defaultPaDashboardShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardList where description equals to UPDATED_DESCRIPTION
        defaultPaDashboardShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description not equals to DEFAULT_DESCRIPTION
        defaultPaDashboardShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardList where description not equals to UPDATED_DESCRIPTION
        defaultPaDashboardShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaDashboardShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paDashboardList where description equals to UPDATED_DESCRIPTION
        defaultPaDashboardShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description is not null
        defaultPaDashboardShouldBeFound("description.specified=true");

        // Get all the paDashboardList where description is null
        defaultPaDashboardShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description contains DEFAULT_DESCRIPTION
        defaultPaDashboardShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardList where description contains UPDATED_DESCRIPTION
        defaultPaDashboardShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where description does not contain DEFAULT_DESCRIPTION
        defaultPaDashboardShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardList where description does not contain UPDATED_DESCRIPTION
        defaultPaDashboardShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns equals to DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.equals=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns equals to UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.equals=" + UPDATED_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns not equals to DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.notEquals=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns not equals to UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.notEquals=" + UPDATED_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns in DEFAULT_MIN_COLUMNS or UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.in=" + DEFAULT_MIN_COLUMNS + "," + UPDATED_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns equals to UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.in=" + UPDATED_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns is not null
        defaultPaDashboardShouldBeFound("minColumns.specified=true");

        // Get all the paDashboardList where minColumns is null
        defaultPaDashboardShouldNotBeFound("minColumns.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns is greater than or equal to DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.greaterThanOrEqual=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns is greater than or equal to UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.greaterThanOrEqual=" + UPDATED_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns is less than or equal to DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.lessThanOrEqual=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns is less than or equal to SMALLER_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.lessThanOrEqual=" + SMALLER_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns is less than DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.lessThan=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns is less than UPDATED_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.lessThan=" + UPDATED_MIN_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMinColumnsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where minColumns is greater than DEFAULT_MIN_COLUMNS
        defaultPaDashboardShouldNotBeFound("minColumns.greaterThan=" + DEFAULT_MIN_COLUMNS);

        // Get all the paDashboardList where minColumns is greater than SMALLER_MIN_COLUMNS
        defaultPaDashboardShouldBeFound("minColumns.greaterThan=" + SMALLER_MIN_COLUMNS);
    }


    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns equals to DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.equals=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns equals to UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.equals=" + UPDATED_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns not equals to DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.notEquals=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns not equals to UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.notEquals=" + UPDATED_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns in DEFAULT_MAX_COLUMNS or UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.in=" + DEFAULT_MAX_COLUMNS + "," + UPDATED_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns equals to UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.in=" + UPDATED_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns is not null
        defaultPaDashboardShouldBeFound("maxColumns.specified=true");

        // Get all the paDashboardList where maxColumns is null
        defaultPaDashboardShouldNotBeFound("maxColumns.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns is greater than or equal to DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.greaterThanOrEqual=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns is greater than or equal to UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.greaterThanOrEqual=" + UPDATED_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns is less than or equal to DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.lessThanOrEqual=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns is less than or equal to SMALLER_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.lessThanOrEqual=" + SMALLER_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns is less than DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.lessThan=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns is less than UPDATED_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.lessThan=" + UPDATED_MAX_COLUMNS);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByMaxColumnsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where maxColumns is greater than DEFAULT_MAX_COLUMNS
        defaultPaDashboardShouldNotBeFound("maxColumns.greaterThan=" + DEFAULT_MAX_COLUMNS);

        // Get all the paDashboardList where maxColumns is greater than SMALLER_MAX_COLUMNS
        defaultPaDashboardShouldBeFound("maxColumns.greaterThan=" + SMALLER_MAX_COLUMNS);
    }


    @Test
    @Transactional
    public void getAllPaDashboardsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where uid equals to DEFAULT_UID
        defaultPaDashboardShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the paDashboardList where uid equals to UPDATED_UID
        defaultPaDashboardShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where uid not equals to DEFAULT_UID
        defaultPaDashboardShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the paDashboardList where uid not equals to UPDATED_UID
        defaultPaDashboardShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where uid in DEFAULT_UID or UPDATED_UID
        defaultPaDashboardShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the paDashboardList where uid equals to UPDATED_UID
        defaultPaDashboardShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where uid is not null
        defaultPaDashboardShouldBeFound("uid.specified=true");

        // Get all the paDashboardList where uid is null
        defaultPaDashboardShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where active equals to DEFAULT_ACTIVE
        defaultPaDashboardShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardList where active equals to UPDATED_ACTIVE
        defaultPaDashboardShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where active not equals to DEFAULT_ACTIVE
        defaultPaDashboardShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardList where active not equals to UPDATED_ACTIVE
        defaultPaDashboardShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPaDashboardShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the paDashboardList where active equals to UPDATED_ACTIVE
        defaultPaDashboardShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        // Get all the paDashboardList where active is not null
        defaultPaDashboardShouldBeFound("active.specified=true");

        // Get all the paDashboardList where active is null
        defaultPaDashboardShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = paDashboard.getAdOrganization();
        paDashboardRepository.saveAndFlush(paDashboard);
        Long adOrganizationId = adOrganization.getId();

        // Get all the paDashboardList where adOrganization equals to adOrganizationId
        defaultPaDashboardShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the paDashboardList where adOrganization equals to adOrganizationId + 1
        defaultPaDashboardShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaDashboardShouldBeFound(String filter) throws Exception {
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minColumns").value(hasItem(DEFAULT_MIN_COLUMNS)))
            .andExpect(jsonPath("$.[*].maxColumns").value(hasItem(DEFAULT_MAX_COLUMNS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaDashboardShouldNotBeFound(String filter) throws Exception {
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaDashboard() throws Exception {
        // Get the paDashboard
        restPaDashboardMockMvc.perform(get("/api/pa-dashboards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaDashboard() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        int databaseSizeBeforeUpdate = paDashboardRepository.findAll().size();

        // Update the paDashboard
        PaDashboard updatedPaDashboard = paDashboardRepository.findById(paDashboard.getId()).get();
        // Disconnect from session so that the updates on updatedPaDashboard are not directly saved in db
        em.detach(updatedPaDashboard);
        updatedPaDashboard
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .minColumns(UPDATED_MIN_COLUMNS)
            .maxColumns(UPDATED_MAX_COLUMNS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        PaDashboardDTO paDashboardDTO = paDashboardMapper.toDto(updatedPaDashboard);

        restPaDashboardMockMvc.perform(put("/api/pa-dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardDTO)))
            .andExpect(status().isOk());

        // Validate the PaDashboard in the database
        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeUpdate);
        PaDashboard testPaDashboard = paDashboardList.get(paDashboardList.size() - 1);
        assertThat(testPaDashboard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaDashboard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaDashboard.getMinColumns()).isEqualTo(UPDATED_MIN_COLUMNS);
        assertThat(testPaDashboard.getMaxColumns()).isEqualTo(UPDATED_MAX_COLUMNS);
        assertThat(testPaDashboard.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testPaDashboard.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaDashboard() throws Exception {
        int databaseSizeBeforeUpdate = paDashboardRepository.findAll().size();

        // Create the PaDashboard
        PaDashboardDTO paDashboardDTO = paDashboardMapper.toDto(paDashboard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaDashboardMockMvc.perform(put("/api/pa-dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboard in the database
        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaDashboard() throws Exception {
        // Initialize the database
        paDashboardRepository.saveAndFlush(paDashboard);

        int databaseSizeBeforeDelete = paDashboardRepository.findAll().size();

        // Delete the paDashboard
        restPaDashboardMockMvc.perform(delete("/api/pa-dashboards/{id}", paDashboard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaDashboard> paDashboardList = paDashboardRepository.findAll();
        assertThat(paDashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
