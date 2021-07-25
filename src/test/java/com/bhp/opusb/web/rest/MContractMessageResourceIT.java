package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractMessage;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MContractMessageRepository;
import com.bhp.opusb.service.MContractMessageService;
import com.bhp.opusb.service.dto.MContractMessageDTO;
import com.bhp.opusb.service.mapper.MContractMessageMapper;
import com.bhp.opusb.service.dto.MContractMessageCriteria;
import com.bhp.opusb.service.MContractMessageQueryService;

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
 * Integration tests for the {@link MContractMessageResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractMessageResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_VENDOR_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_TEXT = "BBBBBBBBBB";

    @Autowired
    private MContractMessageRepository mContractMessageRepository;

    @Autowired
    private MContractMessageMapper mContractMessageMapper;

    @Autowired
    private MContractMessageService mContractMessageService;

    @Autowired
    private MContractMessageQueryService mContractMessageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractMessageMockMvc;

    private MContractMessage mContractMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractMessage createEntity(EntityManager em) {
        MContractMessage mContractMessage = new MContractMessage()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .vendorText(DEFAULT_VENDOR_TEXT)
            .buyerText(DEFAULT_BUYER_TEXT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractMessage.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractMessage.setContract(mContract);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mContractMessage.setVendor(cVendor);
        return mContractMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractMessage createUpdatedEntity(EntityManager em) {
        MContractMessage mContractMessage = new MContractMessage()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .vendorText(UPDATED_VENDOR_TEXT)
            .buyerText(UPDATED_BUYER_TEXT);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractMessage.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractMessage.setContract(mContract);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mContractMessage.setVendor(cVendor);
        return mContractMessage;
    }

    @BeforeEach
    public void initTest() {
        mContractMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractMessage() throws Exception {
        int databaseSizeBeforeCreate = mContractMessageRepository.findAll().size();

        // Create the MContractMessage
        MContractMessageDTO mContractMessageDTO = mContractMessageMapper.toDto(mContractMessage);
        restMContractMessageMockMvc.perform(post("/api/m-contract-messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractMessageDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractMessage in the database
        List<MContractMessage> mContractMessageList = mContractMessageRepository.findAll();
        assertThat(mContractMessageList).hasSize(databaseSizeBeforeCreate + 1);
        MContractMessage testMContractMessage = mContractMessageList.get(mContractMessageList.size() - 1);
        assertThat(testMContractMessage.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractMessage.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMContractMessage.getVendorText()).isEqualTo(DEFAULT_VENDOR_TEXT);
        assertThat(testMContractMessage.getBuyerText()).isEqualTo(DEFAULT_BUYER_TEXT);
    }

    @Test
    @Transactional
    public void createMContractMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractMessageRepository.findAll().size();

        // Create the MContractMessage with an existing ID
        mContractMessage.setId(1L);
        MContractMessageDTO mContractMessageDTO = mContractMessageMapper.toDto(mContractMessage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractMessageMockMvc.perform(post("/api/m-contract-messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractMessage in the database
        List<MContractMessage> mContractMessageList = mContractMessageRepository.findAll();
        assertThat(mContractMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMContractMessages() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorText").value(hasItem(DEFAULT_VENDOR_TEXT.toString())))
            .andExpect(jsonPath("$.[*].buyerText").value(hasItem(DEFAULT_BUYER_TEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getMContractMessage() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get the mContractMessage
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages/{id}", mContractMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractMessage.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.vendorText").value(DEFAULT_VENDOR_TEXT.toString()))
            .andExpect(jsonPath("$.buyerText").value(DEFAULT_BUYER_TEXT.toString()));
    }


    @Test
    @Transactional
    public void getMContractMessagesByIdFiltering() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        Long id = mContractMessage.getId();

        defaultMContractMessageShouldBeFound("id.equals=" + id);
        defaultMContractMessageShouldNotBeFound("id.notEquals=" + id);

        defaultMContractMessageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractMessageShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractMessageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractMessageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractMessagesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where uid equals to DEFAULT_UID
        defaultMContractMessageShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractMessageList where uid equals to UPDATED_UID
        defaultMContractMessageShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where uid not equals to DEFAULT_UID
        defaultMContractMessageShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractMessageList where uid not equals to UPDATED_UID
        defaultMContractMessageShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractMessageShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractMessageList where uid equals to UPDATED_UID
        defaultMContractMessageShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where uid is not null
        defaultMContractMessageShouldBeFound("uid.specified=true");

        // Get all the mContractMessageList where uid is null
        defaultMContractMessageShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where active equals to DEFAULT_ACTIVE
        defaultMContractMessageShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractMessageList where active equals to UPDATED_ACTIVE
        defaultMContractMessageShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where active not equals to DEFAULT_ACTIVE
        defaultMContractMessageShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractMessageList where active not equals to UPDATED_ACTIVE
        defaultMContractMessageShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractMessageShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractMessageList where active equals to UPDATED_ACTIVE
        defaultMContractMessageShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        // Get all the mContractMessageList where active is not null
        defaultMContractMessageShouldBeFound("active.specified=true");

        // Get all the mContractMessageList where active is null
        defaultMContractMessageShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractMessagesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractMessage.getAdOrganization();
        mContractMessageRepository.saveAndFlush(mContractMessage);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractMessageList where adOrganization equals to adOrganizationId
        defaultMContractMessageShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractMessageList where adOrganization equals to adOrganizationId + 1
        defaultMContractMessageShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractMessagesByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractMessage.getContract();
        mContractMessageRepository.saveAndFlush(mContractMessage);
        Long contractId = contract.getId();

        // Get all the mContractMessageList where contract equals to contractId
        defaultMContractMessageShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractMessageList where contract equals to contractId + 1
        defaultMContractMessageShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractMessagesByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mContractMessage.getVendor();
        mContractMessageRepository.saveAndFlush(mContractMessage);
        Long vendorId = vendor.getId();

        // Get all the mContractMessageList where vendor equals to vendorId
        defaultMContractMessageShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mContractMessageList where vendor equals to vendorId + 1
        defaultMContractMessageShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractMessagesByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        mContractMessage.setAttachment(attachment);
        mContractMessageRepository.saveAndFlush(mContractMessage);
        Long attachmentId = attachment.getId();

        // Get all the mContractMessageList where attachment equals to attachmentId
        defaultMContractMessageShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mContractMessageList where attachment equals to attachmentId + 1
        defaultMContractMessageShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractMessageShouldBeFound(String filter) throws Exception {
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorText").value(hasItem(DEFAULT_VENDOR_TEXT.toString())))
            .andExpect(jsonPath("$.[*].buyerText").value(hasItem(DEFAULT_BUYER_TEXT.toString())));

        // Check, that the count call also returns 1
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractMessageShouldNotBeFound(String filter) throws Exception {
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractMessage() throws Exception {
        // Get the mContractMessage
        restMContractMessageMockMvc.perform(get("/api/m-contract-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractMessage() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        int databaseSizeBeforeUpdate = mContractMessageRepository.findAll().size();

        // Update the mContractMessage
        MContractMessage updatedMContractMessage = mContractMessageRepository.findById(mContractMessage.getId()).get();
        // Disconnect from session so that the updates on updatedMContractMessage are not directly saved in db
        em.detach(updatedMContractMessage);
        updatedMContractMessage
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .vendorText(UPDATED_VENDOR_TEXT)
            .buyerText(UPDATED_BUYER_TEXT);
        MContractMessageDTO mContractMessageDTO = mContractMessageMapper.toDto(updatedMContractMessage);

        restMContractMessageMockMvc.perform(put("/api/m-contract-messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractMessageDTO)))
            .andExpect(status().isOk());

        // Validate the MContractMessage in the database
        List<MContractMessage> mContractMessageList = mContractMessageRepository.findAll();
        assertThat(mContractMessageList).hasSize(databaseSizeBeforeUpdate);
        MContractMessage testMContractMessage = mContractMessageList.get(mContractMessageList.size() - 1);
        assertThat(testMContractMessage.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractMessage.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMContractMessage.getVendorText()).isEqualTo(UPDATED_VENDOR_TEXT);
        assertThat(testMContractMessage.getBuyerText()).isEqualTo(UPDATED_BUYER_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractMessage() throws Exception {
        int databaseSizeBeforeUpdate = mContractMessageRepository.findAll().size();

        // Create the MContractMessage
        MContractMessageDTO mContractMessageDTO = mContractMessageMapper.toDto(mContractMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractMessageMockMvc.perform(put("/api/m-contract-messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractMessage in the database
        List<MContractMessage> mContractMessageList = mContractMessageRepository.findAll();
        assertThat(mContractMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractMessage() throws Exception {
        // Initialize the database
        mContractMessageRepository.saveAndFlush(mContractMessage);

        int databaseSizeBeforeDelete = mContractMessageRepository.findAll().size();

        // Delete the mContractMessage
        restMContractMessageMockMvc.perform(delete("/api/m-contract-messages/{id}", mContractMessage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractMessage> mContractMessageList = mContractMessageRepository.findAll();
        assertThat(mContractMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
