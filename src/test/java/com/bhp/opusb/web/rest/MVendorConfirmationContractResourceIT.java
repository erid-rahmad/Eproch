package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorConfirmationContract;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.repository.MVendorConfirmationContractRepository;
import com.bhp.opusb.service.MVendorConfirmationContractService;
import com.bhp.opusb.service.dto.MVendorConfirmationContractDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationContractMapper;
import com.bhp.opusb.service.dto.MVendorConfirmationContractCriteria;
import com.bhp.opusb.service.MVendorConfirmationContractQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVendorConfirmationContractResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorConfirmationContractResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CONFIRMATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_DETAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PUBLISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PUBLISH_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CONTRACT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CONTRACT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_END_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private MVendorConfirmationContractRepository mVendorConfirmationContractRepository;

    @Autowired
    private MVendorConfirmationContractMapper mVendorConfirmationContractMapper;

    @Autowired
    private MVendorConfirmationContractService mVendorConfirmationContractService;

    @Autowired
    private MVendorConfirmationContractQueryService mVendorConfirmationContractQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorConfirmationContractMockMvc;

    private MVendorConfirmationContract mVendorConfirmationContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationContract createEntity(EntityManager em) {
        MVendorConfirmationContract mVendorConfirmationContract = new MVendorConfirmationContract()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .confirmationNo(DEFAULT_CONFIRMATION_NO)
            .contractDetail(DEFAULT_CONTRACT_DETAIL)
            .publishDate(DEFAULT_PUBLISH_DATE)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationContract.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mVendorConfirmationContract.setAttachment(cAttachment);
        // Add required entity
        MVendorConfirmationLine mVendorConfirmationLine;
        if (TestUtil.findAll(em, MVendorConfirmationLine.class).isEmpty()) {
            mVendorConfirmationLine = MVendorConfirmationLineResourceIT.createEntity(em);
            em.persist(mVendorConfirmationLine);
            em.flush();
        } else {
            mVendorConfirmationLine = TestUtil.findAll(em, MVendorConfirmationLine.class).get(0);
        }
        mVendorConfirmationContract.setVendorConfirmationLine(mVendorConfirmationLine);
        return mVendorConfirmationContract;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorConfirmationContract createUpdatedEntity(EntityManager em) {
        MVendorConfirmationContract mVendorConfirmationContract = new MVendorConfirmationContract()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .confirmationNo(UPDATED_CONFIRMATION_NO)
            .contractDetail(UPDATED_CONTRACT_DETAIL)
            .publishDate(UPDATED_PUBLISH_DATE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mVendorConfirmationContract.setAdOrganization(aDOrganization);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        mVendorConfirmationContract.setAttachment(cAttachment);
        // Add required entity
        MVendorConfirmationLine mVendorConfirmationLine;
        if (TestUtil.findAll(em, MVendorConfirmationLine.class).isEmpty()) {
            mVendorConfirmationLine = MVendorConfirmationLineResourceIT.createUpdatedEntity(em);
            em.persist(mVendorConfirmationLine);
            em.flush();
        } else {
            mVendorConfirmationLine = TestUtil.findAll(em, MVendorConfirmationLine.class).get(0);
        }
        mVendorConfirmationContract.setVendorConfirmationLine(mVendorConfirmationLine);
        return mVendorConfirmationContract;
    }

    @BeforeEach
    public void initTest() {
        mVendorConfirmationContract = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationContract() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationContractRepository.findAll().size();

        // Create the MVendorConfirmationContract
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO = mVendorConfirmationContractMapper.toDto(mVendorConfirmationContract);
        restMVendorConfirmationContractMockMvc.perform(post("/api/m-vendor-confirmation-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationContractDTO)))
            .andExpect(status().isCreated());

        // Validate the MVendorConfirmationContract in the database
        List<MVendorConfirmationContract> mVendorConfirmationContractList = mVendorConfirmationContractRepository.findAll();
        assertThat(mVendorConfirmationContractList).hasSize(databaseSizeBeforeCreate + 1);
        MVendorConfirmationContract testMVendorConfirmationContract = mVendorConfirmationContractList.get(mVendorConfirmationContractList.size() - 1);
        assertThat(testMVendorConfirmationContract.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMVendorConfirmationContract.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMVendorConfirmationContract.getConfirmationNo()).isEqualTo(DEFAULT_CONFIRMATION_NO);
        assertThat(testMVendorConfirmationContract.getContractDetail()).isEqualTo(DEFAULT_CONTRACT_DETAIL);
        assertThat(testMVendorConfirmationContract.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testMVendorConfirmationContract.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testMVendorConfirmationContract.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void createMVendorConfirmationContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVendorConfirmationContractRepository.findAll().size();

        // Create the MVendorConfirmationContract with an existing ID
        mVendorConfirmationContract.setId(1L);
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO = mVendorConfirmationContractMapper.toDto(mVendorConfirmationContract);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVendorConfirmationContractMockMvc.perform(post("/api/m-vendor-confirmation-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationContract in the database
        List<MVendorConfirmationContract> mVendorConfirmationContractList = mVendorConfirmationContractRepository.findAll();
        assertThat(mVendorConfirmationContractList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContracts() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmationNo").value(hasItem(DEFAULT_CONFIRMATION_NO)))
            .andExpect(jsonPath("$.[*].contractDetail").value(hasItem(DEFAULT_CONTRACT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(DEFAULT_PUBLISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMVendorConfirmationContract() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get the mVendorConfirmationContract
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts/{id}", mVendorConfirmationContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorConfirmationContract.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.confirmationNo").value(DEFAULT_CONFIRMATION_NO))
            .andExpect(jsonPath("$.contractDetail").value(DEFAULT_CONTRACT_DETAIL.toString()))
            .andExpect(jsonPath("$.publishDate").value(DEFAULT_PUBLISH_DATE.toString()))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()));
    }


    @Test
    @Transactional
    public void getMVendorConfirmationContractsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        Long id = mVendorConfirmationContract.getId();

        defaultMVendorConfirmationContractShouldBeFound("id.equals=" + id);
        defaultMVendorConfirmationContractShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorConfirmationContractShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorConfirmationContractShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorConfirmationContractShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorConfirmationContractShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where uid equals to DEFAULT_UID
        defaultMVendorConfirmationContractShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationContractList where uid equals to UPDATED_UID
        defaultMVendorConfirmationContractShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where uid not equals to DEFAULT_UID
        defaultMVendorConfirmationContractShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mVendorConfirmationContractList where uid not equals to UPDATED_UID
        defaultMVendorConfirmationContractShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where uid in DEFAULT_UID or UPDATED_UID
        defaultMVendorConfirmationContractShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mVendorConfirmationContractList where uid equals to UPDATED_UID
        defaultMVendorConfirmationContractShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where uid is not null
        defaultMVendorConfirmationContractShouldBeFound("uid.specified=true");

        // Get all the mVendorConfirmationContractList where uid is null
        defaultMVendorConfirmationContractShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where active equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationContractShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationContractList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationContractShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where active not equals to DEFAULT_ACTIVE
        defaultMVendorConfirmationContractShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mVendorConfirmationContractList where active not equals to UPDATED_ACTIVE
        defaultMVendorConfirmationContractShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMVendorConfirmationContractShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mVendorConfirmationContractList where active equals to UPDATED_ACTIVE
        defaultMVendorConfirmationContractShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where active is not null
        defaultMVendorConfirmationContractShouldBeFound("active.specified=true");

        // Get all the mVendorConfirmationContractList where active is null
        defaultMVendorConfirmationContractShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo equals to DEFAULT_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.equals=" + DEFAULT_CONFIRMATION_NO);

        // Get all the mVendorConfirmationContractList where confirmationNo equals to UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.equals=" + UPDATED_CONFIRMATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo not equals to DEFAULT_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.notEquals=" + DEFAULT_CONFIRMATION_NO);

        // Get all the mVendorConfirmationContractList where confirmationNo not equals to UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.notEquals=" + UPDATED_CONFIRMATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo in DEFAULT_CONFIRMATION_NO or UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.in=" + DEFAULT_CONFIRMATION_NO + "," + UPDATED_CONFIRMATION_NO);

        // Get all the mVendorConfirmationContractList where confirmationNo equals to UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.in=" + UPDATED_CONFIRMATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo is not null
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.specified=true");

        // Get all the mVendorConfirmationContractList where confirmationNo is null
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoContainsSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo contains DEFAULT_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.contains=" + DEFAULT_CONFIRMATION_NO);

        // Get all the mVendorConfirmationContractList where confirmationNo contains UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.contains=" + UPDATED_CONFIRMATION_NO);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByConfirmationNoNotContainsSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where confirmationNo does not contain DEFAULT_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldNotBeFound("confirmationNo.doesNotContain=" + DEFAULT_CONFIRMATION_NO);

        // Get all the mVendorConfirmationContractList where confirmationNo does not contain UPDATED_CONFIRMATION_NO
        defaultMVendorConfirmationContractShouldBeFound("confirmationNo.doesNotContain=" + UPDATED_CONFIRMATION_NO);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate equals to DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.equals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.equals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate not equals to DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.notEquals=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate not equals to UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.notEquals=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate in DEFAULT_PUBLISH_DATE or UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.in=" + DEFAULT_PUBLISH_DATE + "," + UPDATED_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate equals to UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.in=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate is not null
        defaultMVendorConfirmationContractShouldBeFound("publishDate.specified=true");

        // Get all the mVendorConfirmationContractList where publishDate is null
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate is greater than or equal to DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.greaterThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate is greater than or equal to UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.greaterThanOrEqual=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate is less than or equal to DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.lessThanOrEqual=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate is less than or equal to SMALLER_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.lessThanOrEqual=" + SMALLER_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate is less than DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.lessThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate is less than UPDATED_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.lessThan=" + UPDATED_PUBLISH_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByPublishDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where publishDate is greater than DEFAULT_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("publishDate.greaterThan=" + DEFAULT_PUBLISH_DATE);

        // Get all the mVendorConfirmationContractList where publishDate is greater than SMALLER_PUBLISH_DATE
        defaultMVendorConfirmationContractShouldBeFound("publishDate.greaterThan=" + SMALLER_PUBLISH_DATE);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate equals to DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.equals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.equals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate not equals to DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.notEquals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate not equals to UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.notEquals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate in DEFAULT_CONTRACT_START_DATE or UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.in=" + DEFAULT_CONTRACT_START_DATE + "," + UPDATED_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.in=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate is not null
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.specified=true");

        // Get all the mVendorConfirmationContractList where contractStartDate is null
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate is greater than or equal to DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate is greater than or equal to UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.greaterThanOrEqual=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate is less than or equal to DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.lessThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate is less than or equal to SMALLER_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.lessThanOrEqual=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate is less than DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.lessThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate is less than UPDATED_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.lessThan=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractStartDate is greater than DEFAULT_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractStartDate.greaterThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the mVendorConfirmationContractList where contractStartDate is greater than SMALLER_CONTRACT_START_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractStartDate.greaterThan=" + SMALLER_CONTRACT_START_DATE);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate equals to DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.equals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.equals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate not equals to DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.notEquals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate not equals to UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.notEquals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate in DEFAULT_CONTRACT_END_DATE or UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.in=" + DEFAULT_CONTRACT_END_DATE + "," + UPDATED_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.in=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate is not null
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.specified=true");

        // Get all the mVendorConfirmationContractList where contractEndDate is null
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate is greater than or equal to DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate is greater than or equal to UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.greaterThanOrEqual=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate is less than or equal to DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.lessThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate is less than or equal to SMALLER_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.lessThanOrEqual=" + SMALLER_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate is less than DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.lessThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate is less than UPDATED_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.lessThan=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByContractEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        // Get all the mVendorConfirmationContractList where contractEndDate is greater than DEFAULT_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldNotBeFound("contractEndDate.greaterThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the mVendorConfirmationContractList where contractEndDate is greater than SMALLER_CONTRACT_END_DATE
        defaultMVendorConfirmationContractShouldBeFound("contractEndDate.greaterThan=" + SMALLER_CONTRACT_END_DATE);
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mVendorConfirmationContract.getAdOrganization();
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mVendorConfirmationContractList where adOrganization equals to adOrganizationId
        defaultMVendorConfirmationContractShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mVendorConfirmationContractList where adOrganization equals to adOrganizationId + 1
        defaultMVendorConfirmationContractShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment attachment = mVendorConfirmationContract.getAttachment();
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);
        Long attachmentId = attachment.getId();

        // Get all the mVendorConfirmationContractList where attachment equals to attachmentId
        defaultMVendorConfirmationContractShouldBeFound("attachmentId.equals=" + attachmentId);

        // Get all the mVendorConfirmationContractList where attachment equals to attachmentId + 1
        defaultMVendorConfirmationContractShouldNotBeFound("attachmentId.equals=" + (attachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorConfirmationContractsByVendorConfirmationLineIsEqualToSomething() throws Exception {
        // Get already existing entity
        MVendorConfirmationLine vendorConfirmationLine = mVendorConfirmationContract.getVendorConfirmationLine();
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);
        Long vendorConfirmationLineId = vendorConfirmationLine.getId();

        // Get all the mVendorConfirmationContractList where vendorConfirmationLine equals to vendorConfirmationLineId
        defaultMVendorConfirmationContractShouldBeFound("vendorConfirmationLineId.equals=" + vendorConfirmationLineId);

        // Get all the mVendorConfirmationContractList where vendorConfirmationLine equals to vendorConfirmationLineId + 1
        defaultMVendorConfirmationContractShouldNotBeFound("vendorConfirmationLineId.equals=" + (vendorConfirmationLineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorConfirmationContractShouldBeFound(String filter) throws Exception {
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorConfirmationContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmationNo").value(hasItem(DEFAULT_CONFIRMATION_NO)))
            .andExpect(jsonPath("$.[*].contractDetail").value(hasItem(DEFAULT_CONTRACT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(DEFAULT_PUBLISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorConfirmationContractShouldNotBeFound(String filter) throws Exception {
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorConfirmationContract() throws Exception {
        // Get the mVendorConfirmationContract
        restMVendorConfirmationContractMockMvc.perform(get("/api/m-vendor-confirmation-contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVendorConfirmationContract() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        int databaseSizeBeforeUpdate = mVendorConfirmationContractRepository.findAll().size();

        // Update the mVendorConfirmationContract
        MVendorConfirmationContract updatedMVendorConfirmationContract = mVendorConfirmationContractRepository.findById(mVendorConfirmationContract.getId()).get();
        // Disconnect from session so that the updates on updatedMVendorConfirmationContract are not directly saved in db
        em.detach(updatedMVendorConfirmationContract);
        updatedMVendorConfirmationContract
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .confirmationNo(UPDATED_CONFIRMATION_NO)
            .contractDetail(UPDATED_CONTRACT_DETAIL)
            .publishDate(UPDATED_PUBLISH_DATE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE);
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO = mVendorConfirmationContractMapper.toDto(updatedMVendorConfirmationContract);

        restMVendorConfirmationContractMockMvc.perform(put("/api/m-vendor-confirmation-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationContractDTO)))
            .andExpect(status().isOk());

        // Validate the MVendorConfirmationContract in the database
        List<MVendorConfirmationContract> mVendorConfirmationContractList = mVendorConfirmationContractRepository.findAll();
        assertThat(mVendorConfirmationContractList).hasSize(databaseSizeBeforeUpdate);
        MVendorConfirmationContract testMVendorConfirmationContract = mVendorConfirmationContractList.get(mVendorConfirmationContractList.size() - 1);
        assertThat(testMVendorConfirmationContract.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMVendorConfirmationContract.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMVendorConfirmationContract.getConfirmationNo()).isEqualTo(UPDATED_CONFIRMATION_NO);
        assertThat(testMVendorConfirmationContract.getContractDetail()).isEqualTo(UPDATED_CONTRACT_DETAIL);
        assertThat(testMVendorConfirmationContract.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testMVendorConfirmationContract.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testMVendorConfirmationContract.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMVendorConfirmationContract() throws Exception {
        int databaseSizeBeforeUpdate = mVendorConfirmationContractRepository.findAll().size();

        // Create the MVendorConfirmationContract
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO = mVendorConfirmationContractMapper.toDto(mVendorConfirmationContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVendorConfirmationContractMockMvc.perform(put("/api/m-vendor-confirmation-contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mVendorConfirmationContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MVendorConfirmationContract in the database
        List<MVendorConfirmationContract> mVendorConfirmationContractList = mVendorConfirmationContractRepository.findAll();
        assertThat(mVendorConfirmationContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVendorConfirmationContract() throws Exception {
        // Initialize the database
        mVendorConfirmationContractRepository.saveAndFlush(mVendorConfirmationContract);

        int databaseSizeBeforeDelete = mVendorConfirmationContractRepository.findAll().size();

        // Delete the mVendorConfirmationContract
        restMVendorConfirmationContractMockMvc.perform(delete("/api/m-vendor-confirmation-contracts/{id}", mVendorConfirmationContract.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MVendorConfirmationContract> mVendorConfirmationContractList = mVendorConfirmationContractRepository.findAll();
        assertThat(mVendorConfirmationContractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
