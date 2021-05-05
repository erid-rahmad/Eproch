package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalPriceSubItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.repository.MProposalPriceSubItemRepository;
import com.bhp.opusb.service.MProposalPriceSubItemService;
import com.bhp.opusb.service.dto.MProposalPriceSubItemDTO;
import com.bhp.opusb.service.mapper.MProposalPriceSubItemMapper;
import com.bhp.opusb.service.dto.MProposalPriceSubItemCriteria;
import com.bhp.opusb.service.MProposalPriceSubItemQueryService;

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
 * Integration tests for the {@link MProposalPriceSubItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalPriceSubItemResourceIT {

    private static final BigDecimal DEFAULT_PROPOSED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROPOSED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROPOSED_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProposalPriceSubItemRepository mProposalPriceSubItemRepository;

    @Autowired
    private MProposalPriceSubItemMapper mProposalPriceSubItemMapper;

    @Autowired
    private MProposalPriceSubItemService mProposalPriceSubItemService;

    @Autowired
    private MProposalPriceSubItemQueryService mProposalPriceSubItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalPriceSubItemMockMvc;

    private MProposalPriceSubItem mProposalPriceSubItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPriceSubItem createEntity(EntityManager em) {
        MProposalPriceSubItem mProposalPriceSubItem = new MProposalPriceSubItem()
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
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
        mProposalPriceSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubItemLine mBiddingSubItemLine;
        if (TestUtil.findAll(em, MBiddingSubItemLine.class).isEmpty()) {
            mBiddingSubItemLine = MBiddingSubItemLineResourceIT.createEntity(em);
            em.persist(mBiddingSubItemLine);
            em.flush();
        } else {
            mBiddingSubItemLine = TestUtil.findAll(em, MBiddingSubItemLine.class).get(0);
        }
        mProposalPriceSubItem.setBiddingSubItemLine(mBiddingSubItemLine);
        // Add required entity
        MProposalPriceLine mProposalPriceLine;
        if (TestUtil.findAll(em, MProposalPriceLine.class).isEmpty()) {
            mProposalPriceLine = MProposalPriceLineResourceIT.createEntity(em);
            em.persist(mProposalPriceLine);
            em.flush();
        } else {
            mProposalPriceLine = TestUtil.findAll(em, MProposalPriceLine.class).get(0);
        }
        mProposalPriceSubItem.setProposalPriceLine(mProposalPriceLine);
        return mProposalPriceSubItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPriceSubItem createUpdatedEntity(EntityManager em) {
        MProposalPriceSubItem mProposalPriceSubItem = new MProposalPriceSubItem()
            .proposedPrice(UPDATED_PROPOSED_PRICE)
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
        mProposalPriceSubItem.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubItemLine mBiddingSubItemLine;
        if (TestUtil.findAll(em, MBiddingSubItemLine.class).isEmpty()) {
            mBiddingSubItemLine = MBiddingSubItemLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubItemLine);
            em.flush();
        } else {
            mBiddingSubItemLine = TestUtil.findAll(em, MBiddingSubItemLine.class).get(0);
        }
        mProposalPriceSubItem.setBiddingSubItemLine(mBiddingSubItemLine);
        // Add required entity
        MProposalPriceLine mProposalPriceLine;
        if (TestUtil.findAll(em, MProposalPriceLine.class).isEmpty()) {
            mProposalPriceLine = MProposalPriceLineResourceIT.createUpdatedEntity(em);
            em.persist(mProposalPriceLine);
            em.flush();
        } else {
            mProposalPriceLine = TestUtil.findAll(em, MProposalPriceLine.class).get(0);
        }
        mProposalPriceSubItem.setProposalPriceLine(mProposalPriceLine);
        return mProposalPriceSubItem;
    }

    @BeforeEach
    public void initTest() {
        mProposalPriceSubItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalPriceSubItem() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceSubItemRepository.findAll().size();

        // Create the MProposalPriceSubItem
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = mProposalPriceSubItemMapper.toDto(mProposalPriceSubItem);
        restMProposalPriceSubItemMockMvc.perform(post("/api/m-proposal-price-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceSubItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalPriceSubItem in the database
        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalPriceSubItem testMProposalPriceSubItem = mProposalPriceSubItemList.get(mProposalPriceSubItemList.size() - 1);
        assertThat(testMProposalPriceSubItem.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMProposalPriceSubItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalPriceSubItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalPriceSubItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceSubItemRepository.findAll().size();

        // Create the MProposalPriceSubItem with an existing ID
        mProposalPriceSubItem.setId(1L);
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = mProposalPriceSubItemMapper.toDto(mProposalPriceSubItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalPriceSubItemMockMvc.perform(post("/api/m-proposal-price-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPriceSubItem in the database
        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceSubItemRepository.findAll().size();
        // set the field null
        mProposalPriceSubItem.setProposedPrice(null);

        // Create the MProposalPriceSubItem, which fails.
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = mProposalPriceSubItemMapper.toDto(mProposalPriceSubItem);

        restMProposalPriceSubItemMockMvc.perform(post("/api/m-proposal-price-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceSubItemDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItems() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPriceSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalPriceSubItem() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get the mProposalPriceSubItem
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items/{id}", mProposalPriceSubItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalPriceSubItem.getId().intValue()))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalPriceSubItemsByIdFiltering() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        Long id = mProposalPriceSubItem.getId();

        defaultMProposalPriceSubItemShouldBeFound("id.equals=" + id);
        defaultMProposalPriceSubItemShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalPriceSubItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalPriceSubItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalPriceSubItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalPriceSubItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice is not null
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.specified=true");

        // Get all the mProposalPriceSubItemList where proposedPrice is null
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceSubItemList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMProposalPriceSubItemShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where uid equals to DEFAULT_UID
        defaultMProposalPriceSubItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalPriceSubItemList where uid equals to UPDATED_UID
        defaultMProposalPriceSubItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where uid not equals to DEFAULT_UID
        defaultMProposalPriceSubItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalPriceSubItemList where uid not equals to UPDATED_UID
        defaultMProposalPriceSubItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalPriceSubItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalPriceSubItemList where uid equals to UPDATED_UID
        defaultMProposalPriceSubItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where uid is not null
        defaultMProposalPriceSubItemShouldBeFound("uid.specified=true");

        // Get all the mProposalPriceSubItemList where uid is null
        defaultMProposalPriceSubItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where active equals to DEFAULT_ACTIVE
        defaultMProposalPriceSubItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceSubItemList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceSubItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where active not equals to DEFAULT_ACTIVE
        defaultMProposalPriceSubItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceSubItemList where active not equals to UPDATED_ACTIVE
        defaultMProposalPriceSubItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalPriceSubItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalPriceSubItemList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceSubItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        // Get all the mProposalPriceSubItemList where active is not null
        defaultMProposalPriceSubItemShouldBeFound("active.specified=true");

        // Get all the mProposalPriceSubItemList where active is null
        defaultMProposalPriceSubItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalPriceSubItem.getAdOrganization();
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalPriceSubItemList where adOrganization equals to adOrganizationId
        defaultMProposalPriceSubItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalPriceSubItemList where adOrganization equals to adOrganizationId + 1
        defaultMProposalPriceSubItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByBiddingSubItemLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubItemLine biddingSubItemLine = mProposalPriceSubItem.getBiddingSubItemLine();
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);
        Long biddingSubItemLineId = biddingSubItemLine.getId();

        // Get all the mProposalPriceSubItemList where biddingSubItemLine equals to biddingSubItemLineId
        defaultMProposalPriceSubItemShouldBeFound("biddingSubItemLineId.equals=" + biddingSubItemLineId);

        // Get all the mProposalPriceSubItemList where biddingSubItemLine equals to biddingSubItemLineId + 1
        defaultMProposalPriceSubItemShouldNotBeFound("biddingSubItemLineId.equals=" + (biddingSubItemLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPriceSubItemsByProposalPriceLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MProposalPriceLine proposalPriceLine = mProposalPriceSubItem.getProposalPriceLine();
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);
        Long proposalPriceLineId = proposalPriceLine.getId();

        // Get all the mProposalPriceSubItemList where proposalPriceLine equals to proposalPriceLineId
        defaultMProposalPriceSubItemShouldBeFound("proposalPriceLineId.equals=" + proposalPriceLineId);

        // Get all the mProposalPriceSubItemList where proposalPriceLine equals to proposalPriceLineId + 1
        defaultMProposalPriceSubItemShouldNotBeFound("proposalPriceLineId.equals=" + (proposalPriceLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalPriceSubItemShouldBeFound(String filter) throws Exception {
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPriceSubItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalPriceSubItemShouldNotBeFound(String filter) throws Exception {
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalPriceSubItem() throws Exception {
        // Get the mProposalPriceSubItem
        restMProposalPriceSubItemMockMvc.perform(get("/api/m-proposal-price-sub-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalPriceSubItem() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        int databaseSizeBeforeUpdate = mProposalPriceSubItemRepository.findAll().size();

        // Update the mProposalPriceSubItem
        MProposalPriceSubItem updatedMProposalPriceSubItem = mProposalPriceSubItemRepository.findById(mProposalPriceSubItem.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalPriceSubItem are not directly saved in db
        em.detach(updatedMProposalPriceSubItem);
        updatedMProposalPriceSubItem
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = mProposalPriceSubItemMapper.toDto(updatedMProposalPriceSubItem);

        restMProposalPriceSubItemMockMvc.perform(put("/api/m-proposal-price-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceSubItemDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalPriceSubItem in the database
        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeUpdate);
        MProposalPriceSubItem testMProposalPriceSubItem = mProposalPriceSubItemList.get(mProposalPriceSubItemList.size() - 1);
        assertThat(testMProposalPriceSubItem.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMProposalPriceSubItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalPriceSubItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalPriceSubItem() throws Exception {
        int databaseSizeBeforeUpdate = mProposalPriceSubItemRepository.findAll().size();

        // Create the MProposalPriceSubItem
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = mProposalPriceSubItemMapper.toDto(mProposalPriceSubItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalPriceSubItemMockMvc.perform(put("/api/m-proposal-price-sub-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceSubItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPriceSubItem in the database
        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalPriceSubItem() throws Exception {
        // Initialize the database
        mProposalPriceSubItemRepository.saveAndFlush(mProposalPriceSubItem);

        int databaseSizeBeforeDelete = mProposalPriceSubItemRepository.findAll().size();

        // Delete the mProposalPriceSubItem
        restMProposalPriceSubItemMockMvc.perform(delete("/api/m-proposal-price-sub-items/{id}", mProposalPriceSubItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalPriceSubItem> mProposalPriceSubItemList = mProposalPriceSubItemRepository.findAll();
        assertThat(mProposalPriceSubItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
