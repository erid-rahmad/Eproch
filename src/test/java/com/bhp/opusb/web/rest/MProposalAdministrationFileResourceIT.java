package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalAdministrationFile;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.repository.MProposalAdministrationFileRepository;
import com.bhp.opusb.service.MProposalAdministrationFileService;
import com.bhp.opusb.service.dto.MProposalAdministrationFileDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationFileMapper;
import com.bhp.opusb.service.dto.MProposalAdministrationFileCriteria;
import com.bhp.opusb.service.MProposalAdministrationFileQueryService;

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
 * Integration tests for the {@link MProposalAdministrationFileResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalAdministrationFileResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProposalAdministrationFileRepository mProposalAdministrationFileRepository;

    @Autowired
    private MProposalAdministrationFileMapper mProposalAdministrationFileMapper;

    @Autowired
    private MProposalAdministrationFileService mProposalAdministrationFileService;

    @Autowired
    private MProposalAdministrationFileQueryService mProposalAdministrationFileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalAdministrationFileMockMvc;

    private MProposalAdministrationFile mProposalAdministrationFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalAdministrationFile createEntity(EntityManager em) {
        MProposalAdministrationFile mProposalAdministrationFile = new MProposalAdministrationFile()
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
        mProposalAdministrationFile.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalAdministrationFile.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalAdministrationFile.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mProposalAdministrationFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mProposalAdministrationFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalAdministrationFile createUpdatedEntity(EntityManager em) {
        MProposalAdministrationFile mProposalAdministrationFile = new MProposalAdministrationFile()
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
        mProposalAdministrationFile.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalAdministrationFile.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalAdministrationFile.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mProposalAdministrationFile.setBiddingSubCriteria(cBiddingSubCriteria);
        return mProposalAdministrationFile;
    }

    @BeforeEach
    public void initTest() {
        mProposalAdministrationFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalAdministrationFile() throws Exception {
        int databaseSizeBeforeCreate = mProposalAdministrationFileRepository.findAll().size();

        // Create the MProposalAdministrationFile
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO = mProposalAdministrationFileMapper.toDto(mProposalAdministrationFile);
        restMProposalAdministrationFileMockMvc.perform(post("/api/m-proposal-administration-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationFileDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalAdministrationFile in the database
        List<MProposalAdministrationFile> mProposalAdministrationFileList = mProposalAdministrationFileRepository.findAll();
        assertThat(mProposalAdministrationFileList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalAdministrationFile testMProposalAdministrationFile = mProposalAdministrationFileList.get(mProposalAdministrationFileList.size() - 1);
        assertThat(testMProposalAdministrationFile.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalAdministrationFile.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalAdministrationFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalAdministrationFileRepository.findAll().size();

        // Create the MProposalAdministrationFile with an existing ID
        mProposalAdministrationFile.setId(1L);
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO = mProposalAdministrationFileMapper.toDto(mProposalAdministrationFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalAdministrationFileMockMvc.perform(post("/api/m-proposal-administration-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalAdministrationFile in the database
        List<MProposalAdministrationFile> mProposalAdministrationFileList = mProposalAdministrationFileRepository.findAll();
        assertThat(mProposalAdministrationFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationFiles() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalAdministrationFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalAdministrationFile() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get the mProposalAdministrationFile
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files/{id}", mProposalAdministrationFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalAdministrationFile.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalAdministrationFilesByIdFiltering() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        Long id = mProposalAdministrationFile.getId();

        defaultMProposalAdministrationFileShouldBeFound("id.equals=" + id);
        defaultMProposalAdministrationFileShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalAdministrationFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalAdministrationFileShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalAdministrationFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalAdministrationFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where uid equals to DEFAULT_UID
        defaultMProposalAdministrationFileShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalAdministrationFileList where uid equals to UPDATED_UID
        defaultMProposalAdministrationFileShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where uid not equals to DEFAULT_UID
        defaultMProposalAdministrationFileShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalAdministrationFileList where uid not equals to UPDATED_UID
        defaultMProposalAdministrationFileShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalAdministrationFileShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalAdministrationFileList where uid equals to UPDATED_UID
        defaultMProposalAdministrationFileShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where uid is not null
        defaultMProposalAdministrationFileShouldBeFound("uid.specified=true");

        // Get all the mProposalAdministrationFileList where uid is null
        defaultMProposalAdministrationFileShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where active equals to DEFAULT_ACTIVE
        defaultMProposalAdministrationFileShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalAdministrationFileList where active equals to UPDATED_ACTIVE
        defaultMProposalAdministrationFileShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where active not equals to DEFAULT_ACTIVE
        defaultMProposalAdministrationFileShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalAdministrationFileList where active not equals to UPDATED_ACTIVE
        defaultMProposalAdministrationFileShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalAdministrationFileShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalAdministrationFileList where active equals to UPDATED_ACTIVE
        defaultMProposalAdministrationFileShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        // Get all the mProposalAdministrationFileList where active is not null
        defaultMProposalAdministrationFileShouldBeFound("active.specified=true");

        // Get all the mProposalAdministrationFileList where active is null
        defaultMProposalAdministrationFileShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByCAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment cAttachment = mProposalAdministrationFile.getCAttachment();
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);
        Long cAttachmentId = cAttachment.getId();

        // Get all the mProposalAdministrationFileList where cAttachment equals to cAttachmentId
        defaultMProposalAdministrationFileShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the mProposalAdministrationFileList where cAttachment equals to cAttachmentId + 1
        defaultMProposalAdministrationFileShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalAdministrationFile.getAdOrganization();
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalAdministrationFileList where adOrganization equals to adOrganizationId
        defaultMProposalAdministrationFileShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalAdministrationFileList where adOrganization equals to adOrganizationId + 1
        defaultMProposalAdministrationFileShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mProposalAdministrationFile.getBiddingSubmission();
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mProposalAdministrationFileList where biddingSubmission equals to biddingSubmissionId
        defaultMProposalAdministrationFileShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mProposalAdministrationFileList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMProposalAdministrationFileShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalAdministrationFilesByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = mProposalAdministrationFile.getBiddingSubCriteria();
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the mProposalAdministrationFileList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultMProposalAdministrationFileShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the mProposalAdministrationFileList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultMProposalAdministrationFileShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalAdministrationFileShouldBeFound(String filter) throws Exception {
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalAdministrationFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalAdministrationFileShouldNotBeFound(String filter) throws Exception {
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalAdministrationFile() throws Exception {
        // Get the mProposalAdministrationFile
        restMProposalAdministrationFileMockMvc.perform(get("/api/m-proposal-administration-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalAdministrationFile() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        int databaseSizeBeforeUpdate = mProposalAdministrationFileRepository.findAll().size();

        // Update the mProposalAdministrationFile
        MProposalAdministrationFile updatedMProposalAdministrationFile = mProposalAdministrationFileRepository.findById(mProposalAdministrationFile.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalAdministrationFile are not directly saved in db
        em.detach(updatedMProposalAdministrationFile);
        updatedMProposalAdministrationFile
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO = mProposalAdministrationFileMapper.toDto(updatedMProposalAdministrationFile);

        restMProposalAdministrationFileMockMvc.perform(put("/api/m-proposal-administration-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationFileDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalAdministrationFile in the database
        List<MProposalAdministrationFile> mProposalAdministrationFileList = mProposalAdministrationFileRepository.findAll();
        assertThat(mProposalAdministrationFileList).hasSize(databaseSizeBeforeUpdate);
        MProposalAdministrationFile testMProposalAdministrationFile = mProposalAdministrationFileList.get(mProposalAdministrationFileList.size() - 1);
        assertThat(testMProposalAdministrationFile.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalAdministrationFile.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalAdministrationFile() throws Exception {
        int databaseSizeBeforeUpdate = mProposalAdministrationFileRepository.findAll().size();

        // Create the MProposalAdministrationFile
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO = mProposalAdministrationFileMapper.toDto(mProposalAdministrationFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalAdministrationFileMockMvc.perform(put("/api/m-proposal-administration-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalAdministrationFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalAdministrationFile in the database
        List<MProposalAdministrationFile> mProposalAdministrationFileList = mProposalAdministrationFileRepository.findAll();
        assertThat(mProposalAdministrationFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalAdministrationFile() throws Exception {
        // Initialize the database
        mProposalAdministrationFileRepository.saveAndFlush(mProposalAdministrationFile);

        int databaseSizeBeforeDelete = mProposalAdministrationFileRepository.findAll().size();

        // Delete the mProposalAdministrationFile
        restMProposalAdministrationFileMockMvc.perform(delete("/api/m-proposal-administration-files/{id}", mProposalAdministrationFile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalAdministrationFile> mProposalAdministrationFileList = mProposalAdministrationFileRepository.findAll();
        assertThat(mProposalAdministrationFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
