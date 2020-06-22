package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.CCountry;
import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.repository.CCityRepository;
import com.bhp.opusb.service.CCityService;
import com.bhp.opusb.service.dto.CCityDTO;
import com.bhp.opusb.service.mapper.CCityMapper;
import com.bhp.opusb.service.dto.CCityCriteria;
import com.bhp.opusb.service.CCityQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CCityResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CCityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CCityRepository cCityRepository;

    @Autowired
    private CCityMapper cCityMapper;

    @Autowired
    private CCityService cCityService;

    @Autowired
    private CCityQueryService cCityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCityMockMvc;

    private CCity cCity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCity createEntity(EntityManager em) {
        CCity cCity = new CCity()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE);
        return cCity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCity createUpdatedEntity(EntityManager em) {
        CCity cCity = new CCity()
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        return cCity;
    }

    @BeforeEach
    public void initTest() {
        cCity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCity() throws Exception {
        int databaseSizeBeforeCreate = cCityRepository.findAll().size();

        // Create the CCity
        CCityDTO cCityDTO = cCityMapper.toDto(cCity);
        restCCityMockMvc.perform(post("/api/c-cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCityDTO)))
            .andExpect(status().isCreated());

        // Validate the CCity in the database
        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeCreate + 1);
        CCity testCCity = cCityList.get(cCityList.size() - 1);
        assertThat(testCCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCity.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCityRepository.findAll().size();

        // Create the CCity with an existing ID
        cCity.setId(1L);
        CCityDTO cCityDTO = cCityMapper.toDto(cCity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCityMockMvc.perform(post("/api/c-cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCity in the database
        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCityRepository.findAll().size();
        // set the field null
        cCity.setName(null);

        // Create the CCity, which fails.
        CCityDTO cCityDTO = cCityMapper.toDto(cCity);

        restCCityMockMvc.perform(post("/api/c-cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCityDTO)))
            .andExpect(status().isBadRequest());

        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCities() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList
        restCCityMockMvc.perform(get("/api/c-cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCCity() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get the cCity
        restCCityMockMvc.perform(get("/api/c-cities/{id}", cCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCCitiesByIdFiltering() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        Long id = cCity.getId();

        defaultCCityShouldBeFound("id.equals=" + id);
        defaultCCityShouldNotBeFound("id.notEquals=" + id);

        defaultCCityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCCityShouldNotBeFound("id.greaterThan=" + id);

        defaultCCityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCCityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCCitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name equals to DEFAULT_NAME
        defaultCCityShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cCityList where name equals to UPDATED_NAME
        defaultCCityShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCitiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name not equals to DEFAULT_NAME
        defaultCCityShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cCityList where name not equals to UPDATED_NAME
        defaultCCityShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCCityShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cCityList where name equals to UPDATED_NAME
        defaultCCityShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name is not null
        defaultCCityShouldBeFound("name.specified=true");

        // Get all the cCityList where name is null
        defaultCCityShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name contains DEFAULT_NAME
        defaultCCityShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cCityList where name contains UPDATED_NAME
        defaultCCityShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where name does not contain DEFAULT_NAME
        defaultCCityShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cCityList where name does not contain UPDATED_NAME
        defaultCCityShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCCitiesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where active equals to DEFAULT_ACTIVE
        defaultCCityShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cCityList where active equals to UPDATED_ACTIVE
        defaultCCityShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where active not equals to DEFAULT_ACTIVE
        defaultCCityShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cCityList where active not equals to UPDATED_ACTIVE
        defaultCCityShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCCityShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cCityList where active equals to UPDATED_ACTIVE
        defaultCCityShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where active is not null
        defaultCCityShouldBeFound("active.specified=true");

        // Get all the cCityList where active is null
        defaultCCityShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCitiesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);
        CCountry country = CCountryResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        cCity.setCountry(country);
        cCityRepository.saveAndFlush(cCity);
        Long countryId = country.getId();

        // Get all the cCityList where country equals to countryId
        defaultCCityShouldBeFound("countryId.equals=" + countryId);

        // Get all the cCityList where country equals to countryId + 1
        defaultCCityShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }


    @Test
    @Transactional
    public void getAllCCitiesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);
        CRegion region = CRegionResourceIT.createEntity(em);
        em.persist(region);
        em.flush();
        cCity.setRegion(region);
        cCityRepository.saveAndFlush(cCity);
        Long regionId = region.getId();

        // Get all the cCityList where region equals to regionId
        defaultCCityShouldBeFound("regionId.equals=" + regionId);

        // Get all the cCityList where region equals to regionId + 1
        defaultCCityShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCCityShouldBeFound(String filter) throws Exception {
        restCCityMockMvc.perform(get("/api/c-cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCCityMockMvc.perform(get("/api/c-cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCCityShouldNotBeFound(String filter) throws Exception {
        restCCityMockMvc.perform(get("/api/c-cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCCityMockMvc.perform(get("/api/c-cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCCity() throws Exception {
        // Get the cCity
        restCCityMockMvc.perform(get("/api/c-cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCity() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        int databaseSizeBeforeUpdate = cCityRepository.findAll().size();

        // Update the cCity
        CCity updatedCCity = cCityRepository.findById(cCity.getId()).get();
        // Disconnect from session so that the updates on updatedCCity are not directly saved in db
        em.detach(updatedCCity);
        updatedCCity
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        CCityDTO cCityDTO = cCityMapper.toDto(updatedCCity);

        restCCityMockMvc.perform(put("/api/c-cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCityDTO)))
            .andExpect(status().isOk());

        // Validate the CCity in the database
        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeUpdate);
        CCity testCCity = cCityList.get(cCityList.size() - 1);
        assertThat(testCCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCity.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCity() throws Exception {
        int databaseSizeBeforeUpdate = cCityRepository.findAll().size();

        // Create the CCity
        CCityDTO cCityDTO = cCityMapper.toDto(cCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCityMockMvc.perform(put("/api/c-cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCity in the database
        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCity() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        int databaseSizeBeforeDelete = cCityRepository.findAll().size();

        // Delete the cCity
        restCCityMockMvc.perform(delete("/api/c-cities/{id}", cCity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCity> cCityList = cCityRepository.findAll();
        assertThat(cCityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
