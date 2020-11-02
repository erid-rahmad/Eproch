package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CTaxCategoryRepository;
import com.bhp.opusb.service.CTaxCategoryService;
import com.bhp.opusb.service.dto.CTaxCategoryDTO;
import com.bhp.opusb.service.mapper.CTaxCategoryMapper;
import com.bhp.opusb.service.dto.CTaxCategoryCriteria;
import com.bhp.opusb.service.CTaxCategoryQueryService;

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
 * Integration tests for the {@link CTaxCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CTaxCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_WITHHOLDING = false;
    private static final Boolean UPDATED_IS_WITHHOLDING = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CTaxCategoryRepository cTaxCategoryRepository;

    @Autowired
    private CTaxCategoryMapper cTaxCategoryMapper;

    @Autowired
    private CTaxCategoryService cTaxCategoryService;

    @Autowired
    private CTaxCategoryQueryService cTaxCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCTaxCategoryMockMvc;

    private CTaxCategory cTaxCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxCategory createEntity(EntityManager em) {
        CTaxCategory cTaxCategory = new CTaxCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isWithholding(DEFAULT_IS_WITHHOLDING)
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
        cTaxCategory.setAdOrganization(aDOrganization);
        return cTaxCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CTaxCategory createUpdatedEntity(EntityManager em) {
        CTaxCategory cTaxCategory = new CTaxCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isWithholding(UPDATED_IS_WITHHOLDING)
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
        cTaxCategory.setAdOrganization(aDOrganization);
        return cTaxCategory;
    }

    @BeforeEach
    public void initTest() {
        cTaxCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCTaxCategory() throws Exception {
        int databaseSizeBeforeCreate = cTaxCategoryRepository.findAll().size();

        // Create the CTaxCategory
        CTaxCategoryDTO cTaxCategoryDTO = cTaxCategoryMapper.toDto(cTaxCategory);
        restCTaxCategoryMockMvc.perform(post("/api/c-tax-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CTaxCategory in the database
        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CTaxCategory testCTaxCategory = cTaxCategoryList.get(cTaxCategoryList.size() - 1);
        assertThat(testCTaxCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCTaxCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCTaxCategory.isIsWithholding()).isEqualTo(DEFAULT_IS_WITHHOLDING);
        assertThat(testCTaxCategory.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCTaxCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCTaxCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cTaxCategoryRepository.findAll().size();

        // Create the CTaxCategory with an existing ID
        cTaxCategory.setId(1L);
        CTaxCategoryDTO cTaxCategoryDTO = cTaxCategoryMapper.toDto(cTaxCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCTaxCategoryMockMvc.perform(post("/api/c-tax-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxCategory in the database
        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cTaxCategoryRepository.findAll().size();
        // set the field null
        cTaxCategory.setName(null);

        // Create the CTaxCategory, which fails.
        CTaxCategoryDTO cTaxCategoryDTO = cTaxCategoryMapper.toDto(cTaxCategory);

        restCTaxCategoryMockMvc.perform(post("/api/c-tax-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCTaxCategories() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isWithholding").value(hasItem(DEFAULT_IS_WITHHOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCTaxCategory() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get the cTaxCategory
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories/{id}", cTaxCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cTaxCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isWithholding").value(DEFAULT_IS_WITHHOLDING.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCTaxCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        Long id = cTaxCategory.getId();

        defaultCTaxCategoryShouldBeFound("id.equals=" + id);
        defaultCTaxCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCTaxCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCTaxCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCTaxCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCTaxCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCTaxCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name equals to DEFAULT_NAME
        defaultCTaxCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cTaxCategoryList where name equals to UPDATED_NAME
        defaultCTaxCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name not equals to DEFAULT_NAME
        defaultCTaxCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cTaxCategoryList where name not equals to UPDATED_NAME
        defaultCTaxCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCTaxCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cTaxCategoryList where name equals to UPDATED_NAME
        defaultCTaxCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name is not null
        defaultCTaxCategoryShouldBeFound("name.specified=true");

        // Get all the cTaxCategoryList where name is null
        defaultCTaxCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name contains DEFAULT_NAME
        defaultCTaxCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cTaxCategoryList where name contains UPDATED_NAME
        defaultCTaxCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where name does not contain DEFAULT_NAME
        defaultCTaxCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cTaxCategoryList where name does not contain UPDATED_NAME
        defaultCTaxCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultCTaxCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCTaxCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cTaxCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description is not null
        defaultCTaxCategoryShouldBeFound("description.specified=true");

        // Get all the cTaxCategoryList where description is null
        defaultCTaxCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description contains DEFAULT_DESCRIPTION
        defaultCTaxCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxCategoryList where description contains UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCTaxCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cTaxCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultCTaxCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCTaxCategoriesByIsWithholdingIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where isWithholding equals to DEFAULT_IS_WITHHOLDING
        defaultCTaxCategoryShouldBeFound("isWithholding.equals=" + DEFAULT_IS_WITHHOLDING);

        // Get all the cTaxCategoryList where isWithholding equals to UPDATED_IS_WITHHOLDING
        defaultCTaxCategoryShouldNotBeFound("isWithholding.equals=" + UPDATED_IS_WITHHOLDING);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByIsWithholdingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where isWithholding not equals to DEFAULT_IS_WITHHOLDING
        defaultCTaxCategoryShouldNotBeFound("isWithholding.notEquals=" + DEFAULT_IS_WITHHOLDING);

        // Get all the cTaxCategoryList where isWithholding not equals to UPDATED_IS_WITHHOLDING
        defaultCTaxCategoryShouldBeFound("isWithholding.notEquals=" + UPDATED_IS_WITHHOLDING);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByIsWithholdingIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where isWithholding in DEFAULT_IS_WITHHOLDING or UPDATED_IS_WITHHOLDING
        defaultCTaxCategoryShouldBeFound("isWithholding.in=" + DEFAULT_IS_WITHHOLDING + "," + UPDATED_IS_WITHHOLDING);

        // Get all the cTaxCategoryList where isWithholding equals to UPDATED_IS_WITHHOLDING
        defaultCTaxCategoryShouldNotBeFound("isWithholding.in=" + UPDATED_IS_WITHHOLDING);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByIsWithholdingIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where isWithholding is not null
        defaultCTaxCategoryShouldBeFound("isWithholding.specified=true");

        // Get all the cTaxCategoryList where isWithholding is null
        defaultCTaxCategoryShouldNotBeFound("isWithholding.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where uid equals to DEFAULT_UID
        defaultCTaxCategoryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cTaxCategoryList where uid equals to UPDATED_UID
        defaultCTaxCategoryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where uid not equals to DEFAULT_UID
        defaultCTaxCategoryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cTaxCategoryList where uid not equals to UPDATED_UID
        defaultCTaxCategoryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCTaxCategoryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cTaxCategoryList where uid equals to UPDATED_UID
        defaultCTaxCategoryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where uid is not null
        defaultCTaxCategoryShouldBeFound("uid.specified=true");

        // Get all the cTaxCategoryList where uid is null
        defaultCTaxCategoryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where active equals to DEFAULT_ACTIVE
        defaultCTaxCategoryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cTaxCategoryList where active equals to UPDATED_ACTIVE
        defaultCTaxCategoryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where active not equals to DEFAULT_ACTIVE
        defaultCTaxCategoryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cTaxCategoryList where active not equals to UPDATED_ACTIVE
        defaultCTaxCategoryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCTaxCategoryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cTaxCategoryList where active equals to UPDATED_ACTIVE
        defaultCTaxCategoryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        // Get all the cTaxCategoryList where active is not null
        defaultCTaxCategoryShouldBeFound("active.specified=true");

        // Get all the cTaxCategoryList where active is null
        defaultCTaxCategoryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCTaxCategoriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cTaxCategory.getAdOrganization();
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cTaxCategoryList where adOrganization equals to adOrganizationId
        defaultCTaxCategoryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cTaxCategoryList where adOrganization equals to adOrganizationId + 1
        defaultCTaxCategoryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCTaxCategoryShouldBeFound(String filter) throws Exception {
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cTaxCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isWithholding").value(hasItem(DEFAULT_IS_WITHHOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCTaxCategoryShouldNotBeFound(String filter) throws Exception {
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCTaxCategory() throws Exception {
        // Get the cTaxCategory
        restCTaxCategoryMockMvc.perform(get("/api/c-tax-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCTaxCategory() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        int databaseSizeBeforeUpdate = cTaxCategoryRepository.findAll().size();

        // Update the cTaxCategory
        CTaxCategory updatedCTaxCategory = cTaxCategoryRepository.findById(cTaxCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCTaxCategory are not directly saved in db
        em.detach(updatedCTaxCategory);
        updatedCTaxCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isWithholding(UPDATED_IS_WITHHOLDING)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CTaxCategoryDTO cTaxCategoryDTO = cTaxCategoryMapper.toDto(updatedCTaxCategory);

        restCTaxCategoryMockMvc.perform(put("/api/c-tax-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CTaxCategory in the database
        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeUpdate);
        CTaxCategory testCTaxCategory = cTaxCategoryList.get(cTaxCategoryList.size() - 1);
        assertThat(testCTaxCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCTaxCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCTaxCategory.isIsWithholding()).isEqualTo(UPDATED_IS_WITHHOLDING);
        assertThat(testCTaxCategory.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCTaxCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCTaxCategory() throws Exception {
        int databaseSizeBeforeUpdate = cTaxCategoryRepository.findAll().size();

        // Create the CTaxCategory
        CTaxCategoryDTO cTaxCategoryDTO = cTaxCategoryMapper.toDto(cTaxCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCTaxCategoryMockMvc.perform(put("/api/c-tax-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cTaxCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CTaxCategory in the database
        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCTaxCategory() throws Exception {
        // Initialize the database
        cTaxCategoryRepository.saveAndFlush(cTaxCategory);

        int databaseSizeBeforeDelete = cTaxCategoryRepository.findAll().size();

        // Delete the cTaxCategory
        restCTaxCategoryMockMvc.perform(delete("/api/c-tax-categories/{id}", cTaxCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CTaxCategory> cTaxCategoryList = cTaxCategoryRepository.findAll();
        assertThat(cTaxCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
