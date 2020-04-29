package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.ADReferenceList;
import com.bhp.opusb.repository.ADReferenceRepository;
import com.bhp.opusb.service.ADReferenceService;
import com.bhp.opusb.service.dto.ADReferenceDTO;
import com.bhp.opusb.service.mapper.ADReferenceMapper;
import com.bhp.opusb.service.dto.ADReferenceCriteria;
import com.bhp.opusb.service.ADReferenceQueryService;

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

import com.bhp.opusb.domain.enumeration.ADReferenceType;
/**
 * Integration tests for the {@link ADReferenceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADReferenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ADReferenceType DEFAULT_REFERENCE_TYPE = ADReferenceType.LIST;
    private static final ADReferenceType UPDATED_REFERENCE_TYPE = ADReferenceType.TABLE;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADReferenceRepository aDReferenceRepository;

    @Autowired
    private ADReferenceMapper aDReferenceMapper;

    @Autowired
    private ADReferenceService aDReferenceService;

    @Autowired
    private ADReferenceQueryService aDReferenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADReferenceMockMvc;

    private ADReference aDReference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADReference createEntity(EntityManager em) {
        ADReference aDReference = new ADReference()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .referenceType(DEFAULT_REFERENCE_TYPE)
            .active(DEFAULT_ACTIVE);
        return aDReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADReference createUpdatedEntity(EntityManager em) {
        ADReference aDReference = new ADReference()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .referenceType(UPDATED_REFERENCE_TYPE)
            .active(UPDATED_ACTIVE);
        return aDReference;
    }

    @BeforeEach
    public void initTest() {
        aDReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createADReference() throws Exception {
        int databaseSizeBeforeCreate = aDReferenceRepository.findAll().size();

        // Create the ADReference
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(aDReference);
        restADReferenceMockMvc.perform(post("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the ADReference in the database
        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        ADReference testADReference = aDReferenceList.get(aDReferenceList.size() - 1);
        assertThat(testADReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADReference.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testADReference.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADReference.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testADReference.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDReferenceRepository.findAll().size();

        // Create the ADReference with an existing ID
        aDReference.setId(1L);
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(aDReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADReferenceMockMvc.perform(post("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADReference in the database
        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDReferenceRepository.findAll().size();
        // set the field null
        aDReference.setName(null);

        // Create the ADReference, which fails.
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(aDReference);

        restADReferenceMockMvc.perform(post("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDReferenceRepository.findAll().size();
        // set the field null
        aDReference.setValue(null);

        // Create the ADReference, which fails.
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(aDReference);

        restADReferenceMockMvc.perform(post("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADReferences() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList
        restADReferenceMockMvc.perform(get("/api/ad-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADReference() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get the aDReference
        restADReferenceMockMvc.perform(get("/api/ad-references/{id}", aDReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDReference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.referenceType").value(DEFAULT_REFERENCE_TYPE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADReferencesByIdFiltering() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        Long id = aDReference.getId();

        defaultADReferenceShouldBeFound("id.equals=" + id);
        defaultADReferenceShouldNotBeFound("id.notEquals=" + id);

        defaultADReferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADReferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultADReferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADReferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADReferencesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name equals to DEFAULT_NAME
        defaultADReferenceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDReferenceList where name equals to UPDATED_NAME
        defaultADReferenceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferencesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name not equals to DEFAULT_NAME
        defaultADReferenceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDReferenceList where name not equals to UPDATED_NAME
        defaultADReferenceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferencesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADReferenceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDReferenceList where name equals to UPDATED_NAME
        defaultADReferenceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferencesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name is not null
        defaultADReferenceShouldBeFound("name.specified=true");

        // Get all the aDReferenceList where name is null
        defaultADReferenceShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferencesByNameContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name contains DEFAULT_NAME
        defaultADReferenceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDReferenceList where name contains UPDATED_NAME
        defaultADReferenceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferencesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where name does not contain DEFAULT_NAME
        defaultADReferenceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDReferenceList where name does not contain UPDATED_NAME
        defaultADReferenceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADReferencesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value equals to DEFAULT_VALUE
        defaultADReferenceShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the aDReferenceList where value equals to UPDATED_VALUE
        defaultADReferenceShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value not equals to DEFAULT_VALUE
        defaultADReferenceShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the aDReferenceList where value not equals to UPDATED_VALUE
        defaultADReferenceShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultADReferenceShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the aDReferenceList where value equals to UPDATED_VALUE
        defaultADReferenceShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value is not null
        defaultADReferenceShouldBeFound("value.specified=true");

        // Get all the aDReferenceList where value is null
        defaultADReferenceShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferencesByValueContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value contains DEFAULT_VALUE
        defaultADReferenceShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the aDReferenceList where value contains UPDATED_VALUE
        defaultADReferenceShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where value does not contain DEFAULT_VALUE
        defaultADReferenceShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the aDReferenceList where value does not contain UPDATED_VALUE
        defaultADReferenceShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllADReferencesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description equals to DEFAULT_DESCRIPTION
        defaultADReferenceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceList where description equals to UPDATED_DESCRIPTION
        defaultADReferenceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferencesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description not equals to DEFAULT_DESCRIPTION
        defaultADReferenceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceList where description not equals to UPDATED_DESCRIPTION
        defaultADReferenceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferencesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADReferenceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDReferenceList where description equals to UPDATED_DESCRIPTION
        defaultADReferenceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferencesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description is not null
        defaultADReferenceShouldBeFound("description.specified=true");

        // Get all the aDReferenceList where description is null
        defaultADReferenceShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferencesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description contains DEFAULT_DESCRIPTION
        defaultADReferenceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceList where description contains UPDATED_DESCRIPTION
        defaultADReferenceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferencesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where description does not contain DEFAULT_DESCRIPTION
        defaultADReferenceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceList where description does not contain UPDATED_DESCRIPTION
        defaultADReferenceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADReferencesByReferenceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where referenceType equals to DEFAULT_REFERENCE_TYPE
        defaultADReferenceShouldBeFound("referenceType.equals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the aDReferenceList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultADReferenceShouldNotBeFound("referenceType.equals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByReferenceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where referenceType not equals to DEFAULT_REFERENCE_TYPE
        defaultADReferenceShouldNotBeFound("referenceType.notEquals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the aDReferenceList where referenceType not equals to UPDATED_REFERENCE_TYPE
        defaultADReferenceShouldBeFound("referenceType.notEquals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByReferenceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where referenceType in DEFAULT_REFERENCE_TYPE or UPDATED_REFERENCE_TYPE
        defaultADReferenceShouldBeFound("referenceType.in=" + DEFAULT_REFERENCE_TYPE + "," + UPDATED_REFERENCE_TYPE);

        // Get all the aDReferenceList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultADReferenceShouldNotBeFound("referenceType.in=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByReferenceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where referenceType is not null
        defaultADReferenceShouldBeFound("referenceType.specified=true");

        // Get all the aDReferenceList where referenceType is null
        defaultADReferenceShouldNotBeFound("referenceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllADReferencesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where active equals to DEFAULT_ACTIVE
        defaultADReferenceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDReferenceList where active equals to UPDATED_ACTIVE
        defaultADReferenceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where active not equals to DEFAULT_ACTIVE
        defaultADReferenceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDReferenceList where active not equals to UPDATED_ACTIVE
        defaultADReferenceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADReferenceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDReferenceList where active equals to UPDATED_ACTIVE
        defaultADReferenceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferencesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        // Get all the aDReferenceList where active is not null
        defaultADReferenceShouldBeFound("active.specified=true");

        // Get all the aDReferenceList where active is null
        defaultADReferenceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADReferencesByADReferenceListIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);
        ADReferenceList aDReferenceList = ADReferenceListResourceIT.createEntity(em);
        em.persist(aDReferenceList);
        em.flush();
        aDReference.addADReferenceList(aDReferenceList);
        aDReferenceRepository.saveAndFlush(aDReference);
        Long aDReferenceListId = aDReferenceList.getId();

        // Get all the aDReferenceList where aDReferenceList equals to aDReferenceListId
        defaultADReferenceShouldBeFound("aDReferenceListId.equals=" + aDReferenceListId);

        // Get all the aDReferenceList where aDReferenceList equals to aDReferenceListId + 1
        defaultADReferenceShouldNotBeFound("aDReferenceListId.equals=" + (aDReferenceListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADReferenceShouldBeFound(String filter) throws Exception {
        restADReferenceMockMvc.perform(get("/api/ad-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADReferenceMockMvc.perform(get("/api/ad-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADReferenceShouldNotBeFound(String filter) throws Exception {
        restADReferenceMockMvc.perform(get("/api/ad-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADReferenceMockMvc.perform(get("/api/ad-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADReference() throws Exception {
        // Get the aDReference
        restADReferenceMockMvc.perform(get("/api/ad-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADReference() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        int databaseSizeBeforeUpdate = aDReferenceRepository.findAll().size();

        // Update the aDReference
        ADReference updatedADReference = aDReferenceRepository.findById(aDReference.getId()).get();
        // Disconnect from session so that the updates on updatedADReference are not directly saved in db
        em.detach(updatedADReference);
        updatedADReference
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .referenceType(UPDATED_REFERENCE_TYPE)
            .active(UPDATED_ACTIVE);
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(updatedADReference);

        restADReferenceMockMvc.perform(put("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the ADReference in the database
        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeUpdate);
        ADReference testADReference = aDReferenceList.get(aDReferenceList.size() - 1);
        assertThat(testADReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADReference.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testADReference.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADReference.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testADReference.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADReference() throws Exception {
        int databaseSizeBeforeUpdate = aDReferenceRepository.findAll().size();

        // Create the ADReference
        ADReferenceDTO aDReferenceDTO = aDReferenceMapper.toDto(aDReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADReferenceMockMvc.perform(put("/api/ad-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADReference in the database
        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADReference() throws Exception {
        // Initialize the database
        aDReferenceRepository.saveAndFlush(aDReference);

        int databaseSizeBeforeDelete = aDReferenceRepository.findAll().size();

        // Delete the aDReference
        restADReferenceMockMvc.perform(delete("/api/ad-references/{id}", aDReference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADReference> aDReferenceList = aDReferenceRepository.findAll();
        assertThat(aDReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
