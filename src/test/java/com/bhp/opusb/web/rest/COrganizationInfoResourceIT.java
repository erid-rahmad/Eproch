package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.COrganizationInfo;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.COrganization;
import com.bhp.opusb.repository.COrganizationInfoRepository;
import com.bhp.opusb.service.COrganizationInfoService;
import com.bhp.opusb.service.dto.COrganizationInfoDTO;
import com.bhp.opusb.service.mapper.COrganizationInfoMapper;
import com.bhp.opusb.service.dto.COrganizationInfoCriteria;
import com.bhp.opusb.service.COrganizationInfoQueryService;

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
 * Integration tests for the {@link COrganizationInfoResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class COrganizationInfoResourceIT {

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
    private COrganizationInfoRepository cOrganizationInfoRepository;

    @Autowired
    private COrganizationInfoMapper cOrganizationInfoMapper;

    @Autowired
    private COrganizationInfoService cOrganizationInfoService;

    @Autowired
    private COrganizationInfoQueryService cOrganizationInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCOrganizationInfoMockMvc;

    private COrganizationInfo cOrganizationInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static COrganizationInfo createEntity(EntityManager em) {
        COrganizationInfo cOrganizationInfo = new COrganizationInfo()
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
        cOrganizationInfo.setAdOrganization(aDOrganization);
        // Add required entity
        COrganization cOrganization;
        if (TestUtil.findAll(em, COrganization.class).isEmpty()) {
            cOrganization = COrganizationResourceIT.createEntity(em);
            em.persist(cOrganization);
            em.flush();
        } else {
            cOrganization = TestUtil.findAll(em, COrganization.class).get(0);
        }
        cOrganizationInfo.setParentOrganization(cOrganization);
        return cOrganizationInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static COrganizationInfo createUpdatedEntity(EntityManager em) {
        COrganizationInfo cOrganizationInfo = new COrganizationInfo()
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
        cOrganizationInfo.setAdOrganization(aDOrganization);
        // Add required entity
        COrganization cOrganization;
        if (TestUtil.findAll(em, COrganization.class).isEmpty()) {
            cOrganization = COrganizationResourceIT.createUpdatedEntity(em);
            em.persist(cOrganization);
            em.flush();
        } else {
            cOrganization = TestUtil.findAll(em, COrganization.class).get(0);
        }
        cOrganizationInfo.setParentOrganization(cOrganization);
        return cOrganizationInfo;
    }

    @BeforeEach
    public void initTest() {
        cOrganizationInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCOrganizationInfo() throws Exception {
        int databaseSizeBeforeCreate = cOrganizationInfoRepository.findAll().size();

        // Create the COrganizationInfo
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(cOrganizationInfo);
        restCOrganizationInfoMockMvc.perform(post("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the COrganizationInfo in the database
        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeCreate + 1);
        COrganizationInfo testCOrganizationInfo = cOrganizationInfoList.get(cOrganizationInfoList.size() - 1);
        assertThat(testCOrganizationInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCOrganizationInfo.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testCOrganizationInfo.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testCOrganizationInfo.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCOrganizationInfo.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCOrganizationInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cOrganizationInfoRepository.findAll().size();

        // Create the COrganizationInfo with an existing ID
        cOrganizationInfo.setId(1L);
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(cOrganizationInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCOrganizationInfoMockMvc.perform(post("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the COrganizationInfo in the database
        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = cOrganizationInfoRepository.findAll().size();
        // set the field null
        cOrganizationInfo.setAddress(null);

        // Create the COrganizationInfo, which fails.
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(cOrganizationInfo);

        restCOrganizationInfoMockMvc.perform(post("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cOrganizationInfoRepository.findAll().size();
        // set the field null
        cOrganizationInfo.setTaxId(null);

        // Create the COrganizationInfo, which fails.
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(cOrganizationInfo);

        restCOrganizationInfoMockMvc.perform(post("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfos() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOrganizationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCOrganizationInfo() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get the cOrganizationInfo
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos/{id}", cOrganizationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cOrganizationInfo.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCOrganizationInfosByIdFiltering() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        Long id = cOrganizationInfo.getId();

        defaultCOrganizationInfoShouldBeFound("id.equals=" + id);
        defaultCOrganizationInfoShouldNotBeFound("id.notEquals=" + id);

        defaultCOrganizationInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCOrganizationInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultCOrganizationInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCOrganizationInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address equals to DEFAULT_ADDRESS
        defaultCOrganizationInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the cOrganizationInfoList where address equals to UPDATED_ADDRESS
        defaultCOrganizationInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address not equals to DEFAULT_ADDRESS
        defaultCOrganizationInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the cOrganizationInfoList where address not equals to UPDATED_ADDRESS
        defaultCOrganizationInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCOrganizationInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the cOrganizationInfoList where address equals to UPDATED_ADDRESS
        defaultCOrganizationInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address is not null
        defaultCOrganizationInfoShouldBeFound("address.specified=true");

        // Get all the cOrganizationInfoList where address is null
        defaultCOrganizationInfoShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address contains DEFAULT_ADDRESS
        defaultCOrganizationInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the cOrganizationInfoList where address contains UPDATED_ADDRESS
        defaultCOrganizationInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where address does not contain DEFAULT_ADDRESS
        defaultCOrganizationInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the cOrganizationInfoList where address does not contain UPDATED_ADDRESS
        defaultCOrganizationInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId equals to DEFAULT_TAX_ID
        defaultCOrganizationInfoShouldBeFound("taxId.equals=" + DEFAULT_TAX_ID);

        // Get all the cOrganizationInfoList where taxId equals to UPDATED_TAX_ID
        defaultCOrganizationInfoShouldNotBeFound("taxId.equals=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId not equals to DEFAULT_TAX_ID
        defaultCOrganizationInfoShouldNotBeFound("taxId.notEquals=" + DEFAULT_TAX_ID);

        // Get all the cOrganizationInfoList where taxId not equals to UPDATED_TAX_ID
        defaultCOrganizationInfoShouldBeFound("taxId.notEquals=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId in DEFAULT_TAX_ID or UPDATED_TAX_ID
        defaultCOrganizationInfoShouldBeFound("taxId.in=" + DEFAULT_TAX_ID + "," + UPDATED_TAX_ID);

        // Get all the cOrganizationInfoList where taxId equals to UPDATED_TAX_ID
        defaultCOrganizationInfoShouldNotBeFound("taxId.in=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId is not null
        defaultCOrganizationInfoShouldBeFound("taxId.specified=true");

        // Get all the cOrganizationInfoList where taxId is null
        defaultCOrganizationInfoShouldNotBeFound("taxId.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId contains DEFAULT_TAX_ID
        defaultCOrganizationInfoShouldBeFound("taxId.contains=" + DEFAULT_TAX_ID);

        // Get all the cOrganizationInfoList where taxId contains UPDATED_TAX_ID
        defaultCOrganizationInfoShouldNotBeFound("taxId.contains=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByTaxIdNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where taxId does not contain DEFAULT_TAX_ID
        defaultCOrganizationInfoShouldNotBeFound("taxId.doesNotContain=" + DEFAULT_TAX_ID);

        // Get all the cOrganizationInfoList where taxId does not contain UPDATED_TAX_ID
        defaultCOrganizationInfoShouldBeFound("taxId.doesNotContain=" + UPDATED_TAX_ID);
    }


    @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultCOrganizationInfoShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the cOrganizationInfoList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the cOrganizationInfoList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the cOrganizationInfoList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount is not null
        defaultCOrganizationInfoShouldBeFound("bankAccount.specified=true");

        // Get all the cOrganizationInfoList where bankAccount is null
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultCOrganizationInfoShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the cOrganizationInfoList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultCOrganizationInfoShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the cOrganizationInfoList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultCOrganizationInfoShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllCOrganizationInfosByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where uid equals to DEFAULT_UID
        defaultCOrganizationInfoShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cOrganizationInfoList where uid equals to UPDATED_UID
        defaultCOrganizationInfoShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where uid not equals to DEFAULT_UID
        defaultCOrganizationInfoShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cOrganizationInfoList where uid not equals to UPDATED_UID
        defaultCOrganizationInfoShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where uid in DEFAULT_UID or UPDATED_UID
        defaultCOrganizationInfoShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cOrganizationInfoList where uid equals to UPDATED_UID
        defaultCOrganizationInfoShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where uid is not null
        defaultCOrganizationInfoShouldBeFound("uid.specified=true");

        // Get all the cOrganizationInfoList where uid is null
        defaultCOrganizationInfoShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where active equals to DEFAULT_ACTIVE
        defaultCOrganizationInfoShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cOrganizationInfoList where active equals to UPDATED_ACTIVE
        defaultCOrganizationInfoShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where active not equals to DEFAULT_ACTIVE
        defaultCOrganizationInfoShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cOrganizationInfoList where active not equals to UPDATED_ACTIVE
        defaultCOrganizationInfoShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCOrganizationInfoShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cOrganizationInfoList where active equals to UPDATED_ACTIVE
        defaultCOrganizationInfoShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        // Get all the cOrganizationInfoList where active is not null
        defaultCOrganizationInfoShouldBeFound("active.specified=true");

        // Get all the cOrganizationInfoList where active is null
        defaultCOrganizationInfoShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCOrganizationInfosByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cOrganizationInfo.getAdOrganization();
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cOrganizationInfoList where adOrganization equals to adOrganizationId
        defaultCOrganizationInfoShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cOrganizationInfoList where adOrganization equals to adOrganizationId + 1
        defaultCOrganizationInfoShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCOrganizationInfosByParentOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        COrganization parentOrganization = cOrganizationInfo.getParentOrganization();
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);
        Long parentOrganizationId = parentOrganization.getId();

        // Get all the cOrganizationInfoList where parentOrganization equals to parentOrganizationId
        defaultCOrganizationInfoShouldBeFound("parentOrganizationId.equals=" + parentOrganizationId);

        // Get all the cOrganizationInfoList where parentOrganization equals to parentOrganizationId + 1
        defaultCOrganizationInfoShouldNotBeFound("parentOrganizationId.equals=" + (parentOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCOrganizationInfoShouldBeFound(String filter) throws Exception {
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOrganizationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCOrganizationInfoShouldNotBeFound(String filter) throws Exception {
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCOrganizationInfo() throws Exception {
        // Get the cOrganizationInfo
        restCOrganizationInfoMockMvc.perform(get("/api/c-organization-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCOrganizationInfo() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        int databaseSizeBeforeUpdate = cOrganizationInfoRepository.findAll().size();

        // Update the cOrganizationInfo
        COrganizationInfo updatedCOrganizationInfo = cOrganizationInfoRepository.findById(cOrganizationInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCOrganizationInfo are not directly saved in db
        em.detach(updatedCOrganizationInfo);
        updatedCOrganizationInfo
            .address(UPDATED_ADDRESS)
            .taxId(UPDATED_TAX_ID)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(updatedCOrganizationInfo);

        restCOrganizationInfoMockMvc.perform(put("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isOk());

        // Validate the COrganizationInfo in the database
        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeUpdate);
        COrganizationInfo testCOrganizationInfo = cOrganizationInfoList.get(cOrganizationInfoList.size() - 1);
        assertThat(testCOrganizationInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCOrganizationInfo.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testCOrganizationInfo.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testCOrganizationInfo.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCOrganizationInfo.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCOrganizationInfo() throws Exception {
        int databaseSizeBeforeUpdate = cOrganizationInfoRepository.findAll().size();

        // Create the COrganizationInfo
        COrganizationInfoDTO cOrganizationInfoDTO = cOrganizationInfoMapper.toDto(cOrganizationInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCOrganizationInfoMockMvc.perform(put("/api/c-organization-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cOrganizationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the COrganizationInfo in the database
        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCOrganizationInfo() throws Exception {
        // Initialize the database
        cOrganizationInfoRepository.saveAndFlush(cOrganizationInfo);

        int databaseSizeBeforeDelete = cOrganizationInfoRepository.findAll().size();

        // Delete the cOrganizationInfo
        restCOrganizationInfoMockMvc.perform(delete("/api/c-organization-infos/{id}", cOrganizationInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<COrganizationInfo> cOrganizationInfoList = cOrganizationInfoRepository.findAll();
        assertThat(cOrganizationInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
