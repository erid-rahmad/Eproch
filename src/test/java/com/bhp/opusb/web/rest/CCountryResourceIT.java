package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCountry;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CCountryRepository;
import com.bhp.opusb.service.CCountryService;
import com.bhp.opusb.service.dto.CCountryDTO;
import com.bhp.opusb.service.mapper.CCountryMapper;
import com.bhp.opusb.service.dto.CCountryCriteria;
import com.bhp.opusb.service.CCountryQueryService;

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
 * Integration tests for the {@link CCountryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CCountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "ZK";
    private static final String UPDATED_CODE = "NS";

    private static final Boolean DEFAULT_WITH_REGION = false;
    private static final Boolean UPDATED_WITH_REGION = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CCountryRepository cCountryRepository;

    @Autowired
    private CCountryMapper cCountryMapper;

    @Autowired
    private CCountryService cCountryService;

    @Autowired
    private CCountryQueryService cCountryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCountryMockMvc;

    private CCountry cCountry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCountry createEntity(EntityManager em) {
        CCountry cCountry = new CCountry()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .withRegion(DEFAULT_WITH_REGION)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cCountry.setCurrency(cCurrency);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cCountry.setAdOrganization(aDOrganization);
        return cCountry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCountry createUpdatedEntity(EntityManager em) {
        CCountry cCountry = new CCountry()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .withRegion(UPDATED_WITH_REGION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cCountry.setCurrency(cCurrency);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cCountry.setAdOrganization(aDOrganization);
        return cCountry;
    }

    @BeforeEach
    public void initTest() {
        cCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCountry() throws Exception {
        int databaseSizeBeforeCreate = cCountryRepository.findAll().size();

        // Create the CCountry
        CCountryDTO cCountryDTO = cCountryMapper.toDto(cCountry);
        restCCountryMockMvc.perform(post("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the CCountry in the database
        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeCreate + 1);
        CCountry testCCountry = cCountryList.get(cCountryList.size() - 1);
        assertThat(testCCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCountry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCCountry.isWithRegion()).isEqualTo(DEFAULT_WITH_REGION);
        assertThat(testCCountry.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCCountry.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCountryRepository.findAll().size();

        // Create the CCountry with an existing ID
        cCountry.setId(1L);
        CCountryDTO cCountryDTO = cCountryMapper.toDto(cCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCountryMockMvc.perform(post("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCountry in the database
        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCountryRepository.findAll().size();
        // set the field null
        cCountry.setName(null);

        // Create the CCountry, which fails.
        CCountryDTO cCountryDTO = cCountryMapper.toDto(cCountry);

        restCCountryMockMvc.perform(post("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isBadRequest());

        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCountryRepository.findAll().size();
        // set the field null
        cCountry.setCode(null);

        // Create the CCountry, which fails.
        CCountryDTO cCountryDTO = cCountryMapper.toDto(cCountry);

        restCCountryMockMvc.perform(post("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isBadRequest());

        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCountries() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList
        restCCountryMockMvc.perform(get("/api/c-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].withRegion").value(hasItem(DEFAULT_WITH_REGION.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCCountry() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get the cCountry
        restCCountryMockMvc.perform(get("/api/c-countries/{id}", cCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.withRegion").value(DEFAULT_WITH_REGION.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCCountriesByIdFiltering() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        Long id = cCountry.getId();

        defaultCCountryShouldBeFound("id.equals=" + id);
        defaultCCountryShouldNotBeFound("id.notEquals=" + id);

        defaultCCountryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCCountryShouldNotBeFound("id.greaterThan=" + id);

        defaultCCountryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCCountryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name equals to DEFAULT_NAME
        defaultCCountryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cCountryList where name equals to UPDATED_NAME
        defaultCCountryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCountriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name not equals to DEFAULT_NAME
        defaultCCountryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cCountryList where name not equals to UPDATED_NAME
        defaultCCountryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCCountryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cCountryList where name equals to UPDATED_NAME
        defaultCCountryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name is not null
        defaultCCountryShouldBeFound("name.specified=true");

        // Get all the cCountryList where name is null
        defaultCCountryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCountriesByNameContainsSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name contains DEFAULT_NAME
        defaultCCountryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cCountryList where name contains UPDATED_NAME
        defaultCCountryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCountriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where name does not contain DEFAULT_NAME
        defaultCCountryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cCountryList where name does not contain UPDATED_NAME
        defaultCCountryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCCountriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code equals to DEFAULT_CODE
        defaultCCountryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cCountryList where code equals to UPDATED_CODE
        defaultCCountryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code not equals to DEFAULT_CODE
        defaultCCountryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cCountryList where code not equals to UPDATED_CODE
        defaultCCountryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCCountryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cCountryList where code equals to UPDATED_CODE
        defaultCCountryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code is not null
        defaultCCountryShouldBeFound("code.specified=true");

        // Get all the cCountryList where code is null
        defaultCCountryShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCountriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code contains DEFAULT_CODE
        defaultCCountryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cCountryList where code contains UPDATED_CODE
        defaultCCountryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where code does not contain DEFAULT_CODE
        defaultCCountryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cCountryList where code does not contain UPDATED_CODE
        defaultCCountryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCCountriesByWithRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where withRegion equals to DEFAULT_WITH_REGION
        defaultCCountryShouldBeFound("withRegion.equals=" + DEFAULT_WITH_REGION);

        // Get all the cCountryList where withRegion equals to UPDATED_WITH_REGION
        defaultCCountryShouldNotBeFound("withRegion.equals=" + UPDATED_WITH_REGION);
    }

    @Test
    @Transactional
    public void getAllCCountriesByWithRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where withRegion not equals to DEFAULT_WITH_REGION
        defaultCCountryShouldNotBeFound("withRegion.notEquals=" + DEFAULT_WITH_REGION);

        // Get all the cCountryList where withRegion not equals to UPDATED_WITH_REGION
        defaultCCountryShouldBeFound("withRegion.notEquals=" + UPDATED_WITH_REGION);
    }

    @Test
    @Transactional
    public void getAllCCountriesByWithRegionIsInShouldWork() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where withRegion in DEFAULT_WITH_REGION or UPDATED_WITH_REGION
        defaultCCountryShouldBeFound("withRegion.in=" + DEFAULT_WITH_REGION + "," + UPDATED_WITH_REGION);

        // Get all the cCountryList where withRegion equals to UPDATED_WITH_REGION
        defaultCCountryShouldNotBeFound("withRegion.in=" + UPDATED_WITH_REGION);
    }

    @Test
    @Transactional
    public void getAllCCountriesByWithRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where withRegion is not null
        defaultCCountryShouldBeFound("withRegion.specified=true");

        // Get all the cCountryList where withRegion is null
        defaultCCountryShouldNotBeFound("withRegion.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCountriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where uid equals to DEFAULT_UID
        defaultCCountryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cCountryList where uid equals to UPDATED_UID
        defaultCCountryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCountriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where uid not equals to DEFAULT_UID
        defaultCCountryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cCountryList where uid not equals to UPDATED_UID
        defaultCCountryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCountriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCCountryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cCountryList where uid equals to UPDATED_UID
        defaultCCountryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCCountriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where uid is not null
        defaultCCountryShouldBeFound("uid.specified=true");

        // Get all the cCountryList where uid is null
        defaultCCountryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCountriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where active equals to DEFAULT_ACTIVE
        defaultCCountryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cCountryList where active equals to UPDATED_ACTIVE
        defaultCCountryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where active not equals to DEFAULT_ACTIVE
        defaultCCountryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cCountryList where active not equals to UPDATED_ACTIVE
        defaultCCountryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCCountryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cCountryList where active equals to UPDATED_ACTIVE
        defaultCCountryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCountriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        // Get all the cCountryList where active is not null
        defaultCCountryShouldBeFound("active.specified=true");

        // Get all the cCountryList where active is null
        defaultCCountryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCCountriesByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = cCountry.getCurrency();
        cCountryRepository.saveAndFlush(cCountry);
        Long currencyId = currency.getId();

        // Get all the cCountryList where currency equals to currencyId
        defaultCCountryShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the cCountryList where currency equals to currencyId + 1
        defaultCCountryShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCCountriesByCRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);
        CRegion cRegion = CRegionResourceIT.createEntity(em);
        em.persist(cRegion);
        em.flush();
        cCountry.addCRegion(cRegion);
        cCountryRepository.saveAndFlush(cCountry);
        Long cRegionId = cRegion.getId();

        // Get all the cCountryList where cRegion equals to cRegionId
        defaultCCountryShouldBeFound("cRegionId.equals=" + cRegionId);

        // Get all the cCountryList where cRegion equals to cRegionId + 1
        defaultCCountryShouldNotBeFound("cRegionId.equals=" + (cRegionId + 1));
    }


    @Test
    @Transactional
    public void getAllCCountriesByCCityIsEqualToSomething() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);
        CCity cCity = CCityResourceIT.createEntity(em);
        em.persist(cCity);
        em.flush();
        cCountry.addCCity(cCity);
        cCountryRepository.saveAndFlush(cCountry);
        Long cCityId = cCity.getId();

        // Get all the cCountryList where cCity equals to cCityId
        defaultCCountryShouldBeFound("cCityId.equals=" + cCityId);

        // Get all the cCountryList where cCity equals to cCityId + 1
        defaultCCountryShouldNotBeFound("cCityId.equals=" + (cCityId + 1));
    }


    @Test
    @Transactional
    public void getAllCCountriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cCountry.getAdOrganization();
        cCountryRepository.saveAndFlush(cCountry);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cCountryList where adOrganization equals to adOrganizationId
        defaultCCountryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cCountryList where adOrganization equals to adOrganizationId + 1
        defaultCCountryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCCountryShouldBeFound(String filter) throws Exception {
        restCCountryMockMvc.perform(get("/api/c-countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].withRegion").value(hasItem(DEFAULT_WITH_REGION.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCCountryMockMvc.perform(get("/api/c-countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCCountryShouldNotBeFound(String filter) throws Exception {
        restCCountryMockMvc.perform(get("/api/c-countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCCountryMockMvc.perform(get("/api/c-countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCCountry() throws Exception {
        // Get the cCountry
        restCCountryMockMvc.perform(get("/api/c-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCountry() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        int databaseSizeBeforeUpdate = cCountryRepository.findAll().size();

        // Update the cCountry
        CCountry updatedCCountry = cCountryRepository.findById(cCountry.getId()).get();
        // Disconnect from session so that the updates on updatedCCountry are not directly saved in db
        em.detach(updatedCCountry);
        updatedCCountry
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .withRegion(UPDATED_WITH_REGION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CCountryDTO cCountryDTO = cCountryMapper.toDto(updatedCCountry);

        restCCountryMockMvc.perform(put("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isOk());

        // Validate the CCountry in the database
        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeUpdate);
        CCountry testCCountry = cCountryList.get(cCountryList.size() - 1);
        assertThat(testCCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCCountry.isWithRegion()).isEqualTo(UPDATED_WITH_REGION);
        assertThat(testCCountry.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCCountry.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCountry() throws Exception {
        int databaseSizeBeforeUpdate = cCountryRepository.findAll().size();

        // Create the CCountry
        CCountryDTO cCountryDTO = cCountryMapper.toDto(cCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCountryMockMvc.perform(put("/api/c-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCountry in the database
        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCountry() throws Exception {
        // Initialize the database
        cCountryRepository.saveAndFlush(cCountry);

        int databaseSizeBeforeDelete = cCountryRepository.findAll().size();

        // Delete the cCountry
        restCCountryMockMvc.perform(delete("/api/c-countries/{id}", cCountry.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCountry> cCountryList = cCountryRepository.findAll();
        assertThat(cCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
