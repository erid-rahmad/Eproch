package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CClause;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CClauseRepository;
import com.bhp.opusb.service.CClauseService;
import com.bhp.opusb.service.dto.CClauseDTO;
import com.bhp.opusb.service.mapper.CClauseMapper;
import com.bhp.opusb.service.dto.CClauseCriteria;
import com.bhp.opusb.service.CClauseQueryService;

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
 * Integration tests for the {@link CClauseResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CClauseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CClauseRepository cClauseRepository;

    @Autowired
    private CClauseMapper cClauseMapper;

    @Autowired
    private CClauseService cClauseService;

    @Autowired
    private CClauseQueryService cClauseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCClauseMockMvc;

    private CClause cClause;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CClause createEntity(EntityManager em) {
        CClause cClause = new CClause()
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
        cClause.setAdOrganization(aDOrganization);
        return cClause;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CClause createUpdatedEntity(EntityManager em) {
        CClause cClause = new CClause()
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
        cClause.setAdOrganization(aDOrganization);
        return cClause;
    }

    @BeforeEach
    public void initTest() {
        cClause = createEntity(em);
    }

    @Test
    @Transactional
    public void createCClause() throws Exception {
        int databaseSizeBeforeCreate = cClauseRepository.findAll().size();

        // Create the CClause
        CClauseDTO cClauseDTO = cClauseMapper.toDto(cClause);
        restCClauseMockMvc.perform(post("/api/c-clauses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseDTO)))
            .andExpect(status().isCreated());

        // Validate the CClause in the database
        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeCreate + 1);
        CClause testCClause = cClauseList.get(cClauseList.size() - 1);
        assertThat(testCClause.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCClause.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCClause.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCClauseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cClauseRepository.findAll().size();

        // Create the CClause with an existing ID
        cClause.setId(1L);
        CClauseDTO cClauseDTO = cClauseMapper.toDto(cClause);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCClauseMockMvc.perform(post("/api/c-clauses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CClause in the database
        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClauseRepository.findAll().size();
        // set the field null
        cClause.setName(null);

        // Create the CClause, which fails.
        CClauseDTO cClauseDTO = cClauseMapper.toDto(cClause);

        restCClauseMockMvc.perform(post("/api/c-clauses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseDTO)))
            .andExpect(status().isBadRequest());

        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCClauses() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList
        restCClauseMockMvc.perform(get("/api/c-clauses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cClause.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCClause() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get the cClause
        restCClauseMockMvc.perform(get("/api/c-clauses/{id}", cClause.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cClause.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCClausesByIdFiltering() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        Long id = cClause.getId();

        defaultCClauseShouldBeFound("id.equals=" + id);
        defaultCClauseShouldNotBeFound("id.notEquals=" + id);

        defaultCClauseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCClauseShouldNotBeFound("id.greaterThan=" + id);

        defaultCClauseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCClauseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCClausesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name equals to DEFAULT_NAME
        defaultCClauseShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cClauseList where name equals to UPDATED_NAME
        defaultCClauseShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClausesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name not equals to DEFAULT_NAME
        defaultCClauseShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cClauseList where name not equals to UPDATED_NAME
        defaultCClauseShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClausesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCClauseShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cClauseList where name equals to UPDATED_NAME
        defaultCClauseShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClausesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name is not null
        defaultCClauseShouldBeFound("name.specified=true");

        // Get all the cClauseList where name is null
        defaultCClauseShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCClausesByNameContainsSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name contains DEFAULT_NAME
        defaultCClauseShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cClauseList where name contains UPDATED_NAME
        defaultCClauseShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClausesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where name does not contain DEFAULT_NAME
        defaultCClauseShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cClauseList where name does not contain UPDATED_NAME
        defaultCClauseShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCClausesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where uid equals to DEFAULT_UID
        defaultCClauseShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cClauseList where uid equals to UPDATED_UID
        defaultCClauseShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClausesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where uid not equals to DEFAULT_UID
        defaultCClauseShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cClauseList where uid not equals to UPDATED_UID
        defaultCClauseShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClausesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where uid in DEFAULT_UID or UPDATED_UID
        defaultCClauseShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cClauseList where uid equals to UPDATED_UID
        defaultCClauseShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClausesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where uid is not null
        defaultCClauseShouldBeFound("uid.specified=true");

        // Get all the cClauseList where uid is null
        defaultCClauseShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCClausesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where active equals to DEFAULT_ACTIVE
        defaultCClauseShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cClauseList where active equals to UPDATED_ACTIVE
        defaultCClauseShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClausesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where active not equals to DEFAULT_ACTIVE
        defaultCClauseShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cClauseList where active not equals to UPDATED_ACTIVE
        defaultCClauseShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClausesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCClauseShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cClauseList where active equals to UPDATED_ACTIVE
        defaultCClauseShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClausesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        // Get all the cClauseList where active is not null
        defaultCClauseShouldBeFound("active.specified=true");

        // Get all the cClauseList where active is null
        defaultCClauseShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCClausesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cClause.getAdOrganization();
        cClauseRepository.saveAndFlush(cClause);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cClauseList where adOrganization equals to adOrganizationId
        defaultCClauseShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cClauseList where adOrganization equals to adOrganizationId + 1
        defaultCClauseShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCClauseShouldBeFound(String filter) throws Exception {
        restCClauseMockMvc.perform(get("/api/c-clauses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cClause.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCClauseMockMvc.perform(get("/api/c-clauses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCClauseShouldNotBeFound(String filter) throws Exception {
        restCClauseMockMvc.perform(get("/api/c-clauses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCClauseMockMvc.perform(get("/api/c-clauses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCClause() throws Exception {
        // Get the cClause
        restCClauseMockMvc.perform(get("/api/c-clauses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCClause() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        int databaseSizeBeforeUpdate = cClauseRepository.findAll().size();

        // Update the cClause
        CClause updatedCClause = cClauseRepository.findById(cClause.getId()).get();
        // Disconnect from session so that the updates on updatedCClause are not directly saved in db
        em.detach(updatedCClause);
        updatedCClause
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CClauseDTO cClauseDTO = cClauseMapper.toDto(updatedCClause);

        restCClauseMockMvc.perform(put("/api/c-clauses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseDTO)))
            .andExpect(status().isOk());

        // Validate the CClause in the database
        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeUpdate);
        CClause testCClause = cClauseList.get(cClauseList.size() - 1);
        assertThat(testCClause.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCClause.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCClause.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCClause() throws Exception {
        int databaseSizeBeforeUpdate = cClauseRepository.findAll().size();

        // Create the CClause
        CClauseDTO cClauseDTO = cClauseMapper.toDto(cClause);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCClauseMockMvc.perform(put("/api/c-clauses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CClause in the database
        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCClause() throws Exception {
        // Initialize the database
        cClauseRepository.saveAndFlush(cClause);

        int databaseSizeBeforeDelete = cClauseRepository.findAll().size();

        // Delete the cClause
        restCClauseMockMvc.perform(delete("/api/c-clauses/{id}", cClause.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CClause> cClauseList = cClauseRepository.findAll();
        assertThat(cClauseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
