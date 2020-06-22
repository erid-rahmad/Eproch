package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.CCity;
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

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TAX_INVOICE_ADDRESS = false;
    private static final Boolean UPDATED_TAX_INVOICE_ADDRESS = true;

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
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .taxInvoiceAddress(DEFAULT_TAX_INVOICE_ADDRESS)
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
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .taxInvoiceAddress(UPDATED_TAX_INVOICE_ADDRESS)
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
        assertThat(testCLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCLocation.isTaxInvoiceAddress()).isEqualTo(DEFAULT_TAX_INVOICE_ADDRESS);
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
    public void checkStreetAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = cLocationRepository.findAll().size();
        // set the field null
        cLocation.setStreetAddress(null);

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
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].taxInvoiceAddress").value(hasItem(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue())))
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
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.taxInvoiceAddress").value(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue()))
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
    public void getAllCLocationsByStreetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress equals to DEFAULT_STREET_ADDRESS
        defaultCLocationShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

        // Get all the cLocationList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultCLocationShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByStreetAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress not equals to DEFAULT_STREET_ADDRESS
        defaultCLocationShouldNotBeFound("streetAddress.notEquals=" + DEFAULT_STREET_ADDRESS);

        // Get all the cLocationList where streetAddress not equals to UPDATED_STREET_ADDRESS
        defaultCLocationShouldBeFound("streetAddress.notEquals=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByStreetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress in DEFAULT_STREET_ADDRESS or UPDATED_STREET_ADDRESS
        defaultCLocationShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

        // Get all the cLocationList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultCLocationShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByStreetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress is not null
        defaultCLocationShouldBeFound("streetAddress.specified=true");

        // Get all the cLocationList where streetAddress is null
        defaultCLocationShouldNotBeFound("streetAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllCLocationsByStreetAddressContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress contains DEFAULT_STREET_ADDRESS
        defaultCLocationShouldBeFound("streetAddress.contains=" + DEFAULT_STREET_ADDRESS);

        // Get all the cLocationList where streetAddress contains UPDATED_STREET_ADDRESS
        defaultCLocationShouldNotBeFound("streetAddress.contains=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByStreetAddressNotContainsSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where streetAddress does not contain DEFAULT_STREET_ADDRESS
        defaultCLocationShouldNotBeFound("streetAddress.doesNotContain=" + DEFAULT_STREET_ADDRESS);

        // Get all the cLocationList where streetAddress does not contain UPDATED_STREET_ADDRESS
        defaultCLocationShouldBeFound("streetAddress.doesNotContain=" + UPDATED_STREET_ADDRESS);
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
    public void getAllCLocationsByTaxInvoiceAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where taxInvoiceAddress equals to DEFAULT_TAX_INVOICE_ADDRESS
        defaultCLocationShouldBeFound("taxInvoiceAddress.equals=" + DEFAULT_TAX_INVOICE_ADDRESS);

        // Get all the cLocationList where taxInvoiceAddress equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCLocationShouldNotBeFound("taxInvoiceAddress.equals=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByTaxInvoiceAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where taxInvoiceAddress not equals to DEFAULT_TAX_INVOICE_ADDRESS
        defaultCLocationShouldNotBeFound("taxInvoiceAddress.notEquals=" + DEFAULT_TAX_INVOICE_ADDRESS);

        // Get all the cLocationList where taxInvoiceAddress not equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCLocationShouldBeFound("taxInvoiceAddress.notEquals=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByTaxInvoiceAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where taxInvoiceAddress in DEFAULT_TAX_INVOICE_ADDRESS or UPDATED_TAX_INVOICE_ADDRESS
        defaultCLocationShouldBeFound("taxInvoiceAddress.in=" + DEFAULT_TAX_INVOICE_ADDRESS + "," + UPDATED_TAX_INVOICE_ADDRESS);

        // Get all the cLocationList where taxInvoiceAddress equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCLocationShouldNotBeFound("taxInvoiceAddress.in=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCLocationsByTaxInvoiceAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLocationRepository.saveAndFlush(cLocation);

        // Get all the cLocationList where taxInvoiceAddress is not null
        defaultCLocationShouldBeFound("taxInvoiceAddress.specified=true");

        // Get all the cLocationList where taxInvoiceAddress is null
        defaultCLocationShouldNotBeFound("taxInvoiceAddress.specified=false");
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCLocationShouldBeFound(String filter) throws Exception {
        restCLocationMockMvc.perform(get("/api/c-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].taxInvoiceAddress").value(hasItem(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue())))
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
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .taxInvoiceAddress(UPDATED_TAX_INVOICE_ADDRESS)
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
        assertThat(testCLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCLocation.isTaxInvoiceAddress()).isEqualTo(UPDATED_TAX_INVOICE_ADDRESS);
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
