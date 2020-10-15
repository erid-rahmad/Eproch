package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADOrganizationInfo;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ADOrganizationInfoRepository;
import com.bhp.opusb.service.ADOrganizationInfoService;
import com.bhp.opusb.service.dto.ADOrganizationInfoDTO;
import com.bhp.opusb.service.mapper.ADOrganizationInfoMapper;
import com.bhp.opusb.service.dto.ADOrganizationInfoCriteria;
import com.bhp.opusb.service.ADOrganizationInfoQueryService;

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
 * Integration tests for the {@link ADOrganizationInfoResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADOrganizationInfoResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADOrganizationInfoRepository aDOrganizationInfoRepository;

    @Autowired
    private ADOrganizationInfoMapper aDOrganizationInfoMapper;

    @Autowired
    private ADOrganizationInfoService aDOrganizationInfoService;

    @Autowired
    private ADOrganizationInfoQueryService aDOrganizationInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADOrganizationInfoMockMvc;

    private ADOrganizationInfo aDOrganizationInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADOrganizationInfo createEntity(EntityManager em) {
        ADOrganizationInfo aDOrganizationInfo = new ADOrganizationInfo()
            .address(DEFAULT_ADDRESS)
            .taxId(DEFAULT_TAX_ID)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
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
        aDOrganizationInfo.setAdOrganization(aDOrganization);
        return aDOrganizationInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADOrganizationInfo createUpdatedEntity(EntityManager em) {
        ADOrganizationInfo aDOrganizationInfo = new ADOrganizationInfo()
            .address(UPDATED_ADDRESS)
            .taxId(UPDATED_TAX_ID)
            .bankAccount(UPDATED_BANK_ACCOUNT)
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
        aDOrganizationInfo.setAdOrganization(aDOrganization);
        return aDOrganizationInfo;
    }

    @BeforeEach
    public void initTest() {
        aDOrganizationInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createADOrganizationInfo() throws Exception {
        int databaseSizeBeforeCreate = aDOrganizationInfoRepository.findAll().size();

        // Create the ADOrganizationInfo
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(aDOrganizationInfo);
        restADOrganizationInfoMockMvc.perform(post("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ADOrganizationInfo in the database
        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ADOrganizationInfo testADOrganizationInfo = aDOrganizationInfoList.get(aDOrganizationInfoList.size() - 1);
        assertThat(testADOrganizationInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testADOrganizationInfo.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testADOrganizationInfo.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testADOrganizationInfo.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testADOrganizationInfo.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADOrganizationInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDOrganizationInfoRepository.findAll().size();

        // Create the ADOrganizationInfo with an existing ID
        aDOrganizationInfo.setId(1L);
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(aDOrganizationInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADOrganizationInfoMockMvc.perform(post("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADOrganizationInfo in the database
        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDOrganizationInfoRepository.findAll().size();
        // set the field null
        aDOrganizationInfo.setAddress(null);

        // Create the ADOrganizationInfo, which fails.
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(aDOrganizationInfo);

        restADOrganizationInfoMockMvc.perform(post("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDOrganizationInfoRepository.findAll().size();
        // set the field null
        aDOrganizationInfo.setTaxId(null);

        // Create the ADOrganizationInfo, which fails.
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(aDOrganizationInfo);

        restADOrganizationInfoMockMvc.perform(post("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfos() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDOrganizationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADOrganizationInfo() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get the aDOrganizationInfo
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos/{id}", aDOrganizationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDOrganizationInfo.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADOrganizationInfosByIdFiltering() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        Long id = aDOrganizationInfo.getId();

        defaultADOrganizationInfoShouldBeFound("id.equals=" + id);
        defaultADOrganizationInfoShouldNotBeFound("id.notEquals=" + id);

        defaultADOrganizationInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADOrganizationInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultADOrganizationInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADOrganizationInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address equals to DEFAULT_ADDRESS
        defaultADOrganizationInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the aDOrganizationInfoList where address equals to UPDATED_ADDRESS
        defaultADOrganizationInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address not equals to DEFAULT_ADDRESS
        defaultADOrganizationInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the aDOrganizationInfoList where address not equals to UPDATED_ADDRESS
        defaultADOrganizationInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultADOrganizationInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the aDOrganizationInfoList where address equals to UPDATED_ADDRESS
        defaultADOrganizationInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address is not null
        defaultADOrganizationInfoShouldBeFound("address.specified=true");

        // Get all the aDOrganizationInfoList where address is null
        defaultADOrganizationInfoShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address contains DEFAULT_ADDRESS
        defaultADOrganizationInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the aDOrganizationInfoList where address contains UPDATED_ADDRESS
        defaultADOrganizationInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where address does not contain DEFAULT_ADDRESS
        defaultADOrganizationInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the aDOrganizationInfoList where address does not contain UPDATED_ADDRESS
        defaultADOrganizationInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId equals to DEFAULT_TAX_ID
        defaultADOrganizationInfoShouldBeFound("taxId.equals=" + DEFAULT_TAX_ID);

        // Get all the aDOrganizationInfoList where taxId equals to UPDATED_TAX_ID
        defaultADOrganizationInfoShouldNotBeFound("taxId.equals=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId not equals to DEFAULT_TAX_ID
        defaultADOrganizationInfoShouldNotBeFound("taxId.notEquals=" + DEFAULT_TAX_ID);

        // Get all the aDOrganizationInfoList where taxId not equals to UPDATED_TAX_ID
        defaultADOrganizationInfoShouldBeFound("taxId.notEquals=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId in DEFAULT_TAX_ID or UPDATED_TAX_ID
        defaultADOrganizationInfoShouldBeFound("taxId.in=" + DEFAULT_TAX_ID + "," + UPDATED_TAX_ID);

        // Get all the aDOrganizationInfoList where taxId equals to UPDATED_TAX_ID
        defaultADOrganizationInfoShouldNotBeFound("taxId.in=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId is not null
        defaultADOrganizationInfoShouldBeFound("taxId.specified=true");

        // Get all the aDOrganizationInfoList where taxId is null
        defaultADOrganizationInfoShouldNotBeFound("taxId.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId contains DEFAULT_TAX_ID
        defaultADOrganizationInfoShouldBeFound("taxId.contains=" + DEFAULT_TAX_ID);

        // Get all the aDOrganizationInfoList where taxId contains UPDATED_TAX_ID
        defaultADOrganizationInfoShouldNotBeFound("taxId.contains=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByTaxIdNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where taxId does not contain DEFAULT_TAX_ID
        defaultADOrganizationInfoShouldNotBeFound("taxId.doesNotContain=" + DEFAULT_TAX_ID);

        // Get all the aDOrganizationInfoList where taxId does not contain UPDATED_TAX_ID
        defaultADOrganizationInfoShouldBeFound("taxId.doesNotContain=" + UPDATED_TAX_ID);
    }


    @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultADOrganizationInfoShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the aDOrganizationInfoList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the aDOrganizationInfoList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the aDOrganizationInfoList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount is not null
        defaultADOrganizationInfoShouldBeFound("bankAccount.specified=true");

        // Get all the aDOrganizationInfoList where bankAccount is null
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultADOrganizationInfoShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the aDOrganizationInfoList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultADOrganizationInfoShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the aDOrganizationInfoList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultADOrganizationInfoShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllADOrganizationInfosByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where uid equals to DEFAULT_UID
        defaultADOrganizationInfoShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the aDOrganizationInfoList where uid equals to UPDATED_UID
        defaultADOrganizationInfoShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where uid not equals to DEFAULT_UID
        defaultADOrganizationInfoShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the aDOrganizationInfoList where uid not equals to UPDATED_UID
        defaultADOrganizationInfoShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByUidIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where uid in DEFAULT_UID or UPDATED_UID
        defaultADOrganizationInfoShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the aDOrganizationInfoList where uid equals to UPDATED_UID
        defaultADOrganizationInfoShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where uid is not null
        defaultADOrganizationInfoShouldBeFound("uid.specified=true");

        // Get all the aDOrganizationInfoList where uid is null
        defaultADOrganizationInfoShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where active equals to DEFAULT_ACTIVE
        defaultADOrganizationInfoShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDOrganizationInfoList where active equals to UPDATED_ACTIVE
        defaultADOrganizationInfoShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where active not equals to DEFAULT_ACTIVE
        defaultADOrganizationInfoShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDOrganizationInfoList where active not equals to UPDATED_ACTIVE
        defaultADOrganizationInfoShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADOrganizationInfoShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDOrganizationInfoList where active equals to UPDATED_ACTIVE
        defaultADOrganizationInfoShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        // Get all the aDOrganizationInfoList where active is not null
        defaultADOrganizationInfoShouldBeFound("active.specified=true");

        // Get all the aDOrganizationInfoList where active is null
        defaultADOrganizationInfoShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADOrganizationInfosByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDOrganizationInfo.getAdOrganization();
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDOrganizationInfoList where adOrganization equals to adOrganizationId
        defaultADOrganizationInfoShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDOrganizationInfoList where adOrganization equals to adOrganizationId + 1
        defaultADOrganizationInfoShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADOrganizationInfoShouldBeFound(String filter) throws Exception {
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDOrganizationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADOrganizationInfoShouldNotBeFound(String filter) throws Exception {
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADOrganizationInfo() throws Exception {
        // Get the aDOrganizationInfo
        restADOrganizationInfoMockMvc.perform(get("/api/ad-organization-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADOrganizationInfo() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        int databaseSizeBeforeUpdate = aDOrganizationInfoRepository.findAll().size();

        // Update the aDOrganizationInfo
        ADOrganizationInfo updatedADOrganizationInfo = aDOrganizationInfoRepository.findById(aDOrganizationInfo.getId()).get();
        // Disconnect from session so that the updates on updatedADOrganizationInfo are not directly saved in db
        em.detach(updatedADOrganizationInfo);
        updatedADOrganizationInfo
            .address(UPDATED_ADDRESS)
            .taxId(UPDATED_TAX_ID)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(updatedADOrganizationInfo);

        restADOrganizationInfoMockMvc.perform(put("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ADOrganizationInfo in the database
        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeUpdate);
        ADOrganizationInfo testADOrganizationInfo = aDOrganizationInfoList.get(aDOrganizationInfoList.size() - 1);
        assertThat(testADOrganizationInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testADOrganizationInfo.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testADOrganizationInfo.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testADOrganizationInfo.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testADOrganizationInfo.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADOrganizationInfo() throws Exception {
        int databaseSizeBeforeUpdate = aDOrganizationInfoRepository.findAll().size();

        // Create the ADOrganizationInfo
        ADOrganizationInfoDTO aDOrganizationInfoDTO = aDOrganizationInfoMapper.toDto(aDOrganizationInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADOrganizationInfoMockMvc.perform(put("/api/ad-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADOrganizationInfo in the database
        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADOrganizationInfo() throws Exception {
        // Initialize the database
        aDOrganizationInfoRepository.saveAndFlush(aDOrganizationInfo);

        int databaseSizeBeforeDelete = aDOrganizationInfoRepository.findAll().size();

        // Delete the aDOrganizationInfo
        restADOrganizationInfoMockMvc.perform(delete("/api/ad-organization-infos/{id}", aDOrganizationInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADOrganizationInfo> aDOrganizationInfoList = aDOrganizationInfoRepository.findAll();
        assertThat(aDOrganizationInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
