package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.Permission;
import com.bhp.opusb.repository.PermissionRepository;
import com.bhp.opusb.service.PermissionService;
import com.bhp.opusb.service.dto.PermissionDTO;
import com.bhp.opusb.service.mapper.PermissionMapper;
import com.bhp.opusb.service.dto.PermissionCriteria;
import com.bhp.opusb.service.PermissionQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PermissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PermissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAN_WRITE = false;
    private static final Boolean UPDATED_CAN_WRITE = true;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionQueryService permissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermissionMockMvc;

    private Permission permission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permission createEntity(EntityManager em) {
        Permission permission = new Permission()
            .name(DEFAULT_NAME)
            .module(DEFAULT_MODULE)
            .canWrite(DEFAULT_CAN_WRITE);
        return permission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permission createUpdatedEntity(EntityManager em) {
        Permission permission = new Permission()
            .name(UPDATED_NAME)
            .module(UPDATED_MODULE)
            .canWrite(UPDATED_CAN_WRITE);
        return permission;
    }

    @BeforeEach
    public void initTest() {
        permission = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermission() throws Exception {
        int databaseSizeBeforeCreate = permissionRepository.findAll().size();

        // Create the Permission
        PermissionDTO permissionDTO = permissionMapper.toDto(permission);
        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isCreated());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeCreate + 1);
        Permission testPermission = permissionList.get(permissionList.size() - 1);
        assertThat(testPermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPermission.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testPermission.isCanWrite()).isEqualTo(DEFAULT_CAN_WRITE);
    }

    @Test
    @Transactional
    public void createPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permissionRepository.findAll().size();

        // Create the Permission with an existing ID
        permission.setId(1L);
        PermissionDTO permissionDTO = permissionMapper.toDto(permission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionRepository.findAll().size();
        // set the field null
        permission.setName(null);

        // Create the Permission, which fails.
        PermissionDTO permissionDTO = permissionMapper.toDto(permission);

        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isBadRequest());

        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionRepository.findAll().size();
        // set the field null
        permission.setModule(null);

        // Create the Permission, which fails.
        PermissionDTO permissionDTO = permissionMapper.toDto(permission);

        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isBadRequest());

        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPermissions() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList
        restPermissionMockMvc.perform(get("/api/permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE)))
            .andExpect(jsonPath("$.[*].canWrite").value(hasItem(DEFAULT_CAN_WRITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPermission() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get the permission
        restPermissionMockMvc.perform(get("/api/permissions/{id}", permission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE))
            .andExpect(jsonPath("$.canWrite").value(DEFAULT_CAN_WRITE.booleanValue()));
    }


    @Test
    @Transactional
    public void getPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        Long id = permission.getId();

        defaultPermissionShouldBeFound("id.equals=" + id);
        defaultPermissionShouldNotBeFound("id.notEquals=" + id);

        defaultPermissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPermissionShouldNotBeFound("id.greaterThan=" + id);

        defaultPermissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPermissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPermissionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name equals to DEFAULT_NAME
        defaultPermissionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the permissionList where name equals to UPDATED_NAME
        defaultPermissionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPermissionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name not equals to DEFAULT_NAME
        defaultPermissionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the permissionList where name not equals to UPDATED_NAME
        defaultPermissionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPermissionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPermissionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the permissionList where name equals to UPDATED_NAME
        defaultPermissionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPermissionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name is not null
        defaultPermissionShouldBeFound("name.specified=true");

        // Get all the permissionList where name is null
        defaultPermissionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPermissionsByNameContainsSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name contains DEFAULT_NAME
        defaultPermissionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the permissionList where name contains UPDATED_NAME
        defaultPermissionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPermissionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where name does not contain DEFAULT_NAME
        defaultPermissionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the permissionList where name does not contain UPDATED_NAME
        defaultPermissionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPermissionsByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module equals to DEFAULT_MODULE
        defaultPermissionShouldBeFound("module.equals=" + DEFAULT_MODULE);

        // Get all the permissionList where module equals to UPDATED_MODULE
        defaultPermissionShouldNotBeFound("module.equals=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByModuleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module not equals to DEFAULT_MODULE
        defaultPermissionShouldNotBeFound("module.notEquals=" + DEFAULT_MODULE);

        // Get all the permissionList where module not equals to UPDATED_MODULE
        defaultPermissionShouldBeFound("module.notEquals=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByModuleIsInShouldWork() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module in DEFAULT_MODULE or UPDATED_MODULE
        defaultPermissionShouldBeFound("module.in=" + DEFAULT_MODULE + "," + UPDATED_MODULE);

        // Get all the permissionList where module equals to UPDATED_MODULE
        defaultPermissionShouldNotBeFound("module.in=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByModuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module is not null
        defaultPermissionShouldBeFound("module.specified=true");

        // Get all the permissionList where module is null
        defaultPermissionShouldNotBeFound("module.specified=false");
    }
                @Test
    @Transactional
    public void getAllPermissionsByModuleContainsSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module contains DEFAULT_MODULE
        defaultPermissionShouldBeFound("module.contains=" + DEFAULT_MODULE);

        // Get all the permissionList where module contains UPDATED_MODULE
        defaultPermissionShouldNotBeFound("module.contains=" + UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByModuleNotContainsSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where module does not contain DEFAULT_MODULE
        defaultPermissionShouldNotBeFound("module.doesNotContain=" + DEFAULT_MODULE);

        // Get all the permissionList where module does not contain UPDATED_MODULE
        defaultPermissionShouldBeFound("module.doesNotContain=" + UPDATED_MODULE);
    }


    @Test
    @Transactional
    public void getAllPermissionsByCanWriteIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where canWrite equals to DEFAULT_CAN_WRITE
        defaultPermissionShouldBeFound("canWrite.equals=" + DEFAULT_CAN_WRITE);

        // Get all the permissionList where canWrite equals to UPDATED_CAN_WRITE
        defaultPermissionShouldNotBeFound("canWrite.equals=" + UPDATED_CAN_WRITE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByCanWriteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where canWrite not equals to DEFAULT_CAN_WRITE
        defaultPermissionShouldNotBeFound("canWrite.notEquals=" + DEFAULT_CAN_WRITE);

        // Get all the permissionList where canWrite not equals to UPDATED_CAN_WRITE
        defaultPermissionShouldBeFound("canWrite.notEquals=" + UPDATED_CAN_WRITE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByCanWriteIsInShouldWork() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where canWrite in DEFAULT_CAN_WRITE or UPDATED_CAN_WRITE
        defaultPermissionShouldBeFound("canWrite.in=" + DEFAULT_CAN_WRITE + "," + UPDATED_CAN_WRITE);

        // Get all the permissionList where canWrite equals to UPDATED_CAN_WRITE
        defaultPermissionShouldNotBeFound("canWrite.in=" + UPDATED_CAN_WRITE);
    }

    @Test
    @Transactional
    public void getAllPermissionsByCanWriteIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        // Get all the permissionList where canWrite is not null
        defaultPermissionShouldBeFound("canWrite.specified=true");

        // Get all the permissionList where canWrite is null
        defaultPermissionShouldNotBeFound("canWrite.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermissionShouldBeFound(String filter) throws Exception {
        restPermissionMockMvc.perform(get("/api/permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE)))
            .andExpect(jsonPath("$.[*].canWrite").value(hasItem(DEFAULT_CAN_WRITE.booleanValue())));

        // Check, that the count call also returns 1
        restPermissionMockMvc.perform(get("/api/permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermissionShouldNotBeFound(String filter) throws Exception {
        restPermissionMockMvc.perform(get("/api/permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermissionMockMvc.perform(get("/api/permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPermission() throws Exception {
        // Get the permission
        restPermissionMockMvc.perform(get("/api/permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermission() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        int databaseSizeBeforeUpdate = permissionRepository.findAll().size();

        // Update the permission
        Permission updatedPermission = permissionRepository.findById(permission.getId()).get();
        // Disconnect from session so that the updates on updatedPermission are not directly saved in db
        em.detach(updatedPermission);
        updatedPermission
            .name(UPDATED_NAME)
            .module(UPDATED_MODULE)
            .canWrite(UPDATED_CAN_WRITE);
        PermissionDTO permissionDTO = permissionMapper.toDto(updatedPermission);

        restPermissionMockMvc.perform(put("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isOk());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeUpdate);
        Permission testPermission = permissionList.get(permissionList.size() - 1);
        assertThat(testPermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPermission.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testPermission.isCanWrite()).isEqualTo(UPDATED_CAN_WRITE);
    }

    @Test
    @Transactional
    public void updateNonExistingPermission() throws Exception {
        int databaseSizeBeforeUpdate = permissionRepository.findAll().size();

        // Create the Permission
        PermissionDTO permissionDTO = permissionMapper.toDto(permission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionMockMvc.perform(put("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermission() throws Exception {
        // Initialize the database
        permissionRepository.saveAndFlush(permission);

        int databaseSizeBeforeDelete = permissionRepository.findAll().size();

        // Delete the permission
        restPermissionMockMvc.perform(delete("/api/permissions/{id}", permission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
