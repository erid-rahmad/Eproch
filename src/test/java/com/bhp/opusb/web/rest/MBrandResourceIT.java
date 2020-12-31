package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBrand;
import com.bhp.opusb.repository.MBrandRepository;
import com.bhp.opusb.service.MBrandService;
import com.bhp.opusb.service.dto.MBrandDTO;
import com.bhp.opusb.service.mapper.MBrandMapper;
import com.bhp.opusb.service.dto.MBrandCriteria;
import com.bhp.opusb.service.MBrandQueryService;

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
 * Integration tests for the {@link MBrandResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MBrandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBrandRepository mBrandRepository;

    @Autowired
    private MBrandMapper mBrandMapper;

    @Autowired
    private MBrandService mBrandService;

    @Autowired
    private MBrandQueryService mBrandQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBrandMockMvc;

    private MBrand mBrand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBrand createEntity(EntityManager em) {
        MBrand mBrand = new MBrand()
            .name(DEFAULT_NAME)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        return mBrand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBrand createUpdatedEntity(EntityManager em) {
        MBrand mBrand = new MBrand()
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        return mBrand;
    }

    @BeforeEach
    public void initTest() {
        mBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBrand() throws Exception {
        int databaseSizeBeforeCreate = mBrandRepository.findAll().size();
        // Create the MBrand
        MBrandDTO mBrandDTO = mBrandMapper.toDto(mBrand);
        restMBrandMockMvc.perform(post("/api/m-brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the MBrand in the database
        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeCreate + 1);
        MBrand testMBrand = mBrandList.get(mBrandList.size() - 1);
        assertThat(testMBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMBrand.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBrand.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBrandRepository.findAll().size();

        // Create the MBrand with an existing ID
        mBrand.setId(1L);
        MBrandDTO mBrandDTO = mBrandMapper.toDto(mBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBrandMockMvc.perform(post("/api/m-brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBrand in the database
        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBrandRepository.findAll().size();
        // set the field null
        mBrand.setName(null);

        // Create the MBrand, which fails.
        MBrandDTO mBrandDTO = mBrandMapper.toDto(mBrand);


        restMBrandMockMvc.perform(post("/api/m-brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBrandDTO)))
            .andExpect(status().isBadRequest());

        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBrands() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList
        restMBrandMockMvc.perform(get("/api/m-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBrand() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get the mBrand
        restMBrandMockMvc.perform(get("/api/m-brands/{id}", mBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBrand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBrandsByIdFiltering() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        Long id = mBrand.getId();

        defaultMBrandShouldBeFound("id.equals=" + id);
        defaultMBrandShouldNotBeFound("id.notEquals=" + id);

        defaultMBrandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBrandShouldNotBeFound("id.greaterThan=" + id);

        defaultMBrandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBrandShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBrandsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name equals to DEFAULT_NAME
        defaultMBrandShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mBrandList where name equals to UPDATED_NAME
        defaultMBrandShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBrandsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name not equals to DEFAULT_NAME
        defaultMBrandShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mBrandList where name not equals to UPDATED_NAME
        defaultMBrandShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBrandsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMBrandShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mBrandList where name equals to UPDATED_NAME
        defaultMBrandShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBrandsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name is not null
        defaultMBrandShouldBeFound("name.specified=true");

        // Get all the mBrandList where name is null
        defaultMBrandShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBrandsByNameContainsSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name contains DEFAULT_NAME
        defaultMBrandShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mBrandList where name contains UPDATED_NAME
        defaultMBrandShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMBrandsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where name does not contain DEFAULT_NAME
        defaultMBrandShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mBrandList where name does not contain UPDATED_NAME
        defaultMBrandShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMBrandsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where uid equals to DEFAULT_UID
        defaultMBrandShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBrandList where uid equals to UPDATED_UID
        defaultMBrandShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBrandsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where uid not equals to DEFAULT_UID
        defaultMBrandShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBrandList where uid not equals to UPDATED_UID
        defaultMBrandShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBrandsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBrandShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBrandList where uid equals to UPDATED_UID
        defaultMBrandShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBrandsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where uid is not null
        defaultMBrandShouldBeFound("uid.specified=true");

        // Get all the mBrandList where uid is null
        defaultMBrandShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBrandsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where active equals to DEFAULT_ACTIVE
        defaultMBrandShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBrandList where active equals to UPDATED_ACTIVE
        defaultMBrandShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBrandsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where active not equals to DEFAULT_ACTIVE
        defaultMBrandShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBrandList where active not equals to UPDATED_ACTIVE
        defaultMBrandShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBrandsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBrandShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBrandList where active equals to UPDATED_ACTIVE
        defaultMBrandShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBrandsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        // Get all the mBrandList where active is not null
        defaultMBrandShouldBeFound("active.specified=true");

        // Get all the mBrandList where active is null
        defaultMBrandShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBrandShouldBeFound(String filter) throws Exception {
        restMBrandMockMvc.perform(get("/api/m-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBrandMockMvc.perform(get("/api/m-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBrandShouldNotBeFound(String filter) throws Exception {
        restMBrandMockMvc.perform(get("/api/m-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBrandMockMvc.perform(get("/api/m-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMBrand() throws Exception {
        // Get the mBrand
        restMBrandMockMvc.perform(get("/api/m-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBrand() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        int databaseSizeBeforeUpdate = mBrandRepository.findAll().size();

        // Update the mBrand
        MBrand updatedMBrand = mBrandRepository.findById(mBrand.getId()).get();
        // Disconnect from session so that the updates on updatedMBrand are not directly saved in db
        em.detach(updatedMBrand);
        updatedMBrand
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBrandDTO mBrandDTO = mBrandMapper.toDto(updatedMBrand);

        restMBrandMockMvc.perform(put("/api/m-brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBrandDTO)))
            .andExpect(status().isOk());

        // Validate the MBrand in the database
        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeUpdate);
        MBrand testMBrand = mBrandList.get(mBrandList.size() - 1);
        assertThat(testMBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMBrand.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBrand.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBrand() throws Exception {
        int databaseSizeBeforeUpdate = mBrandRepository.findAll().size();

        // Create the MBrand
        MBrandDTO mBrandDTO = mBrandMapper.toDto(mBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBrandMockMvc.perform(put("/api/m-brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBrand in the database
        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBrand() throws Exception {
        // Initialize the database
        mBrandRepository.saveAndFlush(mBrand);

        int databaseSizeBeforeDelete = mBrandRepository.findAll().size();

        // Delete the mBrand
        restMBrandMockMvc.perform(delete("/api/m-brands/{id}", mBrand.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBrand> mBrandList = mBrandRepository.findAll();
        assertThat(mBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
