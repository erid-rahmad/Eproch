package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.SupportingDocument;
import com.bhp.opusb.domain.DocumentType;
import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.repository.SupportingDocumentRepository;
import com.bhp.opusb.service.SupportingDocumentService;
import com.bhp.opusb.service.dto.SupportingDocumentDTO;
import com.bhp.opusb.service.mapper.SupportingDocumentMapper;
import com.bhp.opusb.service.dto.SupportingDocumentCriteria;
import com.bhp.opusb.service.SupportingDocumentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupportingDocumentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SupportingDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private SupportingDocumentRepository supportingDocumentRepository;

    @Autowired
    private SupportingDocumentMapper supportingDocumentMapper;

    @Autowired
    private SupportingDocumentService supportingDocumentService;

    @Autowired
    private SupportingDocumentQueryService supportingDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportingDocumentMockMvc;

    private SupportingDocument supportingDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportingDocument createEntity(EntityManager em) {
        SupportingDocument supportingDocument = new SupportingDocument()
            .documentNo(DEFAULT_DOCUMENT_NO)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        // Add required entity
        DocumentType documentType;
        if (TestUtil.findAll(em, DocumentType.class).isEmpty()) {
            documentType = DocumentTypeResourceIT.createEntity(em);
            em.persist(documentType);
            em.flush();
        } else {
            documentType = TestUtil.findAll(em, DocumentType.class).get(0);
        }
        supportingDocument.setType(documentType);
        return supportingDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportingDocument createUpdatedEntity(EntityManager em) {
        SupportingDocument supportingDocument = new SupportingDocument()
            .documentNo(UPDATED_DOCUMENT_NO)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        // Add required entity
        DocumentType documentType;
        if (TestUtil.findAll(em, DocumentType.class).isEmpty()) {
            documentType = DocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(documentType);
            em.flush();
        } else {
            documentType = TestUtil.findAll(em, DocumentType.class).get(0);
        }
        supportingDocument.setType(documentType);
        return supportingDocument;
    }

    @BeforeEach
    public void initTest() {
        supportingDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportingDocument() throws Exception {
        int databaseSizeBeforeCreate = supportingDocumentRepository.findAll().size();

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);
        restSupportingDocumentMockMvc.perform(post("/api/supporting-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportingDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the SupportingDocument in the database
        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        SupportingDocument testSupportingDocument = supportingDocumentList.get(supportingDocumentList.size() - 1);
        assertThat(testSupportingDocument.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testSupportingDocument.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testSupportingDocument.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testSupportingDocument.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSupportingDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportingDocumentRepository.findAll().size();

        // Create the SupportingDocument with an existing ID
        supportingDocument.setId(1L);
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportingDocumentMockMvc.perform(post("/api/supporting-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportingDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportingDocumentRepository.findAll().size();
        // set the field null
        supportingDocument.setDocumentNo(null);

        // Create the SupportingDocument, which fails.
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc.perform(post("/api/supporting-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportingDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupportingDocuments() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportingDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getSupportingDocument() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get the supportingDocument
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents/{id}", supportingDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportingDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }


    @Test
    @Transactional
    public void getSupportingDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        Long id = supportingDocument.getId();

        defaultSupportingDocumentShouldBeFound("id.equals=" + id);
        defaultSupportingDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultSupportingDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupportingDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultSupportingDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupportingDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultSupportingDocumentShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the supportingDocumentList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultSupportingDocumentShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the supportingDocumentList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the supportingDocumentList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo is not null
        defaultSupportingDocumentShouldBeFound("documentNo.specified=true");

        // Get all the supportingDocumentList where documentNo is null
        defaultSupportingDocumentShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultSupportingDocumentShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the supportingDocumentList where documentNo contains UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultSupportingDocumentShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the supportingDocumentList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultSupportingDocumentShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate is not null
        defaultSupportingDocumentShouldBeFound("expirationDate.specified=true");

        // Get all the supportingDocumentList where expirationDate is null
        defaultSupportingDocumentShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate is greater than or equal to DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.greaterThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate is greater than or equal to UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.greaterThanOrEqual=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate is less than or equal to DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.lessThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate is less than or equal to SMALLER_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.lessThanOrEqual=" + SMALLER_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate is less than DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.lessThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate is less than UPDATED_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.lessThan=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupportingDocumentsByExpirationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where expirationDate is greater than DEFAULT_EXPIRATION_DATE
        defaultSupportingDocumentShouldNotBeFound("expirationDate.greaterThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the supportingDocumentList where expirationDate is greater than SMALLER_EXPIRATION_DATE
        defaultSupportingDocumentShouldBeFound("expirationDate.greaterThan=" + SMALLER_EXPIRATION_DATE);
    }


    @Test
    @Transactional
    public void getAllSupportingDocumentsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        DocumentType type = supportingDocument.getType();
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long typeId = type.getId();

        // Get all the supportingDocumentList where type equals to typeId
        defaultSupportingDocumentShouldBeFound("typeId.equals=" + typeId);

        // Get all the supportingDocumentList where type equals to typeId + 1
        defaultSupportingDocumentShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupportingDocumentsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Vendor vendor = VendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        supportingDocument.setVendor(vendor);
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long vendorId = vendor.getId();

        // Get all the supportingDocumentList where vendor equals to vendorId
        defaultSupportingDocumentShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the supportingDocumentList where vendor equals to vendorId + 1
        defaultSupportingDocumentShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupportingDocumentShouldBeFound(String filter) throws Exception {
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportingDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupportingDocumentShouldNotBeFound(String filter) throws Exception {
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupportingDocument() throws Exception {
        // Get the supportingDocument
        restSupportingDocumentMockMvc.perform(get("/api/supporting-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportingDocument() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        int databaseSizeBeforeUpdate = supportingDocumentRepository.findAll().size();

        // Update the supportingDocument
        SupportingDocument updatedSupportingDocument = supportingDocumentRepository.findById(supportingDocument.getId()).get();
        // Disconnect from session so that the updates on updatedSupportingDocument are not directly saved in db
        em.detach(updatedSupportingDocument);
        updatedSupportingDocument
            .documentNo(UPDATED_DOCUMENT_NO)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(updatedSupportingDocument);

        restSupportingDocumentMockMvc.perform(put("/api/supporting-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportingDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the SupportingDocument in the database
        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeUpdate);
        SupportingDocument testSupportingDocument = supportingDocumentList.get(supportingDocumentList.size() - 1);
        assertThat(testSupportingDocument.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testSupportingDocument.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testSupportingDocument.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testSupportingDocument.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportingDocument() throws Exception {
        int databaseSizeBeforeUpdate = supportingDocumentRepository.findAll().size();

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc.perform(put("/api/supporting-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supportingDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupportingDocument() throws Exception {
        // Initialize the database
        supportingDocumentRepository.saveAndFlush(supportingDocument);

        int databaseSizeBeforeDelete = supportingDocumentRepository.findAll().size();

        // Delete the supportingDocument
        restSupportingDocumentMockMvc.perform(delete("/api/supporting-documents/{id}", supportingDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportingDocument> supportingDocumentList = supportingDocumentRepository.findAll();
        assertThat(supportingDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
