package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.service.CAnnouncementService;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;
import com.bhp.opusb.service.dto.CAnnouncementCriteria;
import com.bhp.opusb.service.CAnnouncementQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.bhp.opusb.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CAnnouncementResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CAnnouncementResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUBLISH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PUBLISH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CAnnouncementRepository cAnnouncementRepository;

    @Autowired
    private CAnnouncementMapper cAnnouncementMapper;

    @Autowired
    private CAnnouncementService cAnnouncementService;

    @Autowired
    private CAnnouncementQueryService cAnnouncementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCAnnouncementMockMvc;

    private CAnnouncement cAnnouncement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAnnouncement createEntity(EntityManager em) {
        CAnnouncement cAnnouncement = new CAnnouncement()
            .description(DEFAULT_DESCRIPTION)
            .publishDate(DEFAULT_PUBLISH_DATE)
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
        cAnnouncement.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        cAnnouncement.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        cAnnouncement.setBiddingSchedule(mBiddingSchedule);
        return cAnnouncement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAnnouncement createUpdatedEntity(EntityManager em) {
        CAnnouncement cAnnouncement = new CAnnouncement()
            .description(UPDATED_DESCRIPTION)
            .publishDate(UPDATED_PUBLISH_DATE)
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
        cAnnouncement.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        cAnnouncement.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        cAnnouncement.setBiddingSchedule(mBiddingSchedule);
        return cAnnouncement;
    }

    @BeforeEach
    public void initTest() {
        cAnnouncement = createEntity(em);
    }

    @Test
    @Transactional
    public void createCAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = cAnnouncementRepository.findAll().size();

        // Create the CAnnouncement
        CAnnouncementDTO cAnnouncementDTO = cAnnouncementMapper.toDto(cAnnouncement);
        restCAnnouncementMockMvc.perform(post("/api/c-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementDTO)))
            .andExpect(status().isCreated());

        // Validate the CAnnouncement in the database
        List<CAnnouncement> cAnnouncementList = cAnnouncementRepository.findAll();
        assertThat(cAnnouncementList).hasSize(databaseSizeBeforeCreate + 1);
        CAnnouncement testCAnnouncement = cAnnouncementList.get(cAnnouncementList.size() - 1);
        assertThat(testCAnnouncement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCAnnouncement.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testCAnnouncement.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCAnnouncement.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCAnnouncementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cAnnouncementRepository.findAll().size();

        // Create the CAnnouncement with an existing ID
        cAnnouncement.setId(1L);
        CAnnouncementDTO cAnnouncementDTO = cAnnouncementMapper.toDto(cAnnouncement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAnnouncementMockMvc.perform(post("/api/c-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAnnouncement in the database
        List<CAnnouncement> cAnnouncementList = cAnnouncementRepository.findAll();
        assertThat(cAnnouncementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCAnnouncements() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList
        restCAnnouncementMockMvc.perform(get("/api/c-announcements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCAnnouncement() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get the cAnnouncement
        restCAnnouncementMockMvc.perform(get("/api/c-announcements/{id}", cAnnouncement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cAnnouncement.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.publishDate").value(sameInstant(DEFAULT_PUBLISH_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCAnnouncementsByIdFiltering() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        Long id = cAnnouncement.getId();

        defaultCAnnouncementShouldBeFound("id.equals=" + id);
        defaultCAnnouncementShouldNotBeFound("id.notEquals=" + id);

        defaultCAnnouncementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCAnnouncementShouldNotBeFound("id.greaterThan=" + id);

        defaultCAnnouncementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCAnnouncementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate equals to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.equals=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.equals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate not equals to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.notEquals=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate not equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.notEquals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate in DEFAULT_PUBLISH_DATE or UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.in=" + DEFAULT_PUBLISH_DATE + "," + UPDATED_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.in=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate is not null
        defaultCAnnouncementShouldBeFound("publishDate.specified=true");

        // Get all the cAnnouncementList where publishDate is null
        defaultCAnnouncementShouldNotBeFound("publishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate is greater than or equal to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.greaterThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate is greater than or equal to UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.greaterThanOrEqual=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate is less than or equal to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.lessThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate is less than or equal to SMALLER_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.lessThanOrEqual=" + SMALLER_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate is less than DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.lessThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate is less than UPDATED_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.lessThan=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByPublishDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where publishDate is greater than DEFAULT_PUBLISH_DATE
        defaultCAnnouncementShouldNotBeFound("publishDate.greaterThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementList where publishDate is greater than SMALLER_PUBLISH_DATE
        defaultCAnnouncementShouldBeFound("publishDate.greaterThan=" + SMALLER_PUBLISH_DATE);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where uid equals to DEFAULT_UID
        defaultCAnnouncementShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cAnnouncementList where uid equals to UPDATED_UID
        defaultCAnnouncementShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where uid not equals to DEFAULT_UID
        defaultCAnnouncementShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cAnnouncementList where uid not equals to UPDATED_UID
        defaultCAnnouncementShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where uid in DEFAULT_UID or UPDATED_UID
        defaultCAnnouncementShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cAnnouncementList where uid equals to UPDATED_UID
        defaultCAnnouncementShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where uid is not null
        defaultCAnnouncementShouldBeFound("uid.specified=true");

        // Get all the cAnnouncementList where uid is null
        defaultCAnnouncementShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where active equals to DEFAULT_ACTIVE
        defaultCAnnouncementShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cAnnouncementList where active equals to UPDATED_ACTIVE
        defaultCAnnouncementShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where active not equals to DEFAULT_ACTIVE
        defaultCAnnouncementShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cAnnouncementList where active not equals to UPDATED_ACTIVE
        defaultCAnnouncementShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCAnnouncementShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cAnnouncementList where active equals to UPDATED_ACTIVE
        defaultCAnnouncementShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        // Get all the cAnnouncementList where active is not null
        defaultCAnnouncementShouldBeFound("active.specified=true");

        // Get all the cAnnouncementList where active is null
        defaultCAnnouncementShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cAnnouncement.getAdOrganization();
        cAnnouncementRepository.saveAndFlush(cAnnouncement);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cAnnouncementList where adOrganization equals to adOrganizationId
        defaultCAnnouncementShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cAnnouncementList where adOrganization equals to adOrganizationId + 1
        defaultCAnnouncementShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = cAnnouncement.getBidding();
        cAnnouncementRepository.saveAndFlush(cAnnouncement);
        Long biddingId = bidding.getId();

        // Get all the cAnnouncementList where bidding equals to biddingId
        defaultCAnnouncementShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the cAnnouncementList where bidding equals to biddingId + 1
        defaultCAnnouncementShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = cAnnouncement.getBiddingSchedule();
        cAnnouncementRepository.saveAndFlush(cAnnouncement);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the cAnnouncementList where biddingSchedule equals to biddingScheduleId
        defaultCAnnouncementShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the cAnnouncementList where biddingSchedule equals to biddingScheduleId + 1
        defaultCAnnouncementShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        cAnnouncement.setAttachment(attachment);
        cAnnouncementRepository.saveAndFlush(cAnnouncement);
        Long attachmentId = attachment.getId();

        // Get all the cAnnouncementList where attachment equals to attachmentId
        defaultCAnnouncementShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the cAnnouncementList where attachment equals to attachmentId + 1
        defaultCAnnouncementShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCAnnouncementShouldBeFound(String filter) throws Exception {
        restCAnnouncementMockMvc.perform(get("/api/c-announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCAnnouncementMockMvc.perform(get("/api/c-announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCAnnouncementShouldNotBeFound(String filter) throws Exception {
        restCAnnouncementMockMvc.perform(get("/api/c-announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCAnnouncementMockMvc.perform(get("/api/c-announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCAnnouncement() throws Exception {
        // Get the cAnnouncement
        restCAnnouncementMockMvc.perform(get("/api/c-announcements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCAnnouncement() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        int databaseSizeBeforeUpdate = cAnnouncementRepository.findAll().size();

        // Update the cAnnouncement
        CAnnouncement updatedCAnnouncement = cAnnouncementRepository.findById(cAnnouncement.getId()).get();
        // Disconnect from session so that the updates on updatedCAnnouncement are not directly saved in db
        em.detach(updatedCAnnouncement);
        updatedCAnnouncement
            .description(UPDATED_DESCRIPTION)
            .publishDate(UPDATED_PUBLISH_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CAnnouncementDTO cAnnouncementDTO = cAnnouncementMapper.toDto(updatedCAnnouncement);

        restCAnnouncementMockMvc.perform(put("/api/c-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementDTO)))
            .andExpect(status().isOk());

        // Validate the CAnnouncement in the database
        List<CAnnouncement> cAnnouncementList = cAnnouncementRepository.findAll();
        assertThat(cAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        CAnnouncement testCAnnouncement = cAnnouncementList.get(cAnnouncementList.size() - 1);
        assertThat(testCAnnouncement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCAnnouncement.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testCAnnouncement.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCAnnouncement.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = cAnnouncementRepository.findAll().size();

        // Create the CAnnouncement
        CAnnouncementDTO cAnnouncementDTO = cAnnouncementMapper.toDto(cAnnouncement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAnnouncementMockMvc.perform(put("/api/c-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAnnouncement in the database
        List<CAnnouncement> cAnnouncementList = cAnnouncementRepository.findAll();
        assertThat(cAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCAnnouncement() throws Exception {
        // Initialize the database
        cAnnouncementRepository.saveAndFlush(cAnnouncement);

        int databaseSizeBeforeDelete = cAnnouncementRepository.findAll().size();

        // Delete the cAnnouncement
        restCAnnouncementMockMvc.perform(delete("/api/c-announcements/{id}", cAnnouncement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAnnouncement> cAnnouncementList = cAnnouncementRepository.findAll();
        assertThat(cAnnouncementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
