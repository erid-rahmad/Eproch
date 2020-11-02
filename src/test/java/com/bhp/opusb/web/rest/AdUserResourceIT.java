package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.service.AdUserService;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;
import com.bhp.opusb.service.dto.AdUserCriteria;
import com.bhp.opusb.service.AdUserQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdUserResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdUserResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VENDOR = false;
    private static final Boolean UPDATED_VENDOR = true;

    private static final Integer DEFAULT_FAILED_LOGIN_COUNT = 1;
    private static final Integer UPDATED_FAILED_LOGIN_COUNT = 2;
    private static final Integer SMALLER_FAILED_LOGIN_COUNT = 1 - 1;

    private static final Instant DEFAULT_LAST_LOGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AdUserRepository adUserRepository;

    @Autowired
    private AdUserMapper adUserMapper;

    @Autowired
    private AdUserService adUserService;

    @Autowired
    private AdUserQueryService adUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdUserMockMvc;

    private AdUser adUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdUser createEntity(EntityManager em) {
        AdUser adUser = new AdUser()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .code(DEFAULT_CODE)
            .phone(DEFAULT_PHONE)
            .position(DEFAULT_POSITION)
            .vendor(DEFAULT_VENDOR)
            .failedLoginCount(DEFAULT_FAILED_LOGIN_COUNT)
            .lastLoginDate(DEFAULT_LAST_LOGIN_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adUser.setUser(user);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adUser.setAdOrganization(aDOrganization);
        return adUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdUser createUpdatedEntity(EntityManager em) {
        AdUser adUser = new AdUser()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .code(UPDATED_CODE)
            .phone(UPDATED_PHONE)
            .position(UPDATED_POSITION)
            .vendor(UPDATED_VENDOR)
            .failedLoginCount(UPDATED_FAILED_LOGIN_COUNT)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adUser.setUser(user);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adUser.setAdOrganization(aDOrganization);
        return adUser;
    }

    @BeforeEach
    public void initTest() {
        adUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdUser() throws Exception {
        int databaseSizeBeforeCreate = adUserRepository.findAll().size();

        // Create the AdUser
        AdUserDTO adUserDTO = adUserMapper.toDto(adUser);
        restAdUserMockMvc.perform(post("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AdUser in the database
        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeCreate + 1);
        AdUser testAdUser = adUserList.get(adUserList.size() - 1);
        assertThat(testAdUser.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdUser.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdUser.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAdUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAdUser.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testAdUser.isVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testAdUser.getFailedLoginCount()).isEqualTo(DEFAULT_FAILED_LOGIN_COUNT);
        assertThat(testAdUser.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void createAdUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adUserRepository.findAll().size();

        // Create the AdUser with an existing ID
        adUser.setId(1L);
        AdUserDTO adUserDTO = adUserMapper.toDto(adUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdUserMockMvc.perform(post("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdUser in the database
        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adUserRepository.findAll().size();
        // set the field null
        adUser.setCode(null);

        // Create the AdUser, which fails.
        AdUserDTO adUserDTO = adUserMapper.toDto(adUser);

        restAdUserMockMvc.perform(post("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isBadRequest());

        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = adUserRepository.findAll().size();
        // set the field null
        adUser.setPhone(null);

        // Create the AdUser, which fails.
        AdUserDTO adUserDTO = adUserMapper.toDto(adUser);

        restAdUserMockMvc.perform(post("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isBadRequest());

        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdUsers() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList
        restAdUserMockMvc.perform(get("/api/ad-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].failedLoginCount").value(hasItem(DEFAULT_FAILED_LOGIN_COUNT)))
            .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(DEFAULT_LAST_LOGIN_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAdUser() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get the adUser
        restAdUserMockMvc.perform(get("/api/ad-users/{id}", adUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adUser.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR.booleanValue()))
            .andExpect(jsonPath("$.failedLoginCount").value(DEFAULT_FAILED_LOGIN_COUNT))
            .andExpect(jsonPath("$.lastLoginDate").value(DEFAULT_LAST_LOGIN_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAdUsersByIdFiltering() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        Long id = adUser.getId();

        defaultAdUserShouldBeFound("id.equals=" + id);
        defaultAdUserShouldNotBeFound("id.notEquals=" + id);

        defaultAdUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdUserShouldNotBeFound("id.greaterThan=" + id);

        defaultAdUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdUsersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where uid equals to DEFAULT_UID
        defaultAdUserShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adUserList where uid equals to UPDATED_UID
        defaultAdUserShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUsersByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where uid not equals to DEFAULT_UID
        defaultAdUserShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adUserList where uid not equals to UPDATED_UID
        defaultAdUserShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUsersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdUserShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adUserList where uid equals to UPDATED_UID
        defaultAdUserShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUsersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where uid is not null
        defaultAdUserShouldBeFound("uid.specified=true");

        // Get all the adUserList where uid is null
        defaultAdUserShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUsersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where active equals to DEFAULT_ACTIVE
        defaultAdUserShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adUserList where active equals to UPDATED_ACTIVE
        defaultAdUserShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where active not equals to DEFAULT_ACTIVE
        defaultAdUserShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adUserList where active not equals to UPDATED_ACTIVE
        defaultAdUserShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdUserShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adUserList where active equals to UPDATED_ACTIVE
        defaultAdUserShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where active is not null
        defaultAdUserShouldBeFound("active.specified=true");

        // Get all the adUserList where active is null
        defaultAdUserShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUsersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code equals to DEFAULT_CODE
        defaultAdUserShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the adUserList where code equals to UPDATED_CODE
        defaultAdUserShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code not equals to DEFAULT_CODE
        defaultAdUserShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the adUserList where code not equals to UPDATED_CODE
        defaultAdUserShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAdUserShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the adUserList where code equals to UPDATED_CODE
        defaultAdUserShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code is not null
        defaultAdUserShouldBeFound("code.specified=true");

        // Get all the adUserList where code is null
        defaultAdUserShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdUsersByCodeContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code contains DEFAULT_CODE
        defaultAdUserShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the adUserList where code contains UPDATED_CODE
        defaultAdUserShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where code does not contain DEFAULT_CODE
        defaultAdUserShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the adUserList where code does not contain UPDATED_CODE
        defaultAdUserShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllAdUsersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone equals to DEFAULT_PHONE
        defaultAdUserShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the adUserList where phone equals to UPDATED_PHONE
        defaultAdUserShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone not equals to DEFAULT_PHONE
        defaultAdUserShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the adUserList where phone not equals to UPDATED_PHONE
        defaultAdUserShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultAdUserShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the adUserList where phone equals to UPDATED_PHONE
        defaultAdUserShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone is not null
        defaultAdUserShouldBeFound("phone.specified=true");

        // Get all the adUserList where phone is null
        defaultAdUserShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdUsersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone contains DEFAULT_PHONE
        defaultAdUserShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the adUserList where phone contains UPDATED_PHONE
        defaultAdUserShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where phone does not contain DEFAULT_PHONE
        defaultAdUserShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the adUserList where phone does not contain UPDATED_PHONE
        defaultAdUserShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllAdUsersByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position equals to DEFAULT_POSITION
        defaultAdUserShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the adUserList where position equals to UPDATED_POSITION
        defaultAdUserShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position not equals to DEFAULT_POSITION
        defaultAdUserShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the adUserList where position not equals to UPDATED_POSITION
        defaultAdUserShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultAdUserShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the adUserList where position equals to UPDATED_POSITION
        defaultAdUserShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position is not null
        defaultAdUserShouldBeFound("position.specified=true");

        // Get all the adUserList where position is null
        defaultAdUserShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdUsersByPositionContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position contains DEFAULT_POSITION
        defaultAdUserShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the adUserList where position contains UPDATED_POSITION
        defaultAdUserShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllAdUsersByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where position does not contain DEFAULT_POSITION
        defaultAdUserShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the adUserList where position does not contain UPDATED_POSITION
        defaultAdUserShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllAdUsersByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where vendor equals to DEFAULT_VENDOR
        defaultAdUserShouldBeFound("vendor.equals=" + DEFAULT_VENDOR);

        // Get all the adUserList where vendor equals to UPDATED_VENDOR
        defaultAdUserShouldNotBeFound("vendor.equals=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void getAllAdUsersByVendorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where vendor not equals to DEFAULT_VENDOR
        defaultAdUserShouldNotBeFound("vendor.notEquals=" + DEFAULT_VENDOR);

        // Get all the adUserList where vendor not equals to UPDATED_VENDOR
        defaultAdUserShouldBeFound("vendor.notEquals=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void getAllAdUsersByVendorIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where vendor in DEFAULT_VENDOR or UPDATED_VENDOR
        defaultAdUserShouldBeFound("vendor.in=" + DEFAULT_VENDOR + "," + UPDATED_VENDOR);

        // Get all the adUserList where vendor equals to UPDATED_VENDOR
        defaultAdUserShouldNotBeFound("vendor.in=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void getAllAdUsersByVendorIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where vendor is not null
        defaultAdUserShouldBeFound("vendor.specified=true");

        // Get all the adUserList where vendor is null
        defaultAdUserShouldNotBeFound("vendor.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount equals to DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.equals=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount equals to UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.equals=" + UPDATED_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount not equals to DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.notEquals=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount not equals to UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.notEquals=" + UPDATED_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount in DEFAULT_FAILED_LOGIN_COUNT or UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.in=" + DEFAULT_FAILED_LOGIN_COUNT + "," + UPDATED_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount equals to UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.in=" + UPDATED_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount is not null
        defaultAdUserShouldBeFound("failedLoginCount.specified=true");

        // Get all the adUserList where failedLoginCount is null
        defaultAdUserShouldNotBeFound("failedLoginCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount is greater than or equal to DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.greaterThanOrEqual=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount is greater than or equal to UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.greaterThanOrEqual=" + UPDATED_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount is less than or equal to DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.lessThanOrEqual=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount is less than or equal to SMALLER_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.lessThanOrEqual=" + SMALLER_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsLessThanSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount is less than DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.lessThan=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount is less than UPDATED_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.lessThan=" + UPDATED_FAILED_LOGIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllAdUsersByFailedLoginCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where failedLoginCount is greater than DEFAULT_FAILED_LOGIN_COUNT
        defaultAdUserShouldNotBeFound("failedLoginCount.greaterThan=" + DEFAULT_FAILED_LOGIN_COUNT);

        // Get all the adUserList where failedLoginCount is greater than SMALLER_FAILED_LOGIN_COUNT
        defaultAdUserShouldBeFound("failedLoginCount.greaterThan=" + SMALLER_FAILED_LOGIN_COUNT);
    }


    @Test
    @Transactional
    public void getAllAdUsersByLastLoginDateIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where lastLoginDate equals to DEFAULT_LAST_LOGIN_DATE
        defaultAdUserShouldBeFound("lastLoginDate.equals=" + DEFAULT_LAST_LOGIN_DATE);

        // Get all the adUserList where lastLoginDate equals to UPDATED_LAST_LOGIN_DATE
        defaultAdUserShouldNotBeFound("lastLoginDate.equals=" + UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByLastLoginDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where lastLoginDate not equals to DEFAULT_LAST_LOGIN_DATE
        defaultAdUserShouldNotBeFound("lastLoginDate.notEquals=" + DEFAULT_LAST_LOGIN_DATE);

        // Get all the adUserList where lastLoginDate not equals to UPDATED_LAST_LOGIN_DATE
        defaultAdUserShouldBeFound("lastLoginDate.notEquals=" + UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByLastLoginDateIsInShouldWork() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where lastLoginDate in DEFAULT_LAST_LOGIN_DATE or UPDATED_LAST_LOGIN_DATE
        defaultAdUserShouldBeFound("lastLoginDate.in=" + DEFAULT_LAST_LOGIN_DATE + "," + UPDATED_LAST_LOGIN_DATE);

        // Get all the adUserList where lastLoginDate equals to UPDATED_LAST_LOGIN_DATE
        defaultAdUserShouldNotBeFound("lastLoginDate.in=" + UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void getAllAdUsersByLastLoginDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        // Get all the adUserList where lastLoginDate is not null
        defaultAdUserShouldBeFound("lastLoginDate.specified=true");

        // Get all the adUserList where lastLoginDate is null
        defaultAdUserShouldNotBeFound("lastLoginDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUsersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = adUser.getUser();
        adUserRepository.saveAndFlush(adUser);
        Long userId = user.getId();

        // Get all the adUserList where user equals to userId
        defaultAdUserShouldBeFound("userId.equals=" + userId);

        // Get all the adUserList where user equals to userId + 1
        defaultAdUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllAdUsersByCVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);
        CVendor cVendor = CVendorResourceIT.createEntity(em);
        em.persist(cVendor);
        em.flush();
        adUser.setCVendor(cVendor);
        adUserRepository.saveAndFlush(adUser);
        Long cVendorId = cVendor.getId();

        // Get all the adUserList where cVendor equals to cVendorId
        defaultAdUserShouldBeFound("cVendorId.equals=" + cVendorId);

        // Get all the adUserList where cVendor equals to cVendorId + 1
        defaultAdUserShouldNotBeFound("cVendorId.equals=" + (cVendorId + 1));
    }


    @Test
    @Transactional
    public void getAllAdUsersByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adUser.getAdOrganization();
        adUserRepository.saveAndFlush(adUser);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adUserList where adOrganization equals to adOrganizationId
        defaultAdUserShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adUserList where adOrganization equals to adOrganizationId + 1
        defaultAdUserShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdUserShouldBeFound(String filter) throws Exception {
        restAdUserMockMvc.perform(get("/api/ad-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].failedLoginCount").value(hasItem(DEFAULT_FAILED_LOGIN_COUNT)))
            .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(DEFAULT_LAST_LOGIN_DATE.toString())));

        // Check, that the count call also returns 1
        restAdUserMockMvc.perform(get("/api/ad-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdUserShouldNotBeFound(String filter) throws Exception {
        restAdUserMockMvc.perform(get("/api/ad-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdUserMockMvc.perform(get("/api/ad-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdUser() throws Exception {
        // Get the adUser
        restAdUserMockMvc.perform(get("/api/ad-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdUser() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        int databaseSizeBeforeUpdate = adUserRepository.findAll().size();

        // Update the adUser
        AdUser updatedAdUser = adUserRepository.findById(adUser.getId()).get();
        // Disconnect from session so that the updates on updatedAdUser are not directly saved in db
        em.detach(updatedAdUser);
        updatedAdUser
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .code(UPDATED_CODE)
            .phone(UPDATED_PHONE)
            .position(UPDATED_POSITION)
            .vendor(UPDATED_VENDOR)
            .failedLoginCount(UPDATED_FAILED_LOGIN_COUNT)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        AdUserDTO adUserDTO = adUserMapper.toDto(updatedAdUser);

        restAdUserMockMvc.perform(put("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isOk());

        // Validate the AdUser in the database
        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeUpdate);
        AdUser testAdUser = adUserList.get(adUserList.size() - 1);
        assertThat(testAdUser.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdUser.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdUser.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAdUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAdUser.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testAdUser.isVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testAdUser.getFailedLoginCount()).isEqualTo(UPDATED_FAILED_LOGIN_COUNT);
        assertThat(testAdUser.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdUser() throws Exception {
        int databaseSizeBeforeUpdate = adUserRepository.findAll().size();

        // Create the AdUser
        AdUserDTO adUserDTO = adUserMapper.toDto(adUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdUserMockMvc.perform(put("/api/ad-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdUser in the database
        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdUser() throws Exception {
        // Initialize the database
        adUserRepository.saveAndFlush(adUser);

        int databaseSizeBeforeDelete = adUserRepository.findAll().size();

        // Delete the adUser
        restAdUserMockMvc.perform(delete("/api/ad-users/{id}", adUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdUser> adUserList = adUserRepository.findAll();
        assertThat(adUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
