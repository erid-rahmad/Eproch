package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalAnnouncement;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.MPrequalificationSchedule;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MPrequalAnnouncementRepository;
import com.bhp.opusb.service.MPrequalAnnouncementService;
import com.bhp.opusb.service.dto.MPrequalAnnouncementDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementMapper;
import com.bhp.opusb.service.dto.MPrequalAnnouncementCriteria;
import com.bhp.opusb.service.MPrequalAnnouncementQueryService;

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
 * Integration tests for the {@link MPrequalAnnouncementResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalAnnouncementResourceIT {

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
    private MPrequalAnnouncementRepository mPrequalAnnouncementRepository;

    @Autowired
    private MPrequalAnnouncementMapper mPrequalAnnouncementMapper;

    @Autowired
    private MPrequalAnnouncementService mPrequalAnnouncementService;

    @Autowired
    private MPrequalAnnouncementQueryService mPrequalAnnouncementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalAnnouncementMockMvc;

    private MPrequalAnnouncement mPrequalAnnouncement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalAnnouncement createEntity(EntityManager em) {
        MPrequalAnnouncement mPrequalAnnouncement = new MPrequalAnnouncement()
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
        mPrequalAnnouncement.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalAnnouncement.setPrequalification(mPrequalificationInformation);
        // Add required entity
        MPrequalificationSchedule mPrequalificationSchedule;
        if (TestUtil.findAll(em, MPrequalificationSchedule.class).isEmpty()) {
            mPrequalificationSchedule = MPrequalificationScheduleResourceIT.createEntity(em);
            em.persist(mPrequalificationSchedule);
            em.flush();
        } else {
            mPrequalificationSchedule = TestUtil.findAll(em, MPrequalificationSchedule.class).get(0);
        }
        mPrequalAnnouncement.setPrequalificationSchedule(mPrequalificationSchedule);
        return mPrequalAnnouncement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalAnnouncement createUpdatedEntity(EntityManager em) {
        MPrequalAnnouncement mPrequalAnnouncement = new MPrequalAnnouncement()
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
        mPrequalAnnouncement.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalAnnouncement.setPrequalification(mPrequalificationInformation);
        // Add required entity
        MPrequalificationSchedule mPrequalificationSchedule;
        if (TestUtil.findAll(em, MPrequalificationSchedule.class).isEmpty()) {
            mPrequalificationSchedule = MPrequalificationScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationSchedule);
            em.flush();
        } else {
            mPrequalificationSchedule = TestUtil.findAll(em, MPrequalificationSchedule.class).get(0);
        }
        mPrequalAnnouncement.setPrequalificationSchedule(mPrequalificationSchedule);
        return mPrequalAnnouncement;
    }

    @BeforeEach
    public void initTest() {
        mPrequalAnnouncement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = mPrequalAnnouncementRepository.findAll().size();

        // Create the MPrequalAnnouncement
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO = mPrequalAnnouncementMapper.toDto(mPrequalAnnouncement);
        restMPrequalAnnouncementMockMvc.perform(post("/api/m-prequal-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalAnnouncement in the database
        List<MPrequalAnnouncement> mPrequalAnnouncementList = mPrequalAnnouncementRepository.findAll();
        assertThat(mPrequalAnnouncementList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalAnnouncement testMPrequalAnnouncement = mPrequalAnnouncementList.get(mPrequalAnnouncementList.size() - 1);
        assertThat(testMPrequalAnnouncement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMPrequalAnnouncement.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testMPrequalAnnouncement.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalAnnouncement.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalAnnouncementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalAnnouncementRepository.findAll().size();

        // Create the MPrequalAnnouncement with an existing ID
        mPrequalAnnouncement.setId(1L);
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO = mPrequalAnnouncementMapper.toDto(mPrequalAnnouncement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalAnnouncementMockMvc.perform(post("/api/m-prequal-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalAnnouncement in the database
        List<MPrequalAnnouncement> mPrequalAnnouncementList = mPrequalAnnouncementRepository.findAll();
        assertThat(mPrequalAnnouncementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncements() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalAnnouncement() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get the mPrequalAnnouncement
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements/{id}", mPrequalAnnouncement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalAnnouncement.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.publishDate").value(sameInstant(DEFAULT_PUBLISH_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalAnnouncementsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        Long id = mPrequalAnnouncement.getId();

        defaultMPrequalAnnouncementShouldBeFound("id.equals=" + id);
        defaultMPrequalAnnouncementShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalAnnouncementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalAnnouncementShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalAnnouncementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalAnnouncementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate equals to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.equals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.equals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate not equals to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.notEquals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate not equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.notEquals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate in DEFAULT_PUBLISH_DATE or UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.in=" + DEFAULT_PUBLISH_DATE + "," + UPDATED_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.in=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate is not null
        defaultMPrequalAnnouncementShouldBeFound("publishDate.specified=true");

        // Get all the mPrequalAnnouncementList where publishDate is null
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate is greater than or equal to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.greaterThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate is greater than or equal to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.greaterThanOrEqual=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate is less than or equal to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.lessThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate is less than or equal to SMALLER_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.lessThanOrEqual=" + SMALLER_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate is less than DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.lessThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate is less than UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.lessThan=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPublishDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where publishDate is greater than DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldNotBeFound("publishDate.greaterThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementList where publishDate is greater than SMALLER_PUBLISH_DATE
        defaultMPrequalAnnouncementShouldBeFound("publishDate.greaterThan=" + SMALLER_PUBLISH_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where uid equals to DEFAULT_UID
        defaultMPrequalAnnouncementShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalAnnouncementList where uid equals to UPDATED_UID
        defaultMPrequalAnnouncementShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where uid not equals to DEFAULT_UID
        defaultMPrequalAnnouncementShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalAnnouncementList where uid not equals to UPDATED_UID
        defaultMPrequalAnnouncementShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalAnnouncementShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalAnnouncementList where uid equals to UPDATED_UID
        defaultMPrequalAnnouncementShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where uid is not null
        defaultMPrequalAnnouncementShouldBeFound("uid.specified=true");

        // Get all the mPrequalAnnouncementList where uid is null
        defaultMPrequalAnnouncementShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where active equals to DEFAULT_ACTIVE
        defaultMPrequalAnnouncementShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalAnnouncementList where active equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalAnnouncementShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalAnnouncementList where active not equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalAnnouncementShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalAnnouncementList where active equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        // Get all the mPrequalAnnouncementList where active is not null
        defaultMPrequalAnnouncementShouldBeFound("active.specified=true");

        // Get all the mPrequalAnnouncementList where active is null
        defaultMPrequalAnnouncementShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalAnnouncement.getAdOrganization();
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalAnnouncementList where adOrganization equals to adOrganizationId
        defaultMPrequalAnnouncementShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalAnnouncementList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalAnnouncementShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalAnnouncement.getPrequalification();
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalAnnouncementList where prequalification equals to prequalificationId
        defaultMPrequalAnnouncementShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalAnnouncementList where prequalification equals to prequalificationId + 1
        defaultMPrequalAnnouncementShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByPrequalificationScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationSchedule prequalificationSchedule = mPrequalAnnouncement.getPrequalificationSchedule();
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);
        Long prequalificationScheduleId = prequalificationSchedule.getId();

        // Get all the mPrequalAnnouncementList where prequalificationSchedule equals to prequalificationScheduleId
        defaultMPrequalAnnouncementShouldBeFound("prequalificationScheduleId.equals=" + prequalificationScheduleId);

        // Get all the mPrequalAnnouncementList where prequalificationSchedule equals to prequalificationScheduleId + 1
        defaultMPrequalAnnouncementShouldNotBeFound("prequalificationScheduleId.equals=" + (prequalificationScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        mPrequalAnnouncement.setAttachment(attachment);
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);
        Long attachmentId = attachment.getId();

        // Get all the mPrequalAnnouncementList where attachment equals to attachmentId
        defaultMPrequalAnnouncementShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mPrequalAnnouncementList where attachment equals to attachmentId + 1
        defaultMPrequalAnnouncementShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalAnnouncementShouldBeFound(String filter) throws Exception {
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalAnnouncementShouldNotBeFound(String filter) throws Exception {
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalAnnouncement() throws Exception {
        // Get the mPrequalAnnouncement
        restMPrequalAnnouncementMockMvc.perform(get("/api/m-prequal-announcements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalAnnouncement() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        int databaseSizeBeforeUpdate = mPrequalAnnouncementRepository.findAll().size();

        // Update the mPrequalAnnouncement
        MPrequalAnnouncement updatedMPrequalAnnouncement = mPrequalAnnouncementRepository.findById(mPrequalAnnouncement.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalAnnouncement are not directly saved in db
        em.detach(updatedMPrequalAnnouncement);
        updatedMPrequalAnnouncement
            .description(UPDATED_DESCRIPTION)
            .publishDate(UPDATED_PUBLISH_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO = mPrequalAnnouncementMapper.toDto(updatedMPrequalAnnouncement);

        restMPrequalAnnouncementMockMvc.perform(put("/api/m-prequal-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalAnnouncement in the database
        List<MPrequalAnnouncement> mPrequalAnnouncementList = mPrequalAnnouncementRepository.findAll();
        assertThat(mPrequalAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        MPrequalAnnouncement testMPrequalAnnouncement = mPrequalAnnouncementList.get(mPrequalAnnouncementList.size() - 1);
        assertThat(testMPrequalAnnouncement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMPrequalAnnouncement.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testMPrequalAnnouncement.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalAnnouncement.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalAnnouncementRepository.findAll().size();

        // Create the MPrequalAnnouncement
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO = mPrequalAnnouncementMapper.toDto(mPrequalAnnouncement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalAnnouncementMockMvc.perform(put("/api/m-prequal-announcements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalAnnouncement in the database
        List<MPrequalAnnouncement> mPrequalAnnouncementList = mPrequalAnnouncementRepository.findAll();
        assertThat(mPrequalAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalAnnouncement() throws Exception {
        // Initialize the database
        mPrequalAnnouncementRepository.saveAndFlush(mPrequalAnnouncement);

        int databaseSizeBeforeDelete = mPrequalAnnouncementRepository.findAll().size();

        // Delete the mPrequalAnnouncement
        restMPrequalAnnouncementMockMvc.perform(delete("/api/m-prequal-announcements/{id}", mPrequalAnnouncement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalAnnouncement> mPrequalAnnouncementList = mPrequalAnnouncementRepository.findAll();
        assertThat(mPrequalAnnouncementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
