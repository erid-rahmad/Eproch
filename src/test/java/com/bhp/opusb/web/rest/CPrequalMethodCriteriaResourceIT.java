package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalMethodCriteria;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CPrequalMethodLine;
import com.bhp.opusb.repository.CPrequalMethodCriteriaRepository;
import com.bhp.opusb.service.CPrequalMethodCriteriaService;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodCriteriaMapper;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaCriteria;
import com.bhp.opusb.service.CPrequalMethodCriteriaQueryService;

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
 * Integration tests for the {@link CPrequalMethodCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalMethodCriteriaResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalMethodCriteriaRepository cPrequalMethodCriteriaRepository;

    @Autowired
    private CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper;

    @Autowired
    private CPrequalMethodCriteriaService cPrequalMethodCriteriaService;

    @Autowired
    private CPrequalMethodCriteriaQueryService cPrequalMethodCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalMethodCriteriaMockMvc;

    private CPrequalMethodCriteria cPrequalMethodCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodCriteria createEntity(EntityManager em) {
        CPrequalMethodCriteria cPrequalMethodCriteria = new CPrequalMethodCriteria()
            .weight(DEFAULT_WEIGHT)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cPrequalMethodCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPrequalMethodCriteria.setAdOrganization(aDOrganization);
        return cPrequalMethodCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodCriteria createUpdatedEntity(EntityManager em) {
        CPrequalMethodCriteria cPrequalMethodCriteria = new CPrequalMethodCriteria()
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cPrequalMethodCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPrequalMethodCriteria.setAdOrganization(aDOrganization);
        return cPrequalMethodCriteria;
    }

    @BeforeEach
    public void initTest() {
        cPrequalMethodCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalMethodCriteria() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodCriteriaRepository.findAll().size();

        // Create the CPrequalMethodCriteria
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO = cPrequalMethodCriteriaMapper.toDto(cPrequalMethodCriteria);
        restCPrequalMethodCriteriaMockMvc.perform(post("/api/c-prequal-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalMethodCriteria in the database
        List<CPrequalMethodCriteria> cPrequalMethodCriteriaList = cPrequalMethodCriteriaRepository.findAll();
        assertThat(cPrequalMethodCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalMethodCriteria testCPrequalMethodCriteria = cPrequalMethodCriteriaList.get(cPrequalMethodCriteriaList.size() - 1);
        assertThat(testCPrequalMethodCriteria.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCPrequalMethodCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalMethodCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalMethodCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodCriteriaRepository.findAll().size();

        // Create the CPrequalMethodCriteria with an existing ID
        cPrequalMethodCriteria.setId(1L);
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO = cPrequalMethodCriteriaMapper.toDto(cPrequalMethodCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalMethodCriteriaMockMvc.perform(post("/api/c-prequal-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodCriteria in the database
        List<CPrequalMethodCriteria> cPrequalMethodCriteriaList = cPrequalMethodCriteriaRepository.findAll();
        assertThat(cPrequalMethodCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalMethodCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get the cPrequalMethodCriteria
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria/{id}", cPrequalMethodCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalMethodCriteria.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalMethodCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        Long id = cPrequalMethodCriteria.getId();

        defaultCPrequalMethodCriteriaShouldBeFound("id.equals=" + id);
        defaultCPrequalMethodCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalMethodCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalMethodCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalMethodCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalMethodCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight equals to DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight not equals to DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight not equals to UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight is not null
        defaultCPrequalMethodCriteriaShouldBeFound("weight.specified=true");

        // Get all the cPrequalMethodCriteriaList where weight is null
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight is greater than or equal to UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight is less than or equal to SMALLER_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight is less than DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight is less than UPDATED_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where weight is greater than DEFAULT_WEIGHT
        defaultCPrequalMethodCriteriaShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodCriteriaList where weight is greater than SMALLER_WEIGHT
        defaultCPrequalMethodCriteriaShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where uid equals to DEFAULT_UID
        defaultCPrequalMethodCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalMethodCriteriaList where uid equals to UPDATED_UID
        defaultCPrequalMethodCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where uid not equals to DEFAULT_UID
        defaultCPrequalMethodCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalMethodCriteriaList where uid not equals to UPDATED_UID
        defaultCPrequalMethodCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalMethodCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalMethodCriteriaList where uid equals to UPDATED_UID
        defaultCPrequalMethodCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where uid is not null
        defaultCPrequalMethodCriteriaShouldBeFound("uid.specified=true");

        // Get all the cPrequalMethodCriteriaList where uid is null
        defaultCPrequalMethodCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCPrequalMethodCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodCriteriaList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalMethodCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCPrequalMethodCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalMethodCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalMethodCriteriaList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        // Get all the cPrequalMethodCriteriaList where active is not null
        defaultCPrequalMethodCriteriaShouldBeFound("active.specified=true");

        // Get all the cPrequalMethodCriteriaList where active is null
        defaultCPrequalMethodCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cPrequalMethodCriteria.getBiddingCriteria();
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cPrequalMethodCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCPrequalMethodCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cPrequalMethodCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCPrequalMethodCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalMethodCriteria.getAdOrganization();
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalMethodCriteriaList where adOrganization equals to adOrganizationId
        defaultCPrequalMethodCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalMethodCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalMethodCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodCriteriaByPrequalMethodLineIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);
        CPrequalMethodLine prequalMethodLine = CPrequalMethodLineResourceIT.createEntity(em);
        em.persist(prequalMethodLine);
        em.flush();
        cPrequalMethodCriteria.setPrequalMethodLine(prequalMethodLine);
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);
        Long prequalMethodLineId = prequalMethodLine.getId();

        // Get all the cPrequalMethodCriteriaList where prequalMethodLine equals to prequalMethodLineId
        defaultCPrequalMethodCriteriaShouldBeFound("prequalMethodLineId.equals=" + prequalMethodLineId);

        // Get all the cPrequalMethodCriteriaList where prequalMethodLine equals to prequalMethodLineId + 1
        defaultCPrequalMethodCriteriaShouldNotBeFound("prequalMethodLineId.equals=" + (prequalMethodLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalMethodCriteriaShouldBeFound(String filter) throws Exception {
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalMethodCriteriaShouldNotBeFound(String filter) throws Exception {
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalMethodCriteria() throws Exception {
        // Get the cPrequalMethodCriteria
        restCPrequalMethodCriteriaMockMvc.perform(get("/api/c-prequal-method-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalMethodCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        int databaseSizeBeforeUpdate = cPrequalMethodCriteriaRepository.findAll().size();

        // Update the cPrequalMethodCriteria
        CPrequalMethodCriteria updatedCPrequalMethodCriteria = cPrequalMethodCriteriaRepository.findById(cPrequalMethodCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalMethodCriteria are not directly saved in db
        em.detach(updatedCPrequalMethodCriteria);
        updatedCPrequalMethodCriteria
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO = cPrequalMethodCriteriaMapper.toDto(updatedCPrequalMethodCriteria);

        restCPrequalMethodCriteriaMockMvc.perform(put("/api/c-prequal-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalMethodCriteria in the database
        List<CPrequalMethodCriteria> cPrequalMethodCriteriaList = cPrequalMethodCriteriaRepository.findAll();
        assertThat(cPrequalMethodCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CPrequalMethodCriteria testCPrequalMethodCriteria = cPrequalMethodCriteriaList.get(cPrequalMethodCriteriaList.size() - 1);
        assertThat(testCPrequalMethodCriteria.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCPrequalMethodCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalMethodCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalMethodCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalMethodCriteriaRepository.findAll().size();

        // Create the CPrequalMethodCriteria
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO = cPrequalMethodCriteriaMapper.toDto(cPrequalMethodCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalMethodCriteriaMockMvc.perform(put("/api/c-prequal-method-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodCriteria in the database
        List<CPrequalMethodCriteria> cPrequalMethodCriteriaList = cPrequalMethodCriteriaRepository.findAll();
        assertThat(cPrequalMethodCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalMethodCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodCriteriaRepository.saveAndFlush(cPrequalMethodCriteria);

        int databaseSizeBeforeDelete = cPrequalMethodCriteriaRepository.findAll().size();

        // Delete the cPrequalMethodCriteria
        restCPrequalMethodCriteriaMockMvc.perform(delete("/api/c-prequal-method-criteria/{id}", cPrequalMethodCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalMethodCriteria> cPrequalMethodCriteriaList = cPrequalMethodCriteriaRepository.findAll();
        assertThat(cPrequalMethodCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
