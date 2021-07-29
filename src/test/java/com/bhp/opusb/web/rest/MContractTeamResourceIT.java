package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractTeam;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.repository.MContractTeamRepository;
import com.bhp.opusb.service.MContractTeamService;
import com.bhp.opusb.service.dto.MContractTeamDTO;
import com.bhp.opusb.service.mapper.MContractTeamMapper;
import com.bhp.opusb.service.dto.MContractTeamCriteria;
import com.bhp.opusb.service.MContractTeamQueryService;

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
 * Integration tests for the {@link MContractTeamResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractTeamResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractTeamRepository mContractTeamRepository;

    @Autowired
    private MContractTeamMapper mContractTeamMapper;

    @Autowired
    private MContractTeamService mContractTeamService;

    @Autowired
    private MContractTeamQueryService mContractTeamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractTeamMockMvc;

    private MContractTeam mContractTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTeam createEntity(EntityManager em) {
        MContractTeam mContractTeam = new MContractTeam()
            .status(DEFAULT_STATUS)
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
        mContractTeam.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractTeam.setContract(mContract);
        return mContractTeam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTeam createUpdatedEntity(EntityManager em) {
        MContractTeam mContractTeam = new MContractTeam()
            .status(UPDATED_STATUS)
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
        mContractTeam.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractTeam.setContract(mContract);
        return mContractTeam;
    }

    @BeforeEach
    public void initTest() {
        mContractTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractTeam() throws Exception {
        int databaseSizeBeforeCreate = mContractTeamRepository.findAll().size();

        // Create the MContractTeam
        MContractTeamDTO mContractTeamDTO = mContractTeamMapper.toDto(mContractTeam);
        restMContractTeamMockMvc.perform(post("/api/m-contract-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractTeam in the database
        List<MContractTeam> mContractTeamList = mContractTeamRepository.findAll();
        assertThat(mContractTeamList).hasSize(databaseSizeBeforeCreate + 1);
        MContractTeam testMContractTeam = mContractTeamList.get(mContractTeamList.size() - 1);
        assertThat(testMContractTeam.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMContractTeam.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractTeam.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractTeamRepository.findAll().size();

        // Create the MContractTeam with an existing ID
        mContractTeam.setId(1L);
        MContractTeamDTO mContractTeamDTO = mContractTeamMapper.toDto(mContractTeam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractTeamMockMvc.perform(post("/api/m-contract-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTeam in the database
        List<MContractTeam> mContractTeamList = mContractTeamRepository.findAll();
        assertThat(mContractTeamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMContractTeams() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractTeam() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get the mContractTeam
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams/{id}", mContractTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractTeam.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractTeamsByIdFiltering() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        Long id = mContractTeam.getId();

        defaultMContractTeamShouldBeFound("id.equals=" + id);
        defaultMContractTeamShouldNotBeFound("id.notEquals=" + id);

        defaultMContractTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractTeamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractTeamsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status equals to DEFAULT_STATUS
        defaultMContractTeamShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mContractTeamList where status equals to UPDATED_STATUS
        defaultMContractTeamShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status not equals to DEFAULT_STATUS
        defaultMContractTeamShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mContractTeamList where status not equals to UPDATED_STATUS
        defaultMContractTeamShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMContractTeamShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mContractTeamList where status equals to UPDATED_STATUS
        defaultMContractTeamShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status is not null
        defaultMContractTeamShouldBeFound("status.specified=true");

        // Get all the mContractTeamList where status is null
        defaultMContractTeamShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTeamsByStatusContainsSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status contains DEFAULT_STATUS
        defaultMContractTeamShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mContractTeamList where status contains UPDATED_STATUS
        defaultMContractTeamShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where status does not contain DEFAULT_STATUS
        defaultMContractTeamShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mContractTeamList where status does not contain UPDATED_STATUS
        defaultMContractTeamShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMContractTeamsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where uid equals to DEFAULT_UID
        defaultMContractTeamShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractTeamList where uid equals to UPDATED_UID
        defaultMContractTeamShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where uid not equals to DEFAULT_UID
        defaultMContractTeamShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractTeamList where uid not equals to UPDATED_UID
        defaultMContractTeamShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractTeamShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractTeamList where uid equals to UPDATED_UID
        defaultMContractTeamShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where uid is not null
        defaultMContractTeamShouldBeFound("uid.specified=true");

        // Get all the mContractTeamList where uid is null
        defaultMContractTeamShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where active equals to DEFAULT_ACTIVE
        defaultMContractTeamShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractTeamList where active equals to UPDATED_ACTIVE
        defaultMContractTeamShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where active not equals to DEFAULT_ACTIVE
        defaultMContractTeamShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractTeamList where active not equals to UPDATED_ACTIVE
        defaultMContractTeamShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractTeamShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractTeamList where active equals to UPDATED_ACTIVE
        defaultMContractTeamShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        // Get all the mContractTeamList where active is not null
        defaultMContractTeamShouldBeFound("active.specified=true");

        // Get all the mContractTeamList where active is null
        defaultMContractTeamShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTeamsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractTeam.getAdOrganization();
        mContractTeamRepository.saveAndFlush(mContractTeam);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractTeamList where adOrganization equals to adOrganizationId
        defaultMContractTeamShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractTeamList where adOrganization equals to adOrganizationId + 1
        defaultMContractTeamShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTeamsByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractTeam.getContract();
        mContractTeamRepository.saveAndFlush(mContractTeam);
        Long contractId = contract.getId();

        // Get all the mContractTeamList where contract equals to contractId
        defaultMContractTeamShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractTeamList where contract equals to contractId + 1
        defaultMContractTeamShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractTeamShouldBeFound(String filter) throws Exception {
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractTeamShouldNotBeFound(String filter) throws Exception {
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractTeam() throws Exception {
        // Get the mContractTeam
        restMContractTeamMockMvc.perform(get("/api/m-contract-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractTeam() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        int databaseSizeBeforeUpdate = mContractTeamRepository.findAll().size();

        // Update the mContractTeam
        MContractTeam updatedMContractTeam = mContractTeamRepository.findById(mContractTeam.getId()).get();
        // Disconnect from session so that the updates on updatedMContractTeam are not directly saved in db
        em.detach(updatedMContractTeam);
        updatedMContractTeam
            .status(UPDATED_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractTeamDTO mContractTeamDTO = mContractTeamMapper.toDto(updatedMContractTeam);

        restMContractTeamMockMvc.perform(put("/api/m-contract-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamDTO)))
            .andExpect(status().isOk());

        // Validate the MContractTeam in the database
        List<MContractTeam> mContractTeamList = mContractTeamRepository.findAll();
        assertThat(mContractTeamList).hasSize(databaseSizeBeforeUpdate);
        MContractTeam testMContractTeam = mContractTeamList.get(mContractTeamList.size() - 1);
        assertThat(testMContractTeam.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMContractTeam.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractTeam.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractTeam() throws Exception {
        int databaseSizeBeforeUpdate = mContractTeamRepository.findAll().size();

        // Create the MContractTeam
        MContractTeamDTO mContractTeamDTO = mContractTeamMapper.toDto(mContractTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractTeamMockMvc.perform(put("/api/m-contract-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTeam in the database
        List<MContractTeam> mContractTeamList = mContractTeamRepository.findAll();
        assertThat(mContractTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractTeam() throws Exception {
        // Initialize the database
        mContractTeamRepository.saveAndFlush(mContractTeam);

        int databaseSizeBeforeDelete = mContractTeamRepository.findAll().size();

        // Delete the mContractTeam
        restMContractTeamMockMvc.perform(delete("/api/m-contract-teams/{id}", mContractTeam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractTeam> mContractTeamList = mContractTeamRepository.findAll();
        assertThat(mContractTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
