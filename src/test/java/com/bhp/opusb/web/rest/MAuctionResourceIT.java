package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.domain.MAuctionContent;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CAuctionPrerequisite;
import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.service.MAuctionService;
import com.bhp.opusb.service.dto.MAuctionDTO;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import com.bhp.opusb.service.dto.MAuctionCriteria;
import com.bhp.opusb.service.MAuctionQueryService;

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
 * Integration tests for the {@link MAuctionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
    private MAuctionRepository mAuctionRepository;

    @Autowired
    private MAuctionMapper mAuctionMapper;

    @Autowired
    private MAuctionService mAuctionService;

    @Autowired
    private MAuctionQueryService mAuctionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionMockMvc;

    private MAuction mAuction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuction createEntity(EntityManager em) {
        MAuction mAuction = new MAuction()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
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
        mAuction.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mAuction.setCurrency(cCurrency);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mAuction.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mAuction.setDocumentType(cDocumentType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuction.setOwner(adUser);
        return mAuction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuction createUpdatedEntity(EntityManager em) {
        MAuction mAuction = new MAuction()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
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
        mAuction.setAdOrganization(aDOrganization);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mAuction.setCurrency(cCurrency);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mAuction.setCostCenter(cCostCenter);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mAuction.setDocumentType(cDocumentType);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mAuction.setOwner(adUser);
        return mAuction;
    }

    @BeforeEach
    public void initTest() {
        mAuction = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuction() throws Exception {
        int databaseSizeBeforeCreate = mAuctionRepository.findAll().size();

        // Create the MAuction
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);
        restMAuctionMockMvc.perform(post("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuction in the database
        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeCreate + 1);
        MAuction testMAuction = mAuctionList.get(mAuctionList.size() - 1);
        assertThat(testMAuction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMAuction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMAuction.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMAuction.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMAuction.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMAuction.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMAuction.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMAuction.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMAuction.getDateApprove()).isEqualTo(DEFAULT_DATE_APPROVE);
        assertThat(testMAuction.getDateReject()).isEqualTo(DEFAULT_DATE_REJECT);
        assertThat(testMAuction.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMAuction.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuction.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionRepository.findAll().size();

        // Create the MAuction with an existing ID
        mAuction.setId(1L);
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionMockMvc.perform(post("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuction in the database
        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRepository.findAll().size();
        // set the field null
        mAuction.setName(null);

        // Create the MAuction, which fails.
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);

        restMAuctionMockMvc.perform(post("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isBadRequest());

        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRepository.findAll().size();
        // set the field null
        mAuction.setDocumentAction(null);

        // Create the MAuction, which fails.
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);

        restMAuctionMockMvc.perform(post("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isBadRequest());

        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRepository.findAll().size();
        // set the field null
        mAuction.setDocumentStatus(null);

        // Create the MAuction, which fails.
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);

        restMAuctionMockMvc.perform(post("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isBadRequest());

        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctions() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList
        restMAuctionMockMvc.perform(get("/api/m-auctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
    public void getMAuction() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get the mAuction
        restMAuctionMockMvc.perform(get("/api/m-auctions/{id}", mAuction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
    public void getMAuctionsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        Long id = mAuction.getId();

        defaultMAuctionShouldBeFound("id.equals=" + id);
        defaultMAuctionShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name equals to DEFAULT_NAME
        defaultMAuctionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mAuctionList where name equals to UPDATED_NAME
        defaultMAuctionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name not equals to DEFAULT_NAME
        defaultMAuctionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mAuctionList where name not equals to UPDATED_NAME
        defaultMAuctionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMAuctionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mAuctionList where name equals to UPDATED_NAME
        defaultMAuctionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name is not null
        defaultMAuctionShouldBeFound("name.specified=true");

        // Get all the mAuctionList where name is null
        defaultMAuctionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByNameContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name contains DEFAULT_NAME
        defaultMAuctionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mAuctionList where name contains UPDATED_NAME
        defaultMAuctionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where name does not contain DEFAULT_NAME
        defaultMAuctionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mAuctionList where name does not contain UPDATED_NAME
        defaultMAuctionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description equals to DEFAULT_DESCRIPTION
        defaultMAuctionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mAuctionList where description equals to UPDATED_DESCRIPTION
        defaultMAuctionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description not equals to DEFAULT_DESCRIPTION
        defaultMAuctionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mAuctionList where description not equals to UPDATED_DESCRIPTION
        defaultMAuctionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMAuctionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mAuctionList where description equals to UPDATED_DESCRIPTION
        defaultMAuctionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description is not null
        defaultMAuctionShouldBeFound("description.specified=true");

        // Get all the mAuctionList where description is null
        defaultMAuctionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description contains DEFAULT_DESCRIPTION
        defaultMAuctionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mAuctionList where description contains UPDATED_DESCRIPTION
        defaultMAuctionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where description does not contain DEFAULT_DESCRIPTION
        defaultMAuctionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mAuctionList where description does not contain UPDATED_DESCRIPTION
        defaultMAuctionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mAuctionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx is not null
        defaultMAuctionShouldBeFound("dateTrx.specified=true");

        // Get all the mAuctionList where dateTrx is null
        defaultMAuctionShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx is less than UPDATED_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMAuctionShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMAuctionShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMAuctionShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMAuctionShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMAuctionShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMAuctionShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMAuctionShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mAuctionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMAuctionShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo is not null
        defaultMAuctionShouldBeFound("documentNo.specified=true");

        // Get all the mAuctionList where documentNo is null
        defaultMAuctionShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMAuctionShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMAuctionShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMAuctionShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mAuctionList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMAuctionShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMAuctionShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMAuctionShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mAuctionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction is not null
        defaultMAuctionShouldBeFound("documentAction.specified=true");

        // Get all the mAuctionList where documentAction is null
        defaultMAuctionShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMAuctionShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMAuctionShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mAuctionList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMAuctionShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMAuctionShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMAuctionShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mAuctionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus is not null
        defaultMAuctionShouldBeFound("documentStatus.specified=true");

        // Get all the mAuctionList where documentStatus is null
        defaultMAuctionShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMAuctionShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMAuctionShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mAuctionList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMAuctionShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where approved equals to DEFAULT_APPROVED
        defaultMAuctionShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mAuctionList where approved equals to UPDATED_APPROVED
        defaultMAuctionShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where approved not equals to DEFAULT_APPROVED
        defaultMAuctionShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mAuctionList where approved not equals to UPDATED_APPROVED
        defaultMAuctionShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMAuctionShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mAuctionList where approved equals to UPDATED_APPROVED
        defaultMAuctionShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where approved is not null
        defaultMAuctionShouldBeFound("approved.specified=true");

        // Get all the mAuctionList where approved is null
        defaultMAuctionShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where processed equals to DEFAULT_PROCESSED
        defaultMAuctionShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mAuctionList where processed equals to UPDATED_PROCESSED
        defaultMAuctionShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where processed not equals to DEFAULT_PROCESSED
        defaultMAuctionShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mAuctionList where processed not equals to UPDATED_PROCESSED
        defaultMAuctionShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMAuctionShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mAuctionList where processed equals to UPDATED_PROCESSED
        defaultMAuctionShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where processed is not null
        defaultMAuctionShouldBeFound("processed.specified=true");

        // Get all the mAuctionList where processed is null
        defaultMAuctionShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove equals to DEFAULT_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.equals=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.equals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove not equals to DEFAULT_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.notEquals=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove not equals to UPDATED_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.notEquals=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove in DEFAULT_DATE_APPROVE or UPDATED_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.in=" + DEFAULT_DATE_APPROVE + "," + UPDATED_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove equals to UPDATED_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.in=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove is not null
        defaultMAuctionShouldBeFound("dateApprove.specified=true");

        // Get all the mAuctionList where dateApprove is null
        defaultMAuctionShouldNotBeFound("dateApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove is greater than or equal to DEFAULT_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.greaterThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove is greater than or equal to UPDATED_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.greaterThanOrEqual=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove is less than or equal to DEFAULT_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.lessThanOrEqual=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove is less than or equal to SMALLER_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.lessThanOrEqual=" + SMALLER_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove is less than DEFAULT_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.lessThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove is less than UPDATED_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.lessThan=" + UPDATED_DATE_APPROVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateApproveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateApprove is greater than DEFAULT_DATE_APPROVE
        defaultMAuctionShouldNotBeFound("dateApprove.greaterThan=" + DEFAULT_DATE_APPROVE);

        // Get all the mAuctionList where dateApprove is greater than SMALLER_DATE_APPROVE
        defaultMAuctionShouldBeFound("dateApprove.greaterThan=" + SMALLER_DATE_APPROVE);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject equals to DEFAULT_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.equals=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.equals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject not equals to DEFAULT_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.notEquals=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject not equals to UPDATED_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.notEquals=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject in DEFAULT_DATE_REJECT or UPDATED_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.in=" + DEFAULT_DATE_REJECT + "," + UPDATED_DATE_REJECT);

        // Get all the mAuctionList where dateReject equals to UPDATED_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.in=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject is not null
        defaultMAuctionShouldBeFound("dateReject.specified=true");

        // Get all the mAuctionList where dateReject is null
        defaultMAuctionShouldNotBeFound("dateReject.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject is greater than or equal to DEFAULT_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.greaterThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject is greater than or equal to UPDATED_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.greaterThanOrEqual=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject is less than or equal to DEFAULT_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.lessThanOrEqual=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject is less than or equal to SMALLER_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.lessThanOrEqual=" + SMALLER_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject is less than DEFAULT_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.lessThan=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject is less than UPDATED_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.lessThan=" + UPDATED_DATE_REJECT);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByDateRejectIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where dateReject is greater than DEFAULT_DATE_REJECT
        defaultMAuctionShouldNotBeFound("dateReject.greaterThan=" + DEFAULT_DATE_REJECT);

        // Get all the mAuctionList where dateReject is greater than SMALLER_DATE_REJECT
        defaultMAuctionShouldBeFound("dateReject.greaterThan=" + SMALLER_DATE_REJECT);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMAuctionShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMAuctionShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMAuctionShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMAuctionShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMAuctionShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mAuctionList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMAuctionShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason is not null
        defaultMAuctionShouldBeFound("rejectedReason.specified=true");

        // Get all the mAuctionList where rejectedReason is null
        defaultMAuctionShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMAuctionShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMAuctionShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMAuctionShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mAuctionList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMAuctionShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMAuctionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where uid equals to DEFAULT_UID
        defaultMAuctionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionList where uid equals to UPDATED_UID
        defaultMAuctionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where uid not equals to DEFAULT_UID
        defaultMAuctionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionList where uid not equals to UPDATED_UID
        defaultMAuctionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionList where uid equals to UPDATED_UID
        defaultMAuctionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where uid is not null
        defaultMAuctionShouldBeFound("uid.specified=true");

        // Get all the mAuctionList where uid is null
        defaultMAuctionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where active equals to DEFAULT_ACTIVE
        defaultMAuctionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionList where active equals to UPDATED_ACTIVE
        defaultMAuctionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionList where active not equals to UPDATED_ACTIVE
        defaultMAuctionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionList where active equals to UPDATED_ACTIVE
        defaultMAuctionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        // Get all the mAuctionList where active is not null
        defaultMAuctionShouldBeFound("active.specified=true");

        // Get all the mAuctionList where active is null
        defaultMAuctionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionsByRuleIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);
        MAuctionRule rule = MAuctionRuleResourceIT.createEntity(em);
        em.persist(rule);
        em.flush();
        mAuction.setRule(rule);
        mAuctionRepository.saveAndFlush(mAuction);
        Long ruleId = rule.getId();

        // Get all the mAuctionList where rule equals to ruleId
        defaultMAuctionShouldBeFound("ruleId.equals=" + ruleId);

        // Get all the mAuctionList where rule equals to ruleId + 1
        defaultMAuctionShouldNotBeFound("ruleId.equals=" + (ruleId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);
        MAuctionContent content = MAuctionContentResourceIT.createEntity(em);
        em.persist(content);
        em.flush();
        mAuction.setContent(content);
        mAuctionRepository.saveAndFlush(mAuction);
        Long contentId = content.getId();

        // Get all the mAuctionList where content equals to contentId
        defaultMAuctionShouldBeFound("contentId.equals=" + contentId);

        // Get all the mAuctionList where content equals to contentId + 1
        defaultMAuctionShouldNotBeFound("contentId.equals=" + (contentId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuction.getAdOrganization();
        mAuctionRepository.saveAndFlush(mAuction);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionList where adOrganization equals to adOrganizationId
        defaultMAuctionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mAuction.getCurrency();
        mAuctionRepository.saveAndFlush(mAuction);
        Long currencyId = currency.getId();

        // Get all the mAuctionList where currency equals to currencyId
        defaultMAuctionShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mAuctionList where currency equals to currencyId + 1
        defaultMAuctionShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mAuction.getCostCenter();
        mAuctionRepository.saveAndFlush(mAuction);
        Long costCenterId = costCenter.getId();

        // Get all the mAuctionList where costCenter equals to costCenterId
        defaultMAuctionShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mAuctionList where costCenter equals to costCenterId + 1
        defaultMAuctionShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = mAuction.getDocumentType();
        mAuctionRepository.saveAndFlush(mAuction);
        Long documentTypeId = documentType.getId();

        // Get all the mAuctionList where documentType equals to documentTypeId
        defaultMAuctionShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mAuctionList where documentType equals to documentTypeId + 1
        defaultMAuctionShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByOwnerIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser owner = mAuction.getOwner();
        mAuctionRepository.saveAndFlush(mAuction);
        Long ownerId = owner.getId();

        // Get all the mAuctionList where owner equals to ownerId
        defaultMAuctionShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the mAuctionList where owner equals to ownerId + 1
        defaultMAuctionShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionsByPrerequisiteIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);
        CAuctionPrerequisite prerequisite = CAuctionPrerequisiteResourceIT.createEntity(em);
        em.persist(prerequisite);
        em.flush();
        mAuction.setPrerequisite(prerequisite);
        mAuctionRepository.saveAndFlush(mAuction);
        Long prerequisiteId = prerequisite.getId();

        // Get all the mAuctionList where prerequisite equals to prerequisiteId
        defaultMAuctionShouldBeFound("prerequisiteId.equals=" + prerequisiteId);

        // Get all the mAuctionList where prerequisite equals to prerequisiteId + 1
        defaultMAuctionShouldNotBeFound("prerequisiteId.equals=" + (prerequisiteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionShouldBeFound(String filter) throws Exception {
        restMAuctionMockMvc.perform(get("/api/m-auctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
        restMAuctionMockMvc.perform(get("/api/m-auctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionShouldNotBeFound(String filter) throws Exception {
        restMAuctionMockMvc.perform(get("/api/m-auctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionMockMvc.perform(get("/api/m-auctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuction() throws Exception {
        // Get the mAuction
        restMAuctionMockMvc.perform(get("/api/m-auctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuction() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        int databaseSizeBeforeUpdate = mAuctionRepository.findAll().size();

        // Update the mAuction
        MAuction updatedMAuction = mAuctionRepository.findById(mAuction.getId()).get();
        // Disconnect from session so that the updates on updatedMAuction are not directly saved in db
        em.detach(updatedMAuction);
        updatedMAuction
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
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
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(updatedMAuction);

        restMAuctionMockMvc.perform(put("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isOk());

        // Validate the MAuction in the database
        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeUpdate);
        MAuction testMAuction = mAuctionList.get(mAuctionList.size() - 1);
        assertThat(testMAuction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMAuction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMAuction.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMAuction.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMAuction.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMAuction.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMAuction.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMAuction.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMAuction.getDateApprove()).isEqualTo(UPDATED_DATE_APPROVE);
        assertThat(testMAuction.getDateReject()).isEqualTo(UPDATED_DATE_REJECT);
        assertThat(testMAuction.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMAuction.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuction.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuction() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionRepository.findAll().size();

        // Create the MAuction
        MAuctionDTO mAuctionDTO = mAuctionMapper.toDto(mAuction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionMockMvc.perform(put("/api/m-auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuction in the database
        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuction() throws Exception {
        // Initialize the database
        mAuctionRepository.saveAndFlush(mAuction);

        int databaseSizeBeforeDelete = mAuctionRepository.findAll().size();

        // Delete the mAuction
        restMAuctionMockMvc.perform(delete("/api/m-auctions/{id}", mAuction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuction> mAuctionList = mAuctionRepository.findAll();
        assertThat(mAuctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
