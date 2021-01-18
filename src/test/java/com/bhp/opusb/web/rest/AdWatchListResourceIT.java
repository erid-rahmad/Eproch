package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdWatchList;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdWatchListRepository;
import com.bhp.opusb.service.AdWatchListService;
import com.bhp.opusb.service.dto.AdWatchListDTO;
import com.bhp.opusb.service.mapper.AdWatchListMapper;
import com.bhp.opusb.service.dto.AdWatchListCriteria;
import com.bhp.opusb.service.AdWatchListQueryService;

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
 * Integration tests for the {@link AdWatchListResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdWatchListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AdWatchListRepository adWatchListRepository;

    @Autowired
    private AdWatchListMapper adWatchListMapper;

    @Autowired
    private AdWatchListService adWatchListService;

    @Autowired
    private AdWatchListQueryService adWatchListQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdWatchListMockMvc;

    private AdWatchList adWatchList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdWatchList createEntity(EntityManager em) {
        AdWatchList adWatchList = new AdWatchList()
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
        adWatchList.setAdOrganization(aDOrganization);
        return adWatchList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdWatchList createUpdatedEntity(EntityManager em) {
        AdWatchList adWatchList = new AdWatchList()
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
        adWatchList.setAdOrganization(aDOrganization);
        return adWatchList;
    }

    @BeforeEach
    public void initTest() {
        adWatchList = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdWatchList() throws Exception {
        int databaseSizeBeforeCreate = adWatchListRepository.findAll().size();

        // Create the AdWatchList
        AdWatchListDTO adWatchListDTO = adWatchListMapper.toDto(adWatchList);
        restAdWatchListMockMvc.perform(post("/api/ad-watch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListDTO)))
            .andExpect(status().isCreated());

        // Validate the AdWatchList in the database
        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeCreate + 1);
        AdWatchList testAdWatchList = adWatchListList.get(adWatchListList.size() - 1);
        assertThat(testAdWatchList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdWatchList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdWatchList.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdWatchList.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAdWatchListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adWatchListRepository.findAll().size();

        // Create the AdWatchList with an existing ID
        adWatchList.setId(1L);
        AdWatchListDTO adWatchListDTO = adWatchListMapper.toDto(adWatchList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdWatchListMockMvc.perform(post("/api/ad-watch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdWatchList in the database
        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adWatchListRepository.findAll().size();
        // set the field null
        adWatchList.setName(null);

        // Create the AdWatchList, which fails.
        AdWatchListDTO adWatchListDTO = adWatchListMapper.toDto(adWatchList);

        restAdWatchListMockMvc.perform(post("/api/ad-watch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListDTO)))
            .andExpect(status().isBadRequest());

        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdWatchLists() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adWatchList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdWatchList() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get the adWatchList
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists/{id}", adWatchList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adWatchList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdWatchListsByIdFiltering() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        Long id = adWatchList.getId();

        defaultAdWatchListShouldBeFound("id.equals=" + id);
        defaultAdWatchListShouldNotBeFound("id.notEquals=" + id);

        defaultAdWatchListShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdWatchListShouldNotBeFound("id.greaterThan=" + id);

        defaultAdWatchListShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdWatchListShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdWatchListsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name equals to DEFAULT_NAME
        defaultAdWatchListShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adWatchListList where name equals to UPDATED_NAME
        defaultAdWatchListShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name not equals to DEFAULT_NAME
        defaultAdWatchListShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adWatchListList where name not equals to UPDATED_NAME
        defaultAdWatchListShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdWatchListShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adWatchListList where name equals to UPDATED_NAME
        defaultAdWatchListShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name is not null
        defaultAdWatchListShouldBeFound("name.specified=true");

        // Get all the adWatchListList where name is null
        defaultAdWatchListShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListsByNameContainsSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name contains DEFAULT_NAME
        defaultAdWatchListShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adWatchListList where name contains UPDATED_NAME
        defaultAdWatchListShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where name does not contain DEFAULT_NAME
        defaultAdWatchListShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adWatchListList where name does not contain UPDATED_NAME
        defaultAdWatchListShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description equals to DEFAULT_DESCRIPTION
        defaultAdWatchListShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListList where description equals to UPDATED_DESCRIPTION
        defaultAdWatchListShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description not equals to DEFAULT_DESCRIPTION
        defaultAdWatchListShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListList where description not equals to UPDATED_DESCRIPTION
        defaultAdWatchListShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdWatchListShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adWatchListList where description equals to UPDATED_DESCRIPTION
        defaultAdWatchListShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description is not null
        defaultAdWatchListShouldBeFound("description.specified=true");

        // Get all the adWatchListList where description is null
        defaultAdWatchListShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description contains DEFAULT_DESCRIPTION
        defaultAdWatchListShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListList where description contains UPDATED_DESCRIPTION
        defaultAdWatchListShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where description does not contain DEFAULT_DESCRIPTION
        defaultAdWatchListShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListList where description does not contain UPDATED_DESCRIPTION
        defaultAdWatchListShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdWatchListsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where uid equals to DEFAULT_UID
        defaultAdWatchListShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adWatchListList where uid equals to UPDATED_UID
        defaultAdWatchListShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where uid not equals to DEFAULT_UID
        defaultAdWatchListShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adWatchListList where uid not equals to UPDATED_UID
        defaultAdWatchListShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdWatchListShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adWatchListList where uid equals to UPDATED_UID
        defaultAdWatchListShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where uid is not null
        defaultAdWatchListShouldBeFound("uid.specified=true");

        // Get all the adWatchListList where uid is null
        defaultAdWatchListShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where active equals to DEFAULT_ACTIVE
        defaultAdWatchListShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adWatchListList where active equals to UPDATED_ACTIVE
        defaultAdWatchListShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where active not equals to DEFAULT_ACTIVE
        defaultAdWatchListShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adWatchListList where active not equals to UPDATED_ACTIVE
        defaultAdWatchListShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdWatchListShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adWatchListList where active equals to UPDATED_ACTIVE
        defaultAdWatchListShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        // Get all the adWatchListList where active is not null
        defaultAdWatchListShouldBeFound("active.specified=true");

        // Get all the adWatchListList where active is null
        defaultAdWatchListShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdWatchListsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adWatchList.getAdOrganization();
        adWatchListRepository.saveAndFlush(adWatchList);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adWatchListList where adOrganization equals to adOrganizationId
        defaultAdWatchListShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adWatchListList where adOrganization equals to adOrganizationId + 1
        defaultAdWatchListShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdWatchListShouldBeFound(String filter) throws Exception {
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adWatchList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdWatchListShouldNotBeFound(String filter) throws Exception {
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdWatchList() throws Exception {
        // Get the adWatchList
        restAdWatchListMockMvc.perform(get("/api/ad-watch-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdWatchList() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        int databaseSizeBeforeUpdate = adWatchListRepository.findAll().size();

        // Update the adWatchList
        AdWatchList updatedAdWatchList = adWatchListRepository.findById(adWatchList.getId()).get();
        // Disconnect from session so that the updates on updatedAdWatchList are not directly saved in db
        em.detach(updatedAdWatchList);
        updatedAdWatchList
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        AdWatchListDTO adWatchListDTO = adWatchListMapper.toDto(updatedAdWatchList);

        restAdWatchListMockMvc.perform(put("/api/ad-watch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListDTO)))
            .andExpect(status().isOk());

        // Validate the AdWatchList in the database
        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeUpdate);
        AdWatchList testAdWatchList = adWatchListList.get(adWatchListList.size() - 1);
        assertThat(testAdWatchList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdWatchList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdWatchList.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdWatchList.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdWatchList() throws Exception {
        int databaseSizeBeforeUpdate = adWatchListRepository.findAll().size();

        // Create the AdWatchList
        AdWatchListDTO adWatchListDTO = adWatchListMapper.toDto(adWatchList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdWatchListMockMvc.perform(put("/api/ad-watch-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdWatchList in the database
        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdWatchList() throws Exception {
        // Initialize the database
        adWatchListRepository.saveAndFlush(adWatchList);

        int databaseSizeBeforeDelete = adWatchListRepository.findAll().size();

        // Delete the adWatchList
        restAdWatchListMockMvc.perform(delete("/api/ad-watch-lists/{id}", adWatchList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdWatchList> adWatchListList = adWatchListRepository.findAll();
        assertThat(adWatchListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
