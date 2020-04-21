package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.repository.ADClientRepository;
import com.bhp.opusb.service.ADClientService;
import com.bhp.opusb.service.dto.ADClientDTO;
import com.bhp.opusb.service.mapper.ADClientMapper;
import com.bhp.opusb.service.dto.ADClientCriteria;
import com.bhp.opusb.service.ADClientQueryService;

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
 * Integration tests for the {@link ADClientResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADClientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADClientRepository aDClientRepository;

    @Autowired
    private ADClientMapper aDClientMapper;

    @Autowired
    private ADClientService aDClientService;

    @Autowired
    private ADClientQueryService aDClientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADClientMockMvc;

    private ADClient aDClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADClient createEntity(EntityManager em) {
        ADClient aDClient = new ADClient()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return aDClient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADClient createUpdatedEntity(EntityManager em) {
        ADClient aDClient = new ADClient()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return aDClient;
    }

    @BeforeEach
    public void initTest() {
        aDClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createADClient() throws Exception {
        int databaseSizeBeforeCreate = aDClientRepository.findAll().size();

        // Create the ADClient
        ADClientDTO aDClientDTO = aDClientMapper.toDto(aDClient);
        restADClientMockMvc.perform(post("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isCreated());

        // Validate the ADClient in the database
        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeCreate + 1);
        ADClient testADClient = aDClientList.get(aDClientList.size() - 1);
        assertThat(testADClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADClient.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testADClient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADClient.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDClientRepository.findAll().size();

        // Create the ADClient with an existing ID
        aDClient.setId(1L);
        ADClientDTO aDClientDTO = aDClientMapper.toDto(aDClient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADClientMockMvc.perform(post("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADClient in the database
        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDClientRepository.findAll().size();
        // set the field null
        aDClient.setName(null);

        // Create the ADClient, which fails.
        ADClientDTO aDClientDTO = aDClientMapper.toDto(aDClient);

        restADClientMockMvc.perform(post("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isBadRequest());

        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDClientRepository.findAll().size();
        // set the field null
        aDClient.setCode(null);

        // Create the ADClient, which fails.
        ADClientDTO aDClientDTO = aDClientMapper.toDto(aDClient);

        restADClientMockMvc.perform(post("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isBadRequest());

        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADClients() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList
        restADClientMockMvc.perform(get("/api/ad-clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADClient() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get the aDClient
        restADClientMockMvc.perform(get("/api/ad-clients/{id}", aDClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDClient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADClientsByIdFiltering() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        Long id = aDClient.getId();

        defaultADClientShouldBeFound("id.equals=" + id);
        defaultADClientShouldNotBeFound("id.notEquals=" + id);

        defaultADClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADClientShouldNotBeFound("id.greaterThan=" + id);

        defaultADClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADClientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADClientsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name equals to DEFAULT_NAME
        defaultADClientShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDClientList where name equals to UPDATED_NAME
        defaultADClientShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADClientsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name not equals to DEFAULT_NAME
        defaultADClientShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDClientList where name not equals to UPDATED_NAME
        defaultADClientShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADClientsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADClientShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDClientList where name equals to UPDATED_NAME
        defaultADClientShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADClientsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name is not null
        defaultADClientShouldBeFound("name.specified=true");

        // Get all the aDClientList where name is null
        defaultADClientShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADClientsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name contains DEFAULT_NAME
        defaultADClientShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDClientList where name contains UPDATED_NAME
        defaultADClientShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADClientsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where name does not contain DEFAULT_NAME
        defaultADClientShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDClientList where name does not contain UPDATED_NAME
        defaultADClientShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADClientsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code equals to DEFAULT_CODE
        defaultADClientShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the aDClientList where code equals to UPDATED_CODE
        defaultADClientShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADClientsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code not equals to DEFAULT_CODE
        defaultADClientShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the aDClientList where code not equals to UPDATED_CODE
        defaultADClientShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADClientsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code in DEFAULT_CODE or UPDATED_CODE
        defaultADClientShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the aDClientList where code equals to UPDATED_CODE
        defaultADClientShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADClientsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code is not null
        defaultADClientShouldBeFound("code.specified=true");

        // Get all the aDClientList where code is null
        defaultADClientShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllADClientsByCodeContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code contains DEFAULT_CODE
        defaultADClientShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the aDClientList where code contains UPDATED_CODE
        defaultADClientShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllADClientsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where code does not contain DEFAULT_CODE
        defaultADClientShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the aDClientList where code does not contain UPDATED_CODE
        defaultADClientShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllADClientsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description equals to DEFAULT_DESCRIPTION
        defaultADClientShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDClientList where description equals to UPDATED_DESCRIPTION
        defaultADClientShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADClientsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description not equals to DEFAULT_DESCRIPTION
        defaultADClientShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDClientList where description not equals to UPDATED_DESCRIPTION
        defaultADClientShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADClientsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADClientShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDClientList where description equals to UPDATED_DESCRIPTION
        defaultADClientShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADClientsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description is not null
        defaultADClientShouldBeFound("description.specified=true");

        // Get all the aDClientList where description is null
        defaultADClientShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADClientsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description contains DEFAULT_DESCRIPTION
        defaultADClientShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDClientList where description contains UPDATED_DESCRIPTION
        defaultADClientShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADClientsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where description does not contain DEFAULT_DESCRIPTION
        defaultADClientShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDClientList where description does not contain UPDATED_DESCRIPTION
        defaultADClientShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADClientsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where active equals to DEFAULT_ACTIVE
        defaultADClientShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDClientList where active equals to UPDATED_ACTIVE
        defaultADClientShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADClientsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where active not equals to DEFAULT_ACTIVE
        defaultADClientShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDClientList where active not equals to UPDATED_ACTIVE
        defaultADClientShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADClientsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADClientShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDClientList where active equals to UPDATED_ACTIVE
        defaultADClientShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADClientsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        // Get all the aDClientList where active is not null
        defaultADClientShouldBeFound("active.specified=true");

        // Get all the aDClientList where active is null
        defaultADClientShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADClientShouldBeFound(String filter) throws Exception {
        restADClientMockMvc.perform(get("/api/ad-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADClientMockMvc.perform(get("/api/ad-clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADClientShouldNotBeFound(String filter) throws Exception {
        restADClientMockMvc.perform(get("/api/ad-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADClientMockMvc.perform(get("/api/ad-clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADClient() throws Exception {
        // Get the aDClient
        restADClientMockMvc.perform(get("/api/ad-clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADClient() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        int databaseSizeBeforeUpdate = aDClientRepository.findAll().size();

        // Update the aDClient
        ADClient updatedADClient = aDClientRepository.findById(aDClient.getId()).get();
        // Disconnect from session so that the updates on updatedADClient are not directly saved in db
        em.detach(updatedADClient);
        updatedADClient
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        ADClientDTO aDClientDTO = aDClientMapper.toDto(updatedADClient);

        restADClientMockMvc.perform(put("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isOk());

        // Validate the ADClient in the database
        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeUpdate);
        ADClient testADClient = aDClientList.get(aDClientList.size() - 1);
        assertThat(testADClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADClient.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testADClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADClient.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADClient() throws Exception {
        int databaseSizeBeforeUpdate = aDClientRepository.findAll().size();

        // Create the ADClient
        ADClientDTO aDClientDTO = aDClientMapper.toDto(aDClient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADClientMockMvc.perform(put("/api/ad-clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDClientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADClient in the database
        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADClient() throws Exception {
        // Initialize the database
        aDClientRepository.saveAndFlush(aDClient);

        int databaseSizeBeforeDelete = aDClientRepository.findAll().size();

        // Delete the aDClient
        restADClientMockMvc.perform(delete("/api/ad-clients/{id}", aDClient.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADClient> aDClientList = aDClientRepository.findAll();
        assertThat(aDClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
