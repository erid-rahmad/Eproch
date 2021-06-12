package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.MBiddingSubmissionService;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import com.bhp.opusb.service.dto.MBiddingSubmissionCriteria;
import com.bhp.opusb.service.MBiddingSubmissionQueryService;

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
 * Integration tests for the {@link MBiddingSubmissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingSubmissionResourceIT {

    private static final Boolean DEFAULT_JOINED = false;
    private static final Boolean UPDATED_JOINED = true;

    private static final ZonedDateTime DEFAULT_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TRX = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final ZonedDateTime DEFAULT_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_APPROVE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_APPROVE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REJECT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_REJECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REJECTED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_SUBMIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_SUBMIT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_SUBMIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingSubmissionRepository mBiddingSubmissionRepository;

    @Autowired
    private MBiddingSubmissionMapper mBiddingSubmissionMapper;

    @Autowired
    private MBiddingSubmissionService mBiddingSubmissionService;

    @Autowired
    private MBiddingSubmissionQueryService mBiddingSubmissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingSubmissionMockMvc;

    private MBiddingSubmission mBiddingSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmission createEntity(EntityManager em) {
        MBiddingSubmission mBiddingSubmission = new MBiddingSubmission()
            .joined(DEFAULT_JOINED)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON)
            .dateSubmit(DEFAULT_DATE_SUBMIT)
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
        mBiddingSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingSubmission.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingSubmission.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingSubmission.setVendor(cVendor);
        return mBiddingSubmission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmission createUpdatedEntity(EntityManager em) {
        MBiddingSubmission mBiddingSubmission = new MBiddingSubmission()
            .joined(UPDATED_JOINED)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .dateSubmit(UPDATED_DATE_SUBMIT)
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
        mBiddingSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingSubmission.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingSubmission.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingSubmission.setVendor(cVendor);
        return mBiddingSubmission;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSubmission() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionRepository.findAll().size();

        // Create the MBiddingSubmission
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);
        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSubmission testMBiddingSubmission = mBiddingSubmissionList.get(mBiddingSubmissionList.size() - 1);
        assertThat(testMBiddingSubmission.isJoined()).isEqualTo(DEFAULT_JOINED);
        assertThat(testMBiddingSubmission.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMBiddingSubmission.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMBiddingSubmission.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMBiddingSubmission.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMBiddingSubmission.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMBiddingSubmission.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMBiddingSubmission.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMBiddingSubmission.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMBiddingSubmission.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMBiddingSubmission.getDateSubmit()).isEqualTo(DEFAULT_DATE_SUBMIT);
        assertThat(testMBiddingSubmission.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSubmission.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionRepository.findAll().size();

        // Create the MBiddingSubmission with an existing ID
        mBiddingSubmission.setId(1L);
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionRepository.findAll().size();
        // set the field null
        mBiddingSubmission.setDocumentAction(null);

        // Create the MBiddingSubmission, which fails.
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionRepository.findAll().size();
        // set the field null
        mBiddingSubmission.setDocumentStatus(null);

        // Create the MBiddingSubmission, which fails.
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissions() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].joined").value(hasItem(DEFAULT_JOINED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].dateSubmit").value(hasItem(sameInstant(DEFAULT_DATE_SUBMIT))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/{id}", mBiddingSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSubmission.getId().intValue()))
            .andExpect(jsonPath("$.joined").value(DEFAULT_JOINED.booleanValue()))
            .andExpect(jsonPath("$.dateTrx").value(sameInstant(DEFAULT_DATE_TRX)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(sameInstant(DEFAULT_DATE_APPROVE)))
            .andExpect(jsonPath("$.dateReject").value(sameInstant(DEFAULT_DATE_REJECT)))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.dateSubmit").value(sameInstant(DEFAULT_DATE_SUBMIT)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        Long id = mBiddingSubmission.getId();

        defaultMBiddingSubmissionShouldBeFound("id.equals=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingSubmissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingSubmissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joined equals to DEFAULT_JOINED
        defaultMBiddingSubmissionShouldBeFound("joined.equals=" + DEFAULT_JOINED);

        // Get all the mBiddingSubmissionList where joined equals to UPDATED_JOINED
        defaultMBiddingSubmissionShouldNotBeFound("joined.equals=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joined not equals to DEFAULT_JOINED
        defaultMBiddingSubmissionShouldNotBeFound("joined.notEquals=" + DEFAULT_JOINED);

        // Get all the mBiddingSubmissionList where joined not equals to UPDATED_JOINED
        defaultMBiddingSubmissionShouldBeFound("joined.notEquals=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinedIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joined in DEFAULT_JOINED or UPDATED_JOINED
        defaultMBiddingSubmissionShouldBeFound("joined.in=" + DEFAULT_JOINED + "," + UPDATED_JOINED);

        // Get all the mBiddingSubmissionList where joined equals to UPDATED_JOINED
        defaultMBiddingSubmissionShouldNotBeFound("joined.in=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joined is not null
        defaultMBiddingSubmissionShouldBeFound("joined.specified=true");

        // Get all the mBiddingSubmissionList where joined is null
        defaultMBiddingSubmissionShouldNotBeFound("joined.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx is not null
        defaultMBiddingSubmissionShouldBeFound("dateTrx.specified=true");

        // Get all the mBiddingSubmissionList where dateTrx is null
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx is less than UPDATED_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMBiddingSubmissionShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mBiddingSubmissionList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMBiddingSubmissionShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMBiddingSubmissionShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingSubmissionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingSubmissionList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mBiddingSubmissionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo is not null
        defaultMBiddingSubmissionShouldBeFound("documentNo.specified=true");

        // Get all the mBiddingSubmissionList where documentNo is null
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMBiddingSubmissionShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingSubmissionList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMBiddingSubmissionShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mBiddingSubmissionList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMBiddingSubmissionShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingSubmissionList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mBiddingSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction is not null
        defaultMBiddingSubmissionShouldBeFound("documentAction.specified=true");

        // Get all the mBiddingSubmissionList where documentAction is null
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingSubmissionList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mBiddingSubmissionList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMBiddingSubmissionShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingSubmissionList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mBiddingSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus is not null
        defaultMBiddingSubmissionShouldBeFound("documentStatus.specified=true");

        // Get all the mBiddingSubmissionList where documentStatus is null
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingSubmissionList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mBiddingSubmissionList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMBiddingSubmissionShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where approved equals to DEFAULT_APPROVED
        defaultMBiddingSubmissionShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mBiddingSubmissionList where approved equals to UPDATED_APPROVED
        defaultMBiddingSubmissionShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where approved not equals to DEFAULT_APPROVED
        defaultMBiddingSubmissionShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mBiddingSubmissionList where approved not equals to UPDATED_APPROVED
        defaultMBiddingSubmissionShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMBiddingSubmissionShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mBiddingSubmissionList where approved equals to UPDATED_APPROVED
        defaultMBiddingSubmissionShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where approved is not null
        defaultMBiddingSubmissionShouldBeFound("approved.specified=true");

        // Get all the mBiddingSubmissionList where approved is null
        defaultMBiddingSubmissionShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where processed equals to DEFAULT_PROCESSED
        defaultMBiddingSubmissionShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mBiddingSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMBiddingSubmissionShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where processed not equals to DEFAULT_PROCESSED
        defaultMBiddingSubmissionShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mBiddingSubmissionList where processed not equals to UPDATED_PROCESSED
        defaultMBiddingSubmissionShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMBiddingSubmissionShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mBiddingSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMBiddingSubmissionShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where processed is not null
        defaultMBiddingSubmissionShouldBeFound("processed.specified=true");

        // Get all the mBiddingSubmissionList where processed is null
        defaultMBiddingSubmissionShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove is not null
        defaultMBiddingSubmissionShouldBeFound("dateApprove.specified=true");

        // Get all the mBiddingSubmissionList where dateApprove is null
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMBiddingSubmissionShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mBiddingSubmissionList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMBiddingSubmissionShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject is not null
        defaultMBiddingSubmissionShouldBeFound("dateReject.specified=true");

        // Get all the mBiddingSubmissionList where dateReject is null
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject is less than UPDATED_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMBiddingSubmissionShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mBiddingSubmissionList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMBiddingSubmissionShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingSubmissionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingSubmissionList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mBiddingSubmissionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason is not null
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.specified=true");

        // Get all the mBiddingSubmissionList where rejectedReason is null
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingSubmissionList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMBiddingSubmissionShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mBiddingSubmissionList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMBiddingSubmissionShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit equals to DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.equals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.equals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit not equals to DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.notEquals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit not equals to UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.notEquals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit in DEFAULT_DATE_SUBMIT or UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.in=" + DEFAULT_DATE_SUBMIT + "," + UPDATED_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.in=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit is not null
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.specified=true");

        // Get all the mBiddingSubmissionList where dateSubmit is null
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit is greater than or equal to DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.greaterThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit is greater than or equal to UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.greaterThanOrEqual=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit is less than or equal to DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.lessThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit is less than or equal to SMALLER_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.lessThanOrEqual=" + SMALLER_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit is less than DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.lessThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit is less than UPDATED_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.lessThan=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDateSubmitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where dateSubmit is greater than DEFAULT_DATE_SUBMIT
        defaultMBiddingSubmissionShouldNotBeFound("dateSubmit.greaterThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mBiddingSubmissionList where dateSubmit is greater than SMALLER_DATE_SUBMIT
        defaultMBiddingSubmissionShouldBeFound("dateSubmit.greaterThan=" + SMALLER_DATE_SUBMIT);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid equals to DEFAULT_UID
        defaultMBiddingSubmissionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid not equals to DEFAULT_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionList where uid not equals to UPDATED_UID
        defaultMBiddingSubmissionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingSubmissionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingSubmissionList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid is not null
        defaultMBiddingSubmissionShouldBeFound("uid.specified=true");

        // Get all the mBiddingSubmissionList where uid is null
        defaultMBiddingSubmissionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionList where active not equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingSubmissionList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active is not null
        defaultMBiddingSubmissionShouldBeFound("active.specified=true");

        // Get all the mBiddingSubmissionList where active is null
        defaultMBiddingSubmissionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSubmission.getAdOrganization();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingSubmissionList where adOrganization equals to adOrganizationId
        defaultMBiddingSubmissionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingSubmissionList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingSubmissionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingSubmission.getBidding();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long biddingId = bidding.getId();

        // Get all the mBiddingSubmissionList where bidding equals to biddingId
        defaultMBiddingSubmissionShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingSubmissionList where bidding equals to biddingId + 1
        defaultMBiddingSubmissionShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mBiddingSubmission.getBiddingSchedule();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mBiddingSubmissionList where biddingSchedule equals to biddingScheduleId
        defaultMBiddingSubmissionShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mBiddingSubmissionList where biddingSchedule equals to biddingScheduleId + 1
        defaultMBiddingSubmissionShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        CDocumentType documentType = CDocumentTypeResourceIT.createEntity(em);
        em.persist(documentType);
        em.flush();
        mBiddingSubmission.setDocumentType(documentType);
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long documentTypeId = documentType.getId();

        // Get all the mBiddingSubmissionList where documentType equals to documentTypeId
        defaultMBiddingSubmissionShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mBiddingSubmissionList where documentType equals to documentTypeId + 1
        defaultMBiddingSubmissionShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mBiddingSubmission.getVendor();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long vendorId = vendor.getId();

        // Get all the mBiddingSubmissionList where vendor equals to vendorId
        defaultMBiddingSubmissionShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBiddingSubmissionList where vendor equals to vendorId + 1
        defaultMBiddingSubmissionShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingSubmissionShouldBeFound(String filter) throws Exception {
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].joined").value(hasItem(DEFAULT_JOINED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].dateSubmit").value(hasItem(sameInstant(DEFAULT_DATE_SUBMIT))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingSubmissionShouldNotBeFound(String filter) throws Exception {
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingSubmission() throws Exception {
        // Get the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        int databaseSizeBeforeUpdate = mBiddingSubmissionRepository.findAll().size();

        // Update the mBiddingSubmission
        MBiddingSubmission updatedMBiddingSubmission = mBiddingSubmissionRepository.findById(mBiddingSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSubmission are not directly saved in db
        em.detach(updatedMBiddingSubmission);
        updatedMBiddingSubmission
            .joined(UPDATED_JOINED)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .dateSubmit(UPDATED_DATE_SUBMIT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(updatedMBiddingSubmission);

        restMBiddingSubmissionMockMvc.perform(put("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSubmission testMBiddingSubmission = mBiddingSubmissionList.get(mBiddingSubmissionList.size() - 1);
        assertThat(testMBiddingSubmission.isJoined()).isEqualTo(UPDATED_JOINED);
        assertThat(testMBiddingSubmission.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMBiddingSubmission.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMBiddingSubmission.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMBiddingSubmission.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMBiddingSubmission.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMBiddingSubmission.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMBiddingSubmission.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMBiddingSubmission.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMBiddingSubmission.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMBiddingSubmission.getDateSubmit()).isEqualTo(UPDATED_DATE_SUBMIT);
        assertThat(testMBiddingSubmission.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSubmission.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSubmission() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingSubmissionRepository.findAll().size();

        // Create the MBiddingSubmission
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingSubmissionMockMvc.perform(put("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        int databaseSizeBeforeDelete = mBiddingSubmissionRepository.findAll().size();

        // Delete the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(delete("/api/m-bidding-submissions/{id}", mBiddingSubmission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
