package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvaluationCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.CEvaluationCriteriaRepository;
import com.bhp.opusb.service.CEvaluationCriteriaService;
import com.bhp.opusb.service.dto.CEvaluationCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvaluationCriteriaMapper;
import com.bhp.opusb.service.dto.CEvaluationCriteriaCriteria;
import com.bhp.opusb.service.CEvaluationCriteriaQueryService;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CEvaluationCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvaluationCriteriaResourceIT {

    private static final BigDecimal DEFAULT_SCORING_PERCENTAGE = new BigDecimal(100);
    private static final BigDecimal UPDATED_SCORING_PERCENTAGE = new BigDecimal(99);
    private static final BigDecimal SMALLER_SCORING_PERCENTAGE = new BigDecimal(100 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvaluationCriteriaRepository cEvaluationCriteriaRepository;

    @Autowired
    private CEvaluationCriteriaMapper cEvaluationCriteriaMapper;

    @Autowired
    private CEvaluationCriteriaService cEvaluationCriteriaService;

    @Autowired
    private CEvaluationCriteriaQueryService cEvaluationCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvaluationCriteriaMockMvc;

    private CEvaluationCriteria cEvaluationCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationCriteria createEntity(EntityManager em) {
        CEvaluationCriteria cEvaluationCriteria = new CEvaluationCriteria()
            .scoringPercentage(DEFAULT_SCORING_PERCENTAGE)
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
        cEvaluationCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        cEvaluationCriteria.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvaluationCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cEvaluationCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        cEvaluationCriteria.setPic(adUser);
        return cEvaluationCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationCriteria createUpdatedEntity(EntityManager em) {
        CEvaluationCriteria cEvaluationCriteria = new CEvaluationCriteria()
            .scoringPercentage(UPDATED_SCORING_PERCENTAGE)
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
        cEvaluationCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CEvaluationMethodLine cEvaluationMethodLine;
        if (TestUtil.findAll(em, CEvaluationMethodLine.class).isEmpty()) {
            cEvaluationMethodLine = CEvaluationMethodLineResourceIT.createUpdatedEntity(em);
            em.persist(cEvaluationMethodLine);
            em.flush();
        } else {
            cEvaluationMethodLine = TestUtil.findAll(em, CEvaluationMethodLine.class).get(0);
        }
        cEvaluationCriteria.setEvaluationMethodLine(cEvaluationMethodLine);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvaluationCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cEvaluationCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        cEvaluationCriteria.setPic(adUser);
        return cEvaluationCriteria;
    }

    @BeforeEach
    public void initTest() {
        cEvaluationCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvaluationCriteria() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationCriteriaRepository.findAll().size();

        // Create the CEvaluationCriteria
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO = cEvaluationCriteriaMapper.toDto(cEvaluationCriteria);
        restCEvaluationCriteriaMockMvc.perform(post("/api/c-evaluation-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvaluationCriteria in the database
        List<CEvaluationCriteria> cEvaluationCriteriaList = cEvaluationCriteriaRepository.findAll();
        assertThat(cEvaluationCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CEvaluationCriteria testCEvaluationCriteria = cEvaluationCriteriaList.get(cEvaluationCriteriaList.size() - 1);
        assertThat(testCEvaluationCriteria.getScoringPercentage()).isEqualTo(DEFAULT_SCORING_PERCENTAGE);
        assertThat(testCEvaluationCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvaluationCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvaluationCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationCriteriaRepository.findAll().size();

        // Create the CEvaluationCriteria with an existing ID
        cEvaluationCriteria.setId(1L);
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO = cEvaluationCriteriaMapper.toDto(cEvaluationCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvaluationCriteriaMockMvc.perform(post("/api/c-evaluation-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationCriteria in the database
        List<CEvaluationCriteria> cEvaluationCriteriaList = cEvaluationCriteriaRepository.findAll();
        assertThat(cEvaluationCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteria() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoringPercentage").value(hasItem(DEFAULT_SCORING_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvaluationCriteria() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get the cEvaluationCriteria
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria/{id}", cEvaluationCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvaluationCriteria.getId().intValue()))
            .andExpect(jsonPath("$.scoringPercentage").value(DEFAULT_SCORING_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvaluationCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        Long id = cEvaluationCriteria.getId();

        defaultCEvaluationCriteriaShouldBeFound("id.equals=" + id);
        defaultCEvaluationCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCEvaluationCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvaluationCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvaluationCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvaluationCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage equals to DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.equals=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage equals to UPDATED_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.equals=" + UPDATED_SCORING_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage not equals to DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.notEquals=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage not equals to UPDATED_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.notEquals=" + UPDATED_SCORING_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage in DEFAULT_SCORING_PERCENTAGE or UPDATED_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.in=" + DEFAULT_SCORING_PERCENTAGE + "," + UPDATED_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage equals to UPDATED_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.in=" + UPDATED_SCORING_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage is not null
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.specified=true");

        // Get all the cEvaluationCriteriaList where scoringPercentage is null
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage is greater than or equal to DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.greaterThanOrEqual=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage is greater than or equal to (DEFAULT_SCORING_PERCENTAGE.add(BigDecimal.ONE))
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.greaterThanOrEqual=" + (DEFAULT_SCORING_PERCENTAGE.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage is less than or equal to DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.lessThanOrEqual=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage is less than or equal to SMALLER_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.lessThanOrEqual=" + SMALLER_SCORING_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage is less than DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.lessThan=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage is less than (DEFAULT_SCORING_PERCENTAGE.add(BigDecimal.ONE))
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.lessThan=" + (DEFAULT_SCORING_PERCENTAGE.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByScoringPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where scoringPercentage is greater than DEFAULT_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldNotBeFound("scoringPercentage.greaterThan=" + DEFAULT_SCORING_PERCENTAGE);

        // Get all the cEvaluationCriteriaList where scoringPercentage is greater than SMALLER_SCORING_PERCENTAGE
        defaultCEvaluationCriteriaShouldBeFound("scoringPercentage.greaterThan=" + SMALLER_SCORING_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where uid equals to DEFAULT_UID
        defaultCEvaluationCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvaluationCriteriaList where uid equals to UPDATED_UID
        defaultCEvaluationCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where uid not equals to DEFAULT_UID
        defaultCEvaluationCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvaluationCriteriaList where uid not equals to UPDATED_UID
        defaultCEvaluationCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvaluationCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvaluationCriteriaList where uid equals to UPDATED_UID
        defaultCEvaluationCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where uid is not null
        defaultCEvaluationCriteriaShouldBeFound("uid.specified=true");

        // Get all the cEvaluationCriteriaList where uid is null
        defaultCEvaluationCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCEvaluationCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvaluationCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCEvaluationCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCEvaluationCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvaluationCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvaluationCriteriaList where active equals to UPDATED_ACTIVE
        defaultCEvaluationCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        // Get all the cEvaluationCriteriaList where active is not null
        defaultCEvaluationCriteriaShouldBeFound("active.specified=true");

        // Get all the cEvaluationCriteriaList where active is null
        defaultCEvaluationCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvaluationCriteria.getAdOrganization();
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvaluationCriteriaList where adOrganization equals to adOrganizationId
        defaultCEvaluationCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvaluationCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCEvaluationCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByEvaluationMethodLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEvaluationMethodLine evaluationMethodLine = cEvaluationCriteria.getEvaluationMethodLine();
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);
        Long evaluationMethodLineId = evaluationMethodLine.getId();

        // Get all the cEvaluationCriteriaList where evaluationMethodLine equals to evaluationMethodLineId
        defaultCEvaluationCriteriaShouldBeFound("evaluationMethodLineId.equals=" + evaluationMethodLineId);

        // Get all the cEvaluationCriteriaList where evaluationMethodLine equals to evaluationMethodLineId + 1
        defaultCEvaluationCriteriaShouldNotBeFound("evaluationMethodLineId.equals=" + (evaluationMethodLineId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cEvaluationCriteria.getBiddingCriteria();
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cEvaluationCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCEvaluationCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cEvaluationCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCEvaluationCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = cEvaluationCriteria.getBiddingSubCriteria();
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the cEvaluationCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultCEvaluationCriteriaShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the cEvaluationCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultCEvaluationCriteriaShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationCriteriaByPicIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser pic = cEvaluationCriteria.getPic();
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);
        Long picId = pic.getId();

        // Get all the cEvaluationCriteriaList where pic equals to picId
        defaultCEvaluationCriteriaShouldBeFound("picId.equals=" + picId);

        // Get all the cEvaluationCriteriaList where pic equals to picId + 1
        defaultCEvaluationCriteriaShouldNotBeFound("picId.equals=" + (picId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvaluationCriteriaShouldBeFound(String filter) throws Exception {
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoringPercentage").value(hasItem(DEFAULT_SCORING_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvaluationCriteriaShouldNotBeFound(String filter) throws Exception {
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvaluationCriteria() throws Exception {
        // Get the cEvaluationCriteria
        restCEvaluationCriteriaMockMvc.perform(get("/api/c-evaluation-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvaluationCriteria() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        int databaseSizeBeforeUpdate = cEvaluationCriteriaRepository.findAll().size();

        // Update the cEvaluationCriteria
        CEvaluationCriteria updatedCEvaluationCriteria = cEvaluationCriteriaRepository.findById(cEvaluationCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCEvaluationCriteria are not directly saved in db
        em.detach(updatedCEvaluationCriteria);
        updatedCEvaluationCriteria
            .scoringPercentage(UPDATED_SCORING_PERCENTAGE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO = cEvaluationCriteriaMapper.toDto(updatedCEvaluationCriteria);

        restCEvaluationCriteriaMockMvc.perform(put("/api/c-evaluation-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CEvaluationCriteria in the database
        List<CEvaluationCriteria> cEvaluationCriteriaList = cEvaluationCriteriaRepository.findAll();
        assertThat(cEvaluationCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CEvaluationCriteria testCEvaluationCriteria = cEvaluationCriteriaList.get(cEvaluationCriteriaList.size() - 1);
        assertThat(testCEvaluationCriteria.getScoringPercentage()).isEqualTo(UPDATED_SCORING_PERCENTAGE);
        assertThat(testCEvaluationCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvaluationCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvaluationCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cEvaluationCriteriaRepository.findAll().size();

        // Create the CEvaluationCriteria
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO = cEvaluationCriteriaMapper.toDto(cEvaluationCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvaluationCriteriaMockMvc.perform(put("/api/c-evaluation-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationCriteria in the database
        List<CEvaluationCriteria> cEvaluationCriteriaList = cEvaluationCriteriaRepository.findAll();
        assertThat(cEvaluationCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvaluationCriteria() throws Exception {
        // Initialize the database
        cEvaluationCriteriaRepository.saveAndFlush(cEvaluationCriteria);

        int databaseSizeBeforeDelete = cEvaluationCriteriaRepository.findAll().size();

        // Delete the cEvaluationCriteria
        restCEvaluationCriteriaMockMvc.perform(delete("/api/c-evaluation-criteria/{id}", cEvaluationCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvaluationCriteria> cEvaluationCriteriaList = cEvaluationCriteriaRepository.findAll();
        assertThat(cEvaluationCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
