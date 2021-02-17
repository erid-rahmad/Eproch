package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.repository.CBiddingTypeRepository;
import com.bhp.opusb.service.CBiddingTypeService;
import com.bhp.opusb.service.dto.CBiddingTypeDTO;
import com.bhp.opusb.service.mapper.CBiddingTypeMapper;
import com.bhp.opusb.service.dto.CBiddingTypeCriteria;
import com.bhp.opusb.service.CBiddingTypeQueryService;

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
 * Integration tests for the {@link CBiddingTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBiddingTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COST_EVALUATION_SELECTION = false;
    private static final Boolean UPDATED_COST_EVALUATION_SELECTION = true;

    private static final Boolean DEFAULT_SELECTED_WINNER = false;
    private static final Boolean UPDATED_SELECTED_WINNER = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBiddingTypeRepository cBiddingTypeRepository;

    @Autowired
    private CBiddingTypeMapper cBiddingTypeMapper;

    @Autowired
    private CBiddingTypeService cBiddingTypeService;

    @Autowired
    private CBiddingTypeQueryService cBiddingTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBiddingTypeMockMvc;

    private CBiddingType cBiddingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingType createEntity(EntityManager em) {
        CBiddingType cBiddingType = new CBiddingType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .costEvaluationSelection(DEFAULT_COST_EVALUATION_SELECTION)
            .selectedWinner(DEFAULT_SELECTED_WINNER)
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
        cBiddingType.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cBiddingType.setProductClassification(cProductClassification);
        return cBiddingType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBiddingType createUpdatedEntity(EntityManager em) {
        CBiddingType cBiddingType = new CBiddingType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .costEvaluationSelection(UPDATED_COST_EVALUATION_SELECTION)
            .selectedWinner(UPDATED_SELECTED_WINNER)
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
        cBiddingType.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createUpdatedEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cBiddingType.setProductClassification(cProductClassification);
        return cBiddingType;
    }

    @BeforeEach
    public void initTest() {
        cBiddingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBiddingType() throws Exception {
        int databaseSizeBeforeCreate = cBiddingTypeRepository.findAll().size();

        // Create the CBiddingType
        CBiddingTypeDTO cBiddingTypeDTO = cBiddingTypeMapper.toDto(cBiddingType);
        restCBiddingTypeMockMvc.perform(post("/api/c-bidding-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CBiddingType in the database
        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CBiddingType testCBiddingType = cBiddingTypeList.get(cBiddingTypeList.size() - 1);
        assertThat(testCBiddingType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBiddingType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBiddingType.isCostEvaluationSelection()).isEqualTo(DEFAULT_COST_EVALUATION_SELECTION);
        assertThat(testCBiddingType.isSelectedWinner()).isEqualTo(DEFAULT_SELECTED_WINNER);
        assertThat(testCBiddingType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBiddingType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBiddingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBiddingTypeRepository.findAll().size();

        // Create the CBiddingType with an existing ID
        cBiddingType.setId(1L);
        CBiddingTypeDTO cBiddingTypeDTO = cBiddingTypeMapper.toDto(cBiddingType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBiddingTypeMockMvc.perform(post("/api/c-bidding-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingType in the database
        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBiddingTypeRepository.findAll().size();
        // set the field null
        cBiddingType.setName(null);

        // Create the CBiddingType, which fails.
        CBiddingTypeDTO cBiddingTypeDTO = cBiddingTypeMapper.toDto(cBiddingType);

        restCBiddingTypeMockMvc.perform(post("/api/c-bidding-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypes() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].costEvaluationSelection").value(hasItem(DEFAULT_COST_EVALUATION_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedWinner").value(hasItem(DEFAULT_SELECTED_WINNER.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBiddingType() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get the cBiddingType
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types/{id}", cBiddingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBiddingType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.costEvaluationSelection").value(DEFAULT_COST_EVALUATION_SELECTION.booleanValue()))
            .andExpect(jsonPath("$.selectedWinner").value(DEFAULT_SELECTED_WINNER.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBiddingTypesByIdFiltering() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        Long id = cBiddingType.getId();

        defaultCBiddingTypeShouldBeFound("id.equals=" + id);
        defaultCBiddingTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCBiddingTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBiddingTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCBiddingTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBiddingTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBiddingTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name equals to DEFAULT_NAME
        defaultCBiddingTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBiddingTypeList where name equals to UPDATED_NAME
        defaultCBiddingTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name not equals to DEFAULT_NAME
        defaultCBiddingTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBiddingTypeList where name not equals to UPDATED_NAME
        defaultCBiddingTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBiddingTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBiddingTypeList where name equals to UPDATED_NAME
        defaultCBiddingTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name is not null
        defaultCBiddingTypeShouldBeFound("name.specified=true");

        // Get all the cBiddingTypeList where name is null
        defaultCBiddingTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name contains DEFAULT_NAME
        defaultCBiddingTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBiddingTypeList where name contains UPDATED_NAME
        defaultCBiddingTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where name does not contain DEFAULT_NAME
        defaultCBiddingTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBiddingTypeList where name does not contain UPDATED_NAME
        defaultCBiddingTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCBiddingTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingTypeList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCBiddingTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBiddingTypeList where description equals to UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description is not null
        defaultCBiddingTypeShouldBeFound("description.specified=true");

        // Get all the cBiddingTypeList where description is null
        defaultCBiddingTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description contains DEFAULT_DESCRIPTION
        defaultCBiddingTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingTypeList where description contains UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCBiddingTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBiddingTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCBiddingTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBiddingTypesByCostEvaluationSelectionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where costEvaluationSelection equals to DEFAULT_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldBeFound("costEvaluationSelection.equals=" + DEFAULT_COST_EVALUATION_SELECTION);

        // Get all the cBiddingTypeList where costEvaluationSelection equals to UPDATED_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldNotBeFound("costEvaluationSelection.equals=" + UPDATED_COST_EVALUATION_SELECTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByCostEvaluationSelectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where costEvaluationSelection not equals to DEFAULT_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldNotBeFound("costEvaluationSelection.notEquals=" + DEFAULT_COST_EVALUATION_SELECTION);

        // Get all the cBiddingTypeList where costEvaluationSelection not equals to UPDATED_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldBeFound("costEvaluationSelection.notEquals=" + UPDATED_COST_EVALUATION_SELECTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByCostEvaluationSelectionIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where costEvaluationSelection in DEFAULT_COST_EVALUATION_SELECTION or UPDATED_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldBeFound("costEvaluationSelection.in=" + DEFAULT_COST_EVALUATION_SELECTION + "," + UPDATED_COST_EVALUATION_SELECTION);

        // Get all the cBiddingTypeList where costEvaluationSelection equals to UPDATED_COST_EVALUATION_SELECTION
        defaultCBiddingTypeShouldNotBeFound("costEvaluationSelection.in=" + UPDATED_COST_EVALUATION_SELECTION);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByCostEvaluationSelectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where costEvaluationSelection is not null
        defaultCBiddingTypeShouldBeFound("costEvaluationSelection.specified=true");

        // Get all the cBiddingTypeList where costEvaluationSelection is null
        defaultCBiddingTypeShouldNotBeFound("costEvaluationSelection.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesBySelectedWinnerIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where selectedWinner equals to DEFAULT_SELECTED_WINNER
        defaultCBiddingTypeShouldBeFound("selectedWinner.equals=" + DEFAULT_SELECTED_WINNER);

        // Get all the cBiddingTypeList where selectedWinner equals to UPDATED_SELECTED_WINNER
        defaultCBiddingTypeShouldNotBeFound("selectedWinner.equals=" + UPDATED_SELECTED_WINNER);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesBySelectedWinnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where selectedWinner not equals to DEFAULT_SELECTED_WINNER
        defaultCBiddingTypeShouldNotBeFound("selectedWinner.notEquals=" + DEFAULT_SELECTED_WINNER);

        // Get all the cBiddingTypeList where selectedWinner not equals to UPDATED_SELECTED_WINNER
        defaultCBiddingTypeShouldBeFound("selectedWinner.notEquals=" + UPDATED_SELECTED_WINNER);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesBySelectedWinnerIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where selectedWinner in DEFAULT_SELECTED_WINNER or UPDATED_SELECTED_WINNER
        defaultCBiddingTypeShouldBeFound("selectedWinner.in=" + DEFAULT_SELECTED_WINNER + "," + UPDATED_SELECTED_WINNER);

        // Get all the cBiddingTypeList where selectedWinner equals to UPDATED_SELECTED_WINNER
        defaultCBiddingTypeShouldNotBeFound("selectedWinner.in=" + UPDATED_SELECTED_WINNER);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesBySelectedWinnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where selectedWinner is not null
        defaultCBiddingTypeShouldBeFound("selectedWinner.specified=true");

        // Get all the cBiddingTypeList where selectedWinner is null
        defaultCBiddingTypeShouldNotBeFound("selectedWinner.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where uid equals to DEFAULT_UID
        defaultCBiddingTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBiddingTypeList where uid equals to UPDATED_UID
        defaultCBiddingTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where uid not equals to DEFAULT_UID
        defaultCBiddingTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBiddingTypeList where uid not equals to UPDATED_UID
        defaultCBiddingTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBiddingTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBiddingTypeList where uid equals to UPDATED_UID
        defaultCBiddingTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where uid is not null
        defaultCBiddingTypeShouldBeFound("uid.specified=true");

        // Get all the cBiddingTypeList where uid is null
        defaultCBiddingTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where active equals to DEFAULT_ACTIVE
        defaultCBiddingTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingTypeList where active equals to UPDATED_ACTIVE
        defaultCBiddingTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where active not equals to DEFAULT_ACTIVE
        defaultCBiddingTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBiddingTypeList where active not equals to UPDATED_ACTIVE
        defaultCBiddingTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBiddingTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBiddingTypeList where active equals to UPDATED_ACTIVE
        defaultCBiddingTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        // Get all the cBiddingTypeList where active is not null
        defaultCBiddingTypeShouldBeFound("active.specified=true");

        // Get all the cBiddingTypeList where active is null
        defaultCBiddingTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBiddingTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBiddingType.getAdOrganization();
        cBiddingTypeRepository.saveAndFlush(cBiddingType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBiddingTypeList where adOrganization equals to adOrganizationId
        defaultCBiddingTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBiddingTypeList where adOrganization equals to adOrganizationId + 1
        defaultCBiddingTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCBiddingTypesByProductClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductClassification productClassification = cBiddingType.getProductClassification();
        cBiddingTypeRepository.saveAndFlush(cBiddingType);
        Long productClassificationId = productClassification.getId();

        // Get all the cBiddingTypeList where productClassification equals to productClassificationId
        defaultCBiddingTypeShouldBeFound("productClassificationId.equals=" + productClassificationId);

        // Get all the cBiddingTypeList where productClassification equals to productClassificationId + 1
        defaultCBiddingTypeShouldNotBeFound("productClassificationId.equals=" + (productClassificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBiddingTypeShouldBeFound(String filter) throws Exception {
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBiddingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].costEvaluationSelection").value(hasItem(DEFAULT_COST_EVALUATION_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedWinner").value(hasItem(DEFAULT_SELECTED_WINNER.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBiddingTypeShouldNotBeFound(String filter) throws Exception {
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBiddingType() throws Exception {
        // Get the cBiddingType
        restCBiddingTypeMockMvc.perform(get("/api/c-bidding-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBiddingType() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        int databaseSizeBeforeUpdate = cBiddingTypeRepository.findAll().size();

        // Update the cBiddingType
        CBiddingType updatedCBiddingType = cBiddingTypeRepository.findById(cBiddingType.getId()).get();
        // Disconnect from session so that the updates on updatedCBiddingType are not directly saved in db
        em.detach(updatedCBiddingType);
        updatedCBiddingType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .costEvaluationSelection(UPDATED_COST_EVALUATION_SELECTION)
            .selectedWinner(UPDATED_SELECTED_WINNER)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBiddingTypeDTO cBiddingTypeDTO = cBiddingTypeMapper.toDto(updatedCBiddingType);

        restCBiddingTypeMockMvc.perform(put("/api/c-bidding-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CBiddingType in the database
        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeUpdate);
        CBiddingType testCBiddingType = cBiddingTypeList.get(cBiddingTypeList.size() - 1);
        assertThat(testCBiddingType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBiddingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBiddingType.isCostEvaluationSelection()).isEqualTo(UPDATED_COST_EVALUATION_SELECTION);
        assertThat(testCBiddingType.isSelectedWinner()).isEqualTo(UPDATED_SELECTED_WINNER);
        assertThat(testCBiddingType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBiddingType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBiddingType() throws Exception {
        int databaseSizeBeforeUpdate = cBiddingTypeRepository.findAll().size();

        // Create the CBiddingType
        CBiddingTypeDTO cBiddingTypeDTO = cBiddingTypeMapper.toDto(cBiddingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBiddingTypeMockMvc.perform(put("/api/c-bidding-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBiddingTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBiddingType in the database
        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBiddingType() throws Exception {
        // Initialize the database
        cBiddingTypeRepository.saveAndFlush(cBiddingType);

        int databaseSizeBeforeDelete = cBiddingTypeRepository.findAll().size();

        // Delete the cBiddingType
        restCBiddingTypeMockMvc.perform(delete("/api/c-bidding-types/{id}", cBiddingType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBiddingType> cBiddingTypeList = cBiddingTypeRepository.findAll();
        assertThat(cBiddingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
