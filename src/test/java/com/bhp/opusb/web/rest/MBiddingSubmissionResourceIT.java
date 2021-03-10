package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.MBiddingSubmissionService;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import com.bhp.opusb.service.dto.MBiddingSubmissionCriteria;
import com.bhp.opusb.service.MBiddingSubmissionQueryService;

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
 * Integration tests for the {@link MBiddingSubmissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MBiddingSubmissionResourceIT {

    private static final String DEFAULT_JOIN_BIDDING = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_BIDDING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROPOSED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROPOSED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROPOSED_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CEILING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CEILING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CEILING_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingSubmissionRepository mBiddingSubmissionRepository;

    @Autowired
    private MBiddingSubmissionMapper mBiddingSubmissionMapper;

    @Autowired
    private MBiddingSubmissionService mBiddingSubmissionService;

    @Autowired
    private MBiddingSubmissionQueryService mBiddingSubmissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingSubmissionMockMvc;

    private MBiddingSubmission mBiddingSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmission createEntity(EntityManager em) {
        MBiddingSubmission mBiddingSubmission = new MBiddingSubmission()
            .joinBidding(DEFAULT_JOIN_BIDDING)
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
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
        mBiddingSubmission.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingSubmission.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingSubmission.setVendor(cVendor);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSubmission.setAdOrganization(aDOrganization);
        return mBiddingSubmission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmission createUpdatedEntity(EntityManager em) {
        MBiddingSubmission mBiddingSubmission = new MBiddingSubmission()
            .joinBidding(UPDATED_JOIN_BIDDING)
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .ceilingPrice(UPDATED_CEILING_PRICE)
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
        mBiddingSubmission.setBidding(mBidding);
        // Add required entity
        MBiddingSchedule mBiddingSchedule;
        if (TestUtil.findAll(em, MBiddingSchedule.class).isEmpty()) {
            mBiddingSchedule = MBiddingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSchedule);
            em.flush();
        } else {
            mBiddingSchedule = TestUtil.findAll(em, MBiddingSchedule.class).get(0);
        }
        mBiddingSubmission.setBiddingSchedule(mBiddingSchedule);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mBiddingSubmission.setVendor(cVendor);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSubmission.setAdOrganization(aDOrganization);
        return mBiddingSubmission;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSubmission() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionRepository.findAll().size();
        // Create the MBiddingSubmission
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);
        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSubmission testMBiddingSubmission = mBiddingSubmissionList.get(mBiddingSubmissionList.size() - 1);
        assertThat(testMBiddingSubmission.getJoinBidding()).isEqualTo(DEFAULT_JOIN_BIDDING);
        assertThat(testMBiddingSubmission.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMBiddingSubmission.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMBiddingSubmission.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSubmission.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionRepository.findAll().size();

        // Create the MBiddingSubmission with an existing ID
        mBiddingSubmission.setId(1L);
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionRepository.findAll().size();
        // set the field null
        mBiddingSubmission.setProposedPrice(null);

        // Create the MBiddingSubmission, which fails.
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);


        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCeilingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionRepository.findAll().size();
        // set the field null
        mBiddingSubmission.setCeilingPrice(null);

        // Create the MBiddingSubmission, which fails.
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);


        restMBiddingSubmissionMockMvc.perform(post("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissions() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].joinBidding").value(hasItem(DEFAULT_JOIN_BIDDING)))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/{id}", mBiddingSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSubmission.getId().intValue()))
            .andExpect(jsonPath("$.joinBidding").value(DEFAULT_JOIN_BIDDING))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.ceilingPrice").value(DEFAULT_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        Long id = mBiddingSubmission.getId();

        defaultMBiddingSubmissionShouldBeFound("id.equals=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingSubmissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingSubmissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingSubmissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding equals to DEFAULT_JOIN_BIDDING
        defaultMBiddingSubmissionShouldBeFound("joinBidding.equals=" + DEFAULT_JOIN_BIDDING);

        // Get all the mBiddingSubmissionList where joinBidding equals to UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.equals=" + UPDATED_JOIN_BIDDING);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding not equals to DEFAULT_JOIN_BIDDING
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.notEquals=" + DEFAULT_JOIN_BIDDING);

        // Get all the mBiddingSubmissionList where joinBidding not equals to UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldBeFound("joinBidding.notEquals=" + UPDATED_JOIN_BIDDING);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding in DEFAULT_JOIN_BIDDING or UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldBeFound("joinBidding.in=" + DEFAULT_JOIN_BIDDING + "," + UPDATED_JOIN_BIDDING);

        // Get all the mBiddingSubmissionList where joinBidding equals to UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.in=" + UPDATED_JOIN_BIDDING);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding is not null
        defaultMBiddingSubmissionShouldBeFound("joinBidding.specified=true");

        // Get all the mBiddingSubmissionList where joinBidding is null
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding contains DEFAULT_JOIN_BIDDING
        defaultMBiddingSubmissionShouldBeFound("joinBidding.contains=" + DEFAULT_JOIN_BIDDING);

        // Get all the mBiddingSubmissionList where joinBidding contains UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.contains=" + UPDATED_JOIN_BIDDING);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByJoinBiddingNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where joinBidding does not contain DEFAULT_JOIN_BIDDING
        defaultMBiddingSubmissionShouldNotBeFound("joinBidding.doesNotContain=" + DEFAULT_JOIN_BIDDING);

        // Get all the mBiddingSubmissionList where joinBidding does not contain UPDATED_JOIN_BIDDING
        defaultMBiddingSubmissionShouldBeFound("joinBidding.doesNotContain=" + UPDATED_JOIN_BIDDING);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice is not null
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.specified=true");

        // Get all the mBiddingSubmissionList where proposedPrice is null
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMBiddingSubmissionShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice is not null
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.specified=true");

        // Get all the mBiddingSubmissionList where ceilingPrice is null
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMBiddingSubmissionShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mBiddingSubmissionList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMBiddingSubmissionShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid equals to DEFAULT_UID
        defaultMBiddingSubmissionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid not equals to DEFAULT_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionList where uid not equals to UPDATED_UID
        defaultMBiddingSubmissionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingSubmissionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingSubmissionList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where uid is not null
        defaultMBiddingSubmissionShouldBeFound("uid.specified=true");

        // Get all the mBiddingSubmissionList where uid is null
        defaultMBiddingSubmissionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionList where active not equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingSubmissionList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        // Get all the mBiddingSubmissionList where active is not null
        defaultMBiddingSubmissionShouldBeFound("active.specified=true");

        // Get all the mBiddingSubmissionList where active is null
        defaultMBiddingSubmissionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByMBiddingSubmissionLineIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        MBiddingSubmissionLine mBiddingSubmissionLine = MBiddingSubmissionLineResourceIT.createEntity(em);
        em.persist(mBiddingSubmissionLine);
        em.flush();
        mBiddingSubmission.addMBiddingSubmissionLine(mBiddingSubmissionLine);
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long mBiddingSubmissionLineId = mBiddingSubmissionLine.getId();

        // Get all the mBiddingSubmissionList where mBiddingSubmissionLine equals to mBiddingSubmissionLineId
        defaultMBiddingSubmissionShouldBeFound("mBiddingSubmissionLineId.equals=" + mBiddingSubmissionLineId);

        // Get all the mBiddingSubmissionList where mBiddingSubmissionLine equals to mBiddingSubmissionLineId + 1
        defaultMBiddingSubmissionShouldNotBeFound("mBiddingSubmissionLineId.equals=" + (mBiddingSubmissionLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBiddingSubmission.getBidding();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long biddingId = bidding.getId();

        // Get all the mBiddingSubmissionList where bidding equals to biddingId
        defaultMBiddingSubmissionShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingSubmissionList where bidding equals to biddingId + 1
        defaultMBiddingSubmissionShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByBiddingScheduleIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSchedule biddingSchedule = mBiddingSubmission.getBiddingSchedule();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long biddingScheduleId = biddingSchedule.getId();

        // Get all the mBiddingSubmissionList where biddingSchedule equals to biddingScheduleId
        defaultMBiddingSubmissionShouldBeFound("biddingScheduleId.equals=" + biddingScheduleId);

        // Get all the mBiddingSubmissionList where biddingSchedule equals to biddingScheduleId + 1
        defaultMBiddingSubmissionShouldNotBeFound("biddingScheduleId.equals=" + (biddingScheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mBiddingSubmission.getVendor();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long vendorId = vendor.getId();

        // Get all the mBiddingSubmissionList where vendor equals to vendorId
        defaultMBiddingSubmissionShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBiddingSubmissionList where vendor equals to vendorId + 1
        defaultMBiddingSubmissionShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSubmission.getAdOrganization();
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingSubmissionList where adOrganization equals to adOrganizationId
        defaultMBiddingSubmissionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingSubmissionList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingSubmissionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingSubmissionShouldBeFound(String filter) throws Exception {
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].joinBidding").value(hasItem(DEFAULT_JOIN_BIDDING)))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingSubmissionShouldNotBeFound(String filter) throws Exception {
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMBiddingSubmission() throws Exception {
        // Get the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(get("/api/m-bidding-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        int databaseSizeBeforeUpdate = mBiddingSubmissionRepository.findAll().size();

        // Update the mBiddingSubmission
        MBiddingSubmission updatedMBiddingSubmission = mBiddingSubmissionRepository.findById(mBiddingSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSubmission are not directly saved in db
        em.detach(updatedMBiddingSubmission);
        updatedMBiddingSubmission
            .joinBidding(UPDATED_JOIN_BIDDING)
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(updatedMBiddingSubmission);

        restMBiddingSubmissionMockMvc.perform(put("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSubmission testMBiddingSubmission = mBiddingSubmissionList.get(mBiddingSubmissionList.size() - 1);
        assertThat(testMBiddingSubmission.getJoinBidding()).isEqualTo(UPDATED_JOIN_BIDDING);
        assertThat(testMBiddingSubmission.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMBiddingSubmission.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMBiddingSubmission.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSubmission.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSubmission() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingSubmissionRepository.findAll().size();

        // Create the MBiddingSubmission
        MBiddingSubmissionDTO mBiddingSubmissionDTO = mBiddingSubmissionMapper.toDto(mBiddingSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingSubmissionMockMvc.perform(put("/api/m-bidding-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmission in the database
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSubmission() throws Exception {
        // Initialize the database
        mBiddingSubmissionRepository.saveAndFlush(mBiddingSubmission);

        int databaseSizeBeforeDelete = mBiddingSubmissionRepository.findAll().size();

        // Delete the mBiddingSubmission
        restMBiddingSubmissionMockMvc.perform(delete("/api/m-bidding-submissions/{id}", mBiddingSubmission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSubmission> mBiddingSubmissionList = mBiddingSubmissionRepository.findAll();
        assertThat(mBiddingSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
