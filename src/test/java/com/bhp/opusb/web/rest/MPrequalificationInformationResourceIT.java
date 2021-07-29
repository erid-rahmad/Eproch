package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.repository.MPrequalificationInformationRepository;
import com.bhp.opusb.service.MPrequalificationInformationService;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.mapper.MPrequalificationInformationMapper;
import com.bhp.opusb.service.dto.MPrequalificationInformationCriteria;
import com.bhp.opusb.service.MPrequalificationInformationQueryService;

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
 * Integration tests for the {@link MPrequalificationInformationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationInformationResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNOUNCEMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCEMENT_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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

    @Autowired
    private MPrequalificationInformationRepository mPrequalificationInformationRepository;

    @Autowired
    private MPrequalificationInformationMapper mPrequalificationInformationMapper;

    @Autowired
    private MPrequalificationInformationService mPrequalificationInformationService;

    @Autowired
    private MPrequalificationInformationQueryService mPrequalificationInformationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationInformationMockMvc;

    private MPrequalificationInformation mPrequalificationInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationInformation createEntity(EntityManager em) {
        MPrequalificationInformation mPrequalificationInformation = new MPrequalificationInformation()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .announcementText(DEFAULT_ANNOUNCEMENT_TEXT)
            .status(DEFAULT_STATUS)
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .dateApprove(DEFAULT_DATE_APPROVE)
            .dateReject(DEFAULT_DATE_REJECT)
            .rejectedReason(DEFAULT_REJECTED_REASON);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPrequalificationInformation.setAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationInformation.setAdOrganization(aDOrganization);
        return mPrequalificationInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationInformation createUpdatedEntity(EntityManager em) {
        MPrequalificationInformation mPrequalificationInformation = new MPrequalificationInformation()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .announcementText(UPDATED_ANNOUNCEMENT_TEXT)
            .status(UPDATED_STATUS)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPrequalificationInformation.setAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationInformation.setAdOrganization(aDOrganization);
        return mPrequalificationInformation;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationInformation() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationInformationRepository.findAll().size();

        // Create the MPrequalificationInformation
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);
        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationInformation in the database
        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationInformation testMPrequalificationInformation = mPrequalificationInformationList.get(mPrequalificationInformationList.size() - 1);
        assertThat(testMPrequalificationInformation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationInformation.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMPrequalificationInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMPrequalificationInformation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMPrequalificationInformation.getAnnouncementText()).isEqualTo(DEFAULT_ANNOUNCEMENT_TEXT);
        assertThat(testMPrequalificationInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPrequalificationInformation.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMPrequalificationInformation.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMPrequalificationInformation.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMPrequalificationInformation.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMPrequalificationInformation.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMPrequalificationInformation.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMPrequalificationInformation.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMPrequalificationInformation.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMPrequalificationInformation.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void createMPrequalificationInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationInformationRepository.findAll().size();

        // Create the MPrequalificationInformation with an existing ID
        mPrequalificationInformation.setId(1L);
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationInformation in the database
        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationInformationRepository.findAll().size();
        // set the field null
        mPrequalificationInformation.setType(null);

        // Create the MPrequalificationInformation, which fails.
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationInformationRepository.findAll().size();
        // set the field null
        mPrequalificationInformation.setStatus(null);

        // Create the MPrequalificationInformation, which fails.
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationInformationRepository.findAll().size();
        // set the field null
        mPrequalificationInformation.setDocumentAction(null);

        // Create the MPrequalificationInformation, which fails.
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationInformationRepository.findAll().size();
        // set the field null
        mPrequalificationInformation.setDocumentStatus(null);

        // Create the MPrequalificationInformation, which fails.
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        restMPrequalificationInformationMockMvc.perform(post("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformations() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].announcementText").value(hasItem(DEFAULT_ANNOUNCEMENT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationInformation() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get the mPrequalificationInformation
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations/{id}", mPrequalificationInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationInformation.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.announcementText").value(DEFAULT_ANNOUNCEMENT_TEXT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dateTrx").value(sameInstant(DEFAULT_DATE_TRX)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dateApprove").value(sameInstant(DEFAULT_DATE_APPROVE)))
            .andExpect(jsonPath("$.dateReject").value(sameInstant(DEFAULT_DATE_REJECT)))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON));
    }


    @Test
    @Transactional
    public void getMPrequalificationInformationsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        Long id = mPrequalificationInformation.getId();

        defaultMPrequalificationInformationShouldBeFound("id.equals=" + id);
        defaultMPrequalificationInformationShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationInformationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationInformationShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationInformationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationInformationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where uid equals to DEFAULT_UID
        defaultMPrequalificationInformationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationInformationList where uid equals to UPDATED_UID
        defaultMPrequalificationInformationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where uid not equals to DEFAULT_UID
        defaultMPrequalificationInformationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationInformationList where uid not equals to UPDATED_UID
        defaultMPrequalificationInformationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationInformationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationInformationList where uid equals to UPDATED_UID
        defaultMPrequalificationInformationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where uid is not null
        defaultMPrequalificationInformationShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationInformationList where uid is null
        defaultMPrequalificationInformationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationInformationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationInformationList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationInformationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationInformationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationInformationList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationInformationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationInformationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationInformationList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationInformationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where active is not null
        defaultMPrequalificationInformationShouldBeFound("active.specified=true");

        // Get all the mPrequalificationInformationList where active is null
        defaultMPrequalificationInformationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name equals to DEFAULT_NAME
        defaultMPrequalificationInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mPrequalificationInformationList where name equals to UPDATED_NAME
        defaultMPrequalificationInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name not equals to DEFAULT_NAME
        defaultMPrequalificationInformationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mPrequalificationInformationList where name not equals to UPDATED_NAME
        defaultMPrequalificationInformationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMPrequalificationInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mPrequalificationInformationList where name equals to UPDATED_NAME
        defaultMPrequalificationInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name is not null
        defaultMPrequalificationInformationShouldBeFound("name.specified=true");

        // Get all the mPrequalificationInformationList where name is null
        defaultMPrequalificationInformationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name contains DEFAULT_NAME
        defaultMPrequalificationInformationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mPrequalificationInformationList where name contains UPDATED_NAME
        defaultMPrequalificationInformationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where name does not contain DEFAULT_NAME
        defaultMPrequalificationInformationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mPrequalificationInformationList where name does not contain UPDATED_NAME
        defaultMPrequalificationInformationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type equals to DEFAULT_TYPE
        defaultMPrequalificationInformationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the mPrequalificationInformationList where type equals to UPDATED_TYPE
        defaultMPrequalificationInformationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type not equals to DEFAULT_TYPE
        defaultMPrequalificationInformationShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the mPrequalificationInformationList where type not equals to UPDATED_TYPE
        defaultMPrequalificationInformationShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMPrequalificationInformationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the mPrequalificationInformationList where type equals to UPDATED_TYPE
        defaultMPrequalificationInformationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type is not null
        defaultMPrequalificationInformationShouldBeFound("type.specified=true");

        // Get all the mPrequalificationInformationList where type is null
        defaultMPrequalificationInformationShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type contains DEFAULT_TYPE
        defaultMPrequalificationInformationShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the mPrequalificationInformationList where type contains UPDATED_TYPE
        defaultMPrequalificationInformationShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where type does not contain DEFAULT_TYPE
        defaultMPrequalificationInformationShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the mPrequalificationInformationList where type does not contain UPDATED_TYPE
        defaultMPrequalificationInformationShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status equals to DEFAULT_STATUS
        defaultMPrequalificationInformationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mPrequalificationInformationList where status equals to UPDATED_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status not equals to DEFAULT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mPrequalificationInformationList where status not equals to UPDATED_STATUS
        defaultMPrequalificationInformationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMPrequalificationInformationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mPrequalificationInformationList where status equals to UPDATED_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status is not null
        defaultMPrequalificationInformationShouldBeFound("status.specified=true");

        // Get all the mPrequalificationInformationList where status is null
        defaultMPrequalificationInformationShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status contains DEFAULT_STATUS
        defaultMPrequalificationInformationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mPrequalificationInformationList where status contains UPDATED_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where status does not contain DEFAULT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mPrequalificationInformationList where status does not contain UPDATED_STATUS
        defaultMPrequalificationInformationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx equals to UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx is not null
        defaultMPrequalificationInformationShouldBeFound("dateTrx.specified=true");

        // Get all the mPrequalificationInformationList where dateTrx is null
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx is less than UPDATED_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMPrequalificationInformationShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mPrequalificationInformationList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMPrequalificationInformationShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalificationInformationShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationInformationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationInformationList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mPrequalificationInformationList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo is not null
        defaultMPrequalificationInformationShouldBeFound("documentNo.specified=true");

        // Get all the mPrequalificationInformationList where documentNo is null
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMPrequalificationInformationShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationInformationList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMPrequalificationInformationShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalificationInformationList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMPrequalificationInformationShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationInformationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationInformationList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mPrequalificationInformationList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction is not null
        defaultMPrequalificationInformationShouldBeFound("documentAction.specified=true");

        // Get all the mPrequalificationInformationList where documentAction is null
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationInformationList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mPrequalificationInformationList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMPrequalificationInformationShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationInformationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationInformationList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mPrequalificationInformationList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus is not null
        defaultMPrequalificationInformationShouldBeFound("documentStatus.specified=true");

        // Get all the mPrequalificationInformationList where documentStatus is null
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationInformationList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mPrequalificationInformationList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMPrequalificationInformationShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where approved equals to DEFAULT_APPROVED
        defaultMPrequalificationInformationShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mPrequalificationInformationList where approved equals to UPDATED_APPROVED
        defaultMPrequalificationInformationShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where approved not equals to DEFAULT_APPROVED
        defaultMPrequalificationInformationShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mPrequalificationInformationList where approved not equals to UPDATED_APPROVED
        defaultMPrequalificationInformationShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMPrequalificationInformationShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mPrequalificationInformationList where approved equals to UPDATED_APPROVED
        defaultMPrequalificationInformationShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where approved is not null
        defaultMPrequalificationInformationShouldBeFound("approved.specified=true");

        // Get all the mPrequalificationInformationList where approved is null
        defaultMPrequalificationInformationShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where processed equals to DEFAULT_PROCESSED
        defaultMPrequalificationInformationShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mPrequalificationInformationList where processed equals to UPDATED_PROCESSED
        defaultMPrequalificationInformationShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where processed not equals to DEFAULT_PROCESSED
        defaultMPrequalificationInformationShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mPrequalificationInformationList where processed not equals to UPDATED_PROCESSED
        defaultMPrequalificationInformationShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMPrequalificationInformationShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mPrequalificationInformationList where processed equals to UPDATED_PROCESSED
        defaultMPrequalificationInformationShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where processed is not null
        defaultMPrequalificationInformationShouldBeFound("processed.specified=true");

        // Get all the mPrequalificationInformationList where processed is null
        defaultMPrequalificationInformationShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove is not null
        defaultMPrequalificationInformationShouldBeFound("dateApprove.specified=true");

        // Get all the mPrequalificationInformationList where dateApprove is null
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMPrequalificationInformationShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mPrequalificationInformationList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMPrequalificationInformationShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject equals to UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject is not null
        defaultMPrequalificationInformationShouldBeFound("dateReject.specified=true");

        // Get all the mPrequalificationInformationList where dateReject is null
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject is less than UPDATED_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMPrequalificationInformationShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mPrequalificationInformationList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMPrequalificationInformationShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationInformationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationInformationList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mPrequalificationInformationList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason is not null
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.specified=true");

        // Get all the mPrequalificationInformationList where rejectedReason is null
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationInformationList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        // Get all the mPrequalificationInformationList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMPrequalificationInformationShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mPrequalificationInformationList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMPrequalificationInformationShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mPrequalificationInformation.getAttachment();
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);
        Long attachmentId = attachment.getId();

        // Get all the mPrequalificationInformationList where attachment equals to attachmentId
        defaultMPrequalificationInformationShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mPrequalificationInformationList where attachment equals to attachmentId + 1
        defaultMPrequalificationInformationShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationInformation.getAdOrganization();
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationInformationList where adOrganization equals to adOrganizationId
        defaultMPrequalificationInformationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationInformationList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationInformationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationInformationsByQuotationIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);
        MRfq quotation = MRfqResourceIT.createEntity(em);
        em.persist(quotation);
        em.flush();
        mPrequalificationInformation.setQuotation(quotation);
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);
        Long quotationId = quotation.getId();

        // Get all the mPrequalificationInformationList where quotation equals to quotationId
        defaultMPrequalificationInformationShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the mPrequalificationInformationList where quotation equals to quotationId + 1
        defaultMPrequalificationInformationShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationInformationShouldBeFound(String filter) throws Exception {
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].announcementText").value(hasItem(DEFAULT_ANNOUNCEMENT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(sameInstant(DEFAULT_DATE_TRX))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateApprove").value(hasItem(sameInstant(DEFAULT_DATE_APPROVE))))
            .andExpect(jsonPath("$.[*].dateReject").value(hasItem(sameInstant(DEFAULT_DATE_REJECT))))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)));

        // Check, that the count call also returns 1
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationInformationShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationInformation() throws Exception {
        // Get the mPrequalificationInformation
        restMPrequalificationInformationMockMvc.perform(get("/api/m-prequalification-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationInformation() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        int databaseSizeBeforeUpdate = mPrequalificationInformationRepository.findAll().size();

        // Update the mPrequalificationInformation
        MPrequalificationInformation updatedMPrequalificationInformation = mPrequalificationInformationRepository.findById(mPrequalificationInformation.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationInformation are not directly saved in db
        em.detach(updatedMPrequalificationInformation);
        updatedMPrequalificationInformation
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .announcementText(UPDATED_ANNOUNCEMENT_TEXT)
            .status(UPDATED_STATUS)
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .dateApprove(UPDATED_DATE_APPROVE)
            .dateReject(UPDATED_DATE_REJECT)
            .rejectedReason(UPDATED_REJECTED_REASON);
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(updatedMPrequalificationInformation);

        restMPrequalificationInformationMockMvc.perform(put("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationInformation in the database
        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationInformation testMPrequalificationInformation = mPrequalificationInformationList.get(mPrequalificationInformationList.size() - 1);
        assertThat(testMPrequalificationInformation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationInformation.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMPrequalificationInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMPrequalificationInformation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMPrequalificationInformation.getAnnouncementText()).isEqualTo(UPDATED_ANNOUNCEMENT_TEXT);
        assertThat(testMPrequalificationInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPrequalificationInformation.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMPrequalificationInformation.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMPrequalificationInformation.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMPrequalificationInformation.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMPrequalificationInformation.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMPrequalificationInformation.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMPrequalificationInformation.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMPrequalificationInformation.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMPrequalificationInformation.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationInformation() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationInformationRepository.findAll().size();

        // Create the MPrequalificationInformation
        MPrequalificationInformationDTO mPrequalificationInformationDTO = mPrequalificationInformationMapper.toDto(mPrequalificationInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationInformationMockMvc.perform(put("/api/m-prequalification-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationInformation in the database
        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationInformation() throws Exception {
        // Initialize the database
        mPrequalificationInformationRepository.saveAndFlush(mPrequalificationInformation);

        int databaseSizeBeforeDelete = mPrequalificationInformationRepository.findAll().size();

        // Delete the mPrequalificationInformation
        restMPrequalificationInformationMockMvc.perform(delete("/api/m-prequalification-informations/{id}", mPrequalificationInformation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationInformation> mPrequalificationInformationList = mPrequalificationInformationRepository.findAll();
        assertThat(mPrequalificationInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
