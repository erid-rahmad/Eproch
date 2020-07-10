package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTaskProcess;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.repository.AdTaskProcessRepository;
import com.bhp.opusb.service.AdTaskProcessService;
import com.bhp.opusb.service.dto.AdTaskProcessDTO;
import com.bhp.opusb.service.mapper.AdTaskProcessMapper;
import com.bhp.opusb.service.dto.AdTaskProcessCriteria;
import com.bhp.opusb.service.AdTaskProcessQueryService;

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
 * Integration tests for the {@link AdTaskProcessResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTaskProcessResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Integer DEFAULT_RUN_SEQUENCE = 1;
    private static final Integer UPDATED_RUN_SEQUENCE = 2;
    private static final Integer SMALLER_RUN_SEQUENCE = 1 - 1;

    private static final Boolean DEFAULT_PARALLEL = false;
    private static final Boolean UPDATED_PARALLEL = true;

    @Autowired
    private AdTaskProcessRepository adTaskProcessRepository;

    @Autowired
    private AdTaskProcessMapper adTaskProcessMapper;

    @Autowired
    private AdTaskProcessService adTaskProcessService;

    @Autowired
    private AdTaskProcessQueryService adTaskProcessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTaskProcessMockMvc;

    private AdTaskProcess adTaskProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskProcess createEntity(EntityManager em) {
        AdTaskProcess adTaskProcess = new AdTaskProcess()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .runSequence(DEFAULT_RUN_SEQUENCE)
            .parallel(DEFAULT_PARALLEL);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskProcess.setAdOrganization(aDOrganization);
        // Add required entity
        AdTaskApplication adTaskApplication;
        if (TestUtil.findAll(em, AdTaskApplication.class).isEmpty()) {
            adTaskApplication = AdTaskApplicationResourceIT.createEntity(em);
            em.persist(adTaskApplication);
            em.flush();
        } else {
            adTaskApplication = TestUtil.findAll(em, AdTaskApplication.class).get(0);
        }
        adTaskProcess.setAdTaskApplication(adTaskApplication);
        // Add required entity
        AdTask adTask;
        if (TestUtil.findAll(em, AdTask.class).isEmpty()) {
            adTask = AdTaskResourceIT.createEntity(em);
            em.persist(adTask);
            em.flush();
        } else {
            adTask = TestUtil.findAll(em, AdTask.class).get(0);
        }
        adTaskProcess.setAdTask(adTask);
        return adTaskProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTaskProcess createUpdatedEntity(EntityManager em) {
        AdTaskProcess adTaskProcess = new AdTaskProcess()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .runSequence(UPDATED_RUN_SEQUENCE)
            .parallel(UPDATED_PARALLEL);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTaskProcess.setAdOrganization(aDOrganization);
        // Add required entity
        AdTaskApplication adTaskApplication;
        if (TestUtil.findAll(em, AdTaskApplication.class).isEmpty()) {
            adTaskApplication = AdTaskApplicationResourceIT.createUpdatedEntity(em);
            em.persist(adTaskApplication);
            em.flush();
        } else {
            adTaskApplication = TestUtil.findAll(em, AdTaskApplication.class).get(0);
        }
        adTaskProcess.setAdTaskApplication(adTaskApplication);
        // Add required entity
        AdTask adTask;
        if (TestUtil.findAll(em, AdTask.class).isEmpty()) {
            adTask = AdTaskResourceIT.createUpdatedEntity(em);
            em.persist(adTask);
            em.flush();
        } else {
            adTask = TestUtil.findAll(em, AdTask.class).get(0);
        }
        adTaskProcess.setAdTask(adTask);
        return adTaskProcess;
    }

    @BeforeEach
    public void initTest() {
        adTaskProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTaskProcess() throws Exception {
        int databaseSizeBeforeCreate = adTaskProcessRepository.findAll().size();

        // Create the AdTaskProcess
        AdTaskProcessDTO adTaskProcessDTO = adTaskProcessMapper.toDto(adTaskProcess);
        restAdTaskProcessMockMvc.perform(post("/api/ad-task-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTaskProcess in the database
        List<AdTaskProcess> adTaskProcessList = adTaskProcessRepository.findAll();
        assertThat(adTaskProcessList).hasSize(databaseSizeBeforeCreate + 1);
        AdTaskProcess testAdTaskProcess = adTaskProcessList.get(adTaskProcessList.size() - 1);
        assertThat(testAdTaskProcess.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTaskProcess.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTaskProcess.getRunSequence()).isEqualTo(DEFAULT_RUN_SEQUENCE);
        assertThat(testAdTaskProcess.isParallel()).isEqualTo(DEFAULT_PARALLEL);
    }

    @Test
    @Transactional
    public void createAdTaskProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTaskProcessRepository.findAll().size();

        // Create the AdTaskProcess with an existing ID
        adTaskProcess.setId(1L);
        AdTaskProcessDTO adTaskProcessDTO = adTaskProcessMapper.toDto(adTaskProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTaskProcessMockMvc.perform(post("/api/ad-task-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskProcess in the database
        List<AdTaskProcess> adTaskProcessList = adTaskProcessRepository.findAll();
        assertThat(adTaskProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdTaskProcesses() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].runSequence").value(hasItem(DEFAULT_RUN_SEQUENCE)))
            .andExpect(jsonPath("$.[*].parallel").value(hasItem(DEFAULT_PARALLEL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdTaskProcess() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get the adTaskProcess
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes/{id}", adTaskProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTaskProcess.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.runSequence").value(DEFAULT_RUN_SEQUENCE))
            .andExpect(jsonPath("$.parallel").value(DEFAULT_PARALLEL.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdTaskProcessesByIdFiltering() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        Long id = adTaskProcess.getId();

        defaultAdTaskProcessShouldBeFound("id.equals=" + id);
        defaultAdTaskProcessShouldNotBeFound("id.notEquals=" + id);

        defaultAdTaskProcessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTaskProcessShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTaskProcessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTaskProcessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTaskProcessesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where uid equals to DEFAULT_UID
        defaultAdTaskProcessShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTaskProcessList where uid equals to UPDATED_UID
        defaultAdTaskProcessShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where uid not equals to DEFAULT_UID
        defaultAdTaskProcessShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTaskProcessList where uid not equals to UPDATED_UID
        defaultAdTaskProcessShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTaskProcessShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTaskProcessList where uid equals to UPDATED_UID
        defaultAdTaskProcessShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where uid is not null
        defaultAdTaskProcessShouldBeFound("uid.specified=true");

        // Get all the adTaskProcessList where uid is null
        defaultAdTaskProcessShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where active equals to DEFAULT_ACTIVE
        defaultAdTaskProcessShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTaskProcessList where active equals to UPDATED_ACTIVE
        defaultAdTaskProcessShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where active not equals to DEFAULT_ACTIVE
        defaultAdTaskProcessShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTaskProcessList where active not equals to UPDATED_ACTIVE
        defaultAdTaskProcessShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTaskProcessShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTaskProcessList where active equals to UPDATED_ACTIVE
        defaultAdTaskProcessShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where active is not null
        defaultAdTaskProcessShouldBeFound("active.specified=true");

        // Get all the adTaskProcessList where active is null
        defaultAdTaskProcessShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence equals to DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.equals=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence equals to UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.equals=" + UPDATED_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence not equals to DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.notEquals=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence not equals to UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.notEquals=" + UPDATED_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence in DEFAULT_RUN_SEQUENCE or UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.in=" + DEFAULT_RUN_SEQUENCE + "," + UPDATED_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence equals to UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.in=" + UPDATED_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence is not null
        defaultAdTaskProcessShouldBeFound("runSequence.specified=true");

        // Get all the adTaskProcessList where runSequence is null
        defaultAdTaskProcessShouldNotBeFound("runSequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence is greater than or equal to DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.greaterThanOrEqual=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence is greater than or equal to UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.greaterThanOrEqual=" + UPDATED_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence is less than or equal to DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.lessThanOrEqual=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence is less than or equal to SMALLER_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.lessThanOrEqual=" + SMALLER_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence is less than DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.lessThan=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence is less than UPDATED_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.lessThan=" + UPDATED_RUN_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByRunSequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where runSequence is greater than DEFAULT_RUN_SEQUENCE
        defaultAdTaskProcessShouldNotBeFound("runSequence.greaterThan=" + DEFAULT_RUN_SEQUENCE);

        // Get all the adTaskProcessList where runSequence is greater than SMALLER_RUN_SEQUENCE
        defaultAdTaskProcessShouldBeFound("runSequence.greaterThan=" + SMALLER_RUN_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllAdTaskProcessesByParallelIsEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where parallel equals to DEFAULT_PARALLEL
        defaultAdTaskProcessShouldBeFound("parallel.equals=" + DEFAULT_PARALLEL);

        // Get all the adTaskProcessList where parallel equals to UPDATED_PARALLEL
        defaultAdTaskProcessShouldNotBeFound("parallel.equals=" + UPDATED_PARALLEL);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByParallelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where parallel not equals to DEFAULT_PARALLEL
        defaultAdTaskProcessShouldNotBeFound("parallel.notEquals=" + DEFAULT_PARALLEL);

        // Get all the adTaskProcessList where parallel not equals to UPDATED_PARALLEL
        defaultAdTaskProcessShouldBeFound("parallel.notEquals=" + UPDATED_PARALLEL);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByParallelIsInShouldWork() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where parallel in DEFAULT_PARALLEL or UPDATED_PARALLEL
        defaultAdTaskProcessShouldBeFound("parallel.in=" + DEFAULT_PARALLEL + "," + UPDATED_PARALLEL);

        // Get all the adTaskProcessList where parallel equals to UPDATED_PARALLEL
        defaultAdTaskProcessShouldNotBeFound("parallel.in=" + UPDATED_PARALLEL);
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByParallelIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        // Get all the adTaskProcessList where parallel is not null
        defaultAdTaskProcessShouldBeFound("parallel.specified=true");

        // Get all the adTaskProcessList where parallel is null
        defaultAdTaskProcessShouldNotBeFound("parallel.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTaskProcessesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTaskProcess.getAdOrganization();
        adTaskProcessRepository.saveAndFlush(adTaskProcess);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTaskProcessList where adOrganization equals to adOrganizationId
        defaultAdTaskProcessShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTaskProcessList where adOrganization equals to adOrganizationId + 1
        defaultAdTaskProcessShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTaskProcessesByAdTaskApplicationIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdTaskApplication adTaskApplication = adTaskProcess.getAdTaskApplication();
        adTaskProcessRepository.saveAndFlush(adTaskProcess);
        Long adTaskApplicationId = adTaskApplication.getId();

        // Get all the adTaskProcessList where adTaskApplication equals to adTaskApplicationId
        defaultAdTaskProcessShouldBeFound("adTaskApplicationId.equals=" + adTaskApplicationId);

        // Get all the adTaskProcessList where adTaskApplication equals to adTaskApplicationId + 1
        defaultAdTaskProcessShouldNotBeFound("adTaskApplicationId.equals=" + (adTaskApplicationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTaskProcessesByAdTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdTask adTask = adTaskProcess.getAdTask();
        adTaskProcessRepository.saveAndFlush(adTaskProcess);
        Long adTaskId = adTask.getId();

        // Get all the adTaskProcessList where adTask equals to adTaskId
        defaultAdTaskProcessShouldBeFound("adTaskId.equals=" + adTaskId);

        // Get all the adTaskProcessList where adTask equals to adTaskId + 1
        defaultAdTaskProcessShouldNotBeFound("adTaskId.equals=" + (adTaskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTaskProcessShouldBeFound(String filter) throws Exception {
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTaskProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].runSequence").value(hasItem(DEFAULT_RUN_SEQUENCE)))
            .andExpect(jsonPath("$.[*].parallel").value(hasItem(DEFAULT_PARALLEL.booleanValue())));

        // Check, that the count call also returns 1
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTaskProcessShouldNotBeFound(String filter) throws Exception {
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTaskProcess() throws Exception {
        // Get the adTaskProcess
        restAdTaskProcessMockMvc.perform(get("/api/ad-task-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTaskProcess() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        int databaseSizeBeforeUpdate = adTaskProcessRepository.findAll().size();

        // Update the adTaskProcess
        AdTaskProcess updatedAdTaskProcess = adTaskProcessRepository.findById(adTaskProcess.getId()).get();
        // Disconnect from session so that the updates on updatedAdTaskProcess are not directly saved in db
        em.detach(updatedAdTaskProcess);
        updatedAdTaskProcess
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .runSequence(UPDATED_RUN_SEQUENCE)
            .parallel(UPDATED_PARALLEL);
        AdTaskProcessDTO adTaskProcessDTO = adTaskProcessMapper.toDto(updatedAdTaskProcess);

        restAdTaskProcessMockMvc.perform(put("/api/ad-task-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskProcessDTO)))
            .andExpect(status().isOk());

        // Validate the AdTaskProcess in the database
        List<AdTaskProcess> adTaskProcessList = adTaskProcessRepository.findAll();
        assertThat(adTaskProcessList).hasSize(databaseSizeBeforeUpdate);
        AdTaskProcess testAdTaskProcess = adTaskProcessList.get(adTaskProcessList.size() - 1);
        assertThat(testAdTaskProcess.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTaskProcess.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTaskProcess.getRunSequence()).isEqualTo(UPDATED_RUN_SEQUENCE);
        assertThat(testAdTaskProcess.isParallel()).isEqualTo(UPDATED_PARALLEL);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTaskProcess() throws Exception {
        int databaseSizeBeforeUpdate = adTaskProcessRepository.findAll().size();

        // Create the AdTaskProcess
        AdTaskProcessDTO adTaskProcessDTO = adTaskProcessMapper.toDto(adTaskProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTaskProcessMockMvc.perform(put("/api/ad-task-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTaskProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTaskProcess in the database
        List<AdTaskProcess> adTaskProcessList = adTaskProcessRepository.findAll();
        assertThat(adTaskProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTaskProcess() throws Exception {
        // Initialize the database
        adTaskProcessRepository.saveAndFlush(adTaskProcess);

        int databaseSizeBeforeDelete = adTaskProcessRepository.findAll().size();

        // Delete the adTaskProcess
        restAdTaskProcessMockMvc.perform(delete("/api/ad-task-processes/{id}", adTaskProcess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTaskProcess> adTaskProcessList = adTaskProcessRepository.findAll();
        assertThat(adTaskProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
