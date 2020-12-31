package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.repository.MProductCatalogRepository;
import com.bhp.opusb.service.MProductCatalogService;
import com.bhp.opusb.service.dto.MProductCatalogDTO;
import com.bhp.opusb.service.mapper.MProductCatalogMapper;
import com.bhp.opusb.service.dto.MProductCatalogCriteria;
import com.bhp.opusb.service.MProductCatalogQueryService;

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
 * Integration tests for the {@link MProductCatalogResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MProductCatalogResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;
    private static final Double SMALLER_HEIGHT = 1D - 1D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;
    private static final Double SMALLER_LENGTH = 1D - 1D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;
    private static final Double SMALLER_WIDTH = 1D - 1D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;
    private static final Double SMALLER_WEIGHT = 1D - 1D;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_EXPIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final String DEFAULT_REJECTED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_REASON = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MProductCatalogRepository mProductCatalogRepository;

    @Autowired
    private MProductCatalogMapper mProductCatalogMapper;

    @Autowired
    private MProductCatalogService mProductCatalogService;

    @Autowired
    private MProductCatalogQueryService mProductCatalogQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProductCatalogMockMvc;

    private MProductCatalog mProductCatalog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProductCatalog createEntity(EntityManager em) {
        MProductCatalog mProductCatalog = new MProductCatalog()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .weight(DEFAULT_WEIGHT)
            .price(DEFAULT_PRICE)
            .expiredDate(DEFAULT_EXPIRED_DATE)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .rejectedReason(DEFAULT_REJECTED_REASON)
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
        mProductCatalog.setAdOrganizationId(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mProductCatalog.setCDocumentType(cDocumentType);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mProductCatalog.setCCurrency(cCurrency);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mProductCatalog.setCUom(cUnitOfMeasure);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mProductCatalog.setCVendor(cVendor);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mProductCatalog.setMProduct(cProduct);
        return mProductCatalog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProductCatalog createUpdatedEntity(EntityManager em) {
        MProductCatalog mProductCatalog = new MProductCatalog()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .rejectedReason(UPDATED_REJECTED_REASON)
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
        mProductCatalog.setAdOrganizationId(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mProductCatalog.setCDocumentType(cDocumentType);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mProductCatalog.setCCurrency(cCurrency);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        mProductCatalog.setCUom(cUnitOfMeasure);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mProductCatalog.setCVendor(cVendor);
        // Add required entity
        CProduct cProduct;
        if (TestUtil.findAll(em, CProduct.class).isEmpty()) {
            cProduct = CProductResourceIT.createUpdatedEntity(em);
            em.persist(cProduct);
            em.flush();
        } else {
            cProduct = TestUtil.findAll(em, CProduct.class).get(0);
        }
        mProductCatalog.setMProduct(cProduct);
        return mProductCatalog;
    }

    @BeforeEach
    public void initTest() {
        mProductCatalog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProductCatalog() throws Exception {
        int databaseSizeBeforeCreate = mProductCatalogRepository.findAll().size();
        // Create the MProductCatalog
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);
        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isCreated());

        // Validate the MProductCatalog in the database
        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        MProductCatalog testMProductCatalog = mProductCatalogList.get(mProductCatalogList.size() - 1);
        assertThat(testMProductCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMProductCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMProductCatalog.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testMProductCatalog.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testMProductCatalog.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testMProductCatalog.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testMProductCatalog.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMProductCatalog.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMProductCatalog.getExpiredDate()).isEqualTo(DEFAULT_EXPIRED_DATE);
        assertThat(testMProductCatalog.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMProductCatalog.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMProductCatalog.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMProductCatalog.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMProductCatalog.getRejectedReason()).isEqualTo(DEFAULT_REJECTED_REASON);
        assertThat(testMProductCatalog.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProductCatalog.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProductCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProductCatalogRepository.findAll().size();

        // Create the MProductCatalog with an existing ID
        mProductCatalog.setId(1L);
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProductCatalog in the database
        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProductCatalogRepository.findAll().size();
        // set the field null
        mProductCatalog.setName(null);

        // Create the MProductCatalog, which fails.
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);


        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProductCatalogRepository.findAll().size();
        // set the field null
        mProductCatalog.setShortDescription(null);

        // Create the MProductCatalog, which fails.
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);


        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProductCatalogRepository.findAll().size();
        // set the field null
        mProductCatalog.setDocumentAction(null);

        // Create the MProductCatalog, which fails.
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);


        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProductCatalogRepository.findAll().size();
        // set the field null
        mProductCatalog.setDocumentStatus(null);

        // Create the MProductCatalog, which fails.
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);


        restMProductCatalogMockMvc.perform(post("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogs() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProductCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProductCatalog() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get the mProductCatalog
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs/{id}", mProductCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProductCatalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.expiredDate").value(DEFAULT_EXPIRED_DATE.toString()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.rejectedReason").value(DEFAULT_REJECTED_REASON))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProductCatalogsByIdFiltering() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        Long id = mProductCatalog.getId();

        defaultMProductCatalogShouldBeFound("id.equals=" + id);
        defaultMProductCatalogShouldNotBeFound("id.notEquals=" + id);

        defaultMProductCatalogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProductCatalogShouldNotBeFound("id.greaterThan=" + id);

        defaultMProductCatalogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProductCatalogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name equals to DEFAULT_NAME
        defaultMProductCatalogShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mProductCatalogList where name equals to UPDATED_NAME
        defaultMProductCatalogShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name not equals to DEFAULT_NAME
        defaultMProductCatalogShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mProductCatalogList where name not equals to UPDATED_NAME
        defaultMProductCatalogShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMProductCatalogShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mProductCatalogList where name equals to UPDATED_NAME
        defaultMProductCatalogShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name is not null
        defaultMProductCatalogShouldBeFound("name.specified=true");

        // Get all the mProductCatalogList where name is null
        defaultMProductCatalogShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByNameContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name contains DEFAULT_NAME
        defaultMProductCatalogShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mProductCatalogList where name contains UPDATED_NAME
        defaultMProductCatalogShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where name does not contain DEFAULT_NAME
        defaultMProductCatalogShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mProductCatalogList where name does not contain UPDATED_NAME
        defaultMProductCatalogShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description equals to DEFAULT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mProductCatalogList where description equals to UPDATED_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description not equals to DEFAULT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mProductCatalogList where description not equals to UPDATED_DESCRIPTION
        defaultMProductCatalogShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMProductCatalogShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mProductCatalogList where description equals to UPDATED_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description is not null
        defaultMProductCatalogShouldBeFound("description.specified=true");

        // Get all the mProductCatalogList where description is null
        defaultMProductCatalogShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description contains DEFAULT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mProductCatalogList where description contains UPDATED_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where description does not contain DEFAULT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mProductCatalogList where description does not contain UPDATED_DESCRIPTION
        defaultMProductCatalogShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription equals to DEFAULT_SHORT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("shortDescription.equals=" + DEFAULT_SHORT_DESCRIPTION);

        // Get all the mProductCatalogList where shortDescription equals to UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("shortDescription.equals=" + UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription not equals to DEFAULT_SHORT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("shortDescription.notEquals=" + DEFAULT_SHORT_DESCRIPTION);

        // Get all the mProductCatalogList where shortDescription not equals to UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("shortDescription.notEquals=" + UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription in DEFAULT_SHORT_DESCRIPTION or UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("shortDescription.in=" + DEFAULT_SHORT_DESCRIPTION + "," + UPDATED_SHORT_DESCRIPTION);

        // Get all the mProductCatalogList where shortDescription equals to UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("shortDescription.in=" + UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription is not null
        defaultMProductCatalogShouldBeFound("shortDescription.specified=true");

        // Get all the mProductCatalogList where shortDescription is null
        defaultMProductCatalogShouldNotBeFound("shortDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription contains DEFAULT_SHORT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("shortDescription.contains=" + DEFAULT_SHORT_DESCRIPTION);

        // Get all the mProductCatalogList where shortDescription contains UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("shortDescription.contains=" + UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByShortDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where shortDescription does not contain DEFAULT_SHORT_DESCRIPTION
        defaultMProductCatalogShouldNotBeFound("shortDescription.doesNotContain=" + DEFAULT_SHORT_DESCRIPTION);

        // Get all the mProductCatalogList where shortDescription does not contain UPDATED_SHORT_DESCRIPTION
        defaultMProductCatalogShouldBeFound("shortDescription.doesNotContain=" + UPDATED_SHORT_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height equals to DEFAULT_HEIGHT
        defaultMProductCatalogShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height equals to UPDATED_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height not equals to DEFAULT_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height not equals to UPDATED_HEIGHT
        defaultMProductCatalogShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultMProductCatalogShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the mProductCatalogList where height equals to UPDATED_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height is not null
        defaultMProductCatalogShouldBeFound("height.specified=true");

        // Get all the mProductCatalogList where height is null
        defaultMProductCatalogShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height is greater than or equal to DEFAULT_HEIGHT
        defaultMProductCatalogShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height is greater than or equal to UPDATED_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height is less than or equal to DEFAULT_HEIGHT
        defaultMProductCatalogShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height is less than or equal to SMALLER_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height is less than DEFAULT_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height is less than UPDATED_HEIGHT
        defaultMProductCatalogShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where height is greater than DEFAULT_HEIGHT
        defaultMProductCatalogShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the mProductCatalogList where height is greater than SMALLER_HEIGHT
        defaultMProductCatalogShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length equals to DEFAULT_LENGTH
        defaultMProductCatalogShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length equals to UPDATED_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length not equals to DEFAULT_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.notEquals=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length not equals to UPDATED_LENGTH
        defaultMProductCatalogShouldBeFound("length.notEquals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultMProductCatalogShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the mProductCatalogList where length equals to UPDATED_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length is not null
        defaultMProductCatalogShouldBeFound("length.specified=true");

        // Get all the mProductCatalogList where length is null
        defaultMProductCatalogShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length is greater than or equal to DEFAULT_LENGTH
        defaultMProductCatalogShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length is greater than or equal to UPDATED_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length is less than or equal to DEFAULT_LENGTH
        defaultMProductCatalogShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length is less than or equal to SMALLER_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length is less than DEFAULT_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length is less than UPDATED_LENGTH
        defaultMProductCatalogShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where length is greater than DEFAULT_LENGTH
        defaultMProductCatalogShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the mProductCatalogList where length is greater than SMALLER_LENGTH
        defaultMProductCatalogShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width equals to DEFAULT_WIDTH
        defaultMProductCatalogShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width equals to UPDATED_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width not equals to DEFAULT_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width not equals to UPDATED_WIDTH
        defaultMProductCatalogShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultMProductCatalogShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the mProductCatalogList where width equals to UPDATED_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width is not null
        defaultMProductCatalogShouldBeFound("width.specified=true");

        // Get all the mProductCatalogList where width is null
        defaultMProductCatalogShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width is greater than or equal to DEFAULT_WIDTH
        defaultMProductCatalogShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width is greater than or equal to UPDATED_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width is less than or equal to DEFAULT_WIDTH
        defaultMProductCatalogShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width is less than or equal to SMALLER_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width is less than DEFAULT_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width is less than UPDATED_WIDTH
        defaultMProductCatalogShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where width is greater than DEFAULT_WIDTH
        defaultMProductCatalogShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the mProductCatalogList where width is greater than SMALLER_WIDTH
        defaultMProductCatalogShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight equals to DEFAULT_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight equals to UPDATED_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight not equals to DEFAULT_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight not equals to UPDATED_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the mProductCatalogList where weight equals to UPDATED_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight is not null
        defaultMProductCatalogShouldBeFound("weight.specified=true");

        // Get all the mProductCatalogList where weight is null
        defaultMProductCatalogShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight is greater than or equal to UPDATED_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight is less than or equal to DEFAULT_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight is less than or equal to SMALLER_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight is less than DEFAULT_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight is less than UPDATED_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where weight is greater than DEFAULT_WEIGHT
        defaultMProductCatalogShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the mProductCatalogList where weight is greater than SMALLER_WEIGHT
        defaultMProductCatalogShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price equals to DEFAULT_PRICE
        defaultMProductCatalogShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price equals to UPDATED_PRICE
        defaultMProductCatalogShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price not equals to DEFAULT_PRICE
        defaultMProductCatalogShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price not equals to UPDATED_PRICE
        defaultMProductCatalogShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMProductCatalogShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mProductCatalogList where price equals to UPDATED_PRICE
        defaultMProductCatalogShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price is not null
        defaultMProductCatalogShouldBeFound("price.specified=true");

        // Get all the mProductCatalogList where price is null
        defaultMProductCatalogShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price is greater than or equal to DEFAULT_PRICE
        defaultMProductCatalogShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price is greater than or equal to UPDATED_PRICE
        defaultMProductCatalogShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price is less than or equal to DEFAULT_PRICE
        defaultMProductCatalogShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price is less than or equal to SMALLER_PRICE
        defaultMProductCatalogShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price is less than DEFAULT_PRICE
        defaultMProductCatalogShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price is less than UPDATED_PRICE
        defaultMProductCatalogShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where price is greater than DEFAULT_PRICE
        defaultMProductCatalogShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mProductCatalogList where price is greater than SMALLER_PRICE
        defaultMProductCatalogShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate equals to DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.equals=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate equals to UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.equals=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate not equals to DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.notEquals=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate not equals to UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.notEquals=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate in DEFAULT_EXPIRED_DATE or UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.in=" + DEFAULT_EXPIRED_DATE + "," + UPDATED_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate equals to UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.in=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate is not null
        defaultMProductCatalogShouldBeFound("expiredDate.specified=true");

        // Get all the mProductCatalogList where expiredDate is null
        defaultMProductCatalogShouldNotBeFound("expiredDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate is greater than or equal to DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.greaterThanOrEqual=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate is greater than or equal to UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.greaterThanOrEqual=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate is less than or equal to DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.lessThanOrEqual=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate is less than or equal to SMALLER_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.lessThanOrEqual=" + SMALLER_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate is less than DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.lessThan=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate is less than UPDATED_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.lessThan=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByExpiredDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where expiredDate is greater than DEFAULT_EXPIRED_DATE
        defaultMProductCatalogShouldNotBeFound("expiredDate.greaterThan=" + DEFAULT_EXPIRED_DATE);

        // Get all the mProductCatalogList where expiredDate is greater than SMALLER_EXPIRED_DATE
        defaultMProductCatalogShouldBeFound("expiredDate.greaterThan=" + SMALLER_EXPIRED_DATE);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMProductCatalogShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProductCatalogList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMProductCatalogShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProductCatalogList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mProductCatalogList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction is not null
        defaultMProductCatalogShouldBeFound("documentAction.specified=true");

        // Get all the mProductCatalogList where documentAction is null
        defaultMProductCatalogShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMProductCatalogShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProductCatalogList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMProductCatalogShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mProductCatalogList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMProductCatalogShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMProductCatalogShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProductCatalogList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMProductCatalogShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProductCatalogList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mProductCatalogList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus is not null
        defaultMProductCatalogShouldBeFound("documentStatus.specified=true");

        // Get all the mProductCatalogList where documentStatus is null
        defaultMProductCatalogShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMProductCatalogShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProductCatalogList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMProductCatalogShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mProductCatalogList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMProductCatalogShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where approved equals to DEFAULT_APPROVED
        defaultMProductCatalogShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mProductCatalogList where approved equals to UPDATED_APPROVED
        defaultMProductCatalogShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where approved not equals to DEFAULT_APPROVED
        defaultMProductCatalogShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mProductCatalogList where approved not equals to UPDATED_APPROVED
        defaultMProductCatalogShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMProductCatalogShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mProductCatalogList where approved equals to UPDATED_APPROVED
        defaultMProductCatalogShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where approved is not null
        defaultMProductCatalogShouldBeFound("approved.specified=true");

        // Get all the mProductCatalogList where approved is null
        defaultMProductCatalogShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where processed equals to DEFAULT_PROCESSED
        defaultMProductCatalogShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mProductCatalogList where processed equals to UPDATED_PROCESSED
        defaultMProductCatalogShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where processed not equals to DEFAULT_PROCESSED
        defaultMProductCatalogShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mProductCatalogList where processed not equals to UPDATED_PROCESSED
        defaultMProductCatalogShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMProductCatalogShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mProductCatalogList where processed equals to UPDATED_PROCESSED
        defaultMProductCatalogShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where processed is not null
        defaultMProductCatalogShouldBeFound("processed.specified=true");

        // Get all the mProductCatalogList where processed is null
        defaultMProductCatalogShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason equals to DEFAULT_REJECTED_REASON
        defaultMProductCatalogShouldBeFound("rejectedReason.equals=" + DEFAULT_REJECTED_REASON);

        // Get all the mProductCatalogList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldNotBeFound("rejectedReason.equals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason not equals to DEFAULT_REJECTED_REASON
        defaultMProductCatalogShouldNotBeFound("rejectedReason.notEquals=" + DEFAULT_REJECTED_REASON);

        // Get all the mProductCatalogList where rejectedReason not equals to UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldBeFound("rejectedReason.notEquals=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason in DEFAULT_REJECTED_REASON or UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldBeFound("rejectedReason.in=" + DEFAULT_REJECTED_REASON + "," + UPDATED_REJECTED_REASON);

        // Get all the mProductCatalogList where rejectedReason equals to UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldNotBeFound("rejectedReason.in=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason is not null
        defaultMProductCatalogShouldBeFound("rejectedReason.specified=true");

        // Get all the mProductCatalogList where rejectedReason is null
        defaultMProductCatalogShouldNotBeFound("rejectedReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason contains DEFAULT_REJECTED_REASON
        defaultMProductCatalogShouldBeFound("rejectedReason.contains=" + DEFAULT_REJECTED_REASON);

        // Get all the mProductCatalogList where rejectedReason contains UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldNotBeFound("rejectedReason.contains=" + UPDATED_REJECTED_REASON);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByRejectedReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where rejectedReason does not contain DEFAULT_REJECTED_REASON
        defaultMProductCatalogShouldNotBeFound("rejectedReason.doesNotContain=" + DEFAULT_REJECTED_REASON);

        // Get all the mProductCatalogList where rejectedReason does not contain UPDATED_REJECTED_REASON
        defaultMProductCatalogShouldBeFound("rejectedReason.doesNotContain=" + UPDATED_REJECTED_REASON);
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where uid equals to DEFAULT_UID
        defaultMProductCatalogShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProductCatalogList where uid equals to UPDATED_UID
        defaultMProductCatalogShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where uid not equals to DEFAULT_UID
        defaultMProductCatalogShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProductCatalogList where uid not equals to UPDATED_UID
        defaultMProductCatalogShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProductCatalogShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProductCatalogList where uid equals to UPDATED_UID
        defaultMProductCatalogShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where uid is not null
        defaultMProductCatalogShouldBeFound("uid.specified=true");

        // Get all the mProductCatalogList where uid is null
        defaultMProductCatalogShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where active equals to DEFAULT_ACTIVE
        defaultMProductCatalogShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProductCatalogList where active equals to UPDATED_ACTIVE
        defaultMProductCatalogShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where active not equals to DEFAULT_ACTIVE
        defaultMProductCatalogShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProductCatalogList where active not equals to UPDATED_ACTIVE
        defaultMProductCatalogShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProductCatalogShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProductCatalogList where active equals to UPDATED_ACTIVE
        defaultMProductCatalogShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        // Get all the mProductCatalogList where active is not null
        defaultMProductCatalogShouldBeFound("active.specified=true");

        // Get all the mProductCatalogList where active is null
        defaultMProductCatalogShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProductCatalogsByAdOrganizationIdIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganizationId = mProductCatalog.getAdOrganizationId();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long adOrganizationIdId = adOrganizationId.getId();

        // Get all the mProductCatalogList where adOrganizationId equals to adOrganizationIdId
        defaultMProductCatalogShouldBeFound("adOrganizationIdId.equals=" + adOrganizationIdId);

        // Get all the mProductCatalogList where adOrganizationId equals to adOrganizationIdId + 1
        defaultMProductCatalogShouldNotBeFound("adOrganizationIdId.equals=" + (adOrganizationIdId + 1));
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByCDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType cDocumentType = mProductCatalog.getCDocumentType();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long cDocumentTypeId = cDocumentType.getId();

        // Get all the mProductCatalogList where cDocumentType equals to cDocumentTypeId
        defaultMProductCatalogShouldBeFound("cDocumentTypeId.equals=" + cDocumentTypeId);

        // Get all the mProductCatalogList where cDocumentType equals to cDocumentTypeId + 1
        defaultMProductCatalogShouldNotBeFound("cDocumentTypeId.equals=" + (cDocumentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByCCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency cCurrency = mProductCatalog.getCCurrency();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long cCurrencyId = cCurrency.getId();

        // Get all the mProductCatalogList where cCurrency equals to cCurrencyId
        defaultMProductCatalogShouldBeFound("cCurrencyId.equals=" + cCurrencyId);

        // Get all the mProductCatalogList where cCurrency equals to cCurrencyId + 1
        defaultMProductCatalogShouldNotBeFound("cCurrencyId.equals=" + (cCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByCUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure cUom = mProductCatalog.getCUom();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long cUomId = cUom.getId();

        // Get all the mProductCatalogList where cUom equals to cUomId
        defaultMProductCatalogShouldBeFound("cUomId.equals=" + cUomId);

        // Get all the mProductCatalogList where cUom equals to cUomId + 1
        defaultMProductCatalogShouldNotBeFound("cUomId.equals=" + (cUomId + 1));
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByCVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor cVendor = mProductCatalog.getCVendor();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long cVendorId = cVendor.getId();

        // Get all the mProductCatalogList where cVendor equals to cVendorId
        defaultMProductCatalogShouldBeFound("cVendorId.equals=" + cVendorId);

        // Get all the mProductCatalogList where cVendor equals to cVendorId + 1
        defaultMProductCatalogShouldNotBeFound("cVendorId.equals=" + (cVendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMProductCatalogsByMProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProduct mProduct = mProductCatalog.getMProduct();
        mProductCatalogRepository.saveAndFlush(mProductCatalog);
        Long mProductId = mProduct.getId();

        // Get all the mProductCatalogList where mProduct equals to mProductId
        defaultMProductCatalogShouldBeFound("mProductId.equals=" + mProductId);

        // Get all the mProductCatalogList where mProduct equals to mProductId + 1
        defaultMProductCatalogShouldNotBeFound("mProductId.equals=" + (mProductId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProductCatalogShouldBeFound(String filter) throws Exception {
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProductCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].rejectedReason").value(hasItem(DEFAULT_REJECTED_REASON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProductCatalogShouldNotBeFound(String filter) throws Exception {
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMProductCatalog() throws Exception {
        // Get the mProductCatalog
        restMProductCatalogMockMvc.perform(get("/api/m-product-catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProductCatalog() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        int databaseSizeBeforeUpdate = mProductCatalogRepository.findAll().size();

        // Update the mProductCatalog
        MProductCatalog updatedMProductCatalog = mProductCatalogRepository.findById(mProductCatalog.getId()).get();
        // Disconnect from session so that the updates on updatedMProductCatalog are not directly saved in db
        em.detach(updatedMProductCatalog);
        updatedMProductCatalog
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .rejectedReason(UPDATED_REJECTED_REASON)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(updatedMProductCatalog);

        restMProductCatalogMockMvc.perform(put("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isOk());

        // Validate the MProductCatalog in the database
        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeUpdate);
        MProductCatalog testMProductCatalog = mProductCatalogList.get(mProductCatalogList.size() - 1);
        assertThat(testMProductCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMProductCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMProductCatalog.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testMProductCatalog.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMProductCatalog.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testMProductCatalog.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testMProductCatalog.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMProductCatalog.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMProductCatalog.getExpiredDate()).isEqualTo(UPDATED_EXPIRED_DATE);
        assertThat(testMProductCatalog.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMProductCatalog.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMProductCatalog.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMProductCatalog.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMProductCatalog.getRejectedReason()).isEqualTo(UPDATED_REJECTED_REASON);
        assertThat(testMProductCatalog.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProductCatalog.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProductCatalog() throws Exception {
        int databaseSizeBeforeUpdate = mProductCatalogRepository.findAll().size();

        // Create the MProductCatalog
        MProductCatalogDTO mProductCatalogDTO = mProductCatalogMapper.toDto(mProductCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProductCatalogMockMvc.perform(put("/api/m-product-catalogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProductCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProductCatalog in the database
        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProductCatalog() throws Exception {
        // Initialize the database
        mProductCatalogRepository.saveAndFlush(mProductCatalog);

        int databaseSizeBeforeDelete = mProductCatalogRepository.findAll().size();

        // Delete the mProductCatalog
        restMProductCatalogMockMvc.perform(delete("/api/m-product-catalogs/{id}", mProductCatalog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProductCatalog> mProductCatalogList = mProductCatalogRepository.findAll();
        assertThat(mProductCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
