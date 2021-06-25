package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractLine;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.MContractLineRepository;
import com.bhp.opusb.service.MContractLineService;
import com.bhp.opusb.service.dto.MContractLineDTO;
import com.bhp.opusb.service.mapper.MContractLineMapper;
import com.bhp.opusb.service.dto.MContractLineCriteria;
import com.bhp.opusb.service.MContractLineQueryService;

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
 * Integration tests for the {@link MContractLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractLineResourceIT {

    private static final Integer DEFAULT_LINE_NO = 1;
    private static final Integer UPDATED_LINE_NO = 2;
    private static final Integer SMALLER_LINE_NO = 1 - 1;

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
    private MContractLineRepository mContractLineRepository;

    @Autowired
    private MContractLineMapper mContractLineMapper;

    @Autowired
    private MContractLineService mContractLineService;

    @Autowired
    private MContractLineQueryService mContractLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractLineMockMvc;

    private MContractLine mContractLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractLine createEntity(EntityManager em) {
        MContractLine mContractLine = new MContractLine()
            .lineNo(DEFAULT_LINE_NO)
            .quantity(DEFAULT_QUANTITY)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
            .totalCeilingPrice(DEFAULT_TOTAL_CEILING_PRICE)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .remark(DEFAULT_REMARK)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractLine.setContract(mContract);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mContractLine.setCostCenter(cCostCenter);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mContractLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mContractLine.setUom(cUnitOfMeasure);
        return mContractLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractLine createUpdatedEntity(EntityManager em) {
        MContractLine mContractLine = new MContractLine()
            .lineNo(UPDATED_LINE_NO)
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .totalCeilingPrice(UPDATED_TOTAL_CEILING_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractLine.setContract(mContract);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractLine.setAdOrganization(aDOrganization);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mContractLine.setCostCenter(cCostCenter);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mContractLine.setProduct(cProduct);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mContractLine.setUom(cUnitOfMeasure);
        return mContractLine;
    }

    @BeforeEach
    public void initTest() {
        mContractLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractLine() throws Exception {
        int databaseSizeBeforeCreate = mContractLineRepository.findAll().size();

        // Create the MContractLine
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);
        restMContractLineMockMvc.perform(post("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractLine in the database
        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeCreate + 1);
        MContractLine testMContractLine = mContractLineList.get(mContractLineList.size() - 1);
        assertThat(testMContractLine.getLineNo()).isEqualTo(DEFAULT_LINE_NO);
        assertThat(testMContractLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMContractLine.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMContractLine.getTotalCeilingPrice()).isEqualTo(DEFAULT_TOTAL_CEILING_PRICE);
        assertThat(testMContractLine.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testMContractLine.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMContractLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractLineRepository.findAll().size();

        // Create the MContractLine with an existing ID
        mContractLine.setId(1L);
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractLineMockMvc.perform(post("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractLine in the database
        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractLineRepository.findAll().size();
        // set the field null
        mContractLine.setQuantity(null);

        // Create the MContractLine, which fails.
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);

        restMContractLineMockMvc.perform(post("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isBadRequest());

        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCeilingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractLineRepository.findAll().size();
        // set the field null
        mContractLine.setCeilingPrice(null);

        // Create the MContractLine, which fails.
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);

        restMContractLineMockMvc.perform(post("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isBadRequest());

        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractLineRepository.findAll().size();
        // set the field null
        mContractLine.setDeliveryDate(null);

        // Create the MContractLine, which fails.
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);

        restMContractLineMockMvc.perform(post("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isBadRequest());

        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractLines() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList
        restMContractLineMockMvc.perform(get("/api/m-contract-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
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
    public void getMContractLine() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get the mContractLine
        restMContractLineMockMvc.perform(get("/api/m-contract-lines/{id}", mContractLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractLine.getId().intValue()))
            .andExpect(jsonPath("$.lineNo").value(DEFAULT_LINE_NO))
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
    public void getMContractLinesByIdFiltering() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        Long id = mContractLine.getId();

        defaultMContractLineShouldBeFound("id.equals=" + id);
        defaultMContractLineShouldNotBeFound("id.notEquals=" + id);

        defaultMContractLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo equals to DEFAULT_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.equals=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo equals to UPDATED_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.equals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo not equals to DEFAULT_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.notEquals=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo not equals to UPDATED_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.notEquals=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo in DEFAULT_LINE_NO or UPDATED_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.in=" + DEFAULT_LINE_NO + "," + UPDATED_LINE_NO);

        // Get all the mContractLineList where lineNo equals to UPDATED_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.in=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo is not null
        defaultMContractLineShouldBeFound("lineNo.specified=true");

        // Get all the mContractLineList where lineNo is null
        defaultMContractLineShouldNotBeFound("lineNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo is greater than or equal to DEFAULT_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.greaterThanOrEqual=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo is greater than or equal to UPDATED_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.greaterThanOrEqual=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo is less than or equal to DEFAULT_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.lessThanOrEqual=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo is less than or equal to SMALLER_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.lessThanOrEqual=" + SMALLER_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo is less than DEFAULT_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.lessThan=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo is less than UPDATED_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.lessThan=" + UPDATED_LINE_NO);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByLineNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where lineNo is greater than DEFAULT_LINE_NO
        defaultMContractLineShouldNotBeFound("lineNo.greaterThan=" + DEFAULT_LINE_NO);

        // Get all the mContractLineList where lineNo is greater than SMALLER_LINE_NO
        defaultMContractLineShouldBeFound("lineNo.greaterThan=" + SMALLER_LINE_NO);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity equals to DEFAULT_QUANTITY
        defaultMContractLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity equals to UPDATED_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity not equals to DEFAULT_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity not equals to UPDATED_QUANTITY
        defaultMContractLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMContractLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mContractLineList where quantity equals to UPDATED_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity is not null
        defaultMContractLineShouldBeFound("quantity.specified=true");

        // Get all the mContractLineList where quantity is null
        defaultMContractLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMContractLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMContractLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity is less than DEFAULT_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity is less than UPDATED_QUANTITY
        defaultMContractLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where quantity is greater than DEFAULT_QUANTITY
        defaultMContractLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mContractLineList where quantity is greater than SMALLER_QUANTITY
        defaultMContractLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice is not null
        defaultMContractLineShouldBeFound("ceilingPrice.specified=true");

        // Get all the mContractLineList where ceilingPrice is null
        defaultMContractLineShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mContractLineList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMContractLineShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice equals to DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.equals=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.equals=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice not equals to DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.notEquals=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice not equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.notEquals=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice in DEFAULT_TOTAL_CEILING_PRICE or UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.in=" + DEFAULT_TOTAL_CEILING_PRICE + "," + UPDATED_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice equals to UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.in=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice is not null
        defaultMContractLineShouldBeFound("totalCeilingPrice.specified=true");

        // Get all the mContractLineList where totalCeilingPrice is null
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice is greater than or equal to DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice is greater than or equal to UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.greaterThanOrEqual=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice is less than or equal to DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.lessThanOrEqual=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice is less than or equal to SMALLER_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.lessThanOrEqual=" + SMALLER_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice is less than DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.lessThan=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice is less than UPDATED_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.lessThan=" + UPDATED_TOTAL_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByTotalCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where totalCeilingPrice is greater than DEFAULT_TOTAL_CEILING_PRICE
        defaultMContractLineShouldNotBeFound("totalCeilingPrice.greaterThan=" + DEFAULT_TOTAL_CEILING_PRICE);

        // Get all the mContractLineList where totalCeilingPrice is greater than SMALLER_TOTAL_CEILING_PRICE
        defaultMContractLineShouldBeFound("totalCeilingPrice.greaterThan=" + SMALLER_TOTAL_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate is not null
        defaultMContractLineShouldBeFound("deliveryDate.specified=true");

        // Get all the mContractLineList where deliveryDate is null
        defaultMContractLineShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultMContractLineShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mContractLineList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultMContractLineShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark equals to DEFAULT_REMARK
        defaultMContractLineShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the mContractLineList where remark equals to UPDATED_REMARK
        defaultMContractLineShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark not equals to DEFAULT_REMARK
        defaultMContractLineShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the mContractLineList where remark not equals to UPDATED_REMARK
        defaultMContractLineShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultMContractLineShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the mContractLineList where remark equals to UPDATED_REMARK
        defaultMContractLineShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark is not null
        defaultMContractLineShouldBeFound("remark.specified=true");

        // Get all the mContractLineList where remark is null
        defaultMContractLineShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractLinesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark contains DEFAULT_REMARK
        defaultMContractLineShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the mContractLineList where remark contains UPDATED_REMARK
        defaultMContractLineShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where remark does not contain DEFAULT_REMARK
        defaultMContractLineShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the mContractLineList where remark does not contain UPDATED_REMARK
        defaultMContractLineShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllMContractLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where uid equals to DEFAULT_UID
        defaultMContractLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractLineList where uid equals to UPDATED_UID
        defaultMContractLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where uid not equals to DEFAULT_UID
        defaultMContractLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractLineList where uid not equals to UPDATED_UID
        defaultMContractLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractLineList where uid equals to UPDATED_UID
        defaultMContractLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where uid is not null
        defaultMContractLineShouldBeFound("uid.specified=true");

        // Get all the mContractLineList where uid is null
        defaultMContractLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where active equals to DEFAULT_ACTIVE
        defaultMContractLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractLineList where active equals to UPDATED_ACTIVE
        defaultMContractLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where active not equals to DEFAULT_ACTIVE
        defaultMContractLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractLineList where active not equals to UPDATED_ACTIVE
        defaultMContractLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractLineList where active equals to UPDATED_ACTIVE
        defaultMContractLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        // Get all the mContractLineList where active is not null
        defaultMContractLineShouldBeFound("active.specified=true");

        // Get all the mContractLineList where active is null
        defaultMContractLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractLinesByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractLine.getContract();
        mContractLineRepository.saveAndFlush(mContractLine);
        Long contractId = contract.getId();

        // Get all the mContractLineList where contract equals to contractId
        defaultMContractLineShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractLineList where contract equals to contractId + 1
        defaultMContractLineShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractLine.getAdOrganization();
        mContractLineRepository.saveAndFlush(mContractLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractLineList where adOrganization equals to adOrganizationId
        defaultMContractLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractLineList where adOrganization equals to adOrganizationId + 1
        defaultMContractLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractLinesByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mContractLine.getCostCenter();
        mContractLineRepository.saveAndFlush(mContractLine);
        Long costCenterId = costCenter.getId();

        // Get all the mContractLineList where costCenter equals to costCenterId
        defaultMContractLineShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mContractLineList where costCenter equals to costCenterId + 1
        defaultMContractLineShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractLinesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct product = mContractLine.getProduct();
        mContractLineRepository.saveAndFlush(mContractLine);
        Long productId = product.getId();

        // Get all the mContractLineList where product equals to productId
        defaultMContractLineShouldBeFound("productId.equals=" + productId);

        // Get all the mContractLineList where product equals to productId + 1
        defaultMContractLineShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractLinesByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = mContractLine.getUom();
        mContractLineRepository.saveAndFlush(mContractLine);
        Long uomId = uom.getId();

        // Get all the mContractLineList where uom equals to uomId
        defaultMContractLineShouldBeFound("uomId.equals=" + uomId);

        // Get all the mContractLineList where uom equals to uomId + 1
        defaultMContractLineShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractLineShouldBeFound(String filter) throws Exception {
        restMContractLineMockMvc.perform(get("/api/m-contract-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNo").value(hasItem(DEFAULT_LINE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalCeilingPrice").value(hasItem(DEFAULT_TOTAL_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractLineMockMvc.perform(get("/api/m-contract-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractLineShouldNotBeFound(String filter) throws Exception {
        restMContractLineMockMvc.perform(get("/api/m-contract-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractLineMockMvc.perform(get("/api/m-contract-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractLine() throws Exception {
        // Get the mContractLine
        restMContractLineMockMvc.perform(get("/api/m-contract-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractLine() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        int databaseSizeBeforeUpdate = mContractLineRepository.findAll().size();

        // Update the mContractLine
        MContractLine updatedMContractLine = mContractLineRepository.findById(mContractLine.getId()).get();
        // Disconnect from session so that the updates on updatedMContractLine are not directly saved in db
        em.detach(updatedMContractLine);
        updatedMContractLine
            .lineNo(UPDATED_LINE_NO)
            .quantity(UPDATED_QUANTITY)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .totalCeilingPrice(UPDATED_TOTAL_CEILING_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remark(UPDATED_REMARK)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(updatedMContractLine);

        restMContractLineMockMvc.perform(put("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isOk());

        // Validate the MContractLine in the database
        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeUpdate);
        MContractLine testMContractLine = mContractLineList.get(mContractLineList.size() - 1);
        assertThat(testMContractLine.getLineNo()).isEqualTo(UPDATED_LINE_NO);
        assertThat(testMContractLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMContractLine.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMContractLine.getTotalCeilingPrice()).isEqualTo(UPDATED_TOTAL_CEILING_PRICE);
        assertThat(testMContractLine.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testMContractLine.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMContractLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractLine() throws Exception {
        int databaseSizeBeforeUpdate = mContractLineRepository.findAll().size();

        // Create the MContractLine
        MContractLineDTO mContractLineDTO = mContractLineMapper.toDto(mContractLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractLineMockMvc.perform(put("/api/m-contract-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractLine in the database
        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractLine() throws Exception {
        // Initialize the database
        mContractLineRepository.saveAndFlush(mContractLine);

        int databaseSizeBeforeDelete = mContractLineRepository.findAll().size();

        // Delete the mContractLine
        restMContractLineMockMvc.perform(delete("/api/m-contract-lines/{id}", mContractLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractLine> mContractLineList = mContractLineRepository.findAll();
        assertThat(mContractLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
