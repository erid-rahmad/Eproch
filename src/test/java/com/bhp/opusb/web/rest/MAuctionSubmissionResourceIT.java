package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.repository.MAuctionSubmissionRepository;
import com.bhp.opusb.service.MAuctionSubmissionService;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionMapper;
import com.bhp.opusb.service.dto.MAuctionSubmissionCriteria;
import com.bhp.opusb.service.MAuctionSubmissionQueryService;

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
 * Integration tests for the {@link MAuctionSubmissionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionSubmissionResourceIT {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionSubmissionRepository mAuctionSubmissionRepository;

    @Autowired
    private MAuctionSubmissionMapper mAuctionSubmissionMapper;

    @Autowired
    private MAuctionSubmissionService mAuctionSubmissionService;

    @Autowired
    private MAuctionSubmissionQueryService mAuctionSubmissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionSubmissionMockMvc;

    private MAuctionSubmission mAuctionSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmission createEntity(EntityManager em) {
        MAuctionSubmission mAuctionSubmission = new MAuctionSubmission()
            .price(DEFAULT_PRICE)
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
        mAuctionSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmission.setAuctionItem(mAuctionItem);
        return mAuctionSubmission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionSubmission createUpdatedEntity(EntityManager em) {
        MAuctionSubmission mAuctionSubmission = new MAuctionSubmission()
            .price(UPDATED_PRICE)
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
        mAuctionSubmission.setAdOrganization(aDOrganization);
        // Add required entity
        MAuctionItem mAuctionItem;
        if (TestUtil.findAll(em, MAuctionItem.class).isEmpty()) {
            mAuctionItem = MAuctionItemResourceIT.createUpdatedEntity(em);
            em.persist(mAuctionItem);
            em.flush();
        } else {
            mAuctionItem = TestUtil.findAll(em, MAuctionItem.class).get(0);
        }
        mAuctionSubmission.setAuctionItem(mAuctionItem);
        return mAuctionSubmission;
    }

    @BeforeEach
    public void initTest() {
        mAuctionSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionSubmission() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionRepository.findAll().size();

        // Create the MAuctionSubmission
        MAuctionSubmissionDTO mAuctionSubmissionDTO = mAuctionSubmissionMapper.toDto(mAuctionSubmission);
        restMAuctionSubmissionMockMvc.perform(post("/api/m-auction-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionSubmission in the database
        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionSubmission testMAuctionSubmission = mAuctionSubmissionList.get(mAuctionSubmissionList.size() - 1);
        assertThat(testMAuctionSubmission.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMAuctionSubmission.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionSubmission.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionSubmissionRepository.findAll().size();

        // Create the MAuctionSubmission with an existing ID
        mAuctionSubmission.setId(1L);
        MAuctionSubmissionDTO mAuctionSubmissionDTO = mAuctionSubmissionMapper.toDto(mAuctionSubmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionSubmissionMockMvc.perform(post("/api/m-auction-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmission in the database
        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAuctionSubmissionRepository.findAll().size();
        // set the field null
        mAuctionSubmission.setPrice(null);

        // Create the MAuctionSubmission, which fails.
        MAuctionSubmissionDTO mAuctionSubmissionDTO = mAuctionSubmissionMapper.toDto(mAuctionSubmission);

        restMAuctionSubmissionMockMvc.perform(post("/api/m-auction-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionDTO)))
            .andExpect(status().isBadRequest());

        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissions() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionSubmission() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get the mAuctionSubmission
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions/{id}", mAuctionSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionSubmission.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        Long id = mAuctionSubmission.getId();

        defaultMAuctionSubmissionShouldBeFound("id.equals=" + id);
        defaultMAuctionSubmissionShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionSubmissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionSubmissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionSubmissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionSubmissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price equals to DEFAULT_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price not equals to DEFAULT_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price not equals to UPDATED_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the mAuctionSubmissionList where price equals to UPDATED_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price is not null
        defaultMAuctionSubmissionShouldBeFound("price.specified=true");

        // Get all the mAuctionSubmissionList where price is null
        defaultMAuctionSubmissionShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price is greater than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price is greater than or equal to UPDATED_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price is less than or equal to DEFAULT_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price is less than or equal to SMALLER_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price is less than DEFAULT_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price is less than UPDATED_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where price is greater than DEFAULT_PRICE
        defaultMAuctionSubmissionShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the mAuctionSubmissionList where price is greater than SMALLER_PRICE
        defaultMAuctionSubmissionShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where uid equals to DEFAULT_UID
        defaultMAuctionSubmissionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionSubmissionList where uid equals to UPDATED_UID
        defaultMAuctionSubmissionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where uid not equals to DEFAULT_UID
        defaultMAuctionSubmissionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionSubmissionList where uid not equals to UPDATED_UID
        defaultMAuctionSubmissionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionSubmissionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionSubmissionList where uid equals to UPDATED_UID
        defaultMAuctionSubmissionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where uid is not null
        defaultMAuctionSubmissionShouldBeFound("uid.specified=true");

        // Get all the mAuctionSubmissionList where uid is null
        defaultMAuctionSubmissionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where active equals to DEFAULT_ACTIVE
        defaultMAuctionSubmissionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionSubmissionList where active equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionSubmissionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionSubmissionList where active not equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionSubmissionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionSubmissionList where active equals to UPDATED_ACTIVE
        defaultMAuctionSubmissionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        // Get all the mAuctionSubmissionList where active is not null
        defaultMAuctionSubmissionShouldBeFound("active.specified=true");

        // Get all the mAuctionSubmissionList where active is null
        defaultMAuctionSubmissionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionSubmission.getAdOrganization();
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionSubmissionList where adOrganization equals to adOrganizationId
        defaultMAuctionSubmissionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionSubmissionList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionSubmissionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMAuctionSubmissionsByAuctionItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        MAuctionItem auctionItem = mAuctionSubmission.getAuctionItem();
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);
        Long auctionItemId = auctionItem.getId();

        // Get all the mAuctionSubmissionList where auctionItem equals to auctionItemId
        defaultMAuctionSubmissionShouldBeFound("auctionItemId.equals=" + auctionItemId);

        // Get all the mAuctionSubmissionList where auctionItem equals to auctionItemId + 1
        defaultMAuctionSubmissionShouldNotBeFound("auctionItemId.equals=" + (auctionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionSubmissionShouldBeFound(String filter) throws Exception {
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionSubmissionShouldNotBeFound(String filter) throws Exception {
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionSubmission() throws Exception {
        // Get the mAuctionSubmission
        restMAuctionSubmissionMockMvc.perform(get("/api/m-auction-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionSubmission() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        int databaseSizeBeforeUpdate = mAuctionSubmissionRepository.findAll().size();

        // Update the mAuctionSubmission
        MAuctionSubmission updatedMAuctionSubmission = mAuctionSubmissionRepository.findById(mAuctionSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionSubmission are not directly saved in db
        em.detach(updatedMAuctionSubmission);
        updatedMAuctionSubmission
            .price(UPDATED_PRICE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionSubmissionDTO mAuctionSubmissionDTO = mAuctionSubmissionMapper.toDto(updatedMAuctionSubmission);

        restMAuctionSubmissionMockMvc.perform(put("/api/m-auction-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionSubmission in the database
        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeUpdate);
        MAuctionSubmission testMAuctionSubmission = mAuctionSubmissionList.get(mAuctionSubmissionList.size() - 1);
        assertThat(testMAuctionSubmission.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMAuctionSubmission.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionSubmission.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionSubmissionRepository.findAll().size();

        // Create the MAuctionSubmission
        MAuctionSubmissionDTO mAuctionSubmissionDTO = mAuctionSubmissionMapper.toDto(mAuctionSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionSubmissionMockMvc.perform(put("/api/m-auction-submissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionSubmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionSubmission in the database
        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionSubmission() throws Exception {
        // Initialize the database
        mAuctionSubmissionRepository.saveAndFlush(mAuctionSubmission);

        int databaseSizeBeforeDelete = mAuctionSubmissionRepository.findAll().size();

        // Delete the mAuctionSubmission
        restMAuctionSubmissionMockMvc.perform(delete("/api/m-auction-submissions/{id}", mAuctionSubmission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionSubmission> mAuctionSubmissionList = mAuctionSubmissionRepository.findAll();
        assertThat(mAuctionSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
