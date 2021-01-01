package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProductPrice;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MProductPriceRepository;
import com.bhp.opusb.service.MProductPriceService;
import com.bhp.opusb.service.dto.MProductPriceDTO;
import com.bhp.opusb.service.mapper.MProductPriceMapper;
import com.bhp.opusb.service.dto.MProductPriceCriteria;
import com.bhp.opusb.service.MProductPriceQueryService;

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
 * Integration tests for the {@link MProductPriceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MProductPriceResourceIT {

    private static final BigDecimal DEFAULT_MIN_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MIN_QTY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MAX_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAX_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MAX_QTY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProductPriceRepository mProductPriceRepository;

    @Autowired
    private MProductPriceMapper mProductPriceMapper;

    @Autowired
    private MProductPriceService mProductPriceService;

    @Autowired
    private MProductPriceQueryService mProductPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProductPriceMockMvc;

    private MProductPrice mProductPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProductPrice createEntity(EntityManager em) {
        MProductPrice mProductPrice = new MProductPrice()
            .minQty(DEFAULT_MIN_QTY)
            .maxQty(DEFAULT_MAX_QTY)
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
        mProductPrice.setAdOrganization(aDOrganization);
        return mProductPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProductPrice createUpdatedEntity(EntityManager em) {
        MProductPrice mProductPrice = new MProductPrice()
            .minQty(UPDATED_MIN_QTY)
            .maxQty(UPDATED_MAX_QTY)
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
        mProductPrice.setAdOrganization(aDOrganization);
        return mProductPrice;
    }

    @BeforeEach
    public void initTest() {
        mProductPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProductPrice() throws Exception {
        int databaseSizeBeforeCreate = mProductPriceRepository.findAll().size();
        // Create the MProductPrice
        MProductPriceDTO mProductPriceDTO = mProductPriceMapper.toDto(mProductPrice);
        restMProductPriceMockMvc.perform(post("/api/m-product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the MProductPrice in the database
        List<MProductPrice> mProductPriceList = mProductPriceRepository.findAll();
        assertThat(mProductPriceList).hasSize(databaseSizeBeforeCreate + 1);
        MProductPrice testMProductPrice = mProductPriceList.get(mProductPriceList.size() - 1);
        assertThat(testMProductPrice.getMinQty()).isEqualTo(DEFAULT_MIN_QTY);
        assertThat(testMProductPrice.getMaxQty()).isEqualTo(DEFAULT_MAX_QTY);
        assertThat(testMProductPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMProductPrice.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProductPrice.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProductPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProductPriceRepository.findAll().size();

        // Create the MProductPrice with an existing ID
        mProductPrice.setId(1L);
        MProductPriceDTO mProductPriceDTO = mProductPriceMapper.toDto(mProductPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProductPriceMockMvc.perform(post("/api/m-product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProductPrice in the database
        List<MProductPrice> mProductPriceList = mProductPriceRepository.findAll();
        assertThat(mProductPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMProductPrices() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList
        restMProductPriceMockMvc.perform(get("/api/m-product-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProductPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].minQty").value(hasItem(DEFAULT_MIN_QTY.intValue())))
            .andExpect(jsonPath("$.[*].maxQty").value(hasItem(DEFAULT_MAX_QTY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProductPrice() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get the mProductPrice
        restMProductPriceMockMvc.perform(get("/api/m-product-prices/{id}", mProductPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProductPrice.getId().intValue()))
            .andExpect(jsonPath("$.minQty").value(DEFAULT_MIN_QTY.intValue()))
            .andExpect(jsonPath("$.maxQty").value(DEFAULT_MAX_QTY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProductPricesByIdFiltering() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        Long id = mProductPrice.getId();

        defaultMProductPriceShouldBeFound("id.equals=" + id);
        defaultMProductPriceShouldNotBeFound("id.notEquals=" + id);

        defaultMProductPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProductPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultMProductPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProductPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty equals to DEFAULT_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.equals=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty equals to UPDATED_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.equals=" + UPDATED_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty not equals to DEFAULT_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.notEquals=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty not equals to UPDATED_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.notEquals=" + UPDATED_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty in DEFAULT_MIN_QTY or UPDATED_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.in=" + DEFAULT_MIN_QTY + "," + UPDATED_MIN_QTY);

        // Get all the mProductPriceList where minQty equals to UPDATED_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.in=" + UPDATED_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty is not null
        defaultMProductPriceShouldBeFound("minQty.specified=true");

        // Get all the mProductPriceList where minQty is null
        defaultMProductPriceShouldNotBeFound("minQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty is greater than or equal to DEFAULT_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.greaterThanOrEqual=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty is greater than or equal to UPDATED_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.greaterThanOrEqual=" + UPDATED_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty is less than or equal to DEFAULT_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.lessThanOrEqual=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty is less than or equal to SMALLER_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.lessThanOrEqual=" + SMALLER_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty is less than DEFAULT_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.lessThan=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty is less than UPDATED_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.lessThan=" + UPDATED_MIN_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMinQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where minQty is greater than DEFAULT_MIN_QTY
        defaultMProductPriceShouldNotBeFound("minQty.greaterThan=" + DEFAULT_MIN_QTY);

        // Get all the mProductPriceList where minQty is greater than SMALLER_MIN_QTY
        defaultMProductPriceShouldBeFound("minQty.greaterThan=" + SMALLER_MIN_QTY);
    }


    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty equals to DEFAULT_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.equals=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty equals to UPDATED_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.equals=" + UPDATED_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty not equals to DEFAULT_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.notEquals=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty not equals to UPDATED_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.notEquals=" + UPDATED_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsInShouldWork() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty in DEFAULT_MAX_QTY or UPDATED_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.in=" + DEFAULT_MAX_QTY + "," + UPDATED_MAX_QTY);

        // Get all the mProductPriceList where maxQty equals to UPDATED_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.in=" + UPDATED_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty is not null
        defaultMProductPriceShouldBeFound("maxQty.specified=true");

        // Get all the mProductPriceList where maxQty is null
        defaultMProductPriceShouldNotBeFound("maxQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty is greater than or equal to DEFAULT_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.greaterThanOrEqual=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty is greater than or equal to UPDATED_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.greaterThanOrEqual=" + UPDATED_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty is less than or equal to DEFAULT_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.lessThanOrEqual=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty is less than or equal to SMALLER_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.lessThanOrEqual=" + SMALLER_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty is less than DEFAULT_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.lessThan=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty is less than UPDATED_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.lessThan=" + UPDATED_MAX_QTY);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByMaxQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where maxQty is greater than DEFAULT_MAX_QTY
        defaultMProductPriceShouldNotBeFound("maxQty.greaterThan=" + DEFAULT_MAX_QTY);

        // Get all the mProductPriceList where maxQty is greater than SMALLER_MAX_QTY
        defaultMProductPriceShouldBeFound("maxQty.greaterThan=" + SMALLER_MAX_QTY);
    }


    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price equals to DEFAULT_PRICE
        defaultMProductPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price equals to UPDATED_PRICE
        defaultMProductPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price not equals to DEFAULT_PRICE
        defaultMProductPriceShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price not equals to UPDATED_PRICE
        defaultMProductPriceShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMProductPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mProductPriceList where price equals to UPDATED_PRICE
        defaultMProductPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price is not null
        defaultMProductPriceShouldBeFound("price.specified=true");

        // Get all the mProductPriceList where price is null
        defaultMProductPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price is greater than or equal to DEFAULT_PRICE
        defaultMProductPriceShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price is greater than or equal to UPDATED_PRICE
        defaultMProductPriceShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price is less than or equal to DEFAULT_PRICE
        defaultMProductPriceShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price is less than or equal to SMALLER_PRICE
        defaultMProductPriceShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price is less than DEFAULT_PRICE
        defaultMProductPriceShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price is less than UPDATED_PRICE
        defaultMProductPriceShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where price is greater than DEFAULT_PRICE
        defaultMProductPriceShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mProductPriceList where price is greater than SMALLER_PRICE
        defaultMProductPriceShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProductPricesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where uid equals to DEFAULT_UID
        defaultMProductPriceShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProductPriceList where uid equals to UPDATED_UID
        defaultMProductPriceShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where uid not equals to DEFAULT_UID
        defaultMProductPriceShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProductPriceList where uid not equals to UPDATED_UID
        defaultMProductPriceShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProductPriceShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProductPriceList where uid equals to UPDATED_UID
        defaultMProductPriceShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where uid is not null
        defaultMProductPriceShouldBeFound("uid.specified=true");

        // Get all the mProductPriceList where uid is null
        defaultMProductPriceShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductPricesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where active equals to DEFAULT_ACTIVE
        defaultMProductPriceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProductPriceList where active equals to UPDATED_ACTIVE
        defaultMProductPriceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where active not equals to DEFAULT_ACTIVE
        defaultMProductPriceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProductPriceList where active not equals to UPDATED_ACTIVE
        defaultMProductPriceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProductPriceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProductPriceList where active equals to UPDATED_ACTIVE
        defaultMProductPriceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductPricesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        // Get all the mProductPriceList where active is not null
        defaultMProductPriceShouldBeFound("active.specified=true");

        // Get all the mProductPriceList where active is null
        defaultMProductPriceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductPricesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProductPrice.getAdOrganization();
        mProductPriceRepository.saveAndFlush(mProductPrice);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProductPriceList where adOrganization equals to adOrganizationId
        defaultMProductPriceShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProductPriceList where adOrganization equals to adOrganizationId + 1
        defaultMProductPriceShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProductPriceShouldBeFound(String filter) throws Exception {
        restMProductPriceMockMvc.perform(get("/api/m-product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProductPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].minQty").value(hasItem(DEFAULT_MIN_QTY.intValue())))
            .andExpect(jsonPath("$.[*].maxQty").value(hasItem(DEFAULT_MAX_QTY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProductPriceMockMvc.perform(get("/api/m-product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProductPriceShouldNotBeFound(String filter) throws Exception {
        restMProductPriceMockMvc.perform(get("/api/m-product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProductPriceMockMvc.perform(get("/api/m-product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMProductPrice() throws Exception {
        // Get the mProductPrice
        restMProductPriceMockMvc.perform(get("/api/m-product-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProductPrice() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        int databaseSizeBeforeUpdate = mProductPriceRepository.findAll().size();

        // Update the mProductPrice
        MProductPrice updatedMProductPrice = mProductPriceRepository.findById(mProductPrice.getId()).get();
        // Disconnect from session so that the updates on updatedMProductPrice are not directly saved in db
        em.detach(updatedMProductPrice);
        updatedMProductPrice
            .minQty(UPDATED_MIN_QTY)
            .maxQty(UPDATED_MAX_QTY)
            .price(UPDATED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProductPriceDTO mProductPriceDTO = mProductPriceMapper.toDto(updatedMProductPrice);

        restMProductPriceMockMvc.perform(put("/api/m-product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductPriceDTO)))
            .andExpect(status().isOk());

        // Validate the MProductPrice in the database
        List<MProductPrice> mProductPriceList = mProductPriceRepository.findAll();
        assertThat(mProductPriceList).hasSize(databaseSizeBeforeUpdate);
        MProductPrice testMProductPrice = mProductPriceList.get(mProductPriceList.size() - 1);
        assertThat(testMProductPrice.getMinQty()).isEqualTo(UPDATED_MIN_QTY);
        assertThat(testMProductPrice.getMaxQty()).isEqualTo(UPDATED_MAX_QTY);
        assertThat(testMProductPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMProductPrice.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProductPrice.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProductPrice() throws Exception {
        int databaseSizeBeforeUpdate = mProductPriceRepository.findAll().size();

        // Create the MProductPrice
        MProductPriceDTO mProductPriceDTO = mProductPriceMapper.toDto(mProductPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProductPriceMockMvc.perform(put("/api/m-product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProductPrice in the database
        List<MProductPrice> mProductPriceList = mProductPriceRepository.findAll();
        assertThat(mProductPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProductPrice() throws Exception {
        // Initialize the database
        mProductPriceRepository.saveAndFlush(mProductPrice);

        int databaseSizeBeforeDelete = mProductPriceRepository.findAll().size();

        // Delete the mProductPrice
        restMProductPriceMockMvc.perform(delete("/api/m-product-prices/{id}", mProductPrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProductPrice> mProductPriceList = mProductPriceRepository.findAll();
        assertThat(mProductPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
