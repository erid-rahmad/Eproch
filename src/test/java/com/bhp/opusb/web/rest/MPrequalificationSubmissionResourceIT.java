package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MPrequalificationSubmissionRepository;
import com.bhp.opusb.service.MPrequalificationSubmissionService;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;
import com.bhp.opusb.service.mapper.MPrequalificationSubmissionMapper;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionCriteria;
import com.bhp.opusb.service.MPrequalificationSubmissionQueryService;

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
 * Integration tests for the {@link MPrequalificationSubmissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationSubmissionResourceIT {

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
    private MPrequalificationSubmissionRepository mPrequalificationSubmissionRepository;

    @Autowired
    private MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper;

    @Autowired
    private MPrequalificationSubmissionService mPrequalificationSubmissionService;

    @Autowired
    private MPrequalificationSubmissionQueryService mPrequalificationSubmissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationSubmissionMockMvc;

    private MPrequalificationSubmission mPrequalificationSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationSubmission createEntity(EntityManager em) {
        MPrequalificationSubmission mPrequalificationSubmission = new MPrequalificationSubmission()
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
        mPrequalificationSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationSubmission.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalificationSubmission.setVendor(cVendor);
        return mPrequalificationSubmission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationSubmission createUpdatedEntity(EntityManager em) {
        MPrequalificationSubmission mPrequalificationSubmission = new MPrequalificationSubmission()
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
        mPrequalificationSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationSubmission.setPrequalification(mPrequalificationInformation);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mPrequalificationSubmission.setVendor(cVendor);
        return mPrequalificationSubmission;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationSubmission() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationSubmissionRepository.findAll().size();

        // Create the MPrequalificationSubmission
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);
        restMPrequalificationSubmissionMockMvc.perform(post("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationSubmission in the database
        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationSubmission testMPrequalificationSubmission = mPrequalificationSubmissionList.get(mPrequalificationSubmissionList.size() - 1);
        assertThat(testMPrequalificationSubmission.isJoined()).isEqualTo(DEFAULT_JOINED);
        assertThat(testMPrequalificationSubmission.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMPrequalificationSubmission.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMPrequalificationSubmission.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMPrequalificationSubmission.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMPrequalificationSubmission.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMPrequalificationSubmission.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMPrequalificationSubmission.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMPrequalificationSubmission.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMPrequalificationSubmission.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMPrequalificationSubmission.getDateSubmit()).isEqualTo(DEFAULT_DATE_SUBMIT);
        assertThat(testMPrequalificationSubmission.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationSubmission.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationSubmissionRepository.findAll().size();

        // Create the MPrequalificationSubmission with an existing ID
        mPrequalificationSubmission.setId(1L);
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationSubmissionMockMvc.perform(post("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationSubmission in the database
        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationSubmissionRepository.findAll().size();
        // set the field null
        mPrequalificationSubmission.setDocumentAction(null);

        // Create the MPrequalificationSubmission, which fails.
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);

        restMPrequalificationSubmissionMockMvc.perform(post("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationSubmissionRepository.findAll().size();
        // set the field null
        mPrequalificationSubmission.setDocumentStatus(null);

        // Create the MPrequalificationSubmission, which fails.
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);

        restMPrequalificationSubmissionMockMvc.perform(post("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissions() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationSubmission.getId().intValue())))
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
    public void getMPrequalificationSubmission() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get the mPrequalificationSubmission
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions/{id}", mPrequalificationSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationSubmission.getId().intValue()))
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
    public void getMPrequalificationSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        Long id = mPrequalificationSubmission.getId();

        defaultMPrequalificationSubmissionShouldBeFound("id.equals=" + id);
        defaultMPrequalificationSubmissionShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationSubmissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationSubmissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationSubmissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationSubmissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByJoinedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where joined equals to DEFAULT_JOINED
        defaultMPrequalificationSubmissionShouldBeFound("joined.equals=" + DEFAULT_JOINED);

        // Get all the mPrequalificationSubmissionList where joined equals to UPDATED_JOINED
        defaultMPrequalificationSubmissionShouldNotBeFound("joined.equals=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByJoinedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where joined not equals to DEFAULT_JOINED
        defaultMPrequalificationSubmissionShouldNotBeFound("joined.notEquals=" + DEFAULT_JOINED);

        // Get all the mPrequalificationSubmissionList where joined not equals to UPDATED_JOINED
        defaultMPrequalificationSubmissionShouldBeFound("joined.notEquals=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByJoinedIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where joined in DEFAULT_JOINED or UPDATED_JOINED
        defaultMPrequalificationSubmissionShouldBeFound("joined.in=" + DEFAULT_JOINED + "," + UPDATED_JOINED);

        // Get all the mPrequalificationSubmissionList where joined equals to UPDATED_JOINED
        defaultMPrequalificationSubmissionShouldNotBeFound("joined.in=" + UPDATED_JOINED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByJoinedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where joined is not null
        defaultMPrequalificationSubmissionShouldBeFound("joined.specified=true");

        // Get all the mPrequalificationSubmissionList where joined is null
        defaultMPrequalificationSubmissionShouldNotBeFound("joined.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx is not null
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.specified=true");

        // Get all the mPrequalificationSubmissionList where dateTrx is null
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx is less than UPDATED_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMPrequalificationSubmissionShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationSubmissionList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMPrequalificationSubmissionShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationSubmissionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationSubmissionList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mPrequalificationSubmissionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo is not null
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.specified=true");

        // Get all the mPrequalificationSubmissionList where documentNo is null
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationSubmissionList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationSubmissionList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMPrequalificationSubmissionShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationSubmissionList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mPrequalificationSubmissionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction is not null
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.specified=true");

        // Get all the mPrequalificationSubmissionList where documentAction is null
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationSubmissionList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationSubmissionList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationSubmissionShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationSubmissionList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mPrequalificationSubmissionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus is not null
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.specified=true");

        // Get all the mPrequalificationSubmissionList where documentStatus is null
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationSubmissionList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationSubmissionList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationSubmissionShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where approved equals to DEFAULT_APPROVED
        defaultMPrequalificationSubmissionShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mPrequalificationSubmissionList where approved equals to UPDATED_APPROVED
        defaultMPrequalificationSubmissionShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where approved not equals to DEFAULT_APPROVED
        defaultMPrequalificationSubmissionShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mPrequalificationSubmissionList where approved not equals to UPDATED_APPROVED
        defaultMPrequalificationSubmissionShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMPrequalificationSubmissionShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mPrequalificationSubmissionList where approved equals to UPDATED_APPROVED
        defaultMPrequalificationSubmissionShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where approved is not null
        defaultMPrequalificationSubmissionShouldBeFound("approved.specified=true");

        // Get all the mPrequalificationSubmissionList where approved is null
        defaultMPrequalificationSubmissionShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where processed equals to DEFAULT_PROCESSED
        defaultMPrequalificationSubmissionShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mPrequalificationSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMPrequalificationSubmissionShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where processed not equals to DEFAULT_PROCESSED
        defaultMPrequalificationSubmissionShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mPrequalificationSubmissionList where processed not equals to UPDATED_PROCESSED
        defaultMPrequalificationSubmissionShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMPrequalificationSubmissionShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mPrequalificationSubmissionList where processed equals to UPDATED_PROCESSED
        defaultMPrequalificationSubmissionShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where processed is not null
        defaultMPrequalificationSubmissionShouldBeFound("processed.specified=true");

        // Get all the mPrequalificationSubmissionList where processed is null
        defaultMPrequalificationSubmissionShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove is not null
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.specified=true");

        // Get all the mPrequalificationSubmissionList where dateApprove is null
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationSubmissionList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMPrequalificationSubmissionShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject is not null
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.specified=true");

        // Get all the mPrequalificationSubmissionList where dateReject is null
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject is less than UPDATED_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationSubmissionList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMPrequalificationSubmissionShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationSubmissionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationSubmissionList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mPrequalificationSubmissionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason is not null
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.specified=true");

        // Get all the mPrequalificationSubmissionList where rejectedReason is null
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationSubmissionList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationSubmissionList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMPrequalificationSubmissionShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit equals to DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.equals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.equals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit not equals to DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.notEquals=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit not equals to UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.notEquals=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit in DEFAULT_DATE_SUBMIT or UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.in=" + DEFAULT_DATE_SUBMIT + "," + UPDATED_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit equals to UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.in=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit is not null
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.specified=true");

        // Get all the mPrequalificationSubmissionList where dateSubmit is null
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit is greater than or equal to DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.greaterThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit is greater than or equal to UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.greaterThanOrEqual=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit is less than or equal to DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.lessThanOrEqual=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit is less than or equal to SMALLER_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.lessThanOrEqual=" + SMALLER_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit is less than DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.lessThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit is less than UPDATED_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.lessThan=" + UPDATED_DATE_SUBMIT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDateSubmitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where dateSubmit is greater than DEFAULT_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldNotBeFound("dateSubmit.greaterThan=" + DEFAULT_DATE_SUBMIT);

        // Get all the mPrequalificationSubmissionList where dateSubmit is greater than SMALLER_DATE_SUBMIT
        defaultMPrequalificationSubmissionShouldBeFound("dateSubmit.greaterThan=" + SMALLER_DATE_SUBMIT);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where uid equals to DEFAULT_UID
        defaultMPrequalificationSubmissionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationSubmissionList where uid equals to UPDATED_UID
        defaultMPrequalificationSubmissionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where uid not equals to DEFAULT_UID
        defaultMPrequalificationSubmissionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationSubmissionList where uid not equals to UPDATED_UID
        defaultMPrequalificationSubmissionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationSubmissionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationSubmissionList where uid equals to UPDATED_UID
        defaultMPrequalificationSubmissionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where uid is not null
        defaultMPrequalificationSubmissionShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationSubmissionList where uid is null
        defaultMPrequalificationSubmissionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationSubmissionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationSubmissionList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationSubmissionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationSubmissionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationSubmissionList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationSubmissionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationSubmissionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationSubmissionList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationSubmissionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        // Get all the mPrequalificationSubmissionList where active is not null
        defaultMPrequalificationSubmissionShouldBeFound("active.specified=true");

        // Get all the mPrequalificationSubmissionList where active is null
        defaultMPrequalificationSubmissionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationSubmission.getAdOrganization();
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationSubmissionList where adOrganization equals to adOrganizationId
        defaultMPrequalificationSubmissionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationSubmissionList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationSubmissionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationSubmission.getPrequalification();
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationSubmissionList where prequalification equals to prequalificationId
        defaultMPrequalificationSubmissionShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationSubmissionList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationSubmissionShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);
        CDocumentType documentType = CDocumentTypeResourceIT.createEntity(em);
        em.persist(documentType);
        em.flush();
        mPrequalificationSubmission.setDocumentType(documentType);
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);
        Long documentTypeId = documentType.getId();

        // Get all the mPrequalificationSubmissionList where documentType equals to documentTypeId
        defaultMPrequalificationSubmissionShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mPrequalificationSubmissionList where documentType equals to documentTypeId + 1
        defaultMPrequalificationSubmissionShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationSubmissionsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mPrequalificationSubmission.getVendor();
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);
        Long vendorId = vendor.getId();

        // Get all the mPrequalificationSubmissionList where vendor equals to vendorId
        defaultMPrequalificationSubmissionShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mPrequalificationSubmissionList where vendor equals to vendorId + 1
        defaultMPrequalificationSubmissionShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationSubmissionShouldBeFound(String filter) throws Exception {
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationSubmission.getId().intValue())))
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
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationSubmissionShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationSubmission() throws Exception {
        // Get the mPrequalificationSubmission
        restMPrequalificationSubmissionMockMvc.perform(get("/api/m-prequalification-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationSubmission() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        int databaseSizeBeforeUpdate = mPrequalificationSubmissionRepository.findAll().size();

        // Update the mPrequalificationSubmission
        MPrequalificationSubmission updatedMPrequalificationSubmission = mPrequalificationSubmissionRepository.findById(mPrequalificationSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationSubmission are not directly saved in db
        em.detach(updatedMPrequalificationSubmission);
        updatedMPrequalificationSubmission
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
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(updatedMPrequalificationSubmission);

        restMPrequalificationSubmissionMockMvc.perform(put("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationSubmission in the database
        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationSubmission testMPrequalificationSubmission = mPrequalificationSubmissionList.get(mPrequalificationSubmissionList.size() - 1);
        assertThat(testMPrequalificationSubmission.isJoined()).isEqualTo(UPDATED_JOINED);
        assertThat(testMPrequalificationSubmission.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMPrequalificationSubmission.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMPrequalificationSubmission.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMPrequalificationSubmission.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMPrequalificationSubmission.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMPrequalificationSubmission.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMPrequalificationSubmission.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMPrequalificationSubmission.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMPrequalificationSubmission.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMPrequalificationSubmission.getDateSubmit()).isEqualTo(UPDATED_DATE_SUBMIT);
        assertThat(testMPrequalificationSubmission.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationSubmission.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationSubmission() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationSubmissionRepository.findAll().size();

        // Create the MPrequalificationSubmission
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationSubmissionMockMvc.perform(put("/api/m-prequalification-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationSubmission in the database
        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationSubmission() throws Exception {
        // Initialize the database
        mPrequalificationSubmissionRepository.saveAndFlush(mPrequalificationSubmission);

        int databaseSizeBeforeDelete = mPrequalificationSubmissionRepository.findAll().size();

        // Delete the mPrequalificationSubmission
        restMPrequalificationSubmissionMockMvc.perform(delete("/api/m-prequalification-submissions/{id}", mPrequalificationSubmission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationSubmission> mPrequalificationSubmissionList = mPrequalificationSubmissionRepository.findAll();
        assertThat(mPrequalificationSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
