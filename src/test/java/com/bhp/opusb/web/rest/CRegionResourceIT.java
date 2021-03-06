package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCountry;
import com.bhp.opusb.repository.CRegionRepository;
import com.bhp.opusb.service.CRegionService;
import com.bhp.opusb.service.dto.CRegionDTO;
import com.bhp.opusb.service.mapper.CRegionMapper;
import com.bhp.opusb.service.dto.CRegionCriteria;
import com.bhp.opusb.service.CRegionQueryService;

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
 * Integration tests for the {@link CRegionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CRegionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CRegionRepository cRegionRepository;

    @Autowired
    private CRegionMapper cRegionMapper;

    @Autowired
    private CRegionService cRegionService;

    @Autowired
    private CRegionQueryService cRegionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCRegionMockMvc;

    private CRegion cRegion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegion createEntity(EntityManager em) {
        CRegion cRegion = new CRegion()
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
        cRegion.setAdOrganization(aDOrganization);
        // Add required entity
        CCountry cCountry;
        if (TestUtil.findAll(em, CCountry.class).isEmpty()) {
            cCountry = CCountryResourceIT.createEntity(em);
            em.persist(cCountry);
            em.flush();
        } else {
            cCountry = TestUtil.findAll(em, CCountry.class).get(0);
        }
        cRegion.setCountry(cCountry);
        return cRegion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegion createUpdatedEntity(EntityManager em) {
        CRegion cRegion = new CRegion()
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
        cRegion.setAdOrganization(aDOrganization);
        // Add required entity
        CCountry cCountry;
        if (TestUtil.findAll(em, CCountry.class).isEmpty()) {
            cCountry = CCountryResourceIT.createUpdatedEntity(em);
            em.persist(cCountry);
            em.flush();
        } else {
            cCountry = TestUtil.findAll(em, CCountry.class).get(0);
        }
        cRegion.setCountry(cCountry);
        return cRegion;
    }

    @BeforeEach
    public void initTest() {
        cRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRegion() throws Exception {
        int databaseSizeBeforeCreate = cRegionRepository.findAll().size();

        // Create the CRegion
        CRegionDTO cRegionDTO = cRegionMapper.toDto(cRegion);
        restCRegionMockMvc.perform(post("/api/c-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegionDTO)))
            .andExpect(status().isCreated());

        // Validate the CRegion in the database
        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeCreate + 1);
        CRegion testCRegion = cRegionList.get(cRegionList.size() - 1);
        assertThat(testCRegion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCRegion.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCRegion.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRegionRepository.findAll().size();

        // Create the CRegion with an existing ID
        cRegion.setId(1L);
        CRegionDTO cRegionDTO = cRegionMapper.toDto(cRegion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRegionMockMvc.perform(post("/api/c-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegion in the database
        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRegionRepository.findAll().size();
        // set the field null
        cRegion.setName(null);

        // Create the CRegion, which fails.
        CRegionDTO cRegionDTO = cRegionMapper.toDto(cRegion);

        restCRegionMockMvc.perform(post("/api/c-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegionDTO)))
            .andExpect(status().isBadRequest());

        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCRegions() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList
        restCRegionMockMvc.perform(get("/api/c-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCRegion() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get the cRegion
        restCRegionMockMvc.perform(get("/api/c-regions/{id}", cRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cRegion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCRegionsByIdFiltering() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        Long id = cRegion.getId();

        defaultCRegionShouldBeFound("id.equals=" + id);
        defaultCRegionShouldNotBeFound("id.notEquals=" + id);

        defaultCRegionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCRegionShouldNotBeFound("id.greaterThan=" + id);

        defaultCRegionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCRegionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCRegionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name equals to DEFAULT_NAME
        defaultCRegionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cRegionList where name equals to UPDATED_NAME
        defaultCRegionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name not equals to DEFAULT_NAME
        defaultCRegionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cRegionList where name not equals to UPDATED_NAME
        defaultCRegionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCRegionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cRegionList where name equals to UPDATED_NAME
        defaultCRegionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name is not null
        defaultCRegionShouldBeFound("name.specified=true");

        // Get all the cRegionList where name is null
        defaultCRegionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCRegionsByNameContainsSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name contains DEFAULT_NAME
        defaultCRegionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cRegionList where name contains UPDATED_NAME
        defaultCRegionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCRegionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where name does not contain DEFAULT_NAME
        defaultCRegionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cRegionList where name does not contain UPDATED_NAME
        defaultCRegionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCRegionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description equals to DEFAULT_DESCRIPTION
        defaultCRegionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cRegionList where description equals to UPDATED_DESCRIPTION
        defaultCRegionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description not equals to DEFAULT_DESCRIPTION
        defaultCRegionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cRegionList where description not equals to UPDATED_DESCRIPTION
        defaultCRegionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCRegionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cRegionList where description equals to UPDATED_DESCRIPTION
        defaultCRegionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description is not null
        defaultCRegionShouldBeFound("description.specified=true");

        // Get all the cRegionList where description is null
        defaultCRegionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCRegionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description contains DEFAULT_DESCRIPTION
        defaultCRegionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cRegionList where description contains UPDATED_DESCRIPTION
        defaultCRegionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCRegionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where description does not contain DEFAULT_DESCRIPTION
        defaultCRegionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cRegionList where description does not contain UPDATED_DESCRIPTION
        defaultCRegionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCRegionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where uid equals to DEFAULT_UID
        defaultCRegionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cRegionList where uid equals to UPDATED_UID
        defaultCRegionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where uid not equals to DEFAULT_UID
        defaultCRegionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cRegionList where uid not equals to UPDATED_UID
        defaultCRegionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where uid in DEFAULT_UID or UPDATED_UID
        defaultCRegionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cRegionList where uid equals to UPDATED_UID
        defaultCRegionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where uid is not null
        defaultCRegionShouldBeFound("uid.specified=true");

        // Get all the cRegionList where uid is null
        defaultCRegionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where active equals to DEFAULT_ACTIVE
        defaultCRegionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cRegionList where active equals to UPDATED_ACTIVE
        defaultCRegionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where active not equals to DEFAULT_ACTIVE
        defaultCRegionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cRegionList where active not equals to UPDATED_ACTIVE
        defaultCRegionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCRegionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cRegionList where active equals to UPDATED_ACTIVE
        defaultCRegionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        // Get all the cRegionList where active is not null
        defaultCRegionShouldBeFound("active.specified=true");

        // Get all the cRegionList where active is null
        defaultCRegionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegionsByCCityIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);
        CCity cCity = CCityResourceIT.createEntity(em);
        em.persist(cCity);
        em.flush();
        cRegion.addCCity(cCity);
        cRegionRepository.saveAndFlush(cRegion);
        Long cCityId = cCity.getId();

        // Get all the cRegionList where cCity equals to cCityId
        defaultCRegionShouldBeFound("cCityId.equals=" + cCityId);

        // Get all the cRegionList where cCity equals to cCityId + 1
        defaultCRegionShouldNotBeFound("cCityId.equals=" + (cCityId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cRegion.getAdOrganization();
        cRegionRepository.saveAndFlush(cRegion);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cRegionList where adOrganization equals to adOrganizationId
        defaultCRegionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cRegionList where adOrganization equals to adOrganizationId + 1
        defaultCRegionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegionsByCountryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCountry country = cRegion.getCountry();
        cRegionRepository.saveAndFlush(cRegion);
        Long countryId = country.getId();

        // Get all the cRegionList where country equals to countryId
        defaultCRegionShouldBeFound("countryId.equals=" + countryId);

        // Get all the cRegionList where country equals to countryId + 1
        defaultCRegionShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCRegionShouldBeFound(String filter) throws Exception {
        restCRegionMockMvc.perform(get("/api/c-regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCRegionMockMvc.perform(get("/api/c-regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCRegionShouldNotBeFound(String filter) throws Exception {
        restCRegionMockMvc.perform(get("/api/c-regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCRegionMockMvc.perform(get("/api/c-regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCRegion() throws Exception {
        // Get the cRegion
        restCRegionMockMvc.perform(get("/api/c-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRegion() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        int databaseSizeBeforeUpdate = cRegionRepository.findAll().size();

        // Update the cRegion
        CRegion updatedCRegion = cRegionRepository.findById(cRegion.getId()).get();
        // Disconnect from session so that the updates on updatedCRegion are not directly saved in db
        em.detach(updatedCRegion);
        updatedCRegion
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CRegionDTO cRegionDTO = cRegionMapper.toDto(updatedCRegion);

        restCRegionMockMvc.perform(put("/api/c-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegionDTO)))
            .andExpect(status().isOk());

        // Validate the CRegion in the database
        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeUpdate);
        CRegion testCRegion = cRegionList.get(cRegionList.size() - 1);
        assertThat(testCRegion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCRegion.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCRegion.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCRegion() throws Exception {
        int databaseSizeBeforeUpdate = cRegionRepository.findAll().size();

        // Create the CRegion
        CRegionDTO cRegionDTO = cRegionMapper.toDto(cRegion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRegionMockMvc.perform(put("/api/c-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegion in the database
        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCRegion() throws Exception {
        // Initialize the database
        cRegionRepository.saveAndFlush(cRegion);

        int databaseSizeBeforeDelete = cRegionRepository.findAll().size();

        // Delete the cRegion
        restCRegionMockMvc.perform(delete("/api/c-regions/{id}", cRegion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRegion> cRegionList = cRegionRepository.findAll();
        assertThat(cRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
