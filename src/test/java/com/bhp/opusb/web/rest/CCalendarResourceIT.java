package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCalendar;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CCalendarRepository;
import com.bhp.opusb.service.CCalendarService;
import com.bhp.opusb.service.dto.CCalendarDTO;
import com.bhp.opusb.service.mapper.CCalendarMapper;
import com.bhp.opusb.service.dto.CCalendarCriteria;
import com.bhp.opusb.service.CCalendarQueryService;

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
 * Integration tests for the {@link CCalendarResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CCalendarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CCalendarRepository cCalendarRepository;

    @Autowired
    private CCalendarMapper cCalendarMapper;

    @Autowired
    private CCalendarService cCalendarService;

    @Autowired
    private CCalendarQueryService cCalendarQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCalendarMockMvc;

    private CCalendar cCalendar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCalendar createEntity(EntityManager em) {
        CCalendar cCalendar = new CCalendar()
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
        cCalendar.setAdOrganization(aDOrganization);
        return cCalendar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCalendar createUpdatedEntity(EntityManager em) {
        CCalendar cCalendar = new CCalendar()
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
        cCalendar.setAdOrganization(aDOrganization);
        return cCalendar;
    }

    @BeforeEach
    public void initTest() {
        cCalendar = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCalendar() throws Exception {
        int databaseSizeBeforeCreate = cCalendarRepository.findAll().size();

        // Create the CCalendar
        CCalendarDTO cCalendarDTO = cCalendarMapper.toDto(cCalendar);
        restCCalendarMockMvc.perform(post("/api/c-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCalendarDTO)))
            .andExpect(status().isCreated());

        // Validate the CCalendar in the database
        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeCreate + 1);
        CCalendar testCCalendar = cCalendarList.get(cCalendarList.size() - 1);
        assertThat(testCCalendar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCalendar.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCCalendar.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCCalendar.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCCalendarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCalendarRepository.findAll().size();

        // Create the CCalendar with an existing ID
        cCalendar.setId(1L);
        CCalendarDTO cCalendarDTO = cCalendarMapper.toDto(cCalendar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCalendarMockMvc.perform(post("/api/c-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCalendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCalendar in the database
        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCalendarRepository.findAll().size();
        // set the field null
        cCalendar.setName(null);

        // Create the CCalendar, which fails.
        CCalendarDTO cCalendarDTO = cCalendarMapper.toDto(cCalendar);

        restCCalendarMockMvc.perform(post("/api/c-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCalendarDTO)))
            .andExpect(status().isBadRequest());

        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCalendars() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList
        restCCalendarMockMvc.perform(get("/api/c-calendars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCalendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCCalendar() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get the cCalendar
        restCCalendarMockMvc.perform(get("/api/c-calendars/{id}", cCalendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCalendar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCCalendarsByIdFiltering() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        Long id = cCalendar.getId();

        defaultCCalendarShouldBeFound("id.equals=" + id);
        defaultCCalendarShouldNotBeFound("id.notEquals=" + id);

        defaultCCalendarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCCalendarShouldNotBeFound("id.greaterThan=" + id);

        defaultCCalendarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCCalendarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCCalendarsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name equals to DEFAULT_NAME
        defaultCCalendarShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cCalendarList where name equals to UPDATED_NAME
        defaultCCalendarShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name not equals to DEFAULT_NAME
        defaultCCalendarShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cCalendarList where name not equals to UPDATED_NAME
        defaultCCalendarShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCCalendarShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cCalendarList where name equals to UPDATED_NAME
        defaultCCalendarShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name is not null
        defaultCCalendarShouldBeFound("name.specified=true");

        // Get all the cCalendarList where name is null
        defaultCCalendarShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCalendarsByNameContainsSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name contains DEFAULT_NAME
        defaultCCalendarShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cCalendarList where name contains UPDATED_NAME
        defaultCCalendarShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where name does not contain DEFAULT_NAME
        defaultCCalendarShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cCalendarList where name does not contain UPDATED_NAME
        defaultCCalendarShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCCalendarsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description equals to DEFAULT_DESCRIPTION
        defaultCCalendarShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cCalendarList where description equals to UPDATED_DESCRIPTION
        defaultCCalendarShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description not equals to DEFAULT_DESCRIPTION
        defaultCCalendarShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cCalendarList where description not equals to UPDATED_DESCRIPTION
        defaultCCalendarShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCCalendarShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cCalendarList where description equals to UPDATED_DESCRIPTION
        defaultCCalendarShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description is not null
        defaultCCalendarShouldBeFound("description.specified=true");

        // Get all the cCalendarList where description is null
        defaultCCalendarShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCalendarsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description contains DEFAULT_DESCRIPTION
        defaultCCalendarShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cCalendarList where description contains UPDATED_DESCRIPTION
        defaultCCalendarShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where description does not contain DEFAULT_DESCRIPTION
        defaultCCalendarShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cCalendarList where description does not contain UPDATED_DESCRIPTION
        defaultCCalendarShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCCalendarsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where uid equals to DEFAULT_UID
        defaultCCalendarShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cCalendarList where uid equals to UPDATED_UID
        defaultCCalendarShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where uid not equals to DEFAULT_UID
        defaultCCalendarShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cCalendarList where uid not equals to UPDATED_UID
        defaultCCalendarShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where uid in DEFAULT_UID or UPDATED_UID
        defaultCCalendarShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cCalendarList where uid equals to UPDATED_UID
        defaultCCalendarShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where uid is not null
        defaultCCalendarShouldBeFound("uid.specified=true");

        // Get all the cCalendarList where uid is null
        defaultCCalendarShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCalendarsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where active equals to DEFAULT_ACTIVE
        defaultCCalendarShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cCalendarList where active equals to UPDATED_ACTIVE
        defaultCCalendarShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where active not equals to DEFAULT_ACTIVE
        defaultCCalendarShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cCalendarList where active not equals to UPDATED_ACTIVE
        defaultCCalendarShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCCalendarShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cCalendarList where active equals to UPDATED_ACTIVE
        defaultCCalendarShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCalendarsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        // Get all the cCalendarList where active is not null
        defaultCCalendarShouldBeFound("active.specified=true");

        // Get all the cCalendarList where active is null
        defaultCCalendarShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCalendarsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cCalendar.getAdOrganization();
        cCalendarRepository.saveAndFlush(cCalendar);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cCalendarList where adOrganization equals to adOrganizationId
        defaultCCalendarShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cCalendarList where adOrganization equals to adOrganizationId + 1
        defaultCCalendarShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCCalendarShouldBeFound(String filter) throws Exception {
        restCCalendarMockMvc.perform(get("/api/c-calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCalendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCCalendarMockMvc.perform(get("/api/c-calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCCalendarShouldNotBeFound(String filter) throws Exception {
        restCCalendarMockMvc.perform(get("/api/c-calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCCalendarMockMvc.perform(get("/api/c-calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCCalendar() throws Exception {
        // Get the cCalendar
        restCCalendarMockMvc.perform(get("/api/c-calendars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCalendar() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        int databaseSizeBeforeUpdate = cCalendarRepository.findAll().size();

        // Update the cCalendar
        CCalendar updatedCCalendar = cCalendarRepository.findById(cCalendar.getId()).get();
        // Disconnect from session so that the updates on updatedCCalendar are not directly saved in db
        em.detach(updatedCCalendar);
        updatedCCalendar
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CCalendarDTO cCalendarDTO = cCalendarMapper.toDto(updatedCCalendar);

        restCCalendarMockMvc.perform(put("/api/c-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCalendarDTO)))
            .andExpect(status().isOk());

        // Validate the CCalendar in the database
        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeUpdate);
        CCalendar testCCalendar = cCalendarList.get(cCalendarList.size() - 1);
        assertThat(testCCalendar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCalendar.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCCalendar.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCCalendar.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCalendar() throws Exception {
        int databaseSizeBeforeUpdate = cCalendarRepository.findAll().size();

        // Create the CCalendar
        CCalendarDTO cCalendarDTO = cCalendarMapper.toDto(cCalendar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCalendarMockMvc.perform(put("/api/c-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCalendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCalendar in the database
        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCalendar() throws Exception {
        // Initialize the database
        cCalendarRepository.saveAndFlush(cCalendar);

        int databaseSizeBeforeDelete = cCalendarRepository.findAll().size();

        // Delete the cCalendar
        restCCalendarMockMvc.perform(delete("/api/c-calendars/{id}", cCalendar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCalendar> cCalendarList = cCalendarRepository.findAll();
        assertThat(cCalendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
