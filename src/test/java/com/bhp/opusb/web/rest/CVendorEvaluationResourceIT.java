package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorEvaluationRepository;
import com.bhp.opusb.service.CVendorEvaluationService;
import com.bhp.opusb.service.dto.CVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationMapper;
import com.bhp.opusb.service.dto.CVendorEvaluationCriteria;
import com.bhp.opusb.service.CVendorEvaluationQueryService;

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
 * Integration tests for the {@link CVendorEvaluationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorEvaluationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorEvaluationRepository cVendorEvaluationRepository;

    @Autowired
    private CVendorEvaluationMapper cVendorEvaluationMapper;

    @Autowired
    private CVendorEvaluationService cVendorEvaluationService;

    @Autowired
    private CVendorEvaluationQueryService cVendorEvaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorEvaluationMockMvc;

    private CVendorEvaluation cVendorEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorEvaluation createEntity(EntityManager em) {
        CVendorEvaluation cVendorEvaluation = new CVendorEvaluation()
            .name(DEFAULT_NAME)
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
        cVendorEvaluation.setAdOrganization(aDOrganization);
        return cVendorEvaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorEvaluation createUpdatedEntity(EntityManager em) {
        CVendorEvaluation cVendorEvaluation = new CVendorEvaluation()
            .name(UPDATED_NAME)
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
        cVendorEvaluation.setAdOrganization(aDOrganization);
        return cVendorEvaluation;
    }

    @BeforeEach
    public void initTest() {
        cVendorEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorEvaluation() throws Exception {
        int databaseSizeBeforeCreate = cVendorEvaluationRepository.findAll().size();

        // Create the CVendorEvaluation
        CVendorEvaluationDTO cVendorEvaluationDTO = cVendorEvaluationMapper.toDto(cVendorEvaluation);
        restCVendorEvaluationMockMvc.perform(post("/api/c-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorEvaluation in the database
        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorEvaluation testCVendorEvaluation = cVendorEvaluationList.get(cVendorEvaluationList.size() - 1);
        assertThat(testCVendorEvaluation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCVendorEvaluation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorEvaluation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorEvaluationRepository.findAll().size();

        // Create the CVendorEvaluation with an existing ID
        cVendorEvaluation.setId(1L);
        CVendorEvaluationDTO cVendorEvaluationDTO = cVendorEvaluationMapper.toDto(cVendorEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorEvaluationMockMvc.perform(post("/api/c-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorEvaluation in the database
        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorEvaluationRepository.findAll().size();
        // set the field null
        cVendorEvaluation.setName(null);

        // Create the CVendorEvaluation, which fails.
        CVendorEvaluationDTO cVendorEvaluationDTO = cVendorEvaluationMapper.toDto(cVendorEvaluation);

        restCVendorEvaluationMockMvc.perform(post("/api/c-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluations() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorEvaluation() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get the cVendorEvaluation
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations/{id}", cVendorEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        Long id = cVendorEvaluation.getId();

        defaultCVendorEvaluationShouldBeFound("id.equals=" + id);
        defaultCVendorEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name equals to DEFAULT_NAME
        defaultCVendorEvaluationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cVendorEvaluationList where name equals to UPDATED_NAME
        defaultCVendorEvaluationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name not equals to DEFAULT_NAME
        defaultCVendorEvaluationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cVendorEvaluationList where name not equals to UPDATED_NAME
        defaultCVendorEvaluationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCVendorEvaluationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cVendorEvaluationList where name equals to UPDATED_NAME
        defaultCVendorEvaluationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name is not null
        defaultCVendorEvaluationShouldBeFound("name.specified=true");

        // Get all the cVendorEvaluationList where name is null
        defaultCVendorEvaluationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameContainsSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name contains DEFAULT_NAME
        defaultCVendorEvaluationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cVendorEvaluationList where name contains UPDATED_NAME
        defaultCVendorEvaluationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where name does not contain DEFAULT_NAME
        defaultCVendorEvaluationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cVendorEvaluationList where name does not contain UPDATED_NAME
        defaultCVendorEvaluationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCVendorEvaluationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where uid equals to DEFAULT_UID
        defaultCVendorEvaluationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorEvaluationList where uid equals to UPDATED_UID
        defaultCVendorEvaluationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where uid not equals to DEFAULT_UID
        defaultCVendorEvaluationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorEvaluationList where uid not equals to UPDATED_UID
        defaultCVendorEvaluationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorEvaluationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorEvaluationList where uid equals to UPDATED_UID
        defaultCVendorEvaluationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where uid is not null
        defaultCVendorEvaluationShouldBeFound("uid.specified=true");

        // Get all the cVendorEvaluationList where uid is null
        defaultCVendorEvaluationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where active equals to DEFAULT_ACTIVE
        defaultCVendorEvaluationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorEvaluationList where active equals to UPDATED_ACTIVE
        defaultCVendorEvaluationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where active not equals to DEFAULT_ACTIVE
        defaultCVendorEvaluationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorEvaluationList where active not equals to UPDATED_ACTIVE
        defaultCVendorEvaluationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorEvaluationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorEvaluationList where active equals to UPDATED_ACTIVE
        defaultCVendorEvaluationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        // Get all the cVendorEvaluationList where active is not null
        defaultCVendorEvaluationShouldBeFound("active.specified=true");

        // Get all the cVendorEvaluationList where active is null
        defaultCVendorEvaluationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorEvaluationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorEvaluation.getAdOrganization();
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorEvaluationList where adOrganization equals to adOrganizationId
        defaultCVendorEvaluationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorEvaluationList where adOrganization equals to adOrganizationId + 1
        defaultCVendorEvaluationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorEvaluationShouldBeFound(String filter) throws Exception {
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorEvaluationShouldNotBeFound(String filter) throws Exception {
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorEvaluation() throws Exception {
        // Get the cVendorEvaluation
        restCVendorEvaluationMockMvc.perform(get("/api/c-vendor-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorEvaluation() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        int databaseSizeBeforeUpdate = cVendorEvaluationRepository.findAll().size();

        // Update the cVendorEvaluation
        CVendorEvaluation updatedCVendorEvaluation = cVendorEvaluationRepository.findById(cVendorEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorEvaluation are not directly saved in db
        em.detach(updatedCVendorEvaluation);
        updatedCVendorEvaluation
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorEvaluationDTO cVendorEvaluationDTO = cVendorEvaluationMapper.toDto(updatedCVendorEvaluation);

        restCVendorEvaluationMockMvc.perform(put("/api/c-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorEvaluation in the database
        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeUpdate);
        CVendorEvaluation testCVendorEvaluation = cVendorEvaluationList.get(cVendorEvaluationList.size() - 1);
        assertThat(testCVendorEvaluation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCVendorEvaluation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorEvaluation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = cVendorEvaluationRepository.findAll().size();

        // Create the CVendorEvaluation
        CVendorEvaluationDTO cVendorEvaluationDTO = cVendorEvaluationMapper.toDto(cVendorEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorEvaluationMockMvc.perform(put("/api/c-vendor-evaluations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorEvaluation in the database
        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorEvaluation() throws Exception {
        // Initialize the database
        cVendorEvaluationRepository.saveAndFlush(cVendorEvaluation);

        int databaseSizeBeforeDelete = cVendorEvaluationRepository.findAll().size();

        // Delete the cVendorEvaluation
        restCVendorEvaluationMockMvc.perform(delete("/api/c-vendor-evaluations/{id}", cVendorEvaluation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorEvaluation> cVendorEvaluationList = cVendorEvaluationRepository.findAll();
        assertThat(cVendorEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
