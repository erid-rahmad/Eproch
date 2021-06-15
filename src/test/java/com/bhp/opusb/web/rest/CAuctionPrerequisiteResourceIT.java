package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CAuctionPrerequisite;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CAuctionPrerequisiteRepository;
import com.bhp.opusb.service.CAuctionPrerequisiteService;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteDTO;
import com.bhp.opusb.service.mapper.CAuctionPrerequisiteMapper;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteCriteria;
import com.bhp.opusb.service.CAuctionPrerequisiteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CAuctionPrerequisiteResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CAuctionPrerequisiteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CAuctionPrerequisiteRepository cAuctionPrerequisiteRepository;

    @Autowired
    private CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper;

    @Autowired
    private CAuctionPrerequisiteService cAuctionPrerequisiteService;

    @Autowired
    private CAuctionPrerequisiteQueryService cAuctionPrerequisiteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCAuctionPrerequisiteMockMvc;

    private CAuctionPrerequisite cAuctionPrerequisite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAuctionPrerequisite createEntity(EntityManager em) {
        CAuctionPrerequisite cAuctionPrerequisite = new CAuctionPrerequisite()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
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
        cAuctionPrerequisite.setAdOrganization(aDOrganization);
        return cAuctionPrerequisite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAuctionPrerequisite createUpdatedEntity(EntityManager em) {
        CAuctionPrerequisite cAuctionPrerequisite = new CAuctionPrerequisite()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
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
        cAuctionPrerequisite.setAdOrganization(aDOrganization);
        return cAuctionPrerequisite;
    }

    @BeforeEach
    public void initTest() {
        cAuctionPrerequisite = createEntity(em);
    }

    @Test
    @Transactional
    public void createCAuctionPrerequisite() throws Exception {
        int databaseSizeBeforeCreate = cAuctionPrerequisiteRepository.findAll().size();

        // Create the CAuctionPrerequisite
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO = cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisite);
        restCAuctionPrerequisiteMockMvc.perform(post("/api/c-auction-prerequisites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAuctionPrerequisiteDTO)))
            .andExpect(status().isCreated());

        // Validate the CAuctionPrerequisite in the database
        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeCreate + 1);
        CAuctionPrerequisite testCAuctionPrerequisite = cAuctionPrerequisiteList.get(cAuctionPrerequisiteList.size() - 1);
        assertThat(testCAuctionPrerequisite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCAuctionPrerequisite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCAuctionPrerequisite.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCAuctionPrerequisite.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCAuctionPrerequisiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cAuctionPrerequisiteRepository.findAll().size();

        // Create the CAuctionPrerequisite with an existing ID
        cAuctionPrerequisite.setId(1L);
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO = cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAuctionPrerequisiteMockMvc.perform(post("/api/c-auction-prerequisites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAuctionPrerequisiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAuctionPrerequisite in the database
        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cAuctionPrerequisiteRepository.findAll().size();
        // set the field null
        cAuctionPrerequisite.setName(null);

        // Create the CAuctionPrerequisite, which fails.
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO = cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisite);

        restCAuctionPrerequisiteMockMvc.perform(post("/api/c-auction-prerequisites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAuctionPrerequisiteDTO)))
            .andExpect(status().isBadRequest());

        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisites() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAuctionPrerequisite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCAuctionPrerequisite() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get the cAuctionPrerequisite
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites/{id}", cAuctionPrerequisite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cAuctionPrerequisite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCAuctionPrerequisitesByIdFiltering() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        Long id = cAuctionPrerequisite.getId();

        defaultCAuctionPrerequisiteShouldBeFound("id.equals=" + id);
        defaultCAuctionPrerequisiteShouldNotBeFound("id.notEquals=" + id);

        defaultCAuctionPrerequisiteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCAuctionPrerequisiteShouldNotBeFound("id.greaterThan=" + id);

        defaultCAuctionPrerequisiteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCAuctionPrerequisiteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name equals to DEFAULT_NAME
        defaultCAuctionPrerequisiteShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cAuctionPrerequisiteList where name equals to UPDATED_NAME
        defaultCAuctionPrerequisiteShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name not equals to DEFAULT_NAME
        defaultCAuctionPrerequisiteShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cAuctionPrerequisiteList where name not equals to UPDATED_NAME
        defaultCAuctionPrerequisiteShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCAuctionPrerequisiteShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cAuctionPrerequisiteList where name equals to UPDATED_NAME
        defaultCAuctionPrerequisiteShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name is not null
        defaultCAuctionPrerequisiteShouldBeFound("name.specified=true");

        // Get all the cAuctionPrerequisiteList where name is null
        defaultCAuctionPrerequisiteShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameContainsSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name contains DEFAULT_NAME
        defaultCAuctionPrerequisiteShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cAuctionPrerequisiteList where name contains UPDATED_NAME
        defaultCAuctionPrerequisiteShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where name does not contain DEFAULT_NAME
        defaultCAuctionPrerequisiteShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cAuctionPrerequisiteList where name does not contain UPDATED_NAME
        defaultCAuctionPrerequisiteShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where uid equals to DEFAULT_UID
        defaultCAuctionPrerequisiteShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cAuctionPrerequisiteList where uid equals to UPDATED_UID
        defaultCAuctionPrerequisiteShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where uid not equals to DEFAULT_UID
        defaultCAuctionPrerequisiteShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cAuctionPrerequisiteList where uid not equals to UPDATED_UID
        defaultCAuctionPrerequisiteShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where uid in DEFAULT_UID or UPDATED_UID
        defaultCAuctionPrerequisiteShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cAuctionPrerequisiteList where uid equals to UPDATED_UID
        defaultCAuctionPrerequisiteShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where uid is not null
        defaultCAuctionPrerequisiteShouldBeFound("uid.specified=true");

        // Get all the cAuctionPrerequisiteList where uid is null
        defaultCAuctionPrerequisiteShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where active equals to DEFAULT_ACTIVE
        defaultCAuctionPrerequisiteShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cAuctionPrerequisiteList where active equals to UPDATED_ACTIVE
        defaultCAuctionPrerequisiteShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where active not equals to DEFAULT_ACTIVE
        defaultCAuctionPrerequisiteShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cAuctionPrerequisiteList where active not equals to UPDATED_ACTIVE
        defaultCAuctionPrerequisiteShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCAuctionPrerequisiteShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cAuctionPrerequisiteList where active equals to UPDATED_ACTIVE
        defaultCAuctionPrerequisiteShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        // Get all the cAuctionPrerequisiteList where active is not null
        defaultCAuctionPrerequisiteShouldBeFound("active.specified=true");

        // Get all the cAuctionPrerequisiteList where active is null
        defaultCAuctionPrerequisiteShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAuctionPrerequisitesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cAuctionPrerequisite.getAdOrganization();
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cAuctionPrerequisiteList where adOrganization equals to adOrganizationId
        defaultCAuctionPrerequisiteShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cAuctionPrerequisiteList where adOrganization equals to adOrganizationId + 1
        defaultCAuctionPrerequisiteShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCAuctionPrerequisiteShouldBeFound(String filter) throws Exception {
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAuctionPrerequisite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCAuctionPrerequisiteShouldNotBeFound(String filter) throws Exception {
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCAuctionPrerequisite() throws Exception {
        // Get the cAuctionPrerequisite
        restCAuctionPrerequisiteMockMvc.perform(get("/api/c-auction-prerequisites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCAuctionPrerequisite() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        int databaseSizeBeforeUpdate = cAuctionPrerequisiteRepository.findAll().size();

        // Update the cAuctionPrerequisite
        CAuctionPrerequisite updatedCAuctionPrerequisite = cAuctionPrerequisiteRepository.findById(cAuctionPrerequisite.getId()).get();
        // Disconnect from session so that the updates on updatedCAuctionPrerequisite are not directly saved in db
        em.detach(updatedCAuctionPrerequisite);
        updatedCAuctionPrerequisite
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO = cAuctionPrerequisiteMapper.toDto(updatedCAuctionPrerequisite);

        restCAuctionPrerequisiteMockMvc.perform(put("/api/c-auction-prerequisites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAuctionPrerequisiteDTO)))
            .andExpect(status().isOk());

        // Validate the CAuctionPrerequisite in the database
        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
        CAuctionPrerequisite testCAuctionPrerequisite = cAuctionPrerequisiteList.get(cAuctionPrerequisiteList.size() - 1);
        assertThat(testCAuctionPrerequisite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCAuctionPrerequisite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCAuctionPrerequisite.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCAuctionPrerequisite.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCAuctionPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = cAuctionPrerequisiteRepository.findAll().size();

        // Create the CAuctionPrerequisite
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO = cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAuctionPrerequisiteMockMvc.perform(put("/api/c-auction-prerequisites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAuctionPrerequisiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAuctionPrerequisite in the database
        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCAuctionPrerequisite() throws Exception {
        // Initialize the database
        cAuctionPrerequisiteRepository.saveAndFlush(cAuctionPrerequisite);

        int databaseSizeBeforeDelete = cAuctionPrerequisiteRepository.findAll().size();

        // Delete the cAuctionPrerequisite
        restCAuctionPrerequisiteMockMvc.perform(delete("/api/c-auction-prerequisites/{id}", cAuctionPrerequisite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAuctionPrerequisite> cAuctionPrerequisiteList = cAuctionPrerequisiteRepository.findAll();
        assertThat(cAuctionPrerequisiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
