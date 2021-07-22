package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MPrequalificationEvalFile;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.repository.MPrequalificationEvalFileRepository;
import com.bhp.opusb.service.MPrequalificationEvalFileService;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalFileMapper;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileCriteria;
import com.bhp.opusb.service.MPrequalificationEvalFileQueryService;

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
 * Integration tests for the {@link MPrequalificationEvalFileResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MPrequalificationEvalFileResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MPrequalificationEvalFileRepository mPrequalificationEvalFileRepository;

    @Autowired
    private MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper;

    @Autowired
    private MPrequalificationEvalFileService mPrequalificationEvalFileService;

    @Autowired
    private MPrequalificationEvalFileQueryService mPrequalificationEvalFileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMPrequalificationEvalFileMockMvc;

    private MPrequalificationEvalFile mPrequalificationEvalFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEvalFile createEntity(EntityManager em) {
        MPrequalificationEvalFile mPrequalificationEvalFile = new MPrequalificationEvalFile()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPrequalificationEvalFile.setAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationEvalFile.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationEvalFile.setPrequalificationSubmission(mPrequalificationSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mPrequalificationEvalFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mPrequalificationEvalFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MPrequalificationEvalFile createUpdatedEntity(EntityManager em) {
        MPrequalificationEvalFile mPrequalificationEvalFile = new MPrequalificationEvalFile()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mPrequalificationEvalFile.setAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mPrequalificationEvalFile.setAdOrganization(aDOrganization);
        // Add required entity
        MPrequalificationSubmission mPrequalificationSubmission;
        if (TestUtil.findAll(em, MPrequalificationSubmission.class).isEmpty()) {
            mPrequalificationSubmission = MPrequalificationSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mPrequalificationSubmission);
            em.flush();
        } else {
            mPrequalificationSubmission = TestUtil.findAll(em, MPrequalificationSubmission.class).get(0);
        }
        mPrequalificationEvalFile.setPrequalificationSubmission(mPrequalificationSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mPrequalificationEvalFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mPrequalificationEvalFile;
    }

    @BeforeEach
    public void initTest() {
        mPrequalificationEvalFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPrequalificationEvalFile() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEvalFileRepository.findAll().size();

        // Create the MPrequalificationEvalFile
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO = mPrequalificationEvalFileMapper.toDto(mPrequalificationEvalFile);
        restMPrequalificationEvalFileMockMvc.perform(post("/api/m-prequalification-eval-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalFileDTO)))
            .andExpect(status().isCreated());

        // Validate the MPrequalificationEvalFile in the database
        List<MPrequalificationEvalFile> mPrequalificationEvalFileList = mPrequalificationEvalFileRepository.findAll();
        assertThat(mPrequalificationEvalFileList).hasSize(databaseSizeBeforeCreate + 1);
        MPrequalificationEvalFile testMPrequalificationEvalFile = mPrequalificationEvalFileList.get(mPrequalificationEvalFileList.size() - 1);
        assertThat(testMPrequalificationEvalFile.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMPrequalificationEvalFile.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMPrequalificationEvalFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPrequalificationEvalFileRepository.findAll().size();

        // Create the MPrequalificationEvalFile with an existing ID
        mPrequalificationEvalFile.setId(1L);
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO = mPrequalificationEvalFileMapper.toDto(mPrequalificationEvalFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPrequalificationEvalFileMockMvc.perform(post("/api/m-prequalification-eval-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEvalFile in the database
        List<MPrequalificationEvalFile> mPrequalificationEvalFileList = mPrequalificationEvalFileRepository.findAll();
        assertThat(mPrequalificationEvalFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalFiles() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEvalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMPrequalificationEvalFile() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get the mPrequalificationEvalFile
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files/{id}", mPrequalificationEvalFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mPrequalificationEvalFile.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMPrequalificationEvalFilesByIdFiltering() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        Long id = mPrequalificationEvalFile.getId();

        defaultMPrequalificationEvalFileShouldBeFound("id.equals=" + id);
        defaultMPrequalificationEvalFileShouldNotBeFound("id.notEquals=" + id);

        defaultMPrequalificationEvalFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMPrequalificationEvalFileShouldNotBeFound("id.greaterThan=" + id);

        defaultMPrequalificationEvalFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMPrequalificationEvalFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where uid equals to DEFAULT_UID
        defaultMPrequalificationEvalFileShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mPrequalificationEvalFileList where uid equals to UPDATED_UID
        defaultMPrequalificationEvalFileShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where uid not equals to DEFAULT_UID
        defaultMPrequalificationEvalFileShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mPrequalificationEvalFileList where uid not equals to UPDATED_UID
        defaultMPrequalificationEvalFileShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where uid in DEFAULT_UID or UPDATED_UID
        defaultMPrequalificationEvalFileShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mPrequalificationEvalFileList where uid equals to UPDATED_UID
        defaultMPrequalificationEvalFileShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where uid is not null
        defaultMPrequalificationEvalFileShouldBeFound("uid.specified=true");

        // Get all the mPrequalificationEvalFileList where uid is null
        defaultMPrequalificationEvalFileShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where active equals to DEFAULT_ACTIVE
        defaultMPrequalificationEvalFileShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEvalFileList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalFileShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where active not equals to DEFAULT_ACTIVE
        defaultMPrequalificationEvalFileShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mPrequalificationEvalFileList where active not equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalFileShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMPrequalificationEvalFileShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mPrequalificationEvalFileList where active equals to UPDATED_ACTIVE
        defaultMPrequalificationEvalFileShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        // Get all the mPrequalificationEvalFileList where active is not null
        defaultMPrequalificationEvalFileShouldBeFound("active.specified=true");

        // Get all the mPrequalificationEvalFileList where active is null
        defaultMPrequalificationEvalFileShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mPrequalificationEvalFile.getAttachment();
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);
        Long attachmentId = attachment.getId();

        // Get all the mPrequalificationEvalFileList where attachment equals to attachmentId
        defaultMPrequalificationEvalFileShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mPrequalificationEvalFileList where attachment equals to attachmentId + 1
        defaultMPrequalificationEvalFileShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mPrequalificationEvalFile.getAdOrganization();
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mPrequalificationEvalFileList where adOrganization equals to adOrganizationId
        defaultMPrequalificationEvalFileShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mPrequalificationEvalFileList where adOrganization equals to adOrganizationId + 1
        defaultMPrequalificationEvalFileShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByPrequalificationSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MPrequalificationSubmission prequalificationSubmission = mPrequalificationEvalFile.getPrequalificationSubmission();
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);
        Long prequalificationSubmissionId = prequalificationSubmission.getId();

        // Get all the mPrequalificationEvalFileList where prequalificationSubmission equals to prequalificationSubmissionId
        defaultMPrequalificationEvalFileShouldBeFound("prequalificationSubmissionId.equals=" + prequalificationSubmissionId);

        // Get all the mPrequalificationEvalFileList where prequalificationSubmission equals to prequalificationSubmissionId + 1
        defaultMPrequalificationEvalFileShouldNotBeFound("prequalificationSubmissionId.equals=" + (prequalificationSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMPrequalificationEvalFilesByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = mPrequalificationEvalFile.getBiddingSubCriteria();
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the mPrequalificationEvalFileList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultMPrequalificationEvalFileShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the mPrequalificationEvalFileList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultMPrequalificationEvalFileShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMPrequalificationEvalFileShouldBeFound(String filter) throws Exception {
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPrequalificationEvalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMPrequalificationEvalFileShouldNotBeFound(String filter) throws Exception {
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMPrequalificationEvalFile() throws Exception {
        // Get the mPrequalificationEvalFile
        restMPrequalificationEvalFileMockMvc.perform(get("/api/m-prequalification-eval-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPrequalificationEvalFile() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        int databaseSizeBeforeUpdate = mPrequalificationEvalFileRepository.findAll().size();

        // Update the mPrequalificationEvalFile
        MPrequalificationEvalFile updatedMPrequalificationEvalFile = mPrequalificationEvalFileRepository.findById(mPrequalificationEvalFile.getId()).get();
        // Disconnect from session so that the updates on updatedMPrequalificationEvalFile are not directly saved in db
        em.detach(updatedMPrequalificationEvalFile);
        updatedMPrequalificationEvalFile
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO = mPrequalificationEvalFileMapper.toDto(updatedMPrequalificationEvalFile);

        restMPrequalificationEvalFileMockMvc.perform(put("/api/m-prequalification-eval-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalFileDTO)))
            .andExpect(status().isOk());

        // Validate the MPrequalificationEvalFile in the database
        List<MPrequalificationEvalFile> mPrequalificationEvalFileList = mPrequalificationEvalFileRepository.findAll();
        assertThat(mPrequalificationEvalFileList).hasSize(databaseSizeBeforeUpdate);
        MPrequalificationEvalFile testMPrequalificationEvalFile = mPrequalificationEvalFileList.get(mPrequalificationEvalFileList.size() - 1);
        assertThat(testMPrequalificationEvalFile.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMPrequalificationEvalFile.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMPrequalificationEvalFile() throws Exception {
        int databaseSizeBeforeUpdate = mPrequalificationEvalFileRepository.findAll().size();

        // Create the MPrequalificationEvalFile
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO = mPrequalificationEvalFileMapper.toDto(mPrequalificationEvalFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPrequalificationEvalFileMockMvc.perform(put("/api/m-prequalification-eval-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mPrequalificationEvalFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MPrequalificationEvalFile in the database
        List<MPrequalificationEvalFile> mPrequalificationEvalFileList = mPrequalificationEvalFileRepository.findAll();
        assertThat(mPrequalificationEvalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPrequalificationEvalFile() throws Exception {
        // Initialize the database
        mPrequalificationEvalFileRepository.saveAndFlush(mPrequalificationEvalFile);

        int databaseSizeBeforeDelete = mPrequalificationEvalFileRepository.findAll().size();

        // Delete the mPrequalificationEvalFile
        restMPrequalificationEvalFileMockMvc.perform(delete("/api/m-prequalification-eval-files/{id}", mPrequalificationEvalFile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MPrequalificationEvalFile> mPrequalificationEvalFileList = mPrequalificationEvalFileRepository.findAll();
        assertThat(mPrequalificationEvalFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
