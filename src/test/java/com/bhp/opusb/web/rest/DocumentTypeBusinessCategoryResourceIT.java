package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.domain.DocumentType;
import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;
import com.bhp.opusb.service.DocumentTypeBusinessCategoryService;
import com.bhp.opusb.service.dto.DocumentTypeBusinessCategoryCriteria;
import com.bhp.opusb.service.DocumentTypeBusinessCategoryQueryService;

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
 * Integration tests for the {@link DocumentTypeBusinessCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DocumentTypeBusinessCategoryResourceIT {

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final Boolean DEFAULT_ADDITIONAL = false;
    private static final Boolean UPDATED_ADDITIONAL = true;

    @Autowired
    private DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository;

    @Autowired
    private DocumentTypeBusinessCategoryService documentTypeBusinessCategoryService;

    @Autowired
    private DocumentTypeBusinessCategoryQueryService documentTypeBusinessCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentTypeBusinessCategoryMockMvc;

    private DocumentTypeBusinessCategory documentTypeBusinessCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentTypeBusinessCategory createEntity(EntityManager em) {
        DocumentTypeBusinessCategory documentTypeBusinessCategory = new DocumentTypeBusinessCategory()
            .mandatory(DEFAULT_MANDATORY)
            .additional(DEFAULT_ADDITIONAL);
        return documentTypeBusinessCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentTypeBusinessCategory createUpdatedEntity(EntityManager em) {
        DocumentTypeBusinessCategory documentTypeBusinessCategory = new DocumentTypeBusinessCategory()
            .mandatory(UPDATED_MANDATORY)
            .additional(UPDATED_ADDITIONAL);
        return documentTypeBusinessCategory;
    }

    @BeforeEach
    public void initTest() {
        documentTypeBusinessCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTypeBusinessCategory() throws Exception {
        int databaseSizeBeforeCreate = documentTypeBusinessCategoryRepository.findAll().size();

        // Create the DocumentTypeBusinessCategory
        restDocumentTypeBusinessCategoryMockMvc.perform(post("/api/document-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeBusinessCategory)))
            .andExpect(status().isCreated());

        // Validate the DocumentTypeBusinessCategory in the database
        List<DocumentTypeBusinessCategory> documentTypeBusinessCategoryList = documentTypeBusinessCategoryRepository.findAll();
        assertThat(documentTypeBusinessCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTypeBusinessCategory testDocumentTypeBusinessCategory = documentTypeBusinessCategoryList.get(documentTypeBusinessCategoryList.size() - 1);
        assertThat(testDocumentTypeBusinessCategory.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testDocumentTypeBusinessCategory.isAdditional()).isEqualTo(DEFAULT_ADDITIONAL);
    }

    @Test
    @Transactional
    public void createDocumentTypeBusinessCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTypeBusinessCategoryRepository.findAll().size();

        // Create the DocumentTypeBusinessCategory with an existing ID
        documentTypeBusinessCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeBusinessCategoryMockMvc.perform(post("/api/document-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeBusinessCategory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeBusinessCategory in the database
        List<DocumentTypeBusinessCategory> documentTypeBusinessCategoryList = documentTypeBusinessCategoryRepository.findAll();
        assertThat(documentTypeBusinessCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategories() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypeBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].additional").value(hasItem(DEFAULT_ADDITIONAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocumentTypeBusinessCategory() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get the documentTypeBusinessCategory
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories/{id}", documentTypeBusinessCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentTypeBusinessCategory.getId().intValue()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.additional").value(DEFAULT_ADDITIONAL.booleanValue()));
    }


    @Test
    @Transactional
    public void getDocumentTypeBusinessCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        Long id = documentTypeBusinessCategory.getId();

        defaultDocumentTypeBusinessCategoryShouldBeFound("id.equals=" + id);
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentTypeBusinessCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentTypeBusinessCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where mandatory equals to DEFAULT_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the documentTypeBusinessCategoryList where mandatory equals to UPDATED_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByMandatoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where mandatory not equals to DEFAULT_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("mandatory.notEquals=" + DEFAULT_MANDATORY);

        // Get all the documentTypeBusinessCategoryList where mandatory not equals to UPDATED_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldBeFound("mandatory.notEquals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the documentTypeBusinessCategoryList where mandatory equals to UPDATED_MANDATORY
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where mandatory is not null
        defaultDocumentTypeBusinessCategoryShouldBeFound("mandatory.specified=true");

        // Get all the documentTypeBusinessCategoryList where mandatory is null
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByAdditionalIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where additional equals to DEFAULT_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldBeFound("additional.equals=" + DEFAULT_ADDITIONAL);

        // Get all the documentTypeBusinessCategoryList where additional equals to UPDATED_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("additional.equals=" + UPDATED_ADDITIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByAdditionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where additional not equals to DEFAULT_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("additional.notEquals=" + DEFAULT_ADDITIONAL);

        // Get all the documentTypeBusinessCategoryList where additional not equals to UPDATED_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldBeFound("additional.notEquals=" + UPDATED_ADDITIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByAdditionalIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where additional in DEFAULT_ADDITIONAL or UPDATED_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldBeFound("additional.in=" + DEFAULT_ADDITIONAL + "," + UPDATED_ADDITIONAL);

        // Get all the documentTypeBusinessCategoryList where additional equals to UPDATED_ADDITIONAL
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("additional.in=" + UPDATED_ADDITIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByAdditionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

        // Get all the documentTypeBusinessCategoryList where additional is not null
        defaultDocumentTypeBusinessCategoryShouldBeFound("additional.specified=true");

        // Get all the documentTypeBusinessCategoryList where additional is null
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("additional.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);
        DocumentType documentType = DocumentTypeResourceIT.createEntity(em);
        em.persist(documentType);
        em.flush();
        documentTypeBusinessCategory.setDocumentType(documentType);
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);
        Long documentTypeId = documentType.getId();

        // Get all the documentTypeBusinessCategoryList where documentType equals to documentTypeId
        defaultDocumentTypeBusinessCategoryShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the documentTypeBusinessCategoryList where documentType equals to documentTypeId + 1
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllDocumentTypeBusinessCategoriesByBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);
        BusinessCategory businessCategory = BusinessCategoryResourceIT.createEntity(em);
        em.persist(businessCategory);
        em.flush();
        documentTypeBusinessCategory.setBusinessCategory(businessCategory);
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);
        Long businessCategoryId = businessCategory.getId();

        // Get all the documentTypeBusinessCategoryList where businessCategory equals to businessCategoryId
        defaultDocumentTypeBusinessCategoryShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the documentTypeBusinessCategoryList where businessCategory equals to businessCategoryId + 1
        defaultDocumentTypeBusinessCategoryShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentTypeBusinessCategoryShouldBeFound(String filter) throws Exception {
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypeBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].additional").value(hasItem(DEFAULT_ADDITIONAL.booleanValue())));

        // Check, that the count call also returns 1
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentTypeBusinessCategoryShouldNotBeFound(String filter) throws Exception {
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDocumentTypeBusinessCategory() throws Exception {
        // Get the documentTypeBusinessCategory
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTypeBusinessCategory() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryService.save(documentTypeBusinessCategory);

        int databaseSizeBeforeUpdate = documentTypeBusinessCategoryRepository.findAll().size();

        // Update the documentTypeBusinessCategory
        DocumentTypeBusinessCategory updatedDocumentTypeBusinessCategory = documentTypeBusinessCategoryRepository.findById(documentTypeBusinessCategory.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTypeBusinessCategory are not directly saved in db
        em.detach(updatedDocumentTypeBusinessCategory);
        updatedDocumentTypeBusinessCategory
            .mandatory(UPDATED_MANDATORY)
            .additional(UPDATED_ADDITIONAL);

        restDocumentTypeBusinessCategoryMockMvc.perform(put("/api/document-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTypeBusinessCategory)))
            .andExpect(status().isOk());

        // Validate the DocumentTypeBusinessCategory in the database
        List<DocumentTypeBusinessCategory> documentTypeBusinessCategoryList = documentTypeBusinessCategoryRepository.findAll();
        assertThat(documentTypeBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypeBusinessCategory testDocumentTypeBusinessCategory = documentTypeBusinessCategoryList.get(documentTypeBusinessCategoryList.size() - 1);
        assertThat(testDocumentTypeBusinessCategory.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testDocumentTypeBusinessCategory.isAdditional()).isEqualTo(UPDATED_ADDITIONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTypeBusinessCategory() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeBusinessCategoryRepository.findAll().size();

        // Create the DocumentTypeBusinessCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeBusinessCategoryMockMvc.perform(put("/api/document-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeBusinessCategory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeBusinessCategory in the database
        List<DocumentTypeBusinessCategory> documentTypeBusinessCategoryList = documentTypeBusinessCategoryRepository.findAll();
        assertThat(documentTypeBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentTypeBusinessCategory() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryService.save(documentTypeBusinessCategory);

        int databaseSizeBeforeDelete = documentTypeBusinessCategoryRepository.findAll().size();

        // Delete the documentTypeBusinessCategory
        restDocumentTypeBusinessCategoryMockMvc.perform(delete("/api/document-type-business-categories/{id}", documentTypeBusinessCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentTypeBusinessCategory> documentTypeBusinessCategoryList = documentTypeBusinessCategoryRepository.findAll();
        assertThat(documentTypeBusinessCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
