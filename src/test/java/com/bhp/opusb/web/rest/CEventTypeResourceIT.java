package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEventType;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.repository.CEventTypeRepository;
import com.bhp.opusb.service.CEventTypeService;
import com.bhp.opusb.service.dto.CEventTypeDTO;
import com.bhp.opusb.service.mapper.CEventTypeMapper;
import com.bhp.opusb.service.dto.CEventTypeCriteria;
import com.bhp.opusb.service.CEventTypeQueryService;

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
 * Integration tests for the {@link CEventTypeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEventTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEventTypeRepository cEventTypeRepository;

    @Autowired
    private CEventTypeMapper cEventTypeMapper;

    @Autowired
    private CEventTypeService cEventTypeService;

    @Autowired
    private CEventTypeQueryService cEventTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEventTypeMockMvc;

    private CEventType cEventType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEventType createEntity(EntityManager em) {
        CEventType cEventType = new CEventType()
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
        cEventType.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingType cBiddingType;
        if (TestUtil.findAll(em, CBiddingType.class).isEmpty()) {
            cBiddingType = CBiddingTypeResourceIT.createEntity(em);
            em.persist(cBiddingType);
            em.flush();
        } else {
            cBiddingType = TestUtil.findAll(em, CBiddingType.class).get(0);
        }
        cEventType.setBindingType(cBiddingType);
        return cEventType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEventType createUpdatedEntity(EntityManager em) {
        CEventType cEventType = new CEventType()
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
        cEventType.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingType cBiddingType;
        if (TestUtil.findAll(em, CBiddingType.class).isEmpty()) {
            cBiddingType = CBiddingTypeResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingType);
            em.flush();
        } else {
            cBiddingType = TestUtil.findAll(em, CBiddingType.class).get(0);
        }
        cEventType.setBindingType(cBiddingType);
        return cEventType;
    }

    @BeforeEach
    public void initTest() {
        cEventType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEventType() throws Exception {
        int databaseSizeBeforeCreate = cEventTypeRepository.findAll().size();

        // Create the CEventType
        CEventTypeDTO cEventTypeDTO = cEventTypeMapper.toDto(cEventType);
        restCEventTypeMockMvc.perform(post("/api/c-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CEventType in the database
        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CEventType testCEventType = cEventTypeList.get(cEventTypeList.size() - 1);
        assertThat(testCEventType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCEventType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCEventType.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEventType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEventTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEventTypeRepository.findAll().size();

        // Create the CEventType with an existing ID
        cEventType.setId(1L);
        CEventTypeDTO cEventTypeDTO = cEventTypeMapper.toDto(cEventType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEventTypeMockMvc.perform(post("/api/c-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEventType in the database
        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEventTypeRepository.findAll().size();
        // set the field null
        cEventType.setName(null);

        // Create the CEventType, which fails.
        CEventTypeDTO cEventTypeDTO = cEventTypeMapper.toDto(cEventType);

        restCEventTypeMockMvc.perform(post("/api/c-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEventTypes() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList
        restCEventTypeMockMvc.perform(get("/api/c-event-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEventType() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get the cEventType
        restCEventTypeMockMvc.perform(get("/api/c-event-types/{id}", cEventType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEventType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEventTypesByIdFiltering() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        Long id = cEventType.getId();

        defaultCEventTypeShouldBeFound("id.equals=" + id);
        defaultCEventTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCEventTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEventTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCEventTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEventTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEventTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name equals to DEFAULT_NAME
        defaultCEventTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cEventTypeList where name equals to UPDATED_NAME
        defaultCEventTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name not equals to DEFAULT_NAME
        defaultCEventTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cEventTypeList where name not equals to UPDATED_NAME
        defaultCEventTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCEventTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cEventTypeList where name equals to UPDATED_NAME
        defaultCEventTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name is not null
        defaultCEventTypeShouldBeFound("name.specified=true");

        // Get all the cEventTypeList where name is null
        defaultCEventTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name contains DEFAULT_NAME
        defaultCEventTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cEventTypeList where name contains UPDATED_NAME
        defaultCEventTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where name does not contain DEFAULT_NAME
        defaultCEventTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cEventTypeList where name does not contain UPDATED_NAME
        defaultCEventTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCEventTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCEventTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypeList where description equals to UPDATED_DESCRIPTION
        defaultCEventTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCEventTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCEventTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCEventTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cEventTypeList where description equals to UPDATED_DESCRIPTION
        defaultCEventTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description is not null
        defaultCEventTypeShouldBeFound("description.specified=true");

        // Get all the cEventTypeList where description is null
        defaultCEventTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEventTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description contains DEFAULT_DESCRIPTION
        defaultCEventTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypeList where description contains UPDATED_DESCRIPTION
        defaultCEventTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCEventTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cEventTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCEventTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCEventTypesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where uid equals to DEFAULT_UID
        defaultCEventTypeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEventTypeList where uid equals to UPDATED_UID
        defaultCEventTypeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where uid not equals to DEFAULT_UID
        defaultCEventTypeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEventTypeList where uid not equals to UPDATED_UID
        defaultCEventTypeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEventTypeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEventTypeList where uid equals to UPDATED_UID
        defaultCEventTypeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where uid is not null
        defaultCEventTypeShouldBeFound("uid.specified=true");

        // Get all the cEventTypeList where uid is null
        defaultCEventTypeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where active equals to DEFAULT_ACTIVE
        defaultCEventTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEventTypeList where active equals to UPDATED_ACTIVE
        defaultCEventTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where active not equals to DEFAULT_ACTIVE
        defaultCEventTypeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEventTypeList where active not equals to UPDATED_ACTIVE
        defaultCEventTypeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEventTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEventTypeList where active equals to UPDATED_ACTIVE
        defaultCEventTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEventTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        // Get all the cEventTypeList where active is not null
        defaultCEventTypeShouldBeFound("active.specified=true");

        // Get all the cEventTypeList where active is null
        defaultCEventTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEventTypesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEventType.getAdOrganization();
        cEventTypeRepository.saveAndFlush(cEventType);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEventTypeList where adOrganization equals to adOrganizationId
        defaultCEventTypeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEventTypeList where adOrganization equals to adOrganizationId + 1
        defaultCEventTypeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEventTypesByBindingTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingType bindingType = cEventType.getBindingType();
        cEventTypeRepository.saveAndFlush(cEventType);
        Long bindingTypeId = bindingType.getId();

        // Get all the cEventTypeList where bindingType equals to bindingTypeId
        defaultCEventTypeShouldBeFound("bindingTypeId.equals=" + bindingTypeId);

        // Get all the cEventTypeList where bindingType equals to bindingTypeId + 1
        defaultCEventTypeShouldNotBeFound("bindingTypeId.equals=" + (bindingTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEventTypeShouldBeFound(String filter) throws Exception {
        restCEventTypeMockMvc.perform(get("/api/c-event-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEventTypeMockMvc.perform(get("/api/c-event-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEventTypeShouldNotBeFound(String filter) throws Exception {
        restCEventTypeMockMvc.perform(get("/api/c-event-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEventTypeMockMvc.perform(get("/api/c-event-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEventType() throws Exception {
        // Get the cEventType
        restCEventTypeMockMvc.perform(get("/api/c-event-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEventType() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        int databaseSizeBeforeUpdate = cEventTypeRepository.findAll().size();

        // Update the cEventType
        CEventType updatedCEventType = cEventTypeRepository.findById(cEventType.getId()).get();
        // Disconnect from session so that the updates on updatedCEventType are not directly saved in db
        em.detach(updatedCEventType);
        updatedCEventType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEventTypeDTO cEventTypeDTO = cEventTypeMapper.toDto(updatedCEventType);

        restCEventTypeMockMvc.perform(put("/api/c-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CEventType in the database
        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeUpdate);
        CEventType testCEventType = cEventTypeList.get(cEventTypeList.size() - 1);
        assertThat(testCEventType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCEventType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCEventType.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEventType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEventType() throws Exception {
        int databaseSizeBeforeUpdate = cEventTypeRepository.findAll().size();

        // Create the CEventType
        CEventTypeDTO cEventTypeDTO = cEventTypeMapper.toDto(cEventType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEventTypeMockMvc.perform(put("/api/c-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEventTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEventType in the database
        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEventType() throws Exception {
        // Initialize the database
        cEventTypeRepository.saveAndFlush(cEventType);

        int databaseSizeBeforeDelete = cEventTypeRepository.findAll().size();

        // Delete the cEventType
        restCEventTypeMockMvc.perform(delete("/api/c-event-types/{id}", cEventType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEventType> cEventTypeList = cEventTypeRepository.findAll();
        assertThat(cEventTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
