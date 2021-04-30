package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPreBidMeetingAttachment;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.repository.MPreBidMeetingAttachmentRepository;
import com.bhp.opusb.service.MPreBidMeetingAttachmentService;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingAttachmentMapper;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentCriteria;
import com.bhp.opusb.service.MPreBidMeetingAttachmentQueryService;

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
 * Integration tests for the {@link MPreBidMeetingAttachmentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPreBidMeetingAttachmentResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPreBidMeetingAttachmentRepository mPreBidMeetingAttachmentRepository;

    @Autowired
    private MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper;

    @Autowired
    private MPreBidMeetingAttachmentService mPreBidMeetingAttachmentService;

    @Autowired
    private MPreBidMeetingAttachmentQueryService mPreBidMeetingAttachmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPreBidMeetingAttachmentMockMvc;

    private MPreBidMeetingAttachment mPreBidMeetingAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeetingAttachment createEntity(EntityManager em) {
        MPreBidMeetingAttachment mPreBidMeetingAttachment = new MPreBidMeetingAttachment()
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
        mPreBidMeetingAttachment.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreBidMeetingAttachment.setAdOrganization(aDOrganization);
        // Add required entity
        MPreBidMeeting mPreBidMeeting;
        if (TestUtil.findAll(em, MPreBidMeeting.class).isEmpty()) {
            mPreBidMeeting = MPreBidMeetingResourceIT.createEntity(em);
            em.persist(mPreBidMeeting);
            em.flush();
        } else {
            mPreBidMeeting = TestUtil.findAll(em, MPreBidMeeting.class).get(0);
        }
        mPreBidMeetingAttachment.setPreBidMeeting(mPreBidMeeting);
        return mPreBidMeetingAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeetingAttachment createUpdatedEntity(EntityManager em) {
        MPreBidMeetingAttachment mPreBidMeetingAttachment = new MPreBidMeetingAttachment()
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
        mPreBidMeetingAttachment.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreBidMeetingAttachment.setAdOrganization(aDOrganization);
        // Add required entity
        MPreBidMeeting mPreBidMeeting;
        if (TestUtil.findAll(em, MPreBidMeeting.class).isEmpty()) {
            mPreBidMeeting = MPreBidMeetingResourceIT.createUpdatedEntity(em);
            em.persist(mPreBidMeeting);
            em.flush();
        } else {
            mPreBidMeeting = TestUtil.findAll(em, MPreBidMeeting.class).get(0);
        }
        mPreBidMeetingAttachment.setPreBidMeeting(mPreBidMeeting);
        return mPreBidMeetingAttachment;
    }

    @BeforeEach
    public void initTest() {
        mPreBidMeetingAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPreBidMeetingAttachment() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingAttachmentRepository.findAll().size();

        // Create the MPreBidMeetingAttachment
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO = mPreBidMeetingAttachmentMapper.toDto(mPreBidMeetingAttachment);
        restMPreBidMeetingAttachmentMockMvc.perform(post("/api/m-pre-bid-meeting-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the MPreBidMeetingAttachment in the database
        List<MPreBidMeetingAttachment> mPreBidMeetingAttachmentList = mPreBidMeetingAttachmentRepository.findAll();
        assertThat(mPreBidMeetingAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        MPreBidMeetingAttachment testMPreBidMeetingAttachment = mPreBidMeetingAttachmentList.get(mPreBidMeetingAttachmentList.size() - 1);
        assertThat(testMPreBidMeetingAttachment.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPreBidMeetingAttachment.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPreBidMeetingAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingAttachmentRepository.findAll().size();

        // Create the MPreBidMeetingAttachment with an existing ID
        mPreBidMeetingAttachment.setId(1L);
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO = mPreBidMeetingAttachmentMapper.toDto(mPreBidMeetingAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPreBidMeetingAttachmentMockMvc.perform(post("/api/m-pre-bid-meeting-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeetingAttachment in the database
        List<MPreBidMeetingAttachment> mPreBidMeetingAttachmentList = mPreBidMeetingAttachmentRepository.findAll();
        assertThat(mPreBidMeetingAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachments() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeetingAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPreBidMeetingAttachment() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get the mPreBidMeetingAttachment
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments/{id}", mPreBidMeetingAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPreBidMeetingAttachment.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPreBidMeetingAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        Long id = mPreBidMeetingAttachment.getId();

        defaultMPreBidMeetingAttachmentShouldBeFound("id.equals=" + id);
        defaultMPreBidMeetingAttachmentShouldNotBeFound("id.notEquals=" + id);

        defaultMPreBidMeetingAttachmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPreBidMeetingAttachmentShouldNotBeFound("id.greaterThan=" + id);

        defaultMPreBidMeetingAttachmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPreBidMeetingAttachmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where uid equals to DEFAULT_UID
        defaultMPreBidMeetingAttachmentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingAttachmentList where uid equals to UPDATED_UID
        defaultMPreBidMeetingAttachmentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where uid not equals to DEFAULT_UID
        defaultMPreBidMeetingAttachmentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingAttachmentList where uid not equals to UPDATED_UID
        defaultMPreBidMeetingAttachmentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPreBidMeetingAttachmentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPreBidMeetingAttachmentList where uid equals to UPDATED_UID
        defaultMPreBidMeetingAttachmentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where uid is not null
        defaultMPreBidMeetingAttachmentShouldBeFound("uid.specified=true");

        // Get all the mPreBidMeetingAttachmentList where uid is null
        defaultMPreBidMeetingAttachmentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where active equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingAttachmentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingAttachmentList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingAttachmentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where active not equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingAttachmentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingAttachmentList where active not equals to UPDATED_ACTIVE
        defaultMPreBidMeetingAttachmentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPreBidMeetingAttachmentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPreBidMeetingAttachmentList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingAttachmentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        // Get all the mPreBidMeetingAttachmentList where active is not null
        defaultMPreBidMeetingAttachmentShouldBeFound("active.specified=true");

        // Get all the mPreBidMeetingAttachmentList where active is null
        defaultMPreBidMeetingAttachmentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByCAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment cAttachment = mPreBidMeetingAttachment.getCAttachment();
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);
        Long cAttachmentId = cAttachment.getId();

        // Get all the mPreBidMeetingAttachmentList where cAttachment equals to cAttachmentId
        defaultMPreBidMeetingAttachmentShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the mPreBidMeetingAttachmentList where cAttachment equals to cAttachmentId + 1
        defaultMPreBidMeetingAttachmentShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPreBidMeetingAttachment.getAdOrganization();
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPreBidMeetingAttachmentList where adOrganization equals to adOrganizationId
        defaultMPreBidMeetingAttachmentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPreBidMeetingAttachmentList where adOrganization equals to adOrganizationId + 1
        defaultMPreBidMeetingAttachmentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingAttachmentsByPreBidMeetingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPreBidMeeting preBidMeeting = mPreBidMeetingAttachment.getPreBidMeeting();
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);
        Long preBidMeetingId = preBidMeeting.getId();

        // Get all the mPreBidMeetingAttachmentList where preBidMeeting equals to preBidMeetingId
        defaultMPreBidMeetingAttachmentShouldBeFound("preBidMeetingId.equals=" + preBidMeetingId);

        // Get all the mPreBidMeetingAttachmentList where preBidMeeting equals to preBidMeetingId + 1
        defaultMPreBidMeetingAttachmentShouldNotBeFound("preBidMeetingId.equals=" + (preBidMeetingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPreBidMeetingAttachmentShouldBeFound(String filter) throws Exception {
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeetingAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPreBidMeetingAttachmentShouldNotBeFound(String filter) throws Exception {
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPreBidMeetingAttachment() throws Exception {
        // Get the mPreBidMeetingAttachment
        restMPreBidMeetingAttachmentMockMvc.perform(get("/api/m-pre-bid-meeting-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPreBidMeetingAttachment() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        int databaseSizeBeforeUpdate = mPreBidMeetingAttachmentRepository.findAll().size();

        // Update the mPreBidMeetingAttachment
        MPreBidMeetingAttachment updatedMPreBidMeetingAttachment = mPreBidMeetingAttachmentRepository.findById(mPreBidMeetingAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedMPreBidMeetingAttachment are not directly saved in db
        em.detach(updatedMPreBidMeetingAttachment);
        updatedMPreBidMeetingAttachment
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO = mPreBidMeetingAttachmentMapper.toDto(updatedMPreBidMeetingAttachment);

        restMPreBidMeetingAttachmentMockMvc.perform(put("/api/m-pre-bid-meeting-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the MPreBidMeetingAttachment in the database
        List<MPreBidMeetingAttachment> mPreBidMeetingAttachmentList = mPreBidMeetingAttachmentRepository.findAll();
        assertThat(mPreBidMeetingAttachmentList).hasSize(databaseSizeBeforeUpdate);
        MPreBidMeetingAttachment testMPreBidMeetingAttachment = mPreBidMeetingAttachmentList.get(mPreBidMeetingAttachmentList.size() - 1);
        assertThat(testMPreBidMeetingAttachment.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPreBidMeetingAttachment.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPreBidMeetingAttachment() throws Exception {
        int databaseSizeBeforeUpdate = mPreBidMeetingAttachmentRepository.findAll().size();

        // Create the MPreBidMeetingAttachment
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO = mPreBidMeetingAttachmentMapper.toDto(mPreBidMeetingAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPreBidMeetingAttachmentMockMvc.perform(put("/api/m-pre-bid-meeting-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeetingAttachment in the database
        List<MPreBidMeetingAttachment> mPreBidMeetingAttachmentList = mPreBidMeetingAttachmentRepository.findAll();
        assertThat(mPreBidMeetingAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPreBidMeetingAttachment() throws Exception {
        // Initialize the database
        mPreBidMeetingAttachmentRepository.saveAndFlush(mPreBidMeetingAttachment);

        int databaseSizeBeforeDelete = mPreBidMeetingAttachmentRepository.findAll().size();

        // Delete the mPreBidMeetingAttachment
        restMPreBidMeetingAttachmentMockMvc.perform(delete("/api/m-pre-bid-meeting-attachments/{id}", mPreBidMeetingAttachment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPreBidMeetingAttachment> mPreBidMeetingAttachmentList = mPreBidMeetingAttachmentRepository.findAll();
        assertThat(mPreBidMeetingAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
