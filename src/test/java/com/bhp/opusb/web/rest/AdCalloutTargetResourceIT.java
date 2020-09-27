package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdCalloutTarget;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdCallout;
import com.bhp.opusb.repository.AdCalloutTargetRepository;
import com.bhp.opusb.service.AdCalloutTargetService;
import com.bhp.opusb.service.dto.AdCalloutTargetDTO;
import com.bhp.opusb.service.mapper.AdCalloutTargetMapper;
import com.bhp.opusb.service.dto.AdCalloutTargetCriteria;
import com.bhp.opusb.service.AdCalloutTargetQueryService;

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
 * Integration tests for the {@link AdCalloutTargetResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdCalloutTargetResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_SOURCE_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_NAME = "BBBBBBBBBB";

    @Autowired
    private AdCalloutTargetRepository adCalloutTargetRepository;

    @Autowired
    private AdCalloutTargetMapper adCalloutTargetMapper;

    @Autowired
    private AdCalloutTargetService adCalloutTargetService;

    @Autowired
    private AdCalloutTargetQueryService adCalloutTargetQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdCalloutTargetMockMvc;

    private AdCalloutTarget adCalloutTarget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdCalloutTarget createEntity(EntityManager em) {
        AdCalloutTarget adCalloutTarget = new AdCalloutTarget()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .sourceField(DEFAULT_SOURCE_FIELD)
            .targetName(DEFAULT_TARGET_NAME);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adCalloutTarget.setAdOrganization(aDOrganization);
        // Add required entity
        AdCallout adCallout;
        if (TestUtil.findAll(em, AdCallout.class).isEmpty()) {
            adCallout = AdCalloutResourceIT.createEntity(em);
            em.persist(adCallout);
            em.flush();
        } else {
            adCallout = TestUtil.findAll(em, AdCallout.class).get(0);
        }
        adCalloutTarget.setCallout(adCallout);
        return adCalloutTarget;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdCalloutTarget createUpdatedEntity(EntityManager em) {
        AdCalloutTarget adCalloutTarget = new AdCalloutTarget()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .sourceField(UPDATED_SOURCE_FIELD)
            .targetName(UPDATED_TARGET_NAME);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adCalloutTarget.setAdOrganization(aDOrganization);
        // Add required entity
        AdCallout adCallout;
        if (TestUtil.findAll(em, AdCallout.class).isEmpty()) {
            adCallout = AdCalloutResourceIT.createUpdatedEntity(em);
            em.persist(adCallout);
            em.flush();
        } else {
            adCallout = TestUtil.findAll(em, AdCallout.class).get(0);
        }
        adCalloutTarget.setCallout(adCallout);
        return adCalloutTarget;
    }

    @BeforeEach
    public void initTest() {
        adCalloutTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdCalloutTarget() throws Exception {
        int databaseSizeBeforeCreate = adCalloutTargetRepository.findAll().size();

        // Create the AdCalloutTarget
        AdCalloutTargetDTO adCalloutTargetDTO = adCalloutTargetMapper.toDto(adCalloutTarget);
        restAdCalloutTargetMockMvc.perform(post("/api/ad-callout-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the AdCalloutTarget in the database
        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeCreate + 1);
        AdCalloutTarget testAdCalloutTarget = adCalloutTargetList.get(adCalloutTargetList.size() - 1);
        assertThat(testAdCalloutTarget.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdCalloutTarget.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdCalloutTarget.getSourceField()).isEqualTo(DEFAULT_SOURCE_FIELD);
        assertThat(testAdCalloutTarget.getTargetName()).isEqualTo(DEFAULT_TARGET_NAME);
    }

    @Test
    @Transactional
    public void createAdCalloutTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adCalloutTargetRepository.findAll().size();

        // Create the AdCalloutTarget with an existing ID
        adCalloutTarget.setId(1L);
        AdCalloutTargetDTO adCalloutTargetDTO = adCalloutTargetMapper.toDto(adCalloutTarget);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdCalloutTargetMockMvc.perform(post("/api/ad-callout-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdCalloutTarget in the database
        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTargetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCalloutTargetRepository.findAll().size();
        // set the field null
        adCalloutTarget.setTargetName(null);

        // Create the AdCalloutTarget, which fails.
        AdCalloutTargetDTO adCalloutTargetDTO = adCalloutTargetMapper.toDto(adCalloutTarget);

        restAdCalloutTargetMockMvc.perform(post("/api/ad-callout-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutTargetDTO)))
            .andExpect(status().isBadRequest());

        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargets() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCalloutTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sourceField").value(hasItem(DEFAULT_SOURCE_FIELD)))
            .andExpect(jsonPath("$.[*].targetName").value(hasItem(DEFAULT_TARGET_NAME)));
    }
    
    @Test
    @Transactional
    public void getAdCalloutTarget() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get the adCalloutTarget
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets/{id}", adCalloutTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adCalloutTarget.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.sourceField").value(DEFAULT_SOURCE_FIELD))
            .andExpect(jsonPath("$.targetName").value(DEFAULT_TARGET_NAME));
    }


    @Test
    @Transactional
    public void getAdCalloutTargetsByIdFiltering() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        Long id = adCalloutTarget.getId();

        defaultAdCalloutTargetShouldBeFound("id.equals=" + id);
        defaultAdCalloutTargetShouldNotBeFound("id.notEquals=" + id);

        defaultAdCalloutTargetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdCalloutTargetShouldNotBeFound("id.greaterThan=" + id);

        defaultAdCalloutTargetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdCalloutTargetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdCalloutTargetsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where uid equals to DEFAULT_UID
        defaultAdCalloutTargetShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adCalloutTargetList where uid equals to UPDATED_UID
        defaultAdCalloutTargetShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where uid not equals to DEFAULT_UID
        defaultAdCalloutTargetShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adCalloutTargetList where uid not equals to UPDATED_UID
        defaultAdCalloutTargetShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdCalloutTargetShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adCalloutTargetList where uid equals to UPDATED_UID
        defaultAdCalloutTargetShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where uid is not null
        defaultAdCalloutTargetShouldBeFound("uid.specified=true");

        // Get all the adCalloutTargetList where uid is null
        defaultAdCalloutTargetShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where active equals to DEFAULT_ACTIVE
        defaultAdCalloutTargetShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adCalloutTargetList where active equals to UPDATED_ACTIVE
        defaultAdCalloutTargetShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where active not equals to DEFAULT_ACTIVE
        defaultAdCalloutTargetShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adCalloutTargetList where active not equals to UPDATED_ACTIVE
        defaultAdCalloutTargetShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdCalloutTargetShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adCalloutTargetList where active equals to UPDATED_ACTIVE
        defaultAdCalloutTargetShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where active is not null
        defaultAdCalloutTargetShouldBeFound("active.specified=true");

        // Get all the adCalloutTargetList where active is null
        defaultAdCalloutTargetShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField equals to DEFAULT_SOURCE_FIELD
        defaultAdCalloutTargetShouldBeFound("sourceField.equals=" + DEFAULT_SOURCE_FIELD);

        // Get all the adCalloutTargetList where sourceField equals to UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldNotBeFound("sourceField.equals=" + UPDATED_SOURCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField not equals to DEFAULT_SOURCE_FIELD
        defaultAdCalloutTargetShouldNotBeFound("sourceField.notEquals=" + DEFAULT_SOURCE_FIELD);

        // Get all the adCalloutTargetList where sourceField not equals to UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldBeFound("sourceField.notEquals=" + UPDATED_SOURCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField in DEFAULT_SOURCE_FIELD or UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldBeFound("sourceField.in=" + DEFAULT_SOURCE_FIELD + "," + UPDATED_SOURCE_FIELD);

        // Get all the adCalloutTargetList where sourceField equals to UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldNotBeFound("sourceField.in=" + UPDATED_SOURCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField is not null
        defaultAdCalloutTargetShouldBeFound("sourceField.specified=true");

        // Get all the adCalloutTargetList where sourceField is null
        defaultAdCalloutTargetShouldNotBeFound("sourceField.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldContainsSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField contains DEFAULT_SOURCE_FIELD
        defaultAdCalloutTargetShouldBeFound("sourceField.contains=" + DEFAULT_SOURCE_FIELD);

        // Get all the adCalloutTargetList where sourceField contains UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldNotBeFound("sourceField.contains=" + UPDATED_SOURCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsBySourceFieldNotContainsSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where sourceField does not contain DEFAULT_SOURCE_FIELD
        defaultAdCalloutTargetShouldNotBeFound("sourceField.doesNotContain=" + DEFAULT_SOURCE_FIELD);

        // Get all the adCalloutTargetList where sourceField does not contain UPDATED_SOURCE_FIELD
        defaultAdCalloutTargetShouldBeFound("sourceField.doesNotContain=" + UPDATED_SOURCE_FIELD);
    }


    @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName equals to DEFAULT_TARGET_NAME
        defaultAdCalloutTargetShouldBeFound("targetName.equals=" + DEFAULT_TARGET_NAME);

        // Get all the adCalloutTargetList where targetName equals to UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldNotBeFound("targetName.equals=" + UPDATED_TARGET_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName not equals to DEFAULT_TARGET_NAME
        defaultAdCalloutTargetShouldNotBeFound("targetName.notEquals=" + DEFAULT_TARGET_NAME);

        // Get all the adCalloutTargetList where targetName not equals to UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldBeFound("targetName.notEquals=" + UPDATED_TARGET_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameIsInShouldWork() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName in DEFAULT_TARGET_NAME or UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldBeFound("targetName.in=" + DEFAULT_TARGET_NAME + "," + UPDATED_TARGET_NAME);

        // Get all the adCalloutTargetList where targetName equals to UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldNotBeFound("targetName.in=" + UPDATED_TARGET_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName is not null
        defaultAdCalloutTargetShouldBeFound("targetName.specified=true");

        // Get all the adCalloutTargetList where targetName is null
        defaultAdCalloutTargetShouldNotBeFound("targetName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameContainsSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName contains DEFAULT_TARGET_NAME
        defaultAdCalloutTargetShouldBeFound("targetName.contains=" + DEFAULT_TARGET_NAME);

        // Get all the adCalloutTargetList where targetName contains UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldNotBeFound("targetName.contains=" + UPDATED_TARGET_NAME);
    }

    @Test
    @Transactional
    public void getAllAdCalloutTargetsByTargetNameNotContainsSomething() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        // Get all the adCalloutTargetList where targetName does not contain DEFAULT_TARGET_NAME
        defaultAdCalloutTargetShouldNotBeFound("targetName.doesNotContain=" + DEFAULT_TARGET_NAME);

        // Get all the adCalloutTargetList where targetName does not contain UPDATED_TARGET_NAME
        defaultAdCalloutTargetShouldBeFound("targetName.doesNotContain=" + UPDATED_TARGET_NAME);
    }


    @Test
    @Transactional
    public void getAllAdCalloutTargetsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adCalloutTarget.getAdOrganization();
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adCalloutTargetList where adOrganization equals to adOrganizationId
        defaultAdCalloutTargetShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adCalloutTargetList where adOrganization equals to adOrganizationId + 1
        defaultAdCalloutTargetShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdCalloutTargetsByCalloutIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdCallout callout = adCalloutTarget.getCallout();
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);
        Long calloutId = callout.getId();

        // Get all the adCalloutTargetList where callout equals to calloutId
        defaultAdCalloutTargetShouldBeFound("calloutId.equals=" + calloutId);

        // Get all the adCalloutTargetList where callout equals to calloutId + 1
        defaultAdCalloutTargetShouldNotBeFound("calloutId.equals=" + (calloutId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdCalloutTargetShouldBeFound(String filter) throws Exception {
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCalloutTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sourceField").value(hasItem(DEFAULT_SOURCE_FIELD)))
            .andExpect(jsonPath("$.[*].targetName").value(hasItem(DEFAULT_TARGET_NAME)));

        // Check, that the count call also returns 1
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdCalloutTargetShouldNotBeFound(String filter) throws Exception {
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdCalloutTarget() throws Exception {
        // Get the adCalloutTarget
        restAdCalloutTargetMockMvc.perform(get("/api/ad-callout-targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdCalloutTarget() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        int databaseSizeBeforeUpdate = adCalloutTargetRepository.findAll().size();

        // Update the adCalloutTarget
        AdCalloutTarget updatedAdCalloutTarget = adCalloutTargetRepository.findById(adCalloutTarget.getId()).get();
        // Disconnect from session so that the updates on updatedAdCalloutTarget are not directly saved in db
        em.detach(updatedAdCalloutTarget);
        updatedAdCalloutTarget
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .sourceField(UPDATED_SOURCE_FIELD)
            .targetName(UPDATED_TARGET_NAME);
        AdCalloutTargetDTO adCalloutTargetDTO = adCalloutTargetMapper.toDto(updatedAdCalloutTarget);

        restAdCalloutTargetMockMvc.perform(put("/api/ad-callout-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutTargetDTO)))
            .andExpect(status().isOk());

        // Validate the AdCalloutTarget in the database
        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeUpdate);
        AdCalloutTarget testAdCalloutTarget = adCalloutTargetList.get(adCalloutTargetList.size() - 1);
        assertThat(testAdCalloutTarget.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdCalloutTarget.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdCalloutTarget.getSourceField()).isEqualTo(UPDATED_SOURCE_FIELD);
        assertThat(testAdCalloutTarget.getTargetName()).isEqualTo(UPDATED_TARGET_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAdCalloutTarget() throws Exception {
        int databaseSizeBeforeUpdate = adCalloutTargetRepository.findAll().size();

        // Create the AdCalloutTarget
        AdCalloutTargetDTO adCalloutTargetDTO = adCalloutTargetMapper.toDto(adCalloutTarget);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdCalloutTargetMockMvc.perform(put("/api/ad-callout-targets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adCalloutTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdCalloutTarget in the database
        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdCalloutTarget() throws Exception {
        // Initialize the database
        adCalloutTargetRepository.saveAndFlush(adCalloutTarget);

        int databaseSizeBeforeDelete = adCalloutTargetRepository.findAll().size();

        // Delete the adCalloutTarget
        restAdCalloutTargetMockMvc.perform(delete("/api/ad-callout-targets/{id}", adCalloutTarget.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdCalloutTarget> adCalloutTargetList = adCalloutTargetRepository.findAll();
        assertThat(adCalloutTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
