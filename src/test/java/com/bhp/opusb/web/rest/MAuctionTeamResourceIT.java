package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionTeam;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.repository.MAuctionTeamRepository;
import com.bhp.opusb.service.MAuctionTeamService;
import com.bhp.opusb.service.dto.MAuctionTeamDTO;
import com.bhp.opusb.service.mapper.MAuctionTeamMapper;
import com.bhp.opusb.service.dto.MAuctionTeamCriteria;
import com.bhp.opusb.service.MAuctionTeamQueryService;

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
 * Integration tests for the {@link MAuctionTeamResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionTeamResourceIT {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionTeamRepository mAuctionTeamRepository;

    @Autowired
    private MAuctionTeamMapper mAuctionTeamMapper;

    @Autowired
    private MAuctionTeamService mAuctionTeamService;

    @Autowired
    private MAuctionTeamQueryService mAuctionTeamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionTeamMockMvc;

    private MAuctionTeam mAuctionTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionTeam createEntity(EntityManager em) {
        MAuctionTeam mAuctionTeam = new MAuctionTeam()
            .role(DEFAULT_ROLE)
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
        mAuctionTeam.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuctionTeam.setUser(adUser);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionTeam.setAuction(mAuction);
        return mAuctionTeam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionTeam createUpdatedEntity(EntityManager em) {
        MAuctionTeam mAuctionTeam = new MAuctionTeam()
            .role(UPDATED_ROLE)
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
        mAuctionTeam.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuctionTeam.setUser(adUser);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createUpdatedEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionTeam.setAuction(mAuction);
        return mAuctionTeam;
    }

    @BeforeEach
    public void initTest() {
        mAuctionTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionTeam() throws Exception {
        int databaseSizeBeforeCreate = mAuctionTeamRepository.findAll().size();

        // Create the MAuctionTeam
        MAuctionTeamDTO mAuctionTeamDTO = mAuctionTeamMapper.toDto(mAuctionTeam);
        restMAuctionTeamMockMvc.perform(post("/api/m-auction-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionTeam in the database
        List<MAuctionTeam> mAuctionTeamList = mAuctionTeamRepository.findAll();
        assertThat(mAuctionTeamList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionTeam testMAuctionTeam = mAuctionTeamList.get(mAuctionTeamList.size() - 1);
        assertThat(testMAuctionTeam.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testMAuctionTeam.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionTeam.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionTeamRepository.findAll().size();

        // Create the MAuctionTeam with an existing ID
        mAuctionTeam.setId(1L);
        MAuctionTeamDTO mAuctionTeamDTO = mAuctionTeamMapper.toDto(mAuctionTeam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionTeamMockMvc.perform(post("/api/m-auction-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionTeam in the database
        List<MAuctionTeam> mAuctionTeamList = mAuctionTeamRepository.findAll();
        assertThat(mAuctionTeamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMAuctionTeams() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionTeam() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get the mAuctionTeam
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams/{id}", mAuctionTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionTeam.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionTeamsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        Long id = mAuctionTeam.getId();

        defaultMAuctionTeamShouldBeFound("id.equals=" + id);
        defaultMAuctionTeamShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionTeamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role equals to DEFAULT_ROLE
        defaultMAuctionTeamShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the mAuctionTeamList where role equals to UPDATED_ROLE
        defaultMAuctionTeamShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role not equals to DEFAULT_ROLE
        defaultMAuctionTeamShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the mAuctionTeamList where role not equals to UPDATED_ROLE
        defaultMAuctionTeamShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultMAuctionTeamShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the mAuctionTeamList where role equals to UPDATED_ROLE
        defaultMAuctionTeamShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role is not null
        defaultMAuctionTeamShouldBeFound("role.specified=true");

        // Get all the mAuctionTeamList where role is null
        defaultMAuctionTeamShouldNotBeFound("role.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleContainsSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role contains DEFAULT_ROLE
        defaultMAuctionTeamShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the mAuctionTeamList where role contains UPDATED_ROLE
        defaultMAuctionTeamShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where role does not contain DEFAULT_ROLE
        defaultMAuctionTeamShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the mAuctionTeamList where role does not contain UPDATED_ROLE
        defaultMAuctionTeamShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }


    @Test
    @Transactional
    public void getAllMAuctionTeamsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where uid equals to DEFAULT_UID
        defaultMAuctionTeamShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionTeamList where uid equals to UPDATED_UID
        defaultMAuctionTeamShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where uid not equals to DEFAULT_UID
        defaultMAuctionTeamShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionTeamList where uid not equals to UPDATED_UID
        defaultMAuctionTeamShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionTeamShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionTeamList where uid equals to UPDATED_UID
        defaultMAuctionTeamShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where uid is not null
        defaultMAuctionTeamShouldBeFound("uid.specified=true");

        // Get all the mAuctionTeamList where uid is null
        defaultMAuctionTeamShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where active equals to DEFAULT_ACTIVE
        defaultMAuctionTeamShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionTeamList where active equals to UPDATED_ACTIVE
        defaultMAuctionTeamShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionTeamShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionTeamList where active not equals to UPDATED_ACTIVE
        defaultMAuctionTeamShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionTeamShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionTeamList where active equals to UPDATED_ACTIVE
        defaultMAuctionTeamShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        // Get all the mAuctionTeamList where active is not null
        defaultMAuctionTeamShouldBeFound("active.specified=true");

        // Get all the mAuctionTeamList where active is null
        defaultMAuctionTeamShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionTeamsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionTeam.getAdOrganization();
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionTeamList where adOrganization equals to adOrganizationId
        defaultMAuctionTeamShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionTeamList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionTeamShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionTeamsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser user = mAuctionTeam.getUser();
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);
        Long userId = user.getId();

        // Get all the mAuctionTeamList where user equals to userId
        defaultMAuctionTeamShouldBeFound("userId.equals=" + userId);

        // Get all the mAuctionTeamList where user equals to userId + 1
        defaultMAuctionTeamShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionTeamsByAuctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuction auction = mAuctionTeam.getAuction();
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);
        Long auctionId = auction.getId();

        // Get all the mAuctionTeamList where auction equals to auctionId
        defaultMAuctionTeamShouldBeFound("auctionId.equals=" + auctionId);

        // Get all the mAuctionTeamList where auction equals to auctionId + 1
        defaultMAuctionTeamShouldNotBeFound("auctionId.equals=" + (auctionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionTeamShouldBeFound(String filter) throws Exception {
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionTeamShouldNotBeFound(String filter) throws Exception {
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionTeam() throws Exception {
        // Get the mAuctionTeam
        restMAuctionTeamMockMvc.perform(get("/api/m-auction-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionTeam() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        int databaseSizeBeforeUpdate = mAuctionTeamRepository.findAll().size();

        // Update the mAuctionTeam
        MAuctionTeam updatedMAuctionTeam = mAuctionTeamRepository.findById(mAuctionTeam.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionTeam are not directly saved in db
        em.detach(updatedMAuctionTeam);
        updatedMAuctionTeam
            .role(UPDATED_ROLE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionTeamDTO mAuctionTeamDTO = mAuctionTeamMapper.toDto(updatedMAuctionTeam);

        restMAuctionTeamMockMvc.perform(put("/api/m-auction-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionTeamDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionTeam in the database
        List<MAuctionTeam> mAuctionTeamList = mAuctionTeamRepository.findAll();
        assertThat(mAuctionTeamList).hasSize(databaseSizeBeforeUpdate);
        MAuctionTeam testMAuctionTeam = mAuctionTeamList.get(mAuctionTeamList.size() - 1);
        assertThat(testMAuctionTeam.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testMAuctionTeam.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionTeam.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionTeam() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionTeamRepository.findAll().size();

        // Create the MAuctionTeam
        MAuctionTeamDTO mAuctionTeamDTO = mAuctionTeamMapper.toDto(mAuctionTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionTeamMockMvc.perform(put("/api/m-auction-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionTeam in the database
        List<MAuctionTeam> mAuctionTeamList = mAuctionTeamRepository.findAll();
        assertThat(mAuctionTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionTeam() throws Exception {
        // Initialize the database
        mAuctionTeamRepository.saveAndFlush(mAuctionTeam);

        int databaseSizeBeforeDelete = mAuctionTeamRepository.findAll().size();

        // Delete the mAuctionTeam
        restMAuctionTeamMockMvc.perform(delete("/api/m-auction-teams/{id}", mAuctionTeam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionTeam> mAuctionTeamList = mAuctionTeamRepository.findAll();
        assertThat(mAuctionTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
