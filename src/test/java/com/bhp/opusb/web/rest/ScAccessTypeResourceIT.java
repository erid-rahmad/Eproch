package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ScAccessType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ScAccessTypeRepository;
import com.bhp.opusb.service.ScAccessTypeService;
import com.bhp.opusb.service.dto.ScAccessTypeDTO;
import com.bhp.opusb.service.mapper.ScAccessTypeMapper;
import com.bhp.opusb.service.dto.ScAccessTypeCriteria;
import com.bhp.opusb.service.ScAccessTypeQueryService;

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
 * Integration tests for the {@link ScAccessTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ScAccessTypeResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ScAccessTypeRepository scAccessTypeRepository;

    @Autowired
    private ScAccessTypeMapper scAccessTypeMapper;

    @Autowired
    private ScAccessTypeService scAccessTypeService;

    @Autowired
    private ScAccessTypeQueryService scAccessTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScAccessTypeMockMvc;

    private ScAccessType scAccessType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccessType createEntity(EntityManager em) {
        ScAccessType scAccessType = new ScAccessType()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAccessType.setAdOrganization(aDOrganization);
        return scAccessType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccessType createUpdatedEntity(EntityManager em) {
        ScAccessType scAccessType = new ScAccessType()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAccessType.setAdOrganization(aDOrganization);
        return scAccessType;
    }

    @BeforeEach
    public void initTest() {
        scAccessType = createEntity(em);
    }

    @Test
    @Transactional
    public void createScAccessType() throws Exception {
        int databaseSizeBeforeCreate = scAccessTypeRepository.findAll().size();

        // Create the ScAccessType
        ScAccessTypeDTO scAccessTypeDTO = scAccessTypeMapper.toDto(scAccessType);
        restScAccessTypeMockMvc.perform(post("/api/sc-access-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ScAccessType in the database
        List<ScAccessType> scAccessTypeList = scAccessTypeRepository.findAll();
        assertThat(scAccessTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ScAccessType testScAccessType = scAccessTypeList.get(scAccessTypeList.size() - 1);
        assertThat(testScAccessType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testScAccessType.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testScAccessType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScAccessType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createScAccessTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scAccessTypeRepository.findAll().size();

        // Create the ScAccessType with an existing ID
        scAccessType.setId(1L);
        ScAccessTypeDTO scAccessTypeDTO = scAccessTypeMapper.toDto(scAccessType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScAccessTypeMockMvc.perform(post("/api/sc-access-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAccessType in the database
        List<ScAccessType> scAccessTypeList = scAccessTypeRepository.findAll();
        assertThat(scAccessTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScAccessTypes() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAccessType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getScAccessType() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get the scAccessType
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types/{id}", scAccessType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scAccessType.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getScAccessTypesByIdFiltering() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        Long id = scAccessType.getId();

        defaultScAccessTypeShouldBeFound("id.equals=" + id);
        defaultScAccessTypeShouldNotBeFound("id.notEquals=" + id);

        defaultScAccessTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScAccessTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultScAccessTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScAccessTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScAccessTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where uid equals to DEFAULT_UID
        defaultScAccessTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the scAccessTypeList where uid equals to UPDATED_UID
        defaultScAccessTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where uid not equals to DEFAULT_UID
        defaultScAccessTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the scAccessTypeList where uid not equals to UPDATED_UID
        defaultScAccessTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultScAccessTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the scAccessTypeList where uid equals to UPDATED_UID
        defaultScAccessTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where uid is not null
        defaultScAccessTypeShouldBeFound("uid.specified=true");

        // Get all the scAccessTypeList where uid is null
        defaultScAccessTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where active equals to DEFAULT_ACTIVE
        defaultScAccessTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the scAccessTypeList where active equals to UPDATED_ACTIVE
        defaultScAccessTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where active not equals to DEFAULT_ACTIVE
        defaultScAccessTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the scAccessTypeList where active not equals to UPDATED_ACTIVE
        defaultScAccessTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultScAccessTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the scAccessTypeList where active equals to UPDATED_ACTIVE
        defaultScAccessTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where active is not null
        defaultScAccessTypeShouldBeFound("active.specified=true");

        // Get all the scAccessTypeList where active is null
        defaultScAccessTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name equals to DEFAULT_NAME
        defaultScAccessTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the scAccessTypeList where name equals to UPDATED_NAME
        defaultScAccessTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name not equals to DEFAULT_NAME
        defaultScAccessTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the scAccessTypeList where name not equals to UPDATED_NAME
        defaultScAccessTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultScAccessTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the scAccessTypeList where name equals to UPDATED_NAME
        defaultScAccessTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name is not null
        defaultScAccessTypeShouldBeFound("name.specified=true");

        // Get all the scAccessTypeList where name is null
        defaultScAccessTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllScAccessTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name contains DEFAULT_NAME
        defaultScAccessTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the scAccessTypeList where name contains UPDATED_NAME
        defaultScAccessTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where name does not contain DEFAULT_NAME
        defaultScAccessTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the scAccessTypeList where name does not contain UPDATED_NAME
        defaultScAccessTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description equals to DEFAULT_DESCRIPTION
        defaultScAccessTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessTypeList where description equals to UPDATED_DESCRIPTION
        defaultScAccessTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultScAccessTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessTypeList where description not equals to UPDATED_DESCRIPTION
        defaultScAccessTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultScAccessTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the scAccessTypeList where description equals to UPDATED_DESCRIPTION
        defaultScAccessTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description is not null
        defaultScAccessTypeShouldBeFound("description.specified=true");

        // Get all the scAccessTypeList where description is null
        defaultScAccessTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description contains DEFAULT_DESCRIPTION
        defaultScAccessTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessTypeList where description contains UPDATED_DESCRIPTION
        defaultScAccessTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        // Get all the scAccessTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultScAccessTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessTypeList where description does not contain UPDATED_DESCRIPTION
        defaultScAccessTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllScAccessTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = scAccessType.getAdOrganization();
        scAccessTypeRepository.saveAndFlush(scAccessType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the scAccessTypeList where adOrganization equals to adOrganizationId
        defaultScAccessTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the scAccessTypeList where adOrganization equals to adOrganizationId + 1
        defaultScAccessTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScAccessTypeShouldBeFound(String filter) throws Exception {
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAccessType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScAccessTypeShouldNotBeFound(String filter) throws Exception {
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScAccessType() throws Exception {
        // Get the scAccessType
        restScAccessTypeMockMvc.perform(get("/api/sc-access-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScAccessType() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        int databaseSizeBeforeUpdate = scAccessTypeRepository.findAll().size();

        // Update the scAccessType
        ScAccessType updatedScAccessType = scAccessTypeRepository.findById(scAccessType.getId()).get();
        // Disconnect from session so that the updates on updatedScAccessType are not directly saved in db
        em.detach(updatedScAccessType);
        updatedScAccessType
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ScAccessTypeDTO scAccessTypeDTO = scAccessTypeMapper.toDto(updatedScAccessType);

        restScAccessTypeMockMvc.perform(put("/api/sc-access-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ScAccessType in the database
        List<ScAccessType> scAccessTypeList = scAccessTypeRepository.findAll();
        assertThat(scAccessTypeList).hasSize(databaseSizeBeforeUpdate);
        ScAccessType testScAccessType = scAccessTypeList.get(scAccessTypeList.size() - 1);
        assertThat(testScAccessType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testScAccessType.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testScAccessType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScAccessType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingScAccessType() throws Exception {
        int databaseSizeBeforeUpdate = scAccessTypeRepository.findAll().size();

        // Create the ScAccessType
        ScAccessTypeDTO scAccessTypeDTO = scAccessTypeMapper.toDto(scAccessType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScAccessTypeMockMvc.perform(put("/api/sc-access-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAccessType in the database
        List<ScAccessType> scAccessTypeList = scAccessTypeRepository.findAll();
        assertThat(scAccessTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScAccessType() throws Exception {
        // Initialize the database
        scAccessTypeRepository.saveAndFlush(scAccessType);

        int databaseSizeBeforeDelete = scAccessTypeRepository.findAll().size();

        // Delete the scAccessType
        restScAccessTypeMockMvc.perform(delete("/api/sc-access-types/{id}", scAccessType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScAccessType> scAccessTypeList = scAccessTypeRepository.findAll();
        assertThat(scAccessTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
