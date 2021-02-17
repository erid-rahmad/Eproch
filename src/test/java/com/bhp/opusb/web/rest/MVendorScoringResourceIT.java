package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorScoring;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MVendorScoringRepository;
import com.bhp.opusb.service.MVendorScoringService;
import com.bhp.opusb.service.dto.MVendorScoringDTO;
import com.bhp.opusb.service.mapper.MVendorScoringMapper;
import com.bhp.opusb.service.dto.MVendorScoringCriteria;
import com.bhp.opusb.service.MVendorScoringQueryService;

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
 * Integration tests for the {@link MVendorScoringResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorScoringResourceIT {

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PERCENTAGE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MVendorScoringRepository mVendorScoringRepository;

    @Autowired
    private MVendorScoringMapper mVendorScoringMapper;

    @Autowired
    private MVendorScoringService mVendorScoringService;

    @Autowired
    private MVendorScoringQueryService mVendorScoringQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorScoringMockMvc;

    private MVendorScoring mVendorScoring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoring createEntity(EntityManager em) {
        MVendorScoring mVendorScoring = new MVendorScoring()
            .percentage(DEFAULT_PERCENTAGE)
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
        mVendorScoring.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorScoring.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        mVendorScoring.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mVendorScoring.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mVendorScoring.setAdUser(adUser);
        return mVendorScoring;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorScoring createUpdatedEntity(EntityManager em) {
        MVendorScoring mVendorScoring = new MVendorScoring()
            .percentage(UPDATED_PERCENTAGE)
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
        mVendorScoring.setBidding(mBidding);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorScoring.setAdOrganization(aDOrganization);
        // Add required entity
        CBiddingCriteria cBiddingCriteria;
        if (TestUtil.findAll(em, CBiddingCriteria.class).isEmpty()) {
            cBiddingCriteria = CBiddingCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingCriteria);
            em.flush();
        } else {
            cBiddingCriteria = TestUtil.findAll(em, CBiddingCriteria.class).get(0);
        }
        mVendorScoring.setBiddingCriteria(cBiddingCriteria);
        // Add required entity
        CBiddingSubCriteria cBiddingSubCriteria;
        if (TestUtil.findAll(em, CBiddingSubCriteria.class).isEmpty()) {
            cBiddingSubCriteria = CBiddingSubCriteriaResourceIT.createUpdatedEntity(em);
            em.persist(cBiddingSubCriteria);
            em.flush();
        } else {
            cBiddingSubCriteria = TestUtil.findAll(em, CBiddingSubCriteria.class).get(0);
        }
        mVendorScoring.setBiddingSubCriteria(cBiddingSubCriteria);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mVendorScoring.setAdUser(adUser);
        return mVendorScoring;
    }

    @BeforeEach
    public void initTest() {
        mVendorScoring = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorScoring() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringRepository.findAll().size();

        // Create the MVendorScoring
        MVendorScoringDTO mVendorScoringDTO = mVendorScoringMapper.toDto(mVendorScoring);
        restMVendorScoringMockMvc.perform(post("/api/m-vendor-scorings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorScoring in the database
        List<MVendorScoring> mVendorScoringList = mVendorScoringRepository.findAll();
        assertThat(mVendorScoringList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorScoring testMVendorScoring = mVendorScoringList.get(mVendorScoringList.size() - 1);
        assertThat(testMVendorScoring.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testMVendorScoring.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorScoring.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMVendorScoringWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorScoringRepository.findAll().size();

        // Create the MVendorScoring with an existing ID
        mVendorScoring.setId(1L);
        MVendorScoringDTO mVendorScoringDTO = mVendorScoringMapper.toDto(mVendorScoring);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorScoringMockMvc.perform(post("/api/m-vendor-scorings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoring in the database
        List<MVendorScoring> mVendorScoringList = mVendorScoringRepository.findAll();
        assertThat(mVendorScoringList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorScorings() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoring.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMVendorScoring() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get the mVendorScoring
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings/{id}", mVendorScoring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorScoring.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMVendorScoringsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        Long id = mVendorScoring.getId();

        defaultMVendorScoringShouldBeFound("id.equals=" + id);
        defaultMVendorScoringShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorScoringShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorScoringShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorScoringShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorScoringShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage equals to DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage equals to UPDATED_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage not equals to DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.notEquals=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage not equals to UPDATED_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.notEquals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the mVendorScoringList where percentage equals to UPDATED_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage is not null
        defaultMVendorScoringShouldBeFound("percentage.specified=true");

        // Get all the mVendorScoringList where percentage is null
        defaultMVendorScoringShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage is less than DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage is less than UPDATED_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where percentage is greater than DEFAULT_PERCENTAGE
        defaultMVendorScoringShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the mVendorScoringList where percentage is greater than SMALLER_PERCENTAGE
        defaultMVendorScoringShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where uid equals to DEFAULT_UID
        defaultMVendorScoringShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorScoringList where uid equals to UPDATED_UID
        defaultMVendorScoringShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where uid not equals to DEFAULT_UID
        defaultMVendorScoringShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorScoringList where uid not equals to UPDATED_UID
        defaultMVendorScoringShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorScoringShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorScoringList where uid equals to UPDATED_UID
        defaultMVendorScoringShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where uid is not null
        defaultMVendorScoringShouldBeFound("uid.specified=true");

        // Get all the mVendorScoringList where uid is null
        defaultMVendorScoringShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where active equals to DEFAULT_ACTIVE
        defaultMVendorScoringShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where active not equals to DEFAULT_ACTIVE
        defaultMVendorScoringShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorScoringList where active not equals to UPDATED_ACTIVE
        defaultMVendorScoringShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorScoringShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorScoringList where active equals to UPDATED_ACTIVE
        defaultMVendorScoringShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        // Get all the mVendorScoringList where active is not null
        defaultMVendorScoringShouldBeFound("active.specified=true");

        // Get all the mVendorScoringList where active is null
        defaultMVendorScoringShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorScoringsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mVendorScoring.getBidding();
        mVendorScoringRepository.saveAndFlush(mVendorScoring);
        Long biddingId = bidding.getId();

        // Get all the mVendorScoringList where bidding equals to biddingId
        defaultMVendorScoringShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mVendorScoringList where bidding equals to biddingId + 1
        defaultMVendorScoringShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorScoring.getAdOrganization();
        mVendorScoringRepository.saveAndFlush(mVendorScoring);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorScoringList where adOrganization equals to adOrganizationId
        defaultMVendorScoringShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorScoringList where adOrganization equals to adOrganizationId + 1
        defaultMVendorScoringShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByBiddingCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingCriteria biddingCriteria = mVendorScoring.getBiddingCriteria();
        mVendorScoringRepository.saveAndFlush(mVendorScoring);
        Long biddingCriteriaId = biddingCriteria.getId();

        // Get all the mVendorScoringList where biddingCriteria equals to biddingCriteriaId
        defaultMVendorScoringShouldBeFound("biddingCriteriaId.equals=" + biddingCriteriaId);

        // Get all the mVendorScoringList where biddingCriteria equals to biddingCriteriaId + 1
        defaultMVendorScoringShouldNotBeFound("biddingCriteriaId.equals=" + (biddingCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByBiddingSubCriteriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CBiddingSubCriteria biddingSubCriteria = mVendorScoring.getBiddingSubCriteria();
        mVendorScoringRepository.saveAndFlush(mVendorScoring);
        Long biddingSubCriteriaId = biddingSubCriteria.getId();

        // Get all the mVendorScoringList where biddingSubCriteria equals to biddingSubCriteriaId
        defaultMVendorScoringShouldBeFound("biddingSubCriteriaId.equals=" + biddingSubCriteriaId);

        // Get all the mVendorScoringList where biddingSubCriteria equals to biddingSubCriteriaId + 1
        defaultMVendorScoringShouldNotBeFound("biddingSubCriteriaId.equals=" + (biddingSubCriteriaId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorScoringsByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = mVendorScoring.getAdUser();
        mVendorScoringRepository.saveAndFlush(mVendorScoring);
        Long adUserId = adUser.getId();

        // Get all the mVendorScoringList where adUser equals to adUserId
        defaultMVendorScoringShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the mVendorScoringList where adUser equals to adUserId + 1
        defaultMVendorScoringShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorScoringShouldBeFound(String filter) throws Exception {
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorScoring.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorScoringShouldNotBeFound(String filter) throws Exception {
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorScoring() throws Exception {
        // Get the mVendorScoring
        restMVendorScoringMockMvc.perform(get("/api/m-vendor-scorings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorScoring() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        int databaseSizeBeforeUpdate = mVendorScoringRepository.findAll().size();

        // Update the mVendorScoring
        MVendorScoring updatedMVendorScoring = mVendorScoringRepository.findById(mVendorScoring.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorScoring are not directly saved in db
        em.detach(updatedMVendorScoring);
        updatedMVendorScoring
            .percentage(UPDATED_PERCENTAGE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MVendorScoringDTO mVendorScoringDTO = mVendorScoringMapper.toDto(updatedMVendorScoring);

        restMVendorScoringMockMvc.perform(put("/api/m-vendor-scorings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorScoring in the database
        List<MVendorScoring> mVendorScoringList = mVendorScoringRepository.findAll();
        assertThat(mVendorScoringList).hasSize(databaseSizeBeforeUpdate);
        MVendorScoring testMVendorScoring = mVendorScoringList.get(mVendorScoringList.size() - 1);
        assertThat(testMVendorScoring.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testMVendorScoring.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorScoring.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorScoring() throws Exception {
        int databaseSizeBeforeUpdate = mVendorScoringRepository.findAll().size();

        // Create the MVendorScoring
        MVendorScoringDTO mVendorScoringDTO = mVendorScoringMapper.toDto(mVendorScoring);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorScoringMockMvc.perform(put("/api/m-vendor-scorings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorScoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorScoring in the database
        List<MVendorScoring> mVendorScoringList = mVendorScoringRepository.findAll();
        assertThat(mVendorScoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorScoring() throws Exception {
        // Initialize the database
        mVendorScoringRepository.saveAndFlush(mVendorScoring);

        int databaseSizeBeforeDelete = mVendorScoringRepository.findAll().size();

        // Delete the mVendorScoring
        restMVendorScoringMockMvc.perform(delete("/api/m-vendor-scorings/{id}", mVendorScoring.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorScoring> mVendorScoringList = mVendorScoringRepository.findAll();
        assertThat(mVendorScoringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
