package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionInvitation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MAuctionInvitationRepository;
import com.bhp.opusb.service.MAuctionInvitationService;
import com.bhp.opusb.service.dto.MAuctionInvitationDTO;
import com.bhp.opusb.service.mapper.MAuctionInvitationMapper;
import com.bhp.opusb.service.dto.MAuctionInvitationCriteria;
import com.bhp.opusb.service.MAuctionInvitationQueryService;

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
 * Integration tests for the {@link MAuctionInvitationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionInvitationResourceIT {

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

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionInvitationRepository mAuctionInvitationRepository;

    @Autowired
    private MAuctionInvitationMapper mAuctionInvitationMapper;

    @Autowired
    private MAuctionInvitationService mAuctionInvitationService;

    @Autowired
    private MAuctionInvitationQueryService mAuctionInvitationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionInvitationMockMvc;

    private MAuctionInvitation mAuctionInvitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionInvitation createEntity(EntityManager em) {
        MAuctionInvitation mAuctionInvitation = new MAuctionInvitation()
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON)
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
        mAuctionInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionInvitation.setAuction(mAuction);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mAuctionInvitation.setVendor(cVendor);
        return mAuctionInvitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionInvitation createUpdatedEntity(EntityManager em) {
        MAuctionInvitation mAuctionInvitation = new MAuctionInvitation()
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
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
        mAuctionInvitation.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createUpdatedEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionInvitation.setAuction(mAuction);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mAuctionInvitation.setVendor(cVendor);
        return mAuctionInvitation;
    }

    @BeforeEach
    public void initTest() {
        mAuctionInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionInvitation() throws Exception {
        int databaseSizeBeforeCreate = mAuctionInvitationRepository.findAll().size();

        // Create the MAuctionInvitation
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(mAuctionInvitation);
        restMAuctionInvitationMockMvc.perform(post("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionInvitation in the database
        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionInvitation testMAuctionInvitation = mAuctionInvitationList.get(mAuctionInvitationList.size() - 1);
        assertThat(testMAuctionInvitation.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMAuctionInvitation.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMAuctionInvitation.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMAuctionInvitation.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMAuctionInvitation.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMAuctionInvitation.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMAuctionInvitation.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMAuctionInvitation.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMAuctionInvitation.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMAuctionInvitation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionInvitation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionInvitationRepository.findAll().size();

        // Create the MAuctionInvitation with an existing ID
        mAuctionInvitation.setId(1L);
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(mAuctionInvitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionInvitationMockMvc.perform(post("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionInvitation in the database
        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionInvitationRepository.findAll().size();
        // set the field null
        mAuctionInvitation.setDocumentAction(null);

        // Create the MAuctionInvitation, which fails.
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(mAuctionInvitation);

        restMAuctionInvitationMockMvc.perform(post("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionInvitationRepository.findAll().size();
        // set the field null
        mAuctionInvitation.setDocumentStatus(null);

        // Create the MAuctionInvitation, which fails.
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(mAuctionInvitation);

        restMAuctionInvitationMockMvc.perform(post("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitations() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionInvitation() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get the mAuctionInvitation
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations/{id}", mAuctionInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionInvitation.getId().intValue()))
            .andExpect(jsonPath("$.dateTrx").value(sameInstant(DEFAULT_DATE_TRX)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(sameInstant(DEFAULT_DATE_APPROVE)))
            .andExpect(jsonPath("$.dateReject").value(sameInstant(DEFAULT_DATE_REJECT)))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionInvitationsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        Long id = mAuctionInvitation.getId();

        defaultMAuctionInvitationShouldBeFound("id.equals=" + id);
        defaultMAuctionInvitationShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionInvitationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionInvitationShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionInvitationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionInvitationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx is not null
        defaultMAuctionInvitationShouldBeFound("dateTrx.specified=true");

        // Get all the mAuctionInvitationList where dateTrx is null
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx is less than UPDATED_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMAuctionInvitationShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionInvitationList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMAuctionInvitationShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMAuctionInvitationShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionInvitationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMAuctionInvitationShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionInvitationList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mAuctionInvitationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo is not null
        defaultMAuctionInvitationShouldBeFound("documentNo.specified=true");

        // Get all the mAuctionInvitationList where documentNo is null
        defaultMAuctionInvitationShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMAuctionInvitationShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionInvitationList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMAuctionInvitationShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionInvitationList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMAuctionInvitationShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionInvitationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionInvitationList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mAuctionInvitationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction is not null
        defaultMAuctionInvitationShouldBeFound("documentAction.specified=true");

        // Get all the mAuctionInvitationList where documentAction is null
        defaultMAuctionInvitationShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionInvitationList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionInvitationList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMAuctionInvitationShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionInvitationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionInvitationList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mAuctionInvitationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus is not null
        defaultMAuctionInvitationShouldBeFound("documentStatus.specified=true");

        // Get all the mAuctionInvitationList where documentStatus is null
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionInvitationList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionInvitationList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMAuctionInvitationShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where approved equals to DEFAULT_APPROVED
        defaultMAuctionInvitationShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mAuctionInvitationList where approved equals to UPDATED_APPROVED
        defaultMAuctionInvitationShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where approved not equals to DEFAULT_APPROVED
        defaultMAuctionInvitationShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mAuctionInvitationList where approved not equals to UPDATED_APPROVED
        defaultMAuctionInvitationShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMAuctionInvitationShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mAuctionInvitationList where approved equals to UPDATED_APPROVED
        defaultMAuctionInvitationShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where approved is not null
        defaultMAuctionInvitationShouldBeFound("approved.specified=true");

        // Get all the mAuctionInvitationList where approved is null
        defaultMAuctionInvitationShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where processed equals to DEFAULT_PROCESSED
        defaultMAuctionInvitationShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mAuctionInvitationList where processed equals to UPDATED_PROCESSED
        defaultMAuctionInvitationShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where processed not equals to DEFAULT_PROCESSED
        defaultMAuctionInvitationShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mAuctionInvitationList where processed not equals to UPDATED_PROCESSED
        defaultMAuctionInvitationShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMAuctionInvitationShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mAuctionInvitationList where processed equals to UPDATED_PROCESSED
        defaultMAuctionInvitationShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where processed is not null
        defaultMAuctionInvitationShouldBeFound("processed.specified=true");

        // Get all the mAuctionInvitationList where processed is null
        defaultMAuctionInvitationShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove is not null
        defaultMAuctionInvitationShouldBeFound("dateApprove.specified=true");

        // Get all the mAuctionInvitationList where dateApprove is null
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMAuctionInvitationShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionInvitationList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMAuctionInvitationShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject is not null
        defaultMAuctionInvitationShouldBeFound("dateReject.specified=true");

        // Get all the mAuctionInvitationList where dateReject is null
        defaultMAuctionInvitationShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject is less than UPDATED_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMAuctionInvitationShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionInvitationList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMAuctionInvitationShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMAuctionInvitationShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionInvitationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionInvitationList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mAuctionInvitationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason is not null
        defaultMAuctionInvitationShouldBeFound("rejectedReason.specified=true");

        // Get all the mAuctionInvitationList where rejectedReason is null
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMAuctionInvitationShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionInvitationList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMAuctionInvitationShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionInvitationList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMAuctionInvitationShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where uid equals to DEFAULT_UID
        defaultMAuctionInvitationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionInvitationList where uid equals to UPDATED_UID
        defaultMAuctionInvitationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where uid not equals to DEFAULT_UID
        defaultMAuctionInvitationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionInvitationList where uid not equals to UPDATED_UID
        defaultMAuctionInvitationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionInvitationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionInvitationList where uid equals to UPDATED_UID
        defaultMAuctionInvitationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where uid is not null
        defaultMAuctionInvitationShouldBeFound("uid.specified=true");

        // Get all the mAuctionInvitationList where uid is null
        defaultMAuctionInvitationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where active equals to DEFAULT_ACTIVE
        defaultMAuctionInvitationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionInvitationList where active equals to UPDATED_ACTIVE
        defaultMAuctionInvitationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionInvitationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionInvitationList where active not equals to UPDATED_ACTIVE
        defaultMAuctionInvitationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionInvitationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionInvitationList where active equals to UPDATED_ACTIVE
        defaultMAuctionInvitationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        // Get all the mAuctionInvitationList where active is not null
        defaultMAuctionInvitationShouldBeFound("active.specified=true");

        // Get all the mAuctionInvitationList where active is null
        defaultMAuctionInvitationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionInvitationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionInvitation.getAdOrganization();
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionInvitationList where adOrganization equals to adOrganizationId
        defaultMAuctionInvitationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionInvitationList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionInvitationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByAuctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuction auction = mAuctionInvitation.getAuction();
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);
        Long auctionId = auction.getId();

        // Get all the mAuctionInvitationList where auction equals to auctionId
        defaultMAuctionInvitationShouldBeFound("auctionId.equals=" + auctionId);

        // Get all the mAuctionInvitationList where auction equals to auctionId + 1
        defaultMAuctionInvitationShouldNotBeFound("auctionId.equals=" + (auctionId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionInvitationsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mAuctionInvitation.getVendor();
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);
        Long vendorId = vendor.getId();

        // Get all the mAuctionInvitationList where vendor equals to vendorId
        defaultMAuctionInvitationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mAuctionInvitationList where vendor equals to vendorId + 1
        defaultMAuctionInvitationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionInvitationShouldBeFound(String filter) throws Exception {
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionInvitationShouldNotBeFound(String filter) throws Exception {
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionInvitation() throws Exception {
        // Get the mAuctionInvitation
        restMAuctionInvitationMockMvc.perform(get("/api/m-auction-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionInvitation() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        int databaseSizeBeforeUpdate = mAuctionInvitationRepository.findAll().size();

        // Update the mAuctionInvitation
        MAuctionInvitation updatedMAuctionInvitation = mAuctionInvitationRepository.findById(mAuctionInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionInvitation are not directly saved in db
        em.detach(updatedMAuctionInvitation);
        updatedMAuctionInvitation
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(updatedMAuctionInvitation);

        restMAuctionInvitationMockMvc.perform(put("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionInvitation in the database
        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeUpdate);
        MAuctionInvitation testMAuctionInvitation = mAuctionInvitationList.get(mAuctionInvitationList.size() - 1);
        assertThat(testMAuctionInvitation.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMAuctionInvitation.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMAuctionInvitation.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMAuctionInvitation.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMAuctionInvitation.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMAuctionInvitation.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMAuctionInvitation.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMAuctionInvitation.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMAuctionInvitation.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMAuctionInvitation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionInvitation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionInvitation() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionInvitationRepository.findAll().size();

        // Create the MAuctionInvitation
        MAuctionInvitationDTO mAuctionInvitationDTO = mAuctionInvitationMapper.toDto(mAuctionInvitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionInvitationMockMvc.perform(put("/api/m-auction-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionInvitation in the database
        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionInvitation() throws Exception {
        // Initialize the database
        mAuctionInvitationRepository.saveAndFlush(mAuctionInvitation);

        int databaseSizeBeforeDelete = mAuctionInvitationRepository.findAll().size();

        // Delete the mAuctionInvitation
        restMAuctionInvitationMockMvc.perform(delete("/api/m-auction-invitations/{id}", mAuctionInvitation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionInvitation> mAuctionInvitationList = mAuctionInvitationRepository.findAll();
        assertThat(mAuctionInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
