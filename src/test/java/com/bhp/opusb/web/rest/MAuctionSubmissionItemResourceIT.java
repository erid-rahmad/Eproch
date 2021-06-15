package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionSubmissionItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.repository.MAuctionSubmissionItemRepository;
import com.bhp.opusb.service.MAuctionSubmissionItemService;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionItemMapper;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemCriteria;
import com.bhp.opusb.service.MAuctionSubmissionItemQueryService;

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
 * Integration tests for the {@link MAuctionSubmissionItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionSubmissionItemResourceIT {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionSubmissionItemRepository mAuctionSubmissionItemRepository;

    @Autowired
    private MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper;

    @Autowired
    private MAuctionSubmissionItemService mAuctionSubmissionItemService;

    @Autowired
    private MAuctionSubmissionItemQueryService mAuctionSubmissionItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionSubmissionItemMockMvc;

    private MAuctionSubmissionItem mAuctionSubmissionItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmissionItem createEntity(EntityManager em) {
        MAuctionSubmissionItem mAuctionSubmissionItem = new MAuctionSubmissionItem()
            .price(DEFAULT_PRICE)
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
        mAuctionSubmissionItem.setAdOrganization(aDOrganization);
        // Add required entity
        MAuctionSubmission mAuctionSubmission;
        if (TestUtil.findAll(em, MAuctionSubmission.class).isEmpty()) {
            mAuctionSubmission = MAuctionSubmissionResourceIT.createEntity(em);
            em.persist(mAuctionSubmission);
            em.flush();
        } else {
            mAuctionSubmission = TestUtil.findAll(em, MAuctionSubmission.class).get(0);
        }
        mAuctionSubmissionItem.setAuctionSubmission(mAuctionSubmission);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmissionItem.setAuctionItem(mAuctionItem);
        return mAuctionSubmissionItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmissionItem createUpdatedEntity(EntityManager em) {
        MAuctionSubmissionItem mAuctionSubmissionItem = new MAuctionSubmissionItem()
            .price(UPDATED_PRICE)
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
        mAuctionSubmissionItem.setAdOrganization(aDOrganization);
        // Add required entity
        MAuctionSubmission mAuctionSubmission;
        if (TestUtil.findAll(em, MAuctionSubmission.class).isEmpty()) {
            mAuctionSubmission = MAuctionSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mAuctionSubmission);
            em.flush();
        } else {
            mAuctionSubmission = TestUtil.findAll(em, MAuctionSubmission.class).get(0);
        }
        mAuctionSubmissionItem.setAuctionSubmission(mAuctionSubmission);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createUpdatedEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmissionItem.setAuctionItem(mAuctionItem);
        return mAuctionSubmissionItem;
    }

    @BeforeEach
    public void initTest() {
        mAuctionSubmissionItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionSubmissionItem() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionItemRepository.findAll().size();

        // Create the MAuctionSubmissionItem
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);
        restMAuctionSubmissionItemMockMvc.perform(post("/api/m-auction-submission-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionSubmissionItem in the database
        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionSubmissionItem testMAuctionSubmissionItem = mAuctionSubmissionItemList.get(mAuctionSubmissionItemList.size() - 1);
        assertThat(testMAuctionSubmissionItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMAuctionSubmissionItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionSubmissionItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionSubmissionItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionItemRepository.findAll().size();

        // Create the MAuctionSubmissionItem with an existing ID
        mAuctionSubmissionItem.setId(1L);
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionSubmissionItemMockMvc.perform(post("/api/m-auction-submission-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmissionItem in the database
        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionSubmissionItemRepository.findAll().size();
        // set the field null
        mAuctionSubmissionItem.setPrice(null);

        // Create the MAuctionSubmissionItem, which fails.
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);

        restMAuctionSubmissionItemMockMvc.perform(post("/api/m-auction-submission-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionItemDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItems() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmissionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionSubmissionItem() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get the mAuctionSubmissionItem
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items/{id}", mAuctionSubmissionItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionSubmissionItem.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionSubmissionItemsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        Long id = mAuctionSubmissionItem.getId();

        defaultMAuctionSubmissionItemShouldBeFound("id.equals=" + id);
        defaultMAuctionSubmissionItemShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionSubmissionItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionSubmissionItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionSubmissionItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionSubmissionItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price equals to DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price not equals to DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price not equals to UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mAuctionSubmissionItemList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price is not null
        defaultMAuctionSubmissionItemShouldBeFound("price.specified=true");

        // Get all the mAuctionSubmissionItemList where price is null
        defaultMAuctionSubmissionItemShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price is greater than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price is greater than or equal to UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price is less than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price is less than or equal to SMALLER_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price is less than DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price is less than UPDATED_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where price is greater than DEFAULT_PRICE
        defaultMAuctionSubmissionItemShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionItemList where price is greater than SMALLER_PRICE
        defaultMAuctionSubmissionItemShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where uid equals to DEFAULT_UID
        defaultMAuctionSubmissionItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionSubmissionItemList where uid equals to UPDATED_UID
        defaultMAuctionSubmissionItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where uid not equals to DEFAULT_UID
        defaultMAuctionSubmissionItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionSubmissionItemList where uid not equals to UPDATED_UID
        defaultMAuctionSubmissionItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionSubmissionItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionSubmissionItemList where uid equals to UPDATED_UID
        defaultMAuctionSubmissionItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where uid is not null
        defaultMAuctionSubmissionItemShouldBeFound("uid.specified=true");

        // Get all the mAuctionSubmissionItemList where uid is null
        defaultMAuctionSubmissionItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where active equals to DEFAULT_ACTIVE
        defaultMAuctionSubmissionItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionSubmissionItemList where active equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionSubmissionItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionSubmissionItemList where active not equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionSubmissionItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionSubmissionItemList where active equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        // Get all the mAuctionSubmissionItemList where active is not null
        defaultMAuctionSubmissionItemShouldBeFound("active.specified=true");

        // Get all the mAuctionSubmissionItemList where active is null
        defaultMAuctionSubmissionItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionSubmissionItem.getAdOrganization();
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionSubmissionItemList where adOrganization equals to adOrganizationId
        defaultMAuctionSubmissionItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionSubmissionItemList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionSubmissionItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByAuctionSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuctionSubmission auctionSubmission = mAuctionSubmissionItem.getAuctionSubmission();
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);
        Long auctionSubmissionId = auctionSubmission.getId();

        // Get all the mAuctionSubmissionItemList where auctionSubmission equals to auctionSubmissionId
        defaultMAuctionSubmissionItemShouldBeFound("auctionSubmissionId.equals=" + auctionSubmissionId);

        // Get all the mAuctionSubmissionItemList where auctionSubmission equals to auctionSubmissionId + 1
        defaultMAuctionSubmissionItemShouldNotBeFound("auctionSubmissionId.equals=" + (auctionSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionItemsByAuctionItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuctionItem auctionItem = mAuctionSubmissionItem.getAuctionItem();
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);
        Long auctionItemId = auctionItem.getId();

        // Get all the mAuctionSubmissionItemList where auctionItem equals to auctionItemId
        defaultMAuctionSubmissionItemShouldBeFound("auctionItemId.equals=" + auctionItemId);

        // Get all the mAuctionSubmissionItemList where auctionItem equals to auctionItemId + 1
        defaultMAuctionSubmissionItemShouldNotBeFound("auctionItemId.equals=" + (auctionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionSubmissionItemShouldBeFound(String filter) throws Exception {
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmissionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionSubmissionItemShouldNotBeFound(String filter) throws Exception {
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionSubmissionItem() throws Exception {
        // Get the mAuctionSubmissionItem
        restMAuctionSubmissionItemMockMvc.perform(get("/api/m-auction-submission-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionSubmissionItem() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        int databaseSizeBeforeUpdate = mAuctionSubmissionItemRepository.findAll().size();

        // Update the mAuctionSubmissionItem
        MAuctionSubmissionItem updatedMAuctionSubmissionItem = mAuctionSubmissionItemRepository.findById(mAuctionSubmissionItem.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionSubmissionItem are not directly saved in db
        em.detach(updatedMAuctionSubmissionItem);
        updatedMAuctionSubmissionItem
            .price(UPDATED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = mAuctionSubmissionItemMapper.toDto(updatedMAuctionSubmissionItem);

        restMAuctionSubmissionItemMockMvc.perform(put("/api/m-auction-submission-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionItemDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionSubmissionItem in the database
        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeUpdate);
        MAuctionSubmissionItem testMAuctionSubmissionItem = mAuctionSubmissionItemList.get(mAuctionSubmissionItemList.size() - 1);
        assertThat(testMAuctionSubmissionItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMAuctionSubmissionItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionSubmissionItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionSubmissionItem() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionSubmissionItemRepository.findAll().size();

        // Create the MAuctionSubmissionItem
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionSubmissionItemMockMvc.perform(put("/api/m-auction-submission-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmissionItem in the database
        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionSubmissionItem() throws Exception {
        // Initialize the database
        mAuctionSubmissionItemRepository.saveAndFlush(mAuctionSubmissionItem);

        int databaseSizeBeforeDelete = mAuctionSubmissionItemRepository.findAll().size();

        // Delete the mAuctionSubmissionItem
        restMAuctionSubmissionItemMockMvc.perform(delete("/api/m-auction-submission-items/{id}", mAuctionSubmissionItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionSubmissionItem> mAuctionSubmissionItemList = mAuctionSubmissionItemRepository.findAll();
        assertThat(mAuctionSubmissionItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
