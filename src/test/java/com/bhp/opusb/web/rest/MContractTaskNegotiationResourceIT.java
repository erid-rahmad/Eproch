package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractTaskNegotiation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContractTask;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MContractTaskNegotiationRepository;
import com.bhp.opusb.service.MContractTaskNegotiationService;
import com.bhp.opusb.service.dto.MContractTaskNegotiationDTO;
import com.bhp.opusb.service.mapper.MContractTaskNegotiationMapper;
import com.bhp.opusb.service.dto.MContractTaskNegotiationCriteria;
import com.bhp.opusb.service.MContractTaskNegotiationQueryService;

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
 * Integration tests for the {@link MContractTaskNegotiationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractTaskNegotiationResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_DOCUMENT = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractTaskNegotiationRepository mContractTaskNegotiationRepository;

    @Autowired
    private MContractTaskNegotiationMapper mContractTaskNegotiationMapper;

    @Autowired
    private MContractTaskNegotiationService mContractTaskNegotiationService;

    @Autowired
    private MContractTaskNegotiationQueryService mContractTaskNegotiationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractTaskNegotiationMockMvc;

    private MContractTaskNegotiation mContractTaskNegotiation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTaskNegotiation createEntity(EntityManager em) {
        MContractTaskNegotiation mContractTaskNegotiation = new MContractTaskNegotiation()
            .description(DEFAULT_DESCRIPTION)
            .contractDocument(DEFAULT_CONTRACT_DOCUMENT)
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
        mContractTaskNegotiation.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTask mContractTask;
        if (TestUtil.findAll(em, MContractTask.class).isEmpty()) {
            mContractTask = MContractTaskResourceIT.createEntity(em);
            em.persist(mContractTask);
            em.flush();
        } else {
            mContractTask = TestUtil.findAll(em, MContractTask.class).get(0);
        }
        mContractTaskNegotiation.setContractTask(mContractTask);
        return mContractTaskNegotiation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTaskNegotiation createUpdatedEntity(EntityManager em) {
        MContractTaskNegotiation mContractTaskNegotiation = new MContractTaskNegotiation()
            .description(UPDATED_DESCRIPTION)
            .contractDocument(UPDATED_CONTRACT_DOCUMENT)
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
        mContractTaskNegotiation.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTask mContractTask;
        if (TestUtil.findAll(em, MContractTask.class).isEmpty()) {
            mContractTask = MContractTaskResourceIT.createUpdatedEntity(em);
            em.persist(mContractTask);
            em.flush();
        } else {
            mContractTask = TestUtil.findAll(em, MContractTask.class).get(0);
        }
        mContractTaskNegotiation.setContractTask(mContractTask);
        return mContractTaskNegotiation;
    }

    @BeforeEach
    public void initTest() {
        mContractTaskNegotiation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractTaskNegotiation() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskNegotiationRepository.findAll().size();

        // Create the MContractTaskNegotiation
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = mContractTaskNegotiationMapper.toDto(mContractTaskNegotiation);
        restMContractTaskNegotiationMockMvc.perform(post("/api/m-contract-task-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskNegotiationDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractTaskNegotiation in the database
        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeCreate + 1);
        MContractTaskNegotiation testMContractTaskNegotiation = mContractTaskNegotiationList.get(mContractTaskNegotiationList.size() - 1);
        assertThat(testMContractTaskNegotiation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMContractTaskNegotiation.getContractDocument()).isEqualTo(DEFAULT_CONTRACT_DOCUMENT);
        assertThat(testMContractTaskNegotiation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractTaskNegotiation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractTaskNegotiationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskNegotiationRepository.findAll().size();

        // Create the MContractTaskNegotiation with an existing ID
        mContractTaskNegotiation.setId(1L);
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = mContractTaskNegotiationMapper.toDto(mContractTaskNegotiation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractTaskNegotiationMockMvc.perform(post("/api/m-contract-task-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTaskNegotiation in the database
        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTaskNegotiationRepository.findAll().size();
        // set the field null
        mContractTaskNegotiation.setDescription(null);

        // Create the MContractTaskNegotiation, which fails.
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = mContractTaskNegotiationMapper.toDto(mContractTaskNegotiation);

        restMContractTaskNegotiationMockMvc.perform(post("/api/m-contract-task-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiations() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTaskNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contractDocument").value(hasItem(DEFAULT_CONTRACT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractTaskNegotiation() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get the mContractTaskNegotiation
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations/{id}", mContractTaskNegotiation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractTaskNegotiation.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.contractDocument").value(DEFAULT_CONTRACT_DOCUMENT.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractTaskNegotiationsByIdFiltering() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        Long id = mContractTaskNegotiation.getId();

        defaultMContractTaskNegotiationShouldBeFound("id.equals=" + id);
        defaultMContractTaskNegotiationShouldNotBeFound("id.notEquals=" + id);

        defaultMContractTaskNegotiationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractTaskNegotiationShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractTaskNegotiationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractTaskNegotiationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description equals to DEFAULT_DESCRIPTION
        defaultMContractTaskNegotiationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mContractTaskNegotiationList where description equals to UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description not equals to DEFAULT_DESCRIPTION
        defaultMContractTaskNegotiationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mContractTaskNegotiationList where description not equals to UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mContractTaskNegotiationList where description equals to UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description is not null
        defaultMContractTaskNegotiationShouldBeFound("description.specified=true");

        // Get all the mContractTaskNegotiationList where description is null
        defaultMContractTaskNegotiationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description contains DEFAULT_DESCRIPTION
        defaultMContractTaskNegotiationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mContractTaskNegotiationList where description contains UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where description does not contain DEFAULT_DESCRIPTION
        defaultMContractTaskNegotiationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mContractTaskNegotiationList where description does not contain UPDATED_DESCRIPTION
        defaultMContractTaskNegotiationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where uid equals to DEFAULT_UID
        defaultMContractTaskNegotiationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractTaskNegotiationList where uid equals to UPDATED_UID
        defaultMContractTaskNegotiationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where uid not equals to DEFAULT_UID
        defaultMContractTaskNegotiationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractTaskNegotiationList where uid not equals to UPDATED_UID
        defaultMContractTaskNegotiationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractTaskNegotiationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractTaskNegotiationList where uid equals to UPDATED_UID
        defaultMContractTaskNegotiationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where uid is not null
        defaultMContractTaskNegotiationShouldBeFound("uid.specified=true");

        // Get all the mContractTaskNegotiationList where uid is null
        defaultMContractTaskNegotiationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where active equals to DEFAULT_ACTIVE
        defaultMContractTaskNegotiationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskNegotiationList where active equals to UPDATED_ACTIVE
        defaultMContractTaskNegotiationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where active not equals to DEFAULT_ACTIVE
        defaultMContractTaskNegotiationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskNegotiationList where active not equals to UPDATED_ACTIVE
        defaultMContractTaskNegotiationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractTaskNegotiationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractTaskNegotiationList where active equals to UPDATED_ACTIVE
        defaultMContractTaskNegotiationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        // Get all the mContractTaskNegotiationList where active is not null
        defaultMContractTaskNegotiationShouldBeFound("active.specified=true");

        // Get all the mContractTaskNegotiationList where active is null
        defaultMContractTaskNegotiationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractTaskNegotiation.getAdOrganization();
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractTaskNegotiationList where adOrganization equals to adOrganizationId
        defaultMContractTaskNegotiationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractTaskNegotiationList where adOrganization equals to adOrganizationId + 1
        defaultMContractTaskNegotiationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByContractTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContractTask contractTask = mContractTaskNegotiation.getContractTask();
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);
        Long contractTaskId = contractTask.getId();

        // Get all the mContractTaskNegotiationList where contractTask equals to contractTaskId
        defaultMContractTaskNegotiationShouldBeFound("contractTaskId.equals=" + contractTaskId);

        // Get all the mContractTaskNegotiationList where contractTask equals to contractTaskId + 1
        defaultMContractTaskNegotiationShouldNotBeFound("contractTaskId.equals=" + (contractTaskId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTaskNegotiationsByCAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);
        CAttachment cAttachment = CAttachmentResourceIT.createEntity(em);
        em.persist(cAttachment);
        em.flush();
        mContractTaskNegotiation.setCAttachment(cAttachment);
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);
        Long cAttachmentId = cAttachment.getId();

        // Get all the mContractTaskNegotiationList where cAttachment equals to cAttachmentId
        defaultMContractTaskNegotiationShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the mContractTaskNegotiationList where cAttachment equals to cAttachmentId + 1
        defaultMContractTaskNegotiationShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractTaskNegotiationShouldBeFound(String filter) throws Exception {
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTaskNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contractDocument").value(hasItem(DEFAULT_CONTRACT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractTaskNegotiationShouldNotBeFound(String filter) throws Exception {
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractTaskNegotiation() throws Exception {
        // Get the mContractTaskNegotiation
        restMContractTaskNegotiationMockMvc.perform(get("/api/m-contract-task-negotiations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractTaskNegotiation() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        int databaseSizeBeforeUpdate = mContractTaskNegotiationRepository.findAll().size();

        // Update the mContractTaskNegotiation
        MContractTaskNegotiation updatedMContractTaskNegotiation = mContractTaskNegotiationRepository.findById(mContractTaskNegotiation.getId()).get();
        // Disconnect from session so that the updates on updatedMContractTaskNegotiation are not directly saved in db
        em.detach(updatedMContractTaskNegotiation);
        updatedMContractTaskNegotiation
            .description(UPDATED_DESCRIPTION)
            .contractDocument(UPDATED_CONTRACT_DOCUMENT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = mContractTaskNegotiationMapper.toDto(updatedMContractTaskNegotiation);

        restMContractTaskNegotiationMockMvc.perform(put("/api/m-contract-task-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskNegotiationDTO)))
            .andExpect(status().isOk());

        // Validate the MContractTaskNegotiation in the database
        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeUpdate);
        MContractTaskNegotiation testMContractTaskNegotiation = mContractTaskNegotiationList.get(mContractTaskNegotiationList.size() - 1);
        assertThat(testMContractTaskNegotiation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMContractTaskNegotiation.getContractDocument()).isEqualTo(UPDATED_CONTRACT_DOCUMENT);
        assertThat(testMContractTaskNegotiation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractTaskNegotiation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractTaskNegotiation() throws Exception {
        int databaseSizeBeforeUpdate = mContractTaskNegotiationRepository.findAll().size();

        // Create the MContractTaskNegotiation
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = mContractTaskNegotiationMapper.toDto(mContractTaskNegotiation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractTaskNegotiationMockMvc.perform(put("/api/m-contract-task-negotiations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTaskNegotiation in the database
        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractTaskNegotiation() throws Exception {
        // Initialize the database
        mContractTaskNegotiationRepository.saveAndFlush(mContractTaskNegotiation);

        int databaseSizeBeforeDelete = mContractTaskNegotiationRepository.findAll().size();

        // Delete the mContractTaskNegotiation
        restMContractTaskNegotiationMockMvc.perform(delete("/api/m-contract-task-negotiations/{id}", mContractTaskNegotiation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractTaskNegotiation> mContractTaskNegotiationList = mContractTaskNegotiationRepository.findAll();
        assertThat(mContractTaskNegotiationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
