package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.repository.BusinessCategoryRepository;
import com.bhp.opusb.service.BusinessCategoryService;
import com.bhp.opusb.service.dto.BusinessCategoryDTO;
import com.bhp.opusb.service.mapper.BusinessCategoryMapper;
import com.bhp.opusb.service.dto.BusinessCategoryCriteria;
import com.bhp.opusb.service.BusinessCategoryQueryService;

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

/**
 * Integration tests for the {@link BusinessCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BusinessCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private BusinessCategoryMapper businessCategoryMapper;

    @Autowired
    private BusinessCategoryService businessCategoryService;

    @Autowired
    private BusinessCategoryQueryService businessCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessCategoryMockMvc;

    private BusinessCategory businessCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessCategory createEntity(EntityManager em) {
        BusinessCategory businessCategory = new BusinessCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return businessCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessCategory createUpdatedEntity(EntityManager em) {
        BusinessCategory businessCategory = new BusinessCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return businessCategory;
    }

    @BeforeEach
    public void initTest() {
        businessCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessCategory() throws Exception {
        int databaseSizeBeforeCreate = businessCategoryRepository.findAll().size();

        // Create the BusinessCategory
        BusinessCategoryDTO businessCategoryDTO = businessCategoryMapper.toDto(businessCategory);
        restBusinessCategoryMockMvc.perform(post("/api/business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessCategory in the database
        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessCategory testBusinessCategory = businessCategoryList.get(businessCategoryList.size() - 1);
        assertThat(testBusinessCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBusinessCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessCategoryRepository.findAll().size();

        // Create the BusinessCategory with an existing ID
        businessCategory.setId(1L);
        BusinessCategoryDTO businessCategoryDTO = businessCategoryMapper.toDto(businessCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessCategoryMockMvc.perform(post("/api/business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessCategory in the database
        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessCategoryRepository.findAll().size();
        // set the field null
        businessCategory.setName(null);

        // Create the BusinessCategory, which fails.
        BusinessCategoryDTO businessCategoryDTO = businessCategoryMapper.toDto(businessCategory);

        restBusinessCategoryMockMvc.perform(post("/api/business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessCategories() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList
        restBusinessCategoryMockMvc.perform(get("/api/business-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getBusinessCategory() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get the businessCategory
        restBusinessCategoryMockMvc.perform(get("/api/business-categories/{id}", businessCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getBusinessCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        Long id = businessCategory.getId();

        defaultBusinessCategoryShouldBeFound("id.equals=" + id);
        defaultBusinessCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name equals to DEFAULT_NAME
        defaultBusinessCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the businessCategoryList where name equals to UPDATED_NAME
        defaultBusinessCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name not equals to DEFAULT_NAME
        defaultBusinessCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the businessCategoryList where name not equals to UPDATED_NAME
        defaultBusinessCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBusinessCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the businessCategoryList where name equals to UPDATED_NAME
        defaultBusinessCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name is not null
        defaultBusinessCategoryShouldBeFound("name.specified=true");

        // Get all the businessCategoryList where name is null
        defaultBusinessCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name contains DEFAULT_NAME
        defaultBusinessCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the businessCategoryList where name contains UPDATED_NAME
        defaultBusinessCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where name does not contain DEFAULT_NAME
        defaultBusinessCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the businessCategoryList where name does not contain UPDATED_NAME
        defaultBusinessCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultBusinessCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the businessCategoryList where description equals to UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultBusinessCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the businessCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the businessCategoryList where description equals to UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description is not null
        defaultBusinessCategoryShouldBeFound("description.specified=true");

        // Get all the businessCategoryList where description is null
        defaultBusinessCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description contains DEFAULT_DESCRIPTION
        defaultBusinessCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the businessCategoryList where description contains UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        // Get all the businessCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultBusinessCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the businessCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultBusinessCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);
        BusinessCategory businessCategory = BusinessCategoryResourceIT.createEntity(em);
        em.persist(businessCategory);
        em.flush();
        businessCategory.addBusinessCategory(businessCategory);
        businessCategoryRepository.saveAndFlush(businessCategory);
        Long businessCategoryId = businessCategory.getId();

        // Get all the businessCategoryList where businessCategory equals to businessCategoryId
        defaultBusinessCategoryShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the businessCategoryList where businessCategory equals to businessCategoryId + 1
        defaultBusinessCategoryShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByDocumentTypeBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);
        DocumentTypeBusinessCategory documentTypeBusinessCategory = DocumentTypeBusinessCategoryResourceIT.createEntity(em);
        em.persist(documentTypeBusinessCategory);
        em.flush();
        businessCategory.addDocumentTypeBusinessCategory(documentTypeBusinessCategory);
        businessCategoryRepository.saveAndFlush(businessCategory);
        Long documentTypeBusinessCategoryId = documentTypeBusinessCategory.getId();

        // Get all the businessCategoryList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId
        defaultBusinessCategoryShouldBeFound("documentTypeBusinessCategoryId.equals=" + documentTypeBusinessCategoryId);

        // Get all the businessCategoryList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId + 1
        defaultBusinessCategoryShouldNotBeFound("documentTypeBusinessCategoryId.equals=" + (documentTypeBusinessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByParentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);
        BusinessCategory parentCategory = BusinessCategoryResourceIT.createEntity(em);
        em.persist(parentCategory);
        em.flush();
        businessCategory.setParentCategory(parentCategory);
        businessCategoryRepository.saveAndFlush(businessCategory);
        Long parentCategoryId = parentCategory.getId();

        // Get all the businessCategoryList where parentCategory equals to parentCategoryId
        defaultBusinessCategoryShouldBeFound("parentCategoryId.equals=" + parentCategoryId);

        // Get all the businessCategoryList where parentCategory equals to parentCategoryId + 1
        defaultBusinessCategoryShouldNotBeFound("parentCategoryId.equals=" + (parentCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessCategoriesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);
        Vendor vendor = VendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        businessCategory.addVendor(vendor);
        businessCategoryRepository.saveAndFlush(businessCategory);
        Long vendorId = vendor.getId();

        // Get all the businessCategoryList where vendor equals to vendorId
        defaultBusinessCategoryShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the businessCategoryList where vendor equals to vendorId + 1
        defaultBusinessCategoryShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessCategoryShouldBeFound(String filter) throws Exception {
        restBusinessCategoryMockMvc.perform(get("/api/business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restBusinessCategoryMockMvc.perform(get("/api/business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessCategoryShouldNotBeFound(String filter) throws Exception {
        restBusinessCategoryMockMvc.perform(get("/api/business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessCategoryMockMvc.perform(get("/api/business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBusinessCategory() throws Exception {
        // Get the businessCategory
        restBusinessCategoryMockMvc.perform(get("/api/business-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessCategory() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        int databaseSizeBeforeUpdate = businessCategoryRepository.findAll().size();

        // Update the businessCategory
        BusinessCategory updatedBusinessCategory = businessCategoryRepository.findById(businessCategory.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessCategory are not directly saved in db
        em.detach(updatedBusinessCategory);
        updatedBusinessCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        BusinessCategoryDTO businessCategoryDTO = businessCategoryMapper.toDto(updatedBusinessCategory);

        restBusinessCategoryMockMvc.perform(put("/api/business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessCategory in the database
        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeUpdate);
        BusinessCategory testBusinessCategory = businessCategoryList.get(businessCategoryList.size() - 1);
        assertThat(testBusinessCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessCategory() throws Exception {
        int databaseSizeBeforeUpdate = businessCategoryRepository.findAll().size();

        // Create the BusinessCategory
        BusinessCategoryDTO businessCategoryDTO = businessCategoryMapper.toDto(businessCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessCategoryMockMvc.perform(put("/api/business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessCategory in the database
        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessCategory() throws Exception {
        // Initialize the database
        businessCategoryRepository.saveAndFlush(businessCategory);

        int databaseSizeBeforeDelete = businessCategoryRepository.findAll().size();

        // Delete the businessCategory
        restBusinessCategoryMockMvc.perform(delete("/api/business-categories/{id}", businessCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessCategory> businessCategoryList = businessCategoryRepository.findAll();
        assertThat(businessCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
