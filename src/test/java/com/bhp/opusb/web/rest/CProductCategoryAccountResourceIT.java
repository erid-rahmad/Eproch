package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProductCategoryAccount;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.repository.CProductCategoryAccountRepository;
import com.bhp.opusb.service.CProductCategoryAccountService;
import com.bhp.opusb.service.dto.CProductCategoryAccountDTO;
import com.bhp.opusb.service.mapper.CProductCategoryAccountMapper;
import com.bhp.opusb.service.dto.CProductCategoryAccountCriteria;
import com.bhp.opusb.service.CProductCategoryAccountQueryService;

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
 * Integration tests for the {@link CProductCategoryAccountResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductCategoryAccountResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductCategoryAccountRepository cProductCategoryAccountRepository;

    @Autowired
    private CProductCategoryAccountMapper cProductCategoryAccountMapper;

    @Autowired
    private CProductCategoryAccountService cProductCategoryAccountService;

    @Autowired
    private CProductCategoryAccountQueryService cProductCategoryAccountQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductCategoryAccountMockMvc;

    private CProductCategoryAccount cProductCategoryAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductCategoryAccount createEntity(EntityManager em) {
        CProductCategoryAccount cProductCategoryAccount = new CProductCategoryAccount()
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
        cProductCategoryAccount.setAdOrganization(aDOrganization);
        // Add required entity
        CElementValue cElementValue;
        if (TestUtil.findAll(em, CElementValue.class).isEmpty()) {
            cElementValue = CElementValueResourceIT.createEntity(em);
            em.persist(cElementValue);
            em.flush();
        } else {
            cElementValue = TestUtil.findAll(em, CElementValue.class).get(0);
        }
        cProductCategoryAccount.setAssetAcct(cElementValue);
        // Add required entity
        cProductCategoryAccount.setExpenseAcct(cElementValue);
        // Add required entity
        CProductCategory cProductCategory;
        if (TestUtil.findAll(em, CProductCategory.class).isEmpty()) {
            cProductCategory = CProductCategoryResourceIT.createEntity(em);
            em.persist(cProductCategory);
            em.flush();
        } else {
            cProductCategory = TestUtil.findAll(em, CProductCategory.class).get(0);
        }
        cProductCategoryAccount.setProductCategory(cProductCategory);
        return cProductCategoryAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProductCategoryAccount createUpdatedEntity(EntityManager em) {
        CProductCategoryAccount cProductCategoryAccount = new CProductCategoryAccount()
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
        cProductCategoryAccount.setAdOrganization(aDOrganization);
        // Add required entity
        CElementValue cElementValue;
        if (TestUtil.findAll(em, CElementValue.class).isEmpty()) {
            cElementValue = CElementValueResourceIT.createUpdatedEntity(em);
            em.persist(cElementValue);
            em.flush();
        } else {
            cElementValue = TestUtil.findAll(em, CElementValue.class).get(0);
        }
        cProductCategoryAccount.setAssetAcct(cElementValue);
        // Add required entity
        cProductCategoryAccount.setExpenseAcct(cElementValue);
        // Add required entity
        CProductCategory cProductCategory;
        if (TestUtil.findAll(em, CProductCategory.class).isEmpty()) {
            cProductCategory = CProductCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cProductCategory);
            em.flush();
        } else {
            cProductCategory = TestUtil.findAll(em, CProductCategory.class).get(0);
        }
        cProductCategoryAccount.setProductCategory(cProductCategory);
        return cProductCategoryAccount;
    }

    @BeforeEach
    public void initTest() {
        cProductCategoryAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProductCategoryAccount() throws Exception {
        int databaseSizeBeforeCreate = cProductCategoryAccountRepository.findAll().size();

        // Create the CProductCategoryAccount
        CProductCategoryAccountDTO cProductCategoryAccountDTO = cProductCategoryAccountMapper.toDto(cProductCategoryAccount);
        restCProductCategoryAccountMockMvc.perform(post("/api/c-product-category-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CProductCategoryAccount in the database
        List<CProductCategoryAccount> cProductCategoryAccountList = cProductCategoryAccountRepository.findAll();
        assertThat(cProductCategoryAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CProductCategoryAccount testCProductCategoryAccount = cProductCategoryAccountList.get(cProductCategoryAccountList.size() - 1);
        assertThat(testCProductCategoryAccount.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProductCategoryAccount.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductCategoryAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductCategoryAccountRepository.findAll().size();

        // Create the CProductCategoryAccount with an existing ID
        cProductCategoryAccount.setId(1L);
        CProductCategoryAccountDTO cProductCategoryAccountDTO = cProductCategoryAccountMapper.toDto(cProductCategoryAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductCategoryAccountMockMvc.perform(post("/api/c-product-category-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductCategoryAccount in the database
        List<CProductCategoryAccount> cProductCategoryAccountList = cProductCategoryAccountRepository.findAll();
        assertThat(cProductCategoryAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCProductCategoryAccounts() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductCategoryAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProductCategoryAccount() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get the cProductCategoryAccount
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts/{id}", cProductCategoryAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProductCategoryAccount.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductCategoryAccountsByIdFiltering() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        Long id = cProductCategoryAccount.getId();

        defaultCProductCategoryAccountShouldBeFound("id.equals=" + id);
        defaultCProductCategoryAccountShouldNotBeFound("id.notEquals=" + id);

        defaultCProductCategoryAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductCategoryAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductCategoryAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductCategoryAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where uid equals to DEFAULT_UID
        defaultCProductCategoryAccountShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductCategoryAccountList where uid equals to UPDATED_UID
        defaultCProductCategoryAccountShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where uid not equals to DEFAULT_UID
        defaultCProductCategoryAccountShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductCategoryAccountList where uid not equals to UPDATED_UID
        defaultCProductCategoryAccountShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductCategoryAccountShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductCategoryAccountList where uid equals to UPDATED_UID
        defaultCProductCategoryAccountShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where uid is not null
        defaultCProductCategoryAccountShouldBeFound("uid.specified=true");

        // Get all the cProductCategoryAccountList where uid is null
        defaultCProductCategoryAccountShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where active equals to DEFAULT_ACTIVE
        defaultCProductCategoryAccountShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductCategoryAccountList where active equals to UPDATED_ACTIVE
        defaultCProductCategoryAccountShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where active not equals to DEFAULT_ACTIVE
        defaultCProductCategoryAccountShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductCategoryAccountList where active not equals to UPDATED_ACTIVE
        defaultCProductCategoryAccountShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductCategoryAccountShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductCategoryAccountList where active equals to UPDATED_ACTIVE
        defaultCProductCategoryAccountShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        // Get all the cProductCategoryAccountList where active is not null
        defaultCProductCategoryAccountShouldBeFound("active.specified=true");

        // Get all the cProductCategoryAccountList where active is null
        defaultCProductCategoryAccountShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProductCategoryAccount.getAdOrganization();
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductCategoryAccountList where adOrganization equals to adOrganizationId
        defaultCProductCategoryAccountShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductCategoryAccountList where adOrganization equals to adOrganizationId + 1
        defaultCProductCategoryAccountShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByAssetAcctIsEqualToSomething() throws Exception {
        // Get already existing entity
        CElementValue assetAcct = cProductCategoryAccount.getAssetAcct();
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);
        Long assetAcctId = assetAcct.getId();

        // Get all the cProductCategoryAccountList where assetAcct equals to assetAcctId
        defaultCProductCategoryAccountShouldBeFound("assetAcctId.equals=" + assetAcctId);

        // Get all the cProductCategoryAccountList where assetAcct equals to assetAcctId + 1
        defaultCProductCategoryAccountShouldNotBeFound("assetAcctId.equals=" + (assetAcctId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByExpenseAcctIsEqualToSomething() throws Exception {
        // Get already existing entity
        CElementValue expenseAcct = cProductCategoryAccount.getExpenseAcct();
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);
        Long expenseAcctId = expenseAcct.getId();

        // Get all the cProductCategoryAccountList where expenseAcct equals to expenseAcctId
        defaultCProductCategoryAccountShouldBeFound("expenseAcctId.equals=" + expenseAcctId);

        // Get all the cProductCategoryAccountList where expenseAcct equals to expenseAcctId + 1
        defaultCProductCategoryAccountShouldNotBeFound("expenseAcctId.equals=" + (expenseAcctId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductCategoryAccountsByProductCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductCategory productCategory = cProductCategoryAccount.getProductCategory();
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);
        Long productCategoryId = productCategory.getId();

        // Get all the cProductCategoryAccountList where productCategory equals to productCategoryId
        defaultCProductCategoryAccountShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the cProductCategoryAccountList where productCategory equals to productCategoryId + 1
        defaultCProductCategoryAccountShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductCategoryAccountShouldBeFound(String filter) throws Exception {
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProductCategoryAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductCategoryAccountShouldNotBeFound(String filter) throws Exception {
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProductCategoryAccount() throws Exception {
        // Get the cProductCategoryAccount
        restCProductCategoryAccountMockMvc.perform(get("/api/c-product-category-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProductCategoryAccount() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        int databaseSizeBeforeUpdate = cProductCategoryAccountRepository.findAll().size();

        // Update the cProductCategoryAccount
        CProductCategoryAccount updatedCProductCategoryAccount = cProductCategoryAccountRepository.findById(cProductCategoryAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCProductCategoryAccount are not directly saved in db
        em.detach(updatedCProductCategoryAccount);
        updatedCProductCategoryAccount
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductCategoryAccountDTO cProductCategoryAccountDTO = cProductCategoryAccountMapper.toDto(updatedCProductCategoryAccount);

        restCProductCategoryAccountMockMvc.perform(put("/api/c-product-category-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CProductCategoryAccount in the database
        List<CProductCategoryAccount> cProductCategoryAccountList = cProductCategoryAccountRepository.findAll();
        assertThat(cProductCategoryAccountList).hasSize(databaseSizeBeforeUpdate);
        CProductCategoryAccount testCProductCategoryAccount = cProductCategoryAccountList.get(cProductCategoryAccountList.size() - 1);
        assertThat(testCProductCategoryAccount.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProductCategoryAccount.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProductCategoryAccount() throws Exception {
        int databaseSizeBeforeUpdate = cProductCategoryAccountRepository.findAll().size();

        // Create the CProductCategoryAccount
        CProductCategoryAccountDTO cProductCategoryAccountDTO = cProductCategoryAccountMapper.toDto(cProductCategoryAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductCategoryAccountMockMvc.perform(put("/api/c-product-category-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductCategoryAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProductCategoryAccount in the database
        List<CProductCategoryAccount> cProductCategoryAccountList = cProductCategoryAccountRepository.findAll();
        assertThat(cProductCategoryAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProductCategoryAccount() throws Exception {
        // Initialize the database
        cProductCategoryAccountRepository.saveAndFlush(cProductCategoryAccount);

        int databaseSizeBeforeDelete = cProductCategoryAccountRepository.findAll().size();

        // Delete the cProductCategoryAccount
        restCProductCategoryAccountMockMvc.perform(delete("/api/c-product-category-accounts/{id}", cProductCategoryAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProductCategoryAccount> cProductCategoryAccountList = cProductCategoryAccountRepository.findAll();
        assertThat(cProductCategoryAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
