package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.service.MBiddingScheduleService;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;
import com.bhp.opusb.service.dto.MBiddingScheduleCriteria;
import com.bhp.opusb.service.MBiddingScheduleQueryService;

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
 * Integration tests for the {@link MBiddingScheduleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingScheduleResourceIT {

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
    private MBiddingScheduleRepository mBiddingScheduleRepository;

    @Autowired
    private MBiddingScheduleMapper mBiddingScheduleMapper;

    @Autowired
    private MBiddingScheduleService mBiddingScheduleService;

    @Autowired
    private MBiddingScheduleQueryService mBiddingScheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingScheduleMockMvc;

    private MBiddingSchedule mBiddingSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSchedule createEntity(EntityManager em) {
        MBiddingSchedule mBiddingSchedule = new MBiddingSchedule()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingSchedule.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CEventTypeline cEventTypeline;
        if (TestUtil.findAll(em, CEventTypeline.class).isEmpty()) {
            cEventTypeline = CEventTypelineResourceIT.createEntity(em);
            em.persist(cEventTypeline);
            em.flush();
        } else {
            cEventTypeline = TestUtil.findAll(em, CEventTypeline.class).get(0);
        }
        mBiddingSchedule.setEventTypeLine(cEventTypeline);
        return mBiddingSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSchedule createUpdatedEntity(EntityManager em) {
        MBiddingSchedule mBiddingSchedule = new MBiddingSchedule()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingSchedule.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        CEventTypeline cEventTypeline;
        if (TestUtil.findAll(em, CEventTypeline.class).isEmpty()) {
            cEventTypeline = CEventTypelineResourceIT.createUpdatedEntity(em);
            em.persist(cEventTypeline);
            em.flush();
        } else {
            cEventTypeline = TestUtil.findAll(em, CEventTypeline.class).get(0);
        }
        mBiddingSchedule.setEventTypeLine(cEventTypeline);
        return mBiddingSchedule;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSchedule() throws Exception {
        int databaseSizeBeforeCreate = mBiddingScheduleRepository.findAll().size();

        // Create the MBiddingSchedule
        MBiddingScheduleDTO mBiddingScheduleDTO = mBiddingScheduleMapper.toDto(mBiddingSchedule);
        restMBiddingScheduleMockMvc.perform(post("/api/m-bidding-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSchedule in the database
        List<MBiddingSchedule> mBiddingScheduleList = mBiddingScheduleRepository.findAll();
        assertThat(mBiddingScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSchedule testMBiddingSchedule = mBiddingScheduleList.get(mBiddingScheduleList.size() - 1);
        assertThat(testMBiddingSchedule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMBiddingSchedule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMBiddingSchedule.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSchedule.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingScheduleRepository.findAll().size();

        // Create the MBiddingSchedule with an existing ID
        mBiddingSchedule.setId(1L);
        MBiddingScheduleDTO mBiddingScheduleDTO = mBiddingScheduleMapper.toDto(mBiddingSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingScheduleMockMvc.perform(post("/api/m-bidding-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSchedule in the database
        List<MBiddingSchedule> mBiddingScheduleList = mBiddingScheduleRepository.findAll();
        assertThat(mBiddingScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedules() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSchedule() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get the mBiddingSchedule
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules/{id}", mBiddingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSchedule.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        Long id = mBiddingSchedule.getId();

        defaultMBiddingScheduleShouldBeFound("id.equals=" + id);
        defaultMBiddingScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate equals to DEFAULT_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate equals to UPDATED_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate not equals to DEFAULT_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate not equals to UPDATED_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mBiddingScheduleList where startDate equals to UPDATED_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate is not null
        defaultMBiddingScheduleShouldBeFound("startDate.specified=true");

        // Get all the mBiddingScheduleList where startDate is null
        defaultMBiddingScheduleShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate is less than or equal to SMALLER_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate is less than DEFAULT_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate is less than UPDATED_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where startDate is greater than DEFAULT_START_DATE
        defaultMBiddingScheduleShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mBiddingScheduleList where startDate is greater than SMALLER_START_DATE
        defaultMBiddingScheduleShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate equals to DEFAULT_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate equals to UPDATED_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate not equals to DEFAULT_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate not equals to UPDATED_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mBiddingScheduleList where endDate equals to UPDATED_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate is not null
        defaultMBiddingScheduleShouldBeFound("endDate.specified=true");

        // Get all the mBiddingScheduleList where endDate is null
        defaultMBiddingScheduleShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate is greater than or equal to UPDATED_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate is less than or equal to DEFAULT_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate is less than or equal to SMALLER_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate is less than DEFAULT_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate is less than UPDATED_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where endDate is greater than DEFAULT_END_DATE
        defaultMBiddingScheduleShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the mBiddingScheduleList where endDate is greater than SMALLER_END_DATE
        defaultMBiddingScheduleShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedulesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where uid equals to DEFAULT_UID
        defaultMBiddingScheduleShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingScheduleList where uid equals to UPDATED_UID
        defaultMBiddingScheduleShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where uid not equals to DEFAULT_UID
        defaultMBiddingScheduleShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingScheduleList where uid not equals to UPDATED_UID
        defaultMBiddingScheduleShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingScheduleShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingScheduleList where uid equals to UPDATED_UID
        defaultMBiddingScheduleShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where uid is not null
        defaultMBiddingScheduleShouldBeFound("uid.specified=true");

        // Get all the mBiddingScheduleList where uid is null
        defaultMBiddingScheduleShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where active equals to DEFAULT_ACTIVE
        defaultMBiddingScheduleShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingScheduleList where active equals to UPDATED_ACTIVE
        defaultMBiddingScheduleShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingScheduleShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingScheduleList where active not equals to UPDATED_ACTIVE
        defaultMBiddingScheduleShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingScheduleShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingScheduleList where active equals to UPDATED_ACTIVE
        defaultMBiddingScheduleShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        // Get all the mBiddingScheduleList where active is not null
        defaultMBiddingScheduleShouldBeFound("active.specified=true");

        // Get all the mBiddingScheduleList where active is null
        defaultMBiddingScheduleShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSchedulesByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingSchedule.getBidding();
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);
        Long biddingId = bidding.getId();

        // Get all the mBiddingScheduleList where bidding equals to biddingId
        defaultMBiddingScheduleShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingScheduleList where bidding equals to biddingId + 1
        defaultMBiddingScheduleShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedulesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSchedule.getAdOrganization();
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingScheduleList where adOrganization equals to adOrganizationId
        defaultMBiddingScheduleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingScheduleList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingScheduleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSchedulesByEventTypeLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEventTypeline eventTypeLine = mBiddingSchedule.getEventTypeLine();
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);
        Long eventTypeLineId = eventTypeLine.getId();

        // Get all the mBiddingScheduleList where eventTypeLine equals to eventTypeLineId
        defaultMBiddingScheduleShouldBeFound("eventTypeLineId.equals=" + eventTypeLineId);

        // Get all the mBiddingScheduleList where eventTypeLine equals to eventTypeLineId + 1
        defaultMBiddingScheduleShouldNotBeFound("eventTypeLineId.equals=" + (eventTypeLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingScheduleShouldBeFound(String filter) throws Exception {
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingScheduleShouldNotBeFound(String filter) throws Exception {
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingSchedule() throws Exception {
        // Get the mBiddingSchedule
        restMBiddingScheduleMockMvc.perform(get("/api/m-bidding-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSchedule() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        int databaseSizeBeforeUpdate = mBiddingScheduleRepository.findAll().size();

        // Update the mBiddingSchedule
        MBiddingSchedule updatedMBiddingSchedule = mBiddingScheduleRepository.findById(mBiddingSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSchedule are not directly saved in db
        em.detach(updatedMBiddingSchedule);
        updatedMBiddingSchedule
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingScheduleDTO mBiddingScheduleDTO = mBiddingScheduleMapper.toDto(updatedMBiddingSchedule);

        restMBiddingScheduleMockMvc.perform(put("/api/m-bidding-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSchedule in the database
        List<MBiddingSchedule> mBiddingScheduleList = mBiddingScheduleRepository.findAll();
        assertThat(mBiddingScheduleList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSchedule testMBiddingSchedule = mBiddingScheduleList.get(mBiddingScheduleList.size() - 1);
        assertThat(testMBiddingSchedule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMBiddingSchedule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMBiddingSchedule.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSchedule.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingScheduleRepository.findAll().size();

        // Create the MBiddingSchedule
        MBiddingScheduleDTO mBiddingScheduleDTO = mBiddingScheduleMapper.toDto(mBiddingSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingScheduleMockMvc.perform(put("/api/m-bidding-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSchedule in the database
        List<MBiddingSchedule> mBiddingScheduleList = mBiddingScheduleRepository.findAll();
        assertThat(mBiddingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSchedule() throws Exception {
        // Initialize the database
        mBiddingScheduleRepository.saveAndFlush(mBiddingSchedule);

        int databaseSizeBeforeDelete = mBiddingScheduleRepository.findAll().size();

        // Delete the mBiddingSchedule
        restMBiddingScheduleMockMvc.perform(delete("/api/m-bidding-schedules/{id}", mBiddingSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSchedule> mBiddingScheduleList = mBiddingScheduleRepository.findAll();
        assertThat(mBiddingScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
