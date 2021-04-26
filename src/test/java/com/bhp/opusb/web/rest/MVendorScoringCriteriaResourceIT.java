package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorScoringCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEvalMethodCriteriaLine;
import com.bhp.opusb.domain.CEvalMethodSubCriteria;
import com.bhp.opusb.domain.MVendorScoringLine;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.MVendorScoringCriteriaRepository;
import com.bhp.opusb.service.MVendorScoringCriteriaService;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaDTO;
import com.bhp.opusb.service.mapper.MVendorScoringCriteriaMapper;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaCriteria;
import com.bhp.opusb.service.MVendorScoringCriteriaQueryService;

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
 * Integration tests for the {@link MVendorScoringCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorScoringCriteriaResourceIT {

    private static final String DEFAULT_REQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_REQUIREMENT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorScoringCriteriaRepository mVendorScoringCriteriaRepository;

    @Autowired
    private MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper;

    @Autowired
    private MVendorScoringCriteriaService mVendorScoringCriteriaService;

    @Autowired
    private MVendorScoringCriteriaQueryService mVendorScoringCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorScoringCriteriaMockMvc;

    private MVendorScoringCriteria mVendorScoringCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoringCriteria createEntity(EntityManager em) {
        MVendorScoringCriteria mVendorScoringCriteria = new MVendorScoringCriteria()
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
        mVendorScoringCriteria.setAdOrganization(aDOrganization);
        return mVendorScoringCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoringCriteria createUpdatedEntity(EntityManager em) {
        MVendorScoringCriteria mVendorScoringCriteria = new MVendorScoringCriteria()
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
        mVendorScoringCriteria.setAdOrganization(aDOrganization);
        return mVendorScoringCriteria;
    }

    @BeforeEach
    public void initTest() {
        mVendorScoringCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorScoringCriteria() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringCriteriaRepository.findAll().size();

        // Create the MVendorScoringCriteria
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteria);
        restMVendorScoringCriteriaMockMvc.perform(post("/api/m-vendor-scoring-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorScoringCriteria in the database
        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorScoringCriteria testMVendorScoringCriteria = mVendorScoringCriteriaList.get(mVendorScoringCriteriaList.size() - 1);
        assertThat(testMVendorScoringCriteria.getRequirement()).isEqualTo(DEFAULT_REQUIREMENT);
        assertThat(testMVendorScoringCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorScoringCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorScoringCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringCriteriaRepository.findAll().size();

        // Create the MVendorScoringCriteria with an existing ID
        mVendorScoringCriteria.setId(1L);
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorScoringCriteriaMockMvc.perform(post("/api/m-vendor-scoring-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoringCriteria in the database
        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRequirementIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVendorScoringCriteriaRepository.findAll().size();
        // set the field null
        mVendorScoringCriteria.setRequirement(null);

        // Create the MVendorScoringCriteria, which fails.
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteria);

        restMVendorScoringCriteriaMockMvc.perform(post("/api/m-vendor-scoring-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringCriteriaDTO)))
            .andExpect(status().isBadRequest());

        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteria() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoringCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorScoringCriteria() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get the mVendorScoringCriteria
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria/{id}", mVendorScoringCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorScoringCriteria.getId().intValue()))
            .andExpect(jsonPath("$.requirement").value(DEFAULT_REQUIREMENT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorScoringCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        Long id = mVendorScoringCriteria.getId();

        defaultMVendorScoringCriteriaShouldBeFound("id.equals=" + id);
        defaultMVendorScoringCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorScoringCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorScoringCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorScoringCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorScoringCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement equals to DEFAULT_REQUIREMENT
        defaultMVendorScoringCriteriaShouldBeFound("requirement.equals=" + DEFAULT_REQUIREMENT);

        // Get all the mVendorScoringCriteriaList where requirement equals to UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.equals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement not equals to DEFAULT_REQUIREMENT
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.notEquals=" + DEFAULT_REQUIREMENT);

        // Get all the mVendorScoringCriteriaList where requirement not equals to UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldBeFound("requirement.notEquals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement in DEFAULT_REQUIREMENT or UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldBeFound("requirement.in=" + DEFAULT_REQUIREMENT + "," + UPDATED_REQUIREMENT);

        // Get all the mVendorScoringCriteriaList where requirement equals to UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.in=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement is not null
        defaultMVendorScoringCriteriaShouldBeFound("requirement.specified=true");

        // Get all the mVendorScoringCriteriaList where requirement is null
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementContainsSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement contains DEFAULT_REQUIREMENT
        defaultMVendorScoringCriteriaShouldBeFound("requirement.contains=" + DEFAULT_REQUIREMENT);

        // Get all the mVendorScoringCriteriaList where requirement contains UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.contains=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByRequirementNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where requirement does not contain DEFAULT_REQUIREMENT
        defaultMVendorScoringCriteriaShouldNotBeFound("requirement.doesNotContain=" + DEFAULT_REQUIREMENT);

        // Get all the mVendorScoringCriteriaList where requirement does not contain UPDATED_REQUIREMENT
        defaultMVendorScoringCriteriaShouldBeFound("requirement.doesNotContain=" + UPDATED_REQUIREMENT);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where uid equals to DEFAULT_UID
        defaultMVendorScoringCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorScoringCriteriaList where uid equals to UPDATED_UID
        defaultMVendorScoringCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where uid not equals to DEFAULT_UID
        defaultMVendorScoringCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorScoringCriteriaList where uid not equals to UPDATED_UID
        defaultMVendorScoringCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorScoringCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorScoringCriteriaList where uid equals to UPDATED_UID
        defaultMVendorScoringCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where uid is not null
        defaultMVendorScoringCriteriaShouldBeFound("uid.specified=true");

        // Get all the mVendorScoringCriteriaList where uid is null
        defaultMVendorScoringCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where active equals to DEFAULT_ACTIVE
        defaultMVendorScoringCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringCriteriaList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultMVendorScoringCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringCriteriaList where active not equals to UPDATED_ACTIVE
        defaultMVendorScoringCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorScoringCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorScoringCriteriaList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        // Get all the mVendorScoringCriteriaList where active is not null
        defaultMVendorScoringCriteriaShouldBeFound("active.specified=true");

        // Get all the mVendorScoringCriteriaList where active is null
        defaultMVendorScoringCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorScoringCriteria.getAdOrganization();
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorScoringCriteriaList where adOrganization equals to adOrganizationId
        defaultMVendorScoringCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorScoringCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultMVendorScoringCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByEvalMethodCriteriaLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        CEvalMethodCriteriaLine evalMethodCriteriaLine = CEvalMethodCriteriaLineResourceIT.createEntity(em);
        em.persist(evalMethodCriteriaLine);
        em.flush();
        mVendorScoringCriteria.setEvalMethodCriteriaLine(evalMethodCriteriaLine);
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        Long evalMethodCriteriaLineId = evalMethodCriteriaLine.getId();

        // Get all the mVendorScoringCriteriaList where evalMethodCriteriaLine equals to evalMethodCriteriaLineId
        defaultMVendorScoringCriteriaShouldBeFound("evalMethodCriteriaLineId.equals=" + evalMethodCriteriaLineId);

        // Get all the mVendorScoringCriteriaList where evalMethodCriteriaLine equals to evalMethodCriteriaLineId + 1
        defaultMVendorScoringCriteriaShouldNotBeFound("evalMethodCriteriaLineId.equals=" + (evalMethodCriteriaLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByEvalMethodSubCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        CEvalMethodSubCriteria evalMethodSubCriteria = CEvalMethodSubCriteriaResourceIT.createEntity(em);
        em.persist(evalMethodSubCriteria);
        em.flush();
        mVendorScoringCriteria.setEvalMethodSubCriteria(evalMethodSubCriteria);
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        Long evalMethodSubCriteriaId = evalMethodSubCriteria.getId();

        // Get all the mVendorScoringCriteriaList where evalMethodSubCriteria equals to evalMethodSubCriteriaId
        defaultMVendorScoringCriteriaShouldBeFound("evalMethodSubCriteriaId.equals=" + evalMethodSubCriteriaId);

        // Get all the mVendorScoringCriteriaList where evalMethodSubCriteria equals to evalMethodSubCriteriaId + 1
        defaultMVendorScoringCriteriaShouldNotBeFound("evalMethodSubCriteriaId.equals=" + (evalMethodSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByVendorScoringLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        MVendorScoringLine vendorScoringLine = MVendorScoringLineResourceIT.createEntity(em);
        em.persist(vendorScoringLine);
        em.flush();
        mVendorScoringCriteria.setVendorScoringLine(vendorScoringLine);
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        Long vendorScoringLineId = vendorScoringLine.getId();

        // Get all the mVendorScoringCriteriaList where vendorScoringLine equals to vendorScoringLineId
        defaultMVendorScoringCriteriaShouldBeFound("vendorScoringLineId.equals=" + vendorScoringLineId);

        // Get all the mVendorScoringCriteriaList where vendorScoringLine equals to vendorScoringLineId + 1
        defaultMVendorScoringCriteriaShouldNotBeFound("vendorScoringLineId.equals=" + (vendorScoringLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringCriteriaByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        CBiddingSubCriteriaLine biddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
        em.persist(biddingSubCriteriaLine);
        em.flush();
        mVendorScoringCriteria.setBiddingSubCriteriaLine(biddingSubCriteriaLine);
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mVendorScoringCriteriaList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMVendorScoringCriteriaShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mVendorScoringCriteriaList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMVendorScoringCriteriaShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorScoringCriteriaShouldBeFound(String filter) throws Exception {
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoringCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorScoringCriteriaShouldNotBeFound(String filter) throws Exception {
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorScoringCriteria() throws Exception {
        // Get the mVendorScoringCriteria
        restMVendorScoringCriteriaMockMvc.perform(get("/api/m-vendor-scoring-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorScoringCriteria() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        int databaseSizeBeforeUpdate = mVendorScoringCriteriaRepository.findAll().size();

        // Update the mVendorScoringCriteria
        MVendorScoringCriteria updatedMVendorScoringCriteria = mVendorScoringCriteriaRepository.findById(mVendorScoringCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorScoringCriteria are not directly saved in db
        em.detach(updatedMVendorScoringCriteria);
        updatedMVendorScoringCriteria
            .requirement(UPDATED_REQUIREMENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = mVendorScoringCriteriaMapper.toDto(updatedMVendorScoringCriteria);

        restMVendorScoringCriteriaMockMvc.perform(put("/api/m-vendor-scoring-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorScoringCriteria in the database
        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeUpdate);
        MVendorScoringCriteria testMVendorScoringCriteria = mVendorScoringCriteriaList.get(mVendorScoringCriteriaList.size() - 1);
        assertThat(testMVendorScoringCriteria.getRequirement()).isEqualTo(UPDATED_REQUIREMENT);
        assertThat(testMVendorScoringCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorScoringCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorScoringCriteria() throws Exception {
        int databaseSizeBeforeUpdate = mVendorScoringCriteriaRepository.findAll().size();

        // Create the MVendorScoringCriteria
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorScoringCriteriaMockMvc.perform(put("/api/m-vendor-scoring-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoringCriteria in the database
        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorScoringCriteria() throws Exception {
        // Initialize the database
        mVendorScoringCriteriaRepository.saveAndFlush(mVendorScoringCriteria);

        int databaseSizeBeforeDelete = mVendorScoringCriteriaRepository.findAll().size();

        // Delete the mVendorScoringCriteria
        restMVendorScoringCriteriaMockMvc.perform(delete("/api/m-vendor-scoring-criteria/{id}", mVendorScoringCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorScoringCriteria> mVendorScoringCriteriaList = mVendorScoringCriteriaRepository.findAll();
        assertThat(mVendorScoringCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
