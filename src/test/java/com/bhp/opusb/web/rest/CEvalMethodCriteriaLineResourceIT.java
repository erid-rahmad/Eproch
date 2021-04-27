package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvalMethodCriteriaLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.repository.CEvalMethodCriteriaLineRepository;
import com.bhp.opusb.service.CEvalMethodCriteriaLineService;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CEvalMethodCriteriaLineMapper;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineCriteria;
import com.bhp.opusb.service.CEvalMethodCriteriaLineQueryService;

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
 * Integration tests for the {@link CEvalMethodCriteriaLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvalMethodCriteriaLineResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvalMethodCriteriaLineRepository cEvalMethodCriteriaLineRepository;

    @Autowired
    private CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper;

    @Autowired
    private CEvalMethodCriteriaLineService cEvalMethodCriteriaLineService;

    @Autowired
    private CEvalMethodCriteriaLineQueryService cEvalMethodCriteriaLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvalMethodCriteriaLineMockMvc;

    private CEvalMethodCriteriaLine cEvalMethodCriteriaLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvalMethodCriteriaLine createEntity(EntityManager em) {
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine = new CEvalMethodCriteriaLine()
            .weight(DEFAULT_WEIGHT)
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
        cEvalMethodCriteriaLine.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvalMethodCriteriaLine.setBiddingCriteria(cBiddingCriteria);
        return cEvalMethodCriteriaLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvalMethodCriteriaLine createUpdatedEntity(EntityManager em) {
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine = new CEvalMethodCriteriaLine()
            .weight(UPDATED_WEIGHT)
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
        cEvalMethodCriteriaLine.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cEvalMethodCriteriaLine.setBiddingCriteria(cBiddingCriteria);
        return cEvalMethodCriteriaLine;
    }

    @BeforeEach
    public void initTest() {
        cEvalMethodCriteriaLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvalMethodCriteriaLine() throws Exception {
        int databaseSizeBeforeCreate = cEvalMethodCriteriaLineRepository.findAll().size();

        // Create the CEvalMethodCriteriaLine
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO = cEvalMethodCriteriaLineMapper.toDto(cEvalMethodCriteriaLine);
        restCEvalMethodCriteriaLineMockMvc.perform(post("/api/c-eval-method-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodCriteriaLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvalMethodCriteriaLine in the database
        List<CEvalMethodCriteriaLine> cEvalMethodCriteriaLineList = cEvalMethodCriteriaLineRepository.findAll();
        assertThat(cEvalMethodCriteriaLineList).hasSize(databaseSizeBeforeCreate + 1);
        CEvalMethodCriteriaLine testCEvalMethodCriteriaLine = cEvalMethodCriteriaLineList.get(cEvalMethodCriteriaLineList.size() - 1);
        assertThat(testCEvalMethodCriteriaLine.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCEvalMethodCriteriaLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvalMethodCriteriaLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvalMethodCriteriaLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvalMethodCriteriaLineRepository.findAll().size();

        // Create the CEvalMethodCriteriaLine with an existing ID
        cEvalMethodCriteriaLine.setId(1L);
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO = cEvalMethodCriteriaLineMapper.toDto(cEvalMethodCriteriaLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvalMethodCriteriaLineMockMvc.perform(post("/api/c-eval-method-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodCriteriaLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvalMethodCriteriaLine in the database
        List<CEvalMethodCriteriaLine> cEvalMethodCriteriaLineList = cEvalMethodCriteriaLineRepository.findAll();
        assertThat(cEvalMethodCriteriaLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLines() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvalMethodCriteriaLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvalMethodCriteriaLine() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get the cEvalMethodCriteriaLine
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines/{id}", cEvalMethodCriteriaLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvalMethodCriteriaLine.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvalMethodCriteriaLinesByIdFiltering() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        Long id = cEvalMethodCriteriaLine.getId();

        defaultCEvalMethodCriteriaLineShouldBeFound("id.equals=" + id);
        defaultCEvalMethodCriteriaLineShouldNotBeFound("id.notEquals=" + id);

        defaultCEvalMethodCriteriaLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvalMethodCriteriaLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvalMethodCriteriaLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvalMethodCriteriaLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight equals to DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight equals to UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight not equals to DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight not equals to UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight equals to UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight is not null
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.specified=true");

        // Get all the cEvalMethodCriteriaLineList where weight is null
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight is greater than or equal to UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight is less than or equal to SMALLER_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight is less than DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight is less than UPDATED_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where weight is greater than DEFAULT_WEIGHT
        defaultCEvalMethodCriteriaLineShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cEvalMethodCriteriaLineList where weight is greater than SMALLER_WEIGHT
        defaultCEvalMethodCriteriaLineShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where uid equals to DEFAULT_UID
        defaultCEvalMethodCriteriaLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvalMethodCriteriaLineList where uid equals to UPDATED_UID
        defaultCEvalMethodCriteriaLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where uid not equals to DEFAULT_UID
        defaultCEvalMethodCriteriaLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvalMethodCriteriaLineList where uid not equals to UPDATED_UID
        defaultCEvalMethodCriteriaLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvalMethodCriteriaLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvalMethodCriteriaLineList where uid equals to UPDATED_UID
        defaultCEvalMethodCriteriaLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where uid is not null
        defaultCEvalMethodCriteriaLineShouldBeFound("uid.specified=true");

        // Get all the cEvalMethodCriteriaLineList where uid is null
        defaultCEvalMethodCriteriaLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where active equals to DEFAULT_ACTIVE
        defaultCEvalMethodCriteriaLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvalMethodCriteriaLineList where active equals to UPDATED_ACTIVE
        defaultCEvalMethodCriteriaLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where active not equals to DEFAULT_ACTIVE
        defaultCEvalMethodCriteriaLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvalMethodCriteriaLineList where active not equals to UPDATED_ACTIVE
        defaultCEvalMethodCriteriaLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvalMethodCriteriaLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvalMethodCriteriaLineList where active equals to UPDATED_ACTIVE
        defaultCEvalMethodCriteriaLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        // Get all the cEvalMethodCriteriaLineList where active is not null
        defaultCEvalMethodCriteriaLineShouldBeFound("active.specified=true");

        // Get all the cEvalMethodCriteriaLineList where active is null
        defaultCEvalMethodCriteriaLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvalMethodCriteriaLine.getAdOrganization();
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvalMethodCriteriaLineList where adOrganization equals to adOrganizationId
        defaultCEvalMethodCriteriaLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvalMethodCriteriaLineList where adOrganization equals to adOrganizationId + 1
        defaultCEvalMethodCriteriaLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvalMethodCriteriaLinesByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cEvalMethodCriteriaLine.getBiddingCriteria();
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cEvalMethodCriteriaLineList where biddingCriteria equals to biddingCriteriaId
        defaultCEvalMethodCriteriaLineShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cEvalMethodCriteriaLineList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCEvalMethodCriteriaLineShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvalMethodCriteriaLineShouldBeFound(String filter) throws Exception {
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvalMethodCriteriaLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvalMethodCriteriaLineShouldNotBeFound(String filter) throws Exception {
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvalMethodCriteriaLine() throws Exception {
        // Get the cEvalMethodCriteriaLine
        restCEvalMethodCriteriaLineMockMvc.perform(get("/api/c-eval-method-criteria-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvalMethodCriteriaLine() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        int databaseSizeBeforeUpdate = cEvalMethodCriteriaLineRepository.findAll().size();

        // Update the cEvalMethodCriteriaLine
        CEvalMethodCriteriaLine updatedCEvalMethodCriteriaLine = cEvalMethodCriteriaLineRepository.findById(cEvalMethodCriteriaLine.getId()).get();
        // Disconnect from session so that the updates on updatedCEvalMethodCriteriaLine are not directly saved in db
        em.detach(updatedCEvalMethodCriteriaLine);
        updatedCEvalMethodCriteriaLine
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO = cEvalMethodCriteriaLineMapper.toDto(updatedCEvalMethodCriteriaLine);

        restCEvalMethodCriteriaLineMockMvc.perform(put("/api/c-eval-method-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodCriteriaLineDTO)))
            .andExpect(status().isOk());

        // Validate the CEvalMethodCriteriaLine in the database
        List<CEvalMethodCriteriaLine> cEvalMethodCriteriaLineList = cEvalMethodCriteriaLineRepository.findAll();
        assertThat(cEvalMethodCriteriaLineList).hasSize(databaseSizeBeforeUpdate);
        CEvalMethodCriteriaLine testCEvalMethodCriteriaLine = cEvalMethodCriteriaLineList.get(cEvalMethodCriteriaLineList.size() - 1);
        assertThat(testCEvalMethodCriteriaLine.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCEvalMethodCriteriaLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvalMethodCriteriaLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvalMethodCriteriaLine() throws Exception {
        int databaseSizeBeforeUpdate = cEvalMethodCriteriaLineRepository.findAll().size();

        // Create the CEvalMethodCriteriaLine
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO = cEvalMethodCriteriaLineMapper.toDto(cEvalMethodCriteriaLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvalMethodCriteriaLineMockMvc.perform(put("/api/c-eval-method-criteria-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvalMethodCriteriaLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvalMethodCriteriaLine in the database
        List<CEvalMethodCriteriaLine> cEvalMethodCriteriaLineList = cEvalMethodCriteriaLineRepository.findAll();
        assertThat(cEvalMethodCriteriaLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvalMethodCriteriaLine() throws Exception {
        // Initialize the database
        cEvalMethodCriteriaLineRepository.saveAndFlush(cEvalMethodCriteriaLine);

        int databaseSizeBeforeDelete = cEvalMethodCriteriaLineRepository.findAll().size();

        // Delete the cEvalMethodCriteriaLine
        restCEvalMethodCriteriaLineMockMvc.perform(delete("/api/c-eval-method-criteria-lines/{id}", cEvalMethodCriteriaLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvalMethodCriteriaLine> cEvalMethodCriteriaLineList = cEvalMethodCriteriaLineRepository.findAll();
        assertThat(cEvalMethodCriteriaLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
