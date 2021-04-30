package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.MPreBidMeetingAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MPreBidMeetingRepository;
import com.bhp.opusb.service.MPreBidMeetingService;
import com.bhp.opusb.service.dto.MPreBidMeetingDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingMapper;
import com.bhp.opusb.service.dto.MPreBidMeetingCriteria;
import com.bhp.opusb.service.MPreBidMeetingQueryService;

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
 * Integration tests for the {@link MPreBidMeetingResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPreBidMeetingResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPreBidMeetingRepository mPreBidMeetingRepository;

    @Autowired
    private MPreBidMeetingMapper mPreBidMeetingMapper;

    @Autowired
    private MPreBidMeetingService mPreBidMeetingService;

    @Autowired
    private MPreBidMeetingQueryService mPreBidMeetingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPreBidMeetingMockMvc;

    private MPreBidMeeting mPreBidMeeting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeeting createEntity(EntityManager em) {
        MPreBidMeeting mPreBidMeeting = new MPreBidMeeting()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mPreBidMeeting.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreBidMeeting.setAdOrganization(aDOrganization);
        return mPreBidMeeting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeeting createUpdatedEntity(EntityManager em) {
        MPreBidMeeting mPreBidMeeting = new MPreBidMeeting()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mPreBidMeeting.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPreBidMeeting.setAdOrganization(aDOrganization);
        return mPreBidMeeting;
    }

    @BeforeEach
    public void initTest() {
        mPreBidMeeting = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPreBidMeeting() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingRepository.findAll().size();

        // Create the MPreBidMeeting
        MPreBidMeetingDTO mPreBidMeetingDTO = mPreBidMeetingMapper.toDto(mPreBidMeeting);
        restMPreBidMeetingMockMvc.perform(post("/api/m-pre-bid-meetings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingDTO)))
            .andExpect(status().isCreated());

        // Validate the MPreBidMeeting in the database
        List<MPreBidMeeting> mPreBidMeetingList = mPreBidMeetingRepository.findAll();
        assertThat(mPreBidMeetingList).hasSize(databaseSizeBeforeCreate + 1);
        MPreBidMeeting testMPreBidMeeting = mPreBidMeetingList.get(mPreBidMeetingList.size() - 1);
        assertThat(testMPreBidMeeting.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPreBidMeeting.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPreBidMeetingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingRepository.findAll().size();

        // Create the MPreBidMeeting with an existing ID
        mPreBidMeeting.setId(1L);
        MPreBidMeetingDTO mPreBidMeetingDTO = mPreBidMeetingMapper.toDto(mPreBidMeeting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPreBidMeetingMockMvc.perform(post("/api/m-pre-bid-meetings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeeting in the database
        List<MPreBidMeeting> mPreBidMeetingList = mPreBidMeetingRepository.findAll();
        assertThat(mPreBidMeetingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetings() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPreBidMeeting() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get the mPreBidMeeting
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings/{id}", mPreBidMeeting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPreBidMeeting.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPreBidMeetingsByIdFiltering() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        Long id = mPreBidMeeting.getId();

        defaultMPreBidMeetingShouldBeFound("id.equals=" + id);
        defaultMPreBidMeetingShouldNotBeFound("id.notEquals=" + id);

        defaultMPreBidMeetingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPreBidMeetingShouldNotBeFound("id.greaterThan=" + id);

        defaultMPreBidMeetingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPreBidMeetingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where uid equals to DEFAULT_UID
        defaultMPreBidMeetingShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingList where uid equals to UPDATED_UID
        defaultMPreBidMeetingShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where uid not equals to DEFAULT_UID
        defaultMPreBidMeetingShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingList where uid not equals to UPDATED_UID
        defaultMPreBidMeetingShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPreBidMeetingShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPreBidMeetingList where uid equals to UPDATED_UID
        defaultMPreBidMeetingShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where uid is not null
        defaultMPreBidMeetingShouldBeFound("uid.specified=true");

        // Get all the mPreBidMeetingList where uid is null
        defaultMPreBidMeetingShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where active equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where active not equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingList where active not equals to UPDATED_ACTIVE
        defaultMPreBidMeetingShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPreBidMeetingShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPreBidMeetingList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        // Get all the mPreBidMeetingList where active is not null
        defaultMPreBidMeetingShouldBeFound("active.specified=true");

        // Get all the mPreBidMeetingList where active is null
        defaultMPreBidMeetingShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mPreBidMeeting.getBiddingSchedule();
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mPreBidMeetingList where biddingSchedule equals to biddingScheduleId
        defaultMPreBidMeetingShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mPreBidMeetingList where biddingSchedule equals to biddingScheduleId + 1
        defaultMPreBidMeetingShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingsByMPreBidMeetingAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);
        MPreBidMeetingAttachment mPreBidMeetingAttachment = MPreBidMeetingAttachmentResourceIT.createEntity(em);
        em.persist(mPreBidMeetingAttachment);
        em.flush();
        mPreBidMeeting.addMPreBidMeetingAttachment(mPreBidMeetingAttachment);
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);
        Long mPreBidMeetingAttachmentId = mPreBidMeetingAttachment.getId();

        // Get all the mPreBidMeetingList where mPreBidMeetingAttachment equals to mPreBidMeetingAttachmentId
        defaultMPreBidMeetingShouldBeFound("mPreBidMeetingAttachmentId.equals=" + mPreBidMeetingAttachmentId);

        // Get all the mPreBidMeetingList where mPreBidMeetingAttachment equals to mPreBidMeetingAttachmentId + 1
        defaultMPreBidMeetingShouldNotBeFound("mPreBidMeetingAttachmentId.equals=" + (mPreBidMeetingAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPreBidMeeting.getAdOrganization();
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPreBidMeetingList where adOrganization equals to adOrganizationId
        defaultMPreBidMeetingShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPreBidMeetingList where adOrganization equals to adOrganizationId + 1
        defaultMPreBidMeetingShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPreBidMeetingShouldBeFound(String filter) throws Exception {
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPreBidMeetingShouldNotBeFound(String filter) throws Exception {
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPreBidMeeting() throws Exception {
        // Get the mPreBidMeeting
        restMPreBidMeetingMockMvc.perform(get("/api/m-pre-bid-meetings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPreBidMeeting() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        int databaseSizeBeforeUpdate = mPreBidMeetingRepository.findAll().size();

        // Update the mPreBidMeeting
        MPreBidMeeting updatedMPreBidMeeting = mPreBidMeetingRepository.findById(mPreBidMeeting.getId()).get();
        // Disconnect from session so that the updates on updatedMPreBidMeeting are not directly saved in db
        em.detach(updatedMPreBidMeeting);
        updatedMPreBidMeeting
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPreBidMeetingDTO mPreBidMeetingDTO = mPreBidMeetingMapper.toDto(updatedMPreBidMeeting);

        restMPreBidMeetingMockMvc.perform(put("/api/m-pre-bid-meetings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingDTO)))
            .andExpect(status().isOk());

        // Validate the MPreBidMeeting in the database
        List<MPreBidMeeting> mPreBidMeetingList = mPreBidMeetingRepository.findAll();
        assertThat(mPreBidMeetingList).hasSize(databaseSizeBeforeUpdate);
        MPreBidMeeting testMPreBidMeeting = mPreBidMeetingList.get(mPreBidMeetingList.size() - 1);
        assertThat(testMPreBidMeeting.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPreBidMeeting.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPreBidMeeting() throws Exception {
        int databaseSizeBeforeUpdate = mPreBidMeetingRepository.findAll().size();

        // Create the MPreBidMeeting
        MPreBidMeetingDTO mPreBidMeetingDTO = mPreBidMeetingMapper.toDto(mPreBidMeeting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPreBidMeetingMockMvc.perform(put("/api/m-pre-bid-meetings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeeting in the database
        List<MPreBidMeeting> mPreBidMeetingList = mPreBidMeetingRepository.findAll();
        assertThat(mPreBidMeetingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPreBidMeeting() throws Exception {
        // Initialize the database
        mPreBidMeetingRepository.saveAndFlush(mPreBidMeeting);

        int databaseSizeBeforeDelete = mPreBidMeetingRepository.findAll().size();

        // Delete the mPreBidMeeting
        restMPreBidMeetingMockMvc.perform(delete("/api/m-pre-bid-meetings/{id}", mPreBidMeeting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPreBidMeeting> mPreBidMeetingList = mPreBidMeetingRepository.findAll();
        assertThat(mPreBidMeetingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
