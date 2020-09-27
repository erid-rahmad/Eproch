package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.AdTaskProcess;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdTaskRepository;
import com.bhp.opusb.service.AdTaskService;
import com.bhp.opusb.service.dto.AdTaskDTO;
import com.bhp.opusb.service.mapper.AdTaskMapper;
import com.bhp.opusb.service.dto.AdTaskCriteria;
import com.bhp.opusb.service.AdTaskQueryService;

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
 * Integration tests for the {@link AdTaskResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTaskResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ASYNC = false;
    private static final Boolean UPDATED_ASYNC = true;

    @Autowired
    private AdTaskRepository adTaskRepository;

    @Autowired
    private AdTaskMapper adTaskMapper;

    @Autowired
    private AdTaskService adTaskService;

    @Autowired
    private AdTaskQueryService adTaskQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTaskMockMvc;

    private AdTask adTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTask createEntity(EntityManager em) {
        AdTask adTask = new AdTask()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .async(DEFAULT_ASYNC);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTask.setAdOrganization(aDOrganization);
        return adTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTask createUpdatedEntity(EntityManager em) {
        AdTask adTask = new AdTask()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .async(UPDATED_ASYNC);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTask.setAdOrganization(aDOrganization);
        return adTask;
    }

    @BeforeEach
    public void initTest() {
        adTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTask() throws Exception {
        int databaseSizeBeforeCreate = adTaskRepository.findAll().size();

        // Create the AdTask
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(adTask);
        restAdTaskMockMvc.perform(post("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTask in the database
        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeCreate + 1);
        AdTask testAdTask = adTaskList.get(adTaskList.size() - 1);
        assertThat(testAdTask.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTask.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTask.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdTask.isAsync()).isEqualTo(DEFAULT_ASYNC);
    }

    @Test
    @Transactional
    public void createAdTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTaskRepository.findAll().size();

        // Create the AdTask with an existing ID
        adTask.setId(1L);
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(adTask);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTaskMockMvc.perform(post("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTask in the database
        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskRepository.findAll().size();
        // set the field null
        adTask.setName(null);

        // Create the AdTask, which fails.
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(adTask);

        restAdTaskMockMvc.perform(post("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isBadRequest());

        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTaskRepository.findAll().size();
        // set the field null
        adTask.setValue(null);

        // Create the AdTask, which fails.
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(adTask);

        restAdTaskMockMvc.perform(post("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isBadRequest());

        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTasks() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList
        restAdTaskMockMvc.perform(get("/api/ad-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].async").value(hasItem(DEFAULT_ASYNC.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdTask() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get the adTask
        restAdTaskMockMvc.perform(get("/api/ad-tasks/{id}", adTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTask.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.async").value(DEFAULT_ASYNC.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdTasksByIdFiltering() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        Long id = adTask.getId();

        defaultAdTaskShouldBeFound("id.equals=" + id);
        defaultAdTaskShouldNotBeFound("id.notEquals=" + id);

        defaultAdTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTaskShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTasksByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where uid equals to DEFAULT_UID
        defaultAdTaskShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTaskList where uid equals to UPDATED_UID
        defaultAdTaskShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTasksByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where uid not equals to DEFAULT_UID
        defaultAdTaskShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTaskList where uid not equals to UPDATED_UID
        defaultAdTaskShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTasksByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTaskShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTaskList where uid equals to UPDATED_UID
        defaultAdTaskShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTasksByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where uid is not null
        defaultAdTaskShouldBeFound("uid.specified=true");

        // Get all the adTaskList where uid is null
        defaultAdTaskShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTasksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where active equals to DEFAULT_ACTIVE
        defaultAdTaskShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTaskList where active equals to UPDATED_ACTIVE
        defaultAdTaskShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where active not equals to DEFAULT_ACTIVE
        defaultAdTaskShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTaskList where active not equals to UPDATED_ACTIVE
        defaultAdTaskShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTaskShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTaskList where active equals to UPDATED_ACTIVE
        defaultAdTaskShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where active is not null
        defaultAdTaskShouldBeFound("active.specified=true");

        // Get all the adTaskList where active is null
        defaultAdTaskShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTasksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name equals to DEFAULT_NAME
        defaultAdTaskShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTaskList where name equals to UPDATED_NAME
        defaultAdTaskShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTasksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name not equals to DEFAULT_NAME
        defaultAdTaskShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTaskList where name not equals to UPDATED_NAME
        defaultAdTaskShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTasksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTaskShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTaskList where name equals to UPDATED_NAME
        defaultAdTaskShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTasksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name is not null
        defaultAdTaskShouldBeFound("name.specified=true");

        // Get all the adTaskList where name is null
        defaultAdTaskShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTasksByNameContainsSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name contains DEFAULT_NAME
        defaultAdTaskShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTaskList where name contains UPDATED_NAME
        defaultAdTaskShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTasksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where name does not contain DEFAULT_NAME
        defaultAdTaskShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTaskList where name does not contain UPDATED_NAME
        defaultAdTaskShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTasksByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value equals to DEFAULT_VALUE
        defaultAdTaskShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the adTaskList where value equals to UPDATED_VALUE
        defaultAdTaskShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value not equals to DEFAULT_VALUE
        defaultAdTaskShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the adTaskList where value not equals to UPDATED_VALUE
        defaultAdTaskShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAdTaskShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the adTaskList where value equals to UPDATED_VALUE
        defaultAdTaskShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value is not null
        defaultAdTaskShouldBeFound("value.specified=true");

        // Get all the adTaskList where value is null
        defaultAdTaskShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTasksByValueContainsSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value contains DEFAULT_VALUE
        defaultAdTaskShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the adTaskList where value contains UPDATED_VALUE
        defaultAdTaskShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTasksByValueNotContainsSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where value does not contain DEFAULT_VALUE
        defaultAdTaskShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the adTaskList where value does not contain UPDATED_VALUE
        defaultAdTaskShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTasksByAsyncIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where async equals to DEFAULT_ASYNC
        defaultAdTaskShouldBeFound("async.equals=" + DEFAULT_ASYNC);

        // Get all the adTaskList where async equals to UPDATED_ASYNC
        defaultAdTaskShouldNotBeFound("async.equals=" + UPDATED_ASYNC);
    }

    @Test
    @Transactional
    public void getAllAdTasksByAsyncIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where async not equals to DEFAULT_ASYNC
        defaultAdTaskShouldNotBeFound("async.notEquals=" + DEFAULT_ASYNC);

        // Get all the adTaskList where async not equals to UPDATED_ASYNC
        defaultAdTaskShouldBeFound("async.notEquals=" + UPDATED_ASYNC);
    }

    @Test
    @Transactional
    public void getAllAdTasksByAsyncIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where async in DEFAULT_ASYNC or UPDATED_ASYNC
        defaultAdTaskShouldBeFound("async.in=" + DEFAULT_ASYNC + "," + UPDATED_ASYNC);

        // Get all the adTaskList where async equals to UPDATED_ASYNC
        defaultAdTaskShouldNotBeFound("async.in=" + UPDATED_ASYNC);
    }

    @Test
    @Transactional
    public void getAllAdTasksByAsyncIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        // Get all the adTaskList where async is not null
        defaultAdTaskShouldBeFound("async.specified=true");

        // Get all the adTaskList where async is null
        defaultAdTaskShouldNotBeFound("async.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTasksByAdTaskProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);
        AdTaskProcess adTaskProcess = AdTaskProcessResourceIT.createEntity(em);
        em.persist(adTaskProcess);
        em.flush();
        adTask.addAdTaskProcess(adTaskProcess);
        adTaskRepository.saveAndFlush(adTask);
        Long adTaskProcessId = adTaskProcess.getId();

        // Get all the adTaskList where adTaskProcess equals to adTaskProcessId
        defaultAdTaskShouldBeFound("adTaskProcessId.equals=" + adTaskProcessId);

        // Get all the adTaskList where adTaskProcess equals to adTaskProcessId + 1
        defaultAdTaskShouldNotBeFound("adTaskProcessId.equals=" + (adTaskProcessId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTasksByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTask.getAdOrganization();
        adTaskRepository.saveAndFlush(adTask);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTaskList where adOrganization equals to adOrganizationId
        defaultAdTaskShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTaskList where adOrganization equals to adOrganizationId + 1
        defaultAdTaskShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTaskShouldBeFound(String filter) throws Exception {
        restAdTaskMockMvc.perform(get("/api/ad-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].async").value(hasItem(DEFAULT_ASYNC.booleanValue())));

        // Check, that the count call also returns 1
        restAdTaskMockMvc.perform(get("/api/ad-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTaskShouldNotBeFound(String filter) throws Exception {
        restAdTaskMockMvc.perform(get("/api/ad-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTaskMockMvc.perform(get("/api/ad-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTask() throws Exception {
        // Get the adTask
        restAdTaskMockMvc.perform(get("/api/ad-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTask() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        int databaseSizeBeforeUpdate = adTaskRepository.findAll().size();

        // Update the adTask
        AdTask updatedAdTask = adTaskRepository.findById(adTask.getId()).get();
        // Disconnect from session so that the updates on updatedAdTask are not directly saved in db
        em.detach(updatedAdTask);
        updatedAdTask
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .async(UPDATED_ASYNC);
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(updatedAdTask);

        restAdTaskMockMvc.perform(put("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isOk());

        // Validate the AdTask in the database
        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeUpdate);
        AdTask testAdTask = adTaskList.get(adTaskList.size() - 1);
        assertThat(testAdTask.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTask.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTask.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdTask.isAsync()).isEqualTo(UPDATED_ASYNC);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTask() throws Exception {
        int databaseSizeBeforeUpdate = adTaskRepository.findAll().size();

        // Create the AdTask
        AdTaskDTO adTaskDTO = adTaskMapper.toDto(adTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTaskMockMvc.perform(put("/api/ad-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTask in the database
        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTask() throws Exception {
        // Initialize the database
        adTaskRepository.saveAndFlush(adTask);

        int databaseSizeBeforeDelete = adTaskRepository.findAll().size();

        // Delete the adTask
        restAdTaskMockMvc.perform(delete("/api/ad-tasks/{id}", adTask.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTask> adTaskList = adTaskRepository.findAll();
        assertThat(adTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
