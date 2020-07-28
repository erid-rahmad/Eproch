package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CRegistrationDocType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CRegistrationDocTypeRepository;
import com.bhp.opusb.service.CRegistrationDocTypeService;
import com.bhp.opusb.service.dto.CRegistrationDocTypeDTO;
import com.bhp.opusb.service.mapper.CRegistrationDocTypeMapper;
import com.bhp.opusb.service.dto.CRegistrationDocTypeCriteria;
import com.bhp.opusb.service.CRegistrationDocTypeQueryService;

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

import com.bhp.opusb.domain.enumeration.BusinessCategorySelection;
import com.bhp.opusb.domain.enumeration.BusinessCategorySelection;
/**
 * Integration tests for the {@link CRegistrationDocTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CRegistrationDocTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_EXPIRATION_DATE = false;
    private static final Boolean UPDATED_HAS_EXPIRATION_DATE = true;

    private static final BusinessCategorySelection DEFAULT_MANDATORY_BUSINESS_CATEGORIES = BusinessCategorySelection.ALL;
    private static final BusinessCategorySelection UPDATED_MANDATORY_BUSINESS_CATEGORIES = BusinessCategorySelection.SELECTED;

    private static final BusinessCategorySelection DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES = BusinessCategorySelection.ALL;
    private static final BusinessCategorySelection UPDATED_ADDITIONAL_BUSINESS_CATEGORIES = BusinessCategorySelection.SELECTED;

    private static final Boolean DEFAULT_MANDATORY_FOR_COMPANY = false;
    private static final Boolean UPDATED_MANDATORY_FOR_COMPANY = true;

    private static final Boolean DEFAULT_ADDITIONAL_FOR_COMPANY = false;
    private static final Boolean UPDATED_ADDITIONAL_FOR_COMPANY = true;

    private static final Boolean DEFAULT_MANDATORY_FOR_PROFESSIONAL = false;
    private static final Boolean UPDATED_MANDATORY_FOR_PROFESSIONAL = true;

    private static final Boolean DEFAULT_ADDITIONAL_FOR_PROFESSIONAL = false;
    private static final Boolean UPDATED_ADDITIONAL_FOR_PROFESSIONAL = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CRegistrationDocTypeRepository cRegistrationDocTypeRepository;

    @Autowired
    private CRegistrationDocTypeMapper cRegistrationDocTypeMapper;

    @Autowired
    private CRegistrationDocTypeService cRegistrationDocTypeService;

    @Autowired
    private CRegistrationDocTypeQueryService cRegistrationDocTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCRegistrationDocTypeMockMvc;

    private CRegistrationDocType cRegistrationDocType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegistrationDocType createEntity(EntityManager em) {
        CRegistrationDocType cRegistrationDocType = new CRegistrationDocType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .hasExpirationDate(DEFAULT_HAS_EXPIRATION_DATE)
            .mandatoryBusinessCategories(DEFAULT_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(DEFAULT_MANDATORY_FOR_COMPANY)
            .additionalForCompany(DEFAULT_ADDITIONAL_FOR_COMPANY)
            .mandatoryForProfessional(DEFAULT_MANDATORY_FOR_PROFESSIONAL)
            .additionalForProfessional(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL)
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
        cRegistrationDocType.setAdOrganization(aDOrganization);
        return cRegistrationDocType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegistrationDocType createUpdatedEntity(EntityManager em) {
        CRegistrationDocType cRegistrationDocType = new CRegistrationDocType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hasExpirationDate(UPDATED_HAS_EXPIRATION_DATE)
            .mandatoryBusinessCategories(UPDATED_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(UPDATED_MANDATORY_FOR_COMPANY)
            .additionalForCompany(UPDATED_ADDITIONAL_FOR_COMPANY)
            .mandatoryForProfessional(UPDATED_MANDATORY_FOR_PROFESSIONAL)
            .additionalForProfessional(UPDATED_ADDITIONAL_FOR_PROFESSIONAL)
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
        cRegistrationDocType.setAdOrganization(aDOrganization);
        return cRegistrationDocType;
    }

    @BeforeEach
    public void initTest() {
        cRegistrationDocType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRegistrationDocType() throws Exception {
        int databaseSizeBeforeCreate = cRegistrationDocTypeRepository.findAll().size();

        // Create the CRegistrationDocType
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);
        restCRegistrationDocTypeMockMvc.perform(post("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CRegistrationDocType in the database
        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CRegistrationDocType testCRegistrationDocType = cRegistrationDocTypeList.get(cRegistrationDocTypeList.size() - 1);
        assertThat(testCRegistrationDocType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRegistrationDocType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCRegistrationDocType.isHasExpirationDate()).isEqualTo(DEFAULT_HAS_EXPIRATION_DATE);
        assertThat(testCRegistrationDocType.getMandatoryBusinessCategories()).isEqualTo(DEFAULT_MANDATORY_BUSINESS_CATEGORIES);
        assertThat(testCRegistrationDocType.getAdditionalBusinessCategories()).isEqualTo(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);
        assertThat(testCRegistrationDocType.isMandatoryForCompany()).isEqualTo(DEFAULT_MANDATORY_FOR_COMPANY);
        assertThat(testCRegistrationDocType.isAdditionalForCompany()).isEqualTo(DEFAULT_ADDITIONAL_FOR_COMPANY);
        assertThat(testCRegistrationDocType.isMandatoryForProfessional()).isEqualTo(DEFAULT_MANDATORY_FOR_PROFESSIONAL);
        assertThat(testCRegistrationDocType.isAdditionalForProfessional()).isEqualTo(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);
        assertThat(testCRegistrationDocType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCRegistrationDocType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCRegistrationDocTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRegistrationDocTypeRepository.findAll().size();

        // Create the CRegistrationDocType with an existing ID
        cRegistrationDocType.setId(1L);
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRegistrationDocTypeMockMvc.perform(post("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegistrationDocType in the database
        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRegistrationDocTypeRepository.findAll().size();
        // set the field null
        cRegistrationDocType.setName(null);

        // Create the CRegistrationDocType, which fails.
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);

        restCRegistrationDocTypeMockMvc.perform(post("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMandatoryBusinessCategoriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRegistrationDocTypeRepository.findAll().size();
        // set the field null
        cRegistrationDocType.setMandatoryBusinessCategories(null);

        // Create the CRegistrationDocType, which fails.
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);

        restCRegistrationDocTypeMockMvc.perform(post("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdditionalBusinessCategoriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRegistrationDocTypeRepository.findAll().size();
        // set the field null
        cRegistrationDocType.setAdditionalBusinessCategories(null);

        // Create the CRegistrationDocType, which fails.
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);

        restCRegistrationDocTypeMockMvc.perform(post("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypes() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegistrationDocType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExpirationDate").value(hasItem(DEFAULT_HAS_EXPIRATION_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryBusinessCategories").value(hasItem(DEFAULT_MANDATORY_BUSINESS_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].additionalBusinessCategories").value(hasItem(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].mandatoryForCompany").value(hasItem(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForCompany").value(hasItem(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryForProfessional").value(hasItem(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForProfessional").value(hasItem(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCRegistrationDocType() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get the cRegistrationDocType
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types/{id}", cRegistrationDocType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cRegistrationDocType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hasExpirationDate").value(DEFAULT_HAS_EXPIRATION_DATE.booleanValue()))
            .andExpect(jsonPath("$.mandatoryBusinessCategories").value(DEFAULT_MANDATORY_BUSINESS_CATEGORIES.toString()))
            .andExpect(jsonPath("$.additionalBusinessCategories").value(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES.toString()))
            .andExpect(jsonPath("$.mandatoryForCompany").value(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.additionalForCompany").value(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.mandatoryForProfessional").value(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue()))
            .andExpect(jsonPath("$.additionalForProfessional").value(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCRegistrationDocTypesByIdFiltering() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        Long id = cRegistrationDocType.getId();

        defaultCRegistrationDocTypeShouldBeFound("id.equals=" + id);
        defaultCRegistrationDocTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCRegistrationDocTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCRegistrationDocTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCRegistrationDocTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCRegistrationDocTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name equals to DEFAULT_NAME
        defaultCRegistrationDocTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cRegistrationDocTypeList where name equals to UPDATED_NAME
        defaultCRegistrationDocTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name not equals to DEFAULT_NAME
        defaultCRegistrationDocTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cRegistrationDocTypeList where name not equals to UPDATED_NAME
        defaultCRegistrationDocTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCRegistrationDocTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cRegistrationDocTypeList where name equals to UPDATED_NAME
        defaultCRegistrationDocTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name is not null
        defaultCRegistrationDocTypeShouldBeFound("name.specified=true");

        // Get all the cRegistrationDocTypeList where name is null
        defaultCRegistrationDocTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name contains DEFAULT_NAME
        defaultCRegistrationDocTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cRegistrationDocTypeList where name contains UPDATED_NAME
        defaultCRegistrationDocTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where name does not contain DEFAULT_NAME
        defaultCRegistrationDocTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cRegistrationDocTypeList where name does not contain UPDATED_NAME
        defaultCRegistrationDocTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCRegistrationDocTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cRegistrationDocTypeList where description equals to UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCRegistrationDocTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cRegistrationDocTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cRegistrationDocTypeList where description equals to UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description is not null
        defaultCRegistrationDocTypeShouldBeFound("description.specified=true");

        // Get all the cRegistrationDocTypeList where description is null
        defaultCRegistrationDocTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description contains DEFAULT_DESCRIPTION
        defaultCRegistrationDocTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cRegistrationDocTypeList where description contains UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCRegistrationDocTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cRegistrationDocTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCRegistrationDocTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByHasExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where hasExpirationDate equals to DEFAULT_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldBeFound("hasExpirationDate.equals=" + DEFAULT_HAS_EXPIRATION_DATE);

        // Get all the cRegistrationDocTypeList where hasExpirationDate equals to UPDATED_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldNotBeFound("hasExpirationDate.equals=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByHasExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where hasExpirationDate not equals to DEFAULT_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldNotBeFound("hasExpirationDate.notEquals=" + DEFAULT_HAS_EXPIRATION_DATE);

        // Get all the cRegistrationDocTypeList where hasExpirationDate not equals to UPDATED_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldBeFound("hasExpirationDate.notEquals=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByHasExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where hasExpirationDate in DEFAULT_HAS_EXPIRATION_DATE or UPDATED_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldBeFound("hasExpirationDate.in=" + DEFAULT_HAS_EXPIRATION_DATE + "," + UPDATED_HAS_EXPIRATION_DATE);

        // Get all the cRegistrationDocTypeList where hasExpirationDate equals to UPDATED_HAS_EXPIRATION_DATE
        defaultCRegistrationDocTypeShouldNotBeFound("hasExpirationDate.in=" + UPDATED_HAS_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByHasExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where hasExpirationDate is not null
        defaultCRegistrationDocTypeShouldBeFound("hasExpirationDate.specified=true");

        // Get all the cRegistrationDocTypeList where hasExpirationDate is null
        defaultCRegistrationDocTypeShouldNotBeFound("hasExpirationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryBusinessCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories equals to DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("mandatoryBusinessCategories.equals=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryBusinessCategories.equals=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryBusinessCategoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories not equals to DEFAULT_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryBusinessCategories.notEquals=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories not equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("mandatoryBusinessCategories.notEquals=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryBusinessCategoriesIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories in DEFAULT_MANDATORY_BUSINESS_CATEGORIES or UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("mandatoryBusinessCategories.in=" + DEFAULT_MANDATORY_BUSINESS_CATEGORIES + "," + UPDATED_MANDATORY_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories equals to UPDATED_MANDATORY_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryBusinessCategories.in=" + UPDATED_MANDATORY_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryBusinessCategoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories is not null
        defaultCRegistrationDocTypeShouldBeFound("mandatoryBusinessCategories.specified=true");

        // Get all the cRegistrationDocTypeList where mandatoryBusinessCategories is null
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryBusinessCategories.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalBusinessCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories equals to DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("additionalBusinessCategories.equals=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("additionalBusinessCategories.equals=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalBusinessCategoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories not equals to DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("additionalBusinessCategories.notEquals=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories not equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("additionalBusinessCategories.notEquals=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalBusinessCategoriesIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories in DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES or UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldBeFound("additionalBusinessCategories.in=" + DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES + "," + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories equals to UPDATED_ADDITIONAL_BUSINESS_CATEGORIES
        defaultCRegistrationDocTypeShouldNotBeFound("additionalBusinessCategories.in=" + UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalBusinessCategoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories is not null
        defaultCRegistrationDocTypeShouldBeFound("additionalBusinessCategories.specified=true");

        // Get all the cRegistrationDocTypeList where additionalBusinessCategories is null
        defaultCRegistrationDocTypeShouldNotBeFound("additionalBusinessCategories.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany equals to DEFAULT_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForCompany.equals=" + DEFAULT_MANDATORY_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForCompany.equals=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany not equals to DEFAULT_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForCompany.notEquals=" + DEFAULT_MANDATORY_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany not equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForCompany.notEquals=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany in DEFAULT_MANDATORY_FOR_COMPANY or UPDATED_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForCompany.in=" + DEFAULT_MANDATORY_FOR_COMPANY + "," + UPDATED_MANDATORY_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany equals to UPDATED_MANDATORY_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForCompany.in=" + UPDATED_MANDATORY_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForCompany is not null
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForCompany.specified=true");

        // Get all the cRegistrationDocTypeList where mandatoryForCompany is null
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForCompany.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForCompany equals to DEFAULT_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("additionalForCompany.equals=" + DEFAULT_ADDITIONAL_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where additionalForCompany equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForCompany.equals=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForCompany not equals to DEFAULT_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForCompany.notEquals=" + DEFAULT_ADDITIONAL_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where additionalForCompany not equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("additionalForCompany.notEquals=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForCompany in DEFAULT_ADDITIONAL_FOR_COMPANY or UPDATED_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldBeFound("additionalForCompany.in=" + DEFAULT_ADDITIONAL_FOR_COMPANY + "," + UPDATED_ADDITIONAL_FOR_COMPANY);

        // Get all the cRegistrationDocTypeList where additionalForCompany equals to UPDATED_ADDITIONAL_FOR_COMPANY
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForCompany.in=" + UPDATED_ADDITIONAL_FOR_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForCompany is not null
        defaultCRegistrationDocTypeShouldBeFound("additionalForCompany.specified=true");

        // Get all the cRegistrationDocTypeList where additionalForCompany is null
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForCompany.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForProfessionalIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional equals to DEFAULT_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForProfessional.equals=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForProfessional.equals=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForProfessionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional not equals to DEFAULT_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForProfessional.notEquals=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional not equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForProfessional.notEquals=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForProfessionalIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional in DEFAULT_MANDATORY_FOR_PROFESSIONAL or UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForProfessional.in=" + DEFAULT_MANDATORY_FOR_PROFESSIONAL + "," + UPDATED_MANDATORY_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional equals to UPDATED_MANDATORY_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForProfessional.in=" + UPDATED_MANDATORY_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByMandatoryForProfessionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional is not null
        defaultCRegistrationDocTypeShouldBeFound("mandatoryForProfessional.specified=true");

        // Get all the cRegistrationDocTypeList where mandatoryForProfessional is null
        defaultCRegistrationDocTypeShouldNotBeFound("mandatoryForProfessional.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForProfessionalIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForProfessional equals to DEFAULT_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("additionalForProfessional.equals=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where additionalForProfessional equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForProfessional.equals=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForProfessionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForProfessional not equals to DEFAULT_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForProfessional.notEquals=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where additionalForProfessional not equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("additionalForProfessional.notEquals=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForProfessionalIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForProfessional in DEFAULT_ADDITIONAL_FOR_PROFESSIONAL or UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldBeFound("additionalForProfessional.in=" + DEFAULT_ADDITIONAL_FOR_PROFESSIONAL + "," + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);

        // Get all the cRegistrationDocTypeList where additionalForProfessional equals to UPDATED_ADDITIONAL_FOR_PROFESSIONAL
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForProfessional.in=" + UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdditionalForProfessionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where additionalForProfessional is not null
        defaultCRegistrationDocTypeShouldBeFound("additionalForProfessional.specified=true");

        // Get all the cRegistrationDocTypeList where additionalForProfessional is null
        defaultCRegistrationDocTypeShouldNotBeFound("additionalForProfessional.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where uid equals to DEFAULT_UID
        defaultCRegistrationDocTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cRegistrationDocTypeList where uid equals to UPDATED_UID
        defaultCRegistrationDocTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where uid not equals to DEFAULT_UID
        defaultCRegistrationDocTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cRegistrationDocTypeList where uid not equals to UPDATED_UID
        defaultCRegistrationDocTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCRegistrationDocTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cRegistrationDocTypeList where uid equals to UPDATED_UID
        defaultCRegistrationDocTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where uid is not null
        defaultCRegistrationDocTypeShouldBeFound("uid.specified=true");

        // Get all the cRegistrationDocTypeList where uid is null
        defaultCRegistrationDocTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where active equals to DEFAULT_ACTIVE
        defaultCRegistrationDocTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cRegistrationDocTypeList where active equals to UPDATED_ACTIVE
        defaultCRegistrationDocTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where active not equals to DEFAULT_ACTIVE
        defaultCRegistrationDocTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cRegistrationDocTypeList where active not equals to UPDATED_ACTIVE
        defaultCRegistrationDocTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCRegistrationDocTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cRegistrationDocTypeList where active equals to UPDATED_ACTIVE
        defaultCRegistrationDocTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        // Get all the cRegistrationDocTypeList where active is not null
        defaultCRegistrationDocTypeShouldBeFound("active.specified=true");

        // Get all the cRegistrationDocTypeList where active is null
        defaultCRegistrationDocTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cRegistrationDocType.getAdOrganization();
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cRegistrationDocTypeList where adOrganization equals to adOrganizationId
        defaultCRegistrationDocTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cRegistrationDocTypeList where adOrganization equals to adOrganizationId + 1
        defaultCRegistrationDocTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCRegistrationDocTypeShouldBeFound(String filter) throws Exception {
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegistrationDocType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hasExpirationDate").value(hasItem(DEFAULT_HAS_EXPIRATION_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryBusinessCategories").value(hasItem(DEFAULT_MANDATORY_BUSINESS_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].additionalBusinessCategories").value(hasItem(DEFAULT_ADDITIONAL_BUSINESS_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].mandatoryForCompany").value(hasItem(DEFAULT_MANDATORY_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForCompany").value(hasItem(DEFAULT_ADDITIONAL_FOR_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryForProfessional").value(hasItem(DEFAULT_MANDATORY_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalForProfessional").value(hasItem(DEFAULT_ADDITIONAL_FOR_PROFESSIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCRegistrationDocTypeShouldNotBeFound(String filter) throws Exception {
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCRegistrationDocType() throws Exception {
        // Get the cRegistrationDocType
        restCRegistrationDocTypeMockMvc.perform(get("/api/c-registration-doc-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRegistrationDocType() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        int databaseSizeBeforeUpdate = cRegistrationDocTypeRepository.findAll().size();

        // Update the cRegistrationDocType
        CRegistrationDocType updatedCRegistrationDocType = cRegistrationDocTypeRepository.findById(cRegistrationDocType.getId()).get();
        // Disconnect from session so that the updates on updatedCRegistrationDocType are not directly saved in db
        em.detach(updatedCRegistrationDocType);
        updatedCRegistrationDocType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hasExpirationDate(UPDATED_HAS_EXPIRATION_DATE)
            .mandatoryBusinessCategories(UPDATED_MANDATORY_BUSINESS_CATEGORIES)
            .additionalBusinessCategories(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES)
            .mandatoryForCompany(UPDATED_MANDATORY_FOR_COMPANY)
            .additionalForCompany(UPDATED_ADDITIONAL_FOR_COMPANY)
            .mandatoryForProfessional(UPDATED_MANDATORY_FOR_PROFESSIONAL)
            .additionalForProfessional(UPDATED_ADDITIONAL_FOR_PROFESSIONAL)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(updatedCRegistrationDocType);

        restCRegistrationDocTypeMockMvc.perform(put("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CRegistrationDocType in the database
        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeUpdate);
        CRegistrationDocType testCRegistrationDocType = cRegistrationDocTypeList.get(cRegistrationDocTypeList.size() - 1);
        assertThat(testCRegistrationDocType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRegistrationDocType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCRegistrationDocType.isHasExpirationDate()).isEqualTo(UPDATED_HAS_EXPIRATION_DATE);
        assertThat(testCRegistrationDocType.getMandatoryBusinessCategories()).isEqualTo(UPDATED_MANDATORY_BUSINESS_CATEGORIES);
        assertThat(testCRegistrationDocType.getAdditionalBusinessCategories()).isEqualTo(UPDATED_ADDITIONAL_BUSINESS_CATEGORIES);
        assertThat(testCRegistrationDocType.isMandatoryForCompany()).isEqualTo(UPDATED_MANDATORY_FOR_COMPANY);
        assertThat(testCRegistrationDocType.isAdditionalForCompany()).isEqualTo(UPDATED_ADDITIONAL_FOR_COMPANY);
        assertThat(testCRegistrationDocType.isMandatoryForProfessional()).isEqualTo(UPDATED_MANDATORY_FOR_PROFESSIONAL);
        assertThat(testCRegistrationDocType.isAdditionalForProfessional()).isEqualTo(UPDATED_ADDITIONAL_FOR_PROFESSIONAL);
        assertThat(testCRegistrationDocType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCRegistrationDocType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCRegistrationDocType() throws Exception {
        int databaseSizeBeforeUpdate = cRegistrationDocTypeRepository.findAll().size();

        // Create the CRegistrationDocType
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = cRegistrationDocTypeMapper.toDto(cRegistrationDocType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRegistrationDocTypeMockMvc.perform(put("/api/c-registration-doc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegistrationDocType in the database
        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCRegistrationDocType() throws Exception {
        // Initialize the database
        cRegistrationDocTypeRepository.saveAndFlush(cRegistrationDocType);

        int databaseSizeBeforeDelete = cRegistrationDocTypeRepository.findAll().size();

        // Delete the cRegistrationDocType
        restCRegistrationDocTypeMockMvc.perform(delete("/api/c-registration-doc-types/{id}", cRegistrationDocType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRegistrationDocType> cRegistrationDocTypeList = cRegistrationDocTypeRepository.findAll();
        assertThat(cRegistrationDocTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
