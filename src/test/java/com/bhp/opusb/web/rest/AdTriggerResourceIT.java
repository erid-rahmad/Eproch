package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.domain.AdTriggerParam;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdTriggerRepository;
import com.bhp.opusb.service.AdTriggerService;
import com.bhp.opusb.service.dto.AdTriggerDTO;
import com.bhp.opusb.service.mapper.AdTriggerMapper;
import com.bhp.opusb.service.dto.AdTriggerCriteria;
import com.bhp.opusb.service.AdTriggerQueryService;

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

import com.bhp.opusb.domain.enumeration.AdTriggerType;
/**
 * Integration tests for the {@link AdTriggerResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdTriggerResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AdTriggerType DEFAULT_TYPE = AdTriggerType.PROCESS;
    private static final AdTriggerType UPDATED_TYPE = AdTriggerType.REPORT;

    @Autowired
    private AdTriggerRepository adTriggerRepository;

    @Autowired
    private AdTriggerMapper adTriggerMapper;

    @Autowired
    private AdTriggerService adTriggerService;

    @Autowired
    private AdTriggerQueryService adTriggerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdTriggerMockMvc;

    private AdTrigger adTrigger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTrigger createEntity(EntityManager em) {
        AdTrigger adTrigger = new AdTrigger()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTrigger.setAdOrganization(aDOrganization);
        return adTrigger;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdTrigger createUpdatedEntity(EntityManager em) {
        AdTrigger adTrigger = new AdTrigger()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adTrigger.setAdOrganization(aDOrganization);
        return adTrigger;
    }

    @BeforeEach
    public void initTest() {
        adTrigger = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdTrigger() throws Exception {
        int databaseSizeBeforeCreate = adTriggerRepository.findAll().size();

        // Create the AdTrigger
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(adTrigger);
        restAdTriggerMockMvc.perform(post("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isCreated());

        // Validate the AdTrigger in the database
        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeCreate + 1);
        AdTrigger testAdTrigger = adTriggerList.get(adTriggerList.size() - 1);
        assertThat(testAdTrigger.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdTrigger.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdTrigger.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdTrigger.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdTrigger.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdTrigger.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createAdTriggerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adTriggerRepository.findAll().size();

        // Create the AdTrigger with an existing ID
        adTrigger.setId(1L);
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(adTrigger);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdTriggerMockMvc.perform(post("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTrigger in the database
        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTriggerRepository.findAll().size();
        // set the field null
        adTrigger.setName(null);

        // Create the AdTrigger, which fails.
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(adTrigger);

        restAdTriggerMockMvc.perform(post("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isBadRequest());

        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adTriggerRepository.findAll().size();
        // set the field null
        adTrigger.setValue(null);

        // Create the AdTrigger, which fails.
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(adTrigger);

        restAdTriggerMockMvc.perform(post("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isBadRequest());

        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdTriggers() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList
        restAdTriggerMockMvc.perform(get("/api/ad-triggers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTrigger.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAdTrigger() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get the adTrigger
        restAdTriggerMockMvc.perform(get("/api/ad-triggers/{id}", adTrigger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adTrigger.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getAdTriggersByIdFiltering() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        Long id = adTrigger.getId();

        defaultAdTriggerShouldBeFound("id.equals=" + id);
        defaultAdTriggerShouldNotBeFound("id.notEquals=" + id);

        defaultAdTriggerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdTriggerShouldNotBeFound("id.greaterThan=" + id);

        defaultAdTriggerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdTriggerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdTriggersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where uid equals to DEFAULT_UID
        defaultAdTriggerShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adTriggerList where uid equals to UPDATED_UID
        defaultAdTriggerShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where uid not equals to DEFAULT_UID
        defaultAdTriggerShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adTriggerList where uid not equals to UPDATED_UID
        defaultAdTriggerShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdTriggerShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adTriggerList where uid equals to UPDATED_UID
        defaultAdTriggerShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where uid is not null
        defaultAdTriggerShouldBeFound("uid.specified=true");

        // Get all the adTriggerList where uid is null
        defaultAdTriggerShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where active equals to DEFAULT_ACTIVE
        defaultAdTriggerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adTriggerList where active equals to UPDATED_ACTIVE
        defaultAdTriggerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where active not equals to DEFAULT_ACTIVE
        defaultAdTriggerShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adTriggerList where active not equals to UPDATED_ACTIVE
        defaultAdTriggerShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdTriggerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adTriggerList where active equals to UPDATED_ACTIVE
        defaultAdTriggerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where active is not null
        defaultAdTriggerShouldBeFound("active.specified=true");

        // Get all the adTriggerList where active is null
        defaultAdTriggerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name equals to DEFAULT_NAME
        defaultAdTriggerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adTriggerList where name equals to UPDATED_NAME
        defaultAdTriggerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name not equals to DEFAULT_NAME
        defaultAdTriggerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adTriggerList where name not equals to UPDATED_NAME
        defaultAdTriggerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdTriggerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adTriggerList where name equals to UPDATED_NAME
        defaultAdTriggerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name is not null
        defaultAdTriggerShouldBeFound("name.specified=true");

        // Get all the adTriggerList where name is null
        defaultAdTriggerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggersByNameContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name contains DEFAULT_NAME
        defaultAdTriggerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adTriggerList where name contains UPDATED_NAME
        defaultAdTriggerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where name does not contain DEFAULT_NAME
        defaultAdTriggerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adTriggerList where name does not contain UPDATED_NAME
        defaultAdTriggerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdTriggersByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value equals to DEFAULT_VALUE
        defaultAdTriggerShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the adTriggerList where value equals to UPDATED_VALUE
        defaultAdTriggerShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value not equals to DEFAULT_VALUE
        defaultAdTriggerShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the adTriggerList where value not equals to UPDATED_VALUE
        defaultAdTriggerShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByValueIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAdTriggerShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the adTriggerList where value equals to UPDATED_VALUE
        defaultAdTriggerShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value is not null
        defaultAdTriggerShouldBeFound("value.specified=true");

        // Get all the adTriggerList where value is null
        defaultAdTriggerShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggersByValueContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value contains DEFAULT_VALUE
        defaultAdTriggerShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the adTriggerList where value contains UPDATED_VALUE
        defaultAdTriggerShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByValueNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where value does not contain DEFAULT_VALUE
        defaultAdTriggerShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the adTriggerList where value does not contain UPDATED_VALUE
        defaultAdTriggerShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdTriggersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description equals to DEFAULT_DESCRIPTION
        defaultAdTriggerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adTriggerList where description equals to UPDATED_DESCRIPTION
        defaultAdTriggerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description not equals to DEFAULT_DESCRIPTION
        defaultAdTriggerShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adTriggerList where description not equals to UPDATED_DESCRIPTION
        defaultAdTriggerShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdTriggerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adTriggerList where description equals to UPDATED_DESCRIPTION
        defaultAdTriggerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description is not null
        defaultAdTriggerShouldBeFound("description.specified=true");

        // Get all the adTriggerList where description is null
        defaultAdTriggerShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdTriggersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description contains DEFAULT_DESCRIPTION
        defaultAdTriggerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adTriggerList where description contains UPDATED_DESCRIPTION
        defaultAdTriggerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where description does not contain DEFAULT_DESCRIPTION
        defaultAdTriggerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adTriggerList where description does not contain UPDATED_DESCRIPTION
        defaultAdTriggerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdTriggersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where type equals to DEFAULT_TYPE
        defaultAdTriggerShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the adTriggerList where type equals to UPDATED_TYPE
        defaultAdTriggerShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where type not equals to DEFAULT_TYPE
        defaultAdTriggerShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the adTriggerList where type not equals to UPDATED_TYPE
        defaultAdTriggerShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAdTriggerShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the adTriggerList where type equals to UPDATED_TYPE
        defaultAdTriggerShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdTriggersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        // Get all the adTriggerList where type is not null
        defaultAdTriggerShouldBeFound("type.specified=true");

        // Get all the adTriggerList where type is null
        defaultAdTriggerShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdTriggersByAdTriggerParamIsEqualToSomething() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);
        AdTriggerParam adTriggerParam = AdTriggerParamResourceIT.createEntity(em);
        em.persist(adTriggerParam);
        em.flush();
        adTrigger.addAdTriggerParam(adTriggerParam);
        adTriggerRepository.saveAndFlush(adTrigger);
        Long adTriggerParamId = adTriggerParam.getId();

        // Get all the adTriggerList where adTriggerParam equals to adTriggerParamId
        defaultAdTriggerShouldBeFound("adTriggerParamId.equals=" + adTriggerParamId);

        // Get all the adTriggerList where adTriggerParam equals to adTriggerParamId + 1
        defaultAdTriggerShouldNotBeFound("adTriggerParamId.equals=" + (adTriggerParamId + 1));
    }


    @Test
    @Transactional
    public void getAllAdTriggersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adTrigger.getAdOrganization();
        adTriggerRepository.saveAndFlush(adTrigger);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adTriggerList where adOrganization equals to adOrganizationId
        defaultAdTriggerShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adTriggerList where adOrganization equals to adOrganizationId + 1
        defaultAdTriggerShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdTriggerShouldBeFound(String filter) throws Exception {
        restAdTriggerMockMvc.perform(get("/api/ad-triggers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adTrigger.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restAdTriggerMockMvc.perform(get("/api/ad-triggers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdTriggerShouldNotBeFound(String filter) throws Exception {
        restAdTriggerMockMvc.perform(get("/api/ad-triggers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdTriggerMockMvc.perform(get("/api/ad-triggers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdTrigger() throws Exception {
        // Get the adTrigger
        restAdTriggerMockMvc.perform(get("/api/ad-triggers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdTrigger() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        int databaseSizeBeforeUpdate = adTriggerRepository.findAll().size();

        // Update the adTrigger
        AdTrigger updatedAdTrigger = adTriggerRepository.findById(adTrigger.getId()).get();
        // Disconnect from session so that the updates on updatedAdTrigger are not directly saved in db
        em.detach(updatedAdTrigger);
        updatedAdTrigger
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(updatedAdTrigger);

        restAdTriggerMockMvc.perform(put("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isOk());

        // Validate the AdTrigger in the database
        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeUpdate);
        AdTrigger testAdTrigger = adTriggerList.get(adTriggerList.size() - 1);
        assertThat(testAdTrigger.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdTrigger.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdTrigger.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdTrigger.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdTrigger.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdTrigger.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdTrigger() throws Exception {
        int databaseSizeBeforeUpdate = adTriggerRepository.findAll().size();

        // Create the AdTrigger
        AdTriggerDTO adTriggerDTO = adTriggerMapper.toDto(adTrigger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdTriggerMockMvc.perform(put("/api/ad-triggers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adTriggerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdTrigger in the database
        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdTrigger() throws Exception {
        // Initialize the database
        adTriggerRepository.saveAndFlush(adTrigger);

        int databaseSizeBeforeDelete = adTriggerRepository.findAll().size();

        // Delete the adTrigger
        restAdTriggerMockMvc.perform(delete("/api/ad-triggers/{id}", adTrigger.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdTrigger> adTriggerList = adTriggerRepository.findAll();
        assertThat(adTriggerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
