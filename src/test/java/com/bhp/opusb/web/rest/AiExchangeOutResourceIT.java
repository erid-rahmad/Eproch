package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.service.AiExchangeOutService;
import com.bhp.opusb.service.dto.AiExchangeOutDTO;
import com.bhp.opusb.service.mapper.AiExchangeOutMapper;
import com.bhp.opusb.service.dto.AiExchangeOutCriteria;
import com.bhp.opusb.service.AiExchangeOutQueryService;

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
 * Integration tests for the {@link AiExchangeOutResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AiExchangeOutResourceIT {

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
    private AiExchangeOutRepository aiExchangeOutRepository;

    @Autowired
    private AiExchangeOutMapper aiExchangeOutMapper;

    @Autowired
    private AiExchangeOutService aiExchangeOutService;

    @Autowired
    private AiExchangeOutQueryService aiExchangeOutQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAiExchangeOutMockMvc;

    private AiExchangeOut aiExchangeOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiExchangeOut createEntity(EntityManager em) {
        AiExchangeOut aiExchangeOut = new AiExchangeOut()
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .payload(DEFAULT_PAYLOAD)
            .status(DEFAULT_STATUS);
        return aiExchangeOut;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiExchangeOut createUpdatedEntity(EntityManager em) {
        AiExchangeOut aiExchangeOut = new AiExchangeOut()
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS);
        return aiExchangeOut;
    }

    @BeforeEach
    public void initTest() {
        aiExchangeOut = createEntity(em);
    }

    @Test
    @Transactional
    public void createAiExchangeOut() throws Exception {
        int databaseSizeBeforeCreate = aiExchangeOutRepository.findAll().size();

        // Create the AiExchangeOut
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(aiExchangeOut);
        restAiExchangeOutMockMvc.perform(post("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isCreated());

        // Validate the AiExchangeOut in the database
        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeCreate + 1);
        AiExchangeOut testAiExchangeOut = aiExchangeOutList.get(aiExchangeOutList.size() - 1);
        assertThat(testAiExchangeOut.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testAiExchangeOut.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testAiExchangeOut.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testAiExchangeOut.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAiExchangeOutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aiExchangeOutRepository.findAll().size();

        // Create the AiExchangeOut with an existing ID
        aiExchangeOut.setId(1L);
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(aiExchangeOut);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAiExchangeOutMockMvc.perform(post("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AiExchangeOut in the database
        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aiExchangeOutRepository.findAll().size();
        // set the field null
        aiExchangeOut.setEntityType(null);

        // Create the AiExchangeOut, which fails.
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(aiExchangeOut);

        restAiExchangeOutMockMvc.perform(post("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isBadRequest());

        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = aiExchangeOutRepository.findAll().size();
        // set the field null
        aiExchangeOut.setStatus(null);

        // Create the AiExchangeOut, which fails.
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(aiExchangeOut);

        restAiExchangeOutMockMvc.perform(post("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isBadRequest());

        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOuts() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aiExchangeOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAiExchangeOut() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get the aiExchangeOut
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs/{id}", aiExchangeOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aiExchangeOut.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getAiExchangeOutsByIdFiltering() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        Long id = aiExchangeOut.getId();

        defaultAiExchangeOutShouldBeFound("id.equals=" + id);
        defaultAiExchangeOutShouldNotBeFound("id.notEquals=" + id);

        defaultAiExchangeOutShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAiExchangeOutShouldNotBeFound("id.greaterThan=" + id);

        defaultAiExchangeOutShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAiExchangeOutShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType equals to DEFAULT_ENTITY_TYPE
        defaultAiExchangeOutShouldBeFound("entityType.equals=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeOutList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldNotBeFound("entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType not equals to DEFAULT_ENTITY_TYPE
        defaultAiExchangeOutShouldNotBeFound("entityType.notEquals=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeOutList where entityType not equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldBeFound("entityType.notEquals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType in DEFAULT_ENTITY_TYPE or UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldBeFound("entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE);

        // Get all the aiExchangeOutList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldNotBeFound("entityType.in=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType is not null
        defaultAiExchangeOutShouldBeFound("entityType.specified=true");

        // Get all the aiExchangeOutList where entityType is null
        defaultAiExchangeOutShouldNotBeFound("entityType.specified=false");
    }
                @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeContainsSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType contains DEFAULT_ENTITY_TYPE
        defaultAiExchangeOutShouldBeFound("entityType.contains=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeOutList where entityType contains UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldNotBeFound("entityType.contains=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityType does not contain DEFAULT_ENTITY_TYPE
        defaultAiExchangeOutShouldNotBeFound("entityType.doesNotContain=" + DEFAULT_ENTITY_TYPE);

        // Get all the aiExchangeOutList where entityType does not contain UPDATED_ENTITY_TYPE
        defaultAiExchangeOutShouldBeFound("entityType.doesNotContain=" + UPDATED_ENTITY_TYPE);
    }


    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId equals to DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId equals to UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId not equals to DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId not equals to UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId equals to UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId is not null
        defaultAiExchangeOutShouldBeFound("entityId.specified=true");

        // Get all the aiExchangeOutList where entityId is null
        defaultAiExchangeOutShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId is less than DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId is less than UPDATED_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where entityId is greater than DEFAULT_ENTITY_ID
        defaultAiExchangeOutShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the aiExchangeOutList where entityId is greater than SMALLER_ENTITY_ID
        defaultAiExchangeOutShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllAiExchangeOutsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where status equals to DEFAULT_STATUS
        defaultAiExchangeOutShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the aiExchangeOutList where status equals to UPDATED_STATUS
        defaultAiExchangeOutShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where status not equals to DEFAULT_STATUS
        defaultAiExchangeOutShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the aiExchangeOutList where status not equals to UPDATED_STATUS
        defaultAiExchangeOutShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAiExchangeOutShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the aiExchangeOutList where status equals to UPDATED_STATUS
        defaultAiExchangeOutShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAiExchangeOutsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        // Get all the aiExchangeOutList where status is not null
        defaultAiExchangeOutShouldBeFound("status.specified=true");

        // Get all the aiExchangeOutList where status is null
        defaultAiExchangeOutShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAiExchangeOutShouldBeFound(String filter) throws Exception {
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aiExchangeOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAiExchangeOutShouldNotBeFound(String filter) throws Exception {
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAiExchangeOut() throws Exception {
        // Get the aiExchangeOut
        restAiExchangeOutMockMvc.perform(get("/api/ai-exchange-outs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAiExchangeOut() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        int databaseSizeBeforeUpdate = aiExchangeOutRepository.findAll().size();

        // Update the aiExchangeOut
        AiExchangeOut updatedAiExchangeOut = aiExchangeOutRepository.findById(aiExchangeOut.getId()).get();
        // Disconnect from session so that the updates on updatedAiExchangeOut are not directly saved in db
        em.detach(updatedAiExchangeOut);
        updatedAiExchangeOut
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS);
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(updatedAiExchangeOut);

        restAiExchangeOutMockMvc.perform(put("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isOk());

        // Validate the AiExchangeOut in the database
        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeUpdate);
        AiExchangeOut testAiExchangeOut = aiExchangeOutList.get(aiExchangeOutList.size() - 1);
        assertThat(testAiExchangeOut.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testAiExchangeOut.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testAiExchangeOut.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testAiExchangeOut.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAiExchangeOut() throws Exception {
        int databaseSizeBeforeUpdate = aiExchangeOutRepository.findAll().size();

        // Create the AiExchangeOut
        AiExchangeOutDTO aiExchangeOutDTO = aiExchangeOutMapper.toDto(aiExchangeOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAiExchangeOutMockMvc.perform(put("/api/ai-exchange-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aiExchangeOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AiExchangeOut in the database
        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAiExchangeOut() throws Exception {
        // Initialize the database
        aiExchangeOutRepository.saveAndFlush(aiExchangeOut);

        int databaseSizeBeforeDelete = aiExchangeOutRepository.findAll().size();

        // Delete the aiExchangeOut
        restAiExchangeOutMockMvc.perform(delete("/api/ai-exchange-outs/{id}", aiExchangeOut.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AiExchangeOut> aiExchangeOutList = aiExchangeOutRepository.findAll();
        assertThat(aiExchangeOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
