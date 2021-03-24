package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CEvaluationMethod;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.repository.CEvaluationMethodRepository;
import com.bhp.opusb.service.CEvaluationMethodService;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodMapper;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteria;
import com.bhp.opusb.service.CEvaluationMethodQueryService;

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
 * Integration tests for the {@link CEvaluationMethodResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CEvaluationMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_LIMIT = new BigDecimal(100);
    private static final BigDecimal UPDATED_PRICE_LIMIT = new BigDecimal(99);
    private static final BigDecimal SMALLER_PRICE_LIMIT = new BigDecimal(100 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CEvaluationMethodRepository cEvaluationMethodRepository;

    @Autowired
    private CEvaluationMethodMapper cEvaluationMethodMapper;

    @Autowired
    private CEvaluationMethodService cEvaluationMethodService;

    @Autowired
    private CEvaluationMethodQueryService cEvaluationMethodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCEvaluationMethodMockMvc;

    private CEvaluationMethod cEvaluationMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethod createEntity(EntityManager em) {
        CEvaluationMethod cEvaluationMethod = new CEvaluationMethod()
            .name(DEFAULT_NAME)
            .priceLimit(DEFAULT_PRICE_LIMIT)
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
        cEvaluationMethod.setAdOrganization(aDOrganization);
        return cEvaluationMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEvaluationMethod createUpdatedEntity(EntityManager em) {
        CEvaluationMethod cEvaluationMethod = new CEvaluationMethod()
            .name(UPDATED_NAME)
            .priceLimit(UPDATED_PRICE_LIMIT)
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
        cEvaluationMethod.setAdOrganization(aDOrganization);
        return cEvaluationMethod;
    }

    @BeforeEach
    public void initTest() {
        cEvaluationMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEvaluationMethod() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodRepository.findAll().size();

        // Create the CEvaluationMethod
        CEvaluationMethodDTO cEvaluationMethodDTO = cEvaluationMethodMapper.toDto(cEvaluationMethod);
        restCEvaluationMethodMockMvc.perform(post("/api/c-evaluation-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the CEvaluationMethod in the database
        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeCreate + 1);
        CEvaluationMethod testCEvaluationMethod = cEvaluationMethodList.get(cEvaluationMethodList.size() - 1);
        assertThat(testCEvaluationMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCEvaluationMethod.getPriceLimit()).isEqualTo(DEFAULT_PRICE_LIMIT);
        assertThat(testCEvaluationMethod.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCEvaluationMethod.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCEvaluationMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEvaluationMethodRepository.findAll().size();

        // Create the CEvaluationMethod with an existing ID
        cEvaluationMethod.setId(1L);
        CEvaluationMethodDTO cEvaluationMethodDTO = cEvaluationMethodMapper.toDto(cEvaluationMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEvaluationMethodMockMvc.perform(post("/api/c-evaluation-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethod in the database
        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEvaluationMethodRepository.findAll().size();
        // set the field null
        cEvaluationMethod.setName(null);

        // Create the CEvaluationMethod, which fails.
        CEvaluationMethodDTO cEvaluationMethodDTO = cEvaluationMethodMapper.toDto(cEvaluationMethod);

        restCEvaluationMethodMockMvc.perform(post("/api/c-evaluation-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodDTO)))
            .andExpect(status().isBadRequest());

        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethods() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceLimit").value(hasItem(DEFAULT_PRICE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCEvaluationMethod() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get the cEvaluationMethod
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods/{id}", cEvaluationMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cEvaluationMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priceLimit").value(DEFAULT_PRICE_LIMIT.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCEvaluationMethodsByIdFiltering() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        Long id = cEvaluationMethod.getId();

        defaultCEvaluationMethodShouldBeFound("id.equals=" + id);
        defaultCEvaluationMethodShouldNotBeFound("id.notEquals=" + id);

        defaultCEvaluationMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCEvaluationMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultCEvaluationMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCEvaluationMethodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name equals to DEFAULT_NAME
        defaultCEvaluationMethodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cEvaluationMethodList where name equals to UPDATED_NAME
        defaultCEvaluationMethodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name not equals to DEFAULT_NAME
        defaultCEvaluationMethodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cEvaluationMethodList where name not equals to UPDATED_NAME
        defaultCEvaluationMethodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCEvaluationMethodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cEvaluationMethodList where name equals to UPDATED_NAME
        defaultCEvaluationMethodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name is not null
        defaultCEvaluationMethodShouldBeFound("name.specified=true");

        // Get all the cEvaluationMethodList where name is null
        defaultCEvaluationMethodShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name contains DEFAULT_NAME
        defaultCEvaluationMethodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cEvaluationMethodList where name contains UPDATED_NAME
        defaultCEvaluationMethodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where name does not contain DEFAULT_NAME
        defaultCEvaluationMethodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cEvaluationMethodList where name does not contain UPDATED_NAME
        defaultCEvaluationMethodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit equals to DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.equals=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit equals to UPDATED_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.equals=" + UPDATED_PRICE_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit not equals to DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.notEquals=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit not equals to UPDATED_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.notEquals=" + UPDATED_PRICE_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit in DEFAULT_PRICE_LIMIT or UPDATED_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.in=" + DEFAULT_PRICE_LIMIT + "," + UPDATED_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit equals to UPDATED_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.in=" + UPDATED_PRICE_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit is not null
        defaultCEvaluationMethodShouldBeFound("priceLimit.specified=true");

        // Get all the cEvaluationMethodList where priceLimit is null
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit is greater than or equal to DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.greaterThanOrEqual=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit is greater than or equal to (DEFAULT_PRICE_LIMIT.add(BigDecimal.ONE))
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.greaterThanOrEqual=" + (DEFAULT_PRICE_LIMIT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit is less than or equal to DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.lessThanOrEqual=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit is less than or equal to SMALLER_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.lessThanOrEqual=" + SMALLER_PRICE_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit is less than DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.lessThan=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit is less than (DEFAULT_PRICE_LIMIT.add(BigDecimal.ONE))
        defaultCEvaluationMethodShouldBeFound("priceLimit.lessThan=" + (DEFAULT_PRICE_LIMIT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByPriceLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where priceLimit is greater than DEFAULT_PRICE_LIMIT
        defaultCEvaluationMethodShouldNotBeFound("priceLimit.greaterThan=" + DEFAULT_PRICE_LIMIT);

        // Get all the cEvaluationMethodList where priceLimit is greater than SMALLER_PRICE_LIMIT
        defaultCEvaluationMethodShouldBeFound("priceLimit.greaterThan=" + SMALLER_PRICE_LIMIT);
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where uid equals to DEFAULT_UID
        defaultCEvaluationMethodShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodList where uid equals to UPDATED_UID
        defaultCEvaluationMethodShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where uid not equals to DEFAULT_UID
        defaultCEvaluationMethodShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cEvaluationMethodList where uid not equals to UPDATED_UID
        defaultCEvaluationMethodShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where uid in DEFAULT_UID or UPDATED_UID
        defaultCEvaluationMethodShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cEvaluationMethodList where uid equals to UPDATED_UID
        defaultCEvaluationMethodShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where uid is not null
        defaultCEvaluationMethodShouldBeFound("uid.specified=true");

        // Get all the cEvaluationMethodList where uid is null
        defaultCEvaluationMethodShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where active equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where active not equals to DEFAULT_ACTIVE
        defaultCEvaluationMethodShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cEvaluationMethodList where active not equals to UPDATED_ACTIVE
        defaultCEvaluationMethodShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCEvaluationMethodShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cEvaluationMethodList where active equals to UPDATED_ACTIVE
        defaultCEvaluationMethodShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        // Get all the cEvaluationMethodList where active is not null
        defaultCEvaluationMethodShouldBeFound("active.specified=true");

        // Get all the cEvaluationMethodList where active is null
        defaultCEvaluationMethodShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCEvaluationMethodsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cEvaluationMethod.getAdOrganization();
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cEvaluationMethodList where adOrganization equals to adOrganizationId
        defaultCEvaluationMethodShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cEvaluationMethodList where adOrganization equals to adOrganizationId + 1
        defaultCEvaluationMethodShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCEvaluationMethodsByBiddingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);
        CBiddingType biddingType = CBiddingTypeResourceIT.createEntity(em);
        em.persist(biddingType);
        em.flush();
        cEvaluationMethod.setBiddingType(biddingType);
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);
        Long biddingTypeId = biddingType.getId();

        // Get all the cEvaluationMethodList where biddingType equals to biddingTypeId
        defaultCEvaluationMethodShouldBeFound("biddingTypeId.equals=" + biddingTypeId);

        // Get all the cEvaluationMethodList where biddingType equals to biddingTypeId + 1
        defaultCEvaluationMethodShouldNotBeFound("biddingTypeId.equals=" + (biddingTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCEvaluationMethodShouldBeFound(String filter) throws Exception {
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEvaluationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceLimit").value(hasItem(DEFAULT_PRICE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCEvaluationMethodShouldNotBeFound(String filter) throws Exception {
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCEvaluationMethod() throws Exception {
        // Get the cEvaluationMethod
        restCEvaluationMethodMockMvc.perform(get("/api/c-evaluation-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEvaluationMethod() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        int databaseSizeBeforeUpdate = cEvaluationMethodRepository.findAll().size();

        // Update the cEvaluationMethod
        CEvaluationMethod updatedCEvaluationMethod = cEvaluationMethodRepository.findById(cEvaluationMethod.getId()).get();
        // Disconnect from session so that the updates on updatedCEvaluationMethod are not directly saved in db
        em.detach(updatedCEvaluationMethod);
        updatedCEvaluationMethod
            .name(UPDATED_NAME)
            .priceLimit(UPDATED_PRICE_LIMIT)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CEvaluationMethodDTO cEvaluationMethodDTO = cEvaluationMethodMapper.toDto(updatedCEvaluationMethod);

        restCEvaluationMethodMockMvc.perform(put("/api/c-evaluation-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodDTO)))
            .andExpect(status().isOk());

        // Validate the CEvaluationMethod in the database
        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeUpdate);
        CEvaluationMethod testCEvaluationMethod = cEvaluationMethodList.get(cEvaluationMethodList.size() - 1);
        assertThat(testCEvaluationMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCEvaluationMethod.getPriceLimit()).isEqualTo(UPDATED_PRICE_LIMIT);
        assertThat(testCEvaluationMethod.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCEvaluationMethod.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCEvaluationMethod() throws Exception {
        int databaseSizeBeforeUpdate = cEvaluationMethodRepository.findAll().size();

        // Create the CEvaluationMethod
        CEvaluationMethodDTO cEvaluationMethodDTO = cEvaluationMethodMapper.toDto(cEvaluationMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEvaluationMethodMockMvc.perform(put("/api/c-evaluation-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cEvaluationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEvaluationMethod in the database
        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEvaluationMethod() throws Exception {
        // Initialize the database
        cEvaluationMethodRepository.saveAndFlush(cEvaluationMethod);

        int databaseSizeBeforeDelete = cEvaluationMethodRepository.findAll().size();

        // Delete the cEvaluationMethod
        restCEvaluationMethodMockMvc.perform(delete("/api/c-evaluation-methods/{id}", cEvaluationMethod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEvaluationMethod> cEvaluationMethodList = cEvaluationMethodRepository.findAll();
        assertThat(cEvaluationMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
