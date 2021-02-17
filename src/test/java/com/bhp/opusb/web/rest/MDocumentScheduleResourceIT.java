package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MDocumentSchedule;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MDocumentScheduleRepository;
import com.bhp.opusb.service.MDocumentScheduleService;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;
import com.bhp.opusb.service.mapper.MDocumentScheduleMapper;
import com.bhp.opusb.service.dto.MDocumentScheduleCriteria;
import com.bhp.opusb.service.MDocumentScheduleQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MDocumentScheduleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MDocumentScheduleResourceIT {

    private static final String DEFAULT_DOC_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_DOC_EVENT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MDocumentScheduleRepository mDocumentScheduleRepository;

    @Autowired
    private MDocumentScheduleMapper mDocumentScheduleMapper;

    @Autowired
    private MDocumentScheduleService mDocumentScheduleService;

    @Autowired
    private MDocumentScheduleQueryService mDocumentScheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMDocumentScheduleMockMvc;

    private MDocumentSchedule mDocumentSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MDocumentSchedule createEntity(EntityManager em) {
        MDocumentSchedule mDocumentSchedule = new MDocumentSchedule()
            .docEvent(DEFAULT_DOC_EVENT)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mDocumentSchedule.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mDocumentSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mDocumentSchedule.setVendorSubmission(mBiddingSchedule);
        // Add required entity
        mDocumentSchedule.setVendorEvaluation(mBiddingSchedule);
        return mDocumentSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MDocumentSchedule createUpdatedEntity(EntityManager em) {
        MDocumentSchedule mDocumentSchedule = new MDocumentSchedule()
            .docEvent(UPDATED_DOC_EVENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mDocumentSchedule.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mDocumentSchedule.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mDocumentSchedule.setVendorSubmission(mBiddingSchedule);
        // Add required entity
        mDocumentSchedule.setVendorEvaluation(mBiddingSchedule);
        return mDocumentSchedule;
    }

    @BeforeEach
    public void initTest() {
        mDocumentSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMDocumentSchedule() throws Exception {
        int databaseSizeBeforeCreate = mDocumentScheduleRepository.findAll().size();

        // Create the MDocumentSchedule
        MDocumentScheduleDTO mDocumentScheduleDTO = mDocumentScheduleMapper.toDto(mDocumentSchedule);
        restMDocumentScheduleMockMvc.perform(post("/api/m-document-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mDocumentScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the MDocumentSchedule in the database
        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        MDocumentSchedule testMDocumentSchedule = mDocumentScheduleList.get(mDocumentScheduleList.size() - 1);
        assertThat(testMDocumentSchedule.getDocEvent()).isEqualTo(DEFAULT_DOC_EVENT);
        assertThat(testMDocumentSchedule.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMDocumentSchedule.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMDocumentScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mDocumentScheduleRepository.findAll().size();

        // Create the MDocumentSchedule with an existing ID
        mDocumentSchedule.setId(1L);
        MDocumentScheduleDTO mDocumentScheduleDTO = mDocumentScheduleMapper.toDto(mDocumentSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMDocumentScheduleMockMvc.perform(post("/api/m-document-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mDocumentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MDocumentSchedule in the database
        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = mDocumentScheduleRepository.findAll().size();
        // set the field null
        mDocumentSchedule.setDocEvent(null);

        // Create the MDocumentSchedule, which fails.
        MDocumentScheduleDTO mDocumentScheduleDTO = mDocumentScheduleMapper.toDto(mDocumentSchedule);

        restMDocumentScheduleMockMvc.perform(post("/api/m-document-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mDocumentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedules() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mDocumentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].docEvent").value(hasItem(DEFAULT_DOC_EVENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMDocumentSchedule() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get the mDocumentSchedule
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules/{id}", mDocumentSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mDocumentSchedule.getId().intValue()))
            .andExpect(jsonPath("$.docEvent").value(DEFAULT_DOC_EVENT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMDocumentSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        Long id = mDocumentSchedule.getId();

        defaultMDocumentScheduleShouldBeFound("id.equals=" + id);
        defaultMDocumentScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultMDocumentScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMDocumentScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultMDocumentScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMDocumentScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventIsEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent equals to DEFAULT_DOC_EVENT
        defaultMDocumentScheduleShouldBeFound("docEvent.equals=" + DEFAULT_DOC_EVENT);

        // Get all the mDocumentScheduleList where docEvent equals to UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldNotBeFound("docEvent.equals=" + UPDATED_DOC_EVENT);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent not equals to DEFAULT_DOC_EVENT
        defaultMDocumentScheduleShouldNotBeFound("docEvent.notEquals=" + DEFAULT_DOC_EVENT);

        // Get all the mDocumentScheduleList where docEvent not equals to UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldBeFound("docEvent.notEquals=" + UPDATED_DOC_EVENT);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventIsInShouldWork() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent in DEFAULT_DOC_EVENT or UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldBeFound("docEvent.in=" + DEFAULT_DOC_EVENT + "," + UPDATED_DOC_EVENT);

        // Get all the mDocumentScheduleList where docEvent equals to UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldNotBeFound("docEvent.in=" + UPDATED_DOC_EVENT);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent is not null
        defaultMDocumentScheduleShouldBeFound("docEvent.specified=true");

        // Get all the mDocumentScheduleList where docEvent is null
        defaultMDocumentScheduleShouldNotBeFound("docEvent.specified=false");
    }
                @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventContainsSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent contains DEFAULT_DOC_EVENT
        defaultMDocumentScheduleShouldBeFound("docEvent.contains=" + DEFAULT_DOC_EVENT);

        // Get all the mDocumentScheduleList where docEvent contains UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldNotBeFound("docEvent.contains=" + UPDATED_DOC_EVENT);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByDocEventNotContainsSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where docEvent does not contain DEFAULT_DOC_EVENT
        defaultMDocumentScheduleShouldNotBeFound("docEvent.doesNotContain=" + DEFAULT_DOC_EVENT);

        // Get all the mDocumentScheduleList where docEvent does not contain UPDATED_DOC_EVENT
        defaultMDocumentScheduleShouldBeFound("docEvent.doesNotContain=" + UPDATED_DOC_EVENT);
    }


    @Test
    @Transactional
    public void getAllMDocumentSchedulesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where uid equals to DEFAULT_UID
        defaultMDocumentScheduleShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mDocumentScheduleList where uid equals to UPDATED_UID
        defaultMDocumentScheduleShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where uid not equals to DEFAULT_UID
        defaultMDocumentScheduleShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mDocumentScheduleList where uid not equals to UPDATED_UID
        defaultMDocumentScheduleShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where uid in DEFAULT_UID or UPDATED_UID
        defaultMDocumentScheduleShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mDocumentScheduleList where uid equals to UPDATED_UID
        defaultMDocumentScheduleShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where uid is not null
        defaultMDocumentScheduleShouldBeFound("uid.specified=true");

        // Get all the mDocumentScheduleList where uid is null
        defaultMDocumentScheduleShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where active equals to DEFAULT_ACTIVE
        defaultMDocumentScheduleShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mDocumentScheduleList where active equals to UPDATED_ACTIVE
        defaultMDocumentScheduleShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where active not equals to DEFAULT_ACTIVE
        defaultMDocumentScheduleShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mDocumentScheduleList where active not equals to UPDATED_ACTIVE
        defaultMDocumentScheduleShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMDocumentScheduleShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mDocumentScheduleList where active equals to UPDATED_ACTIVE
        defaultMDocumentScheduleShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        // Get all the mDocumentScheduleList where active is not null
        defaultMDocumentScheduleShouldBeFound("active.specified=true");

        // Get all the mDocumentScheduleList where active is null
        defaultMDocumentScheduleShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMDocumentSchedulesByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mDocumentSchedule.getBidding();
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);
        Long biddingId = bidding.getId();

        // Get all the mDocumentScheduleList where bidding equals to biddingId
        defaultMDocumentScheduleShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mDocumentScheduleList where bidding equals to biddingId + 1
        defaultMDocumentScheduleShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMDocumentSchedulesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mDocumentSchedule.getAdOrganization();
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mDocumentScheduleList where adOrganization equals to adOrganizationId
        defaultMDocumentScheduleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mDocumentScheduleList where adOrganization equals to adOrganizationId + 1
        defaultMDocumentScheduleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMDocumentSchedulesByVendorSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule vendorSubmission = mDocumentSchedule.getVendorSubmission();
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);
        Long vendorSubmissionId = vendorSubmission.getId();

        // Get all the mDocumentScheduleList where vendorSubmission equals to vendorSubmissionId
        defaultMDocumentScheduleShouldBeFound("vendorSubmissionId.equals=" + vendorSubmissionId);

        // Get all the mDocumentScheduleList where vendorSubmission equals to vendorSubmissionId + 1
        defaultMDocumentScheduleShouldNotBeFound("vendorSubmissionId.equals=" + (vendorSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMDocumentSchedulesByVendorEvaluationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule vendorEvaluation = mDocumentSchedule.getVendorEvaluation();
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);
        Long vendorEvaluationId = vendorEvaluation.getId();

        // Get all the mDocumentScheduleList where vendorEvaluation equals to vendorEvaluationId
        defaultMDocumentScheduleShouldBeFound("vendorEvaluationId.equals=" + vendorEvaluationId);

        // Get all the mDocumentScheduleList where vendorEvaluation equals to vendorEvaluationId + 1
        defaultMDocumentScheduleShouldNotBeFound("vendorEvaluationId.equals=" + (vendorEvaluationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMDocumentScheduleShouldBeFound(String filter) throws Exception {
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mDocumentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].docEvent").value(hasItem(DEFAULT_DOC_EVENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMDocumentScheduleShouldNotBeFound(String filter) throws Exception {
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMDocumentSchedule() throws Exception {
        // Get the mDocumentSchedule
        restMDocumentScheduleMockMvc.perform(get("/api/m-document-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMDocumentSchedule() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        int databaseSizeBeforeUpdate = mDocumentScheduleRepository.findAll().size();

        // Update the mDocumentSchedule
        MDocumentSchedule updatedMDocumentSchedule = mDocumentScheduleRepository.findById(mDocumentSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedMDocumentSchedule are not directly saved in db
        em.detach(updatedMDocumentSchedule);
        updatedMDocumentSchedule
            .docEvent(UPDATED_DOC_EVENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MDocumentScheduleDTO mDocumentScheduleDTO = mDocumentScheduleMapper.toDto(updatedMDocumentSchedule);

        restMDocumentScheduleMockMvc.perform(put("/api/m-document-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mDocumentScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the MDocumentSchedule in the database
        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeUpdate);
        MDocumentSchedule testMDocumentSchedule = mDocumentScheduleList.get(mDocumentScheduleList.size() - 1);
        assertThat(testMDocumentSchedule.getDocEvent()).isEqualTo(UPDATED_DOC_EVENT);
        assertThat(testMDocumentSchedule.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMDocumentSchedule.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMDocumentSchedule() throws Exception {
        int databaseSizeBeforeUpdate = mDocumentScheduleRepository.findAll().size();

        // Create the MDocumentSchedule
        MDocumentScheduleDTO mDocumentScheduleDTO = mDocumentScheduleMapper.toDto(mDocumentSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMDocumentScheduleMockMvc.perform(put("/api/m-document-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mDocumentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MDocumentSchedule in the database
        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMDocumentSchedule() throws Exception {
        // Initialize the database
        mDocumentScheduleRepository.saveAndFlush(mDocumentSchedule);

        int databaseSizeBeforeDelete = mDocumentScheduleRepository.findAll().size();

        // Delete the mDocumentSchedule
        restMDocumentScheduleMockMvc.perform(delete("/api/m-document-schedules/{id}", mDocumentSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MDocumentSchedule> mDocumentScheduleList = mDocumentScheduleRepository.findAll();
        assertThat(mDocumentScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
