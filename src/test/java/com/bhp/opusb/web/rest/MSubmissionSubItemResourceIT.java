package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MSubmissionSubItem;
import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.repository.MSubmissionSubItemRepository;
import com.bhp.opusb.service.MSubmissionSubItemService;
import com.bhp.opusb.service.dto.MSubmissionSubItemDTO;
import com.bhp.opusb.service.mapper.MSubmissionSubItemMapper;
import com.bhp.opusb.service.dto.MSubmissionSubItemCriteria;
import com.bhp.opusb.service.MSubmissionSubItemQueryService;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MSubmissionSubItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MSubmissionSubItemResourceIT {

    private static final BigDecimal DEFAULT_PROPOSED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROPOSED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROPOSED_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MSubmissionSubItemRepository mSubmissionSubItemRepository;

    @Autowired
    private MSubmissionSubItemMapper mSubmissionSubItemMapper;

    @Autowired
    private MSubmissionSubItemService mSubmissionSubItemService;

    @Autowired
    private MSubmissionSubItemQueryService mSubmissionSubItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMSubmissionSubItemMockMvc;

    private MSubmissionSubItem mSubmissionSubItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MSubmissionSubItem createEntity(EntityManager em) {
        MSubmissionSubItem mSubmissionSubItem = new MSubmissionSubItem()
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingSubItem mBiddingSubItem;
        if (TestUtil.findAll(em, MBiddingSubItem.class).isEmpty()) {
            mBiddingSubItem = MBiddingSubItemResourceIT.createEntity(em);
            em.persist(mBiddingSubItem);
            em.flush();
        } else {
            mBiddingSubItem = TestUtil.findAll(em, MBiddingSubItem.class).get(0);
        }
        mSubmissionSubItem.setBiddingSubItem(mBiddingSubItem);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mSubmissionSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmissionLine mBiddingSubmissionLine;
        if (TestUtil.findAll(em, MBiddingSubmissionLine.class).isEmpty()) {
            mBiddingSubmissionLine = MBiddingSubmissionLineResourceIT.createEntity(em);
            em.persist(mBiddingSubmissionLine);
            em.flush();
        } else {
            mBiddingSubmissionLine = TestUtil.findAll(em, MBiddingSubmissionLine.class).get(0);
        }
        mSubmissionSubItem.setMBiddingSubmissionLine(mBiddingSubmissionLine);
        return mSubmissionSubItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MSubmissionSubItem createUpdatedEntity(EntityManager em) {
        MSubmissionSubItem mSubmissionSubItem = new MSubmissionSubItem()
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingSubItem mBiddingSubItem;
        if (TestUtil.findAll(em, MBiddingSubItem.class).isEmpty()) {
            mBiddingSubItem = MBiddingSubItemResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubItem);
            em.flush();
        } else {
            mBiddingSubItem = TestUtil.findAll(em, MBiddingSubItem.class).get(0);
        }
        mSubmissionSubItem.setBiddingSubItem(mBiddingSubItem);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mSubmissionSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmissionLine mBiddingSubmissionLine;
        if (TestUtil.findAll(em, MBiddingSubmissionLine.class).isEmpty()) {
            mBiddingSubmissionLine = MBiddingSubmissionLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmissionLine);
            em.flush();
        } else {
            mBiddingSubmissionLine = TestUtil.findAll(em, MBiddingSubmissionLine.class).get(0);
        }
        mSubmissionSubItem.setMBiddingSubmissionLine(mBiddingSubmissionLine);
        return mSubmissionSubItem;
    }

    @BeforeEach
    public void initTest() {
        mSubmissionSubItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMSubmissionSubItem() throws Exception {
        int databaseSizeBeforeCreate = mSubmissionSubItemRepository.findAll().size();
        // Create the MSubmissionSubItem
        MSubmissionSubItemDTO mSubmissionSubItemDTO = mSubmissionSubItemMapper.toDto(mSubmissionSubItem);
        restMSubmissionSubItemMockMvc.perform(post("/api/m-submission-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mSubmissionSubItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MSubmissionSubItem in the database
        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeCreate + 1);
        MSubmissionSubItem testMSubmissionSubItem = mSubmissionSubItemList.get(mSubmissionSubItemList.size() - 1);
        assertThat(testMSubmissionSubItem.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMSubmissionSubItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMSubmissionSubItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMSubmissionSubItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mSubmissionSubItemRepository.findAll().size();

        // Create the MSubmissionSubItem with an existing ID
        mSubmissionSubItem.setId(1L);
        MSubmissionSubItemDTO mSubmissionSubItemDTO = mSubmissionSubItemMapper.toDto(mSubmissionSubItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMSubmissionSubItemMockMvc.perform(post("/api/m-submission-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mSubmissionSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MSubmissionSubItem in the database
        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mSubmissionSubItemRepository.findAll().size();
        // set the field null
        mSubmissionSubItem.setProposedPrice(null);

        // Create the MSubmissionSubItem, which fails.
        MSubmissionSubItemDTO mSubmissionSubItemDTO = mSubmissionSubItemMapper.toDto(mSubmissionSubItem);


        restMSubmissionSubItemMockMvc.perform(post("/api/m-submission-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mSubmissionSubItemDTO)))
            .andExpect(status().isBadRequest());

        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItems() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mSubmissionSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMSubmissionSubItem() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get the mSubmissionSubItem
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items/{id}", mSubmissionSubItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mSubmissionSubItem.getId().intValue()))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMSubmissionSubItemsByIdFiltering() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        Long id = mSubmissionSubItem.getId();

        defaultMSubmissionSubItemShouldBeFound("id.equals=" + id);
        defaultMSubmissionSubItemShouldNotBeFound("id.notEquals=" + id);

        defaultMSubmissionSubItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMSubmissionSubItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMSubmissionSubItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMSubmissionSubItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice is not null
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.specified=true");

        // Get all the mSubmissionSubItemList where proposedPrice is null
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mSubmissionSubItemList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMSubmissionSubItemShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where uid equals to DEFAULT_UID
        defaultMSubmissionSubItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mSubmissionSubItemList where uid equals to UPDATED_UID
        defaultMSubmissionSubItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where uid not equals to DEFAULT_UID
        defaultMSubmissionSubItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mSubmissionSubItemList where uid not equals to UPDATED_UID
        defaultMSubmissionSubItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMSubmissionSubItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mSubmissionSubItemList where uid equals to UPDATED_UID
        defaultMSubmissionSubItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where uid is not null
        defaultMSubmissionSubItemShouldBeFound("uid.specified=true");

        // Get all the mSubmissionSubItemList where uid is null
        defaultMSubmissionSubItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where active equals to DEFAULT_ACTIVE
        defaultMSubmissionSubItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mSubmissionSubItemList where active equals to UPDATED_ACTIVE
        defaultMSubmissionSubItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where active not equals to DEFAULT_ACTIVE
        defaultMSubmissionSubItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mSubmissionSubItemList where active not equals to UPDATED_ACTIVE
        defaultMSubmissionSubItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMSubmissionSubItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mSubmissionSubItemList where active equals to UPDATED_ACTIVE
        defaultMSubmissionSubItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        // Get all the mSubmissionSubItemList where active is not null
        defaultMSubmissionSubItemShouldBeFound("active.specified=true");

        // Get all the mSubmissionSubItemList where active is null
        defaultMSubmissionSubItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByBiddingSubItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubItem biddingSubItem = mSubmissionSubItem.getBiddingSubItem();
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);
        Long biddingSubItemId = biddingSubItem.getId();

        // Get all the mSubmissionSubItemList where biddingSubItem equals to biddingSubItemId
        defaultMSubmissionSubItemShouldBeFound("biddingSubItemId.equals=" + biddingSubItemId);

        // Get all the mSubmissionSubItemList where biddingSubItem equals to biddingSubItemId + 1
        defaultMSubmissionSubItemShouldNotBeFound("biddingSubItemId.equals=" + (biddingSubItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mSubmissionSubItem.getAdOrganization();
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mSubmissionSubItemList where adOrganization equals to adOrganizationId
        defaultMSubmissionSubItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mSubmissionSubItemList where adOrganization equals to adOrganizationId + 1
        defaultMSubmissionSubItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMSubmissionSubItemsByMBiddingSubmissionLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmissionLine mBiddingSubmissionLine = mSubmissionSubItem.getMBiddingSubmissionLine();
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);
        Long mBiddingSubmissionLineId = mBiddingSubmissionLine.getId();

        // Get all the mSubmissionSubItemList where mBiddingSubmissionLine equals to mBiddingSubmissionLineId
        defaultMSubmissionSubItemShouldBeFound("mBiddingSubmissionLineId.equals=" + mBiddingSubmissionLineId);

        // Get all the mSubmissionSubItemList where mBiddingSubmissionLine equals to mBiddingSubmissionLineId + 1
        defaultMSubmissionSubItemShouldNotBeFound("mBiddingSubmissionLineId.equals=" + (mBiddingSubmissionLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMSubmissionSubItemShouldBeFound(String filter) throws Exception {
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mSubmissionSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMSubmissionSubItemShouldNotBeFound(String filter) throws Exception {
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMSubmissionSubItem() throws Exception {
        // Get the mSubmissionSubItem
        restMSubmissionSubItemMockMvc.perform(get("/api/m-submission-sub-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMSubmissionSubItem() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        int databaseSizeBeforeUpdate = mSubmissionSubItemRepository.findAll().size();

        // Update the mSubmissionSubItem
        MSubmissionSubItem updatedMSubmissionSubItem = mSubmissionSubItemRepository.findById(mSubmissionSubItem.getId()).get();
        // Disconnect from session so that the updates on updatedMSubmissionSubItem are not directly saved in db
        em.detach(updatedMSubmissionSubItem);
        updatedMSubmissionSubItem
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MSubmissionSubItemDTO mSubmissionSubItemDTO = mSubmissionSubItemMapper.toDto(updatedMSubmissionSubItem);

        restMSubmissionSubItemMockMvc.perform(put("/api/m-submission-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mSubmissionSubItemDTO)))
            .andExpect(status().isOk());

        // Validate the MSubmissionSubItem in the database
        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeUpdate);
        MSubmissionSubItem testMSubmissionSubItem = mSubmissionSubItemList.get(mSubmissionSubItemList.size() - 1);
        assertThat(testMSubmissionSubItem.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMSubmissionSubItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMSubmissionSubItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMSubmissionSubItem() throws Exception {
        int databaseSizeBeforeUpdate = mSubmissionSubItemRepository.findAll().size();

        // Create the MSubmissionSubItem
        MSubmissionSubItemDTO mSubmissionSubItemDTO = mSubmissionSubItemMapper.toDto(mSubmissionSubItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMSubmissionSubItemMockMvc.perform(put("/api/m-submission-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mSubmissionSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MSubmissionSubItem in the database
        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMSubmissionSubItem() throws Exception {
        // Initialize the database
        mSubmissionSubItemRepository.saveAndFlush(mSubmissionSubItem);

        int databaseSizeBeforeDelete = mSubmissionSubItemRepository.findAll().size();

        // Delete the mSubmissionSubItem
        restMSubmissionSubItemMockMvc.perform(delete("/api/m-submission-sub-items/{id}", mSubmissionSubItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MSubmissionSubItem> mSubmissionSubItemList = mSubmissionSubItemRepository.findAll();
        assertThat(mSubmissionSubItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
