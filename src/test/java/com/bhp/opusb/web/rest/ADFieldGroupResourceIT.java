package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADFieldGroup;
import com.bhp.opusb.repository.ADFieldGroupRepository;
import com.bhp.opusb.service.ADFieldGroupService;
import com.bhp.opusb.service.dto.ADFieldGroupDTO;
import com.bhp.opusb.service.mapper.ADFieldGroupMapper;
import com.bhp.opusb.service.dto.ADFieldGroupCriteria;
import com.bhp.opusb.service.ADFieldGroupQueryService;

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
 * Integration tests for the {@link ADFieldGroupResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADFieldGroupResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COLLAPSIBLE = false;
    private static final Boolean UPDATED_COLLAPSIBLE = true;

    private static final Boolean DEFAULT_COLLAPSE_BY_DEFAULT = false;
    private static final Boolean UPDATED_COLLAPSE_BY_DEFAULT = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADFieldGroupRepository aDFieldGroupRepository;

    @Autowired
    private ADFieldGroupMapper aDFieldGroupMapper;

    @Autowired
    private ADFieldGroupService aDFieldGroupService;

    @Autowired
    private ADFieldGroupQueryService aDFieldGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADFieldGroupMockMvc;

    private ADFieldGroup aDFieldGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADFieldGroup createEntity(EntityManager em) {
        ADFieldGroup aDFieldGroup = new ADFieldGroup()
            .uid(DEFAULT_UID)
            .name(DEFAULT_NAME)
            .collapsible(DEFAULT_COLLAPSIBLE)
            .collapseByDefault(DEFAULT_COLLAPSE_BY_DEFAULT)
            .active(DEFAULT_ACTIVE);
        return aDFieldGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADFieldGroup createUpdatedEntity(EntityManager em) {
        ADFieldGroup aDFieldGroup = new ADFieldGroup()
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .collapsible(UPDATED_COLLAPSIBLE)
            .collapseByDefault(UPDATED_COLLAPSE_BY_DEFAULT)
            .active(UPDATED_ACTIVE);
        return aDFieldGroup;
    }

    @BeforeEach
    public void initTest() {
        aDFieldGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createADFieldGroup() throws Exception {
        int databaseSizeBeforeCreate = aDFieldGroupRepository.findAll().size();

        // Create the ADFieldGroup
        ADFieldGroupDTO aDFieldGroupDTO = aDFieldGroupMapper.toDto(aDFieldGroup);
        restADFieldGroupMockMvc.perform(post("/api/ad-field-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ADFieldGroup in the database
        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ADFieldGroup testADFieldGroup = aDFieldGroupList.get(aDFieldGroupList.size() - 1);
        assertThat(testADFieldGroup.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testADFieldGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADFieldGroup.isCollapsible()).isEqualTo(DEFAULT_COLLAPSIBLE);
        assertThat(testADFieldGroup.isCollapseByDefault()).isEqualTo(DEFAULT_COLLAPSE_BY_DEFAULT);
        assertThat(testADFieldGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADFieldGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDFieldGroupRepository.findAll().size();

        // Create the ADFieldGroup with an existing ID
        aDFieldGroup.setId(1L);
        ADFieldGroupDTO aDFieldGroupDTO = aDFieldGroupMapper.toDto(aDFieldGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADFieldGroupMockMvc.perform(post("/api/ad-field-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADFieldGroup in the database
        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDFieldGroupRepository.findAll().size();
        // set the field null
        aDFieldGroup.setName(null);

        // Create the ADFieldGroup, which fails.
        ADFieldGroupDTO aDFieldGroupDTO = aDFieldGroupMapper.toDto(aDFieldGroup);

        restADFieldGroupMockMvc.perform(post("/api/ad-field-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADFieldGroups() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDFieldGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].collapsible").value(hasItem(DEFAULT_COLLAPSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].collapseByDefault").value(hasItem(DEFAULT_COLLAPSE_BY_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADFieldGroup() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get the aDFieldGroup
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups/{id}", aDFieldGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDFieldGroup.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.collapsible").value(DEFAULT_COLLAPSIBLE.booleanValue()))
            .andExpect(jsonPath("$.collapseByDefault").value(DEFAULT_COLLAPSE_BY_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADFieldGroupsByIdFiltering() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        Long id = aDFieldGroup.getId();

        defaultADFieldGroupShouldBeFound("id.equals=" + id);
        defaultADFieldGroupShouldNotBeFound("id.notEquals=" + id);

        defaultADFieldGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADFieldGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultADFieldGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADFieldGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADFieldGroupsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where uid equals to DEFAULT_UID
        defaultADFieldGroupShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the aDFieldGroupList where uid equals to UPDATED_UID
        defaultADFieldGroupShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where uid not equals to DEFAULT_UID
        defaultADFieldGroupShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the aDFieldGroupList where uid not equals to UPDATED_UID
        defaultADFieldGroupShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where uid in DEFAULT_UID or UPDATED_UID
        defaultADFieldGroupShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the aDFieldGroupList where uid equals to UPDATED_UID
        defaultADFieldGroupShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where uid is not null
        defaultADFieldGroupShouldBeFound("uid.specified=true");

        // Get all the aDFieldGroupList where uid is null
        defaultADFieldGroupShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name equals to DEFAULT_NAME
        defaultADFieldGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDFieldGroupList where name equals to UPDATED_NAME
        defaultADFieldGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name not equals to DEFAULT_NAME
        defaultADFieldGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDFieldGroupList where name not equals to UPDATED_NAME
        defaultADFieldGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADFieldGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDFieldGroupList where name equals to UPDATED_NAME
        defaultADFieldGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name is not null
        defaultADFieldGroupShouldBeFound("name.specified=true");

        // Get all the aDFieldGroupList where name is null
        defaultADFieldGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name contains DEFAULT_NAME
        defaultADFieldGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDFieldGroupList where name contains UPDATED_NAME
        defaultADFieldGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where name does not contain DEFAULT_NAME
        defaultADFieldGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDFieldGroupList where name does not contain UPDATED_NAME
        defaultADFieldGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapsibleIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapsible equals to DEFAULT_COLLAPSIBLE
        defaultADFieldGroupShouldBeFound("collapsible.equals=" + DEFAULT_COLLAPSIBLE);

        // Get all the aDFieldGroupList where collapsible equals to UPDATED_COLLAPSIBLE
        defaultADFieldGroupShouldNotBeFound("collapsible.equals=" + UPDATED_COLLAPSIBLE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapsibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapsible not equals to DEFAULT_COLLAPSIBLE
        defaultADFieldGroupShouldNotBeFound("collapsible.notEquals=" + DEFAULT_COLLAPSIBLE);

        // Get all the aDFieldGroupList where collapsible not equals to UPDATED_COLLAPSIBLE
        defaultADFieldGroupShouldBeFound("collapsible.notEquals=" + UPDATED_COLLAPSIBLE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapsibleIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapsible in DEFAULT_COLLAPSIBLE or UPDATED_COLLAPSIBLE
        defaultADFieldGroupShouldBeFound("collapsible.in=" + DEFAULT_COLLAPSIBLE + "," + UPDATED_COLLAPSIBLE);

        // Get all the aDFieldGroupList where collapsible equals to UPDATED_COLLAPSIBLE
        defaultADFieldGroupShouldNotBeFound("collapsible.in=" + UPDATED_COLLAPSIBLE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapsibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapsible is not null
        defaultADFieldGroupShouldBeFound("collapsible.specified=true");

        // Get all the aDFieldGroupList where collapsible is null
        defaultADFieldGroupShouldNotBeFound("collapsible.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapseByDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapseByDefault equals to DEFAULT_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldBeFound("collapseByDefault.equals=" + DEFAULT_COLLAPSE_BY_DEFAULT);

        // Get all the aDFieldGroupList where collapseByDefault equals to UPDATED_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldNotBeFound("collapseByDefault.equals=" + UPDATED_COLLAPSE_BY_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapseByDefaultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapseByDefault not equals to DEFAULT_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldNotBeFound("collapseByDefault.notEquals=" + DEFAULT_COLLAPSE_BY_DEFAULT);

        // Get all the aDFieldGroupList where collapseByDefault not equals to UPDATED_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldBeFound("collapseByDefault.notEquals=" + UPDATED_COLLAPSE_BY_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapseByDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapseByDefault in DEFAULT_COLLAPSE_BY_DEFAULT or UPDATED_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldBeFound("collapseByDefault.in=" + DEFAULT_COLLAPSE_BY_DEFAULT + "," + UPDATED_COLLAPSE_BY_DEFAULT);

        // Get all the aDFieldGroupList where collapseByDefault equals to UPDATED_COLLAPSE_BY_DEFAULT
        defaultADFieldGroupShouldNotBeFound("collapseByDefault.in=" + UPDATED_COLLAPSE_BY_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByCollapseByDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where collapseByDefault is not null
        defaultADFieldGroupShouldBeFound("collapseByDefault.specified=true");

        // Get all the aDFieldGroupList where collapseByDefault is null
        defaultADFieldGroupShouldNotBeFound("collapseByDefault.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where active equals to DEFAULT_ACTIVE
        defaultADFieldGroupShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDFieldGroupList where active equals to UPDATED_ACTIVE
        defaultADFieldGroupShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where active not equals to DEFAULT_ACTIVE
        defaultADFieldGroupShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDFieldGroupList where active not equals to UPDATED_ACTIVE
        defaultADFieldGroupShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADFieldGroupShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDFieldGroupList where active equals to UPDATED_ACTIVE
        defaultADFieldGroupShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldGroupsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        // Get all the aDFieldGroupList where active is not null
        defaultADFieldGroupShouldBeFound("active.specified=true");

        // Get all the aDFieldGroupList where active is null
        defaultADFieldGroupShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADFieldGroupShouldBeFound(String filter) throws Exception {
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDFieldGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].collapsible").value(hasItem(DEFAULT_COLLAPSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].collapseByDefault").value(hasItem(DEFAULT_COLLAPSE_BY_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADFieldGroupShouldNotBeFound(String filter) throws Exception {
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADFieldGroup() throws Exception {
        // Get the aDFieldGroup
        restADFieldGroupMockMvc.perform(get("/api/ad-field-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADFieldGroup() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        int databaseSizeBeforeUpdate = aDFieldGroupRepository.findAll().size();

        // Update the aDFieldGroup
        ADFieldGroup updatedADFieldGroup = aDFieldGroupRepository.findById(aDFieldGroup.getId()).get();
        // Disconnect from session so that the updates on updatedADFieldGroup are not directly saved in db
        em.detach(updatedADFieldGroup);
        updatedADFieldGroup
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .collapsible(UPDATED_COLLAPSIBLE)
            .collapseByDefault(UPDATED_COLLAPSE_BY_DEFAULT)
            .active(UPDATED_ACTIVE);
        ADFieldGroupDTO aDFieldGroupDTO = aDFieldGroupMapper.toDto(updatedADFieldGroup);

        restADFieldGroupMockMvc.perform(put("/api/ad-field-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ADFieldGroup in the database
        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeUpdate);
        ADFieldGroup testADFieldGroup = aDFieldGroupList.get(aDFieldGroupList.size() - 1);
        assertThat(testADFieldGroup.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testADFieldGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADFieldGroup.isCollapsible()).isEqualTo(UPDATED_COLLAPSIBLE);
        assertThat(testADFieldGroup.isCollapseByDefault()).isEqualTo(UPDATED_COLLAPSE_BY_DEFAULT);
        assertThat(testADFieldGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADFieldGroup() throws Exception {
        int databaseSizeBeforeUpdate = aDFieldGroupRepository.findAll().size();

        // Create the ADFieldGroup
        ADFieldGroupDTO aDFieldGroupDTO = aDFieldGroupMapper.toDto(aDFieldGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADFieldGroupMockMvc.perform(put("/api/ad-field-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADFieldGroup in the database
        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADFieldGroup() throws Exception {
        // Initialize the database
        aDFieldGroupRepository.saveAndFlush(aDFieldGroup);

        int databaseSizeBeforeDelete = aDFieldGroupRepository.findAll().size();

        // Delete the aDFieldGroup
        restADFieldGroupMockMvc.perform(delete("/api/ad-field-groups/{id}", aDFieldGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADFieldGroup> aDFieldGroupList = aDFieldGroupRepository.findAll();
        assertThat(aDFieldGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
