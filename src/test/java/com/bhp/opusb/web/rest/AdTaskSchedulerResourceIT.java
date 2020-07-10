package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.repository.AdTaskSchedulerRepository;
import com.bhp.opusb.service.AdTaskSchedulerService;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerMapper;
import com.bhp.opusb.service.dto.AdTaskSchedulerCriteria;
import com.bhp.opusb.service.AdTaskSchedulerQueryService;

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

import com.bhp.opusb.domain.enumeration.AdSchedulerTrigger;
/**
 * Integration tests for the {@link AdTaskSchedulerResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTaskSchedulerResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final AdSchedulerTrigger DEFAULT_TRIGGER = AdSchedulerTrigger.CRON;
    private static final AdSchedulerTrigger UPDATED_TRIGGER = AdSchedulerTrigger.PERIODIC;

    private static final String DEFAULT_CRON_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_CRON_EXPRESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIODIC_COUNT = 1;
    private static final Integer UPDATED_PERIODIC_COUNT = 2;
    private static final Integer SMALLER_PERIODIC_COUNT = 1 - 1;

    private static final String DEFAULT_PERIODIC_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_PERIODIC_UNIT = "BBBBBBBBBB";

    @Autowired
    private AdTaskSchedulerRepository adTaskSchedulerRepository;

    @Autowired
    private AdTaskSchedulerMapper adTaskSchedulerMapper;

    @Autowired
    private AdTaskSchedulerService adTaskSchedulerService;

    @Autowired
    private AdTaskSchedulerQueryService adTaskSchedulerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTaskSchedulerMockMvc;

    private AdTaskScheduler adTaskScheduler;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskScheduler createEntity(EntityManager em) {
        AdTaskScheduler adTaskScheduler = new AdTaskScheduler()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .trigger(DEFAULT_TRIGGER)
            .cronExpression(DEFAULT_CRON_EXPRESSION)
            .periodicCount(DEFAULT_PERIODIC_COUNT)
            .periodicUnit(DEFAULT_PERIODIC_UNIT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskScheduler.setAdOrganization(aDOrganization);
        // Add required entity
        AdTask adTask;
        if (TestUtil.findAll(em, AdTask.class).isEmpty()) {
            adTask = AdTaskResourceIT.createEntity(em);
            em.persist(adTask);
            em.flush();
        } else {
            adTask = TestUtil.findAll(em, AdTask.class).get(0);
        }
        adTaskScheduler.setAdTask(adTask);
        return adTaskScheduler;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskScheduler createUpdatedEntity(EntityManager em) {
        AdTaskScheduler adTaskScheduler = new AdTaskScheduler()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .trigger(UPDATED_TRIGGER)
            .cronExpression(UPDATED_CRON_EXPRESSION)
            .periodicCount(UPDATED_PERIODIC_COUNT)
            .periodicUnit(UPDATED_PERIODIC_UNIT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskScheduler.setAdOrganization(aDOrganization);
        // Add required entity
        AdTask adTask;
        if (TestUtil.findAll(em, AdTask.class).isEmpty()) {
            adTask = AdTaskResourceIT.createUpdatedEntity(em);
            em.persist(adTask);
            em.flush();
        } else {
            adTask = TestUtil.findAll(em, AdTask.class).get(0);
        }
        adTaskScheduler.setAdTask(adTask);
        return adTaskScheduler;
    }

    @BeforeEach
    public void initTest() {
        adTaskScheduler = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTaskScheduler() throws Exception {
        int databaseSizeBeforeCreate = adTaskSchedulerRepository.findAll().size();

        // Create the AdTaskScheduler
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(adTaskScheduler);
        restAdTaskSchedulerMockMvc.perform(post("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTaskScheduler in the database
        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeCreate + 1);
        AdTaskScheduler testAdTaskScheduler = adTaskSchedulerList.get(adTaskSchedulerList.size() - 1);
        assertThat(testAdTaskScheduler.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTaskScheduler.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTaskScheduler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTaskScheduler.getTrigger()).isEqualTo(DEFAULT_TRIGGER);
        assertThat(testAdTaskScheduler.getCronExpression()).isEqualTo(DEFAULT_CRON_EXPRESSION);
        assertThat(testAdTaskScheduler.getPeriodicCount()).isEqualTo(DEFAULT_PERIODIC_COUNT);
        assertThat(testAdTaskScheduler.getPeriodicUnit()).isEqualTo(DEFAULT_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void createAdTaskSchedulerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTaskSchedulerRepository.findAll().size();

        // Create the AdTaskScheduler with an existing ID
        adTaskScheduler.setId(1L);
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(adTaskScheduler);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTaskSchedulerMockMvc.perform(post("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskScheduler in the database
        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskSchedulerRepository.findAll().size();
        // set the field null
        adTaskScheduler.setName(null);

        // Create the AdTaskScheduler, which fails.
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(adTaskScheduler);

        restAdTaskSchedulerMockMvc.perform(post("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTriggerIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskSchedulerRepository.findAll().size();
        // set the field null
        adTaskScheduler.setTrigger(null);

        // Create the AdTaskScheduler, which fails.
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(adTaskScheduler);

        restAdTaskSchedulerMockMvc.perform(post("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isBadRequest());

        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulers() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskScheduler.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].trigger").value(hasItem(DEFAULT_TRIGGER.toString())))
            .andExpect(jsonPath("$.[*].cronExpression").value(hasItem(DEFAULT_CRON_EXPRESSION)))
            .andExpect(jsonPath("$.[*].periodicCount").value(hasItem(DEFAULT_PERIODIC_COUNT)))
            .andExpect(jsonPath("$.[*].periodicUnit").value(hasItem(DEFAULT_PERIODIC_UNIT)));
    }
    
    @Test
    @Transactional
    public void getAdTaskScheduler() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get the adTaskScheduler
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers/{id}", adTaskScheduler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTaskScheduler.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.trigger").value(DEFAULT_TRIGGER.toString()))
            .andExpect(jsonPath("$.cronExpression").value(DEFAULT_CRON_EXPRESSION))
            .andExpect(jsonPath("$.periodicCount").value(DEFAULT_PERIODIC_COUNT))
            .andExpect(jsonPath("$.periodicUnit").value(DEFAULT_PERIODIC_UNIT));
    }


    @Test
    @Transactional
    public void getAdTaskSchedulersByIdFiltering() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        Long id = adTaskScheduler.getId();

        defaultAdTaskSchedulerShouldBeFound("id.equals=" + id);
        defaultAdTaskSchedulerShouldNotBeFound("id.notEquals=" + id);

        defaultAdTaskSchedulerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTaskSchedulerShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTaskSchedulerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTaskSchedulerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where uid equals to DEFAULT_UID
        defaultAdTaskSchedulerShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTaskSchedulerList where uid equals to UPDATED_UID
        defaultAdTaskSchedulerShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where uid not equals to DEFAULT_UID
        defaultAdTaskSchedulerShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTaskSchedulerList where uid not equals to UPDATED_UID
        defaultAdTaskSchedulerShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTaskSchedulerShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTaskSchedulerList where uid equals to UPDATED_UID
        defaultAdTaskSchedulerShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where uid is not null
        defaultAdTaskSchedulerShouldBeFound("uid.specified=true");

        // Get all the adTaskSchedulerList where uid is null
        defaultAdTaskSchedulerShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where active equals to DEFAULT_ACTIVE
        defaultAdTaskSchedulerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTaskSchedulerList where active equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where active not equals to DEFAULT_ACTIVE
        defaultAdTaskSchedulerShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTaskSchedulerList where active not equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTaskSchedulerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTaskSchedulerList where active equals to UPDATED_ACTIVE
        defaultAdTaskSchedulerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where active is not null
        defaultAdTaskSchedulerShouldBeFound("active.specified=true");

        // Get all the adTaskSchedulerList where active is null
        defaultAdTaskSchedulerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name equals to DEFAULT_NAME
        defaultAdTaskSchedulerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerList where name equals to UPDATED_NAME
        defaultAdTaskSchedulerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name not equals to DEFAULT_NAME
        defaultAdTaskSchedulerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerList where name not equals to UPDATED_NAME
        defaultAdTaskSchedulerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTaskSchedulerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTaskSchedulerList where name equals to UPDATED_NAME
        defaultAdTaskSchedulerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name is not null
        defaultAdTaskSchedulerShouldBeFound("name.specified=true");

        // Get all the adTaskSchedulerList where name is null
        defaultAdTaskSchedulerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name contains DEFAULT_NAME
        defaultAdTaskSchedulerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerList where name contains UPDATED_NAME
        defaultAdTaskSchedulerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where name does not contain DEFAULT_NAME
        defaultAdTaskSchedulerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTaskSchedulerList where name does not contain UPDATED_NAME
        defaultAdTaskSchedulerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByTriggerIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where trigger equals to DEFAULT_TRIGGER
        defaultAdTaskSchedulerShouldBeFound("trigger.equals=" + DEFAULT_TRIGGER);

        // Get all the adTaskSchedulerList where trigger equals to UPDATED_TRIGGER
        defaultAdTaskSchedulerShouldNotBeFound("trigger.equals=" + UPDATED_TRIGGER);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByTriggerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where trigger not equals to DEFAULT_TRIGGER
        defaultAdTaskSchedulerShouldNotBeFound("trigger.notEquals=" + DEFAULT_TRIGGER);

        // Get all the adTaskSchedulerList where trigger not equals to UPDATED_TRIGGER
        defaultAdTaskSchedulerShouldBeFound("trigger.notEquals=" + UPDATED_TRIGGER);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByTriggerIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where trigger in DEFAULT_TRIGGER or UPDATED_TRIGGER
        defaultAdTaskSchedulerShouldBeFound("trigger.in=" + DEFAULT_TRIGGER + "," + UPDATED_TRIGGER);

        // Get all the adTaskSchedulerList where trigger equals to UPDATED_TRIGGER
        defaultAdTaskSchedulerShouldNotBeFound("trigger.in=" + UPDATED_TRIGGER);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByTriggerIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where trigger is not null
        defaultAdTaskSchedulerShouldBeFound("trigger.specified=true");

        // Get all the adTaskSchedulerList where trigger is null
        defaultAdTaskSchedulerShouldNotBeFound("trigger.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression equals to DEFAULT_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldBeFound("cronExpression.equals=" + DEFAULT_CRON_EXPRESSION);

        // Get all the adTaskSchedulerList where cronExpression equals to UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.equals=" + UPDATED_CRON_EXPRESSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression not equals to DEFAULT_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.notEquals=" + DEFAULT_CRON_EXPRESSION);

        // Get all the adTaskSchedulerList where cronExpression not equals to UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldBeFound("cronExpression.notEquals=" + UPDATED_CRON_EXPRESSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression in DEFAULT_CRON_EXPRESSION or UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldBeFound("cronExpression.in=" + DEFAULT_CRON_EXPRESSION + "," + UPDATED_CRON_EXPRESSION);

        // Get all the adTaskSchedulerList where cronExpression equals to UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.in=" + UPDATED_CRON_EXPRESSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression is not null
        defaultAdTaskSchedulerShouldBeFound("cronExpression.specified=true");

        // Get all the adTaskSchedulerList where cronExpression is null
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression contains DEFAULT_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldBeFound("cronExpression.contains=" + DEFAULT_CRON_EXPRESSION);

        // Get all the adTaskSchedulerList where cronExpression contains UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.contains=" + UPDATED_CRON_EXPRESSION);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByCronExpressionNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where cronExpression does not contain DEFAULT_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldNotBeFound("cronExpression.doesNotContain=" + DEFAULT_CRON_EXPRESSION);

        // Get all the adTaskSchedulerList where cronExpression does not contain UPDATED_CRON_EXPRESSION
        defaultAdTaskSchedulerShouldBeFound("cronExpression.doesNotContain=" + UPDATED_CRON_EXPRESSION);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount equals to DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.equals=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount equals to UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.equals=" + UPDATED_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount not equals to DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.notEquals=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount not equals to UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.notEquals=" + UPDATED_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount in DEFAULT_PERIODIC_COUNT or UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.in=" + DEFAULT_PERIODIC_COUNT + "," + UPDATED_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount equals to UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.in=" + UPDATED_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount is not null
        defaultAdTaskSchedulerShouldBeFound("periodicCount.specified=true");

        // Get all the adTaskSchedulerList where periodicCount is null
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount is greater than or equal to DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.greaterThanOrEqual=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount is greater than or equal to UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.greaterThanOrEqual=" + UPDATED_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount is less than or equal to DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.lessThanOrEqual=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount is less than or equal to SMALLER_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.lessThanOrEqual=" + SMALLER_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsLessThanSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount is less than DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.lessThan=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount is less than UPDATED_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.lessThan=" + UPDATED_PERIODIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicCount is greater than DEFAULT_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldNotBeFound("periodicCount.greaterThan=" + DEFAULT_PERIODIC_COUNT);

        // Get all the adTaskSchedulerList where periodicCount is greater than SMALLER_PERIODIC_COUNT
        defaultAdTaskSchedulerShouldBeFound("periodicCount.greaterThan=" + SMALLER_PERIODIC_COUNT);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit equals to DEFAULT_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.equals=" + DEFAULT_PERIODIC_UNIT);

        // Get all the adTaskSchedulerList where periodicUnit equals to UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.equals=" + UPDATED_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit not equals to DEFAULT_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.notEquals=" + DEFAULT_PERIODIC_UNIT);

        // Get all the adTaskSchedulerList where periodicUnit not equals to UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.notEquals=" + UPDATED_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit in DEFAULT_PERIODIC_UNIT or UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.in=" + DEFAULT_PERIODIC_UNIT + "," + UPDATED_PERIODIC_UNIT);

        // Get all the adTaskSchedulerList where periodicUnit equals to UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.in=" + UPDATED_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit is not null
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.specified=true");

        // Get all the adTaskSchedulerList where periodicUnit is null
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit contains DEFAULT_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.contains=" + DEFAULT_PERIODIC_UNIT);

        // Get all the adTaskSchedulerList where periodicUnit contains UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.contains=" + UPDATED_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void getAllAdTaskSchedulersByPeriodicUnitNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        // Get all the adTaskSchedulerList where periodicUnit does not contain DEFAULT_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldNotBeFound("periodicUnit.doesNotContain=" + DEFAULT_PERIODIC_UNIT);

        // Get all the adTaskSchedulerList where periodicUnit does not contain UPDATED_PERIODIC_UNIT
        defaultAdTaskSchedulerShouldBeFound("periodicUnit.doesNotContain=" + UPDATED_PERIODIC_UNIT);
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTaskScheduler.getAdOrganization();
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTaskSchedulerList where adOrganization equals to adOrganizationId
        defaultAdTaskSchedulerShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTaskSchedulerList where adOrganization equals to adOrganizationId + 1
        defaultAdTaskSchedulerShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTaskSchedulersByAdTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdTask adTask = adTaskScheduler.getAdTask();
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);
        Long adTaskId = adTask.getId();

        // Get all the adTaskSchedulerList where adTask equals to adTaskId
        defaultAdTaskSchedulerShouldBeFound("adTaskId.equals=" + adTaskId);

        // Get all the adTaskSchedulerList where adTask equals to adTaskId + 1
        defaultAdTaskSchedulerShouldNotBeFound("adTaskId.equals=" + (adTaskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTaskSchedulerShouldBeFound(String filter) throws Exception {
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskScheduler.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].trigger").value(hasItem(DEFAULT_TRIGGER.toString())))
            .andExpect(jsonPath("$.[*].cronExpression").value(hasItem(DEFAULT_CRON_EXPRESSION)))
            .andExpect(jsonPath("$.[*].periodicCount").value(hasItem(DEFAULT_PERIODIC_COUNT)))
            .andExpect(jsonPath("$.[*].periodicUnit").value(hasItem(DEFAULT_PERIODIC_UNIT)));

        // Check, that the count call also returns 1
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTaskSchedulerShouldNotBeFound(String filter) throws Exception {
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTaskScheduler() throws Exception {
        // Get the adTaskScheduler
        restAdTaskSchedulerMockMvc.perform(get("/api/ad-task-schedulers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTaskScheduler() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        int databaseSizeBeforeUpdate = adTaskSchedulerRepository.findAll().size();

        // Update the adTaskScheduler
        AdTaskScheduler updatedAdTaskScheduler = adTaskSchedulerRepository.findById(adTaskScheduler.getId()).get();
        // Disconnect from session so that the updates on updatedAdTaskScheduler are not directly saved in db
        em.detach(updatedAdTaskScheduler);
        updatedAdTaskScheduler
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .trigger(UPDATED_TRIGGER)
            .cronExpression(UPDATED_CRON_EXPRESSION)
            .periodicCount(UPDATED_PERIODIC_COUNT)
            .periodicUnit(UPDATED_PERIODIC_UNIT);
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(updatedAdTaskScheduler);

        restAdTaskSchedulerMockMvc.perform(put("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isOk());

        // Validate the AdTaskScheduler in the database
        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeUpdate);
        AdTaskScheduler testAdTaskScheduler = adTaskSchedulerList.get(adTaskSchedulerList.size() - 1);
        assertThat(testAdTaskScheduler.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTaskScheduler.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTaskScheduler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTaskScheduler.getTrigger()).isEqualTo(UPDATED_TRIGGER);
        assertThat(testAdTaskScheduler.getCronExpression()).isEqualTo(UPDATED_CRON_EXPRESSION);
        assertThat(testAdTaskScheduler.getPeriodicCount()).isEqualTo(UPDATED_PERIODIC_COUNT);
        assertThat(testAdTaskScheduler.getPeriodicUnit()).isEqualTo(UPDATED_PERIODIC_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTaskScheduler() throws Exception {
        int databaseSizeBeforeUpdate = adTaskSchedulerRepository.findAll().size();

        // Create the AdTaskScheduler
        AdTaskSchedulerDTO adTaskSchedulerDTO = adTaskSchedulerMapper.toDto(adTaskScheduler);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTaskSchedulerMockMvc.perform(put("/api/ad-task-schedulers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskSchedulerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskScheduler in the database
        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTaskScheduler() throws Exception {
        // Initialize the database
        adTaskSchedulerRepository.saveAndFlush(adTaskScheduler);

        int databaseSizeBeforeDelete = adTaskSchedulerRepository.findAll().size();

        // Delete the adTaskScheduler
        restAdTaskSchedulerMockMvc.perform(delete("/api/ad-task-schedulers/{id}", adTaskScheduler.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTaskScheduler> adTaskSchedulerList = adTaskSchedulerRepository.findAll();
        assertThat(adTaskSchedulerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
