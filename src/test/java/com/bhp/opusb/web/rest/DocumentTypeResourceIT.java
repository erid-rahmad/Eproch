package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.DocumentType;
import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.repository.DocumentTypeRepository;
import com.bhp.opusb.service.DocumentTypeService;
import com.bhp.opusb.service.dto.DocumentTypeDTO;
import com.bhp.opusb.service.mapper.DocumentTypeMapper;
import com.bhp.opusb.service.dto.DocumentTypeCriteria;
import com.bhp.opusb.service.DocumentTypeQueryService;

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
 * Integration tests for the {@link DocumentTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DocumentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_EXPIRATION_DATE = false;
    private static final Boolean UPDATED_HAS_EXPIRATION_DATE = true;

    private static final Boolean DEFAULT_FOR_COMPANY = false;
    private static final Boolean UPDATED_FOR_COMPANY = true;

    private static final Boolean DEFAULT_FOR_PROFESSIONAL = false;
    private static final Boolean UPDATED_FOR_PROFESSIONAL = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private DocumentTypeMapper documentTypeMapper;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentTypeQueryService documentTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentTypeMockMvc;

    private DocumentType documentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .hasExpirationDate(DEFAULT_HAS_EXPIRATION_DATE)
            .forCompany(DEFAULT_FOR_COMPANY)
            .forProfessional(DEFAULT_FOR_PROFESSIONAL)
            .active(DEFAULT_ACTIVE);
        return documentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createUpdatedEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hasExpirationDate(UPDATED_HAS_EXPIRATION_DATE)
            .forCompany(UPDATED_FOR_COMPANY)
            .forProfessional(UPDATED_FOR_PROFESSIONAL)
            .active(UPDATED_ACTIVE);
        return documentType;
    }

    @BeforeEach
    public void initTest() {
        documentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentType() throws Exception {
        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();

        // Create the DocumentType
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);
        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocumentType.isHasExpirationDate()).isEqualTo(DEFAULT_HAS_EXPIRATION_DATE);
        assertThat(testDocumentType.isForCompany()).isEqualTo(DEFAULT_FOR_COMPANY);
        assertThat(testDocumentType.isForProfessional()).isEqualTo(DEFAULT_FOR_PROFESSIONAL);
        assertThat(testDocumentType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createDocumentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();

        // Create the DocumentType with an existing ID
        documentType.setId(1L);
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentTypeRepository.findAll().size();
        // set the field null
        documentType.setName(null);

        // Create the DocumentType, which fails.
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);

        restDocumentTypeMockMvc.perform(post("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentTypes() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExpirationDate").value(hasItem(DEFAULT_HAS_EXPIRATION_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].forCompany").value(hasItem(DEFAULT_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].forProfessional").value(hasItem(DEFAULT_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get the documentType
        restDocumentTypeMockMvc.perform(get("/api/document-types/{id}", documentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hasExpirationDate").value(DEFAULT_HAS_EXPIRATION_DATE.booleanValue()))
            .andExpect(jsonPath("$.forCompany").value(DEFAULT_FOR_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.forProfessional").value(DEFAULT_FOR_PROFESSIONAL.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getDocumentTypesByIdFiltering() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        Long id = documentType.getId();

        defaultDocumentTypeShouldBeFound("id.equals=" + id);
        defaultDocumentTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name equals to DEFAULT_NAME
        defaultDocumentTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the documentTypeList where name equals to UPDATED_NAME
        defaultDocumentTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name not equals to DEFAULT_NAME
        defaultDocumentTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the documentTypeList where name not equals to UPDATED_NAME
        defaultDocumentTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDocumentTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the documentTypeList where name equals to UPDATED_NAME
        defaultDocumentTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name is not null
        defaultDocumentTypeShouldBeFound("name.specified=true");

        // Get all the documentTypeList where name is null
        defaultDocumentTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name contains DEFAULT_NAME
        defaultDocumentTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the documentTypeList where name contains UPDATED_NAME
        defaultDocumentTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where name does not contain DEFAULT_NAME
        defaultDocumentTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the documentTypeList where name does not contain UPDATED_NAME
        defaultDocumentTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description equals to DEFAULT_DESCRIPTION
        defaultDocumentTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the documentTypeList where description equals to UPDATED_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the documentTypeList where description not equals to UPDATED_DESCRIPTION
        defaultDocumentTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDocumentTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the documentTypeList where description equals to UPDATED_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description is not null
        defaultDocumentTypeShouldBeFound("description.specified=true");

        // Get all the documentTypeList where description is null
        defaultDocumentTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description contains DEFAULT_DESCRIPTION
        defaultDocumentTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the documentTypeList where description contains UPDATED_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultDocumentTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the documentTypeList where description does not contain UPDATED_DESCRIPTION
        defaultDocumentTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByHasExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where hasExpirationDate equals to DEFAULT_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldBeFound("hasExpirationDate.equals=" + DEFAULT_HAS_EXPIRATION_DATE);

        // Get all the documentTypeList where hasExpirationDate equals to UPDATED_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldNotBeFound("hasExpirationDate.equals=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByHasExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where hasExpirationDate not equals to DEFAULT_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldNotBeFound("hasExpirationDate.notEquals=" + DEFAULT_HAS_EXPIRATION_DATE);

        // Get all the documentTypeList where hasExpirationDate not equals to UPDATED_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldBeFound("hasExpirationDate.notEquals=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByHasExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where hasExpirationDate in DEFAULT_HAS_EXPIRATION_DATE or UPDATED_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldBeFound("hasExpirationDate.in=" + DEFAULT_HAS_EXPIRATION_DATE + "," + UPDATED_HAS_EXPIRATION_DATE);

        // Get all the documentTypeList where hasExpirationDate equals to UPDATED_HAS_EXPIRATION_DATE
        defaultDocumentTypeShouldNotBeFound("hasExpirationDate.in=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByHasExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where hasExpirationDate is not null
        defaultDocumentTypeShouldBeFound("hasExpirationDate.specified=true");

        // Get all the documentTypeList where hasExpirationDate is null
        defaultDocumentTypeShouldNotBeFound("hasExpirationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forCompany equals to DEFAULT_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("forCompany.equals=" + DEFAULT_FOR_COMPANY);

        // Get all the documentTypeList where forCompany equals to UPDATED_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("forCompany.equals=" + UPDATED_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forCompany not equals to DEFAULT_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("forCompany.notEquals=" + DEFAULT_FOR_COMPANY);

        // Get all the documentTypeList where forCompany not equals to UPDATED_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("forCompany.notEquals=" + UPDATED_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forCompany in DEFAULT_FOR_COMPANY or UPDATED_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("forCompany.in=" + DEFAULT_FOR_COMPANY + "," + UPDATED_FOR_COMPANY);

        // Get all the documentTypeList where forCompany equals to UPDATED_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("forCompany.in=" + UPDATED_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forCompany is not null
        defaultDocumentTypeShouldBeFound("forCompany.specified=true");

        // Get all the documentTypeList where forCompany is null
        defaultDocumentTypeShouldNotBeFound("forCompany.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForProfessionalIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forProfessional equals to DEFAULT_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("forProfessional.equals=" + DEFAULT_FOR_PROFESSIONAL);

        // Get all the documentTypeList where forProfessional equals to UPDATED_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("forProfessional.equals=" + UPDATED_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForProfessionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forProfessional not equals to DEFAULT_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("forProfessional.notEquals=" + DEFAULT_FOR_PROFESSIONAL);

        // Get all the documentTypeList where forProfessional not equals to UPDATED_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("forProfessional.notEquals=" + UPDATED_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForProfessionalIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forProfessional in DEFAULT_FOR_PROFESSIONAL or UPDATED_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("forProfessional.in=" + DEFAULT_FOR_PROFESSIONAL + "," + UPDATED_FOR_PROFESSIONAL);

        // Get all the documentTypeList where forProfessional equals to UPDATED_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("forProfessional.in=" + UPDATED_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByForProfessionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where forProfessional is not null
        defaultDocumentTypeShouldBeFound("forProfessional.specified=true");

        // Get all the documentTypeList where forProfessional is null
        defaultDocumentTypeShouldNotBeFound("forProfessional.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where active equals to DEFAULT_ACTIVE
        defaultDocumentTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the documentTypeList where active equals to UPDATED_ACTIVE
        defaultDocumentTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where active not equals to DEFAULT_ACTIVE
        defaultDocumentTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the documentTypeList where active not equals to UPDATED_ACTIVE
        defaultDocumentTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultDocumentTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the documentTypeList where active equals to UPDATED_ACTIVE
        defaultDocumentTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where active is not null
        defaultDocumentTypeShouldBeFound("active.specified=true");

        // Get all the documentTypeList where active is null
        defaultDocumentTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByDocumentTypeBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);
        DocumentTypeBusinessCategory documentTypeBusinessCategory = DocumentTypeBusinessCategoryResourceIT.createEntity(em);
        em.persist(documentTypeBusinessCategory);
        em.flush();
        documentType.addDocumentTypeBusinessCategory(documentTypeBusinessCategory);
        documentTypeRepository.saveAndFlush(documentType);
        Long documentTypeBusinessCategoryId = documentTypeBusinessCategory.getId();

        // Get all the documentTypeList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId
        defaultDocumentTypeShouldBeFound("documentTypeBusinessCategoryId.equals=" + documentTypeBusinessCategoryId);

        // Get all the documentTypeList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId + 1
        defaultDocumentTypeShouldNotBeFound("documentTypeBusinessCategoryId.equals=" + (documentTypeBusinessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentTypeShouldBeFound(String filter) throws Exception {
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExpirationDate").value(hasItem(DEFAULT_HAS_EXPIRATION_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].forCompany").value(hasItem(DEFAULT_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].forProfessional").value(hasItem(DEFAULT_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restDocumentTypeMockMvc.perform(get("/api/document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentTypeShouldNotBeFound(String filter) throws Exception {
        restDocumentTypeMockMvc.perform(get("/api/document-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentTypeMockMvc.perform(get("/api/document-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDocumentType() throws Exception {
        // Get the documentType
        restDocumentTypeMockMvc.perform(get("/api/document-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Update the documentType
        DocumentType updatedDocumentType = documentTypeRepository.findById(documentType.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentType are not directly saved in db
        em.detach(updatedDocumentType);
        updatedDocumentType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hasExpirationDate(UPDATED_HAS_EXPIRATION_DATE)
            .forCompany(UPDATED_FOR_COMPANY)
            .forProfessional(UPDATED_FOR_PROFESSIONAL)
            .active(UPDATED_ACTIVE);
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(updatedDocumentType);

        restDocumentTypeMockMvc.perform(put("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentType.isHasExpirationDate()).isEqualTo(UPDATED_HAS_EXPIRATION_DATE);
        assertThat(testDocumentType.isForCompany()).isEqualTo(UPDATED_FOR_COMPANY);
        assertThat(testDocumentType.isForProfessional()).isEqualTo(UPDATED_FOR_PROFESSIONAL);
        assertThat(testDocumentType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Create the DocumentType
        DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc.perform(put("/api/document-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeDelete = documentTypeRepository.findAll().size();

        // Delete the documentType
        restDocumentTypeMockMvc.perform(delete("/api/document-types/{id}", documentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
