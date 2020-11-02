package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorBankAcct;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CBank;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorBankAcctRepository;
import com.bhp.opusb.service.CVendorBankAcctService;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.mapper.CVendorBankAcctMapper;
import com.bhp.opusb.service.dto.CVendorBankAcctCriteria;
import com.bhp.opusb.service.CVendorBankAcctQueryService;

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
 * Integration tests for the {@link CVendorBankAcctResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorBankAcctResourceIT {

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorBankAcctRepository cVendorBankAcctRepository;

    @Autowired
    private CVendorBankAcctMapper cVendorBankAcctMapper;

    @Autowired
    private CVendorBankAcctService cVendorBankAcctService;

    @Autowired
    private CVendorBankAcctQueryService cVendorBankAcctQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorBankAcctMockMvc;

    private CVendorBankAcct cVendorBankAcct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorBankAcct createEntity(EntityManager em) {
        CVendorBankAcct cVendorBankAcct = new CVendorBankAcct()
            .accountNo(DEFAULT_ACCOUNT_NO)
            .name(DEFAULT_NAME)
            .branch(DEFAULT_BRANCH)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cVendorBankAcct.setVendor(cVendor);
        // Add required entity
        CBank cBank;
        if (TestUtil.findAll(em, CBank.class).isEmpty()) {
            cBank = CBankResourceIT.createEntity(em);
            em.persist(cBank);
            em.flush();
        } else {
            cBank = TestUtil.findAll(em, CBank.class).get(0);
        }
        cVendorBankAcct.setBank(cBank);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cVendorBankAcct.setCurrency(cCurrency);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cVendorBankAcct.setFile(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorBankAcct.setAdOrganization(aDOrganization);
        return cVendorBankAcct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorBankAcct createUpdatedEntity(EntityManager em) {
        CVendorBankAcct cVendorBankAcct = new CVendorBankAcct()
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        cVendorBankAcct.setVendor(cVendor);
        // Add required entity
        CBank cBank;
        if (TestUtil.findAll(em, CBank.class).isEmpty()) {
            cBank = CBankResourceIT.createUpdatedEntity(em);
            em.persist(cBank);
            em.flush();
        } else {
            cBank = TestUtil.findAll(em, CBank.class).get(0);
        }
        cVendorBankAcct.setBank(cBank);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        cVendorBankAcct.setCurrency(cCurrency);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cVendorBankAcct.setFile(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorBankAcct.setAdOrganization(aDOrganization);
        return cVendorBankAcct;
    }

    @BeforeEach
    public void initTest() {
        cVendorBankAcct = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorBankAcct() throws Exception {
        int databaseSizeBeforeCreate = cVendorBankAcctRepository.findAll().size();

        // Create the CVendorBankAcct
        CVendorBankAcctDTO cVendorBankAcctDTO = cVendorBankAcctMapper.toDto(cVendorBankAcct);
        restCVendorBankAcctMockMvc.perform(post("/api/c-vendor-bank-accts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBankAcctDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorBankAcct in the database
        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorBankAcct testCVendorBankAcct = cVendorBankAcctList.get(cVendorBankAcctList.size() - 1);
        assertThat(testCVendorBankAcct.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCVendorBankAcct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCVendorBankAcct.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testCVendorBankAcct.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorBankAcct.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorBankAcctWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorBankAcctRepository.findAll().size();

        // Create the CVendorBankAcct with an existing ID
        cVendorBankAcct.setId(1L);
        CVendorBankAcctDTO cVendorBankAcctDTO = cVendorBankAcctMapper.toDto(cVendorBankAcct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorBankAcctMockMvc.perform(post("/api/c-vendor-bank-accts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBankAcctDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorBankAcct in the database
        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVendorBankAcctRepository.findAll().size();
        // set the field null
        cVendorBankAcct.setAccountNo(null);

        // Create the CVendorBankAcct, which fails.
        CVendorBankAcctDTO cVendorBankAcctDTO = cVendorBankAcctMapper.toDto(cVendorBankAcct);

        restCVendorBankAcctMockMvc.perform(post("/api/c-vendor-bank-accts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBankAcctDTO)))
            .andExpect(status().isBadRequest());

        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAccts() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorBankAcct.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorBankAcct() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get the cVendorBankAcct
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts/{id}", cVendorBankAcct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorBankAcct.getId().intValue()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorBankAcctsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        Long id = cVendorBankAcct.getId();

        defaultCVendorBankAcctShouldBeFound("id.equals=" + id);
        defaultCVendorBankAcctShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorBankAcctShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorBankAcctShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorBankAcctShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorBankAcctShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultCVendorBankAcctShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the cVendorBankAcctList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo not equals to DEFAULT_ACCOUNT_NO
        defaultCVendorBankAcctShouldNotBeFound("accountNo.notEquals=" + DEFAULT_ACCOUNT_NO);

        // Get all the cVendorBankAcctList where accountNo not equals to UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldBeFound("accountNo.notEquals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the cVendorBankAcctList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo is not null
        defaultCVendorBankAcctShouldBeFound("accountNo.specified=true");

        // Get all the cVendorBankAcctList where accountNo is null
        defaultCVendorBankAcctShouldNotBeFound("accountNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultCVendorBankAcctShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the cVendorBankAcctList where accountNo contains UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultCVendorBankAcctShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the cVendorBankAcctList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultCVendorBankAcctShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name equals to DEFAULT_NAME
        defaultCVendorBankAcctShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cVendorBankAcctList where name equals to UPDATED_NAME
        defaultCVendorBankAcctShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name not equals to DEFAULT_NAME
        defaultCVendorBankAcctShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cVendorBankAcctList where name not equals to UPDATED_NAME
        defaultCVendorBankAcctShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCVendorBankAcctShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cVendorBankAcctList where name equals to UPDATED_NAME
        defaultCVendorBankAcctShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name is not null
        defaultCVendorBankAcctShouldBeFound("name.specified=true");

        // Get all the cVendorBankAcctList where name is null
        defaultCVendorBankAcctShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name contains DEFAULT_NAME
        defaultCVendorBankAcctShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cVendorBankAcctList where name contains UPDATED_NAME
        defaultCVendorBankAcctShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where name does not contain DEFAULT_NAME
        defaultCVendorBankAcctShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cVendorBankAcctList where name does not contain UPDATED_NAME
        defaultCVendorBankAcctShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch equals to DEFAULT_BRANCH
        defaultCVendorBankAcctShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the cVendorBankAcctList where branch equals to UPDATED_BRANCH
        defaultCVendorBankAcctShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch not equals to DEFAULT_BRANCH
        defaultCVendorBankAcctShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the cVendorBankAcctList where branch not equals to UPDATED_BRANCH
        defaultCVendorBankAcctShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultCVendorBankAcctShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the cVendorBankAcctList where branch equals to UPDATED_BRANCH
        defaultCVendorBankAcctShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch is not null
        defaultCVendorBankAcctShouldBeFound("branch.specified=true");

        // Get all the cVendorBankAcctList where branch is null
        defaultCVendorBankAcctShouldNotBeFound("branch.specified=false");
    }
                @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch contains DEFAULT_BRANCH
        defaultCVendorBankAcctShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the cVendorBankAcctList where branch contains UPDATED_BRANCH
        defaultCVendorBankAcctShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where branch does not contain DEFAULT_BRANCH
        defaultCVendorBankAcctShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the cVendorBankAcctList where branch does not contain UPDATED_BRANCH
        defaultCVendorBankAcctShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where uid equals to DEFAULT_UID
        defaultCVendorBankAcctShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorBankAcctList where uid equals to UPDATED_UID
        defaultCVendorBankAcctShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where uid not equals to DEFAULT_UID
        defaultCVendorBankAcctShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorBankAcctList where uid not equals to UPDATED_UID
        defaultCVendorBankAcctShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorBankAcctShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorBankAcctList where uid equals to UPDATED_UID
        defaultCVendorBankAcctShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where uid is not null
        defaultCVendorBankAcctShouldBeFound("uid.specified=true");

        // Get all the cVendorBankAcctList where uid is null
        defaultCVendorBankAcctShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where active equals to DEFAULT_ACTIVE
        defaultCVendorBankAcctShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorBankAcctList where active equals to UPDATED_ACTIVE
        defaultCVendorBankAcctShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where active not equals to DEFAULT_ACTIVE
        defaultCVendorBankAcctShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorBankAcctList where active not equals to UPDATED_ACTIVE
        defaultCVendorBankAcctShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorBankAcctShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorBankAcctList where active equals to UPDATED_ACTIVE
        defaultCVendorBankAcctShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        // Get all the cVendorBankAcctList where active is not null
        defaultCVendorBankAcctShouldBeFound("active.specified=true");

        // Get all the cVendorBankAcctList where active is null
        defaultCVendorBankAcctShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorBankAcctsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = cVendorBankAcct.getVendor();
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);
        Long vendorId = vendor.getId();

        // Get all the cVendorBankAcctList where vendor equals to vendorId
        defaultCVendorBankAcctShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cVendorBankAcctList where vendor equals to vendorId + 1
        defaultCVendorBankAcctShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByBankIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBank bank = cVendorBankAcct.getBank();
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);
        Long bankId = bank.getId();

        // Get all the cVendorBankAcctList where bank equals to bankId
        defaultCVendorBankAcctShouldBeFound("bankId.equals=" + bankId);

        // Get all the cVendorBankAcctList where bank equals to bankId + 1
        defaultCVendorBankAcctShouldNotBeFound("bankId.equals=" + (bankId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = cVendorBankAcct.getCurrency();
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);
        Long currencyId = currency.getId();

        // Get all the cVendorBankAcctList where currency equals to currencyId
        defaultCVendorBankAcctShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the cVendorBankAcctList where currency equals to currencyId + 1
        defaultCVendorBankAcctShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByFileIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment file = cVendorBankAcct.getFile();
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);
        Long fileId = file.getId();

        // Get all the cVendorBankAcctList where file equals to fileId
        defaultCVendorBankAcctShouldBeFound("fileId.equals=" + fileId);

        // Get all the cVendorBankAcctList where file equals to fileId + 1
        defaultCVendorBankAcctShouldNotBeFound("fileId.equals=" + (fileId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBankAcctsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorBankAcct.getAdOrganization();
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorBankAcctList where adOrganization equals to adOrganizationId
        defaultCVendorBankAcctShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorBankAcctList where adOrganization equals to adOrganizationId + 1
        defaultCVendorBankAcctShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorBankAcctShouldBeFound(String filter) throws Exception {
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorBankAcct.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorBankAcctShouldNotBeFound(String filter) throws Exception {
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorBankAcct() throws Exception {
        // Get the cVendorBankAcct
        restCVendorBankAcctMockMvc.perform(get("/api/c-vendor-bank-accts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorBankAcct() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        int databaseSizeBeforeUpdate = cVendorBankAcctRepository.findAll().size();

        // Update the cVendorBankAcct
        CVendorBankAcct updatedCVendorBankAcct = cVendorBankAcctRepository.findById(cVendorBankAcct.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorBankAcct are not directly saved in db
        em.detach(updatedCVendorBankAcct);
        updatedCVendorBankAcct
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorBankAcctDTO cVendorBankAcctDTO = cVendorBankAcctMapper.toDto(updatedCVendorBankAcct);

        restCVendorBankAcctMockMvc.perform(put("/api/c-vendor-bank-accts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBankAcctDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorBankAcct in the database
        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeUpdate);
        CVendorBankAcct testCVendorBankAcct = cVendorBankAcctList.get(cVendorBankAcctList.size() - 1);
        assertThat(testCVendorBankAcct.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCVendorBankAcct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCVendorBankAcct.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCVendorBankAcct.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorBankAcct.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorBankAcct() throws Exception {
        int databaseSizeBeforeUpdate = cVendorBankAcctRepository.findAll().size();

        // Create the CVendorBankAcct
        CVendorBankAcctDTO cVendorBankAcctDTO = cVendorBankAcctMapper.toDto(cVendorBankAcct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorBankAcctMockMvc.perform(put("/api/c-vendor-bank-accts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBankAcctDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorBankAcct in the database
        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorBankAcct() throws Exception {
        // Initialize the database
        cVendorBankAcctRepository.saveAndFlush(cVendorBankAcct);

        int databaseSizeBeforeDelete = cVendorBankAcctRepository.findAll().size();

        // Delete the cVendorBankAcct
        restCVendorBankAcctMockMvc.perform(delete("/api/c-vendor-bank-accts/{id}", cVendorBankAcct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorBankAcct> cVendorBankAcctList = cVendorBankAcctRepository.findAll();
        assertThat(cVendorBankAcctList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
