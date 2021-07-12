package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBlacklistLine;
import com.bhp.opusb.domain.MBlacklist;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CFunctionary;
import com.bhp.opusb.repository.MBlacklistLineRepository;
import com.bhp.opusb.service.MBlacklistLineService;
import com.bhp.opusb.service.dto.MBlacklistLineDTO;
import com.bhp.opusb.service.mapper.MBlacklistLineMapper;
import com.bhp.opusb.service.dto.MBlacklistLineCriteria;
import com.bhp.opusb.service.MBlacklistLineQueryService;

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
 * Integration tests for the {@link MBlacklistLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBlacklistLineResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private MBlacklistLineRepository mBlacklistLineRepository;

    @Autowired
    private MBlacklistLineMapper mBlacklistLineMapper;

    @Autowired
    private MBlacklistLineService mBlacklistLineService;

    @Autowired
    private MBlacklistLineQueryService mBlacklistLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBlacklistLineMockMvc;

    private MBlacklistLine mBlacklistLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBlacklistLine createEntity(EntityManager em) {
        MBlacklistLine mBlacklistLine = new MBlacklistLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .notes(DEFAULT_NOTES);
        // Add required entity
        MBlacklist mBlacklist;
        if (TestUtil.findAll(em, MBlacklist.class).isEmpty()) {
            mBlacklist = MBlacklistResourceIT.createEntity(em);
            em.persist(mBlacklist);
            em.flush();
        } else {
            mBlacklist = TestUtil.findAll(em, MBlacklist.class).get(0);
        }
        mBlacklistLine.setBlacklist(mBlacklist);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBlacklistLine.setAdOrganization(aDOrganization);
        return mBlacklistLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBlacklistLine createUpdatedEntity(EntityManager em) {
        MBlacklistLine mBlacklistLine = new MBlacklistLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .notes(UPDATED_NOTES);
        // Add required entity
        MBlacklist mBlacklist;
        if (TestUtil.findAll(em, MBlacklist.class).isEmpty()) {
            mBlacklist = MBlacklistResourceIT.createUpdatedEntity(em);
            em.persist(mBlacklist);
            em.flush();
        } else {
            mBlacklist = TestUtil.findAll(em, MBlacklist.class).get(0);
        }
        mBlacklistLine.setBlacklist(mBlacklist);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBlacklistLine.setAdOrganization(aDOrganization);
        return mBlacklistLine;
    }

    @BeforeEach
    public void initTest() {
        mBlacklistLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBlacklistLine() throws Exception {
        int databaseSizeBeforeCreate = mBlacklistLineRepository.findAll().size();

        // Create the MBlacklistLine
        MBlacklistLineDTO mBlacklistLineDTO = mBlacklistLineMapper.toDto(mBlacklistLine);
        restMBlacklistLineMockMvc.perform(post("/api/m-blacklist-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBlacklistLine in the database
        List<MBlacklistLine> mBlacklistLineList = mBlacklistLineRepository.findAll();
        assertThat(mBlacklistLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBlacklistLine testMBlacklistLine = mBlacklistLineList.get(mBlacklistLineList.size() - 1);
        assertThat(testMBlacklistLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBlacklistLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBlacklistLine.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createMBlacklistLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBlacklistLineRepository.findAll().size();

        // Create the MBlacklistLine with an existing ID
        mBlacklistLine.setId(1L);
        MBlacklistLineDTO mBlacklistLineDTO = mBlacklistLineMapper.toDto(mBlacklistLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBlacklistLineMockMvc.perform(post("/api/m-blacklist-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBlacklistLine in the database
        List<MBlacklistLine> mBlacklistLineList = mBlacklistLineRepository.findAll();
        assertThat(mBlacklistLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBlacklistLines() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBlacklistLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getMBlacklistLine() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get the mBlacklistLine
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines/{id}", mBlacklistLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBlacklistLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }


    @Test
    @Transactional
    public void getMBlacklistLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        Long id = mBlacklistLine.getId();

        defaultMBlacklistLineShouldBeFound("id.equals=" + id);
        defaultMBlacklistLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBlacklistLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBlacklistLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBlacklistLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBlacklistLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBlacklistLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where uid equals to DEFAULT_UID
        defaultMBlacklistLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBlacklistLineList where uid equals to UPDATED_UID
        defaultMBlacklistLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where uid not equals to DEFAULT_UID
        defaultMBlacklistLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBlacklistLineList where uid not equals to UPDATED_UID
        defaultMBlacklistLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBlacklistLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBlacklistLineList where uid equals to UPDATED_UID
        defaultMBlacklistLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where uid is not null
        defaultMBlacklistLineShouldBeFound("uid.specified=true");

        // Get all the mBlacklistLineList where uid is null
        defaultMBlacklistLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where active equals to DEFAULT_ACTIVE
        defaultMBlacklistLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBlacklistLineList where active equals to UPDATED_ACTIVE
        defaultMBlacklistLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where active not equals to DEFAULT_ACTIVE
        defaultMBlacklistLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBlacklistLineList where active not equals to UPDATED_ACTIVE
        defaultMBlacklistLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBlacklistLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBlacklistLineList where active equals to UPDATED_ACTIVE
        defaultMBlacklistLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        // Get all the mBlacklistLineList where active is not null
        defaultMBlacklistLineShouldBeFound("active.specified=true");

        // Get all the mBlacklistLineList where active is null
        defaultMBlacklistLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBlacklistLinesByBlacklistIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBlacklist blacklist = mBlacklistLine.getBlacklist();
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        Long blacklistId = blacklist.getId();

        // Get all the mBlacklistLineList where blacklist equals to blacklistId
        defaultMBlacklistLineShouldBeFound("blacklistId.equals=" + blacklistId);

        // Get all the mBlacklistLineList where blacklist equals to blacklistId + 1
        defaultMBlacklistLineShouldNotBeFound("blacklistId.equals=" + (blacklistId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBlacklistLine.getAdOrganization();
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBlacklistLineList where adOrganization equals to adOrganizationId
        defaultMBlacklistLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBlacklistLineList where adOrganization equals to adOrganizationId + 1
        defaultMBlacklistLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistLinesByPicIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        AdUser pic = AdUserResourceIT.createEntity(em);
        em.persist(pic);
        em.flush();
        mBlacklistLine.setPic(pic);
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        Long picId = pic.getId();

        // Get all the mBlacklistLineList where pic equals to picId
        defaultMBlacklistLineShouldBeFound("picId.equals=" + picId);

        // Get all the mBlacklistLineList where pic equals to picId + 1
        defaultMBlacklistLineShouldNotBeFound("picId.equals=" + (picId + 1));
    }


    @Test
    @Transactional
    public void getAllMBlacklistLinesByFunctionaryIsEqualToSomething() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        CFunctionary functionary = CFunctionaryResourceIT.createEntity(em);
        em.persist(functionary);
        em.flush();
        mBlacklistLine.setFunctionary(functionary);
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);
        Long functionaryId = functionary.getId();

        // Get all the mBlacklistLineList where functionary equals to functionaryId
        defaultMBlacklistLineShouldBeFound("functionaryId.equals=" + functionaryId);

        // Get all the mBlacklistLineList where functionary equals to functionaryId + 1
        defaultMBlacklistLineShouldNotBeFound("functionaryId.equals=" + (functionaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBlacklistLineShouldBeFound(String filter) throws Exception {
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBlacklistLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));

        // Check, that the count call also returns 1
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBlacklistLineShouldNotBeFound(String filter) throws Exception {
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBlacklistLine() throws Exception {
        // Get the mBlacklistLine
        restMBlacklistLineMockMvc.perform(get("/api/m-blacklist-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBlacklistLine() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        int databaseSizeBeforeUpdate = mBlacklistLineRepository.findAll().size();

        // Update the mBlacklistLine
        MBlacklistLine updatedMBlacklistLine = mBlacklistLineRepository.findById(mBlacklistLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBlacklistLine are not directly saved in db
        em.detach(updatedMBlacklistLine);
        updatedMBlacklistLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .notes(UPDATED_NOTES);
        MBlacklistLineDTO mBlacklistLineDTO = mBlacklistLineMapper.toDto(updatedMBlacklistLine);

        restMBlacklistLineMockMvc.perform(put("/api/m-blacklist-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBlacklistLine in the database
        List<MBlacklistLine> mBlacklistLineList = mBlacklistLineRepository.findAll();
        assertThat(mBlacklistLineList).hasSize(databaseSizeBeforeUpdate);
        MBlacklistLine testMBlacklistLine = mBlacklistLineList.get(mBlacklistLineList.size() - 1);
        assertThat(testMBlacklistLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBlacklistLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBlacklistLine.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingMBlacklistLine() throws Exception {
        int databaseSizeBeforeUpdate = mBlacklistLineRepository.findAll().size();

        // Create the MBlacklistLine
        MBlacklistLineDTO mBlacklistLineDTO = mBlacklistLineMapper.toDto(mBlacklistLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBlacklistLineMockMvc.perform(put("/api/m-blacklist-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBlacklistLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBlacklistLine in the database
        List<MBlacklistLine> mBlacklistLineList = mBlacklistLineRepository.findAll();
        assertThat(mBlacklistLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBlacklistLine() throws Exception {
        // Initialize the database
        mBlacklistLineRepository.saveAndFlush(mBlacklistLine);

        int databaseSizeBeforeDelete = mBlacklistLineRepository.findAll().size();

        // Delete the mBlacklistLine
        restMBlacklistLineMockMvc.perform(delete("/api/m-blacklist-lines/{id}", mBlacklistLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBlacklistLine> mBlacklistLineList = mBlacklistLineRepository.findAll();
        assertThat(mBlacklistLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
