package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionSubmissionLog;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.repository.MAuctionSubmissionLogRepository;
import com.bhp.opusb.service.MAuctionSubmissionLogService;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionLogMapper;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogCriteria;
import com.bhp.opusb.service.MAuctionSubmissionLogQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MAuctionSubmissionLogResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionSubmissionLogResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_DATE_TRX = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TRX = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private MAuctionSubmissionLogRepository mAuctionSubmissionLogRepository;

    @Autowired
    private MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper;

    @Autowired
    private MAuctionSubmissionLogService mAuctionSubmissionLogService;

    @Autowired
    private MAuctionSubmissionLogQueryService mAuctionSubmissionLogQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionSubmissionLogMockMvc;

    private MAuctionSubmissionLog mAuctionSubmissionLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmissionLog createEntity(EntityManager em) {
        MAuctionSubmissionLog mAuctionSubmissionLog = new MAuctionSubmissionLog()
            .action(DEFAULT_ACTION)
            .userName(DEFAULT_USER_NAME)
            .price(DEFAULT_PRICE)
            .dateTrx(DEFAULT_DATE_TRX)
            .message(DEFAULT_MESSAGE);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmissionLog.setAuctionItem(mAuctionItem);
        return mAuctionSubmissionLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmissionLog createUpdatedEntity(EntityManager em) {
        MAuctionSubmissionLog mAuctionSubmissionLog = new MAuctionSubmissionLog()
            .action(UPDATED_ACTION)
            .userName(UPDATED_USER_NAME)
            .price(UPDATED_PRICE)
            .dateTrx(UPDATED_DATE_TRX)
            .message(UPDATED_MESSAGE);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createUpdatedEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmissionLog.setAuctionItem(mAuctionItem);
        return mAuctionSubmissionLog;
    }

    @BeforeEach
    public void initTest() {
        mAuctionSubmissionLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionSubmissionLog() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionLogRepository.findAll().size();

        // Create the MAuctionSubmissionLog
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);
        restMAuctionSubmissionLogMockMvc.perform(post("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionSubmissionLog in the database
        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionSubmissionLog testMAuctionSubmissionLog = mAuctionSubmissionLogList.get(mAuctionSubmissionLogList.size() - 1);
        assertThat(testMAuctionSubmissionLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testMAuctionSubmissionLog.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMAuctionSubmissionLog.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMAuctionSubmissionLog.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMAuctionSubmissionLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createMAuctionSubmissionLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionLogRepository.findAll().size();

        // Create the MAuctionSubmissionLog with an existing ID
        mAuctionSubmissionLog.setId(1L);
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionSubmissionLogMockMvc.perform(post("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmissionLog in the database
        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionSubmissionLogRepository.findAll().size();
        // set the field null
        mAuctionSubmissionLog.setAction(null);

        // Create the MAuctionSubmissionLog, which fails.
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);

        restMAuctionSubmissionLogMockMvc.perform(post("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionSubmissionLogRepository.findAll().size();
        // set the field null
        mAuctionSubmissionLog.setUserName(null);

        // Create the MAuctionSubmissionLog, which fails.
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);

        restMAuctionSubmissionLogMockMvc.perform(post("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionSubmissionLogRepository.findAll().size();
        // set the field null
        mAuctionSubmissionLog.setDateTrx(null);

        // Create the MAuctionSubmissionLog, which fails.
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);

        restMAuctionSubmissionLogMockMvc.perform(post("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogs() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmissionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void getMAuctionSubmissionLog() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get the mAuctionSubmissionLog
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs/{id}", mAuctionSubmissionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionSubmissionLog.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }


    @Test
    @Transactional
    public void getMAuctionSubmissionLogsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        Long id = mAuctionSubmissionLog.getId();

        defaultMAuctionSubmissionLogShouldBeFound("id.equals=" + id);
        defaultMAuctionSubmissionLogShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionSubmissionLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionSubmissionLogShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionSubmissionLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionSubmissionLogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action equals to DEFAULT_ACTION
        defaultMAuctionSubmissionLogShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the mAuctionSubmissionLogList where action equals to UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action not equals to DEFAULT_ACTION
        defaultMAuctionSubmissionLogShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the mAuctionSubmissionLogList where action not equals to UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the mAuctionSubmissionLogList where action equals to UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action is not null
        defaultMAuctionSubmissionLogShouldBeFound("action.specified=true");

        // Get all the mAuctionSubmissionLogList where action is null
        defaultMAuctionSubmissionLogShouldNotBeFound("action.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action contains DEFAULT_ACTION
        defaultMAuctionSubmissionLogShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the mAuctionSubmissionLogList where action contains UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where action does not contain DEFAULT_ACTION
        defaultMAuctionSubmissionLogShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the mAuctionSubmissionLogList where action does not contain UPDATED_ACTION
        defaultMAuctionSubmissionLogShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName equals to DEFAULT_USER_NAME
        defaultMAuctionSubmissionLogShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the mAuctionSubmissionLogList where userName equals to UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName not equals to DEFAULT_USER_NAME
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the mAuctionSubmissionLogList where userName not equals to UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the mAuctionSubmissionLogList where userName equals to UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName is not null
        defaultMAuctionSubmissionLogShouldBeFound("userName.specified=true");

        // Get all the mAuctionSubmissionLogList where userName is null
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName contains DEFAULT_USER_NAME
        defaultMAuctionSubmissionLogShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the mAuctionSubmissionLogList where userName contains UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where userName does not contain DEFAULT_USER_NAME
        defaultMAuctionSubmissionLogShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the mAuctionSubmissionLogList where userName does not contain UPDATED_USER_NAME
        defaultMAuctionSubmissionLogShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price equals to DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price not equals to DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price not equals to UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mAuctionSubmissionLogList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price is not null
        defaultMAuctionSubmissionLogShouldBeFound("price.specified=true");

        // Get all the mAuctionSubmissionLogList where price is null
        defaultMAuctionSubmissionLogShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price is greater than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price is greater than or equal to UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price is less than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price is less than or equal to SMALLER_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price is less than DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price is less than UPDATED_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where price is greater than DEFAULT_PRICE
        defaultMAuctionSubmissionLogShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionLogList where price is greater than SMALLER_PRICE
        defaultMAuctionSubmissionLogShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMAuctionSubmissionLogShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionSubmissionLogList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionSubmissionLogShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMAuctionSubmissionLogShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionSubmissionLogList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMAuctionSubmissionLogShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMAuctionSubmissionLogShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mAuctionSubmissionLogList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionSubmissionLogShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where dateTrx is not null
        defaultMAuctionSubmissionLogShouldBeFound("dateTrx.specified=true");

        // Get all the mAuctionSubmissionLogList where dateTrx is null
        defaultMAuctionSubmissionLogShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message equals to DEFAULT_MESSAGE
        defaultMAuctionSubmissionLogShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the mAuctionSubmissionLogList where message equals to UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message not equals to DEFAULT_MESSAGE
        defaultMAuctionSubmissionLogShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the mAuctionSubmissionLogList where message not equals to UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the mAuctionSubmissionLogList where message equals to UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message is not null
        defaultMAuctionSubmissionLogShouldBeFound("message.specified=true");

        // Get all the mAuctionSubmissionLogList where message is null
        defaultMAuctionSubmissionLogShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message contains DEFAULT_MESSAGE
        defaultMAuctionSubmissionLogShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the mAuctionSubmissionLogList where message contains UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        // Get all the mAuctionSubmissionLogList where message does not contain DEFAULT_MESSAGE
        defaultMAuctionSubmissionLogShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the mAuctionSubmissionLogList where message does not contain UPDATED_MESSAGE
        defaultMAuctionSubmissionLogShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionLogsByAuctionItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuctionItem auctionItem = mAuctionSubmissionLog.getAuctionItem();
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);
        Long auctionItemId = auctionItem.getId();

        // Get all the mAuctionSubmissionLogList where auctionItem equals to auctionItemId
        defaultMAuctionSubmissionLogShouldBeFound("auctionItemId.equals=" + auctionItemId);

        // Get all the mAuctionSubmissionLogList where auctionItem equals to auctionItemId + 1
        defaultMAuctionSubmissionLogShouldNotBeFound("auctionItemId.equals=" + (auctionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionSubmissionLogShouldBeFound(String filter) throws Exception {
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmissionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));

        // Check, that the count call also returns 1
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionSubmissionLogShouldNotBeFound(String filter) throws Exception {
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionSubmissionLog() throws Exception {
        // Get the mAuctionSubmissionLog
        restMAuctionSubmissionLogMockMvc.perform(get("/api/m-auction-submission-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionSubmissionLog() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        int databaseSizeBeforeUpdate = mAuctionSubmissionLogRepository.findAll().size();

        // Update the mAuctionSubmissionLog
        MAuctionSubmissionLog updatedMAuctionSubmissionLog = mAuctionSubmissionLogRepository.findById(mAuctionSubmissionLog.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionSubmissionLog are not directly saved in db
        em.detach(updatedMAuctionSubmissionLog);
        updatedMAuctionSubmissionLog
            .action(UPDATED_ACTION)
            .userName(UPDATED_USER_NAME)
            .price(UPDATED_PRICE)
            .dateTrx(UPDATED_DATE_TRX)
            .message(UPDATED_MESSAGE);
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(updatedMAuctionSubmissionLog);

        restMAuctionSubmissionLogMockMvc.perform(put("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionSubmissionLog in the database
        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeUpdate);
        MAuctionSubmissionLog testMAuctionSubmissionLog = mAuctionSubmissionLogList.get(mAuctionSubmissionLogList.size() - 1);
        assertThat(testMAuctionSubmissionLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testMAuctionSubmissionLog.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMAuctionSubmissionLog.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMAuctionSubmissionLog.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMAuctionSubmissionLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionSubmissionLog() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionSubmissionLogRepository.findAll().size();

        // Create the MAuctionSubmissionLog
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionSubmissionLogMockMvc.perform(put("/api/m-auction-submission-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmissionLog in the database
        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionSubmissionLog() throws Exception {
        // Initialize the database
        mAuctionSubmissionLogRepository.saveAndFlush(mAuctionSubmissionLog);

        int databaseSizeBeforeDelete = mAuctionSubmissionLogRepository.findAll().size();

        // Delete the mAuctionSubmissionLog
        restMAuctionSubmissionLogMockMvc.perform(delete("/api/m-auction-submission-logs/{id}", mAuctionSubmissionLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionSubmissionLog> mAuctionSubmissionLogList = mAuctionSubmissionLogRepository.findAll();
        assertThat(mAuctionSubmissionLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
