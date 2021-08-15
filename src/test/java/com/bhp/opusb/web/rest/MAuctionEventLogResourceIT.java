package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionEventLog;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MAuctionEventLogRepository;
import com.bhp.opusb.service.MAuctionEventLogService;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.mapper.MAuctionEventLogMapper;
import com.bhp.opusb.service.dto.MAuctionEventLogCriteria;
import com.bhp.opusb.service.MAuctionEventLogQueryService;

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
 * Integration tests for the {@link MAuctionEventLogResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionEventLogResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_TRX = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TRX = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private MAuctionEventLogRepository mAuctionEventLogRepository;

    @Autowired
    private MAuctionEventLogMapper mAuctionEventLogMapper;

    @Autowired
    private MAuctionEventLogService mAuctionEventLogService;

    @Autowired
    private MAuctionEventLogQueryService mAuctionEventLogQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionEventLogMockMvc;

    private MAuctionEventLog mAuctionEventLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionEventLog createEntity(EntityManager em) {
        MAuctionEventLog mAuctionEventLog = new MAuctionEventLog()
            .action(DEFAULT_ACTION)
            .dateTrx(DEFAULT_DATE_TRX)
            .username(DEFAULT_USERNAME)
            .price(DEFAULT_PRICE)
            .note(DEFAULT_NOTE);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionEventLog.setAuction(mAuction);
        return mAuctionEventLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionEventLog createUpdatedEntity(EntityManager em) {
        MAuctionEventLog mAuctionEventLog = new MAuctionEventLog()
            .action(UPDATED_ACTION)
            .dateTrx(UPDATED_DATE_TRX)
            .username(UPDATED_USERNAME)
            .price(UPDATED_PRICE)
            .note(UPDATED_NOTE);
        // Add required entity
        MAuction mAuction;
        if (TestUtil.findAll(em, MAuction.class).isEmpty()) {
            mAuction = MAuctionResourceIT.createUpdatedEntity(em);
            em.persist(mAuction);
            em.flush();
        } else {
            mAuction = TestUtil.findAll(em, MAuction.class).get(0);
        }
        mAuctionEventLog.setAuction(mAuction);
        return mAuctionEventLog;
    }

    @BeforeEach
    public void initTest() {
        mAuctionEventLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionEventLog() throws Exception {
        int databaseSizeBeforeCreate = mAuctionEventLogRepository.findAll().size();

        // Create the MAuctionEventLog
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);
        restMAuctionEventLogMockMvc.perform(post("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionEventLog in the database
        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionEventLog testMAuctionEventLog = mAuctionEventLogList.get(mAuctionEventLogList.size() - 1);
        assertThat(testMAuctionEventLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testMAuctionEventLog.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMAuctionEventLog.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMAuctionEventLog.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMAuctionEventLog.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createMAuctionEventLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionEventLogRepository.findAll().size();

        // Create the MAuctionEventLog with an existing ID
        mAuctionEventLog.setId(1L);
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionEventLogMockMvc.perform(post("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionEventLog in the database
        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionEventLogRepository.findAll().size();
        // set the field null
        mAuctionEventLog.setAction(null);

        // Create the MAuctionEventLog, which fails.
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);

        restMAuctionEventLogMockMvc.perform(post("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionEventLogRepository.findAll().size();
        // set the field null
        mAuctionEventLog.setDateTrx(null);

        // Create the MAuctionEventLog, which fails.
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);

        restMAuctionEventLogMockMvc.perform(post("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionEventLogRepository.findAll().size();
        // set the field null
        mAuctionEventLog.setUsername(null);

        // Create the MAuctionEventLog, which fails.
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);

        restMAuctionEventLogMockMvc.perform(post("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogs() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionEventLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getMAuctionEventLog() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get the mAuctionEventLog
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs/{id}", mAuctionEventLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionEventLog.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }


    @Test
    @Transactional
    public void getMAuctionEventLogsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        Long id = mAuctionEventLog.getId();

        defaultMAuctionEventLogShouldBeFound("id.equals=" + id);
        defaultMAuctionEventLogShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionEventLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionEventLogShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionEventLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionEventLogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action equals to DEFAULT_ACTION
        defaultMAuctionEventLogShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the mAuctionEventLogList where action equals to UPDATED_ACTION
        defaultMAuctionEventLogShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action not equals to DEFAULT_ACTION
        defaultMAuctionEventLogShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the mAuctionEventLogList where action not equals to UPDATED_ACTION
        defaultMAuctionEventLogShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultMAuctionEventLogShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the mAuctionEventLogList where action equals to UPDATED_ACTION
        defaultMAuctionEventLogShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action is not null
        defaultMAuctionEventLogShouldBeFound("action.specified=true");

        // Get all the mAuctionEventLogList where action is null
        defaultMAuctionEventLogShouldNotBeFound("action.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action contains DEFAULT_ACTION
        defaultMAuctionEventLogShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the mAuctionEventLogList where action contains UPDATED_ACTION
        defaultMAuctionEventLogShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where action does not contain DEFAULT_ACTION
        defaultMAuctionEventLogShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the mAuctionEventLogList where action does not contain UPDATED_ACTION
        defaultMAuctionEventLogShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMAuctionEventLogShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionEventLogList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionEventLogShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMAuctionEventLogShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mAuctionEventLogList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMAuctionEventLogShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMAuctionEventLogShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mAuctionEventLogList where dateTrx equals to UPDATED_DATE_TRX
        defaultMAuctionEventLogShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where dateTrx is not null
        defaultMAuctionEventLogShouldBeFound("dateTrx.specified=true");

        // Get all the mAuctionEventLogList where dateTrx is null
        defaultMAuctionEventLogShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username equals to DEFAULT_USERNAME
        defaultMAuctionEventLogShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the mAuctionEventLogList where username equals to UPDATED_USERNAME
        defaultMAuctionEventLogShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username not equals to DEFAULT_USERNAME
        defaultMAuctionEventLogShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the mAuctionEventLogList where username not equals to UPDATED_USERNAME
        defaultMAuctionEventLogShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultMAuctionEventLogShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the mAuctionEventLogList where username equals to UPDATED_USERNAME
        defaultMAuctionEventLogShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username is not null
        defaultMAuctionEventLogShouldBeFound("username.specified=true");

        // Get all the mAuctionEventLogList where username is null
        defaultMAuctionEventLogShouldNotBeFound("username.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username contains DEFAULT_USERNAME
        defaultMAuctionEventLogShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the mAuctionEventLogList where username contains UPDATED_USERNAME
        defaultMAuctionEventLogShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where username does not contain DEFAULT_USERNAME
        defaultMAuctionEventLogShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the mAuctionEventLogList where username does not contain UPDATED_USERNAME
        defaultMAuctionEventLogShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price equals to DEFAULT_PRICE
        defaultMAuctionEventLogShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price equals to UPDATED_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price not equals to DEFAULT_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price not equals to UPDATED_PRICE
        defaultMAuctionEventLogShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMAuctionEventLogShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mAuctionEventLogList where price equals to UPDATED_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price is not null
        defaultMAuctionEventLogShouldBeFound("price.specified=true");

        // Get all the mAuctionEventLogList where price is null
        defaultMAuctionEventLogShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price is greater than or equal to DEFAULT_PRICE
        defaultMAuctionEventLogShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price is greater than or equal to UPDATED_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price is less than or equal to DEFAULT_PRICE
        defaultMAuctionEventLogShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price is less than or equal to SMALLER_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price is less than DEFAULT_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price is less than UPDATED_PRICE
        defaultMAuctionEventLogShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where price is greater than DEFAULT_PRICE
        defaultMAuctionEventLogShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mAuctionEventLogList where price is greater than SMALLER_PRICE
        defaultMAuctionEventLogShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note equals to DEFAULT_NOTE
        defaultMAuctionEventLogShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the mAuctionEventLogList where note equals to UPDATED_NOTE
        defaultMAuctionEventLogShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note not equals to DEFAULT_NOTE
        defaultMAuctionEventLogShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the mAuctionEventLogList where note not equals to UPDATED_NOTE
        defaultMAuctionEventLogShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultMAuctionEventLogShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the mAuctionEventLogList where note equals to UPDATED_NOTE
        defaultMAuctionEventLogShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note is not null
        defaultMAuctionEventLogShouldBeFound("note.specified=true");

        // Get all the mAuctionEventLogList where note is null
        defaultMAuctionEventLogShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note contains DEFAULT_NOTE
        defaultMAuctionEventLogShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the mAuctionEventLogList where note contains UPDATED_NOTE
        defaultMAuctionEventLogShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllMAuctionEventLogsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        // Get all the mAuctionEventLogList where note does not contain DEFAULT_NOTE
        defaultMAuctionEventLogShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the mAuctionEventLogList where note does not contain UPDATED_NOTE
        defaultMAuctionEventLogShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByAuctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuction auction = mAuctionEventLog.getAuction();
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);
        Long auctionId = auction.getId();

        // Get all the mAuctionEventLogList where auction equals to auctionId
        defaultMAuctionEventLogShouldBeFound("auctionId.equals=" + auctionId);

        // Get all the mAuctionEventLogList where auction equals to auctionId + 1
        defaultMAuctionEventLogShouldNotBeFound("auctionId.equals=" + (auctionId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByAuctionItemIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);
        MAuctionItem auctionItem = MAuctionItemResourceIT.createEntity(em);
        em.persist(auctionItem);
        em.flush();
        mAuctionEventLog.setAuctionItem(auctionItem);
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);
        Long auctionItemId = auctionItem.getId();

        // Get all the mAuctionEventLogList where auctionItem equals to auctionItemId
        defaultMAuctionEventLogShouldBeFound("auctionItemId.equals=" + auctionItemId);

        // Get all the mAuctionEventLogList where auctionItem equals to auctionItemId + 1
        defaultMAuctionEventLogShouldNotBeFound("auctionItemId.equals=" + (auctionItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionEventLogsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        mAuctionEventLog.setVendor(vendor);
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);
        Long vendorId = vendor.getId();

        // Get all the mAuctionEventLogList where vendor equals to vendorId
        defaultMAuctionEventLogShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mAuctionEventLogList where vendor equals to vendorId + 1
        defaultMAuctionEventLogShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionEventLogShouldBeFound(String filter) throws Exception {
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionEventLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));

        // Check, that the count call also returns 1
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionEventLogShouldNotBeFound(String filter) throws Exception {
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionEventLog() throws Exception {
        // Get the mAuctionEventLog
        restMAuctionEventLogMockMvc.perform(get("/api/m-auction-event-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionEventLog() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        int databaseSizeBeforeUpdate = mAuctionEventLogRepository.findAll().size();

        // Update the mAuctionEventLog
        MAuctionEventLog updatedMAuctionEventLog = mAuctionEventLogRepository.findById(mAuctionEventLog.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionEventLog are not directly saved in db
        em.detach(updatedMAuctionEventLog);
        updatedMAuctionEventLog
            .action(UPDATED_ACTION)
            .dateTrx(UPDATED_DATE_TRX)
            .username(UPDATED_USERNAME)
            .price(UPDATED_PRICE)
            .note(UPDATED_NOTE);
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(updatedMAuctionEventLog);

        restMAuctionEventLogMockMvc.perform(put("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionEventLog in the database
        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeUpdate);
        MAuctionEventLog testMAuctionEventLog = mAuctionEventLogList.get(mAuctionEventLogList.size() - 1);
        assertThat(testMAuctionEventLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testMAuctionEventLog.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMAuctionEventLog.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMAuctionEventLog.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMAuctionEventLog.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionEventLog() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionEventLogRepository.findAll().size();

        // Create the MAuctionEventLog
        MAuctionEventLogDTO mAuctionEventLogDTO = mAuctionEventLogMapper.toDto(mAuctionEventLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionEventLogMockMvc.perform(put("/api/m-auction-event-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionEventLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionEventLog in the database
        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionEventLog() throws Exception {
        // Initialize the database
        mAuctionEventLogRepository.saveAndFlush(mAuctionEventLog);

        int databaseSizeBeforeDelete = mAuctionEventLogRepository.findAll().size();

        // Delete the mAuctionEventLog
        restMAuctionEventLogMockMvc.perform(delete("/api/m-auction-event-logs/{id}", mAuctionEventLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionEventLog> mAuctionEventLogList = mAuctionEventLogRepository.findAll();
        assertThat(mAuctionEventLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
