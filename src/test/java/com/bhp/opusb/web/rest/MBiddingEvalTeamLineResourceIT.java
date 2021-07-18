package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingEvalTeamLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingEvaluationTeam;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MBiddingEvalTeamLineRepository;
import com.bhp.opusb.service.MBiddingEvalTeamLineService;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalTeamLineMapper;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineCriteria;
import com.bhp.opusb.service.MBiddingEvalTeamLineQueryService;

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
 * Integration tests for the {@link MBiddingEvalTeamLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingEvalTeamLineResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    @Autowired
    private MBiddingEvalTeamLineRepository mBiddingEvalTeamLineRepository;

    @Autowired
    private MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper;

    @Autowired
    private MBiddingEvalTeamLineService mBiddingEvalTeamLineService;

    @Autowired
    private MBiddingEvalTeamLineQueryService mBiddingEvalTeamLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingEvalTeamLineMockMvc;

    private MBiddingEvalTeamLine mBiddingEvalTeamLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalTeamLine createEntity(EntityManager em) {
        MBiddingEvalTeamLine mBiddingEvalTeamLine = new MBiddingEvalTeamLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .position(DEFAULT_POSITION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingEvalTeamLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingEvaluationTeam mBiddingEvaluationTeam;
        if (TestUtil.findAll(em, MBiddingEvaluationTeam.class).isEmpty()) {
            mBiddingEvaluationTeam = MBiddingEvaluationTeamResourceIT.createEntity(em);
            em.persist(mBiddingEvaluationTeam);
            em.flush();
        } else {
            mBiddingEvaluationTeam = TestUtil.findAll(em, MBiddingEvaluationTeam.class).get(0);
        }
        mBiddingEvalTeamLine.setEvaluationTeam(mBiddingEvaluationTeam);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mBiddingEvalTeamLine.setAdUser(adUser);
        return mBiddingEvalTeamLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingEvalTeamLine createUpdatedEntity(EntityManager em) {
        MBiddingEvalTeamLine mBiddingEvalTeamLine = new MBiddingEvalTeamLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingEvalTeamLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingEvaluationTeam mBiddingEvaluationTeam;
        if (TestUtil.findAll(em, MBiddingEvaluationTeam.class).isEmpty()) {
            mBiddingEvaluationTeam = MBiddingEvaluationTeamResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingEvaluationTeam);
            em.flush();
        } else {
            mBiddingEvaluationTeam = TestUtil.findAll(em, MBiddingEvaluationTeam.class).get(0);
        }
        mBiddingEvalTeamLine.setEvaluationTeam(mBiddingEvaluationTeam);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mBiddingEvalTeamLine.setAdUser(adUser);
        return mBiddingEvalTeamLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingEvalTeamLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingEvalTeamLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalTeamLineRepository.findAll().size();

        // Create the MBiddingEvalTeamLine
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLine);
        restMBiddingEvalTeamLineMockMvc.perform(post("/api/m-bidding-eval-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalTeamLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingEvalTeamLine in the database
        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingEvalTeamLine testMBiddingEvalTeamLine = mBiddingEvalTeamLineList.get(mBiddingEvalTeamLineList.size() - 1);
        assertThat(testMBiddingEvalTeamLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingEvalTeamLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBiddingEvalTeamLine.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createMBiddingEvalTeamLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingEvalTeamLineRepository.findAll().size();

        // Create the MBiddingEvalTeamLine with an existing ID
        mBiddingEvalTeamLine.setId(1L);
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingEvalTeamLineMockMvc.perform(post("/api/m-bidding-eval-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalTeamLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalTeamLine in the database
        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingEvalTeamLineRepository.findAll().size();
        // set the field null
        mBiddingEvalTeamLine.setPosition(null);

        // Create the MBiddingEvalTeamLine, which fails.
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLine);

        restMBiddingEvalTeamLineMockMvc.perform(post("/api/m-bidding-eval-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalTeamLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLines() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalTeamLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }
    
    @Test
    @Transactional
    public void getMBiddingEvalTeamLine() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get the mBiddingEvalTeamLine
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines/{id}", mBiddingEvalTeamLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingEvalTeamLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }


    @Test
    @Transactional
    public void getMBiddingEvalTeamLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        Long id = mBiddingEvalTeamLine.getId();

        defaultMBiddingEvalTeamLineShouldBeFound("id.equals=" + id);
        defaultMBiddingEvalTeamLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingEvalTeamLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingEvalTeamLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingEvalTeamLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingEvalTeamLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where uid equals to DEFAULT_UID
        defaultMBiddingEvalTeamLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingEvalTeamLineList where uid equals to UPDATED_UID
        defaultMBiddingEvalTeamLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where uid not equals to DEFAULT_UID
        defaultMBiddingEvalTeamLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingEvalTeamLineList where uid not equals to UPDATED_UID
        defaultMBiddingEvalTeamLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingEvalTeamLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingEvalTeamLineList where uid equals to UPDATED_UID
        defaultMBiddingEvalTeamLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where uid is not null
        defaultMBiddingEvalTeamLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingEvalTeamLineList where uid is null
        defaultMBiddingEvalTeamLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingEvalTeamLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalTeamLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalTeamLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingEvalTeamLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingEvalTeamLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingEvalTeamLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingEvalTeamLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingEvalTeamLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingEvalTeamLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where active is not null
        defaultMBiddingEvalTeamLineShouldBeFound("active.specified=true");

        // Get all the mBiddingEvalTeamLineList where active is null
        defaultMBiddingEvalTeamLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position equals to DEFAULT_POSITION
        defaultMBiddingEvalTeamLineShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the mBiddingEvalTeamLineList where position equals to UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position not equals to DEFAULT_POSITION
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the mBiddingEvalTeamLineList where position not equals to UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the mBiddingEvalTeamLineList where position equals to UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position is not null
        defaultMBiddingEvalTeamLineShouldBeFound("position.specified=true");

        // Get all the mBiddingEvalTeamLineList where position is null
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position contains DEFAULT_POSITION
        defaultMBiddingEvalTeamLineShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the mBiddingEvalTeamLineList where position contains UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        // Get all the mBiddingEvalTeamLineList where position does not contain DEFAULT_POSITION
        defaultMBiddingEvalTeamLineShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the mBiddingEvalTeamLineList where position does not contain UPDATED_POSITION
        defaultMBiddingEvalTeamLineShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingEvalTeamLine.getAdOrganization();
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingEvalTeamLineList where adOrganization equals to adOrganizationId
        defaultMBiddingEvalTeamLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingEvalTeamLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingEvalTeamLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByEvaluationTeamIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingEvaluationTeam evaluationTeam = mBiddingEvalTeamLine.getEvaluationTeam();
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);
        Long evaluationTeamId = evaluationTeam.getId();

        // Get all the mBiddingEvalTeamLineList where evaluationTeam equals to evaluationTeamId
        defaultMBiddingEvalTeamLineShouldBeFound("evaluationTeamId.equals=" + evaluationTeamId);

        // Get all the mBiddingEvalTeamLineList where evaluationTeam equals to evaluationTeamId + 1
        defaultMBiddingEvalTeamLineShouldNotBeFound("evaluationTeamId.equals=" + (evaluationTeamId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingEvalTeamLinesByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = mBiddingEvalTeamLine.getAdUser();
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);
        Long adUserId = adUser.getId();

        // Get all the mBiddingEvalTeamLineList where adUser equals to adUserId
        defaultMBiddingEvalTeamLineShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the mBiddingEvalTeamLineList where adUser equals to adUserId + 1
        defaultMBiddingEvalTeamLineShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingEvalTeamLineShouldBeFound(String filter) throws Exception {
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingEvalTeamLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));

        // Check, that the count call also returns 1
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingEvalTeamLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingEvalTeamLine() throws Exception {
        // Get the mBiddingEvalTeamLine
        restMBiddingEvalTeamLineMockMvc.perform(get("/api/m-bidding-eval-team-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingEvalTeamLine() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        int databaseSizeBeforeUpdate = mBiddingEvalTeamLineRepository.findAll().size();

        // Update the mBiddingEvalTeamLine
        MBiddingEvalTeamLine updatedMBiddingEvalTeamLine = mBiddingEvalTeamLineRepository.findById(mBiddingEvalTeamLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingEvalTeamLine are not directly saved in db
        em.detach(updatedMBiddingEvalTeamLine);
        updatedMBiddingEvalTeamLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION);
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineMapper.toDto(updatedMBiddingEvalTeamLine);

        restMBiddingEvalTeamLineMockMvc.perform(put("/api/m-bidding-eval-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalTeamLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingEvalTeamLine in the database
        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingEvalTeamLine testMBiddingEvalTeamLine = mBiddingEvalTeamLineList.get(mBiddingEvalTeamLineList.size() - 1);
        assertThat(testMBiddingEvalTeamLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingEvalTeamLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBiddingEvalTeamLine.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingEvalTeamLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingEvalTeamLineRepository.findAll().size();

        // Create the MBiddingEvalTeamLine
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingEvalTeamLineMockMvc.perform(put("/api/m-bidding-eval-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingEvalTeamLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingEvalTeamLine in the database
        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingEvalTeamLine() throws Exception {
        // Initialize the database
        mBiddingEvalTeamLineRepository.saveAndFlush(mBiddingEvalTeamLine);

        int databaseSizeBeforeDelete = mBiddingEvalTeamLineRepository.findAll().size();

        // Delete the mBiddingEvalTeamLine
        restMBiddingEvalTeamLineMockMvc.perform(delete("/api/m-bidding-eval-team-lines/{id}", mBiddingEvalTeamLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingEvalTeamLine> mBiddingEvalTeamLineList = mBiddingEvalTeamLineRepository.findAll();
        assertThat(mBiddingEvalTeamLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
