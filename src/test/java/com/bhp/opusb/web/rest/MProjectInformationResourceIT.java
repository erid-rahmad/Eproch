package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProjectInformation;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.service.MProjectInformationService;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.mapper.MProjectInformationMapper;
import com.bhp.opusb.service.dto.MProjectInformationCriteria;
import com.bhp.opusb.service.MProjectInformationQueryService;

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
 * Integration tests for the {@link MProjectInformationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProjectInformationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MProjectInformationRepository mProjectInformationRepository;

    @Autowired
    private MProjectInformationMapper mProjectInformationMapper;

    @Autowired
    private MProjectInformationService mProjectInformationService;

    @Autowired
    private MProjectInformationQueryService mProjectInformationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProjectInformationMockMvc;

    private MProjectInformation mProjectInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProjectInformation createEntity(EntityManager em) {
        MProjectInformation mProjectInformation = new MProjectInformation()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mProjectInformation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProjectInformation.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mProjectInformation.setAttachment(cAttachment);
        return mProjectInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProjectInformation createUpdatedEntity(EntityManager em) {
        MProjectInformation mProjectInformation = new MProjectInformation()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mProjectInformation.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProjectInformation.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mProjectInformation.setAttachment(cAttachment);
        return mProjectInformation;
    }

    @BeforeEach
    public void initTest() {
        mProjectInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProjectInformation() throws Exception {
        int databaseSizeBeforeCreate = mProjectInformationRepository.findAll().size();

        // Create the MProjectInformation
        MProjectInformationDTO mProjectInformationDTO = mProjectInformationMapper.toDto(mProjectInformation);
        restMProjectInformationMockMvc.perform(post("/api/m-project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProjectInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the MProjectInformation in the database
        List<MProjectInformation> mProjectInformationList = mProjectInformationRepository.findAll();
        assertThat(mProjectInformationList).hasSize(databaseSizeBeforeCreate + 1);
        MProjectInformation testMProjectInformation = mProjectInformationList.get(mProjectInformationList.size() - 1);
        assertThat(testMProjectInformation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProjectInformation.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMProjectInformation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMProjectInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProjectInformationRepository.findAll().size();

        // Create the MProjectInformation with an existing ID
        mProjectInformation.setId(1L);
        MProjectInformationDTO mProjectInformationDTO = mProjectInformationMapper.toDto(mProjectInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProjectInformationMockMvc.perform(post("/api/m-project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProjectInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProjectInformation in the database
        List<MProjectInformation> mProjectInformationList = mProjectInformationRepository.findAll();
        assertThat(mProjectInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMProjectInformations() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProjectInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMProjectInformation() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get the mProjectInformation
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations/{id}", mProjectInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProjectInformation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getMProjectInformationsByIdFiltering() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        Long id = mProjectInformation.getId();

        defaultMProjectInformationShouldBeFound("id.equals=" + id);
        defaultMProjectInformationShouldNotBeFound("id.notEquals=" + id);

        defaultMProjectInformationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProjectInformationShouldNotBeFound("id.greaterThan=" + id);

        defaultMProjectInformationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProjectInformationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProjectInformationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where uid equals to DEFAULT_UID
        defaultMProjectInformationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProjectInformationList where uid equals to UPDATED_UID
        defaultMProjectInformationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where uid not equals to DEFAULT_UID
        defaultMProjectInformationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProjectInformationList where uid not equals to UPDATED_UID
        defaultMProjectInformationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProjectInformationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProjectInformationList where uid equals to UPDATED_UID
        defaultMProjectInformationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where uid is not null
        defaultMProjectInformationShouldBeFound("uid.specified=true");

        // Get all the mProjectInformationList where uid is null
        defaultMProjectInformationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where active equals to DEFAULT_ACTIVE
        defaultMProjectInformationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProjectInformationList where active equals to UPDATED_ACTIVE
        defaultMProjectInformationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where active not equals to DEFAULT_ACTIVE
        defaultMProjectInformationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProjectInformationList where active not equals to UPDATED_ACTIVE
        defaultMProjectInformationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProjectInformationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProjectInformationList where active equals to UPDATED_ACTIVE
        defaultMProjectInformationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where active is not null
        defaultMProjectInformationShouldBeFound("active.specified=true");

        // Get all the mProjectInformationList where active is null
        defaultMProjectInformationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name equals to DEFAULT_NAME
        defaultMProjectInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mProjectInformationList where name equals to UPDATED_NAME
        defaultMProjectInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name not equals to DEFAULT_NAME
        defaultMProjectInformationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mProjectInformationList where name not equals to UPDATED_NAME
        defaultMProjectInformationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMProjectInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mProjectInformationList where name equals to UPDATED_NAME
        defaultMProjectInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name is not null
        defaultMProjectInformationShouldBeFound("name.specified=true");

        // Get all the mProjectInformationList where name is null
        defaultMProjectInformationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProjectInformationsByNameContainsSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name contains DEFAULT_NAME
        defaultMProjectInformationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mProjectInformationList where name contains UPDATED_NAME
        defaultMProjectInformationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProjectInformationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        // Get all the mProjectInformationList where name does not contain DEFAULT_NAME
        defaultMProjectInformationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mProjectInformationList where name does not contain UPDATED_NAME
        defaultMProjectInformationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMProjectInformationsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mProjectInformation.getBidding();
        mProjectInformationRepository.saveAndFlush(mProjectInformation);
        Long biddingId = bidding.getId();

        // Get all the mProjectInformationList where bidding equals to biddingId
        defaultMProjectInformationShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mProjectInformationList where bidding equals to biddingId + 1
        defaultMProjectInformationShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMProjectInformationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProjectInformation.getAdOrganization();
        mProjectInformationRepository.saveAndFlush(mProjectInformation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProjectInformationList where adOrganization equals to adOrganizationId
        defaultMProjectInformationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProjectInformationList where adOrganization equals to adOrganizationId + 1
        defaultMProjectInformationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProjectInformationsByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mProjectInformation.getAttachment();
        mProjectInformationRepository.saveAndFlush(mProjectInformation);
        Long attachmentId = attachment.getId();

        // Get all the mProjectInformationList where attachment equals to attachmentId
        defaultMProjectInformationShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mProjectInformationList where attachment equals to attachmentId + 1
        defaultMProjectInformationShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProjectInformationShouldBeFound(String filter) throws Exception {
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProjectInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProjectInformationShouldNotBeFound(String filter) throws Exception {
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProjectInformation() throws Exception {
        // Get the mProjectInformation
        restMProjectInformationMockMvc.perform(get("/api/m-project-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProjectInformation() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        int databaseSizeBeforeUpdate = mProjectInformationRepository.findAll().size();

        // Update the mProjectInformation
        MProjectInformation updatedMProjectInformation = mProjectInformationRepository.findById(mProjectInformation.getId()).get();
        // Disconnect from session so that the updates on updatedMProjectInformation are not directly saved in db
        em.detach(updatedMProjectInformation);
        updatedMProjectInformation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME);
        MProjectInformationDTO mProjectInformationDTO = mProjectInformationMapper.toDto(updatedMProjectInformation);

        restMProjectInformationMockMvc.perform(put("/api/m-project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProjectInformationDTO)))
            .andExpect(status().isOk());

        // Validate the MProjectInformation in the database
        List<MProjectInformation> mProjectInformationList = mProjectInformationRepository.findAll();
        assertThat(mProjectInformationList).hasSize(databaseSizeBeforeUpdate);
        MProjectInformation testMProjectInformation = mProjectInformationList.get(mProjectInformationList.size() - 1);
        assertThat(testMProjectInformation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProjectInformation.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMProjectInformation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMProjectInformation() throws Exception {
        int databaseSizeBeforeUpdate = mProjectInformationRepository.findAll().size();

        // Create the MProjectInformation
        MProjectInformationDTO mProjectInformationDTO = mProjectInformationMapper.toDto(mProjectInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProjectInformationMockMvc.perform(put("/api/m-project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProjectInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProjectInformation in the database
        List<MProjectInformation> mProjectInformationList = mProjectInformationRepository.findAll();
        assertThat(mProjectInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProjectInformation() throws Exception {
        // Initialize the database
        mProjectInformationRepository.saveAndFlush(mProjectInformation);

        int databaseSizeBeforeDelete = mProjectInformationRepository.findAll().size();

        // Delete the mProjectInformation
        restMProjectInformationMockMvc.perform(delete("/api/m-project-informations/{id}", mProjectInformation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProjectInformation> mProjectInformationList = mProjectInformationRepository.findAll();
        assertThat(mProjectInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
