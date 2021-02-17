package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CEventType;
import com.bhp.opusb.repository.CEventTypelineRepository;
import com.bhp.opusb.service.CEventTypelineService;
import com.bhp.opusb.service.dto.CEventTypelineDTO;
import com.bhp.opusb.service.mapper.CEventTypelineMapper;
import com.bhp.opusb.service.dto.CEventTypelineCriteria;
import com.bhp.opusb.service.CEventTypelineQueryService;

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
 * Integration tests for the {@link CEventTypelineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEventTypelineResourceIT {

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEventTypelineRepository cEventTypelineRepository;

    @Autowired
    private CEventTypelineMapper cEventTypelineMapper;

    @Autowired
    private CEventTypelineService cEventTypelineService;

    @Autowired
    private CEventTypelineQueryService cEventTypelineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEventTypelineMockMvc;

    private CEventTypeline cEventTypeline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEventTypeline createEntity(EntityManager em) {
        CEventTypeline cEventTypeline = new CEventTypeline()
            .event(DEFAULT_EVENT)
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
        cEventTypeline.setAdOrganization(aDOrganization);
        // Add required entity
        CEventType cEventType;
        if (TestUtil.findAll(em, CEventType.class).isEmpty()) {
            cEventType = CEventTypeResourceIT.createEntity(em);
            em.persist(cEventType);
            em.flush();
        } else {
            cEventType = TestUtil.findAll(em, CEventType.class).get(0);
        }
        cEventTypeline.setEventType(cEventType);
        return cEventTypeline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEventTypeline createUpdatedEntity(EntityManager em) {
        CEventTypeline cEventTypeline = new CEventTypeline()
            .event(UPDATED_EVENT)
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
        cEventTypeline.setAdOrganization(aDOrganization);
        // Add required entity
        CEventType cEventType;
        if (TestUtil.findAll(em, CEventType.class).isEmpty()) {
            cEventType = CEventTypeResourceIT.createUpdatedEntity(em);
            em.persist(cEventType);
            em.flush();
        } else {
            cEventType = TestUtil.findAll(em, CEventType.class).get(0);
        }
        cEventTypeline.setEventType(cEventType);
        return cEventTypeline;
    }

    @BeforeEach
    public void initTest() {
        cEventTypeline = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEventTypeline() throws Exception {
        int databaseSizeBeforeCreate = cEventTypelineRepository.findAll().size();

        // Create the CEventTypeline
        CEventTypelineDTO cEventTypelineDTO = cEventTypelineMapper.toDto(cEventTypeline);
        restCEventTypelineMockMvc.perform(post("/api/c-event-typelines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypelineDTO)))
            .andExpect(status().isCreated());

        // Validate the CEventTypeline in the database
        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeCreate + 1);
        CEventTypeline testCEventTypeline = cEventTypelineList.get(cEventTypelineList.size() - 1);
        assertThat(testCEventTypeline.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testCEventTypeline.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCEventTypeline.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEventTypeline.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEventTypelineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEventTypelineRepository.findAll().size();

        // Create the CEventTypeline with an existing ID
        cEventTypeline.setId(1L);
        CEventTypelineDTO cEventTypelineDTO = cEventTypelineMapper.toDto(cEventTypeline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEventTypelineMockMvc.perform(post("/api/c-event-typelines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypelineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEventTypeline in the database
        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEventTypelineRepository.findAll().size();
        // set the field null
        cEventTypeline.setEvent(null);

        // Create the CEventTypeline, which fails.
        CEventTypelineDTO cEventTypelineDTO = cEventTypelineMapper.toDto(cEventTypeline);

        restCEventTypelineMockMvc.perform(post("/api/c-event-typelines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypelineDTO)))
            .andExpect(status().isBadRequest());

        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEventTypelines() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEventTypeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEventTypeline() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get the cEventTypeline
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines/{id}", cEventTypeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEventTypeline.getId().intValue()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEventTypelinesByIdFiltering() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        Long id = cEventTypeline.getId();

        defaultCEventTypelineShouldBeFound("id.equals=" + id);
        defaultCEventTypelineShouldNotBeFound("id.notEquals=" + id);

        defaultCEventTypelineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEventTypelineShouldNotBeFound("id.greaterThan=" + id);

        defaultCEventTypelineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEventTypelineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEventTypelinesByEventIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event equals to DEFAULT_EVENT
        defaultCEventTypelineShouldBeFound("event.equals=" + DEFAULT_EVENT);

        // Get all the cEventTypelineList where event equals to UPDATED_EVENT
        defaultCEventTypelineShouldNotBeFound("event.equals=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByEventIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event not equals to DEFAULT_EVENT
        defaultCEventTypelineShouldNotBeFound("event.notEquals=" + DEFAULT_EVENT);

        // Get all the cEventTypelineList where event not equals to UPDATED_EVENT
        defaultCEventTypelineShouldBeFound("event.notEquals=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByEventIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event in DEFAULT_EVENT or UPDATED_EVENT
        defaultCEventTypelineShouldBeFound("event.in=" + DEFAULT_EVENT + "," + UPDATED_EVENT);

        // Get all the cEventTypelineList where event equals to UPDATED_EVENT
        defaultCEventTypelineShouldNotBeFound("event.in=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event is not null
        defaultCEventTypelineShouldBeFound("event.specified=true");

        // Get all the cEventTypelineList where event is null
        defaultCEventTypelineShouldNotBeFound("event.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventTypelinesByEventContainsSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event contains DEFAULT_EVENT
        defaultCEventTypelineShouldBeFound("event.contains=" + DEFAULT_EVENT);

        // Get all the cEventTypelineList where event contains UPDATED_EVENT
        defaultCEventTypelineShouldNotBeFound("event.contains=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByEventNotContainsSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where event does not contain DEFAULT_EVENT
        defaultCEventTypelineShouldNotBeFound("event.doesNotContain=" + DEFAULT_EVENT);

        // Get all the cEventTypelineList where event does not contain UPDATED_EVENT
        defaultCEventTypelineShouldBeFound("event.doesNotContain=" + UPDATED_EVENT);
    }


    @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description equals to DEFAULT_DESCRIPTION
        defaultCEventTypelineShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypelineList where description equals to UPDATED_DESCRIPTION
        defaultCEventTypelineShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description not equals to DEFAULT_DESCRIPTION
        defaultCEventTypelineShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypelineList where description not equals to UPDATED_DESCRIPTION
        defaultCEventTypelineShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCEventTypelineShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cEventTypelineList where description equals to UPDATED_DESCRIPTION
        defaultCEventTypelineShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description is not null
        defaultCEventTypelineShouldBeFound("description.specified=true");

        // Get all the cEventTypelineList where description is null
        defaultCEventTypelineShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description contains DEFAULT_DESCRIPTION
        defaultCEventTypelineShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypelineList where description contains UPDATED_DESCRIPTION
        defaultCEventTypelineShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where description does not contain DEFAULT_DESCRIPTION
        defaultCEventTypelineShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypelineList where description does not contain UPDATED_DESCRIPTION
        defaultCEventTypelineShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCEventTypelinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where uid equals to DEFAULT_UID
        defaultCEventTypelineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEventTypelineList where uid equals to UPDATED_UID
        defaultCEventTypelineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where uid not equals to DEFAULT_UID
        defaultCEventTypelineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEventTypelineList where uid not equals to UPDATED_UID
        defaultCEventTypelineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEventTypelineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEventTypelineList where uid equals to UPDATED_UID
        defaultCEventTypelineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where uid is not null
        defaultCEventTypelineShouldBeFound("uid.specified=true");

        // Get all the cEventTypelineList where uid is null
        defaultCEventTypelineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where active equals to DEFAULT_ACTIVE
        defaultCEventTypelineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEventTypelineList where active equals to UPDATED_ACTIVE
        defaultCEventTypelineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where active not equals to DEFAULT_ACTIVE
        defaultCEventTypelineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEventTypelineList where active not equals to UPDATED_ACTIVE
        defaultCEventTypelineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEventTypelineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEventTypelineList where active equals to UPDATED_ACTIVE
        defaultCEventTypelineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        // Get all the cEventTypelineList where active is not null
        defaultCEventTypelineShouldBeFound("active.specified=true");

        // Get all the cEventTypelineList where active is null
        defaultCEventTypelineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventTypelinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEventTypeline.getAdOrganization();
        cEventTypelineRepository.saveAndFlush(cEventTypeline);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEventTypelineList where adOrganization equals to adOrganizationId
        defaultCEventTypelineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEventTypelineList where adOrganization equals to adOrganizationId + 1
        defaultCEventTypelineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEventTypelinesByEventTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CEventType eventType = cEventTypeline.getEventType();
        cEventTypelineRepository.saveAndFlush(cEventTypeline);
        Long eventTypeId = eventType.getId();

        // Get all the cEventTypelineList where eventType equals to eventTypeId
        defaultCEventTypelineShouldBeFound("eventTypeId.equals=" + eventTypeId);

        // Get all the cEventTypelineList where eventType equals to eventTypeId + 1
        defaultCEventTypelineShouldNotBeFound("eventTypeId.equals=" + (eventTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEventTypelineShouldBeFound(String filter) throws Exception {
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEventTypeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEventTypelineShouldNotBeFound(String filter) throws Exception {
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEventTypeline() throws Exception {
        // Get the cEventTypeline
        restCEventTypelineMockMvc.perform(get("/api/c-event-typelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEventTypeline() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        int databaseSizeBeforeUpdate = cEventTypelineRepository.findAll().size();

        // Update the cEventTypeline
        CEventTypeline updatedCEventTypeline = cEventTypelineRepository.findById(cEventTypeline.getId()).get();
        // Disconnect from session so that the updates on updatedCEventTypeline are not directly saved in db
        em.detach(updatedCEventTypeline);
        updatedCEventTypeline
            .event(UPDATED_EVENT)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEventTypelineDTO cEventTypelineDTO = cEventTypelineMapper.toDto(updatedCEventTypeline);

        restCEventTypelineMockMvc.perform(put("/api/c-event-typelines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypelineDTO)))
            .andExpect(status().isOk());

        // Validate the CEventTypeline in the database
        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeUpdate);
        CEventTypeline testCEventTypeline = cEventTypelineList.get(cEventTypelineList.size() - 1);
        assertThat(testCEventTypeline.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testCEventTypeline.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCEventTypeline.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEventTypeline.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEventTypeline() throws Exception {
        int databaseSizeBeforeUpdate = cEventTypelineRepository.findAll().size();

        // Create the CEventTypeline
        CEventTypelineDTO cEventTypelineDTO = cEventTypelineMapper.toDto(cEventTypeline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEventTypelineMockMvc.perform(put("/api/c-event-typelines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypelineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEventTypeline in the database
        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEventTypeline() throws Exception {
        // Initialize the database
        cEventTypelineRepository.saveAndFlush(cEventTypeline);

        int databaseSizeBeforeDelete = cEventTypelineRepository.findAll().size();

        // Delete the cEventTypeline
        restCEventTypelineMockMvc.perform(delete("/api/c-event-typelines/{id}", cEventTypeline.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEventTypeline> cEventTypelineList = cEventTypelineRepository.findAll();
        assertThat(cEventTypelineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
