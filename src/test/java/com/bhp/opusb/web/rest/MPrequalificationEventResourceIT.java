package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationEvent;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CPrequalificationEvent;
import com.bhp.opusb.domain.CPrequalificationMethod;
import com.bhp.opusb.repository.MPrequalificationEventRepository;
import com.bhp.opusb.service.MPrequalificationEventService;
import com.bhp.opusb.service.dto.MPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEventMapper;
import com.bhp.opusb.service.dto.MPrequalificationEventCriteria;
import com.bhp.opusb.service.MPrequalificationEventQueryService;

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
 * Integration tests for the {@link MPrequalificationEventResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationEventResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationEventRepository mPrequalificationEventRepository;

    @Autowired
    private MPrequalificationEventMapper mPrequalificationEventMapper;

    @Autowired
    private MPrequalificationEventService mPrequalificationEventService;

    @Autowired
    private MPrequalificationEventQueryService mPrequalificationEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationEventMockMvc;

    private MPrequalificationEvent mPrequalificationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEvent createEntity(EntityManager em) {
        MPrequalificationEvent mPrequalificationEvent = new MPrequalificationEvent()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationEvent.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationEvent.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationEvent cPrequalificationEvent;
        if (TestUtil.findAll(em, CPrequalificationEvent.class).isEmpty()) {
            cPrequalificationEvent = CPrequalificationEventResourceIT.createEntity(em);
            em.persist(cPrequalificationEvent);
            em.flush();
        } else {
            cPrequalificationEvent = TestUtil.findAll(em, CPrequalificationEvent.class).get(0);
        }
        mPrequalificationEvent.setEvent(cPrequalificationEvent);
        // Add required entity
        CPrequalificationMethod cPrequalificationMethod;
        if (TestUtil.findAll(em, CPrequalificationMethod.class).isEmpty()) {
            cPrequalificationMethod = CPrequalificationMethodResourceIT.createEntity(em);
            em.persist(cPrequalificationMethod);
            em.flush();
        } else {
            cPrequalificationMethod = TestUtil.findAll(em, CPrequalificationMethod.class).get(0);
        }
        mPrequalificationEvent.setMethod(cPrequalificationMethod);
        return mPrequalificationEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEvent createUpdatedEntity(EntityManager em) {
        MPrequalificationEvent mPrequalificationEvent = new MPrequalificationEvent()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MPrequalificationInformation mPrequalificationInformation;
        if (TestUtil.findAll(em, MPrequalificationInformation.class).isEmpty()) {
            mPrequalificationInformation = MPrequalificationInformationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationInformation);
            em.flush();
        } else {
            mPrequalificationInformation = TestUtil.findAll(em, MPrequalificationInformation.class).get(0);
        }
        mPrequalificationEvent.setPrequalification(mPrequalificationInformation);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationEvent.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationEvent cPrequalificationEvent;
        if (TestUtil.findAll(em, CPrequalificationEvent.class).isEmpty()) {
            cPrequalificationEvent = CPrequalificationEventResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationEvent);
            em.flush();
        } else {
            cPrequalificationEvent = TestUtil.findAll(em, CPrequalificationEvent.class).get(0);
        }
        mPrequalificationEvent.setEvent(cPrequalificationEvent);
        // Add required entity
        CPrequalificationMethod cPrequalificationMethod;
        if (TestUtil.findAll(em, CPrequalificationMethod.class).isEmpty()) {
            cPrequalificationMethod = CPrequalificationMethodResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationMethod);
            em.flush();
        } else {
            cPrequalificationMethod = TestUtil.findAll(em, CPrequalificationMethod.class).get(0);
        }
        mPrequalificationEvent.setMethod(cPrequalificationMethod);
        return mPrequalificationEvent;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationEvent() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEventRepository.findAll().size();

        // Create the MPrequalificationEvent
        MPrequalificationEventDTO mPrequalificationEventDTO = mPrequalificationEventMapper.toDto(mPrequalificationEvent);
        restMPrequalificationEventMockMvc.perform(post("/api/m-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEventDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationEvent in the database
        List<MPrequalificationEvent> mPrequalificationEventList = mPrequalificationEventRepository.findAll();
        assertThat(mPrequalificationEventList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationEvent testMPrequalificationEvent = mPrequalificationEventList.get(mPrequalificationEventList.size() - 1);
        assertThat(testMPrequalificationEvent.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationEvent.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEventRepository.findAll().size();

        // Create the MPrequalificationEvent with an existing ID
        mPrequalificationEvent.setId(1L);
        MPrequalificationEventDTO mPrequalificationEventDTO = mPrequalificationEventMapper.toDto(mPrequalificationEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationEventMockMvc.perform(post("/api/m-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEvent in the database
        List<MPrequalificationEvent> mPrequalificationEventList = mPrequalificationEventRepository.findAll();
        assertThat(mPrequalificationEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvents() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationEvent() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get the mPrequalificationEvent
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events/{id}", mPrequalificationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationEvent.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationEventsByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        Long id = mPrequalificationEvent.getId();

        defaultMPrequalificationEventShouldBeFound("id.equals=" + id);
        defaultMPrequalificationEventShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationEventShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEventsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where uid equals to DEFAULT_UID
        defaultMPrequalificationEventShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationEventList where uid equals to UPDATED_UID
        defaultMPrequalificationEventShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where uid not equals to DEFAULT_UID
        defaultMPrequalificationEventShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationEventList where uid not equals to UPDATED_UID
        defaultMPrequalificationEventShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationEventShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationEventList where uid equals to UPDATED_UID
        defaultMPrequalificationEventShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where uid is not null
        defaultMPrequalificationEventShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationEventList where uid is null
        defaultMPrequalificationEventShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationEventShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEventList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEventShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationEventShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEventList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationEventShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationEventShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationEventList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEventShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        // Get all the mPrequalificationEventList where active is not null
        defaultMPrequalificationEventShouldBeFound("active.specified=true");

        // Get all the mPrequalificationEventList where active is null
        defaultMPrequalificationEventShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEventsByPrequalificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationInformation prequalification = mPrequalificationEvent.getPrequalification();
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);
        Long prequalificationId = prequalification.getId();

        // Get all the mPrequalificationEventList where prequalification equals to prequalificationId
        defaultMPrequalificationEventShouldBeFound("prequalificationId.equals=" + prequalificationId);

        // Get all the mPrequalificationEventList where prequalification equals to prequalificationId + 1
        defaultMPrequalificationEventShouldNotBeFound("prequalificationId.equals=" + (prequalificationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEventsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationEvent.getAdOrganization();
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationEventList where adOrganization equals to adOrganizationId
        defaultMPrequalificationEventShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationEventList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationEventShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEventsByEventIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationEvent event = mPrequalificationEvent.getEvent();
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);
        Long eventId = event.getId();

        // Get all the mPrequalificationEventList where event equals to eventId
        defaultMPrequalificationEventShouldBeFound("eventId.equals=" + eventId);

        // Get all the mPrequalificationEventList where event equals to eventId + 1
        defaultMPrequalificationEventShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEventsByMethodIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationMethod method = mPrequalificationEvent.getMethod();
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);
        Long methodId = method.getId();

        // Get all the mPrequalificationEventList where method equals to methodId
        defaultMPrequalificationEventShouldBeFound("methodId.equals=" + methodId);

        // Get all the mPrequalificationEventList where method equals to methodId + 1
        defaultMPrequalificationEventShouldNotBeFound("methodId.equals=" + (methodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationEventShouldBeFound(String filter) throws Exception {
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationEventShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationEvent() throws Exception {
        // Get the mPrequalificationEvent
        restMPrequalificationEventMockMvc.perform(get("/api/m-prequalification-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationEvent() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        int databaseSizeBeforeUpdate = mPrequalificationEventRepository.findAll().size();

        // Update the mPrequalificationEvent
        MPrequalificationEvent updatedMPrequalificationEvent = mPrequalificationEventRepository.findById(mPrequalificationEvent.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationEvent are not directly saved in db
        em.detach(updatedMPrequalificationEvent);
        updatedMPrequalificationEvent
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationEventDTO mPrequalificationEventDTO = mPrequalificationEventMapper.toDto(updatedMPrequalificationEvent);

        restMPrequalificationEventMockMvc.perform(put("/api/m-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEventDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationEvent in the database
        List<MPrequalificationEvent> mPrequalificationEventList = mPrequalificationEventRepository.findAll();
        assertThat(mPrequalificationEventList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationEvent testMPrequalificationEvent = mPrequalificationEventList.get(mPrequalificationEventList.size() - 1);
        assertThat(testMPrequalificationEvent.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationEvent.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationEvent() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationEventRepository.findAll().size();

        // Create the MPrequalificationEvent
        MPrequalificationEventDTO mPrequalificationEventDTO = mPrequalificationEventMapper.toDto(mPrequalificationEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationEventMockMvc.perform(put("/api/m-prequalification-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEvent in the database
        List<MPrequalificationEvent> mPrequalificationEventList = mPrequalificationEventRepository.findAll();
        assertThat(mPrequalificationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationEvent() throws Exception {
        // Initialize the database
        mPrequalificationEventRepository.saveAndFlush(mPrequalificationEvent);

        int databaseSizeBeforeDelete = mPrequalificationEventRepository.findAll().size();

        // Delete the mPrequalificationEvent
        restMPrequalificationEventMockMvc.perform(delete("/api/m-prequalification-events/{id}", mPrequalificationEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationEvent> mPrequalificationEventList = mPrequalificationEventRepository.findAll();
        assertThat(mPrequalificationEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
