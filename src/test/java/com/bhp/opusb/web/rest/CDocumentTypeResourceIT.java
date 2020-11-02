package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.service.CDocumentTypeService;
import com.bhp.opusb.service.dto.CDocumentTypeDTO;
import com.bhp.opusb.service.mapper.CDocumentTypeMapper;
import com.bhp.opusb.service.dto.CDocumentTypeCriteria;
import com.bhp.opusb.service.CDocumentTypeQueryService;

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
 * Integration tests for the {@link CDocumentTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CDocumentTypeResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CDocumentTypeRepository cDocumentTypeRepository;

    @Autowired
    private CDocumentTypeMapper cDocumentTypeMapper;

    @Autowired
    private CDocumentTypeService cDocumentTypeService;

    @Autowired
    private CDocumentTypeQueryService cDocumentTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCDocumentTypeMockMvc;

    private CDocumentType cDocumentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CDocumentType createEntity(EntityManager em) {
        CDocumentType cDocumentType = new CDocumentType()
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
        cDocumentType.setAdOrganization(aDOrganization);
        return cDocumentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CDocumentType createUpdatedEntity(EntityManager em) {
        CDocumentType cDocumentType = new CDocumentType()
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
        cDocumentType.setAdOrganization(aDOrganization);
        return cDocumentType;
    }

    @BeforeEach
    public void initTest() {
        cDocumentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCDocumentType() throws Exception {
        int databaseSizeBeforeCreate = cDocumentTypeRepository.findAll().size();

        // Create the CDocumentType
        CDocumentTypeDTO cDocumentTypeDTO = cDocumentTypeMapper.toDto(cDocumentType);
        restCDocumentTypeMockMvc.perform(post("/api/c-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cDocumentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CDocumentType in the database
        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CDocumentType testCDocumentType = cDocumentTypeList.get(cDocumentTypeList.size() - 1);
        assertThat(testCDocumentType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCDocumentType.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCDocumentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCDocumentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCDocumentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cDocumentTypeRepository.findAll().size();

        // Create the CDocumentType with an existing ID
        cDocumentType.setId(1L);
        CDocumentTypeDTO cDocumentTypeDTO = cDocumentTypeMapper.toDto(cDocumentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCDocumentTypeMockMvc.perform(post("/api/c-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cDocumentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CDocumentType in the database
        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDocumentTypeRepository.findAll().size();
        // set the field null
        cDocumentType.setName(null);

        // Create the CDocumentType, which fails.
        CDocumentTypeDTO cDocumentTypeDTO = cDocumentTypeMapper.toDto(cDocumentType);

        restCDocumentTypeMockMvc.perform(post("/api/c-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cDocumentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypes() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cDocumentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCDocumentType() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get the cDocumentType
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types/{id}", cDocumentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cDocumentType.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getCDocumentTypesByIdFiltering() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        Long id = cDocumentType.getId();

        defaultCDocumentTypeShouldBeFound("id.equals=" + id);
        defaultCDocumentTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCDocumentTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCDocumentTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCDocumentTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCDocumentTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCDocumentTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where uid equals to DEFAULT_UID
        defaultCDocumentTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cDocumentTypeList where uid equals to UPDATED_UID
        defaultCDocumentTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where uid not equals to DEFAULT_UID
        defaultCDocumentTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cDocumentTypeList where uid not equals to UPDATED_UID
        defaultCDocumentTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCDocumentTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cDocumentTypeList where uid equals to UPDATED_UID
        defaultCDocumentTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where uid is not null
        defaultCDocumentTypeShouldBeFound("uid.specified=true");

        // Get all the cDocumentTypeList where uid is null
        defaultCDocumentTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where active equals to DEFAULT_ACTIVE
        defaultCDocumentTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cDocumentTypeList where active equals to UPDATED_ACTIVE
        defaultCDocumentTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where active not equals to DEFAULT_ACTIVE
        defaultCDocumentTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cDocumentTypeList where active not equals to UPDATED_ACTIVE
        defaultCDocumentTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCDocumentTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cDocumentTypeList where active equals to UPDATED_ACTIVE
        defaultCDocumentTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where active is not null
        defaultCDocumentTypeShouldBeFound("active.specified=true");

        // Get all the cDocumentTypeList where active is null
        defaultCDocumentTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name equals to DEFAULT_NAME
        defaultCDocumentTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cDocumentTypeList where name equals to UPDATED_NAME
        defaultCDocumentTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name not equals to DEFAULT_NAME
        defaultCDocumentTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cDocumentTypeList where name not equals to UPDATED_NAME
        defaultCDocumentTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCDocumentTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cDocumentTypeList where name equals to UPDATED_NAME
        defaultCDocumentTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name is not null
        defaultCDocumentTypeShouldBeFound("name.specified=true");

        // Get all the cDocumentTypeList where name is null
        defaultCDocumentTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCDocumentTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name contains DEFAULT_NAME
        defaultCDocumentTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cDocumentTypeList where name contains UPDATED_NAME
        defaultCDocumentTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where name does not contain DEFAULT_NAME
        defaultCDocumentTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cDocumentTypeList where name does not contain UPDATED_NAME
        defaultCDocumentTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCDocumentTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cDocumentTypeList where description equals to UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCDocumentTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cDocumentTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cDocumentTypeList where description equals to UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description is not null
        defaultCDocumentTypeShouldBeFound("description.specified=true");

        // Get all the cDocumentTypeList where description is null
        defaultCDocumentTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description contains DEFAULT_DESCRIPTION
        defaultCDocumentTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cDocumentTypeList where description contains UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCDocumentTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        // Get all the cDocumentTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCDocumentTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cDocumentTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCDocumentTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCDocumentTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cDocumentType.getAdOrganization();
        cDocumentTypeRepository.saveAndFlush(cDocumentType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cDocumentTypeList where adOrganization equals to adOrganizationId
        defaultCDocumentTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cDocumentTypeList where adOrganization equals to adOrganizationId + 1
        defaultCDocumentTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCDocumentTypeShouldBeFound(String filter) throws Exception {
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cDocumentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCDocumentTypeShouldNotBeFound(String filter) throws Exception {
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCDocumentType() throws Exception {
        // Get the cDocumentType
        restCDocumentTypeMockMvc.perform(get("/api/c-document-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCDocumentType() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        int databaseSizeBeforeUpdate = cDocumentTypeRepository.findAll().size();

        // Update the cDocumentType
        CDocumentType updatedCDocumentType = cDocumentTypeRepository.findById(cDocumentType.getId()).get();
        // Disconnect from session so that the updates on updatedCDocumentType are not directly saved in db
        em.detach(updatedCDocumentType);
        updatedCDocumentType
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        CDocumentTypeDTO cDocumentTypeDTO = cDocumentTypeMapper.toDto(updatedCDocumentType);

        restCDocumentTypeMockMvc.perform(put("/api/c-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cDocumentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CDocumentType in the database
        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
        CDocumentType testCDocumentType = cDocumentTypeList.get(cDocumentTypeList.size() - 1);
        assertThat(testCDocumentType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCDocumentType.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCDocumentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = cDocumentTypeRepository.findAll().size();

        // Create the CDocumentType
        CDocumentTypeDTO cDocumentTypeDTO = cDocumentTypeMapper.toDto(cDocumentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCDocumentTypeMockMvc.perform(put("/api/c-document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cDocumentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CDocumentType in the database
        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCDocumentType() throws Exception {
        // Initialize the database
        cDocumentTypeRepository.saveAndFlush(cDocumentType);

        int databaseSizeBeforeDelete = cDocumentTypeRepository.findAll().size();

        // Delete the cDocumentType
        restCDocumentTypeMockMvc.perform(delete("/api/c-document-types/{id}", cDocumentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CDocumentType> cDocumentTypeList = cDocumentTypeRepository.findAll();
        assertThat(cDocumentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
