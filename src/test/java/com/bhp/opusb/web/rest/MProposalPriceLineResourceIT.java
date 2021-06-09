package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.domain.MProposalPriceSubItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.repository.MProposalPriceLineRepository;
import com.bhp.opusb.service.MProposalPriceLineService;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;
import com.bhp.opusb.service.mapper.MProposalPriceLineMapper;
import com.bhp.opusb.service.dto.MProposalPriceLineCriteria;
import com.bhp.opusb.service.MProposalPriceLineQueryService;

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
 * Integration tests for the {@link MProposalPriceLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalPriceLineResourceIT {

    private static final Boolean DEFAULT_DOCUMENT = false;
    private static final Boolean UPDATED_DOCUMENT = true;

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
    private MProposalPriceLineRepository mProposalPriceLineRepository;

    @Autowired
    private MProposalPriceLineMapper mProposalPriceLineMapper;

    @Autowired
    private MProposalPriceLineService mProposalPriceLineService;

    @Autowired
    private MProposalPriceLineQueryService mProposalPriceLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalPriceLineMockMvc;

    private MProposalPriceLine mProposalPriceLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPriceLine createEntity(EntityManager em) {
        MProposalPriceLine mProposalPriceLine = new MProposalPriceLine()
            .document(DEFAULT_DOCUMENT)
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
            .totalPriceSubmission(DEFAULT_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
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
        mProposalPriceLine.setAdOrganization(aDOrganization);
        // Add required entity
        MProposalPrice mProposalPrice;
        if (TestUtil.findAll(em, MProposalPrice.class).isEmpty()) {
            mProposalPrice = MProposalPriceResourceIT.createEntity(em);
            em.persist(mProposalPrice);
            em.flush();
        } else {
            mProposalPrice = TestUtil.findAll(em, MProposalPrice.class).get(0);
        }
        mProposalPriceLine.setProposalPrice(mProposalPrice);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mProposalPriceLine.setBiddingLine(mBiddingLine);
        return mProposalPriceLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPriceLine createUpdatedEntity(EntityManager em) {
        MProposalPriceLine mProposalPriceLine = new MProposalPriceLine()
            .document(UPDATED_DOCUMENT)
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .totalPriceSubmission(UPDATED_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(UPDATED_DELIVERY_DATE)
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
        mProposalPriceLine.setAdOrganization(aDOrganization);
        // Add required entity
        MProposalPrice mProposalPrice;
        if (TestUtil.findAll(em, MProposalPrice.class).isEmpty()) {
            mProposalPrice = MProposalPriceResourceIT.createUpdatedEntity(em);
            em.persist(mProposalPrice);
            em.flush();
        } else {
            mProposalPrice = TestUtil.findAll(em, MProposalPrice.class).get(0);
        }
        mProposalPriceLine.setProposalPrice(mProposalPrice);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mProposalPriceLine.setBiddingLine(mBiddingLine);
        return mProposalPriceLine;
    }

    @BeforeEach
    public void initTest() {
        mProposalPriceLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalPriceLine() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceLineRepository.findAll().size();

        // Create the MProposalPriceLine
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);
        restMProposalPriceLineMockMvc.perform(post("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalPriceLine in the database
        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalPriceLine testMProposalPriceLine = mProposalPriceLineList.get(mProposalPriceLineList.size() - 1);
        assertThat(testMProposalPriceLine.isDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testMProposalPriceLine.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMProposalPriceLine.getTotalPriceSubmission()).isEqualTo(DEFAULT_TOTAL_PRICE_SUBMISSION);
        assertThat(testMProposalPriceLine.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testMProposalPriceLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalPriceLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalPriceLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceLineRepository.findAll().size();

        // Create the MProposalPriceLine with an existing ID
        mProposalPriceLine.setId(1L);
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalPriceLineMockMvc.perform(post("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPriceLine in the database
        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceLineRepository.findAll().size();
        // set the field null
        mProposalPriceLine.setProposedPrice(null);

        // Create the MProposalPriceLine, which fails.
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);

        restMProposalPriceLineMockMvc.perform(post("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceSubmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceLineRepository.findAll().size();
        // set the field null
        mProposalPriceLine.setTotalPriceSubmission(null);

        // Create the MProposalPriceLine, which fails.
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);

        restMProposalPriceLineMockMvc.perform(post("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceLineRepository.findAll().size();
        // set the field null
        mProposalPriceLine.setDeliveryDate(null);

        // Create the MProposalPriceLine, which fails.
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);

        restMProposalPriceLineMockMvc.perform(post("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLines() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPriceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPriceSubmission").value(hasItem(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalPriceLine() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get the mProposalPriceLine
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines/{id}", mProposalPriceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalPriceLine.getId().intValue()))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT.booleanValue()))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.totalPriceSubmission").value(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalPriceLinesByIdFiltering() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        Long id = mProposalPriceLine.getId();

        defaultMProposalPriceLineShouldBeFound("id.equals=" + id);
        defaultMProposalPriceLineShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalPriceLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalPriceLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalPriceLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalPriceLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where document equals to DEFAULT_DOCUMENT
        defaultMProposalPriceLineShouldBeFound("document.equals=" + DEFAULT_DOCUMENT);

        // Get all the mProposalPriceLineList where document equals to UPDATED_DOCUMENT
        defaultMProposalPriceLineShouldNotBeFound("document.equals=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDocumentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where document not equals to DEFAULT_DOCUMENT
        defaultMProposalPriceLineShouldNotBeFound("document.notEquals=" + DEFAULT_DOCUMENT);

        // Get all the mProposalPriceLineList where document not equals to UPDATED_DOCUMENT
        defaultMProposalPriceLineShouldBeFound("document.notEquals=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where document in DEFAULT_DOCUMENT or UPDATED_DOCUMENT
        defaultMProposalPriceLineShouldBeFound("document.in=" + DEFAULT_DOCUMENT + "," + UPDATED_DOCUMENT);

        // Get all the mProposalPriceLineList where document equals to UPDATED_DOCUMENT
        defaultMProposalPriceLineShouldNotBeFound("document.in=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where document is not null
        defaultMProposalPriceLineShouldBeFound("document.specified=true");

        // Get all the mProposalPriceLineList where document is null
        defaultMProposalPriceLineShouldNotBeFound("document.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice is not null
        defaultMProposalPriceLineShouldBeFound("proposedPrice.specified=true");

        // Get all the mProposalPriceLineList where proposedPrice is null
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceLineShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceLineList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMProposalPriceLineShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission equals to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.equals=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.equals=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission not equals to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.notEquals=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission not equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.notEquals=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission in DEFAULT_TOTAL_PRICE_SUBMISSION or UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.in=" + DEFAULT_TOTAL_PRICE_SUBMISSION + "," + UPDATED_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission equals to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.in=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission is not null
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.specified=true");

        // Get all the mProposalPriceLineList where totalPriceSubmission is null
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission is greater than or equal to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission is greater than or equal to UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission is less than or equal to DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission is less than or equal to SMALLER_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission is less than DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.lessThan=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission is less than UPDATED_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.lessThan=" + UPDATED_TOTAL_PRICE_SUBMISSION);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByTotalPriceSubmissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where totalPriceSubmission is greater than DEFAULT_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldNotBeFound("totalPriceSubmission.greaterThan=" + DEFAULT_TOTAL_PRICE_SUBMISSION);

        // Get all the mProposalPriceLineList where totalPriceSubmission is greater than SMALLER_TOTAL_PRICE_SUBMISSION
        defaultMProposalPriceLineShouldBeFound("totalPriceSubmission.greaterThan=" + SMALLER_TOTAL_PRICE_SUBMISSION);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate is not null
        defaultMProposalPriceLineShouldBeFound("deliveryDate.specified=true");

        // Get all the mProposalPriceLineList where deliveryDate is null
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultMProposalPriceLineShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the mProposalPriceLineList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultMProposalPriceLineShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where uid equals to DEFAULT_UID
        defaultMProposalPriceLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalPriceLineList where uid equals to UPDATED_UID
        defaultMProposalPriceLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where uid not equals to DEFAULT_UID
        defaultMProposalPriceLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalPriceLineList where uid not equals to UPDATED_UID
        defaultMProposalPriceLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalPriceLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalPriceLineList where uid equals to UPDATED_UID
        defaultMProposalPriceLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where uid is not null
        defaultMProposalPriceLineShouldBeFound("uid.specified=true");

        // Get all the mProposalPriceLineList where uid is null
        defaultMProposalPriceLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where active equals to DEFAULT_ACTIVE
        defaultMProposalPriceLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceLineList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where active not equals to DEFAULT_ACTIVE
        defaultMProposalPriceLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceLineList where active not equals to UPDATED_ACTIVE
        defaultMProposalPriceLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalPriceLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalPriceLineList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        // Get all the mProposalPriceLineList where active is not null
        defaultMProposalPriceLineShouldBeFound("active.specified=true");

        // Get all the mProposalPriceLineList where active is null
        defaultMProposalPriceLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPriceLinesByMProposalPriceSubItemIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);
        MProposalPriceSubItem mProposalPriceSubItem = MProposalPriceSubItemResourceIT.createEntity(em);
        em.persist(mProposalPriceSubItem);
        em.flush();
        mProposalPriceLine.addMProposalPriceSubItem(mProposalPriceSubItem);
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);
        Long mProposalPriceSubItemId = mProposalPriceSubItem.getId();

        // Get all the mProposalPriceLineList where mProposalPriceSubItem equals to mProposalPriceSubItemId
        defaultMProposalPriceLineShouldBeFound("mProposalPriceSubItemId.equals=" + mProposalPriceSubItemId);

        // Get all the mProposalPriceLineList where mProposalPriceSubItem equals to mProposalPriceSubItemId + 1
        defaultMProposalPriceLineShouldNotBeFound("mProposalPriceSubItemId.equals=" + (mProposalPriceSubItemId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalPriceLine.getAdOrganization();
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalPriceLineList where adOrganization equals to adOrganizationId
        defaultMProposalPriceLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalPriceLineList where adOrganization equals to adOrganizationId + 1
        defaultMProposalPriceLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByProposalPriceIsEqualToSomething() throws Exception {
        // Get already existing entity
        MProposalPrice proposalPrice = mProposalPriceLine.getProposalPrice();
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);
        Long proposalPriceId = proposalPrice.getId();

        // Get all the mProposalPriceLineList where proposalPrice equals to proposalPriceId
        defaultMProposalPriceLineShouldBeFound("proposalPriceId.equals=" + proposalPriceId);

        // Get all the mProposalPriceLineList where proposalPrice equals to proposalPriceId + 1
        defaultMProposalPriceLineShouldNotBeFound("proposalPriceId.equals=" + (proposalPriceId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPriceLinesByBiddingLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingLine biddingLine = mProposalPriceLine.getBiddingLine();
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);
        Long biddingLineId = biddingLine.getId();

        // Get all the mProposalPriceLineList where biddingLine equals to biddingLineId
        defaultMProposalPriceLineShouldBeFound("biddingLineId.equals=" + biddingLineId);

        // Get all the mProposalPriceLineList where biddingLine equals to biddingLineId + 1
        defaultMProposalPriceLineShouldNotBeFound("biddingLineId.equals=" + (biddingLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalPriceLineShouldBeFound(String filter) throws Exception {
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPriceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPriceSubmission").value(hasItem(DEFAULT_TOTAL_PRICE_SUBMISSION.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalPriceLineShouldNotBeFound(String filter) throws Exception {
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalPriceLine() throws Exception {
        // Get the mProposalPriceLine
        restMProposalPriceLineMockMvc.perform(get("/api/m-proposal-price-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalPriceLine() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        int databaseSizeBeforeUpdate = mProposalPriceLineRepository.findAll().size();

        // Update the mProposalPriceLine
        MProposalPriceLine updatedMProposalPriceLine = mProposalPriceLineRepository.findById(mProposalPriceLine.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalPriceLine are not directly saved in db
        em.detach(updatedMProposalPriceLine);
        updatedMProposalPriceLine
            .document(UPDATED_DOCUMENT)
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .totalPriceSubmission(UPDATED_TOTAL_PRICE_SUBMISSION)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(updatedMProposalPriceLine);

        restMProposalPriceLineMockMvc.perform(put("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalPriceLine in the database
        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeUpdate);
        MProposalPriceLine testMProposalPriceLine = mProposalPriceLineList.get(mProposalPriceLineList.size() - 1);
        assertThat(testMProposalPriceLine.isDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testMProposalPriceLine.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMProposalPriceLine.getTotalPriceSubmission()).isEqualTo(UPDATED_TOTAL_PRICE_SUBMISSION);
        assertThat(testMProposalPriceLine.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testMProposalPriceLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalPriceLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalPriceLine() throws Exception {
        int databaseSizeBeforeUpdate = mProposalPriceLineRepository.findAll().size();

        // Create the MProposalPriceLine
        MProposalPriceLineDTO mProposalPriceLineDTO = mProposalPriceLineMapper.toDto(mProposalPriceLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalPriceLineMockMvc.perform(put("/api/m-proposal-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPriceLine in the database
        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalPriceLine() throws Exception {
        // Initialize the database
        mProposalPriceLineRepository.saveAndFlush(mProposalPriceLine);

        int databaseSizeBeforeDelete = mProposalPriceLineRepository.findAll().size();

        // Delete the mProposalPriceLine
        restMProposalPriceLineMockMvc.perform(delete("/api/m-proposal-price-lines/{id}", mProposalPriceLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalPriceLine> mProposalPriceLineList = mProposalPriceLineRepository.findAll();
        assertThat(mProposalPriceLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
