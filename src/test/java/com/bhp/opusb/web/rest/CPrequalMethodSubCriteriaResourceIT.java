package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalMethodSubCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.CPrequalMethodCriteria;
import com.bhp.opusb.repository.CPrequalMethodSubCriteriaRepository;
import com.bhp.opusb.service.CPrequalMethodSubCriteriaService;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodSubCriteriaMapper;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.CPrequalMethodSubCriteriaQueryService;

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
 * Integration tests for the {@link CPrequalMethodSubCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalMethodSubCriteriaResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalMethodSubCriteriaRepository cPrequalMethodSubCriteriaRepository;

    @Autowired
    private CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper;

    @Autowired
    private CPrequalMethodSubCriteriaService cPrequalMethodSubCriteriaService;

    @Autowired
    private CPrequalMethodSubCriteriaQueryService cPrequalMethodSubCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalMethodSubCriteriaMockMvc;

    private CPrequalMethodSubCriteria cPrequalMethodSubCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodSubCriteria createEntity(EntityManager em) {
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria = new CPrequalMethodSubCriteria()
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
        cPrequalMethodSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        CPrequalMethodCriteria cPrequalMethodCriteria;
        if (TestUtil.findAll(em, CPrequalMethodCriteria.class).isEmpty()) {
            cPrequalMethodCriteria = CPrequalMethodCriteriaResourceIT.createEntity(em);
            em.persist(cPrequalMethodCriteria);
            em.flush();
        } else {
            cPrequalMethodCriteria = TestUtil.findAll(em, CPrequalMethodCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setPrequalMethodCriteria(cPrequalMethodCriteria);
        return cPrequalMethodSubCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodSubCriteria createUpdatedEntity(EntityManager em) {
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria = new CPrequalMethodSubCriteria()
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
        cPrequalMethodSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        CPrequalMethodCriteria cPrequalMethodCriteria;
        if (TestUtil.findAll(em, CPrequalMethodCriteria.class).isEmpty()) {
            cPrequalMethodCriteria = CPrequalMethodCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalMethodCriteria);
            em.flush();
        } else {
            cPrequalMethodCriteria = TestUtil.findAll(em, CPrequalMethodCriteria.class).get(0);
        }
        cPrequalMethodSubCriteria.setPrequalMethodCriteria(cPrequalMethodCriteria);
        return cPrequalMethodSubCriteria;
    }

    @BeforeEach
    public void initTest() {
        cPrequalMethodSubCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalMethodSubCriteria() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodSubCriteriaRepository.findAll().size();

        // Create the CPrequalMethodSubCriteria
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO = cPrequalMethodSubCriteriaMapper.toDto(cPrequalMethodSubCriteria);
        restCPrequalMethodSubCriteriaMockMvc.perform(post("/api/c-prequal-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodSubCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalMethodSubCriteria in the database
        List<CPrequalMethodSubCriteria> cPrequalMethodSubCriteriaList = cPrequalMethodSubCriteriaRepository.findAll();
        assertThat(cPrequalMethodSubCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalMethodSubCriteria testCPrequalMethodSubCriteria = cPrequalMethodSubCriteriaList.get(cPrequalMethodSubCriteriaList.size() - 1);
        assertThat(testCPrequalMethodSubCriteria.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCPrequalMethodSubCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalMethodSubCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalMethodSubCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodSubCriteriaRepository.findAll().size();

        // Create the CPrequalMethodSubCriteria with an existing ID
        cPrequalMethodSubCriteria.setId(1L);
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO = cPrequalMethodSubCriteriaMapper.toDto(cPrequalMethodSubCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalMethodSubCriteriaMockMvc.perform(post("/api/c-prequal-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodSubCriteria in the database
        List<CPrequalMethodSubCriteria> cPrequalMethodSubCriteriaList = cPrequalMethodSubCriteriaRepository.findAll();
        assertThat(cPrequalMethodSubCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalMethodSubCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get the cPrequalMethodSubCriteria
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria/{id}", cPrequalMethodSubCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalMethodSubCriteria.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalMethodSubCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        Long id = cPrequalMethodSubCriteria.getId();

        defaultCPrequalMethodSubCriteriaShouldBeFound("id.equals=" + id);
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalMethodSubCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalMethodSubCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight equals to DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight not equals to DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight not equals to UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight is not null
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.specified=true");

        // Get all the cPrequalMethodSubCriteriaList where weight is null
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight is greater than or equal to UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight is less than or equal to SMALLER_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight is less than DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight is less than UPDATED_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where weight is greater than DEFAULT_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodSubCriteriaList where weight is greater than SMALLER_WEIGHT
        defaultCPrequalMethodSubCriteriaShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where uid equals to DEFAULT_UID
        defaultCPrequalMethodSubCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalMethodSubCriteriaList where uid equals to UPDATED_UID
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where uid not equals to DEFAULT_UID
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalMethodSubCriteriaList where uid not equals to UPDATED_UID
        defaultCPrequalMethodSubCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalMethodSubCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalMethodSubCriteriaList where uid equals to UPDATED_UID
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where uid is not null
        defaultCPrequalMethodSubCriteriaShouldBeFound("uid.specified=true");

        // Get all the cPrequalMethodSubCriteriaList where uid is null
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodSubCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalMethodSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        // Get all the cPrequalMethodSubCriteriaList where active is not null
        defaultCPrequalMethodSubCriteriaShouldBeFound("active.specified=true");

        // Get all the cPrequalMethodSubCriteriaList where active is null
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalMethodSubCriteria.getAdOrganization();
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalMethodSubCriteriaList where adOrganization equals to adOrganizationId
        defaultCPrequalMethodSubCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalMethodSubCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cPrequalMethodSubCriteria.getBiddingCriteria();
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cPrequalMethodSubCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCPrequalMethodSubCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cPrequalMethodSubCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = cPrequalMethodSubCriteria.getBiddingSubCriteria();
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the cPrequalMethodSubCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultCPrequalMethodSubCriteriaShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the cPrequalMethodSubCriteriaList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodSubCriteriaByPrequalMethodCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalMethodCriteria prequalMethodCriteria = cPrequalMethodSubCriteria.getPrequalMethodCriteria();
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);
        Long prequalMethodCriteriaId = prequalMethodCriteria.getId();

        // Get all the cPrequalMethodSubCriteriaList where prequalMethodCriteria equals to prequalMethodCriteriaId
        defaultCPrequalMethodSubCriteriaShouldBeFound("prequalMethodCriteriaId.equals=" + prequalMethodCriteriaId);

        // Get all the cPrequalMethodSubCriteriaList where prequalMethodCriteria equals to prequalMethodCriteriaId + 1
        defaultCPrequalMethodSubCriteriaShouldNotBeFound("prequalMethodCriteriaId.equals=" + (prequalMethodCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalMethodSubCriteriaShouldBeFound(String filter) throws Exception {
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalMethodSubCriteriaShouldNotBeFound(String filter) throws Exception {
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalMethodSubCriteria() throws Exception {
        // Get the cPrequalMethodSubCriteria
        restCPrequalMethodSubCriteriaMockMvc.perform(get("/api/c-prequal-method-sub-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalMethodSubCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        int databaseSizeBeforeUpdate = cPrequalMethodSubCriteriaRepository.findAll().size();

        // Update the cPrequalMethodSubCriteria
        CPrequalMethodSubCriteria updatedCPrequalMethodSubCriteria = cPrequalMethodSubCriteriaRepository.findById(cPrequalMethodSubCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalMethodSubCriteria are not directly saved in db
        em.detach(updatedCPrequalMethodSubCriteria);
        updatedCPrequalMethodSubCriteria
            .weight(UPDATED_WEIGHT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO = cPrequalMethodSubCriteriaMapper.toDto(updatedCPrequalMethodSubCriteria);

        restCPrequalMethodSubCriteriaMockMvc.perform(put("/api/c-prequal-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodSubCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalMethodSubCriteria in the database
        List<CPrequalMethodSubCriteria> cPrequalMethodSubCriteriaList = cPrequalMethodSubCriteriaRepository.findAll();
        assertThat(cPrequalMethodSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CPrequalMethodSubCriteria testCPrequalMethodSubCriteria = cPrequalMethodSubCriteriaList.get(cPrequalMethodSubCriteriaList.size() - 1);
        assertThat(testCPrequalMethodSubCriteria.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCPrequalMethodSubCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalMethodSubCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalMethodSubCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalMethodSubCriteriaRepository.findAll().size();

        // Create the CPrequalMethodSubCriteria
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO = cPrequalMethodSubCriteriaMapper.toDto(cPrequalMethodSubCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalMethodSubCriteriaMockMvc.perform(put("/api/c-prequal-method-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodSubCriteria in the database
        List<CPrequalMethodSubCriteria> cPrequalMethodSubCriteriaList = cPrequalMethodSubCriteriaRepository.findAll();
        assertThat(cPrequalMethodSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalMethodSubCriteria() throws Exception {
        // Initialize the database
        cPrequalMethodSubCriteriaRepository.saveAndFlush(cPrequalMethodSubCriteria);

        int databaseSizeBeforeDelete = cPrequalMethodSubCriteriaRepository.findAll().size();

        // Delete the cPrequalMethodSubCriteria
        restCPrequalMethodSubCriteriaMockMvc.perform(delete("/api/c-prequal-method-sub-criteria/{id}", cPrequalMethodSubCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalMethodSubCriteria> cPrequalMethodSubCriteriaList = cPrequalMethodSubCriteriaRepository.findAll();
        assertThat(cPrequalMethodSubCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
