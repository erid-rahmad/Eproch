package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorLocationRepository;
import com.bhp.opusb.service.CVendorLocationService;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.mapper.CVendorLocationMapper;
import com.bhp.opusb.service.dto.CVendorLocationCriteria;
import com.bhp.opusb.service.CVendorLocationQueryService;

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
 * Integration tests for the {@link CVendorLocationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorLocationResourceIT {

    private static final Boolean DEFAULT_TAX_INVOICE_ADDRESS = false;
    private static final Boolean UPDATED_TAX_INVOICE_ADDRESS = true;

    private static final Boolean DEFAULT_SHIP_ADDRESS = false;
    private static final Boolean UPDATED_SHIP_ADDRESS = true;

    private static final Boolean DEFAULT_INVOICE_ADDRESS = false;
    private static final Boolean UPDATED_INVOICE_ADDRESS = true;

    private static final Boolean DEFAULT_PAY_FROM_ADDRESS = false;
    private static final Boolean UPDATED_PAY_FROM_ADDRESS = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorLocationRepository cVendorLocationRepository;

    @Autowired
    private CVendorLocationMapper cVendorLocationMapper;

    @Autowired
    private CVendorLocationService cVendorLocationService;

    @Autowired
    private CVendorLocationQueryService cVendorLocationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorLocationMockMvc;

    private CVendorLocation cVendorLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorLocation createEntity(EntityManager em) {
        CVendorLocation cVendorLocation = new CVendorLocation()
            .taxInvoiceAddress(DEFAULT_TAX_INVOICE_ADDRESS)
            .shipAddress(DEFAULT_SHIP_ADDRESS)
            .invoiceAddress(DEFAULT_INVOICE_ADDRESS)
            .payFromAddress(DEFAULT_PAY_FROM_ADDRESS)
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
        cVendorLocation.setAdOrganization(aDOrganization);
        return cVendorLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorLocation createUpdatedEntity(EntityManager em) {
        CVendorLocation cVendorLocation = new CVendorLocation()
            .taxInvoiceAddress(UPDATED_TAX_INVOICE_ADDRESS)
            .shipAddress(UPDATED_SHIP_ADDRESS)
            .invoiceAddress(UPDATED_INVOICE_ADDRESS)
            .payFromAddress(UPDATED_PAY_FROM_ADDRESS)
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
        cVendorLocation.setAdOrganization(aDOrganization);
        return cVendorLocation;
    }

    @BeforeEach
    public void initTest() {
        cVendorLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorLocation() throws Exception {
        int databaseSizeBeforeCreate = cVendorLocationRepository.findAll().size();

        // Create the CVendorLocation
        CVendorLocationDTO cVendorLocationDTO = cVendorLocationMapper.toDto(cVendorLocation);
        restCVendorLocationMockMvc.perform(post("/api/c-vendor-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorLocation in the database
        List<CVendorLocation> cVendorLocationList = cVendorLocationRepository.findAll();
        assertThat(cVendorLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorLocation testCVendorLocation = cVendorLocationList.get(cVendorLocationList.size() - 1);
        assertThat(testCVendorLocation.isTaxInvoiceAddress()).isEqualTo(DEFAULT_TAX_INVOICE_ADDRESS);
        assertThat(testCVendorLocation.isShipAddress()).isEqualTo(DEFAULT_SHIP_ADDRESS);
        assertThat(testCVendorLocation.isInvoiceAddress()).isEqualTo(DEFAULT_INVOICE_ADDRESS);
        assertThat(testCVendorLocation.isPayFromAddress()).isEqualTo(DEFAULT_PAY_FROM_ADDRESS);
        assertThat(testCVendorLocation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorLocation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorLocationRepository.findAll().size();

        // Create the CVendorLocation with an existing ID
        cVendorLocation.setId(1L);
        CVendorLocationDTO cVendorLocationDTO = cVendorLocationMapper.toDto(cVendorLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorLocationMockMvc.perform(post("/api/c-vendor-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorLocation in the database
        List<CVendorLocation> cVendorLocationList = cVendorLocationRepository.findAll();
        assertThat(cVendorLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCVendorLocations() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxInvoiceAddress").value(hasItem(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].shipAddress").value(hasItem(DEFAULT_SHIP_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].invoiceAddress").value(hasItem(DEFAULT_INVOICE_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].payFromAddress").value(hasItem(DEFAULT_PAY_FROM_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorLocation() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get the cVendorLocation
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations/{id}", cVendorLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorLocation.getId().intValue()))
            .andExpect(jsonPath("$.taxInvoiceAddress").value(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.shipAddress").value(DEFAULT_SHIP_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.invoiceAddress").value(DEFAULT_INVOICE_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.payFromAddress").value(DEFAULT_PAY_FROM_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorLocationsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        Long id = cVendorLocation.getId();

        defaultCVendorLocationShouldBeFound("id.equals=" + id);
        defaultCVendorLocationShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorLocationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorLocationsByTaxInvoiceAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where taxInvoiceAddress equals to DEFAULT_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("taxInvoiceAddress.equals=" + DEFAULT_TAX_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where taxInvoiceAddress equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("taxInvoiceAddress.equals=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByTaxInvoiceAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where taxInvoiceAddress not equals to DEFAULT_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("taxInvoiceAddress.notEquals=" + DEFAULT_TAX_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where taxInvoiceAddress not equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("taxInvoiceAddress.notEquals=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByTaxInvoiceAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where taxInvoiceAddress in DEFAULT_TAX_INVOICE_ADDRESS or UPDATED_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("taxInvoiceAddress.in=" + DEFAULT_TAX_INVOICE_ADDRESS + "," + UPDATED_TAX_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where taxInvoiceAddress equals to UPDATED_TAX_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("taxInvoiceAddress.in=" + UPDATED_TAX_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByTaxInvoiceAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where taxInvoiceAddress is not null
        defaultCVendorLocationShouldBeFound("taxInvoiceAddress.specified=true");

        // Get all the cVendorLocationList where taxInvoiceAddress is null
        defaultCVendorLocationShouldNotBeFound("taxInvoiceAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByShipAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where shipAddress equals to DEFAULT_SHIP_ADDRESS
        defaultCVendorLocationShouldBeFound("shipAddress.equals=" + DEFAULT_SHIP_ADDRESS);

        // Get all the cVendorLocationList where shipAddress equals to UPDATED_SHIP_ADDRESS
        defaultCVendorLocationShouldNotBeFound("shipAddress.equals=" + UPDATED_SHIP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByShipAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where shipAddress not equals to DEFAULT_SHIP_ADDRESS
        defaultCVendorLocationShouldNotBeFound("shipAddress.notEquals=" + DEFAULT_SHIP_ADDRESS);

        // Get all the cVendorLocationList where shipAddress not equals to UPDATED_SHIP_ADDRESS
        defaultCVendorLocationShouldBeFound("shipAddress.notEquals=" + UPDATED_SHIP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByShipAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where shipAddress in DEFAULT_SHIP_ADDRESS or UPDATED_SHIP_ADDRESS
        defaultCVendorLocationShouldBeFound("shipAddress.in=" + DEFAULT_SHIP_ADDRESS + "," + UPDATED_SHIP_ADDRESS);

        // Get all the cVendorLocationList where shipAddress equals to UPDATED_SHIP_ADDRESS
        defaultCVendorLocationShouldNotBeFound("shipAddress.in=" + UPDATED_SHIP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByShipAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where shipAddress is not null
        defaultCVendorLocationShouldBeFound("shipAddress.specified=true");

        // Get all the cVendorLocationList where shipAddress is null
        defaultCVendorLocationShouldNotBeFound("shipAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByInvoiceAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where invoiceAddress equals to DEFAULT_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("invoiceAddress.equals=" + DEFAULT_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where invoiceAddress equals to UPDATED_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("invoiceAddress.equals=" + UPDATED_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByInvoiceAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where invoiceAddress not equals to DEFAULT_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("invoiceAddress.notEquals=" + DEFAULT_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where invoiceAddress not equals to UPDATED_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("invoiceAddress.notEquals=" + UPDATED_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByInvoiceAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where invoiceAddress in DEFAULT_INVOICE_ADDRESS or UPDATED_INVOICE_ADDRESS
        defaultCVendorLocationShouldBeFound("invoiceAddress.in=" + DEFAULT_INVOICE_ADDRESS + "," + UPDATED_INVOICE_ADDRESS);

        // Get all the cVendorLocationList where invoiceAddress equals to UPDATED_INVOICE_ADDRESS
        defaultCVendorLocationShouldNotBeFound("invoiceAddress.in=" + UPDATED_INVOICE_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByInvoiceAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where invoiceAddress is not null
        defaultCVendorLocationShouldBeFound("invoiceAddress.specified=true");

        // Get all the cVendorLocationList where invoiceAddress is null
        defaultCVendorLocationShouldNotBeFound("invoiceAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByPayFromAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where payFromAddress equals to DEFAULT_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldBeFound("payFromAddress.equals=" + DEFAULT_PAY_FROM_ADDRESS);

        // Get all the cVendorLocationList where payFromAddress equals to UPDATED_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldNotBeFound("payFromAddress.equals=" + UPDATED_PAY_FROM_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByPayFromAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where payFromAddress not equals to DEFAULT_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldNotBeFound("payFromAddress.notEquals=" + DEFAULT_PAY_FROM_ADDRESS);

        // Get all the cVendorLocationList where payFromAddress not equals to UPDATED_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldBeFound("payFromAddress.notEquals=" + UPDATED_PAY_FROM_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByPayFromAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where payFromAddress in DEFAULT_PAY_FROM_ADDRESS or UPDATED_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldBeFound("payFromAddress.in=" + DEFAULT_PAY_FROM_ADDRESS + "," + UPDATED_PAY_FROM_ADDRESS);

        // Get all the cVendorLocationList where payFromAddress equals to UPDATED_PAY_FROM_ADDRESS
        defaultCVendorLocationShouldNotBeFound("payFromAddress.in=" + UPDATED_PAY_FROM_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByPayFromAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where payFromAddress is not null
        defaultCVendorLocationShouldBeFound("payFromAddress.specified=true");

        // Get all the cVendorLocationList where payFromAddress is null
        defaultCVendorLocationShouldNotBeFound("payFromAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where uid equals to DEFAULT_UID
        defaultCVendorLocationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorLocationList where uid equals to UPDATED_UID
        defaultCVendorLocationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where uid not equals to DEFAULT_UID
        defaultCVendorLocationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorLocationList where uid not equals to UPDATED_UID
        defaultCVendorLocationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorLocationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorLocationList where uid equals to UPDATED_UID
        defaultCVendorLocationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where uid is not null
        defaultCVendorLocationShouldBeFound("uid.specified=true");

        // Get all the cVendorLocationList where uid is null
        defaultCVendorLocationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where active equals to DEFAULT_ACTIVE
        defaultCVendorLocationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorLocationList where active equals to UPDATED_ACTIVE
        defaultCVendorLocationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where active not equals to DEFAULT_ACTIVE
        defaultCVendorLocationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorLocationList where active not equals to UPDATED_ACTIVE
        defaultCVendorLocationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorLocationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorLocationList where active equals to UPDATED_ACTIVE
        defaultCVendorLocationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        // Get all the cVendorLocationList where active is not null
        defaultCVendorLocationShouldBeFound("active.specified=true");

        // Get all the cVendorLocationList where active is null
        defaultCVendorLocationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorLocationsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        cVendorLocation.setVendor(vendor);
        cVendorLocationRepository.saveAndFlush(cVendorLocation);
        Long vendorId = vendor.getId();

        // Get all the cVendorLocationList where vendor equals to vendorId
        defaultCVendorLocationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cVendorLocationList where vendor equals to vendorId + 1
        defaultCVendorLocationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorLocationsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);
        CLocation location = CLocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        cVendorLocation.setLocation(location);
        cVendorLocationRepository.saveAndFlush(cVendorLocation);
        Long locationId = location.getId();

        // Get all the cVendorLocationList where location equals to locationId
        defaultCVendorLocationShouldBeFound("locationId.equals=" + locationId);

        // Get all the cVendorLocationList where location equals to locationId + 1
        defaultCVendorLocationShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorLocationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorLocation.getAdOrganization();
        cVendorLocationRepository.saveAndFlush(cVendorLocation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorLocationList where adOrganization equals to adOrganizationId
        defaultCVendorLocationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorLocationList where adOrganization equals to adOrganizationId + 1
        defaultCVendorLocationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorLocationShouldBeFound(String filter) throws Exception {
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxInvoiceAddress").value(hasItem(DEFAULT_TAX_INVOICE_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].shipAddress").value(hasItem(DEFAULT_SHIP_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].invoiceAddress").value(hasItem(DEFAULT_INVOICE_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].payFromAddress").value(hasItem(DEFAULT_PAY_FROM_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorLocationShouldNotBeFound(String filter) throws Exception {
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorLocation() throws Exception {
        // Get the cVendorLocation
        restCVendorLocationMockMvc.perform(get("/api/c-vendor-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorLocation() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        int databaseSizeBeforeUpdate = cVendorLocationRepository.findAll().size();

        // Update the cVendorLocation
        CVendorLocation updatedCVendorLocation = cVendorLocationRepository.findById(cVendorLocation.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorLocation are not directly saved in db
        em.detach(updatedCVendorLocation);
        updatedCVendorLocation
            .taxInvoiceAddress(UPDATED_TAX_INVOICE_ADDRESS)
            .shipAddress(UPDATED_SHIP_ADDRESS)
            .invoiceAddress(UPDATED_INVOICE_ADDRESS)
            .payFromAddress(UPDATED_PAY_FROM_ADDRESS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorLocationDTO cVendorLocationDTO = cVendorLocationMapper.toDto(updatedCVendorLocation);

        restCVendorLocationMockMvc.perform(put("/api/c-vendor-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorLocationDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorLocation in the database
        List<CVendorLocation> cVendorLocationList = cVendorLocationRepository.findAll();
        assertThat(cVendorLocationList).hasSize(databaseSizeBeforeUpdate);
        CVendorLocation testCVendorLocation = cVendorLocationList.get(cVendorLocationList.size() - 1);
        assertThat(testCVendorLocation.isTaxInvoiceAddress()).isEqualTo(UPDATED_TAX_INVOICE_ADDRESS);
        assertThat(testCVendorLocation.isShipAddress()).isEqualTo(UPDATED_SHIP_ADDRESS);
        assertThat(testCVendorLocation.isInvoiceAddress()).isEqualTo(UPDATED_INVOICE_ADDRESS);
        assertThat(testCVendorLocation.isPayFromAddress()).isEqualTo(UPDATED_PAY_FROM_ADDRESS);
        assertThat(testCVendorLocation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorLocation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorLocation() throws Exception {
        int databaseSizeBeforeUpdate = cVendorLocationRepository.findAll().size();

        // Create the CVendorLocation
        CVendorLocationDTO cVendorLocationDTO = cVendorLocationMapper.toDto(cVendorLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorLocationMockMvc.perform(put("/api/c-vendor-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorLocation in the database
        List<CVendorLocation> cVendorLocationList = cVendorLocationRepository.findAll();
        assertThat(cVendorLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorLocation() throws Exception {
        // Initialize the database
        cVendorLocationRepository.saveAndFlush(cVendorLocation);

        int databaseSizeBeforeDelete = cVendorLocationRepository.findAll().size();

        // Delete the cVendorLocation
        restCVendorLocationMockMvc.perform(delete("/api/c-vendor-locations/{id}", cVendorLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorLocation> cVendorLocationList = cVendorLocationRepository.findAll();
        assertThat(cVendorLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
