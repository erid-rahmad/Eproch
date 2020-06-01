package com.bhp.opusb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.ADReferenceList;
import com.bhp.opusb.repository.ADReferenceListRepository;
import com.bhp.opusb.service.ADReferenceListQueryService;
import com.bhp.opusb.service.ADReferenceListService;
import com.bhp.opusb.service.dto.ADReferenceListDTO;
import com.bhp.opusb.service.mapper.ADReferenceListMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ADReferenceListResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADReferenceListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADReferenceListRepository aDReferenceListRepository;

    @Autowired
    private ADReferenceListMapper aDReferenceListMapper;

    @Autowired
    private ADReferenceListService aDReferenceListService;

    @Autowired
    private ADReferenceListQueryService aDReferenceListQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADReferenceListMockMvc;

    private ADReferenceList aDReferenceList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADReferenceList createEntity(EntityManager em) {
        ADReferenceList aDReferenceList = new ADReferenceList()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
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
        aDReferenceList.setAdOrganization(aDOrganization);
        // Add required entity
        ADReference aDReference;
        if (TestUtil.findAll(em, ADReference.class).isEmpty()) {
            aDReference = ADReferenceResourceIT.createEntity(em);
            em.persist(aDReference);
            em.flush();
        } else {
            aDReference = TestUtil.findAll(em, ADReference.class).get(0);
        }
        aDReferenceList.setAdReference(aDReference);
        return aDReferenceList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADReferenceList createUpdatedEntity(EntityManager em) {
        ADReferenceList aDReferenceList = new ADReferenceList()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
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
        aDReferenceList.setAdOrganization(aDOrganization);
        // Add required entity
        ADReference aDReference;
        if (TestUtil.findAll(em, ADReference.class).isEmpty()) {
            aDReference = ADReferenceResourceIT.createUpdatedEntity(em);
            em.persist(aDReference);
            em.flush();
        } else {
            aDReference = TestUtil.findAll(em, ADReference.class).get(0);
        }
        aDReferenceList.setAdReference(aDReference);
        return aDReferenceList;
    }

    @BeforeEach
    public void initTest() {
        aDReferenceList = createEntity(em);
    }

    @Test
    @Transactional
    public void createADReferenceList() throws Exception {
        int databaseSizeBeforeCreate = aDReferenceListRepository.findAll().size();

        // Create the ADReferenceList
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(aDReferenceList);
        restADReferenceListMockMvc.perform(post("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isCreated());

        // Validate the ADReferenceList in the database
        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeCreate + 1);
        ADReferenceList testADReferenceList = aDReferenceListList.get(aDReferenceListList.size() - 1);
        assertThat(testADReferenceList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADReferenceList.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testADReferenceList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADReferenceList.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADReferenceListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDReferenceListRepository.findAll().size();

        // Create the ADReferenceList with an existing ID
        aDReferenceList.setId(1L);
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(aDReferenceList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADReferenceListMockMvc.perform(post("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADReferenceList in the database
        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDReferenceListRepository.findAll().size();
        // set the field null
        aDReferenceList.setName(null);

        // Create the ADReferenceList, which fails.
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(aDReferenceList);

        restADReferenceListMockMvc.perform(post("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isBadRequest());

        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDReferenceListRepository.findAll().size();
        // set the field null
        aDReferenceList.setValue(null);

        // Create the ADReferenceList, which fails.
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(aDReferenceList);

        restADReferenceListMockMvc.perform(post("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isBadRequest());

        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADReferenceLists() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDReferenceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADReferenceList() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get the aDReferenceList
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists/{id}", aDReferenceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDReferenceList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADReferenceListsByIdFiltering() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        Long id = aDReferenceList.getId();

        defaultADReferenceListShouldBeFound("id.equals=" + id);
        defaultADReferenceListShouldNotBeFound("id.notEquals=" + id);

        defaultADReferenceListShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADReferenceListShouldNotBeFound("id.greaterThan=" + id);

        defaultADReferenceListShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADReferenceListShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADReferenceListsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name equals to DEFAULT_NAME
        defaultADReferenceListShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDReferenceListList where name equals to UPDATED_NAME
        defaultADReferenceListShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name not equals to DEFAULT_NAME
        defaultADReferenceListShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDReferenceListList where name not equals to UPDATED_NAME
        defaultADReferenceListShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADReferenceListShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDReferenceListList where name equals to UPDATED_NAME
        defaultADReferenceListShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name is not null
        defaultADReferenceListShouldBeFound("name.specified=true");

        // Get all the aDReferenceListList where name is null
        defaultADReferenceListShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferenceListsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name contains DEFAULT_NAME
        defaultADReferenceListShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDReferenceListList where name contains UPDATED_NAME
        defaultADReferenceListShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where name does not contain DEFAULT_NAME
        defaultADReferenceListShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDReferenceListList where name does not contain UPDATED_NAME
        defaultADReferenceListShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADReferenceListsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value equals to DEFAULT_VALUE
        defaultADReferenceListShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the aDReferenceListList where value equals to UPDATED_VALUE
        defaultADReferenceListShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value not equals to DEFAULT_VALUE
        defaultADReferenceListShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the aDReferenceListList where value not equals to UPDATED_VALUE
        defaultADReferenceListShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultADReferenceListShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the aDReferenceListList where value equals to UPDATED_VALUE
        defaultADReferenceListShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value is not null
        defaultADReferenceListShouldBeFound("value.specified=true");

        // Get all the aDReferenceListList where value is null
        defaultADReferenceListShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferenceListsByValueContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value contains DEFAULT_VALUE
        defaultADReferenceListShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the aDReferenceListList where value contains UPDATED_VALUE
        defaultADReferenceListShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where value does not contain DEFAULT_VALUE
        defaultADReferenceListShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the aDReferenceListList where value does not contain UPDATED_VALUE
        defaultADReferenceListShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description equals to DEFAULT_DESCRIPTION
        defaultADReferenceListShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceListList where description equals to UPDATED_DESCRIPTION
        defaultADReferenceListShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description not equals to DEFAULT_DESCRIPTION
        defaultADReferenceListShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceListList where description not equals to UPDATED_DESCRIPTION
        defaultADReferenceListShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADReferenceListShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDReferenceListList where description equals to UPDATED_DESCRIPTION
        defaultADReferenceListShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description is not null
        defaultADReferenceListShouldBeFound("description.specified=true");

        // Get all the aDReferenceListList where description is null
        defaultADReferenceListShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description contains DEFAULT_DESCRIPTION
        defaultADReferenceListShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceListList where description contains UPDATED_DESCRIPTION
        defaultADReferenceListShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where description does not contain DEFAULT_DESCRIPTION
        defaultADReferenceListShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDReferenceListList where description does not contain UPDATED_DESCRIPTION
        defaultADReferenceListShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADReferenceListsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where active equals to DEFAULT_ACTIVE
        defaultADReferenceListShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDReferenceListList where active equals to UPDATED_ACTIVE
        defaultADReferenceListShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where active not equals to DEFAULT_ACTIVE
        defaultADReferenceListShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDReferenceListList where active not equals to UPDATED_ACTIVE
        defaultADReferenceListShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADReferenceListShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDReferenceListList where active equals to UPDATED_ACTIVE
        defaultADReferenceListShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        // Get all the aDReferenceListList where active is not null
        defaultADReferenceListShouldBeFound("active.specified=true");

        // Get all the aDReferenceListList where active is null
        defaultADReferenceListShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADReferenceListsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDReferenceList.getAdOrganization();
        aDReferenceListRepository.saveAndFlush(aDReferenceList);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDReferenceListList where adOrganization equals to adOrganizationId
        defaultADReferenceListShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDReferenceListList where adOrganization equals to adOrganizationId + 1
        defaultADReferenceListShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllADReferenceListsByAdReferenceIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADReference adReference = aDReferenceList.getAdReference();
        aDReferenceListRepository.saveAndFlush(aDReferenceList);
        Long adReferenceId = adReference.getId();

        // Get all the aDReferenceListList where adReference equals to adReferenceId
        defaultADReferenceListShouldBeFound("adReferenceId.equals=" + adReferenceId);

        // Get all the aDReferenceListList where adReference equals to adReferenceId + 1
        defaultADReferenceListShouldNotBeFound("adReferenceId.equals=" + (adReferenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADReferenceListShouldBeFound(String filter) throws Exception {
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDReferenceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADReferenceListShouldNotBeFound(String filter) throws Exception {
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADReferenceList() throws Exception {
        // Get the aDReferenceList
        restADReferenceListMockMvc.perform(get("/api/ad-reference-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADReferenceList() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        int databaseSizeBeforeUpdate = aDReferenceListRepository.findAll().size();

        // Update the aDReferenceList
        ADReferenceList updatedADReferenceList = aDReferenceListRepository.findById(aDReferenceList.getId()).get();
        // Disconnect from session so that the updates on updatedADReferenceList are not directly saved in db
        em.detach(updatedADReferenceList);
        updatedADReferenceList
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(updatedADReferenceList);

        restADReferenceListMockMvc.perform(put("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isOk());

        // Validate the ADReferenceList in the database
        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeUpdate);
        ADReferenceList testADReferenceList = aDReferenceListList.get(aDReferenceListList.size() - 1);
        assertThat(testADReferenceList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADReferenceList.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testADReferenceList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADReferenceList.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADReferenceList() throws Exception {
        int databaseSizeBeforeUpdate = aDReferenceListRepository.findAll().size();

        // Create the ADReferenceList
        ADReferenceListDTO aDReferenceListDTO = aDReferenceListMapper.toDto(aDReferenceList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADReferenceListMockMvc.perform(put("/api/ad-reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDReferenceListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADReferenceList in the database
        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADReferenceList() throws Exception {
        // Initialize the database
        aDReferenceListRepository.saveAndFlush(aDReferenceList);

        int databaseSizeBeforeDelete = aDReferenceListRepository.findAll().size();

        // Delete the aDReferenceList
        restADReferenceListMockMvc.perform(delete("/api/ad-reference-lists/{id}", aDReferenceList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADReferenceList> aDReferenceListList = aDReferenceListRepository.findAll();
        assertThat(aDReferenceListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
