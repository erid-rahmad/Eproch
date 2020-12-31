package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CGallery;
import com.bhp.opusb.domain.CGalleryItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CGalleryRepository;
import com.bhp.opusb.service.CGalleryService;
import com.bhp.opusb.service.dto.CGalleryDTO;
import com.bhp.opusb.service.mapper.CGalleryMapper;
import com.bhp.opusb.service.dto.CGalleryCriteria;
import com.bhp.opusb.service.CGalleryQueryService;

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
 * Integration tests for the {@link CGalleryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CGalleryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CGalleryRepository cGalleryRepository;

    @Autowired
    private CGalleryMapper cGalleryMapper;

    @Autowired
    private CGalleryService cGalleryService;

    @Autowired
    private CGalleryQueryService cGalleryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCGalleryMockMvc;

    private CGallery cGallery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CGallery createEntity(EntityManager em) {
        CGallery cGallery = new CGallery()
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
        cGallery.setAdOrganization(aDOrganization);
        return cGallery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CGallery createUpdatedEntity(EntityManager em) {
        CGallery cGallery = new CGallery()
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
        cGallery.setAdOrganization(aDOrganization);
        return cGallery;
    }

    @BeforeEach
    public void initTest() {
        cGallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createCGallery() throws Exception {
        int databaseSizeBeforeCreate = cGalleryRepository.findAll().size();
        // Create the CGallery
        CGalleryDTO cGalleryDTO = cGalleryMapper.toDto(cGallery);
        restCGalleryMockMvc.perform(post("/api/c-galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryDTO)))
            .andExpect(status().isCreated());

        // Validate the CGallery in the database
        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeCreate + 1);
        CGallery testCGallery = cGalleryList.get(cGalleryList.size() - 1);
        assertThat(testCGallery.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCGallery.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCGallery.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCGallery.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cGalleryRepository.findAll().size();

        // Create the CGallery with an existing ID
        cGallery.setId(1L);
        CGalleryDTO cGalleryDTO = cGalleryMapper.toDto(cGallery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCGalleryMockMvc.perform(post("/api/c-galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CGallery in the database
        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cGalleryRepository.findAll().size();
        // set the field null
        cGallery.setName(null);

        // Create the CGallery, which fails.
        CGalleryDTO cGalleryDTO = cGalleryMapper.toDto(cGallery);


        restCGalleryMockMvc.perform(post("/api/c-galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryDTO)))
            .andExpect(status().isBadRequest());

        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCGalleries() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList
        restCGalleryMockMvc.perform(get("/api/c-galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cGallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCGallery() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get the cGallery
        restCGalleryMockMvc.perform(get("/api/c-galleries/{id}", cGallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cGallery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCGalleriesByIdFiltering() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        Long id = cGallery.getId();

        defaultCGalleryShouldBeFound("id.equals=" + id);
        defaultCGalleryShouldNotBeFound("id.notEquals=" + id);

        defaultCGalleryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCGalleryShouldNotBeFound("id.greaterThan=" + id);

        defaultCGalleryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCGalleryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCGalleriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name equals to DEFAULT_NAME
        defaultCGalleryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cGalleryList where name equals to UPDATED_NAME
        defaultCGalleryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name not equals to DEFAULT_NAME
        defaultCGalleryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cGalleryList where name not equals to UPDATED_NAME
        defaultCGalleryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCGalleryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cGalleryList where name equals to UPDATED_NAME
        defaultCGalleryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name is not null
        defaultCGalleryShouldBeFound("name.specified=true");

        // Get all the cGalleryList where name is null
        defaultCGalleryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCGalleriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name contains DEFAULT_NAME
        defaultCGalleryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cGalleryList where name contains UPDATED_NAME
        defaultCGalleryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where name does not contain DEFAULT_NAME
        defaultCGalleryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cGalleryList where name does not contain UPDATED_NAME
        defaultCGalleryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCGalleriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description equals to DEFAULT_DESCRIPTION
        defaultCGalleryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cGalleryList where description equals to UPDATED_DESCRIPTION
        defaultCGalleryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description not equals to DEFAULT_DESCRIPTION
        defaultCGalleryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cGalleryList where description not equals to UPDATED_DESCRIPTION
        defaultCGalleryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCGalleryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cGalleryList where description equals to UPDATED_DESCRIPTION
        defaultCGalleryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description is not null
        defaultCGalleryShouldBeFound("description.specified=true");

        // Get all the cGalleryList where description is null
        defaultCGalleryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCGalleriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description contains DEFAULT_DESCRIPTION
        defaultCGalleryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cGalleryList where description contains UPDATED_DESCRIPTION
        defaultCGalleryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where description does not contain DEFAULT_DESCRIPTION
        defaultCGalleryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cGalleryList where description does not contain UPDATED_DESCRIPTION
        defaultCGalleryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCGalleriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where uid equals to DEFAULT_UID
        defaultCGalleryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cGalleryList where uid equals to UPDATED_UID
        defaultCGalleryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where uid not equals to DEFAULT_UID
        defaultCGalleryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cGalleryList where uid not equals to UPDATED_UID
        defaultCGalleryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCGalleryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cGalleryList where uid equals to UPDATED_UID
        defaultCGalleryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where uid is not null
        defaultCGalleryShouldBeFound("uid.specified=true");

        // Get all the cGalleryList where uid is null
        defaultCGalleryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where active equals to DEFAULT_ACTIVE
        defaultCGalleryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cGalleryList where active equals to UPDATED_ACTIVE
        defaultCGalleryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where active not equals to DEFAULT_ACTIVE
        defaultCGalleryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cGalleryList where active not equals to UPDATED_ACTIVE
        defaultCGalleryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCGalleryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cGalleryList where active equals to UPDATED_ACTIVE
        defaultCGalleryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        // Get all the cGalleryList where active is not null
        defaultCGalleryShouldBeFound("active.specified=true");

        // Get all the cGalleryList where active is null
        defaultCGalleryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleriesByCGalleryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);
        CGalleryItem cGalleryItem = CGalleryItemResourceIT.createEntity(em);
        em.persist(cGalleryItem);
        em.flush();
        cGallery.addCGalleryItem(cGalleryItem);
        cGalleryRepository.saveAndFlush(cGallery);
        Long cGalleryItemId = cGalleryItem.getId();

        // Get all the cGalleryList where cGalleryItem equals to cGalleryItemId
        defaultCGalleryShouldBeFound("cGalleryItemId.equals=" + cGalleryItemId);

        // Get all the cGalleryList where cGalleryItem equals to cGalleryItemId + 1
        defaultCGalleryShouldNotBeFound("cGalleryItemId.equals=" + (cGalleryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllCGalleriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cGallery.getAdOrganization();
        cGalleryRepository.saveAndFlush(cGallery);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cGalleryList where adOrganization equals to adOrganizationId
        defaultCGalleryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cGalleryList where adOrganization equals to adOrganizationId + 1
        defaultCGalleryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCGalleryShouldBeFound(String filter) throws Exception {
        restCGalleryMockMvc.perform(get("/api/c-galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cGallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCGalleryMockMvc.perform(get("/api/c-galleries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCGalleryShouldNotBeFound(String filter) throws Exception {
        restCGalleryMockMvc.perform(get("/api/c-galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCGalleryMockMvc.perform(get("/api/c-galleries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCGallery() throws Exception {
        // Get the cGallery
        restCGalleryMockMvc.perform(get("/api/c-galleries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCGallery() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        int databaseSizeBeforeUpdate = cGalleryRepository.findAll().size();

        // Update the cGallery
        CGallery updatedCGallery = cGalleryRepository.findById(cGallery.getId()).get();
        // Disconnect from session so that the updates on updatedCGallery are not directly saved in db
        em.detach(updatedCGallery);
        updatedCGallery
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CGalleryDTO cGalleryDTO = cGalleryMapper.toDto(updatedCGallery);

        restCGalleryMockMvc.perform(put("/api/c-galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryDTO)))
            .andExpect(status().isOk());

        // Validate the CGallery in the database
        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeUpdate);
        CGallery testCGallery = cGalleryList.get(cGalleryList.size() - 1);
        assertThat(testCGallery.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCGallery.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCGallery.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCGallery.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCGallery() throws Exception {
        int databaseSizeBeforeUpdate = cGalleryRepository.findAll().size();

        // Create the CGallery
        CGalleryDTO cGalleryDTO = cGalleryMapper.toDto(cGallery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCGalleryMockMvc.perform(put("/api/c-galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CGallery in the database
        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCGallery() throws Exception {
        // Initialize the database
        cGalleryRepository.saveAndFlush(cGallery);

        int databaseSizeBeforeDelete = cGalleryRepository.findAll().size();

        // Delete the cGallery
        restCGalleryMockMvc.perform(delete("/api/c-galleries/{id}", cGallery.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CGallery> cGalleryList = cGalleryRepository.findAll();
        assertThat(cGalleryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
