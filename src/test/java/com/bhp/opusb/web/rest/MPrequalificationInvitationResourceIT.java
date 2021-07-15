package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationInvitation;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MPrequalificationInvitationRepository;
import com.bhp.opusb.service.MPrequalificationInvitationService;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;
import com.bhp.opusb.service.mapper.MPrequalificationInvitationMapper;
import com.bhp.opusb.service.dto.MPrequalificationInvitationCriteria;
import com.bhp.opusb.service.MPrequalificationInvitationQueryService;

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
 * Integration tests for the {@link MPrequalificationInvitationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationInvitationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationInvitationRepository mPrequalificationInvitationRepository;

    @Autowired
    private MPrequalificationInvitationMapper mPrequalificationInvitationMapper;

    @Autowired
    private MPrequalificationInvitationService mPrequalificationInvitationService;

    @Autowired
    private MPrequalificationInvitationQueryService mPrequalificationInvitationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationInvitationMockMvc;

    private MPrequalificationInvitation mPrequalificationInvitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationInvitation createEntity(EntityManager em) {
        MPrequalificationInvitation mPrequalificationInvitation = new MPrequalificationInvitation()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationInvitation.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mPrequalificationInvitation.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mPrequalificationInvitation.setBusinessSubCategory(cBusinessCategory);
        return mPrequalificationInvitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationInvitation createUpdatedEntity(EntityManager em) {
        MPrequalificationInvitation mPrequalificationInvitation = new MPrequalificationInvitation()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationInvitation.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        mPrequalificationInvitation.setBusinessCategory(cBusinessCategory);
        // Add required entity
        mPrequalificationInvitation.setBusinessSubCategory(cBusinessCategory);
        return mPrequalificationInvitation;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationInvitation() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationInvitationRepository.findAll().size();

        // Create the MPrequalificationInvitation
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO = mPrequalificationInvitationMapper.toDto(mPrequalificationInvitation);
        restMPrequalificationInvitationMockMvc.perform(post("/api/m-prequalification-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInvitationDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationInvitation in the database
        List<MPrequalificationInvitation> mPrequalificationInvitationList = mPrequalificationInvitationRepository.findAll();
        assertThat(mPrequalificationInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationInvitation testMPrequalificationInvitation = mPrequalificationInvitationList.get(mPrequalificationInvitationList.size() - 1);
        assertThat(testMPrequalificationInvitation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationInvitation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationInvitationRepository.findAll().size();

        // Create the MPrequalificationInvitation with an existing ID
        mPrequalificationInvitation.setId(1L);
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO = mPrequalificationInvitationMapper.toDto(mPrequalificationInvitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationInvitationMockMvc.perform(post("/api/m-prequalification-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationInvitation in the database
        List<MPrequalificationInvitation> mPrequalificationInvitationList = mPrequalificationInvitationRepository.findAll();
        assertThat(mPrequalificationInvitationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInvitations() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationInvitation() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get the mPrequalificationInvitation
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations/{id}", mPrequalificationInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationInvitation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationInvitationsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        Long id = mPrequalificationInvitation.getId();

        defaultMPrequalificationInvitationShouldBeFound("id.equals=" + id);
        defaultMPrequalificationInvitationShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationInvitationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationInvitationShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationInvitationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationInvitationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where uid equals to DEFAULT_UID
        defaultMPrequalificationInvitationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationInvitationList where uid equals to UPDATED_UID
        defaultMPrequalificationInvitationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where uid not equals to DEFAULT_UID
        defaultMPrequalificationInvitationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationInvitationList where uid not equals to UPDATED_UID
        defaultMPrequalificationInvitationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationInvitationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationInvitationList where uid equals to UPDATED_UID
        defaultMPrequalificationInvitationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where uid is not null
        defaultMPrequalificationInvitationShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationInvitationList where uid is null
        defaultMPrequalificationInvitationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationInvitationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationInvitationList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationInvitationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationInvitationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationInvitationList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationInvitationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationInvitationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationInvitationList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationInvitationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        // Get all the mPrequalificationInvitationList where active is not null
        defaultMPrequalificationInvitationShouldBeFound("active.specified=true");

        // Get all the mPrequalificationInvitationList where active is null
        defaultMPrequalificationInvitationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationInvitation.getPrequalification();
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationInvitationList where prequalification equals to prequalificationId
        defaultMPrequalificationInvitationShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationInvitationList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationInvitationShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationInvitation.getAdOrganization();
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationInvitationList where adOrganization equals to adOrganizationId
        defaultMPrequalificationInvitationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationInvitationList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationInvitationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = mPrequalificationInvitation.getBusinessCategory();
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mPrequalificationInvitationList where businessCategory equals to businessCategoryId
        defaultMPrequalificationInvitationShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mPrequalificationInvitationList where businessCategory equals to businessCategoryId + 1
        defaultMPrequalificationInvitationShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInvitationsByBusinessSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessSubCategory = mPrequalificationInvitation.getBusinessSubCategory();
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);
        Long businessSubCategoryId = businessSubCategory.getId();

        // Get all the mPrequalificationInvitationList where businessSubCategory equals to businessSubCategoryId
        defaultMPrequalificationInvitationShouldBeFound("businessSubCategoryId.equals=" + businessSubCategoryId);

        // Get all the mPrequalificationInvitationList where businessSubCategory equals to businessSubCategoryId + 1
        defaultMPrequalificationInvitationShouldNotBeFound("businessSubCategoryId.equals=" + (businessSubCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationInvitationShouldBeFound(String filter) throws Exception {
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationInvitationShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationInvitation() throws Exception {
        // Get the mPrequalificationInvitation
        restMPrequalificationInvitationMockMvc.perform(get("/api/m-prequalification-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationInvitation() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        int databaseSizeBeforeUpdate = mPrequalificationInvitationRepository.findAll().size();

        // Update the mPrequalificationInvitation
        MPrequalificationInvitation updatedMPrequalificationInvitation = mPrequalificationInvitationRepository.findById(mPrequalificationInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationInvitation are not directly saved in db
        em.detach(updatedMPrequalificationInvitation);
        updatedMPrequalificationInvitation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO = mPrequalificationInvitationMapper.toDto(updatedMPrequalificationInvitation);

        restMPrequalificationInvitationMockMvc.perform(put("/api/m-prequalification-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInvitationDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationInvitation in the database
        List<MPrequalificationInvitation> mPrequalificationInvitationList = mPrequalificationInvitationRepository.findAll();
        assertThat(mPrequalificationInvitationList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationInvitation testMPrequalificationInvitation = mPrequalificationInvitationList.get(mPrequalificationInvitationList.size() - 1);
        assertThat(testMPrequalificationInvitation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationInvitation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationInvitation() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationInvitationRepository.findAll().size();

        // Create the MPrequalificationInvitation
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO = mPrequalificationInvitationMapper.toDto(mPrequalificationInvitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationInvitationMockMvc.perform(put("/api/m-prequalification-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationInvitation in the database
        List<MPrequalificationInvitation> mPrequalificationInvitationList = mPrequalificationInvitationRepository.findAll();
        assertThat(mPrequalificationInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationInvitation() throws Exception {
        // Initialize the database
        mPrequalificationInvitationRepository.saveAndFlush(mPrequalificationInvitation);

        int databaseSizeBeforeDelete = mPrequalificationInvitationRepository.findAll().size();

        // Delete the mPrequalificationInvitation
        restMPrequalificationInvitationMockMvc.perform(delete("/api/m-prequalification-invitations/{id}", mPrequalificationInvitation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationInvitation> mPrequalificationInvitationList = mPrequalificationInvitationRepository.findAll();
        assertThat(mPrequalificationInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
