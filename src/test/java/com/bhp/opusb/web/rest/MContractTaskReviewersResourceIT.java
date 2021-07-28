package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractTaskReviewers;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContractTask;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MContractTaskReviewersRepository;
import com.bhp.opusb.service.MContractTaskReviewersService;
import com.bhp.opusb.service.dto.MContractTaskReviewersDTO;
import com.bhp.opusb.service.mapper.MContractTaskReviewersMapper;
import com.bhp.opusb.service.dto.MContractTaskReviewersCriteria;
import com.bhp.opusb.service.MContractTaskReviewersQueryService;

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
 * Integration tests for the {@link MContractTaskReviewersResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractTaskReviewersResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractTaskReviewersRepository mContractTaskReviewersRepository;

    @Autowired
    private MContractTaskReviewersMapper mContractTaskReviewersMapper;

    @Autowired
    private MContractTaskReviewersService mContractTaskReviewersService;

    @Autowired
    private MContractTaskReviewersQueryService mContractTaskReviewersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractTaskReviewersMockMvc;

    private MContractTaskReviewers mContractTaskReviewers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTaskReviewers createEntity(EntityManager em) {
        MContractTaskReviewers mContractTaskReviewers = new MContractTaskReviewers()
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
        mContractTaskReviewers.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTask mContractTask;
        if (TestUtil.findAll(em, MContractTask.class).isEmpty()) {
            mContractTask = MContractTaskResourceIT.createEntity(em);
            em.persist(mContractTask);
            em.flush();
        } else {
            mContractTask = TestUtil.findAll(em, MContractTask.class).get(0);
        }
        mContractTaskReviewers.setContractTask(mContractTask);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContractTaskReviewers.setPic(adUser);
        return mContractTaskReviewers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTaskReviewers createUpdatedEntity(EntityManager em) {
        MContractTaskReviewers mContractTaskReviewers = new MContractTaskReviewers()
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
        mContractTaskReviewers.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTask mContractTask;
        if (TestUtil.findAll(em, MContractTask.class).isEmpty()) {
            mContractTask = MContractTaskResourceIT.createUpdatedEntity(em);
            em.persist(mContractTask);
            em.flush();
        } else {
            mContractTask = TestUtil.findAll(em, MContractTask.class).get(0);
        }
        mContractTaskReviewers.setContractTask(mContractTask);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContractTaskReviewers.setPic(adUser);
        return mContractTaskReviewers;
    }

    @BeforeEach
    public void initTest() {
        mContractTaskReviewers = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractTaskReviewers() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskReviewersRepository.findAll().size();

        // Create the MContractTaskReviewers
        MContractTaskReviewersDTO mContractTaskReviewersDTO = mContractTaskReviewersMapper.toDto(mContractTaskReviewers);
        restMContractTaskReviewersMockMvc.perform(post("/api/m-contract-task-reviewers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskReviewersDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractTaskReviewers in the database
        List<MContractTaskReviewers> mContractTaskReviewersList = mContractTaskReviewersRepository.findAll();
        assertThat(mContractTaskReviewersList).hasSize(databaseSizeBeforeCreate + 1);
        MContractTaskReviewers testMContractTaskReviewers = mContractTaskReviewersList.get(mContractTaskReviewersList.size() - 1);
        assertThat(testMContractTaskReviewers.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractTaskReviewers.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractTaskReviewersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskReviewersRepository.findAll().size();

        // Create the MContractTaskReviewers with an existing ID
        mContractTaskReviewers.setId(1L);
        MContractTaskReviewersDTO mContractTaskReviewersDTO = mContractTaskReviewersMapper.toDto(mContractTaskReviewers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractTaskReviewersMockMvc.perform(post("/api/m-contract-task-reviewers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskReviewersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTaskReviewers in the database
        List<MContractTaskReviewers> mContractTaskReviewersList = mContractTaskReviewersRepository.findAll();
        assertThat(mContractTaskReviewersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMContractTaskReviewers() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTaskReviewers.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractTaskReviewers() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get the mContractTaskReviewers
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers/{id}", mContractTaskReviewers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractTaskReviewers.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractTaskReviewersByIdFiltering() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        Long id = mContractTaskReviewers.getId();

        defaultMContractTaskReviewersShouldBeFound("id.equals=" + id);
        defaultMContractTaskReviewersShouldNotBeFound("id.notEquals=" + id);

        defaultMContractTaskReviewersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractTaskReviewersShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractTaskReviewersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractTaskReviewersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractTaskReviewersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where uid equals to DEFAULT_UID
        defaultMContractTaskReviewersShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractTaskReviewersList where uid equals to UPDATED_UID
        defaultMContractTaskReviewersShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where uid not equals to DEFAULT_UID
        defaultMContractTaskReviewersShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractTaskReviewersList where uid not equals to UPDATED_UID
        defaultMContractTaskReviewersShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractTaskReviewersShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractTaskReviewersList where uid equals to UPDATED_UID
        defaultMContractTaskReviewersShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where uid is not null
        defaultMContractTaskReviewersShouldBeFound("uid.specified=true");

        // Get all the mContractTaskReviewersList where uid is null
        defaultMContractTaskReviewersShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where active equals to DEFAULT_ACTIVE
        defaultMContractTaskReviewersShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskReviewersList where active equals to UPDATED_ACTIVE
        defaultMContractTaskReviewersShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where active not equals to DEFAULT_ACTIVE
        defaultMContractTaskReviewersShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskReviewersList where active not equals to UPDATED_ACTIVE
        defaultMContractTaskReviewersShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractTaskReviewersShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractTaskReviewersList where active equals to UPDATED_ACTIVE
        defaultMContractTaskReviewersShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        // Get all the mContractTaskReviewersList where active is not null
        defaultMContractTaskReviewersShouldBeFound("active.specified=true");

        // Get all the mContractTaskReviewersList where active is null
        defaultMContractTaskReviewersShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTaskReviewersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractTaskReviewers.getAdOrganization();
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractTaskReviewersList where adOrganization equals to adOrganizationId
        defaultMContractTaskReviewersShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractTaskReviewersList where adOrganization equals to adOrganizationId + 1
        defaultMContractTaskReviewersShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTaskReviewersByContractTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContractTask contractTask = mContractTaskReviewers.getContractTask();
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);
        Long contractTaskId = contractTask.getId();

        // Get all the mContractTaskReviewersList where contractTask equals to contractTaskId
        defaultMContractTaskReviewersShouldBeFound("contractTaskId.equals=" + contractTaskId);

        // Get all the mContractTaskReviewersList where contractTask equals to contractTaskId + 1
        defaultMContractTaskReviewersShouldNotBeFound("contractTaskId.equals=" + (contractTaskId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTaskReviewersByPicIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser pic = mContractTaskReviewers.getPic();
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);
        Long picId = pic.getId();

        // Get all the mContractTaskReviewersList where pic equals to picId
        defaultMContractTaskReviewersShouldBeFound("picId.equals=" + picId);

        // Get all the mContractTaskReviewersList where pic equals to picId + 1
        defaultMContractTaskReviewersShouldNotBeFound("picId.equals=" + (picId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractTaskReviewersShouldBeFound(String filter) throws Exception {
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTaskReviewers.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractTaskReviewersShouldNotBeFound(String filter) throws Exception {
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractTaskReviewers() throws Exception {
        // Get the mContractTaskReviewers
        restMContractTaskReviewersMockMvc.perform(get("/api/m-contract-task-reviewers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractTaskReviewers() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        int databaseSizeBeforeUpdate = mContractTaskReviewersRepository.findAll().size();

        // Update the mContractTaskReviewers
        MContractTaskReviewers updatedMContractTaskReviewers = mContractTaskReviewersRepository.findById(mContractTaskReviewers.getId()).get();
        // Disconnect from session so that the updates on updatedMContractTaskReviewers are not directly saved in db
        em.detach(updatedMContractTaskReviewers);
        updatedMContractTaskReviewers
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractTaskReviewersDTO mContractTaskReviewersDTO = mContractTaskReviewersMapper.toDto(updatedMContractTaskReviewers);

        restMContractTaskReviewersMockMvc.perform(put("/api/m-contract-task-reviewers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskReviewersDTO)))
            .andExpect(status().isOk());

        // Validate the MContractTaskReviewers in the database
        List<MContractTaskReviewers> mContractTaskReviewersList = mContractTaskReviewersRepository.findAll();
        assertThat(mContractTaskReviewersList).hasSize(databaseSizeBeforeUpdate);
        MContractTaskReviewers testMContractTaskReviewers = mContractTaskReviewersList.get(mContractTaskReviewersList.size() - 1);
        assertThat(testMContractTaskReviewers.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractTaskReviewers.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractTaskReviewers() throws Exception {
        int databaseSizeBeforeUpdate = mContractTaskReviewersRepository.findAll().size();

        // Create the MContractTaskReviewers
        MContractTaskReviewersDTO mContractTaskReviewersDTO = mContractTaskReviewersMapper.toDto(mContractTaskReviewers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractTaskReviewersMockMvc.perform(put("/api/m-contract-task-reviewers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskReviewersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTaskReviewers in the database
        List<MContractTaskReviewers> mContractTaskReviewersList = mContractTaskReviewersRepository.findAll();
        assertThat(mContractTaskReviewersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractTaskReviewers() throws Exception {
        // Initialize the database
        mContractTaskReviewersRepository.saveAndFlush(mContractTaskReviewers);

        int databaseSizeBeforeDelete = mContractTaskReviewersRepository.findAll().size();

        // Delete the mContractTaskReviewers
        restMContractTaskReviewersMockMvc.perform(delete("/api/m-contract-task-reviewers/{id}", mContractTaskReviewers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractTaskReviewers> mContractTaskReviewersList = mContractTaskReviewersRepository.findAll();
        assertThat(mContractTaskReviewersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
