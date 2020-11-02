package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.domain.CProductCategoryAccount;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.service.CProductService;
import com.bhp.opusb.service.dto.CProductDTO;
import com.bhp.opusb.service.mapper.CProductMapper;
import com.bhp.opusb.service.dto.CProductCriteria;
import com.bhp.opusb.service.CProductQueryService;

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
 * Integration tests for the {@link CProductResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CProductResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CProductRepository cProductRepository;

    @Autowired
    private CProductMapper cProductMapper;

    @Autowired
    private CProductService cProductService;

    @Autowired
    private CProductQueryService cProductQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCProductMockMvc;

    private CProduct cProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProduct createEntity(EntityManager em) {
        CProduct cProduct = new CProduct()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
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
        cProduct.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cProduct.setProductClassification(cProductClassification);
        // Add required entity
        CProductCategory cProductCategory;
        if (TestUtil.findAll(em, CProductCategory.class).isEmpty()) {
            cProductCategory = CProductCategoryResourceIT.createEntity(em);
            em.persist(cProductCategory);
            em.flush();
        } else {
            cProductCategory = TestUtil.findAll(em, CProductCategory.class).get(0);
        }
        cProduct.setProductCategory(cProductCategory);
        // Add required entity
        cProduct.setProductSubCategory(cProductCategory);
        // Add required entity
        CProductCategoryAccount cProductCategoryAccount;
        if (TestUtil.findAll(em, CProductCategoryAccount.class).isEmpty()) {
            cProductCategoryAccount = CProductCategoryAccountResourceIT.createEntity(em);
            em.persist(cProductCategoryAccount);
            em.flush();
        } else {
            cProductCategoryAccount = TestUtil.findAll(em, CProductCategoryAccount.class).get(0);
        }
        cProduct.setAssetAcct(cProductCategoryAccount);
        // Add required entity
        cProduct.setExpenseAcct(cProductCategoryAccount);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        cProduct.setUom(cUnitOfMeasure);
        return cProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CProduct createUpdatedEntity(EntityManager em) {
        CProduct cProduct = new CProduct()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
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
        cProduct.setAdOrganization(aDOrganization);
        // Add required entity
        CProductClassification cProductClassification;
        if (TestUtil.findAll(em, CProductClassification.class).isEmpty()) {
            cProductClassification = CProductClassificationResourceIT.createUpdatedEntity(em);
            em.persist(cProductClassification);
            em.flush();
        } else {
            cProductClassification = TestUtil.findAll(em, CProductClassification.class).get(0);
        }
        cProduct.setProductClassification(cProductClassification);
        // Add required entity
        CProductCategory cProductCategory;
        if (TestUtil.findAll(em, CProductCategory.class).isEmpty()) {
            cProductCategory = CProductCategoryResourceIT.createUpdatedEntity(em);
            em.persist(cProductCategory);
            em.flush();
        } else {
            cProductCategory = TestUtil.findAll(em, CProductCategory.class).get(0);
        }
        cProduct.setProductCategory(cProductCategory);
        // Add required entity
        cProduct.setProductSubCategory(cProductCategory);
        // Add required entity
        CProductCategoryAccount cProductCategoryAccount;
        if (TestUtil.findAll(em, CProductCategoryAccount.class).isEmpty()) {
            cProductCategoryAccount = CProductCategoryAccountResourceIT.createUpdatedEntity(em);
            em.persist(cProductCategoryAccount);
            em.flush();
        } else {
            cProductCategoryAccount = TestUtil.findAll(em, CProductCategoryAccount.class).get(0);
        }
        cProduct.setAssetAcct(cProductCategoryAccount);
        // Add required entity
        cProduct.setExpenseAcct(cProductCategoryAccount);
        // Add required entity
        CUnitOfMeasure cUnitOfMeasure;
        if (TestUtil.findAll(em, CUnitOfMeasure.class).isEmpty()) {
            cUnitOfMeasure = CUnitOfMeasureResourceIT.createUpdatedEntity(em);
            em.persist(cUnitOfMeasure);
            em.flush();
        } else {
            cUnitOfMeasure = TestUtil.findAll(em, CUnitOfMeasure.class).get(0);
        }
        cProduct.setUom(cUnitOfMeasure);
        return cProduct;
    }

    @BeforeEach
    public void initTest() {
        cProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createCProduct() throws Exception {
        int databaseSizeBeforeCreate = cProductRepository.findAll().size();

        // Create the CProduct
        CProductDTO cProductDTO = cProductMapper.toDto(cProduct);
        restCProductMockMvc.perform(post("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isCreated());

        // Validate the CProduct in the database
        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeCreate + 1);
        CProduct testCProduct = cProductList.get(cProductList.size() - 1);
        assertThat(testCProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCProduct.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCProduct.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCProduct.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cProductRepository.findAll().size();

        // Create the CProduct with an existing ID
        cProduct.setId(1L);
        CProductDTO cProductDTO = cProductMapper.toDto(cProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCProductMockMvc.perform(post("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProduct in the database
        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductRepository.findAll().size();
        // set the field null
        cProduct.setCode(null);

        // Create the CProduct, which fails.
        CProductDTO cProductDTO = cProductMapper.toDto(cProduct);

        restCProductMockMvc.perform(post("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isBadRequest());

        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cProductRepository.findAll().size();
        // set the field null
        cProduct.setName(null);

        // Create the CProduct, which fails.
        CProductDTO cProductDTO = cProductMapper.toDto(cProduct);

        restCProductMockMvc.perform(post("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isBadRequest());

        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCProducts() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList
        restCProductMockMvc.perform(get("/api/c-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCProduct() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get the cProduct
        restCProductMockMvc.perform(get("/api/c-products/{id}", cProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cProduct.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCProductsByIdFiltering() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        Long id = cProduct.getId();

        defaultCProductShouldBeFound("id.equals=" + id);
        defaultCProductShouldNotBeFound("id.notEquals=" + id);

        defaultCProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCProductShouldNotBeFound("id.greaterThan=" + id);

        defaultCProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCProductsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code equals to DEFAULT_CODE
        defaultCProductShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cProductList where code equals to UPDATED_CODE
        defaultCProductShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code not equals to DEFAULT_CODE
        defaultCProductShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cProductList where code not equals to UPDATED_CODE
        defaultCProductShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCProductShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cProductList where code equals to UPDATED_CODE
        defaultCProductShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code is not null
        defaultCProductShouldBeFound("code.specified=true");

        // Get all the cProductList where code is null
        defaultCProductShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductsByCodeContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code contains DEFAULT_CODE
        defaultCProductShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cProductList where code contains UPDATED_CODE
        defaultCProductShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCProductsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where code does not contain DEFAULT_CODE
        defaultCProductShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cProductList where code does not contain UPDATED_CODE
        defaultCProductShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name equals to DEFAULT_NAME
        defaultCProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cProductList where name equals to UPDATED_NAME
        defaultCProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name not equals to DEFAULT_NAME
        defaultCProductShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cProductList where name not equals to UPDATED_NAME
        defaultCProductShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cProductList where name equals to UPDATED_NAME
        defaultCProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name is not null
        defaultCProductShouldBeFound("name.specified=true");

        // Get all the cProductList where name is null
        defaultCProductShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name contains DEFAULT_NAME
        defaultCProductShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cProductList where name contains UPDATED_NAME
        defaultCProductShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where name does not contain DEFAULT_NAME
        defaultCProductShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cProductList where name does not contain UPDATED_NAME
        defaultCProductShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCProductsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description equals to DEFAULT_DESCRIPTION
        defaultCProductShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductList where description equals to UPDATED_DESCRIPTION
        defaultCProductShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description not equals to DEFAULT_DESCRIPTION
        defaultCProductShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cProductList where description not equals to UPDATED_DESCRIPTION
        defaultCProductShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCProductShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cProductList where description equals to UPDATED_DESCRIPTION
        defaultCProductShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description is not null
        defaultCProductShouldBeFound("description.specified=true");

        // Get all the cProductList where description is null
        defaultCProductShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description contains DEFAULT_DESCRIPTION
        defaultCProductShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cProductList where description contains UPDATED_DESCRIPTION
        defaultCProductShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCProductsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where description does not contain DEFAULT_DESCRIPTION
        defaultCProductShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cProductList where description does not contain UPDATED_DESCRIPTION
        defaultCProductShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCProductsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type equals to DEFAULT_TYPE
        defaultCProductShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cProductList where type equals to UPDATED_TYPE
        defaultCProductShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type not equals to DEFAULT_TYPE
        defaultCProductShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cProductList where type not equals to UPDATED_TYPE
        defaultCProductShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCProductShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cProductList where type equals to UPDATED_TYPE
        defaultCProductShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type is not null
        defaultCProductShouldBeFound("type.specified=true");

        // Get all the cProductList where type is null
        defaultCProductShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCProductsByTypeContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type contains DEFAULT_TYPE
        defaultCProductShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cProductList where type contains UPDATED_TYPE
        defaultCProductShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCProductsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where type does not contain DEFAULT_TYPE
        defaultCProductShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cProductList where type does not contain UPDATED_TYPE
        defaultCProductShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCProductsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where uid equals to DEFAULT_UID
        defaultCProductShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cProductList where uid equals to UPDATED_UID
        defaultCProductShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where uid not equals to DEFAULT_UID
        defaultCProductShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cProductList where uid not equals to UPDATED_UID
        defaultCProductShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where uid in DEFAULT_UID or UPDATED_UID
        defaultCProductShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cProductList where uid equals to UPDATED_UID
        defaultCProductShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCProductsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where uid is not null
        defaultCProductShouldBeFound("uid.specified=true");

        // Get all the cProductList where uid is null
        defaultCProductShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where active equals to DEFAULT_ACTIVE
        defaultCProductShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cProductList where active equals to UPDATED_ACTIVE
        defaultCProductShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where active not equals to DEFAULT_ACTIVE
        defaultCProductShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cProductList where active not equals to UPDATED_ACTIVE
        defaultCProductShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCProductShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cProductList where active equals to UPDATED_ACTIVE
        defaultCProductShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCProductsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        // Get all the cProductList where active is not null
        defaultCProductShouldBeFound("active.specified=true");

        // Get all the cProductList where active is null
        defaultCProductShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCProductsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cProduct.getAdOrganization();
        cProductRepository.saveAndFlush(cProduct);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cProductList where adOrganization equals to adOrganizationId
        defaultCProductShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cProductList where adOrganization equals to adOrganizationId + 1
        defaultCProductShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByProductClassificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductClassification productClassification = cProduct.getProductClassification();
        cProductRepository.saveAndFlush(cProduct);
        Long productClassificationId = productClassification.getId();

        // Get all the cProductList where productClassification equals to productClassificationId
        defaultCProductShouldBeFound("productClassificationId.equals=" + productClassificationId);

        // Get all the cProductList where productClassification equals to productClassificationId + 1
        defaultCProductShouldNotBeFound("productClassificationId.equals=" + (productClassificationId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByProductCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductCategory productCategory = cProduct.getProductCategory();
        cProductRepository.saveAndFlush(cProduct);
        Long productCategoryId = productCategory.getId();

        // Get all the cProductList where productCategory equals to productCategoryId
        defaultCProductShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the cProductList where productCategory equals to productCategoryId + 1
        defaultCProductShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByProductSubCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductCategory productSubCategory = cProduct.getProductSubCategory();
        cProductRepository.saveAndFlush(cProduct);
        Long productSubCategoryId = productSubCategory.getId();

        // Get all the cProductList where productSubCategory equals to productSubCategoryId
        defaultCProductShouldBeFound("productSubCategoryId.equals=" + productSubCategoryId);

        // Get all the cProductList where productSubCategory equals to productSubCategoryId + 1
        defaultCProductShouldNotBeFound("productSubCategoryId.equals=" + (productSubCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByAssetAcctIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductCategoryAccount assetAcct = cProduct.getAssetAcct();
        cProductRepository.saveAndFlush(cProduct);
        Long assetAcctId = assetAcct.getId();

        // Get all the cProductList where assetAcct equals to assetAcctId
        defaultCProductShouldBeFound("assetAcctId.equals=" + assetAcctId);

        // Get all the cProductList where assetAcct equals to assetAcctId + 1
        defaultCProductShouldNotBeFound("assetAcctId.equals=" + (assetAcctId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByExpenseAcctIsEqualToSomething() throws Exception {
        // Get already existing entity
        CProductCategoryAccount expenseAcct = cProduct.getExpenseAcct();
        cProductRepository.saveAndFlush(cProduct);
        Long expenseAcctId = expenseAcct.getId();

        // Get all the cProductList where expenseAcct equals to expenseAcctId
        defaultCProductShouldBeFound("expenseAcctId.equals=" + expenseAcctId);

        // Get all the cProductList where expenseAcct equals to expenseAcctId + 1
        defaultCProductShouldNotBeFound("expenseAcctId.equals=" + (expenseAcctId + 1));
    }


    @Test
    @Transactional
    public void getAllCProductsByUomIsEqualToSomething() throws Exception {
        // Get already existing entity
        CUnitOfMeasure uom = cProduct.getUom();
        cProductRepository.saveAndFlush(cProduct);
        Long uomId = uom.getId();

        // Get all the cProductList where uom equals to uomId
        defaultCProductShouldBeFound("uomId.equals=" + uomId);

        // Get all the cProductList where uom equals to uomId + 1
        defaultCProductShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCProductShouldBeFound(String filter) throws Exception {
        restCProductMockMvc.perform(get("/api/c-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCProductMockMvc.perform(get("/api/c-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCProductShouldNotBeFound(String filter) throws Exception {
        restCProductMockMvc.perform(get("/api/c-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCProductMockMvc.perform(get("/api/c-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCProduct() throws Exception {
        // Get the cProduct
        restCProductMockMvc.perform(get("/api/c-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCProduct() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        int databaseSizeBeforeUpdate = cProductRepository.findAll().size();

        // Update the cProduct
        CProduct updatedCProduct = cProductRepository.findById(cProduct.getId()).get();
        // Disconnect from session so that the updates on updatedCProduct are not directly saved in db
        em.detach(updatedCProduct);
        updatedCProduct
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CProductDTO cProductDTO = cProductMapper.toDto(updatedCProduct);

        restCProductMockMvc.perform(put("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isOk());

        // Validate the CProduct in the database
        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeUpdate);
        CProduct testCProduct = cProductList.get(cProductList.size() - 1);
        assertThat(testCProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCProduct.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCProduct.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCProduct() throws Exception {
        int databaseSizeBeforeUpdate = cProductRepository.findAll().size();

        // Create the CProduct
        CProductDTO cProductDTO = cProductMapper.toDto(cProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCProductMockMvc.perform(put("/api/c-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CProduct in the database
        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCProduct() throws Exception {
        // Initialize the database
        cProductRepository.saveAndFlush(cProduct);

        int databaseSizeBeforeDelete = cProductRepository.findAll().size();

        // Delete the cProduct
        restCProductMockMvc.perform(delete("/api/c-products/{id}", cProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CProduct> cProductList = cProductRepository.findAll();
        assertThat(cProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
