package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationSchedule;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CPrequalificationEventLine;
import com.bhp.opusb.repository.MPrequalificationScheduleRepository;
import com.bhp.opusb.service.MPrequalificationScheduleService;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;
import com.bhp.opusb.service.mapper.MPrequalificationScheduleMapper;
import com.bhp.opusb.service.dto.MPrequalificationScheduleCriteria;
import com.bhp.opusb.service.MPrequalificationScheduleQueryService;

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
 * Integration tests for the {@link MPrequalificationScheduleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationScheduleResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationScheduleRepository mPrequalificationScheduleRepository;

    @Autowired
    private MPrequalificationScheduleMapper mPrequalificationScheduleMapper;

    @Autowired
    private MPrequalificationScheduleService mPrequalificationScheduleService;

    @Autowired
    private MPrequalificationScheduleQueryService mPrequalificationScheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationScheduleMockMvc;

    private MPrequalificationSchedule mPrequalificationSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationSchedule createEntity(EntityManager em) {
        MPrequalificationSchedule mPrequalificationSchedule = new MPrequalificationSchedule()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
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
        mPrequalificationSchedule.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationEventLine cPrequalificationEventLine;
        if (TestUtil.findAll(em, CPrequalificationEventLine.class).isEmpty()) {
            cPrequalificationEventLine = CPrequalificationEventLineResourceIT.createEntity(em);
            em.persist(cPrequalificationEventLine);
            em.flush();
        } else {
            cPrequalificationEventLine = TestUtil.findAll(em, CPrequalificationEventLine.class).get(0);
        }
        mPrequalificationSchedule.setEventLine(cPrequalificationEventLine);
        return mPrequalificationSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationSchedule createUpdatedEntity(EntityManager em) {
        MPrequalificationSchedule mPrequalificationSchedule = new MPrequalificationSchedule()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
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
        mPrequalificationSchedule.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationEventLine cPrequalificationEventLine;
        if (TestUtil.findAll(em, CPrequalificationEventLine.class).isEmpty()) {
            cPrequalificationEventLine = CPrequalificationEventLineResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationEventLine);
            em.flush();
        } else {
            cPrequalificationEventLine = TestUtil.findAll(em, CPrequalificationEventLine.class).get(0);
        }
        mPrequalificationSchedule.setEventLine(cPrequalificationEventLine);
        return mPrequalificationSchedule;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationSchedule() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationScheduleRepository.findAll().size();

        // Create the MPrequalificationSchedule
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO = mPrequalificationScheduleMapper.toDto(mPrequalificationSchedule);
        restMPrequalificationScheduleMockMvc.perform(post("/api/m-prequalification-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationSchedule in the database
        List<MPrequalificationSchedule> mPrequalificationScheduleList = mPrequalificationScheduleRepository.findAll();
        assertThat(mPrequalificationScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationSchedule testMPrequalificationSchedule = mPrequalificationScheduleList.get(mPrequalificationScheduleList.size() - 1);
        assertThat(testMPrequalificationSchedule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMPrequalificationSchedule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMPrequalificationSchedule.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationSchedule.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationScheduleRepository.findAll().size();

        // Create the MPrequalificationSchedule with an existing ID
        mPrequalificationSchedule.setId(1L);
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO = mPrequalificationScheduleMapper.toDto(mPrequalificationSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationScheduleMockMvc.perform(post("/api/m-prequalification-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationSchedule in the database
        List<MPrequalificationSchedule> mPrequalificationScheduleList = mPrequalificationScheduleRepository.findAll();
        assertThat(mPrequalificationScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedules() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationSchedule() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get the mPrequalificationSchedule
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules/{id}", mPrequalificationSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationSchedule.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        Long id = mPrequalificationSchedule.getId();

        defaultMPrequalificationScheduleShouldBeFound("id.equals=" + id);
        defaultMPrequalificationScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate equals to DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate equals to UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate not equals to DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate not equals to UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate equals to UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate is not null
        defaultMPrequalificationScheduleShouldBeFound("startDate.specified=true");

        // Get all the mPrequalificationScheduleList where startDate is null
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate is less than or equal to SMALLER_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate is less than DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate is less than UPDATED_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where startDate is greater than DEFAULT_START_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationScheduleList where startDate is greater than SMALLER_START_DATE
        defaultMPrequalificationScheduleShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate equals to DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate equals to UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate not equals to DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate not equals to UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate equals to UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate is not null
        defaultMPrequalificationScheduleShouldBeFound("endDate.specified=true");

        // Get all the mPrequalificationScheduleList where endDate is null
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate is greater than or equal to UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate is less than or equal to DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate is less than or equal to SMALLER_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate is less than DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate is less than UPDATED_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where endDate is greater than DEFAULT_END_DATE
        defaultMPrequalificationScheduleShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationScheduleList where endDate is greater than SMALLER_END_DATE
        defaultMPrequalificationScheduleShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where uid equals to DEFAULT_UID
        defaultMPrequalificationScheduleShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationScheduleList where uid equals to UPDATED_UID
        defaultMPrequalificationScheduleShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where uid not equals to DEFAULT_UID
        defaultMPrequalificationScheduleShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationScheduleList where uid not equals to UPDATED_UID
        defaultMPrequalificationScheduleShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationScheduleShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationScheduleList where uid equals to UPDATED_UID
        defaultMPrequalificationScheduleShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where uid is not null
        defaultMPrequalificationScheduleShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationScheduleList where uid is null
        defaultMPrequalificationScheduleShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationScheduleShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationScheduleList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationScheduleShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationScheduleShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationScheduleList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationScheduleShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationScheduleShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationScheduleList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationScheduleShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        // Get all the mPrequalificationScheduleList where active is not null
        defaultMPrequalificationScheduleShouldBeFound("active.specified=true");

        // Get all the mPrequalificationScheduleList where active is null
        defaultMPrequalificationScheduleShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationSchedule.getPrequalification();
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationScheduleList where prequalification equals to prequalificationId
        defaultMPrequalificationScheduleShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationScheduleList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationScheduleShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationSchedule.getAdOrganization();
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationScheduleList where adOrganization equals to adOrganizationId
        defaultMPrequalificationScheduleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationScheduleList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationScheduleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSchedulesByEventLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationEventLine eventLine = mPrequalificationSchedule.getEventLine();
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);
        Long eventLineId = eventLine.getId();

        // Get all the mPrequalificationScheduleList where eventLine equals to eventLineId
        defaultMPrequalificationScheduleShouldBeFound("eventLineId.equals=" + eventLineId);

        // Get all the mPrequalificationScheduleList where eventLine equals to eventLineId + 1
        defaultMPrequalificationScheduleShouldNotBeFound("eventLineId.equals=" + (eventLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationScheduleShouldBeFound(String filter) throws Exception {
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationScheduleShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationSchedule() throws Exception {
        // Get the mPrequalificationSchedule
        restMPrequalificationScheduleMockMvc.perform(get("/api/m-prequalification-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationSchedule() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        int databaseSizeBeforeUpdate = mPrequalificationScheduleRepository.findAll().size();

        // Update the mPrequalificationSchedule
        MPrequalificationSchedule updatedMPrequalificationSchedule = mPrequalificationScheduleRepository.findById(mPrequalificationSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationSchedule are not directly saved in db
        em.detach(updatedMPrequalificationSchedule);
        updatedMPrequalificationSchedule
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO = mPrequalificationScheduleMapper.toDto(updatedMPrequalificationSchedule);

        restMPrequalificationScheduleMockMvc.perform(put("/api/m-prequalification-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationSchedule in the database
        List<MPrequalificationSchedule> mPrequalificationScheduleList = mPrequalificationScheduleRepository.findAll();
        assertThat(mPrequalificationScheduleList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationSchedule testMPrequalificationSchedule = mPrequalificationScheduleList.get(mPrequalificationScheduleList.size() - 1);
        assertThat(testMPrequalificationSchedule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMPrequalificationSchedule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMPrequalificationSchedule.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationSchedule.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationScheduleRepository.findAll().size();

        // Create the MPrequalificationSchedule
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO = mPrequalificationScheduleMapper.toDto(mPrequalificationSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationScheduleMockMvc.perform(put("/api/m-prequalification-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationSchedule in the database
        List<MPrequalificationSchedule> mPrequalificationScheduleList = mPrequalificationScheduleRepository.findAll();
        assertThat(mPrequalificationScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationSchedule() throws Exception {
        // Initialize the database
        mPrequalificationScheduleRepository.saveAndFlush(mPrequalificationSchedule);

        int databaseSizeBeforeDelete = mPrequalificationScheduleRepository.findAll().size();

        // Delete the mPrequalificationSchedule
        restMPrequalificationScheduleMockMvc.perform(delete("/api/m-prequalification-schedules/{id}", mPrequalificationSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationSchedule> mPrequalificationScheduleList = mPrequalificationScheduleRepository.findAll();
        assertThat(mPrequalificationScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
