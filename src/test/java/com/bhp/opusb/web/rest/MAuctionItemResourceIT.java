package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.MAuctionItemRepository;
import com.bhp.opusb.service.MAuctionItemService;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionItemMapper;
import com.bhp.opusb.service.dto.MAuctionItemCriteria;
import com.bhp.opusb.service.MAuctionItemQueryService;

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
 * Integration tests for the {@link MAuctionItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionItemResourceIT {

    private static final String DEFAULT_AUCTION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_AUCTION_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CEILING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CEILING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CEILING_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BID_DECREMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BID_DECREMENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_BID_DECREMENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROTECT_BACK_BUFFER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROTECT_BACK_BUFFER = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROTECT_BACK_BUFFER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROTECT_FRONT_BUFFER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROTECT_FRONT_BUFFER = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROTECT_FRONT_BUFFER = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionItemRepository mAuctionItemRepository;

    @Autowired
    private MAuctionItemMapper mAuctionItemMapper;

    @Autowired
    private MAuctionItemService mAuctionItemService;

    @Autowired
    private MAuctionItemQueryService mAuctionItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionItemMockMvc;

    private MAuctionItem mAuctionItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionItem createEntity(EntityManager em) {
        MAuctionItem mAuctionItem = new MAuctionItem()
            .auctionStatus(DEFAULT_AUCTION_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
            .amount(DEFAULT_AMOUNT)
            .bidDecrement(DEFAULT_BID_DECREMENT)
            .protectBackBuffer(DEFAULT_PROTECT_BACK_BUFFER)
            .protectFrontBuffer(DEFAULT_PROTECT_FRONT_BUFFER)
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
        mAuctionItem.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionItem.setAuction(mAuction);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mAuctionItem.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mAuctionItem.setUom(cUnitOfMeasure);
        return mAuctionItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionItem createUpdatedEntity(EntityManager em) {
        MAuctionItem mAuctionItem = new MAuctionItem()
            .auctionStatus(UPDATED_AUCTION_STATUS)
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .amount(UPDATED_AMOUNT)
            .bidDecrement(UPDATED_BID_DECREMENT)
            .protectBackBuffer(UPDATED_PROTECT_BACK_BUFFER)
            .protectFrontBuffer(UPDATED_PROTECT_FRONT_BUFFER)
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
        mAuctionItem.setAdOrganization(aDOrganization);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createUpdatedEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionItem.setAuction(mAuction);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mAuctionItem.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mAuctionItem.setUom(cUnitOfMeasure);
        return mAuctionItem;
    }

    @BeforeEach
    public void initTest() {
        mAuctionItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionItem() throws Exception {
        int databaseSizeBeforeCreate = mAuctionItemRepository.findAll().size();

        // Create the MAuctionItem
        MAuctionItemDTO mAuctionItemDTO = mAuctionItemMapper.toDto(mAuctionItem);
        restMAuctionItemMockMvc.perform(post("/api/m-auction-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionItem in the database
        List<MAuctionItem> mAuctionItemList = mAuctionItemRepository.findAll();
        assertThat(mAuctionItemList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionItem testMAuctionItem = mAuctionItemList.get(mAuctionItemList.size() - 1);
        assertThat(testMAuctionItem.getAuctionStatus()).isEqualTo(DEFAULT_AUCTION_STATUS);
        assertThat(testMAuctionItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMAuctionItem.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMAuctionItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMAuctionItem.getBidDecrement()).isEqualTo(DEFAULT_BID_DECREMENT);
        assertThat(testMAuctionItem.getProtectBackBuffer()).isEqualTo(DEFAULT_PROTECT_BACK_BUFFER);
        assertThat(testMAuctionItem.getProtectFrontBuffer()).isEqualTo(DEFAULT_PROTECT_FRONT_BUFFER);
        assertThat(testMAuctionItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionItemRepository.findAll().size();

        // Create the MAuctionItem with an existing ID
        mAuctionItem.setId(1L);
        MAuctionItemDTO mAuctionItemDTO = mAuctionItemMapper.toDto(mAuctionItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionItemMockMvc.perform(post("/api/m-auction-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionItem in the database
        List<MAuctionItem> mAuctionItemList = mAuctionItemRepository.findAll();
        assertThat(mAuctionItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMAuctionItems() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].auctionStatus").value(hasItem(DEFAULT_AUCTION_STATUS)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].bidDecrement").value(hasItem(DEFAULT_BID_DECREMENT.intValue())))
            .andExpect(jsonPath("$.[*].protectBackBuffer").value(hasItem(DEFAULT_PROTECT_BACK_BUFFER.intValue())))
            .andExpect(jsonPath("$.[*].protectFrontBuffer").value(hasItem(DEFAULT_PROTECT_FRONT_BUFFER.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionItem() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get the mAuctionItem
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items/{id}", mAuctionItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionItem.getId().intValue()))
            .andExpect(jsonPath("$.auctionStatus").value(DEFAULT_AUCTION_STATUS))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.ceilingPrice").value(DEFAULT_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bidDecrement").value(DEFAULT_BID_DECREMENT.intValue()))
            .andExpect(jsonPath("$.protectBackBuffer").value(DEFAULT_PROTECT_BACK_BUFFER.intValue()))
            .andExpect(jsonPath("$.protectFrontBuffer").value(DEFAULT_PROTECT_FRONT_BUFFER.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionItemsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        Long id = mAuctionItem.getId();

        defaultMAuctionItemShouldBeFound("id.equals=" + id);
        defaultMAuctionItemShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus equals to DEFAULT_AUCTION_STATUS
        defaultMAuctionItemShouldBeFound("auctionStatus.equals=" + DEFAULT_AUCTION_STATUS);

        // Get all the mAuctionItemList where auctionStatus equals to UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldNotBeFound("auctionStatus.equals=" + UPDATED_AUCTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus not equals to DEFAULT_AUCTION_STATUS
        defaultMAuctionItemShouldNotBeFound("auctionStatus.notEquals=" + DEFAULT_AUCTION_STATUS);

        // Get all the mAuctionItemList where auctionStatus not equals to UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldBeFound("auctionStatus.notEquals=" + UPDATED_AUCTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus in DEFAULT_AUCTION_STATUS or UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldBeFound("auctionStatus.in=" + DEFAULT_AUCTION_STATUS + "," + UPDATED_AUCTION_STATUS);

        // Get all the mAuctionItemList where auctionStatus equals to UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldNotBeFound("auctionStatus.in=" + UPDATED_AUCTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus is not null
        defaultMAuctionItemShouldBeFound("auctionStatus.specified=true");

        // Get all the mAuctionItemList where auctionStatus is null
        defaultMAuctionItemShouldNotBeFound("auctionStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusContainsSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus contains DEFAULT_AUCTION_STATUS
        defaultMAuctionItemShouldBeFound("auctionStatus.contains=" + DEFAULT_AUCTION_STATUS);

        // Get all the mAuctionItemList where auctionStatus contains UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldNotBeFound("auctionStatus.contains=" + UPDATED_AUCTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where auctionStatus does not contain DEFAULT_AUCTION_STATUS
        defaultMAuctionItemShouldNotBeFound("auctionStatus.doesNotContain=" + DEFAULT_AUCTION_STATUS);

        // Get all the mAuctionItemList where auctionStatus does not contain UPDATED_AUCTION_STATUS
        defaultMAuctionItemShouldBeFound("auctionStatus.doesNotContain=" + UPDATED_AUCTION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity equals to DEFAULT_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity equals to UPDATED_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity not equals to DEFAULT_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity not equals to UPDATED_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mAuctionItemList where quantity equals to UPDATED_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity is not null
        defaultMAuctionItemShouldBeFound("quantity.specified=true");

        // Get all the mAuctionItemList where quantity is null
        defaultMAuctionItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity is less than DEFAULT_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity is less than UPDATED_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where quantity is greater than DEFAULT_QUANTITY
        defaultMAuctionItemShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mAuctionItemList where quantity is greater than SMALLER_QUANTITY
        defaultMAuctionItemShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice is not null
        defaultMAuctionItemShouldBeFound("ceilingPrice.specified=true");

        // Get all the mAuctionItemList where ceilingPrice is null
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMAuctionItemShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mAuctionItemList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMAuctionItemShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount equals to DEFAULT_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount equals to UPDATED_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount not equals to DEFAULT_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount not equals to UPDATED_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the mAuctionItemList where amount equals to UPDATED_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount is not null
        defaultMAuctionItemShouldBeFound("amount.specified=true");

        // Get all the mAuctionItemList where amount is null
        defaultMAuctionItemShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount is greater than or equal to UPDATED_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount is less than or equal to DEFAULT_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount is less than or equal to SMALLER_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount is less than DEFAULT_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount is less than UPDATED_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where amount is greater than DEFAULT_AMOUNT
        defaultMAuctionItemShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the mAuctionItemList where amount is greater than SMALLER_AMOUNT
        defaultMAuctionItemShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement equals to DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.equals=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement equals to UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.equals=" + UPDATED_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement not equals to DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.notEquals=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement not equals to UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.notEquals=" + UPDATED_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement in DEFAULT_BID_DECREMENT or UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.in=" + DEFAULT_BID_DECREMENT + "," + UPDATED_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement equals to UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.in=" + UPDATED_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement is not null
        defaultMAuctionItemShouldBeFound("bidDecrement.specified=true");

        // Get all the mAuctionItemList where bidDecrement is null
        defaultMAuctionItemShouldNotBeFound("bidDecrement.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement is greater than or equal to DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.greaterThanOrEqual=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement is greater than or equal to UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.greaterThanOrEqual=" + UPDATED_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement is less than or equal to DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.lessThanOrEqual=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement is less than or equal to SMALLER_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.lessThanOrEqual=" + SMALLER_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement is less than DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.lessThan=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement is less than UPDATED_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.lessThan=" + UPDATED_BID_DECREMENT);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByBidDecrementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where bidDecrement is greater than DEFAULT_BID_DECREMENT
        defaultMAuctionItemShouldNotBeFound("bidDecrement.greaterThan=" + DEFAULT_BID_DECREMENT);

        // Get all the mAuctionItemList where bidDecrement is greater than SMALLER_BID_DECREMENT
        defaultMAuctionItemShouldBeFound("bidDecrement.greaterThan=" + SMALLER_BID_DECREMENT);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer equals to DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.equals=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer equals to UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.equals=" + UPDATED_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer not equals to DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.notEquals=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer not equals to UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.notEquals=" + UPDATED_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer in DEFAULT_PROTECT_BACK_BUFFER or UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.in=" + DEFAULT_PROTECT_BACK_BUFFER + "," + UPDATED_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer equals to UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.in=" + UPDATED_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer is not null
        defaultMAuctionItemShouldBeFound("protectBackBuffer.specified=true");

        // Get all the mAuctionItemList where protectBackBuffer is null
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer is greater than or equal to DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.greaterThanOrEqual=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer is greater than or equal to UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.greaterThanOrEqual=" + UPDATED_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer is less than or equal to DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.lessThanOrEqual=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer is less than or equal to SMALLER_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.lessThanOrEqual=" + SMALLER_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer is less than DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.lessThan=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer is less than UPDATED_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.lessThan=" + UPDATED_PROTECT_BACK_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectBackBufferIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectBackBuffer is greater than DEFAULT_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectBackBuffer.greaterThan=" + DEFAULT_PROTECT_BACK_BUFFER);

        // Get all the mAuctionItemList where protectBackBuffer is greater than SMALLER_PROTECT_BACK_BUFFER
        defaultMAuctionItemShouldBeFound("protectBackBuffer.greaterThan=" + SMALLER_PROTECT_BACK_BUFFER);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer equals to DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.equals=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer equals to UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.equals=" + UPDATED_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer not equals to DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.notEquals=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer not equals to UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.notEquals=" + UPDATED_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer in DEFAULT_PROTECT_FRONT_BUFFER or UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.in=" + DEFAULT_PROTECT_FRONT_BUFFER + "," + UPDATED_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer equals to UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.in=" + UPDATED_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer is not null
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.specified=true");

        // Get all the mAuctionItemList where protectFrontBuffer is null
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer is greater than or equal to DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.greaterThanOrEqual=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer is greater than or equal to UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.greaterThanOrEqual=" + UPDATED_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer is less than or equal to DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.lessThanOrEqual=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer is less than or equal to SMALLER_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.lessThanOrEqual=" + SMALLER_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer is less than DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.lessThan=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer is less than UPDATED_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.lessThan=" + UPDATED_PROTECT_FRONT_BUFFER);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByProtectFrontBufferIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where protectFrontBuffer is greater than DEFAULT_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldNotBeFound("protectFrontBuffer.greaterThan=" + DEFAULT_PROTECT_FRONT_BUFFER);

        // Get all the mAuctionItemList where protectFrontBuffer is greater than SMALLER_PROTECT_FRONT_BUFFER
        defaultMAuctionItemShouldBeFound("protectFrontBuffer.greaterThan=" + SMALLER_PROTECT_FRONT_BUFFER);
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where uid equals to DEFAULT_UID
        defaultMAuctionItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionItemList where uid equals to UPDATED_UID
        defaultMAuctionItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where uid not equals to DEFAULT_UID
        defaultMAuctionItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionItemList where uid not equals to UPDATED_UID
        defaultMAuctionItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionItemList where uid equals to UPDATED_UID
        defaultMAuctionItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where uid is not null
        defaultMAuctionItemShouldBeFound("uid.specified=true");

        // Get all the mAuctionItemList where uid is null
        defaultMAuctionItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where active equals to DEFAULT_ACTIVE
        defaultMAuctionItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionItemList where active equals to UPDATED_ACTIVE
        defaultMAuctionItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionItemList where active not equals to UPDATED_ACTIVE
        defaultMAuctionItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionItemList where active equals to UPDATED_ACTIVE
        defaultMAuctionItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        // Get all the mAuctionItemList where active is not null
        defaultMAuctionItemShouldBeFound("active.specified=true");

        // Get all the mAuctionItemList where active is null
        defaultMAuctionItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionItem.getAdOrganization();
        mAuctionItemRepository.saveAndFlush(mAuctionItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionItemList where adOrganization equals to adOrganizationId
        defaultMAuctionItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionItemList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByAuctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuction auction = mAuctionItem.getAuction();
        mAuctionItemRepository.saveAndFlush(mAuctionItem);
        Long auctionId = auction.getId();

        // Get all the mAuctionItemList where auction equals to auctionId
        defaultMAuctionItemShouldBeFound("auctionId.equals=" + auctionId);

        // Get all the mAuctionItemList where auction equals to auctionId + 1
        defaultMAuctionItemShouldNotBeFound("auctionId.equals=" + (auctionId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mAuctionItem.getProduct();
        mAuctionItemRepository.saveAndFlush(mAuctionItem);
        Long productId = product.getId();

        // Get all the mAuctionItemList where product equals to productId
        defaultMAuctionItemShouldBeFound("productId.equals=" + productId);

        // Get all the mAuctionItemList where product equals to productId + 1
        defaultMAuctionItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionItemsByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mAuctionItem.getUom();
        mAuctionItemRepository.saveAndFlush(mAuctionItem);
        Long uomId = uom.getId();

        // Get all the mAuctionItemList where uom equals to uomId
        defaultMAuctionItemShouldBeFound("uomId.equals=" + uomId);

        // Get all the mAuctionItemList where uom equals to uomId + 1
        defaultMAuctionItemShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionItemShouldBeFound(String filter) throws Exception {
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].auctionStatus").value(hasItem(DEFAULT_AUCTION_STATUS)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].bidDecrement").value(hasItem(DEFAULT_BID_DECREMENT.intValue())))
            .andExpect(jsonPath("$.[*].protectBackBuffer").value(hasItem(DEFAULT_PROTECT_BACK_BUFFER.intValue())))
            .andExpect(jsonPath("$.[*].protectFrontBuffer").value(hasItem(DEFAULT_PROTECT_FRONT_BUFFER.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionItemShouldNotBeFound(String filter) throws Exception {
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionItem() throws Exception {
        // Get the mAuctionItem
        restMAuctionItemMockMvc.perform(get("/api/m-auction-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionItem() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        int databaseSizeBeforeUpdate = mAuctionItemRepository.findAll().size();

        // Update the mAuctionItem
        MAuctionItem updatedMAuctionItem = mAuctionItemRepository.findById(mAuctionItem.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionItem are not directly saved in db
        em.detach(updatedMAuctionItem);
        updatedMAuctionItem
            .auctionStatus(UPDATED_AUCTION_STATUS)
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .amount(UPDATED_AMOUNT)
            .bidDecrement(UPDATED_BID_DECREMENT)
            .protectBackBuffer(UPDATED_PROTECT_BACK_BUFFER)
            .protectFrontBuffer(UPDATED_PROTECT_FRONT_BUFFER)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionItemDTO mAuctionItemDTO = mAuctionItemMapper.toDto(updatedMAuctionItem);

        restMAuctionItemMockMvc.perform(put("/api/m-auction-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionItemDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionItem in the database
        List<MAuctionItem> mAuctionItemList = mAuctionItemRepository.findAll();
        assertThat(mAuctionItemList).hasSize(databaseSizeBeforeUpdate);
        MAuctionItem testMAuctionItem = mAuctionItemList.get(mAuctionItemList.size() - 1);
        assertThat(testMAuctionItem.getAuctionStatus()).isEqualTo(UPDATED_AUCTION_STATUS);
        assertThat(testMAuctionItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMAuctionItem.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMAuctionItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMAuctionItem.getBidDecrement()).isEqualTo(UPDATED_BID_DECREMENT);
        assertThat(testMAuctionItem.getProtectBackBuffer()).isEqualTo(UPDATED_PROTECT_BACK_BUFFER);
        assertThat(testMAuctionItem.getProtectFrontBuffer()).isEqualTo(UPDATED_PROTECT_FRONT_BUFFER);
        assertThat(testMAuctionItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionItem() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionItemRepository.findAll().size();

        // Create the MAuctionItem
        MAuctionItemDTO mAuctionItemDTO = mAuctionItemMapper.toDto(mAuctionItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionItemMockMvc.perform(put("/api/m-auction-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionItem in the database
        List<MAuctionItem> mAuctionItemList = mAuctionItemRepository.findAll();
        assertThat(mAuctionItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionItem() throws Exception {
        // Initialize the database
        mAuctionItemRepository.saveAndFlush(mAuctionItem);

        int databaseSizeBeforeDelete = mAuctionItemRepository.findAll().size();

        // Delete the mAuctionItem
        restMAuctionItemMockMvc.perform(delete("/api/m-auction-items/{id}", mAuctionItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionItem> mAuctionItemList = mAuctionItemRepository.findAll();
        assertThat(mAuctionItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
