package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.Reference;
import com.bhp.opusb.domain.ReferenceList;
import com.bhp.opusb.repository.ReferenceRepository;
import com.bhp.opusb.service.ReferenceService;
import com.bhp.opusb.service.dto.ReferenceDTO;
import com.bhp.opusb.service.mapper.ReferenceMapper;
import com.bhp.opusb.service.dto.ReferenceCriteria;
import com.bhp.opusb.service.ReferenceQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhp.opusb.domain.enumeration.ReferenceType;
/**
 * Integration tests for the {@link ReferenceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReferenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ReferenceType DEFAULT_REFERENCE_TYPE = ReferenceType.LIST;
    private static final ReferenceType UPDATED_REFERENCE_TYPE = ReferenceType.TABLE;

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private ReferenceMapper referenceMapper;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReferenceQueryService referenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferenceMockMvc;

    private Reference reference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reference createEntity(EntityManager em) {
        Reference reference = new Reference()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .referenceType(DEFAULT_REFERENCE_TYPE);
        return reference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reference createUpdatedEntity(EntityManager em) {
        Reference reference = new Reference()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .referenceType(UPDATED_REFERENCE_TYPE);
        return reference;
    }

    @BeforeEach
    public void initTest() {
        reference = createEntity(em);
    }

    @Test
    @Transactional
    public void createReference() throws Exception {
        int databaseSizeBeforeCreate = referenceRepository.findAll().size();

        // Create the Reference
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);
        restReferenceMockMvc.perform(post("/api/references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeCreate + 1);
        Reference testReference = referenceList.get(referenceList.size() - 1);
        assertThat(testReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReference.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReference.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void createReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenceRepository.findAll().size();

        // Create the Reference with an existing ID
        reference.setId(1L);
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceMockMvc.perform(post("/api/references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceRepository.findAll().size();
        // set the field null
        reference.setName(null);

        // Create the Reference, which fails.
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);

        restReferenceMockMvc.perform(post("/api/references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isBadRequest());

        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferences() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", reference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.referenceType").value(DEFAULT_REFERENCE_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getReferencesByIdFiltering() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        Long id = reference.getId();

        defaultReferenceShouldBeFound("id.equals=" + id);
        defaultReferenceShouldNotBeFound("id.notEquals=" + id);

        defaultReferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultReferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReferencesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name equals to DEFAULT_NAME
        defaultReferenceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the referenceList where name equals to UPDATED_NAME
        defaultReferenceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferencesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name not equals to DEFAULT_NAME
        defaultReferenceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the referenceList where name not equals to UPDATED_NAME
        defaultReferenceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferencesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReferenceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the referenceList where name equals to UPDATED_NAME
        defaultReferenceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferencesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name is not null
        defaultReferenceShouldBeFound("name.specified=true");

        // Get all the referenceList where name is null
        defaultReferenceShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReferencesByNameContainsSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name contains DEFAULT_NAME
        defaultReferenceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the referenceList where name contains UPDATED_NAME
        defaultReferenceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferencesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where name does not contain DEFAULT_NAME
        defaultReferenceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the referenceList where name does not contain UPDATED_NAME
        defaultReferenceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description equals to DEFAULT_DESCRIPTION
        defaultReferenceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the referenceList where description equals to UPDATED_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description not equals to DEFAULT_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the referenceList where description not equals to UPDATED_DESCRIPTION
        defaultReferenceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReferenceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the referenceList where description equals to UPDATED_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description is not null
        defaultReferenceShouldBeFound("description.specified=true");

        // Get all the referenceList where description is null
        defaultReferenceShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllReferencesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description contains DEFAULT_DESCRIPTION
        defaultReferenceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the referenceList where description contains UPDATED_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description does not contain DEFAULT_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the referenceList where description does not contain UPDATED_DESCRIPTION
        defaultReferenceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllReferencesByReferenceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where referenceType equals to DEFAULT_REFERENCE_TYPE
        defaultReferenceShouldBeFound("referenceType.equals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the referenceList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultReferenceShouldNotBeFound("referenceType.equals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllReferencesByReferenceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where referenceType not equals to DEFAULT_REFERENCE_TYPE
        defaultReferenceShouldNotBeFound("referenceType.notEquals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the referenceList where referenceType not equals to UPDATED_REFERENCE_TYPE
        defaultReferenceShouldBeFound("referenceType.notEquals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllReferencesByReferenceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where referenceType in DEFAULT_REFERENCE_TYPE or UPDATED_REFERENCE_TYPE
        defaultReferenceShouldBeFound("referenceType.in=" + DEFAULT_REFERENCE_TYPE + "," + UPDATED_REFERENCE_TYPE);

        // Get all the referenceList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultReferenceShouldNotBeFound("referenceType.in=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllReferencesByReferenceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where referenceType is not null
        defaultReferenceShouldBeFound("referenceType.specified=true");

        // Get all the referenceList where referenceType is null
        defaultReferenceShouldNotBeFound("referenceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferencesByReferenceListIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);
        ReferenceList referenceList = ReferenceListResourceIT.createEntity(em);
        em.persist(referenceList);
        em.flush();
        reference.addReferenceList(referenceList);
        referenceRepository.saveAndFlush(reference);
        Long referenceListId = referenceList.getId();

        // Get all the referenceList where referenceList equals to referenceListId
        defaultReferenceShouldBeFound("referenceListId.equals=" + referenceListId);

        // Get all the referenceList where referenceList equals to referenceListId + 1
        defaultReferenceShouldNotBeFound("referenceListId.equals=" + (referenceListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReferenceShouldBeFound(String filter) throws Exception {
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE.toString())));

        // Check, that the count call also returns 1
        restReferenceMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReferenceShouldNotBeFound(String filter) throws Exception {
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferenceMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReference() throws Exception {
        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        int databaseSizeBeforeUpdate = referenceRepository.findAll().size();

        // Update the reference
        Reference updatedReference = referenceRepository.findById(reference.getId()).get();
        // Disconnect from session so that the updates on updatedReference are not directly saved in db
        em.detach(updatedReference);
        updatedReference
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .referenceType(UPDATED_REFERENCE_TYPE);
        ReferenceDTO referenceDTO = referenceMapper.toDto(updatedReference);

        restReferenceMockMvc.perform(put("/api/references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isOk());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeUpdate);
        Reference testReference = referenceList.get(referenceList.size() - 1);
        assertThat(testReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReference.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReference.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingReference() throws Exception {
        int databaseSizeBeforeUpdate = referenceRepository.findAll().size();

        // Create the Reference
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceMockMvc.perform(put("/api/references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        int databaseSizeBeforeDelete = referenceRepository.findAll().size();

        // Delete the reference
        restReferenceMockMvc.perform(delete("/api/references/{id}", reference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
