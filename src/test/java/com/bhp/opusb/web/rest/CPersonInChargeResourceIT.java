package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPersonInCharge;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPersonInChargeRepository;
import com.bhp.opusb.service.CPersonInChargeService;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.mapper.CPersonInChargeMapper;
import com.bhp.opusb.service.dto.CPersonInChargeCriteria;
import com.bhp.opusb.service.CPersonInChargeQueryService;

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
 * Integration tests for the {@link CPersonInChargeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPersonInChargeResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FUNCTIONARY = false;
    private static final Boolean UPDATED_FUNCTIONARY = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPersonInChargeRepository cPersonInChargeRepository;

    @Autowired
    private CPersonInChargeMapper cPersonInChargeMapper;

    @Autowired
    private CPersonInChargeService cPersonInChargeService;

    @Autowired
    private CPersonInChargeQueryService cPersonInChargeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPersonInChargeMockMvc;

    private CPersonInCharge cPersonInCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPersonInCharge createEntity(EntityManager em) {
        CPersonInCharge cPersonInCharge = new CPersonInCharge()
            .uid(DEFAULT_UID)
            .position(DEFAULT_POSITION)
            .phone(DEFAULT_PHONE)
            .functionary(DEFAULT_FUNCTIONARY)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cPersonInCharge.setUser(user);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPersonInCharge.setAdOrganization(aDOrganization);
        return cPersonInCharge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPersonInCharge createUpdatedEntity(EntityManager em) {
        CPersonInCharge cPersonInCharge = new CPersonInCharge()
            .uid(UPDATED_UID)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .functionary(UPDATED_FUNCTIONARY)
            .active(UPDATED_ACTIVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cPersonInCharge.setUser(user);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPersonInCharge.setAdOrganization(aDOrganization);
        return cPersonInCharge;
    }

    @BeforeEach
    public void initTest() {
        cPersonInCharge = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPersonInCharge() throws Exception {
        int databaseSizeBeforeCreate = cPersonInChargeRepository.findAll().size();

        // Create the CPersonInCharge
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(cPersonInCharge);
        restCPersonInChargeMockMvc.perform(post("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isCreated());

        // Validate the CPersonInCharge in the database
        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeCreate + 1);
        CPersonInCharge testCPersonInCharge = cPersonInChargeList.get(cPersonInChargeList.size() - 1);
        assertThat(testCPersonInCharge.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPersonInCharge.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCPersonInCharge.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCPersonInCharge.isFunctionary()).isEqualTo(DEFAULT_FUNCTIONARY);
        assertThat(testCPersonInCharge.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPersonInChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPersonInChargeRepository.findAll().size();

        // Create the CPersonInCharge with an existing ID
        cPersonInCharge.setId(1L);
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(cPersonInCharge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPersonInChargeMockMvc.perform(post("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPersonInCharge in the database
        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPersonInChargeRepository.findAll().size();
        // set the field null
        cPersonInCharge.setPosition(null);

        // Create the CPersonInCharge, which fails.
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(cPersonInCharge);

        restCPersonInChargeMockMvc.perform(post("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isBadRequest());

        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPersonInChargeRepository.findAll().size();
        // set the field null
        cPersonInCharge.setPhone(null);

        // Create the CPersonInCharge, which fails.
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(cPersonInCharge);

        restCPersonInChargeMockMvc.perform(post("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isBadRequest());

        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPersonInCharges() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPersonInCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].functionary").value(hasItem(DEFAULT_FUNCTIONARY.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPersonInCharge() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get the cPersonInCharge
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges/{id}", cPersonInCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPersonInCharge.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.functionary").value(DEFAULT_FUNCTIONARY.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPersonInChargesByIdFiltering() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        Long id = cPersonInCharge.getId();

        defaultCPersonInChargeShouldBeFound("id.equals=" + id);
        defaultCPersonInChargeShouldNotBeFound("id.notEquals=" + id);

        defaultCPersonInChargeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPersonInChargeShouldNotBeFound("id.greaterThan=" + id);

        defaultCPersonInChargeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPersonInChargeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPersonInChargesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where uid equals to DEFAULT_UID
        defaultCPersonInChargeShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPersonInChargeList where uid equals to UPDATED_UID
        defaultCPersonInChargeShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where uid not equals to DEFAULT_UID
        defaultCPersonInChargeShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPersonInChargeList where uid not equals to UPDATED_UID
        defaultCPersonInChargeShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPersonInChargeShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPersonInChargeList where uid equals to UPDATED_UID
        defaultCPersonInChargeShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where uid is not null
        defaultCPersonInChargeShouldBeFound("uid.specified=true");

        // Get all the cPersonInChargeList where uid is null
        defaultCPersonInChargeShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position equals to DEFAULT_POSITION
        defaultCPersonInChargeShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the cPersonInChargeList where position equals to UPDATED_POSITION
        defaultCPersonInChargeShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position not equals to DEFAULT_POSITION
        defaultCPersonInChargeShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the cPersonInChargeList where position not equals to UPDATED_POSITION
        defaultCPersonInChargeShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultCPersonInChargeShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the cPersonInChargeList where position equals to UPDATED_POSITION
        defaultCPersonInChargeShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position is not null
        defaultCPersonInChargeShouldBeFound("position.specified=true");

        // Get all the cPersonInChargeList where position is null
        defaultCPersonInChargeShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPersonInChargesByPositionContainsSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position contains DEFAULT_POSITION
        defaultCPersonInChargeShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the cPersonInChargeList where position contains UPDATED_POSITION
        defaultCPersonInChargeShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where position does not contain DEFAULT_POSITION
        defaultCPersonInChargeShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the cPersonInChargeList where position does not contain UPDATED_POSITION
        defaultCPersonInChargeShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone equals to DEFAULT_PHONE
        defaultCPersonInChargeShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the cPersonInChargeList where phone equals to UPDATED_PHONE
        defaultCPersonInChargeShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone not equals to DEFAULT_PHONE
        defaultCPersonInChargeShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the cPersonInChargeList where phone not equals to UPDATED_PHONE
        defaultCPersonInChargeShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCPersonInChargeShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the cPersonInChargeList where phone equals to UPDATED_PHONE
        defaultCPersonInChargeShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone is not null
        defaultCPersonInChargeShouldBeFound("phone.specified=true");

        // Get all the cPersonInChargeList where phone is null
        defaultCPersonInChargeShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone contains DEFAULT_PHONE
        defaultCPersonInChargeShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the cPersonInChargeList where phone contains UPDATED_PHONE
        defaultCPersonInChargeShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where phone does not contain DEFAULT_PHONE
        defaultCPersonInChargeShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the cPersonInChargeList where phone does not contain UPDATED_PHONE
        defaultCPersonInChargeShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCPersonInChargesByFunctionaryIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where functionary equals to DEFAULT_FUNCTIONARY
        defaultCPersonInChargeShouldBeFound("functionary.equals=" + DEFAULT_FUNCTIONARY);

        // Get all the cPersonInChargeList where functionary equals to UPDATED_FUNCTIONARY
        defaultCPersonInChargeShouldNotBeFound("functionary.equals=" + UPDATED_FUNCTIONARY);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByFunctionaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where functionary not equals to DEFAULT_FUNCTIONARY
        defaultCPersonInChargeShouldNotBeFound("functionary.notEquals=" + DEFAULT_FUNCTIONARY);

        // Get all the cPersonInChargeList where functionary not equals to UPDATED_FUNCTIONARY
        defaultCPersonInChargeShouldBeFound("functionary.notEquals=" + UPDATED_FUNCTIONARY);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByFunctionaryIsInShouldWork() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where functionary in DEFAULT_FUNCTIONARY or UPDATED_FUNCTIONARY
        defaultCPersonInChargeShouldBeFound("functionary.in=" + DEFAULT_FUNCTIONARY + "," + UPDATED_FUNCTIONARY);

        // Get all the cPersonInChargeList where functionary equals to UPDATED_FUNCTIONARY
        defaultCPersonInChargeShouldNotBeFound("functionary.in=" + UPDATED_FUNCTIONARY);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByFunctionaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where functionary is not null
        defaultCPersonInChargeShouldBeFound("functionary.specified=true");

        // Get all the cPersonInChargeList where functionary is null
        defaultCPersonInChargeShouldNotBeFound("functionary.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where active equals to DEFAULT_ACTIVE
        defaultCPersonInChargeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPersonInChargeList where active equals to UPDATED_ACTIVE
        defaultCPersonInChargeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where active not equals to DEFAULT_ACTIVE
        defaultCPersonInChargeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPersonInChargeList where active not equals to UPDATED_ACTIVE
        defaultCPersonInChargeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPersonInChargeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPersonInChargeList where active equals to UPDATED_ACTIVE
        defaultCPersonInChargeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        // Get all the cPersonInChargeList where active is not null
        defaultCPersonInChargeShouldBeFound("active.specified=true");

        // Get all the cPersonInChargeList where active is null
        defaultCPersonInChargeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPersonInChargesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = cPersonInCharge.getUser();
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);
        Long userId = user.getId();

        // Get all the cPersonInChargeList where user equals to userId
        defaultCPersonInChargeShouldBeFound("userId.equals=" + userId);

        // Get all the cPersonInChargeList where user equals to userId + 1
        defaultCPersonInChargeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllCPersonInChargesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        cPersonInCharge.setVendor(vendor);
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);
        Long vendorId = vendor.getId();

        // Get all the cPersonInChargeList where vendor equals to vendorId
        defaultCPersonInChargeShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cPersonInChargeList where vendor equals to vendorId + 1
        defaultCPersonInChargeShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCPersonInChargesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPersonInCharge.getAdOrganization();
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPersonInChargeList where adOrganization equals to adOrganizationId
        defaultCPersonInChargeShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPersonInChargeList where adOrganization equals to adOrganizationId + 1
        defaultCPersonInChargeShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPersonInChargeShouldBeFound(String filter) throws Exception {
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPersonInCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].functionary").value(hasItem(DEFAULT_FUNCTIONARY.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPersonInChargeShouldNotBeFound(String filter) throws Exception {
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPersonInCharge() throws Exception {
        // Get the cPersonInCharge
        restCPersonInChargeMockMvc.perform(get("/api/c-person-in-charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPersonInCharge() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        int databaseSizeBeforeUpdate = cPersonInChargeRepository.findAll().size();

        // Update the cPersonInCharge
        CPersonInCharge updatedCPersonInCharge = cPersonInChargeRepository.findById(cPersonInCharge.getId()).get();
        // Disconnect from session so that the updates on updatedCPersonInCharge are not directly saved in db
        em.detach(updatedCPersonInCharge);
        updatedCPersonInCharge
            .uid(UPDATED_UID)
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE)
            .functionary(UPDATED_FUNCTIONARY)
            .active(UPDATED_ACTIVE);
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(updatedCPersonInCharge);

        restCPersonInChargeMockMvc.perform(put("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isOk());

        // Validate the CPersonInCharge in the database
        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeUpdate);
        CPersonInCharge testCPersonInCharge = cPersonInChargeList.get(cPersonInChargeList.size() - 1);
        assertThat(testCPersonInCharge.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPersonInCharge.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCPersonInCharge.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCPersonInCharge.isFunctionary()).isEqualTo(UPDATED_FUNCTIONARY);
        assertThat(testCPersonInCharge.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPersonInCharge() throws Exception {
        int databaseSizeBeforeUpdate = cPersonInChargeRepository.findAll().size();

        // Create the CPersonInCharge
        CPersonInChargeDTO cPersonInChargeDTO = cPersonInChargeMapper.toDto(cPersonInCharge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPersonInChargeMockMvc.perform(put("/api/c-person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPersonInChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPersonInCharge in the database
        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPersonInCharge() throws Exception {
        // Initialize the database
        cPersonInChargeRepository.saveAndFlush(cPersonInCharge);

        int databaseSizeBeforeDelete = cPersonInChargeRepository.findAll().size();

        // Delete the cPersonInCharge
        restCPersonInChargeMockMvc.perform(delete("/api/c-person-in-charges/{id}", cPersonInCharge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPersonInCharge> cPersonInChargeList = cPersonInChargeRepository.findAll();
        assertThat(cPersonInChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
