package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CAnnouncementResult;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.CAnnouncementResultRepository;
import com.bhp.opusb.service.CAnnouncementResultService;
import com.bhp.opusb.service.dto.CAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.CAnnouncementResultMapper;
import com.bhp.opusb.service.dto.CAnnouncementResultCriteria;
import com.bhp.opusb.service.CAnnouncementResultQueryService;

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
 * Integration tests for the {@link CAnnouncementResultResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CAnnouncementResultResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUBLISH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PUBLISH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CAnnouncementResultRepository cAnnouncementResultRepository;

    @Autowired
    private CAnnouncementResultMapper cAnnouncementResultMapper;

    @Autowired
    private CAnnouncementResultService cAnnouncementResultService;

    @Autowired
    private CAnnouncementResultQueryService cAnnouncementResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCAnnouncementResultMockMvc;

    private CAnnouncementResult cAnnouncementResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAnnouncementResult createEntity(EntityManager em) {
        CAnnouncementResult cAnnouncementResult = new CAnnouncementResult()
            .description(DEFAULT_DESCRIPTION)
            .publishDate(DEFAULT_PUBLISH_DATE)
            .documentNo(DEFAULT_DOCUMENT_NO)
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
        cAnnouncementResult.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        cAnnouncementResult.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        cAnnouncementResult.setBiddingSchedule(mBiddingSchedule);
        return cAnnouncementResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAnnouncementResult createUpdatedEntity(EntityManager em) {
        CAnnouncementResult cAnnouncementResult = new CAnnouncementResult()
            .description(UPDATED_DESCRIPTION)
            .publishDate(UPDATED_PUBLISH_DATE)
            .documentNo(UPDATED_DOCUMENT_NO)
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
        cAnnouncementResult.setAdOrganization(aDOrganization);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        cAnnouncementResult.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        cAnnouncementResult.setBiddingSchedule(mBiddingSchedule);
        return cAnnouncementResult;
    }

    @BeforeEach
    public void initTest() {
        cAnnouncementResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createCAnnouncementResult() throws Exception {
        int databaseSizeBeforeCreate = cAnnouncementResultRepository.findAll().size();

        // Create the CAnnouncementResult
        CAnnouncementResultDTO cAnnouncementResultDTO = cAnnouncementResultMapper.toDto(cAnnouncementResult);
        restCAnnouncementResultMockMvc.perform(post("/api/c-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementResultDTO)))
            .andExpect(status().isCreated());

        // Validate the CAnnouncementResult in the database
        List<CAnnouncementResult> cAnnouncementResultList = cAnnouncementResultRepository.findAll();
        assertThat(cAnnouncementResultList).hasSize(databaseSizeBeforeCreate + 1);
        CAnnouncementResult testCAnnouncementResult = cAnnouncementResultList.get(cAnnouncementResultList.size() - 1);
        assertThat(testCAnnouncementResult.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCAnnouncementResult.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testCAnnouncementResult.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testCAnnouncementResult.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCAnnouncementResult.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCAnnouncementResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cAnnouncementResultRepository.findAll().size();

        // Create the CAnnouncementResult with an existing ID
        cAnnouncementResult.setId(1L);
        CAnnouncementResultDTO cAnnouncementResultDTO = cAnnouncementResultMapper.toDto(cAnnouncementResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAnnouncementResultMockMvc.perform(post("/api/c-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAnnouncementResult in the database
        List<CAnnouncementResult> cAnnouncementResultList = cAnnouncementResultRepository.findAll();
        assertThat(cAnnouncementResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResults() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAnnouncementResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCAnnouncementResult() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get the cAnnouncementResult
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results/{id}", cAnnouncementResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cAnnouncementResult.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.publishDate").value(sameInstant(DEFAULT_PUBLISH_DATE)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCAnnouncementResultsByIdFiltering() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        Long id = cAnnouncementResult.getId();

        defaultCAnnouncementResultShouldBeFound("id.equals=" + id);
        defaultCAnnouncementResultShouldNotBeFound("id.notEquals=" + id);

        defaultCAnnouncementResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCAnnouncementResultShouldNotBeFound("id.greaterThan=" + id);

        defaultCAnnouncementResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCAnnouncementResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate equals to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.equals=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.equals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate not equals to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.notEquals=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate not equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.notEquals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate in DEFAULT_PUBLISH_DATE or UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.in=" + DEFAULT_PUBLISH_DATE + "," + UPDATED_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.in=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate is not null
        defaultCAnnouncementResultShouldBeFound("publishDate.specified=true");

        // Get all the cAnnouncementResultList where publishDate is null
        defaultCAnnouncementResultShouldNotBeFound("publishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate is greater than or equal to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.greaterThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate is greater than or equal to UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.greaterThanOrEqual=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate is less than or equal to DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.lessThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate is less than or equal to SMALLER_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.lessThanOrEqual=" + SMALLER_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate is less than DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.lessThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate is less than UPDATED_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.lessThan=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByPublishDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where publishDate is greater than DEFAULT_PUBLISH_DATE
        defaultCAnnouncementResultShouldNotBeFound("publishDate.greaterThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the cAnnouncementResultList where publishDate is greater than SMALLER_PUBLISH_DATE
        defaultCAnnouncementResultShouldBeFound("publishDate.greaterThan=" + SMALLER_PUBLISH_DATE);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultCAnnouncementResultShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cAnnouncementResultList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultCAnnouncementResultShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cAnnouncementResultList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the cAnnouncementResultList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo is not null
        defaultCAnnouncementResultShouldBeFound("documentNo.specified=true");

        // Get all the cAnnouncementResultList where documentNo is null
        defaultCAnnouncementResultShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultCAnnouncementResultShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the cAnnouncementResultList where documentNo contains UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultCAnnouncementResultShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the cAnnouncementResultList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultCAnnouncementResultShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where uid equals to DEFAULT_UID
        defaultCAnnouncementResultShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cAnnouncementResultList where uid equals to UPDATED_UID
        defaultCAnnouncementResultShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where uid not equals to DEFAULT_UID
        defaultCAnnouncementResultShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cAnnouncementResultList where uid not equals to UPDATED_UID
        defaultCAnnouncementResultShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where uid in DEFAULT_UID or UPDATED_UID
        defaultCAnnouncementResultShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cAnnouncementResultList where uid equals to UPDATED_UID
        defaultCAnnouncementResultShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where uid is not null
        defaultCAnnouncementResultShouldBeFound("uid.specified=true");

        // Get all the cAnnouncementResultList where uid is null
        defaultCAnnouncementResultShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where active equals to DEFAULT_ACTIVE
        defaultCAnnouncementResultShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cAnnouncementResultList where active equals to UPDATED_ACTIVE
        defaultCAnnouncementResultShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where active not equals to DEFAULT_ACTIVE
        defaultCAnnouncementResultShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cAnnouncementResultList where active not equals to UPDATED_ACTIVE
        defaultCAnnouncementResultShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCAnnouncementResultShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cAnnouncementResultList where active equals to UPDATED_ACTIVE
        defaultCAnnouncementResultShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        // Get all the cAnnouncementResultList where active is not null
        defaultCAnnouncementResultShouldBeFound("active.specified=true");

        // Get all the cAnnouncementResultList where active is null
        defaultCAnnouncementResultShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAnnouncementResultsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cAnnouncementResult.getAdOrganization();
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cAnnouncementResultList where adOrganization equals to adOrganizationId
        defaultCAnnouncementResultShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cAnnouncementResultList where adOrganization equals to adOrganizationId + 1
        defaultCAnnouncementResultShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = cAnnouncementResult.getBidding();
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);
        Long biddingId = bidding.getId();

        // Get all the cAnnouncementResultList where bidding equals to biddingId
        defaultCAnnouncementResultShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the cAnnouncementResultList where bidding equals to biddingId + 1
        defaultCAnnouncementResultShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = cAnnouncementResult.getBiddingSchedule();
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the cAnnouncementResultList where biddingSchedule equals to biddingScheduleId
        defaultCAnnouncementResultShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the cAnnouncementResultList where biddingSchedule equals to biddingScheduleId + 1
        defaultCAnnouncementResultShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllCAnnouncementResultsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        cAnnouncementResult.setAttachment(attachment);
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);
        Long attachmentId = attachment.getId();

        // Get all the cAnnouncementResultList where attachment equals to attachmentId
        defaultCAnnouncementResultShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the cAnnouncementResultList where attachment equals to attachmentId + 1
        defaultCAnnouncementResultShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCAnnouncementResultShouldBeFound(String filter) throws Exception {
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAnnouncementResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCAnnouncementResultShouldNotBeFound(String filter) throws Exception {
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCAnnouncementResult() throws Exception {
        // Get the cAnnouncementResult
        restCAnnouncementResultMockMvc.perform(get("/api/c-announcement-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCAnnouncementResult() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        int databaseSizeBeforeUpdate = cAnnouncementResultRepository.findAll().size();

        // Update the cAnnouncementResult
        CAnnouncementResult updatedCAnnouncementResult = cAnnouncementResultRepository.findById(cAnnouncementResult.getId()).get();
        // Disconnect from session so that the updates on updatedCAnnouncementResult are not directly saved in db
        em.detach(updatedCAnnouncementResult);
        updatedCAnnouncementResult
            .description(UPDATED_DESCRIPTION)
            .publishDate(UPDATED_PUBLISH_DATE)
            .documentNo(UPDATED_DOCUMENT_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CAnnouncementResultDTO cAnnouncementResultDTO = cAnnouncementResultMapper.toDto(updatedCAnnouncementResult);

        restCAnnouncementResultMockMvc.perform(put("/api/c-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementResultDTO)))
            .andExpect(status().isOk());

        // Validate the CAnnouncementResult in the database
        List<CAnnouncementResult> cAnnouncementResultList = cAnnouncementResultRepository.findAll();
        assertThat(cAnnouncementResultList).hasSize(databaseSizeBeforeUpdate);
        CAnnouncementResult testCAnnouncementResult = cAnnouncementResultList.get(cAnnouncementResultList.size() - 1);
        assertThat(testCAnnouncementResult.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCAnnouncementResult.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testCAnnouncementResult.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testCAnnouncementResult.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCAnnouncementResult.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCAnnouncementResult() throws Exception {
        int databaseSizeBeforeUpdate = cAnnouncementResultRepository.findAll().size();

        // Create the CAnnouncementResult
        CAnnouncementResultDTO cAnnouncementResultDTO = cAnnouncementResultMapper.toDto(cAnnouncementResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAnnouncementResultMockMvc.perform(put("/api/c-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAnnouncementResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAnnouncementResult in the database
        List<CAnnouncementResult> cAnnouncementResultList = cAnnouncementResultRepository.findAll();
        assertThat(cAnnouncementResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCAnnouncementResult() throws Exception {
        // Initialize the database
        cAnnouncementResultRepository.saveAndFlush(cAnnouncementResult);

        int databaseSizeBeforeDelete = cAnnouncementResultRepository.findAll().size();

        // Delete the cAnnouncementResult
        restCAnnouncementResultMockMvc.perform(delete("/api/c-announcement-results/{id}", cAnnouncementResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAnnouncementResult> cAnnouncementResultList = cAnnouncementResultRepository.findAll();
        assertThat(cAnnouncementResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
