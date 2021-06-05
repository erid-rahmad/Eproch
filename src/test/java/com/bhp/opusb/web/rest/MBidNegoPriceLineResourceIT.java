package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBidNegoPriceLine;
import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.repository.MBidNegoPriceLineRepository;
import com.bhp.opusb.service.MBidNegoPriceLineService;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceLineMapper;
import com.bhp.opusb.service.dto.MBidNegoPriceLineCriteria;
import com.bhp.opusb.service.MBidNegoPriceLineQueryService;

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
 * Integration tests for the {@link MBidNegoPriceLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBidNegoPriceLineResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final BigDecimal DEFAULT_PRICE_NEGOTIATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_NEGOTIATION = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE_NEGOTIATION = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_NEGOTIATION_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_NEGOTIATION_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_NEGOTIATION_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_NEGOTIATION_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEGOTIATION_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_NEGOTIATION_PERCENTAGE = new BigDecimal(1 - 1);

    @Autowired
    private MBidNegoPriceLineRepository mBidNegoPriceLineRepository;

    @Autowired
    private MBidNegoPriceLineMapper mBidNegoPriceLineMapper;

    @Autowired
    private MBidNegoPriceLineService mBidNegoPriceLineService;

    @Autowired
    private MBidNegoPriceLineQueryService mBidNegoPriceLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBidNegoPriceLineMockMvc;

    private MBidNegoPriceLine mBidNegoPriceLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidNegoPriceLine createEntity(EntityManager em) {
        MBidNegoPriceLine mBidNegoPriceLine = new MBidNegoPriceLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .priceNegotiation(DEFAULT_PRICE_NEGOTIATION)
            .totalNegotiationPrice(DEFAULT_TOTAL_NEGOTIATION_PRICE)
            .negotiationPercentage(DEFAULT_NEGOTIATION_PERCENTAGE);
        // Add required entity
        MBidNegoPrice mBidNegoPrice;
        if (TestUtil.findAll(em, MBidNegoPrice.class).isEmpty()) {
            mBidNegoPrice = MBidNegoPriceResourceIT.createEntity(em);
            em.persist(mBidNegoPrice);
            em.flush();
        } else {
            mBidNegoPrice = TestUtil.findAll(em, MBidNegoPrice.class).get(0);
        }
        mBidNegoPriceLine.setBidNegoPrice(mBidNegoPrice);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mBidNegoPriceLine.setBiddingLine(mBiddingLine);
        // Add required entity
        MProposalPriceLine mProposalPriceLine;
        if (TestUtil.findAll(em, MProposalPriceLine.class).isEmpty()) {
            mProposalPriceLine = MProposalPriceLineResourceIT.createEntity(em);
            em.persist(mProposalPriceLine);
            em.flush();
        } else {
            mProposalPriceLine = TestUtil.findAll(em, MProposalPriceLine.class).get(0);
        }
        mBidNegoPriceLine.setProposalLine(mProposalPriceLine);
        return mBidNegoPriceLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidNegoPriceLine createUpdatedEntity(EntityManager em) {
        MBidNegoPriceLine mBidNegoPriceLine = new MBidNegoPriceLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .priceNegotiation(UPDATED_PRICE_NEGOTIATION)
            .totalNegotiationPrice(UPDATED_TOTAL_NEGOTIATION_PRICE)
            .negotiationPercentage(UPDATED_NEGOTIATION_PERCENTAGE);
        // Add required entity
        MBidNegoPrice mBidNegoPrice;
        if (TestUtil.findAll(em, MBidNegoPrice.class).isEmpty()) {
            mBidNegoPrice = MBidNegoPriceResourceIT.createUpdatedEntity(em);
            em.persist(mBidNegoPrice);
            em.flush();
        } else {
            mBidNegoPrice = TestUtil.findAll(em, MBidNegoPrice.class).get(0);
        }
        mBidNegoPriceLine.setBidNegoPrice(mBidNegoPrice);
        // Add required entity
        MBiddingLine mBiddingLine;
        if (TestUtil.findAll(em, MBiddingLine.class).isEmpty()) {
            mBiddingLine = MBiddingLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingLine);
            em.flush();
        } else {
            mBiddingLine = TestUtil.findAll(em, MBiddingLine.class).get(0);
        }
        mBidNegoPriceLine.setBiddingLine(mBiddingLine);
        // Add required entity
        MProposalPriceLine mProposalPriceLine;
        if (TestUtil.findAll(em, MProposalPriceLine.class).isEmpty()) {
            mProposalPriceLine = MProposalPriceLineResourceIT.createUpdatedEntity(em);
            em.persist(mProposalPriceLine);
            em.flush();
        } else {
            mProposalPriceLine = TestUtil.findAll(em, MProposalPriceLine.class).get(0);
        }
        mBidNegoPriceLine.setProposalLine(mProposalPriceLine);
        return mBidNegoPriceLine;
    }

    @BeforeEach
    public void initTest() {
        mBidNegoPriceLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBidNegoPriceLine() throws Exception {
        int databaseSizeBeforeCreate = mBidNegoPriceLineRepository.findAll().size();

        // Create the MBidNegoPriceLine
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);
        restMBidNegoPriceLineMockMvc.perform(post("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MBidNegoPriceLine in the database
        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeCreate + 1);
        MBidNegoPriceLine testMBidNegoPriceLine = mBidNegoPriceLineList.get(mBidNegoPriceLineList.size() - 1);
        assertThat(testMBidNegoPriceLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBidNegoPriceLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBidNegoPriceLine.getPriceNegotiation()).isEqualTo(DEFAULT_PRICE_NEGOTIATION);
        assertThat(testMBidNegoPriceLine.getTotalNegotiationPrice()).isEqualTo(DEFAULT_TOTAL_NEGOTIATION_PRICE);
        assertThat(testMBidNegoPriceLine.getNegotiationPercentage()).isEqualTo(DEFAULT_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createMBidNegoPriceLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBidNegoPriceLineRepository.findAll().size();

        // Create the MBidNegoPriceLine with an existing ID
        mBidNegoPriceLine.setId(1L);
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBidNegoPriceLineMockMvc.perform(post("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidNegoPriceLine in the database
        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceNegotiationIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBidNegoPriceLineRepository.findAll().size();
        // set the field null
        mBidNegoPriceLine.setPriceNegotiation(null);

        // Create the MBidNegoPriceLine, which fails.
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);

        restMBidNegoPriceLineMockMvc.perform(post("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalNegotiationPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBidNegoPriceLineRepository.findAll().size();
        // set the field null
        mBidNegoPriceLine.setTotalNegotiationPrice(null);

        // Create the MBidNegoPriceLine, which fails.
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);

        restMBidNegoPriceLineMockMvc.perform(post("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNegotiationPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBidNegoPriceLineRepository.findAll().size();
        // set the field null
        mBidNegoPriceLine.setNegotiationPercentage(null);

        // Create the MBidNegoPriceLine, which fails.
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);

        restMBidNegoPriceLineMockMvc.perform(post("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isBadRequest());

        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLines() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidNegoPriceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].priceNegotiation").value(hasItem(DEFAULT_PRICE_NEGOTIATION.intValue())))
            .andExpect(jsonPath("$.[*].totalNegotiationPrice").value(hasItem(DEFAULT_TOTAL_NEGOTIATION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].negotiationPercentage").value(hasItem(DEFAULT_NEGOTIATION_PERCENTAGE.intValue())));
    }
    
    @Test
    @Transactional
    public void getMBidNegoPriceLine() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get the mBidNegoPriceLine
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines/{id}", mBidNegoPriceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBidNegoPriceLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.priceNegotiation").value(DEFAULT_PRICE_NEGOTIATION.intValue()))
            .andExpect(jsonPath("$.totalNegotiationPrice").value(DEFAULT_TOTAL_NEGOTIATION_PRICE.intValue()))
            .andExpect(jsonPath("$.negotiationPercentage").value(DEFAULT_NEGOTIATION_PERCENTAGE.intValue()));
    }


    @Test
    @Transactional
    public void getMBidNegoPriceLinesByIdFiltering() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        Long id = mBidNegoPriceLine.getId();

        defaultMBidNegoPriceLineShouldBeFound("id.equals=" + id);
        defaultMBidNegoPriceLineShouldNotBeFound("id.notEquals=" + id);

        defaultMBidNegoPriceLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBidNegoPriceLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMBidNegoPriceLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBidNegoPriceLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where uid equals to DEFAULT_UID
        defaultMBidNegoPriceLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBidNegoPriceLineList where uid equals to UPDATED_UID
        defaultMBidNegoPriceLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where uid not equals to DEFAULT_UID
        defaultMBidNegoPriceLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBidNegoPriceLineList where uid not equals to UPDATED_UID
        defaultMBidNegoPriceLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBidNegoPriceLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBidNegoPriceLineList where uid equals to UPDATED_UID
        defaultMBidNegoPriceLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where uid is not null
        defaultMBidNegoPriceLineShouldBeFound("uid.specified=true");

        // Get all the mBidNegoPriceLineList where uid is null
        defaultMBidNegoPriceLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where active equals to DEFAULT_ACTIVE
        defaultMBidNegoPriceLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBidNegoPriceLineList where active equals to UPDATED_ACTIVE
        defaultMBidNegoPriceLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where active not equals to DEFAULT_ACTIVE
        defaultMBidNegoPriceLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBidNegoPriceLineList where active not equals to UPDATED_ACTIVE
        defaultMBidNegoPriceLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBidNegoPriceLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBidNegoPriceLineList where active equals to UPDATED_ACTIVE
        defaultMBidNegoPriceLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where active is not null
        defaultMBidNegoPriceLineShouldBeFound("active.specified=true");

        // Get all the mBidNegoPriceLineList where active is null
        defaultMBidNegoPriceLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation equals to DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.equals=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation equals to UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.equals=" + UPDATED_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation not equals to DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.notEquals=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation not equals to UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.notEquals=" + UPDATED_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation in DEFAULT_PRICE_NEGOTIATION or UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.in=" + DEFAULT_PRICE_NEGOTIATION + "," + UPDATED_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation equals to UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.in=" + UPDATED_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation is not null
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.specified=true");

        // Get all the mBidNegoPriceLineList where priceNegotiation is null
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation is greater than or equal to DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.greaterThanOrEqual=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation is greater than or equal to UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.greaterThanOrEqual=" + UPDATED_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation is less than or equal to DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.lessThanOrEqual=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation is less than or equal to SMALLER_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.lessThanOrEqual=" + SMALLER_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsLessThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation is less than DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.lessThan=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation is less than UPDATED_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.lessThan=" + UPDATED_PRICE_NEGOTIATION);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByPriceNegotiationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where priceNegotiation is greater than DEFAULT_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldNotBeFound("priceNegotiation.greaterThan=" + DEFAULT_PRICE_NEGOTIATION);

        // Get all the mBidNegoPriceLineList where priceNegotiation is greater than SMALLER_PRICE_NEGOTIATION
        defaultMBidNegoPriceLineShouldBeFound("priceNegotiation.greaterThan=" + SMALLER_PRICE_NEGOTIATION);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice equals to DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.equals=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice equals to UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.equals=" + UPDATED_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice not equals to DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.notEquals=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice not equals to UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.notEquals=" + UPDATED_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice in DEFAULT_TOTAL_NEGOTIATION_PRICE or UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.in=" + DEFAULT_TOTAL_NEGOTIATION_PRICE + "," + UPDATED_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice equals to UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.in=" + UPDATED_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is not null
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.specified=true");

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is null
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is greater than or equal to DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is greater than or equal to UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.greaterThanOrEqual=" + UPDATED_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is less than or equal to DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.lessThanOrEqual=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is less than or equal to SMALLER_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.lessThanOrEqual=" + SMALLER_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is less than DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.lessThan=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is less than UPDATED_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.lessThan=" + UPDATED_TOTAL_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByTotalNegotiationPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is greater than DEFAULT_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldNotBeFound("totalNegotiationPrice.greaterThan=" + DEFAULT_TOTAL_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceLineList where totalNegotiationPrice is greater than SMALLER_TOTAL_NEGOTIATION_PRICE
        defaultMBidNegoPriceLineShouldBeFound("totalNegotiationPrice.greaterThan=" + SMALLER_TOTAL_NEGOTIATION_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage equals to DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.equals=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage equals to UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.equals=" + UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage not equals to DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.notEquals=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage not equals to UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.notEquals=" + UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage in DEFAULT_NEGOTIATION_PERCENTAGE or UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.in=" + DEFAULT_NEGOTIATION_PERCENTAGE + "," + UPDATED_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage equals to UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.in=" + UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is not null
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.specified=true");

        // Get all the mBidNegoPriceLineList where negotiationPercentage is null
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is greater than or equal to DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.greaterThanOrEqual=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is greater than or equal to UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.greaterThanOrEqual=" + UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is less than or equal to DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.lessThanOrEqual=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is less than or equal to SMALLER_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.lessThanOrEqual=" + SMALLER_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is less than DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.lessThan=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is less than UPDATED_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.lessThan=" + UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByNegotiationPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is greater than DEFAULT_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldNotBeFound("negotiationPercentage.greaterThan=" + DEFAULT_NEGOTIATION_PERCENTAGE);

        // Get all the mBidNegoPriceLineList where negotiationPercentage is greater than SMALLER_NEGOTIATION_PERCENTAGE
        defaultMBidNegoPriceLineShouldBeFound("negotiationPercentage.greaterThan=" + SMALLER_NEGOTIATION_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByBidNegoPriceIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidNegoPrice bidNegoPrice = mBidNegoPriceLine.getBidNegoPrice();
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);
        Long bidNegoPriceId = bidNegoPrice.getId();

        // Get all the mBidNegoPriceLineList where bidNegoPrice equals to bidNegoPriceId
        defaultMBidNegoPriceLineShouldBeFound("bidNegoPriceId.equals=" + bidNegoPriceId);

        // Get all the mBidNegoPriceLineList where bidNegoPrice equals to bidNegoPriceId + 1
        defaultMBidNegoPriceLineShouldNotBeFound("bidNegoPriceId.equals=" + (bidNegoPriceId + 1));
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByBiddingLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingLine biddingLine = mBidNegoPriceLine.getBiddingLine();
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);
        Long biddingLineId = biddingLine.getId();

        // Get all the mBidNegoPriceLineList where biddingLine equals to biddingLineId
        defaultMBidNegoPriceLineShouldBeFound("biddingLineId.equals=" + biddingLineId);

        // Get all the mBidNegoPriceLineList where biddingLine equals to biddingLineId + 1
        defaultMBidNegoPriceLineShouldNotBeFound("biddingLineId.equals=" + (biddingLineId + 1));
    }


    @Test
    @Transactional
    public void getAllMBidNegoPriceLinesByProposalLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MProposalPriceLine proposalLine = mBidNegoPriceLine.getProposalLine();
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);
        Long proposalLineId = proposalLine.getId();

        // Get all the mBidNegoPriceLineList where proposalLine equals to proposalLineId
        defaultMBidNegoPriceLineShouldBeFound("proposalLineId.equals=" + proposalLineId);

        // Get all the mBidNegoPriceLineList where proposalLine equals to proposalLineId + 1
        defaultMBidNegoPriceLineShouldNotBeFound("proposalLineId.equals=" + (proposalLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBidNegoPriceLineShouldBeFound(String filter) throws Exception {
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidNegoPriceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].priceNegotiation").value(hasItem(DEFAULT_PRICE_NEGOTIATION.intValue())))
            .andExpect(jsonPath("$.[*].totalNegotiationPrice").value(hasItem(DEFAULT_TOTAL_NEGOTIATION_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].negotiationPercentage").value(hasItem(DEFAULT_NEGOTIATION_PERCENTAGE.intValue())));

        // Check, that the count call also returns 1
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBidNegoPriceLineShouldNotBeFound(String filter) throws Exception {
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBidNegoPriceLine() throws Exception {
        // Get the mBidNegoPriceLine
        restMBidNegoPriceLineMockMvc.perform(get("/api/m-bid-nego-price-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBidNegoPriceLine() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        int databaseSizeBeforeUpdate = mBidNegoPriceLineRepository.findAll().size();

        // Update the mBidNegoPriceLine
        MBidNegoPriceLine updatedMBidNegoPriceLine = mBidNegoPriceLineRepository.findById(mBidNegoPriceLine.getId()).get();
        // Disconnect from session so that the updates on updatedMBidNegoPriceLine are not directly saved in db
        em.detach(updatedMBidNegoPriceLine);
        updatedMBidNegoPriceLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .priceNegotiation(UPDATED_PRICE_NEGOTIATION)
            .totalNegotiationPrice(UPDATED_TOTAL_NEGOTIATION_PRICE)
            .negotiationPercentage(UPDATED_NEGOTIATION_PERCENTAGE);
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(updatedMBidNegoPriceLine);

        restMBidNegoPriceLineMockMvc.perform(put("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isOk());

        // Validate the MBidNegoPriceLine in the database
        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeUpdate);
        MBidNegoPriceLine testMBidNegoPriceLine = mBidNegoPriceLineList.get(mBidNegoPriceLineList.size() - 1);
        assertThat(testMBidNegoPriceLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBidNegoPriceLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBidNegoPriceLine.getPriceNegotiation()).isEqualTo(UPDATED_PRICE_NEGOTIATION);
        assertThat(testMBidNegoPriceLine.getTotalNegotiationPrice()).isEqualTo(UPDATED_TOTAL_NEGOTIATION_PRICE);
        assertThat(testMBidNegoPriceLine.getNegotiationPercentage()).isEqualTo(UPDATED_NEGOTIATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBidNegoPriceLine() throws Exception {
        int databaseSizeBeforeUpdate = mBidNegoPriceLineRepository.findAll().size();

        // Create the MBidNegoPriceLine
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBidNegoPriceLineMockMvc.perform(put("/api/m-bid-nego-price-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidNegoPriceLine in the database
        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBidNegoPriceLine() throws Exception {
        // Initialize the database
        mBidNegoPriceLineRepository.saveAndFlush(mBidNegoPriceLine);

        int databaseSizeBeforeDelete = mBidNegoPriceLineRepository.findAll().size();

        // Delete the mBidNegoPriceLine
        restMBidNegoPriceLineMockMvc.perform(delete("/api/m-bid-nego-price-lines/{id}", mBidNegoPriceLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBidNegoPriceLine> mBidNegoPriceLineList = mBidNegoPriceLineRepository.findAll();
        assertThat(mBidNegoPriceLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
