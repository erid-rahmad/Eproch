package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalRegistration;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalAnnouncement;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MPrequalRegistrationRepository;
import com.bhp.opusb.service.MPrequalRegistrationService;
import com.bhp.opusb.service.dto.MPrequalRegistrationDTO;
import com.bhp.opusb.service.mapper.MPrequalRegistrationMapper;
import com.bhp.opusb.service.dto.MPrequalRegistrationCriteria;
import com.bhp.opusb.service.MPrequalRegistrationQueryService;

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
 * Integration tests for the {@link MPrequalRegistrationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalRegistrationResourceIT {

    private static final String DEFAULT_REGISTRATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ANSWER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ANSWER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ANSWER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalRegistrationRepository mPrequalRegistrationRepository;

    @Autowired
    private MPrequalRegistrationMapper mPrequalRegistrationMapper;

    @Autowired
    private MPrequalRegistrationService mPrequalRegistrationService;

    @Autowired
    private MPrequalRegistrationQueryService mPrequalRegistrationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalRegistrationMockMvc;

    private MPrequalRegistration mPrequalRegistration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalRegistration createEntity(EntityManager em) {
        MPrequalRegistration mPrequalRegistration = new MPrequalRegistration()
            .registrationStatus(DEFAULT_REGISTRATION_STATUS)
            .reason(DEFAULT_REASON)
            .answerDate(DEFAULT_ANSWER_DATE)
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
        mPrequalRegistration.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalAnnouncement mPrequalAnnouncement;
        if (TestUtil.findAll(em, MPrequalAnnouncement.class).isEmpty()) {
            mPrequalAnnouncement = MPrequalAnnouncementResourceIT.createEntity(em);
            em.persist(mPrequalAnnouncement);
            em.flush();
        } else {
            mPrequalAnnouncement = TestUtil.findAll(em, MPrequalAnnouncement.class).get(0);
        }
        mPrequalRegistration.setAnnouncement(mPrequalAnnouncement);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalRegistration.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalRegistration.setVendor(cVendor);
        return mPrequalRegistration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalRegistration createUpdatedEntity(EntityManager em) {
        MPrequalRegistration mPrequalRegistration = new MPrequalRegistration()
            .registrationStatus(UPDATED_REGISTRATION_STATUS)
            .reason(UPDATED_REASON)
            .answerDate(UPDATED_ANSWER_DATE)
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
        mPrequalRegistration.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalAnnouncement mPrequalAnnouncement;
        if (TestUtil.findAll(em, MPrequalAnnouncement.class).isEmpty()) {
            mPrequalAnnouncement = MPrequalAnnouncementResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalAnnouncement);
            em.flush();
        } else {
            mPrequalAnnouncement = TestUtil.findAll(em, MPrequalAnnouncement.class).get(0);
        }
        mPrequalRegistration.setAnnouncement(mPrequalAnnouncement);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalRegistration.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalRegistration.setVendor(cVendor);
        return mPrequalRegistration;
    }

    @BeforeEach
    public void initTest() {
        mPrequalRegistration = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalRegistration() throws Exception {
        int databaseSizeBeforeCreate = mPrequalRegistrationRepository.findAll().size();

        // Create the MPrequalRegistration
        MPrequalRegistrationDTO mPrequalRegistrationDTO = mPrequalRegistrationMapper.toDto(mPrequalRegistration);
        restMPrequalRegistrationMockMvc.perform(post("/api/m-prequal-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalRegistrationDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalRegistration in the database
        List<MPrequalRegistration> mPrequalRegistrationList = mPrequalRegistrationRepository.findAll();
        assertThat(mPrequalRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalRegistration testMPrequalRegistration = mPrequalRegistrationList.get(mPrequalRegistrationList.size() - 1);
        assertThat(testMPrequalRegistration.getRegistrationStatus()).isEqualTo(DEFAULT_REGISTRATION_STATUS);
        assertThat(testMPrequalRegistration.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testMPrequalRegistration.getAnswerDate()).isEqualTo(DEFAULT_ANSWER_DATE);
        assertThat(testMPrequalRegistration.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalRegistration.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalRegistrationRepository.findAll().size();

        // Create the MPrequalRegistration with an existing ID
        mPrequalRegistration.setId(1L);
        MPrequalRegistrationDTO mPrequalRegistrationDTO = mPrequalRegistrationMapper.toDto(mPrequalRegistration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalRegistrationMockMvc.perform(post("/api/m-prequal-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalRegistration in the database
        List<MPrequalRegistration> mPrequalRegistrationList = mPrequalRegistrationRepository.findAll();
        assertThat(mPrequalRegistrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrations() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationStatus").value(hasItem(DEFAULT_REGISTRATION_STATUS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].answerDate").value(hasItem(sameInstant(DEFAULT_ANSWER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalRegistration() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get the mPrequalRegistration
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations/{id}", mPrequalRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalRegistration.getId().intValue()))
            .andExpect(jsonPath("$.registrationStatus").value(DEFAULT_REGISTRATION_STATUS))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.answerDate").value(sameInstant(DEFAULT_ANSWER_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalRegistrationsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        Long id = mPrequalRegistration.getId();

        defaultMPrequalRegistrationShouldBeFound("id.equals=" + id);
        defaultMPrequalRegistrationShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalRegistrationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalRegistrationShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalRegistrationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalRegistrationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus equals to DEFAULT_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.equals=" + DEFAULT_REGISTRATION_STATUS);

        // Get all the mPrequalRegistrationList where registrationStatus equals to UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.equals=" + UPDATED_REGISTRATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus not equals to DEFAULT_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.notEquals=" + DEFAULT_REGISTRATION_STATUS);

        // Get all the mPrequalRegistrationList where registrationStatus not equals to UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.notEquals=" + UPDATED_REGISTRATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus in DEFAULT_REGISTRATION_STATUS or UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.in=" + DEFAULT_REGISTRATION_STATUS + "," + UPDATED_REGISTRATION_STATUS);

        // Get all the mPrequalRegistrationList where registrationStatus equals to UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.in=" + UPDATED_REGISTRATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus is not null
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.specified=true");

        // Get all the mPrequalRegistrationList where registrationStatus is null
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus contains DEFAULT_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.contains=" + DEFAULT_REGISTRATION_STATUS);

        // Get all the mPrequalRegistrationList where registrationStatus contains UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.contains=" + UPDATED_REGISTRATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByRegistrationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where registrationStatus does not contain DEFAULT_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldNotBeFound("registrationStatus.doesNotContain=" + DEFAULT_REGISTRATION_STATUS);

        // Get all the mPrequalRegistrationList where registrationStatus does not contain UPDATED_REGISTRATION_STATUS
        defaultMPrequalRegistrationShouldBeFound("registrationStatus.doesNotContain=" + UPDATED_REGISTRATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason equals to DEFAULT_REASON
        defaultMPrequalRegistrationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the mPrequalRegistrationList where reason equals to UPDATED_REASON
        defaultMPrequalRegistrationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason not equals to DEFAULT_REASON
        defaultMPrequalRegistrationShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the mPrequalRegistrationList where reason not equals to UPDATED_REASON
        defaultMPrequalRegistrationShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultMPrequalRegistrationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the mPrequalRegistrationList where reason equals to UPDATED_REASON
        defaultMPrequalRegistrationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason is not null
        defaultMPrequalRegistrationShouldBeFound("reason.specified=true");

        // Get all the mPrequalRegistrationList where reason is null
        defaultMPrequalRegistrationShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason contains DEFAULT_REASON
        defaultMPrequalRegistrationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the mPrequalRegistrationList where reason contains UPDATED_REASON
        defaultMPrequalRegistrationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where reason does not contain DEFAULT_REASON
        defaultMPrequalRegistrationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the mPrequalRegistrationList where reason does not contain UPDATED_REASON
        defaultMPrequalRegistrationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate equals to DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.equals=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate equals to UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.equals=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate not equals to DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.notEquals=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate not equals to UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.notEquals=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate in DEFAULT_ANSWER_DATE or UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.in=" + DEFAULT_ANSWER_DATE + "," + UPDATED_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate equals to UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.in=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate is not null
        defaultMPrequalRegistrationShouldBeFound("answerDate.specified=true");

        // Get all the mPrequalRegistrationList where answerDate is null
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate is greater than or equal to DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.greaterThanOrEqual=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate is greater than or equal to UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.greaterThanOrEqual=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate is less than or equal to DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.lessThanOrEqual=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate is less than or equal to SMALLER_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.lessThanOrEqual=" + SMALLER_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate is less than DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.lessThan=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate is less than UPDATED_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.lessThan=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnswerDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where answerDate is greater than DEFAULT_ANSWER_DATE
        defaultMPrequalRegistrationShouldNotBeFound("answerDate.greaterThan=" + DEFAULT_ANSWER_DATE);

        // Get all the mPrequalRegistrationList where answerDate is greater than SMALLER_ANSWER_DATE
        defaultMPrequalRegistrationShouldBeFound("answerDate.greaterThan=" + SMALLER_ANSWER_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where uid equals to DEFAULT_UID
        defaultMPrequalRegistrationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalRegistrationList where uid equals to UPDATED_UID
        defaultMPrequalRegistrationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where uid not equals to DEFAULT_UID
        defaultMPrequalRegistrationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalRegistrationList where uid not equals to UPDATED_UID
        defaultMPrequalRegistrationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalRegistrationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalRegistrationList where uid equals to UPDATED_UID
        defaultMPrequalRegistrationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where uid is not null
        defaultMPrequalRegistrationShouldBeFound("uid.specified=true");

        // Get all the mPrequalRegistrationList where uid is null
        defaultMPrequalRegistrationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where active equals to DEFAULT_ACTIVE
        defaultMPrequalRegistrationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalRegistrationList where active equals to UPDATED_ACTIVE
        defaultMPrequalRegistrationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalRegistrationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalRegistrationList where active not equals to UPDATED_ACTIVE
        defaultMPrequalRegistrationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalRegistrationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalRegistrationList where active equals to UPDATED_ACTIVE
        defaultMPrequalRegistrationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        // Get all the mPrequalRegistrationList where active is not null
        defaultMPrequalRegistrationShouldBeFound("active.specified=true");

        // Get all the mPrequalRegistrationList where active is null
        defaultMPrequalRegistrationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalRegistration.getAdOrganization();
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalRegistrationList where adOrganization equals to adOrganizationId
        defaultMPrequalRegistrationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalRegistrationList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalRegistrationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByAnnouncementIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalAnnouncement announcement = mPrequalRegistration.getAnnouncement();
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);
        Long announcementId = announcement.getId();

        // Get all the mPrequalRegistrationList where announcement equals to announcementId
        defaultMPrequalRegistrationShouldBeFound("announcementId.equals=" + announcementId);

        // Get all the mPrequalRegistrationList where announcement equals to announcementId + 1
        defaultMPrequalRegistrationShouldNotBeFound("announcementId.equals=" + (announcementId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalRegistration.getPrequalification();
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalRegistrationList where prequalification equals to prequalificationId
        defaultMPrequalRegistrationShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalRegistrationList where prequalification equals to prequalificationId + 1
        defaultMPrequalRegistrationShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalRegistrationsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPrequalRegistration.getVendor();
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);
        Long vendorId = vendor.getId();

        // Get all the mPrequalRegistrationList where vendor equals to vendorId
        defaultMPrequalRegistrationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPrequalRegistrationList where vendor equals to vendorId + 1
        defaultMPrequalRegistrationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalRegistrationShouldBeFound(String filter) throws Exception {
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationStatus").value(hasItem(DEFAULT_REGISTRATION_STATUS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].answerDate").value(hasItem(sameInstant(DEFAULT_ANSWER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalRegistrationShouldNotBeFound(String filter) throws Exception {
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalRegistration() throws Exception {
        // Get the mPrequalRegistration
        restMPrequalRegistrationMockMvc.perform(get("/api/m-prequal-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalRegistration() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        int databaseSizeBeforeUpdate = mPrequalRegistrationRepository.findAll().size();

        // Update the mPrequalRegistration
        MPrequalRegistration updatedMPrequalRegistration = mPrequalRegistrationRepository.findById(mPrequalRegistration.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalRegistration are not directly saved in db
        em.detach(updatedMPrequalRegistration);
        updatedMPrequalRegistration
            .registrationStatus(UPDATED_REGISTRATION_STATUS)
            .reason(UPDATED_REASON)
            .answerDate(UPDATED_ANSWER_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalRegistrationDTO mPrequalRegistrationDTO = mPrequalRegistrationMapper.toDto(updatedMPrequalRegistration);

        restMPrequalRegistrationMockMvc.perform(put("/api/m-prequal-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalRegistrationDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalRegistration in the database
        List<MPrequalRegistration> mPrequalRegistrationList = mPrequalRegistrationRepository.findAll();
        assertThat(mPrequalRegistrationList).hasSize(databaseSizeBeforeUpdate);
        MPrequalRegistration testMPrequalRegistration = mPrequalRegistrationList.get(mPrequalRegistrationList.size() - 1);
        assertThat(testMPrequalRegistration.getRegistrationStatus()).isEqualTo(UPDATED_REGISTRATION_STATUS);
        assertThat(testMPrequalRegistration.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testMPrequalRegistration.getAnswerDate()).isEqualTo(UPDATED_ANSWER_DATE);
        assertThat(testMPrequalRegistration.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalRegistration.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalRegistration() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalRegistrationRepository.findAll().size();

        // Create the MPrequalRegistration
        MPrequalRegistrationDTO mPrequalRegistrationDTO = mPrequalRegistrationMapper.toDto(mPrequalRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalRegistrationMockMvc.perform(put("/api/m-prequal-registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalRegistration in the database
        List<MPrequalRegistration> mPrequalRegistrationList = mPrequalRegistrationRepository.findAll();
        assertThat(mPrequalRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalRegistration() throws Exception {
        // Initialize the database
        mPrequalRegistrationRepository.saveAndFlush(mPrequalRegistration);

        int databaseSizeBeforeDelete = mPrequalRegistrationRepository.findAll().size();

        // Delete the mPrequalRegistration
        restMPrequalRegistrationMockMvc.perform(delete("/api/m-prequal-registrations/{id}", mPrequalRegistration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalRegistration> mPrequalRegistrationList = mPrequalRegistrationRepository.findAll();
        assertThat(mPrequalRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
