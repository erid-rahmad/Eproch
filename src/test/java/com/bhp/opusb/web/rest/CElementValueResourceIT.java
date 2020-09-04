package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CElementValueRepository;
import com.bhp.opusb.service.CElementValueService;
import com.bhp.opusb.service.dto.CElementValueDTO;
import com.bhp.opusb.service.mapper.CElementValueMapper;
import com.bhp.opusb.service.dto.CElementValueCriteria;
import com.bhp.opusb.service.CElementValueQueryService;

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

/**
 * Integration tests for the {@link CElementValueResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CElementValueResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CElementValueRepository cElementValueRepository;

    @Autowired
    private CElementValueMapper cElementValueMapper;

    @Autowired
    private CElementValueService cElementValueService;

    @Autowired
    private CElementValueQueryService cElementValueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCElementValueMockMvc;

    private CElementValue cElementValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CElementValue createEntity(EntityManager em) {
        CElementValue cElementValue = new CElementValue()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
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
        cElementValue.setAdOrganization(aDOrganization);
        return cElementValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CElementValue createUpdatedEntity(EntityManager em) {
        CElementValue cElementValue = new CElementValue()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
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
        cElementValue.setAdOrganization(aDOrganization);
        return cElementValue;
    }

    @BeforeEach
    public void initTest() {
        cElementValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCElementValue() throws Exception {
        int databaseSizeBeforeCreate = cElementValueRepository.findAll().size();

        // Create the CElementValue
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);
        restCElementValueMockMvc.perform(post("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isCreated());

        // Validate the CElementValue in the database
        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeCreate + 1);
        CElementValue testCElementValue = cElementValueList.get(cElementValueList.size() - 1);
        assertThat(testCElementValue.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCElementValue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCElementValue.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCElementValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCElementValue.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCElementValue.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCElementValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cElementValueRepository.findAll().size();

        // Create the CElementValue with an existing ID
        cElementValue.setId(1L);
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCElementValueMockMvc.perform(post("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CElementValue in the database
        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cElementValueRepository.findAll().size();
        // set the field null
        cElementValue.setCode(null);

        // Create the CElementValue, which fails.
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);

        restCElementValueMockMvc.perform(post("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isBadRequest());

        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cElementValueRepository.findAll().size();
        // set the field null
        cElementValue.setName(null);

        // Create the CElementValue, which fails.
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);

        restCElementValueMockMvc.perform(post("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isBadRequest());

        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cElementValueRepository.findAll().size();
        // set the field null
        cElementValue.setType(null);

        // Create the CElementValue, which fails.
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);

        restCElementValueMockMvc.perform(post("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isBadRequest());

        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCElementValues() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList
        restCElementValueMockMvc.perform(get("/api/c-element-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cElementValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCElementValue() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get the cElementValue
        restCElementValueMockMvc.perform(get("/api/c-element-values/{id}", cElementValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cElementValue.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCElementValuesByIdFiltering() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        Long id = cElementValue.getId();

        defaultCElementValueShouldBeFound("id.equals=" + id);
        defaultCElementValueShouldNotBeFound("id.notEquals=" + id);

        defaultCElementValueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCElementValueShouldNotBeFound("id.greaterThan=" + id);

        defaultCElementValueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCElementValueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCElementValuesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code equals to DEFAULT_CODE
        defaultCElementValueShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cElementValueList where code equals to UPDATED_CODE
        defaultCElementValueShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code not equals to DEFAULT_CODE
        defaultCElementValueShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cElementValueList where code not equals to UPDATED_CODE
        defaultCElementValueShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCElementValueShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cElementValueList where code equals to UPDATED_CODE
        defaultCElementValueShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code is not null
        defaultCElementValueShouldBeFound("code.specified=true");

        // Get all the cElementValueList where code is null
        defaultCElementValueShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCElementValuesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code contains DEFAULT_CODE
        defaultCElementValueShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cElementValueList where code contains UPDATED_CODE
        defaultCElementValueShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where code does not contain DEFAULT_CODE
        defaultCElementValueShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cElementValueList where code does not contain UPDATED_CODE
        defaultCElementValueShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCElementValuesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name equals to DEFAULT_NAME
        defaultCElementValueShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cElementValueList where name equals to UPDATED_NAME
        defaultCElementValueShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name not equals to DEFAULT_NAME
        defaultCElementValueShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cElementValueList where name not equals to UPDATED_NAME
        defaultCElementValueShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCElementValueShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cElementValueList where name equals to UPDATED_NAME
        defaultCElementValueShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name is not null
        defaultCElementValueShouldBeFound("name.specified=true");

        // Get all the cElementValueList where name is null
        defaultCElementValueShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCElementValuesByNameContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name contains DEFAULT_NAME
        defaultCElementValueShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cElementValueList where name contains UPDATED_NAME
        defaultCElementValueShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where name does not contain DEFAULT_NAME
        defaultCElementValueShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cElementValueList where name does not contain UPDATED_NAME
        defaultCElementValueShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCElementValuesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type equals to DEFAULT_TYPE
        defaultCElementValueShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cElementValueList where type equals to UPDATED_TYPE
        defaultCElementValueShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type not equals to DEFAULT_TYPE
        defaultCElementValueShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cElementValueList where type not equals to UPDATED_TYPE
        defaultCElementValueShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCElementValueShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cElementValueList where type equals to UPDATED_TYPE
        defaultCElementValueShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type is not null
        defaultCElementValueShouldBeFound("type.specified=true");

        // Get all the cElementValueList where type is null
        defaultCElementValueShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCElementValuesByTypeContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type contains DEFAULT_TYPE
        defaultCElementValueShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cElementValueList where type contains UPDATED_TYPE
        defaultCElementValueShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where type does not contain DEFAULT_TYPE
        defaultCElementValueShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cElementValueList where type does not contain UPDATED_TYPE
        defaultCElementValueShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCElementValuesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description equals to DEFAULT_DESCRIPTION
        defaultCElementValueShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cElementValueList where description equals to UPDATED_DESCRIPTION
        defaultCElementValueShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description not equals to DEFAULT_DESCRIPTION
        defaultCElementValueShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cElementValueList where description not equals to UPDATED_DESCRIPTION
        defaultCElementValueShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCElementValueShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cElementValueList where description equals to UPDATED_DESCRIPTION
        defaultCElementValueShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description is not null
        defaultCElementValueShouldBeFound("description.specified=true");

        // Get all the cElementValueList where description is null
        defaultCElementValueShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCElementValuesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description contains DEFAULT_DESCRIPTION
        defaultCElementValueShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cElementValueList where description contains UPDATED_DESCRIPTION
        defaultCElementValueShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where description does not contain DEFAULT_DESCRIPTION
        defaultCElementValueShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cElementValueList where description does not contain UPDATED_DESCRIPTION
        defaultCElementValueShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCElementValuesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where uid equals to DEFAULT_UID
        defaultCElementValueShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cElementValueList where uid equals to UPDATED_UID
        defaultCElementValueShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where uid not equals to DEFAULT_UID
        defaultCElementValueShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cElementValueList where uid not equals to UPDATED_UID
        defaultCElementValueShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where uid in DEFAULT_UID or UPDATED_UID
        defaultCElementValueShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cElementValueList where uid equals to UPDATED_UID
        defaultCElementValueShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where uid is not null
        defaultCElementValueShouldBeFound("uid.specified=true");

        // Get all the cElementValueList where uid is null
        defaultCElementValueShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCElementValuesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where active equals to DEFAULT_ACTIVE
        defaultCElementValueShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cElementValueList where active equals to UPDATED_ACTIVE
        defaultCElementValueShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where active not equals to DEFAULT_ACTIVE
        defaultCElementValueShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cElementValueList where active not equals to UPDATED_ACTIVE
        defaultCElementValueShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCElementValueShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cElementValueList where active equals to UPDATED_ACTIVE
        defaultCElementValueShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCElementValuesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        // Get all the cElementValueList where active is not null
        defaultCElementValueShouldBeFound("active.specified=true");

        // Get all the cElementValueList where active is null
        defaultCElementValueShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCElementValuesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cElementValue.getAdOrganization();
        cElementValueRepository.saveAndFlush(cElementValue);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cElementValueList where adOrganization equals to adOrganizationId
        defaultCElementValueShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cElementValueList where adOrganization equals to adOrganizationId + 1
        defaultCElementValueShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCElementValueShouldBeFound(String filter) throws Exception {
        restCElementValueMockMvc.perform(get("/api/c-element-values?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cElementValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCElementValueMockMvc.perform(get("/api/c-element-values/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCElementValueShouldNotBeFound(String filter) throws Exception {
        restCElementValueMockMvc.perform(get("/api/c-element-values?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCElementValueMockMvc.perform(get("/api/c-element-values/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCElementValue() throws Exception {
        // Get the cElementValue
        restCElementValueMockMvc.perform(get("/api/c-element-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCElementValue() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        int databaseSizeBeforeUpdate = cElementValueRepository.findAll().size();

        // Update the cElementValue
        CElementValue updatedCElementValue = cElementValueRepository.findById(cElementValue.getId()).get();
        // Disconnect from session so that the updates on updatedCElementValue are not directly saved in db
        em.detach(updatedCElementValue);
        updatedCElementValue
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(updatedCElementValue);

        restCElementValueMockMvc.perform(put("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isOk());

        // Validate the CElementValue in the database
        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeUpdate);
        CElementValue testCElementValue = cElementValueList.get(cElementValueList.size() - 1);
        assertThat(testCElementValue.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCElementValue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCElementValue.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCElementValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCElementValue.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCElementValue.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCElementValue() throws Exception {
        int databaseSizeBeforeUpdate = cElementValueRepository.findAll().size();

        // Create the CElementValue
        CElementValueDTO cElementValueDTO = cElementValueMapper.toDto(cElementValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCElementValueMockMvc.perform(put("/api/c-element-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cElementValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CElementValue in the database
        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCElementValue() throws Exception {
        // Initialize the database
        cElementValueRepository.saveAndFlush(cElementValue);

        int databaseSizeBeforeDelete = cElementValueRepository.findAll().size();

        // Delete the cElementValue
        restCElementValueMockMvc.perform(delete("/api/c-element-values/{id}", cElementValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CElementValue> cElementValueList = cElementValueRepository.findAll();
        assertThat(cElementValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
