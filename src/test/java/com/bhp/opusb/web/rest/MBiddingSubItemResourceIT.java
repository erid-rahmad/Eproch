package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.repository.MBiddingSubItemRepository;
import com.bhp.opusb.service.MBiddingSubItemService;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemMapper;
import com.bhp.opusb.service.dto.MBiddingSubItemCriteria;
import com.bhp.opusb.service.MBiddingSubItemQueryService;

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
 * Integration tests for the {@link MBiddingSubItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingSubItemResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_AMOUNT = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingSubItemRepository mBiddingSubItemRepository;

    @Autowired
    private MBiddingSubItemMapper mBiddingSubItemMapper;

    @Autowired
    private MBiddingSubItemService mBiddingSubItemService;

    @Autowired
    private MBiddingSubItemQueryService mBiddingSubItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingSubItemMockMvc;

    private MBiddingSubItem mBiddingSubItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubItem createEntity(EntityManager em) {
        MBiddingSubItem mBiddingSubItem = new MBiddingSubItem()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
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
        mBiddingSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingSubItem.setProduct(cProduct);
        return mBiddingSubItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubItem createUpdatedEntity(EntityManager em) {
        MBiddingSubItem mBiddingSubItem = new MBiddingSubItem()
            .totalAmount(UPDATED_TOTAL_AMOUNT)
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
        mBiddingSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingSubItem.setProduct(cProduct);
        return mBiddingSubItem;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSubItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSubItem() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubItemRepository.findAll().size();

        // Create the MBiddingSubItem
        MBiddingSubItemDTO mBiddingSubItemDTO = mBiddingSubItemMapper.toDto(mBiddingSubItem);
        restMBiddingSubItemMockMvc.perform(post("/api/m-bidding-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSubItem in the database
        List<MBiddingSubItem> mBiddingSubItemList = mBiddingSubItemRepository.findAll();
        assertThat(mBiddingSubItemList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSubItem testMBiddingSubItem = mBiddingSubItemList.get(mBiddingSubItemList.size() - 1);
        assertThat(testMBiddingSubItem.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testMBiddingSubItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSubItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingSubItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubItemRepository.findAll().size();

        // Create the MBiddingSubItem with an existing ID
        mBiddingSubItem.setId(1L);
        MBiddingSubItemDTO mBiddingSubItemDTO = mBiddingSubItemMapper.toDto(mBiddingSubItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingSubItemMockMvc.perform(post("/api/m-bidding-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubItem in the database
        List<MBiddingSubItem> mBiddingSubItemList = mBiddingSubItemRepository.findAll();
        assertThat(mBiddingSubItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItems() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSubItem() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get the mBiddingSubItem
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items/{id}", mBiddingSubItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSubItem.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSubItemsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        Long id = mBiddingSubItem.getId();

        defaultMBiddingSubItemShouldBeFound("id.equals=" + id);
        defaultMBiddingSubItemShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingSubItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingSubItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingSubItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingSubItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount not equals to DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.notEquals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount not equals to UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.notEquals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount is not null
        defaultMBiddingSubItemShouldBeFound("totalAmount.specified=true");

        // Get all the mBiddingSubItemList where totalAmount is null
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the mBiddingSubItemList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultMBiddingSubItemShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where uid equals to DEFAULT_UID
        defaultMBiddingSubItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingSubItemList where uid equals to UPDATED_UID
        defaultMBiddingSubItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where uid not equals to DEFAULT_UID
        defaultMBiddingSubItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingSubItemList where uid not equals to UPDATED_UID
        defaultMBiddingSubItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingSubItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingSubItemList where uid equals to UPDATED_UID
        defaultMBiddingSubItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where uid is not null
        defaultMBiddingSubItemShouldBeFound("uid.specified=true");

        // Get all the mBiddingSubItemList where uid is null
        defaultMBiddingSubItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where active equals to DEFAULT_ACTIVE
        defaultMBiddingSubItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubItemList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingSubItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubItemList where active not equals to UPDATED_ACTIVE
        defaultMBiddingSubItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingSubItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingSubItemList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        // Get all the mBiddingSubItemList where active is not null
        defaultMBiddingSubItemShouldBeFound("active.specified=true");

        // Get all the mBiddingSubItemList where active is null
        defaultMBiddingSubItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemsByMBiddingSubItemLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        MBiddingSubItemLine mBiddingSubItemLine = MBiddingSubItemLineResourceIT.createEntity(em);
        em.persist(mBiddingSubItemLine);
        em.flush();
        mBiddingSubItem.addMBiddingSubItemLine(mBiddingSubItemLine);
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        Long mBiddingSubItemLineId = mBiddingSubItemLine.getId();

        // Get all the mBiddingSubItemList where mBiddingSubItemLine equals to mBiddingSubItemLineId
        defaultMBiddingSubItemShouldBeFound("mBiddingSubItemLineId.equals=" + mBiddingSubItemLineId);

        // Get all the mBiddingSubItemList where mBiddingSubItemLine equals to mBiddingSubItemLineId + 1
        defaultMBiddingSubItemShouldNotBeFound("mBiddingSubItemLineId.equals=" + (mBiddingSubItemLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSubItem.getAdOrganization();
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingSubItemList where adOrganization equals to adOrganizationId
        defaultMBiddingSubItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingSubItemList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingSubItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemsByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mBiddingSubItem.getProduct();
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        Long productId = product.getId();

        // Get all the mBiddingSubItemList where product equals to productId
        defaultMBiddingSubItemShouldBeFound("productId.equals=" + productId);

        // Get all the mBiddingSubItemList where product equals to productId + 1
        defaultMBiddingSubItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemsByBiddingLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        MBiddingLine biddingLine = MBiddingLineResourceIT.createEntity(em);
        em.persist(biddingLine);
        em.flush();
        mBiddingSubItem.setBiddingLine(biddingLine);
        biddingLine.setSubItem(mBiddingSubItem);
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);
        Long biddingLineId = biddingLine.getId();

        // Get all the mBiddingSubItemList where biddingLine equals to biddingLineId
        defaultMBiddingSubItemShouldBeFound("biddingLineId.equals=" + biddingLineId);

        // Get all the mBiddingSubItemList where biddingLine equals to biddingLineId + 1
        defaultMBiddingSubItemShouldNotBeFound("biddingLineId.equals=" + (biddingLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingSubItemShouldBeFound(String filter) throws Exception {
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingSubItemShouldNotBeFound(String filter) throws Exception {
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingSubItem() throws Exception {
        // Get the mBiddingSubItem
        restMBiddingSubItemMockMvc.perform(get("/api/m-bidding-sub-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSubItem() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        int databaseSizeBeforeUpdate = mBiddingSubItemRepository.findAll().size();

        // Update the mBiddingSubItem
        MBiddingSubItem updatedMBiddingSubItem = mBiddingSubItemRepository.findById(mBiddingSubItem.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSubItem are not directly saved in db
        em.detach(updatedMBiddingSubItem);
        updatedMBiddingSubItem
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingSubItemDTO mBiddingSubItemDTO = mBiddingSubItemMapper.toDto(updatedMBiddingSubItem);

        restMBiddingSubItemMockMvc.perform(put("/api/m-bidding-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSubItem in the database
        List<MBiddingSubItem> mBiddingSubItemList = mBiddingSubItemRepository.findAll();
        assertThat(mBiddingSubItemList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSubItem testMBiddingSubItem = mBiddingSubItemList.get(mBiddingSubItemList.size() - 1);
        assertThat(testMBiddingSubItem.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testMBiddingSubItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSubItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSubItem() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingSubItemRepository.findAll().size();

        // Create the MBiddingSubItem
        MBiddingSubItemDTO mBiddingSubItemDTO = mBiddingSubItemMapper.toDto(mBiddingSubItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingSubItemMockMvc.perform(put("/api/m-bidding-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubItem in the database
        List<MBiddingSubItem> mBiddingSubItemList = mBiddingSubItemRepository.findAll();
        assertThat(mBiddingSubItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSubItem() throws Exception {
        // Initialize the database
        mBiddingSubItemRepository.saveAndFlush(mBiddingSubItem);

        int databaseSizeBeforeDelete = mBiddingSubItemRepository.findAll().size();

        // Delete the mBiddingSubItem
        restMBiddingSubItemMockMvc.perform(delete("/api/m-bidding-sub-items/{id}", mBiddingSubItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSubItem> mBiddingSubItemList = mBiddingSubItemRepository.findAll();
        assertThat(mBiddingSubItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
