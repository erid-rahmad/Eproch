package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CQuestionCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CQuestionCategoryRepository;
import com.bhp.opusb.service.CQuestionCategoryService;
import com.bhp.opusb.service.dto.CQuestionCategoryDTO;
import com.bhp.opusb.service.mapper.CQuestionCategoryMapper;
import com.bhp.opusb.service.dto.CQuestionCategoryCriteria;
import com.bhp.opusb.service.CQuestionCategoryQueryService;

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
 * Integration tests for the {@link CQuestionCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CQuestionCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CQuestionCategoryRepository cQuestionCategoryRepository;

    @Autowired
    private CQuestionCategoryMapper cQuestionCategoryMapper;

    @Autowired
    private CQuestionCategoryService cQuestionCategoryService;

    @Autowired
    private CQuestionCategoryQueryService cQuestionCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCQuestionCategoryMockMvc;

    private CQuestionCategory cQuestionCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CQuestionCategory createEntity(EntityManager em) {
        CQuestionCategory cQuestionCategory = new CQuestionCategory()
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
        cQuestionCategory.setAdOrganization(aDOrganization);
        return cQuestionCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CQuestionCategory createUpdatedEntity(EntityManager em) {
        CQuestionCategory cQuestionCategory = new CQuestionCategory()
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
        cQuestionCategory.setAdOrganization(aDOrganization);
        return cQuestionCategory;
    }

    @BeforeEach
    public void initTest() {
        cQuestionCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCQuestionCategory() throws Exception {
        int databaseSizeBeforeCreate = cQuestionCategoryRepository.findAll().size();

        // Create the CQuestionCategory
        CQuestionCategoryDTO cQuestionCategoryDTO = cQuestionCategoryMapper.toDto(cQuestionCategory);
        restCQuestionCategoryMockMvc.perform(post("/api/c-question-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cQuestionCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CQuestionCategory in the database
        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CQuestionCategory testCQuestionCategory = cQuestionCategoryList.get(cQuestionCategoryList.size() - 1);
        assertThat(testCQuestionCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCQuestionCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCQuestionCategory.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCQuestionCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCQuestionCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cQuestionCategoryRepository.findAll().size();

        // Create the CQuestionCategory with an existing ID
        cQuestionCategory.setId(1L);
        CQuestionCategoryDTO cQuestionCategoryDTO = cQuestionCategoryMapper.toDto(cQuestionCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCQuestionCategoryMockMvc.perform(post("/api/c-question-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cQuestionCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CQuestionCategory in the database
        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cQuestionCategoryRepository.findAll().size();
        // set the field null
        cQuestionCategory.setName(null);

        // Create the CQuestionCategory, which fails.
        CQuestionCategoryDTO cQuestionCategoryDTO = cQuestionCategoryMapper.toDto(cQuestionCategory);

        restCQuestionCategoryMockMvc.perform(post("/api/c-question-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cQuestionCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategories() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cQuestionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCQuestionCategory() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get the cQuestionCategory
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories/{id}", cQuestionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cQuestionCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCQuestionCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        Long id = cQuestionCategory.getId();

        defaultCQuestionCategoryShouldBeFound("id.equals=" + id);
        defaultCQuestionCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCQuestionCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCQuestionCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCQuestionCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCQuestionCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name equals to DEFAULT_NAME
        defaultCQuestionCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cQuestionCategoryList where name equals to UPDATED_NAME
        defaultCQuestionCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name not equals to DEFAULT_NAME
        defaultCQuestionCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cQuestionCategoryList where name not equals to UPDATED_NAME
        defaultCQuestionCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCQuestionCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cQuestionCategoryList where name equals to UPDATED_NAME
        defaultCQuestionCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name is not null
        defaultCQuestionCategoryShouldBeFound("name.specified=true");

        // Get all the cQuestionCategoryList where name is null
        defaultCQuestionCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name contains DEFAULT_NAME
        defaultCQuestionCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cQuestionCategoryList where name contains UPDATED_NAME
        defaultCQuestionCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where name does not contain DEFAULT_NAME
        defaultCQuestionCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cQuestionCategoryList where name does not contain UPDATED_NAME
        defaultCQuestionCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultCQuestionCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cQuestionCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCQuestionCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cQuestionCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cQuestionCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description is not null
        defaultCQuestionCategoryShouldBeFound("description.specified=true");

        // Get all the cQuestionCategoryList where description is null
        defaultCQuestionCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description contains DEFAULT_DESCRIPTION
        defaultCQuestionCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cQuestionCategoryList where description contains UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCQuestionCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cQuestionCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultCQuestionCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCQuestionCategoriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where uid equals to DEFAULT_UID
        defaultCQuestionCategoryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cQuestionCategoryList where uid equals to UPDATED_UID
        defaultCQuestionCategoryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where uid not equals to DEFAULT_UID
        defaultCQuestionCategoryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cQuestionCategoryList where uid not equals to UPDATED_UID
        defaultCQuestionCategoryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCQuestionCategoryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cQuestionCategoryList where uid equals to UPDATED_UID
        defaultCQuestionCategoryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where uid is not null
        defaultCQuestionCategoryShouldBeFound("uid.specified=true");

        // Get all the cQuestionCategoryList where uid is null
        defaultCQuestionCategoryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where active equals to DEFAULT_ACTIVE
        defaultCQuestionCategoryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cQuestionCategoryList where active equals to UPDATED_ACTIVE
        defaultCQuestionCategoryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where active not equals to DEFAULT_ACTIVE
        defaultCQuestionCategoryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cQuestionCategoryList where active not equals to UPDATED_ACTIVE
        defaultCQuestionCategoryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCQuestionCategoryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cQuestionCategoryList where active equals to UPDATED_ACTIVE
        defaultCQuestionCategoryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        // Get all the cQuestionCategoryList where active is not null
        defaultCQuestionCategoryShouldBeFound("active.specified=true");

        // Get all the cQuestionCategoryList where active is null
        defaultCQuestionCategoryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCQuestionCategoriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cQuestionCategory.getAdOrganization();
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cQuestionCategoryList where adOrganization equals to adOrganizationId
        defaultCQuestionCategoryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cQuestionCategoryList where adOrganization equals to adOrganizationId + 1
        defaultCQuestionCategoryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCQuestionCategoryShouldBeFound(String filter) throws Exception {
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cQuestionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCQuestionCategoryShouldNotBeFound(String filter) throws Exception {
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCQuestionCategory() throws Exception {
        // Get the cQuestionCategory
        restCQuestionCategoryMockMvc.perform(get("/api/c-question-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCQuestionCategory() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        int databaseSizeBeforeUpdate = cQuestionCategoryRepository.findAll().size();

        // Update the cQuestionCategory
        CQuestionCategory updatedCQuestionCategory = cQuestionCategoryRepository.findById(cQuestionCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCQuestionCategory are not directly saved in db
        em.detach(updatedCQuestionCategory);
        updatedCQuestionCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CQuestionCategoryDTO cQuestionCategoryDTO = cQuestionCategoryMapper.toDto(updatedCQuestionCategory);

        restCQuestionCategoryMockMvc.perform(put("/api/c-question-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cQuestionCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CQuestionCategory in the database
        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeUpdate);
        CQuestionCategory testCQuestionCategory = cQuestionCategoryList.get(cQuestionCategoryList.size() - 1);
        assertThat(testCQuestionCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCQuestionCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCQuestionCategory.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCQuestionCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCQuestionCategory() throws Exception {
        int databaseSizeBeforeUpdate = cQuestionCategoryRepository.findAll().size();

        // Create the CQuestionCategory
        CQuestionCategoryDTO cQuestionCategoryDTO = cQuestionCategoryMapper.toDto(cQuestionCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCQuestionCategoryMockMvc.perform(put("/api/c-question-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cQuestionCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CQuestionCategory in the database
        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCQuestionCategory() throws Exception {
        // Initialize the database
        cQuestionCategoryRepository.saveAndFlush(cQuestionCategory);

        int databaseSizeBeforeDelete = cQuestionCategoryRepository.findAll().size();

        // Delete the cQuestionCategory
        restCQuestionCategoryMockMvc.perform(delete("/api/c-question-categories/{id}", cQuestionCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CQuestionCategory> cQuestionCategoryList = cQuestionCategoryRepository.findAll();
        assertThat(cQuestionCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
