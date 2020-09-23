package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CProductClassificationRepository;
import com.bhp.opusb.service.CProductClassificationService;
import com.bhp.opusb.service.dto.CProductClassificationDTO;
import com.bhp.opusb.service.mapper.CProductClassificationMapper;
import com.bhp.opusb.service.dto.CProductClassificationCriteria;
import com.bhp.opusb.service.CProductClassificationQueryService;

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
 * Integration tests for the {@link CProductClassificationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductClassificationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductClassificationRepository cProductClassificationRepository;

    @Autowired
    private CProductClassificationMapper cProductClassificationMapper;

    @Autowired
    private CProductClassificationService cProductClassificationService;

    @Autowired
    private CProductClassificationQueryService cProductClassificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductClassificationMockMvc;

    private CProductClassification cProductClassification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductClassification createEntity(EntityManager em) {
        CProductClassification cProductClassification = new CProductClassification()
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
        cProductClassification.setAdOrganization(aDOrganization);
        return cProductClassification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductClassification createUpdatedEntity(EntityManager em) {
        CProductClassification cProductClassification = new CProductClassification()
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
        cProductClassification.setAdOrganization(aDOrganization);
        return cProductClassification;
    }

    @BeforeEach
    public void initTest() {
        cProductClassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProductClassification() throws Exception {
        int databaseSizeBeforeCreate = cProductClassificationRepository.findAll().size();

        // Create the CProductClassification
        CProductClassificationDTO cProductClassificationDTO = cProductClassificationMapper.toDto(cProductClassification);
        restCProductClassificationMockMvc.perform(post("/api/c-product-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductClassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the CProductClassification in the database
        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        CProductClassification testCProductClassification = cProductClassificationList.get(cProductClassificationList.size() - 1);
        assertThat(testCProductClassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCProductClassification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCProductClassification.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProductClassification.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductClassificationRepository.findAll().size();

        // Create the CProductClassification with an existing ID
        cProductClassification.setId(1L);
        CProductClassificationDTO cProductClassificationDTO = cProductClassificationMapper.toDto(cProductClassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductClassificationMockMvc.perform(post("/api/c-product-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductClassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductClassification in the database
        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductClassificationRepository.findAll().size();
        // set the field null
        cProductClassification.setName(null);

        // Create the CProductClassification, which fails.
        CProductClassificationDTO cProductClassificationDTO = cProductClassificationMapper.toDto(cProductClassification);

        restCProductClassificationMockMvc.perform(post("/api/c-product-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductClassificationDTO)))
            .andExpect(status().isBadRequest());

        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCProductClassifications() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProductClassification() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get the cProductClassification
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications/{id}", cProductClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProductClassification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductClassificationsByIdFiltering() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        Long id = cProductClassification.getId();

        defaultCProductClassificationShouldBeFound("id.equals=" + id);
        defaultCProductClassificationShouldNotBeFound("id.notEquals=" + id);

        defaultCProductClassificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductClassificationShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductClassificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductClassificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductClassificationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name equals to DEFAULT_NAME
        defaultCProductClassificationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cProductClassificationList where name equals to UPDATED_NAME
        defaultCProductClassificationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name not equals to DEFAULT_NAME
        defaultCProductClassificationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cProductClassificationList where name not equals to UPDATED_NAME
        defaultCProductClassificationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCProductClassificationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cProductClassificationList where name equals to UPDATED_NAME
        defaultCProductClassificationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name is not null
        defaultCProductClassificationShouldBeFound("name.specified=true");

        // Get all the cProductClassificationList where name is null
        defaultCProductClassificationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductClassificationsByNameContainsSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name contains DEFAULT_NAME
        defaultCProductClassificationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cProductClassificationList where name contains UPDATED_NAME
        defaultCProductClassificationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where name does not contain DEFAULT_NAME
        defaultCProductClassificationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cProductClassificationList where name does not contain UPDATED_NAME
        defaultCProductClassificationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description equals to DEFAULT_DESCRIPTION
        defaultCProductClassificationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductClassificationList where description equals to UPDATED_DESCRIPTION
        defaultCProductClassificationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description not equals to DEFAULT_DESCRIPTION
        defaultCProductClassificationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductClassificationList where description not equals to UPDATED_DESCRIPTION
        defaultCProductClassificationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCProductClassificationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cProductClassificationList where description equals to UPDATED_DESCRIPTION
        defaultCProductClassificationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description is not null
        defaultCProductClassificationShouldBeFound("description.specified=true");

        // Get all the cProductClassificationList where description is null
        defaultCProductClassificationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description contains DEFAULT_DESCRIPTION
        defaultCProductClassificationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cProductClassificationList where description contains UPDATED_DESCRIPTION
        defaultCProductClassificationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where description does not contain DEFAULT_DESCRIPTION
        defaultCProductClassificationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cProductClassificationList where description does not contain UPDATED_DESCRIPTION
        defaultCProductClassificationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCProductClassificationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where uid equals to DEFAULT_UID
        defaultCProductClassificationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductClassificationList where uid equals to UPDATED_UID
        defaultCProductClassificationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where uid not equals to DEFAULT_UID
        defaultCProductClassificationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductClassificationList where uid not equals to UPDATED_UID
        defaultCProductClassificationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductClassificationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductClassificationList where uid equals to UPDATED_UID
        defaultCProductClassificationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where uid is not null
        defaultCProductClassificationShouldBeFound("uid.specified=true");

        // Get all the cProductClassificationList where uid is null
        defaultCProductClassificationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where active equals to DEFAULT_ACTIVE
        defaultCProductClassificationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductClassificationList where active equals to UPDATED_ACTIVE
        defaultCProductClassificationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where active not equals to DEFAULT_ACTIVE
        defaultCProductClassificationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductClassificationList where active not equals to UPDATED_ACTIVE
        defaultCProductClassificationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductClassificationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductClassificationList where active equals to UPDATED_ACTIVE
        defaultCProductClassificationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        // Get all the cProductClassificationList where active is not null
        defaultCProductClassificationShouldBeFound("active.specified=true");

        // Get all the cProductClassificationList where active is null
        defaultCProductClassificationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductClassificationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProductClassification.getAdOrganization();
        cProductClassificationRepository.saveAndFlush(cProductClassification);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductClassificationList where adOrganization equals to adOrganizationId
        defaultCProductClassificationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductClassificationList where adOrganization equals to adOrganizationId + 1
        defaultCProductClassificationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductClassificationShouldBeFound(String filter) throws Exception {
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductClassificationShouldNotBeFound(String filter) throws Exception {
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProductClassification() throws Exception {
        // Get the cProductClassification
        restCProductClassificationMockMvc.perform(get("/api/c-product-classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProductClassification() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        int databaseSizeBeforeUpdate = cProductClassificationRepository.findAll().size();

        // Update the cProductClassification
        CProductClassification updatedCProductClassification = cProductClassificationRepository.findById(cProductClassification.getId()).get();
        // Disconnect from session so that the updates on updatedCProductClassification are not directly saved in db
        em.detach(updatedCProductClassification);
        updatedCProductClassification
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductClassificationDTO cProductClassificationDTO = cProductClassificationMapper.toDto(updatedCProductClassification);

        restCProductClassificationMockMvc.perform(put("/api/c-product-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductClassificationDTO)))
            .andExpect(status().isOk());

        // Validate the CProductClassification in the database
        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeUpdate);
        CProductClassification testCProductClassification = cProductClassificationList.get(cProductClassificationList.size() - 1);
        assertThat(testCProductClassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCProductClassification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCProductClassification.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProductClassification.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProductClassification() throws Exception {
        int databaseSizeBeforeUpdate = cProductClassificationRepository.findAll().size();

        // Create the CProductClassification
        CProductClassificationDTO cProductClassificationDTO = cProductClassificationMapper.toDto(cProductClassification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductClassificationMockMvc.perform(put("/api/c-product-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductClassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductClassification in the database
        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProductClassification() throws Exception {
        // Initialize the database
        cProductClassificationRepository.saveAndFlush(cProductClassification);

        int databaseSizeBeforeDelete = cProductClassificationRepository.findAll().size();

        // Delete the cProductClassification
        restCProductClassificationMockMvc.perform(delete("/api/c-product-classifications/{id}", cProductClassification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProductClassification> cProductClassificationList = cProductClassificationRepository.findAll();
        assertThat(cProductClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
