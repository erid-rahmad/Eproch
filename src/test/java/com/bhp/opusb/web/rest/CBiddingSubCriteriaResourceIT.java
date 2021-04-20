package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.repository.CBiddingSubCriteriaRepository;
import com.bhp.opusb.service.CBiddingSubCriteriaService;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaMapper;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaCriteria;
import com.bhp.opusb.service.CBiddingSubCriteriaQueryService;

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
 * Integration tests for the {@link CBiddingSubCriteriaResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBiddingSubCriteriaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MULTIPLE_OPTIONS = false;
    private static final Boolean UPDATED_MULTIPLE_OPTIONS = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBiddingSubCriteriaRepository cBiddingSubCriteriaRepository;

    @Autowired
    private CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper;

    @Autowired
    private CBiddingSubCriteriaService cBiddingSubCriteriaService;

    @Autowired
    private CBiddingSubCriteriaQueryService cBiddingSubCriteriaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBiddingSubCriteriaMockMvc;

    private CBiddingSubCriteria cBiddingSubCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingSubCriteria createEntity(EntityManager em) {
        CBiddingSubCriteria cBiddingSubCriteria = new CBiddingSubCriteria()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .evaluationType(DEFAULT_EVALUATION_TYPE)
            .multipleOptions(DEFAULT_MULTIPLE_OPTIONS)
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
        cBiddingSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cBiddingSubCriteria.setBiddingCriteria(cBiddingCriteria);
        return cBiddingSubCriteria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingSubCriteria createUpdatedEntity(EntityManager em) {
        CBiddingSubCriteria cBiddingSubCriteria = new CBiddingSubCriteria()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .multipleOptions(UPDATED_MULTIPLE_OPTIONS)
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
        cBiddingSubCriteria.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        cBiddingSubCriteria.setBiddingCriteria(cBiddingCriteria);
        return cBiddingSubCriteria;
    }

    @BeforeEach
    public void initTest() {
        cBiddingSubCriteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBiddingSubCriteria() throws Exception {
        int databaseSizeBeforeCreate = cBiddingSubCriteriaRepository.findAll().size();

        // Create the CBiddingSubCriteria
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteria);
        restCBiddingSubCriteriaMockMvc.perform(post("/api/c-bidding-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaDTO)))
            .andExpect(status().isCreated());

        // Validate the CBiddingSubCriteria in the database
        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        CBiddingSubCriteria testCBiddingSubCriteria = cBiddingSubCriteriaList.get(cBiddingSubCriteriaList.size() - 1);
        assertThat(testCBiddingSubCriteria.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBiddingSubCriteria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBiddingSubCriteria.getEvaluationType()).isEqualTo(DEFAULT_EVALUATION_TYPE);
        assertThat(testCBiddingSubCriteria.isMultipleOptions()).isEqualTo(DEFAULT_MULTIPLE_OPTIONS);
        assertThat(testCBiddingSubCriteria.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBiddingSubCriteria.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBiddingSubCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBiddingSubCriteriaRepository.findAll().size();

        // Create the CBiddingSubCriteria with an existing ID
        cBiddingSubCriteria.setId(1L);
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBiddingSubCriteriaMockMvc.perform(post("/api/c-bidding-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingSubCriteria in the database
        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBiddingSubCriteriaRepository.findAll().size();
        // set the field null
        cBiddingSubCriteria.setName(null);

        // Create the CBiddingSubCriteria, which fails.
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteria);

        restCBiddingSubCriteriaMockMvc.perform(post("/api/c-bidding-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteria() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].evaluationType").value(hasItem(DEFAULT_EVALUATION_TYPE)))
            .andExpect(jsonPath("$.[*].multipleOptions").value(hasItem(DEFAULT_MULTIPLE_OPTIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBiddingSubCriteria() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get the cBiddingSubCriteria
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria/{id}", cBiddingSubCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBiddingSubCriteria.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.evaluationType").value(DEFAULT_EVALUATION_TYPE))
            .andExpect(jsonPath("$.multipleOptions").value(DEFAULT_MULTIPLE_OPTIONS.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBiddingSubCriteriaByIdFiltering() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        Long id = cBiddingSubCriteria.getId();

        defaultCBiddingSubCriteriaShouldBeFound("id.equals=" + id);
        defaultCBiddingSubCriteriaShouldNotBeFound("id.notEquals=" + id);

        defaultCBiddingSubCriteriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBiddingSubCriteriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCBiddingSubCriteriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBiddingSubCriteriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name equals to DEFAULT_NAME
        defaultCBiddingSubCriteriaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaList where name equals to UPDATED_NAME
        defaultCBiddingSubCriteriaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name not equals to DEFAULT_NAME
        defaultCBiddingSubCriteriaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaList where name not equals to UPDATED_NAME
        defaultCBiddingSubCriteriaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBiddingSubCriteriaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBiddingSubCriteriaList where name equals to UPDATED_NAME
        defaultCBiddingSubCriteriaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name is not null
        defaultCBiddingSubCriteriaShouldBeFound("name.specified=true");

        // Get all the cBiddingSubCriteriaList where name is null
        defaultCBiddingSubCriteriaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name contains DEFAULT_NAME
        defaultCBiddingSubCriteriaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaList where name contains UPDATED_NAME
        defaultCBiddingSubCriteriaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where name does not contain DEFAULT_NAME
        defaultCBiddingSubCriteriaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBiddingSubCriteriaList where name does not contain UPDATED_NAME
        defaultCBiddingSubCriteriaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description equals to DEFAULT_DESCRIPTION
        defaultCBiddingSubCriteriaShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingSubCriteriaList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description not equals to DEFAULT_DESCRIPTION
        defaultCBiddingSubCriteriaShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingSubCriteriaList where description not equals to UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBiddingSubCriteriaList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description is not null
        defaultCBiddingSubCriteriaShouldBeFound("description.specified=true");

        // Get all the cBiddingSubCriteriaList where description is null
        defaultCBiddingSubCriteriaShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description contains DEFAULT_DESCRIPTION
        defaultCBiddingSubCriteriaShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingSubCriteriaList where description contains UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where description does not contain DEFAULT_DESCRIPTION
        defaultCBiddingSubCriteriaShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingSubCriteriaList where description does not contain UPDATED_DESCRIPTION
        defaultCBiddingSubCriteriaShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType equals to DEFAULT_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.equals=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cBiddingSubCriteriaList where evaluationType equals to UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.equals=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType not equals to DEFAULT_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.notEquals=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cBiddingSubCriteriaList where evaluationType not equals to UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.notEquals=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType in DEFAULT_EVALUATION_TYPE or UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.in=" + DEFAULT_EVALUATION_TYPE + "," + UPDATED_EVALUATION_TYPE);

        // Get all the cBiddingSubCriteriaList where evaluationType equals to UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.in=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType is not null
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.specified=true");

        // Get all the cBiddingSubCriteriaList where evaluationType is null
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType contains DEFAULT_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.contains=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cBiddingSubCriteriaList where evaluationType contains UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.contains=" + UPDATED_EVALUATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByEvaluationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where evaluationType does not contain DEFAULT_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldNotBeFound("evaluationType.doesNotContain=" + DEFAULT_EVALUATION_TYPE);

        // Get all the cBiddingSubCriteriaList where evaluationType does not contain UPDATED_EVALUATION_TYPE
        defaultCBiddingSubCriteriaShouldBeFound("evaluationType.doesNotContain=" + UPDATED_EVALUATION_TYPE);
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByMultipleOptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where multipleOptions equals to DEFAULT_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldBeFound("multipleOptions.equals=" + DEFAULT_MULTIPLE_OPTIONS);

        // Get all the cBiddingSubCriteriaList where multipleOptions equals to UPDATED_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldNotBeFound("multipleOptions.equals=" + UPDATED_MULTIPLE_OPTIONS);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByMultipleOptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where multipleOptions not equals to DEFAULT_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldNotBeFound("multipleOptions.notEquals=" + DEFAULT_MULTIPLE_OPTIONS);

        // Get all the cBiddingSubCriteriaList where multipleOptions not equals to UPDATED_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldBeFound("multipleOptions.notEquals=" + UPDATED_MULTIPLE_OPTIONS);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByMultipleOptionsIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where multipleOptions in DEFAULT_MULTIPLE_OPTIONS or UPDATED_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldBeFound("multipleOptions.in=" + DEFAULT_MULTIPLE_OPTIONS + "," + UPDATED_MULTIPLE_OPTIONS);

        // Get all the cBiddingSubCriteriaList where multipleOptions equals to UPDATED_MULTIPLE_OPTIONS
        defaultCBiddingSubCriteriaShouldNotBeFound("multipleOptions.in=" + UPDATED_MULTIPLE_OPTIONS);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByMultipleOptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where multipleOptions is not null
        defaultCBiddingSubCriteriaShouldBeFound("multipleOptions.specified=true");

        // Get all the cBiddingSubCriteriaList where multipleOptions is null
        defaultCBiddingSubCriteriaShouldNotBeFound("multipleOptions.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where uid equals to DEFAULT_UID
        defaultCBiddingSubCriteriaShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBiddingSubCriteriaList where uid equals to UPDATED_UID
        defaultCBiddingSubCriteriaShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where uid not equals to DEFAULT_UID
        defaultCBiddingSubCriteriaShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBiddingSubCriteriaList where uid not equals to UPDATED_UID
        defaultCBiddingSubCriteriaShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBiddingSubCriteriaShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBiddingSubCriteriaList where uid equals to UPDATED_UID
        defaultCBiddingSubCriteriaShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where uid is not null
        defaultCBiddingSubCriteriaShouldBeFound("uid.specified=true");

        // Get all the cBiddingSubCriteriaList where uid is null
        defaultCBiddingSubCriteriaShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where active equals to DEFAULT_ACTIVE
        defaultCBiddingSubCriteriaShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where active not equals to DEFAULT_ACTIVE
        defaultCBiddingSubCriteriaShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingSubCriteriaList where active not equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBiddingSubCriteriaShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBiddingSubCriteriaList where active equals to UPDATED_ACTIVE
        defaultCBiddingSubCriteriaShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        // Get all the cBiddingSubCriteriaList where active is not null
        defaultCBiddingSubCriteriaShouldBeFound("active.specified=true");

        // Get all the cBiddingSubCriteriaList where active is null
        defaultCBiddingSubCriteriaShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBiddingSubCriteria.getAdOrganization();
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBiddingSubCriteriaList where adOrganization equals to adOrganizationId
        defaultCBiddingSubCriteriaShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBiddingSubCriteriaList where adOrganization equals to adOrganizationId + 1
        defaultCBiddingSubCriteriaShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBiddingSubCriteriaByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = cBiddingSubCriteria.getBiddingCriteria();
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the cBiddingSubCriteriaList where biddingCriteria equals to biddingCriteriaId
        defaultCBiddingSubCriteriaShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the cBiddingSubCriteriaList where biddingCriteria equals to biddingCriteriaId + 1
        defaultCBiddingSubCriteriaShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBiddingSubCriteriaShouldBeFound(String filter) throws Exception {
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingSubCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].evaluationType").value(hasItem(DEFAULT_EVALUATION_TYPE)))
            .andExpect(jsonPath("$.[*].multipleOptions").value(hasItem(DEFAULT_MULTIPLE_OPTIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBiddingSubCriteriaShouldNotBeFound(String filter) throws Exception {
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBiddingSubCriteria() throws Exception {
        // Get the cBiddingSubCriteria
        restCBiddingSubCriteriaMockMvc.perform(get("/api/c-bidding-sub-criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBiddingSubCriteria() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        int databaseSizeBeforeUpdate = cBiddingSubCriteriaRepository.findAll().size();

        // Update the cBiddingSubCriteria
        CBiddingSubCriteria updatedCBiddingSubCriteria = cBiddingSubCriteriaRepository.findById(cBiddingSubCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedCBiddingSubCriteria are not directly saved in db
        em.detach(updatedCBiddingSubCriteria);
        updatedCBiddingSubCriteria
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .multipleOptions(UPDATED_MULTIPLE_OPTIONS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = cBiddingSubCriteriaMapper.toDto(updatedCBiddingSubCriteria);

        restCBiddingSubCriteriaMockMvc.perform(put("/api/c-bidding-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaDTO)))
            .andExpect(status().isOk());

        // Validate the CBiddingSubCriteria in the database
        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
        CBiddingSubCriteria testCBiddingSubCriteria = cBiddingSubCriteriaList.get(cBiddingSubCriteriaList.size() - 1);
        assertThat(testCBiddingSubCriteria.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBiddingSubCriteria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBiddingSubCriteria.getEvaluationType()).isEqualTo(UPDATED_EVALUATION_TYPE);
        assertThat(testCBiddingSubCriteria.isMultipleOptions()).isEqualTo(UPDATED_MULTIPLE_OPTIONS);
        assertThat(testCBiddingSubCriteria.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBiddingSubCriteria.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBiddingSubCriteria() throws Exception {
        int databaseSizeBeforeUpdate = cBiddingSubCriteriaRepository.findAll().size();

        // Create the CBiddingSubCriteria
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBiddingSubCriteriaMockMvc.perform(put("/api/c-bidding-sub-criteria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingSubCriteriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingSubCriteria in the database
        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBiddingSubCriteria() throws Exception {
        // Initialize the database
        cBiddingSubCriteriaRepository.saveAndFlush(cBiddingSubCriteria);

        int databaseSizeBeforeDelete = cBiddingSubCriteriaRepository.findAll().size();

        // Delete the cBiddingSubCriteria
        restCBiddingSubCriteriaMockMvc.perform(delete("/api/c-bidding-sub-criteria/{id}", cBiddingSubCriteria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBiddingSubCriteria> cBiddingSubCriteriaList = cBiddingSubCriteriaRepository.findAll();
        assertThat(cBiddingSubCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
