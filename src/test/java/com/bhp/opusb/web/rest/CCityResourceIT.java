package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.ADOrganization;
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
import java.util.UUID;

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

    private static final String DEFAULT_CODE = "HRU";
    private static final String UPDATED_CODE = "DLE";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

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
            .code(DEFAULT_CODE)
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
        cCity.setAdOrganization(aDOrganization);
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
            .code(UPDATED_CODE)
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
        cCity.setAdOrganization(aDOrganization);
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
        assertThat(testCCity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCity.getUid()).isEqualTo(DEFAULT_UID);
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
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCityRepository.findAll().size();
        // set the field null
        cCity.setCode(null);

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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
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
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
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
    public void getAllCCitiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code equals to DEFAULT_CODE
        defaultCCityShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cCityList where code equals to UPDATED_CODE
        defaultCCityShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code not equals to DEFAULT_CODE
        defaultCCityShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cCityList where code not equals to UPDATED_CODE
        defaultCCityShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCCityShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cCityList where code equals to UPDATED_CODE
        defaultCCityShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code is not null
        defaultCCityShouldBeFound("code.specified=true");

        // Get all the cCityList where code is null
        defaultCCityShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCitiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code contains DEFAULT_CODE
        defaultCCityShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cCityList where code contains UPDATED_CODE
        defaultCCityShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCitiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where code does not contain DEFAULT_CODE
        defaultCCityShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cCityList where code does not contain UPDATED_CODE
        defaultCCityShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
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
    public void getAllCCitiesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where uid equals to DEFAULT_UID
        defaultCCityShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cCityList where uid equals to UPDATED_UID
        defaultCCityShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCitiesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where uid not equals to DEFAULT_UID
        defaultCCityShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cCityList where uid not equals to UPDATED_UID
        defaultCCityShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCitiesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where uid in DEFAULT_UID or UPDATED_UID
        defaultCCityShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cCityList where uid equals to UPDATED_UID
        defaultCCityShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCitiesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCityRepository.saveAndFlush(cCity);

        // Get all the cCityList where uid is not null
        defaultCCityShouldBeFound("uid.specified=true");

        // Get all the cCityList where uid is null
        defaultCCityShouldNotBeFound("uid.specified=false");
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
    public void getAllCCitiesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cCity.getAdOrganization();
        cCityRepository.saveAndFlush(cCity);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cCityList where adOrganization equals to adOrganizationId
        defaultCCityShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cCityList where adOrganization equals to adOrganizationId + 1
        defaultCCityShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
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
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
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
        assertThat(testCCity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCity.getUid()).isEqualTo(UPDATED_UID);
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
