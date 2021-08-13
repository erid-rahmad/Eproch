package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalAnnouncementResult;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.MPrequalificationSchedule;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MPrequalAnnouncementResultRepository;
import com.bhp.opusb.service.MPrequalAnnouncementResultService;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementResultMapper;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultCriteria;
import com.bhp.opusb.service.MPrequalAnnouncementResultQueryService;

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
 * Integration tests for the {@link MPrequalAnnouncementResultResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalAnnouncementResultResourceIT {

    private static final String DEFAULT_DESCRIPTION_PASS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PASS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_FAIL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_FAIL = "BBBBBBBBBB";

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
    private MPrequalAnnouncementResultRepository mPrequalAnnouncementResultRepository;

    @Autowired
    private MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper;

    @Autowired
    private MPrequalAnnouncementResultService mPrequalAnnouncementResultService;

    @Autowired
    private MPrequalAnnouncementResultQueryService mPrequalAnnouncementResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalAnnouncementResultMockMvc;

    private MPrequalAnnouncementResult mPrequalAnnouncementResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalAnnouncementResult createEntity(EntityManager em) {
        MPrequalAnnouncementResult mPrequalAnnouncementResult = new MPrequalAnnouncementResult()
            .descriptionPass(DEFAULT_DESCRIPTION_PASS)
            .descriptionFail(DEFAULT_DESCRIPTION_FAIL)
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
        mPrequalAnnouncementResult.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalAnnouncementResult.setPrequalification(mPrequalificationInformation);
        // Add required entity
        MPrequalificationSchedule mPrequalificationSchedule;
        if (TestUtil.findAll(em, MPrequalificationSchedule.class).isEmpty()) {
            mPrequalificationSchedule = MPrequalificationScheduleResourceIT.createEntity(em);
            em.persist(mPrequalificationSchedule);
            em.flush();
        } else {
            mPrequalificationSchedule = TestUtil.findAll(em, MPrequalificationSchedule.class).get(0);
        }
        mPrequalAnnouncementResult.setPrequalificationSchedule(mPrequalificationSchedule);
        return mPrequalAnnouncementResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalAnnouncementResult createUpdatedEntity(EntityManager em) {
        MPrequalAnnouncementResult mPrequalAnnouncementResult = new MPrequalAnnouncementResult()
            .descriptionPass(UPDATED_DESCRIPTION_PASS)
            .descriptionFail(UPDATED_DESCRIPTION_FAIL)
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
        mPrequalAnnouncementResult.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalAnnouncementResult.setPrequalification(mPrequalificationInformation);
        // Add required entity
        MPrequalificationSchedule mPrequalificationSchedule;
        if (TestUtil.findAll(em, MPrequalificationSchedule.class).isEmpty()) {
            mPrequalificationSchedule = MPrequalificationScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationSchedule);
            em.flush();
        } else {
            mPrequalificationSchedule = TestUtil.findAll(em, MPrequalificationSchedule.class).get(0);
        }
        mPrequalAnnouncementResult.setPrequalificationSchedule(mPrequalificationSchedule);
        return mPrequalAnnouncementResult;
    }

    @BeforeEach
    public void initTest() {
        mPrequalAnnouncementResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalAnnouncementResult() throws Exception {
        int databaseSizeBeforeCreate = mPrequalAnnouncementResultRepository.findAll().size();

        // Create the MPrequalAnnouncementResult
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultMapper.toDto(mPrequalAnnouncementResult);
        restMPrequalAnnouncementResultMockMvc.perform(post("/api/m-prequal-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementResultDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalAnnouncementResult in the database
        List<MPrequalAnnouncementResult> mPrequalAnnouncementResultList = mPrequalAnnouncementResultRepository.findAll();
        assertThat(mPrequalAnnouncementResultList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalAnnouncementResult testMPrequalAnnouncementResult = mPrequalAnnouncementResultList.get(mPrequalAnnouncementResultList.size() - 1);
        assertThat(testMPrequalAnnouncementResult.getDescriptionPass()).isEqualTo(DEFAULT_DESCRIPTION_PASS);
        assertThat(testMPrequalAnnouncementResult.getDescriptionFail()).isEqualTo(DEFAULT_DESCRIPTION_FAIL);
        assertThat(testMPrequalAnnouncementResult.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testMPrequalAnnouncementResult.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMPrequalAnnouncementResult.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalAnnouncementResult.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalAnnouncementResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalAnnouncementResultRepository.findAll().size();

        // Create the MPrequalAnnouncementResult with an existing ID
        mPrequalAnnouncementResult.setId(1L);
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultMapper.toDto(mPrequalAnnouncementResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalAnnouncementResultMockMvc.perform(post("/api/m-prequal-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalAnnouncementResult in the database
        List<MPrequalAnnouncementResult> mPrequalAnnouncementResultList = mPrequalAnnouncementResultRepository.findAll();
        assertThat(mPrequalAnnouncementResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResults() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalAnnouncementResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].descriptionPass").value(hasItem(DEFAULT_DESCRIPTION_PASS.toString())))
            .andExpect(jsonPath("$.[*].descriptionFail").value(hasItem(DEFAULT_DESCRIPTION_FAIL.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalAnnouncementResult() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get the mPrequalAnnouncementResult
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results/{id}", mPrequalAnnouncementResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalAnnouncementResult.getId().intValue()))
            .andExpect(jsonPath("$.descriptionPass").value(DEFAULT_DESCRIPTION_PASS.toString()))
            .andExpect(jsonPath("$.descriptionFail").value(DEFAULT_DESCRIPTION_FAIL.toString()))
            .andExpect(jsonPath("$.publishDate").value(sameInstant(DEFAULT_PUBLISH_DATE)))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalAnnouncementResultsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        Long id = mPrequalAnnouncementResult.getId();

        defaultMPrequalAnnouncementResultShouldBeFound("id.equals=" + id);
        defaultMPrequalAnnouncementResultShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalAnnouncementResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalAnnouncementResultShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalAnnouncementResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalAnnouncementResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate equals to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.equals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.equals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate not equals to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.notEquals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate not equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.notEquals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate in DEFAULT_PUBLISH_DATE or UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.in=" + DEFAULT_PUBLISH_DATE + "," + UPDATED_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.in=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate is not null
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.specified=true");

        // Get all the mPrequalAnnouncementResultList where publishDate is null
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate is greater than or equal to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.greaterThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate is greater than or equal to UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.greaterThanOrEqual=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate is less than or equal to DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.lessThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate is less than or equal to SMALLER_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.lessThanOrEqual=" + SMALLER_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate is less than DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.lessThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate is less than UPDATED_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.lessThan=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPublishDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where publishDate is greater than DEFAULT_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldNotBeFound("publishDate.greaterThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mPrequalAnnouncementResultList where publishDate is greater than SMALLER_PUBLISH_DATE
        defaultMPrequalAnnouncementResultShouldBeFound("publishDate.greaterThan=" + SMALLER_PUBLISH_DATE);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalAnnouncementResultList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalAnnouncementResultList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mPrequalAnnouncementResultList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo is not null
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.specified=true");

        // Get all the mPrequalAnnouncementResultList where documentNo is null
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalAnnouncementResultList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mPrequalAnnouncementResultList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMPrequalAnnouncementResultShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where uid equals to DEFAULT_UID
        defaultMPrequalAnnouncementResultShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalAnnouncementResultList where uid equals to UPDATED_UID
        defaultMPrequalAnnouncementResultShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where uid not equals to DEFAULT_UID
        defaultMPrequalAnnouncementResultShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalAnnouncementResultList where uid not equals to UPDATED_UID
        defaultMPrequalAnnouncementResultShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalAnnouncementResultShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalAnnouncementResultList where uid equals to UPDATED_UID
        defaultMPrequalAnnouncementResultShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where uid is not null
        defaultMPrequalAnnouncementResultShouldBeFound("uid.specified=true");

        // Get all the mPrequalAnnouncementResultList where uid is null
        defaultMPrequalAnnouncementResultShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where active equals to DEFAULT_ACTIVE
        defaultMPrequalAnnouncementResultShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalAnnouncementResultList where active equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementResultShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalAnnouncementResultShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalAnnouncementResultList where active not equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementResultShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalAnnouncementResultShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalAnnouncementResultList where active equals to UPDATED_ACTIVE
        defaultMPrequalAnnouncementResultShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        // Get all the mPrequalAnnouncementResultList where active is not null
        defaultMPrequalAnnouncementResultShouldBeFound("active.specified=true");

        // Get all the mPrequalAnnouncementResultList where active is null
        defaultMPrequalAnnouncementResultShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalAnnouncementResult.getAdOrganization();
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalAnnouncementResultList where adOrganization equals to adOrganizationId
        defaultMPrequalAnnouncementResultShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalAnnouncementResultList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalAnnouncementResultShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalAnnouncementResult.getPrequalification();
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalAnnouncementResultList where prequalification equals to prequalificationId
        defaultMPrequalAnnouncementResultShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalAnnouncementResultList where prequalification equals to prequalificationId + 1
        defaultMPrequalAnnouncementResultShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByPrequalificationScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationSchedule prequalificationSchedule = mPrequalAnnouncementResult.getPrequalificationSchedule();
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);
        Long prequalificationScheduleId = prequalificationSchedule.getId();

        // Get all the mPrequalAnnouncementResultList where prequalificationSchedule equals to prequalificationScheduleId
        defaultMPrequalAnnouncementResultShouldBeFound("prequalificationScheduleId.equals=" + prequalificationScheduleId);

        // Get all the mPrequalAnnouncementResultList where prequalificationSchedule equals to prequalificationScheduleId + 1
        defaultMPrequalAnnouncementResultShouldNotBeFound("prequalificationScheduleId.equals=" + (prequalificationScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalAnnouncementResultsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        mPrequalAnnouncementResult.setAttachment(attachment);
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);
        Long attachmentId = attachment.getId();

        // Get all the mPrequalAnnouncementResultList where attachment equals to attachmentId
        defaultMPrequalAnnouncementResultShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mPrequalAnnouncementResultList where attachment equals to attachmentId + 1
        defaultMPrequalAnnouncementResultShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalAnnouncementResultShouldBeFound(String filter) throws Exception {
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalAnnouncementResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].descriptionPass").value(hasItem(DEFAULT_DESCRIPTION_PASS.toString())))
            .andExpect(jsonPath("$.[*].descriptionFail").value(hasItem(DEFAULT_DESCRIPTION_FAIL.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(sameInstant(DEFAULT_PUBLISH_DATE))))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalAnnouncementResultShouldNotBeFound(String filter) throws Exception {
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalAnnouncementResult() throws Exception {
        // Get the mPrequalAnnouncementResult
        restMPrequalAnnouncementResultMockMvc.perform(get("/api/m-prequal-announcement-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalAnnouncementResult() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        int databaseSizeBeforeUpdate = mPrequalAnnouncementResultRepository.findAll().size();

        // Update the mPrequalAnnouncementResult
        MPrequalAnnouncementResult updatedMPrequalAnnouncementResult = mPrequalAnnouncementResultRepository.findById(mPrequalAnnouncementResult.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalAnnouncementResult are not directly saved in db
        em.detach(updatedMPrequalAnnouncementResult);
        updatedMPrequalAnnouncementResult
            .descriptionPass(UPDATED_DESCRIPTION_PASS)
            .descriptionFail(UPDATED_DESCRIPTION_FAIL)
            .publishDate(UPDATED_PUBLISH_DATE)
            .documentNo(UPDATED_DOCUMENT_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultMapper.toDto(updatedMPrequalAnnouncementResult);

        restMPrequalAnnouncementResultMockMvc.perform(put("/api/m-prequal-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementResultDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalAnnouncementResult in the database
        List<MPrequalAnnouncementResult> mPrequalAnnouncementResultList = mPrequalAnnouncementResultRepository.findAll();
        assertThat(mPrequalAnnouncementResultList).hasSize(databaseSizeBeforeUpdate);
        MPrequalAnnouncementResult testMPrequalAnnouncementResult = mPrequalAnnouncementResultList.get(mPrequalAnnouncementResultList.size() - 1);
        assertThat(testMPrequalAnnouncementResult.getDescriptionPass()).isEqualTo(UPDATED_DESCRIPTION_PASS);
        assertThat(testMPrequalAnnouncementResult.getDescriptionFail()).isEqualTo(UPDATED_DESCRIPTION_FAIL);
        assertThat(testMPrequalAnnouncementResult.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testMPrequalAnnouncementResult.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMPrequalAnnouncementResult.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalAnnouncementResult.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalAnnouncementResult() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalAnnouncementResultRepository.findAll().size();

        // Create the MPrequalAnnouncementResult
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultMapper.toDto(mPrequalAnnouncementResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalAnnouncementResultMockMvc.perform(put("/api/m-prequal-announcement-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalAnnouncementResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalAnnouncementResult in the database
        List<MPrequalAnnouncementResult> mPrequalAnnouncementResultList = mPrequalAnnouncementResultRepository.findAll();
        assertThat(mPrequalAnnouncementResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalAnnouncementResult() throws Exception {
        // Initialize the database
        mPrequalAnnouncementResultRepository.saveAndFlush(mPrequalAnnouncementResult);

        int databaseSizeBeforeDelete = mPrequalAnnouncementResultRepository.findAll().size();

        // Delete the mPrequalAnnouncementResult
        restMPrequalAnnouncementResultMockMvc.perform(delete("/api/m-prequal-announcement-results/{id}", mPrequalAnnouncementResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalAnnouncementResult> mPrequalAnnouncementResultList = mPrequalAnnouncementResultRepository.findAll();
        assertThat(mPrequalAnnouncementResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
