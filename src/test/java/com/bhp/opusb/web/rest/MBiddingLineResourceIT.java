package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.service.MBiddingLineService;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.mapper.MBiddingLineMapper;
import com.bhp.opusb.service.dto.MBiddingLineCriteria;
import com.bhp.opusb.service.MBiddingLineQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MBiddingLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CEILING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CEILING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CEILING_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_CEILING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CEILING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_CEILING_PRICE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingLineRepository mBiddingLineRepository;

    @Autowired
    private MBiddingLineMapper mBiddingLineMapper;

    @Autowired
    private MBiddingLineService mBiddingLineService;

    @Autowired
    private MBiddingLineQueryService mBiddingLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingLineMockMvc;

    private MBiddingLine mBiddingLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingLine createEntity(EntityManager em) {
        MBiddingLine mBiddingLine = new MBiddingLine()
            .quantity(DEFAULT_QUANTITY)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
            .totalCeilingPrice(DEFAULT_TOTAL_CEILING_PRICE)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .remark(DEFAULT_REMARK)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingLine.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mBiddingLine.setCostCenter(cCostCenter);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mBiddingLine.setUom(cUnitOfMeasure);
        return mBiddingLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingLine createUpdatedEntity(EntityManager em) {
        MBiddingLine mBiddingLine = new MBiddingLine()
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .totalCeilingPrice(UPDATED_TOTAL_CEILING_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBiddingLine.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mBiddingLine.setCostCenter(cCostCenter);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mBiddingLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mBiddingLine.setUom(cUnitOfMeasure);
        return mBiddingLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingLineRepository.findAll().size();

        // Create the MBiddingLine
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);
        restMBiddingLineMockMvc.perform(post("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingLine in the database
        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingLine testMBiddingLine = mBiddingLineList.get(mBiddingLineList.size() - 1);
        assertThat(testMBiddingLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMBiddingLine.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMBiddingLine.getTotalCeilingPrice()).isEqualTo(DEFAULT_TOTAL_CEILING_PRICE);
        assertThat(testMBiddingLine.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testMBiddingLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMBiddingLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingLineRepository.findAll().size();

        // Create the MBiddingLine with an existing ID
        mBiddingLine.setId(1L);
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingLineMockMvc.perform(post("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingLine in the database
        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingLineRepository.findAll().size();
        // set the field null
        mBiddingLine.setQuantity(null);

        // Create the MBiddingLine, which fails.
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);

        restMBiddingLineMockMvc.perform(post("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCeilingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingLineRepository.findAll().size();
        // set the field null
        mBiddingLine.setCeilingPrice(null);

        // Create the MBiddingLine, which fails.
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);

        restMBiddingLineMockMvc.perform(post("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingLineRepository.findAll().size();
        // set the field null
        mBiddingLine.setDeliveryDate(null);

        // Create the MBiddingLine, which fails.
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);

        restMBiddingLineMockMvc.perform(post("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingLines() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalCeilingPrice").value(hasItem(DEFAULT_TOTAL_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingLine() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get the mBiddingLine
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines/{id}", mBiddingLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.ceilingPrice").value(DEFAULT_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.totalCeilingPrice").value(DEFAULT_TOTAL_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        Long id = mBiddingLine.getId();

        defaultMBiddingLineShouldBeFound("id.equals=" + id);
        defaultMBiddingLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity equals to DEFAULT_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity equals to UPDATED_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity not equals to DEFAULT_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity not equals to UPDATED_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mBiddingLineList where quantity equals to UPDATED_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity is not null
        defaultMBiddingLineShouldBeFound("quantity.specified=true");

        // Get all the mBiddingLineList where quantity is null
        defaultMBiddingLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity is less than DEFAULT_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity is less than UPDATED_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where quantity is greater than DEFAULT_QUANTITY
        defaultMBiddingLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mBiddingLineList where quantity is greater than SMALLER_QUANTITY
        defaultMBiddingLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice is not null
        defaultMBiddingLineShouldBeFound("ceilingPrice.specified=true");

        // Get all the mBiddingLineList where ceilingPrice is null
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingLineList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice equals to DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.equals=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.equals=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice not equals to DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.notEquals=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice not equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.notEquals=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice in DEFAULT_TOTAL_CEILING_PRICE or UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.in=" + DEFAULT_TOTAL_CEILING_PRICE + "," + UPDATED_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.in=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice is not null
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.specified=true");

        // Get all the mBiddingLineList where totalCeilingPrice is null
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice is greater than or equal to DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice is greater than or equal to UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.greaterThanOrEqual=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice is less than or equal to DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.lessThanOrEqual=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice is less than or equal to SMALLER_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.lessThanOrEqual=" + SMALLER_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice is less than DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.lessThan=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice is less than UPDATED_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.lessThan=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByTotalCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where totalCeilingPrice is greater than DEFAULT_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldNotBeFound("totalCeilingPrice.greaterThan=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mBiddingLineList where totalCeilingPrice is greater than SMALLER_TOTAL_CEILING_PRICE
        defaultMBiddingLineShouldBeFound("totalCeilingPrice.greaterThan=" + SMALLER_TOTAL_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate is not null
        defaultMBiddingLineShouldBeFound("deliveryDate.specified=true");

        // Get all the mBiddingLineList where deliveryDate is null
        defaultMBiddingLineShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultMBiddingLineShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingLineList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultMBiddingLineShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark equals to DEFAULT_REMARK
        defaultMBiddingLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mBiddingLineList where remark equals to UPDATED_REMARK
        defaultMBiddingLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark not equals to DEFAULT_REMARK
        defaultMBiddingLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mBiddingLineList where remark not equals to UPDATED_REMARK
        defaultMBiddingLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMBiddingLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mBiddingLineList where remark equals to UPDATED_REMARK
        defaultMBiddingLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark is not null
        defaultMBiddingLineShouldBeFound("remark.specified=true");

        // Get all the mBiddingLineList where remark is null
        defaultMBiddingLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark contains DEFAULT_REMARK
        defaultMBiddingLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mBiddingLineList where remark contains UPDATED_REMARK
        defaultMBiddingLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where remark does not contain DEFAULT_REMARK
        defaultMBiddingLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mBiddingLineList where remark does not contain UPDATED_REMARK
        defaultMBiddingLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where uid equals to DEFAULT_UID
        defaultMBiddingLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingLineList where uid equals to UPDATED_UID
        defaultMBiddingLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where uid not equals to DEFAULT_UID
        defaultMBiddingLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingLineList where uid not equals to UPDATED_UID
        defaultMBiddingLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingLineList where uid equals to UPDATED_UID
        defaultMBiddingLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where uid is not null
        defaultMBiddingLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingLineList where uid is null
        defaultMBiddingLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        // Get all the mBiddingLineList where active is not null
        defaultMBiddingLineShouldBeFound("active.specified=true");

        // Get all the mBiddingLineList where active is null
        defaultMBiddingLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingLinesBySubItemIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        MBiddingSubItem subItem = MBiddingSubItemResourceIT.createEntity(em);
        em.persist(subItem);
        em.flush();
        mBiddingLine.setSubItem(subItem);
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long subItemId = subItem.getId();

        // Get all the mBiddingLineList where subItem equals to subItemId
        defaultMBiddingLineShouldBeFound("subItemId.equals=" + subItemId);

        // Get all the mBiddingLineList where subItem equals to subItemId + 1
        defaultMBiddingLineShouldNotBeFound("subItemId.equals=" + (subItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingLine.getBidding();
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long biddingId = bidding.getId();

        // Get all the mBiddingLineList where bidding equals to biddingId
        defaultMBiddingLineShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingLineList where bidding equals to biddingId + 1
        defaultMBiddingLineShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingLine.getAdOrganization();
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingLineList where adOrganization equals to adOrganizationId
        defaultMBiddingLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mBiddingLine.getCostCenter();
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long costCenterId = costCenter.getId();

        // Get all the mBiddingLineList where costCenter equals to costCenterId
        defaultMBiddingLineShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mBiddingLineList where costCenter equals to costCenterId + 1
        defaultMBiddingLineShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mBiddingLine.getProduct();
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long productId = product.getId();

        // Get all the mBiddingLineList where product equals to productId
        defaultMBiddingLineShouldBeFound("productId.equals=" + productId);

        // Get all the mBiddingLineList where product equals to productId + 1
        defaultMBiddingLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mBiddingLine.getUom();
        mBiddingLineRepository.saveAndFlush(mBiddingLine);
        Long uomId = uom.getId();

        // Get all the mBiddingLineList where uom equals to uomId
        defaultMBiddingLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mBiddingLineList where uom equals to uomId + 1
        defaultMBiddingLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingLineShouldBeFound(String filter) throws Exception {
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalCeilingPrice").value(hasItem(DEFAULT_TOTAL_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingLine() throws Exception {
        // Get the mBiddingLine
        restMBiddingLineMockMvc.perform(get("/api/m-bidding-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingLine() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        int databaseSizeBeforeUpdate = mBiddingLineRepository.findAll().size();

        // Update the mBiddingLine
        MBiddingLine updatedMBiddingLine = mBiddingLineRepository.findById(mBiddingLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingLine are not directly saved in db
        em.detach(updatedMBiddingLine);
        updatedMBiddingLine
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .totalCeilingPrice(UPDATED_TOTAL_CEILING_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(updatedMBiddingLine);

        restMBiddingLineMockMvc.perform(put("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingLine in the database
        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingLine testMBiddingLine = mBiddingLineList.get(mBiddingLineList.size() - 1);
        assertThat(testMBiddingLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMBiddingLine.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMBiddingLine.getTotalCeilingPrice()).isEqualTo(UPDATED_TOTAL_CEILING_PRICE);
        assertThat(testMBiddingLine.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testMBiddingLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMBiddingLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingLineRepository.findAll().size();

        // Create the MBiddingLine
        MBiddingLineDTO mBiddingLineDTO = mBiddingLineMapper.toDto(mBiddingLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingLineMockMvc.perform(put("/api/m-bidding-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingLine in the database
        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingLine() throws Exception {
        // Initialize the database
        mBiddingLineRepository.saveAndFlush(mBiddingLine);

        int databaseSizeBeforeDelete = mBiddingLineRepository.findAll().size();

        // Delete the mBiddingLine
        restMBiddingLineMockMvc.perform(delete("/api/m-bidding-lines/{id}", mBiddingLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingLine> mBiddingLineList = mBiddingLineRepository.findAll();
        assertThat(mBiddingLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
