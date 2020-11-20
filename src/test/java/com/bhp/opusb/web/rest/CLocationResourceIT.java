package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CLocationRepository;
import com.bhp.opusb.service.CLocationService;
import com.bhp.opusb.service.dto.CLocationDTO;
import com.bhp.opusb.service.mapper.CLocationMapper;
import com.bhp.opusb.service.dto.CLocationCriteria;
import com.bhp.opusb.service.CLocationQueryService;

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
 * Integration tests for the {@link CLocationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CLocationResourceIT {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CLocationRepository cLocationRepository;

    @Autowired
    private CLocationMapper cLocationMapper;

    @Autowired
    private CLocationService cLocationService;

    @Autowired
    private CLocationQueryService cLocationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCLocationMockMvc;

    private CLocation cLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLocation createEntity(EntityManager em) {
        CLocation cLocation = new CLocation()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .postalCode(DEFAULT_POSTAL_CODE)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CCity cCity;
        if (TestUtil.findAll(em, CCity.class).isEmpty()) {
            cCity = CCityResourceIT.createEntity(em);
            em.persist(cCity);
            em.flush();
        } else {
            cCity = TestUtil.findAll(em, CCity.class).get(0);
        }
        cLocation.setCity(cCity);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cLocation.setAdOrganization(aDOrganization);
        return cLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLocation createUpdatedEntity(EntityManager em) {
        CLocation cLocation = new CLocation()
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .postalCode(UPDATED_POSTAL_CODE)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CCity cCity;
        if (TestUtil.findAll(em, CCity.class).isEmpty()) {
            cCity = CCityResourceIT.createUpdatedEntity(em);
            em.persist(cCity);
            em.flush();
        } else {
            cCity = TestUtil.findAll(em, CCity.class).get(0);
        }
        cLocation.setCity(cCity);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cLocation.setAdOrganization(aDOrganization);
        return cLocation;
    }

    @BeforeEach
    public void initTest() {
        cLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCLocation() throws Exception {
        int databaseSizeBeforeCreate = cLocationRepository.findAll().size();

        // Create the CLocation
        CLocationDTO cLocationDTO = cLocationMapper.toDto(cLocation);
        restCLocationMockMvc.perform(post("/api/c-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the CLocation in the database
        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CLocation testCLocation = cLocationList.get(cLocationList.size() - 1);
        assertThat(testCLocation.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCLocation.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCLocation.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testCLocation.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testCLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCLocation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCLocation.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCLocation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCLocation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cLocationRepository.findAll().size();

        // Create the CLocation with an existing ID
        cLocation.setId(1L);
        CLocationDTO cLocationDTO = cLocationMapper.toDto(cLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCLocationMockMvc.perform(post("/api/c-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLocation in the database
        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = cLocationRepository.findAll().size();
        // set the field null
        cLocation.setAddress1(null);

        // Create the CLocation, which fails.
        CLocationDTO cLocationDTO = cLocationMapper.toDto(cLocation);

        restCLocationMockMvc.perform(post("/api/c-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocationDTO)))
            .andExpect(status().isBadRequest());

        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCLocations() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList
        restCLocationMockMvc.perform(get("/api/c-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCLocation() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get the cLocation
        restCLocationMockMvc.perform(get("/api/c-locations/{id}", cLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cLocation.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3))
            .andExpect(jsonPath("$.address4").value(DEFAULT_ADDRESS_4))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCLocationsByIdFiltering() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        Long id = cLocation.getId();

        defaultCLocationShouldBeFound("id.equals=" + id);
        defaultCLocationShouldNotBeFound("id.notEquals=" + id);

        defaultCLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultCLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCLocationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCLocationsByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 equals to DEFAULT_ADDRESS_1
        defaultCLocationShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the cLocationList where address1 equals to UPDATED_ADDRESS_1
        defaultCLocationShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 not equals to DEFAULT_ADDRESS_1
        defaultCLocationShouldNotBeFound("address1.notEquals=" + DEFAULT_ADDRESS_1);

        // Get all the cLocationList where address1 not equals to UPDATED_ADDRESS_1
        defaultCLocationShouldBeFound("address1.notEquals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultCLocationShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the cLocationList where address1 equals to UPDATED_ADDRESS_1
        defaultCLocationShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 is not null
        defaultCLocationShouldBeFound("address1.specified=true");

        // Get all the cLocationList where address1 is null
        defaultCLocationShouldNotBeFound("address1.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 contains DEFAULT_ADDRESS_1
        defaultCLocationShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the cLocationList where address1 contains UPDATED_ADDRESS_1
        defaultCLocationShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address1 does not contain DEFAULT_ADDRESS_1
        defaultCLocationShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the cLocationList where address1 does not contain UPDATED_ADDRESS_1
        defaultCLocationShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }


    @Test
    @Transactional
    public void getAllCLocationsByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 equals to DEFAULT_ADDRESS_2
        defaultCLocationShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the cLocationList where address2 equals to UPDATED_ADDRESS_2
        defaultCLocationShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 not equals to DEFAULT_ADDRESS_2
        defaultCLocationShouldNotBeFound("address2.notEquals=" + DEFAULT_ADDRESS_2);

        // Get all the cLocationList where address2 not equals to UPDATED_ADDRESS_2
        defaultCLocationShouldBeFound("address2.notEquals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultCLocationShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the cLocationList where address2 equals to UPDATED_ADDRESS_2
        defaultCLocationShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 is not null
        defaultCLocationShouldBeFound("address2.specified=true");

        // Get all the cLocationList where address2 is null
        defaultCLocationShouldNotBeFound("address2.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 contains DEFAULT_ADDRESS_2
        defaultCLocationShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the cLocationList where address2 contains UPDATED_ADDRESS_2
        defaultCLocationShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address2 does not contain DEFAULT_ADDRESS_2
        defaultCLocationShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the cLocationList where address2 does not contain UPDATED_ADDRESS_2
        defaultCLocationShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }


    @Test
    @Transactional
    public void getAllCLocationsByAddress3IsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 equals to DEFAULT_ADDRESS_3
        defaultCLocationShouldBeFound("address3.equals=" + DEFAULT_ADDRESS_3);

        // Get all the cLocationList where address3 equals to UPDATED_ADDRESS_3
        defaultCLocationShouldNotBeFound("address3.equals=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 not equals to DEFAULT_ADDRESS_3
        defaultCLocationShouldNotBeFound("address3.notEquals=" + DEFAULT_ADDRESS_3);

        // Get all the cLocationList where address3 not equals to UPDATED_ADDRESS_3
        defaultCLocationShouldBeFound("address3.notEquals=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress3IsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 in DEFAULT_ADDRESS_3 or UPDATED_ADDRESS_3
        defaultCLocationShouldBeFound("address3.in=" + DEFAULT_ADDRESS_3 + "," + UPDATED_ADDRESS_3);

        // Get all the cLocationList where address3 equals to UPDATED_ADDRESS_3
        defaultCLocationShouldNotBeFound("address3.in=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress3IsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 is not null
        defaultCLocationShouldBeFound("address3.specified=true");

        // Get all the cLocationList where address3 is null
        defaultCLocationShouldNotBeFound("address3.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByAddress3ContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 contains DEFAULT_ADDRESS_3
        defaultCLocationShouldBeFound("address3.contains=" + DEFAULT_ADDRESS_3);

        // Get all the cLocationList where address3 contains UPDATED_ADDRESS_3
        defaultCLocationShouldNotBeFound("address3.contains=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress3NotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address3 does not contain DEFAULT_ADDRESS_3
        defaultCLocationShouldNotBeFound("address3.doesNotContain=" + DEFAULT_ADDRESS_3);

        // Get all the cLocationList where address3 does not contain UPDATED_ADDRESS_3
        defaultCLocationShouldBeFound("address3.doesNotContain=" + UPDATED_ADDRESS_3);
    }


    @Test
    @Transactional
    public void getAllCLocationsByAddress4IsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 equals to DEFAULT_ADDRESS_4
        defaultCLocationShouldBeFound("address4.equals=" + DEFAULT_ADDRESS_4);

        // Get all the cLocationList where address4 equals to UPDATED_ADDRESS_4
        defaultCLocationShouldNotBeFound("address4.equals=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 not equals to DEFAULT_ADDRESS_4
        defaultCLocationShouldNotBeFound("address4.notEquals=" + DEFAULT_ADDRESS_4);

        // Get all the cLocationList where address4 not equals to UPDATED_ADDRESS_4
        defaultCLocationShouldBeFound("address4.notEquals=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress4IsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 in DEFAULT_ADDRESS_4 or UPDATED_ADDRESS_4
        defaultCLocationShouldBeFound("address4.in=" + DEFAULT_ADDRESS_4 + "," + UPDATED_ADDRESS_4);

        // Get all the cLocationList where address4 equals to UPDATED_ADDRESS_4
        defaultCLocationShouldNotBeFound("address4.in=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress4IsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 is not null
        defaultCLocationShouldBeFound("address4.specified=true");

        // Get all the cLocationList where address4 is null
        defaultCLocationShouldNotBeFound("address4.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByAddress4ContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 contains DEFAULT_ADDRESS_4
        defaultCLocationShouldBeFound("address4.contains=" + DEFAULT_ADDRESS_4);

        // Get all the cLocationList where address4 contains UPDATED_ADDRESS_4
        defaultCLocationShouldNotBeFound("address4.contains=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    public void getAllCLocationsByAddress4NotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where address4 does not contain DEFAULT_ADDRESS_4
        defaultCLocationShouldNotBeFound("address4.doesNotContain=" + DEFAULT_ADDRESS_4);

        // Get all the cLocationList where address4 does not contain UPDATED_ADDRESS_4
        defaultCLocationShouldBeFound("address4.doesNotContain=" + UPDATED_ADDRESS_4);
    }


    @Test
    @Transactional
    public void getAllCLocationsByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultCLocationShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the cLocationList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCLocationShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultCLocationShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the cLocationList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultCLocationShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultCLocationShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the cLocationList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCLocationShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode is not null
        defaultCLocationShouldBeFound("postalCode.specified=true");

        // Get all the cLocationList where postalCode is null
        defaultCLocationShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode contains DEFAULT_POSTAL_CODE
        defaultCLocationShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the cLocationList where postalCode contains UPDATED_POSTAL_CODE
        defaultCLocationShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultCLocationShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the cLocationList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultCLocationShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllCLocationsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone equals to DEFAULT_PHONE
        defaultCLocationShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the cLocationList where phone equals to UPDATED_PHONE
        defaultCLocationShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone not equals to DEFAULT_PHONE
        defaultCLocationShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the cLocationList where phone not equals to UPDATED_PHONE
        defaultCLocationShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCLocationShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the cLocationList where phone equals to UPDATED_PHONE
        defaultCLocationShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone is not null
        defaultCLocationShouldBeFound("phone.specified=true");

        // Get all the cLocationList where phone is null
        defaultCLocationShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone contains DEFAULT_PHONE
        defaultCLocationShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the cLocationList where phone contains UPDATED_PHONE
        defaultCLocationShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where phone does not contain DEFAULT_PHONE
        defaultCLocationShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the cLocationList where phone does not contain UPDATED_PHONE
        defaultCLocationShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCLocationsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax equals to DEFAULT_FAX
        defaultCLocationShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the cLocationList where fax equals to UPDATED_FAX
        defaultCLocationShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCLocationsByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax not equals to DEFAULT_FAX
        defaultCLocationShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the cLocationList where fax not equals to UPDATED_FAX
        defaultCLocationShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCLocationsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultCLocationShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the cLocationList where fax equals to UPDATED_FAX
        defaultCLocationShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCLocationsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax is not null
        defaultCLocationShouldBeFound("fax.specified=true");

        // Get all the cLocationList where fax is null
        defaultCLocationShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByFaxContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax contains DEFAULT_FAX
        defaultCLocationShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the cLocationList where fax contains UPDATED_FAX
        defaultCLocationShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCLocationsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where fax does not contain DEFAULT_FAX
        defaultCLocationShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the cLocationList where fax does not contain UPDATED_FAX
        defaultCLocationShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllCLocationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where uid equals to DEFAULT_UID
        defaultCLocationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cLocationList where uid equals to UPDATED_UID
        defaultCLocationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where uid not equals to DEFAULT_UID
        defaultCLocationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cLocationList where uid not equals to UPDATED_UID
        defaultCLocationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where uid in DEFAULT_UID or UPDATED_UID
        defaultCLocationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cLocationList where uid equals to UPDATED_UID
        defaultCLocationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCLocationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where uid is not null
        defaultCLocationShouldBeFound("uid.specified=true");

        // Get all the cLocationList where uid is null
        defaultCLocationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCLocationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where active equals to DEFAULT_ACTIVE
        defaultCLocationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cLocationList where active equals to UPDATED_ACTIVE
        defaultCLocationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where active not equals to DEFAULT_ACTIVE
        defaultCLocationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cLocationList where active not equals to UPDATED_ACTIVE
        defaultCLocationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCLocationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cLocationList where active equals to UPDATED_ACTIVE
        defaultCLocationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCLocationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where active is not null
        defaultCLocationShouldBeFound("active.specified=true");

        // Get all the cLocationList where active is null
        defaultCLocationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCLocationsByCityIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCity city = cLocation.getCity();
        cLocationRepository.saveAndFlush(cLocation);
        Long cityId = city.getId();

        // Get all the cLocationList where city equals to cityId
        defaultCLocationShouldBeFound("cityId.equals=" + cityId);

        // Get all the cLocationList where city equals to cityId + 1
        defaultCLocationShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }


    @Test
    @Transactional
    public void getAllCLocationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cLocation.getAdOrganization();
        cLocationRepository.saveAndFlush(cLocation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cLocationList where adOrganization equals to adOrganizationId
        defaultCLocationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cLocationList where adOrganization equals to adOrganizationId + 1
        defaultCLocationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCLocationShouldBeFound(String filter) throws Exception {
        restCLocationMockMvc.perform(get("/api/c-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCLocationMockMvc.perform(get("/api/c-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCLocationShouldNotBeFound(String filter) throws Exception {
        restCLocationMockMvc.perform(get("/api/c-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCLocationMockMvc.perform(get("/api/c-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCLocation() throws Exception {
        // Get the cLocation
        restCLocationMockMvc.perform(get("/api/c-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCLocation() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        int databaseSizeBeforeUpdate = cLocationRepository.findAll().size();

        // Update the cLocation
        CLocation updatedCLocation = cLocationRepository.findById(cLocation.getId()).get();
        // Disconnect from session so that the updates on updatedCLocation are not directly saved in db
        em.detach(updatedCLocation);
        updatedCLocation
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .postalCode(UPDATED_POSTAL_CODE)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CLocationDTO cLocationDTO = cLocationMapper.toDto(updatedCLocation);

        restCLocationMockMvc.perform(put("/api/c-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocationDTO)))
            .andExpect(status().isOk());

        // Validate the CLocation in the database
        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeUpdate);
        CLocation testCLocation = cLocationList.get(cLocationList.size() - 1);
        assertThat(testCLocation.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCLocation.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCLocation.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testCLocation.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testCLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCLocation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCLocation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCLocation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCLocation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCLocation() throws Exception {
        int databaseSizeBeforeUpdate = cLocationRepository.findAll().size();

        // Create the CLocation
        CLocationDTO cLocationDTO = cLocationMapper.toDto(cLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLocationMockMvc.perform(put("/api/c-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLocation in the database
        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCLocation() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        int databaseSizeBeforeDelete = cLocationRepository.findAll().size();

        // Delete the cLocation
        restCLocationMockMvc.perform(delete("/api/c-locations/{id}", cLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CLocation> cLocationList = cLocationRepository.findAll();
        assertThat(cLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
