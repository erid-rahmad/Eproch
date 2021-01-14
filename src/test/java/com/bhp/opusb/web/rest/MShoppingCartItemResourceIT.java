package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MShoppingCartItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.domain.MShoppingCart;
import com.bhp.opusb.repository.MShoppingCartItemRepository;
import com.bhp.opusb.service.MShoppingCartItemService;
import com.bhp.opusb.service.dto.MShoppingCartItemDTO;
import com.bhp.opusb.service.mapper.MShoppingCartItemMapper;
import com.bhp.opusb.service.dto.MShoppingCartItemCriteria;
import com.bhp.opusb.service.MShoppingCartItemQueryService;

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
 * Integration tests for the {@link MShoppingCartItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MShoppingCartItemResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(0);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(0 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MShoppingCartItemRepository mShoppingCartItemRepository;

    @Autowired
    private MShoppingCartItemMapper mShoppingCartItemMapper;

    @Autowired
    private MShoppingCartItemService mShoppingCartItemService;

    @Autowired
    private MShoppingCartItemQueryService mShoppingCartItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMShoppingCartItemMockMvc;

    private MShoppingCartItem mShoppingCartItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MShoppingCartItem createEntity(EntityManager em) {
        MShoppingCartItem mShoppingCartItem = new MShoppingCartItem()
            .quantity(DEFAULT_QUANTITY)
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
        mShoppingCartItem.setAdOrganization(aDOrganization);
        // Add required entity
        MProductCatalog mProductCatalog;
        if (TestUtil.findAll(em, MProductCatalog.class).isEmpty()) {
            mProductCatalog = MProductCatalogResourceIT.createEntity(em);
            em.persist(mProductCatalog);
            em.flush();
        } else {
            mProductCatalog = TestUtil.findAll(em, MProductCatalog.class).get(0);
        }
        mShoppingCartItem.setMProductCatalog(mProductCatalog);
        // Add required entity
        MShoppingCart mShoppingCart;
        if (TestUtil.findAll(em, MShoppingCart.class).isEmpty()) {
            mShoppingCart = MShoppingCartResourceIT.createEntity(em);
            em.persist(mShoppingCart);
            em.flush();
        } else {
            mShoppingCart = TestUtil.findAll(em, MShoppingCart.class).get(0);
        }
        mShoppingCartItem.setMShoppingCart(mShoppingCart);
        return mShoppingCartItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MShoppingCartItem createUpdatedEntity(EntityManager em) {
        MShoppingCartItem mShoppingCartItem = new MShoppingCartItem()
            .quantity(UPDATED_QUANTITY)
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
        mShoppingCartItem.setAdOrganization(aDOrganization);
        // Add required entity
        MProductCatalog mProductCatalog;
        if (TestUtil.findAll(em, MProductCatalog.class).isEmpty()) {
            mProductCatalog = MProductCatalogResourceIT.createUpdatedEntity(em);
            em.persist(mProductCatalog);
            em.flush();
        } else {
            mProductCatalog = TestUtil.findAll(em, MProductCatalog.class).get(0);
        }
        mShoppingCartItem.setMProductCatalog(mProductCatalog);
        // Add required entity
        MShoppingCart mShoppingCart;
        if (TestUtil.findAll(em, MShoppingCart.class).isEmpty()) {
            mShoppingCart = MShoppingCartResourceIT.createUpdatedEntity(em);
            em.persist(mShoppingCart);
            em.flush();
        } else {
            mShoppingCart = TestUtil.findAll(em, MShoppingCart.class).get(0);
        }
        mShoppingCartItem.setMShoppingCart(mShoppingCart);
        return mShoppingCartItem;
    }

    @BeforeEach
    public void initTest() {
        mShoppingCartItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMShoppingCartItem() throws Exception {
        int databaseSizeBeforeCreate = mShoppingCartItemRepository.findAll().size();

        // Create the MShoppingCartItem
        MShoppingCartItemDTO mShoppingCartItemDTO = mShoppingCartItemMapper.toDto(mShoppingCartItem);
        restMShoppingCartItemMockMvc.perform(post("/api/m-shopping-cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MShoppingCartItem in the database
        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeCreate + 1);
        MShoppingCartItem testMShoppingCartItem = mShoppingCartItemList.get(mShoppingCartItemList.size() - 1);
        assertThat(testMShoppingCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMShoppingCartItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMShoppingCartItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMShoppingCartItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mShoppingCartItemRepository.findAll().size();

        // Create the MShoppingCartItem with an existing ID
        mShoppingCartItem.setId(1L);
        MShoppingCartItemDTO mShoppingCartItemDTO = mShoppingCartItemMapper.toDto(mShoppingCartItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMShoppingCartItemMockMvc.perform(post("/api/m-shopping-cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MShoppingCartItem in the database
        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mShoppingCartItemRepository.findAll().size();
        // set the field null
        mShoppingCartItem.setQuantity(null);

        // Create the MShoppingCartItem, which fails.
        MShoppingCartItemDTO mShoppingCartItemDTO = mShoppingCartItemMapper.toDto(mShoppingCartItem);

        restMShoppingCartItemMockMvc.perform(post("/api/m-shopping-cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartItemDTO)))
            .andExpect(status().isBadRequest());

        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItems() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mShoppingCartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMShoppingCartItem() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get the mShoppingCartItem
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items/{id}", mShoppingCartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mShoppingCartItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMShoppingCartItemsByIdFiltering() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        Long id = mShoppingCartItem.getId();

        defaultMShoppingCartItemShouldBeFound("id.equals=" + id);
        defaultMShoppingCartItemShouldNotBeFound("id.notEquals=" + id);

        defaultMShoppingCartItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMShoppingCartItemShouldNotBeFound("id.greaterThan=" + id);

        defaultMShoppingCartItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMShoppingCartItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity equals to DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity equals to UPDATED_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity not equals to DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity not equals to UPDATED_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mShoppingCartItemList where quantity equals to UPDATED_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity is not null
        defaultMShoppingCartItemShouldBeFound("quantity.specified=true");

        // Get all the mShoppingCartItemList where quantity is null
        defaultMShoppingCartItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity is less than DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity is less than UPDATED_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where quantity is greater than DEFAULT_QUANTITY
        defaultMShoppingCartItemShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mShoppingCartItemList where quantity is greater than SMALLER_QUANTITY
        defaultMShoppingCartItemShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMShoppingCartItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where uid equals to DEFAULT_UID
        defaultMShoppingCartItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mShoppingCartItemList where uid equals to UPDATED_UID
        defaultMShoppingCartItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where uid not equals to DEFAULT_UID
        defaultMShoppingCartItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mShoppingCartItemList where uid not equals to UPDATED_UID
        defaultMShoppingCartItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultMShoppingCartItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mShoppingCartItemList where uid equals to UPDATED_UID
        defaultMShoppingCartItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where uid is not null
        defaultMShoppingCartItemShouldBeFound("uid.specified=true");

        // Get all the mShoppingCartItemList where uid is null
        defaultMShoppingCartItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where active equals to DEFAULT_ACTIVE
        defaultMShoppingCartItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mShoppingCartItemList where active equals to UPDATED_ACTIVE
        defaultMShoppingCartItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where active not equals to DEFAULT_ACTIVE
        defaultMShoppingCartItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mShoppingCartItemList where active not equals to UPDATED_ACTIVE
        defaultMShoppingCartItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMShoppingCartItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mShoppingCartItemList where active equals to UPDATED_ACTIVE
        defaultMShoppingCartItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        // Get all the mShoppingCartItemList where active is not null
        defaultMShoppingCartItemShouldBeFound("active.specified=true");

        // Get all the mShoppingCartItemList where active is null
        defaultMShoppingCartItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMShoppingCartItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mShoppingCartItem.getAdOrganization();
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mShoppingCartItemList where adOrganization equals to adOrganizationId
        defaultMShoppingCartItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mShoppingCartItemList where adOrganization equals to adOrganizationId + 1
        defaultMShoppingCartItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMShoppingCartItemsByMProductCatalogIsEqualToSomething() throws Exception {
        // Get already existing entity
        MProductCatalog mProductCatalog = mShoppingCartItem.getMProductCatalog();
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);
        Long mProductCatalogId = mProductCatalog.getId();

        // Get all the mShoppingCartItemList where mProductCatalog equals to mProductCatalogId
        defaultMShoppingCartItemShouldBeFound("mProductCatalogId.equals=" + mProductCatalogId);

        // Get all the mShoppingCartItemList where mProductCatalog equals to mProductCatalogId + 1
        defaultMShoppingCartItemShouldNotBeFound("mProductCatalogId.equals=" + (mProductCatalogId + 1));
    }


    @Test
    @Transactional
    public void getAllMShoppingCartItemsByMShoppingCartIsEqualToSomething() throws Exception {
        // Get already existing entity
        MShoppingCart mShoppingCart = mShoppingCartItem.getMShoppingCart();
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);
        Long mShoppingCartId = mShoppingCart.getId();

        // Get all the mShoppingCartItemList where mShoppingCart equals to mShoppingCartId
        defaultMShoppingCartItemShouldBeFound("mShoppingCartId.equals=" + mShoppingCartId);

        // Get all the mShoppingCartItemList where mShoppingCart equals to mShoppingCartId + 1
        defaultMShoppingCartItemShouldNotBeFound("mShoppingCartId.equals=" + (mShoppingCartId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMShoppingCartItemShouldBeFound(String filter) throws Exception {
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mShoppingCartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMShoppingCartItemShouldNotBeFound(String filter) throws Exception {
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMShoppingCartItem() throws Exception {
        // Get the mShoppingCartItem
        restMShoppingCartItemMockMvc.perform(get("/api/m-shopping-cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMShoppingCartItem() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        int databaseSizeBeforeUpdate = mShoppingCartItemRepository.findAll().size();

        // Update the mShoppingCartItem
        MShoppingCartItem updatedMShoppingCartItem = mShoppingCartItemRepository.findById(mShoppingCartItem.getId()).get();
        // Disconnect from session so that the updates on updatedMShoppingCartItem are not directly saved in db
        em.detach(updatedMShoppingCartItem);
        updatedMShoppingCartItem
            .quantity(UPDATED_QUANTITY)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MShoppingCartItemDTO mShoppingCartItemDTO = mShoppingCartItemMapper.toDto(updatedMShoppingCartItem);

        restMShoppingCartItemMockMvc.perform(put("/api/m-shopping-cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartItemDTO)))
            .andExpect(status().isOk());

        // Validate the MShoppingCartItem in the database
        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeUpdate);
        MShoppingCartItem testMShoppingCartItem = mShoppingCartItemList.get(mShoppingCartItemList.size() - 1);
        assertThat(testMShoppingCartItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMShoppingCartItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMShoppingCartItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMShoppingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = mShoppingCartItemRepository.findAll().size();

        // Create the MShoppingCartItem
        MShoppingCartItemDTO mShoppingCartItemDTO = mShoppingCartItemMapper.toDto(mShoppingCartItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMShoppingCartItemMockMvc.perform(put("/api/m-shopping-cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MShoppingCartItem in the database
        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMShoppingCartItem() throws Exception {
        // Initialize the database
        mShoppingCartItemRepository.saveAndFlush(mShoppingCartItem);

        int databaseSizeBeforeDelete = mShoppingCartItemRepository.findAll().size();

        // Delete the mShoppingCartItem
        restMShoppingCartItemMockMvc.perform(delete("/api/m-shopping-cart-items/{id}", mShoppingCartItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MShoppingCartItem> mShoppingCartItemList = mShoppingCartItemRepository.findAll();
        assertThat(mShoppingCartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
