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

    private static final String DEFAULT_MANDATORY_BUSINESS_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY_BUSINESS_CATEGORIES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_BUSINESS_CATEGORIES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANDATORY_FOR_COMPANY = false;
    private static final Boolean UPDATED_MANDATORY_FOR_COMPANY = true;

    private static final Boolean DEFAULT_MANDATORY_FOR_PROFESSIONAL = false;
    private static final Boolean UPDATED_MANDATORY_FOR_PROFESSIONAL = true;

    private static final Boolean DEFAULT_ADDITIONAL_FOR_COMPANY = false;
    private static final Boolean UPDATED_ADDITIONAL_FOR_COMPANY = true;

    private static final Boolean DEFAULT_ADDITIONAL_FOR_PROFESSIONAL = false;
    private static final Boolean UPDATED_ADDITIONAL_FOR_PROFESSIONAL = true;

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
            .mandatoryBusinessCategories(DEFAULT_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(DEFAULT_MANDATORY_FOR_COMPANY)
            .mandatoryForProfessional(DEFAULT_MANDATORY_FOR_PROFESSIONAL)
            .additionalForCompany(DEFAULT_ADDITIONAL_FOR_COMPANY)
            .additionalForProfessional(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL)
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
            .mandatoryBusinessCategories(UPDATED_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(UPDATED_MANDATORY_FOR_COMPANY)
            .mandatoryForProfessional(UPDATED_MANDATORY_FOR_PROFESSIONAL)
            .additionalForCompany(UPDATED_ADDITIONAL_FOR_COMPANY)
            .additionalForProfessional(UPDATED_ADDITIONAL_FOR_PROFESSIONAL)
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
        assertThat(testDocumentType.getMandatoryBusinessCategories()).isEqualTo(DEFAULT_MANDATORY_BUSINESS_CATEGORIES);
        assertThat(testDocumentType.getAdditionalBusinessCategories()).isEqualTo(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);
        assertThat(testDocumentType.isMandatoryForCompany()).isEqualTo(DEFAULT_MANDATORY_FOR_COMPANY);
        assertThat(testDocumentType.isMandatoryForProfessional()).isEqualTo(DEFAULT_MANDATORY_FOR_PROFESSIONAL);
        assertThat(testDocumentType.isAdditionalForCompany()).isEqualTo(DEFAULT_ADDITIONAL_FOR_COMPANY);
        assertThat(testDocumentType.isAdditionalForProfessional()).isEqualTo(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);
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
            .andExpect(jsonPath("$.[*].mandatoryBusinessCategories").value(hasItem(DEFAULT_MANDATORY_BUSINESS_CATEGORIES)))
            .andExpect(jsonPath("$.[*].additionalBusinessCategories").value(hasItem(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES)))
            .andExpect(jsonPath("$.[*].mandatoryForCompany").value(hasItem(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryForProfessional").value(hasItem(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForCompany").value(hasItem(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForProfessional").value(hasItem(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue())))
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
            .andExpect(jsonPath("$.mandatoryBusinessCategories").value(DEFAULT_MANDATORY_BUSINESS_CATEGORIES))
            .andExpect(jsonPath("$.additionalBusinessCategories").value(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES))
            .andExpect(jsonPath("$.mandatoryForCompany").value(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.mandatoryForProfessional").value(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue()))
            .andExpect(jsonPath("$.additionalForCompany").value(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.additionalForProfessional").value(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue()))
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
    public void getAllDocumentTypesByMandatoryBusinessCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories equals to DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.equals=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where mandatoryBusinessCategories equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.equals=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryBusinessCategoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories not equals to DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.notEquals=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where mandatoryBusinessCategories not equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.notEquals=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryBusinessCategoriesIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories in DEFAULT_MANDATORY_BUSINESS_CATEGORIES or UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.in=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES + "," + UPDATED_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where mandatoryBusinessCategories equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.in=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryBusinessCategoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories is not null
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.specified=true");

        // Get all the documentTypeList where mandatoryBusinessCategories is null
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryBusinessCategoriesContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories contains DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.contains=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where mandatoryBusinessCategories contains UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.contains=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryBusinessCategoriesNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryBusinessCategories does not contain DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("mandatoryBusinessCategories.doesNotContain=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where mandatoryBusinessCategories does not contain UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("mandatoryBusinessCategories.doesNotContain=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories equals to DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.equals=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where additionalBusinessCategories equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.equals=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories not equals to DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.notEquals=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where additionalBusinessCategories not equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.notEquals=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories in DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES or UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.in=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES + "," + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where additionalBusinessCategories equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.in=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories is not null
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.specified=true");

        // Get all the documentTypeList where additionalBusinessCategories is null
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories contains DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.contains=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where additionalBusinessCategories contains UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.contains=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalBusinessCategoriesNotContainsSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalBusinessCategories does not contain DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldNotBeFound("additionalBusinessCategories.doesNotContain=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the documentTypeList where additionalBusinessCategories does not contain UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultDocumentTypeShouldBeFound("additionalBusinessCategories.doesNotContain=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }


    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForCompany equals to DEFAULT_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("mandatoryForCompany.equals=" + DEFAULT_MANDATORY_FOR_COMPANY);

        // Get all the documentTypeList where mandatoryForCompany equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("mandatoryForCompany.equals=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForCompany not equals to DEFAULT_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("mandatoryForCompany.notEquals=" + DEFAULT_MANDATORY_FOR_COMPANY);

        // Get all the documentTypeList where mandatoryForCompany not equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("mandatoryForCompany.notEquals=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForCompany in DEFAULT_MANDATORY_FOR_COMPANY or UPDATED_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("mandatoryForCompany.in=" + DEFAULT_MANDATORY_FOR_COMPANY + "," + UPDATED_MANDATORY_FOR_COMPANY);

        // Get all the documentTypeList where mandatoryForCompany equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("mandatoryForCompany.in=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForCompany is not null
        defaultDocumentTypeShouldBeFound("mandatoryForCompany.specified=true");

        // Get all the documentTypeList where mandatoryForCompany is null
        defaultDocumentTypeShouldNotBeFound("mandatoryForCompany.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForProfessionalIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForProfessional equals to DEFAULT_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("mandatoryForProfessional.equals=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL);

        // Get all the documentTypeList where mandatoryForProfessional equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("mandatoryForProfessional.equals=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForProfessionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForProfessional not equals to DEFAULT_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("mandatoryForProfessional.notEquals=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL);

        // Get all the documentTypeList where mandatoryForProfessional not equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("mandatoryForProfessional.notEquals=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForProfessionalIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForProfessional in DEFAULT_MANDATORY_FOR_PROFESSIONAL or UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("mandatoryForProfessional.in=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL + "," + UPDATED_MANDATORY_FOR_PROFESSIONAL);

        // Get all the documentTypeList where mandatoryForProfessional equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("mandatoryForProfessional.in=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByMandatoryForProfessionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where mandatoryForProfessional is not null
        defaultDocumentTypeShouldBeFound("mandatoryForProfessional.specified=true");

        // Get all the documentTypeList where mandatoryForProfessional is null
        defaultDocumentTypeShouldNotBeFound("mandatoryForProfessional.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForCompany equals to DEFAULT_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("additionalForCompany.equals=" + DEFAULT_ADDITIONAL_FOR_COMPANY);

        // Get all the documentTypeList where additionalForCompany equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("additionalForCompany.equals=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForCompany not equals to DEFAULT_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("additionalForCompany.notEquals=" + DEFAULT_ADDITIONAL_FOR_COMPANY);

        // Get all the documentTypeList where additionalForCompany not equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("additionalForCompany.notEquals=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForCompany in DEFAULT_ADDITIONAL_FOR_COMPANY or UPDATED_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldBeFound("additionalForCompany.in=" + DEFAULT_ADDITIONAL_FOR_COMPANY + "," + UPDATED_ADDITIONAL_FOR_COMPANY);

        // Get all the documentTypeList where additionalForCompany equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultDocumentTypeShouldNotBeFound("additionalForCompany.in=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForCompany is not null
        defaultDocumentTypeShouldBeFound("additionalForCompany.specified=true");

        // Get all the documentTypeList where additionalForCompany is null
        defaultDocumentTypeShouldNotBeFound("additionalForCompany.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForProfessionalIsEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForProfessional equals to DEFAULT_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("additionalForProfessional.equals=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the documentTypeList where additionalForProfessional equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("additionalForProfessional.equals=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForProfessionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForProfessional not equals to DEFAULT_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("additionalForProfessional.notEquals=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the documentTypeList where additionalForProfessional not equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("additionalForProfessional.notEquals=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForProfessionalIsInShouldWork() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForProfessional in DEFAULT_ADDITIONAL_FOR_PROFESSIONAL or UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldBeFound("additionalForProfessional.in=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL + "," + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the documentTypeList where additionalForProfessional equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultDocumentTypeShouldNotBeFound("additionalForProfessional.in=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllDocumentTypesByAdditionalForProfessionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList where additionalForProfessional is not null
        defaultDocumentTypeShouldBeFound("additionalForProfessional.specified=true");

        // Get all the documentTypeList where additionalForProfessional is null
        defaultDocumentTypeShouldNotBeFound("additionalForProfessional.specified=false");
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
        // // Initialize the database
        // documentTypeRepository.saveAndFlush(documentType);
        // DocumentTypeBusinessCategory documentTypeBusinessCategory = DocumentTypeBusinessCategoryResourceIT.createEntity(em);
        // em.persist(documentTypeBusinessCategory);
        // em.flush();
        // documentType.addDocumentTypeBusinessCategory(documentTypeBusinessCategory);
        // documentTypeRepository.saveAndFlush(documentType);
        // Long documentTypeBusinessCategoryId = documentTypeBusinessCategory.getId();

        // // Get all the documentTypeList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId
        // defaultDocumentTypeShouldBeFound("documentTypeBusinessCategoryId.equals=" + documentTypeBusinessCategoryId);

        // // Get all the documentTypeList where documentTypeBusinessCategory equals to documentTypeBusinessCategoryId + 1
        // defaultDocumentTypeShouldNotBeFound("documentTypeBusinessCategoryId.equals=" + (documentTypeBusinessCategoryId + 1));
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
            .andExpect(jsonPath("$.[*].mandatoryBusinessCategories").value(hasItem(DEFAULT_MANDATORY_BUSINESS_CATEGORIES)))
            .andExpect(jsonPath("$.[*].additionalBusinessCategories").value(hasItem(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES)))
            .andExpect(jsonPath("$.[*].mandatoryForCompany").value(hasItem(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryForProfessional").value(hasItem(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForCompany").value(hasItem(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForProfessional").value(hasItem(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue())))
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
            .mandatoryBusinessCategories(UPDATED_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(UPDATED_MANDATORY_FOR_COMPANY)
            .mandatoryForProfessional(UPDATED_MANDATORY_FOR_PROFESSIONAL)
            .additionalForCompany(UPDATED_ADDITIONAL_FOR_COMPANY)
            .additionalForProfessional(UPDATED_ADDITIONAL_FOR_PROFESSIONAL)
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
        assertThat(testDocumentType.getMandatoryBusinessCategories()).isEqualTo(UPDATED_MANDATORY_BUSINESS_CATEGORIES);
        assertThat(testDocumentType.getAdditionalBusinessCategories()).isEqualTo(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
        assertThat(testDocumentType.isMandatoryForCompany()).isEqualTo(UPDATED_MANDATORY_FOR_COMPANY);
        assertThat(testDocumentType.isMandatoryForProfessional()).isEqualTo(UPDATED_MANDATORY_FOR_PROFESSIONAL);
        assertThat(testDocumentType.isAdditionalForCompany()).isEqualTo(UPDATED_ADDITIONAL_FOR_COMPANY);
        assertThat(testDocumentType.isAdditionalForProfessional()).isEqualTo(UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
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
