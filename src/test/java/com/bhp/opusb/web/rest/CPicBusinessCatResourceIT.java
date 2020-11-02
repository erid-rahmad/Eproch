package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPicBusinessCat;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPicBusinessCatRepository;
import com.bhp.opusb.service.CPicBusinessCatService;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;
import com.bhp.opusb.service.mapper.CPicBusinessCatMapper;
import com.bhp.opusb.service.dto.CPicBusinessCatCriteria;
import com.bhp.opusb.service.CPicBusinessCatQueryService;

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
 * Integration tests for the {@link CPicBusinessCatResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPicBusinessCatResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPicBusinessCatRepository cPicBusinessCatRepository;

    @Autowired
    private CPicBusinessCatMapper cPicBusinessCatMapper;

    @Autowired
    private CPicBusinessCatService cPicBusinessCatService;

    @Autowired
    private CPicBusinessCatQueryService cPicBusinessCatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPicBusinessCatMockMvc;

    private CPicBusinessCat cPicBusinessCat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPicBusinessCat createEntity(EntityManager em) {
        CPicBusinessCat cPicBusinessCat = new CPicBusinessCat()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        cPicBusinessCat.setPic(adUser);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        cPicBusinessCat.setBusinessCategory(cBusinessCategory);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPicBusinessCat.setAdOrganization(aDOrganization);
        return cPicBusinessCat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPicBusinessCat createUpdatedEntity(EntityManager em) {
        CPicBusinessCat cPicBusinessCat = new CPicBusinessCat()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        cPicBusinessCat.setPic(adUser);
        // Add required entity
        CBusinessCategory cBusinessCategory;
        if (TestUtil.findAll(em, CBusinessCategory.class).isEmpty()) {
            cBusinessCategory = CBusinessCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cBusinessCategory);
            em.flush();
        } else {
            cBusinessCategory = TestUtil.findAll(em, CBusinessCategory.class).get(0);
        }
        cPicBusinessCat.setBusinessCategory(cBusinessCategory);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPicBusinessCat.setAdOrganization(aDOrganization);
        return cPicBusinessCat;
    }

    @BeforeEach
    public void initTest() {
        cPicBusinessCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPicBusinessCat() throws Exception {
        int databaseSizeBeforeCreate = cPicBusinessCatRepository.findAll().size();

        // Create the CPicBusinessCat
        CPicBusinessCatDTO cPicBusinessCatDTO = cPicBusinessCatMapper.toDto(cPicBusinessCat);
        restCPicBusinessCatMockMvc.perform(post("/api/c-pic-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPicBusinessCatDTO)))
            .andExpect(status().isCreated());

        // Validate the CPicBusinessCat in the database
        List<CPicBusinessCat> cPicBusinessCatList = cPicBusinessCatRepository.findAll();
        assertThat(cPicBusinessCatList).hasSize(databaseSizeBeforeCreate + 1);
        CPicBusinessCat testCPicBusinessCat = cPicBusinessCatList.get(cPicBusinessCatList.size() - 1);
        assertThat(testCPicBusinessCat.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPicBusinessCat.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPicBusinessCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPicBusinessCatRepository.findAll().size();

        // Create the CPicBusinessCat with an existing ID
        cPicBusinessCat.setId(1L);
        CPicBusinessCatDTO cPicBusinessCatDTO = cPicBusinessCatMapper.toDto(cPicBusinessCat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPicBusinessCatMockMvc.perform(post("/api/c-pic-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPicBusinessCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPicBusinessCat in the database
        List<CPicBusinessCat> cPicBusinessCatList = cPicBusinessCatRepository.findAll();
        assertThat(cPicBusinessCatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCPicBusinessCats() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPicBusinessCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPicBusinessCat() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get the cPicBusinessCat
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats/{id}", cPicBusinessCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPicBusinessCat.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPicBusinessCatsByIdFiltering() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        Long id = cPicBusinessCat.getId();

        defaultCPicBusinessCatShouldBeFound("id.equals=" + id);
        defaultCPicBusinessCatShouldNotBeFound("id.notEquals=" + id);

        defaultCPicBusinessCatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPicBusinessCatShouldNotBeFound("id.greaterThan=" + id);

        defaultCPicBusinessCatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPicBusinessCatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPicBusinessCatsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where uid equals to DEFAULT_UID
        defaultCPicBusinessCatShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPicBusinessCatList where uid equals to UPDATED_UID
        defaultCPicBusinessCatShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where uid not equals to DEFAULT_UID
        defaultCPicBusinessCatShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPicBusinessCatList where uid not equals to UPDATED_UID
        defaultCPicBusinessCatShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPicBusinessCatShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPicBusinessCatList where uid equals to UPDATED_UID
        defaultCPicBusinessCatShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where uid is not null
        defaultCPicBusinessCatShouldBeFound("uid.specified=true");

        // Get all the cPicBusinessCatList where uid is null
        defaultCPicBusinessCatShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where active equals to DEFAULT_ACTIVE
        defaultCPicBusinessCatShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPicBusinessCatList where active equals to UPDATED_ACTIVE
        defaultCPicBusinessCatShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where active not equals to DEFAULT_ACTIVE
        defaultCPicBusinessCatShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPicBusinessCatList where active not equals to UPDATED_ACTIVE
        defaultCPicBusinessCatShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPicBusinessCatShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPicBusinessCatList where active equals to UPDATED_ACTIVE
        defaultCPicBusinessCatShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        // Get all the cPicBusinessCatList where active is not null
        defaultCPicBusinessCatShouldBeFound("active.specified=true");

        // Get all the cPicBusinessCatList where active is null
        defaultCPicBusinessCatShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPicBusinessCatsByPicIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser pic = cPicBusinessCat.getPic();
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);
        Long picId = pic.getId();

        // Get all the cPicBusinessCatList where pic equals to picId
        defaultCPicBusinessCatShouldBeFound("picId.equals=" + picId);

        // Get all the cPicBusinessCatList where pic equals to picId + 1
        defaultCPicBusinessCatShouldNotBeFound("picId.equals=" + (picId + 1));
    }


    @Test
    @Transactional
    public void getAllCPicBusinessCatsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBusinessCategory businessCategory = cPicBusinessCat.getBusinessCategory();
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);
        Long businessCategoryId = businessCategory.getId();

        // Get all the cPicBusinessCatList where businessCategory equals to businessCategoryId
        defaultCPicBusinessCatShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the cPicBusinessCatList where businessCategory equals to businessCategoryId + 1
        defaultCPicBusinessCatShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCPicBusinessCatsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPicBusinessCat.getAdOrganization();
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPicBusinessCatList where adOrganization equals to adOrganizationId
        defaultCPicBusinessCatShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPicBusinessCatList where adOrganization equals to adOrganizationId + 1
        defaultCPicBusinessCatShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPicBusinessCatShouldBeFound(String filter) throws Exception {
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPicBusinessCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPicBusinessCatShouldNotBeFound(String filter) throws Exception {
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPicBusinessCat() throws Exception {
        // Get the cPicBusinessCat
        restCPicBusinessCatMockMvc.perform(get("/api/c-pic-business-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPicBusinessCat() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        int databaseSizeBeforeUpdate = cPicBusinessCatRepository.findAll().size();

        // Update the cPicBusinessCat
        CPicBusinessCat updatedCPicBusinessCat = cPicBusinessCatRepository.findById(cPicBusinessCat.getId()).get();
        // Disconnect from session so that the updates on updatedCPicBusinessCat are not directly saved in db
        em.detach(updatedCPicBusinessCat);
        updatedCPicBusinessCat
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPicBusinessCatDTO cPicBusinessCatDTO = cPicBusinessCatMapper.toDto(updatedCPicBusinessCat);

        restCPicBusinessCatMockMvc.perform(put("/api/c-pic-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPicBusinessCatDTO)))
            .andExpect(status().isOk());

        // Validate the CPicBusinessCat in the database
        List<CPicBusinessCat> cPicBusinessCatList = cPicBusinessCatRepository.findAll();
        assertThat(cPicBusinessCatList).hasSize(databaseSizeBeforeUpdate);
        CPicBusinessCat testCPicBusinessCat = cPicBusinessCatList.get(cPicBusinessCatList.size() - 1);
        assertThat(testCPicBusinessCat.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPicBusinessCat.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPicBusinessCat() throws Exception {
        int databaseSizeBeforeUpdate = cPicBusinessCatRepository.findAll().size();

        // Create the CPicBusinessCat
        CPicBusinessCatDTO cPicBusinessCatDTO = cPicBusinessCatMapper.toDto(cPicBusinessCat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPicBusinessCatMockMvc.perform(put("/api/c-pic-business-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPicBusinessCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPicBusinessCat in the database
        List<CPicBusinessCat> cPicBusinessCatList = cPicBusinessCatRepository.findAll();
        assertThat(cPicBusinessCatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPicBusinessCat() throws Exception {
        // Initialize the database
        cPicBusinessCatRepository.saveAndFlush(cPicBusinessCat);

        int databaseSizeBeforeDelete = cPicBusinessCatRepository.findAll().size();

        // Delete the cPicBusinessCat
        restCPicBusinessCatMockMvc.perform(delete("/api/c-pic-business-cats/{id}", cPicBusinessCat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPicBusinessCat> cPicBusinessCatList = cPicBusinessCatRepository.findAll();
        assertThat(cPicBusinessCatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
