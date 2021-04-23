package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvent;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.repository.CEventRepository;
import com.bhp.opusb.service.CEventService;
import com.bhp.opusb.service.dto.CEventDTO;
import com.bhp.opusb.service.mapper.CEventMapper;
import com.bhp.opusb.service.dto.CEventCriteria;
import com.bhp.opusb.service.CEventQueryService;

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
 * Integration tests for the {@link CEventResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEventResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEventRepository cEventRepository;

    @Autowired
    private CEventMapper cEventMapper;

    @Autowired
    private CEventService cEventService;

    @Autowired
    private CEventQueryService cEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEventMockMvc;

    private CEvent cEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvent createEntity(EntityManager em) {
        CEvent cEvent = new CEvent()
            .name(DEFAULT_NAME)
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
        cEvent.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cEvent.setCProductClassification(cProductClassification);
        return cEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvent createUpdatedEntity(EntityManager em) {
        CEvent cEvent = new CEvent()
            .name(UPDATED_NAME)
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
        cEvent.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createUpdatedEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cEvent.setCProductClassification(cProductClassification);
        return cEvent;
    }

    @BeforeEach
    public void initTest() {
        cEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvent() throws Exception {
        int databaseSizeBeforeCreate = cEventRepository.findAll().size();

        // Create the CEvent
        CEventDTO cEventDTO = cEventMapper.toDto(cEvent);
        restCEventMockMvc.perform(post("/api/c-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvent in the database
        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeCreate + 1);
        CEvent testCEvent = cEventList.get(cEventList.size() - 1);
        assertThat(testCEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCEvent.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvent.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEventRepository.findAll().size();

        // Create the CEvent with an existing ID
        cEvent.setId(1L);
        CEventDTO cEventDTO = cEventMapper.toDto(cEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEventMockMvc.perform(post("/api/c-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvent in the database
        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEventRepository.findAll().size();
        // set the field null
        cEvent.setName(null);

        // Create the CEvent, which fails.
        CEventDTO cEventDTO = cEventMapper.toDto(cEvent);

        restCEventMockMvc.perform(post("/api/c-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventDTO)))
            .andExpect(status().isBadRequest());

        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEvents() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList
        restCEventMockMvc.perform(get("/api/c-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvent() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get the cEvent
        restCEventMockMvc.perform(get("/api/c-events/{id}", cEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEventsByIdFiltering() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        Long id = cEvent.getId();

        defaultCEventShouldBeFound("id.equals=" + id);
        defaultCEventShouldNotBeFound("id.notEquals=" + id);

        defaultCEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEventShouldNotBeFound("id.greaterThan=" + id);

        defaultCEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEventsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name equals to DEFAULT_NAME
        defaultCEventShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cEventList where name equals to UPDATED_NAME
        defaultCEventShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name not equals to DEFAULT_NAME
        defaultCEventShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cEventList where name not equals to UPDATED_NAME
        defaultCEventShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCEventShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cEventList where name equals to UPDATED_NAME
        defaultCEventShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name is not null
        defaultCEventShouldBeFound("name.specified=true");

        // Get all the cEventList where name is null
        defaultCEventShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventsByNameContainsSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name contains DEFAULT_NAME
        defaultCEventShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cEventList where name contains UPDATED_NAME
        defaultCEventShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where name does not contain DEFAULT_NAME
        defaultCEventShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cEventList where name does not contain UPDATED_NAME
        defaultCEventShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description equals to DEFAULT_DESCRIPTION
        defaultCEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventList where description equals to UPDATED_DESCRIPTION
        defaultCEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description not equals to DEFAULT_DESCRIPTION
        defaultCEventShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventList where description not equals to UPDATED_DESCRIPTION
        defaultCEventShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cEventList where description equals to UPDATED_DESCRIPTION
        defaultCEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description is not null
        defaultCEventShouldBeFound("description.specified=true");

        // Get all the cEventList where description is null
        defaultCEventShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description contains DEFAULT_DESCRIPTION
        defaultCEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cEventList where description contains UPDATED_DESCRIPTION
        defaultCEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where description does not contain DEFAULT_DESCRIPTION
        defaultCEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cEventList where description does not contain UPDATED_DESCRIPTION
        defaultCEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCEventsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where uid equals to DEFAULT_UID
        defaultCEventShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEventList where uid equals to UPDATED_UID
        defaultCEventShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where uid not equals to DEFAULT_UID
        defaultCEventShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEventList where uid not equals to UPDATED_UID
        defaultCEventShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEventShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEventList where uid equals to UPDATED_UID
        defaultCEventShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where uid is not null
        defaultCEventShouldBeFound("uid.specified=true");

        // Get all the cEventList where uid is null
        defaultCEventShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where active equals to DEFAULT_ACTIVE
        defaultCEventShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEventList where active equals to UPDATED_ACTIVE
        defaultCEventShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where active not equals to DEFAULT_ACTIVE
        defaultCEventShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEventList where active not equals to UPDATED_ACTIVE
        defaultCEventShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEventShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEventList where active equals to UPDATED_ACTIVE
        defaultCEventShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        // Get all the cEventList where active is not null
        defaultCEventShouldBeFound("active.specified=true");

        // Get all the cEventList where active is null
        defaultCEventShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvent.getAdOrganization();
        cEventRepository.saveAndFlush(cEvent);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEventList where adOrganization equals to adOrganizationId
        defaultCEventShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEventList where adOrganization equals to adOrganizationId + 1
        defaultCEventShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEventsByCProductClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductClassification cProductClassification = cEvent.getCProductClassification();
        cEventRepository.saveAndFlush(cEvent);
        Long cProductClassificationId = cProductClassification.getId();

        // Get all the cEventList where cProductClassification equals to cProductClassificationId
        defaultCEventShouldBeFound("cProductClassificationId.equals=" + cProductClassificationId);

        // Get all the cEventList where cProductClassification equals to cProductClassificationId + 1
        defaultCEventShouldNotBeFound("cProductClassificationId.equals=" + (cProductClassificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEventShouldBeFound(String filter) throws Exception {
        restCEventMockMvc.perform(get("/api/c-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEventMockMvc.perform(get("/api/c-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEventShouldNotBeFound(String filter) throws Exception {
        restCEventMockMvc.perform(get("/api/c-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEventMockMvc.perform(get("/api/c-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvent() throws Exception {
        // Get the cEvent
        restCEventMockMvc.perform(get("/api/c-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvent() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        int databaseSizeBeforeUpdate = cEventRepository.findAll().size();

        // Update the cEvent
        CEvent updatedCEvent = cEventRepository.findById(cEvent.getId()).get();
        // Disconnect from session so that the updates on updatedCEvent are not directly saved in db
        em.detach(updatedCEvent);
        updatedCEvent
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEventDTO cEventDTO = cEventMapper.toDto(updatedCEvent);

        restCEventMockMvc.perform(put("/api/c-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventDTO)))
            .andExpect(status().isOk());

        // Validate the CEvent in the database
        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeUpdate);
        CEvent testCEvent = cEventList.get(cEventList.size() - 1);
        assertThat(testCEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCEvent.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvent.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvent() throws Exception {
        int databaseSizeBeforeUpdate = cEventRepository.findAll().size();

        // Create the CEvent
        CEventDTO cEventDTO = cEventMapper.toDto(cEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEventMockMvc.perform(put("/api/c-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvent in the database
        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvent() throws Exception {
        // Initialize the database
        cEventRepository.saveAndFlush(cEvent);

        int databaseSizeBeforeDelete = cEventRepository.findAll().size();

        // Delete the cEvent
        restCEventMockMvc.perform(delete("/api/c-events/{id}", cEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvent> cEventList = cEventRepository.findAll();
        assertThat(cEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
