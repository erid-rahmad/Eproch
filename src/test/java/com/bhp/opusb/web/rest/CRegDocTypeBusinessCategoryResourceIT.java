package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CRegDocTypeBusinessCategory;
import com.bhp.opusb.domain.CRegistrationDocType;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CRegDocTypeBusinessCategoryRepository;
import com.bhp.opusb.service.CRegDocTypeBusinessCategoryService;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CRegDocTypeBusinessCategoryMapper;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryCriteria;
import com.bhp.opusb.service.CRegDocTypeBusinessCategoryQueryService;

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
 * Integration tests for the {@link CRegDocTypeBusinessCategoryResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CRegDocTypeBusinessCategoryResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CRegDocTypeBusinessCategoryRepository cRegDocTypeBusinessCategoryRepository;

    @Autowired
    private CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper;

    @Autowired
    private CRegDocTypeBusinessCategoryService cRegDocTypeBusinessCategoryService;

    @Autowired
    private CRegDocTypeBusinessCategoryQueryService cRegDocTypeBusinessCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCRegDocTypeBusinessCategoryMockMvc;

    private CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegDocTypeBusinessCategory createEntity(EntityManager em) {
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory = new CRegDocTypeBusinessCategory()
            .uid(DEFAULT_UID)
            .mandatory(DEFAULT_MANDATORY)
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
        cRegDocTypeBusinessCategory.setAdOrganization(aDOrganization);
        return cRegDocTypeBusinessCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegDocTypeBusinessCategory createUpdatedEntity(EntityManager em) {
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory = new CRegDocTypeBusinessCategory()
            .uid(UPDATED_UID)
            .mandatory(UPDATED_MANDATORY)
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
        cRegDocTypeBusinessCategory.setAdOrganization(aDOrganization);
        return cRegDocTypeBusinessCategory;
    }

    @BeforeEach
    public void initTest() {
        cRegDocTypeBusinessCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRegDocTypeBusinessCategory() throws Exception {
        int databaseSizeBeforeCreate = cRegDocTypeBusinessCategoryRepository.findAll().size();

        // Create the CRegDocTypeBusinessCategory
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO = cRegDocTypeBusinessCategoryMapper.toDto(cRegDocTypeBusinessCategory);
        restCRegDocTypeBusinessCategoryMockMvc.perform(post("/api/c-reg-doc-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegDocTypeBusinessCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CRegDocTypeBusinessCategory in the database
        List<CRegDocTypeBusinessCategory> cRegDocTypeBusinessCategoryList = cRegDocTypeBusinessCategoryRepository.findAll();
        assertThat(cRegDocTypeBusinessCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CRegDocTypeBusinessCategory testCRegDocTypeBusinessCategory = cRegDocTypeBusinessCategoryList.get(cRegDocTypeBusinessCategoryList.size() - 1);
        assertThat(testCRegDocTypeBusinessCategory.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCRegDocTypeBusinessCategory.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testCRegDocTypeBusinessCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCRegDocTypeBusinessCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRegDocTypeBusinessCategoryRepository.findAll().size();

        // Create the CRegDocTypeBusinessCategory with an existing ID
        cRegDocTypeBusinessCategory.setId(1L);
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO = cRegDocTypeBusinessCategoryMapper.toDto(cRegDocTypeBusinessCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRegDocTypeBusinessCategoryMockMvc.perform(post("/api/c-reg-doc-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegDocTypeBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegDocTypeBusinessCategory in the database
        List<CRegDocTypeBusinessCategory> cRegDocTypeBusinessCategoryList = cRegDocTypeBusinessCategoryRepository.findAll();
        assertThat(cRegDocTypeBusinessCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategories() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegDocTypeBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCRegDocTypeBusinessCategory() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get the cRegDocTypeBusinessCategory
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories/{id}", cRegDocTypeBusinessCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cRegDocTypeBusinessCategory.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCRegDocTypeBusinessCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        Long id = cRegDocTypeBusinessCategory.getId();

        defaultCRegDocTypeBusinessCategoryShouldBeFound("id.equals=" + id);
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCRegDocTypeBusinessCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCRegDocTypeBusinessCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where uid equals to DEFAULT_UID
        defaultCRegDocTypeBusinessCategoryShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cRegDocTypeBusinessCategoryList where uid equals to UPDATED_UID
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where uid not equals to DEFAULT_UID
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cRegDocTypeBusinessCategoryList where uid not equals to UPDATED_UID
        defaultCRegDocTypeBusinessCategoryShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where uid in DEFAULT_UID or UPDATED_UID
        defaultCRegDocTypeBusinessCategoryShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cRegDocTypeBusinessCategoryList where uid equals to UPDATED_UID
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where uid is not null
        defaultCRegDocTypeBusinessCategoryShouldBeFound("uid.specified=true");

        // Get all the cRegDocTypeBusinessCategoryList where uid is null
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory equals to DEFAULT_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory equals to UPDATED_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByMandatoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory not equals to DEFAULT_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("mandatory.notEquals=" + DEFAULT_MANDATORY);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory not equals to UPDATED_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldBeFound("mandatory.notEquals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory equals to UPDATED_MANDATORY
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where mandatory is not null
        defaultCRegDocTypeBusinessCategoryShouldBeFound("mandatory.specified=true");

        // Get all the cRegDocTypeBusinessCategoryList where mandatory is null
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where active equals to DEFAULT_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cRegDocTypeBusinessCategoryList where active equals to UPDATED_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where active not equals to DEFAULT_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cRegDocTypeBusinessCategoryList where active not equals to UPDATED_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cRegDocTypeBusinessCategoryList where active equals to UPDATED_ACTIVE
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        // Get all the cRegDocTypeBusinessCategoryList where active is not null
        defaultCRegDocTypeBusinessCategoryShouldBeFound("active.specified=true");

        // Get all the cRegDocTypeBusinessCategoryList where active is null
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);
        CRegistrationDocType documentType = CRegistrationDocTypeResourceIT.createEntity(em);
        em.persist(documentType);
        em.flush();
        cRegDocTypeBusinessCategory.setDocumentType(documentType);
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);
        Long documentTypeId = documentType.getId();

        // Get all the cRegDocTypeBusinessCategoryList where documentType equals to documentTypeId
        defaultCRegDocTypeBusinessCategoryShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the cRegDocTypeBusinessCategoryList where documentType equals to documentTypeId + 1
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);
        CBusinessCategory businessCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(businessCategory);
        em.flush();
        cRegDocTypeBusinessCategory.setBusinessCategory(businessCategory);
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);
        Long businessCategoryId = businessCategory.getId();

        // Get all the cRegDocTypeBusinessCategoryList where businessCategory equals to businessCategoryId
        defaultCRegDocTypeBusinessCategoryShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the cRegDocTypeBusinessCategoryList where businessCategory equals to businessCategoryId + 1
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegDocTypeBusinessCategoriesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cRegDocTypeBusinessCategory.getAdOrganization();
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cRegDocTypeBusinessCategoryList where adOrganization equals to adOrganizationId
        defaultCRegDocTypeBusinessCategoryShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cRegDocTypeBusinessCategoryList where adOrganization equals to adOrganizationId + 1
        defaultCRegDocTypeBusinessCategoryShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCRegDocTypeBusinessCategoryShouldBeFound(String filter) throws Exception {
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegDocTypeBusinessCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCRegDocTypeBusinessCategoryShouldNotBeFound(String filter) throws Exception {
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCRegDocTypeBusinessCategory() throws Exception {
        // Get the cRegDocTypeBusinessCategory
        restCRegDocTypeBusinessCategoryMockMvc.perform(get("/api/c-reg-doc-type-business-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRegDocTypeBusinessCategory() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        int databaseSizeBeforeUpdate = cRegDocTypeBusinessCategoryRepository.findAll().size();

        // Update the cRegDocTypeBusinessCategory
        CRegDocTypeBusinessCategory updatedCRegDocTypeBusinessCategory = cRegDocTypeBusinessCategoryRepository.findById(cRegDocTypeBusinessCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCRegDocTypeBusinessCategory are not directly saved in db
        em.detach(updatedCRegDocTypeBusinessCategory);
        updatedCRegDocTypeBusinessCategory
            .uid(UPDATED_UID)
            .mandatory(UPDATED_MANDATORY)
            .active(UPDATED_ACTIVE);
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO = cRegDocTypeBusinessCategoryMapper.toDto(updatedCRegDocTypeBusinessCategory);

        restCRegDocTypeBusinessCategoryMockMvc.perform(put("/api/c-reg-doc-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegDocTypeBusinessCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CRegDocTypeBusinessCategory in the database
        List<CRegDocTypeBusinessCategory> cRegDocTypeBusinessCategoryList = cRegDocTypeBusinessCategoryRepository.findAll();
        assertThat(cRegDocTypeBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
        CRegDocTypeBusinessCategory testCRegDocTypeBusinessCategory = cRegDocTypeBusinessCategoryList.get(cRegDocTypeBusinessCategoryList.size() - 1);
        assertThat(testCRegDocTypeBusinessCategory.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCRegDocTypeBusinessCategory.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testCRegDocTypeBusinessCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCRegDocTypeBusinessCategory() throws Exception {
        int databaseSizeBeforeUpdate = cRegDocTypeBusinessCategoryRepository.findAll().size();

        // Create the CRegDocTypeBusinessCategory
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO = cRegDocTypeBusinessCategoryMapper.toDto(cRegDocTypeBusinessCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRegDocTypeBusinessCategoryMockMvc.perform(put("/api/c-reg-doc-type-business-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegDocTypeBusinessCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegDocTypeBusinessCategory in the database
        List<CRegDocTypeBusinessCategory> cRegDocTypeBusinessCategoryList = cRegDocTypeBusinessCategoryRepository.findAll();
        assertThat(cRegDocTypeBusinessCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCRegDocTypeBusinessCategory() throws Exception {
        // Initialize the database
        cRegDocTypeBusinessCategoryRepository.saveAndFlush(cRegDocTypeBusinessCategory);

        int databaseSizeBeforeDelete = cRegDocTypeBusinessCategoryRepository.findAll().size();

        // Delete the cRegDocTypeBusinessCategory
        restCRegDocTypeBusinessCategoryMockMvc.perform(delete("/api/c-reg-doc-type-business-categories/{id}", cRegDocTypeBusinessCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRegDocTypeBusinessCategory> cRegDocTypeBusinessCategoryList = cRegDocTypeBusinessCategoryRepository.findAll();
        assertThat(cRegDocTypeBusinessCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
