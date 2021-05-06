package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalTechnicalFile;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.repository.MProposalTechnicalFileRepository;
import com.bhp.opusb.service.MProposalTechnicalFileService;
import com.bhp.opusb.service.dto.MProposalTechnicalFileDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalFileMapper;
import com.bhp.opusb.service.dto.MProposalTechnicalFileCriteria;
import com.bhp.opusb.service.MProposalTechnicalFileQueryService;

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
 * Integration tests for the {@link MProposalTechnicalFileResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalTechnicalFileResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProposalTechnicalFileRepository mProposalTechnicalFileRepository;

    @Autowired
    private MProposalTechnicalFileMapper mProposalTechnicalFileMapper;

    @Autowired
    private MProposalTechnicalFileService mProposalTechnicalFileService;

    @Autowired
    private MProposalTechnicalFileQueryService mProposalTechnicalFileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalTechnicalFileMockMvc;

    private MProposalTechnicalFile mProposalTechnicalFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalTechnicalFile createEntity(EntityManager em) {
        MProposalTechnicalFile mProposalTechnicalFile = new MProposalTechnicalFile()
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
        mProposalTechnicalFile.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalTechnicalFile.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalTechnicalFile.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mProposalTechnicalFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mProposalTechnicalFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalTechnicalFile createUpdatedEntity(EntityManager em) {
        MProposalTechnicalFile mProposalTechnicalFile = new MProposalTechnicalFile()
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
        mProposalTechnicalFile.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalTechnicalFile.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalTechnicalFile.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mProposalTechnicalFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mProposalTechnicalFile;
    }

    @BeforeEach
    public void initTest() {
        mProposalTechnicalFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalTechnicalFile() throws Exception {
        int databaseSizeBeforeCreate = mProposalTechnicalFileRepository.findAll().size();

        // Create the MProposalTechnicalFile
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO = mProposalTechnicalFileMapper.toDto(mProposalTechnicalFile);
        restMProposalTechnicalFileMockMvc.perform(post("/api/m-proposal-technical-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalFileDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalTechnicalFile in the database
        List<MProposalTechnicalFile> mProposalTechnicalFileList = mProposalTechnicalFileRepository.findAll();
        assertThat(mProposalTechnicalFileList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalTechnicalFile testMProposalTechnicalFile = mProposalTechnicalFileList.get(mProposalTechnicalFileList.size() - 1);
        assertThat(testMProposalTechnicalFile.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalTechnicalFile.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalTechnicalFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalTechnicalFileRepository.findAll().size();

        // Create the MProposalTechnicalFile with an existing ID
        mProposalTechnicalFile.setId(1L);
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO = mProposalTechnicalFileMapper.toDto(mProposalTechnicalFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalTechnicalFileMockMvc.perform(post("/api/m-proposal-technical-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalTechnicalFile in the database
        List<MProposalTechnicalFile> mProposalTechnicalFileList = mProposalTechnicalFileRepository.findAll();
        assertThat(mProposalTechnicalFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalFiles() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalTechnicalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalTechnicalFile() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get the mProposalTechnicalFile
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files/{id}", mProposalTechnicalFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalTechnicalFile.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalTechnicalFilesByIdFiltering() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        Long id = mProposalTechnicalFile.getId();

        defaultMProposalTechnicalFileShouldBeFound("id.equals=" + id);
        defaultMProposalTechnicalFileShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalTechnicalFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalTechnicalFileShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalTechnicalFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalTechnicalFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where uid equals to DEFAULT_UID
        defaultMProposalTechnicalFileShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalTechnicalFileList where uid equals to UPDATED_UID
        defaultMProposalTechnicalFileShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where uid not equals to DEFAULT_UID
        defaultMProposalTechnicalFileShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalTechnicalFileList where uid not equals to UPDATED_UID
        defaultMProposalTechnicalFileShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalTechnicalFileShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalTechnicalFileList where uid equals to UPDATED_UID
        defaultMProposalTechnicalFileShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where uid is not null
        defaultMProposalTechnicalFileShouldBeFound("uid.specified=true");

        // Get all the mProposalTechnicalFileList where uid is null
        defaultMProposalTechnicalFileShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where active equals to DEFAULT_ACTIVE
        defaultMProposalTechnicalFileShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalTechnicalFileList where active equals to UPDATED_ACTIVE
        defaultMProposalTechnicalFileShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where active not equals to DEFAULT_ACTIVE
        defaultMProposalTechnicalFileShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalTechnicalFileList where active not equals to UPDATED_ACTIVE
        defaultMProposalTechnicalFileShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalTechnicalFileShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalTechnicalFileList where active equals to UPDATED_ACTIVE
        defaultMProposalTechnicalFileShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        // Get all the mProposalTechnicalFileList where active is not null
        defaultMProposalTechnicalFileShouldBeFound("active.specified=true");

        // Get all the mProposalTechnicalFileList where active is null
        defaultMProposalTechnicalFileShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByCAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment cAttachment = mProposalTechnicalFile.getCAttachment();
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);
        Long cAttachmentId = cAttachment.getId();

        // Get all the mProposalTechnicalFileList where cAttachment equals to cAttachmentId
        defaultMProposalTechnicalFileShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the mProposalTechnicalFileList where cAttachment equals to cAttachmentId + 1
        defaultMProposalTechnicalFileShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalTechnicalFile.getAdOrganization();
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalTechnicalFileList where adOrganization equals to adOrganizationId
        defaultMProposalTechnicalFileShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalTechnicalFileList where adOrganization equals to adOrganizationId + 1
        defaultMProposalTechnicalFileShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mProposalTechnicalFile.getBiddingSubmission();
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mProposalTechnicalFileList where biddingSubmission equals to biddingSubmissionId
        defaultMProposalTechnicalFileShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mProposalTechnicalFileList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMProposalTechnicalFileShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalTechnicalFilesByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = mProposalTechnicalFile.getBiddingSubCriteria();
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the mProposalTechnicalFileList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultMProposalTechnicalFileShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the mProposalTechnicalFileList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultMProposalTechnicalFileShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalTechnicalFileShouldBeFound(String filter) throws Exception {
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalTechnicalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalTechnicalFileShouldNotBeFound(String filter) throws Exception {
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalTechnicalFile() throws Exception {
        // Get the mProposalTechnicalFile
        restMProposalTechnicalFileMockMvc.perform(get("/api/m-proposal-technical-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalTechnicalFile() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        int databaseSizeBeforeUpdate = mProposalTechnicalFileRepository.findAll().size();

        // Update the mProposalTechnicalFile
        MProposalTechnicalFile updatedMProposalTechnicalFile = mProposalTechnicalFileRepository.findById(mProposalTechnicalFile.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalTechnicalFile are not directly saved in db
        em.detach(updatedMProposalTechnicalFile);
        updatedMProposalTechnicalFile
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO = mProposalTechnicalFileMapper.toDto(updatedMProposalTechnicalFile);

        restMProposalTechnicalFileMockMvc.perform(put("/api/m-proposal-technical-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalFileDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalTechnicalFile in the database
        List<MProposalTechnicalFile> mProposalTechnicalFileList = mProposalTechnicalFileRepository.findAll();
        assertThat(mProposalTechnicalFileList).hasSize(databaseSizeBeforeUpdate);
        MProposalTechnicalFile testMProposalTechnicalFile = mProposalTechnicalFileList.get(mProposalTechnicalFileList.size() - 1);
        assertThat(testMProposalTechnicalFile.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalTechnicalFile.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalTechnicalFile() throws Exception {
        int databaseSizeBeforeUpdate = mProposalTechnicalFileRepository.findAll().size();

        // Create the MProposalTechnicalFile
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO = mProposalTechnicalFileMapper.toDto(mProposalTechnicalFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalTechnicalFileMockMvc.perform(put("/api/m-proposal-technical-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalTechnicalFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalTechnicalFile in the database
        List<MProposalTechnicalFile> mProposalTechnicalFileList = mProposalTechnicalFileRepository.findAll();
        assertThat(mProposalTechnicalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalTechnicalFile() throws Exception {
        // Initialize the database
        mProposalTechnicalFileRepository.saveAndFlush(mProposalTechnicalFile);

        int databaseSizeBeforeDelete = mProposalTechnicalFileRepository.findAll().size();

        // Delete the mProposalTechnicalFile
        restMProposalTechnicalFileMockMvc.perform(delete("/api/m-proposal-technical-files/{id}", mProposalTechnicalFile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalTechnicalFile> mProposalTechnicalFileList = mProposalTechnicalFileRepository.findAll();
        assertThat(mProposalTechnicalFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
