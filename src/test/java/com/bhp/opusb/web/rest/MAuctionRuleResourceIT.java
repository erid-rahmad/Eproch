package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MAuctionRuleRepository;
import com.bhp.opusb.service.MAuctionRuleService;
import com.bhp.opusb.service.dto.MAuctionRuleDTO;
import com.bhp.opusb.service.mapper.MAuctionRuleMapper;
import com.bhp.opusb.service.dto.MAuctionRuleCriteria;
import com.bhp.opusb.service.MAuctionRuleQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.bhp.opusb.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MAuctionRuleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionRuleResourceIT {

    private static final String DEFAULT_BID_PREV_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_BID_PREV_PERIOD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PRE_BID_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRE_BID_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PRE_BID_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_FIRST_LOT_RUN_TIME = 1;
    private static final Integer UPDATED_FIRST_LOT_RUN_TIME = 2;
    private static final Integer SMALLER_FIRST_LOT_RUN_TIME = 1 - 1;

    private static final Integer DEFAULT_BID_RANK_OVERTIME = 1;
    private static final Integer UPDATED_BID_RANK_OVERTIME = 2;
    private static final Integer SMALLER_BID_RANK_OVERTIME = 1 - 1;

    private static final Integer DEFAULT_START_OVERTIME_WITHIN = 1;
    private static final Integer UPDATED_START_OVERTIME_WITHIN = 2;
    private static final Integer SMALLER_START_OVERTIME_WITHIN = 1 - 1;

    private static final Integer DEFAULT_OVERTIME_PERIOD = 1;
    private static final Integer UPDATED_OVERTIME_PERIOD = 2;
    private static final Integer SMALLER_OVERTIME_PERIOD = 1 - 1;

    private static final LocalDate DEFAULT_EST_AWARD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EST_AWARD_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EST_AWARD_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_BID_IMPROVEMENT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_BID_IMPROVEMENT_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_TIE_BIDS_RULE = "AAAAAAAAAA";
    private static final String UPDATED_TIE_BIDS_RULE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_RESPONSE = false;
    private static final Boolean UPDATED_SHOW_RESPONSE = true;

    private static final Boolean DEFAULT_SHOW_LEADER = false;
    private static final Boolean UPDATED_SHOW_LEADER = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionRuleRepository mAuctionRuleRepository;

    @Autowired
    private MAuctionRuleMapper mAuctionRuleMapper;

    @Autowired
    private MAuctionRuleService mAuctionRuleService;

    @Autowired
    private MAuctionRuleQueryService mAuctionRuleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionRuleMockMvc;

    private MAuctionRule mAuctionRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionRule createEntity(EntityManager em) {
        MAuctionRule mAuctionRule = new MAuctionRule()
            .bidPrevPeriod(DEFAULT_BID_PREV_PERIOD)
            .preBidEndDate(DEFAULT_PRE_BID_END_DATE)
            .startDate(DEFAULT_START_DATE)
            .firstLotRunTime(DEFAULT_FIRST_LOT_RUN_TIME)
            .bidRankOvertime(DEFAULT_BID_RANK_OVERTIME)
            .startOvertimeWithin(DEFAULT_START_OVERTIME_WITHIN)
            .overtimePeriod(DEFAULT_OVERTIME_PERIOD)
            .estAwardDate(DEFAULT_EST_AWARD_DATE)
            .bidImprovementUnit(DEFAULT_BID_IMPROVEMENT_UNIT)
            .tieBidsRule(DEFAULT_TIE_BIDS_RULE)
            .showResponse(DEFAULT_SHOW_RESPONSE)
            .showLeader(DEFAULT_SHOW_LEADER)
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
        mAuctionRule.setAdOrganization(aDOrganization);
        return mAuctionRule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionRule createUpdatedEntity(EntityManager em) {
        MAuctionRule mAuctionRule = new MAuctionRule()
            .bidPrevPeriod(UPDATED_BID_PREV_PERIOD)
            .preBidEndDate(UPDATED_PRE_BID_END_DATE)
            .startDate(UPDATED_START_DATE)
            .firstLotRunTime(UPDATED_FIRST_LOT_RUN_TIME)
            .bidRankOvertime(UPDATED_BID_RANK_OVERTIME)
            .startOvertimeWithin(UPDATED_START_OVERTIME_WITHIN)
            .overtimePeriod(UPDATED_OVERTIME_PERIOD)
            .estAwardDate(UPDATED_EST_AWARD_DATE)
            .bidImprovementUnit(UPDATED_BID_IMPROVEMENT_UNIT)
            .tieBidsRule(UPDATED_TIE_BIDS_RULE)
            .showResponse(UPDATED_SHOW_RESPONSE)
            .showLeader(UPDATED_SHOW_LEADER)
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
        mAuctionRule.setAdOrganization(aDOrganization);
        return mAuctionRule;
    }

    @BeforeEach
    public void initTest() {
        mAuctionRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionRule() throws Exception {
        int databaseSizeBeforeCreate = mAuctionRuleRepository.findAll().size();

        // Create the MAuctionRule
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);
        restMAuctionRuleMockMvc.perform(post("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionRule in the database
        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionRule testMAuctionRule = mAuctionRuleList.get(mAuctionRuleList.size() - 1);
        assertThat(testMAuctionRule.getBidPrevPeriod()).isEqualTo(DEFAULT_BID_PREV_PERIOD);
        assertThat(testMAuctionRule.getPreBidEndDate()).isEqualTo(DEFAULT_PRE_BID_END_DATE);
        assertThat(testMAuctionRule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMAuctionRule.getFirstLotRunTime()).isEqualTo(DEFAULT_FIRST_LOT_RUN_TIME);
        assertThat(testMAuctionRule.getBidRankOvertime()).isEqualTo(DEFAULT_BID_RANK_OVERTIME);
        assertThat(testMAuctionRule.getStartOvertimeWithin()).isEqualTo(DEFAULT_START_OVERTIME_WITHIN);
        assertThat(testMAuctionRule.getOvertimePeriod()).isEqualTo(DEFAULT_OVERTIME_PERIOD);
        assertThat(testMAuctionRule.getEstAwardDate()).isEqualTo(DEFAULT_EST_AWARD_DATE);
        assertThat(testMAuctionRule.getBidImprovementUnit()).isEqualTo(DEFAULT_BID_IMPROVEMENT_UNIT);
        assertThat(testMAuctionRule.getTieBidsRule()).isEqualTo(DEFAULT_TIE_BIDS_RULE);
        assertThat(testMAuctionRule.isShowResponse()).isEqualTo(DEFAULT_SHOW_RESPONSE);
        assertThat(testMAuctionRule.isShowLeader()).isEqualTo(DEFAULT_SHOW_LEADER);
        assertThat(testMAuctionRule.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionRule.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionRuleRepository.findAll().size();

        // Create the MAuctionRule with an existing ID
        mAuctionRule.setId(1L);
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionRuleMockMvc.perform(post("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionRule in the database
        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBidPrevPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRuleRepository.findAll().size();
        // set the field null
        mAuctionRule.setBidPrevPeriod(null);

        // Create the MAuctionRule, which fails.
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);

        restMAuctionRuleMockMvc.perform(post("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBidImprovementUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRuleRepository.findAll().size();
        // set the field null
        mAuctionRule.setBidImprovementUnit(null);

        // Create the MAuctionRule, which fails.
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);

        restMAuctionRuleMockMvc.perform(post("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTieBidsRuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionRuleRepository.findAll().size();
        // set the field null
        mAuctionRule.setTieBidsRule(null);

        // Create the MAuctionRule, which fails.
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);

        restMAuctionRuleMockMvc.perform(post("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionRules() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidPrevPeriod").value(hasItem(DEFAULT_BID_PREV_PERIOD)))
            .andExpect(jsonPath("$.[*].preBidEndDate").value(hasItem(sameInstant(DEFAULT_PRE_BID_END_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].firstLotRunTime").value(hasItem(DEFAULT_FIRST_LOT_RUN_TIME)))
            .andExpect(jsonPath("$.[*].bidRankOvertime").value(hasItem(DEFAULT_BID_RANK_OVERTIME)))
            .andExpect(jsonPath("$.[*].startOvertimeWithin").value(hasItem(DEFAULT_START_OVERTIME_WITHIN)))
            .andExpect(jsonPath("$.[*].overtimePeriod").value(hasItem(DEFAULT_OVERTIME_PERIOD)))
            .andExpect(jsonPath("$.[*].estAwardDate").value(hasItem(DEFAULT_EST_AWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].bidImprovementUnit").value(hasItem(DEFAULT_BID_IMPROVEMENT_UNIT)))
            .andExpect(jsonPath("$.[*].tieBidsRule").value(hasItem(DEFAULT_TIE_BIDS_RULE)))
            .andExpect(jsonPath("$.[*].showResponse").value(hasItem(DEFAULT_SHOW_RESPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].showLeader").value(hasItem(DEFAULT_SHOW_LEADER.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionRule() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get the mAuctionRule
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules/{id}", mAuctionRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionRule.getId().intValue()))
            .andExpect(jsonPath("$.bidPrevPeriod").value(DEFAULT_BID_PREV_PERIOD))
            .andExpect(jsonPath("$.preBidEndDate").value(sameInstant(DEFAULT_PRE_BID_END_DATE)))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.firstLotRunTime").value(DEFAULT_FIRST_LOT_RUN_TIME))
            .andExpect(jsonPath("$.bidRankOvertime").value(DEFAULT_BID_RANK_OVERTIME))
            .andExpect(jsonPath("$.startOvertimeWithin").value(DEFAULT_START_OVERTIME_WITHIN))
            .andExpect(jsonPath("$.overtimePeriod").value(DEFAULT_OVERTIME_PERIOD))
            .andExpect(jsonPath("$.estAwardDate").value(DEFAULT_EST_AWARD_DATE.toString()))
            .andExpect(jsonPath("$.bidImprovementUnit").value(DEFAULT_BID_IMPROVEMENT_UNIT))
            .andExpect(jsonPath("$.tieBidsRule").value(DEFAULT_TIE_BIDS_RULE))
            .andExpect(jsonPath("$.showResponse").value(DEFAULT_SHOW_RESPONSE.booleanValue()))
            .andExpect(jsonPath("$.showLeader").value(DEFAULT_SHOW_LEADER.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionRulesByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        Long id = mAuctionRule.getId();

        defaultMAuctionRuleShouldBeFound("id.equals=" + id);
        defaultMAuctionRuleShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionRuleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionRuleShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionRuleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionRuleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod equals to DEFAULT_BID_PREV_PERIOD
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.equals=" + DEFAULT_BID_PREV_PERIOD);

        // Get all the mAuctionRuleList where bidPrevPeriod equals to UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.equals=" + UPDATED_BID_PREV_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod not equals to DEFAULT_BID_PREV_PERIOD
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.notEquals=" + DEFAULT_BID_PREV_PERIOD);

        // Get all the mAuctionRuleList where bidPrevPeriod not equals to UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.notEquals=" + UPDATED_BID_PREV_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod in DEFAULT_BID_PREV_PERIOD or UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.in=" + DEFAULT_BID_PREV_PERIOD + "," + UPDATED_BID_PREV_PERIOD);

        // Get all the mAuctionRuleList where bidPrevPeriod equals to UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.in=" + UPDATED_BID_PREV_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod is not null
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.specified=true");

        // Get all the mAuctionRuleList where bidPrevPeriod is null
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod contains DEFAULT_BID_PREV_PERIOD
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.contains=" + DEFAULT_BID_PREV_PERIOD);

        // Get all the mAuctionRuleList where bidPrevPeriod contains UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.contains=" + UPDATED_BID_PREV_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidPrevPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidPrevPeriod does not contain DEFAULT_BID_PREV_PERIOD
        defaultMAuctionRuleShouldNotBeFound("bidPrevPeriod.doesNotContain=" + DEFAULT_BID_PREV_PERIOD);

        // Get all the mAuctionRuleList where bidPrevPeriod does not contain UPDATED_BID_PREV_PERIOD
        defaultMAuctionRuleShouldBeFound("bidPrevPeriod.doesNotContain=" + UPDATED_BID_PREV_PERIOD);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate equals to DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.equals=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate equals to UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.equals=" + UPDATED_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate not equals to DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.notEquals=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate not equals to UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.notEquals=" + UPDATED_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate in DEFAULT_PRE_BID_END_DATE or UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.in=" + DEFAULT_PRE_BID_END_DATE + "," + UPDATED_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate equals to UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.in=" + UPDATED_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate is not null
        defaultMAuctionRuleShouldBeFound("preBidEndDate.specified=true");

        // Get all the mAuctionRuleList where preBidEndDate is null
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate is greater than or equal to DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.greaterThanOrEqual=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate is greater than or equal to UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.greaterThanOrEqual=" + UPDATED_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate is less than or equal to DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.lessThanOrEqual=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate is less than or equal to SMALLER_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.lessThanOrEqual=" + SMALLER_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate is less than DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.lessThan=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate is less than UPDATED_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.lessThan=" + UPDATED_PRE_BID_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByPreBidEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where preBidEndDate is greater than DEFAULT_PRE_BID_END_DATE
        defaultMAuctionRuleShouldNotBeFound("preBidEndDate.greaterThan=" + DEFAULT_PRE_BID_END_DATE);

        // Get all the mAuctionRuleList where preBidEndDate is greater than SMALLER_PRE_BID_END_DATE
        defaultMAuctionRuleShouldBeFound("preBidEndDate.greaterThan=" + SMALLER_PRE_BID_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate equals to DEFAULT_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate equals to UPDATED_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate not equals to DEFAULT_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate not equals to UPDATED_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mAuctionRuleList where startDate equals to UPDATED_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate is not null
        defaultMAuctionRuleShouldBeFound("startDate.specified=true");

        // Get all the mAuctionRuleList where startDate is null
        defaultMAuctionRuleShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate is greater than or equal to UPDATED_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate is less than or equal to DEFAULT_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate is less than or equal to SMALLER_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate is less than DEFAULT_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate is less than UPDATED_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startDate is greater than DEFAULT_START_DATE
        defaultMAuctionRuleShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the mAuctionRuleList where startDate is greater than SMALLER_START_DATE
        defaultMAuctionRuleShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime equals to DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.equals=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime equals to UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.equals=" + UPDATED_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime not equals to DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.notEquals=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime not equals to UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.notEquals=" + UPDATED_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime in DEFAULT_FIRST_LOT_RUN_TIME or UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.in=" + DEFAULT_FIRST_LOT_RUN_TIME + "," + UPDATED_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime equals to UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.in=" + UPDATED_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime is not null
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.specified=true");

        // Get all the mAuctionRuleList where firstLotRunTime is null
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime is greater than or equal to DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.greaterThanOrEqual=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime is greater than or equal to UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.greaterThanOrEqual=" + UPDATED_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime is less than or equal to DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.lessThanOrEqual=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime is less than or equal to SMALLER_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.lessThanOrEqual=" + SMALLER_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime is less than DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.lessThan=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime is less than UPDATED_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.lessThan=" + UPDATED_FIRST_LOT_RUN_TIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByFirstLotRunTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where firstLotRunTime is greater than DEFAULT_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldNotBeFound("firstLotRunTime.greaterThan=" + DEFAULT_FIRST_LOT_RUN_TIME);

        // Get all the mAuctionRuleList where firstLotRunTime is greater than SMALLER_FIRST_LOT_RUN_TIME
        defaultMAuctionRuleShouldBeFound("firstLotRunTime.greaterThan=" + SMALLER_FIRST_LOT_RUN_TIME);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime equals to DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.equals=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime equals to UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.equals=" + UPDATED_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime not equals to DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.notEquals=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime not equals to UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.notEquals=" + UPDATED_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime in DEFAULT_BID_RANK_OVERTIME or UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.in=" + DEFAULT_BID_RANK_OVERTIME + "," + UPDATED_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime equals to UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.in=" + UPDATED_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime is not null
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.specified=true");

        // Get all the mAuctionRuleList where bidRankOvertime is null
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime is greater than or equal to DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.greaterThanOrEqual=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime is greater than or equal to UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.greaterThanOrEqual=" + UPDATED_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime is less than or equal to DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.lessThanOrEqual=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime is less than or equal to SMALLER_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.lessThanOrEqual=" + SMALLER_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime is less than DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.lessThan=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime is less than UPDATED_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.lessThan=" + UPDATED_BID_RANK_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidRankOvertimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidRankOvertime is greater than DEFAULT_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldNotBeFound("bidRankOvertime.greaterThan=" + DEFAULT_BID_RANK_OVERTIME);

        // Get all the mAuctionRuleList where bidRankOvertime is greater than SMALLER_BID_RANK_OVERTIME
        defaultMAuctionRuleShouldBeFound("bidRankOvertime.greaterThan=" + SMALLER_BID_RANK_OVERTIME);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin equals to DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.equals=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin equals to UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.equals=" + UPDATED_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin not equals to DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.notEquals=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin not equals to UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.notEquals=" + UPDATED_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin in DEFAULT_START_OVERTIME_WITHIN or UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.in=" + DEFAULT_START_OVERTIME_WITHIN + "," + UPDATED_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin equals to UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.in=" + UPDATED_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin is not null
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.specified=true");

        // Get all the mAuctionRuleList where startOvertimeWithin is null
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin is greater than or equal to DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.greaterThanOrEqual=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin is greater than or equal to UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.greaterThanOrEqual=" + UPDATED_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin is less than or equal to DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.lessThanOrEqual=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin is less than or equal to SMALLER_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.lessThanOrEqual=" + SMALLER_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin is less than DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.lessThan=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin is less than UPDATED_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.lessThan=" + UPDATED_START_OVERTIME_WITHIN);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByStartOvertimeWithinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where startOvertimeWithin is greater than DEFAULT_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldNotBeFound("startOvertimeWithin.greaterThan=" + DEFAULT_START_OVERTIME_WITHIN);

        // Get all the mAuctionRuleList where startOvertimeWithin is greater than SMALLER_START_OVERTIME_WITHIN
        defaultMAuctionRuleShouldBeFound("startOvertimeWithin.greaterThan=" + SMALLER_START_OVERTIME_WITHIN);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod equals to DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.equals=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod equals to UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.equals=" + UPDATED_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod not equals to DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.notEquals=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod not equals to UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.notEquals=" + UPDATED_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod in DEFAULT_OVERTIME_PERIOD or UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.in=" + DEFAULT_OVERTIME_PERIOD + "," + UPDATED_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod equals to UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.in=" + UPDATED_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod is not null
        defaultMAuctionRuleShouldBeFound("overtimePeriod.specified=true");

        // Get all the mAuctionRuleList where overtimePeriod is null
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod is greater than or equal to DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.greaterThanOrEqual=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod is greater than or equal to UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.greaterThanOrEqual=" + UPDATED_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod is less than or equal to DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.lessThanOrEqual=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod is less than or equal to SMALLER_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.lessThanOrEqual=" + SMALLER_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod is less than DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.lessThan=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod is less than UPDATED_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.lessThan=" + UPDATED_OVERTIME_PERIOD);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByOvertimePeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where overtimePeriod is greater than DEFAULT_OVERTIME_PERIOD
        defaultMAuctionRuleShouldNotBeFound("overtimePeriod.greaterThan=" + DEFAULT_OVERTIME_PERIOD);

        // Get all the mAuctionRuleList where overtimePeriod is greater than SMALLER_OVERTIME_PERIOD
        defaultMAuctionRuleShouldBeFound("overtimePeriod.greaterThan=" + SMALLER_OVERTIME_PERIOD);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate equals to DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.equals=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate equals to UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.equals=" + UPDATED_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate not equals to DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.notEquals=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate not equals to UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.notEquals=" + UPDATED_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate in DEFAULT_EST_AWARD_DATE or UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.in=" + DEFAULT_EST_AWARD_DATE + "," + UPDATED_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate equals to UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.in=" + UPDATED_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate is not null
        defaultMAuctionRuleShouldBeFound("estAwardDate.specified=true");

        // Get all the mAuctionRuleList where estAwardDate is null
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate is greater than or equal to DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.greaterThanOrEqual=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate is greater than or equal to UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.greaterThanOrEqual=" + UPDATED_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate is less than or equal to DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.lessThanOrEqual=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate is less than or equal to SMALLER_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.lessThanOrEqual=" + SMALLER_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate is less than DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.lessThan=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate is less than UPDATED_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.lessThan=" + UPDATED_EST_AWARD_DATE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByEstAwardDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where estAwardDate is greater than DEFAULT_EST_AWARD_DATE
        defaultMAuctionRuleShouldNotBeFound("estAwardDate.greaterThan=" + DEFAULT_EST_AWARD_DATE);

        // Get all the mAuctionRuleList where estAwardDate is greater than SMALLER_EST_AWARD_DATE
        defaultMAuctionRuleShouldBeFound("estAwardDate.greaterThan=" + SMALLER_EST_AWARD_DATE);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit equals to DEFAULT_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.equals=" + DEFAULT_BID_IMPROVEMENT_UNIT);

        // Get all the mAuctionRuleList where bidImprovementUnit equals to UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.equals=" + UPDATED_BID_IMPROVEMENT_UNIT);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit not equals to DEFAULT_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.notEquals=" + DEFAULT_BID_IMPROVEMENT_UNIT);

        // Get all the mAuctionRuleList where bidImprovementUnit not equals to UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.notEquals=" + UPDATED_BID_IMPROVEMENT_UNIT);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit in DEFAULT_BID_IMPROVEMENT_UNIT or UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.in=" + DEFAULT_BID_IMPROVEMENT_UNIT + "," + UPDATED_BID_IMPROVEMENT_UNIT);

        // Get all the mAuctionRuleList where bidImprovementUnit equals to UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.in=" + UPDATED_BID_IMPROVEMENT_UNIT);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit is not null
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.specified=true");

        // Get all the mAuctionRuleList where bidImprovementUnit is null
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit contains DEFAULT_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.contains=" + DEFAULT_BID_IMPROVEMENT_UNIT);

        // Get all the mAuctionRuleList where bidImprovementUnit contains UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.contains=" + UPDATED_BID_IMPROVEMENT_UNIT);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByBidImprovementUnitNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where bidImprovementUnit does not contain DEFAULT_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldNotBeFound("bidImprovementUnit.doesNotContain=" + DEFAULT_BID_IMPROVEMENT_UNIT);

        // Get all the mAuctionRuleList where bidImprovementUnit does not contain UPDATED_BID_IMPROVEMENT_UNIT
        defaultMAuctionRuleShouldBeFound("bidImprovementUnit.doesNotContain=" + UPDATED_BID_IMPROVEMENT_UNIT);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule equals to DEFAULT_TIE_BIDS_RULE
        defaultMAuctionRuleShouldBeFound("tieBidsRule.equals=" + DEFAULT_TIE_BIDS_RULE);

        // Get all the mAuctionRuleList where tieBidsRule equals to UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.equals=" + UPDATED_TIE_BIDS_RULE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule not equals to DEFAULT_TIE_BIDS_RULE
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.notEquals=" + DEFAULT_TIE_BIDS_RULE);

        // Get all the mAuctionRuleList where tieBidsRule not equals to UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldBeFound("tieBidsRule.notEquals=" + UPDATED_TIE_BIDS_RULE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule in DEFAULT_TIE_BIDS_RULE or UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldBeFound("tieBidsRule.in=" + DEFAULT_TIE_BIDS_RULE + "," + UPDATED_TIE_BIDS_RULE);

        // Get all the mAuctionRuleList where tieBidsRule equals to UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.in=" + UPDATED_TIE_BIDS_RULE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule is not null
        defaultMAuctionRuleShouldBeFound("tieBidsRule.specified=true");

        // Get all the mAuctionRuleList where tieBidsRule is null
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule contains DEFAULT_TIE_BIDS_RULE
        defaultMAuctionRuleShouldBeFound("tieBidsRule.contains=" + DEFAULT_TIE_BIDS_RULE);

        // Get all the mAuctionRuleList where tieBidsRule contains UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.contains=" + UPDATED_TIE_BIDS_RULE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByTieBidsRuleNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where tieBidsRule does not contain DEFAULT_TIE_BIDS_RULE
        defaultMAuctionRuleShouldNotBeFound("tieBidsRule.doesNotContain=" + DEFAULT_TIE_BIDS_RULE);

        // Get all the mAuctionRuleList where tieBidsRule does not contain UPDATED_TIE_BIDS_RULE
        defaultMAuctionRuleShouldBeFound("tieBidsRule.doesNotContain=" + UPDATED_TIE_BIDS_RULE);
    }


    @Test
    @Transactional
    public void getAllMAuctionRulesByShowResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showResponse equals to DEFAULT_SHOW_RESPONSE
        defaultMAuctionRuleShouldBeFound("showResponse.equals=" + DEFAULT_SHOW_RESPONSE);

        // Get all the mAuctionRuleList where showResponse equals to UPDATED_SHOW_RESPONSE
        defaultMAuctionRuleShouldNotBeFound("showResponse.equals=" + UPDATED_SHOW_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowResponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showResponse not equals to DEFAULT_SHOW_RESPONSE
        defaultMAuctionRuleShouldNotBeFound("showResponse.notEquals=" + DEFAULT_SHOW_RESPONSE);

        // Get all the mAuctionRuleList where showResponse not equals to UPDATED_SHOW_RESPONSE
        defaultMAuctionRuleShouldBeFound("showResponse.notEquals=" + UPDATED_SHOW_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowResponseIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showResponse in DEFAULT_SHOW_RESPONSE or UPDATED_SHOW_RESPONSE
        defaultMAuctionRuleShouldBeFound("showResponse.in=" + DEFAULT_SHOW_RESPONSE + "," + UPDATED_SHOW_RESPONSE);

        // Get all the mAuctionRuleList where showResponse equals to UPDATED_SHOW_RESPONSE
        defaultMAuctionRuleShouldNotBeFound("showResponse.in=" + UPDATED_SHOW_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowResponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showResponse is not null
        defaultMAuctionRuleShouldBeFound("showResponse.specified=true");

        // Get all the mAuctionRuleList where showResponse is null
        defaultMAuctionRuleShouldNotBeFound("showResponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowLeaderIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showLeader equals to DEFAULT_SHOW_LEADER
        defaultMAuctionRuleShouldBeFound("showLeader.equals=" + DEFAULT_SHOW_LEADER);

        // Get all the mAuctionRuleList where showLeader equals to UPDATED_SHOW_LEADER
        defaultMAuctionRuleShouldNotBeFound("showLeader.equals=" + UPDATED_SHOW_LEADER);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowLeaderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showLeader not equals to DEFAULT_SHOW_LEADER
        defaultMAuctionRuleShouldNotBeFound("showLeader.notEquals=" + DEFAULT_SHOW_LEADER);

        // Get all the mAuctionRuleList where showLeader not equals to UPDATED_SHOW_LEADER
        defaultMAuctionRuleShouldBeFound("showLeader.notEquals=" + UPDATED_SHOW_LEADER);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowLeaderIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showLeader in DEFAULT_SHOW_LEADER or UPDATED_SHOW_LEADER
        defaultMAuctionRuleShouldBeFound("showLeader.in=" + DEFAULT_SHOW_LEADER + "," + UPDATED_SHOW_LEADER);

        // Get all the mAuctionRuleList where showLeader equals to UPDATED_SHOW_LEADER
        defaultMAuctionRuleShouldNotBeFound("showLeader.in=" + UPDATED_SHOW_LEADER);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByShowLeaderIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where showLeader is not null
        defaultMAuctionRuleShouldBeFound("showLeader.specified=true");

        // Get all the mAuctionRuleList where showLeader is null
        defaultMAuctionRuleShouldNotBeFound("showLeader.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where uid equals to DEFAULT_UID
        defaultMAuctionRuleShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionRuleList where uid equals to UPDATED_UID
        defaultMAuctionRuleShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where uid not equals to DEFAULT_UID
        defaultMAuctionRuleShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionRuleList where uid not equals to UPDATED_UID
        defaultMAuctionRuleShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionRuleShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionRuleList where uid equals to UPDATED_UID
        defaultMAuctionRuleShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where uid is not null
        defaultMAuctionRuleShouldBeFound("uid.specified=true");

        // Get all the mAuctionRuleList where uid is null
        defaultMAuctionRuleShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where active equals to DEFAULT_ACTIVE
        defaultMAuctionRuleShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionRuleList where active equals to UPDATED_ACTIVE
        defaultMAuctionRuleShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionRuleShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionRuleList where active not equals to UPDATED_ACTIVE
        defaultMAuctionRuleShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionRuleShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionRuleList where active equals to UPDATED_ACTIVE
        defaultMAuctionRuleShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        // Get all the mAuctionRuleList where active is not null
        defaultMAuctionRuleShouldBeFound("active.specified=true");

        // Get all the mAuctionRuleList where active is null
        defaultMAuctionRuleShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionRulesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionRule.getAdOrganization();
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionRuleList where adOrganization equals to adOrganizationId
        defaultMAuctionRuleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionRuleList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionRuleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionRuleShouldBeFound(String filter) throws Exception {
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidPrevPeriod").value(hasItem(DEFAULT_BID_PREV_PERIOD)))
            .andExpect(jsonPath("$.[*].preBidEndDate").value(hasItem(sameInstant(DEFAULT_PRE_BID_END_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].firstLotRunTime").value(hasItem(DEFAULT_FIRST_LOT_RUN_TIME)))
            .andExpect(jsonPath("$.[*].bidRankOvertime").value(hasItem(DEFAULT_BID_RANK_OVERTIME)))
            .andExpect(jsonPath("$.[*].startOvertimeWithin").value(hasItem(DEFAULT_START_OVERTIME_WITHIN)))
            .andExpect(jsonPath("$.[*].overtimePeriod").value(hasItem(DEFAULT_OVERTIME_PERIOD)))
            .andExpect(jsonPath("$.[*].estAwardDate").value(hasItem(DEFAULT_EST_AWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].bidImprovementUnit").value(hasItem(DEFAULT_BID_IMPROVEMENT_UNIT)))
            .andExpect(jsonPath("$.[*].tieBidsRule").value(hasItem(DEFAULT_TIE_BIDS_RULE)))
            .andExpect(jsonPath("$.[*].showResponse").value(hasItem(DEFAULT_SHOW_RESPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].showLeader").value(hasItem(DEFAULT_SHOW_LEADER.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionRuleShouldNotBeFound(String filter) throws Exception {
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionRule() throws Exception {
        // Get the mAuctionRule
        restMAuctionRuleMockMvc.perform(get("/api/m-auction-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionRule() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        int databaseSizeBeforeUpdate = mAuctionRuleRepository.findAll().size();

        // Update the mAuctionRule
        MAuctionRule updatedMAuctionRule = mAuctionRuleRepository.findById(mAuctionRule.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionRule are not directly saved in db
        em.detach(updatedMAuctionRule);
        updatedMAuctionRule
            .bidPrevPeriod(UPDATED_BID_PREV_PERIOD)
            .preBidEndDate(UPDATED_PRE_BID_END_DATE)
            .startDate(UPDATED_START_DATE)
            .firstLotRunTime(UPDATED_FIRST_LOT_RUN_TIME)
            .bidRankOvertime(UPDATED_BID_RANK_OVERTIME)
            .startOvertimeWithin(UPDATED_START_OVERTIME_WITHIN)
            .overtimePeriod(UPDATED_OVERTIME_PERIOD)
            .estAwardDate(UPDATED_EST_AWARD_DATE)
            .bidImprovementUnit(UPDATED_BID_IMPROVEMENT_UNIT)
            .tieBidsRule(UPDATED_TIE_BIDS_RULE)
            .showResponse(UPDATED_SHOW_RESPONSE)
            .showLeader(UPDATED_SHOW_LEADER)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(updatedMAuctionRule);

        restMAuctionRuleMockMvc.perform(put("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionRule in the database
        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeUpdate);
        MAuctionRule testMAuctionRule = mAuctionRuleList.get(mAuctionRuleList.size() - 1);
        assertThat(testMAuctionRule.getBidPrevPeriod()).isEqualTo(UPDATED_BID_PREV_PERIOD);
        assertThat(testMAuctionRule.getPreBidEndDate()).isEqualTo(UPDATED_PRE_BID_END_DATE);
        assertThat(testMAuctionRule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMAuctionRule.getFirstLotRunTime()).isEqualTo(UPDATED_FIRST_LOT_RUN_TIME);
        assertThat(testMAuctionRule.getBidRankOvertime()).isEqualTo(UPDATED_BID_RANK_OVERTIME);
        assertThat(testMAuctionRule.getStartOvertimeWithin()).isEqualTo(UPDATED_START_OVERTIME_WITHIN);
        assertThat(testMAuctionRule.getOvertimePeriod()).isEqualTo(UPDATED_OVERTIME_PERIOD);
        assertThat(testMAuctionRule.getEstAwardDate()).isEqualTo(UPDATED_EST_AWARD_DATE);
        assertThat(testMAuctionRule.getBidImprovementUnit()).isEqualTo(UPDATED_BID_IMPROVEMENT_UNIT);
        assertThat(testMAuctionRule.getTieBidsRule()).isEqualTo(UPDATED_TIE_BIDS_RULE);
        assertThat(testMAuctionRule.isShowResponse()).isEqualTo(UPDATED_SHOW_RESPONSE);
        assertThat(testMAuctionRule.isShowLeader()).isEqualTo(UPDATED_SHOW_LEADER);
        assertThat(testMAuctionRule.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionRule.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionRule() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionRuleRepository.findAll().size();

        // Create the MAuctionRule
        MAuctionRuleDTO mAuctionRuleDTO = mAuctionRuleMapper.toDto(mAuctionRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionRuleMockMvc.perform(put("/api/m-auction-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionRule in the database
        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionRule() throws Exception {
        // Initialize the database
        mAuctionRuleRepository.saveAndFlush(mAuctionRule);

        int databaseSizeBeforeDelete = mAuctionRuleRepository.findAll().size();

        // Delete the mAuctionRule
        restMAuctionRuleMockMvc.perform(delete("/api/m-auction-rules/{id}", mAuctionRule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionRule> mAuctionRuleList = mAuctionRuleRepository.findAll();
        assertThat(mAuctionRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
