package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CBusinessCategoryRepository;
import com.bhp.opusb.service.CBusinessCategoryService;
import com.bhp.opusb.service.dto.CBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CBusinessCategoryMapper;
import com.bhp.opusb.service.dto.CBusinessCategoryCriteria;
import com.bhp.opusb.service.CBusinessCategoryQueryService;

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

import com.bhp.opusb.domain.enumeration.CBusinessCategorySector;
/**
 * Integration tests for the {@link CBusinessCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBusinessCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CBusinessCategorySector DEFAULT_SECTOR = CBusinessCategorySector.PRIMARY;
    private static final CBusinessCategorySector UPDATED_SECTOR = CBusinessCategorySector.SECONDARY;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBusinessCategoryRepository cBusinessCategoryRepository;

    @Autowired
    private CBusinessCategoryMapper cBusinessCategoryMapper;

    @Autowired
    private CBusinessCategoryService cBusinessCategoryService;

    @Autowired
    private CBusinessCategoryQueryService cBusinessCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBusinessCategoryMockMvc;

    private CBusinessCategory cBusinessCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBusinessCategory createEntity(EntityManager em) {
        CBusinessCategory cBusinessCategory = new CBusinessCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sector(DEFAULT_SECTOR)
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
        cBusinessCategory.setAdOrganization(aDOrganization);
        return cBusinessCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBusinessCategory createUpdatedEntity(EntityManager em) {
        CBusinessCategory cBusinessCategory = new CBusinessCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sector(UPDATED_SECTOR)
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
        cBusinessCategory.setAdOrganization(aDOrganization);
        return cBusinessCategory;
    }

    @BeforeEach
    public void initTest() {
        cBusinessCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBusinessCategory() throws Exception {
        int databaseSizeBeforeCreate = cBusinessCategoryRepository.findAll().size();

        // Create the CBusinessCategory
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(cBusinessCategory);
        restCBusinessCategoryMockMvc.perform(post("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CBusinessCategory in the database
        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CBusinessCategory testCBusinessCategory = cBusinessCategoryList.get(cBusinessCategoryList.size() - 1);
        assertThat(testCBusinessCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBusinessCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBusinessCategory.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testCBusinessCategory.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBusinessCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBusinessCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBusinessCategoryRepository.findAll().size();

        // Create the CBusinessCategory with an existing ID
        cBusinessCategory.setId(1L);
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(cBusinessCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBusinessCategoryMockMvc.perform(post("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBusinessCategory in the database
        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBusinessCategoryRepository.findAll().size();
        // set the field null
        cBusinessCategory.setName(null);

        // Create the CBusinessCategory, which fails.
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(cBusinessCategory);

        restCBusinessCategoryMockMvc.perform(post("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSectorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBusinessCategoryRepository.findAll().size();
        // set the field null
        cBusinessCategory.setSector(null);

        // Create the CBusinessCategory, which fails.
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(cBusinessCategory);

        restCBusinessCategoryMockMvc.perform(post("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategories() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBusinessCategory() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get the cBusinessCategory
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories/{id}", cBusinessCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBusinessCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBusinessCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        Long id = cBusinessCategory.getId();

        defaultCBusinessCategoryShouldBeFound("id.equals=" + id);
        defaultCBusinessCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCBusinessCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBusinessCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCBusinessCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBusinessCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name equals to DEFAULT_NAME
        defaultCBusinessCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBusinessCategoryList where name equals to UPDATED_NAME
        defaultCBusinessCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name not equals to DEFAULT_NAME
        defaultCBusinessCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBusinessCategoryList where name not equals to UPDATED_NAME
        defaultCBusinessCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBusinessCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBusinessCategoryList where name equals to UPDATED_NAME
        defaultCBusinessCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name is not null
        defaultCBusinessCategoryShouldBeFound("name.specified=true");

        // Get all the cBusinessCategoryList where name is null
        defaultCBusinessCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name contains DEFAULT_NAME
        defaultCBusinessCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBusinessCategoryList where name contains UPDATED_NAME
        defaultCBusinessCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where name does not contain DEFAULT_NAME
        defaultCBusinessCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBusinessCategoryList where name does not contain UPDATED_NAME
        defaultCBusinessCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultCBusinessCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBusinessCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCBusinessCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBusinessCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBusinessCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description is not null
        defaultCBusinessCategoryShouldBeFound("description.specified=true");

        // Get all the cBusinessCategoryList where description is null
        defaultCBusinessCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description contains DEFAULT_DESCRIPTION
        defaultCBusinessCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBusinessCategoryList where description contains UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCBusinessCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBusinessCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultCBusinessCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBusinessCategoriesBySectorIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where sector equals to DEFAULT_SECTOR
        defaultCBusinessCategoryShouldBeFound("sector.equals=" + DEFAULT_SECTOR);

        // Get all the cBusinessCategoryList where sector equals to UPDATED_SECTOR
        defaultCBusinessCategoryShouldNotBeFound("sector.equals=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesBySectorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where sector not equals to DEFAULT_SECTOR
        defaultCBusinessCategoryShouldNotBeFound("sector.notEquals=" + DEFAULT_SECTOR);

        // Get all the cBusinessCategoryList where sector not equals to UPDATED_SECTOR
        defaultCBusinessCategoryShouldBeFound("sector.notEquals=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesBySectorIsInShouldWork() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where sector in DEFAULT_SECTOR or UPDATED_SECTOR
        defaultCBusinessCategoryShouldBeFound("sector.in=" + DEFAULT_SECTOR + "," + UPDATED_SECTOR);

        // Get all the cBusinessCategoryList where sector equals to UPDATED_SECTOR
        defaultCBusinessCategoryShouldNotBeFound("sector.in=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesBySectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where sector is not null
        defaultCBusinessCategoryShouldBeFound("sector.specified=true");

        // Get all the cBusinessCategoryList where sector is null
        defaultCBusinessCategoryShouldNotBeFound("sector.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where uid equals to DEFAULT_UID
        defaultCBusinessCategoryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBusinessCategoryList where uid equals to UPDATED_UID
        defaultCBusinessCategoryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where uid not equals to DEFAULT_UID
        defaultCBusinessCategoryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBusinessCategoryList where uid not equals to UPDATED_UID
        defaultCBusinessCategoryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBusinessCategoryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBusinessCategoryList where uid equals to UPDATED_UID
        defaultCBusinessCategoryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where uid is not null
        defaultCBusinessCategoryShouldBeFound("uid.specified=true");

        // Get all the cBusinessCategoryList where uid is null
        defaultCBusinessCategoryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where active equals to DEFAULT_ACTIVE
        defaultCBusinessCategoryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBusinessCategoryList where active equals to UPDATED_ACTIVE
        defaultCBusinessCategoryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where active not equals to DEFAULT_ACTIVE
        defaultCBusinessCategoryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBusinessCategoryList where active not equals to UPDATED_ACTIVE
        defaultCBusinessCategoryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBusinessCategoryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBusinessCategoryList where active equals to UPDATED_ACTIVE
        defaultCBusinessCategoryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        // Get all the cBusinessCategoryList where active is not null
        defaultCBusinessCategoryShouldBeFound("active.specified=true");

        // Get all the cBusinessCategoryList where active is null
        defaultCBusinessCategoryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBusinessCategoriesByCBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);
        CBusinessCategory cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(cBusinessCategory);
        em.flush();
        cBusinessCategory.addCBusinessCategory(cBusinessCategory);
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);
        Long cBusinessCategoryId = cBusinessCategory.getId();

        // Get all the cBusinessCategoryList where cBusinessCategory equals to cBusinessCategoryId
        defaultCBusinessCategoryShouldBeFound("cBusinessCategoryId.equals=" + cBusinessCategoryId);

        // Get all the cBusinessCategoryList where cBusinessCategory equals to cBusinessCategoryId + 1
        defaultCBusinessCategoryShouldNotBeFound("cBusinessCategoryId.equals=" + (cBusinessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCBusinessCategoriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBusinessCategory.getAdOrganization();
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBusinessCategoryList where adOrganization equals to adOrganizationId
        defaultCBusinessCategoryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBusinessCategoryList where adOrganization equals to adOrganizationId + 1
        defaultCBusinessCategoryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBusinessCategoriesByParentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);
        CBusinessCategory parentCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(parentCategory);
        em.flush();
        cBusinessCategory.setParentCategory(parentCategory);
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);
        Long parentCategoryId = parentCategory.getId();

        // Get all the cBusinessCategoryList where parentCategory equals to parentCategoryId
        defaultCBusinessCategoryShouldBeFound("parentCategoryId.equals=" + parentCategoryId);

        // Get all the cBusinessCategoryList where parentCategory equals to parentCategoryId + 1
        defaultCBusinessCategoryShouldNotBeFound("parentCategoryId.equals=" + (parentCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBusinessCategoryShouldBeFound(String filter) throws Exception {
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBusinessCategoryShouldNotBeFound(String filter) throws Exception {
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBusinessCategory() throws Exception {
        // Get the cBusinessCategory
        restCBusinessCategoryMockMvc.perform(get("/api/c-business-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBusinessCategory() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        int databaseSizeBeforeUpdate = cBusinessCategoryRepository.findAll().size();

        // Update the cBusinessCategory
        CBusinessCategory updatedCBusinessCategory = cBusinessCategoryRepository.findById(cBusinessCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCBusinessCategory are not directly saved in db
        em.detach(updatedCBusinessCategory);
        updatedCBusinessCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sector(UPDATED_SECTOR)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(updatedCBusinessCategory);

        restCBusinessCategoryMockMvc.perform(put("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CBusinessCategory in the database
        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
        CBusinessCategory testCBusinessCategory = cBusinessCategoryList.get(cBusinessCategoryList.size() - 1);
        assertThat(testCBusinessCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBusinessCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBusinessCategory.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testCBusinessCategory.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBusinessCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBusinessCategory() throws Exception {
        int databaseSizeBeforeUpdate = cBusinessCategoryRepository.findAll().size();

        // Create the CBusinessCategory
        CBusinessCategoryDTO cBusinessCategoryDTO = cBusinessCategoryMapper.toDto(cBusinessCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBusinessCategoryMockMvc.perform(put("/api/c-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBusinessCategory in the database
        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBusinessCategory() throws Exception {
        // Initialize the database
        cBusinessCategoryRepository.saveAndFlush(cBusinessCategory);

        int databaseSizeBeforeDelete = cBusinessCategoryRepository.findAll().size();

        // Delete the cBusinessCategory
        restCBusinessCategoryMockMvc.perform(delete("/api/c-business-categories/{id}", cBusinessCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBusinessCategory> cBusinessCategoryList = cBusinessCategoryRepository.findAll();
        assertThat(cBusinessCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
