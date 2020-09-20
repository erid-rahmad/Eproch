package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdForm;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdFormRepository;
import com.bhp.opusb.service.AdFormService;
import com.bhp.opusb.service.dto.AdFormDTO;
import com.bhp.opusb.service.mapper.AdFormMapper;
import com.bhp.opusb.service.dto.AdFormCriteria;
import com.bhp.opusb.service.AdFormQueryService;

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

import com.bhp.opusb.domain.enumeration.AdAccessLevel;
/**
 * Integration tests for the {@link AdFormResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdFormResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final AdAccessLevel DEFAULT_ACCESS_LEVEL = AdAccessLevel.SYS;
    private static final AdAccessLevel UPDATED_ACCESS_LEVEL = AdAccessLevel.SYS_CLN;

    @Autowired
    private AdFormRepository adFormRepository;

    @Autowired
    private AdFormMapper adFormMapper;

    @Autowired
    private AdFormService adFormService;

    @Autowired
    private AdFormQueryService adFormQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdFormMockMvc;

    private AdForm adForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdForm createEntity(EntityManager em) {
        AdForm adForm = new AdForm()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .formName(DEFAULT_FORM_NAME)
            .accessLevel(DEFAULT_ACCESS_LEVEL);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adForm.setAdOrganization(aDOrganization);
        return adForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdForm createUpdatedEntity(EntityManager em) {
        AdForm adForm = new AdForm()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .formName(UPDATED_FORM_NAME)
            .accessLevel(UPDATED_ACCESS_LEVEL);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adForm.setAdOrganization(aDOrganization);
        return adForm;
    }

    @BeforeEach
    public void initTest() {
        adForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdForm() throws Exception {
        int databaseSizeBeforeCreate = adFormRepository.findAll().size();

        // Create the AdForm
        AdFormDTO adFormDTO = adFormMapper.toDto(adForm);
        restAdFormMockMvc.perform(post("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isCreated());

        // Validate the AdForm in the database
        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeCreate + 1);
        AdForm testAdForm = adFormList.get(adFormList.size() - 1);
        assertThat(testAdForm.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdForm.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdForm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdForm.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testAdForm.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void createAdFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adFormRepository.findAll().size();

        // Create the AdForm with an existing ID
        adForm.setId(1L);
        AdFormDTO adFormDTO = adFormMapper.toDto(adForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdFormMockMvc.perform(post("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdForm in the database
        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adFormRepository.findAll().size();
        // set the field null
        adForm.setName(null);

        // Create the AdForm, which fails.
        AdFormDTO adFormDTO = adFormMapper.toDto(adForm);

        restAdFormMockMvc.perform(post("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isBadRequest());

        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adFormRepository.findAll().size();
        // set the field null
        adForm.setFormName(null);

        // Create the AdForm, which fails.
        AdFormDTO adFormDTO = adFormMapper.toDto(adForm);

        restAdFormMockMvc.perform(post("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isBadRequest());

        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdForms() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList
        restAdFormMockMvc.perform(get("/api/ad-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getAdForm() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get the adForm
        restAdFormMockMvc.perform(get("/api/ad-forms/{id}", adForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adForm.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
            .andExpect(jsonPath("$.accessLevel").value(DEFAULT_ACCESS_LEVEL.toString()));
    }


    @Test
    @Transactional
    public void getAdFormsByIdFiltering() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        Long id = adForm.getId();

        defaultAdFormShouldBeFound("id.equals=" + id);
        defaultAdFormShouldNotBeFound("id.notEquals=" + id);

        defaultAdFormShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdFormShouldNotBeFound("id.greaterThan=" + id);

        defaultAdFormShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdFormShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdFormsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where uid equals to DEFAULT_UID
        defaultAdFormShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adFormList where uid equals to UPDATED_UID
        defaultAdFormShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdFormsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where uid not equals to DEFAULT_UID
        defaultAdFormShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adFormList where uid not equals to UPDATED_UID
        defaultAdFormShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdFormsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdFormShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adFormList where uid equals to UPDATED_UID
        defaultAdFormShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdFormsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where uid is not null
        defaultAdFormShouldBeFound("uid.specified=true");

        // Get all the adFormList where uid is null
        defaultAdFormShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdFormsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where active equals to DEFAULT_ACTIVE
        defaultAdFormShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adFormList where active equals to UPDATED_ACTIVE
        defaultAdFormShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdFormsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where active not equals to DEFAULT_ACTIVE
        defaultAdFormShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adFormList where active not equals to UPDATED_ACTIVE
        defaultAdFormShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdFormsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdFormShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adFormList where active equals to UPDATED_ACTIVE
        defaultAdFormShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdFormsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where active is not null
        defaultAdFormShouldBeFound("active.specified=true");

        // Get all the adFormList where active is null
        defaultAdFormShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdFormsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name equals to DEFAULT_NAME
        defaultAdFormShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adFormList where name equals to UPDATED_NAME
        defaultAdFormShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name not equals to DEFAULT_NAME
        defaultAdFormShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adFormList where name not equals to UPDATED_NAME
        defaultAdFormShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdFormShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adFormList where name equals to UPDATED_NAME
        defaultAdFormShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name is not null
        defaultAdFormShouldBeFound("name.specified=true");

        // Get all the adFormList where name is null
        defaultAdFormShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdFormsByNameContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name contains DEFAULT_NAME
        defaultAdFormShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adFormList where name contains UPDATED_NAME
        defaultAdFormShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where name does not contain DEFAULT_NAME
        defaultAdFormShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adFormList where name does not contain UPDATED_NAME
        defaultAdFormShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdFormsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description equals to DEFAULT_DESCRIPTION
        defaultAdFormShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adFormList where description equals to UPDATED_DESCRIPTION
        defaultAdFormShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdFormsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description not equals to DEFAULT_DESCRIPTION
        defaultAdFormShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adFormList where description not equals to UPDATED_DESCRIPTION
        defaultAdFormShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdFormsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdFormShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adFormList where description equals to UPDATED_DESCRIPTION
        defaultAdFormShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdFormsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description is not null
        defaultAdFormShouldBeFound("description.specified=true");

        // Get all the adFormList where description is null
        defaultAdFormShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdFormsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description contains DEFAULT_DESCRIPTION
        defaultAdFormShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adFormList where description contains UPDATED_DESCRIPTION
        defaultAdFormShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdFormsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where description does not contain DEFAULT_DESCRIPTION
        defaultAdFormShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adFormList where description does not contain UPDATED_DESCRIPTION
        defaultAdFormShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdFormsByFormNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName equals to DEFAULT_FORM_NAME
        defaultAdFormShouldBeFound("formName.equals=" + DEFAULT_FORM_NAME);

        // Get all the adFormList where formName equals to UPDATED_FORM_NAME
        defaultAdFormShouldNotBeFound("formName.equals=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByFormNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName not equals to DEFAULT_FORM_NAME
        defaultAdFormShouldNotBeFound("formName.notEquals=" + DEFAULT_FORM_NAME);

        // Get all the adFormList where formName not equals to UPDATED_FORM_NAME
        defaultAdFormShouldBeFound("formName.notEquals=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByFormNameIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName in DEFAULT_FORM_NAME or UPDATED_FORM_NAME
        defaultAdFormShouldBeFound("formName.in=" + DEFAULT_FORM_NAME + "," + UPDATED_FORM_NAME);

        // Get all the adFormList where formName equals to UPDATED_FORM_NAME
        defaultAdFormShouldNotBeFound("formName.in=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByFormNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName is not null
        defaultAdFormShouldBeFound("formName.specified=true");

        // Get all the adFormList where formName is null
        defaultAdFormShouldNotBeFound("formName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdFormsByFormNameContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName contains DEFAULT_FORM_NAME
        defaultAdFormShouldBeFound("formName.contains=" + DEFAULT_FORM_NAME);

        // Get all the adFormList where formName contains UPDATED_FORM_NAME
        defaultAdFormShouldNotBeFound("formName.contains=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllAdFormsByFormNameNotContainsSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where formName does not contain DEFAULT_FORM_NAME
        defaultAdFormShouldNotBeFound("formName.doesNotContain=" + DEFAULT_FORM_NAME);

        // Get all the adFormList where formName does not contain UPDATED_FORM_NAME
        defaultAdFormShouldBeFound("formName.doesNotContain=" + UPDATED_FORM_NAME);
    }


    @Test
    @Transactional
    public void getAllAdFormsByAccessLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where accessLevel equals to DEFAULT_ACCESS_LEVEL
        defaultAdFormShouldBeFound("accessLevel.equals=" + DEFAULT_ACCESS_LEVEL);

        // Get all the adFormList where accessLevel equals to UPDATED_ACCESS_LEVEL
        defaultAdFormShouldNotBeFound("accessLevel.equals=" + UPDATED_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdFormsByAccessLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where accessLevel not equals to DEFAULT_ACCESS_LEVEL
        defaultAdFormShouldNotBeFound("accessLevel.notEquals=" + DEFAULT_ACCESS_LEVEL);

        // Get all the adFormList where accessLevel not equals to UPDATED_ACCESS_LEVEL
        defaultAdFormShouldBeFound("accessLevel.notEquals=" + UPDATED_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdFormsByAccessLevelIsInShouldWork() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where accessLevel in DEFAULT_ACCESS_LEVEL or UPDATED_ACCESS_LEVEL
        defaultAdFormShouldBeFound("accessLevel.in=" + DEFAULT_ACCESS_LEVEL + "," + UPDATED_ACCESS_LEVEL);

        // Get all the adFormList where accessLevel equals to UPDATED_ACCESS_LEVEL
        defaultAdFormShouldNotBeFound("accessLevel.in=" + UPDATED_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdFormsByAccessLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        // Get all the adFormList where accessLevel is not null
        defaultAdFormShouldBeFound("accessLevel.specified=true");

        // Get all the adFormList where accessLevel is null
        defaultAdFormShouldNotBeFound("accessLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdFormsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adForm.getAdOrganization();
        adFormRepository.saveAndFlush(adForm);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adFormList where adOrganization equals to adOrganizationId
        defaultAdFormShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adFormList where adOrganization equals to adOrganizationId + 1
        defaultAdFormShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdFormShouldBeFound(String filter) throws Exception {
        restAdFormMockMvc.perform(get("/api/ad-forms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL.toString())));

        // Check, that the count call also returns 1
        restAdFormMockMvc.perform(get("/api/ad-forms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdFormShouldNotBeFound(String filter) throws Exception {
        restAdFormMockMvc.perform(get("/api/ad-forms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdFormMockMvc.perform(get("/api/ad-forms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdForm() throws Exception {
        // Get the adForm
        restAdFormMockMvc.perform(get("/api/ad-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdForm() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        int databaseSizeBeforeUpdate = adFormRepository.findAll().size();

        // Update the adForm
        AdForm updatedAdForm = adFormRepository.findById(adForm.getId()).get();
        // Disconnect from session so that the updates on updatedAdForm are not directly saved in db
        em.detach(updatedAdForm);
        updatedAdForm
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .formName(UPDATED_FORM_NAME)
            .accessLevel(UPDATED_ACCESS_LEVEL);
        AdFormDTO adFormDTO = adFormMapper.toDto(updatedAdForm);

        restAdFormMockMvc.perform(put("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isOk());

        // Validate the AdForm in the database
        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeUpdate);
        AdForm testAdForm = adFormList.get(adFormList.size() - 1);
        assertThat(testAdForm.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdForm.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdForm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdForm.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testAdForm.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingAdForm() throws Exception {
        int databaseSizeBeforeUpdate = adFormRepository.findAll().size();

        // Create the AdForm
        AdFormDTO adFormDTO = adFormMapper.toDto(adForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdFormMockMvc.perform(put("/api/ad-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdForm in the database
        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdForm() throws Exception {
        // Initialize the database
        adFormRepository.saveAndFlush(adForm);

        int databaseSizeBeforeDelete = adFormRepository.findAll().size();

        // Delete the adForm
        restAdFormMockMvc.perform(delete("/api/ad-forms/{id}", adForm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdForm> adFormList = adFormRepository.findAll();
        assertThat(adFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
