package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CNonBusinessDay;
import com.bhp.opusb.domain.CCalendar;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CNonBusinessDayRepository;
import com.bhp.opusb.service.CNonBusinessDayService;
import com.bhp.opusb.service.dto.CNonBusinessDayDTO;
import com.bhp.opusb.service.mapper.CNonBusinessDayMapper;
import com.bhp.opusb.service.dto.CNonBusinessDayCriteria;
import com.bhp.opusb.service.CNonBusinessDayQueryService;

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
 * Integration tests for the {@link CNonBusinessDayResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CNonBusinessDayResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CNonBusinessDayRepository cNonBusinessDayRepository;

    @Autowired
    private CNonBusinessDayMapper cNonBusinessDayMapper;

    @Autowired
    private CNonBusinessDayService cNonBusinessDayService;

    @Autowired
    private CNonBusinessDayQueryService cNonBusinessDayQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCNonBusinessDayMockMvc;

    private CNonBusinessDay cNonBusinessDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CNonBusinessDay createEntity(EntityManager em) {
        CNonBusinessDay cNonBusinessDay = new CNonBusinessDay()
            .category(DEFAULT_CATEGORY)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
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
        cNonBusinessDay.setCalendar(cCalendar);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cNonBusinessDay.setAdOrganization(aDOrganization);
        return cNonBusinessDay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CNonBusinessDay createUpdatedEntity(EntityManager em) {
        CNonBusinessDay cNonBusinessDay = new CNonBusinessDay()
            .category(UPDATED_CATEGORY)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
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
        cNonBusinessDay.setCalendar(cCalendar);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cNonBusinessDay.setAdOrganization(aDOrganization);
        return cNonBusinessDay;
    }

    @BeforeEach
    public void initTest() {
        cNonBusinessDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createCNonBusinessDay() throws Exception {
        int databaseSizeBeforeCreate = cNonBusinessDayRepository.findAll().size();

        // Create the CNonBusinessDay
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(cNonBusinessDay);
        restCNonBusinessDayMockMvc.perform(post("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isCreated());

        // Validate the CNonBusinessDay in the database
        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeCreate + 1);
        CNonBusinessDay testCNonBusinessDay = cNonBusinessDayList.get(cNonBusinessDayList.size() - 1);
        assertThat(testCNonBusinessDay.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCNonBusinessDay.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCNonBusinessDay.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCNonBusinessDay.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCNonBusinessDay.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCNonBusinessDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cNonBusinessDayRepository.findAll().size();

        // Create the CNonBusinessDay with an existing ID
        cNonBusinessDay.setId(1L);
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(cNonBusinessDay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCNonBusinessDayMockMvc.perform(post("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CNonBusinessDay in the database
        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNonBusinessDayRepository.findAll().size();
        // set the field null
        cNonBusinessDay.setCategory(null);

        // Create the CNonBusinessDay, which fails.
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(cNonBusinessDay);

        restCNonBusinessDayMockMvc.perform(post("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isBadRequest());

        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNonBusinessDayRepository.findAll().size();
        // set the field null
        cNonBusinessDay.setDate(null);

        // Create the CNonBusinessDay, which fails.
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(cNonBusinessDay);

        restCNonBusinessDayMockMvc.perform(post("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isBadRequest());

        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDays() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cNonBusinessDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCNonBusinessDay() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get the cNonBusinessDay
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days/{id}", cNonBusinessDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cNonBusinessDay.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCNonBusinessDaysByIdFiltering() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        Long id = cNonBusinessDay.getId();

        defaultCNonBusinessDayShouldBeFound("id.equals=" + id);
        defaultCNonBusinessDayShouldNotBeFound("id.notEquals=" + id);

        defaultCNonBusinessDayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCNonBusinessDayShouldNotBeFound("id.greaterThan=" + id);

        defaultCNonBusinessDayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCNonBusinessDayShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category equals to DEFAULT_CATEGORY
        defaultCNonBusinessDayShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the cNonBusinessDayList where category equals to UPDATED_CATEGORY
        defaultCNonBusinessDayShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category not equals to DEFAULT_CATEGORY
        defaultCNonBusinessDayShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the cNonBusinessDayList where category not equals to UPDATED_CATEGORY
        defaultCNonBusinessDayShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultCNonBusinessDayShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the cNonBusinessDayList where category equals to UPDATED_CATEGORY
        defaultCNonBusinessDayShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category is not null
        defaultCNonBusinessDayShouldBeFound("category.specified=true");

        // Get all the cNonBusinessDayList where category is null
        defaultCNonBusinessDayShouldNotBeFound("category.specified=false");
    }
                @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryContainsSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category contains DEFAULT_CATEGORY
        defaultCNonBusinessDayShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the cNonBusinessDayList where category contains UPDATED_CATEGORY
        defaultCNonBusinessDayShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where category does not contain DEFAULT_CATEGORY
        defaultCNonBusinessDayShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the cNonBusinessDayList where category does not contain UPDATED_CATEGORY
        defaultCNonBusinessDayShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }


    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date equals to DEFAULT_DATE
        defaultCNonBusinessDayShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date equals to UPDATED_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date not equals to DEFAULT_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date not equals to UPDATED_DATE
        defaultCNonBusinessDayShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsInShouldWork() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCNonBusinessDayShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the cNonBusinessDayList where date equals to UPDATED_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date is not null
        defaultCNonBusinessDayShouldBeFound("date.specified=true");

        // Get all the cNonBusinessDayList where date is null
        defaultCNonBusinessDayShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date is greater than or equal to DEFAULT_DATE
        defaultCNonBusinessDayShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date is greater than or equal to UPDATED_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date is less than or equal to DEFAULT_DATE
        defaultCNonBusinessDayShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date is less than or equal to SMALLER_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date is less than DEFAULT_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date is less than UPDATED_DATE
        defaultCNonBusinessDayShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where date is greater than DEFAULT_DATE
        defaultCNonBusinessDayShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the cNonBusinessDayList where date is greater than SMALLER_DATE
        defaultCNonBusinessDayShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description equals to DEFAULT_DESCRIPTION
        defaultCNonBusinessDayShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cNonBusinessDayList where description equals to UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description not equals to DEFAULT_DESCRIPTION
        defaultCNonBusinessDayShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cNonBusinessDayList where description not equals to UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cNonBusinessDayList where description equals to UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description is not null
        defaultCNonBusinessDayShouldBeFound("description.specified=true");

        // Get all the cNonBusinessDayList where description is null
        defaultCNonBusinessDayShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description contains DEFAULT_DESCRIPTION
        defaultCNonBusinessDayShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cNonBusinessDayList where description contains UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where description does not contain DEFAULT_DESCRIPTION
        defaultCNonBusinessDayShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cNonBusinessDayList where description does not contain UPDATED_DESCRIPTION
        defaultCNonBusinessDayShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCNonBusinessDaysByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where uid equals to DEFAULT_UID
        defaultCNonBusinessDayShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cNonBusinessDayList where uid equals to UPDATED_UID
        defaultCNonBusinessDayShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where uid not equals to DEFAULT_UID
        defaultCNonBusinessDayShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cNonBusinessDayList where uid not equals to UPDATED_UID
        defaultCNonBusinessDayShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where uid in DEFAULT_UID or UPDATED_UID
        defaultCNonBusinessDayShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cNonBusinessDayList where uid equals to UPDATED_UID
        defaultCNonBusinessDayShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where uid is not null
        defaultCNonBusinessDayShouldBeFound("uid.specified=true");

        // Get all the cNonBusinessDayList where uid is null
        defaultCNonBusinessDayShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where active equals to DEFAULT_ACTIVE
        defaultCNonBusinessDayShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cNonBusinessDayList where active equals to UPDATED_ACTIVE
        defaultCNonBusinessDayShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where active not equals to DEFAULT_ACTIVE
        defaultCNonBusinessDayShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cNonBusinessDayList where active not equals to UPDATED_ACTIVE
        defaultCNonBusinessDayShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCNonBusinessDayShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cNonBusinessDayList where active equals to UPDATED_ACTIVE
        defaultCNonBusinessDayShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        // Get all the cNonBusinessDayList where active is not null
        defaultCNonBusinessDayShouldBeFound("active.specified=true");

        // Get all the cNonBusinessDayList where active is null
        defaultCNonBusinessDayShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCNonBusinessDaysByCalendarIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCalendar calendar = cNonBusinessDay.getCalendar();
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);
        Long calendarId = calendar.getId();

        // Get all the cNonBusinessDayList where calendar equals to calendarId
        defaultCNonBusinessDayShouldBeFound("calendarId.equals=" + calendarId);

        // Get all the cNonBusinessDayList where calendar equals to calendarId + 1
        defaultCNonBusinessDayShouldNotBeFound("calendarId.equals=" + (calendarId + 1));
    }


    @Test
    @Transactional
    public void getAllCNonBusinessDaysByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cNonBusinessDay.getAdOrganization();
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cNonBusinessDayList where adOrganization equals to adOrganizationId
        defaultCNonBusinessDayShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cNonBusinessDayList where adOrganization equals to adOrganizationId + 1
        defaultCNonBusinessDayShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCNonBusinessDayShouldBeFound(String filter) throws Exception {
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cNonBusinessDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCNonBusinessDayShouldNotBeFound(String filter) throws Exception {
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCNonBusinessDay() throws Exception {
        // Get the cNonBusinessDay
        restCNonBusinessDayMockMvc.perform(get("/api/c-non-business-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCNonBusinessDay() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        int databaseSizeBeforeUpdate = cNonBusinessDayRepository.findAll().size();

        // Update the cNonBusinessDay
        CNonBusinessDay updatedCNonBusinessDay = cNonBusinessDayRepository.findById(cNonBusinessDay.getId()).get();
        // Disconnect from session so that the updates on updatedCNonBusinessDay are not directly saved in db
        em.detach(updatedCNonBusinessDay);
        updatedCNonBusinessDay
            .category(UPDATED_CATEGORY)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(updatedCNonBusinessDay);

        restCNonBusinessDayMockMvc.perform(put("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isOk());

        // Validate the CNonBusinessDay in the database
        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeUpdate);
        CNonBusinessDay testCNonBusinessDay = cNonBusinessDayList.get(cNonBusinessDayList.size() - 1);
        assertThat(testCNonBusinessDay.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCNonBusinessDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCNonBusinessDay.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCNonBusinessDay.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCNonBusinessDay.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCNonBusinessDay() throws Exception {
        int databaseSizeBeforeUpdate = cNonBusinessDayRepository.findAll().size();

        // Create the CNonBusinessDay
        CNonBusinessDayDTO cNonBusinessDayDTO = cNonBusinessDayMapper.toDto(cNonBusinessDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCNonBusinessDayMockMvc.perform(put("/api/c-non-business-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cNonBusinessDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CNonBusinessDay in the database
        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCNonBusinessDay() throws Exception {
        // Initialize the database
        cNonBusinessDayRepository.saveAndFlush(cNonBusinessDay);

        int databaseSizeBeforeDelete = cNonBusinessDayRepository.findAll().size();

        // Delete the cNonBusinessDay
        restCNonBusinessDayMockMvc.perform(delete("/api/c-non-business-days/{id}", cNonBusinessDay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CNonBusinessDay> cNonBusinessDayList = cNonBusinessDayRepository.findAll();
        assertThat(cNonBusinessDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
