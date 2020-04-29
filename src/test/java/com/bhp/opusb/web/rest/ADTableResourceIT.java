package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.service.ADTableService;
import com.bhp.opusb.service.dto.ADTableDTO;
import com.bhp.opusb.service.mapper.ADTableMapper;
import com.bhp.opusb.service.dto.ADTableCriteria;
import com.bhp.opusb.service.ADTableQueryService;

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
 * Integration tests for the {@link ADTableResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADTableResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VIEW = false;
    private static final Boolean UPDATED_VIEW = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADTableRepository aDTableRepository;

    @Autowired
    private ADTableMapper aDTableMapper;

    @Autowired
    private ADTableService aDTableService;

    @Autowired
    private ADTableQueryService aDTableQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADTableMockMvc;

    private ADTable aDTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADTable createEntity(EntityManager em) {
        ADTable aDTable = new ADTable()
            .name(DEFAULT_NAME)
            .view(DEFAULT_VIEW)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDTable.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDTable.setAdOrganization(aDOrganization);
        return aDTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADTable createUpdatedEntity(EntityManager em) {
        ADTable aDTable = new ADTable()
            .name(UPDATED_NAME)
            .view(UPDATED_VIEW)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createUpdatedEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDTable.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDTable.setAdOrganization(aDOrganization);
        return aDTable;
    }

    @BeforeEach
    public void initTest() {
        aDTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createADTable() throws Exception {
        int databaseSizeBeforeCreate = aDTableRepository.findAll().size();

        // Create the ADTable
        ADTableDTO aDTableDTO = aDTableMapper.toDto(aDTable);
        restADTableMockMvc.perform(post("/api/ad-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ADTable in the database
        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeCreate + 1);
        ADTable testADTable = aDTableList.get(aDTableList.size() - 1);
        assertThat(testADTable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADTable.isView()).isEqualTo(DEFAULT_VIEW);
        assertThat(testADTable.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDTableRepository.findAll().size();

        // Create the ADTable with an existing ID
        aDTable.setId(1L);
        ADTableDTO aDTableDTO = aDTableMapper.toDto(aDTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADTableMockMvc.perform(post("/api/ad-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADTable in the database
        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDTableRepository.findAll().size();
        // set the field null
        aDTable.setName(null);

        // Create the ADTable, which fails.
        ADTableDTO aDTableDTO = aDTableMapper.toDto(aDTable);

        restADTableMockMvc.perform(post("/api/ad-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTableDTO)))
            .andExpect(status().isBadRequest());

        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADTables() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList
        restADTableMockMvc.perform(get("/api/ad-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].view").value(hasItem(DEFAULT_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADTable() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get the aDTable
        restADTableMockMvc.perform(get("/api/ad-tables/{id}", aDTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDTable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.view").value(DEFAULT_VIEW.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADTablesByIdFiltering() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        Long id = aDTable.getId();

        defaultADTableShouldBeFound("id.equals=" + id);
        defaultADTableShouldNotBeFound("id.notEquals=" + id);

        defaultADTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADTableShouldNotBeFound("id.greaterThan=" + id);

        defaultADTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADTableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADTablesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name equals to DEFAULT_NAME
        defaultADTableShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDTableList where name equals to UPDATED_NAME
        defaultADTableShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTablesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name not equals to DEFAULT_NAME
        defaultADTableShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDTableList where name not equals to UPDATED_NAME
        defaultADTableShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTablesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADTableShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDTableList where name equals to UPDATED_NAME
        defaultADTableShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTablesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name is not null
        defaultADTableShouldBeFound("name.specified=true");

        // Get all the aDTableList where name is null
        defaultADTableShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTablesByNameContainsSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name contains DEFAULT_NAME
        defaultADTableShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDTableList where name contains UPDATED_NAME
        defaultADTableShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTablesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where name does not contain DEFAULT_NAME
        defaultADTableShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDTableList where name does not contain UPDATED_NAME
        defaultADTableShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADTablesByViewIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where view equals to DEFAULT_VIEW
        defaultADTableShouldBeFound("view.equals=" + DEFAULT_VIEW);

        // Get all the aDTableList where view equals to UPDATED_VIEW
        defaultADTableShouldNotBeFound("view.equals=" + UPDATED_VIEW);
    }

    @Test
    @Transactional
    public void getAllADTablesByViewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where view not equals to DEFAULT_VIEW
        defaultADTableShouldNotBeFound("view.notEquals=" + DEFAULT_VIEW);

        // Get all the aDTableList where view not equals to UPDATED_VIEW
        defaultADTableShouldBeFound("view.notEquals=" + UPDATED_VIEW);
    }

    @Test
    @Transactional
    public void getAllADTablesByViewIsInShouldWork() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where view in DEFAULT_VIEW or UPDATED_VIEW
        defaultADTableShouldBeFound("view.in=" + DEFAULT_VIEW + "," + UPDATED_VIEW);

        // Get all the aDTableList where view equals to UPDATED_VIEW
        defaultADTableShouldNotBeFound("view.in=" + UPDATED_VIEW);
    }

    @Test
    @Transactional
    public void getAllADTablesByViewIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where view is not null
        defaultADTableShouldBeFound("view.specified=true");

        // Get all the aDTableList where view is null
        defaultADTableShouldNotBeFound("view.specified=false");
    }

    @Test
    @Transactional
    public void getAllADTablesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where active equals to DEFAULT_ACTIVE
        defaultADTableShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDTableList where active equals to UPDATED_ACTIVE
        defaultADTableShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTablesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where active not equals to DEFAULT_ACTIVE
        defaultADTableShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDTableList where active not equals to UPDATED_ACTIVE
        defaultADTableShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTablesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADTableShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDTableList where active equals to UPDATED_ACTIVE
        defaultADTableShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTablesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        // Get all the aDTableList where active is not null
        defaultADTableShouldBeFound("active.specified=true");

        // Get all the aDTableList where active is null
        defaultADTableShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADTablesByADColumnIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);
        ADColumn aDColumn = ADColumnResourceIT.createEntity(em);
        em.persist(aDColumn);
        em.flush();
        aDTable.addADColumn(aDColumn);
        aDTableRepository.saveAndFlush(aDTable);
        Long aDColumnId = aDColumn.getId();

        // Get all the aDTableList where aDColumn equals to aDColumnId
        defaultADTableShouldBeFound("aDColumnId.equals=" + aDColumnId);

        // Get all the aDTableList where aDColumn equals to aDColumnId + 1
        defaultADTableShouldNotBeFound("aDColumnId.equals=" + (aDColumnId + 1));
    }


    @Test
    @Transactional
    public void getAllADTablesByAdClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADClient adClient = aDTable.getAdClient();
        aDTableRepository.saveAndFlush(aDTable);
        Long adClientId = adClient.getId();

        // Get all the aDTableList where adClient equals to adClientId
        defaultADTableShouldBeFound("adClientId.equals=" + adClientId);

        // Get all the aDTableList where adClient equals to adClientId + 1
        defaultADTableShouldNotBeFound("adClientId.equals=" + (adClientId + 1));
    }


    @Test
    @Transactional
    public void getAllADTablesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDTable.getAdOrganization();
        aDTableRepository.saveAndFlush(aDTable);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDTableList where adOrganization equals to adOrganizationId
        defaultADTableShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDTableList where adOrganization equals to adOrganizationId + 1
        defaultADTableShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADTableShouldBeFound(String filter) throws Exception {
        restADTableMockMvc.perform(get("/api/ad-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].view").value(hasItem(DEFAULT_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADTableMockMvc.perform(get("/api/ad-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADTableShouldNotBeFound(String filter) throws Exception {
        restADTableMockMvc.perform(get("/api/ad-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADTableMockMvc.perform(get("/api/ad-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADTable() throws Exception {
        // Get the aDTable
        restADTableMockMvc.perform(get("/api/ad-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADTable() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        int databaseSizeBeforeUpdate = aDTableRepository.findAll().size();

        // Update the aDTable
        ADTable updatedADTable = aDTableRepository.findById(aDTable.getId()).get();
        // Disconnect from session so that the updates on updatedADTable are not directly saved in db
        em.detach(updatedADTable);
        updatedADTable
            .name(UPDATED_NAME)
            .view(UPDATED_VIEW)
            .active(UPDATED_ACTIVE);
        ADTableDTO aDTableDTO = aDTableMapper.toDto(updatedADTable);

        restADTableMockMvc.perform(put("/api/ad-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTableDTO)))
            .andExpect(status().isOk());

        // Validate the ADTable in the database
        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeUpdate);
        ADTable testADTable = aDTableList.get(aDTableList.size() - 1);
        assertThat(testADTable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADTable.isView()).isEqualTo(UPDATED_VIEW);
        assertThat(testADTable.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADTable() throws Exception {
        int databaseSizeBeforeUpdate = aDTableRepository.findAll().size();

        // Create the ADTable
        ADTableDTO aDTableDTO = aDTableMapper.toDto(aDTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADTableMockMvc.perform(put("/api/ad-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADTable in the database
        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADTable() throws Exception {
        // Initialize the database
        aDTableRepository.saveAndFlush(aDTable);

        int databaseSizeBeforeDelete = aDTableRepository.findAll().size();

        // Delete the aDTable
        restADTableMockMvc.perform(delete("/api/ad-tables/{id}", aDTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADTable> aDTableList = aDTableRepository.findAll();
        assertThat(aDTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
