package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CClauseLine;
import com.bhp.opusb.domain.CClause;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CClauseLineRepository;
import com.bhp.opusb.service.CClauseLineService;
import com.bhp.opusb.service.dto.CClauseLineDTO;
import com.bhp.opusb.service.mapper.CClauseLineMapper;
import com.bhp.opusb.service.dto.CClauseLineCriteria;
import com.bhp.opusb.service.CClauseLineQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CClauseLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CClauseLineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CClauseLineRepository cClauseLineRepository;

    @Autowired
    private CClauseLineMapper cClauseLineMapper;

    @Autowired
    private CClauseLineService cClauseLineService;

    @Autowired
    private CClauseLineQueryService cClauseLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCClauseLineMockMvc;

    private CClauseLine cClauseLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CClauseLine createEntity(EntityManager em) {
        CClauseLine cClauseLine = new CClauseLine()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CClause cClause;
        if (TestUtil.findAll(em, CClause.class).isEmpty()) {
            cClause = CClauseResourceIT.createEntity(em);
            em.persist(cClause);
            em.flush();
        } else {
            cClause = TestUtil.findAll(em, CClause.class).get(0);
        }
        cClauseLine.setClause(cClause);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cClauseLine.setAdOrganization(aDOrganization);
        return cClauseLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CClauseLine createUpdatedEntity(EntityManager em) {
        CClauseLine cClauseLine = new CClauseLine()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CClause cClause;
        if (TestUtil.findAll(em, CClause.class).isEmpty()) {
            cClause = CClauseResourceIT.createUpdatedEntity(em);
            em.persist(cClause);
            em.flush();
        } else {
            cClause = TestUtil.findAll(em, CClause.class).get(0);
        }
        cClauseLine.setClause(cClause);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cClauseLine.setAdOrganization(aDOrganization);
        return cClauseLine;
    }

    @BeforeEach
    public void initTest() {
        cClauseLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCClauseLine() throws Exception {
        int databaseSizeBeforeCreate = cClauseLineRepository.findAll().size();

        // Create the CClauseLine
        CClauseLineDTO cClauseLineDTO = cClauseLineMapper.toDto(cClauseLine);
        restCClauseLineMockMvc.perform(post("/api/c-clause-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CClauseLine in the database
        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeCreate + 1);
        CClauseLine testCClauseLine = cClauseLineList.get(cClauseLineList.size() - 1);
        assertThat(testCClauseLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCClauseLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCClauseLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCClauseLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCClauseLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cClauseLineRepository.findAll().size();

        // Create the CClauseLine with an existing ID
        cClauseLine.setId(1L);
        CClauseLineDTO cClauseLineDTO = cClauseLineMapper.toDto(cClauseLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCClauseLineMockMvc.perform(post("/api/c-clause-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CClauseLine in the database
        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClauseLineRepository.findAll().size();
        // set the field null
        cClauseLine.setName(null);

        // Create the CClauseLine, which fails.
        CClauseLineDTO cClauseLineDTO = cClauseLineMapper.toDto(cClauseLine);

        restCClauseLineMockMvc.perform(post("/api/c-clause-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseLineDTO)))
            .andExpect(status().isBadRequest());

        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCClauseLines() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cClauseLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCClauseLine() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get the cClauseLine
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines/{id}", cClauseLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cClauseLine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCClauseLinesByIdFiltering() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        Long id = cClauseLine.getId();

        defaultCClauseLineShouldBeFound("id.equals=" + id);
        defaultCClauseLineShouldNotBeFound("id.notEquals=" + id);

        defaultCClauseLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCClauseLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCClauseLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCClauseLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCClauseLinesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name equals to DEFAULT_NAME
        defaultCClauseLineShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cClauseLineList where name equals to UPDATED_NAME
        defaultCClauseLineShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name not equals to DEFAULT_NAME
        defaultCClauseLineShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cClauseLineList where name not equals to UPDATED_NAME
        defaultCClauseLineShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCClauseLineShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cClauseLineList where name equals to UPDATED_NAME
        defaultCClauseLineShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name is not null
        defaultCClauseLineShouldBeFound("name.specified=true");

        // Get all the cClauseLineList where name is null
        defaultCClauseLineShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCClauseLinesByNameContainsSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name contains DEFAULT_NAME
        defaultCClauseLineShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cClauseLineList where name contains UPDATED_NAME
        defaultCClauseLineShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where name does not contain DEFAULT_NAME
        defaultCClauseLineShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cClauseLineList where name does not contain UPDATED_NAME
        defaultCClauseLineShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCClauseLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where uid equals to DEFAULT_UID
        defaultCClauseLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cClauseLineList where uid equals to UPDATED_UID
        defaultCClauseLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where uid not equals to DEFAULT_UID
        defaultCClauseLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cClauseLineList where uid not equals to UPDATED_UID
        defaultCClauseLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCClauseLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cClauseLineList where uid equals to UPDATED_UID
        defaultCClauseLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where uid is not null
        defaultCClauseLineShouldBeFound("uid.specified=true");

        // Get all the cClauseLineList where uid is null
        defaultCClauseLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where active equals to DEFAULT_ACTIVE
        defaultCClauseLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cClauseLineList where active equals to UPDATED_ACTIVE
        defaultCClauseLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where active not equals to DEFAULT_ACTIVE
        defaultCClauseLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cClauseLineList where active not equals to UPDATED_ACTIVE
        defaultCClauseLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCClauseLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cClauseLineList where active equals to UPDATED_ACTIVE
        defaultCClauseLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        // Get all the cClauseLineList where active is not null
        defaultCClauseLineShouldBeFound("active.specified=true");

        // Get all the cClauseLineList where active is null
        defaultCClauseLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCClauseLinesByClauseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CClause clause = cClauseLine.getClause();
        cClauseLineRepository.saveAndFlush(cClauseLine);
        Long clauseId = clause.getId();

        // Get all the cClauseLineList where clause equals to clauseId
        defaultCClauseLineShouldBeFound("clauseId.equals=" + clauseId);

        // Get all the cClauseLineList where clause equals to clauseId + 1
        defaultCClauseLineShouldNotBeFound("clauseId.equals=" + (clauseId + 1));
    }


    @Test
    @Transactional
    public void getAllCClauseLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cClauseLine.getAdOrganization();
        cClauseLineRepository.saveAndFlush(cClauseLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cClauseLineList where adOrganization equals to adOrganizationId
        defaultCClauseLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cClauseLineList where adOrganization equals to adOrganizationId + 1
        defaultCClauseLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCClauseLineShouldBeFound(String filter) throws Exception {
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cClauseLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCClauseLineShouldNotBeFound(String filter) throws Exception {
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCClauseLine() throws Exception {
        // Get the cClauseLine
        restCClauseLineMockMvc.perform(get("/api/c-clause-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCClauseLine() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        int databaseSizeBeforeUpdate = cClauseLineRepository.findAll().size();

        // Update the cClauseLine
        CClauseLine updatedCClauseLine = cClauseLineRepository.findById(cClauseLine.getId()).get();
        // Disconnect from session so that the updates on updatedCClauseLine are not directly saved in db
        em.detach(updatedCClauseLine);
        updatedCClauseLine
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CClauseLineDTO cClauseLineDTO = cClauseLineMapper.toDto(updatedCClauseLine);

        restCClauseLineMockMvc.perform(put("/api/c-clause-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseLineDTO)))
            .andExpect(status().isOk());

        // Validate the CClauseLine in the database
        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeUpdate);
        CClauseLine testCClauseLine = cClauseLineList.get(cClauseLineList.size() - 1);
        assertThat(testCClauseLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCClauseLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCClauseLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCClauseLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCClauseLine() throws Exception {
        int databaseSizeBeforeUpdate = cClauseLineRepository.findAll().size();

        // Create the CClauseLine
        CClauseLineDTO cClauseLineDTO = cClauseLineMapper.toDto(cClauseLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCClauseLineMockMvc.perform(put("/api/c-clause-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cClauseLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CClauseLine in the database
        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCClauseLine() throws Exception {
        // Initialize the database
        cClauseLineRepository.saveAndFlush(cClauseLine);

        int databaseSizeBeforeDelete = cClauseLineRepository.findAll().size();

        // Delete the cClauseLine
        restCClauseLineMockMvc.perform(delete("/api/c-clause-lines/{id}", cClauseLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CClauseLine> cClauseLineList = cClauseLineRepository.findAll();
        assertThat(cClauseLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
