package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CProductCategoryRepository;
import com.bhp.opusb.service.CProductCategoryService;
import com.bhp.opusb.service.dto.CProductCategoryDTO;
import com.bhp.opusb.service.mapper.CProductCategoryMapper;
import com.bhp.opusb.service.dto.CProductCategoryCriteria;
import com.bhp.opusb.service.CProductCategoryQueryService;

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
 * Integration tests for the {@link CProductCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductCategoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductCategoryRepository cProductCategoryRepository;

    @Autowired
    private CProductCategoryMapper cProductCategoryMapper;

    @Autowired
    private CProductCategoryService cProductCategoryService;

    @Autowired
    private CProductCategoryQueryService cProductCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductCategoryMockMvc;

    private CProductCategory cProductCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductCategory createEntity(EntityManager em) {
        CProductCategory cProductCategory = new CProductCategory()
            .code(DEFAULT_CODE)
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
        cProductCategory.setAdOrganization(aDOrganization);
        return cProductCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductCategory createUpdatedEntity(EntityManager em) {
        CProductCategory cProductCategory = new CProductCategory()
            .code(UPDATED_CODE)
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
        cProductCategory.setAdOrganization(aDOrganization);
        return cProductCategory;
    }

    @BeforeEach
    public void initTest() {
        cProductCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProductCategory() throws Exception {
        int databaseSizeBeforeCreate = cProductCategoryRepository.findAll().size();

        // Create the CProductCategory
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(cProductCategory);
        restCProductCategoryMockMvc.perform(post("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CProductCategory in the database
        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CProductCategory testCProductCategory = cProductCategoryList.get(cProductCategoryList.size() - 1);
        assertThat(testCProductCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCProductCategory.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProductCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductCategoryRepository.findAll().size();

        // Create the CProductCategory with an existing ID
        cProductCategory.setId(1L);
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(cProductCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductCategoryMockMvc.perform(post("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductCategory in the database
        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductCategoryRepository.findAll().size();
        // set the field null
        cProductCategory.setCode(null);

        // Create the CProductCategory, which fails.
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(cProductCategory);

        restCProductCategoryMockMvc.perform(post("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductCategoryRepository.findAll().size();
        // set the field null
        cProductCategory.setName(null);

        // Create the CProductCategory, which fails.
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(cProductCategory);

        restCProductCategoryMockMvc.perform(post("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCProductCategories() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProductCategory() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get the cProductCategory
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories/{id}", cProductCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProductCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        Long id = cProductCategory.getId();

        defaultCProductCategoryShouldBeFound("id.equals=" + id);
        defaultCProductCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCProductCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code equals to DEFAULT_CODE
        defaultCProductCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cProductCategoryList where code equals to UPDATED_CODE
        defaultCProductCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code not equals to DEFAULT_CODE
        defaultCProductCategoryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cProductCategoryList where code not equals to UPDATED_CODE
        defaultCProductCategoryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCProductCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cProductCategoryList where code equals to UPDATED_CODE
        defaultCProductCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code is not null
        defaultCProductCategoryShouldBeFound("code.specified=true");

        // Get all the cProductCategoryList where code is null
        defaultCProductCategoryShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductCategoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code contains DEFAULT_CODE
        defaultCProductCategoryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cProductCategoryList where code contains UPDATED_CODE
        defaultCProductCategoryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where code does not contain DEFAULT_CODE
        defaultCProductCategoryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cProductCategoryList where code does not contain UPDATED_CODE
        defaultCProductCategoryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name equals to DEFAULT_NAME
        defaultCProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cProductCategoryList where name equals to UPDATED_NAME
        defaultCProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name not equals to DEFAULT_NAME
        defaultCProductCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cProductCategoryList where name not equals to UPDATED_NAME
        defaultCProductCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cProductCategoryList where name equals to UPDATED_NAME
        defaultCProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name is not null
        defaultCProductCategoryShouldBeFound("name.specified=true");

        // Get all the cProductCategoryList where name is null
        defaultCProductCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name contains DEFAULT_NAME
        defaultCProductCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cProductCategoryList where name contains UPDATED_NAME
        defaultCProductCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where name does not contain DEFAULT_NAME
        defaultCProductCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cProductCategoryList where name does not contain UPDATED_NAME
        defaultCProductCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultCProductCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCProductCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCProductCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultCProductCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCProductCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cProductCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCProductCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description is not null
        defaultCProductCategoryShouldBeFound("description.specified=true");

        // Get all the cProductCategoryList where description is null
        defaultCProductCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description contains DEFAULT_DESCRIPTION
        defaultCProductCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cProductCategoryList where description contains UPDATED_DESCRIPTION
        defaultCProductCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCProductCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cProductCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultCProductCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where uid equals to DEFAULT_UID
        defaultCProductCategoryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductCategoryList where uid equals to UPDATED_UID
        defaultCProductCategoryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where uid not equals to DEFAULT_UID
        defaultCProductCategoryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductCategoryList where uid not equals to UPDATED_UID
        defaultCProductCategoryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductCategoryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductCategoryList where uid equals to UPDATED_UID
        defaultCProductCategoryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where uid is not null
        defaultCProductCategoryShouldBeFound("uid.specified=true");

        // Get all the cProductCategoryList where uid is null
        defaultCProductCategoryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where active equals to DEFAULT_ACTIVE
        defaultCProductCategoryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductCategoryList where active equals to UPDATED_ACTIVE
        defaultCProductCategoryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where active not equals to DEFAULT_ACTIVE
        defaultCProductCategoryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductCategoryList where active not equals to UPDATED_ACTIVE
        defaultCProductCategoryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductCategoryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductCategoryList where active equals to UPDATED_ACTIVE
        defaultCProductCategoryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        // Get all the cProductCategoryList where active is not null
        defaultCProductCategoryShouldBeFound("active.specified=true");

        // Get all the cProductCategoryList where active is null
        defaultCProductCategoryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductCategoriesByCProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);
        CProductCategory cProductCategory = CProductCategoryResourceIT.createEntity(em);
        em.persist(cProductCategory);
        em.flush();
        cProductCategory.addCProductCategory(cProductCategory);
        cProductCategoryRepository.saveAndFlush(cProductCategory);
        Long cProductCategoryId = cProductCategory.getId();

        // Get all the cProductCategoryList where cProductCategory equals to cProductCategoryId
        defaultCProductCategoryShouldBeFound("cProductCategoryId.equals=" + cProductCategoryId);

        // Get all the cProductCategoryList where cProductCategory equals to cProductCategoryId + 1
        defaultCProductCategoryShouldNotBeFound("cProductCategoryId.equals=" + (cProductCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProductCategory.getAdOrganization();
        cProductCategoryRepository.saveAndFlush(cProductCategory);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductCategoryList where adOrganization equals to adOrganizationId
        defaultCProductCategoryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductCategoryList where adOrganization equals to adOrganizationId + 1
        defaultCProductCategoryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductCategoriesByParentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);
        CProductCategory parentCategory = CProductCategoryResourceIT.createEntity(em);
        em.persist(parentCategory);
        em.flush();
        cProductCategory.setParentCategory(parentCategory);
        cProductCategoryRepository.saveAndFlush(cProductCategory);
        Long parentCategoryId = parentCategory.getId();

        // Get all the cProductCategoryList where parentCategory equals to parentCategoryId
        defaultCProductCategoryShouldBeFound("parentCategoryId.equals=" + parentCategoryId);

        // Get all the cProductCategoryList where parentCategory equals to parentCategoryId + 1
        defaultCProductCategoryShouldNotBeFound("parentCategoryId.equals=" + (parentCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductCategoryShouldBeFound(String filter) throws Exception {
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductCategoryShouldNotBeFound(String filter) throws Exception {
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProductCategory() throws Exception {
        // Get the cProductCategory
        restCProductCategoryMockMvc.perform(get("/api/c-product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProductCategory() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        int databaseSizeBeforeUpdate = cProductCategoryRepository.findAll().size();

        // Update the cProductCategory
        CProductCategory updatedCProductCategory = cProductCategoryRepository.findById(cProductCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCProductCategory are not directly saved in db
        em.detach(updatedCProductCategory);
        updatedCProductCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(updatedCProductCategory);

        restCProductCategoryMockMvc.perform(put("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CProductCategory in the database
        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeUpdate);
        CProductCategory testCProductCategory = cProductCategoryList.get(cProductCategoryList.size() - 1);
        assertThat(testCProductCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCProductCategory.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProductCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = cProductCategoryRepository.findAll().size();

        // Create the CProductCategory
        CProductCategoryDTO cProductCategoryDTO = cProductCategoryMapper.toDto(cProductCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductCategoryMockMvc.perform(put("/api/c-product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductCategory in the database
        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProductCategory() throws Exception {
        // Initialize the database
        cProductCategoryRepository.saveAndFlush(cProductCategory);

        int databaseSizeBeforeDelete = cProductCategoryRepository.findAll().size();

        // Delete the cProductCategory
        restCProductCategoryMockMvc.perform(delete("/api/c-product-categories/{id}", cProductCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProductCategory> cProductCategoryList = cProductCategoryRepository.findAll();
        assertThat(cProductCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
