package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.domain.MSubmissionSubItem;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.repository.MBiddingSubmissionLineRepository;
import com.bhp.opusb.service.MBiddingSubmissionLineService;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionLineMapper;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineCriteria;
import com.bhp.opusb.service.MBiddingSubmissionLineQueryService;

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
 * Integration tests for the {@link MBiddingSubmissionLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MBiddingSubmissionLineResourceIT {

    private static final BigDecimal DEFAULT_PROPOSED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROPOSED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROPOSED_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE_SUBMISSION = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE_SUBMISSION = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE_SUBMISSION = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository;

    @Autowired
    private MBiddingSubmissionLineMapper mBiddingSubmissionLineMapper;

    @Autowired
    private MBiddingSubmissionLineService mBiddingSubmissionLineService;

    @Autowired
    private MBiddingSubmissionLineQueryService mBiddingSubmissionLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingSubmissionLineMockMvc;

    private MBiddingSubmissionLine mBiddingSubmissionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmissionLine createEntity(EntityManager em) {
        MBiddingSubmissionLine mBiddingSubmissionLine = new MBiddingSubmissionLine()
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
            .totalPriceSubmission(DEFAULT_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mBiddingSubmissionLine.setBiddingLine(mBiddingLine);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSubmissionLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingSubmissionLine.setMBiddingSubmission(mBiddingSubmission);
        return mBiddingSubmissionLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingSubmissionLine createUpdatedEntity(EntityManager em) {
        MBiddingSubmissionLine mBiddingSubmissionLine = new MBiddingSubmissionLine()
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .totalPriceSubmission(UPDATED_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mBiddingSubmissionLine.setBiddingLine(mBiddingLine);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mBiddingSubmissionLine.setAdOrganization(aDOrganization);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mBiddingSubmissionLine.setMBiddingSubmission(mBiddingSubmission);
        return mBiddingSubmissionLine;
    }

    @BeforeEach
    public void initTest() {
        mBiddingSubmissionLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingSubmissionLine() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionLineRepository.findAll().size();
        // Create the MBiddingSubmissionLine
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);
        restMBiddingSubmissionLineMockMvc.perform(post("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingSubmissionLine in the database
        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingSubmissionLine testMBiddingSubmissionLine = mBiddingSubmissionLineList.get(mBiddingSubmissionLineList.size() - 1);
        assertThat(testMBiddingSubmissionLine.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMBiddingSubmissionLine.getTotalPriceSubmission()).isEqualTo(DEFAULT_TOTAL_PRICE_SUBMISSION);
        assertThat(testMBiddingSubmissionLine.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testMBiddingSubmissionLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingSubmissionLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingSubmissionLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingSubmissionLineRepository.findAll().size();

        // Create the MBiddingSubmissionLine with an existing ID
        mBiddingSubmissionLine.setId(1L);
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingSubmissionLineMockMvc.perform(post("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmissionLine in the database
        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionLineRepository.findAll().size();
        // set the field null
        mBiddingSubmissionLine.setProposedPrice(null);

        // Create the MBiddingSubmissionLine, which fails.
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);


        restMBiddingSubmissionLineMockMvc.perform(post("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceSubmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionLineRepository.findAll().size();
        // set the field null
        mBiddingSubmissionLine.setTotalPriceSubmission(null);

        // Create the MBiddingSubmissionLine, which fails.
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);


        restMBiddingSubmissionLineMockMvc.perform(post("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBiddingSubmissionLineRepository.findAll().size();
        // set the field null
        mBiddingSubmissionLine.setDeliveryDate(null);

        // Create the MBiddingSubmissionLine, which fails.
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);


        restMBiddingSubmissionLineMockMvc.perform(post("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLines() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmissionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPriceSubmission").value(hasItem(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingSubmissionLine() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get the mBiddingSubmissionLine
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines/{id}", mBiddingSubmissionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingSubmissionLine.getId().intValue()))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.totalPriceSubmission").value(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingSubmissionLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        Long id = mBiddingSubmissionLine.getId();

        defaultMBiddingSubmissionLineShouldBeFound("id.equals=" + id);
        defaultMBiddingSubmissionLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingSubmissionLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingSubmissionLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingSubmissionLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingSubmissionLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice is not null
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.specified=true");

        // Get all the mBiddingSubmissionLineList where proposedPrice is null
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mBiddingSubmissionLineList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMBiddingSubmissionLineShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission equals to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.equals=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.equals=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission not equals to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.notEquals=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission not equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.notEquals=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission in DEFAULT_TOTAL_PRICE_SUBMISSION or UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.in=" + DEFAULT_TOTAL_PRICE_SUBMISSION + "," + UPDATED_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.in=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is not null
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.specified=true");

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is null
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is greater than or equal to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is greater than or equal to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is less than or equal to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is less than or equal to SMALLER_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is less than DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.lessThan=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is less than UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.lessThan=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByTotalPriceSubmissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is greater than DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldNotBeFound("totalPriceSubmission.greaterThan=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mBiddingSubmissionLineList where totalPriceSubmission is greater than SMALLER_TOTAL_PRICE_SUBMISSION
        defaultMBiddingSubmissionLineShouldBeFound("totalPriceSubmission.greaterThan=" + SMALLER_TOTAL_PRICE_SUBMISSION);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate is not null
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.specified=true");

        // Get all the mBiddingSubmissionLineList where deliveryDate is null
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mBiddingSubmissionLineList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultMBiddingSubmissionLineShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where uid equals to DEFAULT_UID
        defaultMBiddingSubmissionLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionLineList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where uid not equals to DEFAULT_UID
        defaultMBiddingSubmissionLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingSubmissionLineList where uid not equals to UPDATED_UID
        defaultMBiddingSubmissionLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingSubmissionLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingSubmissionLineList where uid equals to UPDATED_UID
        defaultMBiddingSubmissionLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where uid is not null
        defaultMBiddingSubmissionLineShouldBeFound("uid.specified=true");

        // Get all the mBiddingSubmissionLineList where uid is null
        defaultMBiddingSubmissionLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where active equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingSubmissionLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingSubmissionLineList where active not equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingSubmissionLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingSubmissionLineList where active equals to UPDATED_ACTIVE
        defaultMBiddingSubmissionLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        // Get all the mBiddingSubmissionLineList where active is not null
        defaultMBiddingSubmissionLineShouldBeFound("active.specified=true");

        // Get all the mBiddingSubmissionLineList where active is null
        defaultMBiddingSubmissionLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByMSubmissionSubItemIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);
        MSubmissionSubItem mSubmissionSubItem = MSubmissionSubItemResourceIT.createEntity(em);
        em.persist(mSubmissionSubItem);
        em.flush();
        mBiddingSubmissionLine.addMSubmissionSubItem(mSubmissionSubItem);
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);
        Long mSubmissionSubItemId = mSubmissionSubItem.getId();

        // Get all the mBiddingSubmissionLineList where mSubmissionSubItem equals to mSubmissionSubItemId
        defaultMBiddingSubmissionLineShouldBeFound("mSubmissionSubItemId.equals=" + mSubmissionSubItemId);

        // Get all the mBiddingSubmissionLineList where mSubmissionSubItem equals to mSubmissionSubItemId + 1
        defaultMBiddingSubmissionLineShouldNotBeFound("mSubmissionSubItemId.equals=" + (mSubmissionSubItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByBiddingLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingLine biddingLine = mBiddingSubmissionLine.getBiddingLine();
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);
        Long biddingLineId = biddingLine.getId();

        // Get all the mBiddingSubmissionLineList where biddingLine equals to biddingLineId
        defaultMBiddingSubmissionLineShouldBeFound("biddingLineId.equals=" + biddingLineId);

        // Get all the mBiddingSubmissionLineList where biddingLine equals to biddingLineId + 1
        defaultMBiddingSubmissionLineShouldNotBeFound("biddingLineId.equals=" + (biddingLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mBiddingSubmissionLine.getAdOrganization();
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingSubmissionLineList where adOrganization equals to adOrganizationId
        defaultMBiddingSubmissionLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingSubmissionLineList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingSubmissionLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingSubmissionLinesByMBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionLine.getMBiddingSubmission();
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);
        Long mBiddingSubmissionId = mBiddingSubmission.getId();

        // Get all the mBiddingSubmissionLineList where mBiddingSubmission equals to mBiddingSubmissionId
        defaultMBiddingSubmissionLineShouldBeFound("mBiddingSubmissionId.equals=" + mBiddingSubmissionId);

        // Get all the mBiddingSubmissionLineList where mBiddingSubmission equals to mBiddingSubmissionId + 1
        defaultMBiddingSubmissionLineShouldNotBeFound("mBiddingSubmissionId.equals=" + (mBiddingSubmissionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingSubmissionLineShouldBeFound(String filter) throws Exception {
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingSubmissionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPriceSubmission").value(hasItem(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingSubmissionLineShouldNotBeFound(String filter) throws Exception {
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMBiddingSubmissionLine() throws Exception {
        // Get the mBiddingSubmissionLine
        restMBiddingSubmissionLineMockMvc.perform(get("/api/m-bidding-submission-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingSubmissionLine() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        int databaseSizeBeforeUpdate = mBiddingSubmissionLineRepository.findAll().size();

        // Update the mBiddingSubmissionLine
        MBiddingSubmissionLine updatedMBiddingSubmissionLine = mBiddingSubmissionLineRepository.findById(mBiddingSubmissionLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingSubmissionLine are not directly saved in db
        em.detach(updatedMBiddingSubmissionLine);
        updatedMBiddingSubmissionLine
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .totalPriceSubmission(UPDATED_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(updatedMBiddingSubmissionLine);

        restMBiddingSubmissionLineMockMvc.perform(put("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingSubmissionLine in the database
        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeUpdate);
        MBiddingSubmissionLine testMBiddingSubmissionLine = mBiddingSubmissionLineList.get(mBiddingSubmissionLineList.size() - 1);
        assertThat(testMBiddingSubmissionLine.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMBiddingSubmissionLine.getTotalPriceSubmission()).isEqualTo(UPDATED_TOTAL_PRICE_SUBMISSION);
        assertThat(testMBiddingSubmissionLine.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testMBiddingSubmissionLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingSubmissionLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingSubmissionLine() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingSubmissionLineRepository.findAll().size();

        // Create the MBiddingSubmissionLine
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO = mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingSubmissionLineMockMvc.perform(put("/api/m-bidding-submission-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingSubmissionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingSubmissionLine in the database
        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingSubmissionLine() throws Exception {
        // Initialize the database
        mBiddingSubmissionLineRepository.saveAndFlush(mBiddingSubmissionLine);

        int databaseSizeBeforeDelete = mBiddingSubmissionLineRepository.findAll().size();

        // Delete the mBiddingSubmissionLine
        restMBiddingSubmissionLineMockMvc.perform(delete("/api/m-bidding-submission-lines/{id}", mBiddingSubmissionLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingSubmissionLine> mBiddingSubmissionLineList = mBiddingSubmissionLineRepository.findAll();
        assertThat(mBiddingSubmissionLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
