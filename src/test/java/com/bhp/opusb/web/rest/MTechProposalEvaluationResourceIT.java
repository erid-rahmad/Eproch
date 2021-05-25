package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MTechProposalEvaluation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.CEvaluationMethodCriteria;
import com.bhp.opusb.domain.CEvalMethodSubCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MTechProposalEvaluationRepository;
import com.bhp.opusb.service.MTechProposalEvaluationService;
import com.bhp.opusb.service.dto.MTechProposalEvaluationDTO;
import com.bhp.opusb.service.mapper.MTechProposalEvaluationMapper;
import com.bhp.opusb.service.dto.MTechProposalEvaluationCriteria;
import com.bhp.opusb.service.MTechProposalEvaluationQueryService;

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
 * Integration tests for the {@link MTechProposalEvaluationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MTechProposalEvaluationResourceIT {

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVERAGE_SCORE = 1;
    private static final Integer UPDATED_AVERAGE_SCORE = 2;
    private static final Integer SMALLER_AVERAGE_SCORE = 1 - 1;

    private static final String DEFAULT_PASS_FAIL = "AAAAAAAAAA";
    private static final String UPDATED_PASS_FAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_REQUIREMENT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MTechProposalEvaluationRepository mTechProposalEvaluationRepository;

    @Autowired
    private MTechProposalEvaluationMapper mTechProposalEvaluationMapper;

    @Autowired
    private MTechProposalEvaluationService mTechProposalEvaluationService;

    @Autowired
    private MTechProposalEvaluationQueryService mTechProposalEvaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMTechProposalEvaluationMockMvc;

    private MTechProposalEvaluation mTechProposalEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MTechProposalEvaluation createEntity(EntityManager em) {
        MTechProposalEvaluation mTechProposalEvaluation = new MTechProposalEvaluation()
            .notes(DEFAULT_NOTES)
            .evaluation(DEFAULT_EVALUATION)
            .averageScore(DEFAULT_AVERAGE_SCORE)
            .passFail(DEFAULT_PASS_FAIL)
            .requirement(DEFAULT_REQUIREMENT)
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
        mTechProposalEvaluation.setAdOrganization(aDOrganization);
        return mTechProposalEvaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MTechProposalEvaluation createUpdatedEntity(EntityManager em) {
        MTechProposalEvaluation mTechProposalEvaluation = new MTechProposalEvaluation()
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
            .requirement(UPDATED_REQUIREMENT)
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
        mTechProposalEvaluation.setAdOrganization(aDOrganization);
        return mTechProposalEvaluation;
    }

    @BeforeEach
    public void initTest() {
        mTechProposalEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMTechProposalEvaluation() throws Exception {
        int databaseSizeBeforeCreate = mTechProposalEvaluationRepository.findAll().size();

        // Create the MTechProposalEvaluation
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = mTechProposalEvaluationMapper.toDto(mTechProposalEvaluation);
        restMTechProposalEvaluationMockMvc.perform(post("/api/m-tech-proposal-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mTechProposalEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the MTechProposalEvaluation in the database
        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        MTechProposalEvaluation testMTechProposalEvaluation = mTechProposalEvaluationList.get(mTechProposalEvaluationList.size() - 1);
        assertThat(testMTechProposalEvaluation.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMTechProposalEvaluation.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testMTechProposalEvaluation.getAverageScore()).isEqualTo(DEFAULT_AVERAGE_SCORE);
        assertThat(testMTechProposalEvaluation.getPassFail()).isEqualTo(DEFAULT_PASS_FAIL);
        assertThat(testMTechProposalEvaluation.getRequirement()).isEqualTo(DEFAULT_REQUIREMENT);
        assertThat(testMTechProposalEvaluation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMTechProposalEvaluation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMTechProposalEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mTechProposalEvaluationRepository.findAll().size();

        // Create the MTechProposalEvaluation with an existing ID
        mTechProposalEvaluation.setId(1L);
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = mTechProposalEvaluationMapper.toDto(mTechProposalEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMTechProposalEvaluationMockMvc.perform(post("/api/m-tech-proposal-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mTechProposalEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MTechProposalEvaluation in the database
        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = mTechProposalEvaluationRepository.findAll().size();
        // set the field null
        mTechProposalEvaluation.setEvaluation(null);

        // Create the MTechProposalEvaluation, which fails.
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = mTechProposalEvaluationMapper.toDto(mTechProposalEvaluation);

        restMTechProposalEvaluationMockMvc.perform(post("/api/m-tech-proposal-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mTechProposalEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluations() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mTechProposalEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMTechProposalEvaluation() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get the mTechProposalEvaluation
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations/{id}", mTechProposalEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mTechProposalEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.averageScore").value(DEFAULT_AVERAGE_SCORE))
            .andExpect(jsonPath("$.passFail").value(DEFAULT_PASS_FAIL))
            .andExpect(jsonPath("$.requirement").value(DEFAULT_REQUIREMENT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMTechProposalEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        Long id = mTechProposalEvaluation.getId();

        defaultMTechProposalEvaluationShouldBeFound("id.equals=" + id);
        defaultMTechProposalEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultMTechProposalEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMTechProposalEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultMTechProposalEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMTechProposalEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes equals to DEFAULT_NOTES
        defaultMTechProposalEvaluationShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the mTechProposalEvaluationList where notes equals to UPDATED_NOTES
        defaultMTechProposalEvaluationShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes not equals to DEFAULT_NOTES
        defaultMTechProposalEvaluationShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the mTechProposalEvaluationList where notes not equals to UPDATED_NOTES
        defaultMTechProposalEvaluationShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultMTechProposalEvaluationShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the mTechProposalEvaluationList where notes equals to UPDATED_NOTES
        defaultMTechProposalEvaluationShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes is not null
        defaultMTechProposalEvaluationShouldBeFound("notes.specified=true");

        // Get all the mTechProposalEvaluationList where notes is null
        defaultMTechProposalEvaluationShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes contains DEFAULT_NOTES
        defaultMTechProposalEvaluationShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the mTechProposalEvaluationList where notes contains UPDATED_NOTES
        defaultMTechProposalEvaluationShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where notes does not contain DEFAULT_NOTES
        defaultMTechProposalEvaluationShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the mTechProposalEvaluationList where notes does not contain UPDATED_NOTES
        defaultMTechProposalEvaluationShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation equals to DEFAULT_EVALUATION
        defaultMTechProposalEvaluationShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the mTechProposalEvaluationList where evaluation equals to UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation not equals to DEFAULT_EVALUATION
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the mTechProposalEvaluationList where evaluation not equals to UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the mTechProposalEvaluationList where evaluation equals to UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation is not null
        defaultMTechProposalEvaluationShouldBeFound("evaluation.specified=true");

        // Get all the mTechProposalEvaluationList where evaluation is null
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation contains DEFAULT_EVALUATION
        defaultMTechProposalEvaluationShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the mTechProposalEvaluationList where evaluation contains UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where evaluation does not contain DEFAULT_EVALUATION
        defaultMTechProposalEvaluationShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the mTechProposalEvaluationList where evaluation does not contain UPDATED_EVALUATION
        defaultMTechProposalEvaluationShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore equals to DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.equals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.equals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore not equals to DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.notEquals=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore not equals to UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.notEquals=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore in DEFAULT_AVERAGE_SCORE or UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.in=" + DEFAULT_AVERAGE_SCORE + "," + UPDATED_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore equals to UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.in=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore is not null
        defaultMTechProposalEvaluationShouldBeFound("averageScore.specified=true");

        // Get all the mTechProposalEvaluationList where averageScore is null
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore is greater than or equal to DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.greaterThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore is greater than or equal to UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.greaterThanOrEqual=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore is less than or equal to DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.lessThanOrEqual=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore is less than or equal to SMALLER_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.lessThanOrEqual=" + SMALLER_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore is less than DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.lessThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore is less than UPDATED_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.lessThan=" + UPDATED_AVERAGE_SCORE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAverageScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where averageScore is greater than DEFAULT_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldNotBeFound("averageScore.greaterThan=" + DEFAULT_AVERAGE_SCORE);

        // Get all the mTechProposalEvaluationList where averageScore is greater than SMALLER_AVERAGE_SCORE
        defaultMTechProposalEvaluationShouldBeFound("averageScore.greaterThan=" + SMALLER_AVERAGE_SCORE);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail equals to DEFAULT_PASS_FAIL
        defaultMTechProposalEvaluationShouldBeFound("passFail.equals=" + DEFAULT_PASS_FAIL);

        // Get all the mTechProposalEvaluationList where passFail equals to UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.equals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail not equals to DEFAULT_PASS_FAIL
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.notEquals=" + DEFAULT_PASS_FAIL);

        // Get all the mTechProposalEvaluationList where passFail not equals to UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldBeFound("passFail.notEquals=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail in DEFAULT_PASS_FAIL or UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldBeFound("passFail.in=" + DEFAULT_PASS_FAIL + "," + UPDATED_PASS_FAIL);

        // Get all the mTechProposalEvaluationList where passFail equals to UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.in=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail is not null
        defaultMTechProposalEvaluationShouldBeFound("passFail.specified=true");

        // Get all the mTechProposalEvaluationList where passFail is null
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.specified=false");
    }
                @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail contains DEFAULT_PASS_FAIL
        defaultMTechProposalEvaluationShouldBeFound("passFail.contains=" + DEFAULT_PASS_FAIL);

        // Get all the mTechProposalEvaluationList where passFail contains UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.contains=" + UPDATED_PASS_FAIL);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByPassFailNotContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where passFail does not contain DEFAULT_PASS_FAIL
        defaultMTechProposalEvaluationShouldNotBeFound("passFail.doesNotContain=" + DEFAULT_PASS_FAIL);

        // Get all the mTechProposalEvaluationList where passFail does not contain UPDATED_PASS_FAIL
        defaultMTechProposalEvaluationShouldBeFound("passFail.doesNotContain=" + UPDATED_PASS_FAIL);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement equals to DEFAULT_REQUIREMENT
        defaultMTechProposalEvaluationShouldBeFound("requirement.equals=" + DEFAULT_REQUIREMENT);

        // Get all the mTechProposalEvaluationList where requirement equals to UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.equals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement not equals to DEFAULT_REQUIREMENT
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.notEquals=" + DEFAULT_REQUIREMENT);

        // Get all the mTechProposalEvaluationList where requirement not equals to UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldBeFound("requirement.notEquals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement in DEFAULT_REQUIREMENT or UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldBeFound("requirement.in=" + DEFAULT_REQUIREMENT + "," + UPDATED_REQUIREMENT);

        // Get all the mTechProposalEvaluationList where requirement equals to UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.in=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement is not null
        defaultMTechProposalEvaluationShouldBeFound("requirement.specified=true");

        // Get all the mTechProposalEvaluationList where requirement is null
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.specified=false");
    }
                @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement contains DEFAULT_REQUIREMENT
        defaultMTechProposalEvaluationShouldBeFound("requirement.contains=" + DEFAULT_REQUIREMENT);

        // Get all the mTechProposalEvaluationList where requirement contains UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.contains=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByRequirementNotContainsSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where requirement does not contain DEFAULT_REQUIREMENT
        defaultMTechProposalEvaluationShouldNotBeFound("requirement.doesNotContain=" + DEFAULT_REQUIREMENT);

        // Get all the mTechProposalEvaluationList where requirement does not contain UPDATED_REQUIREMENT
        defaultMTechProposalEvaluationShouldBeFound("requirement.doesNotContain=" + UPDATED_REQUIREMENT);
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where uid equals to DEFAULT_UID
        defaultMTechProposalEvaluationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mTechProposalEvaluationList where uid equals to UPDATED_UID
        defaultMTechProposalEvaluationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where uid not equals to DEFAULT_UID
        defaultMTechProposalEvaluationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mTechProposalEvaluationList where uid not equals to UPDATED_UID
        defaultMTechProposalEvaluationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMTechProposalEvaluationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mTechProposalEvaluationList where uid equals to UPDATED_UID
        defaultMTechProposalEvaluationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where uid is not null
        defaultMTechProposalEvaluationShouldBeFound("uid.specified=true");

        // Get all the mTechProposalEvaluationList where uid is null
        defaultMTechProposalEvaluationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where active equals to DEFAULT_ACTIVE
        defaultMTechProposalEvaluationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mTechProposalEvaluationList where active equals to UPDATED_ACTIVE
        defaultMTechProposalEvaluationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where active not equals to DEFAULT_ACTIVE
        defaultMTechProposalEvaluationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mTechProposalEvaluationList where active not equals to UPDATED_ACTIVE
        defaultMTechProposalEvaluationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMTechProposalEvaluationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mTechProposalEvaluationList where active equals to UPDATED_ACTIVE
        defaultMTechProposalEvaluationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        // Get all the mTechProposalEvaluationList where active is not null
        defaultMTechProposalEvaluationShouldBeFound("active.specified=true");

        // Get all the mTechProposalEvaluationList where active is null
        defaultMTechProposalEvaluationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mTechProposalEvaluation.getAdOrganization();
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mTechProposalEvaluationList where adOrganization equals to adOrganizationId
        defaultMTechProposalEvaluationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mTechProposalEvaluationList where adOrganization equals to adOrganizationId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByBiddingIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        MBidding bidding = MBiddingResourceIT.createEntity(em);
        em.persist(bidding);
        em.flush();
        mTechProposalEvaluation.setBidding(bidding);
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long biddingId = bidding.getId();

        // Get all the mTechProposalEvaluationList where bidding equals to biddingId
        defaultMTechProposalEvaluationShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mTechProposalEvaluationList where bidding equals to biddingId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvaluationMethodCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        CEvaluationMethodCriteria evaluationMethodCriteria = CEvaluationMethodCriteriaResourceIT.createEntity(em);
        em.persist(evaluationMethodCriteria);
        em.flush();
        mTechProposalEvaluation.setEvaluationMethodCriteria(evaluationMethodCriteria);
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long evaluationMethodCriteriaId = evaluationMethodCriteria.getId();

        // Get all the mTechProposalEvaluationList where evaluationMethodCriteria equals to evaluationMethodCriteriaId
        defaultMTechProposalEvaluationShouldBeFound("evaluationMethodCriteriaId.equals=" + evaluationMethodCriteriaId);

        // Get all the mTechProposalEvaluationList where evaluationMethodCriteria equals to evaluationMethodCriteriaId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("evaluationMethodCriteriaId.equals=" + (evaluationMethodCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByEvalMethodSubCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        CEvalMethodSubCriteria evalMethodSubCriteria = CEvalMethodSubCriteriaResourceIT.createEntity(em);
        em.persist(evalMethodSubCriteria);
        em.flush();
        mTechProposalEvaluation.setEvalMethodSubCriteria(evalMethodSubCriteria);
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long evalMethodSubCriteriaId = evalMethodSubCriteria.getId();

        // Get all the mTechProposalEvaluationList where evalMethodSubCriteria equals to evalMethodSubCriteriaId
        defaultMTechProposalEvaluationShouldBeFound("evalMethodSubCriteriaId.equals=" + evalMethodSubCriteriaId);

        // Get all the mTechProposalEvaluationList where evalMethodSubCriteria equals to evalMethodSubCriteriaId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("evalMethodSubCriteriaId.equals=" + (evalMethodSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        CBiddingSubCriteriaLine biddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
        em.persist(biddingSubCriteriaLine);
        em.flush();
        mTechProposalEvaluation.setBiddingSubCriteriaLine(biddingSubCriteriaLine);
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mTechProposalEvaluationList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMTechProposalEvaluationShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mTechProposalEvaluationList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMTechProposalEvaluationsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        mTechProposalEvaluation.setVendor(vendor);
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);
        Long vendorId = vendor.getId();

        // Get all the mTechProposalEvaluationList where vendor equals to vendorId
        defaultMTechProposalEvaluationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mTechProposalEvaluationList where vendor equals to vendorId + 1
        defaultMTechProposalEvaluationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMTechProposalEvaluationShouldBeFound(String filter) throws Exception {
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mTechProposalEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].averageScore").value(hasItem(DEFAULT_AVERAGE_SCORE)))
            .andExpect(jsonPath("$.[*].passFail").value(hasItem(DEFAULT_PASS_FAIL)))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMTechProposalEvaluationShouldNotBeFound(String filter) throws Exception {
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMTechProposalEvaluation() throws Exception {
        // Get the mTechProposalEvaluation
        restMTechProposalEvaluationMockMvc.perform(get("/api/m-tech-proposal-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMTechProposalEvaluation() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        int databaseSizeBeforeUpdate = mTechProposalEvaluationRepository.findAll().size();

        // Update the mTechProposalEvaluation
        MTechProposalEvaluation updatedMTechProposalEvaluation = mTechProposalEvaluationRepository.findById(mTechProposalEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedMTechProposalEvaluation are not directly saved in db
        em.detach(updatedMTechProposalEvaluation);
        updatedMTechProposalEvaluation
            .notes(UPDATED_NOTES)
            .evaluation(UPDATED_EVALUATION)
            .averageScore(UPDATED_AVERAGE_SCORE)
            .passFail(UPDATED_PASS_FAIL)
            .requirement(UPDATED_REQUIREMENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = mTechProposalEvaluationMapper.toDto(updatedMTechProposalEvaluation);

        restMTechProposalEvaluationMockMvc.perform(put("/api/m-tech-proposal-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mTechProposalEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the MTechProposalEvaluation in the database
        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeUpdate);
        MTechProposalEvaluation testMTechProposalEvaluation = mTechProposalEvaluationList.get(mTechProposalEvaluationList.size() - 1);
        assertThat(testMTechProposalEvaluation.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMTechProposalEvaluation.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testMTechProposalEvaluation.getAverageScore()).isEqualTo(UPDATED_AVERAGE_SCORE);
        assertThat(testMTechProposalEvaluation.getPassFail()).isEqualTo(UPDATED_PASS_FAIL);
        assertThat(testMTechProposalEvaluation.getRequirement()).isEqualTo(UPDATED_REQUIREMENT);
        assertThat(testMTechProposalEvaluation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMTechProposalEvaluation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMTechProposalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = mTechProposalEvaluationRepository.findAll().size();

        // Create the MTechProposalEvaluation
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = mTechProposalEvaluationMapper.toDto(mTechProposalEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMTechProposalEvaluationMockMvc.perform(put("/api/m-tech-proposal-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mTechProposalEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MTechProposalEvaluation in the database
        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMTechProposalEvaluation() throws Exception {
        // Initialize the database
        mTechProposalEvaluationRepository.saveAndFlush(mTechProposalEvaluation);

        int databaseSizeBeforeDelete = mTechProposalEvaluationRepository.findAll().size();

        // Delete the mTechProposalEvaluation
        restMTechProposalEvaluationMockMvc.perform(delete("/api/m-tech-proposal-evaluations/{id}", mTechProposalEvaluation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MTechProposalEvaluation> mTechProposalEvaluationList = mTechProposalEvaluationRepository.findAll();
        assertThat(mTechProposalEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
