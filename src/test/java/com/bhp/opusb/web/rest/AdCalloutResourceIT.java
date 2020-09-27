package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdCallout;
import com.bhp.opusb.domain.AdCalloutTarget;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.repository.AdCalloutRepository;
import com.bhp.opusb.service.AdCalloutService;
import com.bhp.opusb.service.dto.AdCalloutDTO;
import com.bhp.opusb.service.mapper.AdCalloutMapper;
import com.bhp.opusb.service.dto.AdCalloutCriteria;
import com.bhp.opusb.service.AdCalloutQueryService;

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
 * Integration tests for the {@link AdCalloutResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdCalloutResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private AdCalloutRepository adCalloutRepository;

    @Autowired
    private AdCalloutMapper adCalloutMapper;

    @Autowired
    private AdCalloutService adCalloutService;

    @Autowired
    private AdCalloutQueryService adCalloutQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdCalloutMockMvc;

    private AdCallout adCallout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdCallout createEntity(EntityManager em) {
        AdCallout adCallout = new AdCallout()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
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
        adCallout.setAdOrganization(aDOrganization);
        // Add required entity
        ADField aDField;
        if (TestUtil.findAll(em, ADField.class).isEmpty()) {
            aDField = ADFieldResourceIT.createEntity(em);
            em.persist(aDField);
            em.flush();
        } else {
            aDField = TestUtil.findAll(em, ADField.class).get(0);
        }
        adCallout.setField(aDField);
        return adCallout;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdCallout createUpdatedEntity(EntityManager em) {
        AdCallout adCallout = new AdCallout()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
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
        adCallout.setAdOrganization(aDOrganization);
        // Add required entity
        ADField aDField;
        if (TestUtil.findAll(em, ADField.class).isEmpty()) {
            aDField = ADFieldResourceIT.createUpdatedEntity(em);
            em.persist(aDField);
            em.flush();
        } else {
            aDField = TestUtil.findAll(em, ADField.class).get(0);
        }
        adCallout.setField(aDField);
        return adCallout;
    }

    @BeforeEach
    public void initTest() {
        adCallout = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdCallout() throws Exception {
        int databaseSizeBeforeCreate = adCalloutRepository.findAll().size();

        // Create the AdCallout
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(adCallout);
        restAdCalloutMockMvc.perform(post("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isCreated());

        // Validate the AdCallout in the database
        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeCreate + 1);
        AdCallout testAdCallout = adCalloutList.get(adCalloutList.size() - 1);
        assertThat(testAdCallout.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdCallout.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdCallout.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdCallout.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdCallout.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createAdCalloutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adCalloutRepository.findAll().size();

        // Create the AdCallout with an existing ID
        adCallout.setId(1L);
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(adCallout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdCalloutMockMvc.perform(post("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdCallout in the database
        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCalloutRepository.findAll().size();
        // set the field null
        adCallout.setName(null);

        // Create the AdCallout, which fails.
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(adCallout);

        restAdCalloutMockMvc.perform(post("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isBadRequest());

        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCalloutRepository.findAll().size();
        // set the field null
        adCallout.setType(null);

        // Create the AdCallout, which fails.
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(adCallout);

        restAdCalloutMockMvc.perform(post("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isBadRequest());

        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdCallouts() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList
        restAdCalloutMockMvc.perform(get("/api/ad-callouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCallout.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getAdCallout() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get the adCallout
        restAdCalloutMockMvc.perform(get("/api/ad-callouts/{id}", adCallout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adCallout.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getAdCalloutsByIdFiltering() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        Long id = adCallout.getId();

        defaultAdCalloutShouldBeFound("id.equals=" + id);
        defaultAdCalloutShouldNotBeFound("id.notEquals=" + id);

        defaultAdCalloutShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdCalloutShouldNotBeFound("id.greaterThan=" + id);

        defaultAdCalloutShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdCalloutShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where uid equals to DEFAULT_UID
        defaultAdCalloutShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adCalloutList where uid equals to UPDATED_UID
        defaultAdCalloutShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where uid not equals to DEFAULT_UID
        defaultAdCalloutShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adCalloutList where uid not equals to UPDATED_UID
        defaultAdCalloutShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdCalloutShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adCalloutList where uid equals to UPDATED_UID
        defaultAdCalloutShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where uid is not null
        defaultAdCalloutShouldBeFound("uid.specified=true");

        // Get all the adCalloutList where uid is null
        defaultAdCalloutShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where active equals to DEFAULT_ACTIVE
        defaultAdCalloutShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adCalloutList where active equals to UPDATED_ACTIVE
        defaultAdCalloutShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where active not equals to DEFAULT_ACTIVE
        defaultAdCalloutShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adCalloutList where active not equals to UPDATED_ACTIVE
        defaultAdCalloutShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdCalloutShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adCalloutList where active equals to UPDATED_ACTIVE
        defaultAdCalloutShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where active is not null
        defaultAdCalloutShouldBeFound("active.specified=true");

        // Get all the adCalloutList where active is null
        defaultAdCalloutShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name equals to DEFAULT_NAME
        defaultAdCalloutShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adCalloutList where name equals to UPDATED_NAME
        defaultAdCalloutShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name not equals to DEFAULT_NAME
        defaultAdCalloutShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adCalloutList where name not equals to UPDATED_NAME
        defaultAdCalloutShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdCalloutShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adCalloutList where name equals to UPDATED_NAME
        defaultAdCalloutShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name is not null
        defaultAdCalloutShouldBeFound("name.specified=true");

        // Get all the adCalloutList where name is null
        defaultAdCalloutShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdCalloutsByNameContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name contains DEFAULT_NAME
        defaultAdCalloutShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adCalloutList where name contains UPDATED_NAME
        defaultAdCalloutShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where name does not contain DEFAULT_NAME
        defaultAdCalloutShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adCalloutList where name does not contain UPDATED_NAME
        defaultAdCalloutShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description equals to DEFAULT_DESCRIPTION
        defaultAdCalloutShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adCalloutList where description equals to UPDATED_DESCRIPTION
        defaultAdCalloutShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description not equals to DEFAULT_DESCRIPTION
        defaultAdCalloutShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adCalloutList where description not equals to UPDATED_DESCRIPTION
        defaultAdCalloutShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdCalloutShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adCalloutList where description equals to UPDATED_DESCRIPTION
        defaultAdCalloutShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description is not null
        defaultAdCalloutShouldBeFound("description.specified=true");

        // Get all the adCalloutList where description is null
        defaultAdCalloutShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description contains DEFAULT_DESCRIPTION
        defaultAdCalloutShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adCalloutList where description contains UPDATED_DESCRIPTION
        defaultAdCalloutShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where description does not contain DEFAULT_DESCRIPTION
        defaultAdCalloutShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adCalloutList where description does not contain UPDATED_DESCRIPTION
        defaultAdCalloutShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type equals to DEFAULT_TYPE
        defaultAdCalloutShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the adCalloutList where type equals to UPDATED_TYPE
        defaultAdCalloutShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type not equals to DEFAULT_TYPE
        defaultAdCalloutShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the adCalloutList where type not equals to UPDATED_TYPE
        defaultAdCalloutShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAdCalloutShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the adCalloutList where type equals to UPDATED_TYPE
        defaultAdCalloutShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type is not null
        defaultAdCalloutShouldBeFound("type.specified=true");

        // Get all the adCalloutList where type is null
        defaultAdCalloutShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdCalloutsByTypeContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type contains DEFAULT_TYPE
        defaultAdCalloutShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the adCalloutList where type contains UPDATED_TYPE
        defaultAdCalloutShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        // Get all the adCalloutList where type does not contain DEFAULT_TYPE
        defaultAdCalloutShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the adCalloutList where type does not contain UPDATED_TYPE
        defaultAdCalloutShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByAdCalloutTargetIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);
        AdCalloutTarget adCalloutTarget = AdCalloutTargetResourceIT.createEntity(em);
        em.persist(adCalloutTarget);
        em.flush();
        adCallout.addAdCalloutTarget(adCalloutTarget);
        adCalloutRepository.saveAndFlush(adCallout);
        Long adCalloutTargetId = adCalloutTarget.getId();

        // Get all the adCalloutList where adCalloutTarget equals to adCalloutTargetId
        defaultAdCalloutShouldBeFound("adCalloutTargetId.equals=" + adCalloutTargetId);

        // Get all the adCalloutList where adCalloutTarget equals to adCalloutTargetId + 1
        defaultAdCalloutShouldNotBeFound("adCalloutTargetId.equals=" + (adCalloutTargetId + 1));
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adCallout.getAdOrganization();
        adCalloutRepository.saveAndFlush(adCallout);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adCalloutList where adOrganization equals to adOrganizationId
        defaultAdCalloutShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adCalloutList where adOrganization equals to adOrganizationId + 1
        defaultAdCalloutShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByTriggerIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);
        AdTrigger trigger = AdTriggerResourceIT.createEntity(em);
        em.persist(trigger);
        em.flush();
        adCallout.setTrigger(trigger);
        adCalloutRepository.saveAndFlush(adCallout);
        Long triggerId = trigger.getId();

        // Get all the adCalloutList where trigger equals to triggerId
        defaultAdCalloutShouldBeFound("triggerId.equals=" + triggerId);

        // Get all the adCalloutList where trigger equals to triggerId + 1
        defaultAdCalloutShouldNotBeFound("triggerId.equals=" + (triggerId + 1));
    }


    @Test
    @Transactional
    public void getAllAdCalloutsByFieldIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADField field = adCallout.getField();
        adCalloutRepository.saveAndFlush(adCallout);
        Long fieldId = field.getId();

        // Get all the adCalloutList where field equals to fieldId
        defaultAdCalloutShouldBeFound("fieldId.equals=" + fieldId);

        // Get all the adCalloutList where field equals to fieldId + 1
        defaultAdCalloutShouldNotBeFound("fieldId.equals=" + (fieldId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdCalloutShouldBeFound(String filter) throws Exception {
        restAdCalloutMockMvc.perform(get("/api/ad-callouts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCallout.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restAdCalloutMockMvc.perform(get("/api/ad-callouts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdCalloutShouldNotBeFound(String filter) throws Exception {
        restAdCalloutMockMvc.perform(get("/api/ad-callouts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdCalloutMockMvc.perform(get("/api/ad-callouts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdCallout() throws Exception {
        // Get the adCallout
        restAdCalloutMockMvc.perform(get("/api/ad-callouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdCallout() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        int databaseSizeBeforeUpdate = adCalloutRepository.findAll().size();

        // Update the adCallout
        AdCallout updatedAdCallout = adCalloutRepository.findById(adCallout.getId()).get();
        // Disconnect from session so that the updates on updatedAdCallout are not directly saved in db
        em.detach(updatedAdCallout);
        updatedAdCallout
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(updatedAdCallout);

        restAdCalloutMockMvc.perform(put("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isOk());

        // Validate the AdCallout in the database
        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeUpdate);
        AdCallout testAdCallout = adCalloutList.get(adCalloutList.size() - 1);
        assertThat(testAdCallout.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdCallout.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdCallout.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdCallout.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdCallout.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdCallout() throws Exception {
        int databaseSizeBeforeUpdate = adCalloutRepository.findAll().size();

        // Create the AdCallout
        AdCalloutDTO adCalloutDTO = adCalloutMapper.toDto(adCallout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdCalloutMockMvc.perform(put("/api/ad-callouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdCallout in the database
        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdCallout() throws Exception {
        // Initialize the database
        adCalloutRepository.saveAndFlush(adCallout);

        int databaseSizeBeforeDelete = adCalloutRepository.findAll().size();

        // Delete the adCallout
        restAdCalloutMockMvc.perform(delete("/api/ad-callouts/{id}", adCallout.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdCallout> adCalloutList = adCalloutRepository.findAll();
        assertThat(adCalloutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
