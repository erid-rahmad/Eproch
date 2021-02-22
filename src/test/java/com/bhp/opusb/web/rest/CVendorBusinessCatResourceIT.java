package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CVendorBusinessCat;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CVendorBusinessCatRepository;
import com.bhp.opusb.service.CVendorBusinessCatService;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.mapper.CVendorBusinessCatMapper;
import com.bhp.opusb.service.dto.CVendorBusinessCatCriteria;
import com.bhp.opusb.service.CVendorBusinessCatQueryService;

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
 * Integration tests for the {@link CVendorBusinessCatResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CVendorBusinessCatResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CVendorBusinessCatRepository cVendorBusinessCatRepository;

    @Autowired
    private CVendorBusinessCatMapper cVendorBusinessCatMapper;

    @Autowired
    private CVendorBusinessCatService cVendorBusinessCatService;

    @Autowired
    private CVendorBusinessCatQueryService cVendorBusinessCatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVendorBusinessCatMockMvc;

    private CVendorBusinessCat cVendorBusinessCat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorBusinessCat createEntity(EntityManager em) {
        CVendorBusinessCat cVendorBusinessCat = new CVendorBusinessCat()
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
        cVendorBusinessCat.setVendor(cVendor);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        cVendorBusinessCat.setBusinessClassification(cBusinessCategory);
        // Add required entity
        cVendorBusinessCat.setBusinessCategory(cBusinessCategory);
        // Add required entity
        cVendorBusinessCat.setSubBusinessCategory(cBusinessCategory);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorBusinessCat.setAdOrganization(aDOrganization);
        return cVendorBusinessCat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVendorBusinessCat createUpdatedEntity(EntityManager em) {
        CVendorBusinessCat cVendorBusinessCat = new CVendorBusinessCat()
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
        cVendorBusinessCat.setVendor(cVendor);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        cVendorBusinessCat.setBusinessClassification(cBusinessCategory);
        // Add required entity
        cVendorBusinessCat.setBusinessCategory(cBusinessCategory);
        // Add required entity
        cVendorBusinessCat.setSubBusinessCategory(cBusinessCategory);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cVendorBusinessCat.setAdOrganization(aDOrganization);
        return cVendorBusinessCat;
    }

    @BeforeEach
    public void initTest() {
        cVendorBusinessCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCVendorBusinessCat() throws Exception {
        int databaseSizeBeforeCreate = cVendorBusinessCatRepository.findAll().size();

        // Create the CVendorBusinessCat
        CVendorBusinessCatDTO cVendorBusinessCatDTO = cVendorBusinessCatMapper.toDto(cVendorBusinessCat);
        restCVendorBusinessCatMockMvc.perform(post("/api/c-vendor-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBusinessCatDTO)))
            .andExpect(status().isCreated());

        // Validate the CVendorBusinessCat in the database
        List<CVendorBusinessCat> cVendorBusinessCatList = cVendorBusinessCatRepository.findAll();
        assertThat(cVendorBusinessCatList).hasSize(databaseSizeBeforeCreate + 1);
        CVendorBusinessCat testCVendorBusinessCat = cVendorBusinessCatList.get(cVendorBusinessCatList.size() - 1);
        assertThat(testCVendorBusinessCat.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCVendorBusinessCat.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCVendorBusinessCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVendorBusinessCatRepository.findAll().size();

        // Create the CVendorBusinessCat with an existing ID
        cVendorBusinessCat.setId(1L);
        CVendorBusinessCatDTO cVendorBusinessCatDTO = cVendorBusinessCatMapper.toDto(cVendorBusinessCat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVendorBusinessCatMockMvc.perform(post("/api/c-vendor-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBusinessCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorBusinessCat in the database
        List<CVendorBusinessCat> cVendorBusinessCatList = cVendorBusinessCatRepository.findAll();
        assertThat(cVendorBusinessCatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCats() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorBusinessCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCVendorBusinessCat() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get the cVendorBusinessCat
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats/{id}", cVendorBusinessCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVendorBusinessCat.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCVendorBusinessCatsByIdFiltering() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        Long id = cVendorBusinessCat.getId();

        defaultCVendorBusinessCatShouldBeFound("id.equals=" + id);
        defaultCVendorBusinessCatShouldNotBeFound("id.notEquals=" + id);

        defaultCVendorBusinessCatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVendorBusinessCatShouldNotBeFound("id.greaterThan=" + id);

        defaultCVendorBusinessCatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVendorBusinessCatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where uid equals to DEFAULT_UID
        defaultCVendorBusinessCatShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cVendorBusinessCatList where uid equals to UPDATED_UID
        defaultCVendorBusinessCatShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where uid not equals to DEFAULT_UID
        defaultCVendorBusinessCatShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cVendorBusinessCatList where uid not equals to UPDATED_UID
        defaultCVendorBusinessCatShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where uid in DEFAULT_UID or UPDATED_UID
        defaultCVendorBusinessCatShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cVendorBusinessCatList where uid equals to UPDATED_UID
        defaultCVendorBusinessCatShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where uid is not null
        defaultCVendorBusinessCatShouldBeFound("uid.specified=true");

        // Get all the cVendorBusinessCatList where uid is null
        defaultCVendorBusinessCatShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where active equals to DEFAULT_ACTIVE
        defaultCVendorBusinessCatShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cVendorBusinessCatList where active equals to UPDATED_ACTIVE
        defaultCVendorBusinessCatShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where active not equals to DEFAULT_ACTIVE
        defaultCVendorBusinessCatShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cVendorBusinessCatList where active not equals to UPDATED_ACTIVE
        defaultCVendorBusinessCatShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCVendorBusinessCatShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cVendorBusinessCatList where active equals to UPDATED_ACTIVE
        defaultCVendorBusinessCatShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        // Get all the cVendorBusinessCatList where active is not null
        defaultCVendorBusinessCatShouldBeFound("active.specified=true");

        // Get all the cVendorBusinessCatList where active is null
        defaultCVendorBusinessCatShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = cVendorBusinessCat.getVendor();
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);
        Long vendorId = vendor.getId();

        // Get all the cVendorBusinessCatList where vendor equals to vendorId
        defaultCVendorBusinessCatShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cVendorBusinessCatList where vendor equals to vendorId + 1
        defaultCVendorBusinessCatShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByBusinessClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessClassification = cVendorBusinessCat.getBusinessClassification();
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);
        Long businessClassificationId = businessClassification.getId();

        // Get all the cVendorBusinessCatList where businessClassification equals to businessClassificationId
        defaultCVendorBusinessCatShouldBeFound("businessClassificationId.equals=" + businessClassificationId);

        // Get all the cVendorBusinessCatList where businessClassification equals to businessClassificationId + 1
        defaultCVendorBusinessCatShouldNotBeFound("businessClassificationId.equals=" + (businessClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = cVendorBusinessCat.getBusinessCategory();
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);
        Long businessCategoryId = businessCategory.getId();

        // Get all the cVendorBusinessCatList where businessCategory equals to businessCategoryId
        defaultCVendorBusinessCatShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the cVendorBusinessCatList where businessCategory equals to businessCategoryId + 1
        defaultCVendorBusinessCatShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCatsBySubBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory subBusinessCategory = cVendorBusinessCat.getSubBusinessCategory();
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);
        Long subBusinessCategoryId = subBusinessCategory.getId();

        // Get all the cVendorBusinessCatList where subBusinessCategory equals to subBusinessCategoryId
        defaultCVendorBusinessCatShouldBeFound("subBusinessCategoryId.equals=" + subBusinessCategoryId);

        // Get all the cVendorBusinessCatList where subBusinessCategory equals to subBusinessCategoryId + 1
        defaultCVendorBusinessCatShouldNotBeFound("subBusinessCategoryId.equals=" + (subBusinessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCVendorBusinessCatsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cVendorBusinessCat.getAdOrganization();
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cVendorBusinessCatList where adOrganization equals to adOrganizationId
        defaultCVendorBusinessCatShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cVendorBusinessCatList where adOrganization equals to adOrganizationId + 1
        defaultCVendorBusinessCatShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVendorBusinessCatShouldBeFound(String filter) throws Exception {
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVendorBusinessCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVendorBusinessCatShouldNotBeFound(String filter) throws Exception {
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCVendorBusinessCat() throws Exception {
        // Get the cVendorBusinessCat
        restCVendorBusinessCatMockMvc.perform(get("/api/c-vendor-business-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCVendorBusinessCat() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        int databaseSizeBeforeUpdate = cVendorBusinessCatRepository.findAll().size();

        // Update the cVendorBusinessCat
        CVendorBusinessCat updatedCVendorBusinessCat = cVendorBusinessCatRepository.findById(cVendorBusinessCat.getId()).get();
        // Disconnect from session so that the updates on updatedCVendorBusinessCat are not directly saved in db
        em.detach(updatedCVendorBusinessCat);
        updatedCVendorBusinessCat
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CVendorBusinessCatDTO cVendorBusinessCatDTO = cVendorBusinessCatMapper.toDto(updatedCVendorBusinessCat);

        restCVendorBusinessCatMockMvc.perform(put("/api/c-vendor-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBusinessCatDTO)))
            .andExpect(status().isOk());

        // Validate the CVendorBusinessCat in the database
        List<CVendorBusinessCat> cVendorBusinessCatList = cVendorBusinessCatRepository.findAll();
        assertThat(cVendorBusinessCatList).hasSize(databaseSizeBeforeUpdate);
        CVendorBusinessCat testCVendorBusinessCat = cVendorBusinessCatList.get(cVendorBusinessCatList.size() - 1);
        assertThat(testCVendorBusinessCat.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCVendorBusinessCat.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCVendorBusinessCat() throws Exception {
        int databaseSizeBeforeUpdate = cVendorBusinessCatRepository.findAll().size();

        // Create the CVendorBusinessCat
        CVendorBusinessCatDTO cVendorBusinessCatDTO = cVendorBusinessCatMapper.toDto(cVendorBusinessCat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVendorBusinessCatMockMvc.perform(put("/api/c-vendor-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cVendorBusinessCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVendorBusinessCat in the database
        List<CVendorBusinessCat> cVendorBusinessCatList = cVendorBusinessCatRepository.findAll();
        assertThat(cVendorBusinessCatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCVendorBusinessCat() throws Exception {
        // Initialize the database
        cVendorBusinessCatRepository.saveAndFlush(cVendorBusinessCat);

        int databaseSizeBeforeDelete = cVendorBusinessCatRepository.findAll().size();

        // Delete the cVendorBusinessCat
        restCVendorBusinessCatMockMvc.perform(delete("/api/c-vendor-business-cats/{id}", cVendorBusinessCat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVendorBusinessCat> cVendorBusinessCatList = cVendorBusinessCatRepository.findAll();
        assertThat(cVendorBusinessCatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
