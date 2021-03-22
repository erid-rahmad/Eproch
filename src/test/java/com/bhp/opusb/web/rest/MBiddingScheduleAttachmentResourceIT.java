package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingScheduleAttachment;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MBiddingScheduleAttachmentRepository;
import com.bhp.opusb.service.MBiddingScheduleAttachmentService;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleAttachmentMapper;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentCriteria;
import com.bhp.opusb.service.MBiddingScheduleAttachmentQueryService;

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
 * Integration tests for the {@link MBiddingScheduleAttachmentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingScheduleAttachmentResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingScheduleAttachmentRepository mBiddingScheduleAttachmentRepository;

    @Autowired
    private MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper;

    @Autowired
    private MBiddingScheduleAttachmentService mBiddingScheduleAttachmentService;

    @Autowired
    private MBiddingScheduleAttachmentQueryService mBiddingScheduleAttachmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingScheduleAttachmentMockMvc;

    private MBiddingScheduleAttachment mBiddingScheduleAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingScheduleAttachment createEntity(EntityManager em) {
        MBiddingScheduleAttachment mBiddingScheduleAttachment = new MBiddingScheduleAttachment()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mBiddingScheduleAttachment.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingScheduleAttachment.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingScheduleAttachment.setBiddingSchedule(mBiddingSchedule);
        return mBiddingScheduleAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingScheduleAttachment createUpdatedEntity(EntityManager em) {
        MBiddingScheduleAttachment mBiddingScheduleAttachment = new MBiddingScheduleAttachment()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mBiddingScheduleAttachment.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingScheduleAttachment.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingScheduleAttachment.setBiddingSchedule(mBiddingSchedule);
        return mBiddingScheduleAttachment;
    }

    @BeforeEach
    public void initTest() {
        mBiddingScheduleAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingScheduleAttachment() throws Exception {
        int databaseSizeBeforeCreate = mBiddingScheduleAttachmentRepository.findAll().size();

        // Create the MBiddingScheduleAttachment
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO = mBiddingScheduleAttachmentMapper.toDto(mBiddingScheduleAttachment);
        restMBiddingScheduleAttachmentMockMvc.perform(post("/api/m-bidding-schedule-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingScheduleAttachment in the database
        List<MBiddingScheduleAttachment> mBiddingScheduleAttachmentList = mBiddingScheduleAttachmentRepository.findAll();
        assertThat(mBiddingScheduleAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingScheduleAttachment testMBiddingScheduleAttachment = mBiddingScheduleAttachmentList.get(mBiddingScheduleAttachmentList.size() - 1);
        assertThat(testMBiddingScheduleAttachment.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingScheduleAttachment.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingScheduleAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingScheduleAttachmentRepository.findAll().size();

        // Create the MBiddingScheduleAttachment with an existing ID
        mBiddingScheduleAttachment.setId(1L);
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO = mBiddingScheduleAttachmentMapper.toDto(mBiddingScheduleAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingScheduleAttachmentMockMvc.perform(post("/api/m-bidding-schedule-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingScheduleAttachment in the database
        List<MBiddingScheduleAttachment> mBiddingScheduleAttachmentList = mBiddingScheduleAttachmentRepository.findAll();
        assertThat(mBiddingScheduleAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachments() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingScheduleAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingScheduleAttachment() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get the mBiddingScheduleAttachment
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments/{id}", mBiddingScheduleAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingScheduleAttachment.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingScheduleAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        Long id = mBiddingScheduleAttachment.getId();

        defaultMBiddingScheduleAttachmentShouldBeFound("id.equals=" + id);
        defaultMBiddingScheduleAttachmentShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingScheduleAttachmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingScheduleAttachmentShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingScheduleAttachmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingScheduleAttachmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where uid equals to DEFAULT_UID
        defaultMBiddingScheduleAttachmentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingScheduleAttachmentList where uid equals to UPDATED_UID
        defaultMBiddingScheduleAttachmentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where uid not equals to DEFAULT_UID
        defaultMBiddingScheduleAttachmentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingScheduleAttachmentList where uid not equals to UPDATED_UID
        defaultMBiddingScheduleAttachmentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingScheduleAttachmentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingScheduleAttachmentList where uid equals to UPDATED_UID
        defaultMBiddingScheduleAttachmentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where uid is not null
        defaultMBiddingScheduleAttachmentShouldBeFound("uid.specified=true");

        // Get all the mBiddingScheduleAttachmentList where uid is null
        defaultMBiddingScheduleAttachmentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where active equals to DEFAULT_ACTIVE
        defaultMBiddingScheduleAttachmentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingScheduleAttachmentList where active equals to UPDATED_ACTIVE
        defaultMBiddingScheduleAttachmentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingScheduleAttachmentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingScheduleAttachmentList where active not equals to UPDATED_ACTIVE
        defaultMBiddingScheduleAttachmentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingScheduleAttachmentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingScheduleAttachmentList where active equals to UPDATED_ACTIVE
        defaultMBiddingScheduleAttachmentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        // Get all the mBiddingScheduleAttachmentList where active is not null
        defaultMBiddingScheduleAttachmentShouldBeFound("active.specified=true");

        // Get all the mBiddingScheduleAttachmentList where active is null
        defaultMBiddingScheduleAttachmentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByCAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment cAttachment = mBiddingScheduleAttachment.getCAttachment();
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);
        Long cAttachmentId = cAttachment.getId();

        // Get all the mBiddingScheduleAttachmentList where cAttachment equals to cAttachmentId
        defaultMBiddingScheduleAttachmentShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the mBiddingScheduleAttachmentList where cAttachment equals to cAttachmentId + 1
        defaultMBiddingScheduleAttachmentShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingScheduleAttachment.getAdOrganization();
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingScheduleAttachmentList where adOrganization equals to adOrganizationId
        defaultMBiddingScheduleAttachmentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingScheduleAttachmentList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingScheduleAttachmentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingScheduleAttachmentsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mBiddingScheduleAttachment.getBiddingSchedule();
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mBiddingScheduleAttachmentList where biddingSchedule equals to biddingScheduleId
        defaultMBiddingScheduleAttachmentShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mBiddingScheduleAttachmentList where biddingSchedule equals to biddingScheduleId + 1
        defaultMBiddingScheduleAttachmentShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingScheduleAttachmentShouldBeFound(String filter) throws Exception {
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingScheduleAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingScheduleAttachmentShouldNotBeFound(String filter) throws Exception {
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingScheduleAttachment() throws Exception {
        // Get the mBiddingScheduleAttachment
        restMBiddingScheduleAttachmentMockMvc.perform(get("/api/m-bidding-schedule-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingScheduleAttachment() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        int databaseSizeBeforeUpdate = mBiddingScheduleAttachmentRepository.findAll().size();

        // Update the mBiddingScheduleAttachment
        MBiddingScheduleAttachment updatedMBiddingScheduleAttachment = mBiddingScheduleAttachmentRepository.findById(mBiddingScheduleAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingScheduleAttachment are not directly saved in db
        em.detach(updatedMBiddingScheduleAttachment);
        updatedMBiddingScheduleAttachment
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO = mBiddingScheduleAttachmentMapper.toDto(updatedMBiddingScheduleAttachment);

        restMBiddingScheduleAttachmentMockMvc.perform(put("/api/m-bidding-schedule-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingScheduleAttachment in the database
        List<MBiddingScheduleAttachment> mBiddingScheduleAttachmentList = mBiddingScheduleAttachmentRepository.findAll();
        assertThat(mBiddingScheduleAttachmentList).hasSize(databaseSizeBeforeUpdate);
        MBiddingScheduleAttachment testMBiddingScheduleAttachment = mBiddingScheduleAttachmentList.get(mBiddingScheduleAttachmentList.size() - 1);
        assertThat(testMBiddingScheduleAttachment.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingScheduleAttachment.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingScheduleAttachment() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingScheduleAttachmentRepository.findAll().size();

        // Create the MBiddingScheduleAttachment
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO = mBiddingScheduleAttachmentMapper.toDto(mBiddingScheduleAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingScheduleAttachmentMockMvc.perform(put("/api/m-bidding-schedule-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingScheduleAttachment in the database
        List<MBiddingScheduleAttachment> mBiddingScheduleAttachmentList = mBiddingScheduleAttachmentRepository.findAll();
        assertThat(mBiddingScheduleAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingScheduleAttachment() throws Exception {
        // Initialize the database
        mBiddingScheduleAttachmentRepository.saveAndFlush(mBiddingScheduleAttachment);

        int databaseSizeBeforeDelete = mBiddingScheduleAttachmentRepository.findAll().size();

        // Delete the mBiddingScheduleAttachment
        restMBiddingScheduleAttachmentMockMvc.perform(delete("/api/m-bidding-schedule-attachments/{id}", mBiddingScheduleAttachment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingScheduleAttachment> mBiddingScheduleAttachmentList = mBiddingScheduleAttachmentRepository.findAll();
        assertThat(mBiddingScheduleAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
