package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPeriod;
import com.bhp.opusb.domain.CCalendar;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPeriodRepository;
import com.bhp.opusb.service.CPeriodService;
import com.bhp.opusb.service.dto.CPeriodDTO;
import com.bhp.opusb.service.mapper.CPeriodMapper;
import com.bhp.opusb.service.dto.CPeriodCriteria;
import com.bhp.opusb.service.CPeriodQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CPeriodResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPeriodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPeriodRepository cPeriodRepository;

    @Autowired
    private CPeriodMapper cPeriodMapper;

    @Autowired
    private CPeriodService cPeriodService;

    @Autowired
    private CPeriodQueryService cPeriodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPeriodMockMvc;

    private CPeriod cPeriod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPeriod createEntity(EntityManager em) {
        CPeriod cPeriod = new CPeriod()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CCalendar cCalendar;
        if (TestUtil.findAll(em, CCalendar.class).isEmpty()) {
            cCalendar = CCalendarResourceIT.createEntity(em);
            em.persist(cCalendar);
            em.flush();
        } else {
            cCalendar = TestUtil.findAll(em, CCalendar.class).get(0);
        }
        cPeriod.setCalendar(cCalendar);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPeriod.setAdOrganization(aDOrganization);
        return cPeriod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPeriod createUpdatedEntity(EntityManager em) {
        CPeriod cPeriod = new CPeriod()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CCalendar cCalendar;
        if (TestUtil.findAll(em, CCalendar.class).isEmpty()) {
            cCalendar = CCalendarResourceIT.createUpdatedEntity(em);
            em.persist(cCalendar);
            em.flush();
        } else {
            cCalendar = TestUtil.findAll(em, CCalendar.class).get(0);
        }
        cPeriod.setCalendar(cCalendar);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPeriod.setAdOrganization(aDOrganization);
        return cPeriod;
    }

    @BeforeEach
    public void initTest() {
        cPeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPeriod() throws Exception {
        int databaseSizeBeforeCreate = cPeriodRepository.findAll().size();

        // Create the CPeriod
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);
        restCPeriodMockMvc.perform(post("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isCreated());

        // Validate the CPeriod in the database
        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeCreate + 1);
        CPeriod testCPeriod = cPeriodList.get(cPeriodList.size() - 1);
        assertThat(testCPeriod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCPeriod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCPeriod.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCPeriod.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCPeriod.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPeriod.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPeriodRepository.findAll().size();

        // Create the CPeriod with an existing ID
        cPeriod.setId(1L);
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPeriodMockMvc.perform(post("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPeriod in the database
        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPeriodRepository.findAll().size();
        // set the field null
        cPeriod.setName(null);

        // Create the CPeriod, which fails.
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);

        restCPeriodMockMvc.perform(post("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPeriodRepository.findAll().size();
        // set the field null
        cPeriod.setStartDate(null);

        // Create the CPeriod, which fails.
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);

        restCPeriodMockMvc.perform(post("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPeriodRepository.findAll().size();
        // set the field null
        cPeriod.setEndDate(null);

        // Create the CPeriod, which fails.
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);

        restCPeriodMockMvc.perform(post("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPeriods() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList
        restCPeriodMockMvc.perform(get("/api/c-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPeriod() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get the cPeriod
        restCPeriodMockMvc.perform(get("/api/c-periods/{id}", cPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPeriod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPeriodsByIdFiltering() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        Long id = cPeriod.getId();

        defaultCPeriodShouldBeFound("id.equals=" + id);
        defaultCPeriodShouldNotBeFound("id.notEquals=" + id);

        defaultCPeriodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPeriodShouldNotBeFound("id.greaterThan=" + id);

        defaultCPeriodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPeriodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPeriodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name equals to DEFAULT_NAME
        defaultCPeriodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cPeriodList where name equals to UPDATED_NAME
        defaultCPeriodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name not equals to DEFAULT_NAME
        defaultCPeriodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cPeriodList where name not equals to UPDATED_NAME
        defaultCPeriodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCPeriodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cPeriodList where name equals to UPDATED_NAME
        defaultCPeriodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name is not null
        defaultCPeriodShouldBeFound("name.specified=true");

        // Get all the cPeriodList where name is null
        defaultCPeriodShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPeriodsByNameContainsSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name contains DEFAULT_NAME
        defaultCPeriodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cPeriodList where name contains UPDATED_NAME
        defaultCPeriodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where name does not contain DEFAULT_NAME
        defaultCPeriodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cPeriodList where name does not contain UPDATED_NAME
        defaultCPeriodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCPeriodsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description equals to DEFAULT_DESCRIPTION
        defaultCPeriodShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cPeriodList where description equals to UPDATED_DESCRIPTION
        defaultCPeriodShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description not equals to DEFAULT_DESCRIPTION
        defaultCPeriodShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cPeriodList where description not equals to UPDATED_DESCRIPTION
        defaultCPeriodShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCPeriodShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cPeriodList where description equals to UPDATED_DESCRIPTION
        defaultCPeriodShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description is not null
        defaultCPeriodShouldBeFound("description.specified=true");

        // Get all the cPeriodList where description is null
        defaultCPeriodShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPeriodsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description contains DEFAULT_DESCRIPTION
        defaultCPeriodShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cPeriodList where description contains UPDATED_DESCRIPTION
        defaultCPeriodShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where description does not contain DEFAULT_DESCRIPTION
        defaultCPeriodShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cPeriodList where description does not contain UPDATED_DESCRIPTION
        defaultCPeriodShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate equals to DEFAULT_START_DATE
        defaultCPeriodShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate equals to UPDATED_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate not equals to DEFAULT_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate not equals to UPDATED_START_DATE
        defaultCPeriodShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCPeriodShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the cPeriodList where startDate equals to UPDATED_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate is not null
        defaultCPeriodShouldBeFound("startDate.specified=true");

        // Get all the cPeriodList where startDate is null
        defaultCPeriodShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultCPeriodShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate is greater than or equal to UPDATED_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate is less than or equal to DEFAULT_START_DATE
        defaultCPeriodShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate is less than or equal to SMALLER_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate is less than DEFAULT_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate is less than UPDATED_START_DATE
        defaultCPeriodShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where startDate is greater than DEFAULT_START_DATE
        defaultCPeriodShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the cPeriodList where startDate is greater than SMALLER_START_DATE
        defaultCPeriodShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate equals to DEFAULT_END_DATE
        defaultCPeriodShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate equals to UPDATED_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate not equals to DEFAULT_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate not equals to UPDATED_END_DATE
        defaultCPeriodShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultCPeriodShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the cPeriodList where endDate equals to UPDATED_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate is not null
        defaultCPeriodShouldBeFound("endDate.specified=true");

        // Get all the cPeriodList where endDate is null
        defaultCPeriodShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultCPeriodShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate is greater than or equal to UPDATED_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate is less than or equal to DEFAULT_END_DATE
        defaultCPeriodShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate is less than or equal to SMALLER_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate is less than DEFAULT_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate is less than UPDATED_END_DATE
        defaultCPeriodShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where endDate is greater than DEFAULT_END_DATE
        defaultCPeriodShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the cPeriodList where endDate is greater than SMALLER_END_DATE
        defaultCPeriodShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllCPeriodsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where uid equals to DEFAULT_UID
        defaultCPeriodShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPeriodList where uid equals to UPDATED_UID
        defaultCPeriodShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where uid not equals to DEFAULT_UID
        defaultCPeriodShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPeriodList where uid not equals to UPDATED_UID
        defaultCPeriodShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPeriodShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPeriodList where uid equals to UPDATED_UID
        defaultCPeriodShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where uid is not null
        defaultCPeriodShouldBeFound("uid.specified=true");

        // Get all the cPeriodList where uid is null
        defaultCPeriodShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPeriodsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where active equals to DEFAULT_ACTIVE
        defaultCPeriodShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPeriodList where active equals to UPDATED_ACTIVE
        defaultCPeriodShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where active not equals to DEFAULT_ACTIVE
        defaultCPeriodShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPeriodList where active not equals to UPDATED_ACTIVE
        defaultCPeriodShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPeriodShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPeriodList where active equals to UPDATED_ACTIVE
        defaultCPeriodShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPeriodsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        // Get all the cPeriodList where active is not null
        defaultCPeriodShouldBeFound("active.specified=true");

        // Get all the cPeriodList where active is null
        defaultCPeriodShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPeriodsByCalendarIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCalendar calendar = cPeriod.getCalendar();
        cPeriodRepository.saveAndFlush(cPeriod);
        Long calendarId = calendar.getId();

        // Get all the cPeriodList where calendar equals to calendarId
        defaultCPeriodShouldBeFound("calendarId.equals=" + calendarId);

        // Get all the cPeriodList where calendar equals to calendarId + 1
        defaultCPeriodShouldNotBeFound("calendarId.equals=" + (calendarId + 1));
    }


    @Test
    @Transactional
    public void getAllCPeriodsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPeriod.getAdOrganization();
        cPeriodRepository.saveAndFlush(cPeriod);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPeriodList where adOrganization equals to adOrganizationId
        defaultCPeriodShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPeriodList where adOrganization equals to adOrganizationId + 1
        defaultCPeriodShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPeriodShouldBeFound(String filter) throws Exception {
        restCPeriodMockMvc.perform(get("/api/c-periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPeriodMockMvc.perform(get("/api/c-periods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPeriodShouldNotBeFound(String filter) throws Exception {
        restCPeriodMockMvc.perform(get("/api/c-periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPeriodMockMvc.perform(get("/api/c-periods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPeriod() throws Exception {
        // Get the cPeriod
        restCPeriodMockMvc.perform(get("/api/c-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPeriod() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        int databaseSizeBeforeUpdate = cPeriodRepository.findAll().size();

        // Update the cPeriod
        CPeriod updatedCPeriod = cPeriodRepository.findById(cPeriod.getId()).get();
        // Disconnect from session so that the updates on updatedCPeriod are not directly saved in db
        em.detach(updatedCPeriod);
        updatedCPeriod
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(updatedCPeriod);

        restCPeriodMockMvc.perform(put("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isOk());

        // Validate the CPeriod in the database
        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeUpdate);
        CPeriod testCPeriod = cPeriodList.get(cPeriodList.size() - 1);
        assertThat(testCPeriod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCPeriod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCPeriod.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCPeriod.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCPeriod.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPeriod.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPeriod() throws Exception {
        int databaseSizeBeforeUpdate = cPeriodRepository.findAll().size();

        // Create the CPeriod
        CPeriodDTO cPeriodDTO = cPeriodMapper.toDto(cPeriod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPeriodMockMvc.perform(put("/api/c-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPeriod in the database
        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPeriod() throws Exception {
        // Initialize the database
        cPeriodRepository.saveAndFlush(cPeriod);

        int databaseSizeBeforeDelete = cPeriodRepository.findAll().size();

        // Delete the cPeriod
        restCPeriodMockMvc.perform(delete("/api/c-periods/{id}", cPeriod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPeriod> cPeriodList = cPeriodRepository.findAll();
        assertThat(cPeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
