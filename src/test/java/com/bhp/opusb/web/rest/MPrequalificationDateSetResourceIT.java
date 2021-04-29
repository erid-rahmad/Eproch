package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationDateSet;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MPrequalificationDateSetRepository;
import com.bhp.opusb.service.MPrequalificationDateSetService;
import com.bhp.opusb.service.dto.MPrequalificationDateSetDTO;
import com.bhp.opusb.service.mapper.MPrequalificationDateSetMapper;
import com.bhp.opusb.service.dto.MPrequalificationDateSetCriteria;
import com.bhp.opusb.service.MPrequalificationDateSetQueryService;

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
 * Integration tests for the {@link MPrequalificationDateSetResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationDateSetResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationDateSetRepository mPrequalificationDateSetRepository;

    @Autowired
    private MPrequalificationDateSetMapper mPrequalificationDateSetMapper;

    @Autowired
    private MPrequalificationDateSetService mPrequalificationDateSetService;

    @Autowired
    private MPrequalificationDateSetQueryService mPrequalificationDateSetQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationDateSetMockMvc;

    private MPrequalificationDateSet mPrequalificationDateSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationDateSet createEntity(EntityManager em) {
        MPrequalificationDateSet mPrequalificationDateSet = new MPrequalificationDateSet()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS)
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
        mPrequalificationDateSet.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationDateSet.setAdOrganization(aDOrganization);
        return mPrequalificationDateSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationDateSet createUpdatedEntity(EntityManager em) {
        MPrequalificationDateSet mPrequalificationDateSet = new MPrequalificationDateSet()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS)
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
        mPrequalificationDateSet.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationDateSet.setAdOrganization(aDOrganization);
        return mPrequalificationDateSet;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationDateSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationDateSet() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationDateSetRepository.findAll().size();

        // Create the MPrequalificationDateSet
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO = mPrequalificationDateSetMapper.toDto(mPrequalificationDateSet);
        restMPrequalificationDateSetMockMvc.perform(post("/api/m-prequalification-date-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationDateSetDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationDateSet in the database
        List<MPrequalificationDateSet> mPrequalificationDateSetList = mPrequalificationDateSetRepository.findAll();
        assertThat(mPrequalificationDateSetList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationDateSet testMPrequalificationDateSet = mPrequalificationDateSetList.get(mPrequalificationDateSetList.size() - 1);
        assertThat(testMPrequalificationDateSet.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMPrequalificationDateSet.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMPrequalificationDateSet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPrequalificationDateSet.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationDateSet.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationDateSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationDateSetRepository.findAll().size();

        // Create the MPrequalificationDateSet with an existing ID
        mPrequalificationDateSet.setId(1L);
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO = mPrequalificationDateSetMapper.toDto(mPrequalificationDateSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationDateSetMockMvc.perform(post("/api/m-prequalification-date-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationDateSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationDateSet in the database
        List<MPrequalificationDateSet> mPrequalificationDateSetList = mPrequalificationDateSetRepository.findAll();
        assertThat(mPrequalificationDateSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSets() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationDateSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationDateSet() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get the mPrequalificationDateSet
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets/{id}", mPrequalificationDateSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationDateSet.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationDateSetsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        Long id = mPrequalificationDateSet.getId();

        defaultMPrequalificationDateSetShouldBeFound("id.equals=" + id);
        defaultMPrequalificationDateSetShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationDateSetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationDateSetShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationDateSetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationDateSetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate equals to DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate equals to UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate not equals to DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate not equals to UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate equals to UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate is not null
        defaultMPrequalificationDateSetShouldBeFound("startDate.specified=true");

        // Get all the mPrequalificationDateSetList where startDate is null
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate is less than or equal to SMALLER_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate is less than DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate is less than UPDATED_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where startDate is greater than DEFAULT_START_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mPrequalificationDateSetList where startDate is greater than SMALLER_START_DATE
        defaultMPrequalificationDateSetShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate equals to DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate equals to UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate not equals to DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate not equals to UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate equals to UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate is not null
        defaultMPrequalificationDateSetShouldBeFound("endDate.specified=true");

        // Get all the mPrequalificationDateSetList where endDate is null
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate is greater than or equal to UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate is less than or equal to DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate is less than or equal to SMALLER_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate is less than DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate is less than UPDATED_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where endDate is greater than DEFAULT_END_DATE
        defaultMPrequalificationDateSetShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the mPrequalificationDateSetList where endDate is greater than SMALLER_END_DATE
        defaultMPrequalificationDateSetShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status equals to DEFAULT_STATUS
        defaultMPrequalificationDateSetShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mPrequalificationDateSetList where status equals to UPDATED_STATUS
        defaultMPrequalificationDateSetShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status not equals to DEFAULT_STATUS
        defaultMPrequalificationDateSetShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mPrequalificationDateSetList where status not equals to UPDATED_STATUS
        defaultMPrequalificationDateSetShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMPrequalificationDateSetShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mPrequalificationDateSetList where status equals to UPDATED_STATUS
        defaultMPrequalificationDateSetShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status is not null
        defaultMPrequalificationDateSetShouldBeFound("status.specified=true");

        // Get all the mPrequalificationDateSetList where status is null
        defaultMPrequalificationDateSetShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status contains DEFAULT_STATUS
        defaultMPrequalificationDateSetShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mPrequalificationDateSetList where status contains UPDATED_STATUS
        defaultMPrequalificationDateSetShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where status does not contain DEFAULT_STATUS
        defaultMPrequalificationDateSetShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mPrequalificationDateSetList where status does not contain UPDATED_STATUS
        defaultMPrequalificationDateSetShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where uid equals to DEFAULT_UID
        defaultMPrequalificationDateSetShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationDateSetList where uid equals to UPDATED_UID
        defaultMPrequalificationDateSetShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where uid not equals to DEFAULT_UID
        defaultMPrequalificationDateSetShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationDateSetList where uid not equals to UPDATED_UID
        defaultMPrequalificationDateSetShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationDateSetShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationDateSetList where uid equals to UPDATED_UID
        defaultMPrequalificationDateSetShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where uid is not null
        defaultMPrequalificationDateSetShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationDateSetList where uid is null
        defaultMPrequalificationDateSetShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationDateSetShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationDateSetList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationDateSetShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationDateSetShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationDateSetList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationDateSetShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationDateSetShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationDateSetList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationDateSetShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        // Get all the mPrequalificationDateSetList where active is not null
        defaultMPrequalificationDateSetShouldBeFound("active.specified=true");

        // Get all the mPrequalificationDateSetList where active is null
        defaultMPrequalificationDateSetShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mPrequalificationDateSet.getBiddingSchedule();
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mPrequalificationDateSetList where biddingSchedule equals to biddingScheduleId
        defaultMPrequalificationDateSetShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mPrequalificationDateSetList where biddingSchedule equals to biddingScheduleId + 1
        defaultMPrequalificationDateSetShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationDateSetsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationDateSet.getAdOrganization();
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationDateSetList where adOrganization equals to adOrganizationId
        defaultMPrequalificationDateSetShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationDateSetList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationDateSetShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationDateSetShouldBeFound(String filter) throws Exception {
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationDateSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationDateSetShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationDateSet() throws Exception {
        // Get the mPrequalificationDateSet
        restMPrequalificationDateSetMockMvc.perform(get("/api/m-prequalification-date-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationDateSet() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        int databaseSizeBeforeUpdate = mPrequalificationDateSetRepository.findAll().size();

        // Update the mPrequalificationDateSet
        MPrequalificationDateSet updatedMPrequalificationDateSet = mPrequalificationDateSetRepository.findById(mPrequalificationDateSet.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationDateSet are not directly saved in db
        em.detach(updatedMPrequalificationDateSet);
        updatedMPrequalificationDateSet
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO = mPrequalificationDateSetMapper.toDto(updatedMPrequalificationDateSet);

        restMPrequalificationDateSetMockMvc.perform(put("/api/m-prequalification-date-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationDateSetDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationDateSet in the database
        List<MPrequalificationDateSet> mPrequalificationDateSetList = mPrequalificationDateSetRepository.findAll();
        assertThat(mPrequalificationDateSetList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationDateSet testMPrequalificationDateSet = mPrequalificationDateSetList.get(mPrequalificationDateSetList.size() - 1);
        assertThat(testMPrequalificationDateSet.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMPrequalificationDateSet.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMPrequalificationDateSet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPrequalificationDateSet.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationDateSet.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationDateSet() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationDateSetRepository.findAll().size();

        // Create the MPrequalificationDateSet
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO = mPrequalificationDateSetMapper.toDto(mPrequalificationDateSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationDateSetMockMvc.perform(put("/api/m-prequalification-date-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationDateSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationDateSet in the database
        List<MPrequalificationDateSet> mPrequalificationDateSetList = mPrequalificationDateSetRepository.findAll();
        assertThat(mPrequalificationDateSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationDateSet() throws Exception {
        // Initialize the database
        mPrequalificationDateSetRepository.saveAndFlush(mPrequalificationDateSet);

        int databaseSizeBeforeDelete = mPrequalificationDateSetRepository.findAll().size();

        // Delete the mPrequalificationDateSet
        restMPrequalificationDateSetMockMvc.perform(delete("/api/m-prequalification-date-sets/{id}", mPrequalificationDateSet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationDateSet> mPrequalificationDateSetList = mPrequalificationDateSetRepository.findAll();
        assertThat(mPrequalificationDateSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
