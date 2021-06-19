package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractDocument;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MContractDocumentRepository;
import com.bhp.opusb.service.MContractDocumentService;
import com.bhp.opusb.service.dto.MContractDocumentDTO;
import com.bhp.opusb.service.mapper.MContractDocumentMapper;
import com.bhp.opusb.service.dto.MContractDocumentCriteria;
import com.bhp.opusb.service.MContractDocumentQueryService;

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
 * Integration tests for the {@link MContractDocumentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractDocumentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractDocumentRepository mContractDocumentRepository;

    @Autowired
    private MContractDocumentMapper mContractDocumentMapper;

    @Autowired
    private MContractDocumentService mContractDocumentService;

    @Autowired
    private MContractDocumentQueryService mContractDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractDocumentMockMvc;

    private MContractDocument mContractDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractDocument createEntity(EntityManager em) {
        MContractDocument mContractDocument = new MContractDocument()
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
        mContractDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractDocument.setContract(mContract);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mContractDocument.setAttachment(cAttachment);
        return mContractDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractDocument createUpdatedEntity(EntityManager em) {
        MContractDocument mContractDocument = new MContractDocument()
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
        mContractDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractDocument.setContract(mContract);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mContractDocument.setAttachment(cAttachment);
        return mContractDocument;
    }

    @BeforeEach
    public void initTest() {
        mContractDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractDocument() throws Exception {
        int databaseSizeBeforeCreate = mContractDocumentRepository.findAll().size();

        // Create the MContractDocument
        MContractDocumentDTO mContractDocumentDTO = mContractDocumentMapper.toDto(mContractDocument);
        restMContractDocumentMockMvc.perform(post("/api/m-contract-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractDocument in the database
        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        MContractDocument testMContractDocument = mContractDocumentList.get(mContractDocumentList.size() - 1);
        assertThat(testMContractDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMContractDocument.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractDocument.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractDocumentRepository.findAll().size();

        // Create the MContractDocument with an existing ID
        mContractDocument.setId(1L);
        MContractDocumentDTO mContractDocumentDTO = mContractDocumentMapper.toDto(mContractDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractDocumentMockMvc.perform(post("/api/m-contract-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractDocument in the database
        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractDocumentRepository.findAll().size();
        // set the field null
        mContractDocument.setName(null);

        // Create the MContractDocument, which fails.
        MContractDocumentDTO mContractDocumentDTO = mContractDocumentMapper.toDto(mContractDocument);

        restMContractDocumentMockMvc.perform(post("/api/m-contract-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractDocuments() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractDocument() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get the mContractDocument
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents/{id}", mContractDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractDocument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        Long id = mContractDocument.getId();

        defaultMContractDocumentShouldBeFound("id.equals=" + id);
        defaultMContractDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultMContractDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractDocumentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name equals to DEFAULT_NAME
        defaultMContractDocumentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mContractDocumentList where name equals to UPDATED_NAME
        defaultMContractDocumentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name not equals to DEFAULT_NAME
        defaultMContractDocumentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mContractDocumentList where name not equals to UPDATED_NAME
        defaultMContractDocumentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMContractDocumentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mContractDocumentList where name equals to UPDATED_NAME
        defaultMContractDocumentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name is not null
        defaultMContractDocumentShouldBeFound("name.specified=true");

        // Get all the mContractDocumentList where name is null
        defaultMContractDocumentShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractDocumentsByNameContainsSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name contains DEFAULT_NAME
        defaultMContractDocumentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mContractDocumentList where name contains UPDATED_NAME
        defaultMContractDocumentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where name does not contain DEFAULT_NAME
        defaultMContractDocumentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mContractDocumentList where name does not contain UPDATED_NAME
        defaultMContractDocumentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMContractDocumentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where uid equals to DEFAULT_UID
        defaultMContractDocumentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractDocumentList where uid equals to UPDATED_UID
        defaultMContractDocumentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where uid not equals to DEFAULT_UID
        defaultMContractDocumentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractDocumentList where uid not equals to UPDATED_UID
        defaultMContractDocumentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractDocumentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractDocumentList where uid equals to UPDATED_UID
        defaultMContractDocumentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where uid is not null
        defaultMContractDocumentShouldBeFound("uid.specified=true");

        // Get all the mContractDocumentList where uid is null
        defaultMContractDocumentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where active equals to DEFAULT_ACTIVE
        defaultMContractDocumentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractDocumentList where active equals to UPDATED_ACTIVE
        defaultMContractDocumentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where active not equals to DEFAULT_ACTIVE
        defaultMContractDocumentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractDocumentList where active not equals to UPDATED_ACTIVE
        defaultMContractDocumentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractDocumentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractDocumentList where active equals to UPDATED_ACTIVE
        defaultMContractDocumentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        // Get all the mContractDocumentList where active is not null
        defaultMContractDocumentShouldBeFound("active.specified=true");

        // Get all the mContractDocumentList where active is null
        defaultMContractDocumentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractDocumentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractDocument.getAdOrganization();
        mContractDocumentRepository.saveAndFlush(mContractDocument);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractDocumentList where adOrganization equals to adOrganizationId
        defaultMContractDocumentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractDocumentList where adOrganization equals to adOrganizationId + 1
        defaultMContractDocumentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractDocumentsByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractDocument.getContract();
        mContractDocumentRepository.saveAndFlush(mContractDocument);
        Long contractId = contract.getId();

        // Get all the mContractDocumentList where contract equals to contractId
        defaultMContractDocumentShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractDocumentList where contract equals to contractId + 1
        defaultMContractDocumentShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractDocumentsByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mContractDocument.getAttachment();
        mContractDocumentRepository.saveAndFlush(mContractDocument);
        Long attachmentId = attachment.getId();

        // Get all the mContractDocumentList where attachment equals to attachmentId
        defaultMContractDocumentShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mContractDocumentList where attachment equals to attachmentId + 1
        defaultMContractDocumentShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractDocumentShouldBeFound(String filter) throws Exception {
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractDocumentShouldNotBeFound(String filter) throws Exception {
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractDocument() throws Exception {
        // Get the mContractDocument
        restMContractDocumentMockMvc.perform(get("/api/m-contract-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractDocument() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        int databaseSizeBeforeUpdate = mContractDocumentRepository.findAll().size();

        // Update the mContractDocument
        MContractDocument updatedMContractDocument = mContractDocumentRepository.findById(mContractDocument.getId()).get();
        // Disconnect from session so that the updates on updatedMContractDocument are not directly saved in db
        em.detach(updatedMContractDocument);
        updatedMContractDocument
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractDocumentDTO mContractDocumentDTO = mContractDocumentMapper.toDto(updatedMContractDocument);

        restMContractDocumentMockMvc.perform(put("/api/m-contract-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the MContractDocument in the database
        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeUpdate);
        MContractDocument testMContractDocument = mContractDocumentList.get(mContractDocumentList.size() - 1);
        assertThat(testMContractDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMContractDocument.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractDocument.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractDocument() throws Exception {
        int databaseSizeBeforeUpdate = mContractDocumentRepository.findAll().size();

        // Create the MContractDocument
        MContractDocumentDTO mContractDocumentDTO = mContractDocumentMapper.toDto(mContractDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractDocumentMockMvc.perform(put("/api/m-contract-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractDocument in the database
        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractDocument() throws Exception {
        // Initialize the database
        mContractDocumentRepository.saveAndFlush(mContractDocument);

        int databaseSizeBeforeDelete = mContractDocumentRepository.findAll().size();

        // Delete the mContractDocument
        restMContractDocumentMockMvc.perform(delete("/api/m-contract-documents/{id}", mContractDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractDocument> mContractDocumentList = mContractDocumentRepository.findAll();
        assertThat(mContractDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
