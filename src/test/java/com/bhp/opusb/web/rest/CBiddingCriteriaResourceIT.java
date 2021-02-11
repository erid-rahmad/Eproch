package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CBiddingCriteriaRepository;
import com.bhp.opusb.service.CBiddingCriteriaService;
import com.bhp.opusb.service.dto.CBiddingCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingCriteriaMapper;
import com.bhp.opusb.service.dto.CBiddingCriteriaCriteria;
import com.bhp.opusb.service.CBiddingCriteriaQueryService;

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
 * Integration tests for the {@link CBiddingCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBiddingCriteriaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBiddingCriteriaRepository cBiddingCriteriaRepository;

    @Autowired
    private CBiddingCriteriaMapper cBiddingCriteriaMapper;

    @Autowired
    private CBiddingCriteriaService cBiddingCriteriaService;

    @Autowired
    private CBiddingCriteriaQueryService cBiddingCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBiddingCriteriaMockMvc;

    private CBiddingCriteria cBiddingCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingCriteria createEntity(EntityManager em) {
        CBiddingCriteria cBiddingCriteria = new CBiddingCriteria()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
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
        cBiddingCriteria.setAdOrganization(aDOrganization);
        return cBiddingCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingCriteria createUpdatedEntity(EntityManager em) {
        CBiddingCriteria cBiddingCriteria = new CBiddingCriteria()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
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
        cBiddingCriteria.setAdOrganization(aDOrganization);
        return cBiddingCriteria;
    }

    @BeforeEach
    public void initTest() {
        cBiddingCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBiddingCriteria() throws Exception {
        int databaseSizeBeforeCreate = cBiddingCriteriaRepository.findAll().size();

        // Create the CBiddingCriteria
        CBiddingCriteriaDTO cBiddingCriteriaDTO = cBiddingCriteriaMapper.toDto(cBiddingCriteria);
        restCBiddingCriteriaMockMvc.perform(post("/api/c-bidding-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CBiddingCriteria in the database
        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CBiddingCriteria testCBiddingCriteria = cBiddingCriteriaList.get(cBiddingCriteriaList.size() - 1);
        assertThat(testCBiddingCriteria.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBiddingCriteria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBiddingCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBiddingCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBiddingCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBiddingCriteriaRepository.findAll().size();

        // Create the CBiddingCriteria with an existing ID
        cBiddingCriteria.setId(1L);
        CBiddingCriteriaDTO cBiddingCriteriaDTO = cBiddingCriteriaMapper.toDto(cBiddingCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBiddingCriteriaMockMvc.perform(post("/api/c-bidding-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingCriteria in the database
        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBiddingCriteriaRepository.findAll().size();
        // set the field null
        cBiddingCriteria.setName(null);

        // Create the CBiddingCriteria, which fails.
        CBiddingCriteriaDTO cBiddingCriteriaDTO = cBiddingCriteriaMapper.toDto(cBiddingCriteria);

        restCBiddingCriteriaMockMvc.perform(post("/api/c-bidding-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingCriteriaDTO)))
            .andExpect(status().isBadRequest());

        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteria() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBiddingCriteria() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get the cBiddingCriteria
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria/{id}", cBiddingCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBiddingCriteria.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBiddingCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        Long id = cBiddingCriteria.getId();

        defaultCBiddingCriteriaShouldBeFound("id.equals=" + id);
        defaultCBiddingCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCBiddingCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBiddingCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCBiddingCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBiddingCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name equals to DEFAULT_NAME
        defaultCBiddingCriteriaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBiddingCriteriaList where name equals to UPDATED_NAME
        defaultCBiddingCriteriaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name not equals to DEFAULT_NAME
        defaultCBiddingCriteriaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBiddingCriteriaList where name not equals to UPDATED_NAME
        defaultCBiddingCriteriaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBiddingCriteriaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBiddingCriteriaList where name equals to UPDATED_NAME
        defaultCBiddingCriteriaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name is not null
        defaultCBiddingCriteriaShouldBeFound("name.specified=true");

        // Get all the cBiddingCriteriaList where name is null
        defaultCBiddingCriteriaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameContainsSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name contains DEFAULT_NAME
        defaultCBiddingCriteriaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBiddingCriteriaList where name contains UPDATED_NAME
        defaultCBiddingCriteriaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where name does not contain DEFAULT_NAME
        defaultCBiddingCriteriaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBiddingCriteriaList where name does not contain UPDATED_NAME
        defaultCBiddingCriteriaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description equals to DEFAULT_DESCRIPTION
        defaultCBiddingCriteriaShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingCriteriaList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description not equals to DEFAULT_DESCRIPTION
        defaultCBiddingCriteriaShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingCriteriaList where description not equals to UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBiddingCriteriaList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description is not null
        defaultCBiddingCriteriaShouldBeFound("description.specified=true");

        // Get all the cBiddingCriteriaList where description is null
        defaultCBiddingCriteriaShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description contains DEFAULT_DESCRIPTION
        defaultCBiddingCriteriaShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingCriteriaList where description contains UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where description does not contain DEFAULT_DESCRIPTION
        defaultCBiddingCriteriaShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingCriteriaList where description does not contain UPDATED_DESCRIPTION
        defaultCBiddingCriteriaShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBiddingCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where uid equals to DEFAULT_UID
        defaultCBiddingCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBiddingCriteriaList where uid equals to UPDATED_UID
        defaultCBiddingCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where uid not equals to DEFAULT_UID
        defaultCBiddingCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBiddingCriteriaList where uid not equals to UPDATED_UID
        defaultCBiddingCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBiddingCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBiddingCriteriaList where uid equals to UPDATED_UID
        defaultCBiddingCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where uid is not null
        defaultCBiddingCriteriaShouldBeFound("uid.specified=true");

        // Get all the cBiddingCriteriaList where uid is null
        defaultCBiddingCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCBiddingCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingCriteriaList where active equals to UPDATED_ACTIVE
        defaultCBiddingCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCBiddingCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCBiddingCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBiddingCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBiddingCriteriaList where active equals to UPDATED_ACTIVE
        defaultCBiddingCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        // Get all the cBiddingCriteriaList where active is not null
        defaultCBiddingCriteriaShouldBeFound("active.specified=true");

        // Get all the cBiddingCriteriaList where active is null
        defaultCBiddingCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBiddingCriteria.getAdOrganization();
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBiddingCriteriaList where adOrganization equals to adOrganizationId
        defaultCBiddingCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBiddingCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCBiddingCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBiddingCriteriaShouldBeFound(String filter) throws Exception {
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBiddingCriteriaShouldNotBeFound(String filter) throws Exception {
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBiddingCriteria() throws Exception {
        // Get the cBiddingCriteria
        restCBiddingCriteriaMockMvc.perform(get("/api/c-bidding-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBiddingCriteria() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        int databaseSizeBeforeUpdate = cBiddingCriteriaRepository.findAll().size();

        // Update the cBiddingCriteria
        CBiddingCriteria updatedCBiddingCriteria = cBiddingCriteriaRepository.findById(cBiddingCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCBiddingCriteria are not directly saved in db
        em.detach(updatedCBiddingCriteria);
        updatedCBiddingCriteria
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBiddingCriteriaDTO cBiddingCriteriaDTO = cBiddingCriteriaMapper.toDto(updatedCBiddingCriteria);

        restCBiddingCriteriaMockMvc.perform(put("/api/c-bidding-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CBiddingCriteria in the database
        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CBiddingCriteria testCBiddingCriteria = cBiddingCriteriaList.get(cBiddingCriteriaList.size() - 1);
        assertThat(testCBiddingCriteria.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBiddingCriteria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBiddingCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBiddingCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBiddingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cBiddingCriteriaRepository.findAll().size();

        // Create the CBiddingCriteria
        CBiddingCriteriaDTO cBiddingCriteriaDTO = cBiddingCriteriaMapper.toDto(cBiddingCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBiddingCriteriaMockMvc.perform(put("/api/c-bidding-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingCriteria in the database
        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBiddingCriteria() throws Exception {
        // Initialize the database
        cBiddingCriteriaRepository.saveAndFlush(cBiddingCriteria);

        int databaseSizeBeforeDelete = cBiddingCriteriaRepository.findAll().size();

        // Delete the cBiddingCriteria
        restCBiddingCriteriaMockMvc.perform(delete("/api/c-bidding-criteria/{id}", cBiddingCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBiddingCriteria> cBiddingCriteriaList = cBiddingCriteriaRepository.findAll();
        assertThat(cBiddingCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
