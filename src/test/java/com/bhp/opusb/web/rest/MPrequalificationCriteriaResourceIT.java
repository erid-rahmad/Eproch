package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.CPrequalMethodCriteria;
import com.bhp.opusb.domain.CPrequalMethodSubCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.MPrequalificationCriteriaRepository;
import com.bhp.opusb.service.MPrequalificationCriteriaService;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaDTO;
import com.bhp.opusb.service.mapper.MPrequalificationCriteriaMapper;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaCriteria;
import com.bhp.opusb.service.MPrequalificationCriteriaQueryService;

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
 * Integration tests for the {@link MPrequalificationCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationCriteriaResourceIT {

    private static final String DEFAULT_REQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_REQUIREMENT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationCriteriaRepository mPrequalificationCriteriaRepository;

    @Autowired
    private MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper;

    @Autowired
    private MPrequalificationCriteriaService mPrequalificationCriteriaService;

    @Autowired
    private MPrequalificationCriteriaQueryService mPrequalificationCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationCriteriaMockMvc;

    private MPrequalificationCriteria mPrequalificationCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationCriteria createEntity(EntityManager em) {
        MPrequalificationCriteria mPrequalificationCriteria = new MPrequalificationCriteria()
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
        mPrequalificationCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationCriteria.setPrequalification(mPrequalificationInformation);
        return mPrequalificationCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationCriteria createUpdatedEntity(EntityManager em) {
        MPrequalificationCriteria mPrequalificationCriteria = new MPrequalificationCriteria()
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
        mPrequalificationCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationCriteria.setPrequalification(mPrequalificationInformation);
        return mPrequalificationCriteria;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationCriteria() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationCriteriaRepository.findAll().size();

        // Create the MPrequalificationCriteria
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);
        restMPrequalificationCriteriaMockMvc.perform(post("/api/m-prequalification-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationCriteria in the database
        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationCriteria testMPrequalificationCriteria = mPrequalificationCriteriaList.get(mPrequalificationCriteriaList.size() - 1);
        assertThat(testMPrequalificationCriteria.getRequirement()).isEqualTo(DEFAULT_REQUIREMENT);
        assertThat(testMPrequalificationCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationCriteriaRepository.findAll().size();

        // Create the MPrequalificationCriteria with an existing ID
        mPrequalificationCriteria.setId(1L);
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationCriteriaMockMvc.perform(post("/api/m-prequalification-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationCriteria in the database
        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRequirementIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPrequalificationCriteriaRepository.findAll().size();
        // set the field null
        mPrequalificationCriteria.setRequirement(null);

        // Create the MPrequalificationCriteria, which fails.
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);

        restMPrequalificationCriteriaMockMvc.perform(post("/api/m-prequalification-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationCriteriaDTO)))
            .andExpect(status().isBadRequest());

        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteria() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationCriteria() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get the mPrequalificationCriteria
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria/{id}", mPrequalificationCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationCriteria.getId().intValue()))
            .andExpect(jsonPath("$.requirement").value(DEFAULT_REQUIREMENT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        Long id = mPrequalificationCriteria.getId();

        defaultMPrequalificationCriteriaShouldBeFound("id.equals=" + id);
        defaultMPrequalificationCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement equals to DEFAULT_REQUIREMENT
        defaultMPrequalificationCriteriaShouldBeFound("requirement.equals=" + DEFAULT_REQUIREMENT);

        // Get all the mPrequalificationCriteriaList where requirement equals to UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.equals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement not equals to DEFAULT_REQUIREMENT
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.notEquals=" + DEFAULT_REQUIREMENT);

        // Get all the mPrequalificationCriteriaList where requirement not equals to UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldBeFound("requirement.notEquals=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement in DEFAULT_REQUIREMENT or UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldBeFound("requirement.in=" + DEFAULT_REQUIREMENT + "," + UPDATED_REQUIREMENT);

        // Get all the mPrequalificationCriteriaList where requirement equals to UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.in=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement is not null
        defaultMPrequalificationCriteriaShouldBeFound("requirement.specified=true");

        // Get all the mPrequalificationCriteriaList where requirement is null
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.specified=false");
    }
                @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement contains DEFAULT_REQUIREMENT
        defaultMPrequalificationCriteriaShouldBeFound("requirement.contains=" + DEFAULT_REQUIREMENT);

        // Get all the mPrequalificationCriteriaList where requirement contains UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.contains=" + UPDATED_REQUIREMENT);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByRequirementNotContainsSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where requirement does not contain DEFAULT_REQUIREMENT
        defaultMPrequalificationCriteriaShouldNotBeFound("requirement.doesNotContain=" + DEFAULT_REQUIREMENT);

        // Get all the mPrequalificationCriteriaList where requirement does not contain UPDATED_REQUIREMENT
        defaultMPrequalificationCriteriaShouldBeFound("requirement.doesNotContain=" + UPDATED_REQUIREMENT);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where uid equals to DEFAULT_UID
        defaultMPrequalificationCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationCriteriaList where uid equals to UPDATED_UID
        defaultMPrequalificationCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where uid not equals to DEFAULT_UID
        defaultMPrequalificationCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationCriteriaList where uid not equals to UPDATED_UID
        defaultMPrequalificationCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationCriteriaList where uid equals to UPDATED_UID
        defaultMPrequalificationCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where uid is not null
        defaultMPrequalificationCriteriaShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationCriteriaList where uid is null
        defaultMPrequalificationCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationCriteriaList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationCriteriaList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationCriteriaList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        // Get all the mPrequalificationCriteriaList where active is not null
        defaultMPrequalificationCriteriaShouldBeFound("active.specified=true");

        // Get all the mPrequalificationCriteriaList where active is null
        defaultMPrequalificationCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationCriteria.getAdOrganization();
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationCriteriaList where adOrganization equals to adOrganizationId
        defaultMPrequalificationCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationCriteria.getPrequalification();
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationCriteriaList where prequalification equals to prequalificationId
        defaultMPrequalificationCriteriaShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationCriteriaList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationCriteriaShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByPrequalMethodCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        CPrequalMethodCriteria prequalMethodCriteria = CPrequalMethodCriteriaResourceIT.createEntity(em);
        em.persist(prequalMethodCriteria);
        em.flush();
        mPrequalificationCriteria.setPrequalMethodCriteria(prequalMethodCriteria);
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        Long prequalMethodCriteriaId = prequalMethodCriteria.getId();

        // Get all the mPrequalificationCriteriaList where prequalMethodCriteria equals to prequalMethodCriteriaId
        defaultMPrequalificationCriteriaShouldBeFound("prequalMethodCriteriaId.equals=" + prequalMethodCriteriaId);

        // Get all the mPrequalificationCriteriaList where prequalMethodCriteria equals to prequalMethodCriteriaId + 1
        defaultMPrequalificationCriteriaShouldNotBeFound("prequalMethodCriteriaId.equals=" + (prequalMethodCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByPrequalMethodSubCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        CPrequalMethodSubCriteria prequalMethodSubCriteria = CPrequalMethodSubCriteriaResourceIT.createEntity(em);
        em.persist(prequalMethodSubCriteria);
        em.flush();
        mPrequalificationCriteria.setPrequalMethodSubCriteria(prequalMethodSubCriteria);
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        Long prequalMethodSubCriteriaId = prequalMethodSubCriteria.getId();

        // Get all the mPrequalificationCriteriaList where prequalMethodSubCriteria equals to prequalMethodSubCriteriaId
        defaultMPrequalificationCriteriaShouldBeFound("prequalMethodSubCriteriaId.equals=" + prequalMethodSubCriteriaId);

        // Get all the mPrequalificationCriteriaList where prequalMethodSubCriteria equals to prequalMethodSubCriteriaId + 1
        defaultMPrequalificationCriteriaShouldNotBeFound("prequalMethodSubCriteriaId.equals=" + (prequalMethodSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationCriteriaByBiddingSubCriteriaLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        CBiddingSubCriteriaLine biddingSubCriteriaLine = CBiddingSubCriteriaLineResourceIT.createEntity(em);
        em.persist(biddingSubCriteriaLine);
        em.flush();
        mPrequalificationCriteria.setBiddingSubCriteriaLine(biddingSubCriteriaLine);
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);
        Long biddingSubCriteriaLineId = biddingSubCriteriaLine.getId();

        // Get all the mPrequalificationCriteriaList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId
        defaultMPrequalificationCriteriaShouldBeFound("biddingSubCriteriaLineId.equals=" + biddingSubCriteriaLineId);

        // Get all the mPrequalificationCriteriaList where biddingSubCriteriaLine equals to biddingSubCriteriaLineId + 1
        defaultMPrequalificationCriteriaShouldNotBeFound("biddingSubCriteriaLineId.equals=" + (biddingSubCriteriaLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationCriteriaShouldBeFound(String filter) throws Exception {
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].requirement").value(hasItem(DEFAULT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationCriteriaShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationCriteria() throws Exception {
        // Get the mPrequalificationCriteria
        restMPrequalificationCriteriaMockMvc.perform(get("/api/m-prequalification-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationCriteria() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        int databaseSizeBeforeUpdate = mPrequalificationCriteriaRepository.findAll().size();

        // Update the mPrequalificationCriteria
        MPrequalificationCriteria updatedMPrequalificationCriteria = mPrequalificationCriteriaRepository.findById(mPrequalificationCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationCriteria are not directly saved in db
        em.detach(updatedMPrequalificationCriteria);
        updatedMPrequalificationCriteria
            .requirement(UPDATED_REQUIREMENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = mPrequalificationCriteriaMapper.toDto(updatedMPrequalificationCriteria);

        restMPrequalificationCriteriaMockMvc.perform(put("/api/m-prequalification-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationCriteria in the database
        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationCriteria testMPrequalificationCriteria = mPrequalificationCriteriaList.get(mPrequalificationCriteriaList.size() - 1);
        assertThat(testMPrequalificationCriteria.getRequirement()).isEqualTo(UPDATED_REQUIREMENT);
        assertThat(testMPrequalificationCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationCriteria() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationCriteriaRepository.findAll().size();

        // Create the MPrequalificationCriteria
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationCriteriaMockMvc.perform(put("/api/m-prequalification-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationCriteria in the database
        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationCriteria() throws Exception {
        // Initialize the database
        mPrequalificationCriteriaRepository.saveAndFlush(mPrequalificationCriteria);

        int databaseSizeBeforeDelete = mPrequalificationCriteriaRepository.findAll().size();

        // Delete the mPrequalificationCriteria
        restMPrequalificationCriteriaMockMvc.perform(delete("/api/m-prequalification-criteria/{id}", mPrequalificationCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationCriteria> mPrequalificationCriteriaList = mPrequalificationCriteriaRepository.findAll();
        assertThat(mPrequalificationCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
