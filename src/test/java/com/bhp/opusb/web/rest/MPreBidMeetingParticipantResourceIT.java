package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPreBidMeetingParticipant;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MPreBidMeetingParticipantRepository;
import com.bhp.opusb.service.MPreBidMeetingParticipantService;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingParticipantMapper;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantCriteria;
import com.bhp.opusb.service.MPreBidMeetingParticipantQueryService;

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
 * Integration tests for the {@link MPreBidMeetingParticipantResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPreBidMeetingParticipantResourceIT {

    private static final Boolean DEFAULT_ATTENDED = false;
    private static final Boolean UPDATED_ATTENDED = true;

    private static final ZonedDateTime DEFAULT_REGISTER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository;

    @Autowired
    private MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper;

    @Autowired
    private MPreBidMeetingParticipantService mPreBidMeetingParticipantService;

    @Autowired
    private MPreBidMeetingParticipantQueryService mPreBidMeetingParticipantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPreBidMeetingParticipantMockMvc;

    private MPreBidMeetingParticipant mPreBidMeetingParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeetingParticipant createEntity(EntityManager em) {
        MPreBidMeetingParticipant mPreBidMeetingParticipant = new MPreBidMeetingParticipant()
            .attended(DEFAULT_ATTENDED)
            .registerDate(DEFAULT_REGISTER_DATE)
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
        mPreBidMeetingParticipant.setAdOrganization(aDOrganization);
        // Add required entity
        MPreBidMeeting mPreBidMeeting;
        if (TestUtil.findAll(em, MPreBidMeeting.class).isEmpty()) {
            mPreBidMeeting = MPreBidMeetingResourceIT.createEntity(em);
            em.persist(mPreBidMeeting);
            em.flush();
        } else {
            mPreBidMeeting = TestUtil.findAll(em, MPreBidMeeting.class).get(0);
        }
        mPreBidMeetingParticipant.setPreBidMeeting(mPreBidMeeting);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPreBidMeetingParticipant.setVendor(cVendor);
        return mPreBidMeetingParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreBidMeetingParticipant createUpdatedEntity(EntityManager em) {
        MPreBidMeetingParticipant mPreBidMeetingParticipant = new MPreBidMeetingParticipant()
            .attended(UPDATED_ATTENDED)
            .registerDate(UPDATED_REGISTER_DATE)
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
        mPreBidMeetingParticipant.setAdOrganization(aDOrganization);
        // Add required entity
        MPreBidMeeting mPreBidMeeting;
        if (TestUtil.findAll(em, MPreBidMeeting.class).isEmpty()) {
            mPreBidMeeting = MPreBidMeetingResourceIT.createUpdatedEntity(em);
            em.persist(mPreBidMeeting);
            em.flush();
        } else {
            mPreBidMeeting = TestUtil.findAll(em, MPreBidMeeting.class).get(0);
        }
        mPreBidMeetingParticipant.setPreBidMeeting(mPreBidMeeting);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPreBidMeetingParticipant.setVendor(cVendor);
        return mPreBidMeetingParticipant;
    }

    @BeforeEach
    public void initTest() {
        mPreBidMeetingParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPreBidMeetingParticipant() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingParticipantRepository.findAll().size();

        // Create the MPreBidMeetingParticipant
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO = mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipant);
        restMPreBidMeetingParticipantMockMvc.perform(post("/api/m-pre-bid-meeting-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the MPreBidMeetingParticipant in the database
        List<MPreBidMeetingParticipant> mPreBidMeetingParticipantList = mPreBidMeetingParticipantRepository.findAll();
        assertThat(mPreBidMeetingParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        MPreBidMeetingParticipant testMPreBidMeetingParticipant = mPreBidMeetingParticipantList.get(mPreBidMeetingParticipantList.size() - 1);
        assertThat(testMPreBidMeetingParticipant.isAttended()).isEqualTo(DEFAULT_ATTENDED);
        assertThat(testMPreBidMeetingParticipant.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
        assertThat(testMPreBidMeetingParticipant.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPreBidMeetingParticipant.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPreBidMeetingParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPreBidMeetingParticipantRepository.findAll().size();

        // Create the MPreBidMeetingParticipant with an existing ID
        mPreBidMeetingParticipant.setId(1L);
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO = mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPreBidMeetingParticipantMockMvc.perform(post("/api/m-pre-bid-meeting-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeetingParticipant in the database
        List<MPreBidMeetingParticipant> mPreBidMeetingParticipantList = mPreBidMeetingParticipantRepository.findAll();
        assertThat(mPreBidMeetingParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipants() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeetingParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].attended").value(hasItem(DEFAULT_ATTENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(sameInstant(DEFAULT_REGISTER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPreBidMeetingParticipant() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get the mPreBidMeetingParticipant
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants/{id}", mPreBidMeetingParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPreBidMeetingParticipant.getId().intValue()))
            .andExpect(jsonPath("$.attended").value(DEFAULT_ATTENDED.booleanValue()))
            .andExpect(jsonPath("$.registerDate").value(sameInstant(DEFAULT_REGISTER_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPreBidMeetingParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        Long id = mPreBidMeetingParticipant.getId();

        defaultMPreBidMeetingParticipantShouldBeFound("id.equals=" + id);
        defaultMPreBidMeetingParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultMPreBidMeetingParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPreBidMeetingParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultMPreBidMeetingParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPreBidMeetingParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByAttendedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where attended equals to DEFAULT_ATTENDED
        defaultMPreBidMeetingParticipantShouldBeFound("attended.equals=" + DEFAULT_ATTENDED);

        // Get all the mPreBidMeetingParticipantList where attended equals to UPDATED_ATTENDED
        defaultMPreBidMeetingParticipantShouldNotBeFound("attended.equals=" + UPDATED_ATTENDED);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByAttendedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where attended not equals to DEFAULT_ATTENDED
        defaultMPreBidMeetingParticipantShouldNotBeFound("attended.notEquals=" + DEFAULT_ATTENDED);

        // Get all the mPreBidMeetingParticipantList where attended not equals to UPDATED_ATTENDED
        defaultMPreBidMeetingParticipantShouldBeFound("attended.notEquals=" + UPDATED_ATTENDED);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByAttendedIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where attended in DEFAULT_ATTENDED or UPDATED_ATTENDED
        defaultMPreBidMeetingParticipantShouldBeFound("attended.in=" + DEFAULT_ATTENDED + "," + UPDATED_ATTENDED);

        // Get all the mPreBidMeetingParticipantList where attended equals to UPDATED_ATTENDED
        defaultMPreBidMeetingParticipantShouldNotBeFound("attended.in=" + UPDATED_ATTENDED);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByAttendedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where attended is not null
        defaultMPreBidMeetingParticipantShouldBeFound("attended.specified=true");

        // Get all the mPreBidMeetingParticipantList where attended is null
        defaultMPreBidMeetingParticipantShouldNotBeFound("attended.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate equals to DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.equals=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate equals to UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.equals=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate not equals to DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.notEquals=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate not equals to UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.notEquals=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate in DEFAULT_REGISTER_DATE or UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.in=" + DEFAULT_REGISTER_DATE + "," + UPDATED_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate equals to UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.in=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate is not null
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.specified=true");

        // Get all the mPreBidMeetingParticipantList where registerDate is null
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate is greater than or equal to DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.greaterThanOrEqual=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate is greater than or equal to UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.greaterThanOrEqual=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate is less than or equal to DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.lessThanOrEqual=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate is less than or equal to SMALLER_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.lessThanOrEqual=" + SMALLER_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate is less than DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.lessThan=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate is less than UPDATED_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.lessThan=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByRegisterDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where registerDate is greater than DEFAULT_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldNotBeFound("registerDate.greaterThan=" + DEFAULT_REGISTER_DATE);

        // Get all the mPreBidMeetingParticipantList where registerDate is greater than SMALLER_REGISTER_DATE
        defaultMPreBidMeetingParticipantShouldBeFound("registerDate.greaterThan=" + SMALLER_REGISTER_DATE);
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where uid equals to DEFAULT_UID
        defaultMPreBidMeetingParticipantShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingParticipantList where uid equals to UPDATED_UID
        defaultMPreBidMeetingParticipantShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where uid not equals to DEFAULT_UID
        defaultMPreBidMeetingParticipantShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPreBidMeetingParticipantList where uid not equals to UPDATED_UID
        defaultMPreBidMeetingParticipantShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPreBidMeetingParticipantShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPreBidMeetingParticipantList where uid equals to UPDATED_UID
        defaultMPreBidMeetingParticipantShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where uid is not null
        defaultMPreBidMeetingParticipantShouldBeFound("uid.specified=true");

        // Get all the mPreBidMeetingParticipantList where uid is null
        defaultMPreBidMeetingParticipantShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where active equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingParticipantShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingParticipantList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingParticipantShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where active not equals to DEFAULT_ACTIVE
        defaultMPreBidMeetingParticipantShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPreBidMeetingParticipantList where active not equals to UPDATED_ACTIVE
        defaultMPreBidMeetingParticipantShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPreBidMeetingParticipantShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPreBidMeetingParticipantList where active equals to UPDATED_ACTIVE
        defaultMPreBidMeetingParticipantShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        // Get all the mPreBidMeetingParticipantList where active is not null
        defaultMPreBidMeetingParticipantShouldBeFound("active.specified=true");

        // Get all the mPreBidMeetingParticipantList where active is null
        defaultMPreBidMeetingParticipantShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPreBidMeetingParticipant.getAdOrganization();
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPreBidMeetingParticipantList where adOrganization equals to adOrganizationId
        defaultMPreBidMeetingParticipantShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPreBidMeetingParticipantList where adOrganization equals to adOrganizationId + 1
        defaultMPreBidMeetingParticipantShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByPreBidMeetingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPreBidMeeting preBidMeeting = mPreBidMeetingParticipant.getPreBidMeeting();
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);
        Long preBidMeetingId = preBidMeeting.getId();

        // Get all the mPreBidMeetingParticipantList where preBidMeeting equals to preBidMeetingId
        defaultMPreBidMeetingParticipantShouldBeFound("preBidMeetingId.equals=" + preBidMeetingId);

        // Get all the mPreBidMeetingParticipantList where preBidMeeting equals to preBidMeetingId + 1
        defaultMPreBidMeetingParticipantShouldNotBeFound("preBidMeetingId.equals=" + (preBidMeetingId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreBidMeetingParticipantsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPreBidMeetingParticipant.getVendor();
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);
        Long vendorId = vendor.getId();

        // Get all the mPreBidMeetingParticipantList where vendor equals to vendorId
        defaultMPreBidMeetingParticipantShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPreBidMeetingParticipantList where vendor equals to vendorId + 1
        defaultMPreBidMeetingParticipantShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPreBidMeetingParticipantShouldBeFound(String filter) throws Exception {
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreBidMeetingParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].attended").value(hasItem(DEFAULT_ATTENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(sameInstant(DEFAULT_REGISTER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPreBidMeetingParticipantShouldNotBeFound(String filter) throws Exception {
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPreBidMeetingParticipant() throws Exception {
        // Get the mPreBidMeetingParticipant
        restMPreBidMeetingParticipantMockMvc.perform(get("/api/m-pre-bid-meeting-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPreBidMeetingParticipant() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        int databaseSizeBeforeUpdate = mPreBidMeetingParticipantRepository.findAll().size();

        // Update the mPreBidMeetingParticipant
        MPreBidMeetingParticipant updatedMPreBidMeetingParticipant = mPreBidMeetingParticipantRepository.findById(mPreBidMeetingParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedMPreBidMeetingParticipant are not directly saved in db
        em.detach(updatedMPreBidMeetingParticipant);
        updatedMPreBidMeetingParticipant
            .attended(UPDATED_ATTENDED)
            .registerDate(UPDATED_REGISTER_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO = mPreBidMeetingParticipantMapper.toDto(updatedMPreBidMeetingParticipant);

        restMPreBidMeetingParticipantMockMvc.perform(put("/api/m-pre-bid-meeting-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the MPreBidMeetingParticipant in the database
        List<MPreBidMeetingParticipant> mPreBidMeetingParticipantList = mPreBidMeetingParticipantRepository.findAll();
        assertThat(mPreBidMeetingParticipantList).hasSize(databaseSizeBeforeUpdate);
        MPreBidMeetingParticipant testMPreBidMeetingParticipant = mPreBidMeetingParticipantList.get(mPreBidMeetingParticipantList.size() - 1);
        assertThat(testMPreBidMeetingParticipant.isAttended()).isEqualTo(UPDATED_ATTENDED);
        assertThat(testMPreBidMeetingParticipant.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testMPreBidMeetingParticipant.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPreBidMeetingParticipant.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPreBidMeetingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = mPreBidMeetingParticipantRepository.findAll().size();

        // Create the MPreBidMeetingParticipant
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO = mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPreBidMeetingParticipantMockMvc.perform(put("/api/m-pre-bid-meeting-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreBidMeetingParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreBidMeetingParticipant in the database
        List<MPreBidMeetingParticipant> mPreBidMeetingParticipantList = mPreBidMeetingParticipantRepository.findAll();
        assertThat(mPreBidMeetingParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPreBidMeetingParticipant() throws Exception {
        // Initialize the database
        mPreBidMeetingParticipantRepository.saveAndFlush(mPreBidMeetingParticipant);

        int databaseSizeBeforeDelete = mPreBidMeetingParticipantRepository.findAll().size();

        // Delete the mPreBidMeetingParticipant
        restMPreBidMeetingParticipantMockMvc.perform(delete("/api/m-pre-bid-meeting-participants/{id}", mPreBidMeetingParticipant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPreBidMeetingParticipant> mPreBidMeetingParticipantList = mPreBidMeetingParticipantRepository.findAll();
        assertThat(mPreBidMeetingParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
