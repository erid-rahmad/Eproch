package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalificationMethod;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPrequalificationMethodRepository;
import com.bhp.opusb.service.CPrequalificationMethodService;
import com.bhp.opusb.service.dto.CPrequalificationMethodDTO;
import com.bhp.opusb.service.mapper.CPrequalificationMethodMapper;
import com.bhp.opusb.service.dto.CPrequalificationMethodCriteria;
import com.bhp.opusb.service.CPrequalificationMethodQueryService;

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
 * Integration tests for the {@link CPrequalificationMethodResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalificationMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalificationMethodRepository cPrequalificationMethodRepository;

    @Autowired
    private CPrequalificationMethodMapper cPrequalificationMethodMapper;

    @Autowired
    private CPrequalificationMethodService cPrequalificationMethodService;

    @Autowired
    private CPrequalificationMethodQueryService cPrequalificationMethodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalificationMethodMockMvc;

    private CPrequalificationMethod cPrequalificationMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationMethod createEntity(EntityManager em) {
        CPrequalificationMethod cPrequalificationMethod = new CPrequalificationMethod()
            .name(DEFAULT_NAME)
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
        cPrequalificationMethod.setAdOrganization(aDOrganization);
        return cPrequalificationMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationMethod createUpdatedEntity(EntityManager em) {
        CPrequalificationMethod cPrequalificationMethod = new CPrequalificationMethod()
            .name(UPDATED_NAME)
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
        cPrequalificationMethod.setAdOrganization(aDOrganization);
        return cPrequalificationMethod;
    }

    @BeforeEach
    public void initTest() {
        cPrequalificationMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalificationMethod() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationMethodRepository.findAll().size();

        // Create the CPrequalificationMethod
        CPrequalificationMethodDTO cPrequalificationMethodDTO = cPrequalificationMethodMapper.toDto(cPrequalificationMethod);
        restCPrequalificationMethodMockMvc.perform(post("/api/c-prequalification-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalificationMethod in the database
        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalificationMethod testCPrequalificationMethod = cPrequalificationMethodList.get(cPrequalificationMethodList.size() - 1);
        assertThat(testCPrequalificationMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCPrequalificationMethod.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalificationMethod.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalificationMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationMethodRepository.findAll().size();

        // Create the CPrequalificationMethod with an existing ID
        cPrequalificationMethod.setId(1L);
        CPrequalificationMethodDTO cPrequalificationMethodDTO = cPrequalificationMethodMapper.toDto(cPrequalificationMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalificationMethodMockMvc.perform(post("/api/c-prequalification-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationMethod in the database
        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalificationMethodRepository.findAll().size();
        // set the field null
        cPrequalificationMethod.setName(null);

        // Create the CPrequalificationMethod, which fails.
        CPrequalificationMethodDTO cPrequalificationMethodDTO = cPrequalificationMethodMapper.toDto(cPrequalificationMethod);

        restCPrequalificationMethodMockMvc.perform(post("/api/c-prequalification-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationMethodDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethods() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalificationMethod() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get the cPrequalificationMethod
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods/{id}", cPrequalificationMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalificationMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalificationMethodsByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        Long id = cPrequalificationMethod.getId();

        defaultCPrequalificationMethodShouldBeFound("id.equals=" + id);
        defaultCPrequalificationMethodShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalificationMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalificationMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalificationMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalificationMethodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name equals to DEFAULT_NAME
        defaultCPrequalificationMethodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cPrequalificationMethodList where name equals to UPDATED_NAME
        defaultCPrequalificationMethodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name not equals to DEFAULT_NAME
        defaultCPrequalificationMethodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cPrequalificationMethodList where name not equals to UPDATED_NAME
        defaultCPrequalificationMethodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCPrequalificationMethodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cPrequalificationMethodList where name equals to UPDATED_NAME
        defaultCPrequalificationMethodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name is not null
        defaultCPrequalificationMethodShouldBeFound("name.specified=true");

        // Get all the cPrequalificationMethodList where name is null
        defaultCPrequalificationMethodShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name contains DEFAULT_NAME
        defaultCPrequalificationMethodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cPrequalificationMethodList where name contains UPDATED_NAME
        defaultCPrequalificationMethodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where name does not contain DEFAULT_NAME
        defaultCPrequalificationMethodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cPrequalificationMethodList where name does not contain UPDATED_NAME
        defaultCPrequalificationMethodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where uid equals to DEFAULT_UID
        defaultCPrequalificationMethodShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalificationMethodList where uid equals to UPDATED_UID
        defaultCPrequalificationMethodShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where uid not equals to DEFAULT_UID
        defaultCPrequalificationMethodShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalificationMethodList where uid not equals to UPDATED_UID
        defaultCPrequalificationMethodShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalificationMethodShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalificationMethodList where uid equals to UPDATED_UID
        defaultCPrequalificationMethodShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where uid is not null
        defaultCPrequalificationMethodShouldBeFound("uid.specified=true");

        // Get all the cPrequalificationMethodList where uid is null
        defaultCPrequalificationMethodShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where active equals to DEFAULT_ACTIVE
        defaultCPrequalificationMethodShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationMethodList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationMethodShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalificationMethodShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationMethodList where active not equals to UPDATED_ACTIVE
        defaultCPrequalificationMethodShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalificationMethodShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalificationMethodList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationMethodShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        // Get all the cPrequalificationMethodList where active is not null
        defaultCPrequalificationMethodShouldBeFound("active.specified=true");

        // Get all the cPrequalificationMethodList where active is null
        defaultCPrequalificationMethodShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationMethodsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalificationMethod.getAdOrganization();
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalificationMethodList where adOrganization equals to adOrganizationId
        defaultCPrequalificationMethodShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalificationMethodList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalificationMethodShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalificationMethodShouldBeFound(String filter) throws Exception {
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalificationMethodShouldNotBeFound(String filter) throws Exception {
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalificationMethod() throws Exception {
        // Get the cPrequalificationMethod
        restCPrequalificationMethodMockMvc.perform(get("/api/c-prequalification-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalificationMethod() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        int databaseSizeBeforeUpdate = cPrequalificationMethodRepository.findAll().size();

        // Update the cPrequalificationMethod
        CPrequalificationMethod updatedCPrequalificationMethod = cPrequalificationMethodRepository.findById(cPrequalificationMethod.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalificationMethod are not directly saved in db
        em.detach(updatedCPrequalificationMethod);
        updatedCPrequalificationMethod
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalificationMethodDTO cPrequalificationMethodDTO = cPrequalificationMethodMapper.toDto(updatedCPrequalificationMethod);

        restCPrequalificationMethodMockMvc.perform(put("/api/c-prequalification-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationMethodDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalificationMethod in the database
        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeUpdate);
        CPrequalificationMethod testCPrequalificationMethod = cPrequalificationMethodList.get(cPrequalificationMethodList.size() - 1);
        assertThat(testCPrequalificationMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCPrequalificationMethod.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalificationMethod.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalificationMethod() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalificationMethodRepository.findAll().size();

        // Create the CPrequalificationMethod
        CPrequalificationMethodDTO cPrequalificationMethodDTO = cPrequalificationMethodMapper.toDto(cPrequalificationMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalificationMethodMockMvc.perform(put("/api/c-prequalification-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationMethod in the database
        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalificationMethod() throws Exception {
        // Initialize the database
        cPrequalificationMethodRepository.saveAndFlush(cPrequalificationMethod);

        int databaseSizeBeforeDelete = cPrequalificationMethodRepository.findAll().size();

        // Delete the cPrequalificationMethod
        restCPrequalificationMethodMockMvc.perform(delete("/api/c-prequalification-methods/{id}", cPrequalificationMethod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalificationMethod> cPrequalificationMethodList = cPrequalificationMethodRepository.findAll();
        assertThat(cPrequalificationMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
