package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalificationEvent;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPrequalificationEventRepository;
import com.bhp.opusb.service.CPrequalificationEventService;
import com.bhp.opusb.service.dto.CPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventMapper;
import com.bhp.opusb.service.dto.CPrequalificationEventCriteria;
import com.bhp.opusb.service.CPrequalificationEventQueryService;

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
 * Integration tests for the {@link CPrequalificationEventResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalificationEventResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalificationEventRepository cPrequalificationEventRepository;

    @Autowired
    private CPrequalificationEventMapper cPrequalificationEventMapper;

    @Autowired
    private CPrequalificationEventService cPrequalificationEventService;

    @Autowired
    private CPrequalificationEventQueryService cPrequalificationEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalificationEventMockMvc;

    private CPrequalificationEvent cPrequalificationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationEvent createEntity(EntityManager em) {
        CPrequalificationEvent cPrequalificationEvent = new CPrequalificationEvent()
            .name(DEFAULT_NAME)
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
        cPrequalificationEvent.setAdOrganization(aDOrganization);
        return cPrequalificationEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationEvent createUpdatedEntity(EntityManager em) {
        CPrequalificationEvent cPrequalificationEvent = new CPrequalificationEvent()
            .name(UPDATED_NAME)
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
        cPrequalificationEvent.setAdOrganization(aDOrganization);
        return cPrequalificationEvent;
    }

    @BeforeEach
    public void initTest() {
        cPrequalificationEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalificationEvent() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationEventRepository.findAll().size();

        // Create the CPrequalificationEvent
        CPrequalificationEventDTO cPrequalificationEventDTO = cPrequalificationEventMapper.toDto(cPrequalificationEvent);
        restCPrequalificationEventMockMvc.perform(post("/api/c-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalificationEvent in the database
        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalificationEvent testCPrequalificationEvent = cPrequalificationEventList.get(cPrequalificationEventList.size() - 1);
        assertThat(testCPrequalificationEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCPrequalificationEvent.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalificationEvent.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalificationEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationEventRepository.findAll().size();

        // Create the CPrequalificationEvent with an existing ID
        cPrequalificationEvent.setId(1L);
        CPrequalificationEventDTO cPrequalificationEventDTO = cPrequalificationEventMapper.toDto(cPrequalificationEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalificationEventMockMvc.perform(post("/api/c-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationEvent in the database
        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalificationEventRepository.findAll().size();
        // set the field null
        cPrequalificationEvent.setName(null);

        // Create the CPrequalificationEvent, which fails.
        CPrequalificationEventDTO cPrequalificationEventDTO = cPrequalificationEventMapper.toDto(cPrequalificationEvent);

        restCPrequalificationEventMockMvc.perform(post("/api/c-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEvents() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalificationEvent() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get the cPrequalificationEvent
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events/{id}", cPrequalificationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalificationEvent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalificationEventsByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        Long id = cPrequalificationEvent.getId();

        defaultCPrequalificationEventShouldBeFound("id.equals=" + id);
        defaultCPrequalificationEventShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalificationEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalificationEventShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalificationEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalificationEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name equals to DEFAULT_NAME
        defaultCPrequalificationEventShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cPrequalificationEventList where name equals to UPDATED_NAME
        defaultCPrequalificationEventShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name not equals to DEFAULT_NAME
        defaultCPrequalificationEventShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cPrequalificationEventList where name not equals to UPDATED_NAME
        defaultCPrequalificationEventShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCPrequalificationEventShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cPrequalificationEventList where name equals to UPDATED_NAME
        defaultCPrequalificationEventShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name is not null
        defaultCPrequalificationEventShouldBeFound("name.specified=true");

        // Get all the cPrequalificationEventList where name is null
        defaultCPrequalificationEventShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name contains DEFAULT_NAME
        defaultCPrequalificationEventShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cPrequalificationEventList where name contains UPDATED_NAME
        defaultCPrequalificationEventShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where name does not contain DEFAULT_NAME
        defaultCPrequalificationEventShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cPrequalificationEventList where name does not contain UPDATED_NAME
        defaultCPrequalificationEventShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where uid equals to DEFAULT_UID
        defaultCPrequalificationEventShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalificationEventList where uid equals to UPDATED_UID
        defaultCPrequalificationEventShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where uid not equals to DEFAULT_UID
        defaultCPrequalificationEventShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalificationEventList where uid not equals to UPDATED_UID
        defaultCPrequalificationEventShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalificationEventShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalificationEventList where uid equals to UPDATED_UID
        defaultCPrequalificationEventShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where uid is not null
        defaultCPrequalificationEventShouldBeFound("uid.specified=true");

        // Get all the cPrequalificationEventList where uid is null
        defaultCPrequalificationEventShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where active equals to DEFAULT_ACTIVE
        defaultCPrequalificationEventShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationEventList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationEventShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalificationEventShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationEventList where active not equals to UPDATED_ACTIVE
        defaultCPrequalificationEventShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalificationEventShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalificationEventList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationEventShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        // Get all the cPrequalificationEventList where active is not null
        defaultCPrequalificationEventShouldBeFound("active.specified=true");

        // Get all the cPrequalificationEventList where active is null
        defaultCPrequalificationEventShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalificationEvent.getAdOrganization();
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalificationEventList where adOrganization equals to adOrganizationId
        defaultCPrequalificationEventShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalificationEventList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalificationEventShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalificationEventShouldBeFound(String filter) throws Exception {
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalificationEventShouldNotBeFound(String filter) throws Exception {
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalificationEvent() throws Exception {
        // Get the cPrequalificationEvent
        restCPrequalificationEventMockMvc.perform(get("/api/c-prequalification-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalificationEvent() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        int databaseSizeBeforeUpdate = cPrequalificationEventRepository.findAll().size();

        // Update the cPrequalificationEvent
        CPrequalificationEvent updatedCPrequalificationEvent = cPrequalificationEventRepository.findById(cPrequalificationEvent.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalificationEvent are not directly saved in db
        em.detach(updatedCPrequalificationEvent);
        updatedCPrequalificationEvent
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalificationEventDTO cPrequalificationEventDTO = cPrequalificationEventMapper.toDto(updatedCPrequalificationEvent);

        restCPrequalificationEventMockMvc.perform(put("/api/c-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalificationEvent in the database
        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeUpdate);
        CPrequalificationEvent testCPrequalificationEvent = cPrequalificationEventList.get(cPrequalificationEventList.size() - 1);
        assertThat(testCPrequalificationEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCPrequalificationEvent.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalificationEvent.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalificationEvent() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalificationEventRepository.findAll().size();

        // Create the CPrequalificationEvent
        CPrequalificationEventDTO cPrequalificationEventDTO = cPrequalificationEventMapper.toDto(cPrequalificationEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalificationEventMockMvc.perform(put("/api/c-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationEvent in the database
        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalificationEvent() throws Exception {
        // Initialize the database
        cPrequalificationEventRepository.saveAndFlush(cPrequalificationEvent);

        int databaseSizeBeforeDelete = cPrequalificationEventRepository.findAll().size();

        // Delete the cPrequalificationEvent
        restCPrequalificationEventMockMvc.perform(delete("/api/c-prequalification-events/{id}", cPrequalificationEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalificationEvent> cPrequalificationEventList = cPrequalificationEventRepository.findAll();
        assertThat(cPrequalificationEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
