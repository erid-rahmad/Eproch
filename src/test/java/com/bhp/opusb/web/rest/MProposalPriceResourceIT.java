package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.MProposalPriceRepository;
import com.bhp.opusb.service.MProposalPriceService;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.mapper.MProposalPriceMapper;
import com.bhp.opusb.service.dto.MProposalPriceCriteria;
import com.bhp.opusb.service.MProposalPriceQueryService;

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
 * Integration tests for the {@link MProposalPriceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MProposalPriceResourceIT {

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
    private MProposalPriceRepository mProposalPriceRepository;

    @Autowired
    private MProposalPriceMapper mProposalPriceMapper;

    @Autowired
    private MProposalPriceService mProposalPriceService;

    @Autowired
    private MProposalPriceQueryService mProposalPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMProposalPriceMockMvc;

    private MProposalPrice mProposalPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPrice createEntity(EntityManager em) {
        MProposalPrice mProposalPrice = new MProposalPrice()
            .proposedPrice(DEFAULT_PROPOSED_PRICE)
            .ceilingPrice(DEFAULT_CEILING_PRICE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalPrice.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalPrice.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mProposalPrice.setAttachment(cAttachment);
        return mProposalPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MProposalPrice createUpdatedEntity(EntityManager em) {
        MProposalPrice mProposalPrice = new MProposalPrice()
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        MBiddingSubmission mBiddingSubmission;
        if (TestUtil.findAll(em, MBiddingSubmission.class).isEmpty()) {
            mBiddingSubmission = MBiddingSubmissionResourceIT.createUpdatedEntity(em);
            em.persist(mBiddingSubmission);
            em.flush();
        } else {
            mBiddingSubmission = TestUtil.findAll(em, MBiddingSubmission.class).get(0);
        }
        mProposalPrice.setBiddingSubmission(mBiddingSubmission);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mProposalPrice.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mProposalPrice.setAttachment(cAttachment);
        return mProposalPrice;
    }

    @BeforeEach
    public void initTest() {
        mProposalPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createMProposalPrice() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceRepository.findAll().size();

        // Create the MProposalPrice
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(mProposalPrice);
        restMProposalPriceMockMvc.perform(post("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the MProposalPrice in the database
        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeCreate + 1);
        MProposalPrice testMProposalPrice = mProposalPriceList.get(mProposalPriceList.size() - 1);
        assertThat(testMProposalPrice.getProposedPrice()).isEqualTo(DEFAULT_PROPOSED_PRICE);
        assertThat(testMProposalPrice.getCeilingPrice()).isEqualTo(DEFAULT_CEILING_PRICE);
        assertThat(testMProposalPrice.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMProposalPrice.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMProposalPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mProposalPriceRepository.findAll().size();

        // Create the MProposalPrice with an existing ID
        mProposalPrice.setId(1L);
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(mProposalPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMProposalPriceMockMvc.perform(post("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPrice in the database
        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProposedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceRepository.findAll().size();
        // set the field null
        mProposalPrice.setProposedPrice(null);

        // Create the MProposalPrice, which fails.
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(mProposalPrice);

        restMProposalPriceMockMvc.perform(post("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCeilingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mProposalPriceRepository.findAll().size();
        // set the field null
        mProposalPrice.setCeilingPrice(null);

        // Create the MProposalPrice, which fails.
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(mProposalPrice);

        restMProposalPriceMockMvc.perform(post("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isBadRequest());

        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMProposalPrices() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMProposalPrice() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get the mProposalPrice
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices/{id}", mProposalPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mProposalPrice.getId().intValue()))
            .andExpect(jsonPath("$.proposedPrice").value(DEFAULT_PROPOSED_PRICE.intValue()))
            .andExpect(jsonPath("$.ceilingPrice").value(DEFAULT_CEILING_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMProposalPricesByIdFiltering() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        Long id = mProposalPrice.getId();

        defaultMProposalPriceShouldBeFound("id.equals=" + id);
        defaultMProposalPriceShouldNotBeFound("id.notEquals=" + id);

        defaultMProposalPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMProposalPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultMProposalPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMProposalPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.equals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.equals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice not equals to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.notEquals=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice not equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.notEquals=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice in DEFAULT_PROPOSED_PRICE or UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.in=" + DEFAULT_PROPOSED_PRICE + "," + UPDATED_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice equals to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.in=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice is not null
        defaultMProposalPriceShouldBeFound("proposedPrice.specified=true");

        // Get all the mProposalPriceList where proposedPrice is null
        defaultMProposalPriceShouldNotBeFound("proposedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice is greater than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.greaterThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice is greater than or equal to UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.greaterThanOrEqual=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice is less than or equal to DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.lessThanOrEqual=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice is less than or equal to SMALLER_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.lessThanOrEqual=" + SMALLER_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice is less than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.lessThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice is less than UPDATED_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.lessThan=" + UPDATED_PROPOSED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByProposedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where proposedPrice is greater than DEFAULT_PROPOSED_PRICE
        defaultMProposalPriceShouldNotBeFound("proposedPrice.greaterThan=" + DEFAULT_PROPOSED_PRICE);

        // Get all the mProposalPriceList where proposedPrice is greater than SMALLER_PROPOSED_PRICE
        defaultMProposalPriceShouldBeFound("proposedPrice.greaterThan=" + SMALLER_PROPOSED_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice equals to DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.equals=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.equals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice not equals to DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.notEquals=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice not equals to UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.notEquals=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice in DEFAULT_CEILING_PRICE or UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.in=" + DEFAULT_CEILING_PRICE + "," + UPDATED_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice equals to UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.in=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice is not null
        defaultMProposalPriceShouldBeFound("ceilingPrice.specified=true");

        // Get all the mProposalPriceList where ceilingPrice is null
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice is greater than or equal to DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.greaterThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice is greater than or equal to UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.greaterThanOrEqual=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice is less than or equal to DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.lessThanOrEqual=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice is less than or equal to SMALLER_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.lessThanOrEqual=" + SMALLER_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice is less than DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.lessThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice is less than UPDATED_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.lessThan=" + UPDATED_CEILING_PRICE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByCeilingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where ceilingPrice is greater than DEFAULT_CEILING_PRICE
        defaultMProposalPriceShouldNotBeFound("ceilingPrice.greaterThan=" + DEFAULT_CEILING_PRICE);

        // Get all the mProposalPriceList where ceilingPrice is greater than SMALLER_CEILING_PRICE
        defaultMProposalPriceShouldBeFound("ceilingPrice.greaterThan=" + SMALLER_CEILING_PRICE);
    }


    @Test
    @Transactional
    public void getAllMProposalPricesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where uid equals to DEFAULT_UID
        defaultMProposalPriceShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mProposalPriceList where uid equals to UPDATED_UID
        defaultMProposalPriceShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where uid not equals to DEFAULT_UID
        defaultMProposalPriceShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mProposalPriceList where uid not equals to UPDATED_UID
        defaultMProposalPriceShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where uid in DEFAULT_UID or UPDATED_UID
        defaultMProposalPriceShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mProposalPriceList where uid equals to UPDATED_UID
        defaultMProposalPriceShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where uid is not null
        defaultMProposalPriceShouldBeFound("uid.specified=true");

        // Get all the mProposalPriceList where uid is null
        defaultMProposalPriceShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where active equals to DEFAULT_ACTIVE
        defaultMProposalPriceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where active not equals to DEFAULT_ACTIVE
        defaultMProposalPriceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mProposalPriceList where active not equals to UPDATED_ACTIVE
        defaultMProposalPriceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMProposalPriceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mProposalPriceList where active equals to UPDATED_ACTIVE
        defaultMProposalPriceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        // Get all the mProposalPriceList where active is not null
        defaultMProposalPriceShouldBeFound("active.specified=true");

        // Get all the mProposalPriceList where active is null
        defaultMProposalPriceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMProposalPricesByBiddingSubmissionIsEqualToSomething() throws Exception {
        // Get already existing entity
        MBiddingSubmission biddingSubmission = mProposalPrice.getBiddingSubmission();
        mProposalPriceRepository.saveAndFlush(mProposalPrice);
        Long biddingSubmissionId = biddingSubmission.getId();

        // Get all the mProposalPriceList where biddingSubmission equals to biddingSubmissionId
        defaultMProposalPriceShouldBeFound("biddingSubmissionId.equals=" + biddingSubmissionId);

        // Get all the mProposalPriceList where biddingSubmission equals to biddingSubmissionId + 1
        defaultMProposalPriceShouldNotBeFound("biddingSubmissionId.equals=" + (biddingSubmissionId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPricesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mProposalPrice.getAdOrganization();
        mProposalPriceRepository.saveAndFlush(mProposalPrice);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mProposalPriceList where adOrganization equals to adOrganizationId
        defaultMProposalPriceShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mProposalPriceList where adOrganization equals to adOrganizationId + 1
        defaultMProposalPriceShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMProposalPricesByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mProposalPrice.getAttachment();
        mProposalPriceRepository.saveAndFlush(mProposalPrice);
        Long attachmentId = attachment.getId();

        // Get all the mProposalPriceList where attachment equals to attachmentId
        defaultMProposalPriceShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mProposalPriceList where attachment equals to attachmentId + 1
        defaultMProposalPriceShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMProposalPriceShouldBeFound(String filter) throws Exception {
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mProposalPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].proposedPrice").value(hasItem(DEFAULT_PROPOSED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ceilingPrice").value(hasItem(DEFAULT_CEILING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMProposalPriceShouldNotBeFound(String filter) throws Exception {
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMProposalPrice() throws Exception {
        // Get the mProposalPrice
        restMProposalPriceMockMvc.perform(get("/api/m-proposal-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMProposalPrice() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        int databaseSizeBeforeUpdate = mProposalPriceRepository.findAll().size();

        // Update the mProposalPrice
        MProposalPrice updatedMProposalPrice = mProposalPriceRepository.findById(mProposalPrice.getId()).get();
        // Disconnect from session so that the updates on updatedMProposalPrice are not directly saved in db
        em.detach(updatedMProposalPrice);
        updatedMProposalPrice
            .proposedPrice(UPDATED_PROPOSED_PRICE)
            .ceilingPrice(UPDATED_CEILING_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(updatedMProposalPrice);

        restMProposalPriceMockMvc.perform(put("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isOk());

        // Validate the MProposalPrice in the database
        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeUpdate);
        MProposalPrice testMProposalPrice = mProposalPriceList.get(mProposalPriceList.size() - 1);
        assertThat(testMProposalPrice.getProposedPrice()).isEqualTo(UPDATED_PROPOSED_PRICE);
        assertThat(testMProposalPrice.getCeilingPrice()).isEqualTo(UPDATED_CEILING_PRICE);
        assertThat(testMProposalPrice.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMProposalPrice.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMProposalPrice() throws Exception {
        int databaseSizeBeforeUpdate = mProposalPriceRepository.findAll().size();

        // Create the MProposalPrice
        MProposalPriceDTO mProposalPriceDTO = mProposalPriceMapper.toDto(mProposalPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMProposalPriceMockMvc.perform(put("/api/m-proposal-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mProposalPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MProposalPrice in the database
        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMProposalPrice() throws Exception {
        // Initialize the database
        mProposalPriceRepository.saveAndFlush(mProposalPrice);

        int databaseSizeBeforeDelete = mProposalPriceRepository.findAll().size();

        // Delete the mProposalPrice
        restMProposalPriceMockMvc.perform(delete("/api/m-proposal-prices/{id}", mProposalPrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MProposalPrice> mProposalPriceList = mProposalPriceRepository.findAll();
        assertThat(mProposalPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
