package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;

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
    public void getNonExistingDocumentTypeBusinessCategory() throws Exception {
        // Get the documentTypeBusinessCategory
        restDocumentTypeBusinessCategoryMockMvc.perform(get("/api/document-type-business-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTypeBusinessCategory() throws Exception {
        // Initialize the database
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

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
        documentTypeBusinessCategoryRepository.saveAndFlush(documentTypeBusinessCategory);

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
