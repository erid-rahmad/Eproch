package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.service.MVendorConfirmationLineService;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationLineMapper;
import com.bhp.opusb.service.dto.MVendorConfirmationLineCriteria;
import com.bhp.opusb.service.MVendorConfirmationLineQueryService;

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
 * Integration tests for the {@link MVendorConfirmationLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorConfirmationLineResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private MVendorConfirmationLineRepository mVendorConfirmationLineRepository;

    @Autowired
    private MVendorConfirmationLineMapper mVendorConfirmationLineMapper;

    @Autowired
    private MVendorConfirmationLineService mVendorConfirmationLineService;

    @Autowired
    private MVendorConfirmationLineQueryService mVendorConfirmationLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorConfirmationLineMockMvc;

    private MVendorConfirmationLine mVendorConfirmationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationLine createEntity(EntityManager em) {
        MVendorConfirmationLine mVendorConfirmationLine = new MVendorConfirmationLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .status(DEFAULT_STATUS);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorConfirmationLine.setVendor(cVendor);
        // Add required entity
        MVendorConfirmation mVendorConfirmation;
        if (TestUtil.findAll(em, MVendorConfirmation.class).isEmpty()) {
            mVendorConfirmation = MVendorConfirmationResourceIT.createEntity(em);
            em.persist(mVendorConfirmation);
            em.flush();
        } else {
            mVendorConfirmation = TestUtil.findAll(em, MVendorConfirmation.class).get(0);
        }
        mVendorConfirmationLine.setVendorConfirmation(mVendorConfirmation);
        return mVendorConfirmationLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationLine createUpdatedEntity(EntityManager em) {
        MVendorConfirmationLine mVendorConfirmationLine = new MVendorConfirmationLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .status(UPDATED_STATUS);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationLine.setAdOrganization(aDOrganization);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorConfirmationLine.setVendor(cVendor);
        // Add required entity
        MVendorConfirmation mVendorConfirmation;
        if (TestUtil.findAll(em, MVendorConfirmation.class).isEmpty()) {
            mVendorConfirmation = MVendorConfirmationResourceIT.createUpdatedEntity(em);
            em.persist(mVendorConfirmation);
            em.flush();
        } else {
            mVendorConfirmation = TestUtil.findAll(em, MVendorConfirmation.class).get(0);
        }
        mVendorConfirmationLine.setVendorConfirmation(mVendorConfirmation);
        return mVendorConfirmationLine;
    }

    @BeforeEach
    public void initTest() {
        mVendorConfirmationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationLine() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationLineRepository.findAll().size();

        // Create the MVendorConfirmationLine
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO = mVendorConfirmationLineMapper.toDto(mVendorConfirmationLine);
        restMVendorConfirmationLineMockMvc.perform(post("/api/m-vendor-confirmation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorConfirmationLine in the database
        List<MVendorConfirmationLine> mVendorConfirmationLineList = mVendorConfirmationLineRepository.findAll();
        assertThat(mVendorConfirmationLineList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorConfirmationLine testMVendorConfirmationLine = mVendorConfirmationLineList.get(mVendorConfirmationLineList.size() - 1);
        assertThat(testMVendorConfirmationLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorConfirmationLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMVendorConfirmationLine.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationLineRepository.findAll().size();

        // Create the MVendorConfirmationLine with an existing ID
        mVendorConfirmationLine.setId(1L);
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO = mVendorConfirmationLineMapper.toDto(mVendorConfirmationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorConfirmationLineMockMvc.perform(post("/api/m-vendor-confirmation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationLine in the database
        List<MVendorConfirmationLine> mVendorConfirmationLineList = mVendorConfirmationLineRepository.findAll();
        assertThat(mVendorConfirmationLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLines() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getMVendorConfirmationLine() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get the mVendorConfirmationLine
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines/{id}", mVendorConfirmationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorConfirmationLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getMVendorConfirmationLinesByIdFiltering() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        Long id = mVendorConfirmationLine.getId();

        defaultMVendorConfirmationLineShouldBeFound("id.equals=" + id);
        defaultMVendorConfirmationLineShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorConfirmationLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorConfirmationLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorConfirmationLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorConfirmationLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where uid equals to DEFAULT_UID
        defaultMVendorConfirmationLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationLineList where uid equals to UPDATED_UID
        defaultMVendorConfirmationLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where uid not equals to DEFAULT_UID
        defaultMVendorConfirmationLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationLineList where uid not equals to UPDATED_UID
        defaultMVendorConfirmationLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorConfirmationLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorConfirmationLineList where uid equals to UPDATED_UID
        defaultMVendorConfirmationLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where uid is not null
        defaultMVendorConfirmationLineShouldBeFound("uid.specified=true");

        // Get all the mVendorConfirmationLineList where uid is null
        defaultMVendorConfirmationLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where active equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationLineList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where active not equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationLineList where active not equals to UPDATED_ACTIVE
        defaultMVendorConfirmationLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorConfirmationLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorConfirmationLineList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where active is not null
        defaultMVendorConfirmationLineShouldBeFound("active.specified=true");

        // Get all the mVendorConfirmationLineList where active is null
        defaultMVendorConfirmationLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status equals to DEFAULT_STATUS
        defaultMVendorConfirmationLineShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mVendorConfirmationLineList where status equals to UPDATED_STATUS
        defaultMVendorConfirmationLineShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status not equals to DEFAULT_STATUS
        defaultMVendorConfirmationLineShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the mVendorConfirmationLineList where status not equals to UPDATED_STATUS
        defaultMVendorConfirmationLineShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMVendorConfirmationLineShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mVendorConfirmationLineList where status equals to UPDATED_STATUS
        defaultMVendorConfirmationLineShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status is not null
        defaultMVendorConfirmationLineShouldBeFound("status.specified=true");

        // Get all the mVendorConfirmationLineList where status is null
        defaultMVendorConfirmationLineShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusContainsSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status contains DEFAULT_STATUS
        defaultMVendorConfirmationLineShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the mVendorConfirmationLineList where status contains UPDATED_STATUS
        defaultMVendorConfirmationLineShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        // Get all the mVendorConfirmationLineList where status does not contain DEFAULT_STATUS
        defaultMVendorConfirmationLineShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the mVendorConfirmationLineList where status does not contain UPDATED_STATUS
        defaultMVendorConfirmationLineShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorConfirmationLine.getAdOrganization();
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorConfirmationLineList where adOrganization equals to adOrganizationId
        defaultMVendorConfirmationLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorConfirmationLineList where adOrganization equals to adOrganizationId + 1
        defaultMVendorConfirmationLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mVendorConfirmationLine.getVendor();
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);
        Long vendorId = vendor.getId();

        // Get all the mVendorConfirmationLineList where vendor equals to vendorId
        defaultMVendorConfirmationLineShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mVendorConfirmationLineList where vendor equals to vendorId + 1
        defaultMVendorConfirmationLineShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByBiddingEvalResultIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);
        MBiddingEvalResult biddingEvalResult = MBiddingEvalResultResourceIT.createEntity(em);
        em.persist(biddingEvalResult);
        em.flush();
        mVendorConfirmationLine.setBiddingEvalResult(biddingEvalResult);
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);
        Long biddingEvalResultId = biddingEvalResult.getId();

        // Get all the mVendorConfirmationLineList where biddingEvalResult equals to biddingEvalResultId
        defaultMVendorConfirmationLineShouldBeFound("biddingEvalResultId.equals=" + biddingEvalResultId);

        // Get all the mVendorConfirmationLineList where biddingEvalResult equals to biddingEvalResultId + 1
        defaultMVendorConfirmationLineShouldNotBeFound("biddingEvalResultId.equals=" + (biddingEvalResultId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationLinesByVendorConfirmationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorConfirmation vendorConfirmation = mVendorConfirmationLine.getVendorConfirmation();
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);
        Long vendorConfirmationId = vendorConfirmation.getId();

        // Get all the mVendorConfirmationLineList where vendorConfirmation equals to vendorConfirmationId
        defaultMVendorConfirmationLineShouldBeFound("vendorConfirmationId.equals=" + vendorConfirmationId);

        // Get all the mVendorConfirmationLineList where vendorConfirmation equals to vendorConfirmationId + 1
        defaultMVendorConfirmationLineShouldNotBeFound("vendorConfirmationId.equals=" + (vendorConfirmationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorConfirmationLineShouldBeFound(String filter) throws Exception {
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorConfirmationLineShouldNotBeFound(String filter) throws Exception {
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorConfirmationLine() throws Exception {
        // Get the mVendorConfirmationLine
        restMVendorConfirmationLineMockMvc.perform(get("/api/m-vendor-confirmation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorConfirmationLine() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        int databaseSizeBeforeUpdate = mVendorConfirmationLineRepository.findAll().size();

        // Update the mVendorConfirmationLine
        MVendorConfirmationLine updatedMVendorConfirmationLine = mVendorConfirmationLineRepository.findById(mVendorConfirmationLine.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorConfirmationLine are not directly saved in db
        em.detach(updatedMVendorConfirmationLine);
        updatedMVendorConfirmationLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .status(UPDATED_STATUS);
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO = mVendorConfirmationLineMapper.toDto(updatedMVendorConfirmationLine);

        restMVendorConfirmationLineMockMvc.perform(put("/api/m-vendor-confirmation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationLineDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorConfirmationLine in the database
        List<MVendorConfirmationLine> mVendorConfirmationLineList = mVendorConfirmationLineRepository.findAll();
        assertThat(mVendorConfirmationLineList).hasSize(databaseSizeBeforeUpdate);
        MVendorConfirmationLine testMVendorConfirmationLine = mVendorConfirmationLineList.get(mVendorConfirmationLineList.size() - 1);
        assertThat(testMVendorConfirmationLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorConfirmationLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMVendorConfirmationLine.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorConfirmationLine() throws Exception {
        int databaseSizeBeforeUpdate = mVendorConfirmationLineRepository.findAll().size();

        // Create the MVendorConfirmationLine
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO = mVendorConfirmationLineMapper.toDto(mVendorConfirmationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorConfirmationLineMockMvc.perform(put("/api/m-vendor-confirmation-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationLine in the database
        List<MVendorConfirmationLine> mVendorConfirmationLineList = mVendorConfirmationLineRepository.findAll();
        assertThat(mVendorConfirmationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorConfirmationLine() throws Exception {
        // Initialize the database
        mVendorConfirmationLineRepository.saveAndFlush(mVendorConfirmationLine);

        int databaseSizeBeforeDelete = mVendorConfirmationLineRepository.findAll().size();

        // Delete the mVendorConfirmationLine
        restMVendorConfirmationLineMockMvc.perform(delete("/api/m-vendor-confirmation-lines/{id}", mVendorConfirmationLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorConfirmationLine> mVendorConfirmationLineList = mVendorConfirmationLineRepository.findAll();
        assertThat(mVendorConfirmationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
