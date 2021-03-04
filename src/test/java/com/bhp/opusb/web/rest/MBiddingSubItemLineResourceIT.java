package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.MBiddingSubItemLineRepository;
import com.bhp.opusb.service.MBiddingSubItemLineService;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemLineMapper;
import com.bhp.opusb.service.dto.MBiddingSubItemLineCriteria;
import com.bhp.opusb.service.MBiddingSubItemLineQueryService;

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
 * Integration tests for the {@link MBiddingSubItemLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingSubItemLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingSubItemLineRepository mBiddingSubItemLineRepository;

    @Autowired
    private MBiddingSubItemLineMapper mBiddingSubItemLineMapper;

    @Autowired
    private MBiddingSubItemLineService mBiddingSubItemLineService;

    @Autowired
    private MBiddingSubItemLineQueryService mBiddingSubItemLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingSubItemLineMockMvc;

    private MBiddingSubItemLine mBiddingSubItemLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubItemLine createEntity(EntityManager em) {
        MBiddingSubItemLine mBiddingSubItemLine = new MBiddingSubItemLine()
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .amount(DEFAULT_AMOUNT)
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
        mBiddingSubItemLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubItem mBiddingSubItem;
        if (TestUtil.findAll(em, MBiddingSubItem.class).isEmpty()) {
            mBiddingSubItem = MBiddingSubItemResourceIT.createEntity(em);
            em.persist(mBiddingSubItem);
            em.flush();
        } else {
            mBiddingSubItem = TestUtil.findAll(em, MBiddingSubItem.class).get(0);
        }
        mBiddingSubItemLine.setBiddingSubItem(mBiddingSubItem);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingSubItemLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mBiddingSubItemLine.setUom(cUnitOfMeasure);
        return mBiddingSubItemLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubItemLine createUpdatedEntity(EntityManager em) {
        MBiddingSubItemLine mBiddingSubItemLine = new MBiddingSubItemLine()
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .amount(UPDATED_AMOUNT)
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
        mBiddingSubItemLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubItem mBiddingSubItem;
        if (TestUtil.findAll(em, MBiddingSubItem.class).isEmpty()) {
            mBiddingSubItem = MBiddingSubItemResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubItem);
            em.flush();
        } else {
            mBiddingSubItem = TestUtil.findAll(em, MBiddingSubItem.class).get(0);
        }
        mBiddingSubItemLine.setBiddingSubItem(mBiddingSubItem);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingSubItemLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mBiddingSubItemLine.setUom(cUnitOfMeasure);
        return mBiddingSubItemLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSubItemLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSubItemLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubItemLineRepository.findAll().size();

        // Create the MBiddingSubItemLine
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);
        restMBiddingSubItemLineMockMvc.perform(post("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSubItemLine in the database
        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSubItemLine testMBiddingSubItemLine = mBiddingSubItemLineList.get(mBiddingSubItemLineList.size() - 1);
        assertThat(testMBiddingSubItemLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMBiddingSubItemLine.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMBiddingSubItemLine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMBiddingSubItemLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSubItemLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingSubItemLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubItemLineRepository.findAll().size();

        // Create the MBiddingSubItemLine with an existing ID
        mBiddingSubItemLine.setId(1L);
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingSubItemLineMockMvc.perform(post("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubItemLine in the database
        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubItemLineRepository.findAll().size();
        // set the field null
        mBiddingSubItemLine.setQuantity(null);

        // Create the MBiddingSubItemLine, which fails.
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);

        restMBiddingSubItemLineMockMvc.perform(post("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubItemLineRepository.findAll().size();
        // set the field null
        mBiddingSubItemLine.setPrice(null);

        // Create the MBiddingSubItemLine, which fails.
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);

        restMBiddingSubItemLineMockMvc.perform(post("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLines() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubItemLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSubItemLine() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get the mBiddingSubItemLine
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines/{id}", mBiddingSubItemLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSubItemLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSubItemLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        Long id = mBiddingSubItemLine.getId();

        defaultMBiddingSubItemLineShouldBeFound("id.equals=" + id);
        defaultMBiddingSubItemLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingSubItemLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingSubItemLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingSubItemLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingSubItemLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity equals to DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity equals to UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity not equals to DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity not equals to UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity equals to UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity is not null
        defaultMBiddingSubItemLineShouldBeFound("quantity.specified=true");

        // Get all the mBiddingSubItemLineList where quantity is null
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity is less than DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity is less than UPDATED_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where quantity is greater than DEFAULT_QUANTITY
        defaultMBiddingSubItemLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mBiddingSubItemLineList where quantity is greater than SMALLER_QUANTITY
        defaultMBiddingSubItemLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price equals to DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price equals to UPDATED_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price not equals to DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price not equals to UPDATED_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mBiddingSubItemLineList where price equals to UPDATED_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price is not null
        defaultMBiddingSubItemLineShouldBeFound("price.specified=true");

        // Get all the mBiddingSubItemLineList where price is null
        defaultMBiddingSubItemLineShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price is greater than or equal to DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price is greater than or equal to UPDATED_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price is less than or equal to DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price is less than or equal to SMALLER_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price is less than DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price is less than UPDATED_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where price is greater than DEFAULT_PRICE
        defaultMBiddingSubItemLineShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mBiddingSubItemLineList where price is greater than SMALLER_PRICE
        defaultMBiddingSubItemLineShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount equals to DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount equals to UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount not equals to DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount not equals to UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount equals to UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount is not null
        defaultMBiddingSubItemLineShouldBeFound("amount.specified=true");

        // Get all the mBiddingSubItemLineList where amount is null
        defaultMBiddingSubItemLineShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount is greater than or equal to UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount is less than or equal to DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount is less than or equal to SMALLER_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount is less than DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount is less than UPDATED_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where amount is greater than DEFAULT_AMOUNT
        defaultMBiddingSubItemLineShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the mBiddingSubItemLineList where amount is greater than SMALLER_AMOUNT
        defaultMBiddingSubItemLineShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where uid equals to DEFAULT_UID
        defaultMBiddingSubItemLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingSubItemLineList where uid equals to UPDATED_UID
        defaultMBiddingSubItemLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where uid not equals to DEFAULT_UID
        defaultMBiddingSubItemLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingSubItemLineList where uid not equals to UPDATED_UID
        defaultMBiddingSubItemLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingSubItemLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingSubItemLineList where uid equals to UPDATED_UID
        defaultMBiddingSubItemLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where uid is not null
        defaultMBiddingSubItemLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingSubItemLineList where uid is null
        defaultMBiddingSubItemLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingSubItemLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubItemLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubItemLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingSubItemLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubItemLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingSubItemLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingSubItemLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingSubItemLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubItemLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        // Get all the mBiddingSubItemLineList where active is not null
        defaultMBiddingSubItemLineShouldBeFound("active.specified=true");

        // Get all the mBiddingSubItemLineList where active is null
        defaultMBiddingSubItemLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSubItemLine.getAdOrganization();
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingSubItemLineList where adOrganization equals to adOrganizationId
        defaultMBiddingSubItemLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingSubItemLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingSubItemLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByBiddingSubItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubItem biddingSubItem = mBiddingSubItemLine.getBiddingSubItem();
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);
        Long biddingSubItemId = biddingSubItem.getId();

        // Get all the mBiddingSubItemLineList where biddingSubItem equals to biddingSubItemId
        defaultMBiddingSubItemLineShouldBeFound("biddingSubItemId.equals=" + biddingSubItemId);

        // Get all the mBiddingSubItemLineList where biddingSubItem equals to biddingSubItemId + 1
        defaultMBiddingSubItemLineShouldNotBeFound("biddingSubItemId.equals=" + (biddingSubItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mBiddingSubItemLine.getProduct();
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);
        Long productId = product.getId();

        // Get all the mBiddingSubItemLineList where product equals to productId
        defaultMBiddingSubItemLineShouldBeFound("productId.equals=" + productId);

        // Get all the mBiddingSubItemLineList where product equals to productId + 1
        defaultMBiddingSubItemLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubItemLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mBiddingSubItemLine.getUom();
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);
        Long uomId = uom.getId();

        // Get all the mBiddingSubItemLineList where uom equals to uomId
        defaultMBiddingSubItemLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mBiddingSubItemLineList where uom equals to uomId + 1
        defaultMBiddingSubItemLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingSubItemLineShouldBeFound(String filter) throws Exception {
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubItemLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingSubItemLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingSubItemLine() throws Exception {
        // Get the mBiddingSubItemLine
        restMBiddingSubItemLineMockMvc.perform(get("/api/m-bidding-sub-item-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSubItemLine() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        int databaseSizeBeforeUpdate = mBiddingSubItemLineRepository.findAll().size();

        // Update the mBiddingSubItemLine
        MBiddingSubItemLine updatedMBiddingSubItemLine = mBiddingSubItemLineRepository.findById(mBiddingSubItemLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSubItemLine are not directly saved in db
        em.detach(updatedMBiddingSubItemLine);
        updatedMBiddingSubItemLine
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .amount(UPDATED_AMOUNT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(updatedMBiddingSubItemLine);

        restMBiddingSubItemLineMockMvc.perform(put("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSubItemLine in the database
        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSubItemLine testMBiddingSubItemLine = mBiddingSubItemLineList.get(mBiddingSubItemLineList.size() - 1);
        assertThat(testMBiddingSubItemLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMBiddingSubItemLine.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMBiddingSubItemLine.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMBiddingSubItemLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSubItemLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSubItemLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingSubItemLineRepository.findAll().size();

        // Create the MBiddingSubItemLine
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingSubItemLineMockMvc.perform(put("/api/m-bidding-sub-item-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubItemLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubItemLine in the database
        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSubItemLine() throws Exception {
        // Initialize the database
        mBiddingSubItemLineRepository.saveAndFlush(mBiddingSubItemLine);

        int databaseSizeBeforeDelete = mBiddingSubItemLineRepository.findAll().size();

        // Delete the mBiddingSubItemLine
        restMBiddingSubItemLineMockMvc.perform(delete("/api/m-bidding-sub-item-lines/{id}", mBiddingSubItemLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSubItemLine> mBiddingSubItemLineList = mBiddingSubItemLineRepository.findAll();
        assertThat(mBiddingSubItemLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
