package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingNegotiationChat;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MBiddingNegotiationChatRepository;
import com.bhp.opusb.service.MBiddingNegotiationChatService;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationChatMapper;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatCriteria;
import com.bhp.opusb.service.MBiddingNegotiationChatQueryService;

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
 * Integration tests for the {@link MBiddingNegotiationChatResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingNegotiationChatResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_VENDOR_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_TEXT = "BBBBBBBBBB";

    @Autowired
    private MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository;

    @Autowired
    private MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper;

    @Autowired
    private MBiddingNegotiationChatService mBiddingNegotiationChatService;

    @Autowired
    private MBiddingNegotiationChatQueryService mBiddingNegotiationChatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingNegotiationChatMockMvc;

    private MBiddingNegotiationChat mBiddingNegotiationChat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiationChat createEntity(EntityManager em) {
        MBiddingNegotiationChat mBiddingNegotiationChat = new MBiddingNegotiationChat()
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
        mBiddingNegotiationChat.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingNegotiationLine mBiddingNegotiationLine;
        if (TestUtil.findAll(em, MBiddingNegotiationLine.class).isEmpty()) {
            mBiddingNegotiationLine = MBiddingNegotiationLineResourceIT.createEntity(em);
            em.persist(mBiddingNegotiationLine);
            em.flush();
        } else {
            mBiddingNegotiationLine = TestUtil.findAll(em, MBiddingNegotiationLine.class).get(0);
        }
        mBiddingNegotiationChat.setNegotiationLine(mBiddingNegotiationLine);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingNegotiationChat.setBidding(mBidding);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingNegotiationChat.setVendor(cVendor);
        return mBiddingNegotiationChat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingNegotiationChat createUpdatedEntity(EntityManager em) {
        MBiddingNegotiationChat mBiddingNegotiationChat = new MBiddingNegotiationChat()
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
        mBiddingNegotiationChat.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingNegotiationLine mBiddingNegotiationLine;
        if (TestUtil.findAll(em, MBiddingNegotiationLine.class).isEmpty()) {
            mBiddingNegotiationLine = MBiddingNegotiationLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingNegotiationLine);
            em.flush();
        } else {
            mBiddingNegotiationLine = TestUtil.findAll(em, MBiddingNegotiationLine.class).get(0);
        }
        mBiddingNegotiationChat.setNegotiationLine(mBiddingNegotiationLine);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingNegotiationChat.setBidding(mBidding);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingNegotiationChat.setVendor(cVendor);
        return mBiddingNegotiationChat;
    }

    @BeforeEach
    public void initTest() {
        mBiddingNegotiationChat = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiationChat() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationChatRepository.findAll().size();

        // Create the MBiddingNegotiationChat
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO = mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChat);
        restMBiddingNegotiationChatMockMvc.perform(post("/api/m-bidding-negotiation-chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationChatDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingNegotiationChat in the database
        List<MBiddingNegotiationChat> mBiddingNegotiationChatList = mBiddingNegotiationChatRepository.findAll();
        assertThat(mBiddingNegotiationChatList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingNegotiationChat testMBiddingNegotiationChat = mBiddingNegotiationChatList.get(mBiddingNegotiationChatList.size() - 1);
        assertThat(testMBiddingNegotiationChat.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingNegotiationChat.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBiddingNegotiationChat.getVendorText()).isEqualTo(DEFAULT_VENDOR_TEXT);
        assertThat(testMBiddingNegotiationChat.getBuyerText()).isEqualTo(DEFAULT_BUYER_TEXT);
    }

    @Test
    @Transactional
    public void createMBiddingNegotiationChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingNegotiationChatRepository.findAll().size();

        // Create the MBiddingNegotiationChat with an existing ID
        mBiddingNegotiationChat.setId(1L);
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO = mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingNegotiationChatMockMvc.perform(post("/api/m-bidding-negotiation-chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationChatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiationChat in the database
        List<MBiddingNegotiationChat> mBiddingNegotiationChatList = mBiddingNegotiationChatRepository.findAll();
        assertThat(mBiddingNegotiationChatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChats() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiationChat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorText").value(hasItem(DEFAULT_VENDOR_TEXT.toString())))
            .andExpect(jsonPath("$.[*].buyerText").value(hasItem(DEFAULT_BUYER_TEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getMBiddingNegotiationChat() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get the mBiddingNegotiationChat
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats/{id}", mBiddingNegotiationChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingNegotiationChat.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.vendorText").value(DEFAULT_VENDOR_TEXT.toString()))
            .andExpect(jsonPath("$.buyerText").value(DEFAULT_BUYER_TEXT.toString()));
    }


    @Test
    @Transactional
    public void getMBiddingNegotiationChatsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        Long id = mBiddingNegotiationChat.getId();

        defaultMBiddingNegotiationChatShouldBeFound("id.equals=" + id);
        defaultMBiddingNegotiationChatShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingNegotiationChatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingNegotiationChatShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingNegotiationChatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingNegotiationChatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where uid equals to DEFAULT_UID
        defaultMBiddingNegotiationChatShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationChatList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationChatShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where uid not equals to DEFAULT_UID
        defaultMBiddingNegotiationChatShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingNegotiationChatList where uid not equals to UPDATED_UID
        defaultMBiddingNegotiationChatShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingNegotiationChatShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingNegotiationChatList where uid equals to UPDATED_UID
        defaultMBiddingNegotiationChatShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where uid is not null
        defaultMBiddingNegotiationChatShouldBeFound("uid.specified=true");

        // Get all the mBiddingNegotiationChatList where uid is null
        defaultMBiddingNegotiationChatShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where active equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationChatShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationChatList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationChatShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingNegotiationChatShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingNegotiationChatList where active not equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationChatShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingNegotiationChatShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingNegotiationChatList where active equals to UPDATED_ACTIVE
        defaultMBiddingNegotiationChatShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        // Get all the mBiddingNegotiationChatList where active is not null
        defaultMBiddingNegotiationChatShouldBeFound("active.specified=true");

        // Get all the mBiddingNegotiationChatList where active is null
        defaultMBiddingNegotiationChatShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingNegotiationChat.getAdOrganization();
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingNegotiationChatList where adOrganization equals to adOrganizationId
        defaultMBiddingNegotiationChatShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingNegotiationChatList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingNegotiationChatShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByNegotiationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingNegotiationLine negotiationLine = mBiddingNegotiationChat.getNegotiationLine();
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        Long negotiationLineId = negotiationLine.getId();

        // Get all the mBiddingNegotiationChatList where negotiationLine equals to negotiationLineId
        defaultMBiddingNegotiationChatShouldBeFound("negotiationLineId.equals=" + negotiationLineId);

        // Get all the mBiddingNegotiationChatList where negotiationLine equals to negotiationLineId + 1
        defaultMBiddingNegotiationChatShouldNotBeFound("negotiationLineId.equals=" + (negotiationLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingNegotiationChat.getBidding();
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        Long biddingId = bidding.getId();

        // Get all the mBiddingNegotiationChatList where bidding equals to biddingId
        defaultMBiddingNegotiationChatShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingNegotiationChatList where bidding equals to biddingId + 1
        defaultMBiddingNegotiationChatShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mBiddingNegotiationChat.getVendor();
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        Long vendorId = vendor.getId();

        // Get all the mBiddingNegotiationChatList where vendor equals to vendorId
        defaultMBiddingNegotiationChatShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBiddingNegotiationChatList where vendor equals to vendorId + 1
        defaultMBiddingNegotiationChatShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingNegotiationChatsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        CAttachment attachment = CAttachmentResourceIT.createEntity(em);
        em.persist(attachment);
        em.flush();
        mBiddingNegotiationChat.setAttachment(attachment);
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);
        Long attachmentId = attachment.getId();

        // Get all the mBiddingNegotiationChatList where attachment equals to attachmentId
        defaultMBiddingNegotiationChatShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mBiddingNegotiationChatList where attachment equals to attachmentId + 1
        defaultMBiddingNegotiationChatShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingNegotiationChatShouldBeFound(String filter) throws Exception {
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingNegotiationChat.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorText").value(hasItem(DEFAULT_VENDOR_TEXT.toString())))
            .andExpect(jsonPath("$.[*].buyerText").value(hasItem(DEFAULT_BUYER_TEXT.toString())));

        // Check, that the count call also returns 1
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingNegotiationChatShouldNotBeFound(String filter) throws Exception {
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingNegotiationChat() throws Exception {
        // Get the mBiddingNegotiationChat
        restMBiddingNegotiationChatMockMvc.perform(get("/api/m-bidding-negotiation-chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingNegotiationChat() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        int databaseSizeBeforeUpdate = mBiddingNegotiationChatRepository.findAll().size();

        // Update the mBiddingNegotiationChat
        MBiddingNegotiationChat updatedMBiddingNegotiationChat = mBiddingNegotiationChatRepository.findById(mBiddingNegotiationChat.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingNegotiationChat are not directly saved in db
        em.detach(updatedMBiddingNegotiationChat);
        updatedMBiddingNegotiationChat
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .vendorText(UPDATED_VENDOR_TEXT)
            .buyerText(UPDATED_BUYER_TEXT);
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO = mBiddingNegotiationChatMapper.toDto(updatedMBiddingNegotiationChat);

        restMBiddingNegotiationChatMockMvc.perform(put("/api/m-bidding-negotiation-chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationChatDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingNegotiationChat in the database
        List<MBiddingNegotiationChat> mBiddingNegotiationChatList = mBiddingNegotiationChatRepository.findAll();
        assertThat(mBiddingNegotiationChatList).hasSize(databaseSizeBeforeUpdate);
        MBiddingNegotiationChat testMBiddingNegotiationChat = mBiddingNegotiationChatList.get(mBiddingNegotiationChatList.size() - 1);
        assertThat(testMBiddingNegotiationChat.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingNegotiationChat.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBiddingNegotiationChat.getVendorText()).isEqualTo(UPDATED_VENDOR_TEXT);
        assertThat(testMBiddingNegotiationChat.getBuyerText()).isEqualTo(UPDATED_BUYER_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingNegotiationChat() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingNegotiationChatRepository.findAll().size();

        // Create the MBiddingNegotiationChat
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO = mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingNegotiationChatMockMvc.perform(put("/api/m-bidding-negotiation-chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingNegotiationChatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingNegotiationChat in the database
        List<MBiddingNegotiationChat> mBiddingNegotiationChatList = mBiddingNegotiationChatRepository.findAll();
        assertThat(mBiddingNegotiationChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingNegotiationChat() throws Exception {
        // Initialize the database
        mBiddingNegotiationChatRepository.saveAndFlush(mBiddingNegotiationChat);

        int databaseSizeBeforeDelete = mBiddingNegotiationChatRepository.findAll().size();

        // Delete the mBiddingNegotiationChat
        restMBiddingNegotiationChatMockMvc.perform(delete("/api/m-bidding-negotiation-chats/{id}", mBiddingNegotiationChat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingNegotiationChat> mBiddingNegotiationChatList = mBiddingNegotiationChatRepository.findAll();
        assertThat(mBiddingNegotiationChatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
