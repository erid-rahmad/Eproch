package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.repository.CBiddingSubCriteriaLineRepository;
import com.bhp.opusb.service.CBiddingSubCriteriaLineService;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaLineMapper;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineCriteria;
import com.bhp.opusb.service.CBiddingSubCriteriaLineQueryService;

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
 * Integration tests for the {@link CBiddingSubCriteriaLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBiddingSubCriteriaLineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBiddingSubCriteriaLineRepository cBiddingSubCriteriaLineRepository;

    @Autowired
    private CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper;

    @Autowired
    private CBiddingSubCriteriaLineService cBiddingSubCriteriaLineService;

    @Autowired
    private CBiddingSubCriteriaLineQueryService cBiddingSubCriteriaLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBiddingSubCriteriaLineMockMvc;

    private CBiddingSubCriteriaLine cBiddingSubCriteriaLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingSubCriteriaLine createEntity(EntityManager em) {
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine = new CBiddingSubCriteriaLine()
            .name(DEFAULT_NAME)
            .score(DEFAULT_SCORE)
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
        cBiddingSubCriteriaLine.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cBiddingSubCriteriaLine.setBiddingSubCriteria(cBiddingSubCriteria);
        return cBiddingSubCriteriaLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingSubCriteriaLine createUpdatedEntity(EntityManager em) {
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine = new CBiddingSubCriteriaLine()
            .name(UPDATED_NAME)
            .score(UPDATED_SCORE)
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
        cBiddingSubCriteriaLine.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cBiddingSubCriteriaLine.setBiddingSubCriteria(cBiddingSubCriteria);
        return cBiddingSubCriteriaLine;
    }

    @BeforeEach
    public void initTest() {
        cBiddingSubCriteriaLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBiddingSubCriteriaLine() throws Exception {
        int databaseSizeBeforeCreate = cBiddingSubCriteriaLineRepository.findAll().size();

        // Create the CBiddingSubCriteriaLine
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLine);
        restCBiddingSubCriteriaLineMockMvc.perform(post("/api/c-bidding-sub-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CBiddingSubCriteriaLine in the database
        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeCreate + 1);
        CBiddingSubCriteriaLine testCBiddingSubCriteriaLine = cBiddingSubCriteriaLineList.get(cBiddingSubCriteriaLineList.size() - 1);
        assertThat(testCBiddingSubCriteriaLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBiddingSubCriteriaLine.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testCBiddingSubCriteriaLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBiddingSubCriteriaLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBiddingSubCriteriaLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBiddingSubCriteriaLineRepository.findAll().size();

        // Create the CBiddingSubCriteriaLine with an existing ID
        cBiddingSubCriteriaLine.setId(1L);
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBiddingSubCriteriaLineMockMvc.perform(post("/api/c-bidding-sub-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingSubCriteriaLine in the database
        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBiddingSubCriteriaLineRepository.findAll().size();
        // set the field null
        cBiddingSubCriteriaLine.setName(null);

        // Create the CBiddingSubCriteriaLine, which fails.
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLine);

        restCBiddingSubCriteriaLineMockMvc.perform(post("/api/c-bidding-sub-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaLineDTO)))
            .andExpect(status().isBadRequest());

        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLines() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingSubCriteriaLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBiddingSubCriteriaLine() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get the cBiddingSubCriteriaLine
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines/{id}", cBiddingSubCriteriaLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBiddingSubCriteriaLine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBiddingSubCriteriaLinesByIdFiltering() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        Long id = cBiddingSubCriteriaLine.getId();

        defaultCBiddingSubCriteriaLineShouldBeFound("id.equals=" + id);
        defaultCBiddingSubCriteriaLineShouldNotBeFound("id.notEquals=" + id);

        defaultCBiddingSubCriteriaLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBiddingSubCriteriaLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCBiddingSubCriteriaLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBiddingSubCriteriaLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name equals to DEFAULT_NAME
        defaultCBiddingSubCriteriaLineShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaLineList where name equals to UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name not equals to DEFAULT_NAME
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaLineList where name not equals to UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBiddingSubCriteriaLineList where name equals to UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name is not null
        defaultCBiddingSubCriteriaLineShouldBeFound("name.specified=true");

        // Get all the cBiddingSubCriteriaLineList where name is null
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name contains DEFAULT_NAME
        defaultCBiddingSubCriteriaLineShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaLineList where name contains UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where name does not contain DEFAULT_NAME
        defaultCBiddingSubCriteriaLineShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaLineList where name does not contain UPDATED_NAME
        defaultCBiddingSubCriteriaLineShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score equals to DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score equals to UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score not equals to DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score not equals to UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score equals to UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score is not null
        defaultCBiddingSubCriteriaLineShouldBeFound("score.specified=true");

        // Get all the cBiddingSubCriteriaLineList where score is null
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score is greater than or equal to DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score is greater than or equal to UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score is less than or equal to DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score is less than or equal to SMALLER_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score is less than DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score is less than UPDATED_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where score is greater than DEFAULT_SCORE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the cBiddingSubCriteriaLineList where score is greater than SMALLER_SCORE
        defaultCBiddingSubCriteriaLineShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where uid equals to DEFAULT_UID
        defaultCBiddingSubCriteriaLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBiddingSubCriteriaLineList where uid equals to UPDATED_UID
        defaultCBiddingSubCriteriaLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where uid not equals to DEFAULT_UID
        defaultCBiddingSubCriteriaLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBiddingSubCriteriaLineList where uid not equals to UPDATED_UID
        defaultCBiddingSubCriteriaLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBiddingSubCriteriaLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBiddingSubCriteriaLineList where uid equals to UPDATED_UID
        defaultCBiddingSubCriteriaLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where uid is not null
        defaultCBiddingSubCriteriaLineShouldBeFound("uid.specified=true");

        // Get all the cBiddingSubCriteriaLineList where uid is null
        defaultCBiddingSubCriteriaLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where active equals to DEFAULT_ACTIVE
        defaultCBiddingSubCriteriaLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingSubCriteriaLineList where active equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where active not equals to DEFAULT_ACTIVE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingSubCriteriaLineList where active not equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBiddingSubCriteriaLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBiddingSubCriteriaLineList where active equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        // Get all the cBiddingSubCriteriaLineList where active is not null
        defaultCBiddingSubCriteriaLineShouldBeFound("active.specified=true");

        // Get all the cBiddingSubCriteriaLineList where active is null
        defaultCBiddingSubCriteriaLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBiddingSubCriteriaLine.getAdOrganization();
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBiddingSubCriteriaLineList where adOrganization equals to adOrganizationId
        defaultCBiddingSubCriteriaLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBiddingSubCriteriaLineList where adOrganization equals to adOrganizationId + 1
        defaultCBiddingSubCriteriaLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaLinesByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = cBiddingSubCriteriaLine.getBiddingSubCriteria();
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the cBiddingSubCriteriaLineList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultCBiddingSubCriteriaLineShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the cBiddingSubCriteriaLineList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultCBiddingSubCriteriaLineShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBiddingSubCriteriaLineShouldBeFound(String filter) throws Exception {
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingSubCriteriaLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBiddingSubCriteriaLineShouldNotBeFound(String filter) throws Exception {
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBiddingSubCriteriaLine() throws Exception {
        // Get the cBiddingSubCriteriaLine
        restCBiddingSubCriteriaLineMockMvc.perform(get("/api/c-bidding-sub-criteria-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBiddingSubCriteriaLine() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        int databaseSizeBeforeUpdate = cBiddingSubCriteriaLineRepository.findAll().size();

        // Update the cBiddingSubCriteriaLine
        CBiddingSubCriteriaLine updatedCBiddingSubCriteriaLine = cBiddingSubCriteriaLineRepository.findById(cBiddingSubCriteriaLine.getId()).get();
        // Disconnect from session so that the updates on updatedCBiddingSubCriteriaLine are not directly saved in db
        em.detach(updatedCBiddingSubCriteriaLine);
        updatedCBiddingSubCriteriaLine
            .name(UPDATED_NAME)
            .score(UPDATED_SCORE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineMapper.toDto(updatedCBiddingSubCriteriaLine);

        restCBiddingSubCriteriaLineMockMvc.perform(put("/api/c-bidding-sub-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaLineDTO)))
            .andExpect(status().isOk());

        // Validate the CBiddingSubCriteriaLine in the database
        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeUpdate);
        CBiddingSubCriteriaLine testCBiddingSubCriteriaLine = cBiddingSubCriteriaLineList.get(cBiddingSubCriteriaLineList.size() - 1);
        assertThat(testCBiddingSubCriteriaLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBiddingSubCriteriaLine.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testCBiddingSubCriteriaLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBiddingSubCriteriaLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBiddingSubCriteriaLine() throws Exception {
        int databaseSizeBeforeUpdate = cBiddingSubCriteriaLineRepository.findAll().size();

        // Create the CBiddingSubCriteriaLine
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBiddingSubCriteriaLineMockMvc.perform(put("/api/c-bidding-sub-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingSubCriteriaLine in the database
        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBiddingSubCriteriaLine() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaLineRepository.saveAndFlush(cBiddingSubCriteriaLine);

        int databaseSizeBeforeDelete = cBiddingSubCriteriaLineRepository.findAll().size();

        // Delete the cBiddingSubCriteriaLine
        restCBiddingSubCriteriaLineMockMvc.perform(delete("/api/c-bidding-sub-criteria-lines/{id}", cBiddingSubCriteriaLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBiddingSubCriteriaLine> cBiddingSubCriteriaLineList = cBiddingSubCriteriaLineRepository.findAll();
        assertThat(cBiddingSubCriteriaLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
