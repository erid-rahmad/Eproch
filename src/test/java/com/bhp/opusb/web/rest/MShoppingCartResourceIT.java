package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MShoppingCart;
import com.bhp.opusb.domain.MShoppingCartItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MShoppingCartRepository;
import com.bhp.opusb.service.MShoppingCartService;
import com.bhp.opusb.service.dto.MShoppingCartDTO;
import com.bhp.opusb.service.mapper.MShoppingCartMapper;
import com.bhp.opusb.service.dto.MShoppingCartCriteria;
import com.bhp.opusb.service.MShoppingCartQueryService;

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
 * Integration tests for the {@link MShoppingCartResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MShoppingCartResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MShoppingCartRepository mShoppingCartRepository;

    @Autowired
    private MShoppingCartMapper mShoppingCartMapper;

    @Autowired
    private MShoppingCartService mShoppingCartService;

    @Autowired
    private MShoppingCartQueryService mShoppingCartQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMShoppingCartMockMvc;

    private MShoppingCart mShoppingCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MShoppingCart createEntity(EntityManager em) {
        MShoppingCart mShoppingCart = new MShoppingCart()
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
        mShoppingCart.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mShoppingCart.setAdUser(adUser);
        return mShoppingCart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MShoppingCart createUpdatedEntity(EntityManager em) {
        MShoppingCart mShoppingCart = new MShoppingCart()
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
        mShoppingCart.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mShoppingCart.setAdUser(adUser);
        return mShoppingCart;
    }

    @BeforeEach
    public void initTest() {
        mShoppingCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createMShoppingCart() throws Exception {
        int databaseSizeBeforeCreate = mShoppingCartRepository.findAll().size();
        // Create the MShoppingCart
        MShoppingCartDTO mShoppingCartDTO = mShoppingCartMapper.toDto(mShoppingCart);
        restMShoppingCartMockMvc.perform(post("/api/m-shopping-carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartDTO)))
            .andExpect(status().isCreated());

        // Validate the MShoppingCart in the database
        List<MShoppingCart> mShoppingCartList = mShoppingCartRepository.findAll();
        assertThat(mShoppingCartList).hasSize(databaseSizeBeforeCreate + 1);
        MShoppingCart testMShoppingCart = mShoppingCartList.get(mShoppingCartList.size() - 1);
        assertThat(testMShoppingCart.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMShoppingCart.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMShoppingCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mShoppingCartRepository.findAll().size();

        // Create the MShoppingCart with an existing ID
        mShoppingCart.setId(1L);
        MShoppingCartDTO mShoppingCartDTO = mShoppingCartMapper.toDto(mShoppingCart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMShoppingCartMockMvc.perform(post("/api/m-shopping-carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MShoppingCart in the database
        List<MShoppingCart> mShoppingCartList = mShoppingCartRepository.findAll();
        assertThat(mShoppingCartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMShoppingCarts() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mShoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMShoppingCart() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get the mShoppingCart
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts/{id}", mShoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mShoppingCart.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMShoppingCartsByIdFiltering() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        Long id = mShoppingCart.getId();

        defaultMShoppingCartShouldBeFound("id.equals=" + id);
        defaultMShoppingCartShouldNotBeFound("id.notEquals=" + id);

        defaultMShoppingCartShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMShoppingCartShouldNotBeFound("id.greaterThan=" + id);

        defaultMShoppingCartShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMShoppingCartShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMShoppingCartsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where uid equals to DEFAULT_UID
        defaultMShoppingCartShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mShoppingCartList where uid equals to UPDATED_UID
        defaultMShoppingCartShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where uid not equals to DEFAULT_UID
        defaultMShoppingCartShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mShoppingCartList where uid not equals to UPDATED_UID
        defaultMShoppingCartShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where uid in DEFAULT_UID or UPDATED_UID
        defaultMShoppingCartShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mShoppingCartList where uid equals to UPDATED_UID
        defaultMShoppingCartShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where uid is not null
        defaultMShoppingCartShouldBeFound("uid.specified=true");

        // Get all the mShoppingCartList where uid is null
        defaultMShoppingCartShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where active equals to DEFAULT_ACTIVE
        defaultMShoppingCartShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mShoppingCartList where active equals to UPDATED_ACTIVE
        defaultMShoppingCartShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where active not equals to DEFAULT_ACTIVE
        defaultMShoppingCartShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mShoppingCartList where active not equals to UPDATED_ACTIVE
        defaultMShoppingCartShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMShoppingCartShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mShoppingCartList where active equals to UPDATED_ACTIVE
        defaultMShoppingCartShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        // Get all the mShoppingCartList where active is not null
        defaultMShoppingCartShouldBeFound("active.specified=true");

        // Get all the mShoppingCartList where active is null
        defaultMShoppingCartShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMShoppingCartsByMShoppingCartItemIsEqualToSomething() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);
        MShoppingCartItem mShoppingCartItem = MShoppingCartItemResourceIT.createEntity(em);
        em.persist(mShoppingCartItem);
        em.flush();
        mShoppingCart.addMShoppingCartItem(mShoppingCartItem);
        mShoppingCartRepository.saveAndFlush(mShoppingCart);
        Long mShoppingCartItemId = mShoppingCartItem.getId();

        // Get all the mShoppingCartList where mShoppingCartItem equals to mShoppingCartItemId
        defaultMShoppingCartShouldBeFound("mShoppingCartItemId.equals=" + mShoppingCartItemId);

        // Get all the mShoppingCartList where mShoppingCartItem equals to mShoppingCartItemId + 1
        defaultMShoppingCartShouldNotBeFound("mShoppingCartItemId.equals=" + (mShoppingCartItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMShoppingCartsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mShoppingCart.getAdOrganization();
        mShoppingCartRepository.saveAndFlush(mShoppingCart);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mShoppingCartList where adOrganization equals to adOrganizationId
        defaultMShoppingCartShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mShoppingCartList where adOrganization equals to adOrganizationId + 1
        defaultMShoppingCartShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMShoppingCartsByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = mShoppingCart.getAdUser();
        mShoppingCartRepository.saveAndFlush(mShoppingCart);
        Long adUserId = adUser.getId();

        // Get all the mShoppingCartList where adUser equals to adUserId
        defaultMShoppingCartShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the mShoppingCartList where adUser equals to adUserId + 1
        defaultMShoppingCartShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMShoppingCartShouldBeFound(String filter) throws Exception {
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mShoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMShoppingCartShouldNotBeFound(String filter) throws Exception {
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMShoppingCart() throws Exception {
        // Get the mShoppingCart
        restMShoppingCartMockMvc.perform(get("/api/m-shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMShoppingCart() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        int databaseSizeBeforeUpdate = mShoppingCartRepository.findAll().size();

        // Update the mShoppingCart
        MShoppingCart updatedMShoppingCart = mShoppingCartRepository.findById(mShoppingCart.getId()).get();
        // Disconnect from session so that the updates on updatedMShoppingCart are not directly saved in db
        em.detach(updatedMShoppingCart);
        updatedMShoppingCart
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MShoppingCartDTO mShoppingCartDTO = mShoppingCartMapper.toDto(updatedMShoppingCart);

        restMShoppingCartMockMvc.perform(put("/api/m-shopping-carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartDTO)))
            .andExpect(status().isOk());

        // Validate the MShoppingCart in the database
        List<MShoppingCart> mShoppingCartList = mShoppingCartRepository.findAll();
        assertThat(mShoppingCartList).hasSize(databaseSizeBeforeUpdate);
        MShoppingCart testMShoppingCart = mShoppingCartList.get(mShoppingCartList.size() - 1);
        assertThat(testMShoppingCart.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMShoppingCart.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = mShoppingCartRepository.findAll().size();

        // Create the MShoppingCart
        MShoppingCartDTO mShoppingCartDTO = mShoppingCartMapper.toDto(mShoppingCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMShoppingCartMockMvc.perform(put("/api/m-shopping-carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mShoppingCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MShoppingCart in the database
        List<MShoppingCart> mShoppingCartList = mShoppingCartRepository.findAll();
        assertThat(mShoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMShoppingCart() throws Exception {
        // Initialize the database
        mShoppingCartRepository.saveAndFlush(mShoppingCart);

        int databaseSizeBeforeDelete = mShoppingCartRepository.findAll().size();

        // Delete the mShoppingCart
        restMShoppingCartMockMvc.perform(delete("/api/m-shopping-carts/{id}", mShoppingCart.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MShoppingCart> mShoppingCartList = mShoppingCartRepository.findAll();
        assertThat(mShoppingCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
