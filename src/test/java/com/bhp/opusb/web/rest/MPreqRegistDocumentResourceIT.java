package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPreqRegistDocument;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalRegistration;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MPreqRegistDocumentRepository;
import com.bhp.opusb.service.MPreqRegistDocumentService;
import com.bhp.opusb.service.dto.MPreqRegistDocumentDTO;
import com.bhp.opusb.service.mapper.MPreqRegistDocumentMapper;
import com.bhp.opusb.service.dto.MPreqRegistDocumentCriteria;
import com.bhp.opusb.service.MPreqRegistDocumentQueryService;

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
 * Integration tests for the {@link MPreqRegistDocumentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPreqRegistDocumentResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPreqRegistDocumentRepository mPreqRegistDocumentRepository;

    @Autowired
    private MPreqRegistDocumentMapper mPreqRegistDocumentMapper;

    @Autowired
    private MPreqRegistDocumentService mPreqRegistDocumentService;

    @Autowired
    private MPreqRegistDocumentQueryService mPreqRegistDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPreqRegistDocumentMockMvc;

    private MPreqRegistDocument mPreqRegistDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreqRegistDocument createEntity(EntityManager em) {
        MPreqRegistDocument mPreqRegistDocument = new MPreqRegistDocument()
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
        mPreqRegistDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalRegistration mPrequalRegistration;
        if (TestUtil.findAll(em, MPrequalRegistration.class).isEmpty()) {
            mPrequalRegistration = MPrequalRegistrationResourceIT.createEntity(em);
            em.persist(mPrequalRegistration);
            em.flush();
        } else {
            mPrequalRegistration = TestUtil.findAll(em, MPrequalRegistration.class).get(0);
        }
        mPreqRegistDocument.setRegistration(mPrequalRegistration);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPreqRegistDocument.setSiupDocument(cAttachment);
        // Add required entity
        mPreqRegistDocument.setSpdaDocument(cAttachment);
        return mPreqRegistDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPreqRegistDocument createUpdatedEntity(EntityManager em) {
        MPreqRegistDocument mPreqRegistDocument = new MPreqRegistDocument()
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
        mPreqRegistDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalRegistration mPrequalRegistration;
        if (TestUtil.findAll(em, MPrequalRegistration.class).isEmpty()) {
            mPrequalRegistration = MPrequalRegistrationResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalRegistration);
            em.flush();
        } else {
            mPrequalRegistration = TestUtil.findAll(em, MPrequalRegistration.class).get(0);
        }
        mPreqRegistDocument.setRegistration(mPrequalRegistration);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPreqRegistDocument.setSiupDocument(cAttachment);
        // Add required entity
        mPreqRegistDocument.setSpdaDocument(cAttachment);
        return mPreqRegistDocument;
    }

    @BeforeEach
    public void initTest() {
        mPreqRegistDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPreqRegistDocument() throws Exception {
        int databaseSizeBeforeCreate = mPreqRegistDocumentRepository.findAll().size();

        // Create the MPreqRegistDocument
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO = mPreqRegistDocumentMapper.toDto(mPreqRegistDocument);
        restMPreqRegistDocumentMockMvc.perform(post("/api/m-preq-regist-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the MPreqRegistDocument in the database
        List<MPreqRegistDocument> mPreqRegistDocumentList = mPreqRegistDocumentRepository.findAll();
        assertThat(mPreqRegistDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        MPreqRegistDocument testMPreqRegistDocument = mPreqRegistDocumentList.get(mPreqRegistDocumentList.size() - 1);
        assertThat(testMPreqRegistDocument.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPreqRegistDocument.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPreqRegistDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPreqRegistDocumentRepository.findAll().size();

        // Create the MPreqRegistDocument with an existing ID
        mPreqRegistDocument.setId(1L);
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO = mPreqRegistDocumentMapper.toDto(mPreqRegistDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPreqRegistDocumentMockMvc.perform(post("/api/m-preq-regist-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreqRegistDocument in the database
        List<MPreqRegistDocument> mPreqRegistDocumentList = mPreqRegistDocumentRepository.findAll();
        assertThat(mPreqRegistDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPreqRegistDocuments() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreqRegistDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPreqRegistDocument() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get the mPreqRegistDocument
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents/{id}", mPreqRegistDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPreqRegistDocument.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPreqRegistDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        Long id = mPreqRegistDocument.getId();

        defaultMPreqRegistDocumentShouldBeFound("id.equals=" + id);
        defaultMPreqRegistDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultMPreqRegistDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPreqRegistDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultMPreqRegistDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPreqRegistDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where uid equals to DEFAULT_UID
        defaultMPreqRegistDocumentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPreqRegistDocumentList where uid equals to UPDATED_UID
        defaultMPreqRegistDocumentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where uid not equals to DEFAULT_UID
        defaultMPreqRegistDocumentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPreqRegistDocumentList where uid not equals to UPDATED_UID
        defaultMPreqRegistDocumentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPreqRegistDocumentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPreqRegistDocumentList where uid equals to UPDATED_UID
        defaultMPreqRegistDocumentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where uid is not null
        defaultMPreqRegistDocumentShouldBeFound("uid.specified=true");

        // Get all the mPreqRegistDocumentList where uid is null
        defaultMPreqRegistDocumentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where active equals to DEFAULT_ACTIVE
        defaultMPreqRegistDocumentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPreqRegistDocumentList where active equals to UPDATED_ACTIVE
        defaultMPreqRegistDocumentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where active not equals to DEFAULT_ACTIVE
        defaultMPreqRegistDocumentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPreqRegistDocumentList where active not equals to UPDATED_ACTIVE
        defaultMPreqRegistDocumentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPreqRegistDocumentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPreqRegistDocumentList where active equals to UPDATED_ACTIVE
        defaultMPreqRegistDocumentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        // Get all the mPreqRegistDocumentList where active is not null
        defaultMPreqRegistDocumentShouldBeFound("active.specified=true");

        // Get all the mPreqRegistDocumentList where active is null
        defaultMPreqRegistDocumentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPreqRegistDocument.getAdOrganization();
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPreqRegistDocumentList where adOrganization equals to adOrganizationId
        defaultMPreqRegistDocumentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPreqRegistDocumentList where adOrganization equals to adOrganizationId + 1
        defaultMPreqRegistDocumentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsByRegistrationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalRegistration registration = mPreqRegistDocument.getRegistration();
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);
        Long registrationId = registration.getId();

        // Get all the mPreqRegistDocumentList where registration equals to registrationId
        defaultMPreqRegistDocumentShouldBeFound("registrationId.equals=" + registrationId);

        // Get all the mPreqRegistDocumentList where registration equals to registrationId + 1
        defaultMPreqRegistDocumentShouldNotBeFound("registrationId.equals=" + (registrationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsBySiupDocumentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment siupDocument = mPreqRegistDocument.getSiupDocument();
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);
        Long siupDocumentId = siupDocument.getId();

        // Get all the mPreqRegistDocumentList where siupDocument equals to siupDocumentId
        defaultMPreqRegistDocumentShouldBeFound("siupDocumentId.equals=" + siupDocumentId);

        // Get all the mPreqRegistDocumentList where siupDocument equals to siupDocumentId + 1
        defaultMPreqRegistDocumentShouldNotBeFound("siupDocumentId.equals=" + (siupDocumentId + 1));
    }


    @Test
    @Transactional
    public void getAllMPreqRegistDocumentsBySpdaDocumentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment spdaDocument = mPreqRegistDocument.getSpdaDocument();
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);
        Long spdaDocumentId = spdaDocument.getId();

        // Get all the mPreqRegistDocumentList where spdaDocument equals to spdaDocumentId
        defaultMPreqRegistDocumentShouldBeFound("spdaDocumentId.equals=" + spdaDocumentId);

        // Get all the mPreqRegistDocumentList where spdaDocument equals to spdaDocumentId + 1
        defaultMPreqRegistDocumentShouldNotBeFound("spdaDocumentId.equals=" + (spdaDocumentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPreqRegistDocumentShouldBeFound(String filter) throws Exception {
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPreqRegistDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPreqRegistDocumentShouldNotBeFound(String filter) throws Exception {
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPreqRegistDocument() throws Exception {
        // Get the mPreqRegistDocument
        restMPreqRegistDocumentMockMvc.perform(get("/api/m-preq-regist-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPreqRegistDocument() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        int databaseSizeBeforeUpdate = mPreqRegistDocumentRepository.findAll().size();

        // Update the mPreqRegistDocument
        MPreqRegistDocument updatedMPreqRegistDocument = mPreqRegistDocumentRepository.findById(mPreqRegistDocument.getId()).get();
        // Disconnect from session so that the updates on updatedMPreqRegistDocument are not directly saved in db
        em.detach(updatedMPreqRegistDocument);
        updatedMPreqRegistDocument
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO = mPreqRegistDocumentMapper.toDto(updatedMPreqRegistDocument);

        restMPreqRegistDocumentMockMvc.perform(put("/api/m-preq-regist-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the MPreqRegistDocument in the database
        List<MPreqRegistDocument> mPreqRegistDocumentList = mPreqRegistDocumentRepository.findAll();
        assertThat(mPreqRegistDocumentList).hasSize(databaseSizeBeforeUpdate);
        MPreqRegistDocument testMPreqRegistDocument = mPreqRegistDocumentList.get(mPreqRegistDocumentList.size() - 1);
        assertThat(testMPreqRegistDocument.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPreqRegistDocument.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPreqRegistDocument() throws Exception {
        int databaseSizeBeforeUpdate = mPreqRegistDocumentRepository.findAll().size();

        // Create the MPreqRegistDocument
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO = mPreqRegistDocumentMapper.toDto(mPreqRegistDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPreqRegistDocumentMockMvc.perform(put("/api/m-preq-regist-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPreqRegistDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPreqRegistDocument in the database
        List<MPreqRegistDocument> mPreqRegistDocumentList = mPreqRegistDocumentRepository.findAll();
        assertThat(mPreqRegistDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPreqRegistDocument() throws Exception {
        // Initialize the database
        mPreqRegistDocumentRepository.saveAndFlush(mPreqRegistDocument);

        int databaseSizeBeforeDelete = mPreqRegistDocumentRepository.findAll().size();

        // Delete the mPreqRegistDocument
        restMPreqRegistDocumentMockMvc.perform(delete("/api/m-preq-regist-documents/{id}", mPreqRegistDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPreqRegistDocument> mPreqRegistDocumentList = mPreqRegistDocumentRepository.findAll();
        assertThat(mPreqRegistDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
