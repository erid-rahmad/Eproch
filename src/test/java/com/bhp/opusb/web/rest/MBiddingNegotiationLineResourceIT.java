package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.repository.MBiddingNegotiationLineRepository;
import com.bhp.opusb.service.MBiddingNegotiationLineService;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationLineMapper;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineCriteria;
import com.bhp.opusb.service.MBiddingNegotiationLineQueryService;

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
 * Integration tests for the {@link MBiddingNegotiationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingNegotiationLineResourceIT {

    private static final String DEFAULT_NEGOTIATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_NEGOTIATION_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository;

    @Autowired
    private MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper;

    @Autowired
    private MBiddingNegotiationLineService mBiddingNegotiationLineService;

    @Autowired
    private MBiddingNegotiationLineQueryService mBiddingNegotiationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingNegotiationLineMockMvc;

    private MBiddingNegotiationLine mBiddingNegotiationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiationLine createEntity(EntityManager em) {
        MBiddingNegotiationLine mBiddingNegotiationLine = new MBiddingNegotiationLine()
            .negotiationStatus(DEFAULT_NEGOTIATION_STATUS)
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
        mBiddingNegotiationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingNegotiation mBiddingNegotiation;
        if (TestUtil.findAll(em, MBiddingNegotiation.class).isEmpty()) {
            mBiddingNegotiation = MBiddingNegotiationResourceIT.createEntity(em);
            em.persist(mBiddingNegotiation);
            em.flush();
        } else {
            mBiddingNegotiation = TestUtil.findAll(em, MBiddingNegotiation.class).get(0);
        }
        mBiddingNegotiationLine.setNegotiation(mBiddingNegotiation);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingNegotiationLine.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingNegotiationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiationLine createUpdatedEntity(EntityManager em) {
        MBiddingNegotiationLine mBiddingNegotiationLine = new MBiddingNegotiationLine()
            .negotiationStatus(UPDATED_NEGOTIATION_STATUS)
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
        mBiddingNegotiationLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingNegotiation mBiddingNegotiation;
        if (TestUtil.findAll(em, MBiddingNegotiation.class).isEmpty()) {
            mBiddingNegotiation = MBiddingNegotiationResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingNegotiation);
            em.flush();
        } else {
            mBiddingNegotiation = TestUtil.findAll(em, MBiddingNegotiation.class).get(0);
        }
        mBiddingNegotiationLine.setNegotiation(mBiddingNegotiation);
        // Add required entity
        MBiddingEvalResult mBiddingEvalResult;
        if (TestUtil.findAll(em, MBiddingEvalResult.class).isEmpty()) {
            mBiddingEvalResult = MBiddingEvalResultResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvalResult);
            em.flush();
        } else {
            mBiddingEvalResult = TestUtil.findAll(em, MBiddingEvalResult.class).get(0);
        }
        mBiddingNegotiationLine.setBiddingEvalResult(mBiddingEvalResult);
        return mBiddingNegotiationLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingNegotiationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiationLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationLineRepository.findAll().size();

        // Create the MBiddingNegotiationLine
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO = mBiddingNegotiationLineMapper.toDto(mBiddingNegotiationLine);
        restMBiddingNegotiationLineMockMvc.perform(post("/api/m-bidding-negotiation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingNegotiationLine in the database
        List<MBiddingNegotiationLine> mBiddingNegotiationLineList = mBiddingNegotiationLineRepository.findAll();
        assertThat(mBiddingNegotiationLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingNegotiationLine testMBiddingNegotiationLine = mBiddingNegotiationLineList.get(mBiddingNegotiationLineList.size() - 1);
        assertThat(testMBiddingNegotiationLine.getNegotiationStatus()).isEqualTo(DEFAULT_NEGOTIATION_STATUS);
        assertThat(testMBiddingNegotiationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingNegotiationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationLineRepository.findAll().size();

        // Create the MBiddingNegotiationLine with an existing ID
        mBiddingNegotiationLine.setId(1L);
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO = mBiddingNegotiationLineMapper.toDto(mBiddingNegotiationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingNegotiationLineMockMvc.perform(post("/api/m-bidding-negotiation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiationLine in the database
        List<MBiddingNegotiationLine> mBiddingNegotiationLineList = mBiddingNegotiationLineRepository.findAll();
        assertThat(mBiddingNegotiationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationLines() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].negotiationStatus").value(hasItem(DEFAULT_NEGOTIATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingNegotiationLine() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get the mBiddingNegotiationLine
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines/{id}", mBiddingNegotiationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingNegotiationLine.getId().intValue()))
            .andExpect(jsonPath("$.negotiationStatus").value(DEFAULT_NEGOTIATION_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingNegotiationLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        Long id = mBiddingNegotiationLine.getId();

        defaultMBiddingNegotiationLineShouldBeFound("id.equals=" + id);
        defaultMBiddingNegotiationLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingNegotiationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingNegotiationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingNegotiationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingNegotiationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus equals to DEFAULT_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.equals=" + DEFAULT_NEGOTIATION_STATUS);

        // Get all the mBiddingNegotiationLineList where negotiationStatus equals to UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.equals=" + UPDATED_NEGOTIATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus not equals to DEFAULT_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.notEquals=" + DEFAULT_NEGOTIATION_STATUS);

        // Get all the mBiddingNegotiationLineList where negotiationStatus not equals to UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.notEquals=" + UPDATED_NEGOTIATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus in DEFAULT_NEGOTIATION_STATUS or UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.in=" + DEFAULT_NEGOTIATION_STATUS + "," + UPDATED_NEGOTIATION_STATUS);

        // Get all the mBiddingNegotiationLineList where negotiationStatus equals to UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.in=" + UPDATED_NEGOTIATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus is not null
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.specified=true");

        // Get all the mBiddingNegotiationLineList where negotiationStatus is null
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus contains DEFAULT_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.contains=" + DEFAULT_NEGOTIATION_STATUS);

        // Get all the mBiddingNegotiationLineList where negotiationStatus contains UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.contains=" + UPDATED_NEGOTIATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where negotiationStatus does not contain DEFAULT_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationStatus.doesNotContain=" + DEFAULT_NEGOTIATION_STATUS);

        // Get all the mBiddingNegotiationLineList where negotiationStatus does not contain UPDATED_NEGOTIATION_STATUS
        defaultMBiddingNegotiationLineShouldBeFound("negotiationStatus.doesNotContain=" + UPDATED_NEGOTIATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where uid equals to DEFAULT_UID
        defaultMBiddingNegotiationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationLineList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where uid not equals to DEFAULT_UID
        defaultMBiddingNegotiationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationLineList where uid not equals to UPDATED_UID
        defaultMBiddingNegotiationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingNegotiationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingNegotiationLineList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where uid is not null
        defaultMBiddingNegotiationLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingNegotiationLineList where uid is null
        defaultMBiddingNegotiationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingNegotiationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingNegotiationLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        // Get all the mBiddingNegotiationLineList where active is not null
        defaultMBiddingNegotiationLineShouldBeFound("active.specified=true");

        // Get all the mBiddingNegotiationLineList where active is null
        defaultMBiddingNegotiationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingNegotiationLine.getAdOrganization();
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingNegotiationLineList where adOrganization equals to adOrganizationId
        defaultMBiddingNegotiationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingNegotiationLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingNegotiationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByNegotiationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingNegotiation negotiation = mBiddingNegotiationLine.getNegotiation();
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);
        Long negotiationId = negotiation.getId();

        // Get all the mBiddingNegotiationLineList where negotiation equals to negotiationId
        defaultMBiddingNegotiationLineShouldBeFound("negotiationId.equals=" + negotiationId);

        // Get all the mBiddingNegotiationLineList where negotiation equals to negotiationId + 1
        defaultMBiddingNegotiationLineShouldNotBeFound("negotiationId.equals=" + (negotiationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationLinesByBiddingEvalResultIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvalResult biddingEvalResult = mBiddingNegotiationLine.getBiddingEvalResult();
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);
        Long biddingEvalResultId = biddingEvalResult.getId();

        // Get all the mBiddingNegotiationLineList where biddingEvalResult equals to biddingEvalResultId
        defaultMBiddingNegotiationLineShouldBeFound("biddingEvalResultId.equals=" + biddingEvalResultId);

        // Get all the mBiddingNegotiationLineList where biddingEvalResult equals to biddingEvalResultId + 1
        defaultMBiddingNegotiationLineShouldNotBeFound("biddingEvalResultId.equals=" + (biddingEvalResultId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingNegotiationLineShouldBeFound(String filter) throws Exception {
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].negotiationStatus").value(hasItem(DEFAULT_NEGOTIATION_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingNegotiationLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingNegotiationLine() throws Exception {
        // Get the mBiddingNegotiationLine
        restMBiddingNegotiationLineMockMvc.perform(get("/api/m-bidding-negotiation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingNegotiationLine() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        int databaseSizeBeforeUpdate = mBiddingNegotiationLineRepository.findAll().size();

        // Update the mBiddingNegotiationLine
        MBiddingNegotiationLine updatedMBiddingNegotiationLine = mBiddingNegotiationLineRepository.findById(mBiddingNegotiationLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingNegotiationLine are not directly saved in db
        em.detach(updatedMBiddingNegotiationLine);
        updatedMBiddingNegotiationLine
            .negotiationStatus(UPDATED_NEGOTIATION_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO = mBiddingNegotiationLineMapper.toDto(updatedMBiddingNegotiationLine);

        restMBiddingNegotiationLineMockMvc.perform(put("/api/m-bidding-negotiation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingNegotiationLine in the database
        List<MBiddingNegotiationLine> mBiddingNegotiationLineList = mBiddingNegotiationLineRepository.findAll();
        assertThat(mBiddingNegotiationLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingNegotiationLine testMBiddingNegotiationLine = mBiddingNegotiationLineList.get(mBiddingNegotiationLineList.size() - 1);
        assertThat(testMBiddingNegotiationLine.getNegotiationStatus()).isEqualTo(UPDATED_NEGOTIATION_STATUS);
        assertThat(testMBiddingNegotiationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingNegotiationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingNegotiationLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingNegotiationLineRepository.findAll().size();

        // Create the MBiddingNegotiationLine
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO = mBiddingNegotiationLineMapper.toDto(mBiddingNegotiationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingNegotiationLineMockMvc.perform(put("/api/m-bidding-negotiation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiationLine in the database
        List<MBiddingNegotiationLine> mBiddingNegotiationLineList = mBiddingNegotiationLineRepository.findAll();
        assertThat(mBiddingNegotiationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingNegotiationLine() throws Exception {
        // Initialize the database
        mBiddingNegotiationLineRepository.saveAndFlush(mBiddingNegotiationLine);

        int databaseSizeBeforeDelete = mBiddingNegotiationLineRepository.findAll().size();

        // Delete the mBiddingNegotiationLine
        restMBiddingNegotiationLineMockMvc.perform(delete("/api/m-bidding-negotiation-lines/{id}", mBiddingNegotiationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingNegotiationLine> mBiddingNegotiationLineList = mBiddingNegotiationLineRepository.findAll();
        assertThat(mBiddingNegotiationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
