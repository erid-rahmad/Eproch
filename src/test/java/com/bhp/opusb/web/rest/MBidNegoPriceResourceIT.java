package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MVendorInvitation;
import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.repository.MBidNegoPriceRepository;
import com.bhp.opusb.service.MBidNegoPriceService;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceMapper;
import com.bhp.opusb.service.dto.MBidNegoPriceCriteria;
import com.bhp.opusb.service.MBidNegoPriceQueryService;

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
 * Integration tests for the {@link MBidNegoPriceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBidNegoPriceResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final BigDecimal DEFAULT_NEGOTIATION_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEGOTIATION_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_NEGOTIATION_PRICE = new BigDecimal(1 - 1);

    @Autowired
    private MBidNegoPriceRepository mBidNegoPriceRepository;

    @Autowired
    private MBidNegoPriceMapper mBidNegoPriceMapper;

    @Autowired
    private MBidNegoPriceService mBidNegoPriceService;

    @Autowired
    private MBidNegoPriceQueryService mBidNegoPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBidNegoPriceMockMvc;

    private MBidNegoPrice mBidNegoPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidNegoPrice createEntity(EntityManager em) {
        MBidNegoPrice mBidNegoPrice = new MBidNegoPrice()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .negotiationPrice(DEFAULT_NEGOTIATION_PRICE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBidNegoPrice.setBidding(mBidding);
        // Add required entity
        MVendorInvitation mVendorInvitation;
        if (TestUtil.findAll(em, MVendorInvitation.class).isEmpty()) {
            mVendorInvitation = MVendorInvitationResourceIT.createEntity(em);
            em.persist(mVendorInvitation);
            em.flush();
        } else {
            mVendorInvitation = TestUtil.findAll(em, MVendorInvitation.class).get(0);
        }
        mBidNegoPrice.setVendorInvitation(mVendorInvitation);
        // Add required entity
        MProposalPrice mProposalPrice;
        if (TestUtil.findAll(em, MProposalPrice.class).isEmpty()) {
            mProposalPrice = MProposalPriceResourceIT.createEntity(em);
            em.persist(mProposalPrice);
            em.flush();
        } else {
            mProposalPrice = TestUtil.findAll(em, MProposalPrice.class).get(0);
        }
        mBidNegoPrice.setPriceProposal(mProposalPrice);
        // Add required entity
        MBiddingNegotiationLine mBiddingNegotiationLine;
        if (TestUtil.findAll(em, MBiddingNegotiationLine.class).isEmpty()) {
            mBiddingNegotiationLine = MBiddingNegotiationLineResourceIT.createEntity(em);
            em.persist(mBiddingNegotiationLine);
            em.flush();
        } else {
            mBiddingNegotiationLine = TestUtil.findAll(em, MBiddingNegotiationLine.class).get(0);
        }
        mBidNegoPrice.setNegotiationLine(mBiddingNegotiationLine);
        return mBidNegoPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBidNegoPrice createUpdatedEntity(EntityManager em) {
        MBidNegoPrice mBidNegoPrice = new MBidNegoPrice()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .negotiationPrice(UPDATED_NEGOTIATION_PRICE);
        // Add required entity
        MBidding mBidding;
        if (TestUtil.findAll(em, MBidding.class).isEmpty()) {
            mBidding = MBiddingResourceIT.createUpdatedEntity(em);
            em.persist(mBidding);
            em.flush();
        } else {
            mBidding = TestUtil.findAll(em, MBidding.class).get(0);
        }
        mBidNegoPrice.setBidding(mBidding);
        // Add required entity
        MVendorInvitation mVendorInvitation;
        if (TestUtil.findAll(em, MVendorInvitation.class).isEmpty()) {
            mVendorInvitation = MVendorInvitationResourceIT.createUpdatedEntity(em);
            em.persist(mVendorInvitation);
            em.flush();
        } else {
            mVendorInvitation = TestUtil.findAll(em, MVendorInvitation.class).get(0);
        }
        mBidNegoPrice.setVendorInvitation(mVendorInvitation);
        // Add required entity
        MProposalPrice mProposalPrice;
        if (TestUtil.findAll(em, MProposalPrice.class).isEmpty()) {
            mProposalPrice = MProposalPriceResourceIT.createUpdatedEntity(em);
            em.persist(mProposalPrice);
            em.flush();
        } else {
            mProposalPrice = TestUtil.findAll(em, MProposalPrice.class).get(0);
        }
        mBidNegoPrice.setPriceProposal(mProposalPrice);
        // Add required entity
        MBiddingNegotiationLine mBiddingNegotiationLine;
        if (TestUtil.findAll(em, MBiddingNegotiationLine.class).isEmpty()) {
            mBiddingNegotiationLine = MBiddingNegotiationLineResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingNegotiationLine);
            em.flush();
        } else {
            mBiddingNegotiationLine = TestUtil.findAll(em, MBiddingNegotiationLine.class).get(0);
        }
        mBidNegoPrice.setNegotiationLine(mBiddingNegotiationLine);
        return mBidNegoPrice;
    }

    @BeforeEach
    public void initTest() {
        mBidNegoPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBidNegoPrice() throws Exception {
        int databaseSizeBeforeCreate = mBidNegoPriceRepository.findAll().size();

        // Create the MBidNegoPrice
        MBidNegoPriceDTO mBidNegoPriceDTO = mBidNegoPriceMapper.toDto(mBidNegoPrice);
        restMBidNegoPriceMockMvc.perform(post("/api/m-bid-nego-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the MBidNegoPrice in the database
        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeCreate + 1);
        MBidNegoPrice testMBidNegoPrice = mBidNegoPriceList.get(mBidNegoPriceList.size() - 1);
        assertThat(testMBidNegoPrice.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBidNegoPrice.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMBidNegoPrice.getNegotiationPrice()).isEqualTo(DEFAULT_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void createMBidNegoPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBidNegoPriceRepository.findAll().size();

        // Create the MBidNegoPrice with an existing ID
        mBidNegoPrice.setId(1L);
        MBidNegoPriceDTO mBidNegoPriceDTO = mBidNegoPriceMapper.toDto(mBidNegoPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBidNegoPriceMockMvc.perform(post("/api/m-bid-nego-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidNegoPrice in the database
        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNegotiationPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mBidNegoPriceRepository.findAll().size();
        // set the field null
        mBidNegoPrice.setNegotiationPrice(null);

        // Create the MBidNegoPrice, which fails.
        MBidNegoPriceDTO mBidNegoPriceDTO = mBidNegoPriceMapper.toDto(mBidNegoPrice);

        restMBidNegoPriceMockMvc.perform(post("/api/m-bid-nego-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceDTO)))
            .andExpect(status().isBadRequest());

        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPrices() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidNegoPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].negotiationPrice").value(hasItem(DEFAULT_NEGOTIATION_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getMBidNegoPrice() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get the mBidNegoPrice
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices/{id}", mBidNegoPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBidNegoPrice.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.negotiationPrice").value(DEFAULT_NEGOTIATION_PRICE.intValue()));
    }


    @Test
    @Transactional
    public void getMBidNegoPricesByIdFiltering() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        Long id = mBidNegoPrice.getId();

        defaultMBidNegoPriceShouldBeFound("id.equals=" + id);
        defaultMBidNegoPriceShouldNotBeFound("id.notEquals=" + id);

        defaultMBidNegoPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBidNegoPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultMBidNegoPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBidNegoPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPricesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where uid equals to DEFAULT_UID
        defaultMBidNegoPriceShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBidNegoPriceList where uid equals to UPDATED_UID
        defaultMBidNegoPriceShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where uid not equals to DEFAULT_UID
        defaultMBidNegoPriceShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBidNegoPriceList where uid not equals to UPDATED_UID
        defaultMBidNegoPriceShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBidNegoPriceShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBidNegoPriceList where uid equals to UPDATED_UID
        defaultMBidNegoPriceShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where uid is not null
        defaultMBidNegoPriceShouldBeFound("uid.specified=true");

        // Get all the mBidNegoPriceList where uid is null
        defaultMBidNegoPriceShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where active equals to DEFAULT_ACTIVE
        defaultMBidNegoPriceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBidNegoPriceList where active equals to UPDATED_ACTIVE
        defaultMBidNegoPriceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where active not equals to DEFAULT_ACTIVE
        defaultMBidNegoPriceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBidNegoPriceList where active not equals to UPDATED_ACTIVE
        defaultMBidNegoPriceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBidNegoPriceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBidNegoPriceList where active equals to UPDATED_ACTIVE
        defaultMBidNegoPriceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where active is not null
        defaultMBidNegoPriceShouldBeFound("active.specified=true");

        // Get all the mBidNegoPriceList where active is null
        defaultMBidNegoPriceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice equals to DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.equals=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice equals to UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.equals=" + UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice not equals to DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.notEquals=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice not equals to UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.notEquals=" + UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice in DEFAULT_NEGOTIATION_PRICE or UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.in=" + DEFAULT_NEGOTIATION_PRICE + "," + UPDATED_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice equals to UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.in=" + UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice is not null
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.specified=true");

        // Get all the mBidNegoPriceList where negotiationPrice is null
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice is greater than or equal to DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.greaterThanOrEqual=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice is greater than or equal to UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.greaterThanOrEqual=" + UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice is less than or equal to DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.lessThanOrEqual=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice is less than or equal to SMALLER_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.lessThanOrEqual=" + SMALLER_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice is less than DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.lessThan=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice is less than UPDATED_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.lessThan=" + UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        // Get all the mBidNegoPriceList where negotiationPrice is greater than DEFAULT_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldNotBeFound("negotiationPrice.greaterThan=" + DEFAULT_NEGOTIATION_PRICE);

        // Get all the mBidNegoPriceList where negotiationPrice is greater than SMALLER_NEGOTIATION_PRICE
        defaultMBidNegoPriceShouldBeFound("negotiationPrice.greaterThan=" + SMALLER_NEGOTIATION_PRICE);
    }


    @Test
    @Transactional
    public void getAllMBidNegoPricesByBiddingIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBidding bidding = mBidNegoPrice.getBidding();
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);
        Long biddingId = bidding.getId();

        // Get all the mBidNegoPriceList where bidding equals to biddingId
        defaultMBidNegoPriceShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBidNegoPriceList where bidding equals to biddingId + 1
        defaultMBidNegoPriceShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBidNegoPricesByVendorInvitationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorInvitation vendorInvitation = mBidNegoPrice.getVendorInvitation();
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);
        Long vendorInvitationId = vendorInvitation.getId();

        // Get all the mBidNegoPriceList where vendorInvitation equals to vendorInvitationId
        defaultMBidNegoPriceShouldBeFound("vendorInvitationId.equals=" + vendorInvitationId);

        // Get all the mBidNegoPriceList where vendorInvitation equals to vendorInvitationId + 1
        defaultMBidNegoPriceShouldNotBeFound("vendorInvitationId.equals=" + (vendorInvitationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBidNegoPricesByPriceProposalIsEqualToSomething() throws Exception {
        // Get already existing entity
        MProposalPrice priceProposal = mBidNegoPrice.getPriceProposal();
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);
        Long priceProposalId = priceProposal.getId();

        // Get all the mBidNegoPriceList where priceProposal equals to priceProposalId
        defaultMBidNegoPriceShouldBeFound("priceProposalId.equals=" + priceProposalId);

        // Get all the mBidNegoPriceList where priceProposal equals to priceProposalId + 1
        defaultMBidNegoPriceShouldNotBeFound("priceProposalId.equals=" + (priceProposalId + 1));
    }


    @Test
    @Transactional
    public void getAllMBidNegoPricesByNegotiationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingNegotiationLine negotiationLine = mBidNegoPrice.getNegotiationLine();
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);
        Long negotiationLineId = negotiationLine.getId();

        // Get all the mBidNegoPriceList where negotiationLine equals to negotiationLineId
        defaultMBidNegoPriceShouldBeFound("negotiationLineId.equals=" + negotiationLineId);

        // Get all the mBidNegoPriceList where negotiationLine equals to negotiationLineId + 1
        defaultMBidNegoPriceShouldNotBeFound("negotiationLineId.equals=" + (negotiationLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBidNegoPriceShouldBeFound(String filter) throws Exception {
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBidNegoPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].negotiationPrice").value(hasItem(DEFAULT_NEGOTIATION_PRICE.intValue())));

        // Check, that the count call also returns 1
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBidNegoPriceShouldNotBeFound(String filter) throws Exception {
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBidNegoPrice() throws Exception {
        // Get the mBidNegoPrice
        restMBidNegoPriceMockMvc.perform(get("/api/m-bid-nego-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBidNegoPrice() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        int databaseSizeBeforeUpdate = mBidNegoPriceRepository.findAll().size();

        // Update the mBidNegoPrice
        MBidNegoPrice updatedMBidNegoPrice = mBidNegoPriceRepository.findById(mBidNegoPrice.getId()).get();
        // Disconnect from session so that the updates on updatedMBidNegoPrice are not directly saved in db
        em.detach(updatedMBidNegoPrice);
        updatedMBidNegoPrice
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .negotiationPrice(UPDATED_NEGOTIATION_PRICE);
        MBidNegoPriceDTO mBidNegoPriceDTO = mBidNegoPriceMapper.toDto(updatedMBidNegoPrice);

        restMBidNegoPriceMockMvc.perform(put("/api/m-bid-nego-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceDTO)))
            .andExpect(status().isOk());

        // Validate the MBidNegoPrice in the database
        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeUpdate);
        MBidNegoPrice testMBidNegoPrice = mBidNegoPriceList.get(mBidNegoPriceList.size() - 1);
        assertThat(testMBidNegoPrice.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBidNegoPrice.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMBidNegoPrice.getNegotiationPrice()).isEqualTo(UPDATED_NEGOTIATION_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBidNegoPrice() throws Exception {
        int databaseSizeBeforeUpdate = mBidNegoPriceRepository.findAll().size();

        // Create the MBidNegoPrice
        MBidNegoPriceDTO mBidNegoPriceDTO = mBidNegoPriceMapper.toDto(mBidNegoPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBidNegoPriceMockMvc.perform(put("/api/m-bid-nego-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBidNegoPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBidNegoPrice in the database
        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBidNegoPrice() throws Exception {
        // Initialize the database
        mBidNegoPriceRepository.saveAndFlush(mBidNegoPrice);

        int databaseSizeBeforeDelete = mBidNegoPriceRepository.findAll().size();

        // Delete the mBidNegoPrice
        restMBidNegoPriceMockMvc.perform(delete("/api/m-bid-nego-prices/{id}", mBidNegoPrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBidNegoPrice> mBidNegoPriceList = mBidNegoPriceRepository.findAll();
        assertThat(mBidNegoPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
