package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTriggerParam;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.repository.AdTriggerParamRepository;
import com.bhp.opusb.service.AdTriggerParamService;
import com.bhp.opusb.service.dto.AdTriggerParamDTO;
import com.bhp.opusb.service.mapper.AdTriggerParamMapper;
import com.bhp.opusb.service.dto.AdTriggerParamCriteria;
import com.bhp.opusb.service.AdTriggerParamQueryService;

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
 * Integration tests for the {@link AdTriggerParamResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTriggerParamResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_MANDATORY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_READ_ONLY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_READ_ONLY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT_PATTERN = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;
    private static final Integer SMALLER_MIN_LENGTH = 1 - 1;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;
    private static final Integer SMALLER_MAX_LENGTH = 1 - 1;

    private static final Long DEFAULT_MIN_VALUE = 1L;
    private static final Long UPDATED_MIN_VALUE = 2L;
    private static final Long SMALLER_MIN_VALUE = 1L - 1L;

    private static final Long DEFAULT_MAX_VALUE = 1L;
    private static final Long UPDATED_MAX_VALUE = 2L;
    private static final Long SMALLER_MAX_VALUE = 1L - 1L;

    @Autowired
    private AdTriggerParamRepository adTriggerParamRepository;

    @Autowired
    private AdTriggerParamMapper adTriggerParamMapper;

    @Autowired
    private AdTriggerParamService adTriggerParamService;

    @Autowired
    private AdTriggerParamQueryService adTriggerParamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTriggerParamMockMvc;

    private AdTriggerParam adTriggerParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTriggerParam createEntity(EntityManager em) {
        AdTriggerParam adTriggerParam = new AdTriggerParam()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .mandatory(DEFAULT_MANDATORY)
            .mandatoryLogic(DEFAULT_MANDATORY_LOGIC)
            .displayLogic(DEFAULT_DISPLAY_LOGIC)
            .readOnlyLogic(DEFAULT_READ_ONLY_LOGIC)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .formatPattern(DEFAULT_FORMAT_PATTERN)
            .minLength(DEFAULT_MIN_LENGTH)
            .maxLength(DEFAULT_MAX_LENGTH)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTriggerParam.setAdOrganization(aDOrganization);
        // Add required entity
        AdTrigger adTrigger;
        if (TestUtil.findAll(em, AdTrigger.class).isEmpty()) {
            adTrigger = AdTriggerResourceIT.createEntity(em);
            em.persist(adTrigger);
            em.flush();
        } else {
            adTrigger = TestUtil.findAll(em, AdTrigger.class).get(0);
        }
        adTriggerParam.setAdTrigger(adTrigger);
        return adTriggerParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTriggerParam createUpdatedEntity(EntityManager em) {
        AdTriggerParam adTriggerParam = new AdTriggerParam()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTriggerParam.setAdOrganization(aDOrganization);
        // Add required entity
        AdTrigger adTrigger;
        if (TestUtil.findAll(em, AdTrigger.class).isEmpty()) {
            adTrigger = AdTriggerResourceIT.createUpdatedEntity(em);
            em.persist(adTrigger);
            em.flush();
        } else {
            adTrigger = TestUtil.findAll(em, AdTrigger.class).get(0);
        }
        adTriggerParam.setAdTrigger(adTrigger);
        return adTriggerParam;
    }

    @BeforeEach
    public void initTest() {
        adTriggerParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTriggerParam() throws Exception {
        int databaseSizeBeforeCreate = adTriggerParamRepository.findAll().size();

        // Create the AdTriggerParam
        AdTriggerParamDTO adTriggerParamDTO = adTriggerParamMapper.toDto(adTriggerParam);
        restAdTriggerParamMockMvc.perform(post("/api/ad-trigger-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerParamDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTriggerParam in the database
        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeCreate + 1);
        AdTriggerParam testAdTriggerParam = adTriggerParamList.get(adTriggerParamList.size() - 1);
        assertThat(testAdTriggerParam.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTriggerParam.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTriggerParam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTriggerParam.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testAdTriggerParam.getMandatoryLogic()).isEqualTo(DEFAULT_MANDATORY_LOGIC);
        assertThat(testAdTriggerParam.getDisplayLogic()).isEqualTo(DEFAULT_DISPLAY_LOGIC);
        assertThat(testAdTriggerParam.getReadOnlyLogic()).isEqualTo(DEFAULT_READ_ONLY_LOGIC);
        assertThat(testAdTriggerParam.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testAdTriggerParam.getFormatPattern()).isEqualTo(DEFAULT_FORMAT_PATTERN);
        assertThat(testAdTriggerParam.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testAdTriggerParam.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
        assertThat(testAdTriggerParam.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testAdTriggerParam.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
    }

    @Test
    @Transactional
    public void createAdTriggerParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTriggerParamRepository.findAll().size();

        // Create the AdTriggerParam with an existing ID
        adTriggerParam.setId(1L);
        AdTriggerParamDTO adTriggerParamDTO = adTriggerParamMapper.toDto(adTriggerParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTriggerParamMockMvc.perform(post("/api/ad-trigger-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTriggerParam in the database
        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTriggerParamRepository.findAll().size();
        // set the field null
        adTriggerParam.setName(null);

        // Create the AdTriggerParam, which fails.
        AdTriggerParamDTO adTriggerParamDTO = adTriggerParamMapper.toDto(adTriggerParam);

        restAdTriggerParamMockMvc.perform(post("/api/ad-trigger-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerParamDTO)))
            .andExpect(status().isBadRequest());

        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParams() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTriggerParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAdTriggerParam() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get the adTriggerParam
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params/{id}", adTriggerParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTriggerParam.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.mandatoryLogic").value(DEFAULT_MANDATORY_LOGIC))
            .andExpect(jsonPath("$.displayLogic").value(DEFAULT_DISPLAY_LOGIC))
            .andExpect(jsonPath("$.readOnlyLogic").value(DEFAULT_READ_ONLY_LOGIC))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.formatPattern").value(DEFAULT_FORMAT_PATTERN))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.intValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.intValue()));
    }


    @Test
    @Transactional
    public void getAdTriggerParamsByIdFiltering() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        Long id = adTriggerParam.getId();

        defaultAdTriggerParamShouldBeFound("id.equals=" + id);
        defaultAdTriggerParamShouldNotBeFound("id.notEquals=" + id);

        defaultAdTriggerParamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTriggerParamShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTriggerParamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTriggerParamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where uid equals to DEFAULT_UID
        defaultAdTriggerParamShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTriggerParamList where uid equals to UPDATED_UID
        defaultAdTriggerParamShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where uid not equals to DEFAULT_UID
        defaultAdTriggerParamShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTriggerParamList where uid not equals to UPDATED_UID
        defaultAdTriggerParamShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTriggerParamShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTriggerParamList where uid equals to UPDATED_UID
        defaultAdTriggerParamShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where uid is not null
        defaultAdTriggerParamShouldBeFound("uid.specified=true");

        // Get all the adTriggerParamList where uid is null
        defaultAdTriggerParamShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where active equals to DEFAULT_ACTIVE
        defaultAdTriggerParamShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTriggerParamList where active equals to UPDATED_ACTIVE
        defaultAdTriggerParamShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where active not equals to DEFAULT_ACTIVE
        defaultAdTriggerParamShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTriggerParamList where active not equals to UPDATED_ACTIVE
        defaultAdTriggerParamShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTriggerParamShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTriggerParamList where active equals to UPDATED_ACTIVE
        defaultAdTriggerParamShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where active is not null
        defaultAdTriggerParamShouldBeFound("active.specified=true");

        // Get all the adTriggerParamList where active is null
        defaultAdTriggerParamShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name equals to DEFAULT_NAME
        defaultAdTriggerParamShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTriggerParamList where name equals to UPDATED_NAME
        defaultAdTriggerParamShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name not equals to DEFAULT_NAME
        defaultAdTriggerParamShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTriggerParamList where name not equals to UPDATED_NAME
        defaultAdTriggerParamShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTriggerParamShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTriggerParamList where name equals to UPDATED_NAME
        defaultAdTriggerParamShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name is not null
        defaultAdTriggerParamShouldBeFound("name.specified=true");

        // Get all the adTriggerParamList where name is null
        defaultAdTriggerParamShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByNameContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name contains DEFAULT_NAME
        defaultAdTriggerParamShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTriggerParamList where name contains UPDATED_NAME
        defaultAdTriggerParamShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where name does not contain DEFAULT_NAME
        defaultAdTriggerParamShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTriggerParamList where name does not contain UPDATED_NAME
        defaultAdTriggerParamShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatory equals to DEFAULT_MANDATORY
        defaultAdTriggerParamShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the adTriggerParamList where mandatory equals to UPDATED_MANDATORY
        defaultAdTriggerParamShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatory not equals to DEFAULT_MANDATORY
        defaultAdTriggerParamShouldNotBeFound("mandatory.notEquals=" + DEFAULT_MANDATORY);

        // Get all the adTriggerParamList where mandatory not equals to UPDATED_MANDATORY
        defaultAdTriggerParamShouldBeFound("mandatory.notEquals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultAdTriggerParamShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the adTriggerParamList where mandatory equals to UPDATED_MANDATORY
        defaultAdTriggerParamShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatory is not null
        defaultAdTriggerParamShouldBeFound("mandatory.specified=true");

        // Get all the adTriggerParamList where mandatory is null
        defaultAdTriggerParamShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic equals to DEFAULT_MANDATORY_LOGIC
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.equals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the adTriggerParamList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.equals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic not equals to DEFAULT_MANDATORY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.notEquals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the adTriggerParamList where mandatoryLogic not equals to UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.notEquals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic in DEFAULT_MANDATORY_LOGIC or UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.in=" + DEFAULT_MANDATORY_LOGIC + "," + UPDATED_MANDATORY_LOGIC);

        // Get all the adTriggerParamList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.in=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic is not null
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.specified=true");

        // Get all the adTriggerParamList where mandatoryLogic is null
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic contains DEFAULT_MANDATORY_LOGIC
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.contains=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the adTriggerParamList where mandatoryLogic contains UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.contains=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMandatoryLogicNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where mandatoryLogic does not contain DEFAULT_MANDATORY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("mandatoryLogic.doesNotContain=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the adTriggerParamList where mandatoryLogic does not contain UPDATED_MANDATORY_LOGIC
        defaultAdTriggerParamShouldBeFound("mandatoryLogic.doesNotContain=" + UPDATED_MANDATORY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic equals to DEFAULT_DISPLAY_LOGIC
        defaultAdTriggerParamShouldBeFound("displayLogic.equals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the adTriggerParamList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("displayLogic.equals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic not equals to DEFAULT_DISPLAY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("displayLogic.notEquals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the adTriggerParamList where displayLogic not equals to UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldBeFound("displayLogic.notEquals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic in DEFAULT_DISPLAY_LOGIC or UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldBeFound("displayLogic.in=" + DEFAULT_DISPLAY_LOGIC + "," + UPDATED_DISPLAY_LOGIC);

        // Get all the adTriggerParamList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("displayLogic.in=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic is not null
        defaultAdTriggerParamShouldBeFound("displayLogic.specified=true");

        // Get all the adTriggerParamList where displayLogic is null
        defaultAdTriggerParamShouldNotBeFound("displayLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic contains DEFAULT_DISPLAY_LOGIC
        defaultAdTriggerParamShouldBeFound("displayLogic.contains=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the adTriggerParamList where displayLogic contains UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("displayLogic.contains=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDisplayLogicNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where displayLogic does not contain DEFAULT_DISPLAY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("displayLogic.doesNotContain=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the adTriggerParamList where displayLogic does not contain UPDATED_DISPLAY_LOGIC
        defaultAdTriggerParamShouldBeFound("displayLogic.doesNotContain=" + UPDATED_DISPLAY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic equals to DEFAULT_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.equals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the adTriggerParamList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.equals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic not equals to DEFAULT_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.notEquals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the adTriggerParamList where readOnlyLogic not equals to UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.notEquals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic in DEFAULT_READ_ONLY_LOGIC or UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.in=" + DEFAULT_READ_ONLY_LOGIC + "," + UPDATED_READ_ONLY_LOGIC);

        // Get all the adTriggerParamList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.in=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic is not null
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.specified=true");

        // Get all the adTriggerParamList where readOnlyLogic is null
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic contains DEFAULT_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.contains=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the adTriggerParamList where readOnlyLogic contains UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.contains=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByReadOnlyLogicNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where readOnlyLogic does not contain DEFAULT_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldNotBeFound("readOnlyLogic.doesNotContain=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the adTriggerParamList where readOnlyLogic does not contain UPDATED_READ_ONLY_LOGIC
        defaultAdTriggerParamShouldBeFound("readOnlyLogic.doesNotContain=" + UPDATED_READ_ONLY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultAdTriggerParamShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the adTriggerParamList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue not equals to DEFAULT_DEFAULT_VALUE
        defaultAdTriggerParamShouldNotBeFound("defaultValue.notEquals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the adTriggerParamList where defaultValue not equals to UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldBeFound("defaultValue.notEquals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the adTriggerParamList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue is not null
        defaultAdTriggerParamShouldBeFound("defaultValue.specified=true");

        // Get all the adTriggerParamList where defaultValue is null
        defaultAdTriggerParamShouldNotBeFound("defaultValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue contains DEFAULT_DEFAULT_VALUE
        defaultAdTriggerParamShouldBeFound("defaultValue.contains=" + DEFAULT_DEFAULT_VALUE);

        // Get all the adTriggerParamList where defaultValue contains UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldNotBeFound("defaultValue.contains=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByDefaultValueNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where defaultValue does not contain DEFAULT_DEFAULT_VALUE
        defaultAdTriggerParamShouldNotBeFound("defaultValue.doesNotContain=" + DEFAULT_DEFAULT_VALUE);

        // Get all the adTriggerParamList where defaultValue does not contain UPDATED_DEFAULT_VALUE
        defaultAdTriggerParamShouldBeFound("defaultValue.doesNotContain=" + UPDATED_DEFAULT_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern equals to DEFAULT_FORMAT_PATTERN
        defaultAdTriggerParamShouldBeFound("formatPattern.equals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the adTriggerParamList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldNotBeFound("formatPattern.equals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern not equals to DEFAULT_FORMAT_PATTERN
        defaultAdTriggerParamShouldNotBeFound("formatPattern.notEquals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the adTriggerParamList where formatPattern not equals to UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldBeFound("formatPattern.notEquals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern in DEFAULT_FORMAT_PATTERN or UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldBeFound("formatPattern.in=" + DEFAULT_FORMAT_PATTERN + "," + UPDATED_FORMAT_PATTERN);

        // Get all the adTriggerParamList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldNotBeFound("formatPattern.in=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern is not null
        defaultAdTriggerParamShouldBeFound("formatPattern.specified=true");

        // Get all the adTriggerParamList where formatPattern is null
        defaultAdTriggerParamShouldNotBeFound("formatPattern.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern contains DEFAULT_FORMAT_PATTERN
        defaultAdTriggerParamShouldBeFound("formatPattern.contains=" + DEFAULT_FORMAT_PATTERN);

        // Get all the adTriggerParamList where formatPattern contains UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldNotBeFound("formatPattern.contains=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByFormatPatternNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where formatPattern does not contain DEFAULT_FORMAT_PATTERN
        defaultAdTriggerParamShouldNotBeFound("formatPattern.doesNotContain=" + DEFAULT_FORMAT_PATTERN);

        // Get all the adTriggerParamList where formatPattern does not contain UPDATED_FORMAT_PATTERN
        defaultAdTriggerParamShouldBeFound("formatPattern.doesNotContain=" + UPDATED_FORMAT_PATTERN);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength equals to DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.equals=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength equals to UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.equals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength not equals to DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.notEquals=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength not equals to UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.notEquals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength in DEFAULT_MIN_LENGTH or UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.in=" + DEFAULT_MIN_LENGTH + "," + UPDATED_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength equals to UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.in=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength is not null
        defaultAdTriggerParamShouldBeFound("minLength.specified=true");

        // Get all the adTriggerParamList where minLength is null
        defaultAdTriggerParamShouldNotBeFound("minLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength is greater than or equal to DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.greaterThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength is greater than or equal to UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.greaterThanOrEqual=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength is less than or equal to DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.lessThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength is less than or equal to SMALLER_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.lessThanOrEqual=" + SMALLER_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength is less than DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.lessThan=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength is less than UPDATED_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.lessThan=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minLength is greater than DEFAULT_MIN_LENGTH
        defaultAdTriggerParamShouldNotBeFound("minLength.greaterThan=" + DEFAULT_MIN_LENGTH);

        // Get all the adTriggerParamList where minLength is greater than SMALLER_MIN_LENGTH
        defaultAdTriggerParamShouldBeFound("minLength.greaterThan=" + SMALLER_MIN_LENGTH);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength equals to DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.equals=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength equals to UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.equals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength not equals to DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.notEquals=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength not equals to UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.notEquals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength in DEFAULT_MAX_LENGTH or UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.in=" + DEFAULT_MAX_LENGTH + "," + UPDATED_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength equals to UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.in=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength is not null
        defaultAdTriggerParamShouldBeFound("maxLength.specified=true");

        // Get all the adTriggerParamList where maxLength is null
        defaultAdTriggerParamShouldNotBeFound("maxLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength is greater than or equal to DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.greaterThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength is greater than or equal to UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.greaterThanOrEqual=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength is less than or equal to DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.lessThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength is less than or equal to SMALLER_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.lessThanOrEqual=" + SMALLER_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength is less than DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.lessThan=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength is less than UPDATED_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.lessThan=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxLength is greater than DEFAULT_MAX_LENGTH
        defaultAdTriggerParamShouldNotBeFound("maxLength.greaterThan=" + DEFAULT_MAX_LENGTH);

        // Get all the adTriggerParamList where maxLength is greater than SMALLER_MAX_LENGTH
        defaultAdTriggerParamShouldBeFound("maxLength.greaterThan=" + SMALLER_MAX_LENGTH);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue equals to DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.equals=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue equals to UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.equals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue not equals to DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.notEquals=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue not equals to UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.notEquals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue in DEFAULT_MIN_VALUE or UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.in=" + DEFAULT_MIN_VALUE + "," + UPDATED_MIN_VALUE);

        // Get all the adTriggerParamList where minValue equals to UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.in=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue is not null
        defaultAdTriggerParamShouldBeFound("minValue.specified=true");

        // Get all the adTriggerParamList where minValue is null
        defaultAdTriggerParamShouldNotBeFound("minValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue is greater than or equal to DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.greaterThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue is greater than or equal to UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.greaterThanOrEqual=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue is less than or equal to DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.lessThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue is less than or equal to SMALLER_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.lessThanOrEqual=" + SMALLER_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsLessThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue is less than DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.lessThan=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue is less than UPDATED_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.lessThan=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMinValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where minValue is greater than DEFAULT_MIN_VALUE
        defaultAdTriggerParamShouldNotBeFound("minValue.greaterThan=" + DEFAULT_MIN_VALUE);

        // Get all the adTriggerParamList where minValue is greater than SMALLER_MIN_VALUE
        defaultAdTriggerParamShouldBeFound("minValue.greaterThan=" + SMALLER_MIN_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue equals to DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.equals=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue equals to UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.equals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue not equals to DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.notEquals=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue not equals to UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.notEquals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue in DEFAULT_MAX_VALUE or UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.in=" + DEFAULT_MAX_VALUE + "," + UPDATED_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue equals to UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.in=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue is not null
        defaultAdTriggerParamShouldBeFound("maxValue.specified=true");

        // Get all the adTriggerParamList where maxValue is null
        defaultAdTriggerParamShouldNotBeFound("maxValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue is greater than or equal to DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.greaterThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue is greater than or equal to UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.greaterThanOrEqual=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue is less than or equal to DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.lessThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue is less than or equal to SMALLER_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.lessThanOrEqual=" + SMALLER_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsLessThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue is less than DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.lessThan=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue is less than UPDATED_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.lessThan=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggerParamsByMaxValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        // Get all the adTriggerParamList where maxValue is greater than DEFAULT_MAX_VALUE
        defaultAdTriggerParamShouldNotBeFound("maxValue.greaterThan=" + DEFAULT_MAX_VALUE);

        // Get all the adTriggerParamList where maxValue is greater than SMALLER_MAX_VALUE
        defaultAdTriggerParamShouldBeFound("maxValue.greaterThan=" + SMALLER_MAX_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTriggerParam.getAdOrganization();
        adTriggerParamRepository.saveAndFlush(adTriggerParam);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTriggerParamList where adOrganization equals to adOrganizationId
        defaultAdTriggerParamShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTriggerParamList where adOrganization equals to adOrganizationId + 1
        defaultAdTriggerParamShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTriggerParamsByAdTriggerIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdTrigger adTrigger = adTriggerParam.getAdTrigger();
        adTriggerParamRepository.saveAndFlush(adTriggerParam);
        Long adTriggerId = adTrigger.getId();

        // Get all the adTriggerParamList where adTrigger equals to adTriggerId
        defaultAdTriggerParamShouldBeFound("adTriggerId.equals=" + adTriggerId);

        // Get all the adTriggerParamList where adTrigger equals to adTriggerId + 1
        defaultAdTriggerParamShouldNotBeFound("adTriggerId.equals=" + (adTriggerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTriggerParamShouldBeFound(String filter) throws Exception {
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTriggerParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())));

        // Check, that the count call also returns 1
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTriggerParamShouldNotBeFound(String filter) throws Exception {
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTriggerParam() throws Exception {
        // Get the adTriggerParam
        restAdTriggerParamMockMvc.perform(get("/api/ad-trigger-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTriggerParam() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        int databaseSizeBeforeUpdate = adTriggerParamRepository.findAll().size();

        // Update the adTriggerParam
        AdTriggerParam updatedAdTriggerParam = adTriggerParamRepository.findById(adTriggerParam.getId()).get();
        // Disconnect from session so that the updates on updatedAdTriggerParam are not directly saved in db
        em.detach(updatedAdTriggerParam);
        updatedAdTriggerParam
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE);
        AdTriggerParamDTO adTriggerParamDTO = adTriggerParamMapper.toDto(updatedAdTriggerParam);

        restAdTriggerParamMockMvc.perform(put("/api/ad-trigger-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerParamDTO)))
            .andExpect(status().isOk());

        // Validate the AdTriggerParam in the database
        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeUpdate);
        AdTriggerParam testAdTriggerParam = adTriggerParamList.get(adTriggerParamList.size() - 1);
        assertThat(testAdTriggerParam.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTriggerParam.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTriggerParam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTriggerParam.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testAdTriggerParam.getMandatoryLogic()).isEqualTo(UPDATED_MANDATORY_LOGIC);
        assertThat(testAdTriggerParam.getDisplayLogic()).isEqualTo(UPDATED_DISPLAY_LOGIC);
        assertThat(testAdTriggerParam.getReadOnlyLogic()).isEqualTo(UPDATED_READ_ONLY_LOGIC);
        assertThat(testAdTriggerParam.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testAdTriggerParam.getFormatPattern()).isEqualTo(UPDATED_FORMAT_PATTERN);
        assertThat(testAdTriggerParam.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testAdTriggerParam.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
        assertThat(testAdTriggerParam.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testAdTriggerParam.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTriggerParam() throws Exception {
        int databaseSizeBeforeUpdate = adTriggerParamRepository.findAll().size();

        // Create the AdTriggerParam
        AdTriggerParamDTO adTriggerParamDTO = adTriggerParamMapper.toDto(adTriggerParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTriggerParamMockMvc.perform(put("/api/ad-trigger-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTriggerParam in the database
        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTriggerParam() throws Exception {
        // Initialize the database
        adTriggerParamRepository.saveAndFlush(adTriggerParam);

        int databaseSizeBeforeDelete = adTriggerParamRepository.findAll().size();

        // Delete the adTriggerParam
        restAdTriggerParamMockMvc.perform(delete("/api/ad-trigger-params/{id}", adTriggerParam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTriggerParam> adTriggerParamList = adTriggerParamRepository.findAll();
        assertThat(adTriggerParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
