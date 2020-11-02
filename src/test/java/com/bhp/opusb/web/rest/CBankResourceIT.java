package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CBank;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CBankRepository;
import com.bhp.opusb.service.CBankService;
import com.bhp.opusb.service.dto.CBankDTO;
import com.bhp.opusb.service.mapper.CBankMapper;
import com.bhp.opusb.service.dto.CBankCriteria;
import com.bhp.opusb.service.CBankQueryService;

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
 * Integration tests for the {@link CBankResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CBankResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "UJLKPC0PMN2";
    private static final String UPDATED_SWIFT_CODE = "JZWMLW6T";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CBankRepository cBankRepository;

    @Autowired
    private CBankMapper cBankMapper;

    @Autowired
    private CBankService cBankService;

    @Autowired
    private CBankQueryService cBankQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCBankMockMvc;

    private CBank cBank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBank createEntity(EntityManager em) {
        CBank cBank = new CBank()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .swiftCode(DEFAULT_SWIFT_CODE)
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
        cBank.setAdOrganization(aDOrganization);
        return cBank;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CBank createUpdatedEntity(EntityManager em) {
        CBank cBank = new CBank()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .swiftCode(UPDATED_SWIFT_CODE)
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
        cBank.setAdOrganization(aDOrganization);
        return cBank;
    }

    @BeforeEach
    public void initTest() {
        cBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createCBank() throws Exception {
        int databaseSizeBeforeCreate = cBankRepository.findAll().size();

        // Create the CBank
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);
        restCBankMockMvc.perform(post("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isCreated());

        // Validate the CBank in the database
        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeCreate + 1);
        CBank testCBank = cBankList.get(cBankList.size() - 1);
        assertThat(testCBank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCBank.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCBank.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCBank.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCBank.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testCBank.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCBank.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cBankRepository.findAll().size();

        // Create the CBank with an existing ID
        cBank.setId(1L);
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCBankMockMvc.perform(post("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBank in the database
        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBankRepository.findAll().size();
        // set the field null
        cBank.setName(null);

        // Create the CBank, which fails.
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);

        restCBankMockMvc.perform(post("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isBadRequest());

        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBankRepository.findAll().size();
        // set the field null
        cBank.setValue(null);

        // Create the CBank, which fails.
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);

        restCBankMockMvc.perform(post("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isBadRequest());

        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cBankRepository.findAll().size();
        // set the field null
        cBank.setShortName(null);

        // Create the CBank, which fails.
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);

        restCBankMockMvc.perform(post("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isBadRequest());

        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCBanks() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList
        restCBankMockMvc.perform(get("/api/c-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCBank() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get the cBank
        restCBankMockMvc.perform(get("/api/c-banks/{id}", cBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cBank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.swiftCode").value(DEFAULT_SWIFT_CODE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCBanksByIdFiltering() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        Long id = cBank.getId();

        defaultCBankShouldBeFound("id.equals=" + id);
        defaultCBankShouldNotBeFound("id.notEquals=" + id);

        defaultCBankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCBankShouldNotBeFound("id.greaterThan=" + id);

        defaultCBankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCBankShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCBanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name equals to DEFAULT_NAME
        defaultCBankShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cBankList where name equals to UPDATED_NAME
        defaultCBankShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name not equals to DEFAULT_NAME
        defaultCBankShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cBankList where name not equals to UPDATED_NAME
        defaultCBankShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCBankShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cBankList where name equals to UPDATED_NAME
        defaultCBankShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name is not null
        defaultCBankShouldBeFound("name.specified=true");

        // Get all the cBankList where name is null
        defaultCBankShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBanksByNameContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name contains DEFAULT_NAME
        defaultCBankShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cBankList where name contains UPDATED_NAME
        defaultCBankShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where name does not contain DEFAULT_NAME
        defaultCBankShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cBankList where name does not contain UPDATED_NAME
        defaultCBankShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCBanksByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value equals to DEFAULT_VALUE
        defaultCBankShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the cBankList where value equals to UPDATED_VALUE
        defaultCBankShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCBanksByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value not equals to DEFAULT_VALUE
        defaultCBankShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the cBankList where value not equals to UPDATED_VALUE
        defaultCBankShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCBanksByValueIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCBankShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the cBankList where value equals to UPDATED_VALUE
        defaultCBankShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCBanksByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value is not null
        defaultCBankShouldBeFound("value.specified=true");

        // Get all the cBankList where value is null
        defaultCBankShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBanksByValueContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value contains DEFAULT_VALUE
        defaultCBankShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the cBankList where value contains UPDATED_VALUE
        defaultCBankShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCBanksByValueNotContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where value does not contain DEFAULT_VALUE
        defaultCBankShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the cBankList where value does not contain UPDATED_VALUE
        defaultCBankShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllCBanksByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName equals to DEFAULT_SHORT_NAME
        defaultCBankShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the cBankList where shortName equals to UPDATED_SHORT_NAME
        defaultCBankShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName not equals to DEFAULT_SHORT_NAME
        defaultCBankShouldNotBeFound("shortName.notEquals=" + DEFAULT_SHORT_NAME);

        // Get all the cBankList where shortName not equals to UPDATED_SHORT_NAME
        defaultCBankShouldBeFound("shortName.notEquals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultCBankShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the cBankList where shortName equals to UPDATED_SHORT_NAME
        defaultCBankShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName is not null
        defaultCBankShouldBeFound("shortName.specified=true");

        // Get all the cBankList where shortName is null
        defaultCBankShouldNotBeFound("shortName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBanksByShortNameContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName contains DEFAULT_SHORT_NAME
        defaultCBankShouldBeFound("shortName.contains=" + DEFAULT_SHORT_NAME);

        // Get all the cBankList where shortName contains UPDATED_SHORT_NAME
        defaultCBankShouldNotBeFound("shortName.contains=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllCBanksByShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where shortName does not contain DEFAULT_SHORT_NAME
        defaultCBankShouldNotBeFound("shortName.doesNotContain=" + DEFAULT_SHORT_NAME);

        // Get all the cBankList where shortName does not contain UPDATED_SHORT_NAME
        defaultCBankShouldBeFound("shortName.doesNotContain=" + UPDATED_SHORT_NAME);
    }


    @Test
    @Transactional
    public void getAllCBanksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description equals to DEFAULT_DESCRIPTION
        defaultCBankShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cBankList where description equals to UPDATED_DESCRIPTION
        defaultCBankShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBanksByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description not equals to DEFAULT_DESCRIPTION
        defaultCBankShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cBankList where description not equals to UPDATED_DESCRIPTION
        defaultCBankShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBanksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCBankShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cBankList where description equals to UPDATED_DESCRIPTION
        defaultCBankShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBanksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description is not null
        defaultCBankShouldBeFound("description.specified=true");

        // Get all the cBankList where description is null
        defaultCBankShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBanksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description contains DEFAULT_DESCRIPTION
        defaultCBankShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cBankList where description contains UPDATED_DESCRIPTION
        defaultCBankShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCBanksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where description does not contain DEFAULT_DESCRIPTION
        defaultCBankShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cBankList where description does not contain UPDATED_DESCRIPTION
        defaultCBankShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCBanksBySwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode equals to DEFAULT_SWIFT_CODE
        defaultCBankShouldBeFound("swiftCode.equals=" + DEFAULT_SWIFT_CODE);

        // Get all the cBankList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultCBankShouldNotBeFound("swiftCode.equals=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllCBanksBySwiftCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode not equals to DEFAULT_SWIFT_CODE
        defaultCBankShouldNotBeFound("swiftCode.notEquals=" + DEFAULT_SWIFT_CODE);

        // Get all the cBankList where swiftCode not equals to UPDATED_SWIFT_CODE
        defaultCBankShouldBeFound("swiftCode.notEquals=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllCBanksBySwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode in DEFAULT_SWIFT_CODE or UPDATED_SWIFT_CODE
        defaultCBankShouldBeFound("swiftCode.in=" + DEFAULT_SWIFT_CODE + "," + UPDATED_SWIFT_CODE);

        // Get all the cBankList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultCBankShouldNotBeFound("swiftCode.in=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllCBanksBySwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode is not null
        defaultCBankShouldBeFound("swiftCode.specified=true");

        // Get all the cBankList where swiftCode is null
        defaultCBankShouldNotBeFound("swiftCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCBanksBySwiftCodeContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode contains DEFAULT_SWIFT_CODE
        defaultCBankShouldBeFound("swiftCode.contains=" + DEFAULT_SWIFT_CODE);

        // Get all the cBankList where swiftCode contains UPDATED_SWIFT_CODE
        defaultCBankShouldNotBeFound("swiftCode.contains=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllCBanksBySwiftCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where swiftCode does not contain DEFAULT_SWIFT_CODE
        defaultCBankShouldNotBeFound("swiftCode.doesNotContain=" + DEFAULT_SWIFT_CODE);

        // Get all the cBankList where swiftCode does not contain UPDATED_SWIFT_CODE
        defaultCBankShouldBeFound("swiftCode.doesNotContain=" + UPDATED_SWIFT_CODE);
    }


    @Test
    @Transactional
    public void getAllCBanksByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where uid equals to DEFAULT_UID
        defaultCBankShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cBankList where uid equals to UPDATED_UID
        defaultCBankShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBanksByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where uid not equals to DEFAULT_UID
        defaultCBankShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cBankList where uid not equals to UPDATED_UID
        defaultCBankShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBanksByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where uid in DEFAULT_UID or UPDATED_UID
        defaultCBankShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cBankList where uid equals to UPDATED_UID
        defaultCBankShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCBanksByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where uid is not null
        defaultCBankShouldBeFound("uid.specified=true");

        // Get all the cBankList where uid is null
        defaultCBankShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBanksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where active equals to DEFAULT_ACTIVE
        defaultCBankShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cBankList where active equals to UPDATED_ACTIVE
        defaultCBankShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBanksByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where active not equals to DEFAULT_ACTIVE
        defaultCBankShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cBankList where active not equals to UPDATED_ACTIVE
        defaultCBankShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBanksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCBankShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cBankList where active equals to UPDATED_ACTIVE
        defaultCBankShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCBanksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        // Get all the cBankList where active is not null
        defaultCBankShouldBeFound("active.specified=true");

        // Get all the cBankList where active is null
        defaultCBankShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCBanksByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cBank.getAdOrganization();
        cBankRepository.saveAndFlush(cBank);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cBankList where adOrganization equals to adOrganizationId
        defaultCBankShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cBankList where adOrganization equals to adOrganizationId + 1
        defaultCBankShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCBankShouldBeFound(String filter) throws Exception {
        restCBankMockMvc.perform(get("/api/c-banks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCBankMockMvc.perform(get("/api/c-banks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCBankShouldNotBeFound(String filter) throws Exception {
        restCBankMockMvc.perform(get("/api/c-banks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCBankMockMvc.perform(get("/api/c-banks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCBank() throws Exception {
        // Get the cBank
        restCBankMockMvc.perform(get("/api/c-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCBank() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        int databaseSizeBeforeUpdate = cBankRepository.findAll().size();

        // Update the cBank
        CBank updatedCBank = cBankRepository.findById(cBank.getId()).get();
        // Disconnect from session so that the updates on updatedCBank are not directly saved in db
        em.detach(updatedCBank);
        updatedCBank
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .swiftCode(UPDATED_SWIFT_CODE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CBankDTO cBankDTO = cBankMapper.toDto(updatedCBank);

        restCBankMockMvc.perform(put("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isOk());

        // Validate the CBank in the database
        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeUpdate);
        CBank testCBank = cBankList.get(cBankList.size() - 1);
        assertThat(testCBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCBank.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCBank.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCBank.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCBank.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testCBank.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCBank.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCBank() throws Exception {
        int databaseSizeBeforeUpdate = cBankRepository.findAll().size();

        // Create the CBank
        CBankDTO cBankDTO = cBankMapper.toDto(cBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCBankMockMvc.perform(put("/api/c-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CBank in the database
        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCBank() throws Exception {
        // Initialize the database
        cBankRepository.saveAndFlush(cBank);

        int databaseSizeBeforeDelete = cBankRepository.findAll().size();

        // Delete the cBank
        restCBankMockMvc.perform(delete("/api/c-banks/{id}", cBank.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CBank> cBankList = cBankRepository.findAll();
        assertThat(cBankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
