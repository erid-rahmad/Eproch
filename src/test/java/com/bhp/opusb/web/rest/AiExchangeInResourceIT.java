package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AiExchangeIn;
import com.bhp.opusb.repository.AiExchangeInRepository;
import com.bhp.opusb.service.AiExchangeInService;
import com.bhp.opusb.service.dto.AiExchangeInDTO;
import com.bhp.opusb.service.mapper.AiExchangeInMapper;
import com.bhp.opusb.service.dto.AiExchangeInCriteria;
import com.bhp.opusb.service.AiExchangeInQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhp.opusb.domain.enumeration.AiStatus;
/**
 * Integration tests for the {@link AiExchangeInResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AiExchangeInResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final AiStatus DEFAULT_STATUS = AiStatus.ERROR;
    private static final AiStatus UPDATED_STATUS = AiStatus.PARTIAL;

    @Autowired
    private AiExchangeInRepository aiExchangeInRepository;

    @Autowired
    private AiExchangeInMapper aiExchangeInMapper;

    @Autowired
    private AiExchangeInService aiExchangeInService;

    @Autowired
    private AiExchangeInQueryService aiExchangeInQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAiExchangeInMockMvc;

    private AiExchangeIn aiExchangeIn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiExchangeIn createEntity(EntityManager em) {
        AiExchangeIn aiExchangeIn = new AiExchangeIn()
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .payload(DEFAULT_PAYLOAD)
            .status(DEFAULT_STATUS);
        return aiExchangeIn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiExchangeIn createUpdatedEntity(EntityManager em) {
        AiExchangeIn aiExchangeIn = new AiExchangeIn()
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS);
        return aiExchangeIn;
    }

    @BeforeEach
    public void initTest() {
        aiExchangeIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createAiExchangeIn() throws Exception {
        int databaseSizeBeforeCreate = aiExchangeInRepository.findAll().size();

        // Create the AiExchangeIn
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(aiExchangeIn);
        restAiExchangeInMockMvc.perform(post("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isCreated());

        // Validate the AiExchangeIn in the database
        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeCreate + 1);
        AiExchangeIn testAiExchangeIn = aiExchangeInList.get(aiExchangeInList.size() - 1);
        assertThat(testAiExchangeIn.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testAiExchangeIn.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testAiExchangeIn.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testAiExchangeIn.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAiExchangeInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aiExchangeInRepository.findAll().size();

        // Create the AiExchangeIn with an existing ID
        aiExchangeIn.setId(1L);
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(aiExchangeIn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAiExchangeInMockMvc.perform(post("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AiExchangeIn in the database
        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aiExchangeInRepository.findAll().size();
        // set the field null
        aiExchangeIn.setEntityType(null);

        // Create the AiExchangeIn, which fails.
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(aiExchangeIn);

        restAiExchangeInMockMvc.perform(post("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isBadRequest());

        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = aiExchangeInRepository.findAll().size();
        // set the field null
        aiExchangeIn.setStatus(null);

        // Create the AiExchangeIn, which fails.
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(aiExchangeIn);

        restAiExchangeInMockMvc.perform(post("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isBadRequest());

        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAiExchangeIns() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aiExchangeIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAiExchangeIn() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get the aiExchangeIn
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins/{id}", aiExchangeIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aiExchangeIn.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getAiExchangeInsByIdFiltering() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        Long id = aiExchangeIn.getId();

        defaultAiExchangeInShouldBeFound("id.equals=" + id);
        defaultAiExchangeInShouldNotBeFound("id.notEquals=" + id);

        defaultAiExchangeInShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAiExchangeInShouldNotBeFound("id.greaterThan=" + id);

        defaultAiExchangeInShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAiExchangeInShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType equals to DEFAULT_ENTITY_TYPE
        defaultAiExchangeInShouldBeFound("entityType.equals=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeInList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldNotBeFound("entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType not equals to DEFAULT_ENTITY_TYPE
        defaultAiExchangeInShouldNotBeFound("entityType.notEquals=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeInList where entityType not equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldBeFound("entityType.notEquals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType in DEFAULT_ENTITY_TYPE or UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldBeFound("entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE);

        // Get all the aiExchangeInList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldNotBeFound("entityType.in=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType is not null
        defaultAiExchangeInShouldBeFound("entityType.specified=true");

        // Get all the aiExchangeInList where entityType is null
        defaultAiExchangeInShouldNotBeFound("entityType.specified=false");
    }
                @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeContainsSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType contains DEFAULT_ENTITY_TYPE
        defaultAiExchangeInShouldBeFound("entityType.contains=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeInList where entityType contains UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldNotBeFound("entityType.contains=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityType does not contain DEFAULT_ENTITY_TYPE
        defaultAiExchangeInShouldNotBeFound("entityType.doesNotContain=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeInList where entityType does not contain UPDATED_ENTITY_TYPE
        defaultAiExchangeInShouldBeFound("entityType.doesNotContain=" + UPDATED_ENTITY_TYPE);
    }


    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId equals to DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId equals to UPDATED_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId not equals to DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId not equals to UPDATED_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the aiExchangeInList where entityId equals to UPDATED_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId is not null
        defaultAiExchangeInShouldBeFound("entityId.specified=true");

        // Get all the aiExchangeInList where entityId is null
        defaultAiExchangeInShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId is less than DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId is less than UPDATED_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where entityId is greater than DEFAULT_ENTITY_ID
        defaultAiExchangeInShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeInList where entityId is greater than SMALLER_ENTITY_ID
        defaultAiExchangeInShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllAiExchangeInsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where status equals to DEFAULT_STATUS
        defaultAiExchangeInShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the aiExchangeInList where status equals to UPDATED_STATUS
        defaultAiExchangeInShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where status not equals to DEFAULT_STATUS
        defaultAiExchangeInShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the aiExchangeInList where status not equals to UPDATED_STATUS
        defaultAiExchangeInShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAiExchangeInShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the aiExchangeInList where status equals to UPDATED_STATUS
        defaultAiExchangeInShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeInsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        // Get all the aiExchangeInList where status is not null
        defaultAiExchangeInShouldBeFound("status.specified=true");

        // Get all the aiExchangeInList where status is null
        defaultAiExchangeInShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAiExchangeInShouldBeFound(String filter) throws Exception {
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aiExchangeIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAiExchangeInShouldNotBeFound(String filter) throws Exception {
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAiExchangeIn() throws Exception {
        // Get the aiExchangeIn
        restAiExchangeInMockMvc.perform(get("/api/ai-exchange-ins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAiExchangeIn() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        int databaseSizeBeforeUpdate = aiExchangeInRepository.findAll().size();

        // Update the aiExchangeIn
        AiExchangeIn updatedAiExchangeIn = aiExchangeInRepository.findById(aiExchangeIn.getId()).get();
        // Disconnect from session so that the updates on updatedAiExchangeIn are not directly saved in db
        em.detach(updatedAiExchangeIn);
        updatedAiExchangeIn
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS);
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(updatedAiExchangeIn);

        restAiExchangeInMockMvc.perform(put("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isOk());

        // Validate the AiExchangeIn in the database
        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeUpdate);
        AiExchangeIn testAiExchangeIn = aiExchangeInList.get(aiExchangeInList.size() - 1);
        assertThat(testAiExchangeIn.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testAiExchangeIn.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testAiExchangeIn.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testAiExchangeIn.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAiExchangeIn() throws Exception {
        int databaseSizeBeforeUpdate = aiExchangeInRepository.findAll().size();

        // Create the AiExchangeIn
        AiExchangeInDTO aiExchangeInDTO = aiExchangeInMapper.toDto(aiExchangeIn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAiExchangeInMockMvc.perform(put("/api/ai-exchange-ins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AiExchangeIn in the database
        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAiExchangeIn() throws Exception {
        // Initialize the database
        aiExchangeInRepository.saveAndFlush(aiExchangeIn);

        int databaseSizeBeforeDelete = aiExchangeInRepository.findAll().size();

        // Delete the aiExchangeIn
        restAiExchangeInMockMvc.perform(delete("/api/ai-exchange-ins/{id}", aiExchangeIn.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AiExchangeIn> aiExchangeInList = aiExchangeInRepository.findAll();
        assertThat(aiExchangeInList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
