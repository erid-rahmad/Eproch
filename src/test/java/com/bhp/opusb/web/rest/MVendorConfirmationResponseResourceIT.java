package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorConfirmationResponse;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.domain.MVendorConfirmationContract;
import com.bhp.opusb.repository.MVendorConfirmationResponseRepository;
import com.bhp.opusb.service.MVendorConfirmationResponseService;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationResponseMapper;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseCriteria;
import com.bhp.opusb.service.MVendorConfirmationResponseQueryService;

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
 * Integration tests for the {@link MVendorConfirmationResponseResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorConfirmationResponseResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NEED_REVISION = "AAAAAAAAAA";
    private static final String UPDATED_NEED_REVISION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCEPT = "AAAAAAAAAA";
    private static final String UPDATED_ACCEPT = "BBBBBBBBBB";

    @Autowired
    private MVendorConfirmationResponseRepository mVendorConfirmationResponseRepository;

    @Autowired
    private MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper;

    @Autowired
    private MVendorConfirmationResponseService mVendorConfirmationResponseService;

    @Autowired
    private MVendorConfirmationResponseQueryService mVendorConfirmationResponseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorConfirmationResponseMockMvc;

    private MVendorConfirmationResponse mVendorConfirmationResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationResponse createEntity(EntityManager em) {
        MVendorConfirmationResponse mVendorConfirmationResponse = new MVendorConfirmationResponse()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .needRevision(DEFAULT_NEED_REVISION)
            .accept(DEFAULT_ACCEPT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationResponse.setAdOrganization(aDOrganization);
        // Add required entity
        MVendorConfirmationLine mVendorConfirmationLine;
        if (TestUtil.findAll(em, MVendorConfirmationLine.class).isEmpty()) {
            mVendorConfirmationLine = MVendorConfirmationLineResourceIT.createEntity(em);
            em.persist(mVendorConfirmationLine);
            em.flush();
        } else {
            mVendorConfirmationLine = TestUtil.findAll(em, MVendorConfirmationLine.class).get(0);
        }
        mVendorConfirmationResponse.setVendorConfirmationLine(mVendorConfirmationLine);
        // Add required entity
        MVendorConfirmationContract mVendorConfirmationContract;
        if (TestUtil.findAll(em, MVendorConfirmationContract.class).isEmpty()) {
            mVendorConfirmationContract = MVendorConfirmationContractResourceIT.createEntity(em);
            em.persist(mVendorConfirmationContract);
            em.flush();
        } else {
            mVendorConfirmationContract = TestUtil.findAll(em, MVendorConfirmationContract.class).get(0);
        }
        mVendorConfirmationResponse.setVendorConfirmationContract(mVendorConfirmationContract);
        return mVendorConfirmationResponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationResponse createUpdatedEntity(EntityManager em) {
        MVendorConfirmationResponse mVendorConfirmationResponse = new MVendorConfirmationResponse()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .needRevision(UPDATED_NEED_REVISION)
            .accept(UPDATED_ACCEPT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationResponse.setAdOrganization(aDOrganization);
        // Add required entity
        MVendorConfirmationLine mVendorConfirmationLine;
        if (TestUtil.findAll(em, MVendorConfirmationLine.class).isEmpty()) {
            mVendorConfirmationLine = MVendorConfirmationLineResourceIT.createUpdatedEntity(em);
            em.persist(mVendorConfirmationLine);
            em.flush();
        } else {
            mVendorConfirmationLine = TestUtil.findAll(em, MVendorConfirmationLine.class).get(0);
        }
        mVendorConfirmationResponse.setVendorConfirmationLine(mVendorConfirmationLine);
        // Add required entity
        MVendorConfirmationContract mVendorConfirmationContract;
        if (TestUtil.findAll(em, MVendorConfirmationContract.class).isEmpty()) {
            mVendorConfirmationContract = MVendorConfirmationContractResourceIT.createUpdatedEntity(em);
            em.persist(mVendorConfirmationContract);
            em.flush();
        } else {
            mVendorConfirmationContract = TestUtil.findAll(em, MVendorConfirmationContract.class).get(0);
        }
        mVendorConfirmationResponse.setVendorConfirmationContract(mVendorConfirmationContract);
        return mVendorConfirmationResponse;
    }

    @BeforeEach
    public void initTest() {
        mVendorConfirmationResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationResponse() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationResponseRepository.findAll().size();

        // Create the MVendorConfirmationResponse
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO = mVendorConfirmationResponseMapper.toDto(mVendorConfirmationResponse);
        restMVendorConfirmationResponseMockMvc.perform(post("/api/m-vendor-confirmation-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationResponseDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorConfirmationResponse in the database
        List<MVendorConfirmationResponse> mVendorConfirmationResponseList = mVendorConfirmationResponseRepository.findAll();
        assertThat(mVendorConfirmationResponseList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorConfirmationResponse testMVendorConfirmationResponse = mVendorConfirmationResponseList.get(mVendorConfirmationResponseList.size() - 1);
        assertThat(testMVendorConfirmationResponse.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorConfirmationResponse.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMVendorConfirmationResponse.getNeedRevision()).isEqualTo(DEFAULT_NEED_REVISION);
        assertThat(testMVendorConfirmationResponse.getAccept()).isEqualTo(DEFAULT_ACCEPT);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationResponseRepository.findAll().size();

        // Create the MVendorConfirmationResponse with an existing ID
        mVendorConfirmationResponse.setId(1L);
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO = mVendorConfirmationResponseMapper.toDto(mVendorConfirmationResponse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorConfirmationResponseMockMvc.perform(post("/api/m-vendor-confirmation-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationResponse in the database
        List<MVendorConfirmationResponse> mVendorConfirmationResponseList = mVendorConfirmationResponseRepository.findAll();
        assertThat(mVendorConfirmationResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationResponses() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].needRevision").value(hasItem(DEFAULT_NEED_REVISION.toString())))
            .andExpect(jsonPath("$.[*].accept").value(hasItem(DEFAULT_ACCEPT.toString())));
    }
    
    @Test
    @Transactional
    public void getMVendorConfirmationResponse() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get the mVendorConfirmationResponse
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses/{id}", mVendorConfirmationResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorConfirmationResponse.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.needRevision").value(DEFAULT_NEED_REVISION.toString()))
            .andExpect(jsonPath("$.accept").value(DEFAULT_ACCEPT.toString()));
    }


    @Test
    @Transactional
    public void getMVendorConfirmationResponsesByIdFiltering() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        Long id = mVendorConfirmationResponse.getId();

        defaultMVendorConfirmationResponseShouldBeFound("id.equals=" + id);
        defaultMVendorConfirmationResponseShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorConfirmationResponseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorConfirmationResponseShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorConfirmationResponseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorConfirmationResponseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where uid equals to DEFAULT_UID
        defaultMVendorConfirmationResponseShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationResponseList where uid equals to UPDATED_UID
        defaultMVendorConfirmationResponseShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where uid not equals to DEFAULT_UID
        defaultMVendorConfirmationResponseShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationResponseList where uid not equals to UPDATED_UID
        defaultMVendorConfirmationResponseShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorConfirmationResponseShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorConfirmationResponseList where uid equals to UPDATED_UID
        defaultMVendorConfirmationResponseShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where uid is not null
        defaultMVendorConfirmationResponseShouldBeFound("uid.specified=true");

        // Get all the mVendorConfirmationResponseList where uid is null
        defaultMVendorConfirmationResponseShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where active equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationResponseShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationResponseList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationResponseShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where active not equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationResponseShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationResponseList where active not equals to UPDATED_ACTIVE
        defaultMVendorConfirmationResponseShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorConfirmationResponseShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorConfirmationResponseList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationResponseShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        // Get all the mVendorConfirmationResponseList where active is not null
        defaultMVendorConfirmationResponseShouldBeFound("active.specified=true");

        // Get all the mVendorConfirmationResponseList where active is null
        defaultMVendorConfirmationResponseShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorConfirmationResponse.getAdOrganization();
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorConfirmationResponseList where adOrganization equals to adOrganizationId
        defaultMVendorConfirmationResponseShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorConfirmationResponseList where adOrganization equals to adOrganizationId + 1
        defaultMVendorConfirmationResponseShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByVendorConfirmationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorConfirmationLine vendorConfirmationLine = mVendorConfirmationResponse.getVendorConfirmationLine();
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);
        Long vendorConfirmationLineId = vendorConfirmationLine.getId();

        // Get all the mVendorConfirmationResponseList where vendorConfirmationLine equals to vendorConfirmationLineId
        defaultMVendorConfirmationResponseShouldBeFound("vendorConfirmationLineId.equals=" + vendorConfirmationLineId);

        // Get all the mVendorConfirmationResponseList where vendorConfirmationLine equals to vendorConfirmationLineId + 1
        defaultMVendorConfirmationResponseShouldNotBeFound("vendorConfirmationLineId.equals=" + (vendorConfirmationLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationResponsesByVendorConfirmationContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorConfirmationContract vendorConfirmationContract = mVendorConfirmationResponse.getVendorConfirmationContract();
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);
        Long vendorConfirmationContractId = vendorConfirmationContract.getId();

        // Get all the mVendorConfirmationResponseList where vendorConfirmationContract equals to vendorConfirmationContractId
        defaultMVendorConfirmationResponseShouldBeFound("vendorConfirmationContractId.equals=" + vendorConfirmationContractId);

        // Get all the mVendorConfirmationResponseList where vendorConfirmationContract equals to vendorConfirmationContractId + 1
        defaultMVendorConfirmationResponseShouldNotBeFound("vendorConfirmationContractId.equals=" + (vendorConfirmationContractId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorConfirmationResponseShouldBeFound(String filter) throws Exception {
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].needRevision").value(hasItem(DEFAULT_NEED_REVISION.toString())))
            .andExpect(jsonPath("$.[*].accept").value(hasItem(DEFAULT_ACCEPT.toString())));

        // Check, that the count call also returns 1
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorConfirmationResponseShouldNotBeFound(String filter) throws Exception {
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorConfirmationResponse() throws Exception {
        // Get the mVendorConfirmationResponse
        restMVendorConfirmationResponseMockMvc.perform(get("/api/m-vendor-confirmation-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorConfirmationResponse() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        int databaseSizeBeforeUpdate = mVendorConfirmationResponseRepository.findAll().size();

        // Update the mVendorConfirmationResponse
        MVendorConfirmationResponse updatedMVendorConfirmationResponse = mVendorConfirmationResponseRepository.findById(mVendorConfirmationResponse.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorConfirmationResponse are not directly saved in db
        em.detach(updatedMVendorConfirmationResponse);
        updatedMVendorConfirmationResponse
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .needRevision(UPDATED_NEED_REVISION)
            .accept(UPDATED_ACCEPT);
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO = mVendorConfirmationResponseMapper.toDto(updatedMVendorConfirmationResponse);

        restMVendorConfirmationResponseMockMvc.perform(put("/api/m-vendor-confirmation-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationResponseDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorConfirmationResponse in the database
        List<MVendorConfirmationResponse> mVendorConfirmationResponseList = mVendorConfirmationResponseRepository.findAll();
        assertThat(mVendorConfirmationResponseList).hasSize(databaseSizeBeforeUpdate);
        MVendorConfirmationResponse testMVendorConfirmationResponse = mVendorConfirmationResponseList.get(mVendorConfirmationResponseList.size() - 1);
        assertThat(testMVendorConfirmationResponse.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorConfirmationResponse.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMVendorConfirmationResponse.getNeedRevision()).isEqualTo(UPDATED_NEED_REVISION);
        assertThat(testMVendorConfirmationResponse.getAccept()).isEqualTo(UPDATED_ACCEPT);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorConfirmationResponse() throws Exception {
        int databaseSizeBeforeUpdate = mVendorConfirmationResponseRepository.findAll().size();

        // Create the MVendorConfirmationResponse
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO = mVendorConfirmationResponseMapper.toDto(mVendorConfirmationResponse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorConfirmationResponseMockMvc.perform(put("/api/m-vendor-confirmation-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationResponse in the database
        List<MVendorConfirmationResponse> mVendorConfirmationResponseList = mVendorConfirmationResponseRepository.findAll();
        assertThat(mVendorConfirmationResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorConfirmationResponse() throws Exception {
        // Initialize the database
        mVendorConfirmationResponseRepository.saveAndFlush(mVendorConfirmationResponse);

        int databaseSizeBeforeDelete = mVendorConfirmationResponseRepository.findAll().size();

        // Delete the mVendorConfirmationResponse
        restMVendorConfirmationResponseMockMvc.perform(delete("/api/m-vendor-confirmation-responses/{id}", mVendorConfirmationResponse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorConfirmationResponse> mVendorConfirmationResponseList = mVendorConfirmationResponseRepository.findAll();
        assertThat(mVendorConfirmationResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
