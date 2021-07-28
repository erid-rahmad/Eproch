package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CTask;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CTaskRepository;
import com.bhp.opusb.service.CTaskService;
import com.bhp.opusb.service.dto.CTaskDTO;
import com.bhp.opusb.service.mapper.CTaskMapper;
import com.bhp.opusb.service.dto.CTaskCriteria;
import com.bhp.opusb.service.CTaskQueryService;

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
 * Integration tests for the {@link CTaskResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CTaskResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CTaskRepository cTaskRepository;

    @Autowired
    private CTaskMapper cTaskMapper;

    @Autowired
    private CTaskService cTaskService;

    @Autowired
    private CTaskQueryService cTaskQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCTaskMockMvc;

    private CTask cTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTask createEntity(EntityManager em) {
        CTask cTask = new CTask()
            .name(DEFAULT_NAME)
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
        cTask.setAdOrganization(aDOrganization);
        return cTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTask createUpdatedEntity(EntityManager em) {
        CTask cTask = new CTask()
            .name(UPDATED_NAME)
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
        cTask.setAdOrganization(aDOrganization);
        return cTask;
    }

    @BeforeEach
    public void initTest() {
        cTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createCTask() throws Exception {
        int databaseSizeBeforeCreate = cTaskRepository.findAll().size();

        // Create the CTask
        CTaskDTO cTaskDTO = cTaskMapper.toDto(cTask);
        restCTaskMockMvc.perform(post("/api/c-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the CTask in the database
        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeCreate + 1);
        CTask testCTask = cTaskList.get(cTaskList.size() - 1);
        assertThat(testCTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCTask.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCTask.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cTaskRepository.findAll().size();

        // Create the CTask with an existing ID
        cTask.setId(1L);
        CTaskDTO cTaskDTO = cTaskMapper.toDto(cTask);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCTaskMockMvc.perform(post("/api/c-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTask in the database
        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaskRepository.findAll().size();
        // set the field null
        cTask.setName(null);

        // Create the CTask, which fails.
        CTaskDTO cTaskDTO = cTaskMapper.toDto(cTask);

        restCTaskMockMvc.perform(post("/api/c-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaskDTO)))
            .andExpect(status().isBadRequest());

        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCTasks() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList
        restCTaskMockMvc.perform(get("/api/c-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCTask() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get the cTask
        restCTaskMockMvc.perform(get("/api/c-tasks/{id}", cTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cTask.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCTasksByIdFiltering() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        Long id = cTask.getId();

        defaultCTaskShouldBeFound("id.equals=" + id);
        defaultCTaskShouldNotBeFound("id.notEquals=" + id);

        defaultCTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultCTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCTaskShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCTasksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name equals to DEFAULT_NAME
        defaultCTaskShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cTaskList where name equals to UPDATED_NAME
        defaultCTaskShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTasksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name not equals to DEFAULT_NAME
        defaultCTaskShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cTaskList where name not equals to UPDATED_NAME
        defaultCTaskShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTasksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCTaskShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cTaskList where name equals to UPDATED_NAME
        defaultCTaskShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTasksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name is not null
        defaultCTaskShouldBeFound("name.specified=true");

        // Get all the cTaskList where name is null
        defaultCTaskShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTasksByNameContainsSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name contains DEFAULT_NAME
        defaultCTaskShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cTaskList where name contains UPDATED_NAME
        defaultCTaskShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTasksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where name does not contain DEFAULT_NAME
        defaultCTaskShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cTaskList where name does not contain UPDATED_NAME
        defaultCTaskShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCTasksByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where uid equals to DEFAULT_UID
        defaultCTaskShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cTaskList where uid equals to UPDATED_UID
        defaultCTaskShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTasksByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where uid not equals to DEFAULT_UID
        defaultCTaskShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cTaskList where uid not equals to UPDATED_UID
        defaultCTaskShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTasksByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where uid in DEFAULT_UID or UPDATED_UID
        defaultCTaskShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cTaskList where uid equals to UPDATED_UID
        defaultCTaskShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTasksByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where uid is not null
        defaultCTaskShouldBeFound("uid.specified=true");

        // Get all the cTaskList where uid is null
        defaultCTaskShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTasksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where active equals to DEFAULT_ACTIVE
        defaultCTaskShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cTaskList where active equals to UPDATED_ACTIVE
        defaultCTaskShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTasksByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where active not equals to DEFAULT_ACTIVE
        defaultCTaskShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cTaskList where active not equals to UPDATED_ACTIVE
        defaultCTaskShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTasksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCTaskShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cTaskList where active equals to UPDATED_ACTIVE
        defaultCTaskShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTasksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        // Get all the cTaskList where active is not null
        defaultCTaskShouldBeFound("active.specified=true");

        // Get all the cTaskList where active is null
        defaultCTaskShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTasksByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cTask.getAdOrganization();
        cTaskRepository.saveAndFlush(cTask);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cTaskList where adOrganization equals to adOrganizationId
        defaultCTaskShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cTaskList where adOrganization equals to adOrganizationId + 1
        defaultCTaskShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCTaskShouldBeFound(String filter) throws Exception {
        restCTaskMockMvc.perform(get("/api/c-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCTaskMockMvc.perform(get("/api/c-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCTaskShouldNotBeFound(String filter) throws Exception {
        restCTaskMockMvc.perform(get("/api/c-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCTaskMockMvc.perform(get("/api/c-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCTask() throws Exception {
        // Get the cTask
        restCTaskMockMvc.perform(get("/api/c-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCTask() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        int databaseSizeBeforeUpdate = cTaskRepository.findAll().size();

        // Update the cTask
        CTask updatedCTask = cTaskRepository.findById(cTask.getId()).get();
        // Disconnect from session so that the updates on updatedCTask are not directly saved in db
        em.detach(updatedCTask);
        updatedCTask
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CTaskDTO cTaskDTO = cTaskMapper.toDto(updatedCTask);

        restCTaskMockMvc.perform(put("/api/c-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaskDTO)))
            .andExpect(status().isOk());

        // Validate the CTask in the database
        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeUpdate);
        CTask testCTask = cTaskList.get(cTaskList.size() - 1);
        assertThat(testCTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCTask.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCTask.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCTask() throws Exception {
        int databaseSizeBeforeUpdate = cTaskRepository.findAll().size();

        // Create the CTask
        CTaskDTO cTaskDTO = cTaskMapper.toDto(cTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCTaskMockMvc.perform(put("/api/c-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTask in the database
        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCTask() throws Exception {
        // Initialize the database
        cTaskRepository.saveAndFlush(cTask);

        int databaseSizeBeforeDelete = cTaskRepository.findAll().size();

        // Delete the cTask
        restCTaskMockMvc.perform(delete("/api/c-tasks/{id}", cTask.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CTask> cTaskList = cTaskRepository.findAll();
        assertThat(cTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
