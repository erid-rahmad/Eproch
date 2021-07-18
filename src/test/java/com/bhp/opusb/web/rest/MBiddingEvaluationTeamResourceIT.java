package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvaluationTeam;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.repository.MBiddingEvaluationTeamRepository;
import com.bhp.opusb.service.MBiddingEvaluationTeamService;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationTeamMapper;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamCriteria;
import com.bhp.opusb.service.MBiddingEvaluationTeamQueryService;

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
 * Integration tests for the {@link MBiddingEvaluationTeamResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvaluationTeamResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingEvaluationTeamRepository mBiddingEvaluationTeamRepository;

    @Autowired
    private MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper;

    @Autowired
    private MBiddingEvaluationTeamService mBiddingEvaluationTeamService;

    @Autowired
    private MBiddingEvaluationTeamQueryService mBiddingEvaluationTeamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvaluationTeamMockMvc;

    private MBiddingEvaluationTeam mBiddingEvaluationTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluationTeam createEntity(EntityManager em) {
        MBiddingEvaluationTeam mBiddingEvaluationTeam = new MBiddingEvaluationTeam()
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
        mBiddingEvaluationTeam.setAdOrganization(aDOrganization);
        return mBiddingEvaluationTeam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvaluationTeam createUpdatedEntity(EntityManager em) {
        MBiddingEvaluationTeam mBiddingEvaluationTeam = new MBiddingEvaluationTeam()
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
        mBiddingEvaluationTeam.setAdOrganization(aDOrganization);
        return mBiddingEvaluationTeam;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvaluationTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluationTeam() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationTeamRepository.findAll().size();

        // Create the MBiddingEvaluationTeam
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO = mBiddingEvaluationTeamMapper.toDto(mBiddingEvaluationTeam);
        restMBiddingEvaluationTeamMockMvc.perform(post("/api/m-bidding-evaluation-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvaluationTeam in the database
        List<MBiddingEvaluationTeam> mBiddingEvaluationTeamList = mBiddingEvaluationTeamRepository.findAll();
        assertThat(mBiddingEvaluationTeamList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvaluationTeam testMBiddingEvaluationTeam = mBiddingEvaluationTeamList.get(mBiddingEvaluationTeamList.size() - 1);
        assertThat(testMBiddingEvaluationTeam.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMBiddingEvaluationTeam.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvaluationTeam.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingEvaluationTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvaluationTeamRepository.findAll().size();

        // Create the MBiddingEvaluationTeam with an existing ID
        mBiddingEvaluationTeam.setId(1L);
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO = mBiddingEvaluationTeamMapper.toDto(mBiddingEvaluationTeam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvaluationTeamMockMvc.perform(post("/api/m-bidding-evaluation-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluationTeam in the database
        List<MBiddingEvaluationTeam> mBiddingEvaluationTeamList = mBiddingEvaluationTeamRepository.findAll();
        assertThat(mBiddingEvaluationTeamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeams() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluationTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvaluationTeam() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get the mBiddingEvaluationTeam
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams/{id}", mBiddingEvaluationTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvaluationTeam.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingEvaluationTeamsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        Long id = mBiddingEvaluationTeam.getId();

        defaultMBiddingEvaluationTeamShouldBeFound("id.equals=" + id);
        defaultMBiddingEvaluationTeamShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvaluationTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvaluationTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvaluationTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvaluationTeamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status equals to DEFAULT_STATUS
        defaultMBiddingEvaluationTeamShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationTeamList where status equals to UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status not equals to DEFAULT_STATUS
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationTeamList where status not equals to UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mBiddingEvaluationTeamList where status equals to UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status is not null
        defaultMBiddingEvaluationTeamShouldBeFound("status.specified=true");

        // Get all the mBiddingEvaluationTeamList where status is null
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status contains DEFAULT_STATUS
        defaultMBiddingEvaluationTeamShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationTeamList where status contains UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where status does not contain DEFAULT_STATUS
        defaultMBiddingEvaluationTeamShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mBiddingEvaluationTeamList where status does not contain UPDATED_STATUS
        defaultMBiddingEvaluationTeamShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where uid equals to DEFAULT_UID
        defaultMBiddingEvaluationTeamShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationTeamList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationTeamShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where uid not equals to DEFAULT_UID
        defaultMBiddingEvaluationTeamShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvaluationTeamList where uid not equals to UPDATED_UID
        defaultMBiddingEvaluationTeamShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvaluationTeamShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvaluationTeamList where uid equals to UPDATED_UID
        defaultMBiddingEvaluationTeamShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where uid is not null
        defaultMBiddingEvaluationTeamShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvaluationTeamList where uid is null
        defaultMBiddingEvaluationTeamShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationTeamShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationTeamList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationTeamShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvaluationTeamShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvaluationTeamList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationTeamShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvaluationTeamShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvaluationTeamList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvaluationTeamShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        // Get all the mBiddingEvaluationTeamList where active is not null
        defaultMBiddingEvaluationTeamShouldBeFound("active.specified=true");

        // Get all the mBiddingEvaluationTeamList where active is null
        defaultMBiddingEvaluationTeamShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvaluationTeam.getAdOrganization();
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvaluationTeamList where adOrganization equals to adOrganizationId
        defaultMBiddingEvaluationTeamShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvaluationTeamList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvaluationTeamShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByBiddingIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);
        MBidding bidding = MBiddingResourceIT.createEntity(em);
        em.persist(bidding);
        em.flush();
        mBiddingEvaluationTeam.setBidding(bidding);
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);
        Long biddingId = bidding.getId();

        // Get all the mBiddingEvaluationTeamList where bidding equals to biddingId
        defaultMBiddingEvaluationTeamShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingEvaluationTeamList where bidding equals to biddingId + 1
        defaultMBiddingEvaluationTeamShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvaluationTeamsByPrequalificationIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);
        MPrequalificationInformation prequalification = MPrequalificationInformationResourceIT.createEntity(em);
        em.persist(prequalification);
        em.flush();
        mBiddingEvaluationTeam.setPrequalification(prequalification);
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);
        Long prequalificationId = prequalification.getId();

        // Get all the mBiddingEvaluationTeamList where prequalification equals to prequalificationId
        defaultMBiddingEvaluationTeamShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mBiddingEvaluationTeamList where prequalification equals to prequalificationId + 1
        defaultMBiddingEvaluationTeamShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvaluationTeamShouldBeFound(String filter) throws Exception {
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvaluationTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvaluationTeamShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvaluationTeam() throws Exception {
        // Get the mBiddingEvaluationTeam
        restMBiddingEvaluationTeamMockMvc.perform(get("/api/m-bidding-evaluation-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvaluationTeam() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        int databaseSizeBeforeUpdate = mBiddingEvaluationTeamRepository.findAll().size();

        // Update the mBiddingEvaluationTeam
        MBiddingEvaluationTeam updatedMBiddingEvaluationTeam = mBiddingEvaluationTeamRepository.findById(mBiddingEvaluationTeam.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvaluationTeam are not directly saved in db
        em.detach(updatedMBiddingEvaluationTeam);
        updatedMBiddingEvaluationTeam
            .status(UPDATED_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO = mBiddingEvaluationTeamMapper.toDto(updatedMBiddingEvaluationTeam);

        restMBiddingEvaluationTeamMockMvc.perform(put("/api/m-bidding-evaluation-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationTeamDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvaluationTeam in the database
        List<MBiddingEvaluationTeam> mBiddingEvaluationTeamList = mBiddingEvaluationTeamRepository.findAll();
        assertThat(mBiddingEvaluationTeamList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvaluationTeam testMBiddingEvaluationTeam = mBiddingEvaluationTeamList.get(mBiddingEvaluationTeamList.size() - 1);
        assertThat(testMBiddingEvaluationTeam.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMBiddingEvaluationTeam.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvaluationTeam.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvaluationTeam() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvaluationTeamRepository.findAll().size();

        // Create the MBiddingEvaluationTeam
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO = mBiddingEvaluationTeamMapper.toDto(mBiddingEvaluationTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvaluationTeamMockMvc.perform(put("/api/m-bidding-evaluation-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvaluationTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvaluationTeam in the database
        List<MBiddingEvaluationTeam> mBiddingEvaluationTeamList = mBiddingEvaluationTeamRepository.findAll();
        assertThat(mBiddingEvaluationTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvaluationTeam() throws Exception {
        // Initialize the database
        mBiddingEvaluationTeamRepository.saveAndFlush(mBiddingEvaluationTeam);

        int databaseSizeBeforeDelete = mBiddingEvaluationTeamRepository.findAll().size();

        // Delete the mBiddingEvaluationTeam
        restMBiddingEvaluationTeamMockMvc.perform(delete("/api/m-bidding-evaluation-teams/{id}", mBiddingEvaluationTeam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvaluationTeam> mBiddingEvaluationTeamList = mBiddingEvaluationTeamRepository.findAll();
        assertThat(mBiddingEvaluationTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
